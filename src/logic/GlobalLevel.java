package logic;


import logic.entity.GameObject;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;
import render.RenderUtil;
import util.MathUtil;

public class GlobalLevel extends GameObject {
    private int x, y;
    private int orderNumber;
    private int timeBeforeTeleport;
    private boolean readyToTeleport;
    private Color color;

    public GlobalLevel(int x, int y, int orderNumber, int timeBeforeTeleport) {
        this.x = x;
        this.y = y;
        this.orderNumber = orderNumber;
        this.timeBeforeTeleport = timeBeforeTeleport;
        color = new Color(MathUtil.random.nextInt(255), MathUtil.random.nextInt(255), MathUtil.random.nextInt(255));
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public int getTimeBeforeTeleport() {
        return timeBeforeTeleport;
    }

    public void setTimeBeforeTeleport(int timeBeforeTeleport) {
        this.timeBeforeTeleport = timeBeforeTeleport;
    }

    public boolean isReadyToTeleport() {
        return readyToTeleport;
    }

    public void setReadyToTeleport(boolean readyToTeleport) {
        this.readyToTeleport = readyToTeleport;
    }

    @Override
    public void update() {
        if (!readyToTeleport) {
            timeBeforeTeleport--;
        }
        if (timeBeforeTeleport < 0) {
            readyToTeleport = true;
        }
    }

    @Override
    public void move() {

    }

    @Override
    public void draw() {
        RenderUtil.drawCircle(new Vector2f(x, y), 30, 3, color);
    }

    @Override
    public void playSound() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void toThink() {

    }

    @Override
    public String getAdditionalName() {
        return null;
    }

    @Override
    public String getMyClassName() {
        return null;
    }
}
