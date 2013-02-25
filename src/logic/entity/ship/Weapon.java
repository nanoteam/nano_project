package logic.entity.ship;

import logic.entity.ArsenalGameObject;
import logic.entity.GameObjectMoving;
import logic.entity.ammo.ClusterBomb;
import logic.entity.ammo.PlazmaBall;
import logic.entity.ammo.RubberBall;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;
import render.RenderUtil;
import util.MathUtil;

public class Weapon extends ArsenalGameObject {
    private final static int LENGTH_BETWEEN_SPAWN_AREA_AMMO_AND_WEAPON = 5;
    private int width;
    private int height;
    //if <0 then can shoot
    private int reloadingTimerPrimary = 0;
    private int reloadingTimerAlternative = 0;
    private int reloadTime;
    //K = (mass * speed^2) / 2 or (mv^2)/2 , K - kinetic energy
    //for shot shell speed = sqrt(2*K/m)
    private float K;

    public Weapon(GameObjectMoving gameObject, int width, int height,
                  float randomizeFirePrimary, float randomizeFireAlternative, int reloadTimePrimary, int reloadTimeAlternative, float K) {
        // this way or draw throw draw-method of ship
        this.position = gameObject.getPosition();
        this.fatherObj = gameObject;
        this.level = gameObject.getLevel();
        this.width = width;
        this.height = height;

        this.randomizeFirePrimary = randomizeFirePrimary;
        this.randomizeFireAlternative = randomizeFireAlternative;
        this.reloadTimePrimary = reloadTimePrimary;
        this.reloadTimeAlternative = reloadTimeAlternative;

        this.K = K;

        onShootPrimary = false;
        onShootAlternative = false;
    }

    @Override
    public void update() {
        //primary weapon
        if (onShootPrimary) {
            System.out.println("primary");
            //id relaod complited
            if (reloadingTimerPrimary < 0) {
                //create shoot

                Vector2f sheel_position = new Vector2f(position.x +
                        MathUtil.newXTurn(width + LENGTH_BETWEEN_SPAWN_AREA_AMMO_AND_WEAPON, 0, angle), position.y + MathUtil.newYTurn(width + LENGTH_BETWEEN_SPAWN_AREA_AMMO_AND_WEAPON, 0, angle));
                float shell_speed = (float) Math.sqrt(2 * K / RubberBall.STANDART_MASS);

                Vector2f shell_speed_vector =
                        new Vector2f(
                                (float) (Math.cos(angle + randomizeFirePrimary * Math.random() - randomizeFirePrimary / 2f) * shell_speed),
                                (float) Math.sin(angle + randomizeFirePrimary * Math.random() - randomizeFirePrimary / 2f) * shell_speed);
                RubberBall rubberBall = new RubberBall(sheel_position, shell_speed_vector, level);
                level.getNotAddedGameObjects().add(rubberBall);
                //action for game logic
                onShootPrimary = false;
                reloadingTimerPrimary = reloadTimePrimary;
            }
        }
        //alternative weapon
        if (onShootAlternative) {
            System.out.println("alternative");
            //id relaod complited
            if (reloadingTimerAlternative < 0) {
                //create shoot

                Vector2f sheel_position = new Vector2f(position.x +
                        MathUtil.newXTurn(width + LENGTH_BETWEEN_SPAWN_AREA_AMMO_AND_WEAPON, 0, angle), position.y + MathUtil.newYTurn(width + LENGTH_BETWEEN_SPAWN_AREA_AMMO_AND_WEAPON, 0, angle));
                float shell_speed = (float) Math.sqrt(2 * K / (ClusterBomb.STANDART_MASS));

                Vector2f shell_speed_vector =
                        new Vector2f(
                                (float) (Math.cos(angle + randomizeFireAlternative * Math.random() - randomizeFireAlternative / 2f) * shell_speed),
                                (float) Math.sin(angle + randomizeFireAlternative * Math.random() - randomizeFireAlternative / 2f) * shell_speed);
                ClusterBomb clusterBomb = new ClusterBomb(sheel_position, shell_speed_vector, 8,level);
                level.getNotAddedGameObjects().add(clusterBomb);
                //action for game logic
                onShootAlternative = false;
                reloadingTimerAlternative = reloadTimeAlternative;
            }
        }

        // bullet
        // Bullet bullet = new Bullet(position, angle, sizeBullet,
        // randomizeFire);

        reloadingTimerAlternative--;
        reloadingTimerPrimary--;
    }

    @Override
    public void move() {
        position = fatherObj.getPosition();
        Vector2f vector = level.getMousePosition();


        angle = (float) Math.atan2(vector.y - this.position.y, vector.x
                - this.position.x);

    }

    @Override
    public void draw() {
        RenderUtil.drawLine(new Vector2f(position),
                new Vector2f(position.x + MathUtil.newXTurn(width, 0, angle), position.y + MathUtil.newYTurn(width, 0, angle)), 5, (Color) Color.CYAN);
    }

    @Override
    public void playSound() {
    }

    @Override
    public void toThink() {

    }

    @Override
    public boolean firePrimary() {
        onShootPrimary = true;
        return false;
    }

    @Override
    public boolean fireAlternative() {
        onShootAlternative = true;
        return false;
    }

    @Override
    public void destroy() {

    }

}

/*
        //this is not trivial math task!!!
        //there are line, which has two dot : (x1;y1) and (x2;y2) and nessesiary calc coordinate 3th dot, which consist on this line and locate at width lengh from 1th dot
        float x, y;
        x = position.x + (float) (width * (level.getMousePosition().x - position.x) / Math.sqrt((level.getMousePosition().x - position.x) * (level.getMousePosition().x - position.x) +
                (level.getMousePosition().y - position.y) * (level.getMousePosition().y - position.y)));
        y = position.y + (float) (width * (level.getMousePosition().y - position.y) / Math.sqrt((level.getMousePosition().x - position.x) * (level.getMousePosition().x - position.x) +
                (level.getMousePosition().y - position.y) * (level.getMousePosition().y - position.y)));
        */