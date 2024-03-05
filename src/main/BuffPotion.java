package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BuffPotion extends GameObject {
	
	private Handler handler;
	private Player player;

	public BuffPotion(Game game, float worldX, float worldY, ID id, Handler handler) {
		super(game, worldX, worldY, id);
		this.handler = handler;
		
		getBuffPotionImage();
	}
	
	public void findPlayer() {
		for (int i = 0; i < handler.object.size(); i++) {
			if (handler.object.get(i).getId() == ID.Player) {
				player = (Player) handler.object.get(i);
				break;
			}
		}
	}

	public void tick() {
		if (player == null)
			findPlayer();
		
		if (player != null) {
			if (getSolidBounds().intersects(player.getSolidBounds())) {
				//collision code
				handler.removeObject(this);
				Bullet.buffBulletDmg();
			}
		}
	}

	public Rectangle getHitBounds() {
		// TODO Auto-generated method stub
		return null;
	}

	public Rectangle getSolidBounds() {
		return new Rectangle((int)worldX + 4, (int)worldY + 2, 24, 28);
	}

	public Shape getDistanceBounds() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void getBuffPotionImage() {
		
		try {
			stand = ImageIO.read(getClass().getResourceAsStream("/potions/buffpotion.png"));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void render(Graphics g) {
		
		BufferedImage image = stand;
		
//		switch(direction) {
//		case "stand":
//			image = stand;
//			break;
//		case "up":
//			if (spriteNum == 1) {
//				image = up1;
//			}
//			if (spriteNum == 2) {
//				image = up2;
//			}
//			break;
//		case "down":
//			if (spriteNum == 1) {
//				image = down1;
//			}
//			if (spriteNum == 2) {
//				image = down2;
//			}
//			break;
//		case "left":
//			if (spriteNum == 1) {
//				image = left1;
//			}
//			if (spriteNum == 2) {
//				image = left2;
//			}
//			break;
//		case "right":
//			if (spriteNum == 1) {
//				image = right1;
//			}
//			if (spriteNum == 2) {
//				image = right2;
//			}
//			break;
//		case "upright":
//			if (spriteNum == 1) {
//				image = right1;
//			}
//			if (spriteNum == 2) {
//				image = right2;
//			}
//			break;
//		case "downright":
//			if (spriteNum == 1) {
//				image = right1;
//			}
//			if (spriteNum == 2) {
//				image = right2;
//			}
//			break;
//		case "upleft":
//			if (spriteNum == 1) {
//				image = left1;
//			}
//			if (spriteNum == 2) {
//				image = left2;
//			}
//			break;
//		case "downleft":
//			if (spriteNum == 1) {
//				image = left1;
//			}
//			if (spriteNum == 2) {
//				image = left2;
//			}
//			break;
//		}
		
		g.drawImage(image, (int)worldX, (int)worldY, Game.tileSize/2 + 10, Game.tileSize/2 + 10, null);
		
		// create visible hit box for getBounds()
//		g.setColor(Color.GREEN);
//		g.drawRect((int)worldX + 12, (int)worldY + 24, 23, 23);
//		g.setColor(Color.BLUE);
//		g.drawRect((int)worldX + 4, (int)worldY + 2, 24, 28);
	}
}
