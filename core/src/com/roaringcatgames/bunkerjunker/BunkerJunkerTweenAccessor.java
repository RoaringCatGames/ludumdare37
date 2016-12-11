package com.roaringcatgames.bunkerjunker;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.Map;
import com.roaringcatgames.bunkerjunker.components.CameraComponent;
import com.roaringcatgames.bunkerjunker.systems.CameraPositionSystem;
import com.roaringcatgames.kitten2d.ashley.K2EntityTweenAccessor;

/**
 * Created by barry on 12/11/16.
 */
public class BunkerJunkerTweenAccessor extends K2EntityTweenAccessor {
    public static final int CAMERA_ZOOM = 14;

    @Override
    public int getValues(Entity entity, int tweenType, float[] returnValues) {
        int result = 0;
        switch(tweenType){
            case CAMERA_ZOOM:
                if(Mappers.camera.has(entity)){
                    CameraComponent cc = Mappers.camera.get(entity);
                    returnValues[0] = ((OrthographicCamera)cc.cam).zoom;
                    result = 1;
                }
                break;
            default:
                result = super.getValues(entity, tweenType, returnValues);
                break;
        }

        return result;
    }

    @Override
    public void setValues(Entity entity, int tweenType, float[] newValues) {
        switch(tweenType){
            case CAMERA_ZOOM:
                if(Mappers.camera.has(entity)){
                    CameraComponent cc = Mappers.camera.get(entity);
                    ((OrthographicCamera)cc.cam).zoom = newValues[0];

                }
                break;
            default:
                super.setValues(entity, tweenType, newValues);
                break;
        }
    }
}
