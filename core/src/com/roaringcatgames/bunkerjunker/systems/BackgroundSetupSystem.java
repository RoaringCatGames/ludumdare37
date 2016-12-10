package com.roaringcatgames.bunkerjunker.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.roaringcatgames.bunkerjunker.Assets;
import com.roaringcatgames.kitten2d.ashley.components.TextureComponent;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;

/**
 * Builds all of the Background Sprites and entities
 */
public class BackgroundSetupSystem extends EntitySystem {

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);

        Entity house = engine.createEntity();
        house.add(TransformComponent.create(engine)
            .setPosition(-30f, 21f, 100f));
        house.add(TextureComponent.create(engine)
            .setRegion(Assets.getHouseRegion()));

        engine.addEntity(house);
    }
}
