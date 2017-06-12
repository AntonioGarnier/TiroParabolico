/**
@author Antonio Jesús López Garnier - Correo: alu0100454437@ull.edu.es
@see <a href = "https://github.com/AntonioGarnier" > Mi Github </a>
@version 1.0
*/

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.text.DecimalFormat;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class InformacionPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private int numeroTiro;
	private JLabel tiro, tiempo, distancia, altura, anguloInclinacion, velocidadEjeX, velocidadEjeY, velocidad, alturaMaxima, distanciaMaxima;
	private DecimalFormat formato = new DecimalFormat ("#.00");
	
	public InformacionPanel (int numeroTiro) {
		setMinimumSize(new Dimension (0, 120));
		setNumeroTiro(numeroTiro + 1);
		setLayout(new FlowLayout());
		initEtiquietas();
		initPanel();
	}

	private void initEtiquietas() {
		setTiro(new JLabel ("Tiro " + getNumeroTiro(), JLabel.CENTER));
		setTiempo (new JLabel ("T = 0 s", JLabel.CENTER));
		setDistancia (new JLabel ("<html>X<sub>actual</sub> = 0 m</html>", JLabel.CENTER));
		setAltura (new JLabel ("<html>Y<sub>actual</sub> = 0 m</html>", JLabel.CENTER));
		setAnguloInclinacion(new JLabel ("Angulo = 0º", JLabel.CENTER));
		setVelocidad (new JLabel ("V = 0 m/s", JLabel.CENTER));
		setVelocidadEjeX (new JLabel ("<html>V<sub>x</sub> = 0 m/s</html>", JLabel.CENTER));
		setVelocidadEjeY (new JLabel ("<html>V<sub>y</sub> = 0 m/s</html>", JLabel.CENTER));
		setDistanciaMaxima(new JLabel ("<html>X<sub>max</sub> = 0 m</html>", JLabel.CENTER));
		setAlturaMaxima (new JLabel ("<html>Y<sub>max</sub> = 0 m</html>", JLabel.CENTER));	
	}
	
	/**
	 * Actualiza los valores de las tags
	 * @param tiempo
	 * @param distancia
	 * @param altura
	 * @param angulo
	 * @param velocidad
	 * @param velocidadX
	 * @param velocidadY
	 * @param xMaxima
	 * @param yMaxima
	 */
	public void actualiza(double tiempo, double distancia, double altura, double angulo, double velocidad, double velocidadX, double velocidadY, double xMaxima, double yMaxima) {
		setTiempoValor(tiempo);
		setDistanciaValor(distancia);
		setAlturaValor(altura);
		setAnguloInclinacionValor(angulo);
		setVelocidadValor(velocidad);
		setVelocidadEjeXValor(velocidadX);
		setVelocidadEjeYValor(velocidadY);
		setDistanciaMaximaValor(xMaxima);
		setAlturaMaximaValor(yMaxima);
	}
	
	public void setTiempoValor (double segundos) {
		getTiempo().setText("T = " + formato.format(segundos) + " s");
	}
	
	public void setDistanciaValor (double distancia) {
		getDistancia().setText("<html>X<sub>actual</sub> = " + formato.format(distancia) + " m</html>");
	}
	
	public void setAlturaValor (double altura) {
		getAltura().setText("<html>Y<sub>actual</sub> = " + formato.format(altura) + " m</html>");
	}
	
	public void setAnguloInclinacionValor (double angulo) {
		getAnguloInclinacion().setText("Angulo = " + formato.format(angulo) + "º");
	}
	
	public void setVelocidadValor (double velocidad) {
		getVelocidad().setText("V = " + formato.format(velocidad) + " m/s");
	}
	
	public void setVelocidadEjeXValor (double velocidadX) {
		getVelocidadEjeX().setText("<html>V<sub>x</sub> = " + formato.format(velocidadX) + " m/s</html>");
	}
	
	public void setVelocidadEjeYValor (double velocidadY) {
		getVelocidadEjeY().setText("<html>V<sub>y</sub> = " + formato.format(velocidadY) + " m/s</html>");
	}
	
	public void setDistanciaMaximaValor (double distanciaMaxima) {
		getDistanciaMaxima().setText("<html>X<sub>max</sub> = " + formato.format(distanciaMaxima) + " m</html>");
	}
	
	public void setAlturaMaximaValor (double alturaMaxima) {
		getAlturaMaxima().setText("<html>Y<sub>max</sub> = " + formato.format(alturaMaxima) + " m</html>");
	}
	
	/**
	 * Añadimos las etiquetas al panel
	 */
	private void initPanel() {
		getTiro().setBackground(ColorAleatorio.getColorAleatorio());
		getTiro().setOpaque(true);
		add(getTiro());
		add(getTiempo());
		add(getDistancia());
		add(getAltura());
		add(getAnguloInclinacion());
		add(getVelocidadEjeX());
		add(getVelocidadEjeY());
		add(getVelocidad());
		add(getDistanciaMaxima());
		add(getAlturaMaxima());
	}

	/**
	 * @return the formato
	 */
	public DecimalFormat getFormato() {
		return formato;
	}

	/**
	 * @param formato the formato to set
	 */
	public void setFormato(DecimalFormat formato) {
		this.formato = formato;
	}

	/**
	 * @return the anguloInclinacion
	 */
	public JLabel getAnguloInclinacion() {
		return anguloInclinacion;
	}

	/**
	 * @param anguloInclinacion the anguloInclinacion to set
	 */
	public void setAnguloInclinacion(JLabel anguloInclinacion) {
		this.anguloInclinacion = anguloInclinacion;
	}

	/**
	 * @return the distanciaMaxima
	 */
	public JLabel getDistanciaMaxima() {
		return distanciaMaxima;
	}

	/**
	 * @param distanciaMaxima the distanciaMaxima to set
	 */
	public void setDistanciaMaxima(JLabel distanciaMaxima) {
		this.distanciaMaxima = distanciaMaxima;
	}

	/**
	 * @return the tiro
	 */
	public JLabel getTiro() {
		return tiro;
	}

	/**
	 * @param tiro the tiro to set
	 */
	public void setTiro(JLabel tiro) {
		this.tiro = tiro;
	}

	/**
	 * @return the numeroTiro
	 */
	public int getNumeroTiro() {
		return numeroTiro;
	}

	/**
	 * @param numeroTiro the numeroTiro to set
	 */
	public void setNumeroTiro(int numeroTiro) {
		this.numeroTiro = numeroTiro;
	}

	/**
	 * @return the tiempo
	 */
	public JLabel getTiempo() {
		return tiempo;
	}

	/**
	 * @param tiempo the tiempo to set
	 */
	public void setTiempo(JLabel tiempo) {
		this.tiempo = tiempo;
	}

	/**
	 * @return the distancia
	 */
	public JLabel getDistancia() {
		return distancia;
	}

	/**
	 * @param distancia the distancia to set
	 */
	public void setDistancia(JLabel distancia) {
		this.distancia = distancia;
	}

	/**
	 * @return the altura
	 */
	public JLabel getAltura() {
		return altura;
	}

	/**
	 * @param altura the altura to set
	 */
	public void setAltura(JLabel altura) {
		this.altura = altura;
	}

	/**
	 * @return the velocidadEjeX
	 */
	public JLabel getVelocidadEjeX() {
		return velocidadEjeX;
	}

	/**
	 * @param velocidadEjeX the velocidadEjeX to set
	 */
	public void setVelocidadEjeX(JLabel velocidadEjeX) {
		this.velocidadEjeX = velocidadEjeX;
	}

	/**
	 * @return the velocidadEjeY
	 */
	public JLabel getVelocidadEjeY() {
		return velocidadEjeY;
	}

	/**
	 * @param velocidadEjeY the velocidadEjeY to set
	 */
	public void setVelocidadEjeY(JLabel velocidadEjeY) {
		this.velocidadEjeY = velocidadEjeY;
	}

	/**
	 * @return the velocidad
	 */
	public JLabel getVelocidad() {
		return velocidad;
	}

	/**
	 * @param velocidad the velocidad to set
	 */
	public void setVelocidad(JLabel velocidad) {
		this.velocidad = velocidad;
	}

	/**
	 * @return the alturaMaxima
	 */
	public JLabel getAlturaMaxima() {
		return alturaMaxima;
	}

	/**
	 * @param alturaMaxima the alturaMaxima to set
	 */
	public void setAlturaMaxima(JLabel alturaMaxima) {
		this.alturaMaxima = alturaMaxima;
	}
	
}
