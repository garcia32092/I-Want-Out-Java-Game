package com.tutorial.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Bullet extends GameObject {
	
	private Handler handler;
	private GameObject player;
	private Zombie zombie;
	
	public Bullet(float x, float y, ID id, Handler handler) {
		super(x, y ,id);
		this.handler = handler;
		
		for (int i = 0; i < handler.object.size(); i++) {
			if (handler.object.get(i).getId() == ID.Player)
				player = handler.object.get(i);
		}
	}
	
	public void tick() {
		x += velX;
		y += velY;
		
		float distance = (float) Math.sqrt( (x - player.getX()) * (x - player.getX()) + (y - player.getY()) * (y - player.getY()) );
		if (distance > 1000) {
			handler.removeObject(this);
		}
		
		collision();
	}
	
	private void collision() {
		for (int i = 0; i < handler.object.size(); i++) {
			
			GameObject tempObject = handler.object.get(i);
			
			if (tempObject.getId() == ID.Zombie) {
				if(getBounds().intersects(tempObject.getBounds())) {
					//collision code
					zombie = (Zombie)tempObject;
					zombie.setNormalZombieHp(zombie.getNormalZombieHp() - 25);
					handler.removeObject(this);
				}
				
			}
		}
	}
	
	public void render (Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillRect((int) x, (int) y, 5, 5);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 5, 5);
	}

}
