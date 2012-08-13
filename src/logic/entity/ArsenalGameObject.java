package logic.entity;

import org.lwjgl.util.vector.Vector2f;

/**
 * Class for any item of arsenal, which may to shoot
 * 
 * @author Arthur
 * 
 */
public abstract class ArsenalGameObject extends GameObjectPhysicMoving{

	GameObjectPhysicMoving fatherObj;
	
	boolean onShoot;
	boolean onReload;
	float randomizeFire;
	public int reloadTime;
	float angle;
	Vector2f relativePosition;

	abstract public boolean setShootOn();

}
