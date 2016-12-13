package com.roaringcatgames.bunkerjunker.systems;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.roaringcatgames.bunkerjunker.*;
import com.roaringcatgames.bunkerjunker.components.CameraComponent;
import com.roaringcatgames.bunkerjunker.components.PlayerComponent;
import com.roaringcatgames.kitten2d.ashley.K2ComponentMappers;
import com.roaringcatgames.kitten2d.ashley.K2EntityTweenAccessor;
import com.roaringcatgames.kitten2d.ashley.components.*;
import com.roaringcatgames.kitten2d.gdx.helpers.IGameProcessor;

import java.sql.Time;

/**
 * Created by barry on 12/11/16.
 */
public class IntroSystem extends IntervalSystem implements InputProcessor {
    int intervalCount = 0;

    private Entity player;
    private Entity playerBubble;

    private Entity radio;
    private Entity radioBubble;

    private Entity camera;

    IGameProcessor game;
    public IntroSystem(IGameProcessor game){
        super(1f);
        this.game = game;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        game.addInputProcessor(this);

        float playerX = 3.4f;
        float playerY = -31.4f;
        //Create the Shit!!
        player = engine.createEntity();
        player.add(TransformComponent.create(engine)
            .setPosition(playerX, playerY, Z.player)
            .setScale(-1f, 1f));
        player.add(AnimationComponent.create(engine)
            .addAnimation("DEFAULT", Animations.getPlayerIdle())
            .addAnimation("HAMMERING", Animations.getPlayerHammering())
            .addAnimation("WALKING", Animations.getPlayerWalking()));
        player.add(PlayerComponent.create(engine));
        player.add(StateComponent.create(engine)
            .set("HAMMERING")
            .setLooping(true));
        player.add(TextureComponent.create(engine));
        engine.addEntity(player);

        camera = engine.createEntity();
        camera.add(TransformComponent.create(engine)
            .setPosition(game.getCamera().position.x, game.getCamera().position.y));
        camera.add(CameraComponent.create(engine)
                .setCamera(game.getCamera()));
        camera.add(TweenComponent.create(engine)
            .addTween(Tween.to(camera, K2EntityTweenAccessor.POSITION_XY, 1.5f).target(playerX, playerY+4f)));
        engine.addEntity(camera);


        playerBubble = engine.createEntity();
        playerBubble.add(TransformComponent.create(engine)
            .setPosition(playerX + 1.5f, playerY + 5f, Z.player)
            .setHidden(true));
        playerBubble.add(FollowerComponent.create(engine)
            .setTarget(player)
            .setOffset(1.5f, 5f));
        playerBubble.add(TextureComponent.create(engine));
        playerBubble.add(AnimationComponent.create(engine)
            .addAnimation("DEFAULT", Animations.getPlayerBubbleGrumble())
            .setPaused(false));
        playerBubble.add(StateComponent.create(engine)
            .set("DEFAULT")
            .setLooping(false));
        engine.addEntity(playerBubble);

        float radioX = 14.2f;
        float radioY = -25.0f;
        radio = engine.createEntity();
        radio.add(TransformComponent.create(engine)
            .setPosition(radioX+1f, radioY, Z.supply));
        radio.add(TextureComponent.create(engine)
            .setRegion(Assets.getRadioRegion()));
        engine.addEntity(radio);

        radioBubble = engine.createEntity();
        radioBubble.add(TransformComponent.create(engine)
                .setPosition(radioX + -3f, radioY + 3f, Z.supply)
                .setHidden(true));
        radioBubble.add(TextureComponent.create(engine));
        radioBubble.add(AnimationComponent.create(engine)
                .addAnimation("DEFAULT", Animations.getRadioBubble()));
        radioBubble.add(StateComponent.create(engine)
                .set("DEFAULT")
                .setLooping(true));
        engine.addEntity(radioBubble);

    }

    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
        game.removeInputProcessor(this);
    }

    @Override
    protected void updateInterval() {
        intervalCount += 1;

        Gdx.app.log("INTRO SYSTEM", "Running Intro Interval");

        if(intervalCount == 3){
            TransformComponent tc = K2ComponentMappers.transform.get(radioBubble);
            tc.setHidden(false);
            AnimationComponent ac = K2ComponentMappers.animation.get(radioBubble);
            ac.setPaused(false);
            //TODO: Play Radio gibberish
        }else if(intervalCount == 5){
            StateComponent sc = K2ComponentMappers.state.get(player);
            sc.set("DEFAULT");
            TransformComponent tc = K2ComponentMappers.transform.get(playerBubble);
            tc.setHidden(false);
            AnimationComponent ac = K2ComponentMappers.animation.get(playerBubble);
            ac.setPaused(false);
        }else if(intervalCount == 8f){
            StateComponent sc = K2ComponentMappers.state.get(player);
            sc.set("WALKING");
            TransformComponent ptc = K2ComponentMappers.transform.get(player);
            ptc.setScale(1f, 1f);

            Timeline exitLine = Timeline.createSequence()
                .push(Tween.to(player, K2EntityTweenAccessor.POSITION_X, 0.5f).target(17.75f))
                .push(Tween.to(player, K2EntityTweenAccessor.POSITION_Y, 0.5f).target(-9.35f))
                .push(Tween.to(player, K2EntityTweenAccessor.SCALE, 0.01f).target(-1f, 1f))

                .push(Tween.to(player, K2EntityTweenAccessor.POSITION_XY, 0.5f).target(5.616f, -10.633f))
                .push(Tween.to(player, K2EntityTweenAccessor.POSITION_Y, 0.5f).target(4f))
                .push(Tween.to(player, K2EntityTweenAccessor.SCALE, 0.01f).target(1f, 1f))

                .push(Tween.to(player, K2EntityTweenAccessor.POSITION_X, 0.5f).target(20f))
                .push(Tween.to(player, K2EntityTweenAccessor.SCALE, 0.01f).target(-1f, 1f))

                .push(Tween.to(player, K2EntityTweenAccessor.POSITION_X, 0.5f).target(2f))
                .push(Tween.to(player, K2EntityTweenAccessor.SCALE, 0.01f).target(1f, 1f))

                .push(Tween.to(player, K2EntityTweenAccessor.POSITION_X, 0.5f).target(20))
                .push(Tween.to(player, K2EntityTweenAccessor.SCALE, 0.01f).target(-1f, 1f))

                .push(Tween.to(player, K2EntityTweenAccessor.POSITION_X, 0.5f).target(2f))
                .push(Tween.to(player, K2EntityTweenAccessor.SCALE, 0.01f).target(1f, 1f));
            player.add(TweenComponent.create(getEngine())
                .setTimeline(exitLine));

            camera.add(FollowerComponent.create(getEngine())
                .setTarget(player)
                .setMode(FollowMode.STICKY));
            camera.add(TweenComponent.create(getEngine())
                //.addTween(Tween.to(camera, K2EntityTweenAccessor.POSITION_XY, 1f).target(AppConstants.W/2f, AppConstants.H/2f))
                .addTween(Tween.to(camera, BunkerJunkerTweenAccessor.CAMERA_ZOOM, 1.5f).target(2.5f)));

            TransformComponent tc = K2ComponentMappers.transform.get(playerBubble);
            tc.setHidden(true);
            AnimationComponent ac = K2ComponentMappers.animation.get(playerBubble);
            ac.setPaused(true);
        }else if(intervalCount >= 14){
            this.game.switchScreens("GAME");
        }


    }

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
        Vector2 v = new Vector2(screenX, screenY);
        game.getViewport().unproject(v);
        Gdx.app.log("GAME SCREEN", "Touched Point X: " + v.x + " Y: " + v.y);
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
