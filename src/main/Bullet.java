package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;

public class Bullet extends GameObject {
	
	private Game game;
	private Handler handler;
	private GameObject player;
	private Zombie zombie;
	
	public Bullet(Game game, float x, float y, ID id, Handler handler) {
		super(game, x, y ,id);
		this.handler = handler;
		
		for (int i = 0; i < handler.object.size(); i++) {
			if (handler.object.get(i).getId() == ID.Player)
				player = handler.object.get(i);
		}
	}

	@Override
	public Rectangle getHitBounds() {
		return new Rectangle((int)worldX, (int)worldY, 5, 5);
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
	
	public void tick() {
		worldX += velX;
		worldY += velY;
		
		float distance = (float) Math.sqrt( (worldX - player.getX()) * (worldX - player.getX()) + (worldY - player.getY()) * (worldY - player.getY()) );
		if (distance > 1000) {
			handler.removeObject(this);
		}
		
		collision();
	}
	
	private void collision() {
		for (int i = 0; i < handler.object.size(); i++) {
			
			GameObject tempObject = handler.object.get(i);
			
			if (tempObject.getId() == ID.Zombie) {
				if(getHitBounds().intersects(tempObject.getHitBounds())) {
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
		g.fillRect((int) worldX, (int) worldY, 5, 5);
	}
}
