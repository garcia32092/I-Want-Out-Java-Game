package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class WebSpider extends GameObject {
	
	private static int killCount = 0;
    private Handler handler;
    private int tickCount = 0;
    private float webSpiderHP = 100;
    private int directionChangeCooldown = 0;
    private Random random = new Random();
    private int shootCooldown = 0;

    public WebSpider(Game game, int x, int y, ID id, Handler handler) {
        super(game, x, y, id);
        this.handler = handler;
        this.velX = 3;
        this.velY = 3;
        
        getWebSpiderImage();
    }
    
    private void getWebSpiderImage() {
		
		try {
			
			down1 = ImageIO.read(getClass().getResourceAsStream("/webspider/webspider_walk1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/webspider/webspider_walk2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/webspider/webspider_walk3.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/webspider/webspider_walk4.png"));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

    @Override
    public void tick() {
    	
    	if (webSpiderHP <= 0) {
    		if (++killCount % 20 == 0 || killCount == 10) {
    		    if (killCount == 10) {
    		        // Drop a buff only the first time 2 spiders have been killed
    		    	handler.addObject(new BuffPotion(game, (int)this.worldX, (int)this.worldY, ID.BuffPotion, handler));
    		    } else {
    		        // For every subsequent 10th kill, drop a health potion instead
    		        handler.addObject(new HealthPotion(game, (int)this.worldX, (int)this.worldY, ID.HealthPotion, handler));
    		    }
    		}
			handler.removeObject(this);
		}
    	
        tickCount++;

        if (directionChangeCooldown <= 0) {
            changeDirection();
            directionChangeCooldown = 50 + random.nextInt(100); // Change direction every 50 to 150 ticks
        } else {
            directionChangeCooldown--;
        }

        if (shootCooldown <= 0) {
            shootWeb();
            shootCooldown = 100 + random.nextInt(200); // Shoot web every 100 to 300 ticks
        } else {
            shootCooldown--;
        }
        
        spriteCounter++;
		if (spriteCounter > 8) {
			if (spriteNum == 1) {
				spriteNum = 2;
			}
			else if (spriteNum == 2) {
				spriteNum = 3;
			}
			else if (spriteNum == 3) {
				spriteNum = 4;
			}
			else if (spriteNum == 4) {
				spriteNum = 1;
			}
			spriteCounter = 0;
		}

        // Implement collision with walls and other logic here
		updateDirection();
    	checkCollision();
    	move();
    }

    @Override
    public Rectangle getHitBounds() {
        return new Rectangle((int)worldX + 8, (int)worldY + 8, 34, 32);
    }

    @Override
    public Rectangle getSolidBounds() {
        return getHitBounds(); // Assuming the same bounds for simplicity
    }

    @Override
    public Shape getDistanceBounds() {
        // Implement if needed, for example, for web shooting range checks
        return null;
    }

    private void changeDirection() {
        int angle = random.nextInt(360);
        double rad = Math.toRadians(angle);
        velX = (float)(Math.cos(rad) * 2); // Speed set to 2 for example
        velY = (float)(Math.sin(rad) * 2);
    }

    private void shootWeb() {
        // Assuming Web is another GameObject that marks tiles as "webbed"
        // You can aim at the player or shoot in a random direction
        float webVelX = random.nextBoolean() ? 2 : -2; // Example velocities, adjust as needed
        float webVelY = random.nextBoolean() ? 2 : -2;
        handler.addObject(new Web(game, (int)worldX + 16, (int)worldY + 16, ID.Web, handler, webVelX, webVelY));
    }

    public float getWebSpiderHP() {
		return webSpiderHP;
	}

	public void setWebSpiderHP(float webSpiderHP) {
		this.webSpiderHP = webSpiderHP;
	}
	
	public static int getKillCount() {
		return killCount;
	}
	
	public static void resetKillCount() {
		killCount = 0;
	}

	@Override
    public void render(Graphics g) {
        // Draw the spider
		BufferedImage image = null;
		
		if (spriteNum == 1) {
			image = down1;
		}
		if (spriteNum == 2) {
			image = down2;
		}
		if (spriteNum == 3) {
			image = left1;
		}
		if (spriteNum == 4) {
			image = right1;
		}
		
		g.drawImage(image, (int)worldX, (int)worldY, Game.tileSize, Game.tileSize, null);
		
		// create visible hit box for getBounds()
//		g.setColor(Color.RED);
//		g.drawRect((int)worldX + 8, (int)worldY + 8, 34, 32); // FOR TESTING
		
		// create visible hit box for getBounds()
		g.setColor(Color.GREEN);
//		g.drawRect((int)worldX + 12, (int)worldY + 24, 23, 23); // FOR TESTING
    }
}
