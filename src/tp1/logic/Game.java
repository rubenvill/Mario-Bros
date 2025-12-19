package tp1.logic;
import tp1.logic.gameobjects.Land;
import tp1.logic.gameobjects.Box;
import tp1.logic.gameobjects.Mushroom;
import tp1.logic.gameobjects.ExitDoor;
import tp1.logic.gameobjects.GameItem;
import tp1.logic.gameobjects.GameObject;
import tp1.logic.gameobjects.GameObjectFactory;
import tp1.logic.gameobjects.Goomba;
import tp1.logic.gameobjects.Mario;
import tp1.view.Messages;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.io.IOException;

import tp1.exceptions.GameLoadException;
import tp1.exceptions.GameModelException;
import tp1.exceptions.GameParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.GameInterfaces.GameConfiguration;
import tp1.logic.GameInterfaces.GameModel;
import tp1.logic.GameInterfaces.GameStatus;
import tp1.logic.GameInterfaces.GameWorld;

public class Game implements GameModel, GameStatus, GameWorld{
	
	private GameConfiguration fileLoader;
	private GameObjectContainer gameObjects;
	private Mario mario;
	private int nLevel; 
	private int remainingTime;
	private int points;
	private int lifes;
	private boolean exitRequested;
	private boolean exitedDoor;
	public static final int DIM_X = 30;
	public static final int DIM_Y = 15;

	public Game(int nLevel) { 
		this.fileLoader = null;
		this.nLevel = nLevel;
		this.points = 0;
		this.lifes = 3;
		this.gameObjects = new GameObjectContainer(); 
		exitedDoor = false;
		exitRequested = false;
		if(nLevel == 0) initLevel0();
		else if (nLevel == 1) initLevel1();
		else if (nLevel == 2) initLevel2();
		else if(nLevel == -1) initLevelBlank();
	}
	
	@Override
	public boolean isFinished(){ 
		return (playerWins() || playerLoses() || exitRequested);
	}

	@Override
	public void resetGame(){
		if(fileLoader != null) loadGame(fileLoader);
		else {
			if(this.nLevel == 0) initLevel0();
			else if(this.nLevel == 1) initLevel1();
			else if (nLevel == 2) initLevel2();
			else if(this.nLevel == -1) initLevelBlank();
		}
	}
	
	@Override
	public void resetGame(int newLevel) { 
		this.nLevel = newLevel;
		this.fileLoader = null; 
		
		if(newLevel == 0) initLevel0();
		else if(newLevel == 1) initLevel1();
		else if (newLevel == 2) initLevel2();
		else if (newLevel == -1)initLevelBlank();
	}

	@Override
	public void update() {
		remainingTime--;
		gameObjects.update();
	}

	@Override
	public void addAction(Action act){ 
		if(mario != null) mario.addAction(act);
	}

	@Override
	public void exit() {
		exitRequested = true;
	}

	@Override
	public GameObject addGameObject(String[] objWords) throws GameParseException, OffBoardException {
		Mario tempMario = new Mario();
		Mario newMario = tempMario.parse(objWords, this);
		GameObject newObject;
		
		if (newMario != null) { 
			changeMario(newMario);
			newObject = newMario;
			add(0, newObject); 
			return newObject;
		}
		
		else {
			newObject = GameObjectFactory.parse(objWords, this);
			if (newObject != null) {
				gameObjects.add(newObject);
				return newObject;
			}
		}
		return null;
	}
	
	private void add(int i, GameObject obj) {
		gameObjects.add(i, obj);
	}
	
	private void changeMario(Mario newMario) {
		if(this.mario != null) {
			gameObjects.remove(this.mario);
		}
		this.mario = newMario;
	}
	
	@Override
	public void save(String fileName) throws GameModelException {
		
		try(BufferedWriter out = new BufferedWriter(new FileWriter(fileName))){
			out.write(this.stringify() + Messages.LINE_SEPARATOR);
			out.write(gameObjects.toString());
		}
		catch (IOException e) {
			throw new GameModelException(Messages.COMMAND_SAVE_ERROR);
		}
	}
	
	private String stringify() {
		String t = Integer.toString(remainingTime) + " ";
		String p = Integer.toString(points) + " ";
		String l = Integer.toString(lifes) + " ";
		return t + p + l;
	}

	public void load(String fileName) throws GameLoadException {
		GameConfiguration loadGame = new FileGameConfiguration(fileName, this);
		this.fileLoader = loadGame;
		loadGameState(loadGame);
	}
	
	public void loadGameState(GameConfiguration config){ 
		this.points = config.points();
		this.lifes = config.numLives();
		loadGame(config);
	}
	
	private void loadGame(GameConfiguration config) {
		this.remainingTime = config.getRemainingTime();
		this.gameObjects = new GameObjectContainer();
		this.exitedDoor = false;
		this.exitRequested = false;
		
		this.mario = config.getMario();
		
		gameObjects.add(this.mario); 
		
		for(GameObject obj : config.getNPCObjects()) {
			gameObjects.add(obj);
		}
	}

	@Override
	public boolean playerWins() {
		return exitedDoor; 
	}
	
	@Override
	public boolean playerLoses() {
		return (lifes <= 0 || remainingTime <= 0);
	}

	@Override
	public int remainingTime() {return remainingTime;}
	@Override
	public int points() {return points;}
	@Override
	public int numLives() {return lifes;}

	@Override
	public String positionToString(int col, int row) { 
		Position pos = new Position(row, col);
		return gameObjects.positionToString(pos);
	}
	
	@Override
	public String toString() {
		 return Messages.GAME_NAME + " " + Messages.VERSION + "\n" +
         Messages.REMAINING_TIME.formatted(remainingTime) + "\n" +
         Messages.POINTS.formatted(points) + "\n" +
         Messages.NUM_LIVES.formatted(lifes);
	}
	
	//Gameworld methods
	@Override
	public boolean isPosSolid(Position pos) {
		return gameObjects.isPosSolid(pos); 
	}
	
	@Override
	public void marioDies(){
		lifes--; 
		if (lifes != 0) resetGame();
	}
	
	@Override
	public void marioExited() {
		points = points + (remainingTime*10);
		remainingTime = 0;
		reachedExit(); 
	}
	
	@Override
	public void reachedExit() {
		exitedDoor = true;
	}
	
	@Override
	public void interact(GameItem other) {
		gameObjects.doInteraction(other);
	}
	
	@Override
	public void incrPoints(int incr) {
		points = points + incr; 
	}
	
	@Override
	public void addNewObject(GameObject gameobject) {
		gameObjects.toAdd(gameobject);
	}
	
	private void initLevel0() {
		this.nLevel = 0;
		this.remainingTime = 100;

		gameObjects = new GameObjectContainer();
		for(int col = 0; col < 15; col++) {
			gameObjects.add(new Land(this, new Position(13,col)));
			gameObjects.add(new Land(this, new Position(14,col)));		
		}

		gameObjects.add(new Land(this, new Position(Game.DIM_Y-3,9)));
		gameObjects.add(new Land(this, new Position(Game.DIM_Y-3,12)));
		for(int col = 17; col < Game.DIM_X; col++) {
			gameObjects.add(new Land(this, new Position(Game.DIM_Y-2, col)));
			gameObjects.add(new Land(this, new Position(Game.DIM_Y-1, col)));		
		}

		gameObjects.add(new Land(this, new Position(9,2)));
		gameObjects.add(new Land(this, new Position(9,5)));
		gameObjects.add(new Land(this, new Position(9,6)));
		gameObjects.add(new Land(this, new Position(9,7)));
		gameObjects.add(new Land(this, new Position(5,6)));

		int tamX = 8, tamY= 8;
		int posIniX = Game.DIM_X-3-tamX, posIniY = Game.DIM_Y-3;
		
		for(int col = 0; col < tamX; col++) {
			for (int fila = 0; fila < col+1; fila++) {
				gameObjects.add(new Land(this, new Position(posIniY- fila, posIniX+ col)));
			}
		}

		gameObjects.add(new ExitDoor(this, new Position(Game.DIM_Y-3, Game.DIM_X-1)));

		this.mario = new Mario(this, new Position(Game.DIM_Y-3, 0));
		gameObjects.add(this.mario);

		gameObjects.add(new Goomba(this, new Position(0, 19)));
	}
	
	private void initLevel1() {
		this.nLevel = 1;
		this.remainingTime = 100;
	
		gameObjects = new GameObjectContainer();
		for(int col = 0; col < 15; col++) {
			gameObjects.add(new Land(this, new Position(13,col)));
			gameObjects.add(new Land(this, new Position(14,col)));		
		}

		gameObjects.add(new Land(this, new Position(Game.DIM_Y-3,9)));
		gameObjects.add(new Land(this, new Position(Game.DIM_Y-3,12)));
		for(int col = 17; col < Game.DIM_X; col++) {
			gameObjects.add(new Land(this, new Position(Game.DIM_Y-2, col)));
			gameObjects.add(new Land(this, new Position(Game.DIM_Y-1, col)));		
		}

		gameObjects.add(new Land(this, new Position(9,2)));
		gameObjects.add(new Land(this, new Position(9,5)));
		gameObjects.add(new Land(this, new Position(9,6)));
		gameObjects.add(new Land(this, new Position(9,7)));
		gameObjects.add(new Land(this, new Position(5,6)));

		int tamX = 8, tamY= 8;
		int posIniX = Game.DIM_X-3-tamX, posIniY = Game.DIM_Y-3;
		
		for(int col = 0; col < tamX; col++) {
			for (int fila = 0; fila < col+1; fila++) {
				gameObjects.add(new Land(this, new Position(posIniY- fila, posIniX+ col)));
			}
		}

		gameObjects.add(new ExitDoor(this, new Position(Game.DIM_Y-3, Game.DIM_X-1)));

		this.mario = new Mario(this, new Position(Game.DIM_Y-3, 0));
		gameObjects.add(this.mario);

		gameObjects.add(new Goomba(this, new Position(12, 6)));
		gameObjects.add(new Goomba(this, new Position(12, 8)));
		gameObjects.add(new Goomba(this, new Position(10, 10)));
		gameObjects.add(new Goomba(this, new Position(12, 11)));
		gameObjects.add(new Goomba(this, new Position(12, 14)));
		gameObjects.add(new Goomba(this, new Position(0, 19)));
		gameObjects.add(new Goomba(this, new Position(4, 6)));
	}

	private void initLevel2() {
		this.nLevel = 2;
		this.remainingTime = 100;
		
		gameObjects = new GameObjectContainer();
		for(int col = 0; col < 15; col++) {
			gameObjects.add(new Land(this, new Position(13,col)));
			gameObjects.add(new Land(this, new Position(14,col)));		
		}

		gameObjects.add(new Land(this, new Position(Game.DIM_Y-3,9)));
		gameObjects.add(new Land(this, new Position(Game.DIM_Y-3,12)));
		for(int col = 17; col < Game.DIM_X; col++) {
			gameObjects.add(new Land(this, new Position(Game.DIM_Y-2, col)));
			gameObjects.add(new Land(this, new Position(Game.DIM_Y-1, col)));		
		}

		gameObjects.add(new Land(this, new Position(9,2)));
		gameObjects.add(new Land(this, new Position(9,5)));
		gameObjects.add(new Land(this, new Position(9,6)));
		gameObjects.add(new Land(this, new Position(9,7)));
		gameObjects.add(new Land(this, new Position(5,6)));

		int tamX = 8, tamY= 8;
		int posIniX = Game.DIM_X-3-tamX, posIniY = Game.DIM_Y-3;
		
		for(int col = 0; col < tamX; col++) {
			for (int fila = 0; fila < col+1; fila++) {
				gameObjects.add(new Land(this, new Position(posIniY- fila, posIniX+ col)));
			}
		}

		this.mario = new Mario(this, new Position(Game.DIM_Y-3, 0)); 
		gameObjects.add(this.mario);

		gameObjects.add(new Goomba(this, new Position(12, 6)));
		gameObjects.add(new Goomba(this, new Position(12, 8)));
		gameObjects.add(new Goomba(this, new Position(10, 10)));
		gameObjects.add(new Goomba(this, new Position(12, 11)));
		gameObjects.add(new Goomba(this, new Position(12, 14)));
		gameObjects.add(new Goomba(this, new Position(0, 19)));
		gameObjects.add(new Goomba(this, new Position(4, 6)));
		
		gameObjects.add(new Box(this, new Position(9, 4)));
		gameObjects.add(new Mushroom(this, new Position(12, 8)));
		gameObjects.add(new Mushroom(this, new Position(2, 20)));
	}
	
	private void initLevelBlank() {
		this.nLevel = -1;
		this.remainingTime = 100;
		this.lifes = 3;
		this.points = 0;
		
		gameObjects = new GameObjectContainer();
	}
}
