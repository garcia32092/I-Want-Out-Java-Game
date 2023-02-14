package com.tutorial.main;

import java.util.Random;

public class Spawn {
	
	private Handler handler;
	private HUD hud;
	private Random r = new Random();
	
	private int scoreKeep = 0;
	private int levelKeep = 0;
	
	public Spawn(Handler handler, HUD hud) {
		this.handler = handler;
		this.hud = hud;
	}
	
	public void tick() {
		scoreKeep++;
		
		if (scoreKeep >= 100) {
			scoreKeep = 0;
			hud.setLevel(hud.getLevel() + 1);
			
			handler.addObject(new Zombie(r.nextInt(Game.WIDTH - 100) + 100, r.nextInt(Game.HEIGHT - 100) + 100, ID.Zombie, handler));
			
			
		}
		
	}

}
