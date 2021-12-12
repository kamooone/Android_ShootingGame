package com.Kamooone.kamoooneshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class GameTitle extends AppCompatActivity {

    private static MyOpenHelper helper;

    // バナー広告
    private AdView mAdView;


    @Override
    public void onBackPressed() {
        //戻るボタン無効化
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_title);

        // DB作成
        helper = new MyOpenHelper(getApplicationContext());

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

    public void startGame(View view) {
        startActivity(new Intent(getApplicationContext(), GameActivity.class));
    }

    public void RankingTransition(View view) {
        startActivity(new Intent(getApplicationContext(), RankingActivity.class));
    }

    public static MyOpenHelper GetMyOpenHelper(){
        return helper;
    }
}