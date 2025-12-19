package tp1.logic.gameobjects;

import java.util.Arrays;
import tp1.view.Messages;
import java.util.List;

import tp1.exceptions.GameParseException;
import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.GameInterfaces.GameWorld;

public class GameObjectFactory {
	
	private static final List<GameObject> availableObjects = Arrays.asList(
			new Mario(),
			new Goomba(),
			new Land(),
			new ExitDoor(),
			new Mushroom(),
			new Box()
			);
	
	public static GameObject parse(String[] objWords, GameWorld game) throws GameParseException, OffBoardException{ 
			for(GameObject go : availableObjects) { 
				GameObject parsedObject = go.parse(objWords, game);
				if(parsedObject != null) { 
					return parsedObject;
				}
			}
	
		throw new ObjectParseException(Messages.UNKNOWN_OBJECT.formatted(String.join(" ", objWords)));
	}
}
