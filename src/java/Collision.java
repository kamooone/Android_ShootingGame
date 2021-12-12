package com.Kamooone.kamoooneshot;

import java.util.ArrayList;
import java.util.List;

public class Collision {
    //========================================================================================
    // 中心座標宣言
    //========================================================================================
    private static float       playerCenterX       = 0;
    private static float       playerCenterY       = 0;
    private static List<Float> playerBulletCenterX = new ArrayList<>();
    private static List<Float> playerBulletCenterY = new ArrayList<>();
    private static List<Float> GetPlayerBulletX    = new ArrayList<>();
    private static List<Float> GetPlayerBulletY    = new ArrayList<>();
    private static boolean     playerLife          = true;

    private static List<Float> enemyCenterX        = new ArrayList<>();
    private static List<Float> enemyCenterY        = new ArrayList<>();
    private static List<Float> enemyBulletCenterX  = new ArrayList<>();
    private static List<Float> enemyBulletCenterY  = new ArrayList<>();

    private static boolean       bossLife          = true;
    private static List<Boolean> bossDamageFlg     = new ArrayList<>();
    private static int           bossHitSum        = 0;
    private static float         bossCenterX       = 0;
    private static float         bossCenterY       = 0;
    private static List<Float>   bossBulletCenterX = new ArrayList<>();
    private static List<Float>   bossBulletCenterY = new ArrayList<>();

    private static List<Boolean> enemyLife         = new ArrayList<>();
    private static List<Boolean> playerBulletLife  = new ArrayList<>();
    private static List<Boolean> enemyBulletLife   = new ArrayList<>();

    //========================================================================================
    // 概要   ： 中心座標初期化
    // 仮引数 ： なし
    // 戻り値 ： なし
    //========================================================================================
    public void Init() {
        playerLife = true;
        bossLife   = true;
        bossHitSum = 0;

        for (int i = 0; i < PlayerBullet.BULLET_MAX; i++) {
            playerBulletCenterX.add(0.0f);
            playerBulletCenterY.add(0.0f);
            GetPlayerBulletX.add(0.0f);
            GetPlayerBulletY.add(0.0f);
            playerBulletLife.add(true);
            bossDamageFlg.add(false);
        }

        for (int i = 0; i < Enemy.ENEMY_MAX; i++) {
            enemyCenterX.add(0.0f);
            enemyCenterY.add(0.0f);
            enemyLife.add(true);
        }

        for (int i = 0; i < EnemyBullet.ENEMYBULLET_SUM; i++) {
            enemyBulletCenterX.add(0.0f);
            enemyBulletCenterY.add(0.0f);
            enemyBulletLife.add(true);
            bossBulletCenterX.add(0.0f);
            bossBulletCenterY.add(0.0f);
        }
    }

    //========================================================================================
    // 概要   ： 当たり判定入口
    // 仮引数 ： なし
    // 戻り値 ： なし
    //========================================================================================
    static void CollisionJudge() {
        //------------------------------------------------------------------------------------
        // 自機の弾とエネミーの中心座標を取得
        //------------------------------------------------------------------------------------
        GetTheCenterCoordinates_BulletWithEnemy();

        //------------------------------------------------------------------------------------
        // 自機の弾とエネミー(シングルユニット)との衝突判定
        //------------------------------------------------------------------------------------
        if(GameManager.GetGameRoundState() == GameManager.GameRoundState.ROUND4) {
            HitStatus(playerBulletCenterX, playerBulletCenterY, enemyCenterX, enemyCenterY, PlayerBullet.BULLET_MAX, 1);
        }

        //------------------------------------------------------------------------------------
        // 自機の弾とエネミーとの衝突判定
        //------------------------------------------------------------------------------------
        if(GameManager.GetGameRoundState() != GameManager.GameRoundState.ROUND4 &&
                GameManager.GetGameRoundState() != GameManager.GameRoundState.FINALROUND) {
            HitStatus(playerBulletCenterX, playerBulletCenterY, enemyCenterX, enemyCenterY, PlayerBullet.BULLET_MAX, Enemy.ENEMY_MAX);
        }

        //------------------------------------------------------------------------------------
        // エネミーの弾とプレイヤーの中心座標を取得
        //------------------------------------------------------------------------------------
        GetTheCenterCoordinates_EnemyBulletWithPlayer();

        //------------------------------------------------------------------------------------
        // 敵の弾と自機の接触判定(オーバーロード + オーバーライド)
        //------------------------------------------------------------------------------------
        HitStatus(enemyBulletCenterX, enemyBulletCenterY, playerCenterX, playerCenterY, EnemyBullet.ENEMYBULLET_SUM);

        //------------------------------------------------------------------------------------
        // プレイヤー弾とエネミー弾の当たり判定(オーバーロード + オーバーライド)
        //------------------------------------------------------------------------------------
        HitStatus(enemyBulletCenterX, enemyBulletCenterY, playerBulletCenterX, playerBulletCenterY);

        if (GameManager.GetGameRoundState() == GameManager.GameRoundState.FINALROUND) {
            //------------------------------------------------------------------------------------
            // ボスと自機の接触判定(オーバーロード + オーバーライド)
            //------------------------------------------------------------------------------------
            HitStatus(bossCenterX, bossCenterY, playerCenterX, playerCenterY);

            //------------------------------------------------------------------------------------
            // プレイヤーの弾とボスの接触判定(オーバーロード + オーバーライド)
            //------------------------------------------------------------------------------------
            HitStatus(playerBulletCenterX, playerBulletCenterY, bossCenterX, bossCenterY,
                    PlayerBullet.BULLET_MAX, 1);

            //------------------------------------------------------------------------------------
            // プレイヤー弾とボスの弾の当たり判定(オーバーロード + オーバーライド)
            //------------------------------------------------------------------------------------
            HitStatus(bossBulletCenterX, bossBulletCenterY, playerBulletCenterX, playerBulletCenterY);

            //------------------------------------------------------------------------------------
            // ボスの弾と自機の接触判定(オーバーロード + オーバーライド)
            //------------------------------------------------------------------------------------
            HitStatus(bossBulletCenterX, bossBulletCenterY, playerCenterX, playerCenterY, BossBullet.BULLET_MAX);
        }
    }

    //========================================================================================
    // 概要   ： 自機の弾とエネミーの中心座標を取得
    // 仮引数 ： なし
    // 戻り値 ： なし
    //========================================================================================
    static void GetTheCenterCoordinates_BulletWithEnemy() {
        //------------------------------------------------------------------------------------
        // プレイヤーの弾座標取得
        //------------------------------------------------------------------------------------
        for (int i = 0; i < PlayerBullet.BULLET_MAX; i++) {
            GetPlayerBulletX.set(i, PlayerBullet.GetPosX(i));
            GetPlayerBulletY.set(i, PlayerBullet.GetPosY(i));
        }

        //------------------------------------------------------------------------------------
        // 中心座標を取得
        //------------------------------------------------------------------------------------
        playerBulletCenterX = Processing.GetTheCenterCoordinates_X(playerBulletCenterX,
                PlayerBullet.GetPosX(), PlayerBullet.GetImageView(), PlayerBullet.BULLET_MAX);
        playerBulletCenterY = Processing.GetTheCenterCoordinates_Y(playerBulletCenterY,
                PlayerBullet.GetPosY(), PlayerBullet.GetImageView(), PlayerBullet.BULLET_MAX);

        //------------------------------------------------------------------------------------
        // 中心座標を取得
        //------------------------------------------------------------------------------------
        enemyCenterX = Processing.GetTheCenterCoordinates_X(enemyCenterX, Enemy.GetPosX(),
                Enemy.GetImageView(), Enemy.ENEMY_MAX);
        enemyCenterY = Processing.GetTheCenterCoordinates_Y(enemyCenterY, Enemy.GetPosY(),
                Enemy.GetImageView(), Enemy.ENEMY_MAX);
    }

    //========================================================================================
    // 概要   ： 自機の弾とエネミーとの衝突判定
    // 仮引数 ： 自機の弾の中心座標とエネミーの中心座標、弾のMAX値、エネミーのMAX値
    // 戻り値 ： なし
    //========================================================================================
    static void HitStatus(List<Float> playerBulletCenterX, List<Float> playerBulletCenterY,
                          List<Float> enemyCenterX, List<Float> enemyCenterY,
                          int fireMax, int charMax) {

        //------------------------------------------------------------------------------------
        // 生存フラグリセット
        //------------------------------------------------------------------------------------
        for(int i = 0; i < fireMax; i++) {
            if (!playerBulletLife.get(i)) {
                playerBulletLife.set(i, true);
            }
        }
        for(int i = 0; i < Enemy.ENEMY_MAX; i++) {
            if (!enemyLife.get(i)) {
                enemyLife.set(i, true);
            }
        }

        //------------------------------------------------------------------------------------
        // 衝突判定処理
        //------------------------------------------------------------------------------------
        for (int cnt = 0; cnt < fireMax; cnt++) {
            for (int i = 0; i < charMax; i++) {

                // 二つの対象オブジェクトのベクトルを取得
                float vecX = Processing.GetVector(enemyCenterX.get(i), playerBulletCenterX.get(cnt));
                float vecY = Processing.GetVector(enemyCenterY.get(i), playerBulletCenterY.get(cnt));

                // 求めたベクトルを使用して対象となる二つのオブジェクト間の距離を取得(三平方の定理を使用)
                double length = Processing.GetLength(vecX, vecY);

                // 対象となる二つオブジェクトの半径の和を求める
                float radius = Processing.AddRadius(
                        Enemy.GetImageView(), PlayerBullet.GetImageView());

                //-------------------------------------------------------------
                // 中心座標の2点間の距離より半径の和の方が大きければ接触
                //-------------------------------------------------------------
                if (length <= radius && GetPlayerBulletY.get(cnt) > 100.0f) {

                    //------------------------------------------
                    // 衝突フラグオン(衝突後の処理は各クラスで行う)
                    //------------------------------------------
                    enemyLife.set(i, false);
                    playerBulletLife.set(cnt, false);

                    // 爆発音再生
                    SoundPlayer.playHitSound();

                    //------------------------------
                    // スコア加算
                    //------------------------------
                    GameManager.ScoreUpdate();
                }
            }
        }
    }

    //========================================================================================
    // 概要   ： エネミーの弾とプレイヤーの中心座標を取得
    // 仮引数 ： なし
    // 戻り値 ： なし
    //========================================================================================
    static void GetTheCenterCoordinates_EnemyBulletWithPlayer(){
        //-------------------------------------------------------------
        // 中心座標を取得
        //-------------------------------------------------------------
        enemyBulletCenterX = Processing.GetTheCenterCoordinates_X(enemyBulletCenterX,
                EnemyBullet.GetPosX(), EnemyBullet.GetImageView(), EnemyBullet.ENEMYBULLET_SUM);
        enemyBulletCenterY = Processing.GetTheCenterCoordinates_Y(enemyBulletCenterY,
                EnemyBullet.GetPosY(), EnemyBullet.GetImageView(), EnemyBullet.ENEMYBULLET_SUM);

        bossBulletCenterX = Processing.GetTheCenterCoordinates_X(bossBulletCenterX,
                BossBullet.GetPosX(), BossBullet.GetImageView(), EnemyBullet.ENEMYBULLET_SUM);
        bossBulletCenterY = Processing.GetTheCenterCoordinates_Y(bossBulletCenterY,
                BossBullet.GetPosY(), BossBullet.GetImageView(), EnemyBullet.ENEMYBULLET_SUM);

        //-------------------------------------------------------------
        // 中心座標を取得(オーバーロード)
        //-------------------------------------------------------------
        playerCenterX = Processing.GetTheCenterCoordinates_X(Player.GetPosX(), Player.GetImageView());
        playerCenterY = Processing.GetTheCenterCoordinates_Y(Player.GetPosY(), Player.GetImageView());

        //-------------------------------------------------------------
        // 中心座標を取得(オーバーロード)
        //-------------------------------------------------------------
        bossCenterX = Processing.GetTheCenterCoordinates_X(Boss.GetPosX(), Boss.GetImageView());
        bossCenterY = Processing.GetTheCenterCoordinates_Y(Boss.GetPosY(), Boss.GetImageView());
    }

    //========================================================================================
    // 概要   ： エネミーの弾と自機との衝突判定(オーバーロード + オーバーライド)
    // 仮引数 ： エネミーの弾の中心座標とプレイヤーの中心座標、全てのエネミーの弾の合計値
    // 戻り値 ： なし
    //========================================================================================
    static void HitStatus(List<Float> enemyBulletCenterX, List<Float> enemyBulletCenterY,
                          float playerCenterX, float playerCenterY, int fireMax) {
        //-------------------------------------------------------------
        // エネミーの弾総数分ループ
        //-------------------------------------------------------------
        for(int i = 0; i < fireMax; i++) {
            //-----------------------------------------------
            // 二つの対象オブジェクトのベクトルを取得
            //-----------------------------------------------
            float vecX = Processing.GetVector(enemyBulletCenterX.get(i), playerCenterX);
            float vecY = Processing.GetVector(enemyBulletCenterY.get(i), playerCenterY);

            //-----------------------------------------------
            // 求めたベクトルを使用して対象となる二つのオブジェクト間の距離を取得(三平方の定理を使用)
            //-----------------------------------------------
            double length = Processing.GetLength(vecX, vecY);

            //-----------------------------------------------
            // 対象となる二つオブジェクトの半径の和を求める
            //-----------------------------------------------
            float radius = Processing.AddRadius(
                    Player.GetImageView(), EnemyBullet.GetImageView());

            //-----------------------------------------------
            // 中心座標の2点間の距離より半径の和の方が大きければ接触
            //-----------------------------------------------
            if (length <= radius) {
                SoundPlayer.playHitSound();

                // ゲームオーバー処理
                playerLife = false;
            }
        }
    }

    //========================================================================================
    // 概要   ： 自機の弾とエネミーの弾との衝突判定
    // 仮引数 ： 自機の弾の中心座標とエネミーの弾の中心座標
    // 戻り値 ： なし
    //========================================================================================
    static void HitStatus(List<Float> enemyBulletCenterX, List<Float> enemyBulletCenterY,
                          List<Float> playerBulletCenterX, List<Float> playerBulletCenterY) {

        //------------------------------------------------------------------------------------
        // 生存フラグリセット
        //------------------------------------------------------------------------------------
        for(int i = 0; i < PlayerBullet.BULLET_MAX; i++) {
            if (!playerBulletLife.get(i)) {
                playerBulletLife.set(i, true);
            }
        }
        for(int i = 0; i < EnemyBullet.ENEMYBULLET_SUM; i++) {
            if (!enemyBulletLife.get(i)) {
                enemyBulletLife.set(i, true);
            }
        }

        //------------------------------------------------------------
        // プレイヤーの弾とエネミーの弾総数分ループ
        //------------------------------------------------------------
        for(int cnt = 0; cnt < PlayerBullet.BULLET_MAX; cnt++) {
            for(int i = 0; i < EnemyBullet.ENEMYBULLET_SUM; i++) {

                if(playerBulletLife.get(cnt)) {
                    //---------------------------------------------
                    // 二つの対象オブジェクトのベクトルを取得
                    //---------------------------------------------
                    float vecX = Processing.GetVector(
                            enemyBulletCenterX.get(i), playerBulletCenterX.get(cnt));
                    float vecY = Processing.GetVector(
                            enemyBulletCenterY.get(i), playerBulletCenterY.get(cnt));

                    //---------------------------------------------
                    // 求めたベクトルを使用して対象となる二つのオブジェクト間の距離を取得(三平方の定理)
                    //---------------------------------------------
                    double length = Processing.GetLength(vecX, vecY);

                    //---------------------------------------------
                    // 対象となる二つオブジェクトの半径の和を求める
                    //---------------------------------------------
                    float radius = Processing.AddRadius(
                            EnemyBullet.GetImageView(), PlayerBullet.GetImageView());

                    //---------------------------------------------
                    // 中心座標の2点間の距離より半径の和の方が大きければかつプレイヤーの弾が画面内で接触
                    //---------------------------------------------
                    if (length <= radius && PlayerBullet.GetPosY(cnt) > 100.0f) {

                        // 処理はそれぞれのクラス内で行う
                        playerBulletLife.set(cnt, false);
                        enemyBulletLife.set(i, false);
                    }
                }
            }
        }
    }

    //========================================================================================
    // 概要   ： 自機の弾とボスとの衝突判定
    // 仮引数 ： 自機の弾の中心座標とボスの中心座標、弾のMAX値、ボスの数
    // 戻り値 ： なし
    //========================================================================================
    static void HitStatus(List<Float> playerBulletCenterX, List<Float> playerBulletCenterY,
                          float bossCenterX, float bossCenterY, int fireMax, int charMax) {

        //------------------------------------------------------------------------------------
        // 生存フラグリセット
        //------------------------------------------------------------------------------------
        for(int i = 0; i < fireMax; i++) {
            if (!playerBulletLife.get(i)) {
                playerBulletLife.set(i, true);
            }
            if (bossDamageFlg.get(i)) {
                bossDamageFlg.set(i, false);
            }
        }

        //------------------------------------------------------------------------------------
        // 衝突判定処理
        //------------------------------------------------------------------------------------
        for (int i = 0; i < fireMax; i++) {

            // 二つの対象オブジェクトのベクトルを取得
            float vecX = Processing.GetVector(bossCenterX, playerBulletCenterX.get(i));
            float vecY = Processing.GetVector(bossCenterY, playerBulletCenterY.get(i));

            // 求めたベクトルを使用して対象となる二つのオブジェクト間の距離を取得(三平方の定理を使用)
            double length = Processing.GetLength(vecX, vecY);

            // 対象となる二つオブジェクトの半径の和を求める
            float radius = Processing.AddRadius(Boss.GetImageView(), PlayerBullet.GetImageView());

            //-------------------------------------------------------------
            // 中心座標の2点間の距離より半径の和の方が大きければ接触
            //-------------------------------------------------------------
            if (length <= radius && PlayerBullet.GetPosX(i) > 100.0f) {

                //------------------------------------------
                // 衝突フラグオン(衝突後の処理は各クラスで行う)
                //------------------------------------------
                bossDamageFlg.set(i, true);
                playerBulletLife.set(i, false);

                // 爆発音再生
                SoundPlayer.playHitSound();

                //------------------------------
                // 弾ヒット回数加算
                //------------------------------
                bossHitSum++;
                if(bossHitSum > 500){
                    bossLife = false;
                }
            }
        }
    }

    //========================================================================================
    // 概要   ： ボスと自機との衝突判定(オーバーロード + オーバーライド)
    // 仮引数 ： ボスの中心座標とプレイヤーの中心座標
    // 戻り値 ： なし
    //========================================================================================
    static void HitStatus(float bossCenterX, float bossCenterY, float playerCenterX, float playerCenterY) {
        //-----------------------------------------------
        // 二つの対象オブジェクトのベクトルを取得
        //-----------------------------------------------
        float vecX = Processing.GetVector(bossCenterX, playerCenterX);
        float vecY = Processing.GetVector(bossCenterY, playerCenterY);

        //-----------------------------------------------
        // 求めたベクトルを使用して対象となる二つのオブジェクト間の距離を取得(三平方の定理を使用)
        //-----------------------------------------------
        double length = Processing.GetLength(vecX, vecY);

        //-----------------------------------------------
        // 対象となる二つオブジェクトの半径の和を求める
        //-----------------------------------------------
        float radius = Processing.AddRadius(Boss.GetImageView(), Player.GetImageView());

        //-----------------------------------------------
        // 中心座標の2点間の距離より半径の和の方が大きければ接触
        //-----------------------------------------------
        if (length < radius + 50.0f) {
            SoundPlayer.playHitSound();

            // ゲームオーバー処理
            playerLife = false;
        }
    }

    //========================================================================================
    // 概要   ： コリジョンフラグゲッター
    // 仮引数 ： なし
    // 戻り値 ： コリジョンフラグ
    //========================================================================================
    public static boolean GetEnemyLife(int i)         { return enemyLife.get(i); }
    public static boolean GetPlayerBulletLife(int i)  { return playerBulletLife.get(i); }
    public static boolean GetEnemyBulletLife(int i)   { return enemyBulletLife.get(i); }
    public static boolean GetBossDamageFlg(int i)     { return bossDamageFlg.get(i); }
    public static boolean GetPlayerLife()             { return playerLife; }
    public static boolean GetBossLife()               { return bossLife; }
}
