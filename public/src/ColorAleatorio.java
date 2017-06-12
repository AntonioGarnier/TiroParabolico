/**
@author Antonio Jesús López Garnier - Correo: alu0100454437@ull.edu.es
@see <a href = "https://github.com/AntonioGarnier" > Mi Github </a>
@version 1.0
*/

import java.awt.Color;
import java.util.Random;

public class ColorAleatorio {
	
	private static final int MAX_VALOR_COLOR = 255;
	private static Random aleatorio = new Random ();
	
	public ColorAleatorio () {
		
	}
	
	/**
	 * @return the maxValorColor
	 */
	public static int getMaxValorColor() {
		return MAX_VALOR_COLOR;
	}

	/**
	 * @return the aleatorio
	 */
	public static Random getAleatorio() {
		return aleatorio;
	}

	/**
	 * @param aleatorio the aleatorio to set
	 */
	public static void setAleatorio(Random aleatorio) {
		ColorAleatorio.aleatorio = aleatorio;
	}

	public static Color getColorAleatorio() {
		return new Color (getAleatorio().nextInt(getMaxValorColor()), getAleatorio().nextInt(getMaxValorColor()), getAleatorio().nextInt(getMaxValorColor()));
	}
	
}
