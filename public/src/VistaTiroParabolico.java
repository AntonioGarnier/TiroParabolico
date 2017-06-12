/**
Practica 12: Tiro Parabolico
Programación Gráfica.
Programación orientada a Eventos.
Enfatizar la orientación a objetos en el desarrollo de aplicaciones.
Desarrollo guiado por tests (TDD).
Graphics2D.
Aquí se implementa la vista del tiro parabolico
@author Antonio Jesús López Garnier - Correo: alu0100454437@ull.edu.es
@see <a href = "https://campusvirtual.ull.es/1617/user/profile.php?id=16443" > Perfil Campus Virtual ULL </a>
@see <a href = "https://github.com/AntonioGarnier" > Mi Github </a>
@version 3/05/2017 - 1.0
*/


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class VistaTiroParabolico extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final int DISTANCIA_EJE_X_INICIAL = 300;
	private static final double X_INICIAL = 0.05;
	private static final double Y_INICIAL = 0.95;
	private static final double FACTOR_ESCALA = 1.5;
	private static final int LINEA_PEQUENIA = 3;
	private static final int LINEA_GRANDE = 6;
	private static final int MINIMO_ESCALA_EJES = 300;
	private static final int MAXIMO_ESCALA_EJES = 400;
	private static final int LONGITUD_FLECHA = 5;
	private static final int RADIO_OBJETO = 8;
	private double escalaX, escalaY, angulo, altura;
	private ArrayList<ModeloTiroParabolico> tiros = new ArrayList<ModeloTiroParabolico> ();
	private Point2D.Double centroCoordenadas;
	
	public VistaTiroParabolico () {
		setBackground(Color.GRAY);
	}

	public void addTiro (double velocidadInicial, double anguloInicial, double alturaInicial, boolean mostrarRastro, boolean mostrarVelocidad, boolean mostrarVectorPosicion, InformacionPanel panelInfo) {
		getTiros().add(new ModeloTiroParabolico(velocidadInicial, anguloInicial, alturaInicial, this, mostrarRastro, mostrarVelocidad, mostrarVectorPosicion, panelInfo));
	}
	
	protected void paintComponent (Graphics objetoGrafico) {
		 super.paintComponent(objetoGrafico);
		 drawEjes(objetoGrafico); 
		 drawEscala(objetoGrafico);
		 drawParabola(objetoGrafico);
		 drawFlechaOrigen(objetoGrafico);
	}
	
	/**
	 * Dibuja la flecha que representa el cañon
	 * @param objetoGrafico
	 */
	protected void drawFlechaOrigen(Graphics objetoGrafico) {
		Graphics2D graficador = (Graphics2D) objetoGrafico;
		graficador.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graficador.setColor(Color.RED);
		graficador.setStroke(new BasicStroke (3));
		graficador.draw(new Line2D.Double(getCentroCoordenadas().getX(), getCentroCoordenadas().getY() - getAltura() * getEscalaY(), getCentroCoordenadas().getX() + (Math.cos(Math.toRadians(getAngulo())) * 25) * getEscalaX(), getCentroCoordenadas().getY() - (getAltura() * getEscalaY()) + Math.sin(Math.toRadians(getAngulo())) * 25 * (-1) * getEscalaY()));
		drawFlecha(graficador, new Point2D.Double(getCentroCoordenadas().getX() + (Math.cos(Math.toRadians(getAngulo())) * 25) * getEscalaX(), getCentroCoordenadas().getY() - (getAltura() * getEscalaY()) + Math.sin(Math.toRadians(getAngulo())) * 25 * (-1) * getEscalaY()), new Point2D.Double(getCentroCoordenadas().getX(), getCentroCoordenadas().getY() - (getAltura() * getEscalaY())));
	}
	
	/**
	 * Dibujamos la parabola
	 * @param objetoGrafico
	 */
	protected void drawParabola (Graphics objetoGrafico) {
		Graphics2D graficador = (Graphics2D) objetoGrafico;
		graficador.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		getTiros().forEach(tiro -> {	// recorremos el array que almacena los tiros
			if (tiro.isValido()) {	// Si es un tiro valido, lo pinta
				graficador.setColor(tiro.getColorBola());
				graficador.fill(new Ellipse2D.Double(tiro.getPuntoEnMovimiento().getX() * getEscalaX() + getCentroCoordenadas().getX() - getRadioObjeto() / 2, tiro.getPuntoEnMovimiento().getY() * getEscalaY() + getCentroCoordenadas().getY() - getRadioObjeto() / 2, getRadioObjeto(), getRadioObjeto()));
				// Comprueba si se debe mostrar el rastro
				if (tiro.isMostrarRastro()) {
					graficador.setColor(tiro.getColorRastro());				
					for (int i = 0; i < tiro.getContadorPuntoActual() - 1; i++) // el rastro lo dibujamos uniendo lineas de un punto n hasta el n+1 y sucesivamente
						graficador.draw(new Line2D.Double(tiro.getTrayectoria().get(i).getX() * getEscalaX() + getCentroCoordenadas().getX(), tiro.getTrayectoria().get(i).getY() * getEscalaY() + getCentroCoordenadas().getY(), tiro.getTrayectoria().get(i + 1).getX() * getEscalaX() + getCentroCoordenadas().getX(), tiro.getTrayectoria().get(i + 1).getY() * getEscalaY() + getCentroCoordenadas().getY()));
				}
				// Comprueba si se debe mostrar el vector de posicion
				if (tiro.isMostrarVectorPosicion()) {
					graficador.setStroke(new BasicStroke (2));
					graficador.setColor(tiro.getColorVector());
					graficador.draw(new Line2D.Double(getCentroCoordenadas().getX(), getCentroCoordenadas().getY(), tiro.getPuntoEnMovimiento().getX() * getEscalaX() + getCentroCoordenadas().getX(), tiro.getPuntoEnMovimiento().getY() * getEscalaY() + getCentroCoordenadas().getY()));
					drawFlecha(graficador, new Point2D.Double (tiro.getPuntoEnMovimiento().getX() * getEscalaX() + getCentroCoordenadas().getX(), tiro.getPuntoEnMovimiento().getY() * getEscalaY() + getCentroCoordenadas().getY()), getCentroCoordenadas());
				}
				// Comprueba si se debe mostrar la velocidad
				if (tiro.isMostrarVelocidad()) {
					graficador.setColor(tiro.getColorVector());
					graficador.draw(new Line2D.Double(tiro.getPuntoEnMovimiento().getX() * getEscalaX() + getCentroCoordenadas().getX(), tiro.getPuntoEnMovimiento().getY() * getEscalaY() + getCentroCoordenadas().getY(), tiro.getPuntoEnMovimiento().getX() * getEscalaX() + getCentroCoordenadas().getX() + (tiro.getVectorX() * getEscalaX()), tiro.getPuntoEnMovimiento().getY() * getEscalaY() + getCentroCoordenadas().getY()));
					drawFlecha(graficador, new Point2D.Double (tiro.getPuntoEnMovimiento().getX() * getEscalaX() + getCentroCoordenadas().getX() + (tiro.getVectorX() * getEscalaX()), tiro.getPuntoEnMovimiento().getY() * getEscalaY() + getCentroCoordenadas().getY()), new Point2D.Double(tiro.getPuntoEnMovimiento().getX() * getEscalaX() + getCentroCoordenadas().getX(), tiro.getPuntoEnMovimiento().getY() * getEscalaY() + getCentroCoordenadas().getY()));
					graficador.draw(new Line2D.Double(tiro.getPuntoEnMovimiento().getX() * getEscalaX() + getCentroCoordenadas().getX(), tiro.getPuntoEnMovimiento().getY() * getEscalaY() + getCentroCoordenadas().getY(), tiro.getPuntoEnMovimiento().getX() * getEscalaX() + getCentroCoordenadas().getX(), tiro.getPuntoEnMovimiento().getY() * getEscalaY() + getCentroCoordenadas().getY() + (tiro.getVectorYTag() * getEscalaY() * (- 1))));
					drawFlecha(graficador, new Point2D.Double (tiro.getPuntoEnMovimiento().getX() * getEscalaX() + getCentroCoordenadas().getX(), tiro.getPuntoEnMovimiento().getY() * getEscalaY() + getCentroCoordenadas().getY() + (tiro.getVectorYTag() * getEscalaY() * (- 1))), new Point2D.Double(tiro.getPuntoEnMovimiento().getX() * getEscalaX() + getCentroCoordenadas().getX(), tiro.getPuntoEnMovimiento().getY() * getEscalaY() + getCentroCoordenadas().getY()));
				}
			}
		});
	}
	
	/*
	 * Dividimos: (ancho total del eje / Alcance maximo * 1.5) = escala -> Nos dara un valor en pixeles que representara la unidad. 
	 * Por ejemplo Alcance maximo = 200; ancho total del eje 900.
	 * 200 * 1.5 / 900 = 3 pixeles -> 3 pixeles representarian la unidad, cada 3 pixeles es una unidad.
	 * Si superamos el alcance maximo  de 100, dibujamos lineas chicas cada (10 * escala) (ej: 3 pixeles) y pintamos una grande cada
	 * 10 veces que hagamos eso y ponemos un drawString con el numero.
	 * Si es inferior a 100, dibujamos segun la escala unidad y cada 10 iteraciones ponemos el drawString
	 * 3 rollos: por encima de 500, entre 100 y 500 y menos de 100
	 * 
	 */
	protected void drawSeparacionesX (Graphics objetoGrafico, double escalaX, double separacionCorta, double separacionLarga, double limite) {
		Graphics2D graficador = (Graphics2D) objetoGrafico;
		graficador.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		for (double i = 0; i <= limite; i += separacionCorta)
			if (i % separacionLarga == 0) {
				graficador.draw(new Line2D.Double(getCentroCoordenadas().getX() + i * escalaX, getCentroCoordenadas().getY() + getLineaGrande() , getCentroCoordenadas().getX() + i * escalaX, getCentroCoordenadas().getY() - getLineaGrande()));
				graficador.drawString("" +i, (int)(getCentroCoordenadas().getX() + i * escalaX), (int)getCentroCoordenadas().getY() + getLineaGrande() + 5);
			}
			else
				graficador.draw(new Line2D.Double(getCentroCoordenadas().getX() + i * escalaX, getCentroCoordenadas().getY() + getLineaPequenia() , getCentroCoordenadas().getX() + i * escalaX, getCentroCoordenadas().getY() - getLineaPequenia()));
	}
	
	protected void drawSeparacionesY (Graphics objetoGrafico, double escalaY, double separacionCorta, double separacionLarga, double limite) {
		Graphics2D graficador = (Graphics2D) objetoGrafico;
		graficador.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		for (double i = 0; i <= limite; i += separacionCorta)
			if (i % separacionLarga == 0) {
				graficador.draw(new Line2D.Double(getCentroCoordenadas().getX() + getLineaGrande(), getCentroCoordenadas().getY() + i * escalaY * (- 1) , getCentroCoordenadas().getX() - getLineaGrande(), getCentroCoordenadas().getY() + i * escalaY * (- 1)));
				objetoGrafico.drawString("" +i, (int)(getCentroCoordenadas().getX() + getLineaGrande() + 5), (int)(getCentroCoordenadas().getY() + i * escalaY * (- 1)));
			}
			else
				graficador.draw(new Line2D.Double(getCentroCoordenadas().getX() + getLineaPequenia(), getCentroCoordenadas().getY() + i * escalaY * (- 1) , getCentroCoordenadas().getX() - getLineaPequenia(), getCentroCoordenadas().getY() + i * escalaY * (- 1)));
	}
	
	protected void drawEscala(Graphics objetoGrafico) {
		setEscalaX((0.9 * getWidth()) / getMaximoEscalaEjes());
		setEscalaY((0.9 * getHeight()) / getMinimoEscalaEjes());
		if (!getTiros().isEmpty()) {
			// Para el ejeX
			double limite = getTiros().get(0).getxMaxima() * getFactorEscala();	// Maximo que puede alcanzar el eje X
			if (limite < getMaximoEscalaEjes())
				limite = getMaximoEscalaEjes();
			setEscalaX((0.9 * getWidth()) / limite);	// Establecemos la escala para el eje X
			if (limite >= getMaximoEscalaEjes())
				drawSeparacionesX(objetoGrafico, getEscalaX(), 10, 100, limite);
			else  
				drawSeparacionesX(objetoGrafico, getEscalaX(), 10, 50, limite);
			// Para el ejeY
			limite = getTiros().get(0).getyMaxima() * getFactorEscala();	// Maximo que puede alcanzar el eje Y
			if (limite < getMaximoEscalaEjes())
				limite = getMinimoEscalaEjes();
			setEscalaY((0.9 * getHeight()) / limite);		// Establecemos la escala para el eje Y
			if (limite >= getMaximoEscalaEjes())
				drawSeparacionesY(objetoGrafico, getEscalaY(), 10, 100, limite);
			else
				drawSeparacionesY(objetoGrafico, getEscalaY(), 10, 50, limite);

		}
			
	}

	protected void drawEjes (Graphics objetoGrafico) {
		Graphics2D graficador = (Graphics2D) objetoGrafico;
		graficador.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		// Establecemos el centro de coordenadas
		setCentroCoordenadas(new Point2D.Double (getxInicial() * getWidth(), getyInicial() * getHeight()));
		// Eje X
		graficador.draw( new Line2D.Double(getCentroCoordenadas().getX(), getCentroCoordenadas().getY(), getyInicial() * getWidth(), getCentroCoordenadas().getY()));
		graficador.draw( new Line2D.Double(getyInicial() * getWidth(), getCentroCoordenadas().getY(), getyInicial() * getWidth() - getLongitudFlecha(), getCentroCoordenadas().getY() - getLongitudFlecha()));
		graficador.draw( new Line2D.Double(getyInicial() * getWidth(), getCentroCoordenadas().getY(), getyInicial() * getWidth() - getLongitudFlecha(), getCentroCoordenadas().getY() + getLongitudFlecha()));
		// Eje Y
		graficador.draw( new Line2D.Double(getCentroCoordenadas().getX(), getCentroCoordenadas().getY(), getCentroCoordenadas().getX(), getxInicial() * getHeight()));
		graficador.draw( new Line2D.Double(getCentroCoordenadas().getX(), getxInicial() * getHeight(), getCentroCoordenadas().getX() + getLongitudFlecha(), getxInicial() * getHeight() + getLongitudFlecha()));
		graficador.draw( new Line2D.Double(getCentroCoordenadas().getX(), getxInicial() * getHeight(), getCentroCoordenadas().getX() - getLongitudFlecha(), getxInicial() * getHeight() + getLongitudFlecha()));

	}
	
	/**
	 * Pinta una punta de flecha
	 * @param g2
	 * @param frente	Define el punto donde se dibujara la punta de la flecha
	 * @param cola		Define la cola de la flecha
	 */
	private void drawFlecha(Graphics2D g2, Point2D frente, Point2D cola) {
		double phi = Math.toRadians(30);  // Angulo de apertura
		double barb = 20;						 // Longitud de la flecha
		double dy = frente.getY() - cola.getY();
		double dx = frente.getX() - cola.getX();
		double theta = Math.atan2(dy, dx);
		double x, y, rho = theta + phi;
		for(int j = 0; j < 2; j++) {
			x = frente.getX() - barb * Math.cos(rho);
			y = frente.getY() - barb * Math.sin(rho);
			g2.draw(new Line2D.Double(frente.getX(), frente.getY(), x, y));
			rho = theta - phi;
		}
   }
	
	/**
	 * @return the centroCoordenadas
	 */
	public Point2D.Double getCentroCoordenadas() {
		return centroCoordenadas;
	}

	/**
	 * @param centroCoordenadas the centroCoordenadas to set
	 */
	public void setCentroCoordenadas(Point2D.Double centroCoordenadas) {
		this.centroCoordenadas = centroCoordenadas;
	}

	/**
	 * @return the tiros
	 */
	public ArrayList<ModeloTiroParabolico> getTiros() {
		return tiros;
	}

	/**
	 * @param tiros the tiros to set
	 */
	public void setTiros(ArrayList<ModeloTiroParabolico> tiros) {
		this.tiros = tiros;
	}

	/**
	 * @return the distanciaEjeXInicial
	 */
	public static int getDistanciaEjeXInicial() {
		return DISTANCIA_EJE_X_INICIAL;
	}

	/**
	 * @return the radioObjeto
	 */
	public static int getRadioObjeto() {
		return RADIO_OBJETO;
	}

	/**
	 * @return the xInicial
	 */
	public static double getxInicial() {
		return X_INICIAL;
	}

	/**
	 * @return the yInicial
	 */
	public static double getyInicial() {
		return Y_INICIAL;
	}

	/**
	 * @return the longitudFlecha
	 */
	public static int getLongitudFlecha() {
		return LONGITUD_FLECHA;
	}

	/**
	 * @return the minimoEscalaEjes
	 */
	public static int getMinimoEscalaEjes() {
		return MINIMO_ESCALA_EJES;
	}

	/**
	 * @return the lineaPequenia
	 */
	public static int getLineaPequenia() {
		return LINEA_PEQUENIA;
	}

	/**
	 * @return the lineaGrande
	 */
	public static int getLineaGrande() {
		return LINEA_GRANDE;
	}

	/**
	 * @return the factorEscala
	 */
	public static double getFactorEscala() {
		return FACTOR_ESCALA;
	}

	/**
	 * @return the maximoEscalaEjes
	 */
	public static int getMaximoEscalaEjes() {
		return MAXIMO_ESCALA_EJES;
	}

	/**
	 * @return the escalaX
	 */
	public double getEscalaX() {
		return escalaX;
	}

	/**
	 * @param escalaX the escalaX to set
	 */
	public void setEscalaX(double escalaX) {
		this.escalaX = escalaX;
	}

	/**
	 * @return the escalaY
	 */
	public double getEscalaY() {
		return escalaY;
	}

	/**
	 * @param escalaY the escalaY to set
	 */
	public void setEscalaY(double escalaY) {
		this.escalaY = escalaY;
	}

	/**
	 * @return the angulo
	 */
	public double getAngulo() {
		return angulo;
	}

	/**
	 * @param angulo the angulo to set
	 */
	public void setAngulo(double angulo) {
		this.angulo = angulo;
	}

	/**
	 * @return the altura
	 */
	public double getAltura() {
		return altura;
	}

	/**
	 * @param altura the altura to set
	 */
	public void setAltura(double altura) {
		this.altura = altura;
	}
	
}
