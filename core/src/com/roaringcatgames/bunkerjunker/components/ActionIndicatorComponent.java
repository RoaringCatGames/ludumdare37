package com.roaringcatgames.bunkerjunker.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.utils.Pool;

/**
 * Tags an entity as an action indicator
 */
public class ActionIndicatorComponent implements Component, Pool.Poolable {

    public static ActionIndicatorComponent create(Engine engine){
        return engine.createComponent(ActionIndicatorComponent.class);
    }

    @Override
    public void reset() {

    }
}
