package main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import main.Game.STATE;

public class ShootMechanic extends MouseAdapter {
	
	private Game game;
	private Handler handler;
	private Camera cam;
	private TileManager tileM;
	private Player player = null;
    private boolean isShooting = false;
    private long lastShotTime = 0;
    private final long fireRate = 100; // milliseconds between shots
	
	public ShootMechanic(Game game, Handler handler, Camera cam, TileManager tileM) {
		this.game = game;
		this.handler = handler;
		this.cam = cam;
		this.tileM = tileM;
	}
	
	public void findPlayer() {
		for (int i = 0; i < handler.object.size(); i++) {
			if (handler.object.get(i).getId() == ID.Player) {
				player = (Player) handler.object.get(i);
				break;
			}
		}
	}
	
	private void shoot(int mx, int my) {
		if (player == null) return;
		long currentTime = System.currentTimeMillis();
        if (currentTime - lastShotTime < fireRate) return; // Enforce fire rate
		
		GameObject tempBullet = handler.addObject(new Bullet(game, player.worldX + 20, player.worldY + 20, ID.Bullet, handler, tileM));
		float angle = (float) Math.atan2(my - player.worldY - 20 + cam.getY(), mx - player.worldX - 20 + cam.getX());
		int bulletVel = 10;
		
		tempBullet.velX = (float) ((bulletVel) * Math.cos(angle));
		tempBullet.velY = (float) ((bulletVel) * Math.sin(angle));
		
		lastShotTime = currentTime; // Update last shot time
	}
	
	public void mousePressed(MouseEvent e) {
		if (game.gameState == Game.STATE.Game) {
            findPlayer();
            if (player.hasMachineGun() == true) {
                isShooting = true; // Start shooting if player has the machine gun upgrade
            } else {
                // Single shot logic for pistol
                if (!isShooting) { // Prevent repeated shooting while holding the mouse
                    shoot(e.getX(), e.getY());
                }
            }
        }
	}
	
	public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            isShooting = false; // Stop shooting
        }
    }

    public void tick() {
        if (isShooting && game.gameState == Game.STATE.Game) {
            shoot(game.getMousePosition().x, game.getMousePosition().y);
        }
    }

}
