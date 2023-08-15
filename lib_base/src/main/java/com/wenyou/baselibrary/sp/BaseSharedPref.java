package com.wenyou.baselibrary.sp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcelable;

import com.wenyou.baselibrary.utils.EncodeUtils;
import com.wenyou.baselibrary.utils.ParcelableUtils;
import com.wenyou.baselibrary.utils.YLogUtils;

import java.util.Map;
import java.util.Set;

/**
 * @description context.getSharedPreferences() 会比较耗时，创建文件 或者读取整个文件到内部Map<Key, Value>
 * editor.get 除了在等待 创建/获取文件getSharedPreferences()会等待耗时，还有就是变量同步保护，只是写入内存Map 未有其他操作
 * 1.故注意需要在文件getSharedPreferences()完全获得后调用一般没问题。
 * 2.其次需要注意频繁写入 会对其产生同步等待注意UI超时。----在没有同步等待时基本没耗时约1ms
 * editor.put
 * 也仅仅是synchronized (editor) 同步保护 写入Map 而已。故而注意大量频繁写入的同步保护耗时
 * ----在没有同步等待时基本没耗时约1ms
 * //注意写入提交是将整个文件数据重写，且相对耗时
 * //故 最好合并一次提交
 * editor.apply()     异步线程提交 -- 一般建议使用异步提交
 * editor.commit()    同步线程提交
 * <p>
 * SharedPreferences 是不需要 文件保存权限的
 * @date: 2022/5/9 13:57
 * @author: jy
 */
public class BaseSharedPref {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private String fileName;

    public BaseSharedPref(Context context, String fileName, int mode) {
        preferences = context.getSharedPreferences(fileName, mode);
        editor = preferences.edit();
        this.fileName = fileName;
    }

    public SharedPreferences.Editor getEditor() {
        return editor;
    }

    public SharedPreferences getPreferences() {
        return preferences;
    }

    /**
     * 存储 -未提交，需要最后调用 commit
     * 用于批量写入
     * 需要提交后才能get到
     */
    public void add(String key, Object object) {
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
    }


    /**
     * 存储
     */
    public void putCommit(String key, Object object) {
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
        editor.commit();
    }

    /**
     * 存储
     */
    public void putApply(String key, Object object) {
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            if (object != null) {
                editor.putString(key, object.toString());
            }
        }
        editor.apply();
    }

    /**
     * 获取保存的数据
     */
    public Object getSharedPreference(String key, Object defaultObject) {
        if (defaultObject instanceof String) {
            return preferences.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return preferences.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return preferences.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return preferences.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return preferences.getLong(key, (Long) defaultObject);
        } else {
            return preferences.getString(key, "");
        }
    }

    public String getString(String key, String defValue) {
        return preferences.getString(key, defValue);
    }

    public int getInt(String key, int defValue) {
        return preferences.getInt(key, defValue);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return preferences.getBoolean(key, defValue);
    }

    public float getFloat(String key, float defValue) {
        return preferences.getFloat(key, defValue);
    }

    public long getLong(String key, long defValue) {
        return preferences.getLong(key, defValue);
    }

    public Set<String> getStringSet(String keyName) {
        return preferences.getStringSet(keyName, null);
    }

    public void setStringSet(String keyName, Set<String> value) {
        editor.putStringSet(keyName, value);
        editor.apply();
    }

    public <T extends Parcelable> T getObject(String keyName, Parcelable.Creator<T> creator) {
        return ParcelableUtils.unMarShall(getString(keyName, ""), creator);
    }

    public void setObject(String keyName, Parcelable parcelable) {
        byte[] bytes = ParcelableUtils.marShall(parcelable);
        editor.putString(keyName, EncodeUtils.base64Encode2String(bytes));
    }

    public void commit() {
        editor.commit();
    }

    public void apply() {
        editor.apply();
    }

    /**
     * 移除某个key值已经对应的值
     */
    public void remove(String key) {
        editor.remove(key);
        editor.commit();
    }

    /**
     * 清除所有数据
     */
    public void clear() {
        editor.clear();
        editor.commit();
    }

    /**
     * 查询某个key是否存在
     */
    public Boolean contain(String key) {
        return preferences.contains(key);
    }

    /**
     * 返回所有的键值对
     */
    public Map<String, ?> loadAll() {
        long startTime = System.currentTimeMillis();
        Map<String, ?> map = preferences.getAll();
        YLogUtils.INSTANCE.i("预加载sp--getAll耗时--sp文件名", fileName, "time",
                System.currentTimeMillis() - startTime, "size", map.size());
        return map;
    }

}
