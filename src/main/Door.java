package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;

public class Door extends GameObject {
	
	private Handler handler;
	private Player player;

	public Door(Game game, float worldX, float worldY, ID id, Handler handler) {
		super(game, worldX, worldY, id);
		this.handler = handler;
		
		findPlayer();
	}
	
	public void findPlayer() {
		for (int i = 0; i < handler.object.size(); i++) {
			if (handler.object.get(i).getId() == ID.Player) {
				player = (Player) handler.object.get(i);
				break;
			}
		}
	}

	public void tick() {
		if (player == null)
			findPlayer();
		
		if (player != null) {
			if (getSolidBounds().intersects(player.getSolidBounds()) && (player.hasKey() == true)) {
				//collision code
				handler.removeObject(this);
				player.useKey();;
			}
		}
	}

	public Rectangle getHitBounds() {
		// TODO Auto-generated method stub
		return null;
	}

	public Rectangle getSolidBounds() {
		return new Rectangle((int)worldX, (int)worldY, 96, 96);
	}

	public Shape getDistanceBounds() {
		// TODO Auto-generated method stub
		return null;
	}

	public void render(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect((int)worldX, (int)worldY, 96, 96);
	}
}
