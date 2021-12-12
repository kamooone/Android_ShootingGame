package com.Kamooone.kamoooneshot;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BossBullet extends Bullet {
    //******************************************************************
    // マクロ定義
    //******************************************************************
    public static final int     BULLET_MAX        = 120;
    public static final int     BOSSFIRE_LIFETIME = 120;

    //******************************************************************
    // 変数定義
    //******************************************************************
    // 弾三種類のクラスのインスタンスを生成
    private  List<BossTargetBullet> bulletObj = new ArrayList<>();
    private static List<Float>      setPosX   = new ArrayList<>();
    private static List<Float>      setPosY   = new ArrayList<>();
    private static List<ImageView>  setImg    = new ArrayList<>();
    private int     bulletFireStart           = 0;
    private boolean bulletFireFlg             = false;

    //========================================================================================
    // 概要   ： ボスの初期化処理(必要分のListを生成)
    // 仮引数 ： なし
    // 戻り値 ： なし
    //========================================================================================
    public void Init() {
        gameAcObj = GameActivity.GetThisObj();
        bulletSpeed = Math.round(GameManager.GetScreenHeight() / 150f); // 画面の大きさにより調整

        //------------------------------------------------------------------------------------
        // 三種類の弾のインスタンスを一つのオブジェクトにまとめる
        //------------------------------------------------------------------------------------
        bulletObj = new ArrayList<>(Arrays.asList(new BossTargetBullet()));

        //------------------------------------------------------------------------------------
        // 弾FIRE_MAX分生成
        //------------------------------------------------------------------------------------
        for (int i = 0; i < EnemyBullet.ENEMYBULLET_SUM; i++) {
            img.add(gameAcObj.findViewById(EnemyBullet.GetImageView_Array(i)));
            setImg.add(gameAcObj.findViewById(EnemyBullet.GetImageView_Array(i)));
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
            bulletLife.add(BOSSFIRE_LIFETIME);
            homingStartTime.add(0);
        }

        //------------------------------------------------------------------------------------
        // 弾三種オブジェ初期化
        //------------------------------------------------------------------------------------
        for (int i = 0; i < 1; i++) {
            bulletObj.get(i).Init();
        }
    }

    //========================================================================================
    // 概要   ： ボス弾発射処理  (要素番号を更新することで弾の種類を切り替え)
    // 仮引数 ： なし
    // 戻り値 ： なし
    //========================================================================================
    public void Move(){
        if(Boss.GetPosY() >= 0 && GameManager.GetGameRoundState() == GameManager.GameRoundState.FINALROUND) {
            bulletFireStart++;
            if(bulletFireStart >= 50){
                bulletFireFlg = true;
            }
        }

        if(bulletFireFlg) {
            bulletObj.get(0).Move(img, posX, posY, directionX, directionY, bulletNow, bulletLife,
                    homingStartTime, bulletSpeed);
        }

        setPosX = posX;
        setPosY = posY;
    }

    //========================================================================================
    // 概要   ： ボスの弾座標ゲッター
    // 仮引数 ： なし
    // 戻り値 ： ボスーの弾座標
    //========================================================================================
    public static List<ImageView> GetImageView(){ return setImg; }
    //public static int GetImageView_Array(int i){ return setId.get(i); }
    public static List<Float> GetPosX(){ return setPosX; }
    public static List<Float> GetPosY(){ return setPosY; }
    public static float GetPosX(int i){ return setPosX.get(i); }
    public static float GetPosY(int i){ return setPosY.get(i); }
}
