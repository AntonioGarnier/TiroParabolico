/**
@author Antonio Jesús López Garnier - Correo: alu0100454437@ull.edu.es
@see <a href = "https://github.com/AntonioGarnier" > Mi Github </a>
@version 1.0
*/


import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class Botonera extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton lanzar, pausar, borrar;
	private JLabel velocidadTag, anguloTag, alturaTag;
	private JSlider velocidadControl, anguloControl, alturaControl;
	private JCheckBox velocidadMostrar, rastroMostrar, vectorPosicionMostrar;
	
	/**
	 * Constructor por defecto para la botonera
	 */
	public Botonera () {
		setLayout(new GridLayout(3, 4));
		initBotones();
		initCheckers();
		initControles();
		initEtiquetas();
		initPanel();
	}
	
	/**
	 * Añadimos los botones, etiquetas, etc. al panel
	 */
	private void initPanel() {
		add(getLanzar());
		add(getVelocidadTag());
		add(getVelocidadControl());
		add(getVelocidadMostrar());
		add(getPausar());
		add(getAnguloTag());
		add(getAnguloControl());
		add(getRastroMostrar());
		add(getBorrar());
		add(getAlturaTag());
		add(getAlturaControl());
		add(getVectorPosicionMostrar());
	}

	/**
	 * Inicializamos los botones de lanzar, pausar y borrar
	 */
	private void initBotones() {
		setLanzar(new JButton ("Lanzar"));
		setPausar(new JButton("Pausar"));
		setBorrar(new JButton ("Borrar"));
	}
	
	/**
	 * Inicializamos los checkbox
	 */
	private void initCheckers() {
		setVelocidadMostrar(new JCheckBox ("Mostrar velocidades"));
		setRastroMostrar(new JCheckBox ("Mostrar rastro", true));
		setVectorPosicionMostrar(new JCheckBox ("Mostrar Vector de posicion"));
	}
	
	/**
	 * Inicializamos las etiquetas de informacion de los controles
	 */
	private void initEtiquetas() {
		setVelocidadTag(new JLabel ("Velocidad inicial " + getVelocidadControl().getValue() + " m/s", JLabel.CENTER));
		setAnguloTag(new JLabel ("Angulo inicial " + getAnguloControl().getValue() + " grados", JLabel.CENTER));
		setAlturaTag(new JLabel ("Altura inicial " + getAlturaControl().getValue() + " m", JLabel.CENTER));
	}
	
	/**
	 * Actualiza la etiqueta de la velocidad
	 */
	public void actualizaVelocidadTag() {
		getVelocidadTag().setText("Velocidad inicial " + getVelocidadControl().getValue() + " m/s");		
	}
	
	/**
	 * Actualiza la etiqueta del angulo
	 */
	public void actualizaAnguloTag() {
		getAnguloTag().setText("Angulo inicial " + getAnguloControl().getValue() + " grados");
	}
	
	/**
	 * Actualiza la etiqueta de la altura
	 */
	public void actualizaAlturaTag() {
		getAlturaTag().setText("Altura inicial " + getAlturaControl().getValue() + " m");
	}
	
	/**
	 * Inicializa los sliders y setea las opciones para mostrar los ticks del slider
	 */
	private void initControles() {
		setVelocidadControl(new JSlider (JSlider.HORIZONTAL, 0, ModeloTiroParabolico.getVelocidadMax(), ModeloTiroParabolico.getVelocidadMax() / 2));
		setAnguloControl(new JSlider (JSlider.HORIZONTAL, ModeloTiroParabolico.getAnguloMin(), ModeloTiroParabolico.getAnguloMax(), ModeloTiroParabolico.getAnguloMax() / 2));
		setAlturaControl(new JSlider (JSlider.HORIZONTAL, 0, ModeloTiroParabolico.getAlturaMax(), 0));
		getVelocidadControl().setMinorTickSpacing(1);
		getVelocidadControl().setMajorTickSpacing(10);
		getVelocidadControl().setPaintTicks(true);	
		getAnguloControl().setPaintTicks(true);
		getAnguloControl().setMinorTickSpacing(1);
		getAnguloControl().setMajorTickSpacing(10);
		getAlturaControl().setPaintTicks(true);
		getAlturaControl().setMinorTickSpacing(1);
		getAlturaControl().setMajorTickSpacing(10);
	}
	
	/**
	 * @return the lanzar
	 */
	public JButton getLanzar() {
		return lanzar;
	}

	/**
	 * @param lanzar the lanzar to set
	 */
	public void setLanzar(JButton lanzar) {
		this.lanzar = lanzar;
	}

	/**
	 * @return the pausar
	 */
	public JButton getPausar() {
		return pausar;
	}

	/**
	 * @param pausar the pausar to set
	 */
	public void setPausar(JButton pausar) {
		this.pausar = pausar;
	}

	/**
	 * @return the borrar
	 */
	public JButton getBorrar() {
		return borrar;
	}

	/**
	 * @param borrar the borrar to set
	 */
	public void setBorrar(JButton borrar) {
		this.borrar = borrar;
	}

	/**
	 * @return the velocidadTag
	 */
	public JLabel getVelocidadTag() {
		return velocidadTag;
	}

	/**
	 * @param velocidadTag the velocidadTag to set
	 */
	public void setVelocidadTag(JLabel velocidadTag) {
		this.velocidadTag = velocidadTag;
	}

	/**
	 * @return the anguloTag
	 */
	public JLabel getAnguloTag() {
		return anguloTag;
	}

	/**
	 * @param anguloTag the anguloTag to set
	 */
	public void setAnguloTag(JLabel anguloTag) {
		this.anguloTag = anguloTag;
	}

	/**
	 * @return the alturaTag
	 */
	public JLabel getAlturaTag() {
		return alturaTag;
	}

	/**
	 * @param alturaTag the alturaTag to set
	 */
	public void setAlturaTag(JLabel alturaTag) {
		this.alturaTag = alturaTag;
	}

	/**
	 * @return the velocidadControl
	 */
	public JSlider getVelocidadControl() {
		return velocidadControl;
	}

	/**
	 * @param velocidadControl the velocidadControl to set
	 */
	public void setVelocidadControl(JSlider velocidadControl) {
		this.velocidadControl = velocidadControl;
	}

	/**
	 * @return the anguloControl
	 */
	public JSlider getAnguloControl() {
		return anguloControl;
	}

	/**
	 * @param anguloControl the anguloControl to set
	 */
	public void setAnguloControl(JSlider anguloControl) {
		this.anguloControl = anguloControl;
	}

	/**
	 * @return the alturaControl
	 */
	public JSlider getAlturaControl() {
		return alturaControl;
	}

	/**
	 * @param alturaControl the alturaControl to set
	 */
	public void setAlturaControl(JSlider alturaControl) {
		this.alturaControl = alturaControl;
	}

	/**
	 * @return the velocidadMostrar
	 */
	public JCheckBox getVelocidadMostrar() {
		return velocidadMostrar;
	}

	/**
	 * @param velocidadMostrar the velocidadMostrar to set
	 */
	public void setVelocidadMostrar(JCheckBox velocidadMostrar) {
		this.velocidadMostrar = velocidadMostrar;
	}

	/**
	 * @return the rastroMostrar
	 */
	public JCheckBox getRastroMostrar() {
		return rastroMostrar;
	}

	/**
	 * @param rastroMostrar the rastroMostrar to set
	 */
	public void setRastroMostrar(JCheckBox rastroMostrar) {
		this.rastroMostrar = rastroMostrar;
	}

	/**
	 * @return the vectorPosicionMostrar
	 */
	public JCheckBox getVectorPosicionMostrar() {
		return vectorPosicionMostrar;
	}

	/**
	 * @param vectorPosicionMostrar the vectorPosicionMostrar to set
	 */
	public void setVectorPosicionMostrar(JCheckBox vectorPosicionMostrar) {
		this.vectorPosicionMostrar = vectorPosicionMostrar;
	}
	
	
	
}
