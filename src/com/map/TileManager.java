package com.map;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import com.tutorial.main.Game;

public class TileManager {
	
	Game game;
	Tile[] tile;
	int mapTileNum[][];
	
	public TileManager(Game game) {
		this.game = game;
		
		tile = new Tile[10];
		mapTileNum = new int[game.maxWorldCol][game.maxWorldRow];
		
		getTileImage();
		loadMap("/maps/worldmap01.txt");
	}
	
	public void getTileImage() {
		
		try {
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/earth.png"));
			
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/light_wood_floor_1.png"));
			
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass00.png"));
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
	
	public void render(Graphics2D g2) {
		int worldCol = 0;
		int worldRow = 0;
		int x = 0;
		int y = 0;
		
		while (worldCol < game.maxScreenCol && worldRow < game.maxScreenRow) {
			int tileNum = mapTileNum[worldCol][worldRow];
			
			g2.drawImage(tile[tileNum].image, x, y, game.tileSize, game.tileSize, null);
			worldCol++;
			x += game.tileSize;
			// TO DO:::::
			// GO TO 16:20 in YT video WORLD AND CAMERA
			
//			System.out.println("Columns: " + game.maxScreenCol + " Rows: " + game.maxScreenRow);
//			System.out.println("col number: " + col + " Row number: " + row);
			
			if (worldCol == game.maxScreenCol) {
				worldCol = 0;
				x = 0;
				worldRow++;
				y += game.tileSize;
			}
		}
	}
}
