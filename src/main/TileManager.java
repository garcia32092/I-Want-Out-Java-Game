package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class TileManager {
	
	Game game;
	public Tile[] tile;
	public int mapTileNum[][];
	private Handler handler;
	GameObject player = null;
	boolean drawPath = true;
	
	public TileManager(Game game, Handler handler) {
		this.game = game;
		this.handler = handler;
		
		tile = new Tile[10];
		mapTileNum = new int[game.maxWorldCol][game.maxWorldRow];
		
		getTileImage();
		loadMap("/maps/worldmap01.txt");
	}
	
	public void getTileImage() {
		
		try {
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/light_wood_floor_1.png"));
			tile[0].tileIs = "Light Wood Floor";
			
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/brick_wall.png"));
			tile[1].collision = true;
			tile[1].tileIs = "Brick Wall";
//			System.out.println("Tile " + tile[1].tileIs + " collision set to " + tile[1].collision);
			
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/light_green_grass.png"));
			tile[2].tileIs = "Grass";
			
			tile[3] = new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/green_tree_1.png"));
			tile[3].collision = true;
			tile[3].tileIs = "Tree";
			
			tile[4] = new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wood_floor_1.png"));
			tile[4].collision = false;
			tile[4].tileIs = "Wood Floor";
			
			tile[5] = new Tile();
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/spider_hole_1.png"));
			tile[5].collision = false;
			tile[5].tileIs = "Spider Hole";
			
			tile[6] = new Tile();
			tile[6].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass_tombstone_1.png"));
			tile[6].collision = false;
			tile[6].tileIs = "Grass Tombstone";
		}
		
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadMap(String filePath) {
		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			
			while (col < game.maxWorldCol && row < game.maxWorldRow) {
				String line = br.readLine();
				
				while (col < game.maxWorldCol) {
					String numbers[] = line.split(" ");
					int num = Integer.parseInt(numbers[col]);
					mapTileNum[col][row] = num;
					col++;
				}
				if (col == game.maxWorldCol) {
					col = 0;
					row++;
				}
			}
			br.close();
		}
		catch(Exception e) {
			
		}
	}
	
	public void findPlayer() {
		for (int i = 0; i < handler.object.size(); i++) {
			if (handler.object.get(i).getId() == ID.Player) {
				player = handler.object.get(i);
				break;
			}
		}
	}
	
	public List<Point> findTileSpawnsWithinBounds(Shape playerBounds, String tileType) {
	    List<Point> validSpawnLocations = new ArrayList<>();
	    // Calculate visible world bounds based on player's position and screen size
	    int screenStartX = (int) Math.max(0, player.getX() - game.WIDTH / 2);
	    int screenStartY = (int) Math.max(0, player.getY() - game.HEIGHT / 2);
	    int screenEndX = Math.min(game.worldWidth, screenStartX + game.WIDTH);
	    int screenEndY = Math.min(game.worldHeight, screenStartY + game.HEIGHT);

	    // Convert world coordinates to tile indices
	    int startCol = screenStartX / game.tileSize;
	    int startRow = screenStartY / game.tileSize;
	    int endCol = screenEndX / game.tileSize;
	    int endRow = screenEndY / game.tileSize;

	    // Iterate over visible tiles
	    for (int row = startRow; row <= endRow; row++) {
	        for (int col = startCol; col <= endCol; col++) {
	            int tileNum = mapTileNum[col][row];
	            if (tileType.equals(tile[tileNum].tileIs)) {
	                Rectangle tileRect = new Rectangle(col * game.tileSize, row * game.tileSize, game.tileSize, game.tileSize);
	                if (playerBounds.intersects(tileRect)) {
	                    validSpawnLocations.add(new Point(col, row));
	                }
	            }
	        }
	    }
	    return validSpawnLocations;
	}
	
	public void render(Graphics2D g2) {
		int worldCol = 0;
		int worldRow = 0;
		
		if (player == null)
			findPlayer();
		
		while (worldCol < game.maxWorldCol && worldRow < game.maxWorldRow) {
			// Try generating just the corner square rooms when the player in in them
			// Then you can set the camera to the center of the room when the player is in them as well.
			
			int tileNum = mapTileNum[worldCol][worldRow];
			
			int worldX = worldCol * game.tileSize;
			int worldY = worldRow * game.tileSize;
			int screenX = (int)(worldX - player.worldX + player.screenX);
			int screenY = (int)(worldY - player.worldY + player.screenY);
			
			if (worldX + game.tileSize > player.worldX - player.screenX &&
				worldX - game.tileSize < player.worldX + player.screenX &&
				worldY + game.tileSize > player.worldY - player.screenY &&
				worldY - game.tileSize < player.worldY + player.screenY) {
				g2.drawImage(tile[tileNum].image, screenX, screenY, game.tileSize, game.tileSize, null);
//				tile[tileNum].rectangle = new Rectangle(screenX, screenY, game.tileSize, game.tileSize);
//				g2.setColor(Color.GREEN);
//				g2.draw(tile[tileNum].rectangle);
			}
			
			worldCol++;
			
			if (worldCol == game.maxWorldCol) {
				worldCol = 0;
				worldRow++;
			}
		}
		
//		if (drawPath == true) {
//			g2.setColor(new Color(255, 0, 0, 70));
//			
//			for (int i = 0; i < game.pFinder.pathList.size(); i ++) {
//				
//				int worldX = game.pFinder.pathList.get(i).col * game.tileSize;
//				int worldY = game.pFinder.pathList.get(i).row * game.tileSize;
////				System.out.println("Rendering node at [" + worldX + ", " + worldY + "] with solid status: " + game.pFinder.pathList.get(i).solid);
//				if (game.pFinder.pathList.get(i).solid == true)
//					g2.setColor(new Color(255, 0, 255, 128));
//				else
//					g2.setColor(new Color(255, 0, 0, 70));
//				int screenX = (int)(worldX - player.worldX + player.screenX);
//				int screenY = (int)(worldY - player.worldY + player.screenY);
//				
//				g2.fillRect(screenX, screenY, game.tileSize, game.tileSize);
//			}
//		}
	}
}
