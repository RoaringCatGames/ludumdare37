package com.roaringcatgames.bunkerjunker.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.utils.Array;
import com.roaringcatgames.bunkerjunker.Assets;
import com.roaringcatgames.bunkerjunker.Mappers;
import com.roaringcatgames.bunkerjunker.components.ActionIndicatorComponent;
import com.roaringcatgames.bunkerjunker.components.TriggerComponent;
import com.roaringcatgames.kitten2d.ashley.K2ComponentMappers;
import com.roaringcatgames.kitten2d.ashley.components.TextureComponent;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;

/**
 * Handles watching Trigger States to Show Indicators.
 */
public class ActionIndicatorSystem extends IteratingSystem {

    private Array<Entity> actionIndicators = new Array<Entity>();
    private Array<Entity> triggers = new Array<>();

    public ActionIndicatorSystem(){
        super(Family.one(ActionIndicatorComponent.class, TriggerComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        if(Mappers.trigger.has(entity)){
            triggers.add(entity);
        }else{
            K2ComponentMappers.transform.get(entity).setHidden(true);
            actionIndicators.add(entity);
        }
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        for(Entity t:triggers){
            TriggerComponent tc = Mappers.trigger.get(t);
            if(tc.canBeTriggered && !tc.isTriggered){
                for(Entity a:actionIndicators){
                    TransformComponent tfm = K2ComponentMappers.transform.get(a);
                    if(tfm.isHidden) {
                        TextureComponent txc = K2ComponentMappers.texture.get(a);
                        txc.setRegion(Assets.getIndicatorRegion(tc.sensorType));
                        tfm.setHidden(false);
                    }
                }
                break;
            }
        }
    }
}
