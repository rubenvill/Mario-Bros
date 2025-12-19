//PR1
package tp1.view;

//import tp1.logic.Game;
import tp1.logic.GameInterfaces.GameStatus;	

public abstract class GameView implements ViewInterface{

	protected GameStatus game;
	
	public GameView(GameStatus game) {
		this.game = game;
	}
	
}