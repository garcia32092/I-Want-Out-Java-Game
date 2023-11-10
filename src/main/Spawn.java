package main;

import java.util.Random;

public class Spawn {
	
	private Game game;
	private Handler handler;
	private HUD hud;
	private Random r = new Random();
	
	private int scoreKeep = 0;
//	private int levelKeep = 0;
	
	public Spawn(Game game, Handler handler, HUD hud) {
		this.game = game;
		this.handler = handler;
		this.hud = hud;
	}
	
	public void tick() {
		scoreKeep++;
		
		if (scoreKeep >= 100 && handler.numberOfZombies() < 10) {
			scoreKeep = 0;
			hud.setLevel(hud.getLevel() + 1);
			
//			handler.addObject(new Zombie(game, game.tileSize * 20, game.tileSize * 27, ID.Zombie, handler));
			
			
		}
		
	}

}
