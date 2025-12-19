package tp1.logic.gameobjects;
import tp1.logic.Action;
import tp1.exceptions.ActionParseException;
import tp1.exceptions.GameParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.Position;
import tp1.logic.GameInterfaces.GameWorld;
import tp1.view.Messages;

public abstract class MovingObject extends GameObject {
	protected Action act;
	protected boolean wasFalling;
	
	public MovingObject(GameWorld game, Position pos) {
		super(game, pos);
		this.wasFalling = false;
	}
	
	protected MovingObject(MovingObject other) {
		super(other);
		this.act = other.act;
		this.wasFalling = other.wasFalling;
	}
	
	protected abstract Action defaultAct();

	@Override
	public void update() {
		if (isSidePosSolid(Action.DOWN)) {
			if (act == Action.LEFT) {
				doLeft();
			}
			else if (act == Action.RIGHT) {
				doRight();
			}
		}
		else { 
			doFall();
		}
	}
	
	private void doLeft() {
		if (isSidePosSolid(Action.LEFT) || posExitsBoard(Action.LEFT)){
			act = Action.RIGHT;
		}
		else {
			move(Action.LEFT);
		}
	}
	private void doRight() {
		if (isSidePosSolid(Action.RIGHT)||posExitsBoard(Action.RIGHT)){
			act = Action.LEFT;
		}
		else { 
			move(Action.RIGHT);
		}
	}
	private void doFall() { 
		move(Action.DOWN);
		wasFalling = true;
		if(posExitsBoard(Action.STOP))	{
			dead();
		}
	}
	
	
	@Override
	public GameObject parse(String[] objDescription, GameWorld game) throws GameParseException, OffBoardException{
		GameObject go = super.parse(objDescription, game); 
		if (go!= null) {
			MovingObject obj = (MovingObject) go;

			try {
				if(objDescription.length > 2){ 
					Action a = Action.parseAction(objDescription);
						obj.act = a;
						if (!isValidAction(a)) {
							throw new GameParseException(Messages.INVALID_ACT.formatted(String.join(" ", objDescription)));
						}
						return obj;
				}
				else {obj.act = defaultAct();}
				return obj; 
			}
			catch (ActionParseException aExc) {
				throw new GameParseException(Messages.UNKNOWN_OBJ_DIR.formatted(String.join(" ", objDescription)), aExc);
			}
		}
		
		return null; 
	}
	private boolean isValidAction(Action a) {
		return a == Action.RIGHT || a == Action.LEFT || a == Action.STOP ;
	}
	
	
	@Override
	public String toString() { 
		String posName = super.toString() + " ";
		String ac = act.toString();
		return posName + ac;
	}
}