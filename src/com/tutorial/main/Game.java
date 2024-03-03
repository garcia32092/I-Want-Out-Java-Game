/*
 * implement xbox controller
 * https://stackoverflow.com/questions/17099787/java-using-xbox-controller
 * 
 */



package com.tutorial.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import javax.swing.JPanel;

import com.map.TileManager;

public class Game extends JPanel implements Runnable {
	
	private static final long serialVersionUID = 1550691097823471818L;
	
	final static int originalTileSize = 16;
	final static int scale = 3;
	
	public final static int tileSize = originalTileSize * scale; // 48x48 tile
	public final static int maxScreenCol = 16;
	public final static int maxScreenRow = 12;
	
	public static final int WIDTH = tileSize * maxScreenCol + 16; // 768 pixels
	public static final int HEIGHT = tileSize * maxScreenRow + 39; // 576 pixels
	
	// World Settings
	public final int maxWorldCol = 64;
	public final int maxWorldRow = 48;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;
	
	private Thread thread;
	private boolean running = false;
	
	private TileManager tileM = new TileManager(this);
	private Random r;
	private HUD hud;
	private Handler handler;
	private Spawn spawner;
	private Menu menu;
	private shootMechanic mouseClick;
	
	public enum STATE {
		Menu,
		Help,
		Game,
		gameOver
	}
	
	public STATE gameState = STATE.Menu;
	
	public Game() {
		
		handler = new Handler();
		menu = new Menu(this, handler);
		mouseClick = new shootMechanic(this, handler);

		this.addKeyListener(new KeyInput(handler));
		this.addMouseListener(menu);
		this.addMouseListener(mouseClick);
		
//		System.out.println("Window size: " + WIDTH + " x " + HEIGHT);
		new Window(WIDTH, HEIGHT, "ZOMBEEZ", this);
//		System.out.println("Window size: " + WIDTH + " x " + HEIGHT);
		
		hud = new HUD();
		spawner = new Spawn(handler, hud);
		r = new Random();
		
		if (gameState == STATE.Game) {
			handler.addObject(new Player(WIDTH/2-32, HEIGHT/2-32, ID.Player, handler));
			handler.addObject(new Zombie(r.nextInt(WIDTH), r.nextInt(HEIGHT), ID.Zombie, handler));
		}
		
	}

	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				tick();
				delta--;
			}
			if (running)
				repaint();
			frames++;
			
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				//System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}
	
	private void tick() {
		handler.tick();
		if (gameState == STATE.Game) {
			hud.tick();
			spawner.tick();
			
			if (HUD.HEALTH <= 0) {
				HUD.HEALTH = 100;
				HUD.greenHEALTH = 255;
				HUD.redHEALTH = 0;
				handler.clearEnemies();
				handler.clearPlayer();
				gameState = STATE.gameOver;
			}
			
		}
		
		else if (gameState == STATE.Menu) {
			menu.tick();
		}
		
	}
	
	public void paintComponent(Graphics g) {
		
		
		Graphics2D g2 = (Graphics2D)g;
		
		g2.setColor(Color.gray);
		g2.fillRect(0, 0, WIDTH, HEIGHT);
		
		if (gameState == STATE.Game) {
			tileM.render(g2);
			handler.render(g);
			hud.render(g2);
		}
		
		else if (gameState == STATE.Menu || gameState == STATE.Help || gameState == STATE.gameOver) {
			menu.render(g);
		}
		
		g2.dispose();
//		bs.show();
		
	}
	
	public static float Clamp(float var, float min, float max) {
		if (var >= max)
			return var = max;
		else if (var <= min)
			return var = min;
		else
			return var;
	}
	
	public static int clampInt(int var, int min, int max) {
		if (var >= max)
			return var = max;
		else if (var <= min)
			return var = min;
		else
			return var;
	}
	
	public static int clampLoop(int var, int min, int max) {
		if (var >= max)
			return var = min;
		else if (var <= min)
			return var = max;
		else
			return var;
	}
	
	public static void main(String args[]) {
		new Game();
	}

}
