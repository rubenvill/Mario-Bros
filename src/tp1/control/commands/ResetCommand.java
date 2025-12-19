package tp1.control.commands;
import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.CommandParseException;
import tp1.logic.GameInterfaces.GameModel;
import tp1.view.GameView;
import tp1.view.Messages;

public class ResetCommand extends AbstractCommand{ 

	   private static final String NAME = Messages.COMMAND_RESET_NAME;
	   private static final String SHORTCUT = Messages.COMMAND_RESET_SHORTCUT;
	   private static final String DETAILS = Messages.COMMAND_RESET_DETAILS;
	   private static final String HELP = Messages.COMMAND_RESET_HELP;
	   private Integer nlevel;
	   
	   public ResetCommand() { 
		super(NAME, SHORTCUT, DETAILS, HELP);
	   }
	   
	   public ResetCommand(int nlevel) { 
		   super(NAME, SHORTCUT, DETAILS, HELP);
		   this.nlevel = nlevel;
	   }
	     
	@Override
	public void execute(GameModel game, GameView view) throws CommandExecuteException {
		if(nlevel != null) {
			if(nlevel == 0 || nlevel == 1 || nlevel == -1 || nlevel == 2) {
				game.resetGame(nlevel);
				view.showGame();
			}
			else
				throw new CommandExecuteException(Messages.INVALID_LEVEL_NUMBER);
		}
		else {
			game.resetGame();
			view.showGame();
		}
	}

	@Override
	public Command parse(String[] commandWords) throws CommandParseException {
		if(matchCommandName(commandWords[0])) {
			if(commandWords.length == 1) 
				return new ResetCommand();
			
			else if (commandWords.length == 2) {
				
				try {
					nlevel = Integer.parseInt(commandWords[1]);
					return new ResetCommand(nlevel);
					} catch(NumberFormatException e) { 
						throw new CommandParseException(Messages.LEVEL_NOT_A_NUMBER_ERROR.formatted(commandWords[1]), e);
				}
				
			} else {
				throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
			}
		}
				
		return null;
	}

}
