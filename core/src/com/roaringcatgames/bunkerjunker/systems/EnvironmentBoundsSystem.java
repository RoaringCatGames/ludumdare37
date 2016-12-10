package com.roaringcatgames.bunkerjunker.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.roaringcatgames.bunkerjunker.components.PlayerComponent;
import com.roaringcatgames.kitten2d.ashley.K2ComponentMappers;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;

/**
 * System to run AFTER movement, and clamp player position inside of
 *  determined boundaries.
 */
public class EnvironmentBoundsSystem extends IteratingSystem {

    private float MIN_X_POS = -65f;
    private float MAX_X_POS = 65f;
    private float MAX_X_ELEVATED = 0f;

    public EnvironmentBoundsSystem(){
        super(Family.all(PlayerComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent tc = K2ComponentMappers.transform.get(entity);

        float x = tc.position.x;
        float y = tc.position.y;

        tc.position.x = MathUtils.clamp(x, MIN_X_POS, (y >= 10f) ? MAX_X_ELEVATED : MAX_X_POS);
    }
}
