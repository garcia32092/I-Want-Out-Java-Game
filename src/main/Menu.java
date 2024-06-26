package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import main.Game.STATE;

public class Menu extends MouseAdapter {
	
	private Game game;
	private Camera camera;
	private Handler handler;
	
	public Menu(Game game, Handler handler, Camera camera) {
		this.game = game;
		this.handler = handler;
		this.camera = camera;
	}
	
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		if (game.gameState == STATE.Menu) {
			//PLAY button
			if (mouseOver(mx, my, (Game.WIDTH / 2) - 120, 220, Game.tileSize * 5, 72)) {
				game.gameState = STATE.Game;
				game.stopMusic();
				game.addKeyListener(game.keyIn);
				//                           Game.tileSize * 19, Game.tileSize * 24 (world map location)
//				handler.addObject(new Player(game.WIDTH/2-32, game.HEIGHT/2-32, ID.Player, handler, game)); OLD SPAWN POINT
				handler.addObject(new Player(game, game.tileSize * 20, game.tileSize * 24, ID.Player, handler));
				camera.findPlayer();
				handler.addObject(new Door(game, game.tileSize * 27, game.tileSize * 37, ID.Door, handler));
				handler.addObject(new Door(game, game.tileSize * 59, game.tileSize * 13, ID.Door, handler));
				handler.addObject(new MachineGun(game, game.tileSize * 35, game.tileSize * 37, ID.MachineGun, handler));
				handler.addObject(new Exit(game, game.tileSize * 67, game.tileSize * 13, ID.Exit, handler));
				return;
			}
			
			//HELP button
			if (mouseOver(mx, my, (Game.WIDTH / 2) - 120, 350, Game.tileSize * 5, 72)) {
				game.gameState = STATE.Help;
				return;
			}
			
			//QUIT button
			if (mouseOver(mx, my, (Game.WIDTH / 2) - 120, 480, Game.tileSize * 5, 72)) {
				System.exit(1);
			}
		}
		
		//BACK button for HELP
		else if (game.gameState == STATE.Help) {
			if (mouseOver(mx, my, (Game.WIDTH / 2) - 355, 20, 200, 50)) {
				game.gameState = STATE.Menu;
				return;
			}
		}
		
		
		else if (game.gameState == STATE.gameOver) {
			if (mouseOver(mx, my, (Game.WIDTH / 2) - 120, 480, Game.tileSize * 5, 72)) {
				game.restart();
				game.gameState = STATE.Game;
				Zombie.resetKillCount();
				WebSpider.resetKillCount();
				Bullet.resetBullets();
				game.addKeyListener(game.keyIn);
				handler.clearHandler();
				handler.addObject(new Player(game, game.tileSize * 20, game.tileSize * 24, ID.Player, handler));
				camera.findPlayer();
				handler.addObject(new Door(game, game.tileSize * 27, game.tileSize * 37, ID.Door, handler));
				handler.addObject(new Door(game, game.tileSize * 59, game.tileSize * 13, ID.Door, handler));
				handler.addObject(new MachineGun(game, game.tileSize * 35, game.tileSize * 37, ID.MachineGun, handler));
				handler.addObject(new Exit(game, game.tileSize * 67, game.tileSize * 13, ID.Exit, handler));
				return;
			}
		}
		
		else if (game.gameState == STATE.gameWon) {
			if (mouseOver(mx, my, (Game.WIDTH / 2) - 120, 480, Game.tileSize * 5, 72)) {
				game.restart();
				game.gameState = STATE.Game;
				Zombie.resetKillCount();
				game.addKeyListener(game.keyIn);
				handler.clearHandler();
				handler.addObject(new Player(game, game.tileSize * 20, game.tileSize * 24, ID.Player, handler));
				camera.findPlayer();
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
			Font fnt = new Font("Algerian", 0, 100);
			Font fnt2 = new Font("Garamond", Font.BOLD, 50);
			
			g.setFont(fnt);
			g.setColor(Color.GREEN);
			g.drawString("I WANT OUT", (Game.WIDTH / 2) - 265, 130);
			
			g.setFont(fnt2);
			g.setColor(Color.WHITE);
			g.drawRect((Game.WIDTH / 2) - 120, 220, Game.tileSize * 5, 72);
			g.drawString("PLAY", (Game.WIDTH / 2) - 65, 272);
			
			g.setColor(Color.WHITE);
			g.drawRect((Game.WIDTH / 2) - 120, 350, Game.tileSize * 5, 72);
			g.drawString("HELP", (Game.WIDTH / 2) - 65, 402);

			g.setColor(Color.WHITE);
			g.drawRect((Game.WIDTH / 2) - 120, 480, Game.tileSize * 5, 72);
			g.drawString("QUIT", (Game.WIDTH / 2) - 65, 532);
		}
		
		else if (game.gameState == STATE.Help) {
			Font fnt = new Font("Algerian", 0, 95);
			Font fnt2 = new Font("Garamond", 0, 30);
			Font fnt3 = new Font("Garamond", 0, 32);
			
			g.setFont(fnt);
			g.setColor(Color.WHITE);
			g.drawString("HELP", (Game.WIDTH / 2) - 125, 130);
			
			g.setFont(fnt3);
			g.drawString("The zombies and spiders hold the keys.", (Game.WIDTH / 2) - 260, 240);
			g.drawString("Aim for the head.", (Game.WIDTH / 2) - 130, 285);
			g.drawString("The spiders have some sweet bullet upgrades too.", (Game.WIDTH / 2) - 300, 330);
			g.drawString("Watch out for their webs tho. They hurt.", (Game.WIDTH / 2) - 260, 375);
			g.drawString("How bad do you want out?", (Game.WIDTH / 2) - 185, 420);
			g.drawString("Escape.", (Game.WIDTH / 2) - 75, 465);
			g.drawString("If you can... bitch", (Game.WIDTH / 2) - 125, 510);
			
			g.setFont(fnt2);
			g.drawRect((Game.WIDTH / 2) - 355, 20, 200, 50);
			g.drawString("Back To Menu", 50, 55);
		}
		
		else if (game.gameState == STATE.gameOver) {
			Font fnt = new Font("Algerian", 0, 95);
			Font fnt2 = new Font("Garamond", 0, 38);
			Font fnt3 = new Font("Garamond", 0, 32);
			
			g.setFont(fnt);
			g.setColor(Color.WHITE);
			g.drawString("Game Over", (Game.WIDTH / 2) - 275, 130);
			
			if (Zombie.getKillCount() <= 0) {
				g.setFont(fnt3);
				g.drawString("You didn't kill any zombies... This is awkward...", (Game.WIDTH / 2) - 310, 300);
			}
			
			else if (Zombie.getKillCount() >= 1 && Zombie.getKillCount() <= 24) {
				g.setFont(fnt3);
				g.drawString("You killed " + Zombie.getKillCount() + " zombies. Nice...", (Game.WIDTH / 2) - 190, 300);
			}
			
			else if (Zombie.getKillCount() >= 2) {
				g.setFont(fnt3);
				g.drawString("Whoa! You killed " + Zombie.getKillCount() + " zombies!", (Game.WIDTH / 2) - 185, 300);
			}
			
			g.setFont(fnt2);
			g.drawRect((Game.WIDTH / 2) - 120, 480, Game.tileSize * 5, 72);
			g.drawString("Try again?", (Game.WIDTH / 2) - 75, 530);
						
		}
		
		else if (game.gameState == STATE.gameWon) {
			Font fnt = new Font("Algerian", 0, 95);
			Font fnt2 = new Font("Garamond", 0, 38);
			Font fnt3 = new Font("Garamond", 0, 32);
			
			g.setFont(fnt);
			g.setColor(Color.WHITE);
			g.drawString("You won!", (Game.WIDTH / 2) - 225, 130);
			
			if (Zombie.getKillCount() <= 0) {
				g.setFont(fnt3);
				g.drawString("You didn't kill any zombies... Cheating ass bitch...", (Game.WIDTH / 2) - 310, 300);
			}
			
			else if (Zombie.getKillCount() >= 1 && Zombie.getKillCount() <= 24) {
				g.setFont(fnt3);
				g.drawString("You killed " + Zombie.getKillCount() + " zombies. Nice...", (Game.WIDTH / 2) - 190, 300);
			}
			
			else if (Zombie.getKillCount() >= 2) {
				g.setFont(fnt3);
				g.drawString("Whoa! You killed " + Zombie.getKillCount() + " zombies!", (Game.WIDTH / 2) - 185, 300);
			}
			
			g.setFont(fnt2);
			g.drawRect((Game.WIDTH / 2) - 120, 480, Game.tileSize * 5, 72);
			g.drawString("Try again?", (Game.WIDTH / 2) - 75, 530);
						
		}

	}

}
