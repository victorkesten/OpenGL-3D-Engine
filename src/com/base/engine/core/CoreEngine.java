package com.base.engine.core;

import com.base.engine.rendering.RenderingEngine;
import com.base.engine.rendering.Window;

public class CoreEngine {

	private boolean isRunning;
	private Game game;
	private RenderingEngine renderingEngine;
	
	private int width;
	private int height;
	private double frameTime;
	
	/**
	 * Initialize the core engine class. Specify the wanted framerate, height, width of the class.
	 * Set isRunning to false in order to initialize the Window.
	 * Insert a game variable. 
	 * @param width
	 * @param height
	 * @param framerate
	 * @param game
	 */
	public CoreEngine(int width, int height, double framerate, Game game){
		this.isRunning = false;
		this.game = game;
		this.width = width;
		this.height = height;
		this.frameTime = 1 / framerate;
		game.setEngine(this);
	}

	/**
	 * Create a window with parameter 'title' as the name, width and height as dimensions.
	 * We also initialize our Rendering Engine with this window.
	 * @param title
	 */
	public void createWindow(String title){
		Window.createWindow(width, height, title);
		this.renderingEngine = new RenderingEngine();
	}
	
	/**
	 * Starts the game. if isRunning is true, we're done;
	 */
	public void start(){
		if(isRunning){
			return;
		}
		run();
	}
	
	/**
	 * Stops the game. Sets isRunning to false;
	 */
	public void stop(){
		if(!isRunning){
			return;
		}
		isRunning = false;
	}

	/**
	 * Our game loop.
	 */
	private void run(){
		
		int frames = 0;
		double frameCounter = 0; 	//Was set to long for some reason.
		//Creates and adds all meshes/lights to our game.
		game.init();
		double lastTime = Time.getTime();
		double unprocessedTime = 0;
		
		isRunning = true;
		while(isRunning){
			
			boolean render = false;
			
			//Our frames per second system.
			//Takes the current start time and subtracts the lastTime.
			//The unprocessed time is the accumulated time taken to process the loop.
			//frameCounter is to ensure we still establish the same amount despite different framerate.
			double startTime = Time.getTime();
			double pastTime = startTime - lastTime;
			lastTime = startTime;
			unprocessedTime += pastTime;
			frameCounter += pastTime;
//			System.out.println(unprocessedTime + "AND " + frameTime);
			//The game updating is independent of frame rate.
			//If we render at a frameTime of 60, we will get that in game time.
			while(unprocessedTime > frameTime){
				unprocessedTime -= frameTime;
				render = true;
				if(Window.isCloseRequested()){
					stop();
				}
				
				//TODO: Update Game
				
				game.input((float)frameTime);
				Input.update();
				game.update((float)frameTime);
				
				if(frameCounter >= 1.0f){
					System.out.println(frames+"fps");
					frames = 0;
					frameCounter = 0;
				}
			}
			//We render.
			if(render){
				game.render(renderingEngine);
				Window.render();
				frames++;
			}  else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		cleanUp();
	}
	
	/**
	 * We dispose of our window and clear GL cache.
	 */
	private void cleanUp(){
		Window.dispose();
	}
	
	public RenderingEngine getRenderingEngine(){
		return renderingEngine;
	}

}
