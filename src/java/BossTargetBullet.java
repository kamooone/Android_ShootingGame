package com.Kamooone.kamoooneshot;

import android.widget.ImageView;

import java.util.List;

public class BossTargetBullet {
    //******************************************************************
    // マクロ定義
    //******************************************************************
    public static final float     VECTOR_Y      = 50.0f;
    public static final float     RIGHTVECTOR_X = 20.0f;
    public static final float     LEFTVECTOR_X  = -20.0f;

    //******************************************************************
    // 変数定義
    //******************************************************************
    private   int     bulletInterval  = 0;
    private   int     tripleBulletNo  = 0;

    //******************************************************************
    // 初期化
    //******************************************************************
    public void Init(){ }

    //========================================================================================
    // 概要   ： ボスのターゲット弾の発射前処理から発射後の初期化処理まで全て
    // 仮引数 ： 弾のパラメータ全て
    // 戻り値 ： なし
    //========================================================================================
    public void Move(List<ImageView> img, List<Float> posX, List<Float> posY,
                     List<Double> directionX, List<Double> directionY, List<Boolean> bulletNow,
                     List<Integer> bulletLife, List<Integer> homingStartTime, int bulletSpeed) {

        //====================================================================================
        // ボス弾の発射時の座標を指定
        //====================================================================================
        for (int i = 0; i < BossBullet.BULLET_MAX; i++) {

            //-------------------------------------------
            // 発射可能な弾を探す
            //-------------------------------------------
            if (!bulletNow.get(i) && bulletInterval == 0) {
                bulletNow.set(i, true);
                tripleBulletNo++;

                //-------------------------------------------
                // 弾の発射時の座標を指定
                //-------------------------------------------
                ImageView getImg = Boss.GetImageView();
                posX.set(i, Boss.GetPosX() + getImg.getWidth() / 5);
                posY.set(i, Boss.GetPosY() + getImg.getHeight() * 1.8f);
                img.get(i).setX(posX.get(i));
                img.get(i).setY(posY.get(i));

                //------------------------------------------
                // ターゲット方向のベクトルを求める(右斜め上)
                //------------------------------------------
                if (tripleBulletNo == 2) {
                    float vecX = Processing.GetVector(
                            Boss.GetPosX() + RIGHTVECTOR_X, Boss.GetPosX());
                    float vecY = Processing.GetVector(
                            Boss.GetPosY() + VECTOR_Y, Boss.GetPosY());

                    //---------------------------------
                    // 三平方の定理を使って長さを求める
                    //---------------------------------
                    double length = Processing.GetLength(vecX, vecY);

                    //-----------------------------------------
                    // ベクトルを求めた長さで割り、正規化にする
                    //-----------------------------------------
                    directionX.set(i, vecX / length);
                    directionY.set(i, vecY / length);
                }

                //-----------------------------------------
                // ターゲット方向のベクトルを求める(左斜め上)
                //-----------------------------------------
                if (tripleBulletNo == 3) {
                    float vecX = Processing.GetVector(
                            Boss.GetPosX() + LEFTVECTOR_X, Boss.GetPosX());
                    float vecY = Processing.GetVector(
                            Boss.GetPosY() + VECTOR_Y, Boss.GetPosY());

                    //----------------------------------
                    // 三平方の定理を使って長さを求める
                    //----------------------------------
                    double length = Processing.GetLength(vecX, vecY);

                    //-------------------------------------------
                    // ベクトルを求めた長さで割り、正規化にする
                    //-------------------------------------------
                    directionX.set(i, vecX / length);
                    directionY.set(i, vecY / length);
                }

                //------------------------------------------
                // ターゲット方向のベクトルを求める(右斜め上)
                //------------------------------------------
                if (tripleBulletNo == 4) {
                    float vecX = Processing.GetVector(
                            Boss.GetPosX() + RIGHTVECTOR_X * 3, Boss.GetPosX());
                    float vecY = Processing.GetVector(
                            Boss.GetPosY() + VECTOR_Y, Boss.GetPosY());

                    //---------------------------------
                    // 三平方の定理を使って長さを求める
                    //---------------------------------
                    double length = Processing.GetLength(vecX, vecY);

                    //-----------------------------------------
                    // ベクトルを求めた長さで割り、正規化にする
                    //-----------------------------------------
                    directionX.set(i, vecX / length);
                    directionY.set(i, vecY / length);
                }

                //-----------------------------------------
                // ターゲット方向のベクトルを求める(左斜め上)
                //-----------------------------------------
                if (tripleBulletNo == 5) {
                    float vecX = Processing.GetVector(
                            Boss.GetPosX() + LEFTVECTOR_X * 3, Boss.GetPosX());
                    float vecY = Processing.GetVector(
                            Boss.GetPosY() + VECTOR_Y, Boss.GetPosY());

                    //----------------------------------
                    // 三平方の定理を使って長さを求める
                    //----------------------------------
                    double length = Processing.GetLength(vecX, vecY);

                    //-------------------------------------------
                    // ベクトルを求めた長さで割り、正規化にする
                    //-------------------------------------------
                    directionX.set(i, vecX / length);
                    directionY.set(i, vecY / length);

                    tripleBulletNo = 0;
                    bulletInterval = 20;
                    break;
                }
            }
        }

        //====================================================================================
        // 発射中の弾の移動(ターゲットめがけて)
        //====================================================================================
        for (int i = 0; i < BossBullet.BULLET_MAX; i++) {
            if (bulletNow.get(i)) {

                bulletLife.set(i, bulletLife.get(i) -1);
                tripleBulletNo++;
                switch (tripleBulletNo) {
                    // 直進発射
                    case 1:
                        posY.set(i, posY.get(i) + bulletSpeed);
                        break;
                    case 2:
                    case 3:

                    case 4:
                        posX.set(i, (float)(posX.get(i) + directionX.get(i) * bulletSpeed));
                        posY.set(i, (float)(posY.get(i) + directionY.get(i) * bulletSpeed));
                        break;

                    case 5:
                        posX.set(i, (float)(posX.get(i) + directionX.get(i) * bulletSpeed));
                        posY.set(i, (float)(posY.get(i) + directionY.get(i) * bulletSpeed));
                        tripleBulletNo = 0;
                        break;
                }
            }
        }

        //------------------------------------------------------------------------------------
        // 弾の座標更新
        //------------------------------------------------------------------------------------
        for (int i = 0; i < PlayerBullet.BULLET_MAX; i++) {
            img.get(i).setX(posX.get(i));
            img.get(i).setY(posY.get(i));
        }

        //------------------------------------------------------------------------------------
        // インタバルカウント
        //------------------------------------------------------------------------------------
        if(bulletInterval != 0){
            bulletInterval--;
        }

        //====================================================================================
        // ボスの弾リセット
        //====================================================================================
        for (int i = 0; i < BossBullet.BULLET_MAX; i++) {
            if (bulletLife.get(i) == 0) {
                img.get(i).setX(GameManager.INIT_POS);
                img.get(i).setY(GameManager.INIT_POS);
                posX.set(i, GameManager.INIT_POS);
                posY.set(i, GameManager.INIT_POS);
                bulletLife.set(i, BossBullet.BOSSFIRE_LIFETIME);
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
