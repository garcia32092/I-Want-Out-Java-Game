package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Web extends GameObject {
    private Handler handler;
    private float webHP = 100;
    private int lifetime = 300; // Duration before the web disappears
    private float travelDistance = 0; // Current travel distance
    private final float maxTravelDistance = 200; // Maximum travel distance before becoming a web
    private boolean isStationary = false; // Determines if the web is stationary

    public Web(Game game, int x, int y, ID id, Handler handler, float velX, float velY) {
        super(game, x, y, id);
        this.handler = handler;
        this.velX = velX;
        this.velY = velY;
    }

    @Override
    public void tick() {
    	if (webHP <= 0) {
			handler.removeObject(this);
		}
    	
        if (!isStationary) {
        	move();
            travelDistance += Math.sqrt(velX * velX + velY * velY);

            // Transform into a stationary web after reaching max travel distance
            if (travelDistance >= maxTravelDistance) {
                isStationary = true;
                velX = 0;
                velY = 0;
            }
        } else {
            lifetime--;
            if (lifetime <= 0) {
                handler.removeObject(this); // Remove the web when its lifetime ends
            }
        }

        // Optionally, implement collision detection here

    	updateDirection();
    	checkCollision();
    	if (tileCollisionX || tileCollisionY)
    		isStationary = true;
    }

    @Override
    public Rectangle getHitBounds() {
    	Rectangle rectangle;
        if (!isStationary) {
        	rectangle = new Rectangle((int) worldX, (int) worldY, 4, 4);
        } else {
        	rectangle = new Rectangle((int) worldX, (int) worldY, 16, 16);
        }
        return rectangle;
    }

    @Override
    public Rectangle getSolidBounds() {
        return getHitBounds(); // Not used for Web
    }

    @Override
    public java.awt.Shape getDistanceBounds() {
        return null; // Not used for Web
    }

    public float getWebHP() {
		return webHP;
	}

	public void setWebHP(float webHP) {
		this.webHP = webHP;
	}

	@Override
    public void render(Graphics g) {
        if (isStationary) {
            g.setColor(Color.GRAY); // Stationary web color
            g.fillRect((int) worldX, (int) worldY, 16, 16); // Example rendering
        } else {
            g.setColor(Color.WHITE); // Projectile web color
            g.fillRect((int) worldX, (int) worldY, 4, 4); // Example rendering
        }
    }
}
