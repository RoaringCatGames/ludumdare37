package com.roaringcatgames.bunkerjunker.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.roaringcatgames.bunkerjunker.Animations;
import com.roaringcatgames.bunkerjunker.AppConstants;
import com.roaringcatgames.bunkerjunker.Assets;
import com.roaringcatgames.kitten2d.ashley.components.AnimationComponent;
import com.roaringcatgames.kitten2d.ashley.components.StateComponent;
import com.roaringcatgames.kitten2d.ashley.components.TextureComponent;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;
import com.roaringcatgames.kitten2d.ashley.systems.*;
import com.roaringcatgames.kitten2d.gdx.helpers.IGameProcessor;
import com.roaringcatgames.kitten2d.gdx.screens.LazyInitScreen;

/**
 * Screen To Render the Loading Animation
 */
public class LoadingScreen extends LazyInitScreen {
    private IGameProcessor game;
    private Engine engine;
    private float elapsedTime = 0f;
    private float minSplashSeconds = 3f;

    public LoadingScreen(IGameProcessor game){
        this.game = game;
    }

    @Override
    protected void init() {
        engine = new PooledEngine();
        RenderingSystem render = new RenderingSystem(game.getBatch(), game.getCamera(), AppConstants.PPM);

        engine.addSystem(new AnimationSystem());
        engine.addSystem(render);
        Vector2 minBounds = new Vector2(0f, 0f);
        Vector2 maxBounds = new Vector2(AppConstants.W, AppConstants.H);
        engine.addSystem(new MovementSystem());
        engine.addSystem(new RotationSystem());
        engine.addSystem(new FollowerSystem(Family.all(AnimationComponent.class).get()));

        Entity loadingAnimation = engine.createEntity();
        loadingAnimation.add(TransformComponent.create(engine)
            .setPosition(game.getViewport().getWorldWidth()/2f, game.getViewport().getWorldHeight()/2f));
        loadingAnimation.add(TextureComponent.create(engine));
        loadingAnimation.add(AnimationComponent.create(engine)
            .addAnimation("DEFAULT", new Animation(1f/6f, Assets.getSplashScreenFrames(), Animation.PlayMode.NORMAL)));
        loadingAnimation.add(StateComponent.create(engine)
            .set("DEFAULT"));

        engine.addEntity(loadingAnimation);
        //Add Loading Animation

    }

    @Override
    protected void update(float delta) {
        elapsedTime += delta;

        if(Assets.am.update() && elapsedTime >= minSplashSeconds){
            Gdx.app.log("Splash Screen", "Assets are Loaded!");
            Animations.init();
            game.switchScreens("MENU");
        }else {
            engine.update(delta);
        }
    }
}
