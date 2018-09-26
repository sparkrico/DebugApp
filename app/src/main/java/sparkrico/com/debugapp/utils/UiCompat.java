package sparkrico.com.debugapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;

public class UiCompat {

    public static boolean needCompat() {
        if ("dipper".equals(Build.DEVICE) ||
                "ursa".equals(Build.DEVICE) ||
                "sakura".equals(Build.DEVICE)) {
            return true;
        } else if ("sirius".equals(Build.DEVICE)) {
            return true;
        }
        return false;
    }

    public static int getRealContentHeight(Activity context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Rect frame = new Rect();
        context.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int realContentHeight;
        if (needCompat()) {
            realContentHeight = getContentHeight(context, dm.heightPixels);
        } else {
            realContentHeight = dm.heightPixels - statusBarHeight;
        }
        return realContentHeight;
    }

    private static int getContentHeight(Context context, int heightPixels) {
        int notchHeight = getXiaoMiNotchHeight(context);
        if (notchHeight > 0) {
            return heightPixels + getStatusBarHeight(context) - notchHeight;
        } else {
            return heightPixels;
        }
    }

    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    public static int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    public static int getXiaoMiNotchHeight(Context context) {
        //https://dev.mi.com/console/doc/detail?pId=1293
        if ("dipper".equals(Build.DEVICE) ||
                "ursa".equals(Build.DEVICE) ||
                "sakura".equals(Build.DEVICE)) {
            return 89;
        } else if ("sirius".equals(Build.DEVICE)) {
            return 85;
        }
        return getXiaomiNotchHeightWithResources(context);
    }

    public static int getXiaomiNotchHeightWithResources(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("notch_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

}
