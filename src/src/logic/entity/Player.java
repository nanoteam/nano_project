package logic.entity;

import ai.ControlledEntity;

public class Player {
    private ControlledEntity controlledEntity;

    public ControlledEntity getControlledObject() {
        return controlledEntity;
    }

    public void setControlledObject(ControlledEntity controlledEntity) {
        this.controlledEntity = controlledEntity;
    }

    public Player() {

    }


}
