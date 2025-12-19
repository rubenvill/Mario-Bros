package tp1.logic;

import java.util.Objects;
import tp1.view.Messages;

import tp1.exceptions.OffBoardException;
import tp1.exceptions.PositionParseException;

public class Position {

	private final int col;
	private final int row;
	
	public Position() {
		col = 0;
		row = 0;
	}
	public Position(int r, int c) {
		this.col = c;
		this.row = r;
	}

	public Position(Position other) {
		this.row = other.row;
		this.col = other.col;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(col, row);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		return col == other.col && row == other.row;
	}
	
	public Position movePos(Action act) { 
		return new Position(this.row + act.getY(), this.col + act.getX());
	}
	
	public boolean exitsBoard() { 
		return ((this.col >= Game.DIM_X || this.col < 0)||(this.row >= Game.DIM_Y || this.row < 0));
	}
	
	@Override
	public String toString() {
		return "(" + row + "," + col + ")";
	}

	public static Position parsePos(String[] objDescription) throws PositionParseException, OffBoardException { 
		String posInStr = objDescription[0];
		
		if(posInStr.startsWith("(") && posInStr.endsWith(")")) { 
			String[] coords = posInStr.substring(1, posInStr.length() - 1).split(",");
			
			if(coords.length == 2) { 
		    	try {
		    		int row = Integer.parseInt(coords[0]);
		    		
		    		int col = Integer.parseInt(coords[1]);
		    		Position pos = new Position(row, col);
		    		if(!(pos.exitsBoard())) {
		    			return pos; 
		    		}
		    		else throw new OffBoardException(Messages.POS_OFFBOARD.formatted(String.join(" ", objDescription))); //POSICIÓN FUERA DEL RANGO DEL TABLERO
		    	}
		    	catch (NumberFormatException e) {
		    		throw new PositionParseException(Messages.INVALID_POS.formatted(posInStr), e); //NO CONTIENE NUMEROS; 
		    	}
		    }
			else throw new PositionParseException(Messages.INVALID_POS.formatted(posInStr)); //FORMATO INVALIDO DE CARACTERES INSUFICIENTES
		}
		
		else throw new PositionParseException(Messages.INVALID_POS.formatted(posInStr)); //FORMATO INVALIDO DE PARENTESIS
		//return null;
	}
}
		    