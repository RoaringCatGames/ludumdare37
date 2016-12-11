package com.roaringcatgames.bunkerjunker.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.roaringcatgames.bunkerjunker.AppConstants;
import com.roaringcatgames.bunkerjunker.Controls;
import com.roaringcatgames.bunkerjunker.Mappers;
import com.roaringcatgames.bunkerjunker.components.*;
import com.roaringcatgames.bunkerjunker.utils.PlayerStateUtil;
import com.roaringcatgames.kitten2d.ashley.K2ComponentMappers;
import com.roaringcatgames.kitten2d.ashley.components.StateComponent;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;
import com.roaringcatgames.kitten2d.ashley.components.VelocityComponent;
import com.roaringcatgames.kitten2d.gdx.helpers.IGameProcessor;

/**
 * Controls the Player Movement
 */
public class PlayerMovementSystem extends IteratingSystem implements InputProcessor{

    private IGameProcessor game;

    private boolean isLeftPressed = false;
    private boolean isRightPressed = false;
    private boolean isUpPressed = false;
    private boolean isDownPressed = false;

    private IAction playerStairToggle;

    private float VELOCITY_X = 15f;
    private float VELOCITY_Y = 15f;

    public PlayerMovementSystem(IGameProcessor game){
        super(Family.all(
                PlayerComponent.class,
                TransformComponent.class,
                StateComponent.class,
                VelocityComponent.class).get());

        this.game = game;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        game.addInputProcessor(this);
    }

    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
        this.game.removeInputProcessor(this);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        PlayerComponent pc = Mappers.player.get(entity);
        if(this.playerStairToggle == null){
            this.playerStairToggle = new PlayerOffStepsAction(pc);
        }

        float x = 0f;
        float y = 0f;

        if(isUpPressed || isDownPressed){
            ImmutableArray<Entity> triggers = getEngine().getEntitiesFor(Family.all(TriggerComponent.class).get());
            for(Entity t:triggers){
                TriggerComponent ttc = Mappers.trigger.get(t);
                boolean couldBeGoingUp = isUpPressed && AppConstants.SENSOR_STAIR_UP.equals(ttc.sensorType);
                boolean couldBeGoingDown = isDownPressed && AppConstants.SENSOR_STAIR_DOWN.equals(ttc.sensorType);

                if((couldBeGoingUp || couldBeGoingDown) && ttc.canBeTriggered && !ttc.isTriggered) {
                    Gdx.app.log("PlayerMovementSystem", "Trigger Triggered!!! ON: " + ttc.onSensorKey + " OFF: " + ttc.offSensorKey);
                    ttc.canBeTriggered = false;
                    ttc.isTriggered = true;
                    pc.moveMode = ttc.onSensorKey.contains("A") ? MovementMode.STAIRS_RL : MovementMode.STAIRS_LR;
                    ttc.endedAction = playerStairToggle;
                }
            }
        }

        if(isMoving()) {
            if (isLeftPressed) {
                x = -VELOCITY_X;
                if(pc.moveMode != MovementMode.LEFT_RIGHT){
                    y = pc.moveMode == MovementMode.STAIRS_RL ? VELOCITY_Y : -VELOCITY_Y;
                }
            }
            if (isRightPressed) {
                x = VELOCITY_X;
                if(pc.moveMode != MovementMode.LEFT_RIGHT){
                    y = pc.moveMode == MovementMode.STAIRS_RL ? -VELOCITY_Y : VELOCITY_Y;
                }
            }

            if(pc.moveMode != MovementMode.LEFT_RIGHT) {
                if (isUpPressed) {
                    x = pc.moveMode == MovementMode.STAIRS_RL ? -VELOCITY_X : VELOCITY_X;
                    y = VELOCITY_Y;
                } else if (isDownPressed) {
                    x = pc.moveMode == MovementMode.STAIRS_RL  ? VELOCITY_X : -VELOCITY_X;
                    y = -VELOCITY_Y;
                }
            }
        }

        VelocityComponent vc = K2ComponentMappers.velocity.get(entity);
        float prevX = vc.speed.x;
        vc.setSpeed(x, y);

        if(Mappers.encumbered.has(entity)){
            EncumberedComponent ec = Mappers.encumbered.get(entity);
            float encumberedAdjust = AppConstants.ENCUMBERENCE_SCALE * (ec.weight/AppConstants.MAX_WEIGHT);
            vc.speed.scl(1f - encumberedAdjust);
        }

        if(vc.speed.x != 0f || vc.speed.y != 0f){
            PlayerStateUtil.setStateIfDifferent(entity, "WALKING", true);
        }

        TransformComponent tc = K2ComponentMappers.transform.get(entity);
        float xScaleMagnitude = Math.abs(tc.scale.x);
        if(x < 0f && prevX >= 0f){
            tc.scale.x = -1 * xScaleMagnitude;
        }else if(x > 0f && prevX <= 0f){
            tc.scale.x = xScaleMagnitude;
        }

        //Allowed to Go Up
        //if(isUpPressed &&)

    }

    private boolean isMoving(){
        return isLeftPressed || isRightPressed || isUpPressed || isDownPressed;
    }

    private void toggleKeyDown(int keycode, boolean isPressed){
        if(AppConstants.inArray(Controls.LEFT_KEYS, keycode)){
            isLeftPressed = isPressed;
        }else if(AppConstants.inArray(Controls.RIGHT_KEYS, keycode)){
            isRightPressed = isPressed;
        }else if(AppConstants.inArray(Controls.UP_KEYS, keycode)){
            isUpPressed = isPressed;
        }else if(AppConstants.inArray(Controls.DOWN_KEYS, keycode)){
            isDownPressed = isPressed;
        }
    }
    @Override
    public boolean keyDown(int keycode) {
        toggleKeyDown(keycode, true);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        toggleKeyDown(keycode, false);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
