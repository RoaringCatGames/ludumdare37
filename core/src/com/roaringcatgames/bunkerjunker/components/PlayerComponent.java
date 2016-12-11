package com.roaringcatgames.bunkerjunker.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.utils.Pool;

/**
 * Component to tag a player component
 */
public class PlayerComponent implements Component, Pool.Poolable{

    public MovementMode moveMode = MovementMode.LEFT_RIGHT;

    public static PlayerComponent create(Engine engine){
        return engine.createComponent(PlayerComponent.class);
    }

    public PlayerComponent setOnStairs(MovementMode mode){
        this.moveMode = mode;
        return this;
    }

    @Override
    public void reset() {
        moveMode = MovementMode.LEFT_RIGHT;
    }
}
