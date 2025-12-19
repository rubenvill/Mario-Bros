package tp1.logic.gameobjects;

import tp1.logic.Position;
import tp1.logic.GameInterfaces.GameWorld;
import tp1.view.Messages;
import tp1.exceptions.GameParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.Action;

public class Box extends GameObject{
	private boolean full; 
	private final static int POINTS_BOX = 50;
	
	public Box(GameWorld game, Position pos) {
		super(game, pos);
		full = defaultState();
	}
	public Box() {
		super(null, new Position(0,0));
	}
	public Box(Box other) {
		super(other);
		this.full = other.full;
	}
	
	private boolean defaultState() {
		return true;
	}
	
	@Override
	public GameObject copy() {
		return new Box(this);
	}
	
	@Override
	public String getIcon() {
		if (full) return Messages.BOX;
		else return Messages.EMPTY_BOX;
	}
	
	protected boolean isFull() {
		return full;
	}
	protected void empty() {
		full = false;
	}
	
	@Override
	public boolean isSolid() {
		return true;
	}
	
	@Override
	public void update() {}
	
	
	//INTERACIONES
	@Override
	public boolean interactWith(GameItem other) {
		if (otherInRelativePos(other, Action.DOWN) && full) {
			return other.receiveInteraction(this);
		}
		return false;
	}

	@Override
	public boolean receiveInteraction(Land obj) {
		return false;
	}

	@Override
	public boolean receiveInteraction(ExitDoor obj) {
		return false;
	}

	@Override
	public boolean receiveInteraction(Mario obj) {
		if (full) {
			game.incrPoints(POINTS_BOX);
			game.addNewObject(new Mushroom(game, relativePos(Action.UP)));
			full = false;
			return true;
		}
		return false;
	}

	@Override
	public boolean receiveInteraction(Goomba obj) {
		return false;
	};
	
	@Override
	public boolean receiveInteraction(Mushroom obj) {
		return false;
	}
	
	@Override
	public boolean receiveInteraction(Box obj) {
		return false;
	}
	
	@Override
	protected String getName() {
		return Messages.OBJECT_BOX_NAME;
	}
	@Override
	protected String getShortcut() {
		return Messages.OBJECT_BOX_SHORTCUT;
	}
	@Override
	protected GameObject createObject(GameWorld game, Position pos) {
		return new Box(game, pos);
	}
	
	@Override
	public GameObject parse(String[] objDescription, GameWorld game) throws GameParseException, OffBoardException{
		GameObject go = super.parse(objDescription, game);
		if (go!= null) {
			Box obj = (Box) go;
			
			if(objDescription.length == 3){
				boolean f = parseBoxState(objDescription);
				obj.full = f;
				return obj;
			}
			else if (objDescription.length == 2){
				obj.full = defaultState();
				return obj;
			}
			else throw new GameParseException(Messages.COMMAND_ADDOBJECT_ERROR.formatted(String.join(" ", objDescription))); //tiene muchos argumentos
			}
		
		return null;
	}
	
	private boolean parseBoxState(String[] objDescription) throws GameParseException{
		String strState = objDescription[2].toUpperCase();
		if (strState.equals(Messages.STATE_BOX_EMPTY) || strState.equals(Messages.STATE_BOX_EMPTY_SHORTCUT)) {
			return false;
		}
		else if (strState.equals(Messages.STATE_BOX_FULL) || strState.equals(Messages.STATE_BOX_FULL_SHORTCUT)) {
			return true;
		}
		else throw new GameParseException(Messages.INVALID_BOX_STATUS.formatted(String.join(" ", objDescription)));
	}
	
	
	
	
	@Override
	public String toString() {
		String posName = super.toString() + " ";
		String st;
		if (full) st = Messages.STATE_BOX_FULL;
		else st = Messages.STATE_BOX_EMPTY;
		return posName + st;
	}
}
