package com.roaringcatgames.bunkerjunker;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * Quick Access to our Assets
 */
public class Assets {

    public static Class<TextureAtlas> TEXTURE_ATLAS = TextureAtlas.class;
    public static Class<Music> MUSIC = Music.class;
    public static Class<BitmapFont> BITMAP_FONT = BitmapFont.class;
    public static Class<Sound> SOUND = Sound.class;


    public static AssetManager am;
    private static final String LOADING_ATLAS = "animations/loading.atlas";
    private static final String ANI_ATLAS = "animations/animations.atlas";
    private static final String SPRITE_ATLAS = "sprites/sprites.atlas";


    public static AssetManager load(){
        am = new AssetManager();

        am.load(LOADING_ATLAS, TEXTURE_ATLAS);
        am.load(SPRITE_ATLAS, TEXTURE_ATLAS);
        am.finishLoading();
        am.load(ANI_ATLAS, TEXTURE_ATLAS);

        return am;
    }

    private static ObjectMap<String, Array<TextureAtlas.AtlasRegion>> animationsCache = new ObjectMap<>();
    private static ObjectMap<String, TextureAtlas.AtlasRegion> spriteCache = new ObjectMap<>();

    private static Array<TextureAtlas.AtlasRegion> getCachedAnimationFrames(String atlasName, String name){
        if(!animationsCache.containsKey(name)){
            animationsCache.put(name, am.get(atlasName, TEXTURE_ATLAS).findRegions(name));
        }
        return animationsCache.get(name);
    }

    private static TextureAtlas.AtlasRegion getCachedRegion(String atlasName, String name){
        if(!spriteCache.containsKey(name)){
            spriteCache.put(name, am.get(atlasName, TEXTURE_ATLAS).findRegion(name));
        }
        return spriteCache.get(name);
    }

    /////////////////
    //LOADING SCREEN
    /////////////////
    public static Array<TextureAtlas.AtlasRegion> getSplashScreenFrames(){
        return getCachedAnimationFrames(LOADING_ATLAS, "loading");
    }

    /////////////////
    //MENU SCREEN
    /////////////////
    public static TextureAtlas.AtlasRegion getStartGameRegion(){
        return getCachedRegion(SPRITE_ATLAS, "menu/playgame");
    }

    /////////////////
    //GAME SCREEN
    /////////////////
    public static Array<TextureAtlas.AtlasRegion> getPlayerIdleFrames(){
        return getCachedAnimationFrames(ANI_ATLAS, "player");
    }

    public static TextureAtlas.AtlasRegion getHouseRegion(){
        return getCachedRegion(SPRITE_ATLAS, "house");
    }

    public static TextureAtlas.AtlasRegion getIndicatorRegion(String sensorType){

        TextureAtlas.AtlasRegion result;
        switch (sensorType){
            case AppConstants.SENSOR_STAIR_DOWN:
                result = getCachedRegion(SPRITE_ATLAS, "down");
                break;
            case AppConstants.SENSOR_STAIR_UP:
                result = getCachedRegion(SPRITE_ATLAS, "up");
                break;
            default:
                result = getCachedRegion(SPRITE_ATLAS, "tree");
                break;
        }

        return result;
    }

    public static TextureAtlas.AtlasRegion getTreeRegion() {
        return getCachedRegion(SPRITE_ATLAS, "tree");
    }
}
