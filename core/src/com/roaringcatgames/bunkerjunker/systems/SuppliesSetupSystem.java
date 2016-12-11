package com.roaringcatgames.bunkerjunker.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.roaringcatgames.bunkerjunker.Assets;
import com.roaringcatgames.bunkerjunker.Supplies;
import com.roaringcatgames.bunkerjunker.Z;
import com.roaringcatgames.bunkerjunker.components.SupplyComponent;
import com.roaringcatgames.kitten2d.ashley.components.BoundsComponent;
import com.roaringcatgames.kitten2d.ashley.components.TextureComponent;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;

/**
 * Setup System to generate all of the available supplies
 */
public class SuppliesSetupSystem extends EntitySystem{

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);

        Entity tree = engine.createEntity();
        tree.add(TransformComponent.create(engine)
            .setPosition(10f, 3f, Z.supply));
        tree.add(BoundsComponent.create(engine)
            .setBounds(0f, 0f, 2f, 3f));
        tree.add(TextureComponent.create(engine)
            .setRegion(Assets.getTreeRegion()));
        tree.add(SupplyComponent.create(engine)
            .setWeight(3f)
            .addCategoryWeight(Supplies.COMFORT, 2));
        engine.addEntity(tree);
    }
}
