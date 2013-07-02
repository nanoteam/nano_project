package logic.entity;

import ai.ControlledEntity;
import org.lwjgl.util.vector.Vector2f;

/**
 * Class for any item of arsenal, which may to shoot
 * 
 * @author Arthur
 * 
 */

public abstract class ArsenalGameObject extends GamePhysicObject {

	// the arsenalObject is situated on fatherObj
	protected GamePhysicObject fatherObj;
	// is shooting
	protected boolean onShootPrimary = false;
	protected boolean onShootAlternative = false;

	//value of error fire's trajectory
	protected float randomizeFirePrimary;
	protected float randomizeFireAlternative;

	// time of reload
    protected int reloadTimePrimary;
    protected int reloadTimeAlternative;

    protected float energyCoastPrimary;
    protected float energyCoastAlternative;

    //number particle per shoot
    protected int countAmmoPrimaryPerShoot;
    protected int countAmmoAlternativePerShoot;

    //K = (mass * speed^2) / 2 or (mv^2)/2 , K - kinetic energy
    //for shot shell speed = sqrt(2*K/m)
    protected float kineticEnergyPrimary;
    protected float kineticEnergyAlternative;

	// delta of ship's position and this object's position
	protected Vector2f relativePosition;

    //name of used ammo
    protected String nameAmmoPrimary;
    protected String nameAmmoAlternative;

	abstract public boolean firePrimary();
	abstract public boolean fireAlternative();



}
