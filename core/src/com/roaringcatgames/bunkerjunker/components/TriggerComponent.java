package com.roaringcatgames.bunkerjunker.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.utils.Pool;

/**
 * A component that tracks a triggerable action
 */
public class TriggerComponent implements Component, Pool.Poolable {

    public boolean canBeTriggered = false;
    public boolean isTriggered = false;
    public boolean hasLeftOnSensor = false;
    public String offSensorKey = "";
    public String onSensorKey = "";
    public String sensorType = "";

    public IAction endedAction = null;
    public IAction triggeredAction = null;

    public static TriggerComponent create(Engine engine){
        return engine.createComponent(TriggerComponent.class);
    }

    public TriggerComponent setCanBeTriggered(boolean canBe){
        this.canBeTriggered = canBe;
        return this;
    }

    public TriggerComponent setTriggered(boolean isTriggered){
        this.isTriggered = isTriggered;
        return this;
    }

    public TriggerComponent setOffSensorKey(String offKey){
        this.offSensorKey = offKey;
        return this;
    }

    public TriggerComponent setOnSensorKey(String onKey){
        this.onSensorKey = onKey;
        return this;
    }

    public TriggerComponent setSensorType(String sensorType){
        this.sensorType = sensorType;
        return this;
    }

    public TriggerComponent setEndedAction(IAction action){
        this.endedAction = action;
        return this;
    }

    public TriggerComponent setTriggeredAction(IAction action){
        this.triggeredAction = action;
        return this;
    }

    public TriggerComponent setLeftOnSensor(boolean hasLeft){
        this.hasLeftOnSensor = hasLeft;
        return this;
    }

    @Override
    public void reset() {
        canBeTriggered = false;
        isTriggered = false;
        offSensorKey = "";
        onSensorKey = "";
        sensorType = "";
        triggeredAction = null;
        endedAction = null;
    }
}
