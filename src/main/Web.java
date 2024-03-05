package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Web extends GameObject {
    private Handler handler;
    private float webHP = 100;
    private int lifetime = 300; // Duration before the web disappears
    private float travelDistance = 0; // Current travel distance
    private final float maxTravelDistance = 200; // Maximum travel distance before becoming a web
    private boolean isStationary = false; // Determines if the web is stationary
	private Random random = new Random();
	private int imageRan = random.nextInt(20);
	private String standImage;

    public Web(Game game, int x, int y, ID id, Handler handler, float velX, float velY) {
        super(game, x, y, id);
        this.handler = handler;
        this.velX = velX;
        this.velY = velY;
        
        getWebImage();
    }
    
    private void getWebImage() {
    	
    	if (imageRan % 2 == 0) {
			standImage = "/web/spiderweb1.png";
		} else {
			standImage = "/web/spiderweb2.png";
		}
		
		try {
			
			stand = ImageIO.read(getClass().getResourceAsStream(standImage));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
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
        	rectangle = new Rectangle((int) worldX + 28, (int) worldY + 28, 4, 4);
        } else {
        	rectangle = new Rectangle((int) worldX + 3, (int) worldY + 3, 30, 30);
        }
        return rectangle;
    }

    @Override
    public Rectangle getSolidBounds() {
        return getHitBounds(); // Not used for Web
    }

    @Override
    public Shape getDistanceBounds() {
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
        	
        	BufferedImage image = stand;
    		
    		g.drawImage(image, (int)worldX, (int)worldY, Game.tileSize/2 + 12, Game.tileSize/2 + 12, null);
        	
//            g.setColor(Color.GREEN); // Stationary web color
//            g.drawRect((int) worldX + 3, (int) worldY + 3, 30, 30); // FOR TESTING
        } else {
            g.setColor(Color.WHITE); // Projectile web color
            g.fillRect((int) worldX + 28, (int) worldY + 28, 4, 4);
        }
    }
}
