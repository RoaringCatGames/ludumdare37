package com.roaringcatgames.bunkerjunker.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.roaringcatgames.bunkerjunker.components.PlayerComponent;
import com.roaringcatgames.bunkerjunker.utils.PlayerStateUtil;
import com.roaringcatgames.kitten2d.ashley.K2ComponentMappers;
import com.roaringcatgames.kitten2d.ashley.components.AnimationComponent;
import com.roaringcatgames.kitten2d.ashley.components.StateComponent;
import com.roaringcatgames.kitten2d.ashley.components.VelocityComponent;

/**
 * Manage auto switching between animation states.
 */
public class PlayerAnimationSystem extends IteratingSystem {

    public PlayerAnimationSystem(){
        super(Family.all(PlayerComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        StateComponent sc = K2ComponentMappers.state.get(entity);
        AnimationComponent ac = K2ComponentMappers.animation.get(entity);
        VelocityComponent vc = K2ComponentMappers.velocity.get(entity);

        if(sc.get().equals("WALKING") && vc.speed.x == 0f){
            PlayerStateUtil.setStateIfDifferent(entity, "DEFAULT", true);
        }else if(!sc.get().equals("DEFAULT") || !sc.get().equals("WALKING")){
            if(!sc.isLooping && ac.animations.get(sc.get()).isAnimationFinished(sc.time)){
                Gdx.app.log("PlayerANimationSystem","FINISHED ANIMATING STATE:   "  + sc.get());
                if(vc.speed.x != 0f){
                    PlayerStateUtil.setStateIfDifferent(entity, "WALKING", true);
                }else{
                    PlayerStateUtil.setStateIfDifferent(entity, "DEFAULT", true);
                }
            }
        }
    }
}
