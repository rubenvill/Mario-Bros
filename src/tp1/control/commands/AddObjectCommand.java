package tp1.control.commands;

import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.CommandParseException;
import tp1.exceptions.GameModelException;
import tp1.logic.GameInterfaces.GameModel;
import tp1.view.GameView;
import tp1.view.Messages;
import java.util.Arrays;
import tp1.logic.gameobjects.GameObject;

public class AddObjectCommand extends AbstractCommand{
	private static final String NAME = Messages.COMMAND_ADDOBJECT_NAME;
	private static final String SHORTCUT = Messages.COMMAND_ADDOBJECT_SHORTCUT;
	private static final String DETAILS = Messages.COMMAND_ADDOBJECT_DETAILS;
	private static final String HELP = Messages.COMMAND_ADDOBJECT_HELP;
	private String[] objectDescription;
	
	public AddObjectCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}
	
	public void execute(GameModel game, GameView view) throws CommandExecuteException {
		try {
			GameObject gameObject = game.addGameObject(this.objectDescription);
			if (gameObject != null) {
				view.showGame();
			} 
			
		} catch (GameModelException e) {
				 throw new CommandExecuteException(Messages.ERROR_COMMAND_EXECUTE, e);
		}
	}
	
	public Command parse(String[] objWords) throws CommandParseException {
		if(matchCommandName(objWords[0])) {
			if(objWords.length >= 3) {
				this.objectDescription = Arrays.copyOfRange(objWords, 1, objWords.length); 
				return this;
			}
			else {
				throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
				
			}
		}
		
		return null;
	}
	
	@Override
	public String helpText(){ 
		return super.helpText() + (Messages.LINE_2TABS).formatted(Messages.COMMAND_ADDOBJECT_EXECUTION_EXAMPLE);
	}
	
}