package com.Kamooone.kamoooneshot;

import android.widget.ImageView;

public class Boss extends Char {
    //******************************************************************
    // 変数定義
    //******************************************************************
    private static float setPosX, setPosY;
    private static ImageView setImg;
    private int    moveAround = 1;

    //========================================================================================
    // 概要   ： ボスの初期化処理(必要分のListを生成)
    // 仮引数 ： なし
    // 戻り値 ： なし
    //========================================================================================
    public void Init(){
        gameAcObj = GameActivity.GetThisObj();
        id.add(R.id.boss);
        setImg = gameAcObj.findViewById(R.id.player);
        img.add(gameAcObj.findViewById(id.get(0)));
        img.get(0).setX(GameManager.GetScreenWidth() / 2);
        img.get(0).setY(-2500.0f);
        posX.add(img.get(0).getX());
        posY.add(img.get(0).getY());
    }

    //========================================================================================
    // 概要   ： ボスの座標更新処理
    // 仮引数 ： なし
    // 戻り値 ： なし
    //========================================================================================
    public void Move(){
        if(GameManager.GetGameRoundState() == GameManager.GameRoundState.FINALROUND && posY.get(0) <= 0) {
            posY.set(0, posY.get(0) + 5.0f);
            img.get(0).setY(posY.get(0));
            setPosY = img.get(0).getY();
        }

        if(posY.get(0) >= 0) {
            //------------------------------------------------------------------------------------
            // 左右移動処理
            //------------------------------------------------------------------------------------
            posX.set(0, posX.get(0) + 5.0f * moveAround);
            img.get(0).setX(posX.get(0));
            setPosX = img.get(0).getX();

            //------------------------------------------------------------------------------------
            // 反転処理
            //------------------------------------------------------------------------------------
            if (img.get(0).getX() < 0 || img.get(0).getX() + img.get(0).getWidth() > GameManager.GetScreenWidth()) {
                moveAround *= -1;
            }
        }
        setPosX = img.get(0).getX();
        setPosY = img.get(0).getY();

        //------------------------------------------------------------------------------------
        // ボス消滅処理
        //------------------------------------------------------------------------------------
        if(!Collision.GetBossLife()){
            img.get(0).setX(300.0f);
            img.get(0).setY(-2500.0f);
            posX.add(img.get(0).getX());
            posY.add(img.get(0).getY());
        }
    }

    //========================================================================================
    // 概要   ： ボスの座標ゲッター
    // 仮引数 ： なし
    // 戻り値 ： ボスーの座標
    //========================================================================================
    public static ImageView GetImageView(){ return setImg; }
    public static float GetPosX()         { return setPosX; }
    public static float GetPosY()         { return setPosY; }
}
