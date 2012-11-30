package ai.commands;

public abstract class Command {
    //special action
    //firing
    public static final int FIRE_TARGET = 0;
    public static final int FIRE_SHOOT = 1;
    public static final int FIRE_NONE = 2;
    //moving
    public static final int MOVE_TARGET = 10;
    public static final int MOVE_VECTOR = 11;
    public static final int MOVE_NONE = 12;

    protected static String name;
    
    public static String getName(){
        return name;
    }



}

