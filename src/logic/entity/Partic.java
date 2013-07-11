package logic.entity;

import logic.Level;

import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;

import physic.Material;
import physic.PhysicObject;

import render.RenderUtil;

public class Partic extends GamePhysicObject {

	public Partic(Level level, Vector2f pos, Vector2f speed) {
		this.level = level;
		this.position = pos;
		this.speed = speed;
		this.hp = 100;

        physicObject = PhysicObject
                .createBall(this, position, 2, Material.Wood,PhysicObject.DINAMIC,level.getWorld());
	}

	@Override
	public void update() {
		hp--;
		if (hp < 0)
			live = false;
	}

	@Override
	public void move() {
		this.position = physicObject.getPosition();
	}

	@Override
	public void draw() {
		RenderUtil.drawPlot(position, 2f, (Color) Color.YELLOW);
	}

	@Override
	public void playSound() {
		// TODO Auto-generated method stub

	}

    @Override
    public void toThink() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getAdditionalName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getMyClassName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
	public void destroy() {
		physicObject.destroy();

	}

}
