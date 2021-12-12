package com.Kamooone.kamoooneshot;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EnemyBullet extends Bullet {
    //******************************************************************
    // マクロ定義
    //******************************************************************
    public static final int   ENEMYBULLET_MAX = 5;
    public static final int   ENEMYBULLET_SUM = Enemy.ENEMY_MAX * ENEMYBULLET_MAX;
    private static final int  STRAIGHT        = 0;
    private static final int  DIRECT          = 1;
    private static final int  SPIRAL          = 2;

    //******************************************************************
    // 変数定義
    //******************************************************************
    // 弾二種類のクラスのインスタンスを生成
    private  List<EnemyUsuallyBullet> bulletObj = new ArrayList<>();

    // セッター用バッファ
    private static List<Float> setPosX    = new ArrayList<>();
    private static List<Float> setPosY    = new ArrayList<>();
    private static List<ImageView> setImg = new ArrayList<>();
    private static List<Integer>   setId  = new ArrayList<>();

    //========================================================================================
    // 概要   ： 弾の初期化処理(必要分のListを生成)
    // 仮引数 ： なし
    // 戻り値 ： なし
    //========================================================================================
    public void Init(){
        gameAcObj = GameActivity.GetThisObj();
        bulletSpeed = Math.round(GameManager.GetScreenHeight() / 100f); // 画面の大きさにより調整

        //------------------------------------------------------------------------------------
        // 二種類の弾のインスタンスを一つのオブジェクトにまとめる
        //------------------------------------------------------------------------------------
        bulletObj = new ArrayList<>(Arrays.asList(
                new EnemyUsuallyBullet(), new EnemyTargetBullet(), new EnemyRotatingBullet()));

        //------------------------------------------------------------------------------------
        // 画像のID取得
        //------------------------------------------------------------------------------------
        id = new ArrayList<>(Arrays.asList(
                R.id.enemyBullet1_1, R.id.enemyBullet1_2, R.id.enemyBullet1_3, R.id.enemyBullet1_4,
                R.id.enemyBullet1_5,

                R.id.enemyBullet2_1, R.id.enemyBullet2_2, R.id.enemyBullet2_3, R.id.enemyBullet2_4,
                R.id.enemyBullet2_5,

                R.id.enemyBullet3_1, R.id.enemyBullet3_2, R.id.enemyBullet3_3, R.id.enemyBullet3_4,
                R.id.enemyBullet3_5,

                R.id.enemyBullet4_1, R.id.enemyBullet4_2, R.id.enemyBullet4_3, R.id.enemyBullet4_4,
                R.id.enemyBullet4_5,

                R.id.enemyBullet5_1, R.id.enemyBullet5_2, R.id.enemyBullet5_3, R.id.enemyBullet5_4,
                R.id.enemyBullet5_5,

                R.id.enemyBullet6_1, R.id.enemyBullet6_2, R.id.enemyBullet6_3, R.id.enemyBullet6_4,
                R.id.enemyBullet6_5,

                R.id.enemyBullet7_1, R.id.enemyBullet7_2, R.id.enemyBullet7_3, R.id.enemyBullet7_4,
                R.id.enemyBullet7_5,

                R.id.enemyBullet8_1, R.id.enemyBullet8_2, R.id.enemyBullet8_3, R.id.enemyBullet8_4,
                R.id.enemyBullet8_5,

                R.id.enemyBullet9_1, R.id.enemyBullet9_2, R.id.enemyBullet9_3, R.id.enemyBullet9_4,
                R.id.enemyBullet9_5,

                R.id.enemyBullet10_1, R.id.enemyBullet10_2, R.id.enemyBullet10_3, R.id.enemyBullet10_4,
                R.id.enemyBullet10_5,

                R.id.enemyBullet11_1, R.id.enemyBullet11_2, R.id.enemyBullet11_3, R.id.enemyBullet11_4,
                R.id.enemyBullet11_5,

                R.id.enemyBullet12_1, R.id.enemyBullet12_2, R.id.enemyBullet12_3, R.id.enemyBullet12_4,
                R.id.enemyBullet12_5,

                R.id.enemyBullet13_1, R.id.enemyBullet13_2, R.id.enemyBullet13_3, R.id.enemyBullet13_4,
                R.id.enemyBullet13_5,

                R.id.enemyBullet14_1, R.id.enemyBullet14_2, R.id.enemyBullet14_3, R.id.enemyBullet14_4,
                R.id.enemyBullet14_5,

                R.id.enemyBullet15_1, R.id.enemyBullet15_2, R.id.enemyBullet15_3, R.id.enemyBullet15_4,
                R.id.enemyBullet15_5,

                R.id.enemyBullet16_1, R.id.enemyBullet16_2, R.id.enemyBullet16_3, R.id.enemyBullet16_4,
                R.id.enemyBullet16_5,

                R.id.enemyBullet17_1, R.id.enemyBullet17_2, R.id.enemyBullet17_3, R.id.enemyBullet17_4,
                R.id.enemyBullet17_5,

                R.id.enemyBullet18_1, R.id.enemyBullet18_2, R.id.enemyBullet18_3, R.id.enemyBullet18_4,
                R.id.enemyBullet18_5,

                R.id.enemyBullet19_1, R.id.enemyBullet19_2, R.id.enemyBullet19_3, R.id.enemyBullet19_4,
                R.id.enemyBullet19_5,

                R.id.enemyBullet20_1, R.id.enemyBullet20_2, R.id.enemyBullet20_3, R.id.enemyBullet20_4,
                R.id.enemyBullet20_5,

                R.id.enemyBullet21_1, R.id.enemyBullet21_2, R.id.enemyBullet21_3, R.id.enemyBullet21_4,
                R.id.enemyBullet21_5,

                R.id.enemyBullet22_1, R.id.enemyBullet22_2, R.id.enemyBullet22_3, R.id.enemyBullet22_4,
                R.id.enemyBullet22_5,

                R.id.enemyBullet23_1, R.id.enemyBullet23_2, R.id.enemyBullet23_3, R.id.enemyBullet23_4,
                R.id.enemyBullet23_5,

                R.id.enemyBullet24_1, R.id.enemyBullet24_2, R.id.enemyBullet24_3, R.id.enemyBullet24_4,
                R.id.enemyBullet24_5)
        );
        // idセッター
        setId = id;

        //------------------------------------------------------------------------------------
        // 弾FIRE_MAX分生成
        //------------------------------------------------------------------------------------
        for (int i = 0; i < ENEMYBULLET_SUM; i++) {
            img.add(gameAcObj.findViewById(id.get(i)));
            setImg.add(gameAcObj.findViewById(id.get(i)));
            posX.add(GameManager.INIT_POS);
            posY.add(GameManager.INIT_POS);
            setPosX.add(GameManager.INIT_POS);
            setPosY.add(GameManager.INIT_POS);
            img.get(i).setX(posX.get(i));
            img.get(i).setY(posY.get(i));
            directionX.add(0.0);
            directionY.add(0.0);
            bulletNow.add(false);
            bulletLife.add(FIRE_LIFETIME);
        }

        //------------------------------------------------------------------------------------
        // 弾三種オブジェ初期化
        //------------------------------------------------------------------------------------
        for (int i = 0; i < 3; i++) {
            bulletObj.get(i).Init();
        }
    }

    //========================================================================================
    // 概要   ： エネミー弾発射処理  (要素番号を更新することで弾の種類を切り替え)
    // 仮引数 ： なし
    // 戻り値 ： なし
    //========================================================================================
    public void Move(){
        if(GameManager.GetResetFlg()){ BulletReset(); }
        switch (GameManager.GetGameRoundState()) {
            case ROUND1:
                bulletObj.get(STRAIGHT).Move(img, posX, posY, directionX, directionY, bulletNow, bulletLife, bulletSpeed);
                break;
            case ROUND2:
                bulletObj.get(DIRECT).Move(img, posX, posY, directionX, directionY, bulletNow, bulletLife, bulletSpeed);
                break;
            case ROUND3:
                bulletObj.get(DIRECT).Move(img, posX, posY, directionX, directionY, bulletNow, bulletLife, bulletSpeed);
                break;
            case ROUND4:
                bulletObj.get(SPIRAL).Move(img, posX, posY, directionX, directionY, bulletNow, bulletLife, bulletSpeed);
                break;
        }
        setPosX = posX;
        setPosY = posY;
    }

    //========================================================================================
    // 概要   ： 自機の弾リセット処理
    // 仮引数 ： なし
    // 戻り値 ： なし
    //========================================================================================
    public void BulletReset() {
        bulletResetFlg = true;
        for (int i = 0; i < EnemyBullet.ENEMYBULLET_SUM; i++) {
            img.get(i).setX(GameManager.INIT_POS);
            img.get(i).setY(GameManager.INIT_POS);
            posX.set(i, GameManager.INIT_POS);
            posY.set(i, GameManager.INIT_POS);
            bulletNow.set(i, false);
        }
    }

    //========================================================================================
    // 概要   ： エネミーの弾座標ゲッター
    // 仮引数 ： なし
    // 戻り値 ： エネミーの弾座標
    //========================================================================================
    public static List<ImageView> GetImageView(){ return setImg; }
    public static int GetImageView_Array(int i){ return setId.get(i); }
    public static List<Float> GetPosX(){ return setPosX; }
    public static List<Float> GetPosY(){ return setPosY; }
    public static float GetPosX(int i){ return setPosX.get(i); }
    public static float GetPosY(int i){ return setPosY.get(i); }
}