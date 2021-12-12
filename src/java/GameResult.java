package com.Kamooone.kamoooneshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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

public class GameResult extends AppCompatActivity {

    String toastMessage1 = "ユーザー名が入力されていません。";
    String toastMessage2 = "登録が完了しました。";
    String toastMessage3 = "既に登録されています。";

    private int score;
    private boolean rankingInput_flg;

    // バナー広告
    private AdView mAdView;

    @Override
    public void onBackPressed() {
        //戻るボタン無効化
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_result);

        TextView scoreLabel = findViewById(R.id.scoreLabel);
        TextView highScoreLabel = findViewById(R.id.highScoreLabel);

        score = getIntent().getIntExtra("SCORE", 0);
        scoreLabel.setText(score + "");

        SharedPreferences sharedPreferences = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        int highScore = sharedPreferences.getInt("HIGH_SCORE", 0);

        if (score > highScore) {
            highScoreLabel.setText("High Score : " + score);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("HIGH_SCORE", score);
            editor.apply();

        } else {
            highScoreLabel.setText("High Score : " + highScore);
        }

        rankingInput_flg = false;

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


    // データベース登録ボタン
    public void rankingAdd(View view) {
        MyOpenHelper helper = GameTitle.GetMyOpenHelper();
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        EditText editTextTitle = findViewById(R.id.userName);
        String userName = editTextTitle.getText().toString();
        // 未入力message
        if("".equals(userName)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(toastMessage1)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {// ボタンをクリックしたときの動作
                        }
                    });
            builder.show();
        }else if(!rankingInput_flg){
            // 入力されていたら登録
            rankingInput_flg = true;
            values.put("userName", userName);
            values.put("score", score);
            db.insert("testdb", null, values);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(toastMessage2)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {// ボタンをクリックしたときの動作
                        }
                    });
            builder.show();
        }else if(rankingInput_flg){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(toastMessage3)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {// ボタンをクリックしたときの動作
                        }
                    });
            builder.show();
        }
    }

    public void tryAgain(View view) {
        startActivity(new Intent(getApplicationContext(), GameActivity.class));
    }

    public void TitleTransition(View view) {
        startActivity(new Intent(getApplicationContext(), GameTitle.class));
    }

}