package com.tutorial.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import com.tutorial.main.Game.STATE;

public class Menu extends MouseAdapter {
	
	private Game game;
	private Handler handler;
	private Random r = new Random();
	
	public Menu(Game game, Handler handler) {
		this.game = game;
		this.handler = handler;
	}
	
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		if (game.gameState == STATE.Menu) {
			//PLAY button
			if (mouseOver(mx, my, (Game.WIDTH / 2) - 120, 220, Game.tileSize * 5, 72)) {
				game.gameState = STATE.Game;
				handler.addObject(new Player(Game.WIDTH/2-32, Game.HEIGHT/2-32, ID.Player, handler));
				handler.addObject(new Zombie(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), ID.Zombie, handler));
			}
			
			//HELP button
			if (mouseOver(mx, my, (Game.WIDTH / 2) - 120, 350, Game.tileSize * 5, 72)) {
				game.gameState = STATE.Help;
			}
			
			//QUIT button
			if (mouseOver(mx, my, (Game.WIDTH / 2) - 120, 480, Game.tileSize * 5, 72)) {
				System.exit(1);
			}
		}
		
		//BACK button for HELP
		else if (game.gameState == STATE.Help) {
			if (mouseOver(mx, my, (Game.WIDTH / 2) - 410, 20, 200, 50)) {
				game.gameState = STATE.Menu;
				return;
			}
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		
	}
	
	private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		if (mx > x && mx < x + width) {
			if (my > y && my < y + height) {
				return true;
			}
			
			else return false;
		}
		
		else return false;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		if (game.gameState == STATE.Menu) {
			Font fnt = new Font("arial", 1, 80);
			Font fnt2 = new Font("arial", 1, 50);
			
			g.setFont(fnt);
			g.setColor(Color.GREEN);
			g.drawString("ZOMBIE GAME", (Game.WIDTH / 2) - 280, 130);
			
			g.setFont(fnt2);
			g.setColor(Color.WHITE);
			g.drawRect((Game.WIDTH / 2) - 120, 220, Game.tileSize * 5, 72);
			g.drawString("PLAY", (Game.WIDTH / 2) - 65, 272);
			
			g.setColor(Color.WHITE);
			g.drawRect((Game.WIDTH / 2) - 120, 350, Game.tileSize * 5, 72);
			g.drawString("HELP", (Game.WIDTH / 2) - 65, 405);

			g.setColor(Color.WHITE);
			g.drawRect((Game.WIDTH / 2) - 120, 480, Game.tileSize * 5, 72);
			g.drawString("QUIT", (Game.WIDTH / 2) - 60, 535);
		}
		
		else if (game.gameState == STATE.Help) {
			Font fnt = new Font("arial", 1, 60);
			Font fnt2 = new Font("arial", 1, 30);
			Font fnt3 = new Font("arial", 1, 30);
			
			g.setFont(fnt);
			g.setColor(Color.WHITE);
			g.drawString("HELP", (Game.WIDTH / 2) - 85, 130);
			
			g.setFont(fnt3);
			g.drawString("Use WASD keys to move and avoid the zombies", (Game.WIDTH / 2) - 340, 300);
			
			g.setFont(fnt2);
			g.drawRect((Game.WIDTH / 2) - 410, 20, 200, 50);
			g.drawString("Back", 85, 55);
		}

	}

}
