package tp1.logic.GameInterfaces;

import java.util.List;
import tp1.logic.gameobjects.GameObject;
import tp1.logic.gameobjects.Mario;

public interface GameConfiguration {
	// game status
	public int getRemainingTime();
	public int points();   
	public int numLives();
	// game objects
    public Mario getMario();
    public List<GameObject> getNPCObjects();
}
