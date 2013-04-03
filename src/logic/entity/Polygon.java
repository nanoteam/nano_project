package logic.entity;

import logic.Level;
import logic.entity.entityInterface.IsClonable;
import logic.entity.entityInterface.MorfingCreation;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;
import physic.Material;
import physic.PhysicObject;
import render.RenderUtil;

import java.util.ArrayList;
import java.util.List;

public class Polygon extends GamePhysicObject implements IsClonable, MorfingCreation {

    private List<Vector2f> listVertices = new ArrayList<Vector2f>();
    private Color color;
    private int typeBody = PhysicObject.UNKNOW;

	static {
		className = "Polygon";
	}
    //two phase creation
    public Polygon(Vector2f position, float angle, List<Vector2f> vertices, int typeObject, Color color) {
        this.position = position;
        this.angle = angle;
        listVertices = new ArrayList<Vector2f>(vertices);
        this.typeBody = typeObject;
        this.color = color;
    }

    //easy creation
    public Polygon(Vector2f position, float angle, ArrayList<Vector2f> vertices, int typeObject, Color color, Level level) {
        this.position = position;
        this.angle = angle;
        listVertices = new ArrayList<Vector2f>(vertices);
        this.typeBody = typeObject;
        this.color = color;
        initInPhysicWorld(level);
    }


    private Polygon() {

    }

    public Polygon clone() {
        Polygon clonePolygon = new Polygon();
        clonePolygon.position = new Vector2f(position);
        clonePolygon.angle = angle;
        clonePolygon.typeBody = typeBody;
        clonePolygon.color = new Color(color);
        try {
            clonePolygon.listVertices = new ArrayList<Vector2f>();
            for (Vector2f vector2f : listVertices) {
                clonePolygon.listVertices.add(new Vector2f(vector2f));
            }
        } catch (Exception e) {
            System.out.println("Exception in Polygon.Polygon() ");
            return null;
        }
        return clonePolygon;
    }


    @Override
    public void initInPhysicWorld(Level level) {
        this.level = level;
        this.physicObject = PhysicObject.createPolygon(this, position, listVertices, angle, Material.Wood, typeBody, level.getWorld());
    }


    @Override
    public void update() {

    }

    @Override
    public void move() {
        position = physicObject.getPosition();
        angle = physicObject.getAngle();
        speed = physicObject.getSpeed();
    }

    @Override
    public void draw() {
        RenderUtil.drawPolyLineSmooth(position, listVertices, 4, angle, color);
        RenderUtil.drawPlot(new Vector2f(position), 5, color);
    }

    @Override
    public void playSound() {

    }

    @Override
    public void toThink() {
    }

    @Override
    public void destroy() {
        physicObject.destroy();
    }

    public int getTypeBody() {
        return typeBody;
    }

    public void setTypeBody(int typeBody) {
        this.typeBody = typeBody;
    }
}
