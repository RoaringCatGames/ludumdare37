package com.roaringcatgames.bunkerjunker.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.roaringcatgames.bunkerjunker.AppConstants;
import com.roaringcatgames.bunkerjunker.Assets;
import com.roaringcatgames.bunkerjunker.Z;
import com.roaringcatgames.bunkerjunker.utils.Box2DUtil;
import com.roaringcatgames.kitten2d.ashley.components.BodyComponent;
import com.roaringcatgames.kitten2d.ashley.components.TextureComponent;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;

import javax.swing.*;

/**
 * Builds all of the Background Sprites and entities
 */
public class BackgroundSetupSystem extends EntitySystem {

    private World world;

    public BackgroundSetupSystem(World world){
        this.world = world;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);

        Entity house = engine.createEntity();
        house.add(TransformComponent.create(engine)
            .setPosition(-35f, 22.78f, Z.rooms));
        house.add(TextureComponent.create(engine)
            .setRegion(Assets.getHouseRegion()));
        engine.addEntity(house);

        Entity bunker = engine.createEntity();
        float x = AppConstants.BUNKER_LEFT + (AppConstants.BUNKER_RIGHT-AppConstants.BUNKER_LEFT)/2f;
        bunker.add(TransformComponent.create(engine)
            .setPosition(x, -18.25f, Z.rooms));
        bunker.add(TextureComponent.create(engine)
            .setRegion(Assets.getBunkerRegion()));
        engine.addEntity(bunker);

//        Entity leftTop = buildBasicBox(engine, AppConstants.BUNKER_LEFT, -4f, 1f, 5f);
//        engine.addEntity(leftTop);
//        Entity rightTop = buildBasicBox(engine, AppConstants.BUNKER_RIGHT, -4f, 1f, 5f);
//        engine.addEntity(rightTop);

        //Entity
        //Entity leftTop = buildBasicBox(engine, AppConstants.BUNKER_LEFT, -5f, 1f, 10f);
        Entity ballToTest = buildBallEntity(engine, x, 0f, 2f, 5f, 0.5f, 0.2f);
        engine.addEntity(ballToTest);
        Entity chainOutline = buildChainOutline(engine, bunkerXVals, bunkerYVals, 1f, 0.5f, 0.3f);
        engine.addEntity(chainOutline);
    }

    private Entity buildChainOutline(Engine engine, float[] xVals, float[] yVals, float density, float friction, float restitution){
        Entity e = engine.createEntity();
        BodyComponent bc = new BodyComponent();
        bc.body = Box2DUtil.buildChainBody(world, xVals, yVals, density, friction, restitution);
        e.add(bc);

        return e;
    }

    private Entity buildBallEntity(Engine engine, float x, float y, float radius, float density, float friction, float restitution){
        Entity e = engine.createEntity();

        TransformComponent tfc = TransformComponent.create(engine)
                .setPosition(x, y, 1f)
                .setRotation(15f)
                .setScale(0.25f, 0.25f);
        e.add(tfc);

        BodyComponent bc = BodyComponent.create(engine);
        bc.body = Box2DUtil.buildBallBody(world, false, x, y, radius, density, friction, restitution);

        e.add(bc);
        return e;
    }

    private Entity buildBasicBox(Engine engine, float x, float y, float w, float h){
        Entity e = engine.createEntity();
        BodyComponent bc = new BodyComponent();
        bc.body = Box2DUtil.buildBoxBody(world, true, x, y, w, h, 0f, 0f, 0.5f, 0.5f);
        e.add(bc);

        return e;
    }


    private float[] bunkerXVals = new float[]{
            3.25281f,
            3.0135764f,
            3.0135765f,
            3.252821f,
            3.1331997f,
            1.3388596f,
            -0.09661484f,
            -1.1732159f,
            -2.2498207f,
            -3.3264256f,
            -3.4460506f,
            -3.685295f,
            -3.565672f,
            -2.8083267f,
            -1.2749958f,
            2.1750011f,
            6.2000036f,
            12.716668f,
            19.999998f,
            23.408329f,
            26.0f,
            27.666666f,
            28.81666f,
            28.624996f,
            27.47499f,
            26.133335f,
            23.641663f,
            21.916668f,
            21.916668f
    };

    private float[] bunkerYVals = new float[]{
            -0.35275555f,
            -2.8661413f,
            -5.379527f,
            -8.611024f,
            -9.927556f,
            -11.363778f,
            -13.159053f,
            -15.433072f,
            -17.946457f,
            -21.177952f,
            -23.691341f,
            -25.845669f,
            -27.52126f,
            -30.82499f,
            -33.508324f,
            -35.424988f,
            -35.424988f,
            -35.424988f,
            -35.424988f,
            -35.14375f,
            -34.6f,
            -31.783321f,
            -26.41666f,
            -22.77499f,
            -18.558327f,
            -15.874995f,
            -11.8499975f,
            -10.124995f,
            -0.7333f
    };
}
