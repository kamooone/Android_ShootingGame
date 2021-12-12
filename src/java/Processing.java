package com.Kamooone.kamoooneshot;

import android.widget.ImageView;

import java.util.List;

public class Processing {

    //========================================================================================
    // 概要   ： Xの中心座標を取得
    // 仮引数 ： オブジェクトの中心座標X(空)、オブジェクトのx座標、オブジェクト、要素数
    // 戻り値 ： オブジェクトの中心座標X(配列)
    //========================================================================================
    static List<Float> GetTheCenterCoordinates_X(List<Float> centerX, List<Float> x,
                                                 List<ImageView> img, int MAX_VALUE){
        for(int i = 0; i < MAX_VALUE; i++) {
            centerX.set(i, x.get(i) + img.get(i).getWidth() / 2);
        }
        return centerX;
    }

    //========================================================================================
    // 概要   ： Xの中心座標を取得(オーバーロード)
    // 仮引数 ： オブジェクトの中心座標X(空)、オブジェクトのx座標、オブジェクト、要素数
    // 戻り値 ： オブジェクトの中心座標X(変数)
    //========================================================================================
    static float GetTheCenterCoordinates_X(float x, ImageView img){
        float centerX = x + img.getWidth() / 2;
        return centerX;
    }

    //========================================================================================
    // 概要   ： Yの中心座標を取得
    // 仮引数 ： オブジェクトの中心座標Y(空)、オブジェクトのY座標、オブジェクト、要素数
    // 戻り値 ： オブジェクトの中心座標Y(配列)
    //========================================================================================
    static List<Float> GetTheCenterCoordinates_Y(List<Float> centerY, List<Float> y,
                                                 List<ImageView> img, int MAX_VALUE){
        for(int i = 0; i < MAX_VALUE; i++) {
            centerY.set(i, y.get(i) + img.get(i).getHeight() / 2);
        }
        return centerY;
    }

    //========================================================================================
    // 概要   ： Yの中心座標を取得(オーバーロード)
    // 仮引数 ： オブジェクトの中心座標Y(空)、オブジェクトのY座標、オブジェクト、要素数
    // 戻り値 ： オブジェクトの中心座標Y(変数)
    //========================================================================================
    static float GetTheCenterCoordinates_Y(float y, ImageView img){
        float centerY = y + img.getHeight() / 2;
        return centerY;
    }

    //========================================================================================
    // 概要   ： ベクトルを取得
    // 仮引数 ： ターゲットオブジェクトの座標、もう一方のオブジェクトの座標
    // 戻り値 ： ベクトル
    //========================================================================================
    // X軸のベクトル取得
    static float GetVector(float targetPos, float pos){
        float vecX = targetPos - pos;
        return vecX;
    }

    //========================================================================================
    // 概要   ： 二つのオブジェクト間の距離を取得
    // 仮引数 ： 二つのオブジェクトに対するベクトルXとベクトルY
    // 戻り値 ： 二つのオブジェクト間の距離
    //========================================================================================
    static double GetLength(float vecX, float vecY){
        double length = Math.sqrt((vecX * vecX) + (vecY * vecY));
        return length;
    }

    //========================================================================================
    // 概要   ： 二つのオブジェクトの半径の和を取得
    // 仮引数 ： ターゲットテクスチャオブジェクト、もう一方のテクスチャオブジェクト
    // 戻り値 ： 二つのオブジェクトの半径の和
    //========================================================================================
    static float AddRadius(List<ImageView> target, List<ImageView> my){
        float radius = target.get(0).getWidth() / 2 + my.get(0).getWidth() / 2;
        return radius;
    }
    // プレイヤーとエネミーの弾の当たり判定は小さめに設定
    static float AddRadius(ImageView target, List<ImageView> my) {
        float radius = 0;
        if (!GameManager.GetBossBattleFlg()) {
            radius = target.getWidth() / 4 + my.get(0).getWidth() / 6;
        }
        if (GameManager.GetBossBattleFlg()) {
            radius = target.getWidth() / 2;
        }
        return radius;
    }
    static float AddRadius(ImageView target, ImageView my){
        float radius = target.getWidth() / 3.5f + my.getWidth() / 1.5f;
        return radius;
    }
}
