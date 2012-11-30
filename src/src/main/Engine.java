/**
* @author Andreyuk Artyom happydroidx@gmail.com
* @version 1.0
*/
package main;
//interface for difrent part of game engines: physic, sound, render
//any engine must support this interface
public interface Engine {
	public void tick();
	public void cleanUp();
}
