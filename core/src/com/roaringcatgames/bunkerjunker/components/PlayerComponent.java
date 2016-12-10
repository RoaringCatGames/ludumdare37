package com.roaringcatgames.bunkerjunker.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.utils.Pool;

/**
 * Component to tag a player component
 */
public class PlayerComponent implements Component, Pool.Poolable{

    public static PlayerComponent create(Engine engine){
        return engine.createComponent(PlayerComponent.class);
    }

    @Override
    public void reset() {

    }
}
