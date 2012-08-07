/**
* @author Andreyuk Artyom happydroidx@gmail.com
* @version 1.0
*/
package controller;

import java.util.ArrayDeque;
import java.util.Deque;

import org.lwjgl.LWJGLException;

import main.Engine;

class Keyboard implements Engine{

	Deque buffer; 
	
	Keyboard(){
		buffer = new ArrayDeque<Integer>();
	}
	
	//TODO check this method	
	@Override
	public void tick() {
		//if window was lost fokus, need update keyboard
		if (!org.lwjgl.input.Keyboard.isCreated()){
			createKeyboard();
		}
		
		
	}

	@Override
	public void cleanUp() {
		org.lwjgl.input.Keyboard.destroy();
	}
	
	private void createKeyboard(){
		try {
			org.lwjgl.input.Keyboard.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	
	

}
