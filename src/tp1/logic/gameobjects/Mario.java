package tp1.logic.gameobjects;

import tp1.exceptions.GameParseException;
import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.Action;
import tp1.logic.ActionList;
import tp1.view.Messages;
import tp1.logic.Position;
import tp1.logic.GameInterfaces.GameWorld;

public class Mario extends MovingObject{
	private boolean big;
	private Position bigPos;
	private ActionList actList;
	private boolean isRising;

	public Mario(GameWorld game, Position pos) {
		super(game, pos);
		act = defaultAct();
		actList = new ActionList(); 
		big = defaultSize();
		isRising = false;
		posIfBig();
	}
	
	public Mario() {
		super(null, new Position (0,0));
	}
	
	public Mario(Mario other) {
		super(other);
		this.isRising = other.isRising;
		this.big = other.big;
		this.actList = new ActionList();
		this.posIfBig();
	}
	
	@Override	
	public String getIcon(){
		if (!super.isAlive()) return Messages.EMPTY;
		else {
			if (act == Action.RIGHT) {
				return Messages.MARIO_RIGHT;
			}
			else if (act == Action.LEFT){
				return Messages.MARIO_LEFT;
			}
			else {
				return Messages.MARIO_STOP;
			}
		}
	}
	
	@Override 
	public boolean isSolid() { 
		return false;
	}
	
	@Override
	public GameObject copy() {
		return new Mario(this);
	}
	
	@Override
	protected Action defaultAct() {
		return Action.RIGHT;
	}
	
	private boolean defaultSize() {
		return true;
	}
	
	public void addAction(Action act) {
		actList.add(act);
	}
	
	private void posIfBig() {
		if (big) {
			bigPos = relativePos(Action.UP);
		}
		else bigPos = null;
	}
	
	private void becomeBig() {
		this.big = true; 
		this.posIfBig();
	}

	@Override
	public boolean isInPos(Position p) { 
		posIfBig(); 
		return super.isInPos(p) || (this.big && this.bigPos.equals(p)); 
	}
	
	@Override 
	protected boolean isSidePosSolid(Action dir) {
		if (big) {
			return ((game.isPosSolid(bigPos.movePos(dir)) || super.isSidePosSolid(dir)));
		}
		else{
			return super.isSidePosSolid(dir);
		}
	}
	@Override
	protected boolean posExitsBoard(Action dir) {
		if (big) {
			return (bigPos.movePos(dir).exitsBoard() || super.posExitsBoard(dir));
		}
		else {
			return super.posExitsBoard(dir);
		}
	}

	@Override
	protected void move(Action dir) {
		if (big) {
			bigPos = relativePos(Action.UP);		
		}
		super.move(dir);
	}
	
	private void automaticMovement() {
		super.update();
		posIfBig(); 
		if (!isAlive()) game.marioDies();
		game.interact(this);
	}
	
	private void manualMovement() {
		for(int i = 0; i < actList.getSize(); i++) {
			executeAction(actList.getAction(i));
			posIfBig();
			game.interact(this);
		}
		actList.clearList();
	}
	
	@Override
	public void update() {
		if(actList.isListEmpty()) { 
			automaticMovement();
		}
		else {
			manualMovement();
		}
		wasFalling = false; 
		isRising = false; 
	}
	
	private void executeAction (Action a) {
		switch(a) {
		case Action.RIGHT:
		case Action.LEFT:{
			horizontalAction(a);
			break;
		}
		case Action.UP:{
			upAction();
			break;
		}
		case Action.DOWN:{
			downAction();
			break;
		}
		default: { 
			stopAction();
			break;
		}
		}
	}
	
	private void horizontalAction(Action a) {
		if (isSidePosSolid(a) || posExitsBoard(a)) { 
			if (a == Action.RIGHT) {
				act = Action.LEFT;
			}
			else if (a == Action.LEFT) {
				act = Action.RIGHT;
			}
		} 
		else { 
			act = a;
			move(a);
		}
	}
	private void upAction() {
		isRising = true;
		if (!isSidePosSolid(Action.UP) && !posExitsBoard(Action.UP)) {
			move(Action.UP);
		}
	}
	private void downAction() {
		while (!isSidePosSolid(Action.DOWN) && !posExitsBoard(Action.DOWN)) {
			move(Action.DOWN);
			wasFalling = true;
			game.interact(this);
		}
		if (posExitsBoard(Action.DOWN)) {
			dead();
			game.marioDies();
		}
		if (isSidePosSolid(Action.DOWN) && (!posExitsBoard(Action.DOWN))){ 
			if(!wasFalling) { 
				act = Action.STOP;
			} 
		}
	}
	private void stopAction() {
		act = Action.STOP;
	}
	
	@Override
	public boolean interactWith(GameItem other) {
		if (otherInPos(other) || (this.big && other.isInPos(bigPos))) {
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
		game.marioExited();
		return true;
	}

	@Override
	public boolean receiveInteraction(Mario obj) {
		return false;
	}
	
	@Override
	public boolean receiveInteraction(Goomba obj) {
		if (isAlive()) {
			if(wasFalling) {
				return true;
			}
			else { 
				if (big) { 
					big = false;
					return true;
				}
				else {
					dead();
					game.marioDies();
					return true;
				}
			}
		}
		return false;
	}
	@Override
	public boolean receiveInteraction(Mushroom obj) {
		if(!big) {
			becomeBig();
			return true;
		}
		return false;
	}
	
	@Override
	public boolean receiveInteraction(Box obj) {
		if (isRising) { 
			return obj.receiveInteraction(this);
		}
		return false;
	}

	@Override
	protected String getName() {
		return Messages.OBJECT_MARIO_NAME;
	}
	@Override
	protected String getShortcut() {
		return Messages.OBJECT_MARIO_SHORTCUT;
	}
	@Override
	protected GameObject createObject(GameWorld game, Position pos) {
		return new Mario(game, pos);
	}
	
	@Override 
	public Mario parse(String[] objectDescription, GameWorld game) throws GameParseException, OffBoardException {
		GameObject go = super.parse(objectDescription, game); 
		if (go!= null) {
			Mario mario = (Mario)go;
			
			if (objectDescription.length > 4) {
				throw new ObjectParseException(Messages.COMMAND_ADDOBJECT_ERROR.formatted(String.join(" ", objectDescription))); 
			}
			
			if (objectDescription.length == 4) {

				String strState = objectDescription[3].toUpperCase();
				if(strState.equals(Messages.STATE_SMALL) || strState.equals(Messages.STATE_SMALL_SHORTCUT)) {
					mario.big = false;
				}
				else if (strState.equals(Messages.STATE_BIG) || strState.equals(Messages.STATE_BIG_SHORTCUT)) {
					mario.big = true;
				}
				else throw new GameParseException(Messages.INVALID_MARIO_SIZE.formatted(String.join(" ", objectDescription)));
			}
			return mario;
		}
		return null;
	}
	
	
	
	@Override
	public String toString() {
		String posNameAct = super.toString() + " "; //1ºMovingObject --> 2ºGameObject
		String size;
		if (big) size = Messages.STATE_BIG;
		else size = Messages.STATE_SMALL;
		return posNameAct + size;
	}
}
