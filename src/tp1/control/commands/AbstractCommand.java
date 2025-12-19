package tp1.control.commands;

import tp1.view.Messages;

public abstract class AbstractCommand implements Command {

	private final String name;
	private final String shorcut;
	private final String details;
	private final String help;
	
	public AbstractCommand(String name, String shorcut, String details, String help) {
		this.name = name;
		this.shorcut = shorcut;
		this.details = details;
		this.help = help;
	}
	
	//matchCommandName
	protected String getName() { return name; }
	protected String getShortcut() { return shorcut; }
	//helpText
	protected String getDetails() { return details; }
	protected String getHelp() { return help; }

	protected boolean matchCommandName(String name) { 
		return getShortcut().equalsIgnoreCase(name) ||
			   getName().equalsIgnoreCase(name);
	}

	@Override
	public String helpText(){ //Prints helptext of every command
		return Messages.LINE_TAB.formatted(Messages.COMMAND_HELP_TEXT.formatted(getDetails(), getHelp()));
	}
}