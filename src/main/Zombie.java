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
	private int imageRan = random.nextInt(20);
	private String downImage1, downImage2;
	
	public Zombie(Game game, int x, int y, ID id, Handler handler) {
		super(game, x, y, id);
		this.direction = "stand";
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
		return new Rectangle((int)worldX + 14, (int)worldY + 2, 17, 20);
	}


	public Rectangle getSolidBounds() {
		return new Rectangle((int)worldX + 12, (int)worldY + 24, 23, 23);
	}
	
	public Shape getDistanceBounds() {
		// TODO Auto-generated method stub
		return null;
	}

	public void tick() {
		
		if (player == null)
			findPlayer();
		
		if (onPath) {
			
			int goalCol = player.getSolidBounds().x/game.tileSize;
			int goalRow = player.getSolidBounds().y/game.tileSize;
			
			searchPath(goalCol, goalRow);
		}
				
		if (normalZombieHp <= 0) {
			if (++killCount % 35 == 0 || killCount == 20) {
			    if (killCount == 20) {
			        // Drop a key only the first time 20 zombies have been killed
			        handler.addObject(new Key(game, (int)this.worldX, (int)this.worldY, ID.Key, handler));
			    } else {
			        // For every subsequent 50th kill, drop a health potion instead
			        handler.addObject(new HealthPotion(game, (int)this.worldX, (int)this.worldY, ID.HealthPotion, handler));
			    }
			}
			handler.removeObject(this);
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
//		g.setColor(Color.RED);
//		g.drawRect((int)worldX + 14, (int)worldY + 2, 17, 20);
//		
//		// create visible hit box for getBounds()
//		g.setColor(Color.GREEN);
//		g.drawRect((int)worldX + 12, (int)worldY + 24, 23, 23);
		
	}

	public float getNormalZombieHP() {
		return normalZombieHp;
	}

	public void setNormalZombieHP(float normalZombieHp) {
		this.normalZombieHp = normalZombieHp;
	}
	
	public static int getKillCount() {
		return killCount;
	}
	
	public static void resetKillCount() {
		killCount = 0;
	}
	
	public void findPlayer() {
		for (int i = 0; i < handler.object.size(); i++) {
			if (handler.object.get(i).getId() == ID.Player) {
				player = handler.object.get(i);
				break;
			}
		}
	}

}
