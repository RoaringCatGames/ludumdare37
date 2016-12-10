package com.roaringcatgames.bunkerjunker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;

public class Animations {

    private static Animation playerIdle;

    public static void init(){
        Gdx.app.log("ANIMATIONS INIT", "Splash Screen has " + Assets.getSplashScreenFrames().size + " frames");
        playerIdle = new Animation(1f/16f, Assets.getPlayerIdleFrames(), Animation.PlayMode.LOOP);
    }

    public static Animation getPlayerIdle(){
        return playerIdle;
    }
}
