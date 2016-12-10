package com.roaringcatgames.bunkerjunker.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.roaringcatgames.bunkerjunker.components.PlayerComponent;
import com.roaringcatgames.kitten2d.ashley.K2ComponentMappers;
import com.roaringcatgames.kitten2d.ashley.components.StateComponent;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;
import com.roaringcatgames.kitten2d.ashley.components.VelocityComponent;
import com.roaringcatgames.kitten2d.gdx.helpers.IGameProcessor;

/**
 * Controls the Player Movement
 */
public class PlayerMovementSystem extends IteratingSystem implements InputProcessor{

    private int[] LEFT_KEYS = new int[] {Input.Keys.LEFT, Input.Keys.A};
    private int[] RIGHT_KEYS = new int[] {Input.Keys.RIGHT, Input.Keys.D};
    private int[] UP_KEYS = new int[] {Input.Keys.UP, Input.Keys.W};
    private int[] DOWN_KEYS = new int[] {Input.Keys.DOWN, Input.Keys.S};

    private IGameProcessor game;

    private boolean isLeftPressed = false;
    private boolean isRightPressed = false;
    private boolean isUpPressed = false;
    private boolean isDownPressed = false;

    private float VELOCITY_X = 10f;
    private float VELOCITY_Y = 8f;

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

        float x = 0f;
        if(isMoving()) {
            if (isLeftPressed) {
                x = -VELOCITY_X;
            }
            if (isRightPressed) {
                x = VELOCITY_X;
            }
        }
        VelocityComponent vc = K2ComponentMappers.velocity.get(entity);
        float prevX = vc.speed.x;
        vc.setSpeed(x, vc.speed.y);

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


    private boolean inArray(int[] target, int value){
        for(int item:target){
            if(item == value){
                return true;
            }
        }
        return false;
    }

    private void toggleKeyDown(int keycode, boolean isPressed){
        if(inArray(LEFT_KEYS, keycode)){
            isLeftPressed = isPressed;
        }else if(inArray(RIGHT_KEYS, keycode)){
            isRightPressed = isPressed;
        }else if(inArray(UP_KEYS, keycode)){
            isUpPressed = isPressed;
        }else if(inArray(DOWN_KEYS, keycode)){
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
