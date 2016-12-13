package com.roaringcatgames.bunkerjunker.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.roaringcatgames.bunkerjunker.AppConstants;
import com.roaringcatgames.bunkerjunker.BunkerJunkerTweenAccessor;
import com.roaringcatgames.bunkerjunker.components.PlayerComponent;
import com.roaringcatgames.bunkerjunker.systems.*;
import com.roaringcatgames.kitten2d.ashley.systems.*;
import com.roaringcatgames.kitten2d.gdx.helpers.IGameProcessor;
import com.roaringcatgames.kitten2d.gdx.screens.LazyInitScreen;

/**
 * Intro CutScene Screen
 */
public class IntroScreen extends LazyInitScreen {
    private IGameProcessor game;
    private Engine engine;
    private World world;
    private Vector2 gravity = new Vector2(0f, -9.8f);

    private final float ZOOM_SPEED = 0.1f;
    private final float MAX_ZOOM = 4.8f;
    private final float MIN_ZOOM = 0.75f;

    public IntroScreen(IGameProcessor game) {
        this.game = game;
        this.world = new World(gravity, true);
    }

    @Override
    protected void init() {
        this.engine = new PooledEngine();

        //Initializer Systems
        BackgroundSetupSystem backgroundSetupSystem = new BackgroundSetupSystem(world);
//        DebugGridSystem debugGridSystem = new DebugGridSystem(AppConstants.W * 5f, AppConstants.H * 5f, 2f);
        SuppliesSetupSystem suppliesSetupSystem = new SuppliesSetupSystem();
        //PlayerSetupSystem playerSetupSystem = new PlayerSetupSystem(game, AppConstants.W/2f, 4f);
        //StairSensorSetupSystem stairSetupSystem = new StairSensorSetupSystem();

        RenderingSystem renderingSystem = new RenderingSystem(game.getBatch(), game.getCamera(), AppConstants.PPM);
        TextRenderingSystem textRenderingSystem = new TextRenderingSystem(game.getBatch(), game.getGUICamera(), game.getCamera());
//        DebugSystem debugRenderingSystem = new DebugSystem(game.getCamera());
        Box2DPhysicsSystem physicsSystem = new Box2DPhysicsSystem(world);
//        Box2DPhysicsDebugSystem physicsDebugSystem = new Box2DPhysicsDebugSystem(world, game.getCamera());

        AnimationSystem animationSystem = new AnimationSystem();
        MovementSystem movementSystem = new MovementSystem();
        BoundsSystem boundsSystem = new BoundsSystem();
        FollowerSystem followerSystem = new FollowerSystem(Family.all(PlayerComponent.class).get());
        RotationSystem rotationSystem = new RotationSystem();
        TweenSystem tweenSystem = new TweenSystem(new BunkerJunkerTweenAccessor());

        //TimerSystem timerSystem = new TimerSystem(60f*4f);
        //PlayerMovementSystem playerMovementSystem = new PlayerMovementSystem(game);
        CameraPositionSystem cameraPositionSystem = new CameraPositionSystem();
        //StairSystem stairSystem = new StairSystem();
        //ActionIndicatorSystem actionIndicatorSystem = new ActionIndicatorSystem();
        //EnvironmentBoundsSystem environmentBoundsSystem = new EnvironmentBoundsSystem();
        //PickUpSystem pickUpSystem = new PickUpSystem(this.game, world, AppConstants.BUNKER_LEFT, AppConstants.BUNKER_RIGHT);
        //PlayerAnimationSystem playerAnimationSystem = new PlayerAnimationSystem();

        //Required Setup
        engine.addSystem(tweenSystem);

        //Setup
        engine.addSystem(backgroundSetupSystem);
        engine.addSystem(suppliesSetupSystem);
        engine.addSystem( new IntroSystem(game));


        //Work
        engine.addSystem(physicsSystem);
        engine.addSystem(animationSystem);
        engine.addSystem(rotationSystem);
        engine.addSystem(movementSystem);
        engine.addSystem(followerSystem);
        engine.addSystem(boundsSystem);
        engine.addSystem(cameraPositionSystem);
        //engine.addSystem(pickUpSystem);


        //Rendering
        engine.addSystem(renderingSystem);
        engine.addSystem(textRenderingSystem);

        //Debugging
//        engine.addSystem(debugGridSystem);
//        engine.addSystem(debugRenderingSystem);
//        engine.addSystem(physicsDebugSystem);

        Gdx.app.log("MenuScreen", "Menu Loaded");
        game.playBgMusic(AppConstants.INTRO_BG_MUSIC);

        //game.getCamera().zoom = 2.8f;
    }

    @Override
    protected void update(float deltaChange) {
        float deltaToApply = Math.min(deltaChange, AppConstants.MAX_DELTA_TICK);
        engine.update(deltaToApply);
    }
}