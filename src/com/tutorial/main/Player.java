package com.tutorial.main;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Player extends GameObject {
	
	Random r = new Random();
	Handler handler;
	KeyInput keyIn = new KeyInput(handler);
	
	public Player(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		this.direction = "stand";
		this.spriteCounter = 0;
		this.spriteNum = 1;
		this.handler = handler;
		
		getPlayerImage();
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 32, 32);
	}
	
	public void tick() {
		x += velX;
		y += velY;
		
		x = Game.clampLoop((int)x, -12, Game.WIDTH - 34);
		y = Game.clampLoop((int)y, -12, Game.HEIGHT - 58);
		
//		handler.addObject(new Trail(x, y, ID.Trail, Color.CYAN, 32, 32, 0.08f, handler));
		
		collision();
		
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
	
	private void collision() {
		for (int i = 0; i < handler.object.size(); i++) {
			
			GameObject tempObject = handler.object.get(i);
			
			if (tempObject.getId() == ID.Zombie) {
				if(getBounds().intersects(tempObject.getBounds())) {
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
//		g.setColor(Color.WHITE);
//		g.fillRect((int)x, (int)y, 16, 16);
		
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
		}
		
		g.drawImage(image, (int)x, (int)y, Game.tileSize, Game.tileSize, null);
		
	}

}
