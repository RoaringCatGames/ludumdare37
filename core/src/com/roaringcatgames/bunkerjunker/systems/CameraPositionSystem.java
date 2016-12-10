package com.roaringcatgames.bunkerjunker.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.roaringcatgames.bunkerjunker.Mappers;
import com.roaringcatgames.bunkerjunker.components.CameraComponent;
import com.roaringcatgames.kitten2d.ashley.K2ComponentMappers;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;

/**
 * Will update the position of the camera based on the the camera entity position.
 */
public class CameraPositionSystem extends IteratingSystem {

    public CameraPositionSystem(){
        super(Family.all(CameraComponent.class, TransformComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent tc = K2ComponentMappers.transform.get(entity);
        CameraComponent cc = Mappers.camera.get(entity);

        cc.cam.position.set(tc.position);
    }
}
