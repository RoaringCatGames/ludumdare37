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

        SupplyComponent tmp = addSupplies(engine, 30f, 3f, 2f, 5f, Assets.getTreeRegion(), 100f);
        tmp.addCategoryWeight(Supplies.COMFORT, 2);
        tmp.addCategoryWeight(Supplies.ENVIRONMENT, 20);

        tmp = addSupplies(engine, -45f, 1.5f, 1f, 1f, Assets.getWaterRegion(), 5f);
        tmp.addCategoryWeight(Supplies.SURVIVAL, 20);
        tmp.addCategoryWeight(Supplies.ENVIRONMENT, -1);

        tmp = addSupplies(engine, -30f, AppConstants.SECOND_FLOOR_Y + 1f, 0.5f, 0.5f, Assets.getDogfoodRegion(), 1f);
        tmp.addCategoryWeight(Supplies.SURVIVAL, 2);
        tmp.addCategoryWeight(Supplies.HEART, 3);

        tmp = addSupplies(engine, AppConstants.MAX_X_POS-5f, 2f, 5f, 3f, Assets.getCarRegion(), AppConstants.MAX_WEIGHT);
        tmp.addCategoryWeight(Supplies.ENTERTAINMENT, 100);
        tmp.addCategoryWeight(Supplies.HOARDINESS, 20);

        tmp = addSupplies(engine, -59.92f, 36.13f, 4f, 3.95f, Assets.getTVRegion(), 60f);
        tmp.addCategoryWeight(Supplies.ENTERTAINMENT, 20);
        tmp.addCategoryWeight(Supplies.HOARDINESS, 10);
        tmp.addCategoryWeight(Supplies.COMFORT, 10);

        tmp = addSupplies(engine, -36.72553f, 34.755905f, 6f, 3.99f, Assets.getBrownCouch(), 90f);
        tmp.addCategoryWeight(Supplies.COMFORT, 20);
        tmp.addCategoryWeight(Supplies.HOARDINESS, 10);

        tmp = addSupplies(engine, -35.178654f, 20.74028f, 6f, 3.99f, Assets.getBlueCouch(), 95f);
        tmp.addCategoryWeight(Supplies.COMFORT, 20);
        tmp.addCategoryWeight(Supplies.HOARDINESS, 10);

        tmp = addSupplies(engine, -63.209904f, 18.86528f, 2f, 2f, Assets.getLamp(), 15f);
        tmp.addCategoryWeight(Supplies.COMFORT, 10);
        tmp.addCategoryWeight(Supplies.HOARDINESS, 20);
        tmp.addCategoryWeight(Supplies.SURVIVAL, 1);

        tmp = addSupplies(engine, -17.096996f, 18.1f, 2f, 2f, Assets.getPan(), 10f);
        tmp.addCategoryWeight(Supplies.HOARDINESS, 20);
        tmp.addCategoryWeight(Supplies.SURVIVAL, 8);

        tmp = addSupplies(engine, -30.746994f, 3.54999f, 2f, 3.5f, Assets.getTable(), 110f);
        tmp.addCategoryWeight(Supplies.HOARDINESS, 20);
        tmp.addCategoryWeight(Supplies.COMFORT, 20);

        tmp = addSupplies(engine, -7f, 20.5f, 1.5f, 4f, Assets.getPartnerRegion(), 90f);
        tmp.addCategoryWeight(Supplies.HEART, 1000);
        tmp.addCategoryWeight(Supplies.SURVIVAL, 5);
        tmp.addCategoryWeight(Supplies.ENTERTAINMENT, 40);
    }


    private SupplyComponent addSupplies(Engine engine, float x, float y, float w, float h, TextureRegion tr, float weight){
        Entity supply = engine.createEntity();
        supply.add(TransformComponent.create(engine)
                .setPosition(x, y, Z.supply));
        supply.add(BoundsComponent.create(engine)
                .setBounds(0f, 0f, w, h));
        supply.add(TextureComponent.create(engine)
                .setRegion(tr));
        SupplyComponent sc = SupplyComponent.create(engine)
                .setWeight(weight)
                .setRotatedOnPickup(h >= 4f);
        supply.add(sc);
        engine.addEntity(supply);

        return sc;
    }
}
