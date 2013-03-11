package logic;

import logic.entity.*;
import logic.entity.ship.Ship;
import main.Game;
import org.jbox2d.callbacks.QueryCallback;
import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;
import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Level {
    private static int defaultWidth = 1600;
    private static int defaultHeight = 900;
    private int widthLevel;
    private int heightLevel;
    private Game game;
    private World world = null;
    private AABB aabb = null;
    // physic constans
    // TODO add support resourses manager
    public static float gravity = -3.8f;

    // list of all object
    private List<GameObject> gameObjects = new ArrayList<GameObject>();
    // list of objects which will be added to main list
    private List<GameObject> gameObjectsToAdd = new ArrayList<GameObject>();
    // list of objects which will be deleted from main list
    private List<GameObject> gameObjectsToDelete = new ArrayList<GameObject>();

    private static Random random = new Random();

    public Level(Game game) {
        EmmiterEffects.init(this);
        this.game = game;
        this.widthLevel = Level.defaultWidth;
        this.heightLevel = Level.defaultHeight;
        this.aabb = new AABB(new Vec2(), new Vec2(this.widthLevel / 30,
                this.heightLevel / 30));
        this.world = new World(new Vec2(0, gravity), false);
        world.queryAABB(new QueryCallback() {

            @Override
            public boolean reportFixture(Fixture fixture) {
                // TODO Auto-generated method stub
                return true;
            }
        }, aabb);

        world.setContactListener(new CollisionListener(this));
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public List<GameObject> getNotAddedGameObjects() {
        return gameObjectsToAdd;
    }

    public List<GameObject> getNotDeletedGameObjects() {
        return gameObjectsToDelete;
    }

    /*
    //for study GA
    public void restartShip() {
        Object obj = game.getPlayer().getControlledObject();
        obj = null;
        Ship ship = new Ship(this, 800f, 100);
        game.getPlayer().setControlledObject(ship);
        gameObjectsToAdd.add(ship);

    }
    */

    // this is method for running game in test mode
    public void testInitLevel() {
        Ship ship = new Ship(this, 200, 200);

        gameObjects.add(ship);
        game.getPlayer().setControlledObject(ship);

        ///Ship ship2 = new Ship(this, 500f, 500f);
        //Bot bot = new Bot(ship2);
        //gameObjects.add(ship2);
        //gameObjects.add(bot);

        //border
        Wall leftWall = new Wall(0, 0, 0, 1800,10,this);
        gameObjects.add(leftWall);

        Wall upWall = new Wall(0,1800,3200,1800,10,this);
        gameObjects.add(upWall);

        Wall righWall = new Wall(3200,1800, 3200,0,10,this);
        gameObjects.add(righWall);

        Wall downWall = new Wall(3200,0,0,0,10,this);
        gameObjects.add(downWall);


        //level
        gameObjects.add(new Wall(226,886,372,1269,20,this));
        gameObjects.add(new Wall(259,1476,652,1617,20,this));
        gameObjects.add(new Wall(1096,1748,1430,1612,20,this));
        gameObjects.add(new Wall(1609,1503,1861,1239,20,this));
        gameObjects.add(new Wall(1380,1309,1133,1231,20,this));
        gameObjects.add(new Wall(992,1166,851,954,20,this));
        gameObjects.add(new Wall(798,770,906,549,20,this));
        gameObjects.add(new Wall(1085,408,1360,413,20,this));
        gameObjects.add(new Wall(1392,619,1312,795,20,this));

        gameObjects.add(new Wall(1340,901,1546,1010,20,this));
        gameObjects.add(new Wall(1675,994,1793,899,20,this));
        gameObjects.add(new Wall(1607,571,1823,687,20,this));
        gameObjects.add(new Wall(1924,808,1947,1075,20,this));
        gameObjects.add(new Wall(2035,138,2277,397,20,this));
        gameObjects.add(new Wall(2375,564,2556,836,20,this));
        gameObjects.add(new Wall(2662,1068,2755,1362,20,this));
        gameObjects.add(new Wall(2579,75,2798,292,20,this));
        gameObjects.add(new Wall(2843,559,3022,858,20,this));

        gameObjects.add(new Wall(2994,1244,3032,1569,20,this));
        gameObjects.add(new Wall(2362,1234,2493,1420,20,this));
        gameObjects.add(new Wall(1619,125,1416,345,20,this));

        // gameObjects.add(new Wall(this, 700, 100, 20, 200));
        //gameObjects.add(new JumpWall(this, 900, 40, 200, 40));

        // add to world a chain
        /*gameObjects.add(new Chain(this, upWall.getBody(), new Vector2f(
                  defaultWidth / 2f, defaultHeight - 5), ship2.getBody(),
                  new Vector2f(500 + 50, 500 + 20)));
          gameObjects.add(new Chain(this, upWall.getBody(), new Vector2f(
                  defaultWidth / 4f, defaultHeight - 5), ship2.getBody(),
                  new Vector2f(500 - 50, 500 + 20)));

          /*{
           TrapRotation tr = new TrapRotation(this, new Vector2f(900f, 300f));
           gameObjects.add(tr);
           RevoluteJointDef join = new RevoluteJointDef();
           join.initialize(tr.getBody(), downWall.getBody(), tr.getBody()
           .getPosition());
           world.createJoint(join);
           } */
        gameObjects.add(new Asteroid(this, new Vector2f(400f, 300f)));
        gameObjects.add(new Asteroid(this, new Vector2f(550f, 300f)));
        gameObjects.add(new Asteroid(this, new Vector2f(600f, 300f)));
        gameObjects.add(new Asteroid(this, new Vector2f(650f, 300f)));
        gameObjects.add(new Asteroid(this, new Vector2f(700f, 300f)));
        gameObjects.add(new Asteroid(this, new Vector2f(750f, 300f)));
        gameObjects.add(new Asteroid(this, new Vector2f(800f, 300f)));
        gameObjects.add(new Asteroid(this, new Vector2f(850f, 300f)));


        //gameObjects.add(new AeroDynTest(this));


        // /gameObjects.add(new Engine(this,new Vector2f(200f,200f)));
        //gameObjects.add(new Chain(this, ship.getBody(), ship.getPosition(),
        //ship.getBody(), new Vector2f(100, 200)));

        //gameObjects.add(new AeroDynTest(this));
        //ManipulareTool mt = new ManipulareTool(this, new Vector2f(670f, 500f));
        //gameObjects.add(mt);
        //gameObjects.add(new Chain(this, upWall.getBody(),
        //new Vector2f(670, 797), mt.getBody(), new Vector2f(670, 480)));
        // gameObjects.add(new Engine(this,new Vector2f(200f,200f)));
        // gameObjects.add(new Chain(this, ship.getBody(), ship.getPosition(),
        // ship.getBody(), new Vector2f(100, 200)));

// ManipulareTool mt = new ManipulareTool(this, new Vector2f(670f, 500f));
//		gameObjects.add(mt);
//		gameObjects.add(new Chain(this, upWall.getBody(),
//				new Vector2f(670, 797), mt.getBody(), new Vector2f(670, 480)));     */
    }

    /*public void testLevelStudyAI() {
        Wall leftWall = new Wall(this, 5, defaultHeight / 2, 10, defaultHeight);
        gameObjects.add(leftWall);
        Wall upWall = new Wall(this, defaultWidth / 2, defaultHeight - 5,
                defaultWidth, 10);
        gameObjects.add(upWall);
        Wall righWall = new Wall(this, defaultWidth - 5, defaultHeight / 2, 10,
                defaultHeight);
        gameObjects.add(righWall);
        Wall downWall = new Wall(this, defaultWidth / 2, 5, defaultWidth, 10);
        gameObjects.add(downWall);

        restartShip();
    }    */

    public Player getPlayer() {
        return game.getPlayer();
    }

    /**
     * added by Arthur for testing objects 31.07.12
     */
    // method for adding not added objects after last 'foreach'
    public void addNotAddedObjects() {
        if (gameObjectsToAdd.size() != 0)
            gameObjects.addAll(gameObjectsToAdd);
        gameObjectsToAdd.clear();
    }

    public void deleteNotDeletedObjects() {
        if (gameObjectsToDelete.size() != 0) {
            for (GameObject gameObject : gameObjectsToDelete) {
                gameObject.destroy();
            }
            gameObjects.removeAll(gameObjectsToDelete);
        }
        gameObjectsToDelete.clear();
    }

    public boolean isInBorders(Vector2f position) {
        if (position.x < 0 || position.x > widthLevel || position.y < 0
                || position.y > heightLevel)
            return false;
        else
            return true;
    }

    public World getWorld() {
        return world;
    }

    public void clearWorld() {
        for (GameObject go : gameObjects) {
            go.destroy();
        }
        gameObjects.clear();
    }

    public float getGravity() {
        return gravity;
    }

    public Vector2f getMousePosition() {
        return game.getPositionMouse();
    }

    public float getAngleBetweenShipAndCursor() {
        return game.getAngleBetweenShipAndCursor();
    }
}
