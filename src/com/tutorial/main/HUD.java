package com.tutorial.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class HUD {
	
	public static float HEALTH = 100;
	public static int greenHEALTH = 255;
	public static int redHEALTH = 0;
	
	private int score = 0;
	private int level = 1;
	private int killCount = 0;

	public void tick() {		
		HEALTH = Game.clampInt((int)HEALTH, 0, 100);
		greenHEALTH = Game.clampInt(greenHEALTH, 0, 255);
		redHEALTH = Game.clampInt(redHEALTH, 0, 255);
		
		score++;
		
	}
	
	public void render(Graphics2D g2) {
		g2.setColor(Color.darkGray);
		g2.fillRect(Game.WIDTH/2-120, Game.HEIGHT-62, 200, 12);
		g2.setColor(new Color(redHEALTH, greenHEALTH, 0));
		g2.fillRect(Game.WIDTH/2-120, Game.HEIGHT-62, (int)HEALTH * 2, 12);
		g2.setColor(Color.white);
		g2.drawRect(Game.WIDTH/2-120, Game.HEIGHT-62, 200, 12);
		
//		g2.drawString("Score: " + score, 10, 15);
		g2.drawString("Level: " + level, 10, 30);
		
	}
	
	public void setScore(int score) {
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
	
	public int getKillCount() {
		return killCount;
	}

	public void setKillCount(int killCount) {
		this.killCount = killCount;
	}

}
