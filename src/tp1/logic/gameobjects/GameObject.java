package tp1.logic.gameobjects;

import tp1.exceptions.GameParseException;
import tp1.view.Messages;
import tp1.exceptions.OffBoardException;
import tp1.exceptions.PositionParseException;
import tp1.logic.Action;
import tp1.logic.GameInterfaces.GameWorld;
//import tp1.logic.Game;
import tp1.logic.Position;

public abstract class GameObject implements GameItem{ 
	
	private Position pos;
	private boolean isAlive;
	protected GameWorld game;

	public GameObject(GameWorld game, Position pos) {
		this.isAlive = true;
		this.pos = pos;
		this.game = game;
	}
	
	protected GameObject(GameObject other) { 
		this.pos = other.pos;
		this.game = other.game;
		this.isAlive = other.isAlive;
	}
	
	public abstract String getIcon();

	@Override
	public abstract boolean isSolid();
	
	@Override
	public boolean isAlive() {
		return isAlive;
	}
	
	@Override
	public boolean isInPos(Position p) {
		return this.pos.equals(p);
	}
	

	public abstract GameObject copy ();
	
			
	
	public void dead(){
		this.isAlive = false;
	}
	
	public abstract void update();
	
	protected void move(Action dir) {
		this.pos = relativePos(dir);
	}
	
	
	public boolean doInteraction(GameItem other) { 
		boolean interaction1 = other.interactWith(this);
		boolean interaction2 = this.interactWith(other);
		return (interaction1 || interaction2);
	}
	
	protected boolean isSidePosSolid (Action dir) { 
		return game.isPosSolid(relativePos(dir));
	}
	
	protected boolean posExitsBoard(Action dir) { 
		return relativePos(dir).exitsBoard();
	}
	
	protected Position relativePos(Action dir) {
		return pos.movePos(dir);
	}
	
	protected boolean otherInPos(GameItem other) { 
		return other.isInPos(this.pos);
	}
	
	protected boolean otherInRelativePos(GameItem other, Action dir) {
		return other.isInPos(relativePos(dir));
	}

	protected abstract String getName();
		
	protected abstract String getShortcut();

	protected abstract GameObject createObject(GameWorld game, Position pos);
	
	protected boolean matchName(String name) {
		return getShortcut().equalsIgnoreCase(name) || 
				getName().equalsIgnoreCase(name);
	}

	public GameObject parse(String[] objDescription, GameWorld game) throws GameParseException, OffBoardException{
		if (matchName(objDescription[1])) {	
			try {
			Position userPos = Position.parsePos(objDescription);
			if(userPos != null) {
				return createObject(game, userPos);
			}
			}
			catch (PositionParseException pExc) {
				throw new GameParseException(Messages.INVALID_OBJ_POS.formatted(String.join(" ", objDescription)), pExc);
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		String p = pos.toString() + " ";
		return p + getName();
	}
}