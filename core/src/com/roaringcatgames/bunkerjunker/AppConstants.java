package com.roaringcatgames.bunkerjunker;

/**
 * Handles some constant values
 */
public class AppConstants {

    public static final float MAX_DELTA_TICK = 1f/30f;
    public static float PPM = 32f;
    public static float W = 30f;
    public static float H = 20f;

    public static void setAppWH(float w, float h){
        W = w;
        H = h;
    }
}
