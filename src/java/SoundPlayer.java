package com.Kamooone.kamoooneshot;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class SoundPlayer {
    // SE用
    private static SoundPool soundPool;
    private static int hitSound;

    // BGM用
    private static MediaPlayer mediaPlayer, mediaPlayer1;

    public void Init(Context context) {
        // SE用
        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        hitSound = soundPool.load(context, R.raw.hit, 1);

        // BGM用
        mediaPlayer = MediaPlayer.create(context,R.raw.bgm1);
        mediaPlayer.setLooping(true);//    ループ設定
        mediaPlayer.setVolume(1.0f, 1.0f);

        mediaPlayer1 = MediaPlayer.create(context,R.raw.bgm2);
        mediaPlayer1.setLooping(true);//    ループ設定
        mediaPlayer1.setVolume(1.0f, 1.0f);
    }

    // BGM再生＆停止
    public static void BGM1Start() { mediaPlayer.start(); }
    public static void BGM1Stop() { SoundPlayer.mediaPlayer.stop(); }
    public static void BGM2Start() { mediaPlayer1.start(); }
    public static void BGM2Stop() { SoundPlayer.mediaPlayer1.stop(); }

    // SE再生
    public static void playHitSound() {
        soundPool.play(hitSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    // 解放
    public static void Release(){
        soundPool.release();
        mediaPlayer.release();
        mediaPlayer1.release();
    }
}
