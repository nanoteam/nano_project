package logic.entity;

import logic.Level;

public class JumpWall extends Wall {
	public JumpWall(Level level, float x, float y, float width, float height) {
		super(level, x, y, width, height);
		physicObject.getBody().getFixtureList().m_restitution = 1f;
	}

}
