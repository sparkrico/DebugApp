package sparkrico.com.debugapp.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

import sparkrico.com.debugapp.MainActivity;
import sparkrico.com.debugapp.R;

public class ShortCutsUtils {
    public static void setupShortcuts(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1) {
            return;
        }
        ShortcutManager mShortcutManager = context.getApplicationContext().getSystemService(ShortcutManager.class);

        List<ShortcutInfo> infos = new ArrayList<>();

        Intent intent = new Intent(context, MainActivity.class);
        intent.setAction(Intent.ACTION_VIEW);

        ShortcutInfo info = new ShortcutInfo.Builder(context, "id")
                .setShortLabel("Short")
                .setLongLabel("Long")
                .setIcon(Icon.createWithResource(context, R.mipmap.ic_launcher))
                .setIntent(intent)
                .build();
        infos.add(info);

        mShortcutManager.setDynamicShortcuts(infos);
    }
}
