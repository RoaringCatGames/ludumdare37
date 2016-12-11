package com.roaringcatgames.bunkerjunker;

import com.badlogic.ashley.core.ComponentMapper;
import com.roaringcatgames.bunkerjunker.components.*;

/**
 * Quick access to Component Mappers for all of our custom component types.
 */
public class Mappers {

    public static final ComponentMapper<PlayerComponent> player = ComponentMapper.getFor(PlayerComponent.class);
    public static final ComponentMapper<CameraComponent> camera = ComponentMapper.getFor(CameraComponent.class);
    public static final ComponentMapper<TriggerComponent> trigger = ComponentMapper.getFor(TriggerComponent.class);
    public static final ComponentMapper<SensorComponent> sensor = ComponentMapper.getFor(SensorComponent.class);
    public static final ComponentMapper<SupplyComponent> supply = ComponentMapper.getFor(SupplyComponent.class);
    public static final ComponentMapper<EncumberedComponent> encumbered = ComponentMapper.getFor(EncumberedComponent.class);
}
