package resourses.configuration;
//Parse configuration files, storage link on obj with parsing files
import java.util.HashMap;
import java.util.Map;
//singlton
public final class ConfigsLibrary {
    private static ConfigsLibrary configsLibrary;
    private Parser parser;
    private Map<String, SheetParse> mapConfigurations;
    public static final String pathToSetting = "d:/settings.ini";

    private ConfigsLibrary() {
    	mapConfigurations = new HashMap<String, SheetParse>();
        //some main game files parse on start ConfigsLibrary
        getConfig(pathToSetting);
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
                System.out.println(parse);
                
                return parse;
            } catch (Exception e) {
                //some action
                System.out.println("ConfigsLibrary:parseFile");
                System.out.println("One or more configuration files has error, application can work with error");
                return null;
            }
        }
    }
}
