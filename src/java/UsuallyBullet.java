package com.Kamooone.kamoooneshot;

import android.widget.ImageView;

import java.util.List;

public class UsuallyBullet{
    //******************************************************************
    // メンバ変数
    //******************************************************************
    protected int            bulletInterval  = 0;

    //******************************************************************
    // 初期化
    //******************************************************************
    public void Init(){ }

    //========================================================================================
    // 概要   ： 通常ショットの発射前処理から発射後の初期化処理まで全て
    // 仮引数 ： 弾のパラメータ全て
    // 戻り値 ： なし
    //========================================================================================
    public void Move(List<ImageView> img, List<Float> posX, List<Float> posY,
                     List<Double> directionX, List<Double> directionY, List<Boolean> bulletNow,
                     List<Integer> bulletLife, List<Integer> homingStartTime, int bulletSpeed){

        //------------------------------------------------------------------------------------
        // 発射前処理
        //------------------------------------------------------------------------------------
        for (int i = 0; i < PlayerBullet.BULLET_MAX; i++) {
            if (!bulletNow.get(i) && bulletInterval == 0) {
                bulletNow.set(i, true);
                ImageView getImg = Player.GetImageView();
                img.get(i).setX(Player.GetPosX() + getImg.getWidth() / 3 + PlayerBullet.BulletFirePosCorrection);
                img.get(i).setY(Player.GetPosY());
                posX.set(i, img.get(i).getX());
                posY.set(i, img.get(i).getY());
                bulletInterval = 7;
                break;
            }
        }

        //------------------------------------------------------------------------------------
        // 発射中弾処理
        //------------------------------------------------------------------------------------
        for (int i = 0; i < PlayerBullet.BULLET_MAX; i++) {
            if (bulletNow.get(i)) {
                bulletLife.set(i, bulletLife.get(i) -1);
                posY.set(i, posY.get(i) + bulletSpeed);
            }
        }

        //------------------------------------------------------------------------------------
        // 弾の座標更新
        //------------------------------------------------------------------------------------
        for (int i = 0; i < PlayerBullet.BULLET_MAX; i++) {
            img.get(i).setX(posX.get(i));
            img.get(i).setY(posY.get(i));
            posX.set(i, img.get(i).getX());
            posY.set(i, img.get(i).getY());
        }

        //------------------------------------------------------------------------------------
        // インタバルカウント
        //------------------------------------------------------------------------------------
        if (bulletInterval != 0) {
            bulletInterval--;
        }

        //------------------------------------------------------------------------------------
        // 時間または弾衝突で弾を初期化
        //------------------------------------------------------------------------------------
        for (int i = 0; i < PlayerBullet.BULLET_MAX; i++) {
            if (bulletLife.get(i) == 0) {
                img.get(i).setX(GameManager.INIT_POS);
                img.get(i).setY(GameManager.INIT_POS);
                posX.set(i, GameManager.INIT_POS);
                posY.set(i, GameManager.INIT_POS);
                bulletNow.set(i, false);
                bulletLife.set(i, Bullet.FIRE_LIFETIME);
            }
            if(!Collision.GetPlayerBulletLife(i)){
                img.get(i).setX(GameManager.INIT_POS);
                img.get(i).setY(GameManager.INIT_POS);
                posX.set(i, GameManager.INIT_POS);
                posY.set(i, GameManager.INIT_POS);
            }
        }
    }
}