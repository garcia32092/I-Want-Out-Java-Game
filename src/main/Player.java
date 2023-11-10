package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player extends GameObject {
	
	Handler handler;
	// if you want the key handler to work you must include it in constructor similar to YT video Game LOOP and key input
	// use it to make player faster with upgrade... should also help with animation stopping instead of standing...
	KeyInput keyIn = new KeyInput(handler);
	Game game;
	boolean spawnerCollision = false;
	
	public Player(Game game, int x, int y, ID id, Handler handler) {
		super(game, x, y, id);
		this.direction = "stand";
		this.spriteCounter = 0;
		this.spriteNum = 1;
		this.handler = handler;
		this.game = game;
		
		screenX = Game.WIDTH/2 - (Game.tileSize/2);
		screenY = Game.HEIGHT/2 - (Game.tileSize/2);
		
		getPlayerImage();
	}
	
	public Rectangle getHitBounds() {
		return new Rectangle((int)worldX + 15, (int)worldY + 6, 17, 37);
	}
	

	public Rectangle getSolidBounds() {
		return new Rectangle((int)worldX + 12, (int)worldY + 24, 23, 23);
	}
	
	public Shape getDistanceBounds() {
		int circleWidth = 384;
		int circleHeight = 384;
		Rectangle rect = new Rectangle(((int)worldX - circleWidth/2) + (game.tileSize/2), ((int)worldY - circleHeight/2) + (game.tileSize/2), circleWidth, circleHeight);
		Ellipse2D ellipse = new Ellipse2D.Double(rect.x, rect.y, rect.width, rect.height);
        double centerX = rect.x + rect.width / 2.0;
        double centerY = rect.y + rect.height / 2.0;
        double radius = Math.min(rect.width, rect.height) / 2.0;
        ellipse.setFrame(centerX - radius, centerY - radius, 2.0 * radius, 2.0 * radius);
        return ellipse;
	}
	
	public void tick() {
		checkCollision();
		
		if (!tileCollisionY) {
			worldY += velY;
		} else {
	        velY = 0;
		}
		
		if (!tileCollisionX) {
			worldX += velX;
		} else {
	        velX = 0;
		}
		
//		x = Game.clampLoop((int)x, -12, Game.WIDTH - 34);
//		y = Game.clampLoop((int)y, -12, Game.HEIGHT - 58);
		
//		handler.addObject(new Trail(x, y, ID.Trail, Color.CYAN, 32, 32, 0.08f, handler));
		
		enemyCollision();
		
//		if (keyIn.keyDown[0] == true || keyIn.keyDown[1] == true || keyIn.keyDown[2] == true || keyIn.keyDown[3] == true) {
			spriteCounter++;
			if (spriteCounter > 10) {
				if (spriteNum == 1) {
					spriteNum = 2;
				}
				else if (spriteNum == 2) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
//		}
	}
	
	private void enemyCollision() {
		for (int i = 0; i < handler.object.size(); i++) {
			
			GameObject tempObject = handler.object.get(i);
			
			if (tempObject.getId() == ID.Zombie) {
				if (getHitBounds().intersects(tempObject.getHitBounds())) {
					//collision code
					HUD.HEALTH -= 0.25;
					if (HUD.greenHEALTH < 0 || HUD.redHEALTH < 255) {
						HUD.greenHEALTH -= 6;
						HUD.redHEALTH += 5;
					}
				}
				
			}
		}
	}
	
	private void getPlayerImage() {
		
		try {
			
			stand = ImageIO.read(getClass().getResourceAsStream("/player/slayer_stand.png"));
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/slayer_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/slayer_up_2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/slayer_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/slayer_down_2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/slayer_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/slayer_right_2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/slayer_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/slayer_left_2.png"));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void render(Graphics g) {
		
		BufferedImage image = null;
		
		switch(direction) {
		case "stand":
			image = stand;
			break;
		case "up":
			if (spriteNum == 1) {
				image = up1;
			}
			if (spriteNum == 2) {
				image = up2;
			}
			break;
		case "down":
			if (spriteNum == 1) {
				image = down1;
			}
			if (spriteNum == 2) {
				image = down2;
			}
			break;
		case "left":
			if (spriteNum == 1) {
				image = left1;
			}
			if (spriteNum == 2) {
				image = left2;
			}
			break;
		case "right":
			if (spriteNum == 1) {
				image = right1;
			}
			if (spriteNum == 2) {
				image = right2;
			}
			break;
		case "upright":
			if (spriteNum == 1) {
				image = right1;
			}
			if (spriteNum == 2) {
				image = right2;
			}
			break;
		case "downright":
			if (spriteNum == 1) {
				image = right1;
			}
			if (spriteNum == 2) {
				image = right2;
			}
			break;
		case "upleft":
			if (spriteNum == 1) {
				image = left1;
			}
			if (spriteNum == 2) {
				image = left2;
			}
			break;
		case "downleft":
			if (spriteNum == 1) {
				image = left1;
			}
			if (spriteNum == 2) {
				image = left2;
			}
			break;
		}
		
		g.drawImage(image, (int)worldX, (int)worldY, Game.tileSize, Game.tileSize, null);
		
		// create visible hit box for getBounds()
		g.setColor(Color.GREEN);
//		g.drawRect((int)worldX + 12, (int)worldY + 24, 23, 23);
		((Graphics2D) g).draw(getDistanceBounds());
		
	}

}
