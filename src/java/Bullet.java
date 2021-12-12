//******************************************************************
// 弾抽象クラス
//******************************************************************
package com.Kamooone.kamoooneshot;

import java.util.ArrayList;
import java.util.List;

public abstract class Bullet extends Char {
    //******************************************************************
    // マクロ定義
    //******************************************************************
    public static final int   FIRE_LIFETIME   = 100;

    //******************************************************************
    // メンバ変数
    //******************************************************************
    protected List<Double>   workLength      = new ArrayList<>();
    protected List<Boolean>  bulletNow       = new ArrayList<>();
    protected List<Integer>  bulletLife      = new ArrayList<>();
    protected List<Integer>  homingStartTime = new ArrayList<>();
    protected int            bulletSpeed     = 0;
    protected boolean        bulletResetFlg  = false;
}
