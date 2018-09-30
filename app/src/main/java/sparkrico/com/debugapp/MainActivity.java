package sparkrico.com.debugapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import sparkrico.com.debugapp.utils.ShortCutsUtils;
import sparkrico.com.debugapp.utils.UiCompat;
import sparkrico.com.debugapp.utils.Utils;

public class MainActivity extends AppCompatActivity {

    private static final int READ_PHONE_STATE_PERMISSIONS_REQUEST_READ_CONTACTS = 1;

    TextView tv;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ShortCutsUtils.setupShortcuts(this);

        initViews();
        
        checkPermission();
    }

    private void initViews() {
        tv = findViewById(R.id.textView);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyText();
            }
        });
    }

    private void initData() {

        tv.append("imei: " + Utils.getImeiInfo(this));
        tv.append("\nDEVICE: " + Build.DEVICE);
        tv.append("\nSDK_INT: " + Build.VERSION.SDK_INT);
        tv.append("\nwidthPixels: " + getResources().getDisplayMetrics().widthPixels);
        tv.append("\nheightPixels: " + getResources().getDisplayMetrics().heightPixels);
        tv.append("\ndensity: " + getResources().getDisplayMetrics().density);
        tv.append("\nscaledDensity: " + getResources().getDisplayMetrics().scaledDensity);
        tv.append("\ndensityDpi :" + getResources().getDisplayMetrics().densityDpi);
        tv.append("\nstatusBarHeight :" + UiCompat.getStatusBarHeight(this));
        tv.append("\nnavigationBarHeight :" + UiCompat.getNavigationBarHeight(this));
        if (UiCompat.needCompat()) {
            tv.append("\nnotchHeight :" + UiCompat.getXiaoMiNotchHeight(this));
        }
        tv.append("\nnotch_height :" + UiCompat.getXiaomiNotchHeightWithResources(this));
        tv.append("\nAbis :" + Utils.getAbis());
    }

    private void copyText() {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.setPrimaryClip(ClipData.newPlainText(null, tv.getText()));
        Toast.makeText(getApplicationContext(), "已将链接复制到剪切板", Toast.LENGTH_SHORT).show();
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_PHONE_STATE)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("需要此权限");
                builder.setMessage("如果拒绝需要到应用信息页开启权限");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.READ_PHONE_STATE},
                                READ_PHONE_STATE_PERMISSIONS_REQUEST_READ_CONTACTS);
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.create().show();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_PHONE_STATE},
                        READ_PHONE_STATE_PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        } else {
            initData();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case READ_PHONE_STATE_PERMISSIONS_REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initData();
                } else {
                    finish();
                }

                break;
            }
        }
    }

    private void showAboutMeDialog() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("v0.1.0").append("\n");
        stringBuilder.append("sparkrico@gmail.com").append("\n");

        //
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.app_name);
        builder.setMessage(stringBuilder);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void toShare() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, tv.getText().toString());
        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent, "分享"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            showAboutMeDialog();
            return true;
        } else if (id == R.id.action_share) {
            toShare();
            return true;
        } else if (id == R.id.action_copy) {
            copyText();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
