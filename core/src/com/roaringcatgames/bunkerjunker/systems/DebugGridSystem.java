package com.roaringcatgames.bunkerjunker.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.Array;
import com.roaringcatgames.kitten2d.ashley.components.BoundsComponent;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;
import com.roaringcatgames.kitten2d.ashley.systems.DebugSystem;

/**
 * To Draw Some Lines so we can see movement without assets
 */
public class DebugGridSystem extends EntitySystem{

    private float metersWide, metersHigh, buffer;

    public DebugGridSystem(float metersWide, float metersHigh, float metersBetween){
        this.metersWide = metersWide;
        this.metersHigh = metersHigh;
        this.buffer = metersBetween;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);

        int verticalCount = Math.round(metersWide/buffer);
        int horizontalCount = Math.round(metersHigh/buffer);

        float leftX = -metersWide/2f;
        float bottomY = -metersHigh/2f;

        int vCount = 0;
        while(vCount < verticalCount){
            Entity e = engine.createEntity();
            e.add(TransformComponent.create(engine)
                .setPosition(leftX + (buffer*vCount), 0f));
            e.add(BoundsComponent.create(engine)
                .setBounds(leftX + (buffer*vCount), 0f, 0.05f, metersHigh));
            engine.addEntity(e);
            vCount += 1;
        }

        int hCount = 0;
        while(hCount < horizontalCount){
            Entity e = engine.createEntity();
            e.add(TransformComponent.create(engine)
                    .setPosition(0f, bottomY + (buffer*hCount)));
            e.add(BoundsComponent.create(engine)
                    .setBounds(0f, bottomY + (buffer*hCount), metersWide, 0.05f));
            engine.addEntity(e);
            hCount += 1;
        }

    }
}
