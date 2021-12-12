package com.Kamooone.kamoooneshot;

import android.widget.ImageView;

public class Player extends Char  {
    //******************************************************************
    // 変数定義
    //******************************************************************
    private static float setPosX, setPosY;
    private static ImageView setImg;

    //========================================================================================
    // 概要   ： プレイヤーの初期化処理(必要分のListを生成)
    // 仮引数 ： なし
    // 戻り値 ： なし
    //========================================================================================
    public void Init(){
        gameAcObj = GameActivity.GetThisObj();
        id.add(R.id.player);
        setImg = gameAcObj.findViewById(R.id.player);
        img.add(gameAcObj.findViewById(id.get(0)));
        img.get(0).setX(GameManager.GetScreenWidth() / 2.3f);
        img.get(0).setY(GameManager.GetScreenHeight() / 2.7f);
        posX.add(img.get(0).getX());
        posY.add(GameManager.GetScreenHeight() / 1.3f);
    }

    //========================================================================================
    // 概要   ： プレイヤーの座標更新処理
    // 仮引数 ： なし
    // 戻り値 ： なし
    //========================================================================================
    public void Move() {
        if (Collision.GetPlayerLife()) {
            ImageView getImg = Player.GetImageView();
            img.get(0).setX(GameManager.GetPointX() - getImg.getWidth() / 2);
            img.get(0).setY(GameManager.GetPointY() - getImg.getHeight() * 1.5f);
            posX.set(0, img.get(0).getX());
            posY.set(0, img.get(0).getY());
            setPosX = posX.get(0);
            setPosY = posY.get(0);
        }
        if(!Collision.GetPlayerLife()){
            img.get(0).setX(GameManager.INIT_POS);
            img.get(0).setY(GameManager.INIT_POS);
            posX.set(0, img.get(0).getX());
            posY.set(0, img.get(0).getY());
            setPosX = posX.get(0);
            setPosY = posY.get(0);
        }
    }

    //========================================================================================
    // 概要   ： プレイヤーの座標ゲッター
    // 仮引数 ： なし
    // 戻り値 ： プレイヤーの座標
    //========================================================================================
    public static ImageView GetImageView(){ return setImg; }
    public static float GetPosX(){
        return setPosX;
    }
    public static float GetPosY(){
        return setPosY;
    }
}
