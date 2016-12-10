package com.roaringcatgames.bunkerjunker.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.roaringcatgames.bunkerjunker.Animations;
import com.roaringcatgames.bunkerjunker.components.CameraComponent;
import com.roaringcatgames.bunkerjunker.components.PlayerComponent;
import com.roaringcatgames.kitten2d.ashley.components.*;
import com.roaringcatgames.kitten2d.gdx.helpers.IGameProcessor;

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
                .setPosition(startPositionX, startPositionY));
        player.add(TextureComponent.create(engine));
        player.add(PlayerComponent.create(engine));
        player.add(VelocityComponent.create(engine));
        player.add(AnimationComponent.create(engine)
                .addAnimation("DEFAULT", Animations.getPlayerIdle()));
        player.add(StateComponent.create(engine)
                .set("DEFAULT")
                .setLooping(true));

        engine.addEntity(player);

        Entity camera = engine.createEntity();
        camera.add(TransformComponent.create(engine)
            .setPosition(game.getCamera().position.x, game.getCamera().position.y));
        camera.add(CameraComponent.create(engine)
            .setCamera(game.getCamera()));
        camera.add(FollowerComponent.create(engine)
            .setTarget(player)
            .setOffset(0f, 6f)
            .setMode(FollowMode.STICKY));
        engine.addEntity(camera);
    }

    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
    }
}
