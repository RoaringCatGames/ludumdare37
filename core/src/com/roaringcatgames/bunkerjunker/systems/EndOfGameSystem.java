package com.roaringcatgames.bunkerjunker.systems;

import aurelienribon.tweenengine.Tween;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ObjectMap;
import com.roaringcatgames.bunkerjunker.AppConstants;
import com.roaringcatgames.bunkerjunker.Assets;
import com.roaringcatgames.bunkerjunker.BunkerJunkerTweenAccessor;
import com.roaringcatgames.bunkerjunker.components.CameraComponent;
import com.roaringcatgames.kitten2d.ashley.K2EntityTweenAccessor;
import com.roaringcatgames.kitten2d.ashley.components.*;

/**
 * System for the End of the Game
 */
public class EndOfGameSystem extends IntervalSystem {

    float cameraTargetX = 12.753f;
    float cameraTargetY = -18.2f;

    private Entity camera;
    private Entity meteor;
    private Entity results;
    private Entity resultHeader;

    public EndOfGameSystem(){
        super(1f);
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);

        camera = engine.getEntitiesFor(Family.all(CameraComponent.class).get()).first();
        camera.remove(FollowerComponent.class);
        camera.add(TweenComponent.create(engine)
            .addTween(Tween.to(camera, BunkerJunkerTweenAccessor.CAMERA_ZOOM, 2f).target(2.5f))
            .addTween(Tween.to(camera, K2EntityTweenAccessor.POSITION_XY, 1f).target(cameraTargetX, cameraTargetY)));

        String header = "Prepping Results";
        String stats = "";
        ObjectMap<String, Integer> statvals = AppConstants.getStats();
        for(String key:statvals.keys()){
            stats += key + ": " + statvals.get(key) + "\n";
        }

        if(stats.equals("")){
            header = "You call yourself a Prepper??";
            stats = ":( Nothing Stored :(";
        }

        Entity backResultHeader = engine.createEntity();
        backResultHeader.add(TransformComponent.create(engine)
                .setPosition(AppConstants.W/2f, AppConstants.H-3f, 0f)
                .setTint(Color.BLACK));
        backResultHeader.add(TextComponent.create(engine)
                .setText(header)
                .setFont(Assets.getFont64()));
        engine.addEntity(backResultHeader);

        resultHeader = engine.createEntity();
        resultHeader.add(TransformComponent.create(engine)
                .setPosition(AppConstants.W/2f-0.05f, AppConstants.H-3f-0.05f, 0f)
                .setTint(Color.GOLD));
        resultHeader.add(TextComponent.create(engine)
                .setText(header)
                .setFont(Assets.getFont64()));
        engine.addEntity(resultHeader);


        Entity backResults = engine.createEntity();
        backResults.add(TransformComponent.create(engine)
                .setPosition(AppConstants.W/2f-0.05f, AppConstants.H/2f-0.05f, 0f)
                .setTint(Color.BLACK));
        backResults.add(TextComponent.create(engine)
                .setText(stats)
                .setFont(Assets.getFont16()));
        engine.addEntity(backResults);

        results = engine.createEntity();
        results.add(TransformComponent.create(engine)
            .setPosition(AppConstants.W/2f, AppConstants.H/2f, 0f)
            .setTint(Color.PURPLE));
        results.add(TextComponent.create(engine)
            .setText(stats)
            .setFont(Assets.getFont16()));
        engine.addEntity(results);



    }

    @Override
    protected void updateInterval() {


    }
}
