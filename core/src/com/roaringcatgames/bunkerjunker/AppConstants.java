package com.roaringcatgames.bunkerjunker;

import com.badlogic.gdx.utils.ObjectMap;

/**
 * Handles some constant values
 */
public class AppConstants {

    public static final float MAX_DELTA_TICK = 1f/30f;
    public static float PPM = 32f;
    public static float W = 30f;
    public static float H = 20f;

    public static final float FIRST_FLOOR_Y = 14.5f;
    public static final float SECOND_FLOOR_Y = 29.5f;
    public static final float BUNKER_LEFT = 3f;
    public static final float BUNKER_RIGHT = 22f;

    public static final float MIN_X_POS = -65f;
    public static final float MAX_X_POS = 65f;
    public static final float MAX_X_ELEVATED_2 = -13f;
    public static final float MAX_X_ELEVATED_1 = -5f;

    public static final float ENCUMBERENCE_SCALE = 0.9f;
    public static final float MAX_WEIGHT = 200f;

    public static final String SENSOR_STAIR_UP = "STAIR_UP";
    public static final String SENSOR_STAIR_DOWN = "STAIR_DOWN";
    public static final String INDICATOR_PICKUP = "PICKUP";
    public static final String INDICATOR_THROW = "THROW";
    public static final String INDICATOR_DROP = "DROP";

    public static final String GAME_BG_MUSIC = "GAME";
    public static final String INTRO_BG_MUSIC = "INTRO";

    public static boolean IsGameEnded = false;

    public static void setAppWH(float w, float h){
        W = w;
        H = h;
    }

    public static boolean inArray(int[] target, int value){
        for(int item:target){
            if(item == value){
                return true;
            }
        }
        return false;
    }

    public static void addStat(String category, int value){
        if(statCounts.containsKey(category)){
            statCounts.put(category, statCounts.get(category) + value);
        }else{
            statCounts.put(category, value);
        }
    }

    public static ObjectMap<String, Integer> getStats(){
        return statCounts;
    }

    public static void clearStats(){
        statCounts.clear();
    }

    private static ObjectMap<String, Integer> statCounts = new ObjectMap<>();

}
