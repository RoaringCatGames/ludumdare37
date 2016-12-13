package com.roaringcatgames.bunkerjunker;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
    private static final String INTRO_MUSIC = "music/intro-music.mp3";
    private static final String BG_MUSIC = "music/bunker-bg-music.mp3";
    private static final String SMALL_FONT = "fonts/kalibers-24.fnt";
    private static final String LARGE_FONT = "fonts/kalibers-64.fnt";
    private static final String PICKUP_GRUNT = "sfx/pickup-grunt.mp3";
    private static final String THROW_GRUNT = "sfx/throw-grunt.mp3";
    private static final String STAIRS = "sfx/stairs.mp3";

    public static AssetManager load(){
        am = new AssetManager();

        am.load(LOADING_ATLAS, TEXTURE_ATLAS);
        am.load(SPRITE_ATLAS, TEXTURE_ATLAS);
        am.finishLoading();
        am.load(ANI_ATLAS, TEXTURE_ATLAS);
        am.load(INTRO_MUSIC, MUSIC);
        am.load(BG_MUSIC, MUSIC);
        am.load(SMALL_FONT, BITMAP_FONT);
        am.load(LARGE_FONT, BITMAP_FONT);
        am.load(PICKUP_GRUNT, SOUND);
        am.load(THROW_GRUNT, SOUND);
        am.load(STAIRS, SOUND);

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
    public static TextureAtlas.AtlasRegion getRadioRegion(){
        return getCachedRegion(SPRITE_ATLAS, "pickups/radio");
    }

    /////////////////
    //GAME SCREEN
    /////////////////

    public static Music getGameBGMusic(){
        return am.get(BG_MUSIC, MUSIC);
    }

    public static Music getIntroMusic(){
        return am.get(INTRO_MUSIC, MUSIC);
    }

    public static BitmapFont getFont64(){
        return am.get(LARGE_FONT, BITMAP_FONT);
    }

    public static BitmapFont getFont16(){
        return am.get(SMALL_FONT, BITMAP_FONT);
    }

    public static Sound getPickupGruntSfx(){
        return am.get(PICKUP_GRUNT, SOUND);
    }
    public static Sound getThrowGruntSfx(){
        return am.get(THROW_GRUNT, SOUND);
    }
    public static Sound getStairsSfx(){
        return am.get(STAIRS, SOUND);
    }

    public static Array<TextureAtlas.AtlasRegion> getPlayerIdleFrames(){
        return getCachedAnimationFrames(ANI_ATLAS, "Idle/idle-a");
    }

    public static Array<TextureAtlas.AtlasRegion> getPlayerWalkingFrames(){
        return getCachedAnimationFrames(ANI_ATLAS, "walking/walking");
    }
    public static Array<TextureAtlas.AtlasRegion> getPlayerPickupFrames(){
        return getCachedAnimationFrames(ANI_ATLAS, "throw/throw");
    }
    public static Array<TextureAtlas.AtlasRegion> getPlayerThrowingFrames(){
        return getCachedAnimationFrames(ANI_ATLAS, "throw/throw");
    }
    public static Array<TextureAtlas.AtlasRegion> getPlayerBubbleFrames(){
        return getCachedAnimationFrames(ANI_ATLAS, "Idle/idle-b");
    }
    public static Array<TextureAtlas.AtlasRegion> getPlayerHammeringFrames(){
        return getCachedAnimationFrames(ANI_ATLAS, "hammer/hammer");
    }
    public static Array<TextureAtlas.AtlasRegion> getRadioBubbleFrames(){
        return getCachedAnimationFrames(ANI_ATLAS, "speech/meteorspeech");
    }

    public static TextureAtlas.AtlasRegion getHouseRegion(){
        return getCachedRegion(SPRITE_ATLAS, "House");
    }

    public static TextureAtlas.AtlasRegion getHouseARegion(){
        return getCachedRegion(SPRITE_ATLAS, "house-a");
    }
    public static TextureAtlas.AtlasRegion getHouseBRegion(){
        return getCachedRegion(SPRITE_ATLAS, "house-b");
    }
    public static TextureAtlas.AtlasRegion getRoofARegion(){
        return getCachedRegion(SPRITE_ATLAS, "roof-a");
    }
    public static TextureAtlas.AtlasRegion getRoofBRegion(){
        return getCachedRegion(SPRITE_ATLAS, "roof-b");
    }

    public static TextureAtlas.AtlasRegion getBunkerRegion(){
        return getCachedRegion(SPRITE_ATLAS, "bunker");
    }

    public static TextureAtlas.AtlasRegion getFenceRegion(){
        return getCachedRegion(SPRITE_ATLAS, "White-fence");
    }

    public static TextureAtlas.AtlasRegion getBigBush(int version){
        return getCachedRegion(SPRITE_ATLAS, "bush-" + version);
    }

    public static TextureAtlas.AtlasRegion getTimeCylce(String letter){
        return getCachedRegion(SPRITE_ATLAS, "BG_" + letter);
    }
    public static TextureAtlas.AtlasRegion getDirtRegion(){
        return getCachedRegion(SPRITE_ATLAS, "tilabledirt");
    }

    public static TextureAtlas.AtlasRegion getLayeredDirt(){
        return getCachedRegion(SPRITE_ATLAS, "layereddirt");
    }

    public static TextureAtlas.AtlasRegion getBushesRegion(){
        return getCachedRegion(SPRITE_ATLAS, "small-bush");
    }

    public static TextureAtlas.AtlasRegion getGrassRegion(){
        return getCachedRegion(SPRITE_ATLAS, "grass");
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
            case AppConstants.INDICATOR_PICKUP:
                result = getCachedRegion(SPRITE_ATLAS, "grab");
                break;
            case AppConstants.INDICATOR_DROP:
                result = getCachedRegion(SPRITE_ATLAS, "drop");
                break;
            case AppConstants.INDICATOR_THROW:
                result = getCachedRegion(SPRITE_ATLAS, "store");
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


    /////////////////
    // Supplies
    /////////////////

    public static TextureAtlas.AtlasRegion getWaterRegion(){
        return getCachedRegion(SPRITE_ATLAS, "supplies/water");
    }
    public static TextureAtlas.AtlasRegion getDogfoodRegion(){
        return getCachedRegion(SPRITE_ATLAS, "supplies/dogfood");
    }
    public static TextureAtlas.AtlasRegion getCarRegion(){
        return getCachedRegion(SPRITE_ATLAS, "supplies/car");
    }
    public static TextureAtlas.AtlasRegion getTVRegion(){
        return getCachedRegion(SPRITE_ATLAS, "supplies/region");
    }
}
