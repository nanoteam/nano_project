import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;


public class HelloGame {


    private HelloGame() {
        try {
            Display.setDisplayMode(new DisplayMode(640, 480));
            Display.setTitle("My first texture game");
            Display.create();

        } catch (LWJGLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        Texture boxSide = loadTexture("box");
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluPerspective((float) 30, 640f / 480f, 0.001f, 100);

//        glOrtho(0, 640, 480, 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);
//        glEnable(GL_TEXTURE_2D);
        glEnable(GL_DEPTH_TEST);

        float x = 0;
        float angle = 50;
        Star[] starList = new Star[1000];

        for (int i = 0; i < 1000; i++) {
            starList[i] = new Star();
        }


        while (!Display.isCloseRequested()) {
            if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
                x -= 0.1f;
            if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
                x += 0.1f;
            if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
                glEnable(GL_PROJECTION);
                gluPerspective(++angle, 640f / 480f, 0.001f, 100);
                glEnable(GL_MODELVIEW);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
                glEnable(GL_PROJECTION);
                gluPerspective(--angle, 640f / 480f, 0.001f, 100);
                glEnable(GL_MODELVIEW);

            }

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

//            glLoadIdentity();
//            boxSide.bind();
//
//
            glTranslatef(0f, 0f, x);
            glBegin(GL_POINTS);
            for (Star star : starList) {
                glColor3f(star.r, star.g, star.b);
                glVertex3f(star.x, star.y, star.z);
            }
            glEnd();
//
//            glBegin(GL_QUADS);
//            glTexCoord2d(0, 0);
//            glVertex2d(50, 50);
//            glTexCoord2d(1, 0);
//            glVertex2d(100, 50);
//            glTexCoord2d(1, 1);
//            glVertex2d(100, 100);
//            glTexCoord2d(0, 1);
//            glVertex2d(50, 100);
//            glEnd();
//
//
//            glPushMatrix();
//            glTranslatef(10f,0f,0f);
//            glBegin(GL_QUADS);
//            glVertex2d(200, 200);
//            glVertex2d(250, 200);
//            glVertex2d(250, 250);
//            glVertex2d(200, 250);
//            glEnd();
//            glPopMatrix();
//
//
//            glPushMatrix();
//            glRotatef(angle, 0.0f, 0.0f, 1f);
//            glBegin(GL_QUADS);
//            glTexCoord2d(0, 0);
//            glVertex2d(150, 50);
//            glTexCoord2d(1, 0);
//            glVertex2d(200, 50);
//            glTexCoord2d(1, 1);
//            glVertex2d(200, 150);
//            glTexCoord2d(0, 1);
//            glVertex2d(150, 150);
//            glEnd();
//            glPopMatrix();


            Display.update();
            Display.sync(60);
        }
        Display.destroy();


    }

    public static void main(String[] args) {
        new HelloGame();
    }

    public Texture loadTexture(String name) {
        Texture texture = null;
        try {
            texture = TextureLoader.getTexture("PNG", new FileInputStream(new File("resourses/" + name + ".png")));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return texture;
    }

    public static class Star {
        static Random random = new Random();
        float x;
        float y;
        float z;
        float r;
        float g;
        float b;

        public Star() {
            x = (random.nextFloat() - 0.5f) * 100;
            y = (random.nextFloat() - 0.5f) * 100;
            z = (random.nextFloat() - 0.5f) * 100;
            r = random.nextFloat() * 256;
            g = random.nextFloat() * 256;
            b = random.nextFloat() * 256;
            System.out.println(x + " " + y + " " + z);
        }
    }

}