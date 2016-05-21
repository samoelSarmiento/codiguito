package pe.pucp.edu.pe.siscomfi.view;

import java.awt.EventQueue;
import java.awt.Rectangle;

import javax.swing.JFrame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class VistaPrincipal extends JFrame implements ActionListener{

	private JFrame frmSiscomfi;
	private JMenuItem mntmSalir;
	JMenuItem mntmUsuario;
	JMenuItem mntmPartidoPolitico;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VistaPrincipal window = new VistaPrincipal();
					window.frmSiscomfi.setExtendedState(JFrame.MAXIMIZED_BOTH);
					window.frmSiscomfi.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VistaPrincipal() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSiscomfi = new JFrame();
		frmSiscomfi.setTitle("SISCOMFI");
		frmSiscomfi.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\samoel\\workspace\\Siscomfi\\Imagenes\\minilogo.png"));
		frmSiscomfi.setBounds(100, 100, 450, 300);
		frmSiscomfi.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSiscomfi.getContentPane().setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		frmSiscomfi.setJMenuBar(menuBar);
		
		JMenu mnUsuarios = new JMenu("Mantenimientos");
		menuBar.add(mnUsuarios);
		
		mntmUsuario = new JMenuItem("Usuario");
		mnUsuarios.add(mntmUsuario);
		
		mntmPartidoPolitico = new JMenuItem("Partido Pol\u00EDtico");
		mnUsuarios.add(mntmPartidoPolitico);
		
		JMenu mnReportes = new JMenu("Reportes");
		menuBar.add(mnReportes);
		
		JMenu mnSalir = new JMenu("Cerrar Sesi\u00F3n");
		menuBar.add(mnSalir);
		
		mntmSalir = new JMenuItem("SALIR");
		mnSalir.add(mntmSalir);
		
		//Listeners
		mntmSalir.addActionListener(this);
		mntmPartidoPolitico.addActionListener(this);
		mntmUsuario.addActionListener(this);
		
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == mntmSalir){
			if(JOptionPane.showConfirmDialog(frmSiscomfi, "¿Cerrar sesión?","Aviso",JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION){
				frmSiscomfi.dispose();
			}
		}
		
		//Abre mantenimeino usuario
		if(event.getSource() == mntmUsuario){
			IfMntoUsuario ifUsuario = new IfMntoUsuario();
			frmSiscomfi.add(ifUsuario);
			ifUsuario.setVisible(true);
		}
	}
	
	
}
