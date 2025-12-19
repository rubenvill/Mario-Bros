package tp1.logic.GameInterfaces;
import tp1.exceptions.GameLoadException;
import tp1.exceptions.GameModelException;
import tp1.exceptions.GameParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.Action;
import tp1.logic.gameobjects.GameObject;
//Controller
public interface GameModel {
	public boolean isFinished();
	public void update();
	public void resetGame(int newLevel);
	public void resetGame();
	public void exit();
	public void addAction(Action act);
	public GameObject addGameObject(String[] objWords) throws GameParseException, OffBoardException;
	public void save(String fileName) throws GameModelException;
	public void load(String fileName) throws GameLoadException;
}
