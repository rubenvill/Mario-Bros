package tp1.logic.gameobjects;

import tp1.exceptions.GameParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.Position;
import tp1.logic.GameInterfaces.GameWorld;
import tp1.view.Messages;

public class Land extends GameObject{

	public Land(GameWorld game, Position pos) {
		super(game, pos);
	}
	
	public Land() {
		super(null, new Position(0,0));
	}
	
	public Land(Land other) {
		super(other);
	}
	
	@Override
	public GameObject copy() {
		return new Land(this);
	}
	
	@Override
	public String getIcon() {
		return Messages.LAND;
	}
	
	@Override
	public boolean isSolid() {
		return true;
	}
	
	@Override
	public void update() {}
	
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
		return Messages.OBJECT_LAND_NAME;
	}
	@Override
	protected String getShortcut() {
		return Messages.OBJECT_LAND_SHORTCUT;
	}
	@Override
	protected GameObject createObject(GameWorld game, Position pos) {
		return new Land(game, pos);
	}
	
	@Override
	public GameObject parse(String[] objDescription, GameWorld game) throws GameParseException, OffBoardException{
		GameObject obj = super.parse(objDescription, game);
		if (obj != null) {
			if (objDescription.length > 2) {
				throw new GameParseException(Messages.COMMAND_ADDOBJECT_ERROR.formatted(String.join(" ", objDescription)));
			}
		}
		return obj;
	}
}
