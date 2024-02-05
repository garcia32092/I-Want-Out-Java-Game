package main;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.BufferedImage;

import ai.Node;

public abstract class GameObject {
	
	protected float worldX, worldY, screenX, screenY;

	protected Game game;
	protected ID id;
	protected float velX, velY;
	protected int negSpeed, posSpeed;
	protected BufferedImage up1, up2, down1, down2, left1, left2, right1, right2, stand;
	protected String direction;
	protected int spriteCounter = 0;
	protected int spriteNum = 1;
	protected boolean tileCollisionX = false;
	protected boolean tileCollisionY = false;
	protected boolean onPath = false;
	public boolean spawnerCollision = false, slowed = false, poisoned = false, faster = false, invulnerable = false, stronger = false;
	
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
	    int startCol = (int) worldX / game.tileSize;
	    int startRow = (int) worldY / game.tileSize;

	    game.pFinder.setNodes(startCol, startRow, goalCol, goalRow);

	    if (game.pFinder.search()) {
	    	if (game.pFinder.pathList.size() > 0) {
		        Node nextStep = game.pFinder.pathList.get(0); // Assuming pathList.get(0) is the next node in the path
	
		        int nextX = nextStep.col * game.tileSize;
		        int nextY = nextStep.row * game.tileSize;
	
		        // Determine movement direction
		        int dx = Integer.compare(nextX, (int) this.worldX);
		        int dy = Integer.compare(nextY, (int) this.worldY);
	
		        // Attempt to move in the desired direction
		        boolean canMoveDiagonally = !game.cChecker.checkTileCollision(this, dx, dy);
		        boolean canMoveHorizontally = !game.cChecker.checkTileCollision(this, dx, 0);
		        boolean canMoveVertically = !game.cChecker.checkTileCollision(this, 0, dy);
	
		        if (canMoveDiagonally) {
		            this.velX = dx * this.posSpeed;
		            this.velY = dy * this.posSpeed;
		        } else if (canMoveHorizontally) {
		            this.velX = dx * this.posSpeed;
		            this.velY = 0;
		        } else if (canMoveVertically) {
		            this.velX = 0;
		            this.velY = dy * this.posSpeed;
		        } else {
		            // If no movement is possible, stop the zombie or implement logic to handle being stuck
		            this.velX = 0;
		            this.velY = 0;
		        }
	    	}
	    } else {
	        // Pathfinding failed to find a path; handle accordingly
	    }

	    updateDirection();
	    move();
	}

	protected void updateDirection() {
	    if (velX == 0 && velY < 0) {
	        direction = "up";
	    } else if (velX == 0 && velY > 0) {
	        direction = "down";
	    } else if (velX < 0 && velY == 0) {
	        direction = "left";
	    } else if (velX > 0 && velY == 0) {
	        direction = "right";
	    } else if (velX > 0 && velY < 0) {
	        direction = "upright";
	    } else if (velX < 0 && velY < 0) {
	        direction = "upleft";
	    } else if (velX > 0 && velY > 0) {
	        direction = "downright";
	    } else if (velX < 0 && velY > 0) {
	        direction = "downleft";
	    }
	}

	protected void move() {
//	    checkCollision();

	    if (!tileCollisionX) {
	        worldX += velX;
	    } else {
	        velX = 0; // Stop movement in the X direction if there's a collision
	    }

	    if (!tileCollisionY) {
	        worldY += velY;
	    } else {
	        velY = 0; // Stop movement in the Y direction if there's a collision
	    }
	}
}
