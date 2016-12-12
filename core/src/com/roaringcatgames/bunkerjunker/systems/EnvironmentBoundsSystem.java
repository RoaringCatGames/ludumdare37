package com.roaringcatgames.bunkerjunker.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.roaringcatgames.bunkerjunker.AppConstants;
import com.roaringcatgames.bunkerjunker.components.PlayerComponent;
import com.roaringcatgames.kitten2d.ashley.K2ComponentMappers;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;

/**
 * System to run AFTER movement, and clamp player position inside of
 *  determined boundaries.
 */
public class EnvironmentBoundsSystem extends IteratingSystem {

    public EnvironmentBoundsSystem(){
        super(Family.all(PlayerComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent tc = K2ComponentMappers.transform.get(entity);

        float x = tc.position.x;
        float y = tc.position.y;

        tc.position.x = MathUtils.clamp(x, AppConstants.MIN_X_POS, (y >= AppConstants.FIRST_FLOOR_Y && y <= AppConstants.SECOND_FLOOR_Y) ? AppConstants.MAX_X_ELEVATED_1 :
                (y>= AppConstants.SECOND_FLOOR_Y) ? AppConstants.MAX_X_ELEVATED_2 : AppConstants.MAX_X_POS);
    }
}
