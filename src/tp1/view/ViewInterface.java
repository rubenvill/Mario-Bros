package tp1.view;

public interface ViewInterface {
	// show methods
	public void showWelcome();
	public void showGame();
	public void showEndMessage();
	public void showError(String message);
	public void showMessage(String message);
	public String[] getPrompt();
}
