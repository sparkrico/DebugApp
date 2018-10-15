package sparkrico.com.debugapp.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.io.File;
import java.util.Arrays;

/**
 * Created by sparkrico on 2017/12/4.
 */

public class Utils {

    public static String getExternalStorageDirectory() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    public static String getExternalStoragePublicDirectory() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath();
    }

    public static String getDownloadCacheDirectory() {
        return Environment.getDownloadCacheDirectory().getPath();
    }

    public static String getDataDirectory() {
        return Environment.getDataDirectory().getPath();
    }

    public static String getRootDirectory() {
        return Environment.getRootDirectory().getPath();
    }

    @TargetApi(19)
    public static String getDirectory(Context context) {
        File[] paths = context.getExternalCacheDirs();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < paths.length; i++) {
            stringBuilder.append(paths[i].getPath());
        }
        return stringBuilder.toString();
    }

    public static String getImeiInfo(Context mContext) {
        TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Activity.TELEPHONY_SERVICE);
        String imei = tm.getDeviceId();
        if (!TextUtils.isEmpty(imei)) {
            return imei;
        }
        return "";
    }

    public static String getAbis() {
        String abis = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            abis = Arrays.toString(Build.SUPPORTED_ABIS);
        } else {
            abis = Build.CPU_ABI + Build.CPU_ABI2;
        }
        return abis;
    }
}
