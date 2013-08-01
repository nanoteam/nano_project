package logic;


import logic.entity.GameObject;

public class GlobalLevel extends GameObject {
    private int x, y;
    private int orderNumber;
    private int timeBeforeTeleport;
    private boolean readyToTeleport;

    public GlobalLevel(int x, int y, int orderNumber, int timeBeforeTeleport) {

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
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void draw() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void playSound() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void destroy() {
        //To change body of implemented methods use File | Settings | File Templates.
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
}
