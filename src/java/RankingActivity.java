package com.Kamooone.kamoooneshot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class RankingActivity extends AppCompatActivity {

    private TextView textView;
    private AdView mAdView;

    @Override
    public void onBackPressed() {
        //戻るボタン無効化
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        textView = findViewById(R.id.user1);

        // データベース取得
        MyOpenHelper helper = GameTitle.GetMyOpenHelper();

        // DBからデータを取得し表示
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(
                "testdb",
                new String[] { "userName", "score" },
                null,
                null,
                null,
                null,
                "score DESC"
        );

        cursor.moveToFirst();

        StringBuilder sbuilder = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            sbuilder.append(i+1+"     ");
            sbuilder.append(cursor.getString(0));
            sbuilder.append(" :    ");
            sbuilder.append(cursor.getInt(1));
            sbuilder.append("点\n\n");
            cursor.moveToNext();
        }
        cursor.close();
        textView.setText(sbuilder.toString());

        // 広告
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    public void TitleTransition(View view) {
        startActivity(new Intent(getApplicationContext(), GameTitle.class));
    }
}