package logic.entity.ship;

import logic.entity.ArsenalGameObject;
import logic.entity.GameObjectMoving;
import logic.entity.ammo.RubberBall;
import logic.entity.ammo.WarHead;
import main.Global;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;
import render.RenderUtil;
import resourses.configuration.SheetParse;
import util.MathUtil;

import java.util.HashMap;
import java.util.Map;

public class Weapon extends ArsenalGameObject {
    private final static int LENGTH_BETWEEN_SPAWN_AREA_AMMO_AND_WEAPON = 5;
    private int width;
    private int height;
    //if <0 then can shoot
    private int reloadingTimerPrimary = 0;
    private int reloadingTimerAlternative = 0;

    private Color color;
    //for storage object from parser
    private static Map<String, Weapon> libraryWeapon = new HashMap<String, Weapon>();

    private Weapon() {
    }

    @Override
    public void update() {
        //primary weapon
        if (onShootPrimary) {
            //id relaod complited
            if (reloadingTimerPrimary < 0) {
                //create shoot

                Vector2f shell_position = new Vector2f(position.x +
                        MathUtil.newXTurn(width + LENGTH_BETWEEN_SPAWN_AREA_AMMO_AND_WEAPON, 0, angle), position.y + MathUtil.newYTurn(width + LENGTH_BETWEEN_SPAWN_AREA_AMMO_AND_WEAPON, 0, angle));
                float shell_speed = (float) Math.sqrt(2 * kineticEnergyPrimary / RubberBall.STANDART_MASS);
                Ammo ammo;
                //optimization - style

                {
                    switch (countAmmoPrimaryPerShoot) {
                        case 1: {
                            Vector2f shell_speed_vector =
                                    new Vector2f(
                                            (float) (Math.cos(angle + randomizeFirePrimary * Math.random() - randomizeFirePrimary / 2f) * shell_speed),
                                            (float) Math.sin(angle + randomizeFirePrimary * Math.random() - randomizeFirePrimary / 2f) * shell_speed);
                            ammo = Ammo.getAmmo(shell_position, shell_speed_vector, level, nameAmmoPrimary);
                            level.getNotAddedGameObjects().add(ammo);
                            break;
                        }
                        case 2: {
                            Vector2f shell_speed_vector =
                                    new Vector2f(
                                            (float) (Math.cos(angle + randomizeFirePrimary * Math.random() - randomizeFirePrimary / 2f) * shell_speed),
                                            (float) Math.sin(angle + randomizeFirePrimary * Math.random() - randomizeFirePrimary / 2f) * shell_speed);
                            ammo = Ammo.getAmmo(shell_position, shell_speed_vector, level, nameAmmoPrimary);
                            level.getNotAddedGameObjects().add(ammo);
                            shell_speed_vector =
                                    new Vector2f(
                                            (float) (Math.cos(angle + randomizeFirePrimary * Math.random() - randomizeFirePrimary / 2f) * shell_speed),
                                            (float) Math.sin(angle + randomizeFirePrimary * Math.random() - randomizeFirePrimary / 2f) * shell_speed);
                            ammo = Ammo.getAmmo(shell_position, shell_speed_vector, level, nameAmmoPrimary);
                            level.getNotAddedGameObjects().add(ammo);
                            break;
                        }
                        case 3: {
                            Vector2f shell_speed_vector =
                                    new Vector2f(
                                            (float) (Math.cos(angle + randomizeFirePrimary * Math.random() - randomizeFirePrimary / 2f) * shell_speed),
                                            (float) Math.sin(angle + randomizeFirePrimary * Math.random() - randomizeFirePrimary / 2f) * shell_speed);
                            ammo = Ammo.getAmmo(shell_position, shell_speed_vector, level, nameAmmoPrimary);
                            level.getNotAddedGameObjects().add(ammo);
                            shell_speed_vector =
                                    new Vector2f(
                                            (float) (Math.cos(angle + randomizeFirePrimary * Math.random() - randomizeFirePrimary / 2f) * shell_speed),
                                            (float) Math.sin(angle + randomizeFirePrimary * Math.random() - randomizeFirePrimary / 2f) * shell_speed);
                            ammo = Ammo.getAmmo(shell_position, shell_speed_vector, level, nameAmmoPrimary);
                            level.getNotAddedGameObjects().add(ammo);
                            shell_speed_vector =
                                    new Vector2f(
                                            (float) (Math.cos(angle + randomizeFirePrimary * Math.random() - randomizeFirePrimary / 2f) * shell_speed),
                                            (float) Math.sin(angle + randomizeFirePrimary * Math.random() - randomizeFirePrimary / 2f) * shell_speed);
                            ammo = Ammo.getAmmo(shell_position, shell_speed_vector, level, nameAmmoPrimary);
                            level.getNotAddedGameObjects().add(ammo);
                            break;
                        }
                        case 4: {
                            Vector2f shell_speed_vector =
                                    new Vector2f(
                                            (float) (Math.cos(angle + randomizeFirePrimary * Math.random() - randomizeFirePrimary / 2f) * shell_speed),
                                            (float) Math.sin(angle + randomizeFirePrimary * Math.random() - randomizeFirePrimary / 2f) * shell_speed);
                            ammo = Ammo.getAmmo(shell_position, shell_speed_vector, level, nameAmmoPrimary);
                            level.getNotAddedGameObjects().add(ammo);
                            shell_speed_vector =
                                    new Vector2f(
                                            (float) (Math.cos(angle + randomizeFirePrimary * Math.random() - randomizeFirePrimary / 2f) * shell_speed),
                                            (float) Math.sin(angle + randomizeFirePrimary * Math.random() - randomizeFirePrimary / 2f) * shell_speed);
                            ammo = Ammo.getAmmo(shell_position, shell_speed_vector, level, nameAmmoPrimary);
                            level.getNotAddedGameObjects().add(ammo);
                            shell_speed_vector =
                                    new Vector2f(
                                            (float) (Math.cos(angle + randomizeFirePrimary * Math.random() - randomizeFirePrimary / 2f) * shell_speed),
                                            (float) Math.sin(angle + randomizeFirePrimary * Math.random() - randomizeFirePrimary / 2f) * shell_speed);
                            ammo = Ammo.getAmmo(shell_position, shell_speed_vector, level, nameAmmoPrimary);
                            level.getNotAddedGameObjects().add(ammo);
                            shell_speed_vector =
                                    new Vector2f(
                                            (float) (Math.cos(angle + randomizeFirePrimary * Math.random() - randomizeFirePrimary / 2f) * shell_speed),
                                            (float) Math.sin(angle + randomizeFirePrimary * Math.random() - randomizeFirePrimary / 2f) * shell_speed);
                            ammo = Ammo.getAmmo(shell_position, shell_speed_vector, level, nameAmmoPrimary);
                            level.getNotAddedGameObjects().add(ammo);
                            break;
                        }
                        case 5: {
                            Vector2f shell_speed_vector =
                                    new Vector2f(
                                            (float) (Math.cos(angle + randomizeFirePrimary * Math.random() - randomizeFirePrimary / 2f) * shell_speed),
                                            (float) Math.sin(angle + randomizeFirePrimary * Math.random() - randomizeFirePrimary / 2f) * shell_speed);
                            ammo = Ammo.getAmmo(shell_position, shell_speed_vector, level, nameAmmoPrimary);
                            level.getNotAddedGameObjects().add(ammo);
                            shell_speed_vector =
                                    new Vector2f(
                                            (float) (Math.cos(angle + randomizeFirePrimary * Math.random() - randomizeFirePrimary / 2f) * shell_speed),
                                            (float) Math.sin(angle + randomizeFirePrimary * Math.random() - randomizeFirePrimary / 2f) * shell_speed);
                            ammo = Ammo.getAmmo(shell_position, shell_speed_vector, level, nameAmmoPrimary);
                            level.getNotAddedGameObjects().add(ammo);
                            shell_speed_vector =
                                    new Vector2f(
                                            (float) (Math.cos(angle + randomizeFirePrimary * Math.random() - randomizeFirePrimary / 2f) * shell_speed),
                                            (float) Math.sin(angle + randomizeFirePrimary * Math.random() - randomizeFirePrimary / 2f) * shell_speed);
                            ammo = Ammo.getAmmo(shell_position, shell_speed_vector, level, nameAmmoPrimary);
                            level.getNotAddedGameObjects().add(ammo);
                            shell_speed_vector =
                                    new Vector2f(
                                            (float) (Math.cos(angle + randomizeFirePrimary * Math.random() - randomizeFirePrimary / 2f) * shell_speed),
                                            (float) Math.sin(angle + randomizeFirePrimary * Math.random() - randomizeFirePrimary / 2f) * shell_speed);
                            ammo = Ammo.getAmmo(shell_position, shell_speed_vector, level, nameAmmoPrimary);
                            level.getNotAddedGameObjects().add(ammo);
                            shell_speed_vector =
                                    new Vector2f(
                                            (float) (Math.cos(angle + randomizeFirePrimary * Math.random() - randomizeFirePrimary / 2f) * shell_speed),
                                            (float) Math.sin(angle + randomizeFirePrimary * Math.random() - randomizeFirePrimary / 2f) * shell_speed);
                            ammo = Ammo.getAmmo(shell_position, shell_speed_vector, level, nameAmmoPrimary);
                            level.getNotAddedGameObjects().add(ammo);
                            break;
                        }
                        default: {
                            for (int i = 0; i < countAmmoPrimaryPerShoot; i++) {
                                Vector2f shell_speed_vector =
                                        new Vector2f(
                                                (float) (Math.cos(angle + randomizeFirePrimary * Math.random() - randomizeFirePrimary / 2f) * shell_speed),
                                                (float) Math.sin(angle + randomizeFirePrimary * Math.random() - randomizeFirePrimary / 2f) * shell_speed);
                                ammo = Ammo.getAmmo(shell_position, shell_speed_vector, level, nameAmmoPrimary);
                                level.getNotAddedGameObjects().add(ammo);
                            }

                        }
                    }

                }
                //RubberBall rubberBall = new RubberBall(shell_position, shell_speed_vector, level);
                //action for game logic
                onShootPrimary = false;
                reloadingTimerPrimary = reloadTimePrimary;
            }
        }
        //alternative weapon
        if (onShootAlternative) {
            //id relaod complited
            if (reloadingTimerAlternative < 0) {
                //create shoot
                Ammo ammo;
                Vector2f shell_position = new Vector2f(position.x +
                        MathUtil.newXTurn(width + LENGTH_BETWEEN_SPAWN_AREA_AMMO_AND_WEAPON, 0, angle), position.y + MathUtil.newYTurn(width + LENGTH_BETWEEN_SPAWN_AREA_AMMO_AND_WEAPON, 0, angle));
                float shell_speed = (float) Math.sqrt(2 * kineticEnergyAlternative / (WarHead.STANDART_MASS));
                {
                    switch (countAmmoAlternativePerShoot) {
                        case 1: {
                            Vector2f shell_speed_vector =
                                    new Vector2f(
                                            (float) (Math.cos(angle + randomizeFireAlternative * Math.random() - randomizeFireAlternative / 2f) * shell_speed),
                                            (float) Math.sin(angle + randomizeFireAlternative * Math.random() - randomizeFireAlternative / 2f) * shell_speed);
                            ammo = Ammo.getAmmo(shell_position, shell_speed_vector, level, nameAmmoAlternative);
                            level.getNotAddedGameObjects().add(ammo);
                            break;
                        }
                        case 2: {
                            Vector2f shell_speed_vector =
                                    new Vector2f(
                                            (float) (Math.cos(angle + randomizeFireAlternative * Math.random() - randomizeFireAlternative / 2f) * shell_speed),
                                            (float) Math.sin(angle + randomizeFireAlternative * Math.random() - randomizeFireAlternative / 2f) * shell_speed);
                            ammo = Ammo.getAmmo(shell_position, shell_speed_vector, level, nameAmmoAlternative);
                            level.getNotAddedGameObjects().add(ammo);
                            shell_speed_vector =
                                    new Vector2f(
                                            (float) (Math.cos(angle + randomizeFireAlternative * Math.random() - randomizeFireAlternative / 2f) * shell_speed),
                                            (float) Math.sin(angle + randomizeFireAlternative * Math.random() - randomizeFireAlternative / 2f) * shell_speed);
                            ammo = Ammo.getAmmo(shell_position, shell_speed_vector, level, nameAmmoAlternative);
                            level.getNotAddedGameObjects().add(ammo);
                            break;
                        }
                        case 3: {
                            Vector2f shell_speed_vector =
                                    new Vector2f(
                                            (float) (Math.cos(angle + randomizeFireAlternative * Math.random() - randomizeFireAlternative / 2f) * shell_speed),
                                            (float) Math.sin(angle + randomizeFireAlternative * Math.random() - randomizeFireAlternative / 2f) * shell_speed);
                            ammo = Ammo.getAmmo(shell_position, shell_speed_vector, level, nameAmmoAlternative);
                            level.getNotAddedGameObjects().add(ammo);
                            shell_speed_vector =
                                    new Vector2f(
                                            (float) (Math.cos(angle + randomizeFireAlternative * Math.random() - randomizeFireAlternative / 2f) * shell_speed),
                                            (float) Math.sin(angle + randomizeFireAlternative * Math.random() - randomizeFireAlternative / 2f) * shell_speed);
                            ammo = Ammo.getAmmo(shell_position, shell_speed_vector, level, nameAmmoAlternative);
                            level.getNotAddedGameObjects().add(ammo);
                            shell_speed_vector =
                                    new Vector2f(
                                            (float) (Math.cos(angle + randomizeFireAlternative * Math.random() - randomizeFireAlternative / 2f) * shell_speed),
                                            (float) Math.sin(angle + randomizeFireAlternative * Math.random() - randomizeFireAlternative / 2f) * shell_speed);
                            ammo = Ammo.getAmmo(shell_position, shell_speed_vector, level, nameAmmoAlternative);
                            level.getNotAddedGameObjects().add(ammo);
                            break;
                        }
                        case 4: {
                            Vector2f shell_speed_vector =
                                    new Vector2f(
                                            (float) (Math.cos(angle + randomizeFireAlternative * Math.random() - randomizeFireAlternative / 2f) * shell_speed),
                                            (float) Math.sin(angle + randomizeFireAlternative * Math.random() - randomizeFireAlternative / 2f) * shell_speed);
                            ammo = Ammo.getAmmo(shell_position, shell_speed_vector, level, nameAmmoAlternative);
                            level.getNotAddedGameObjects().add(ammo);
                            shell_speed_vector =
                                    new Vector2f(
                                            (float) (Math.cos(angle + randomizeFireAlternative * Math.random() - randomizeFireAlternative / 2f) * shell_speed),
                                            (float) Math.sin(angle + randomizeFireAlternative * Math.random() - randomizeFireAlternative / 2f) * shell_speed);
                            ammo = Ammo.getAmmo(shell_position, shell_speed_vector, level, nameAmmoAlternative);
                            level.getNotAddedGameObjects().add(ammo);
                            shell_speed_vector =
                                    new Vector2f(
                                            (float) (Math.cos(angle + randomizeFireAlternative * Math.random() - randomizeFireAlternative / 2f) * shell_speed),
                                            (float) Math.sin(angle + randomizeFireAlternative * Math.random() - randomizeFireAlternative / 2f) * shell_speed);
                            ammo = Ammo.getAmmo(shell_position, shell_speed_vector, level, nameAmmoAlternative);
                            level.getNotAddedGameObjects().add(ammo);
                            shell_speed_vector =
                                    new Vector2f(
                                            (float) (Math.cos(angle + randomizeFireAlternative * Math.random() - randomizeFireAlternative / 2f) * shell_speed),
                                            (float) Math.sin(angle + randomizeFireAlternative * Math.random() - randomizeFireAlternative / 2f) * shell_speed);
                            ammo = Ammo.getAmmo(shell_position, shell_speed_vector, level, nameAmmoAlternative);
                            level.getNotAddedGameObjects().add(ammo);
                            break;
                        }
                        case 5: {
                            Vector2f shell_speed_vector =
                                    new Vector2f(
                                            (float) (Math.cos(angle + randomizeFireAlternative * Math.random() - randomizeFireAlternative / 2f) * shell_speed),
                                            (float) Math.sin(angle + randomizeFireAlternative * Math.random() - randomizeFireAlternative / 2f) * shell_speed);
                            ammo = Ammo.getAmmo(shell_position, shell_speed_vector, level, nameAmmoAlternative);
                            level.getNotAddedGameObjects().add(ammo);
                            shell_speed_vector =
                                    new Vector2f(
                                            (float) (Math.cos(angle + randomizeFireAlternative * Math.random() - randomizeFireAlternative / 2f) * shell_speed),
                                            (float) Math.sin(angle + randomizeFireAlternative * Math.random() - randomizeFireAlternative / 2f) * shell_speed);
                            ammo = Ammo.getAmmo(shell_position, shell_speed_vector, level, nameAmmoAlternative);
                            level.getNotAddedGameObjects().add(ammo);
                            shell_speed_vector =
                                    new Vector2f(
                                            (float) (Math.cos(angle + randomizeFireAlternative * Math.random() - randomizeFireAlternative / 2f) * shell_speed),
                                            (float) Math.sin(angle + randomizeFireAlternative * Math.random() - randomizeFireAlternative / 2f) * shell_speed);
                            ammo = Ammo.getAmmo(shell_position, shell_speed_vector, level, nameAmmoAlternative);
                            level.getNotAddedGameObjects().add(ammo);
                            shell_speed_vector =
                                    new Vector2f(
                                            (float) (Math.cos(angle + randomizeFireAlternative * Math.random() - randomizeFireAlternative / 2f) * shell_speed),
                                            (float) Math.sin(angle + randomizeFireAlternative * Math.random() - randomizeFireAlternative / 2f) * shell_speed);
                            ammo = Ammo.getAmmo(shell_position, shell_speed_vector, level, nameAmmoAlternative);
                            level.getNotAddedGameObjects().add(ammo);
                            shell_speed_vector =
                                    new Vector2f(
                                            (float) (Math.cos(angle + randomizeFireAlternative * Math.random() - randomizeFireAlternative / 2f) * shell_speed),
                                            (float) Math.sin(angle + randomizeFireAlternative * Math.random() - randomizeFireAlternative / 2f) * shell_speed);
                            ammo = Ammo.getAmmo(shell_position, shell_speed_vector, level, nameAmmoAlternative);
                            level.getNotAddedGameObjects().add(ammo);
                            break;
                        }
                        default: {
                            for (int i = 0; i < countAmmoPrimaryPerShoot; i++) {
                                Vector2f shell_speed_vector =
                                        new Vector2f(
                                                (float) (Math.cos(angle + randomizeFireAlternative * Math.random() - randomizeFireAlternative / 2f) * shell_speed),
                                                (float) Math.sin(angle + randomizeFireAlternative * Math.random() - randomizeFireAlternative / 2f) * shell_speed);
                                ammo = Ammo.getAmmo(shell_position, shell_speed_vector, level, nameAmmoAlternative);
                                level.getNotAddedGameObjects().add(ammo);
                            }

                        }
                    }

                }
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
        angle = level.getAngleBetweenShipAndCursor();
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

    public static void addWeaponToLibrary(SheetParse config) {

        SheetParse mainConfig = config.findSheetParseByName("Weapon");
        Weapon weapon = new Weapon();

        if (mainConfig.findSheetParseByName("AdditionalName") != null) {
            weapon.additionalName = mainConfig.findSheetParseByName("AdditionalName").getValue();
        } else {
            weapon.additionalName = "unknow";
        }

        if (mainConfig.findSheetParseByName("NameAmmoPrimary") != null) {
            weapon.nameAmmoPrimary = mainConfig.findSheetParseByName("NameAmmoPrimary").getValue();
        } else {
            weapon.nameAmmoPrimary = "unknow";
        }

        if (mainConfig.findSheetParseByName("NameAmmoAlternative") != null) {
            weapon.nameAmmoAlternative = mainConfig.findSheetParseByName("NameAmmoAlternative").getValue();
        } else {
            weapon.nameAmmoAlternative = "unknow";
        }

        if (mainConfig.findSheetParseByName("Mass") != null) {
            weapon.mass = Integer.parseInt(mainConfig.findSheetParseByName("Mass").getValue());
        } else {
            weapon.mass = 10;
        }

        if (mainConfig.findSheetParseByName("Width") != null) {
            weapon.width = Integer.parseInt(mainConfig.findSheetParseByName("Width").getValue());
        } else {
            weapon.width = 20;
        }

        if (mainConfig.findSheetParseByName("Height") != null) {
            weapon.height = Integer.parseInt(mainConfig.findSheetParseByName("Height").getValue());
        } else {
            weapon.height = 5;
        }

        if (mainConfig.findSheetParseByName("RandomizeFirePrimary") != null) {
            weapon.randomizeFirePrimary = Float.parseFloat(mainConfig.findSheetParseByName("RandomizeFirePrimary").getValue());
        } else {
            weapon.randomizeFirePrimary = 0.5f;
        }

        if (mainConfig.findSheetParseByName("RandomizeFireAlternative") != null) {
            weapon.randomizeFireAlternative = Float.parseFloat(mainConfig.findSheetParseByName("RandomizeFireAlternative").getValue());
        } else {
            weapon.randomizeFireAlternative = 0.3f;
        }

        if (mainConfig.findSheetParseByName("ReloadTimePrimary") != null) {
            weapon.reloadTimePrimary = Integer.parseInt(mainConfig.findSheetParseByName("ReloadTimePrimary").getValue());
        } else {
            weapon.reloadTimePrimary = 3000;
        }

        if (mainConfig.findSheetParseByName("ReloadTimeAlternative") != null) {
            weapon.reloadTimeAlternative = Integer.parseInt(mainConfig.findSheetParseByName("ReloadTimeAlternative").getValue());
        } else {
            weapon.reloadTimeAlternative = 3000;
        }

        if (mainConfig.findSheetParseByName("CountAmmoPrimaryPerShoot") != null) {
            weapon.countAmmoPrimaryPerShoot = Integer.parseInt(mainConfig.findSheetParseByName("CountAmmoPrimaryPerShoot").getValue());
        } else {
            weapon.countAmmoPrimaryPerShoot = 1;
        }

        if (mainConfig.findSheetParseByName("CountAmmoAlternativePerShoot") != null) {
            weapon.countAmmoAlternativePerShoot = Integer.parseInt(mainConfig.findSheetParseByName("CountAmmoAlternativePerShoot").getValue());
        } else {
            weapon.countAmmoAlternativePerShoot = 1;
        }

        if (mainConfig.findSheetParseByName("KineticEnergyPrimary") != null) {
            weapon.kineticEnergyPrimary = Float.parseFloat(mainConfig.findSheetParseByName("KineticEnergyPrimary").getValue());
        } else {
            weapon.kineticEnergyPrimary = 1000000f;
        }

        if (mainConfig.findSheetParseByName("KineticEnergyAlternative") != null) {
            weapon.kineticEnergyAlternative = Float.parseFloat(mainConfig.findSheetParseByName("KineticEnergyAlternative").getValue());
        } else {
            weapon.kineticEnergyAlternative = 1000000f;
        }

        if (mainConfig.findSheetParseByName("EnergyCoastPrimary") != null) {
            weapon.energyCoastPrimary = Float.parseFloat(mainConfig.findSheetParseByName("EnergyCoastPrimary").getValue());
        } else {
            weapon.energyCoastPrimary = 100f;
        }

        if (mainConfig.findSheetParseByName("EnergyCoastAlternative") != null) {
            weapon.energyCoastAlternative = Float.parseFloat(mainConfig.findSheetParseByName("EnergyCoastAlternative").getValue());
        } else {
            weapon.energyCoastAlternative = 100f;
        }


        if (mainConfig.findSheetParseByName("Color") != null) {


            /*String color = mainConfig.findSheetParseByName("Color").getValue();
            switch (color) {
                case "Red" : {
                    break;
                }
                case "Blue" : {
                    break;
                }
                case "Yellow" :{
                    break;
                }
                case "Green" :{
                    break;
                }
                case "Pink" :{
                    break;
                }
                case "Grey" :{
                    break;
                }

              */

            weapon.color = (Color) Color.RED;
        } else {
            weapon.color = (Color) Color.RED;
        }

        if (!(libraryWeapon.containsKey(weapon.additionalName))) {
            libraryWeapon.put(weapon.additionalName, weapon);
        }
    }

    public static Weapon getWeapon(GameObjectMoving gameObjectMoving, String nameWeapon) {
        //get random weapon
        if (nameWeapon.equals("any")) {
            //calculate random weapon from list,
            int randomWeapon = Global.random.nextInt(libraryWeapon.size());
            //get name for get obj Weapon
            nameWeapon = new String((String) libraryWeapon.keySet().toArray()[randomWeapon]);
        }
        //get etalon example from library
        Weapon weapon = (Weapon) libraryWeapon.get(nameWeapon);
        // cloning etalon
        Weapon cloneWeapon = Weapon(weapon);
        //adding GameObjectMoving
        cloneWeapon.addToGameObject(gameObjectMoving);
        return cloneWeapon;
    }

    //copy all field nead
    private static Weapon Weapon(Weapon targetWeapon) {
        Weapon cloneWeapon = new Weapon();

        cloneWeapon.width = targetWeapon.width;
        cloneWeapon.height = targetWeapon.height;

        cloneWeapon.kineticEnergyPrimary = targetWeapon.kineticEnergyPrimary;
        cloneWeapon.kineticEnergyAlternative = targetWeapon.kineticEnergyAlternative;
        cloneWeapon.mass = targetWeapon.mass;
        cloneWeapon.energyCoastPrimary = targetWeapon.energyCoastPrimary;
        cloneWeapon.energyCoastAlternative = targetWeapon.energyCoastAlternative;

        cloneWeapon.reloadTimePrimary = targetWeapon.reloadTimePrimary;
        cloneWeapon.reloadTimeAlternative = targetWeapon.reloadTimeAlternative;

        cloneWeapon.randomizeFirePrimary = targetWeapon.randomizeFirePrimary;
        cloneWeapon.randomizeFireAlternative = targetWeapon.randomizeFireAlternative;

        cloneWeapon.color = new Color(targetWeapon.color);
        cloneWeapon.additionalName = new String(targetWeapon.additionalName);
        cloneWeapon.nameAmmoPrimary = new String(targetWeapon.nameAmmoPrimary);
        cloneWeapon.nameAmmoAlternative = new String(targetWeapon.nameAmmoAlternative);

        cloneWeapon.countAmmoAlternativePerShoot = targetWeapon.countAmmoAlternativePerShoot;
        cloneWeapon.countAmmoPrimaryPerShoot = targetWeapon.countAmmoPrimaryPerShoot;

        return cloneWeapon;
    }

    public void addToGameObject(GameObjectMoving gameObject) {
        this.position = gameObject.getPosition();
        this.fatherObj = gameObject;
        this.level = gameObject.getLevel();
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