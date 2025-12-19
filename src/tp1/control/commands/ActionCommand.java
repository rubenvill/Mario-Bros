package tp1.control.commands;

import tp1.logic.GameInterfaces.GameModel;
import tp1.exceptions.CommandParseException;
import tp1.logic.Action;
import tp1.view.GameView;
import tp1.view.Messages;
import java.util.ArrayList;
import java.util.List;

public class ActionCommand extends AbstractCommand {

	private static final String name = Messages.COMMAND_ACTION_NAME;
	private static final String shortcut = Messages.COMMAND_ACTION_SHORTCUT;
	private static final String details = Messages.COMMAND_ACTION_DETAILS;
	private static final String help = Messages.COMMAND_ACTION_HELP;
	private List<Action> actions;//Stores the actions introduced by the user

	public ActionCommand () { 
		super(name, shortcut, details, help);
		this.actions = new ArrayList<>();
	}
	
	public ActionCommand (List<Action> actions) { 
		super(name, shortcut, details, help);
		this.actions = actions;
	}
	
	@Override
	public void execute(GameModel game, GameView view) {
		if(actions != null && !(actions.isEmpty())) {
			for(Action action : actions)
				game.addAction(action);
		}
		game.update();
		view.showGame();
	}
	
	@Override
	public Command parse(String[] commandWords) throws CommandParseException { 
		if(matchCommandName(commandWords[0])) { 
			if(commandWords.length > 1){
				
				List<Action> parsedActions = new ArrayList<>();
				boolean hasValidActions = false;
		        for (int i = 1; i < commandWords.length; i++) {
		            Action action = Action.strToAction(commandWords[i]);
		            if (action != null) {
		            	
		                parsedActions.add(action);
		                hasValidActions = true; 
		            }
		        }
		        if(!hasValidActions) {throw new CommandParseException(Messages.COMMAND_ACTION_INCORRECT); } //si ninguna acción introducida es correcta
		        return new ActionCommand(parsedActions);
			} else {throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER); } //tan solo se ha puesto "action"
		}
		return null;
	}
	

}