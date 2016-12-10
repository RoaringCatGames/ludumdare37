package com.roaringcatgames.bunkerjunker.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
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
 * Screen to Load the Menu
 */
public class MenuScreen extends LazyInitScreen implements InputProcessor{
    private IGameProcessor game;
    private Engine engine;
    private World world;
    private Vector2 gravity = new Vector2(0f, -9.8f);

    public MenuScreen(IGameProcessor game){
        this.game = game;
        this.world = new World(gravity, true);
    }

    @Override
    protected void init() {
        this.engine = new PooledEngine();

        RenderingSystem renderingSystem = new RenderingSystem(game.getBatch(), game.getCamera(), AppConstants.PPM);
        DebugSystem debugRenderingSystem = new DebugSystem(game.getCamera());
        Box2DPhysicsSystem physicsSystem = new Box2DPhysicsSystem(world);
        Box2DPhysicsDebugSystem physicsDebugSystem = new Box2DPhysicsDebugSystem(world, game.getCamera());
        AnimationSystem animationSystem = new AnimationSystem();

        //Work
        engine.addSystem(physicsSystem);
        engine.addSystem(animationSystem);

        //Rendering
        engine.addSystem(renderingSystem);

        //Debugging
        engine.addSystem(debugRenderingSystem);
        engine.addSystem(physicsDebugSystem);

        Gdx.app.log("MenuScreen", "Menu Loaded");

        Entity loadingAnimation = engine.createEntity();
        loadingAnimation.add(TransformComponent.create(engine)
                .setPosition(game.getViewport().getWorldWidth()/2f, game.getViewport().getWorldHeight()/2f));
        loadingAnimation.add(TextureComponent.create(engine));
        loadingAnimation.add(AnimationComponent.create(engine)
                .addAnimation("DEFAULT", Animations.getPlayerIdle()));
        loadingAnimation.add(StateComponent.create(engine)
            .set("DEFAULT")
            .setLooping(true));

        engine.addEntity(loadingAnimation);
    }

    @Override
    protected void update(float deltaChange) {
        engine.update(deltaChange);
    }

    ////////////////////////
    // Input Processor
    ////////////////////////

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
