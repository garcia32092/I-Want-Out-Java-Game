package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Zombie extends GameObject {
	
	private static int killCount = 0;
	private Handler handler;
	private GameObject player;
	private float normalZombieHp = 75;
	private Random random = new Random();
	private int imageRan = random.nextInt(50);
	private String downImage1, downImage2;
	
	public Zombie(Game game, int x, int y, ID id, Handler handler) {
		super(game, x, y, id);
		this.direction = "stand";
		this.spriteCounter = 0;
		this.spriteNum = 1;
		this.handler = handler;
		this.negSpeed = -2;
		this.posSpeed = 2;
		this.onPath = true;
		
		for (int i = 0; i < handler.object.size(); i++) {
			if (handler.object.get(i).getId() == ID.Player)
				player = handler.object.get(i);
		}
		
		getZombieImage();
	}

	public Rectangle getHitBounds() {
		// TODO MAYBE ADD BODY HIT BOX FOR BODY SHOTS THAT DO LESS DAMAGE THAN THE HEAD SHOTS??? (THIS HIT BOX IS MOSTLY ON THE HEAD)
		return new Rectangle((int)worldX + 15, (int)worldY + 4, 17, 24);
	}


	public Rectangle getSolidBounds() {
		return new Rectangle((int)worldX + 12, (int)worldY + 24, 23, 23);
	}
	
	public Shape getDistanceBounds() {
		// TODO Auto-generated method stub
		return null;
	}

	public void tick() {
		
		if (onPath) {
			
			int goalCol = player.getSolidBounds().x/game.tileSize;
			int goalRow = player.getSolidBounds().y/game.tileSize;
			
			searchPath(goalCol, goalRow);
		}
		
//		if (tileCollision) {
//			worldX += velX;
//			worldY += velY;
//			switch(direction) {
//			case "up": worldY -= 3; break;
//			case "down": worldY += 3; break;
//			case "left": worldX -= 3; break;
//			case "right": worldX += 3; break;
//			}
//		}
		
//		
//		float diffX = player.getX() + 16 - worldX;
//		float diffY = player.getY() + 16 - worldY;
//		float distance = (float) Math.sqrt(diffX * diffX + diffY * diffY);
//		
//		velX = ((2/distance) * diffX);
//		velY = ((2/distance) * diffY);
				
		if (normalZombieHp <= 0) {
			handler.removeObject(this);
			killCount++;
		}
		
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
	}
	
	private void getZombieImage() {
		
		if (imageRan % 2 == 0) {
			downImage1 = "/zombie/zombie_right_1.png";
			downImage2 = "/zombie/zombie_right_2.png";
		} else {
			downImage1 = "/zombie/zombie_left_1.png";
			downImage2 = "/zombie/zombie_left_2.png";
		}
		
		try {
			
//			stand = ImageIO.read(getClass().getResourceAsStream("/zombie/zombie_stand.png"));
			up1 = ImageIO.read(getClass().getResourceAsStream("/zombie/zombie_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/zombie/zombie_up_2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream(downImage1));
			down2 = ImageIO.read(getClass().getResourceAsStream(downImage2));
			right1 = ImageIO.read(getClass().getResourceAsStream("/zombie/zombie_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/zombie/zombie_right_2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/zombie/zombie_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/zombie/zombie_left_2.png"));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void render(Graphics g) {
		
		BufferedImage image = null;
		
		switch(direction) {
		case "stand":
			image = right1;
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
		g.setColor(Color.RED);
		g.drawRect((int)worldX + 15, (int)worldY + 4, 17, 24);
		
		// create visible hit box for getBounds()
		g.setColor(Color.GREEN);
		g.drawRect((int)worldX + 12, (int)worldY + 24, 23, 23);
		
	}

	public float getNormalZombieHp() {
		return normalZombieHp;
	}

	public void setNormalZombieHp(float normalZombieHp) {
		this.normalZombieHp = normalZombieHp;
	}
	
	public static int getKillCount() {
		return killCount;
	}
	
	public static void resetKillCount() {
		killCount = 0;
	}

}
