package com.Kamooone.kamoooneshot;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Explosion extends Char{
    //******************************************************************
    // 変数定義
    //******************************************************************
    private List<Integer>     explosionCnt         = new ArrayList<>();
    private List<Integer>     explosionImg         = new ArrayList<>();
    private List<Integer>     explosionInterval    = new ArrayList<>();
    private List<Boolean>     explosionFlg         = new ArrayList<>();

    private int            gameOverTime = 0;
    private static boolean resultFlg    = false;

    //========================================================================================
    // 概要   ： 爆発アニメ初期化
    // 仮引数 ： なし
    // 戻り値 ： なし
    //========================================================================================
    public void Init(){
        gameAcObj = GameActivity.GetThisObj();
        resultFlg = false;
        gameOverTime = 0;

        //------------------------------------------------------------------------------------
        // 画像のID取得
        //------------------------------------------------------------------------------------
        id = new ArrayList<>(Arrays.asList(
                R.id.explosion1, R.id.explosion2, R.id.explosion3, R.id.explosion4, R.id.explosion5,
                R.id.explosion6, R.id.explosion7, R.id.explosion8, R.id.explosion9, R.id.explosion10,
                R.id.explosion11, R.id.explosion12, R.id.explosion13, R.id.explosion14,
                R.id.explosion15, R.id.explosion16, R.id.explosion17, R.id.explosion18,
                R.id.explosion19, R.id.explosion20, R.id.explosion21, R.id.explosion22,
                R.id.explosion23, R.id.explosion24, R.id.explosion25)
        );

        //------------------------------------------------------------------------------------
        // 画像を取得
        //------------------------------------------------------------------------------------
        explosionImg = new ArrayList<>(Arrays.asList(
                R.drawable.explosion1, R.drawable.explosion2, R.drawable.explosion3,
                R.drawable.explosion4, R.drawable.explosion5, R.drawable.explosion6,
                R.drawable.explosion7, R.drawable.explosion8, R.drawable.explosion9,
                R.drawable.explosion10, R.drawable.explosion11, R.drawable.explosion12,
                R.drawable.explosion13, R.drawable.explosion14, R.drawable.explosion15,
                R.drawable.explosion16)
        );

        //------------------------------------------------------------------------------------
        // リストをセット
        //------------------------------------------------------------------------------------
        for(int i = 0; i < Enemy.ENEMY_MAX + 1; i++) {
            img.add(gameAcObj.findViewById(id.get(i)));
            img.get(i).setX(GameManager.INIT_POS);
            img.get(i).setY(GameManager.INIT_POS);
            posX.add(img.get(i).getX());
            posY.add(img.get(i).getY());
            explosionInterval.add(0);
            explosionCnt.add(0);
            explosionFlg.add(false);
        }
    }

    //========================================================================================
    // 概要   ： 爆発アニメ描画処理
    // 仮引数 ： なし
    // 戻り値 ： なし
    //========================================================================================
    public void Move(){
        //------------------------------------------
        // エネミー総数分ループ
        //------------------------------------------
        for (int i = 0; i < Enemy.ENEMY_MAX + 1; i++) {

            //------------------------------------------
            // 爆発アニメの座標をセット
            //------------------------------------------
            if(i != Enemy.ENEMY_MAX) {
                if (!Collision.GetEnemyLife(i)) {
                    explosionFlg.set(i, true);
                    img.get(i).setX(Enemy.GetPosX(i));
                    img.get(i).setY(Enemy.GetPosY(i) + 50.0f);
                    posX.set(i, img.get(i).getX());
                    posY.set(i, img.get(i).getY());
                }
            }
            if(!Collision.GetPlayerLife() && i == Enemy.ENEMY_MAX && !explosionFlg.get(Enemy.ENEMY_MAX)){
                explosionFlg.set(i, true);
                img.get(i).setX(Player.GetPosX() + img.get(0).getWidth() / 2);
                img.get(i).setY(Player.GetPosY());
                posX.set(i, img.get(i).getX());
                posY.set(i, img.get(i).getY());
            }

            //------------------------------------------
            // テクスチャ切り替え処理
            //------------------------------------------
            if (explosionFlg.get(i)) {
                ((ImageView) gameAcObj.findViewById(id.get(i))).setImageResource(explosionImg.get(explosionCnt.get(i)));

                //----------------------
                // テクスチャ切り替え
                //----------------------
                explosionInterval.set(i, explosionInterval.get(i) + 1);

                //----------------------
                // 切り替えインターバル
                //----------------------
                if (explosionInterval.get(i) == 5) {
                    explosionCnt.set(i, explosionCnt.get(i) + 1);
                    explosionInterval.set(i, 0);
                }

                //----------------------
                // 描画終了そして初期化
                //----------------------
                if(explosionCnt.get(Enemy.ENEMY_MAX) == 16 && !Collision.GetPlayerLife()){
                    resultFlg = true;
                }
                if (explosionCnt.get(i) == 16) {
                    explosionInterval.set(i, 0);
                    explosionCnt.set(i, 0);
                    img.get(i).setX(GameManager.INIT_POS);
                    img.get(i).setY(GameManager.INIT_POS);
                    posX.set(i, GameManager.INIT_POS);
                    posY.set(i, GameManager.INIT_POS);
                    explosionFlg.set(i, false);
                }
            }
        }
    }

    //========================================================================================
    // 概要   ： ボス戦時、爆発アニメ描画処理
    // 仮引数 ： なし
    // 戻り値 ： なし
    //========================================================================================
    public void MoveBoss(){
        //------------------------------------------
        // 弾衝突時の爆発
        //------------------------------------------
        for (int i = 0; i < PlayerBullet.BULLET_MAX; i++) {

            //------------------------------------------
            // 爆発アニメの座標をセット
            //------------------------------------------
            for (int cnt = 0; cnt < Enemy.ENEMY_MAX + 1; cnt++) {

                //------------------------------------------
                // ダメージ爆発
                //------------------------------------------
                if (cnt != Enemy.ENEMY_MAX) {
                    if (Collision.GetBossDamageFlg(i) && Collision.GetBossLife() && !explosionFlg.get(cnt)) {
                        explosionFlg.set(cnt, true);
                        img.get(cnt).setX(PlayerBullet.GetPosX(i) + img.get(0).getWidth() / 2);
                        img.get(cnt).setY(PlayerBullet.GetPosY(i));
                        posX.set(cnt, img.get(cnt).getX());
                        posY.set(cnt, img.get(cnt).getY());
                    }
                }
                //------------------------------------------
                // ボス消滅時
                //------------------------------------------
                else if (!Collision.GetPlayerLife() && cnt == Enemy.ENEMY_MAX && !explosionFlg.get(Enemy.ENEMY_MAX)) {
                    explosionFlg.set(cnt, true);
                    img.get(cnt).setX(Player.GetPosX() + img.get(0).getWidth() / 2);
                    img.get(cnt).setY(Player.GetPosY());
                    posX.set(cnt, img.get(cnt).getX());
                    posY.set(cnt, img.get(cnt).getY());
                }
                //------------------------------------------
                //プレイヤー消滅時
                //------------------------------------------
                else if (!Collision.GetBossLife() && cnt == Enemy.ENEMY_MAX && !explosionFlg.get(Enemy.ENEMY_MAX)) {
                    explosionFlg.set(cnt, true);
                    img.get(cnt).setX(Boss.GetPosX() + img.get(0).getWidth() / 2);
                    img.get(cnt).setY(Boss.GetPosY());
                    posX.set(cnt, img.get(cnt).getX());
                    posY.set(cnt, img.get(cnt).getY());
                }
            }
        }

        //------------------------------------------
        // テクスチャ切り替え処理
        //------------------------------------------
        for(int cnt = 0; cnt < Enemy.ENEMY_MAX + 1; cnt++) {
            if (explosionFlg.get(cnt)) {
                ((ImageView) gameAcObj.findViewById(id.get(cnt))).setImageResource(explosionImg.get(explosionCnt.get(cnt)));

                //----------------------
                // テクスチャ切り替え
                //----------------------
                explosionInterval.set(cnt, explosionInterval.get(cnt) + 1);

                //----------------------
                // 切り替えインターバル
                //----------------------
                if (explosionInterval.get(cnt) == 1 && cnt != Enemy.ENEMY_MAX) {
                    explosionCnt.set(cnt, explosionCnt.get(cnt) + 1);
                    explosionInterval.set(cnt, 0);
                }
                if (explosionInterval.get(cnt) == 5 && cnt == Enemy.ENEMY_MAX) {
                    explosionCnt.set(cnt, explosionCnt.get(cnt) + 1);
                    explosionInterval.set(cnt, 0);
                }
            }
        }

        //---------------------------------------------------------------
        // 描画終了そして初期化
        //---------------------------------------------------------------
        for(int cnt = 0; cnt < Enemy.ENEMY_MAX + 1; cnt++) {
            if (explosionCnt.get(Enemy.ENEMY_MAX) == 16 && gameOverTime == 0) {
                gameOverTime = 1;
            }
            if (explosionCnt.get(cnt) == 16) {
                explosionInterval.set(cnt, 0);
                explosionCnt.set(cnt, 0);
                img.get(cnt).setX(GameManager.INIT_POS);
                img.get(cnt).setY(GameManager.INIT_POS);
                posX.set(cnt, GameManager.INIT_POS);
                posY.set(cnt, GameManager.INIT_POS);
                explosionFlg.set(cnt, false);
            }
        }

        //----------------------
        // リザルト遷移処理
        //----------------------
        if(gameOverTime >= 1){
            gameOverTime++;
        }
        if(gameOverTime >= 80){
            resultFlg  = true;
        }
    }

    //========================================================================================
    // 概要   ： ゲームオーバーフラグフラグゲッター
    // 仮引数 ： なし
    // 戻り値 ： ゲームオーバーフラグフラグ
    //========================================================================================
    public static boolean GetResultFlg()  { return resultFlg; }
}
