package tp1.logic.gameobjects;
import tp1.logic.GameInterfaces.GameWorld;
import tp1.logic.Position;
import tp1.exceptions.GameParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.Action;
import tp1.view.Messages;

public class Mushroom extends MovingObject {

	public Mushroom (GameWorld game, Position pos) {
		super(game, pos);
		act = defaultAct();
	}
	public Mushroom () {
		super(null, new Position(0,0));
		act = Action.RIGHT;
	}
	public Mushroom(Mushroom other) {
		super(other);
	}
	
	@Override
	public GameObject copy() {
		return new Mushroom(this);
	}
	
	
	@Override
	protected Action defaultAct() {
		return Action.RIGHT;
	}
	
	@Override
	public String getIcon() {
		if (!super.isAlive()) {
			return Messages.EMPTY;
		}
		return Messages.MUSHROOM;
	}
	
	@Override
	public boolean isSolid() {
		return false;
	}

	@Override
	public boolean interactWith(GameItem other) {
		if (otherInPos(other)) {
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
		this.dead();
		return true;
	}
	@Override
	public boolean receiveInteraction(Goomba obj) {
		return false;
	}
	
	public boolean receiveInteraction(Mushroom obj) {
		return false;
	}
	
	public boolean receiveInteraction(Box obj) {
		return false;
	}
	
	@Override
	protected String getName() {
		return Messages.OBJECT_MUSHROOM_NAME;
	}
	@Override
	protected String getShortcut() {
		return Messages.OBJECT_MUSHROOM_SHORTCUT;
	}
	@Override
	protected GameObject createObject(GameWorld game, Position pos) {
		return new Mushroom(game, pos);
	}
	
	@Override
	public GameObject parse(String[] objDescription, GameWorld game) throws GameParseException, OffBoardException{
		GameObject obj = super.parse(objDescription, game);
		if (objDescription.length > 3) {
			throw new GameParseException(Messages.COMMAND_ADDOBJECT_ERROR.formatted(String.join(" ", objDescription)));
		}
		return obj;
	}
}
