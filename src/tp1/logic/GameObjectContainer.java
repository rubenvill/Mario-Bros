package tp1.logic;

import java.util.ArrayList;
import java.util.List;

import tp1.logic.gameobjects.GameItem;
import tp1.logic.gameobjects.GameObject;
import tp1.view.Messages;

public class GameObjectContainer {
	private List<GameObject> objects; 
	private List<GameObject> newObjects;

	public GameObjectContainer() {
		objects = new ArrayList<GameObject>();
		newObjects = new ArrayList<GameObject>();
	}
	
	public void add(GameObject object) {
		this.objects.add(object);
	}
	
	public void toAdd(GameObject gameobject) {
		this.newObjects.add(gameobject);
	}

	public void update() {
		for(int i = 0; i < objects.size(); i++) {
			objects.get(i).update();
		}

		for(GameObject obj: objects) {
			doInteraction(obj);
		}

		for(GameObject obj : newObjects) {
		    this.objects.add(obj);
		}
		
		newObjects.clear();

		clean();
	}
	
	private void clean() {
		for(int i = 0; i < objects.size(); i++) {
			if (!(objects.get(i).isAlive())) {
				objects.remove(i);
				i--; 
			}
		}
	}

	public void remove(GameObject object) {
		for(int i = 0; i < objects.size(); i++) {
			if (objects.get(i).equals(object)) {
				objects.remove(i);
				i--; 
			}
		}
	}
	
	public void doInteraction(GameItem other) {
		for (GameObject obj: objects) {
			if (obj.isAlive() && other.isAlive()) {
				obj.doInteraction(other);
			}
		}
	}
	
	protected void add(int i, GameObject obj) {
		this.objects.add(i, obj);
	}
	
	public boolean isPosSolid(Position pos) {
		for(GameObject obj : objects) {
			if (obj.isInPos(pos) && obj.isSolid()) {
				return true;
			}
		}
		return false;
	}

	public String positionToString(Position pos) {
		StringBuilder posToStr = new StringBuilder();
		for (GameObject obj : objects) {
			if (obj != null && obj.isInPos(pos)) {
				posToStr.append(obj.getIcon());
			}
		}
		
		return posToStr.toString();
	}	
	
	@Override
	public String toString() { 
		StringBuilder str = new StringBuilder();
		for (GameObject obj : objects) {
			str.append(obj.toString()).append(Messages.LINE_SEPARATOR);
		}
		return str.toString();
	}	
}
