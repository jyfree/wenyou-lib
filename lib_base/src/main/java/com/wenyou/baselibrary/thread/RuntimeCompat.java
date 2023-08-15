package com.wenyou.baselibrary.thread;

import android.os.StrictMode;

import com.wenyou.baselibrary.utils.YLogUtils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Pattern;

/**
 * @description glide方式，获取CPU核数
 * @date: 2022/5/16 10:44
 * @author: jy
 */
public class RuntimeCompat {
    private static final String TAG = "RuntimeCompat";
    private static final String CPU_NAME_REGEX = "cpu[0-9]+";
    private static final String CPU_LOCATION = "/sys/devices/system/cpu/";

    private RuntimeCompat() {
        // Utility class.
    }

    /**
     * Determines the number of cores available on the device.
     */
    static int availableProcessors() {
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     * Determines the number of cores available on the device (pre-v17).
     *
     * <p>Before Jellybean, {@link Runtime#availableProcessors()} returned the number of awake cores,
     * which may not be the number of available cores depending on the device's current state. See
     * https://stackoverflow.com/a/30150409.
     *
     * @return the maximum number of processors available to the VM; never smaller than one
     */
    @SuppressWarnings("PMD")
    private static int getCoreCountPre17() {
        // We override the current ThreadPolicy to allow disk reads.
        // This shouldn't actually do disk-IO and accesses a device file.
        // See: https://github.com/bumptech/glide/issues/1170
        File[] cpus = null;
        StrictMode.ThreadPolicy originalPolicy = StrictMode.allowThreadDiskReads();
        try {
            File cpuInfo = new File(CPU_LOCATION);
            final Pattern cpuNamePattern = Pattern.compile(CPU_NAME_REGEX);
            cpus = cpuInfo.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File file, String s) {
                    return cpuNamePattern.matcher(s).matches();
                }
            });
        } catch (Throwable t) {
            YLogUtils.INSTANCE.eThrowTag(TAG, t, "Failed to calculate accurate cpu count");
        } finally {
            StrictMode.setThreadPolicy(originalPolicy);
        }
        return Math.max(1, cpus != null ? cpus.length : 0);
    }
}
