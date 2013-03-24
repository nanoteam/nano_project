package logic.entity.ship;

import logic.Level;
import logic.entity.GamePhysicObject;
import logic.entity.entityInterface.IsClonable;
import main.Global;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;
import physic.Material;
import physic.PhysicObject;
import render.RenderUtil;
import resourses.configuration.SheetParse;
import util.MathUtil;

import java.util.HashMap;
import java.util.Map;

public class Ammo extends GamePhysicObject implements IsClonable {

    private static Map<String, Ammo> libraryAmmo = new HashMap<String, Ammo>();

    private float radius;
    // <0 == never.
    private float timeToDetonate;
    // <0 == never
    private float impulseToDetonate;
    // <0  == never
    private float impulseToDestruction;
    // <0 - nothing
    private float forceTraction;
    // <0 - nothing
    private float airResistant;
    //0.5 - no gravity, 1 - anti gravity, [0..1]
    private float percentageAntiMass = 0;
    private boolean haveTimer = false;
    private boolean haveImpulseDetonator = false;
    private boolean haveForceTraction = false;
    private boolean haveWarHead = false;
    private boolean haveAntiMass = false;
    private String ammoWarHead;
    private int countParticle;
    private int speedParticle;
    private Color color;
    private Material material;

    private Ammo() {

    }

    @Override
    public void update() {
        if (haveTimer) {
            timeToDetonate--;
            if (timeToDetonate < 0) {
                live = false;
                physicObject.getBody().setActive(false);
            }
        }
        if (haveImpulseDetonator) {

        }

    }

    @Override
    public void move() {
        position = physicObject.getPosition();
        angle = physicObject.getAngle();
        if (haveForceTraction) {
            float forceX = (float) (Math.cos(angle) * forceTraction);
            float forceY = (float) (Math.sin(angle) * forceTraction);
            physicObject.applyForce(forceX, forceY, new Vector2f(position));
            //apply force more realistic
            //physicObject.applyForce(forceX, forceY,  new Vector2f(position.x + MathUtil.newXTurn(-radius, 0, angle), position.y + MathUtil.newYTurn(-radius, 0, angle)));
        }

        if (haveAntiMass) {
            physicObject.applyForce(-physicObject.getMass() * level.getGravity().x * percentageAntiMass*2, -physicObject.getMass() * level.getGravity().y * percentageAntiMass*2, new Vector2f(position));
        }


    }

    @Override
    public void draw() {
        RenderUtil.drawCircle(new Vector2f(position), radius, 3, color);
        RenderUtil.drawLine(
                new Vector2f(position.x + MathUtil.newXTurn(radius, 0, angle), position.y + MathUtil.newYTurn(radius, 0, angle)),
                new Vector2f(position.x, position.y), 5, color);
        //  new Vector2f(position.x + MathUtil.newXTurn(-radius, 0, angle), position.y + MathUtil.newYTurn(-radius, 0, angle)), 5, color);
    }

    @Override
    public void playSound() {
        // TODO Auto-generated method stub

    }

    @Override
    public void toThink() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void destroy() {
        /*
        for (int i = 0; i < 10; i++) {

            level.getNotAddedGameObjects()
                    .add(new Particle(new Vector2f(position), new Vector2f(
                            (Global.random.nextFloat() - 0.5f) * 25f,
                            (Global.random.nextFloat() - 0.5f) * 25f),
                            3, 30 + Global.random.nextInt(20), (Color) Color.GREEN));
        }

        */
        Vector2f speedFatherShell = physicObject.getSpeed();
        physicObject.destroy();
        if (haveWarHead) {
            for (int i = 0; i < countParticle; i++) {
                float angle = (float) (Global.random.nextFloat() * 2f * Math.PI);
                float speedRandom = Global.random.nextFloat() * speedParticle;
                Vector2f speedParticleVector = new Vector2f((float) (Math.cos(angle) * speedRandom + speedFatherShell.x),
                        (float) (Math.sin(angle) * speedRandom + speedFatherShell.y));
                //magic constant for disable collise between particle
                level.getNotAddedGameObjects().add(Ammo.getAmmo(new Vector2f(position.x + Global.random.nextInt((int) radius), position.y + Global.random.nextInt((int) radius)), speedParticleVector, level, ammoWarHead));
                //level.getNotAddedGameObjects().add(new Particle(new Vector2f(position), speedParticleVector, 5, 300, color));
            }
        }
        //level.getNotAddedGameObjects().add(new Explosion(level, position, 1, 1));

    }

    public float getRadius() {
        return radius;
    }

    //warning! In case Ammo there are ierarxail seriziable,
    //also, type of material important for ammo!
    public static void addAmmoToLibrary(SheetParse config) {

        SheetParse mainConfig = config.findSheetParseByName("Ammo");
        Ammo ammo = new Ammo();

        if (mainConfig.findSheetParseByName("AdditionalName") != null) {
            ammo.additionalName = mainConfig.findSheetParseByName("AdditionalName").getValue();
        } else {
            ammo.additionalName = "unknow";
            System.out.println("Ammo !parse default! additionalName");
        }

        if (mainConfig.findSheetParseByName("Radius") != null) {
            ammo.radius = Float.parseFloat(mainConfig.findSheetParseByName("Radius").getValue());
        } else {
            ammo.radius = 50;
            System.out.println("Ammo !parse default! Radius");
        }

        if (mainConfig.findSheetParseByName("PercentageAntiMass") != null) {
            ammo.percentageAntiMass = Float.parseFloat(mainConfig.findSheetParseByName("PercentageAntiMass").getValue());
            if (ammo.percentageAntiMass > 0) {
                ammo.haveAntiMass = true;
            }
        } else {
            ammo.percentageAntiMass = 0;
            ammo.haveAntiMass = false;
            System.out.println("Ammo !parse default! PercentageAntiMass");
        }

        //this is etalon obj, and not need to create pbj in phus world
        if (mainConfig.findSheetParseByName("Material") != null) {
            ammo.material = Material.valueOf(mainConfig.findSheetParseByName("Material").getValue());
            System.out.println(ammo.material.toString());
            //ammo.physicObject = PhysicObject.createBall(null, null, ammo.radius, ammo.material);
        } else {
            //ammo.physicObject = PhysicObject.createBall(null, null, ammo.radius, Material.Metal);
            System.out.println("Ammo !parse default! Material");
        }

        if (mainConfig.findSheetParseByName("TimeToDetonate") != null) {
            ammo.timeToDetonate = Float.parseFloat(mainConfig.findSheetParseByName("TimeToDetonate").getValue());
            if (ammo.timeToDetonate > 0) {
                ammo.haveTimer = true;
            }
        } else {
            ammo.timeToDetonate = 10;
            ammo.haveTimer = true;
            System.out.println("Ammo !parse default! TimeToDetonate");
        }

        if (mainConfig.findSheetParseByName("ImpulseToDetonate") != null) {
            ammo.impulseToDetonate = Float.parseFloat(mainConfig.findSheetParseByName("ImpulseToDetonate").getValue());
        } else {
            ammo.impulseToDetonate = -1;
            System.out.println("Ammo !parse default!");
        }

        if (mainConfig.findSheetParseByName("ImpulseToDestruction") != null) {
            ammo.impulseToDestruction = Float.parseFloat(mainConfig.findSheetParseByName("ImpulseToDestruction").getValue());
        } else {
            ammo.impulseToDestruction = -1;
            System.out.println("Ammo !parse default! ImpulseToDestruction");
        }

        if (mainConfig.findSheetParseByName("ForceTraction") != null) {
            ammo.forceTraction = Float.parseFloat(mainConfig.findSheetParseByName("ForceTraction").getValue());
            if (ammo.forceTraction > 0) {
                ammo.haveForceTraction = true;
            }
        } else {
            ammo.forceTraction = -1;
            System.out.println("Ammo !parse default! ForceTraction");
        }
        SheetParse warHeadParse = mainConfig.findSheetParseByName("WarHead");
        if (warHeadParse != null) {
            // if in warHead config error, ammo.haveWarHead = false;
            if (warHeadParse.isComplex()) {
                ammo.haveWarHead = true;
                if (warHeadParse.findSheetParseByName("AmmoWarHead") != null) {
                    ammo.ammoWarHead = (mainConfig.findSheetParseByName("AmmoWarHead").getValue());
                } else {
                    ammo.haveWarHead = false;
                }
                if (warHeadParse.findSheetParseByName("CountParticle") != null) {
                    ammo.countParticle = Integer.parseInt(mainConfig.findSheetParseByName("CountParticle").getValue());
                } else {
                    ammo.haveWarHead = false;
                }
                if (warHeadParse.findSheetParseByName("SpeedParticle") != null) {
                    ammo.speedParticle = Integer.parseInt(mainConfig.findSheetParseByName("SpeedParticle").getValue());
                } else {
                    ammo.haveWarHead = false;
                }
            }
        }

        SheetParse colorParse = mainConfig.findSheetParseByName("Color");
        //thera are color config in file
        if (colorParse != null) {
            //if color consistf of r,g,b
            if (colorParse.isComplex()) {
                int r, g, b;
                if (colorParse.findSheetParseByName("Red") != null) {
                    r = Integer.parseInt(mainConfig.findSheetParseByName("Red").getValue());
                } else {
                    r = (byte) 255;
                }
                if (colorParse.findSheetParseByName("Green") != null) {
                    g = Integer.parseInt(mainConfig.findSheetParseByName("Green").getValue());
                } else {
                    g = (byte) 255;
                }
                if (colorParse.findSheetParseByName("Blue") != null) {
                    b = Integer.parseInt(mainConfig.findSheetParseByName("Blue").getValue());
                } else {
                    b = (byte) 255;
                }
                ammo.color = new Color(r, g, b);
            }
            //if have color by name like "red", "black"
            //todo add and test work this function
            else {
                String color = mainConfig.findSheetParseByName("Color").getValue();
                /*switch (color) {
                    case "Red": {
                        ammo.color = (Color) Color.RED;
                        break;
                    }
                    case "Blue": {
                        break;
                    }
                    case "Yellow": {
                        break;
                    }
                    case "Green": {
                        break;
                    }
                    case "Pink": {
                        break;
                    }
                    case "Grey": {
                        break;
                    }
                }
            }else{
                ammo.color = (Color) Color.GREEN;
            }     */
                ammo.color = (Color) Color.RED;
            }
        }

        if (!(libraryAmmo.containsKey(ammo.additionalName)))

        {
            libraryAmmo.put(ammo.additionalName, ammo);
        }

    }

    public static Ammo getAmmo(Vector2f position, Vector2f speed, Level level, String nameAmmo) {
        //get random ammo
        if (nameAmmo.equals("any")) {
            //calculate random ammo from list,
            int randomAmmo = Global.random.nextInt(libraryAmmo.size());
            //get name for get obj ammo
            nameAmmo = new String((String) libraryAmmo.keySet().toArray()[randomAmmo]);
        }
        //get etalon example from library
        Ammo ammo = (Ammo) libraryAmmo.get(nameAmmo);
        // cloning etalon
        Ammo cloneAmmo = ammo.clone();
        //adding info actual game
        cloneAmmo.level = level;
        cloneAmmo.physicObject = PhysicObject.createBall(cloneAmmo, position, cloneAmmo.radius, cloneAmmo.material, PhysicObject.DINAMIC, level.getWorld());
        cloneAmmo.physicObject.setPosition(position);
        cloneAmmo.position = position;
        cloneAmmo.physicObject.setSpeed(speed);
        return cloneAmmo;
    }

    //copy all field nead
    //copy constructor
    public Ammo clone() {
        Ammo cloneAmmo = new Ammo();
        cloneAmmo.additionalName = new String(additionalName);
        cloneAmmo.radius = radius;
        cloneAmmo.material = material;
        cloneAmmo.forceTraction = forceTraction;
        cloneAmmo.percentageAntiMass = percentageAntiMass;
        cloneAmmo.impulseToDestruction = impulseToDestruction;
        cloneAmmo.impulseToDetonate = impulseToDetonate;
        cloneAmmo.timeToDetonate = (timeToDetonate) + Global.random.nextInt((int) (timeToDetonate / 10f));
        cloneAmmo.airResistant = airResistant;
        cloneAmmo.haveTimer = haveTimer;
        cloneAmmo.haveForceTraction = haveForceTraction;
        cloneAmmo.haveImpulseDetonator = haveImpulseDetonator;
        cloneAmmo.haveAntiMass = haveAntiMass;
        cloneAmmo.color = color;
        cloneAmmo.haveWarHead = haveWarHead;
        cloneAmmo.ammoWarHead = ammoWarHead;
        cloneAmmo.countParticle = countParticle;
        cloneAmmo.speedParticle = speedParticle;
        return cloneAmmo;
    }
}