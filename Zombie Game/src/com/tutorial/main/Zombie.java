package com.tutorial.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Zombie extends GameObject {
	
	private static int killCount = 0;
	private Handler handler;
	private GameObject player;
	private float normalZombieHp = 75;
	
	public Zombie(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		this.direction = "up";
		this.spriteCounter = 0;
		this.spriteNum = 1;
		this.handler = handler;
		
		for (int i = 0; i < handler.object.size(); i++) {
			if (handler.object.get(i).getId() == ID.Player)
				player = handler.object.get(i);
		}

		getZombieImage();
		
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 16, 16);
	}

	public void tick() {
		x += velX;
		y += velY;
		
		float diffX = player.getX() + 16 - x;
		float diffY = player.getY() + 16 - y;
		float distance = (float) Math.sqrt(diffX * diffX + diffY * diffY);
		
		velX = ((2/distance) * diffX);
		velY = ((2/distance) * diffY);
				
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
		
		if (velX > 0) {
			this.direction = "right";
		}
		
		if (velX < 0) {
			this.direction = "left";
		}
		
		if (velY < 0) {
			this.direction = "up";
		}
		
	}
	
	private void getZombieImage() {
		
		try {
			
//			stand = ImageIO.read(getClass().getResourceAsStream("/zombie/zombie_stand.png"));
			up1 = ImageIO.read(getClass().getResourceAsStream("/zombie/zombie_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/zombie/zombie_up_2.png"));
//			down1 = ImageIO.read(getClass().getResourceAsStream("/zombie/zombie_down_1.png"));
//			down2 = ImageIO.read(getClass().getResourceAsStream("/zombie/zombie_down_2.png"));
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
//		g.setColor(Color.GREEN);
//		g.fillRect((int)x, (int)y, 16, 16);
		
		BufferedImage image = null;
		
		switch(direction) {
//		case "stand":
//			image = stand;
//			break;
		case "up":
			if (spriteNum == 1) {
				image = up1;
			}
			if (spriteNum == 2) {
				image = up2;
			}
			break;
//		case "down":
//			if (spriteNum == 1) {
//				image = down1;
//			}
//			if (spriteNum == 2) {
//				image = down2;
//			}
//			break;
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
		}
		
		g.drawImage(image, (int)x, (int)y, Game.tileSize, Game.tileSize, null);
		
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
