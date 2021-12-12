package com.Kamooone.kamoooneshot;

import android.widget.ImageView;

import java.util.List;

public class EnemySingleUnit extends EnemyZigzag{
    //******************************************************************
    // メンバ変数定義
    //******************************************************************
    private boolean enemySingleUnit = false;

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
        if(!enemySingleUnit) {
            float randomX = (float) (Math.random() * (GameManager.GetScreenWidth() / 1.5f) + 100.0f);
            posX.set(0, randomX);
            posY.set(0, -600.0f);
            img.get(0).setX(posX.get(0));
            img.get(0).setY(posY.get(0));
            enemySingleUnit = true;
        }

        //------------------------------------------------------------------------------------
        // 座標パラメータ更新
        //------------------------------------------------------------------------------------
        if(posY.get(0) < GameManager.GetScreenHeight() / 3) {
            posY.set(0, posY.get(0) + enemySpeed);
        }

        //------------------------------------------------------------------------------------
        // 座標更新反映
        //------------------------------------------------------------------------------------
        img.get(0).setX(posX.get(0));
        img.get(0).setY(posY.get(0));
        posX.set(0, img.get(0).getX());
        posY.set(0, img.get(0).getY());

        //------------------------------------------------------------------------------------
        // 画面外で座標を初期化(ランダム値)
        //------------------------------------------------------------------------------------
        if (!Collision.GetEnemyLife(0)) {
            float randomX = (float) (Math.random() * GameManager.GetScreenWidth() - Enemy.RANDOMPOS_MINX * 2) + Enemy.RANDOMPOS_MINX * 2;
            float randomY = (float) ((Math.random() * (Enemy.RANDOMPOS_MAXY - Enemy.RANDOMPOS_MINY)) + Enemy.RANDOMPOS_MINY);
            img.get(0).setX(randomX);
            img.get(0).setY(randomY);
            posX.set(0, img.get(0).getX());
            posY.set(0, img.get(0).getY());
            enemySingleUnit = false;
        }
    }
}
