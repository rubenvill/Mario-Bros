package tp1.logic.GameInterfaces;
import tp1.logic.Position;
import tp1.logic.gameobjects.GameItem;
import tp1.logic.gameobjects.GameObject;

//Gameview
public interface GameWorld {
	public void marioDies();
	public void marioExited();
	public void incrPoints(int incr);
	public void reachedExit();
	public boolean isPosSolid(Position pos);
	public void interact(GameItem other);
	public void addNewObject(GameObject gameobject); 
}
