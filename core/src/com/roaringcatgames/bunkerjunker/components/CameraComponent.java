package com.roaringcatgames.bunkerjunker.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.utils.Pool;

/**
 * Tracks a Camera
 */
public class CameraComponent implements Component, Pool.Poolable {
    public Camera cam;

    public static CameraComponent create(Engine engine){
        return engine.createComponent(CameraComponent.class);
    }

    public CameraComponent setCamera(Camera cam){
        this.cam = cam;
        return this;
    }


    @Override
    public void reset() {
        this.cam = null;
    }
}
