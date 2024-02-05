package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.Random;

public class WebSpider extends GameObject {
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
    }

    @Override
    public void tick() {
    	
    	if (webSpiderHP <= 0) {
			handler.removeObject(this);
		}
    	
    	updateDirection();
    	checkCollision();
    	move();
    	
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

        // Implement collision with walls and other logic here
    }

    @Override
    public Rectangle getHitBounds() {
        return new Rectangle((int)worldX, (int)worldY, 32, 32);
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

	@Override
    public void render(Graphics g) {
        // Draw the spider
        g.setColor(Color.BLACK);
        g.fillRect((int)worldX, (int)worldY, 32, 32); // Example rendering
    }
}
