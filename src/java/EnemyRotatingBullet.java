package com.Kamooone.kamoooneshot;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class EnemyRotatingBullet extends EnemyUsuallyBullet{
    //******************************************************************
    // マクロ定義
    //******************************************************************

    //******************************************************************
    // メンバ変数定義
    //******************************************************************
    private            List<Float> angleX   = new ArrayList<>();
    private            List<Float> angleY   = new ArrayList<>();
    private int        rotatingBulletNo     = 0;
    private int        rotatingBulletNo1    = 0;
    private int        bulletRotatingSpeed  = 0;

    //******************************************************************
    // 初期化
    //******************************************************************
    public void Init(){
        // 画面の大きさにより調整
        bulletRotatingSpeed = Math.round(GameManager.GetScreenHeight() / 150f);

        // 渦巻弾のベクトル指定
        angleX.add(0.0f);angleX.add(-30.0f);angleX.add(-60.0f);angleX.add(-90.0f);
        angleX.add(-60.0f);angleX.add(-30.0f);angleX.add(0.0f);angleX.add(30.0f);
        angleX.add(60.0f);angleX.add(90.0f);angleX.add(60.0f);angleX.add(30.0f);

        angleY.add(-90.0f);angleY.add(-60.0f);angleY.add(-30.0f);angleY.add(0.0f);
        angleY.add(30.0f);angleY.add(60.0f);angleY.add(90.0f);angleY.add(60.0f);
        angleY.add(30.0f);angleY.add(0.0f);angleY.add(-30.0f);angleY.add(-60.0f);
    }

    //========================================================================================
    // 概要   ： ターゲットショットの発射前処理から発射後の初期化処理まで全て
    // 仮引数 ： 弾のパラメータ全て
    // 戻り値 ： なし
    //========================================================================================
    public void Move(List<ImageView> img, List<Float> posX, List<Float> posY,
                     List<Double> directionX, List<Double> directionY, List<Boolean> bulletNow,
                     List<Integer> bulletLife, int bulletSpeed) {

        //====================================================================================
        // エネミー弾の発射時の座標を指定
        //====================================================================================
        for (int i = 0; i < EnemyBullet.ENEMYBULLET_SUM ; i++) {

            //-------------------------------------------
            // 発射可能な弾を探す
            //-------------------------------------------
            if (!bulletNow.get(i) && bulletInterval == 0 && Enemy.GetPosY(0) >= 0) {
                bulletNow.set(i, true);
                bulletInterval = 3;

                //-------------------------------------------
                // 弾の発射時の座標を指定
                //-------------------------------------------
                ImageView getImg = Enemy.GetImageView_Work();
                posX.set(i, Enemy.GetPosX(0) + getImg.getWidth() / 3);
                posY.set(i, Enemy.GetPosY(0) + getImg.getHeight() / 2);
                img.get(i).setX(posX.get(i));
                img.get(i).setY(posY.get(i));

                //-------------------------------------------
                //ターゲット方向のベクトルを取得
                //-------------------------------------------
                float vecX = Processing.GetVector(Enemy.GetPosX(0) + angleX.get(rotatingBulletNo), Enemy.GetPosX(0));
                float vecY = Processing.GetVector(Enemy.GetPosY(0) + angleY.get(rotatingBulletNo), Enemy.GetPosY(0));

                //-------------------------------------------
                // 三平方の定理を使って長さを求める
                //-------------------------------------------
                double length = Processing.GetLength(vecX, vecY);

                //-------------------------------------------
                // 求めた長さで割り、正規化にする
                //-------------------------------------------
                directionX.set(i, vecX / length);
                directionY.set(i, vecY / length);
                rotatingBulletNo++;

                if (rotatingBulletNo == 12) {
                    rotatingBulletNo = 0;
                    break;
                }
            }
        }

        //====================================================================================
        // 発射中の弾の移動
        //====================================================================================
        for (int i = 0; i < Enemy.ENEMY_MAX * EnemyBullet.ENEMYBULLET_MAX; i++) {

            if (bulletNow.get(i)) {
                rotatingBulletNo1++;
                bulletLife.set(i, bulletLife.get(i) -1);
                switch (rotatingBulletNo1) {
                    case 1:

                    case 2:

                    case 3:

                    case 4:

                    case 5:

                    case 7:

                    case 8:

                    case 9:

                    case 10:

                    case 11:
                        posX.set(i, (float) (posX.get(i) + directionX.get(i) * bulletRotatingSpeed));
                        posY.set(i, (float) (posY.get(i) + directionY.get(i) * bulletRotatingSpeed));
                        img.get(i).setX(posX.get(i));
                        img.get(i).setY(posY.get(i));
                        break;

                    case 12:
                        posX.set(i, (float) (posX.get(i) + directionX.get(i) * bulletRotatingSpeed));
                        posY.set(i, (float) (posY.get(i) + directionY.get(i) * bulletRotatingSpeed));
                        img.get(i).setX(posX.get(i));
                        img.get(i).setY(posY.get(i));
                        rotatingBulletNo1 = 0;
                        break;
                }
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
            if (bulletLife.get(i) == 0) {
                img.get(i).setX(GameManager.INIT_POS);
                img.get(i).setY(GameManager.INIT_POS);
                posX.set(i, GameManager.INIT_POS);
                posY.set(i, GameManager.INIT_POS);
                bulletNow.set(i, false);
                bulletLife.set(i, Bullet.FIRE_LIFETIME);
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