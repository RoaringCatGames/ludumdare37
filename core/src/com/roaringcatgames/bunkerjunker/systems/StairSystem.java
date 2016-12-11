package com.roaringcatgames.bunkerjunker.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.roaringcatgames.bunkerjunker.Mappers;
import com.roaringcatgames.bunkerjunker.components.ActionIndicatorComponent;
import com.roaringcatgames.bunkerjunker.components.SensorComponent;
import com.roaringcatgames.bunkerjunker.components.TriggerComponent;
import com.roaringcatgames.kitten2d.ashley.K2ComponentMappers;
import com.roaringcatgames.kitten2d.ashley.components.BoundsComponent;
import com.roaringcatgames.kitten2d.ashley.components.TextureComponent;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;

/**
 * System to handle state changes for being able to move up stairs
 */
public class StairSystem extends IteratingSystem {

    private Array<Entity> sensors = new Array<>();
    private Array<Entity> triggers = new Array<>();

    public StairSystem(){
        super(Family.one(SensorComponent.class, TriggerComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        if(Mappers.sensor.has(entity)){
            sensors.add(entity);
        }else{
            triggers.add(entity);
        }
    }

    @Override
    public void update(float deltaTime) {
        sensors.clear();
        triggers.clear();

        super.update(deltaTime);

        for(Entity t:triggers){
            BoundsComponent tbc = K2ComponentMappers.bounds.get(t);
            TriggerComponent ttc = Mappers.trigger.get(t);

            boolean isInSensor = false;
            for(Entity s:sensors){
                BoundsComponent sbc =K2ComponentMappers.bounds.get(s);
                if(sbc.bounds.overlaps(tbc.bounds)){
                    isInSensor = true;
                    SensorComponent sc = Mappers.sensor.get(s);
                    //TRIGGER ACTIVATED!!

                    if(!ttc.canBeTriggered && !ttc.isTriggered){
                        Gdx.app.log("StairSystem", "Trigger Enabled ON: " + sc.name + " OFF: " + sc.offName + " Type: " + sc.sensorType);
                        ttc.hasLeftOnSensor = false;
                        ttc.canBeTriggered = true;

                        ttc.onSensorKey = sc.name;
                        ttc.offSensorKey = sc.offName;
                        ttc.sensorType = sc.sensorType;

                    }else if(sc.name.equals(ttc.offSensorKey) || (ttc.hasLeftOnSensor && sc.name.equals(ttc.onSensorKey))){
                        Gdx.app.log("StairSystem", "Trigger Ended!");
                        //Trigger Ended
                        ttc.canBeTriggered = true;
                        ttc.isTriggered = false;
                        ttc.hasLeftOnSensor = false;
                        ttc.onSensorKey = sc.name;
                        ttc.offSensorKey = sc.offName;
                        ttc.sensorType = sc.sensorType;
                        if(ttc.endedAction != null){
                            ttc.endedAction.run();
                        }
                    }

                    break;
                }
            }

            if(!isInSensor && !ttc.isTriggered && ttc.canBeTriggered){
                ttc.canBeTriggered = false;
            }

            if(!isInSensor){
                ttc.hasLeftOnSensor = true;
            }
        }
    }
}
