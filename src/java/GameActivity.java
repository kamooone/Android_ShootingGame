package com.Kamooone.kamoooneshot;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {
    //******************************************************************
    // 変数定義
    //******************************************************************
    // Handler & Timer
    private Handler handler   = new Handler();
    private Timer   timer     = new Timer();

    // gameStatus
    private boolean startFlg  = false;

    // インタースティシャル広告
    private InterstitialAd mInterstitialAd;

    //******************************************************************
    // クラスオブジェクト生成
    //******************************************************************
    private Game        gameObj        = new Game();
    private GameManager gameManagerObj = new GameManager();
    private static GameActivity setThisObj;

    //******************************************************************
    // 画面生成時処理
    //******************************************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setThisObj = this;
        gameManagerObj.Init();
        gameObj.Init(this);

        // インタースティシャル広告
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this,"ca-app-pub-2678597473306441/1157784091", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        // Log.i(GameTitle, "onAdLoaded");
                    }
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        // Log.i(this, loadAdError.getMessage());
                        mInterstitialAd = null;
                    }
                });
    }

    // 戻るボタン無効
    @Override
    public void onBackPressed() { }

    //========================================================================================
    // 概要   ： ゲーム処理
    // 仮引数 ： 画面タッチの状態
    // 戻り値 ： なし
    //========================================================================================
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (!startFlg) {

            startFlg = true;
            gameManagerObj.Func();

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        //------------------------------------------
                        // ゲームループ
                        //------------------------------------------
                        public void run() {
                            gameManagerObj.SetTouchPoint(event);
                            gameObj.Update(timer);


                            // ゲームオーバー時にインタースティシャル広告表示
                            if(Explosion.GetResultFlg()) {

                                // FullScreenContentCallback を設定する
                                mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        // Called when fullscreen content is dismissed.
                                        Log.d("TAG", "The ad was dismissed.");
                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                        // Called when fullscreen content failed to show.
                                        Log.d("TAG", "The ad failed to show.");
                                    }

                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        // Called when fullscreen content is shown.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        mInterstitialAd = null;
                                        Log.d("TAG", "The ad was shown.");
                                    }
                                });

                                // インタースティシャル広告表示
                                if (mInterstitialAd != null) {
                                    mInterstitialAd.show(GameActivity.this);
                                } else {
                                    Log.d("TAG", "The interstitial ad wasn't ready yet.");
                                }
                            }

                        }
                    });
                }
            }, 0, 20);// 20ミリ秒ごとに実行
        } else {
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
            }
        }
        return true;
    }

    @Override
    // 画面削除時に呼ばれる
    protected void onDestroy() {
        super.onDestroy();

        // 解放処理
       SoundPlayer.Release();
        // クラスのインスタンスはガーベジコレクションによって自動解放
    }

    //========================================================================================
    // 概要   ： Thisオブジェクト取得
    // 仮引数 ： なし
    // 戻り値 ： Thisオブジェクト
    //========================================================================================
    public static GameActivity GetThisObj(){
        return setThisObj;
    }
}