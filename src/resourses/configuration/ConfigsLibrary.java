package resourses.configuration;
//Parse configuration files, storage link on obj with parsing files

import logic.entity.ship.Weapon;

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


    private ConfigsLibrary() {
        mapConfigurations = new HashMap<String, SheetParse>();
        //some main game files parse on start ConfigsLibrary
        getConfig(pathToSetting);


        File dirWeapons = new File(pathToWeaponFolder);
        File[] fileWeapon = dirWeapons.listFiles();

        for (int i = 0; i < fileWeapon.length; i++) {
            if (fileWeapon[i].isDirectory()){
                continue;
            }
            Weapon.addWeaponToLibrary(getConfig(fileWeapon[i].getAbsolutePath()));
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
                System.out.println("ConfigsLibrary:parseFile");
                System.out.println("Error in " + pathToFile);
                return null;
            }
        }
    }
}
