package main;

public class Camera {

	private int x, y;

	private Handler handler;
	private GameObject player = null;
	
	public Camera(int x, int y, Handler handler) {
		this.x = x;
		this.y = y;
		this.handler = handler;
		
		findPlayer();
	}
	
	public void findPlayer() {
		for (int i = 0; i < handler.object.size(); i++) {
			if (handler.object.get(i).getId() == ID.Player) {
				player = handler.object.get(i);
				break;
			}
		}
	}
	
	public void tick() {
		if (player == null)
			findPlayer();
		
		if (player != null) {
//			if (tempPlayer.worldX > 200 && tempPlayer.worldY > 200) {
				x = (int) player.worldX - (Game.WIDTH/2 - Game.tileSize/2);
				y = (int) player.worldY - (Game.HEIGHT/2 - Game.tileSize/2);
//			}
		}
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
