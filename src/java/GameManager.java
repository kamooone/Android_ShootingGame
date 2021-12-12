package com.Kamooone.kamoooneshot;

import android.content.Intent;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.util.Timer;

public class GameManager {
    //******************************************************************
    // マクロ定義
    //******************************************************************
    public static final float INIT_POS = -5000;

    //******************************************************************
    // 変数定義
    //******************************************************************
    private static TextView startLabel;
    private static TextView roundLabel, roundLabel2, roundLabel3, roundLabel4, finalRoundLabel;
    private static TextView scoreLabel;

    // サイズ関連
    private static int frameHeight, screenWidth, screenHeight;

    // 画面タッチ座標
    private static float setPointX, setPointY;

    // gameScore
    private static int score = 0;

    private static int finalRoundFlgTime = 0;

    // ゲームflg
    private static boolean resetFlg      = false;
    private static boolean resetKey      = false;
    private static boolean bossBattleFlg = false;

    // ラウンド表示タイム
    private static int roundDrawTime = 0;

    public enum GameRoundState{
        ROUND1,
        ROUND2,
        ROUND3,
        ROUND4,
        FINALROUND
    };
    private static GameRoundState gameRoundState = GameRoundState.ROUND1;
    private static GameActivity   gameAcObj;


    //========================================================================================
    // 概要   ： ゲームマネージャ初期化初期化
    // 仮引数 ： なし
    // 戻り値 ： なし
    //========================================================================================
    public void Init(){
        gameAcObj = GameActivity.GetThisObj();
        startLabel = gameAcObj.findViewById(R.id.startLabel);
        roundLabel = gameAcObj.findViewById(R.id.roundLabel);
        roundLabel2 = gameAcObj.findViewById(R.id.roundLabel2);
        roundLabel3 = gameAcObj.findViewById(R.id.roundLabel3);
        roundLabel4 = gameAcObj.findViewById(R.id.roundLabel4);
        finalRoundLabel = gameAcObj.findViewById(R.id.finalRoundLabel);
        scoreLabel = gameAcObj.findViewById(R.id.scoreLabel);
        score = 0;
        scoreLabel.setText("Score : 0");

        roundLabel.setVisibility(View.INVISIBLE);
        roundLabel2.setVisibility(View.INVISIBLE);
        roundLabel3.setVisibility(View.INVISIBLE);
        roundLabel4.setVisibility(View.INVISIBLE);
        finalRoundLabel.setVisibility(View.INVISIBLE);

        roundDrawTime = 0;

        // スクリーンサイズ取得
        WindowManager wm = gameAcObj.getWindowManager();
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
        FrameLayout frame = gameAcObj.findViewById(R.id.frame);
        frameHeight = frame.getHeight();

        resetFlg = false;
        resetKey = false;
        bossBattleFlg = false;
        finalRoundFlgTime = 0;
        gameRoundState = GameRoundState.ROUND1;
    }

    //========================================================================================
    // 概要   ： フレームレイアウト取得
    // 仮引数 ： なし
    // 戻り値 ： なし
    //========================================================================================
    public void Func(){
        // frameレイアウトの高さを取得
        FrameLayout frame = gameAcObj.findViewById(R.id.frame);
        frameHeight = frame.getHeight();

        // スタートラベル削除
        startLabel.setVisibility(View.GONE);
    }

    //========================================================================================
    // 概要   ： 画面タッチ座標取得
    // 仮引数 ： なし
    // 戻り値 ： なし
    //========================================================================================
    public void SetTouchPoint(MotionEvent event){
        // タッチした座標の取得
        setPointX = event.getX();
        setPointY = event.getY();
    }

    //========================================================================================
    // 概要   ： スコアアップデート
    // 仮引数 ： なし
    // 戻り値 ： なし
    //========================================================================================
    public static void ScoreUpdate(){
        // スコア更新
        score += 10;
        scoreLabel.setText("Score : " + score);
    }

    //========================================================================================
    // 概要   ： ラウンド更新処理
    // 仮引数 ： なし
    // 戻り値 ： なし
    //========================================================================================
    public static void RoundUpdate(){
        //------------------------------------------------------------------
        // ラウンド表示
        //------------------------------------------------------------------
        // ラウンド1
        if(gameRoundState == GameRoundState.ROUND1 && roundDrawTime <= 150){
            roundLabel.setVisibility(View.VISIBLE);
            roundDrawTime++;
        }
        if(gameRoundState == GameRoundState.ROUND1 && roundDrawTime >= 150){
            roundLabel.setVisibility(View.INVISIBLE);
        }

        // ラウンド2
        if(gameRoundState == GameRoundState.ROUND2 && roundDrawTime <= 300){
            roundLabel2.setVisibility(View.VISIBLE);
            roundDrawTime++;
        }
        if(gameRoundState == GameRoundState.ROUND2 && roundDrawTime >= 300){
            roundLabel2.setVisibility(View.INVISIBLE);
        }

        // ラウンド3
        if(gameRoundState == GameRoundState.ROUND3 && roundDrawTime <= 450){
            roundLabel3.setVisibility(View.VISIBLE);
            roundDrawTime++;
        }
        if(gameRoundState == GameRoundState.ROUND3 && roundDrawTime >= 450){
            roundLabel3.setVisibility(View.INVISIBLE);
        }

        // ラウンド4
        if(gameRoundState == GameRoundState.ROUND4 && roundDrawTime <= 600){
            roundLabel4.setVisibility(View.VISIBLE);
            roundDrawTime++;
        }
        if(gameRoundState == GameRoundState.ROUND4 && roundDrawTime >= 600){
            roundLabel4.setVisibility(View.INVISIBLE);
        }

        // ファイナルラウンド
        if(gameRoundState == GameRoundState.FINALROUND && roundDrawTime <= 750){
            finalRoundLabel.setVisibility(View.VISIBLE);
            roundDrawTime++;
        }
        if(gameRoundState == GameRoundState.FINALROUND && roundDrawTime >= 750){
            finalRoundLabel.setVisibility(View.INVISIBLE);
        }


        //------------------------------------------------------------------
        // ラウンド更新
        //------------------------------------------------------------------
        resetFlg = false;
        if(score < 200){
            gameRoundState = GameRoundState.ROUND1;
        }
        if(score >= 200 && score < 400){
            gameRoundState = GameRoundState.ROUND2;
            if(!resetKey){
                resetFlg = true;
                resetKey = true;
            }
        }
        if(score >= 400 && score < 600){
            gameRoundState = GameRoundState.ROUND3;
            if(resetKey){
                resetFlg = true;
                resetKey = false;
            }
        }
        if(score >= 600 && score < 710){
            gameRoundState = GameRoundState.ROUND4;
            if(!resetKey){
                score = 600;
                scoreLabel.setText("Score : " + score);
                resetFlg = true;
                resetKey = true;
            }

        }
        if(score >= 710) {
            if(finalRoundFlgTime < 50){
                finalRoundFlgTime++;
            }
            if(finalRoundFlgTime == 50) {
                gameRoundState = GameRoundState.FINALROUND;
            }
            if(resetKey){
                resetFlg = true;
                resetKey = false;
                bossBattleFlg = true;
            }
        }
    }

    //========================================================================================
    // 概要   ： ゲームオーバー処理
    // 仮引数 ： ゲームループの処理のtimer
    // 戻り値 ： なし
    //========================================================================================
    public static void GameOver(Timer timer){

        if(Explosion.GetResultFlg()) {
            if (timer != null) {
                timer.cancel();
                timer = null;
            }

            // BGMストップ
            SoundPlayer.BGM1Stop();
            SoundPlayer.BGM2Stop();
            Intent intent = new Intent(gameAcObj.getApplicationContext(), GameResult.class);
            intent.putExtra("SCORE", score);
            gameAcObj.startActivity(intent);
            gameAcObj.finish();
        }
    }

    //========================================================================================
    // 概要   ： ゲッターオブジェクト
    // 仮引数 ： なし
    // 戻り値 ： オブジェクト
    //========================================================================================
    public static int GetScreenWidth()               { return screenWidth; }
    public static int GetScreenHeight()              { return screenHeight; }
    public static int GetFrameHeight()               { return frameHeight; }
    public static float GetPointX()                  { return setPointX; }
    public static float GetPointY()                  { return setPointY; }
    public static int GetScore()                     { return score; }
    public static boolean GetResetFlg()              { return resetFlg; }
    public static boolean GetBossBattleFlg()         { return bossBattleFlg; }
    public static GameRoundState GetGameRoundState() { return gameRoundState; }
}
