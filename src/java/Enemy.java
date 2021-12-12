package com.Kamooone.kamoooneshot;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Enemy extends Char {
    //******************************************************************
    // マクロ定義
    //******************************************************************
    public static final int   ENEMY_MAX       = 24;
    public static final float RANDOMPOS_MINX  = 100.0f;
    public static final float RANDOMPOS_MINY  = -2000.0f;
    public static final float RANDOMPOS_MAXY  = -10000.0f;
    private static final int  STRAIGHTMOVE    = 0;
    private static final int  ZIGZAGMOVE      = 1;
    private static final int  SPIRAL          = 2;

    //******************************************************************
    // 変数定義
    //******************************************************************
    private static List<ImageView> setImg = new ArrayList<>();
    private static List<Float>     setPosX              = new ArrayList<>();
    private static List<Float>     setPosY              = new ArrayList<>();
    private List<Integer>          enemyAround          = new ArrayList<>();
    private List<Boolean>          nextEnemyMove        = new ArrayList<>();
    private int                    nextEnemyMoveTime    = 0;
    private int                    nextEnemyMoveNo      = 0;
    private int                    nemyFormationNo      = 0;
    private float                  enemySpeed           = 0.0f;
    private float                  randomX = 0, randomY = 0;

    // エネミー三種類のMoveクラスのインスタンスを生成
    private  List<EnemyZigzag> enemyMoveObj = new ArrayList<>();

    //========================================================================================
    // 概要   ： エネミー初期化
    // 仮引数 ： なし
    // 戻り値 ： なし
    //========================================================================================
    public void Init(){
        gameAcObj  = GameActivity.GetThisObj();
        enemySpeed = Math.round(GameManager.GetScreenHeight() / 250f); // 画面の大きさにより調整

        //------------------------------------------------------------------------------------
        // 画像のID取得
        //------------------------------------------------------------------------------------
        id = new ArrayList<>(Arrays.asList(
                R.id.enemy1, R.id.enemy2, R.id.enemy3, R.id.enemy4, R.id.enemy5,
                R.id.enemy6, R.id.enemy7, R.id.enemy8, R.id.enemy9, R.id.enemy10,
                R.id.enemy11, R.id.enemy12, R.id.enemy13, R.id.enemy14, R.id.enemy15,
                R.id.enemy16, R.id.enemy17, R.id.enemy18, R.id.enemy19, R.id.enemy20,
                R.id.enemy21, R.id.enemy22, R.id.enemy23, R.id.enemy24));

        //------------------------------------------------------------------------------------
        // エネミー初期設定
        //------------------------------------------------------------------------------------
        for(int i = 0; i < ENEMY_MAX; i++) {
            img.add(gameAcObj.findViewById(id.get(i)));
            setImg.add(gameAcObj.findViewById(id.get(i)));
            enemyAround.add(1);
            nextEnemyMove.add(false);
            directionX.add(0.0);
            directionY.add(0.0);

            randomX = (float) (Math.random() * GameManager.GetScreenWidth() - Enemy.RANDOMPOS_MINX * 2) + RANDOMPOS_MINX;
            randomY = (float) ((Math.random() * (RANDOMPOS_MAXY - RANDOMPOS_MINY)) + RANDOMPOS_MINY);

            img.get(i).setX(randomX);
            img.get(i).setY(randomY);
            img.get(i).setRotation(180.0f);
            posX.add(img.get(i).getX());
            posY.add(img.get(i).getY());
            setPosX.add(img.get(i).getX());
            setPosY.add(img.get(i).getX());
        }

        //------------------------------------------------------------------------------------
        // 三種類の弾のインスタンスを一つのオブジェクトにまとめる
        //------------------------------------------------------------------------------------
        enemyMoveObj = new ArrayList<>(Arrays.asList(new EnemyStraight(),new EnemyZigzag(),  new EnemySingleUnit()));
        for(int i = 0; i < 3; i++){ enemyMoveObj.get(i).Init(); }
    }

    //========================================================================================
    // 概要   ： エネミー移動処理
    // 仮引数 ： なし
    // 戻り値 ： なし
    //========================================================================================
    public void Move() {
        if(GameManager.GetResetFlg()){ EnemyReset(); }
        switch(GameManager.GetGameRoundState()) {
            case ROUND1:
                enemyMoveObj.get(STRAIGHTMOVE).Move(img, posX, posY, directionX, directionY, enemyAround, enemySpeed);
                break;
            case ROUND2:
                enemyMoveObj.get(ZIGZAGMOVE).Move(img, posX, posY, directionX, directionY, enemyAround, enemySpeed);
                break;
            case ROUND3:
                enemyMoveObj.get(ZIGZAGMOVE).Move(img, posX, posY, directionX, directionY, enemyAround, enemySpeed);
                break;
            case ROUND4:
                enemyMoveObj.get(SPIRAL).Move(img, posX, posY, directionX, directionY, enemyAround, enemySpeed);
                break;
        }
        setPosX = posX;
        setPosY = posY;
    }

    //========================================================================================
    // 概要   ： エネミーリセット処理
    // 仮引数 ： なし
    // 戻り値 ： なし
    //========================================================================================
    public void EnemyReset(){
        for(int i = 0; i < ENEMY_MAX; i++) {
            // 四機ごとにランダム値を設定
            if(i == 0 || i == 4 || i == 8 || i == 12 || i == 16 || i == 20) {
                randomX = (float) (Math.random() * GameManager.GetScreenWidth() - Enemy.RANDOMPOS_MINX * 2) + RANDOMPOS_MINX * 2;
                randomY = (float) ((Math.random() * (RANDOMPOS_MAXY - RANDOMPOS_MINY)) + RANDOMPOS_MINY);
            }

            img.get(i).setX(randomX);
            img.get(i).setY(randomY);
            img.get(i).setRotation(180.0f);
            posX.add(img.get(i).getX());
            posY.add(img.get(i).getY());
            setPosX.add(img.get(i).getX());
            setPosY.add(img.get(i).getX());
        }
    }

    //========================================================================================
    // 概要   ： エネミーの座標ゲッター
    // 仮引数 ： 要素番号
    // 戻り値 ： エネミーの座標
    //========================================================================================
    public static List<ImageView> GetImageView()  { return setImg; }
    public static ImageView GetImageView_Work()   { return setImg.get(0); }
    public static List<Float> GetPosX()           { return setPosX; }
    public static List<Float> GetPosY()           { return setPosY; }
    public static float GetPosX(int i)            { return setPosX.get(i); }
    public static float GetPosY(int i)            { return setPosY.get(i); }
}