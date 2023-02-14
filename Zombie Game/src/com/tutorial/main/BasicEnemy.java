package com.tutorial.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class BasicEnemy extends GameObject {
	
	public BasicEnemy(int x, int y, ID id) {
		super(x, y, id);
				
		velX = 5;
		velY = 5;
		
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 16, 16);
	}

	public void tick() {
		x += velX;
		y += velY;
		
		if (y < 0 || y >= Game.HEIGHT - 58) {
			velY *= -1;
		}
		
		if (x < 0 || x >= Game.WIDTH - 32) {
			velX *= -1;
		}
		
	}

	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect((int)x, (int)y, 16, 16);
		
	}

}
