//******************************************************************
// ゲームメイン処理クラス
//******************************************************************

package com.Kamooone.kamoooneshot;

import android.content.Context;

import java.util.Timer;

public class Game {
    private GameActivity    gameAcObj = GameActivity.GetThisObj();

    //******************************************************************
    // クラスのインスタンス生成
    //******************************************************************
    private  BackGround     bgObj           = new BackGround();
    private  Player         playerObj       = new Player();
    private  PlayerBullet   playerBulletObj = new PlayerBullet();
    private  Enemy          enemyObj        = new Enemy();
    private  EnemyBullet    enemyBulletObj  = new EnemyBullet();
    private  Boss           bossObj         = new Boss();
    private  BossBullet     bossBulletObj   = new BossBullet();
    private  Collision      collisionObj    = new Collision();
    private  Explosion      explosionObj    = new Explosion();
    private  SoundPlayer    soundPlayerObj  = new SoundPlayer();

    //******************************************************************
    // ゲーム初期化
    //******************************************************************
    public void Init(Context context){
        bgObj.Init();
        playerObj.Init();
        playerBulletObj.Init();
        enemyObj.Init();
        enemyBulletObj.Init();
        bossObj.Init();
        bossBulletObj.Init();
        collisionObj.Init();
        explosionObj.Init();
        soundPlayerObj.Init(context);
    }

    //******************************************************************
    // ゲームアップデート
    //******************************************************************
    public void Update(Timer timer){
        // BGM再生
        SoundPlayer.BGM1Start();

        bgObj.Move();
        playerObj.Move();
        playerBulletObj.Move();
        enemyObj.Move();
        enemyBulletObj.Move();
        bossObj.Move();
        bossBulletObj.Move();
        Collision.CollisionJudge();

        if(GameManager.GetGameRoundState() != GameManager.GameRoundState.FINALROUND) {
            explosionObj.Move();
        }

        if(GameManager.GetGameRoundState() == GameManager.GameRoundState.FINALROUND) {
            // BGM停止
            SoundPlayer.BGM1Stop();

            // 次にのBGM再生
            SoundPlayer.BGM2Start();

            explosionObj.MoveBoss();
        }

        GameManager.RoundUpdate();
        GameManager.GameOver(timer);
    }
}
