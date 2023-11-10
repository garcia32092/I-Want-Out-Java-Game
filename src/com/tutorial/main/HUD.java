package com.tutorial.main;

import java.awt.Color;
import java.awt.Graphics;

public class HUD {
	
	public static int HEALTH = 100;
	public static int greenHEALTH = 255;
	public static int redHEALTH = 0;
	
	private int score = 0;
	private int level = 1;
	
	public void tick() {		
		HEALTH = Game.clampInt(HEALTH, 0, 100);
		greenHEALTH = Game.clampInt(greenHEALTH, 0, 255);
		redHEALTH = Game.clampInt(redHEALTH, 0, 255);
		
		score++;
		
	}
	
	public void render(Graphics g) {
		g.setColor(Color.darkGray);
		g.fillRect(Game.WIDTH/2-120, Game.HEIGHT-62, 200, 12);
		g.setColor(new Color(redHEALTH, greenHEALTH, 0));
		g.fillRect(Game.WIDTH/2-120, Game.HEIGHT-62, HEALTH * 2, 12);
		g.setColor(Color.white);
		g.drawRect(Game.WIDTH/2-120, Game.HEIGHT-62, 200, 12);
		
		g.drawString("Score: " + score, 10, 15);
		g.drawString("Level: " + level, 10, 30);
		
	}
	
	public void score(int score) {
		this.score = score;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public int getLevel() {
		return level;
	}

}
