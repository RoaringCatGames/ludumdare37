package com.roaringcatgames.bunkerjunker;

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
    public static final float BUNKER_LEFT = 10f;
    public static final float BUNKER_RIGHT = 15f;

    public static final String SENSOR_STAIR_UP = "STAIR_UP";
    public static final String SENSOR_STAIR_DOWN = "STAIR_DOWN";


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

}
