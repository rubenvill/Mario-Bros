package tp1.util;

public class MyStringUtils {
	
	public static String repeat(String x, int n) {
		StringBuilder result = new StringBuilder(x.length() * n);

		
		for (int i = 0; i < n; i++)
			result.append(x);

		return result.toString();
	}

	public static String center(String texto, int ancho) {
		return center(texto, ancho, ' ');
	}

	public static String center(String texto, int ancho, char relleno) {
		if (ancho > texto.length()) {
			StringBuilder result = new StringBuilder(ancho);
			
			int vacio = ancho - texto.length();
			int pre = vacio / 2;
			int post = (vacio + 1) / 2;
			
			result.append(repeat("" + relleno, pre));
			result.append(texto);
			result.append(repeat("" + relleno, post));
			
			return result.toString();
		}
		else
			return texto.substring(0, ancho);
	}
	
	public static String right(String texto, int ancho) {
		return right(texto, ancho, ' ');
	}

	public static String right(String texto, int ancho, char relleno) {
		if (ancho > texto.length()) {
			return MyStringUtils.repeat(Character.toString(relleno), ancho - texto.length()) + texto;
		}
		else
			return texto.substring(0, ancho);
	}
	
	public static String left(String texto, int ancho) {
		return left(texto, ancho, ' ');
	}

	public static String left(String texto, int ancho, char relleno) {
		if (ancho > texto.length()) {
			return texto + MyStringUtils.repeat(Character.toString(relleno), ancho - texto.length());
		}
		else
			return texto.substring(0, ancho);
	}
	
	public static String onlyUpper(String str) {
		return str
	            .chars()
	            .filter( c -> Character.isUpperCase( c ) )
	            .collect(
	                StringBuilder::new,
	                ( sb, ch ) -> sb.append( (char)ch ),
	                ( sb1, sb2 ) -> sb1.append( sb2 )
	                )
	            .toString();
	}
	
	public static String[] splitWords(String line) {
		return line.trim().split("\\s+");
	}
}
