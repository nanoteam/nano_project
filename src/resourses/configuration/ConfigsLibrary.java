package resourses.configuration;
//Parse configuration files, storage link on obj with parsing files

import logic.entity.ship.Ammo;
import logic.entity.ship.Weapon;
import logic.Level;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

//singlton
public final class ConfigsLibrary {
    private static ConfigsLibrary configsLibrary;
    private Parser parser;
    private Map<String, SheetParse> mapConfigurations;
    public static final String pathToSetting = "res/settings.ini";
    public static final String pathToWeaponFolder = "res/weapon";
    public static final String pathToAmmoFolder = "res/ammo";
    public static final String pathToLevelFolder = "res/level";

    private ConfigsLibrary() {
        mapConfigurations = new HashMap<String, SheetParse>();
        //some main game files parse on start ConfigsLibrary
        getConfig(pathToSetting);

        //dir weapon
        File dirWeapons = new File(pathToWeaponFolder);
        File[] fileWeapon = dirWeapons.listFiles();

        for (int i = 0; i < fileWeapon.length; i++) {
            if (fileWeapon[i].isDirectory()){
                continue;
            }
            Weapon.addWeaponToLibrary(getConfig(fileWeapon[i].getAbsolutePath()));
        }

        //dir ammo
        File dirAmmo = new File(pathToAmmoFolder);
        File[] fileAmmo = dirAmmo.listFiles();

        for (int i = 0; i < fileAmmo.length; i++) {
            if (fileAmmo[i].isDirectory()){
                continue;
            }
            Ammo.addAmmoToLibrary(getConfig(fileAmmo[i].getAbsolutePath()));
        }

        //dir level
        File dirLevel = new File(pathToLevelFolder);
        File[] fileLevel = dirLevel.listFiles();

        for (int i = 0; i < fileLevel.length; i++) {
            if (fileLevel[i].isDirectory()){
                continue;
            }
            Level.addLevelToLibrary(getConfig(fileLevel[i].getAbsolutePath()));
        }


    }

    public static ConfigsLibrary get() {
        if (null == configsLibrary) {
            configsLibrary = new ConfigsLibrary();
            return configsLibrary;
        } else {
            return configsLibrary;
        }
    }

    public SheetParse getConfig(String pathToFile) {
        if (mapConfigurations.containsKey(pathToFile)) {
            return mapConfigurations.get(pathToFile);
        } else {
            try {
                SheetParse parse = Parser.startParser(pathToFile);
                mapConfigurations.put(pathToFile, parse);
                return parse;
            } catch (Exception e) {
                //some action
                System.out.println("Error in ConfigsLibrary:parseFile" + pathToFile);
                return null;
            }
        }
    }
}
