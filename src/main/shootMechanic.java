package main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import main.Game.STATE;

public class shootMechanic extends MouseAdapter {
	
	private Game game;
	private Handler handler;
	private Camera cam;
	private GameObject tempPlayer = null;
	
	public shootMechanic(Game game, Handler handler, Camera cam) {
		this.game = game;
		this.handler = handler;
		this.cam = cam;
	}
	
	public void findPlayer() {
		for (int i = 0; i < handler.object.size(); i++) {
			if (handler.object.get(i).getId() == ID.Player) {
				tempPlayer = handler.object.get(i);
				break;
			}
		}
	}
	
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		if (game.gameState == STATE.Game) {
			findPlayer();
			GameObject tempBullet = handler.addObject(new Bullet(game, tempPlayer.worldX + 20, tempPlayer.worldY + 20, ID.Bullet, handler));
			float angle = (float) Math.atan2(my - tempPlayer.worldY - 20 + cam.getY(), mx - tempPlayer.worldX - 20 + cam.getX());
			int bulletVel = 10;
			
			tempBullet.velX = (float) ((bulletVel) * Math.cos(angle));
			tempBullet.velY = (float) ((bulletVel) * Math.sin(angle));
			
		}
		
	}

}
