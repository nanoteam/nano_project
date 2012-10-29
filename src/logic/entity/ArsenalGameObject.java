package logic.entity;

import org.lwjgl.util.vector.Vector2f;

/**
 * Class for any item of arsenal, which may to shoot
 * 
 * @author Arthur
 * 
 */

public abstract class ArsenalGameObject extends GameObjectPhysicMoving{

    //Arthur, can you add comment for this fields?
	protected GameObjectPhysicMoving fatherObj;
    protected boolean onShoot;
    protected boolean onReload;
    protected float randomizeFire;
	public int reloadTime;
    protected float angle;
    protected Vector2f relativePosition;

	abstract public boolean setShootOn();

}
