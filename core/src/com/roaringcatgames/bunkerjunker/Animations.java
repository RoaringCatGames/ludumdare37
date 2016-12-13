package com.roaringcatgames.bunkerjunker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g3d.particles.ResourceData;

public class Animations {

    private static Animation playerIdle;
    private static Animation playerWalking;
    private static Animation playerPickup;
    private static Animation playerThrow;
    private static Animation playerHammering;

    private static Animation playerBubbleGrumble;
    private static Animation radioBubble;

    public static void init(){
        Gdx.app.log("ANIMATIONS INIT", "Splash Screen has " + Assets.getSplashScreenFrames().size + " frames");
        playerIdle = new Animation(1f/16f, Assets.getPlayerIdleFrames(), Animation.PlayMode.LOOP);
        playerWalking = new Animation(1f/16f, Assets.getPlayerWalkingFrames(), Animation.PlayMode.LOOP);
        playerPickup = new Animation(1f/7f, Assets.getPlayerPickupFrames(), Animation.PlayMode.NORMAL);
        playerThrow = new Animation(1f/4f, Assets.getPlayerThrowingFrames(), Animation.PlayMode.NORMAL);
        playerBubbleGrumble = new Animation(1f/6f, Assets.getPlayerBubbleFrames(), Animation.PlayMode.NORMAL);
        radioBubble = new Animation(1f/3f, Assets.getRadioBubbleFrames(), Animation.PlayMode.LOOP);
        playerHammering = new Animation(1f/8f, Assets.getPlayerHammeringFrames(), Animation.PlayMode.LOOP_PINGPONG);
    }

    public static Animation getPlayerIdle(){
        return playerIdle;
    }

    public static Animation getPlayerWalking() {
        return playerWalking;
    }

    public static Animation getPlayerPickup() {
        return playerPickup;
    }

    public static Animation getPlayerThrow() {
        return playerThrow;
    }

    public static Animation getPlayerBubbleGrumble() {
        return playerBubbleGrumble;
    }

    public static Animation getRadioBubble() {
        return radioBubble;
    }

    public static Animation getPlayerHammering() {
        return playerHammering;
    }
}
