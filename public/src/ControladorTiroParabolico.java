/**
Practica 12: Tiro Parabolico
Programación Gráfica.
Programación orientada a Eventos.
Enfatizar la orientación a objetos en el desarrollo de aplicaciones.
Desarrollo guiado por tests (TDD).
Graphics2D.
Aquí se implementa el controlador que extiende de JApplet. Intermediario entre modelo y vista
@author Antonio Jesús López Garnier - Correo: alu0100454437@ull.edu.es
@see <a href = "https://campusvirtual.ull.es/1617/user/profile.php?id=16443" > Perfil Campus Virtual ULL </a>
@see <a href = "https://github.com/AntonioGarnier" > Mi Github </a>
@version 3/05/2017 - 1.0
*/


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ControladorTiroParabolico extends JApplet {

	private static final long serialVersionUID = 1L;
	private Botonera controles;				// Botonera que contiene los botones etiquetas y controles del panel inferior
	private ArrayList<InformacionPanel> infoPanel = new ArrayList<InformacionPanel>();	// Array para guardar paneles de informacion
	private JPanel informacion = new JPanel ();		 // Panel de informacion que contiene informacion de los tiros
	private JScrollPane scrollPanel = new JScrollPane(); 		// JScrollPane para hacer el panel superior scrollable
	private VistaTiroParabolico tiroParabolicoPanel;		// Panel que contiene la grafica
	private int contadorTiros = 0;	// Lleva la cuenta del tiro actual

	/**
	 * Main para ejecutar como applet o aplicacion
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame("Practica 11 - PAI - Tiro Parabólico");
		ControladorTiroParabolico applet = new ControladorTiroParabolico();
	   frame.add(applet, BorderLayout.CENTER); 
	   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	   frame.setVisible(true);
	}
	
	/**
	 * Constructor por defecto
	 */
	public ControladorTiroParabolico () {
		initFrame ();
		initPanelBotones();
		initPanelVista();
		initEscuchas();
		getTiroParabolicoPanel().setAngulo(getControles().getAnguloControl().getValue());
	}
	
	/**
	 * Inicializa y establece las caracteristicas del frame
	 */
	private void initFrame() {
		Toolkit miPanel = Toolkit.getDefaultToolkit();
		Dimension tamanioPanel = miPanel.getScreenSize();	
		setSize (tamanioPanel.width, tamanioPanel.height);
		setLayout(new BorderLayout());
	}
	
	/**
	 * Inicializa el panel de botones
	 */
	private void initPanelBotones() {
		setControles (new Botonera());
		add(getControles(), BorderLayout.SOUTH);
	}
	
	private void initPanelVista() {
		getInformacion().setLayout(new GridLayout (0, 1));
		getScrollPanel().setVisible(true);
		getScrollPanel().setAutoscrolls(true);
		getScrollPanel().setPreferredSize(new Dimension (0, 100));
		getScrollPanel().setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		getScrollPanel().setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		getScrollPanel().setViewportView(getInformacion());
		add(getScrollPanel(), BorderLayout.NORTH);
		setTiroParabolicoPanel(new VistaTiroParabolico());
		add(getTiroParabolicoPanel());
	}

	private void initPanelInformacion(int numeroTiro) {
		getInfoPanel().add(new InformacionPanel (numeroTiro));
		getInfoPanel().get(numeroTiro).setVisible(false);
		getInformacion().add(getInfoPanel().get(numeroTiro));
	}
	
	private void initEscuchas() {
		getControles().getLanzar().addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent e) {
				initPanelInformacion(getContadorTiros());
				getTiroParabolicoPanel().addTiro(getControles().getVelocidadControl().getValue(), getControles().getAnguloControl().getValue(), getControles().getAlturaControl().getValue(), getControles().getRastroMostrar().isSelected(), getControles().getVelocidadMostrar().isSelected(), getControles().getVectorPosicionMostrar().isSelected(), getInfoPanel().get(getContadorTiros()));
				getInfoPanel().get(getContadorTiros()).actualiza(0.0, 0.0, getTiroParabolicoPanel().getTiros().get(getContadorTiros()).getAlturaInicial(), getTiroParabolicoPanel().getTiros().get(getContadorTiros()).getAnguloInicial(), getTiroParabolicoPanel().getTiros().get(getContadorTiros()).calculaMagnitudV(0), getTiroParabolicoPanel().getTiros().get(getContadorTiros()).getVectorX(), getTiroParabolicoPanel().getTiros().get(getContadorTiros()).calculaVY(0), getTiroParabolicoPanel().getTiros().get(getContadorTiros()).getxMaxima(), getTiroParabolicoPanel().getTiros().get(getContadorTiros()).getyMaxima());
				getInfoPanel().get(getContadorTiros()).setVisible(true);
				getTiroParabolicoPanel().getTiros().get(getContadorTiros()).comienzaTimer();
				getControles().getPausar().setText("Pausar");
				setContadorTiros(getContadorTiros() + 1);
			}
		});
		
		getControles().getPausar().addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (getControles().getPausar().getText().equals("Pausar")) {
					getTiroParabolicoPanel().getTiros().get(getContadorTiros() - 1).getTiempoTiro().cancel();
					getControles().getPausar().setText("Reaunadar");
				}
				else {
					getTiroParabolicoPanel().getTiros().get(getContadorTiros() - 1).comienzaTimer();;
					getControles().getPausar().setText("Pausar");
				}

			}
		});
		
		getControles().getBorrar().addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent e) {
				getTiroParabolicoPanel().getTiros().clear();
				getInfoPanel().forEach(panelInfo -> panelInfo.setVisible(false));
				getInfoPanel().clear();
				getInformacion().removeAll();
				setContadorTiros(0);
				getTiroParabolicoPanel().repaint();
			}
		});
		
		getControles().getVelocidadControl().addChangeListener(new ChangeListener () {

			@Override
			public void stateChanged(ChangeEvent e) {
				getControles().actualizaVelocidadTag();
			}
		});
		
		getControles().getAnguloControl().addChangeListener(new ChangeListener () {

			@Override
			public void stateChanged(ChangeEvent e) {
				getControles().actualizaAnguloTag();
				getTiroParabolicoPanel().setAngulo(getControles().getAnguloControl().getValue());
				getTiroParabolicoPanel().repaint();
			}
			
		});
		
		getControles().getAlturaControl().addChangeListener(new ChangeListener () {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				getControles().actualizaAlturaTag();				
				getTiroParabolicoPanel().setAltura(getControles().getAlturaControl().getValue());
				getTiroParabolicoPanel().repaint();
			}
		});
		
		getControles().getRastroMostrar().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				getTiroParabolicoPanel().getTiros().get(getContadorTiros() - 1).setMostrarRastro(getControles().getRastroMostrar().isSelected());
				getTiroParabolicoPanel().repaint();
			}
		});
		
		getControles().getVelocidadMostrar().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				getTiroParabolicoPanel().getTiros().get(getContadorTiros() - 1).setMostrarVelocidad(getControles().getVelocidadMostrar().isSelected());
				getTiroParabolicoPanel().repaint();
			}
		});
		
		getControles().getVectorPosicionMostrar().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				getTiroParabolicoPanel().getTiros().get(getContadorTiros() - 1).setMostrarVectorPosicion(getControles().getVectorPosicionMostrar().isSelected());
				getTiroParabolicoPanel().repaint();
			}
		});
		
	}

	/**
	 * @return the informacion
	 */
	public JPanel getInformacion() {
		return informacion;
	}

	/**
	 * @param informacion the informacion to set
	 */
	public void setInformacion(JPanel informacion) {
		this.informacion = informacion;
	}

	/**
	 * @return the infoPanel
	 */
	public ArrayList<InformacionPanel> getInfoPanel() {
		return infoPanel;
	}

	/**
	 * @param infoPanel the infoPanel to set
	 */
	public void setInfoPanel(ArrayList<InformacionPanel> infoPanel) {
		this.infoPanel = infoPanel;
	}

	/**
	 * @return the tiroParabolicoPanel
	 */
	public VistaTiroParabolico getTiroParabolicoPanel() {
		return tiroParabolicoPanel;
	}

	/**
	 * @param tiroParabolicoPanel the tiroParabolicoPanel to set
	 */
	public void setTiroParabolicoPanel(VistaTiroParabolico tiroParabolicoPanel) {
		this.tiroParabolicoPanel = tiroParabolicoPanel;
	}

	/**
	 * @return the controles
	 */
	public Botonera getControles() {
		return controles;
	}

	/**
	 * @param controles the controles to set
	 */
	public void setControles(Botonera controles) {
		this.controles = controles;
	}

	/**
	 * @return the contadorTiros
	 */
	public int getContadorTiros() {
		return contadorTiros;
	}

	/**
	 * @param contadorTiros the contadorTiros to set
	 */
	public void setContadorTiros(int contadorTiros) {
		this.contadorTiros = contadorTiros;
	}

	/**
	 * @return the scrollPanel
	 */
	public JScrollPane getScrollPanel() {
		return scrollPanel;
	}

	/**
	 * @param scrollPanel the scrollPanel to set
	 */
	public void setScrollPanel(JScrollPane scrollPanel) {
		this.scrollPanel = scrollPanel;
	}
	
	
}
