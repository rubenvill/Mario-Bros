package tp1.logic;

import tp1.view.Messages;
import tp1.exceptions.ActionParseException;

public enum Action {
	LEFT(-1,0), RIGHT(1,0), DOWN(0,1), UP(0,-1), STOP(0,0); 
	
	private final int x;
	private final int y;
	
	private Action(int x, int y) {
		this.x=x;
		this.y=y;
	}
	
	public int getX() {
		return x;
	}
    public int getY() {
		return y;
	}
	
	@Override
	public String toString() {
        switch(this) {
            case LEFT: return "LEFT";
            case RIGHT: return "RIGHT";
            case UP: return "UP";
            case DOWN: return "DOWN";
            case STOP: return "STOP";
            default: return "";
        }
    }
	
	public static Action parseAction(String[] objDescription) throws ActionParseException {
		String strAction = objDescription[2].toUpperCase();
		Action action = strToAction(strAction);
		if(action != null) { 
			return action; 
		}
		else throw new ActionParseException(Messages.UNKNOWN_ACT.formatted(objDescription[2]));
	}
	
	public static Action strToAction (String action) {
		String action1 = action.toUpperCase();
		switch(action1) {
		case(Messages.ACTION_LEFT_SHORTCUT):
		case(Messages.ACTION_LEFT):
			return Action.LEFT;
		case(Messages.ACTION_RIGHT_SHORTCUT):
		case(Messages.ACTION_RIGHT):
			return Action.RIGHT;
		case(Messages.ACTION_DOWN_SHORTCUT):
		case(Messages.ACTION_DOWN):
			return Action.DOWN;
		case(Messages.ACTION_STOP_SHORTCUT):
		case(Messages.ACTION_STOP):
			return Action.STOP;
		case(Messages.ACTION_UP_SHORTCUT):
		case(Messages.ACTION_UP):
			return Action.UP;
		default:
			return null;
		}
		
	}
}