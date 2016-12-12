package com.roaringcatgames.bunkerjunker.systems;

import aurelienribon.tweenengine.Tween;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.roaringcatgames.bunkerjunker.AppConstants;
import com.roaringcatgames.bunkerjunker.Assets;
import com.roaringcatgames.kitten2d.ashley.K2ComponentMappers;
import com.roaringcatgames.kitten2d.ashley.K2EntityTweenAccessor;
import com.roaringcatgames.kitten2d.ashley.components.TextComponent;
import com.roaringcatgames.kitten2d.ashley.components.TextureComponent;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;
import com.roaringcatgames.kitten2d.ashley.components.TweenComponent;

/**
 *
 */
public class TimerSystem extends IntervalSystem {

    private Entity timer;
    private Entity backTimer;
    private float totalTime = 60f*3f;

    float elapsedTime = 0f;

    public TimerSystem(float totalTime){
        super(1f);
        this.totalTime = totalTime;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);

        backTimer = engine.createEntity();
        backTimer.add(TransformComponent.create(engine)
                .setPosition(AppConstants.W/2f-0.05f, AppConstants.H-0.05f)
                .setTint(Color.BLACK));
//        timer.add(TextureComponent.create(engine)
//            .setRegion(Assets.getTimeOvalRegion()));
        backTimer.add(TextComponent.create(engine)
                .setFont(Assets.getFont64())
                .setText(getTime()));
        engine.addEntity(backTimer);


        timer = engine.createEntity();
        timer.add(TransformComponent.create(engine)
            .setPosition(AppConstants.W/2f, AppConstants.H)
            .setTint(Color.PINK));
//        timer.add(TextureComponent.create(engine)
//            .setRegion(Assets.getTimeOvalRegion()));
        timer.add(TextComponent.create(engine)
            .setFont(Assets.getFont64())
            .setText(getTime()));



        engine.addEntity(timer);
    }

    boolean triggeredLastMinute = false;
    @Override
    protected void updateInterval() {
        elapsedTime += getInterval();

        if(!triggeredLastMinute && elapsedTime > 60*3f){
            timer.add(TweenComponent.create(getEngine())
                .addTween(Tween.to(timer, K2EntityTweenAccessor.COLOR, 0.5f)
                    .target(Color.RED.r, Color.RED.g, Color.RED.b)
                    .repeatYoyo(Tween.INFINITY, 0f)));
        }
        if(timer !=null) {
            TextComponent tc = K2ComponentMappers.text.get(timer);
            tc.setText(getTime());
        }
        if(backTimer !=null) {
            TextComponent tc = K2ComponentMappers.text.get(backTimer);
            tc.setText(getTime());
        }
    }

    private String getTime(){

        float time = (totalTime-elapsedTime);
        time = MathUtils.clamp(time, 0f, totalTime);
        int minutes = MathUtils.floor(time/60f);
        int seconds = MathUtils.floor(time%60f);
        String secs = seconds < 10 ? "0" + seconds : Integer.toString(seconds);
        return minutes + ":" + secs;
    }
}
