package tp1.control.commands;

import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.CommandParseException;
import tp1.exceptions.GameLoadException;
import tp1.logic.GameInterfaces.GameModel;
import tp1.view.GameView;
import tp1.view.Messages;

public class LoadCommand extends AbstractCommand {
	
	private static final String NAME = Messages.COMMAND_LOAD_NAME;
	private static final String SHORTCUT = Messages.COMMAND_LOAD_SHORTCUT;
	private static final String DETAILS = Messages.COMMAND_LOAD_DETAILS;
	private static final String HELP = Messages.COMMAND_LOAD_HELP;
	private String fileName; //Stores fileName introduced by the user
	
	public LoadCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}
	
	public LoadCommand(String fileName) { 
		super(NAME, SHORTCUT, DETAILS, HELP);
		this.fileName = fileName;
	}
	
	@Override
	public void execute(GameModel game, GameView view) throws CommandExecuteException {
		try {
			game.load(fileName);
			view.showGame();
			
		} catch(GameLoadException e) {
			throw new CommandExecuteException(Messages.COMMAND_LOAD_UNABLE.formatted(fileName), e);
		}
		
		
	}
	
	@Override
	public Command parse(String[] commandWords) throws CommandParseException {
		
		if(matchCommandName(commandWords[0])) { 
			if(commandWords.length == 2)
				return new LoadCommand(commandWords[1]);
			else
				throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
		}
		
		return null;
	}
}
