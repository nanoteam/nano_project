package ai.commands;

import org.lwjgl.util.vector.Vector2f;

public class CommandMoveOn extends Command{
    static {
        name = "CommandMoveOn";
    }
    private Vector2f direction;
    public CommandMoveOn(float dx, float dy){
        direction = new Vector2f(dx,dy);
    }
    public Vector2f getDirection(){
        return direction;
    }
}
