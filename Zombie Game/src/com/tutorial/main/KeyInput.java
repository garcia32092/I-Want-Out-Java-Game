package com.tutorial.main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {
	
	private Handler handler;
	public boolean keyDown, keyUp, keyLeft, keyRight;
	
	// delete handler and just make it like the sprite video. Make booleans public and check if their true in player class so you can create animation
	public KeyInput(Handler handler) {
		this.handler = handler;
		
		keyDown = false;
		keyUp = false;
		keyLeft = false;
		keyRight = false;
		
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if (tempObject.getId() == ID.Player ) {
				//key events for Player 1
					if (key == KeyEvent.VK_W) {
						keyUp = true;
						tempObject.setDirection("up");
						tempObject.setVelY(-5);
					}
					if (key == KeyEvent.VK_S) {
						keyDown = true;
						tempObject.setDirection("down");
						tempObject.setVelY(5);
					}
					if (key == KeyEvent.VK_A) {
						keyLeft = true;
						tempObject.setDirection("left");
						tempObject.setVelX(-5);
					}
					if (key == KeyEvent.VK_D) {
						keyRight = true;
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
				
				if (key == KeyEvent.VK_W) {
					//tempObject.setVelY(0);
					keyUp = false;
				}
				if (key == KeyEvent.VK_S) {
					//tempObject.setVelY(0);
					keyDown = false;
				}
				if (key == KeyEvent.VK_A) {
					//tempObject.setVelX(0);
					keyLeft = false;
				}
				if (key == KeyEvent.VK_D) {
					//tempObject.setVelX(0);
					keyRight = false;
				}
				
				//vertical movement
				if (!keyUp && !keyDown) tempObject.setVelY(0);
				//horizontal movement
				if (!keyLeft && !keyRight) tempObject.setVelX(0);
				
				//bug fix for going to the right after 'd' key is released
				if (keyLeft && !keyRight) {
					tempObject.setVelX(-5);
					tempObject.setDirection("left");
				}
				//bug fix for going to the left after 'a' key is released
				if (!keyLeft && keyRight) {
					tempObject.setVelX(5);
					tempObject.setDirection("right");
				}
				
				//bug fix for player image facing left/right when going up
				if (!keyLeft && !keyRight && keyUp) {
					tempObject.setVelY(-5);
					tempObject.setDirection("up");
				}
				
				//bug fix for player image facing left/right when going down
				if (!keyLeft && !keyRight && keyDown) {
					tempObject.setVelY(5);
					tempObject.setDirection("down");
				}
				
				if (!keyUp && !keyDown && !keyLeft && !keyRight) {
					tempObject.setDirection("stand");
				}
			}
		}
	}

}
