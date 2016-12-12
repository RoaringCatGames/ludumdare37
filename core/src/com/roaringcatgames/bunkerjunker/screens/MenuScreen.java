package com.roaringcatgames.bunkerjunker.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.roaringcatgames.bunkerjunker.AppConstants;
import com.roaringcatgames.bunkerjunker.Assets;
import com.roaringcatgames.kitten2d.ashley.K2ComponentMappers;
import com.roaringcatgames.kitten2d.ashley.components.BoundsComponent;
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

    ////////////////////////
    //User Interactions
    ////////////////////////
    private Vector2 touchPoint = new Vector2(0f, 0f);
    private Entity startGameButton;

    public MenuScreen(IGameProcessor game){
        this.game = game;
        this.world = new World(gravity, true);
    }

    @Override
    public void show() {
        super.show();
        game.addInputProcessor(this);
        game.playBgMusic(AppConstants.INTRO_BG_MUSIC);
    }

    @Override
    public void hide(){
        super.hide();
        game.removeInputProcessor(this);

    }

    @Override
    protected void init() {
        this.engine = new PooledEngine();

        RenderingSystem renderingSystem = new RenderingSystem(game.getBatch(), game.getCamera(), AppConstants.PPM);
        DebugSystem debugRenderingSystem = new DebugSystem(game.getCamera());
        Box2DPhysicsSystem physicsSystem = new Box2DPhysicsSystem(world);
        Box2DPhysicsDebugSystem physicsDebugSystem = new Box2DPhysicsDebugSystem(world, game.getCamera());
        AnimationSystem animationSystem = new AnimationSystem();
        BoundsSystem boundsSystem = new BoundsSystem();
        //Work
        engine.addSystem(physicsSystem);
        engine.addSystem(animationSystem);
        engine.addSystem(boundsSystem);

        //Rendering
        engine.addSystem(renderingSystem);

        //Debugging
        engine.addSystem(debugRenderingSystem);
        engine.addSystem(physicsDebugSystem);

        startGameButton = engine.createEntity();
        startGameButton.add(TransformComponent.create(engine)
                .setPosition(AppConstants.W/2f, AppConstants.H/2f));
        startGameButton.add(TextureComponent.create(engine)
            .setRegion(Assets.getStartGameRegion()));
        startGameButton.add(BoundsComponent.create(engine)
            .setBounds(0f, 0f, 5f, 1f));

        engine.addEntity(startGameButton);
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
        touchPoint.set(screenX, screenY);
        game.getViewport().unproject(touchPoint);

        if(K2ComponentMappers.bounds.has(startGameButton) &&
           K2ComponentMappers.bounds.get(startGameButton).bounds.contains(touchPoint)){
            this.game.switchScreens("GAME");
        }
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
