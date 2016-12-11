package com.roaringcatgames.bunkerjunker.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ObjectMap;
import com.roaringcatgames.bunkerjunker.AppConstants;
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

        addSupplies(engine, 20f, 3f, 2f, 5f, Assets.getTreeRegion(), 100f);
        addSupplies(engine, -45f, 0.5f, 1f, 1f, Assets.getWaterRegion(), 5f);
        addSupplies(engine, -30f, AppConstants.SECOND_FLOOR_Y + 0.25f, 0.5f, 0.5f, Assets.getDogfoodRegion(), 1f);
        addSupplies(engine, AppConstants.MAX_X_POS-5f, 2f, 5f, 3f, Assets.getCarRegion(), AppConstants.MAX_WEIGHT);
    }


    private void addSupplies(Engine engine, float x, float y, float w, float h, TextureRegion tr, float weight){
        Entity supply = engine.createEntity();
        supply.add(TransformComponent.create(engine)
                .setPosition(x, y, Z.supply));
        supply.add(BoundsComponent.create(engine)
                .setBounds(0f, 0f, w, h));
        supply.add(TextureComponent.create(engine)
                .setRegion(tr));
        supply.add(SupplyComponent.create(engine)
                .setWeight(weight)
                .setRotatedOnPickup(h >= 4f)
                .addCategoryWeight(Supplies.COMFORT, 2)
                .addCategoryWeight(Supplies.ENVIRONMENT, 20));
        engine.addEntity(supply);
    }
}
