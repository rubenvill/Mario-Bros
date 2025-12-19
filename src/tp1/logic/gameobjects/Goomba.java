package tp1.logic.gameobjects;
import tp1.logic.Position;
import tp1.logic.GameInterfaces.GameWorld;
import tp1.exceptions.GameParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.Action;
import tp1.view.Messages;

public class Goomba extends MovingObject {
	
private final static int POINTS_DIE = 100;

	public Goomba(GameWorld game, Position pos) {
		super(game, pos);
		act = defaultAct();
	}
	public Goomba() {
		super(null, new Position(0,0));
	}
	
	public Goomba(Goomba other) {
		super(other);
	}
	
	@Override
	public GameObject copy() {
		return new Goomba(this);
	}
	
	@Override
	protected Action defaultAct() {
		return Action.LEFT;
	}

	@Override
	public String getIcon() {
		if (!super.isAlive()) {
			return Messages.EMPTY;
		}
		return Messages.GOOMBA;
	}
	
	@Override
	public boolean isSolid() {
		return false;
	}
	
	
	
	// INTERACCIONES
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
		if (isAlive()) {
			goombaDies();
			return true;
		}
		return false;
	}
	private void goombaDies() {
		this.dead();
        game.incrPoints(POINTS_DIE);
	}

	@Override
	public boolean receiveInteraction(Goomba obj) {
		return false;
	}
	
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
		return Messages.OBJECT_GOOMBA_NAME;
	}
	
	@Override
	protected String getShortcut() {
		return Messages.OBJECT_GOOMBA_SHORTCUT;
	}
	@Override
	protected GameObject createObject(GameWorld game, Position pos) {
		return new Goomba(game, pos);
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
