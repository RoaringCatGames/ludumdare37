package com.roaringcatgames.bunkerjunker.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.Pool;

/**
 * Supply Component to handle attributes of a Supply Item
 */
public class SupplyComponent implements Component, Pool.Poolable {

    public ObjectMap<String, Integer> categoryWeights = new ObjectMap<>();
    public float weight = 1f;

    public static SupplyComponent create(Engine engine){
        return engine.createComponent(SupplyComponent.class);
    }

    public SupplyComponent addCategoryWeight(String category, int weight){
        categoryWeights.put(category, weight);
        return this;
    }

    public SupplyComponent setWeight(float itemWeight){
        this.weight = itemWeight;
        return this;
    }

    @Override
    public void reset() {
        categoryWeights.clear();
        weight = 1f;
    }
}
