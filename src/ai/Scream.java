package ai;

import org.lwjgl.util.vector.Vector2f;

public class Scream {
    public final static int SCREAM_ATACK = 0;
    private Vector2f vector2f;
    private int typeScream;
    private long idShip;

    public Scream(Vector2f vector2f, int typeScream, long idShip) {
        this.vector2f = vector2f;
        this.typeScream = typeScream;
        this.idShip = idShip;
    }

    public Vector2f getVector2f() {
        return vector2f;
    }

    public int getTypeScream() {
        return typeScream;
    }

    public long getIdShip() {
        return idShip;
    }
}
