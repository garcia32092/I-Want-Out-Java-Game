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
		mapTileNum = new int[game.maxScreenCol][game.maxScreenRow];
		
		getTileImage();
		loadMap();
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
	
	public void loadMap() {
		try {
			InputStream is = getClass().getResourceAsStream("/maps/map01.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			
			while (col < game.maxScreenCol && row < game.maxScreenRow) {
				String line = br.readLine();
				
				while (col < game.maxScreenCol) {
					String numbers[] = line.split(" ");
					int num = Integer.parseInt(numbers[col]);
					mapTileNum[col][row] = num;
					col++;
				}
				if (col == game.maxScreenCol) {
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
		int col = 0;
		int row = 0;
		int x = 0;
		int y = 0;
		
		while (col < game.maxScreenCol && row < game.maxScreenRow) {
			int tileNum = mapTileNum[col][row];
			
			g2.drawImage(tile[tileNum].image, x, y, game.tileSize, game.tileSize, null);
			col++;
			x += game.tileSize;
			
//			System.out.println("Columns: " + game.maxScreenCol + " Rows: " + game.maxScreenRow);
//			System.out.println("col number: " + col + " Row number: " + row);
			
			if (col == game.maxScreenCol) {
				col = 0;
				x = 0;
				row++;
				y += game.tileSize;
			}
		}
	}
}
