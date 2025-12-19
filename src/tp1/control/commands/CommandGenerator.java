package tp1.control.commands;

import java.util.Arrays;
import java.util.List;

import tp1.exceptions.CommandParseException;
import tp1.view.Messages;

public class CommandGenerator {
	private static final List<Command> availableCommands = Arrays.asList(
			new LoadCommand(),
			new SaveCommand(),
			new AddObjectCommand(),
			new ActionCommand(),
			new UpdateCommand(),
			new ResetCommand(),
			new HelpCommand(),
			new ExitCommand()
	);
	
	public static Command parse(String[] commandWords) throws CommandParseException {
			
		for (Command c: availableCommands) {
				Command parsedCommand = c.parse(commandWords);
				if(parsedCommand != null) {
					return parsedCommand;
				}
			}
			throw new CommandParseException(Messages.UNKNOWN_COMMAND.formatted(String.join(" ", commandWords)));
		}
	
	public static String commandHelp() { 
		StringBuilder commands = new StringBuilder();
		
		commands.append(Messages.HELP_AVAILABLE_COMMANDS).append(Messages.LINE_SEPARATOR);
		
		for (Command c: availableCommands) { 
			commands.append(c.helpText());
		}
		
		return commands.toString();
	}

}