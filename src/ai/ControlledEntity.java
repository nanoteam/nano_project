package ai;

import org.lwjgl.util.vector.Vector2f;

public interface ControlledEntity {
    public void doAction(int code);
    public void clearFlags();
    public Vector2f getPosition();
    public boolean isControlledByPlayer();
}
