package com.roaringcatgames.bunkerjunker.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.utils.Pool;

/**
 * Component to represent the state of a sensor that can be used for triggerable events
 */
public class SensorComponent implements Component, Pool.Poolable {

    public String name = "";
    public String offName = "";
    public String sensorType = "";

    public static SensorComponent create(Engine engine){
        return engine.createComponent(SensorComponent.class);
    }

    public SensorComponent setName(String name){
        this.name = name;
        return this;
    }

    public SensorComponent setOffName(String offName){
        this.offName = offName;
        return this;
    }

    public SensorComponent setSensorType(String sType){
        this.sensorType = sType;
        return this;
    }

    @Override
    public void reset() {
        name = "";
        offName = "";
        sensorType = "";
    }
}
