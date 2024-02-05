package main;

import java.awt.Point;
import java.awt.Shape;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Spawn {
	
	private Game game;
	private Handler handler;
	private HUD hud;
	private Random r = new Random();
	GameObject player = null;
	
	private int scoreKeep = 0;
//	private int levelKeep = 0;
	
	public Spawn(Game game, Handler handler, HUD hud) {
		this.game = game;
		this.handler = handler;
		this.hud = hud;
	}
	
	public void findPlayer() {
		for (int i = 0; i < handler.object.size(); i++) {
			if (handler.object.get(i).getId() == ID.Player) {
				player = handler.object.get(i);
				break;
			}
		}
	}
	
	public void tick() {
		scoreKeep++;
		
		if (player == null) {
			findPlayer();
		}
		
		if (handler.numberOfZombies() < 12 && player != null && game.tileM != null) {
			scoreKeep = 0;
			hud.setLevel(hud.getLevel() + 1);

			Shape playerBounds = player.getDistanceBounds(); // Assuming you have a method to get the player
	        List<Point> spawnLocations = game.tileM.findTileSpawnsWithinBounds(playerBounds, "Grass");
	        Collections.shuffle(spawnLocations); // Randomize spawn locations
	        for (int i = 0; i < Math.min(spawnLocations.size(), 12 - handler.numberOfZombies()); i++) {
	            Point p = spawnLocations.get(i);
	            handler.addObject(new Zombie(game, p.x * game.tileSize, p.y * game.tileSize, ID.Zombie, handler));
	        }
		}
	}
}