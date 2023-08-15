package com.wenyou.baselibrary.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.wenyou.baselibrary.utils.YLogUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @description logcat写文件
 * @date: 2021/12/16 11:57
 * @author: jy
 */
public class LogCatHelper {

    private static final String TAG = LogCatHelper.class.getSimpleName();
    private static LogCatHelper instance = null;

    private String dirPath;//保存路径
    private String zipDirPath;//zip缓存路径
    private ArrayBlockingQueue<String> mCacheLog;//日志缓冲队列
    private int buffSize;//缓存区大小
    private LogThread logThread;
    private boolean canWrite = false;
    private int cacheDay = 1;//缓存时间

    public static LogCatHelper getInstance() {
        if (instance == null) {
            instance = new LogCatHelper();
        }
        return instance;
    }

    public void init(Context mContext, Builder builder) {
        String tmpDirPath = getLogPath(mContext);
        if (TextUtils.isEmpty(builder.path)) {
            if (tmpDirPath == null) {
                tmpDirPath = Environment.getExternalStorageDirectory().getAbsolutePath()
                        + File.separator + "seeker";
                dirPath = tmpDirPath + File.separator + mContext.getPackageName();
            } else {
                dirPath = tmpDirPath + File.separator + "seeker";
            }
        } else {
            dirPath = builder.path;
        }
        zipDirPath = tmpDirPath;
        createPath();
        buffSize = builder.buffSize;
        mCacheLog = new ArrayBlockingQueue<>(builder.queueCapacity);
        if (builder.cacheDay > 0) {
            cacheDay = builder.cacheDay;
        }
    }

    private String getLogPath(Context context) {
        if (context == null) {
            return null;
        } else {
            File dir = null;
            String mountState = null;

            try {
                mountState = Environment.getExternalStorageState();
            } catch (Exception var5) {
                var5.printStackTrace();
                mountState = null;
            }

            File filesDir;
            String filesDirPath;
            if ("mounted".equals(mountState)) {
                filesDir = context.getExternalFilesDir((String) null);
                if (filesDir != null) {
                    filesDirPath = filesDir.getAbsolutePath();
                    if (!TextUtils.isEmpty(filesDirPath)) {
                        dir = new File(filesDirPath);
                    }
                }
            }

            if (dir == null) {
                filesDir = context.getFilesDir();
                if (filesDir != null) {
                    filesDirPath = filesDir.getAbsolutePath();
                    if (!TextUtils.isEmpty(filesDirPath)) {
                        dir = new File(filesDirPath);
                    }
                }
            }

            if (dir == null) {
                return null;
            } else {
                if (!dir.exists()) {
                    dir.mkdirs();
                    if (!dir.exists()) {
                        return null;
                    }
                }
                return dir.getAbsolutePath();
            }
        }
    }

    /**
     * 创建保存路径
     */
    private void createPath() {
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        canWrite = dir.exists() && dir.canWrite();
        YLogUtils.INSTANCE.iTag(TAG, "日志文件是否可以写入", canWrite, "路径", dirPath);
    }


    /**
     * 启动log日志保存
     */
    public void start() {
        if (TextUtils.isEmpty(dirPath) || null == mCacheLog) {
            YLogUtils.INSTANCE.eTag(TAG, "请先在全局Application中调用init(mContext,builder)初始化！");
            return;
        }
        if (YLogUtils.INSTANCE.getSHOW_LOG()) {
            logThread = new LogThread(dirPath);
            logThread.start();
        }
    }

    public void stop() {
        if (logThread != null) {
            logThread.stopWrite();
        }
    }

    /**
     * 将日志存入缓存区
     *
     * @param msg
     */
    public void writeLogFile(String msg) {
        try {
            if (!canWrite || null == mCacheLog) return;
            mCacheLog.offer(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class LogThread extends Thread {

        private FileOutputStream fos;
        private BufferedOutputStream mOutputStream;
        private String dirPath;
        private String fileName;
        private boolean openWrite = true;

        public LogThread(String dirPath) {
            this.dirPath = dirPath;
        }

        @Override
        public void run() {
            try {
                //创建log文件
                fileName = FormatDate.getFormatDate();
                File file = new File(dirPath, fileName + ".log");
                if (!file.exists()) {
                    file.createNewFile();
                }
                //过滤历史log文件，并删除
                filter(dirPath, fileName);

                fos = new FileOutputStream(file, true);
                mOutputStream = new BufferedOutputStream(fos, buffSize);

                while (openWrite && !isInterrupted()) {
                    if (mCacheLog.size() > 0) {
                        String msg = mCacheLog.poll() + "\r\n";
                        byte[] bytes = msg.getBytes();
                        mOutputStream.write(bytes, 0, bytes.length);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (mOutputStream != null) {
                        //缓冲区未满时，可将缓冲区内容写入文件，因为close会调用flush方法
                        mOutputStream.close();
                    }
                    if (fos != null) {
                        fos.close();
                        fos = null;
                    }

                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }

        public void stopWrite() {
            openWrite = false;
            this.interrupt();
        }
    }

    private void filter(String dirPath, String nowFileName) {
        try {
            int nowTime = 0;
            if (!TextUtils.isEmpty(nowFileName)) {
                nowTime = Integer.parseInt(nowFileName);
                YLogUtils.INSTANCE.iTag(TAG, "创建log文件", nowFileName, "当前时间", nowTime);
            }
            File root = new File(dirPath);
            File[] fileList = root.listFiles();
            if (fileList == null || fileList.length < 1) {
                return;
            }
            YLogUtils.INSTANCE.iTag(TAG, "过滤旧log文件，只保留" + cacheDay + "天log");
            for (File file : fileList) {
                String name = file.getName();
                if (!TextUtils.isEmpty(name) && name.endsWith(".log")) {
                    int time = Integer.parseInt(name.substring(0, name.length() - 4));
                    if (nowTime - time >= cacheDay) {
                        boolean success = file.delete();
                        YLogUtils.INSTANCE.iTag(TAG, "删除文件", name, "删除成功", success);
                    } else {
                        YLogUtils.INSTANCE.iTag(TAG, "保留文件", name);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            YLogUtils.INSTANCE.eTag(TAG, "过滤log失败", e.getMessage());
        }
    }

    /**
     * 获取logfile
     *
     * @return
     */
    public Map<String, File> getLogFile() {
        File root = new File(dirPath);
        File[] fileList = root.listFiles();
        Map<String, File> fileMap = new HashMap<String, File>();
        if (fileList == null) {
            return fileMap;
        }
        for (int i = 0; i < fileList.length; i++) {
            fileMap.put(fileList[i].getName(), fileList[i]);
            YLogUtils.INSTANCE.iTag(TAG, "getLogFile", fileList[i].getName());
        }
        return fileMap;
    }

    public String getDirPath() {
        return dirPath;
    }

    public String getZipDirPath() {
        return zipDirPath;
    }

    @SuppressLint("SimpleDateFormat")
    private static class FormatDate {

        public static String getFormatDate() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            return sdf.format(System.currentTimeMillis());
        }

        public static String getFormatTime() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS  ");
            return sdf.format(System.currentTimeMillis());
        }
    }

    public static Builder beginBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String path;
        private int queueCapacity = 500;
        private int buffSize = 100;
        private int cacheDay = 1;

        public Builder setPath(String path) {
            this.path = path;
            return this;
        }

        public Builder setQueueCapacity(int queueCapacity) {
            this.queueCapacity = queueCapacity;
            return this;
        }

        public Builder setBuffSize(int buffSize) {
            this.buffSize = buffSize;
            return this;
        }

        public Builder setShowLog(boolean showLog) {
            YLogUtils.INSTANCE.setSHOW_LOG(showLog);
            return this;
        }

        public Builder setCacheDay(int cacheDay) {
            this.cacheDay = cacheDay;
            return this;
        }
    }
}
