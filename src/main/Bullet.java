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
	private WebSpider webSpider;
	private Web web;
	private TileManager tileM;
	private static int bulletDmg = 25;
	private static Color bulletColor = Color.BLACK;
	
	public Bullet(Game game, float x, float y, ID id, Handler handler, TileManager tileM) {
		super(game, x, y ,id);
		this.handler = handler;
		this.tileM = tileM;
		
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

	public int getBulletDmg() {
		return bulletDmg;
	}

	public static void buffBulletDmg() {
		bulletDmg += 50;
		bulletColor = Color.RED;
	}
	
	public static void resetBullets() {
		bulletDmg = 25;
		bulletColor = Color.BLACK;
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
					zombie.setNormalZombieHP(zombie.getNormalZombieHP() - getBulletDmg());
					handler.removeObject(this);
				}
			} else if (tempObject.getId() == ID.WebSpider) {
				if(getHitBounds().intersects(tempObject.getHitBounds())) {
					//collision code
					webSpider = (WebSpider)tempObject;
					webSpider.setWebSpiderHP(webSpider.getWebSpiderHP() - getBulletDmg());
					handler.removeObject(this);
				}
			} else if (tempObject.getId() == ID.Web) {
				if(getHitBounds().intersects(tempObject.getHitBounds())) {
					//collision code
					web = (Web)tempObject;
					web.setWebHP(web.getWebHP() - getBulletDmg());
					handler.removeObject(this);
				}
			}
		}
		
		int tileX = (int)worldX / game.tileSize;
	    int tileY = (int)worldY / game.tileSize;
	    
    	// Assuming mapTileNum contains integer IDs for tiles, where '1' represents a Brick Wall
	    // You might need to adjust this condition based on your actual tile setup
	    int tileID = tileM.mapTileNum[tileX][tileY];
	    
	    // Check if the tileID corresponds to the Brick Wall
	    // This condition assumes '1' is the ID for Brick Wall tiles, adjust as needed
	    if (tileM.tile[tileID].collision) // Assuming .collision is a boolean indicating if it's a solid tile
	    	handler.removeObject(this);
	}
	
	public void render (Graphics g) {
		g.setColor(bulletColor);
		g.fillRect((int) worldX, (int) worldY, 5, 5);
	}
}
