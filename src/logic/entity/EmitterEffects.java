package logic.entity;

import logic.CollisionListener;
import logic.Level;
import org.jbox2d.common.Vec2;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;

import java.util.Random;

public class EmitterEffects {
    private static Random random = new Random();
    private Level level;

    public EmitterEffects(Level level) {
        this.level = level;
    }

    public void drawCollision(Vec2[] points, float[] impulses, int type) {
        Vector2f speed;
        Color color = null;
        int j = 0;
        if (points.length == 0)
            return;
        for (Vec2 v : points) {
            for (int i = 0; i < impulses[j]; i++) {
                // will be another random (simple int)
                if (type == CollisionListener.SHIP_WITH_SHIP) {
                    color = (Color) Color.GREEN;
                } else if (type == CollisionListener.SHIP_WITH_WALL) {
                    // if (random.nextInt(2) == 1)
                    // color = (Color) Color.WHITE;
                    // else
                    // color = (Color) Color.RED;
                    color = (Color) Color.GREY;
                }
                speed = new Vector2f(20f - random.nextFloat() * 40,
                        20f - random.nextFloat() * 40);
                level.getNotAddedGameObjects().add(
                        new Particle(new Vector2f(v.x * 30, v.y * 30), speed, 2, (int) (random.nextFloat() * impulses[j] * 3), color));
            }
            j++;
        }

    }

    public void drawBoom(Vector2f position) {

        float maxSpeed = 150;
        float angleParticle, speedParticle;
        Color color;

        for (int i = 0; i < 100; i++) {
            if (random.nextBoolean())
                color = (Color) Color.RED;
            else
                color = (Color) Color.PURPLE;

            angleParticle = (float) (random.nextFloat() * 2 * Math.PI);
            speedParticle = (float) (random.nextFloat() * maxSpeed / 2);

            level.getNotAddedGameObjects().add(
                    new Particle(new Vector2f(position), new Vector2f(
                            (float) (Math.cos(angleParticle) * speedParticle),
                            (float) (Math.sin(angleParticle) * speedParticle)),
                            2, 30 + random.nextInt(20), color));

        }
        color = (Color) Color.GREY;
        for (int i = 0; i < 10; i++) {
            maxSpeed = 200;
            angleParticle = (float) (random.nextFloat() * 2 * Math.PI);
            speedParticle = (float) (random.nextFloat() * maxSpeed / 2);

            level.getNotAddedGameObjects().add(
                    new Particle(new Vector2f(position), new Vector2f(
                            (float) (Math.cos(angleParticle) * speedParticle),
                            (float) (Math.sin(angleParticle) * speedParticle)),
                            4 + random.nextInt(5), 30 + random.nextInt(10),
                            color));
        }
    }

    public void drawParticlesFromEngine(Vector2f position, float angle) {
        int maxSpeed = 20;
        int maxTime = 60;
        int i, j = random.nextInt(15);
        Color color = null;
        if (random.nextBoolean())
            color = (Color) Color.RED;
        else
            color = (Color) Color.YELLOW;
        for (i = 0; i < j; i++)
            level.getNotAddedGameObjects().add(
                    new Particle(position, new Vector2f((float) (maxSpeed
                            * Math.sin(angle) + random.nextFloat() - 1),
                            -(float) (maxSpeed * Math.cos(angle)
                                    + random.nextFloat() - 1)), random
                            .nextInt(4), random.nextInt(maxTime), color));
    }

    public void drawGrowCircles(Vector2f position, int beginRadius, int lifeTime, Color color) {
        int maxSpeed = 20;
        //int maxTime = 60;

        int numberParticle = 5;
        for (int i = 0; i < numberParticle; i++) {
            float angle = random.nextFloat() * 3.14159f * 2f;
            float speedX = (float) Math.sin(angle) * maxSpeed;
            float speedY = (float) Math.cos(angle) * maxSpeed;
            level.getNotAddedGameObjects().add(
                    new Particle(position, new Vector2f(speedX, speedY), random
                            .nextInt(4) + 4, random.nextInt(lifeTime), color));
        }

    }

    public void drawAura(Vector2f position, int radius, int lifeTime, Color color) {
        int numberElement = (int) Math.log10(radius) * 50;
        for (int i = 0; i < numberElement; i++) {
            float angle = (float) (random.nextFloat() * 2 * Math.PI);
            int x = (int) (Math.cos(angle) * radius + position.x);
            int y = (int) (Math.sin(angle) * radius + position.y);
            level.getNotAddedGameObjects().add(new Particle(new Vector2f(x, y), new Vector2f(0, 0), 4, 60, color));
        }

    }
}
