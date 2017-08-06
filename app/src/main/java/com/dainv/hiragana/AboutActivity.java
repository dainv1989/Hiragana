package com.dainv.hiragana;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    private TextView tvVersion;
    private ImageView imgVote;
    private ImageView imgShare;
    private ImageView imgParrot;

    private String versionName = "1.0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        tvVersion = (TextView)findViewById(R.id.txtVersion);
        imgVote = (ImageView)findViewById(R.id.aboutRateApp);
        imgShare = (ImageView)findViewById(R.id.aboutShare);
        imgParrot = (ImageView)findViewById(R.id.imgParrot);

        final Context context = this;
        PackageManager packageManager = this.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(this.getPackageName(), 0);
            versionName = packageInfo.versionName;
            tvVersion.setText("version " + versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        imgVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String packageName = getApplicationContext().getPackageName();
                Uri uri = Uri.parse("market://details?id=" + packageName);
                Intent market = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    startActivity(market);
                }
                catch (ActivityNotFoundException e) {
                    uri = Uri.parse("https://play.google.com/store/apps/details?id=" + packageName);
                    market = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(market);
                    e.printStackTrace();
                }
            }
        });

        /* sharing app on social */
        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shareText = context.getResources().getString(R.string.share_content);
                String appUrl = " https://play.google.com/store/apps/details?id=" +
                        context.getPackageName();

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareText + appUrl);

                startActivity(Intent.createChooser(shareIntent, "Share via"));
            }
        });

        imgParrot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String packageName = "com.dainv.parrotjapanese";
                Uri uri = Uri.parse("market://details?id=" + packageName);
                Intent market = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    startActivity(market);
                }
                catch (ActivityNotFoundException e) {
                    uri = Uri.parse("https://play.google.com/store/apps/details?id=" + packageName);
                    market = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(market);
                    e.printStackTrace();
                }
            }
        });
    }
}
