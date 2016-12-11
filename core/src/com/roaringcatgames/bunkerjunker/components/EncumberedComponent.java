package com.roaringcatgames.bunkerjunker.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * Component to hold data that could encumber User
 */
public class EncumberedComponent implements Component, Poolable {

    public float weight = 0f;

    public static EncumberedComponent create(Engine engine){
        return engine.createComponent(EncumberedComponent.class);
    }

    public EncumberedComponent setWeight(float weight){
        this.weight = weight;
        return this;
    }

    @Override
    public void reset() {
        this.weight = 0f;
    }
}
