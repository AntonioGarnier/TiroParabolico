/**
@author Antonio Jesús López Garnier - Correo: alu0100454437@ull.edu.es
@see <a href = "https://github.com/AntonioGarnier" > Mi Github </a>
@version 1.0
*/


import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ModeloTiroParabolico {

	private static final int VELOCIDAD_MAX = 100;
	private static final int ANGULO_MAX = 90;
	private static final int ANGULO_MIN = -90;
	private static final int ALTURA_MAX = 100;
	private static final double gravedad = 9.81;
	private double velocidadInicial, alturaInicial, anguloInicial;
	private double tiempoDeVuelo, xMaxima, yMaxima;
	private double vectorX;
	private double vectorYTag;
	private double magnitudVTag;
	private int contadorPuntoActual = 0;
	private boolean mostrarRastro, mostrarVelocidad, mostrarVectorPosicion, valido;
	private ArrayList<Point2D.Double> trayectoria = new ArrayList<Point2D.Double> ();
	private ArrayList<Double> vectorY = new ArrayList<Double> ();
	private ArrayList<Double> anguloInclinacion = new ArrayList<Double> ();
	private ArrayList<Double> magnitudV = new ArrayList<Double> ();
	private Point2D.Double puntoEnMovimiento;
	private VistaTiroParabolico panelGrafico;
	private Timer tiempoTiro = new Timer();
	private InformacionPanel panelInfo;
	private Color colorBola = ColorAleatorio.getColorAleatorio();
	private Color colorRastro = ColorAleatorio.getColorAleatorio();
	private Color colorVector = ColorAleatorio.getColorAleatorio();

	public ModeloTiroParabolico (double velocidadInicial, double anguloInicial, double alturaInicial, VistaTiroParabolico panelGrafico, boolean mostrarRastro, boolean mostrarVelocidad, boolean mostrarVectorPosicion, InformacionPanel panelInfo) {
		setPanelInfo(panelInfo);
		setMostrarRastro(mostrarRastro);
		setMostrarVelocidad(mostrarVelocidad);
		setMostrarVectorPosicion(mostrarVectorPosicion);
		setPanelGrafico(panelGrafico);
		setVelocidadInicial(velocidadInicial);
		setAlturaInicial(alturaInicial);
		setAnguloInicial(anguloInicial);
		if (getAnguloInicial() <= 0 && getAlturaInicial() == 0)
			setValido(false);
		else
			setValido(true);
		setVectorX(calculaVX());
		tiempoDeVuelo();
		distanciaMaxima ();
		if (getTiempoDeVuelo() > 0)
			alturaMaxima();
		else
			setyMaxima(alturaInicial);
		generaTrayectoria();
	}

	/**
	 * Timer para poder dibujar la trayectoria poco a poco
	 */
	public void comienzaTimer () {
	    getTiempoTiro().cancel();
	    TimerTask tiroTiempoTarea = new TimerTask() {
	      public void run() {
	      	if (getContadorPuntoActual() < getTrayectoria().size()) {
	      		setPuntoEnMovimiento(getTrayectoria().get(getContadorPuntoActual()));
	      		setVectorYTag(getVectorY().get(getContadorPuntoActual()));
	      		setMagnitudVTag(getMagnitudV().get(getContadorPuntoActual()));
	      		setAnguloInicial(getAnguloInclinacion().get(getContadorPuntoActual()));
	      		getPanelGrafico().repaint();
	      		// Actualizando panel de informacion
	      		getPanelInfo().setVelocidadEjeYValor(getVectorYTag());
	      		getPanelInfo().setVelocidadValor(getMagnitudVTag());
	      		getPanelInfo().setAnguloInclinacionValor(getAnguloInicial());
	      		getPanelInfo().setDistanciaValor(getTrayectoria().get(getContadorPuntoActual()).getX());
	      		getPanelInfo().setAlturaValor(getTrayectoria().get(getContadorPuntoActual()).getY() * (-1));
	      		getPanelInfo().setTiempoValor(getContadorPuntoActual() * 0.01);
	      		setContadorPuntoActual(getContadorPuntoActual() + 1);
	      	}
	      	else
	      		getTiempoTiro().cancel();
	      }
	    };
	    setTiempoTiro(new Timer());
	    getTiempoTiro().scheduleAtFixedRate(tiroTiempoTarea, 0, 5);
	  }
	
	/**
	 * Calcula el tiempo de vuelo del proyectil
	 */
	public void tiempoDeVuelo () {
		setTiempoDeVuelo((getVelocidadInicial() / getGravedad()) * (Math.sin(Math.toRadians(getAnguloInicial())) + Math.sqrt(Math.pow(Math.sin(Math.toRadians(getAnguloInicial())), 2) + ((2 * getGravedad() * getAlturaInicial()) / Math.pow(getVelocidadInicial(), 2)))));
	}
	
	/**
	 * Calcula el angulo de inclinacion
	 * @param vy	Define la velocidad del eje Y
	 * @param v		Define el modulo de la velocidad
	 * @return
	 */
	public double calculaAnguloInclinacion (double vy, double v) {
		return Math.toDegrees(Math.asin(vy / v));
	}
	
	/**
	 * Calcula la distancia maxima alcanzada por el proyectil
	 */
	public void distanciaMaxima () {
		setxMaxima(getVelocidadInicial() * Math.cos(Math.toRadians(getAnguloInicial())) * getTiempoDeVuelo());
	}
	
	/**
	 * Calcula la altura maxima alcanzada por el proyectil
	 */
	public void alturaMaxima () {
		if (getAnguloInicial() < 0)
			setyMaxima (alturaInicial);
		else
			setyMaxima(getAlturaInicial() + ((Math.pow(getVelocidadInicial(), 2) * Math.pow(Math.sin(Math.toRadians(getAnguloInicial())), 2)) / (2 * getGravedad())));
	}
	
	/**
	 * Calcula la coordenada X en el instannte de tiempo pasado por parametros
	 * @param tiempoDeVuelo Define el instante de tiempo
	 * @return
	 */
	public double calculaX (double tiempoDeVuelo) {
		return (getVelocidadInicial() * Math.cos(Math.toRadians(getAnguloInicial())) * tiempoDeVuelo);
	}
	
	/**
	 * Calcula la coordenada Y en el instannte de tiempo pasado por parametros
	 * @param tiempoDeVuelo Define el instante de tiempo
	 * @return
	 */
	public double calculaY (double tiempoDeVuelo) {
		return (-1) * (getAlturaInicial() + (getVelocidadInicial() * Math.sin(Math.toRadians(getAnguloInicial())) * tiempoDeVuelo) - ((getGravedad() * Math.pow(tiempoDeVuelo, 2)) / 2));
	}
	
	/**
	 * Calcula la velocidad en el eje Y segun el instante de tiempo
	 * @param tiempoDeVuelo Define el instante de tiempo
	 * @return
	 */
	public double calculaVY (double tiempoDeVuelo) {
		return (getVelocidadInicial() * Math.sin(Math.toRadians(getAnguloInicial()))) - (getGravedad() * tiempoDeVuelo);
	}
	
	/**
	 * Calcula la velocidad en el eje X
	 * @return
	 */
	public double calculaVX () {
		return getVelocidadInicial() * Math.cos(Math.toRadians(getAnguloInicial()));
	}
	
	/**
	 * Calcula la magnitud de la velocidad en el instante de tiempo especificado
	 * @param tiempoDeVuelo Define el instante de tiempo
	 * @return
	 */
	public double calculaMagnitudV (double tiempoDeVuelo) {
		return Math.sqrt(Math.pow(getVectorX(), 2) + Math.pow(calculaVY(tiempoDeVuelo), 2));
	}
	
	/**
	 * Genera una trayectoria de puntos, un array de velocidades en Y, de magnitudes de V y de angulo de inclinacion
	 */
	public void generaTrayectoria () {
		for (double i = 0.00; i < getTiempoDeVuelo(); i += 0.01) {
			getVectorY().add(calculaVY(i));
			getMagnitudV().add(calculaMagnitudV(i));
			getAnguloInclinacion().add(calculaAnguloInclinacion(calculaVY(i), calculaMagnitudV(i)));
			getTrayectoria().add(new Point2D.Double(calculaX(i), calculaY(i)));
		}
	}
	
	/**
	 * @return the vectorX
	 */
	public double getVectorX() {
		return vectorX;
	}

	/**
	 * @param vectorX the vectorX to set
	 */
	public void setVectorX(double vectorX) {
		this.vectorX = vectorX;
	}

	/**
	 * @return the vectorY
	 */
	public ArrayList<Double> getVectorY() {
		return vectorY;
	}

	/**
	 * @param vectorY the vectorY to set
	 */
	public void setVectorY(ArrayList<Double> vectorY) {
		this.vectorY = vectorY;
	}

	/**
	 * @return the magnitudV
	 */
	public ArrayList<Double> getMagnitudV() {
		return magnitudV;
	}

	/**
	 * @param magnitudV the magnitudV to set
	 */
	public void setMagnitudV(ArrayList<Double> magnitudV) {
		this.magnitudV = magnitudV;
	}

	/**
	 * @return the trayectoria
	 */
	public ArrayList<Point2D.Double> getTrayectoria() {
		return trayectoria;
	}

	/**
	 * @param trayectoria the trayectoria to set
	 */
	public void setTrayectoria(ArrayList<Point2D.Double> trayectoria) {
		this.trayectoria = trayectoria;
	}

	/**
	 * @return the gravedad
	 */
	public static double getGravedad() {
		return gravedad;
	}

	/**
	 * @return the tiempoDeVuelo
	 */
	public double getTiempoDeVuelo() {
		return tiempoDeVuelo;
	}

	/**
	 * @param tiempoDeVuelo the tiempoDeVuelo to set
	 */
	public void setTiempoDeVuelo(double tiempoDeVuelo) {
		this.tiempoDeVuelo = tiempoDeVuelo;
	}

	/**
	 * @return the xMaxima
	 */
	public double getxMaxima() {
		return xMaxima;
	}

	/**
	 * @param xMaxima the xMaxima to set
	 */
	public void setxMaxima(double xMaxima) {
		this.xMaxima = xMaxima;
	}

	/**
	 * @return the yMaxima
	 */
	public double getyMaxima() {
		return yMaxima;
	}

	/**
	 * @param yMaxima the yMaxima to set
	 */
	public void setyMaxima(double yMaxima) {
		this.yMaxima = yMaxima;
	}

	/**
	 * @return the velocidadInicial
	 */
	public double getVelocidadInicial() {
		return velocidadInicial;
	}

	/**
	 * @param velocidadInicial the velocidadInicial to set
	 */
	public void setVelocidadInicial(double velocidadInicial) {
		this.velocidadInicial = velocidadInicial;
	}

	/**
	 * @return the alturaInicial
	 */
	public double getAlturaInicial() {
		return alturaInicial;
	}

	/**
	 * @param alturaInicial the alturaInicial to set
	 */
	public void setAlturaInicial(double alturaInicial) {
		this.alturaInicial = alturaInicial;
	}

	/**
	 * @return the anguloInicial
	 */
	public double getAnguloInicial() {
		return anguloInicial;
	}

	/**
	 * @param anguloInicial the anguloInicial to set
	 */
	public void setAnguloInicial(double anguloInicial) {
		this.anguloInicial = anguloInicial;
	}

	/**
	 * @return the velocidadMax
	 */
	public static int getVelocidadMax() {
		return VELOCIDAD_MAX;
	}

	/**
	 * @return the anguloMax
	 */
	public static int getAnguloMax() {
		return ANGULO_MAX;
	}

	/**
	 * @return the anguloMin
	 */
	public static int getAnguloMin() {
		return ANGULO_MIN;
	}

	/**
	 * @return the alturaMax
	 */
	public static int getAlturaMax() {
		return ALTURA_MAX;
	}

	/**
	 * @return the tiempoTiro
	 */
	public Timer getTiempoTiro() {
		return tiempoTiro;
	}

	/**
	 * @param tiempoTiro the tiempoTiro to set
	 */
	public void setTiempoTiro(Timer tiempoTiro) {
		this.tiempoTiro = tiempoTiro;
	}

	/**
	 * @return the puntoEnMovimiento
	 */
	public Point2D.Double getPuntoEnMovimiento() {
		return puntoEnMovimiento;
	}

	/**
	 * @param puntoEnMovimiento the puntoEnMovimiento to set
	 */
	public void setPuntoEnMovimiento(Point2D.Double puntoEnMovimiento) {
		this.puntoEnMovimiento = puntoEnMovimiento;
	}

	/**
	 * @return the contadorPuntoActual
	 */
	public int getContadorPuntoActual() {
		return contadorPuntoActual;
	}

	/**
	 * @param contadorPuntoActual the contadorPuntoActual to set
	 */
	public void setContadorPuntoActual(int contadorPuntoActual) {
		this.contadorPuntoActual = contadorPuntoActual;
	}

	/**
	 * @return the panelGrafico
	 */
	public VistaTiroParabolico getPanelGrafico() {
		return panelGrafico;
	}

	/**
	 * @param panelGrafico the panelGrafico to set
	 */
	public void setPanelGrafico(VistaTiroParabolico panelGrafico) {
		this.panelGrafico = panelGrafico;
	}

	/**
	 * @return the mostrarRastro
	 */
	public boolean isMostrarRastro() {
		return mostrarRastro;
	}

	/**
	 * @param mostrarRastro the mostrarRastro to set
	 */
	public void setMostrarRastro(boolean mostrarRastro) {
		this.mostrarRastro = mostrarRastro;
	}

	/**
	 * @return the mostrarVelocidad
	 */
	public boolean isMostrarVelocidad() {
		return mostrarVelocidad;
	}

	/**
	 * @param mostrarVelocidad the mostrarVelocidad to set
	 */
	public void setMostrarVelocidad(boolean mostrarVelocidad) {
		this.mostrarVelocidad = mostrarVelocidad;
	}

	/**
	 * @return the mostrarVectorPosicion
	 */
	public boolean isMostrarVectorPosicion() {
		return mostrarVectorPosicion;
	}

	/**
	 * @param mostrarVectorPosicion the mostrarVectorPosicion to set
	 */
	public void setMostrarVectorPosicion(boolean mostrarVectorPosicion) {
		this.mostrarVectorPosicion = mostrarVectorPosicion;
	}

	/**
	 * @return the vectorYTag
	 */
	public double getVectorYTag() {
		return vectorYTag;
	}

	/**
	 * @param vectorYTag the vectorYTag to set
	 */
	public void setVectorYTag(double vectorYTag) {
		this.vectorYTag = vectorYTag;
	}

	/**
	 * @return the magnitudVTag
	 */
	public double getMagnitudVTag() {
		return magnitudVTag;
	}

	/**
	 * @param magnitudVTag the magnitudVTag to set
	 */
	public void setMagnitudVTag(double magnitudVTag) {
		this.magnitudVTag = magnitudVTag;
	}

	/**
	 * @return the anguloInclinacion
	 */
	public ArrayList<Double> getAnguloInclinacion() {
		return anguloInclinacion;
	}

	/**
	 * @param anguloInclinacion the anguloInclinacion to set
	 */
	public void setAnguloInclinacion(ArrayList<Double> anguloInclinacion) {
		this.anguloInclinacion = anguloInclinacion;
	}

	/**
	 * @return the colorBola
	 */
	public Color getColorBola() {
		return colorBola;
	}

	/**
	 * @param colorBola the colorBola to set
	 */
	public void setColorBola(Color colorBola) {
		this.colorBola = colorBola;
	}

	/**
	 * @return the colorRastro
	 */
	public Color getColorRastro() {
		return colorRastro;
	}

	/**
	 * @param colorRastro the colorRastro to set
	 */
	public void setColorRastro(Color colorRastro) {
		this.colorRastro = colorRastro;
	}

	/**
	 * @return the colorVector
	 */
	public Color getColorVector() {
		return colorVector;
	}

	/**
	 * @param colorVector the colorVector to set
	 */
	public void setColorVector(Color colorVector) {
		this.colorVector = colorVector;
	}

	/**
	 * @return the valido
	 */
	public boolean isValido() {
		return valido;
	}

	/**
	 * @param valido the valido to set
	 */
	public void setValido(boolean valido) {
		this.valido = valido;
	}

	/**
	 * @return the panelInfo
	 */
	public InformacionPanel getPanelInfo() {
		return panelInfo;
	}

	/**
	 * @param panelInfo the panelInfo to set
	 */
	public void setPanelInfo(InformacionPanel panelInfo) {
		this.panelInfo = panelInfo;
	}
	
}
