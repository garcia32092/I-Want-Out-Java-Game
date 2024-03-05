/*
 * implement xbox controller
 * https://stackoverflow.com/questions/17099787/java-using-xbox-controller
 * 
 */



package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import ai.PathFinder;

public class Game extends JPanel implements Runnable {
	
	private static final long serialVersionUID = 1550691097823471818L;
	
	final static int originalTileSize = 16;
	final static int scale = 3;
	
	public final static int tileSize = originalTileSize * scale; // 48x48 tile
	public final static int maxScreenCol = 16;
	public final static int maxScreenRow = 12;
	
	public static final int WIDTH = tileSize * maxScreenCol + 16; // 768 pixels + 16 = 786
	public static final int HEIGHT = tileSize * maxScreenRow + 39; // 576 pixels + 39 = 615
	
	// World Settings
	public final static int maxWorldCol = 88;
	public final static int maxWorldRow = 64;
	public final static int worldWidth = tileSize * maxWorldCol;
	public final static int worldHeight = tileSize * maxWorldRow;
	
	private Thread thread;
	private boolean running = false;
	
	private HUD hud;
	private Handler handler;
	private Spawn spawner;
	private Menu menu;
	private ShootMechanic sMechanic;
	private Camera cam;
	KeyInput keyIn;
	
	public TileManager tileM;
	public CollisionChecker cChecker;
	public PathFinder pFinder = new PathFinder(this);
	Sound sound = new Sound();
	
	public enum STATE {
		Menu,
		Help,
		Game,
		gameOver
	}
	
	public STATE gameState = STATE.Menu;
	
	public Game() {
		
		gameSetUp();
		
//		System.out.println("Window size: " + WIDTH + " x " + HEIGHT);
		new Window(WIDTH, HEIGHT, "ZOMBEEZ", this);
//		System.out.println("Window size: " + WIDTH + " x " + HEIGHT);
		playMusic(0);
	}
	
	public void gameSetUp() {
		handler = new Handler();
		cChecker = new CollisionChecker(this, handler);
		keyIn = new KeyInput(handler);
		cam = new Camera(0, 0, handler);
		menu = new Menu(this, handler, cam);
		tileM = new TileManager(this, handler);
		sMechanic = new ShootMechanic(this, handler, cam, tileM);

		this.addMouseListener(menu);
		this.addMouseListener(sMechanic);
		this.addKeyListener(keyIn);
		
		hud = new HUD();
		spawner = new Spawn(this, handler, hud);
	}
	
	public void restart() {
		tileM = new TileManager(this, handler);
		spawner = new Spawn(this, handler, hud);
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
//		cam.tick();
		if (gameState == STATE.Game) {
			hud.tick();
			spawner.tick();
			cam.tick();
			sMechanic.tick();
			
			if (HUD.HEALTH <= 0) {
				gameState = STATE.gameOver;
				HUD.HEALTH = 100;
				HUD.greenHEALTH = 255;
				HUD.redHEALTH = 0;
				handler.clearHandler();
				tileM = null;
				spawner = null;
			}
			
		}
		
		else if (gameState == STATE.Menu) {
			menu.tick();
		}
		
	}
	
	public void paintComponent(Graphics g) {
		
		
		Graphics2D g2 = (Graphics2D)g;
		
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, WIDTH, HEIGHT);
		
		if (gameState == STATE.Game) {
			tileM.render(g2);
			g2.translate(-cam.getX(), -cam.getY());
			handler.render(g2);
			g2.translate(cam.getX(), cam.getY());
			hud.render(g2);
		}
		
		else if (gameState == STATE.Menu || gameState == STATE.Help || gameState == STATE.gameOver) {
			menu.render(g2);
		}
		
		g2.dispose();
//		bs.show();
		
	}
	
	public void playMusic(int i) {
		sound.setFile(i);
		sound.play();
		sound.loop();
	}
	
	public void stopMusic() {
		sound.stop();
	}
	
	public void playSE(int i) {
		sound.setFile(i);
		sound.play();
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
