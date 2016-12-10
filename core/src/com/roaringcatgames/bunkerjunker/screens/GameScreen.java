package com.roaringcatgames.bunkerjunker.screens;

import aurelienribon.tweenengine.equations.Back;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.roaringcatgames.bunkerjunker.AppConstants;
import com.roaringcatgames.bunkerjunker.components.PlayerComponent;
import com.roaringcatgames.bunkerjunker.systems.*;
import com.roaringcatgames.kitten2d.ashley.systems.*;
import com.roaringcatgames.kitten2d.gdx.helpers.IGameProcessor;
import com.roaringcatgames.kitten2d.gdx.screens.LazyInitScreen;

/**
 * THe screen that runs the actual game.
 */
public class GameScreen extends LazyInitScreen implements InputProcessor {
    private IGameProcessor game;
    private Engine engine;
    private World world;
    private Vector2 gravity = new Vector2(0f, -9.8f);

    private final float ZOOM_SPEED = 0.05f;
    private final float MAX_ZOOM = 1.8f;
    private final float MIN_ZOOM = 0.75f;

    public GameScreen(IGameProcessor game) {
        this.game = game;
        this.world = new World(gravity, true);
    }

    @Override
    public void show() {
        super.show();
        game.addInputProcessor(this);
    }

    @Override
    public void hide() {
        super.hide();
        game.removeInputProcessor(this);
    }

    @Override
    protected void init() {
        this.engine = new PooledEngine();

        //Initializer Systems
        BackgroundSetupSystem backgroundSetupSystem = new BackgroundSetupSystem();
        DebugGridSystem debugGridSystem = new DebugGridSystem(AppConstants.W * 5f, AppConstants.H * 5f, 2f);
        PlayerSetupSystem playerSetupSystem = new PlayerSetupSystem(game, AppConstants.W/2f, 4f);
        StairSensorSetupSystem stairSetupSystem = new StairSensorSetupSystem();

        RenderingSystem renderingSystem = new RenderingSystem(game.getBatch(), game.getCamera(), AppConstants.PPM);
        DebugSystem debugRenderingSystem = new DebugSystem(game.getCamera());
        Box2DPhysicsSystem physicsSystem = new Box2DPhysicsSystem(world);
        Box2DPhysicsDebugSystem physicsDebugSystem = new Box2DPhysicsDebugSystem(world, game.getCamera());

        AnimationSystem animationSystem = new AnimationSystem();
        MovementSystem movementSystem = new MovementSystem();
        BoundsSystem boundsSystem = new BoundsSystem();
        FollowerSystem followerSystem = new FollowerSystem(Family.all(PlayerComponent.class).get());
        TweenSystem tweenSystem = new TweenSystem();

        PlayerMovementSystem playerMovementSystem = new PlayerMovementSystem(game);
        CameraPositionSystem cameraPositionSystem = new CameraPositionSystem();
        StairSystem stairSystem = new StairSystem();
        ActionIndicatorSystem actionIndicatorSystem = new ActionIndicatorSystem();
        EnvironmentBoundsSystem environmentBoundsSystem = new EnvironmentBoundsSystem();

        //Required Setup
        engine.addSystem(tweenSystem);

        //Setup
        engine.addSystem(backgroundSetupSystem);
        engine.addSystem(debugGridSystem);
        engine.addSystem(playerSetupSystem);
        engine.addSystem(stairSetupSystem);

        //Work
        engine.addSystem(physicsSystem);
        engine.addSystem(animationSystem);
        engine.addSystem(movementSystem);
        engine.addSystem(followerSystem);
        engine.addSystem(playerMovementSystem);
        engine.addSystem(boundsSystem);
        engine.addSystem(cameraPositionSystem);
        engine.addSystem(stairSystem);
        engine.addSystem(actionIndicatorSystem);

        //Adjustment Systems
        engine.addSystem(environmentBoundsSystem);

        //Rendering
        engine.addSystem(renderingSystem);

        //Debugging
        engine.addSystem(debugRenderingSystem);
        engine.addSystem(physicsDebugSystem);

        Gdx.app.log("MenuScreen", "Menu Loaded");
    }

    @Override
    protected void update(float deltaChange) {
        float deltaToApply = Math.min(deltaChange, AppConstants.MAX_DELTA_TICK);
        engine.update(deltaToApply);
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
        float zoom = this.game.getCamera().zoom;
        zoom += amount* ZOOM_SPEED;
        this.game.getCamera().zoom = MathUtils.clamp(zoom, MIN_ZOOM, MAX_ZOOM);

        Gdx.app.log("MENU SCREEN", "Scrolled " + amount + " Zoom Result " + this.game.getCamera().zoom);
        return false;
    }
}
