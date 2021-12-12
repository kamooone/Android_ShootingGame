//******************************************************************
// キャラ抽象クラス
//******************************************************************
package com.Kamooone.kamoooneshot;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public abstract class Char {
    //******************************************************************
    // メンバ変数
    //******************************************************************
    protected List<ImageView> img             = new ArrayList<>();
    protected List<Integer>   id              = new ArrayList<>();
    protected List<Float>     posX            = new ArrayList<>();
    protected List<Float>     posY            = new ArrayList<>();
    protected List<Double>    directionX      = new ArrayList<>();
    protected List<Double>    directionY      = new ArrayList<>();
    protected GameActivity    gameAcObj;
    protected int    Life;

    //******************************************************************
    // メソッド
    //******************************************************************
    abstract protected  void Init();
    abstract protected  void Move();
}
