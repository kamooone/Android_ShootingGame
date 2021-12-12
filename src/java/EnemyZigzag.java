package com.Kamooone.kamoooneshot;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class EnemyZigzag {
    //******************************************************************
    // マクロ定義
    //******************************************************************
    public static final float   ZIGAG_MoveX = 100.0f;
    public static final float   ZIGAG_MoveY = 10.0f;

    //******************************************************************
    // 変数定義
    //******************************************************************
    private List<Boolean>   nextEnemyMove    = new ArrayList<>();
    private int       nextEnemyMoveTime      = 0;
    private int       nextEnemyMoveNo        = 0;

    //========================================================================================
    // 概要   ： エネミージグザグ移動初期化
    // 仮引数 ： なし
    // 戻り値 ： なし
    //========================================================================================
    public void Init(){
        for(int i = 0; i < Enemy.ENEMY_MAX; i++) { nextEnemyMove.add(false); }
    }

    //========================================================================================
    // 概要   ： エネミー移動処理(ジグザグ)
    // 仮引数 ： エネミーのパラメータすべて
    // 戻り値 ： なし
    //========================================================================================
    public void Move(List<ImageView> img, List<Float> posX, List<Float> posY,
                     List<Double> directionX, List<Double> directionY, List<Integer> enemyAround,
                     float enemySpeed){

        //----------------------------------------------
        // フォーメション処理
        //----------------------------------------------
        if(nextEnemyMoveTime == 15){
            nextEnemyMove.set(nextEnemyMoveNo, true);
            nextEnemyMoveNo++;
            nextEnemyMoveTime = 0;
        }
        if(nextEnemyMoveNo >= Enemy.ENEMY_MAX){
            nextEnemyMoveNo = 0;
        }
        nextEnemyMoveTime++;

        //------------------------------------------------------------------------------------
        // エネミー総数分ループ
        //------------------------------------------------------------------------------------
        for (int i = 0; i < Enemy.ENEMY_MAX; i++) {
            posX.set(i, img.get(i).getX());
            posY.set(i, img.get(i).getY());

            if(nextEnemyMove.get(i)) {

                //------------------------------------------------------------------------------------
                // 反転処理
                //------------------------------------------------------------------------------------
                ImageView getImg = Enemy.GetImageView_Work();
                if (img.get(i).getX() < 0 ||
                        img.get(i).getX() + getImg.getWidth() > GameManager.GetScreenWidth()) {
                    enemyAround.set(i, enemyAround.get(i) * -1);
                }
                // 画面外時調整
                while (img.get(i).getX() < 0) {
                    posX.set(i, posX.get(i) + enemySpeed);
                    img.get(i).setX(posX.get(i));
                }
                while (img.get(i).getX() + getImg.getWidth() > GameManager.GetScreenWidth()) {
                    posX.set(i, posX.get(i) - enemySpeed);
                    img.get(i).setX(posX.get(i));
                }

                //------------------------------------------------------------------------------------
                // ターゲット方向のベクトルを求める(左斜め上)
                //------------------------------------------------------------------------------------
                float vecX = Processing.GetVector(img.get(i).getX() + ZIGAG_MoveX, img.get(i).getX());
                float vecY = Processing.GetVector(img.get(i).getY() + ZIGAG_MoveY, img.get(i).getY());

                //------------------------------------------------------------------------------------
                // 三平方の定理を使って長さを求める
                //------------------------------------------------------------------------------------
                double length = Processing.GetLength(vecX, vecY);

                //------------------------------------------------------------------------------------
                // ベクトルを求めた長さで割り、正規化にする
                //------------------------------------------------------------------------------------
                directionX.set(i, vecX / length);
                directionY.set(i, vecY / length);

                //------------------------------------------------------------------------------------
                // 座標パラメータ更新
                //------------------------------------------------------------------------------------
                posX.set(i, ((float) (posX.get(i) + directionX.get(i) * enemySpeed * enemyAround.get(i))));
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
                    float randomY = (float) ((Math.random() *
                            (Enemy.RANDOMPOS_MAXY - Enemy.RANDOMPOS_MINY)) + Enemy.RANDOMPOS_MINY);
                    img.get(i).setX(randomX);
                    img.get(i).setY(randomY);
                    posX.set(i, img.get(i).getX());
                    posY.set(i, img.get(i).getY());
                }
            }
        }
    }
}