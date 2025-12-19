package tp1.logic.GameInterfaces;
//Gameview
public interface GameStatus {
	public boolean playerWins();
	public boolean playerLoses();
	public int remainingTime();
	public int points();
	public int numLives();
	public String positionToString(int col, int row);
	public String toString();
	
}
