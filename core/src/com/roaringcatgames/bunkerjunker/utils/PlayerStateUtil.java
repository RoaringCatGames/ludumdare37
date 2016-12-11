package com.roaringcatgames.bunkerjunker.utils;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.roaringcatgames.kitten2d.ashley.K2ComponentMappers;
import com.roaringcatgames.kitten2d.ashley.components.StateComponent;

/**
 * Utility to manipulate Player State
 */
public class PlayerStateUtil {

    public static void setStateIfDifferent(Entity player, String stateName, boolean isLooped){
        StateComponent sc = K2ComponentMappers.state.get(player);
        if(!sc.get().equals(stateName)) {
            Gdx.app.log("PlayerStateUtil", "Setting State to: " + stateName + " From: " + sc.get());
            sc.setLooping(isLooped);
            sc.set(stateName);
        }
    }
}
