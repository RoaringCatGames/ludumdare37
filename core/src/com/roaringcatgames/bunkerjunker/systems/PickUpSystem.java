package com.roaringcatgames.bunkerjunker.systems;

import aurelienribon.tweenengine.Tween;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.roaringcatgames.bunkerjunker.AppConstants;
import com.roaringcatgames.bunkerjunker.Controls;
import com.roaringcatgames.bunkerjunker.Mappers;
import com.roaringcatgames.bunkerjunker.Z;
import com.roaringcatgames.bunkerjunker.components.PlayerComponent;
import com.roaringcatgames.bunkerjunker.components.SupplyComponent;
import com.roaringcatgames.bunkerjunker.utils.Box2DUtil;
import com.roaringcatgames.kitten2d.ashley.K2ComponentMappers;
import com.roaringcatgames.kitten2d.ashley.K2EntityTweenAccessor;
import com.roaringcatgames.kitten2d.ashley.components.*;
import com.roaringcatgames.kitten2d.gdx.helpers.IGameProcessor;

/**
 * System to handle picking up supplies
 */
public class PickUpSystem extends IteratingSystem implements InputProcessor{

    private float bunkerLeft, bunkerRight;

    private IGameProcessor game;
    private World world;

    private Entity player;
    private Entity currentSupply;
    private Array<Entity> supplies = new Array<>();

    private boolean isPickupPressed = false;

    public PickUpSystem(IGameProcessor game, World world, float bunkerLeft, float bunkerRight){
        super(Family.one(PlayerComponent.class, SupplyComponent.class).get());
        this.game = game;
        this.world = world;
        this.bunkerLeft = bunkerLeft;
        this.bunkerRight = bunkerRight;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        this.game.addInputProcessor(this);
    }

    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
        this.game.addInputProcessor(this);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        if(Mappers.supply.has(entity)){
            supplies.add(entity);
            if(K2ComponentMappers.follower.has(entity)){
                currentSupply = entity;
            }
        }else{
            player = entity;
        }
    }

    @Override
    public void update(float deltaTime) {
        supplies.clear();
        currentSupply = null;
        super.update(deltaTime);

        //Do Work
        if(isPickupPressed){
            if(currentSupply != null){
                Gdx.app.log("Pickup System", "Dropping Supply");
                dropCurrentSupply();
                currentSupply = null;
            }else{
                Gdx.app.log("Pickup System", "PICKING UP Supply");
                pickupNearSupply();
            }
            isPickupPressed = false;
        }
    }

    private void pickupNearSupply() {
        //If anything to Pickup
        BoundsComponent bc = K2ComponentMappers.bounds.get(player);
        for(Entity supply:supplies){
            BoundsComponent sbc = K2ComponentMappers.bounds.get(supply);
            if(bc.bounds.overlaps(sbc.bounds)){
                // 1. set player Animation
                StateComponent sc = K2ComponentMappers.state.get(player);
                sc.set("PICKUP");
                sc.setLooping(false);

                //2. Rotate supply if flagged to do so
                if(Mappers.supply.get(supply).isRotatedOnPickup) {
                    supply.add(RotationComponent.create(getEngine())
                            .setHasTargetRotation(true)
                            .setTargetRotation(90f)
                            .setRotationSpeed(180f));

                }
                // 3. add Follower Component
                supply.add(FollowerComponent.create(getEngine())
                        .setTarget(player)
                        .setMatchParentRotation(false)
                        .setMode(FollowMode.MOVETOSTICKY)
                        .setFollowSpeed(20f)
                        .setOffset(0f, bc.bounds.height/2f + sbc.bounds.height/2f));

                //4. Bring forward
                supply.add(TweenComponent.create(getEngine())
                    .addTween(Tween.to(supply, K2EntityTweenAccessor.POSITION_Z, 0.5f)
                        .target(Z.carriedSupply)));

                break;
            }
        }
    }

    private void dropCurrentSupply() {
        //Drop Supply
        // 1. remove follower Component
        currentSupply.remove(FollowerComponent.class);

        // 2. set player animation
        StateComponent sc = K2ComponentMappers.state.get(player);
        sc.set("PICKUP");
        sc.setLooping(false);

        // 3. Set Velocity of Supply down (maybe tween to ground?)
        TransformComponent tc = K2ComponentMappers.transform.get(currentSupply);
        if(!isOnBunkerTarget(tc.position.x)) {
            float bottomY = getMinYFromCurrentPosition(tc.position.y);
            BoundsComponent bc = K2ComponentMappers.bounds.get(currentSupply);
            float targetY = bottomY + bc.bounds.height/2f;

            Gdx.app.log("PickupSystem", "Target Y for Drop: " + targetY + " BottomY: " + bottomY);

            currentSupply.add(TweenComponent.create(getEngine())
                .addTween(Tween.to(currentSupply, K2EntityTweenAccessor.POSITION, 0.25f)
                        .target(tc.position.x, targetY, Z.supply)));
        }else{
            //TODO: Add Body Component and let it FALL
            Gdx.app.log("PickupSystem", "Is On Bunker Target");
            BoundsComponent bc = K2ComponentMappers.bounds.get(currentSupply);
            SupplyComponent spc = Mappers.supply.get(currentSupply);
            BodyComponent bdyc = BodyComponent.create(getEngine())
                    .setBody(Box2DUtil.buildBoxBody(world, false, tc.position.x, tc.position.y,
                                                    bc.bounds.width, bc.bounds.height, tc.rotation, spc.weight, 0.5f, 0.5f));
            currentSupply.add(bdyc);

            //TRACK the Stats!!
            for(String key:spc.categoryWeights.keys()){
                AppConstants.addStat(key, spc.categoryWeights.get(key));
            }
        }
    }

    private float getMinYFromCurrentPosition(float y){
        if(y < AppConstants.FIRST_FLOOR_Y){
            y = 0f;
        }else if(y < AppConstants.SECOND_FLOOR_Y){
            y = AppConstants.FIRST_FLOOR_Y;
        }else {
            y = AppConstants.SECOND_FLOOR_Y;
        }

        return y;
    }

    private boolean isOnBunkerTarget(float x){
        return (x >= bunkerLeft && x <= bunkerRight);
    }

    private void setPickupPressed(int keycode, boolean isPressed){
        if(AppConstants.inArray(Controls.PICKUP_KEYS, keycode)){
            isPickupPressed = isPressed;
        }
    }
    @Override
    public boolean keyDown(int keycode) {
        setPickupPressed(keycode, true);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        setPickupPressed(keycode, false);
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
