package com.tutorial.main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {
	
	private Handler handler;
	public boolean[] keyDown = new boolean[4];
	
	// delete handler and just make it like the sprite video. Make booleans public and check if their true in player class so you can create animation
	public KeyInput(Handler handler) {
		this.handler = handler;
		
		keyDown[0] = false;
		keyDown[1] = false;
		keyDown[2] = false;
		keyDown[3] = false;
		
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if (tempObject.getId() == ID.Player ) {
				//key events for Player 1
					if (key == KeyEvent.VK_W) {
						keyDown[0] = true;
						tempObject.setDirection("up");
						tempObject.setVelY(-5);
					}
					if (key == KeyEvent.VK_S) {
						keyDown[1] = true;
						tempObject.setDirection("down");
						tempObject.setVelY(5);
					}
					if (key == KeyEvent.VK_A) {
						keyDown[2] = true;
						tempObject.setDirection("left");
						tempObject.setVelX(-5);
					}
					if (key == KeyEvent.VK_D) {
						keyDown[3] = true;
						tempObject.setDirection("right");
						tempObject.setVelX(5);
					}
			}
		}
		
		if (key == KeyEvent.VK_ESCAPE)
			System.exit(1);
		
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if (tempObject.getId() == ID.Player ) {
				//key events for Player 1
				
				if (key == KeyEvent.VK_W)
					//tempObject.setVelY(0);
					keyDown[0] = false;
				if (key == KeyEvent.VK_S)
					//tempObject.setVelY(0);
					keyDown[1] = false;
				if (key == KeyEvent.VK_A)
					//tempObject.setVelX(0);
					keyDown[2] = false;
				if (key == KeyEvent.VK_D)
					//tempObject.setVelX(0);
					keyDown[3] = false;
				
				//vertical movement
				if (!keyDown[0] && !keyDown[1]) tempObject.setVelY(0);
				//horizontal movement
				if (!keyDown[2] && !keyDown[3]) tempObject.setVelX(0);
				
				//bug fix for going to the right after 'd' key is released
				if (keyDown[2] && !keyDown[3]) tempObject.setVelX(-5);
				//bug fix for going to the left after 'a' key is released
				if (!keyDown[2] && keyDown[3]) tempObject.setVelX(5);
			}
		}
	}

}
