package com.roaringcatgames.bunkerjunker.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.roaringcatgames.bunkerjunker.AppConstants;
import com.roaringcatgames.bunkerjunker.Assets;
import com.roaringcatgames.kitten2d.ashley.K2ComponentMappers;
import com.roaringcatgames.kitten2d.ashley.components.TextComponent;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;

/**
 * Created by barry on 12/11/16.
 */
public class TimerSystem extends IntervalSystem {

    private Entity timer;
    private float totalTime = 60f*3f;

    float elapsedTime = 0f;

    public TimerSystem(float totalTime){
        super(1f);
        this.totalTime = totalTime;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);

        timer = engine.createEntity();
        timer.add(TransformComponent.create(engine)
            .setPosition(AppConstants.W/2f, AppConstants.H)
            .setTint(Color.PINK));
        timer.add(TextComponent.create(engine)
            .setFont(Assets.getFont64())
            .setText(getTime()));

        engine.addEntity(timer);
    }

    @Override
    protected void updateInterval() {
        elapsedTime += getInterval();
        if(timer !=null) {
            TextComponent tc = K2ComponentMappers.text.get(timer);
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
