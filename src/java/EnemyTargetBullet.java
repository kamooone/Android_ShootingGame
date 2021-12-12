package com.Kamooone.kamoooneshot;

import android.widget.ImageView;

import java.util.List;

//========================================================================================
// 概要   ： エネミー弾処理(プレイヤーターゲット)
// 仮引数 ： エネミーの弾パラメータすべて
// 戻り値 ： なし
//========================================================================================
public class EnemyTargetBullet extends EnemyUsuallyBullet{
    //******************************************************************
    // 変数定義
    //******************************************************************

    //******************************************************************
    // 初期化
    //******************************************************************
    public void Init(){ }

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
        for (int i = 0; i < EnemyBullet.ENEMYBULLET_SUM; i++) {

            //-------------------------------------------
            // 発射可能な弾を探す
            //-------------------------------------------
            if (!bulletNow.get(i) && bulletInterval == 0 &&
                    Enemy.GetPosY(i / EnemyBullet.ENEMYBULLET_MAX) >= 0) {
                bulletNow.set(i, true);
                bulletInterval = 10;

                //-------------------------------------------
                // 弾の発射時の座標を指定
                //-------------------------------------------
                ImageView getImg = Enemy.GetImageView_Work();
                posX.set(i, Enemy.GetPosX(i / EnemyBullet.ENEMYBULLET_MAX) + getImg.getWidth() / 3);
                posY.set(i, Enemy.GetPosY(i / EnemyBullet.ENEMYBULLET_MAX) + getImg.getHeight() / 2);
                img.get(i).setX(posX.get(i));
                img.get(i).setY(posY.get(i));

                //-------------------------------------------
                //ターゲット方向のベクトルを取得
                //-------------------------------------------
                float vecX = Processing.GetVector(Player.GetPosX(), Enemy.GetPosX(i / EnemyBullet.ENEMYBULLET_MAX));
                float vecY = Processing.GetVector(Player.GetPosY(), Enemy.GetPosY(i / EnemyBullet.ENEMYBULLET_MAX));

                //-------------------------------------------
                // 三平方の定理を使って長さを求める
                //-------------------------------------------
                double length = Processing.GetLength(vecX, vecY);

                //-------------------------------------------
                // 求めた長さで割り、正規化にする
                //-------------------------------------------
                directionX.set(i, vecX / length);
                directionY.set(i, vecY / length);
                break;
            }
        }

        //====================================================================================
        // 発射中の弾の移動(ターゲットめがけて)
        //====================================================================================
        for (int i = 0; i < EnemyBullet.ENEMYBULLET_SUM; i++) {
            if (bulletNow.get(i)) {
                posX.set(i, ((float)(posX.get(i) + directionX.get(i) * bulletSpeed)));
                posY.set(i, ((float)(posY.get(i) + directionY.get(i) * bulletSpeed)));
                img.get(i).setX(posX.get(i));
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