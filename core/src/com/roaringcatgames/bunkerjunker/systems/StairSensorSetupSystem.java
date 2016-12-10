package com.roaringcatgames.bunkerjunker.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.math.Vector2;
import com.roaringcatgames.bunkerjunker.AppConstants;
import com.roaringcatgames.bunkerjunker.components.SensorComponent;
import com.roaringcatgames.kitten2d.ashley.components.BoundsComponent;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;

/**
 * A Setup System to create the stair Sensors
 */
public class StairSensorSetupSystem extends EntitySystem {

    private static final float SIZE = 0.5f;

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);

        Vector2 aOnPos = new Vector2(-45f, 0.5f);
        Vector2 aOffPos = new Vector2(-60f, 15.5f);

        Entity aOn = createSensor(engine, "A_UP", "A_DOWN", aOnPos, AppConstants.SENSOR_STAIR_UP);
        engine.addEntity(aOn);
        Entity aOff = createSensor(engine, "A_DOWN", "A_UP", aOffPos, AppConstants.SENSOR_STAIR_DOWN);
        engine.addEntity(aOff);

        Vector2 bOnPos = new Vector2(-30f, 15.5f);
        Vector2 bOffPos = new Vector2(-15f, 30.5f);
        Entity bOn = createSensor(engine, "B_UP", "B_DOWN", bOnPos, AppConstants.SENSOR_STAIR_UP);
        engine.addEntity(bOn);
        Entity bOff = createSensor(engine, "B_DOWN", "B_UP", bOffPos, AppConstants.SENSOR_STAIR_DOWN);
        engine.addEntity(bOff);
    }

    private Entity createSensor(Engine engine, String onName, String offName, Vector2 pos, String sensorType){
        Entity sensor = engine.createEntity();
        sensor.add(TransformComponent.create(engine)
                .setPosition(pos.x, pos.y));
        sensor.add(BoundsComponent.create(engine)
                .setBounds(pos.x - SIZE/2f, pos.y - SIZE/2f, SIZE*2f, SIZE));
        sensor.add(SensorComponent.create(engine)
                .setName(onName)
                .setOffName(offName)
                .setSensorType(sensorType));

        return sensor;
    }
}
