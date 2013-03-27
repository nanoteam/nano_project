package logic;

import logic.entity.*;
import logic.entity.entityInterface.IsClonable;
import logic.entity.entityInterface.MorfingCreation;
import logic.entity.ship.Ship;
import main.Game;
import main.Global;
import org.jbox2d.callbacks.QueryCallback;
import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;
import physic.PhysicObject;
import resourses.configuration.SheetParse;
import util.LightInteger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Level {
    private int widthLevel;
    private int heightLevel;
    private Game game;

    private World world = null;
    private AABB aabb = null;

    private float airResist;
    private Vector2f gravity;
    private String additionalName;
    private String description;
    private EmitterEffects emitterEffects;

    //level storage in collection, and of course, have unique id
    private static int idCount = 0;
    private int id = idCount++;


    //map object class Level, who already used in game
    private static Map<LightInteger, Level> gameLevel = new HashMap<LightInteger, Level>();

    //map object class Level, who ready for phase init and get memory
    private static Map<String, Level> libraryLevel = new HashMap<String, Level>();

    // list of all object
    private List<GameObject> gameObjects = new ArrayList<GameObject>();
    // list of objects which will be added to main list
    private List<GameObject> gameObjectsToAdd = new ArrayList<GameObject>();
    // list of objects which will be deleted from main list
    private List<GameObject> gameObjectsToDelete = new ArrayList<GameObject>();

    private Level() {

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

    // this is method for running game in test mode
    public void testInitLevel() {
        ///Ship ship2 = new Ship(this, 500f, 500f);
        //Bot bot = new Bot(ship2);
        //gameObjects.add(ship2);
        //gameObjects.add(bot);


        //border
        Wall leftWall = new Wall(0, 0, 0, heightLevel, 10, this);
        gameObjects.add(leftWall);

        Wall upWall = new Wall(0, heightLevel, widthLevel,heightLevel , 10, this);
        gameObjects.add(upWall);

        Wall righWall = new Wall(widthLevel, heightLevel, widthLevel, 0, 10, this);
        gameObjects.add(righWall);

        Wall downWall = new Wall(widthLevel, 0, 0, 0, 10, this);
        gameObjects.add(downWall);


        BlackHole blackHole = new BlackHole(this, new Vector2f(1800, 1800), 400, 3);
        gameObjects.add(blackHole);
        /*
       //level
       gameObjects.add(new Wall(226, 886, 372, 1269, 20, this));
       gameObjects.add(new Wall(259, 1476, 652, 1617, 20, this));
       gameObjects.add(new Wall(1096, 1748, 1430, 1612, 20, this));
       gameObjects.add(new Wall(1609, 1503, 1861, 1239, 20, this));
       gameObjects.add(new Wall(1380, 1309, 1133, 1231, 20, this));
       gameObjects.add(new Wall(992, 1166, 851, 954, 20, this));
       gameObjects.add(new Wall(798, 770, 906, 549, 20, this));
       gameObjects.add(new Wall(1085, 408, 1360, 413, 20, this));
       gameObjects.add(new Wall(1392, 619, 1312, 795, 20, this));

       gameObjects.add(new Wall(1340, 901, 1546, 1010, 20, this));
       gameObjects.add(new Wall(1675, 994, 1793, 899, 20, this));
       gameObjects.add(new Wall(1607, 571, 1823, 687, 20, this));
       gameObjects.add(new Wall(1924, 808, 1947, 1075, 20, this));
       gameObjects.add(new Wall(2035, 138, 2277, 397, 20, this));
       gameObjects.add(new Wall(2375, 564, 2556, 836, 20, this));
       gameObjects.add(new Wall(2662, 1068, 2755, 1362, 20, this));
       gameObjects.add(new Wall(2579, 75, 2798, 292, 20, this));
       gameObjects.add(new Wall(2843, 559, 3022, 858, 20, this));

       gameObjects.add(new Wall(2994, 1244, 3032, 1569, 20, this));
       gameObjects.add(new Wall(2362, 1234, 2493, 1420, 20, this));
       gameObjects.add(new Wall(1619, 125, 1416, 345, 20, this));


       gameObjects.add(new Asteroid(this, new Vector2f(400f, 300f)));
       gameObjects.add(new Asteroid(this, new Vector2f(550f, 300f)));
       gameObjects.add(new Asteroid(this, new Vector2f(600f, 300f)));
       gameObjects.add(new Asteroid(this, new Vector2f(650f, 300f)));
       gameObjects.add(new Asteroid(this, new Vector2f(700f, 300f)));
       gameObjects.add(new Asteroid(this, new Vector2f(750f, 300f)));
       gameObjects.add(new Asteroid(this, new Vector2f(800f, 300f)));
       gameObjects.add(new Asteroid(this, new Vector2f(850f, 300f)));

        */
        //gameObjects.add(new Polygon(this, new Vector2f(300, 300)));

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

    public EmitterEffects getEmitterEffects() {
        return emitterEffects;
    }

    public Vector2f getGravity() {
        return gravity;
    }

    public Vector2f getMousePosition() {
        return game.getPositionMouse();
    }

    public float getAngleBetweenShipAndCursor() {
        return game.getAngleBetweenShipAndCursor();
    }

    public static void addLevelToLibrary(SheetParse config) {

        SheetParse mainConfig = config.findSheetParseByName("Level");
        Level level = new Level();
        level.emitterEffects = new EmitterEffects(level);

        if (mainConfig.findSheetParseByName("AdditionalName") != null) {
            level.additionalName = mainConfig.findSheetParseByName("AdditionalName").getValue();
        } else {
            level.additionalName = "unknow";
            System.out.println("Level !parse default! additionalName");
        }

        if (mainConfig.findSheetParseByName("Description") != null) {
            level.description = mainConfig.findSheetParseByName("Description").getValue();
        } else {
            level.description = "unknow";
            System.out.println("Level !parse default! description");
        }

        //size
        SheetParse sizeParse = mainConfig.findSheetParseByName("Size");
        if (sizeParse != null) {
            if (sizeParse.findSheetParseByName("width") != null) {
                level.widthLevel = Integer.valueOf(sizeParse.findSheetParseByName("width").getValue());
            } else {
                level.widthLevel = 900;
                System.out.println("Level !parse default! width");
            }
            if (sizeParse.findSheetParseByName("height") != null) {
                level.heightLevel = Integer.valueOf(sizeParse.findSheetParseByName("height").getValue());

            } else {
                level.heightLevel = 900;
                System.out.println("Level !parse default! height");
            }
        } else {
            System.out.println("Level !parse default! Size");
            level.widthLevel = 900;
            level.heightLevel = 900;
        }
        //gravity
        SheetParse gravityParse = mainConfig.findSheetParseByName("GravityVector");
        float x, y;
        if (gravityParse != null) {
            if (gravityParse.findSheetParseByName("x") != null) {
                x = Float.valueOf(gravityParse.findSheetParseByName("x").getValue());
            } else {
                x = 0;
                System.out.println("Level !parse default! GravityVector x");
            }
            if (gravityParse.findSheetParseByName("y") != null) {
                y = Float.valueOf(gravityParse.findSheetParseByName("y").getValue());
            } else {
                y = -3.8f;
                System.out.println("Level !parse default! GravityVector y");
            }
        } else {
            System.out.println("Level !parse default! GravityVector");
            x = 0;
            y = -3.8f;
        }

        level.gravity = new Vector2f(x, y);
        //aeroResist
        if (mainConfig.findSheetParseByName("AirResist") != null) {
            level.airResist = Float.valueOf(mainConfig.findSheetParseByName("AirResist").getValue());
        } else {
            level.airResist = 0f;
            System.out.println("Level !parse default! airResist");
        }

        //create world, get memory for this
        x = y = 0;
        SheetParse sheetParseLevelObjects = mainConfig.findSheetParseByName("LevelObjects");
        if (sheetParseLevelObjects != null) {
            List<SheetParse> listSheetParse = sheetParseLevelObjects.getSheets();
            for (SheetParse sheetParseLevelObject : listSheetParse) {
                if (sheetParseLevelObject.getName().equals("Polygon")) {
                    //vertex
                    int numberX = 1;
                    int numberY = 1;
                    List<Vector2f> listVertex = new ArrayList<Vector2f>();
                    boolean run = true;
                    while (run) {
                        //special flag. x and y must come from sheetParse together, else - wrong parsing. of couse, must be equal 2
                        int mustBe2 = 0;
                        if (sheetParseLevelObject.findSheetParseByName("x" + Integer.toString(numberX)) != null) {
                            x = Float.parseFloat(sheetParseLevelObject.findSheetParseByName("x" + Integer.toString(numberX)).getValue());
                            numberX++;
                        } else {
                            run = false;
                            continue;
                        }
                        if (sheetParseLevelObject.findSheetParseByName("y" + Integer.toString(numberY)) != null) {
                            y = Float.parseFloat(sheetParseLevelObject.findSheetParseByName("y" + Integer.toString(numberY)).getValue());
                            numberY++;
                        } else {
                            run = false;
                            continue;
                        }
                        listVertex.add(new Vector2f(x, y));
                    }
                    //number vertex = { 3 .. 8 } , and becouse start in 1, get numberVertex = { 3 .. 8 }
                    if (numberX != numberY) {
                        continue;
                    }
                    if ((numberX > 8) || (numberX < 3)) {
                        continue;
                    }

                    //normalize

                    float Xc = 0, Yc = 0;

                    for (Vector2f vector2f : listVertex) {
                        Xc += vector2f.x;
                        Yc += vector2f.y;
                    }
                    Xc /= listVertex.size();
                    Yc /= listVertex.size();
                    for (Vector2f vector2f : listVertex) {
                        vector2f.x = vector2f.x - Xc;
                        vector2f.y = vector2f.y - Yc;
                    }

                    float angle;

                    if (sheetParseLevelObject.findSheetParseByName("Angle") != null) {
                        angle = Float.parseFloat(sheetParseLevelObject.findSheetParseByName("Angle").getValue());
                    } else {
                        continue;
                    }

                    //todo change this
                    int typeObject = 0;
                    if (sheetParseLevelObject.findSheetParseByName("TypeObject") != null) {
                        String stringTypeObject = sheetParseLevelObject.findSheetParseByName("TypeObject").getValue();
                        if (stringTypeObject.equals("Static")) {
                            typeObject = PhysicObject.STATIC;
                        } else {
                            if (stringTypeObject.equals("Kinematic")) {
                                typeObject = PhysicObject.KINEMATIC;
                            } else {
                                if (stringTypeObject.equals("Dinamic")) {
                                    typeObject = PhysicObject.DINAMIC;
                                }
                            }
                        }
                    } else {
                        typeObject = PhysicObject.STATIC;
                    }

                    SheetParse colorParse = sheetParseLevelObject.findSheetParseByName("Color");
                    Color color = null;
                    //thera are color config in file
                    if (colorParse != null) {
                        //if color consistf of r,g,b
                        if (colorParse.isComplex()) {
                            int r, g, b;
                            if (colorParse.findSheetParseByName("Red") != null) {
                                r = Integer.parseInt(colorParse.findSheetParseByName("Red").getValue());
                            } else {
                                r = (byte) 255;
                            }
                            if (colorParse.findSheetParseByName("Green") != null) {
                                g = Integer.parseInt(colorParse.findSheetParseByName("Green").getValue());
                            } else {
                                g = (byte) 255;
                            }
                            if (colorParse.findSheetParseByName("Blue") != null) {
                                b = Integer.parseInt(colorParse.findSheetParseByName("Blue").getValue());
                            } else {
                                b = (byte) 255;
                            }
                            color = new Color(r, g, b);
                        } else {
                            //todo
                        }
                    } else {
                        color = (Color) Color.BLUE;
                    }


                    Polygon polygon = new Polygon(new Vector2f(Xc, Yc), angle, listVertex, typeObject, color);
                    level.gameObjects.add(polygon);
                    continue;
                }
                //circle
                if (sheetParseLevelObject.getName().equals("Circle")) {
                    //basic position
                    if (sheetParseLevelObject.findSheetParseByName("x") != null) {
                        x = Integer.parseInt(sheetParseLevelObject.findSheetParseByName("x").getValue());
                    } else {
                        continue;
                    }
                    if (sheetParseLevelObject.findSheetParseByName("y") != null) {
                        y = Integer.parseInt(sheetParseLevelObject.findSheetParseByName("y").getValue());
                    } else {
                        continue;
                    }

                    Vector2f circlePosition = new Vector2f(x, y);

                    float angle;

                    if (sheetParseLevelObject.findSheetParseByName("Angle") != null) {
                        angle = Float.parseFloat(sheetParseLevelObject.findSheetParseByName("Angle").getValue());
                    } else {
                        continue;
                    }

                    float radius;
                    if (sheetParseLevelObject.findSheetParseByName("Radius") != null) {
                        radius = Float.parseFloat(sheetParseLevelObject.findSheetParseByName("Radius").getValue());
                    } else {
                        continue;
                    }

                    //todo change this
                    int typeObject = 0;
                    if (sheetParseLevelObject.findSheetParseByName("TypeObject") != null) {
                        String stringTypeObject = sheetParseLevelObject.findSheetParseByName("TypeObject").getValue();
                        if (stringTypeObject.equals("Static")) {
                            typeObject = PhysicObject.STATIC;
                        } else {
                            if (stringTypeObject.equals("Kinematic")) {
                                typeObject = PhysicObject.KINEMATIC;
                            } else {
                                if (stringTypeObject.equals("Dinamic")) {
                                    typeObject = PhysicObject.DINAMIC;
                                }
                            }
                        }
                    } else {
                        typeObject = PhysicObject.STATIC;
                    }

                    SheetParse colorParse = sheetParseLevelObject.findSheetParseByName("Color");
                    Color color = null;
                    //thera are color config in file
                    if (colorParse != null) {
                        //if color consistf of r,g,b
                        if (colorParse.isComplex()) {
                            int r, g, b;
                            if (colorParse.findSheetParseByName("Red") != null) {
                                r = Integer.parseInt(colorParse.findSheetParseByName("Red").getValue());
                            } else {
                                r = (byte) 255;
                            }
                            if (colorParse.findSheetParseByName("Green") != null) {
                                g = Integer.parseInt(colorParse.findSheetParseByName("Green").getValue());
                            } else {
                                g = (byte) 255;
                            }
                            if (colorParse.findSheetParseByName("Blue") != null) {
                                b = Integer.parseInt(colorParse.findSheetParseByName("Blue").getValue());
                            } else {
                                b = (byte) 255;
                            }
                            color = new Color(r, g, b);
                        } else {
                            //todo
                        }
                    } else {
                        color = (Color) Color.BLUE;
                    }

                    Circle circle = new Circle(circlePosition, radius, angle, typeObject, color);
                    level.gameObjects.add(circle);

                    continue;
                }

                //asteroid
                if (sheetParseLevelObject.getName().equals("Asteroid")) {
                    //basic position
                    if (sheetParseLevelObject.findSheetParseByName("x") != null) {
                        x = Integer.parseInt(sheetParseLevelObject.findSheetParseByName("x").getValue());
                    } else {
                        continue;
                    }
                    if (sheetParseLevelObject.findSheetParseByName("y") != null) {
                        y = Integer.parseInt(sheetParseLevelObject.findSheetParseByName("y").getValue());
                    } else {
                        continue;
                    }

                    Vector2f circlePosition = new Vector2f(x, y);

                    float angle;

                    if (sheetParseLevelObject.findSheetParseByName("Angle") != null) {
                        angle = Float.parseFloat(sheetParseLevelObject.findSheetParseByName("Angle").getValue());
                    } else {
                        continue;
                    }

                    float radius;
                    if (sheetParseLevelObject.findSheetParseByName("Radius") != null) {
                        radius = Float.parseFloat(sheetParseLevelObject.findSheetParseByName("Radius").getValue());
                    } else {
                        continue;
                    }

                    int typeObject = PhysicObject.DINAMIC;

                    SheetParse colorParse = sheetParseLevelObject.findSheetParseByName("Color");
                    Color color = null;
                    //thera are color config in file
                    if (colorParse != null) {
                        //if color consistf of r,g,b
                        if (colorParse.isComplex()) {
                            int r, g, b;
                            if (colorParse.findSheetParseByName("Red") != null) {
                                r = Integer.parseInt(colorParse.findSheetParseByName("Red").getValue());
                            } else {
                                r = (byte) 255;
                            }
                            if (colorParse.findSheetParseByName("Green") != null) {
                                g = Integer.parseInt(colorParse.findSheetParseByName("Green").getValue());
                            } else {
                                g = (byte) 255;
                            }
                            if (colorParse.findSheetParseByName("Blue") != null) {
                                b = Integer.parseInt(colorParse.findSheetParseByName("Blue").getValue());
                            } else {
                                b = (byte) 255;
                            }
                            color = new Color(r, g, b);
                        } else {
                            //todo
                        }
                    } else {
                        color = (Color) Color.BLUE;
                    }

                    Asteroid asteroid = new Asteroid(circlePosition, radius, angle, typeObject, color);
                    level.gameObjects.add(asteroid);

                    continue;
                }
                //ship
                if (sheetParseLevelObject.getName().equals("ShipPlayer1")) {
                    //basic position
                    if (sheetParseLevelObject.findSheetParseByName("x") != null) {
                        x = Integer.parseInt(sheetParseLevelObject.findSheetParseByName("x").getValue());
                    } else {
                        System.out.println("Ship !parse default! x");
                        x = 0;
                    }
                    if (sheetParseLevelObject.findSheetParseByName("y") != null) {
                        y = Integer.parseInt(sheetParseLevelObject.findSheetParseByName("y").getValue());
                    } else {
                        System.out.println("Ship !parse default! y");
                        y = 0;
                    }
                    Vector2f shipPosition = new Vector2f(x, y);
                    Ship ship = new Ship(shipPosition);
                    level.gameObjects.add(ship);
                    continue;
                }
                /*
       if (levelObject.getName().equals("Square")) {

           continue;
       }         */


            }
        }


        if (!(libraryLevel.containsKey(level.additionalName)))

        {
            libraryLevel.put(level.additionalName, level);
        }

    }

    public static Level getLevel(Game game, String nameLevel) {
        //get random level
        if (nameLevel.equals("any")) {
            //calculate random level from list,
            int randomLevel = Global.random.nextInt(libraryLevel.size());
            //get name for get obj level
            nameLevel = new String((String) libraryLevel.keySet().toArray()[randomLevel]);
        }
        //get etalon example from library
        Level level = (Level) libraryLevel.get(nameLevel);
        // cloning etalon
        Level cloneLevel = Level(level);
        //adding info actual game, get memory
        cloneLevel.game = game;
        cloneLevel.aabb = new AABB(new Vec2(), new Vec2(cloneLevel.widthLevel / 30,
                cloneLevel.heightLevel / 30));
        cloneLevel.world = new World(new Vec2(cloneLevel.gravity.x, cloneLevel.gravity.y), false);
        cloneLevel.world.queryAABB(new QueryCallback() {
            @Override
            public boolean reportFixture(Fixture fixture) {
                // TODO Auto-generated method stub
                return true;
            }
        }, cloneLevel.aabb);
        cloneLevel.world.setContactListener(new CollisionListener(cloneLevel));
        cloneLevel.emitterEffects = new EmitterEffects(cloneLevel);
        for (GameObject gameObject : cloneLevel.gameObjects) {
            ((MorfingCreation) gameObject).initInPhysicWorld(cloneLevel);
        }

        gameLevel.put(new LightInteger(level.id), cloneLevel);
        return cloneLevel;
    }

    //copy all field nead
    //copy constructor
    private static Level Level(Level targetLevel) {
        Level cloneLevel = new Level();
        cloneLevel.additionalName = new String(targetLevel.additionalName);
        cloneLevel.widthLevel = targetLevel.widthLevel;
        cloneLevel.heightLevel = targetLevel.heightLevel;
        cloneLevel.airResist = targetLevel.airResist;
        cloneLevel.description = targetLevel.description;
        cloneLevel.gravity = new Vector2f(targetLevel.gravity);

        cloneLevel.gameObjects = new ArrayList<GameObject>();
        for (GameObject gameObject : targetLevel.gameObjects) {
            //lolc code.
            cloneLevel.gameObjects.add((GameObject) ((IsClonable) gameObject).clone());
        }
        return cloneLevel;
    }

    public void destroyLevel() {
        for (GameObject gameObject : gameObjects) {
            gameObject.setLive(false);
            gameObject.destroy();
        }
        emitterEffects = null;
        gameObjects.clear();
        gameObjects = null;
        gameObjectsToAdd.clear();
        gameObjectsToAdd = null;
        gameObjectsToDelete.clear();
        gameObjectsToDelete = null;
    }


    //sleep all object this level
    public void sleep() {

    }

    //wake up all object this level
    public void wakeUp() {

    }

}
