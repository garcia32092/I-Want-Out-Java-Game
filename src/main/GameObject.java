package main;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.BufferedImage;

public abstract class GameObject {
	
	protected float worldX, worldY, screenX, screenY;

	protected Game game;
	protected ID id;
	protected float velX, velY;
	protected int negSpeed, posSpeed;
	protected BufferedImage up1, up2, down1, down2, left1, left2, right1, right2, stand;
	protected String direction;
	protected int spriteCounter;
	protected int spriteNum;
	protected boolean tileCollisionX = false;
	protected boolean tileCollisionY = false;
	protected boolean onPath = false;
	
	public GameObject(Game game, float worldX, float worldY, ID id) {
		this.game = game;
		this.worldX = worldX;
		this.worldY = worldY;
		this.id = id;
	}
	
	public abstract void tick();
	
	public abstract void render(Graphics g);
	
	public abstract Rectangle getHitBounds();
	
	public abstract Rectangle getSolidBounds();
	
	public abstract Shape getDistanceBounds();
	
	public void checkCollision() {
		tileCollisionX = false;
		tileCollisionY = false;
		game.cChecker.checkForTileCollision(this);
	}
	
	public void setX(int x) {
		this.worldX = x;
	}
	
	public void setY(int y) {
		this.worldY = y;
	}
	
	public float getX() {
		return worldX;
	}
	
	public float getY() {
		return worldY;
	}
	
	public void setID(ID id) {
		this.id = id;
	}
	
	public ID getId() {
		return id;
	}
	
	public void setVelX(int velX) {
		this.velX = velX;
	}
	
	public void setVelY(int velY) {
		this.velY = velY;
	}
	
	public float getVelX() {
		return velX;
	}
	
	public float getVelY() {
		return velY;
	}
	
	public float getScreenX() {
		return screenX;
	}

	public void setScreenX(float screenX) {
		this.screenX = screenX;
	}

	public float getScreenY() {
		return screenY;
	}

	public void setScreenY(float screenY) {
		this.screenY = screenY;
	}
	
	public String getDirection() {
		return direction;
	}
	
	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	public void spriteIncrement() {
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
	
	public void searchPath(int goalCol, int goalRow) {
		
		int startCol = getSolidBounds().x/game.tileSize;
		int startRow = getSolidBounds().y/game.tileSize;
		
		if ((startCol >= 0 && startCol < 88) && (startRow >= 0 && startRow < 64))
			game.pFinder.setNodes(startCol, startRow, goalCol, goalRow, this);
		
		if (game.pFinder.search() == true) {
			
			// new worldX & worldY
			int nextX = game.pFinder.pathList.get(0).col * game.tileSize;
			int nextY = game.pFinder.pathList.get(0).row * game.tileSize;
			
			// object's solidBounds position
			int objLeftX = getSolidBounds().x;
			int objRightX = getSolidBounds().x + getSolidBounds().width;
			int objTopY = getSolidBounds().y;
			int objBottomY = getSolidBounds().y + getSolidBounds().height;
			
			if (objTopY > nextY && objLeftX >= nextX && objRightX < nextX + game.tileSize) {
				direction = "up";
				setVelY(negSpeed);
			}
			else if (objTopY < nextY && objLeftX >= nextX && objRightX < nextX + game.tileSize) {
				direction = "down";
				setVelY(posSpeed);
			}
			else if (objTopY >= nextY && objBottomY < nextY + game.tileSize) {
				if (objLeftX > nextX) {
					direction = "left";
					setVelX(negSpeed);
				}
				if (objLeftX < nextX) {
					direction = "right";
					setVelX(posSpeed);
				}
			}
			else if (objTopY > nextY && objLeftX > nextX) {
//				direction = "up";
				setVelY(negSpeed);
				checkCollision();
				if (tileCollisionX == true) {
					direction = "left";
					setVelX(negSpeed);
				}
			}
			else if (objTopY > nextY && objLeftX < nextX) {
//				direction = "up";
				setVelY(negSpeed);
				checkCollision();
				if (tileCollisionX == true) {
					direction = "right";
					setVelX(posSpeed);
				}
			}
			else if (objTopY < nextY && objLeftX > nextX) {
//				direction = "down";
				setVelY(posSpeed);
				checkCollision();
				if (tileCollisionX == true) {
					direction = "left";
					setVelX(negSpeed);
				}
			}
			else if (objTopY < nextY && objLeftX < nextX) {
//				direction = "down";
				setVelY(posSpeed);
				checkCollision();
				if (tileCollisionX == true) {
					direction = "right";
					setVelX(posSpeed);
				}
			}
		}
	}

}
