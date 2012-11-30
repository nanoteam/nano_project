package ai.commands;

import org.lwjgl.util.vector.Vector2f;

public class CommandMoveTo extends Command {
    static {
        name = "CommandMoveTo";
    }

    private Vector2f targetPoint;

    public CommandMoveTo(float dx, float dy) {
        targetPoint = new Vector2f(dx, dy);
    }

    public Vector2f getTargetPoint() {
        return targetPoint;
    }

}
