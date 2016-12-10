package com.roaringcatgames.bunkerjunker.systems;

import com.roaringcatgames.bunkerjunker.components.IAction;
import com.roaringcatgames.bunkerjunker.components.MovementMode;
import com.roaringcatgames.bunkerjunker.components.PlayerComponent;

/**
 * Action to Toggle the stair state of a player.
 */
public class PlayerOffStepsAction implements IAction {

    private PlayerComponent pc;

    public PlayerOffStepsAction(PlayerComponent pc){
        this.pc = pc;
    }
    public void run(){
        if(pc.moveMode != MovementMode.LEFT_RIGHT){
            pc.moveMode = MovementMode.LEFT_RIGHT;
        }
    }
}
