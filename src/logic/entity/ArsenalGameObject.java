package logic.entity;

import org.lwjgl.util.vector.Vector2f;

/**
 * Class for any item of arsenal, which may to shoot
 * 
 * @author Arthur
 * 
 */

public abstract class ArsenalGameObject extends GameObjectMoving {

	// the arsenalObject is situated on fatherObj
	protected GameObjectMoving fatherObj;
	// is shooting
	protected boolean onShootPrimary;
	protected boolean onShootAlternative;

	//value of error fire's trajectory
	protected float randomizeFirePrimary;
	protected float randomizeFireAlternative;

	// time of reload
    protected int reloadTimePrimary;
    protected int reloadTimeAlternative;

	protected float angle;

	// delta of ship's position and this object's position
	protected Vector2f relativePosition;

	abstract public boolean firePrimary();
	abstract public boolean fireAlternative();



}
