package com.Kamooone.kamoooneshot;

import android.widget.ImageView;

import java.util.List;

public class EnemyStraight extends EnemyZigzag{

    //========================================================================================
    // 概要   ： エネミー直進移動初期化
    // 仮引数 ： なし
    // 戻り値 ： なし
    //========================================================================================
    public void Init(){ }

    //========================================================================================
    // 概要   ： エネミー移動処理(直進)
    // 仮引数 ： エネミーのパラメータすべて
    // 戻り値 ： なし
    //========================================================================================
    public void Move(List<ImageView> img, List<Float> posX, List<Float> posY,
                     List<Double> directionX, List<Double> directionY, List<Integer> enemyAround,
                     float enemySpeed){

        //------------------------------------------------------
        // エネミー総数分ループ
        //------------------------------------------------------
        for (int i = 0; i < Enemy.ENEMY_MAX; i++) {

            // 画面外時調整
            ImageView getImg = Enemy.GetImageView_Work();
            while (img.get(i).getX() < 0) {
                posX.set(i, posX.get(i) + enemySpeed);
                img.get(i).setX(posX.get(i));
            }
            while (img.get(i).getX() + getImg.getWidth() > GameManager.GetScreenWidth()) {
                posX.set(i, posX.get(i) - enemySpeed);
                img.get(i).setX(posX.get(i));
            }

            //------------------------------------------------------------------------------------
            // 座標パラメータ更新
            //------------------------------------------------------------------------------------
            posY.set(i, posY.get(i) + enemySpeed);

            //------------------------------------------------------------------------------------
            // 座標更新反映
            //------------------------------------------------------------------------------------
            img.get(i).setX(posX.get(i));
            img.get(i).setY(posY.get(i));
            posX.set(i, img.get(i).getX());
            posY.set(i, img.get(i).getY());

            //------------------------------------------------------------------------------------
            // 画面外で座標を初期化(ランダム値)
            //------------------------------------------------------------------------------------
            if ((img.get(i).getY() > GameManager.GetScreenHeight() -
                    GameManager.GetScreenHeight() / 6) || !Collision.GetEnemyLife(i)) {

                float randomX = (float) (Math.random() * GameManager.GetScreenWidth() -
                        Enemy.RANDOMPOS_MINX * 2) + Enemy.RANDOMPOS_MINX * 2;
                float randomY = (float) ((Math.random() * (Enemy.RANDOMPOS_MAXY -
                        Enemy.RANDOMPOS_MINY)) + Enemy.RANDOMPOS_MINY);
                img.get(i).setX(randomX);
                img.get(i).setY(randomY);
                posX.set(i, img.get(i).getX());
                posY.set(i, img.get(i).getY());
            }
        }
    }
}