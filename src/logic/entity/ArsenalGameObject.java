package logic.entity;

import org.lwjgl.util.vector.Vector2f;

/**
 * Class for any item of arsenal, which may to shoot
 * 
 * @author Arthur
 * 
 */

public abstract class ArsenalGameObject extends GameObjectMoving {

	// Arthur, can you add comment for this fields?

	// the arsenalObject is situated on fatherObj
	protected GameObjectMoving fatherObj;
	// is shooting
	protected boolean onShoot;
	// is relodaing
	protected boolean onReload;
	//value of error fire's trajectory
	protected float randomizeFire;
	// time of reload
	public int reloadTime;
	protected float angle;
	// delta of ship's position and this object's position
	protected Vector2f relativePosition;

	abstract public boolean setShootOn();

}
