package logic.entity.entityInterface;


import logic.Level;

//has two way creating object:
//1 - by construktor like Object(World world)
//2 - by construktor like Object(arg1,arg2,) and after method init(World world) to init, and  full creating entity
public interface MorfingCreation {
    void initInPhysicWorld(Level level);
}
