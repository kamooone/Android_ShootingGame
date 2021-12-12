package com.Kamooone.kamoooneshot;

import android.widget.ImageView;

import java.util.List;

public class EnemyUsuallyBullet{
    //******************************************************************
    // 変数定義
    //******************************************************************
    private int       enemyNo         = 0;
    protected int     bulletInterval  = 0;

    //******************************************************************
    // 初期化
    //******************************************************************
    public void Init(){ }

    //******************************************************************
    // 使わない
    //******************************************************************
    public void Move(){ }

    //========================================================================================
    // 概要   ： 通常ショットの発射前処理から発射後の初期化処理まで全て
    // 仮引数 ： 弾のパラメータ全て
    // 戻り値 ： なし
    //========================================================================================
    public void Move(List<ImageView> img, List<Float> posX, List<Float> posY,
                     List<Double> directionX, List<Double> directionY, List<Boolean> bulletNow,
                     List<Integer> bulletLife, int bulletSpeed) {

        //====================================================================================
        // エネミー弾の発射時の座標を指定
        //====================================================================================
        for (int i = 0; i < EnemyBullet.ENEMYBULLET_SUM; i++) {

            // 発射可能な弾を探す
            if (!bulletNow.get(i) && bulletInterval == 0 && Enemy.GetPosY(enemyNo) >= 0) {

                // 弾の発射時の座標を指定
                ImageView getImg = Enemy.GetImageView_Work();
                posX.set(i, Enemy.GetPosX(enemyNo) + getImg.getWidth() / 3);
                posY.set(i, Enemy.GetPosY(enemyNo) + getImg.getHeight() / 2);
                img.get(i).setX(posX.get(i));
                img.get(i).setY(posY.get(i));
                bulletNow.set(i, true);
                bulletInterval = 10;
            }

            enemyNo++;
            if(enemyNo == Enemy.ENEMY_MAX - 1){
                enemyNo = 0;
            }
        }

        //====================================================================================
        // 発射中の弾の移動(直進)
        //====================================================================================
        for (int i = 0; i < EnemyBullet.ENEMYBULLET_SUM; i++) {
            if (bulletNow.get(i)) {
                posY.set(i, posY.get(i) + bulletSpeed);
                img.get(i).setY(posY.get(i));
            }
        }

        //====================================================================================
        // インタバルカウント
        //====================================================================================
        if (bulletInterval != 0) {
            bulletInterval--;
        }

        //====================================================================================
        // エネミーの弾リセット
        //====================================================================================
        for (int i = 0; i < EnemyBullet.ENEMYBULLET_SUM; i++) {
            if (img.get(i).getY() > GameManager.GetScreenHeight()) {
                img.get(i).setX(GameManager.INIT_POS);
                img.get(i).setY(GameManager.INIT_POS);
                posX.set(i, GameManager.INIT_POS);
                posY.set(i, GameManager.INIT_POS);
                bulletNow.set(i, false);
            }
            if(!Collision.GetEnemyBulletLife(i)){
                img.get(i).setX(GameManager.INIT_POS);
                img.get(i).setY(GameManager.INIT_POS);
                posX.set(i, GameManager.INIT_POS);
                posY.set(i, GameManager.INIT_POS);
            }
        }
    }
}