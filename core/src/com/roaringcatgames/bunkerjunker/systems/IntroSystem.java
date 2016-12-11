package com.roaringcatgames.bunkerjunker.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.roaringcatgames.bunkerjunker.Animations;
import com.roaringcatgames.bunkerjunker.AppConstants;
import com.roaringcatgames.bunkerjunker.Assets;
import com.roaringcatgames.bunkerjunker.Z;
import com.roaringcatgames.bunkerjunker.components.CameraComponent;
import com.roaringcatgames.kitten2d.ashley.components.*;
import com.roaringcatgames.kitten2d.gdx.helpers.IGameProcessor;

/**
 * Created by barry on 12/11/16.
 */
public class IntroSystem extends IntervalSystem {
    int intervalCount = 0;

    private Entity player;
    private Entity playerBubble;

    private Entity radio;
    private Entity radioBubble;

    IGameProcessor game;
    public IntroSystem(IGameProcessor game){
        super(10f);
        this.game = game;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);

        float playerX = AppConstants.BUNKER_LEFT + 8f;
        float playerY = -20f;
        //Create the Shit!!
        player = engine.createEntity();
        player.add(TransformComponent.create(engine)
            .setPosition(playerX, playerY, Z.player));
        player.add(AnimationComponent.create(engine)
            .addAnimation("DEFAULT", Animations.getPlayerIdle())
            .addAnimation("HAMMERING", Animations.getPlayerThrow()));
        player.add(StateComponent.create(engine)
            .set("HAMMERING")
            .setLooping(true));
        player.add(TextureComponent.create(engine));
        player.add(CameraComponent.create(engine)
            .setCamera(game.getCamera()));
        engine.addEntity(player);



        playerBubble = engine.createEntity();
        playerBubble.add(TransformComponent.create(engine)
            .setPosition(playerX + 3f, playerY + 3f, Z.player)
            .setHidden(false));
        playerBubble.add(FollowerComponent.create(engine)
            .setTarget(player)
            .setOffset(3f, 3f));
        playerBubble.add(TextureComponent.create(engine));
        playerBubble.add(AnimationComponent.create(engine)
            .addAnimation("DEFAULT", Animations.getPlayerBubbleGrumble())
            .setPaused(false));
        playerBubble.add(StateComponent.create(engine)
            .set("DEFAULT")
            .setLooping(false));
        engine.addEntity(playerBubble);

        radio = engine.createEntity();
        radio.add(TransformComponent.create(engine)
            .setPosition(AppConstants.BUNKER_LEFT +1f, -14f, Z.supply));
        radio.add(TextureComponent.create(engine)
            .setRegion(Assets.getRadioRegion()));
        engine.addEntity(radio);

        radioBubble = engine.createEntity();
        radioBubble.add(TransformComponent.create(engine)
                .setPosition(AppConstants.BUNKER_LEFT + 1f + 2f, -12f, Z.supply)
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
    protected void updateInterval() {
        intervalCount += 1;


    }
}
