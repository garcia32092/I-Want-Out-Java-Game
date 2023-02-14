package com.tutorial.main;

import java.awt.Graphics;
import java.util.Iterator;
import java.util.LinkedList;

public class Handler {
	
	LinkedList<GameObject> object = new LinkedList<GameObject>();
	
	public void tick() {
		for(int i = 0; i < object.size(); i++) {
			object.get(i).tick();
		}
	}
	
	public void render(Graphics g) {
		for (int i = 0; i < object.size(); i++) {
			object.get(i).render(g);
		}
	}
	
	public GameObject addObject(GameObject object) {
		this.object.add(object);
		return object;
	}
	
	public GameObject removeObject(GameObject object) {
		this.object.remove(object);
		return object;
	}
	
	public void clearEnemies() {
		Iterator<GameObject> iterator = object.iterator();
		while (iterator.hasNext()) {
			GameObject currentObject = iterator.next();
			if (currentObject.getId() != ID.Player) {
				iterator.remove();
			}
		}
	}
	
	public void clearPlayer() {
		Iterator<GameObject> iterator = object.iterator();
		while (iterator.hasNext()) {
			GameObject currentObject = iterator.next();
			if (currentObject.getId() == ID.Player) {
				iterator.remove();
			}
		}
	}

}
