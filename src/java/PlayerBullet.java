package com.Kamooone.kamoooneshot;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerBullet extends Bullet {
    //******************************************************************
    // マクロ定義
    //******************************************************************
    public static final int   BULLET_MAX               = 30;
    public static final float BulletFirePosCorrection  = 15.0f;
    private static final int  STRAIGHTBULLET           = 0;
    private static final int  HOMINGBULLET             = 1;
    private static final int  TRIPLEBULLET             = 2;

    //******************************************************************
    // 変数定義
    //******************************************************************
    // 弾三種類のクラスのインスタンスを生成
    private  List<UsuallyBullet> bulletObj = new ArrayList<>();

    private static List<Float>     setPosX = new ArrayList<>();
    private static List<Float>     setPosY = new ArrayList<>();
    private static List<ImageView> setImg  = new ArrayList<>();

    //========================================================================================
    // 概要   ： 弾の初期化処理(必要分のListを生成)
    // 仮引数 ： なし
    // 戻り値 ： なし
    //========================================================================================
    public void Init() {
        gameAcObj = GameActivity.GetThisObj();
        bulletSpeed = - Math.round(GameManager.GetScreenHeight() / 100f); // 画面の大きさにより調整

        //------------------------------------------------------------------------------------
        // 画像のID取得
        //------------------------------------------------------------------------------------
        id = new ArrayList<>(Arrays.asList(
                R.id.bullet1, R.id.bullet2, R.id.bullet3, R.id.bullet4, R.id.bullet5,
                R.id.bullet6, R.id.bullet7, R.id.bullet8, R.id.bullet9, R.id.bullet10,
                R.id.bullet11, R.id.bullet12, R.id.bullet13, R.id.bullet14, R.id.bullet15,
                R.id.bullet16, R.id.bullet17, R.id.bullet18, R.id.bullet19, R.id.bullet20,
                R.id.bullet21, R.id.bullet22, R.id.bullet23, R.id.bullet24, R.id.bullet25,
                R.id.bullet26, R.id.bullet27, R.id.bullet28, R.id.bullet29, R.id.bullet30));

        //------------------------------------------------------------------------------------
        // 三種類の弾のインスタンスを一つのオブジェクトにまとめる
        //------------------------------------------------------------------------------------
        bulletObj = new ArrayList<>(Arrays.asList(
                new UsuallyBullet(), new HomingBullet(), new TripleBullet()));

        //------------------------------------------------------------------------------------
        // 弾FIRE_MAX分生成
        //------------------------------------------------------------------------------------
        for (int i = 0; i < BULLET_MAX; i++) {
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
            workLength.add(0.0);
            bulletNow.add(false);
            bulletLife.add(FIRE_LIFETIME);
            homingStartTime.add(0);
        }

        //------------------------------------------------------------------------------------
        // 弾三種オブジェ初期化
        //------------------------------------------------------------------------------------
        for (int i = 0; i < 3; i++) {
            bulletObj.get(i).Init();
        }
    }

    //========================================================================================
    // 概要   ： プレイヤー弾発射処理  (要素番号を更新することで弾の種類を切り替え)
    // 仮引数 ： なし
    // 戻り値 ： なし
    //========================================================================================
    public void Move(){
        if(GameManager.GetResetFlg() || !Collision.GetPlayerLife()){ BulletReset(); }
        switch(GameManager.GetGameRoundState()) {
            case ROUND1:
                bulletObj.get(STRAIGHTBULLET).Move(img, posX, posY, directionX, directionY, bulletNow, bulletLife, homingStartTime, bulletSpeed);
                break;
            case ROUND2:
                bulletObj.get(HOMINGBULLET).Move(img, posX, posY, directionX, directionY, bulletNow, bulletLife, homingStartTime, bulletSpeed);
                break;
            case ROUND3:
                bulletObj.get(TRIPLEBULLET).Move(img, posX, posY, directionX, directionY, bulletNow, bulletLife, homingStartTime, bulletSpeed);
                break;
            case ROUND4:
                bulletObj.get(STRAIGHTBULLET).Move(img, posX, posY, directionX, directionY, bulletNow, bulletLife, homingStartTime, bulletSpeed);
                break;
            case FINALROUND:
                bulletObj.get(STRAIGHTBULLET).Move(img, posX, posY, directionX, directionY, bulletNow, bulletLife, homingStartTime, bulletSpeed);
        }
        setPosX = posX;
        setPosY = posY;
    }

    //========================================================================================
    // 概要   ： 自機の弾リセット処理
    // 仮引数 ： なし
    // 戻り値 ： なし
    //========================================================================================
    public void BulletReset(){
        bulletResetFlg = true;
        for (int i = 0; i < PlayerBullet.BULLET_MAX; i++) {
            img.get(i).setX(GameManager.INIT_POS);
            img.get(i).setY(GameManager.INIT_POS);
            posX.set(i, GameManager.INIT_POS);
            posY.set(i, GameManager.INIT_POS);
            bulletNow.set(i, false);
            bulletLife.set(i, FIRE_LIFETIME);
        }
    }

    //========================================================================================
    // 概要   ： プレイヤーの弾座標ゲッター
    // 仮引数 ： なし
    // 戻り値 ： プレイヤーの弾座標
    //========================================================================================
    public static List<ImageView> GetImageView() { return setImg; }
    public static List<Float>     GetPosX()      { return setPosX; }
    public static List<Float>     GetPosY()      { return setPosY; }
    public static float           GetPosX(int i) { return setPosX.get(i); }
    public static float           GetPosY(int i) { return setPosY.get(i); }
}