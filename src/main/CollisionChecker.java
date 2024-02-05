package main;

public class CollisionChecker {

	Game game;
	
	public CollisionChecker(Game game) {
		this.game = game;
	}
	
	public void checkForTileCollision(GameObject gameObject) {
		
		int objectLeftCollisionX = gameObject.getSolidBounds().x;
		int objectRightCollisionX = gameObject.getSolidBounds().x + gameObject.getSolidBounds().width;
		int objectTopCollisionY = gameObject.getSolidBounds().y;
		int objectBottomCollisionY = gameObject.getSolidBounds().y + gameObject.getSolidBounds().height;
		
		int objectLeftCol = (int)((objectLeftCollisionX + gameObject.velX)/game.tileSize);
		int objectRightCol = (int)((objectRightCollisionX + gameObject.velX)/game.tileSize);
		int objectTopRow = (int)((objectTopCollisionY + gameObject.velY)/game.tileSize);
		int objectBottomRow = (int)((objectBottomCollisionY + gameObject.velY)/game.tileSize);
		
		int tileNum1, tileNum2;
		
		switch(gameObject.direction) {
		case "up":
			tileNum1 = game.tileM.mapTileNum[objectLeftCol][objectTopRow];
			tileNum2 = game.tileM.mapTileNum[objectRightCol][objectTopRow];
			if (game.tileM.tile[tileNum1].collision == true || game.tileM.tile[tileNum2].collision == true) {
				gameObject.tileCollisionY = true;
			}
			break;
		case "down":
			tileNum1 = game.tileM.mapTileNum[objectLeftCol][objectBottomRow];
			tileNum2 = game.tileM.mapTileNum[objectRightCol][objectBottomRow];
			if (game.tileM.tile[tileNum1].collision == true || game.tileM.tile[tileNum2].collision == true) {
				gameObject.tileCollisionY = true;
			}
			break;
		case "left":
			tileNum1 = game.tileM.mapTileNum[objectLeftCol][objectTopRow];
			tileNum2 = game.tileM.mapTileNum[objectLeftCol][objectBottomRow];
			if (game.tileM.tile[tileNum1].collision == true || game.tileM.tile[tileNum2].collision == true) {
				gameObject.tileCollisionX = true;
			}
			break;
		case "right":
			tileNum1 = game.tileM.mapTileNum[objectRightCol][objectTopRow];
			tileNum2 = game.tileM.mapTileNum[objectRightCol][objectBottomRow];
			if (game.tileM.tile[tileNum1].collision == true || game.tileM.tile[tileNum2].collision == true) {
				gameObject.tileCollisionX = true;
			}
			break;
		case "upright":
			// Check collision in the upward direction
		    tileNum1 = game.tileM.mapTileNum[objectLeftCol][objectTopRow];
		    tileNum2 = game.tileM.mapTileNum[objectRightCol][objectTopRow];
		    if (game.tileM.tile[tileNum1].collision || game.tileM.tile[tileNum2].collision) {
		        gameObject.tileCollisionY = true;
		    }
		    
		    // Check collision in the rightward direction
		    tileNum1 = game.tileM.mapTileNum[objectRightCol][objectTopRow];
		    tileNum2 = game.tileM.mapTileNum[objectRightCol][objectBottomRow];
		    if (game.tileM.tile[tileNum1].collision || game.tileM.tile[tileNum2].collision) {
		        gameObject.tileCollisionX = true;
		    }
		    break;
		case "upleft":
			tileNum1 = game.tileM.mapTileNum[objectLeftCol][objectTopRow];
			tileNum2 = game.tileM.mapTileNum[objectRightCol][objectTopRow];
			if (game.tileM.tile[tileNum1].collision == true || game.tileM.tile[tileNum2].collision == true) {
				gameObject.tileCollisionY = true;
			}
			
			tileNum1 = game.tileM.mapTileNum[objectLeftCol][objectTopRow];
			tileNum2 = game.tileM.mapTileNum[objectLeftCol][objectBottomRow];
			if (game.tileM.tile[tileNum1].collision == true || game.tileM.tile[tileNum2].collision == true) {
				gameObject.tileCollisionX = true;
			}
			break;
		case "downright":
			tileNum1 = game.tileM.mapTileNum[objectLeftCol][objectBottomRow];
			tileNum2 = game.tileM.mapTileNum[objectRightCol][objectBottomRow];
			if (game.tileM.tile[tileNum1].collision == true || game.tileM.tile[tileNum2].collision == true) {
				gameObject.tileCollisionY = true;
			}
			
			tileNum1 = game.tileM.mapTileNum[objectRightCol][objectTopRow];
			tileNum2 = game.tileM.mapTileNum[objectRightCol][objectBottomRow];
			if (game.tileM.tile[tileNum1].collision == true || game.tileM.tile[tileNum2].collision == true) {
				gameObject.tileCollisionX = true;
			}
			break;
		case "downleft":
			tileNum1 = game.tileM.mapTileNum[objectLeftCol][objectBottomRow];
			tileNum2 = game.tileM.mapTileNum[objectRightCol][objectBottomRow];
			if (game.tileM.tile[tileNum1].collision == true || game.tileM.tile[tileNum2].collision == true) {
				gameObject.tileCollisionY = true;
			}
			
			tileNum1 = game.tileM.mapTileNum[objectLeftCol][objectTopRow];
			tileNum2 = game.tileM.mapTileNum[objectLeftCol][objectBottomRow];
			if (game.tileM.tile[tileNum1].collision == true || game.tileM.tile[tileNum2].collision == true) {
				gameObject.tileCollisionX = true;
			}
			break;
		}
	}
	
	public boolean checkTileCollision(GameObject gameObject, int dx, int dy) {
	    // Calculate the next position
	    int nextX = (int) gameObject.getX() + dx * gameObject.posSpeed;
	    int nextY = (int) gameObject.getY() + dy * gameObject.posSpeed;

	    // Convert position to tile coordinates
	    int tileX = nextX / game.tileSize;
	    int tileY = nextY / game.tileSize;

	    // Assuming the mapTileNum array holds indices to the tile array
	    int tileIndex = game.tileM.mapTileNum[tileX][tileY];

	    // Now access the actual tile object to check for collision
	    boolean hasCollision = game.tileM.tile[tileIndex].collision;

	    return hasCollision;
	}
}
