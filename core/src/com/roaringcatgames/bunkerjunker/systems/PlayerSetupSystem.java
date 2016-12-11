package com.roaringcatgames.bunkerjunker.systems;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.roaringcatgames.bunkerjunker.Animations;
import com.roaringcatgames.bunkerjunker.BunkerJunkerTweenAccessor;
import com.roaringcatgames.bunkerjunker.Z;
import com.roaringcatgames.bunkerjunker.components.ActionIndicatorComponent;
import com.roaringcatgames.bunkerjunker.components.CameraComponent;
import com.roaringcatgames.bunkerjunker.components.PlayerComponent;
import com.roaringcatgames.bunkerjunker.components.TriggerComponent;
import com.roaringcatgames.kitten2d.ashley.K2EntityTweenAccessor;
import com.roaringcatgames.kitten2d.ashley.components.*;
import com.roaringcatgames.kitten2d.gdx.helpers.IGameProcessor;

import java.sql.Time;

/**
 * System to create the Player Setup
 * NOTE: This system owns creating the Camera COMPONENT and tying
 *  it too the player.
 */
public class PlayerSetupSystem extends EntitySystem {

    private IGameProcessor game;
    private float startPositionX, startPositionY;

    public PlayerSetupSystem(IGameProcessor game, float startX, float startY){
        this.game = game;
        this.startPositionX = startX;
        this.startPositionY = startY;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);

        Entity player = engine.createEntity();
        player.add(TransformComponent.create(engine)
                .setPosition(startPositionX, startPositionY, Z.player));
        player.add(BoundsComponent.create(engine)
            .setBounds(0f, 0f, 2f, 7.5f));
        player.add(TextureComponent.create(engine));
        player.add(PlayerComponent.create(engine));
        player.add(VelocityComponent.create(engine));
        player.add(AnimationComponent.create(engine)
                .addAnimation("DEFAULT", Animations.getPlayerIdle()));
        player.add(StateComponent.create(engine)
                .set("DEFAULT")
                .setLooping(true));

        engine.addEntity(player);

        final Entity actionIndicator = engine.createEntity();
        actionIndicator.add(TransformComponent.create(engine)
            .setHidden(true));
        actionIndicator.add(ActionIndicatorComponent.create(engine));
        actionIndicator.add(TextureComponent.create(engine));
        actionIndicator.add(TweenComponent.create(engine)
            .addTween(Tween.from(actionIndicator, K2EntityTweenAccessor.SCALE, 0.25f)
                .target(1.2f, 1.2f)
                .repeatYoyo(Tween.INFINITY, 0f)));
        actionIndicator.add(FollowerComponent.create(engine)
            .setMode(FollowMode.STICKY)
            .setTarget(player)
            .setOffset(0f, startPositionY + 5f));

        engine.addEntity(actionIndicator);

        Entity footTrigger = engine.createEntity();
        footTrigger.add(TransformComponent.create(engine)
            .setPosition(startPositionX, startPositionY - 3.5f));
        footTrigger.add(FollowerComponent.create(engine)
            .setTarget(player)
            .setOffset(0f, -3.5f)
            .setMode(FollowMode.STICKY));
        footTrigger.add(TriggerComponent.create(engine));
        footTrigger.add(BoundsComponent.create(engine)
            .setBounds(startPositionX, startPositionY-3.5f, 0.5f, 0.5f));
        engine.addEntity(footTrigger);

        Entity camera = engine.createEntity();
        camera.add(TransformComponent.create(engine)
            .setPosition(game.getCamera().position.x, game.getCamera().position.y));
        camera.add(CameraComponent.create(engine)
            .setCamera(game.getCamera()));
        camera.add(FollowerComponent.create(engine)
            .setTarget(player)
            .setOffset(0f, 6f)
            .setFollowSpeed(14.5f)
            .setMode(FollowMode.MOVETO));
        camera.add(TweenComponent.create(engine)
            .addTween(Tween.to(camera, BunkerJunkerTweenAccessor.CAMERA_ZOOM, 1f)
                .target(1.5f)));
        engine.addEntity(camera);
    }

    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
    }
}
