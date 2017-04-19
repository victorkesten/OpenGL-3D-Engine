package com.base.game;

import com.base.engine.core.CoreEngine;

public class Main {
	
	/**
	 * Main class. 
	 * Start the 3D Engine. Specify a screen size and frame counter. 
	 */
	public static void main(String[] args){
		CoreEngine engine = new CoreEngine(800,600,60, new TestGame());
		engine.createWindow("3D Engine");
		engine.start();
	}
}
