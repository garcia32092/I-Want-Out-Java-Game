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
	private final int NUMOFZOMBIES = 50;
	private final int NUMOFSPIDERS = 50;
	
	private int zombieSpawnCooldown = 0;
	private final int zombieSpawnDelay = 60; // Adjust the delay as needed for zombies

	private int spiderSpawnCooldown = 0;
	private final int spiderSpawnDelay = 100; // Adjust the delay as needed for spiders

	
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
		} else {
			if (handler.numberOfZombies() < NUMOFZOMBIES && player != null && game.tileM != null && zombieSpawnCooldown <= 0) {
				scoreKeep = 0;
				hud.setLevel(hud.getLevel() + 1);

				Shape playerBounds = player.getDistanceBounds(); // Assuming you have a method to get the player
		        List<Point> spawnLocations = game.tileM.findTileSpawnsWithinBounds(playerBounds, "Grass Tombstone");
		        Collections.shuffle(spawnLocations); // Randomize spawn locations
		        for (int i = 0; i < Math.min(spawnLocations.size(), NUMOFZOMBIES - handler.numberOfZombies()); i++) {
		            Point p = spawnLocations.get(i);
		            handler.addObject(new Zombie(game, p.x * game.tileSize, p.y * game.tileSize, ID.Zombie, handler));
		            zombieSpawnCooldown = zombieSpawnDelay;
		        }
			} else if (zombieSpawnCooldown > 0) {
				zombieSpawnCooldown--;
			}
			
			if (handler.numberOfWebSpiders() < NUMOFSPIDERS && player != null && game.tileM != null && spiderSpawnCooldown <= 0) {
				Shape playerBounds = player.getDistanceBounds(); // Assuming you have a method to get the player
		        List<Point> spawnLocations = game.tileM.findTileSpawnsWithinBounds(playerBounds, "Spider Hole");
		        Collections.shuffle(spawnLocations); // Randomize spawn locations
		        for (int i = 0; i < Math.min(spawnLocations.size(), NUMOFSPIDERS - handler.numberOfWebSpiders()); i++) {
		            Point p = spawnLocations.get(i);
		            handler.addObject(new WebSpider(game, p.x * game.tileSize, p.y * game.tileSize, ID.WebSpider, handler));
		            spiderSpawnCooldown = spiderSpawnDelay;
		        }
			} else if (spiderSpawnCooldown > 0) {
				spiderSpawnCooldown--;
			}
		}
	}
}
