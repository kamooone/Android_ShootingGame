package com.Kamooone.kamoooneshot;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class HomingBullet extends UsuallyBullet{
    //******************************************************************
    // メンバ変数定義
    //******************************************************************
    private   List<Double>     workLengthBuff      = new ArrayList<>();
    private   List<Double>     LengthBuff          = new ArrayList<>();
    private   List<Double>     workDirectionX      = new ArrayList<>();
    private   List<Double>     workDirectionY      = new ArrayList<>();
    private   List<Float>      workVecX            = new ArrayList<>();
    private   List<Float>      workVecY            = new ArrayList<>();
    private   List<Boolean>    targetLockOn        = new ArrayList<>();
    private   List<Integer>    targetEnemyNoArray  = new ArrayList<>();
    private   List<Boolean>    targetLockOnFlg     = new ArrayList<>();

    //******************************************************************
    // 初期化
    //******************************************************************
    public void Init(){
        for (int i = 0; i < PlayerBullet.BULLET_MAX; i++) {
            workLengthBuff.add(0.0);
            LengthBuff.add(5000.0);
            workDirectionX.add(0.0);
            workDirectionY.add(0.0);
            workVecX.add(0.0f);
            workVecY.add(0.0f);
            targetLockOn.add(false);
            targetEnemyNoArray.add(0);
            targetLockOnFlg.add(false);
        }
    }

    //========================================================================================
    // 概要   ： ホーミングショットの発射前処理から発射後の初期化処理まで全て
    // 仮引数 ： 弾のパラメータ全て
    // 戻り値 ： なし
    //========================================================================================
    public void Move(List<ImageView> img, List<Float> posX, List<Float> posY,
                     List<Double> directionX, List<Double> directionY, List<Boolean> bulletNow,
                     List<Integer> bulletLife, List<Integer> homingStartTime, int bulletSpeed){

        //------------------------------------------------------------------------------------
        // 弾発射前処理
        //------------------------------------------------------------------------------------
        for (int i = 0; i < PlayerBullet.BULLET_MAX; i++) {

            //----------------------------------
            // 弾の発射時の座標を指定
            //----------------------------------
            if (!bulletNow.get(i) && bulletInterval == 0) {
                bulletNow.set(i, true);
                ImageView getImg = Player.GetImageView();
                img.get(i).setX(Player.GetPosX() + getImg.getWidth() / 3 +
                        PlayerBullet.BulletFirePosCorrection);
                img.get(i).setY(Player.GetPosY());
                posX.set(i, img.get(i).getX());
                posY.set(i, img.get(i).getY());
                bulletInterval = 7;
                break;
            }
        }

        //------------------------------------------------------------------------------------
        // 発射中の弾の移動
        //------------------------------------------------------------------------------------
        for(int i = 0; i < PlayerBullet.BULLET_MAX; i++) {
            if (bulletNow.get(i)) {
                bulletLife.set(i, bulletLife.get(i) -1);
                //---------------------------------------------------------------------------
                // 一番近い距離の敵の要素番号を取得(初期設定)
                //---------------------------------------------------------------------------
                for (int cnt = 0; cnt < Enemy.ENEMY_MAX; cnt++) {
                    if (/*!targetLockOn.get(i) && */Enemy.GetPosY(cnt) > 0) {

                        //----------------------------------
                        // ターゲット方向のベクトルを求める
                        //----------------------------------
                        workVecX.set(i, Processing.GetVector(Enemy.GetPosX(cnt), Player.GetPosX()));
                        workVecY.set(i, Processing.GetVector(Enemy.GetPosY(cnt), Player.GetPosY()));

                        //----------------------------------
                        // 三平方の定理を使って長さを求める
                        //----------------------------------
                        workLengthBuff.set(i, Processing.GetLength(workVecX.get(i), workVecY.get(i)));

                        //----------------------------------
                        // 一番近い敵の要素番号を取得
                        //----------------------------------
                        if (LengthBuff.get(i) > workLengthBuff.get(i)) {
                            LengthBuff.set(i, workLengthBuff.get(i));
                            targetEnemyNoArray.set(i, cnt);
                            targetLockOnFlg.set(i, true);
                            targetLockOn.set(i, true);
                        }
                    }

                    //------------------------------------------------------------
                    // 一度最短距離の相手を求めたらもうこの処理を行わないようにする
                    //------------------------------------------------------------
                    //if(cnt == Enemy.ENEMY_MAX - 1 && targetLockOnFlg.get(i)){
                    //    targetLockOn.set(i, true);
                    //    targetLockOnFlg.set(i, false);
                    //}
                }

                //-----------------------------------------------------------------------------
                // 追尾位置ベクトルを指定(随時更新)
                //-----------------------------------------------------------------------------
                if (targetLockOn.get(i) && posX.get(i) > 0 && posX.get(i) < GameManager.GetScreenWidth()) {

                    //----------------------------------
                    // ターゲット方向のベクトルを求める
                    //----------------------------------
                    workVecX.set(i, Processing.GetVector(Enemy.GetPosX(targetEnemyNoArray.get(i)), posX.get(i)));
                    workVecY.set(i, Processing.GetVector(Enemy.GetPosY(targetEnemyNoArray.get(i)), posY.get(i)));

                    //----------------------------------
                    // 三平方の定理を使って長さを求める
                    //----------------------------------
                    LengthBuff.set(i, Processing.GetLength(workVecX.get(i), workVecY.get(i)));

                    //----------------------------------
                    // 求めた長さで割り、正規化にする
                    //----------------------------------
                    directionX.set(i, workVecX.get(i) / LengthBuff.get(i));
                    directionY.set(i, workVecY.get(i) / LengthBuff.get(i));
                    workDirectionX.set(i, directionX.get(i));
                    workDirectionY.set(i, directionY.get(i));
                }
            }
        }

        //------------------------------------------------------------------------------------
        // 弾の座標更新
        //------------------------------------------------------------------------------------
        for (int i = 0; i < PlayerBullet.BULLET_MAX; i++) {
            //-----------------------------------------------------------------------------
            // 追尾が始まる時間をカウント(発射後20になったら追尾開始)
            //-----------------------------------------------------------------------------
            homingStartTime.set(i, homingStartTime.get(i) +1);

            //-----------------------------------------------------------------------------
            // 弾の座標更新(追尾)
            //-----------------------------------------------------------------------------
            if(homingStartTime.get(i) >= 20 && Enemy.GetPosY(targetEnemyNoArray.get(i)) > 0 &&
                    targetLockOn.get(i) && Collision.GetEnemyLife(targetEnemyNoArray.get(i)))
            {
                posX.set(i, (float) (posX.get(i) - directionX.get(i) * bulletSpeed));
                posY.set(i, (float) (posY.get(i) - directionY.get(i) * bulletSpeed));
            }
            // 衝突時は消滅
            else if(homingStartTime.get(i) >= 20 && targetLockOn.get(i) &&
                    !Collision.GetEnemyLife(targetEnemyNoArray.get(i)) ||
                    (Enemy.GetPosY(targetEnemyNoArray.get(i)) > GameManager.GetScreenHeight() -
                    GameManager.GetScreenHeight() / 6)) {
                posX.set(i, (float) (posX.get(i) - workDirectionX.get(i) * bulletSpeed * 10000));
                posY.set(i, (float) (posY.get(i) - workDirectionY.get(i) * bulletSpeed * 10000));
            }
            //-----------------------------------------------------------------------------
            // 直進発射
            //-----------------------------------------------------------------------------
            else {
                posY.set(i, posY.get(i) + bulletSpeed);
            }
            img.get(i).setX(posX.get(i));
            img.get(i).setY(posY.get(i));
        }

        //------------------------------------------------------------------------------------
        // インタバルカウント
        //------------------------------------------------------------------------------------
        if(bulletInterval != 0){
            bulletInterval--;
        }

        //------------------------------------------------------------------------------------
        // 時間または衝突で弾を初期化
        //------------------------------------------------------------------------------------
        for (int i = 0; i < PlayerBullet.BULLET_MAX; i++) {
            if (bulletLife.get(i) == 0) {
                img.get(i).setX(GameManager.INIT_POS);
                img.get(i).setY(GameManager.INIT_POS);
                posX.set(i, GameManager.INIT_POS);
                posY.set(i, GameManager.INIT_POS);
                bulletNow.set(i, false);
                targetLockOn.set(i, false);
                homingStartTime.set(i, 0);
                targetEnemyNoArray.set(i, 0);
                bulletLife.set(i, Bullet.FIRE_LIFETIME);
                LengthBuff.set(i, 5000.0);
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