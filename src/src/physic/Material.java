package physic;

public enum Material {
	Metal(0.5f, 0.5f, 0.3f), Wood(0.3f, 0.7f, 0.03f), Stone(10f, 0.7f, 0.7f);

	float density; // plotnost'
	float friction; // trenie
	float restitution; // uprugost'

	private Material(float density, float friction, float restitution) {
		this.density = density;
		this.friction = friction;
		this.restitution = restitution;
	}

}
