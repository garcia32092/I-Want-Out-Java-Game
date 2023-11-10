package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;

public class BasicEnemy extends GameObject {
	
	public BasicEnemy(Game game, int x, int y, ID id) {
		super(game, x, y, id);
				
		velX = 5;
		velY = 5;
		
	}

	public Rectangle getBounds() {
		return new Rectangle((int)worldX, (int)worldY, 16, 16);
	}

	public void tick() {
		worldX += velX;
		worldY += velY;
		
		if (worldY < 0 || worldY >= Game.HEIGHT - 58) {
			velY *= -1;
		}
		
		if (worldX < 0 || worldX >= Game.WIDTH - 32) {
			velX *= -1;
		}
		
	}

	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect((int)worldX, (int)worldY, 16, 16);
		
	}

	@Override
	public Rectangle getHitBounds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rectangle getSolidBounds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Shape getDistanceBounds() {
		// TODO Auto-generated method stub
		return null;
	}

}
