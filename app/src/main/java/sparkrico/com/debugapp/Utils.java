package sparkrico.com.debugapp;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.util.Arrays;

/**
 * Created by sparkrico on 2017/12/4.
 */

public class Utils {

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
