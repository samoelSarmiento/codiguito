package pe.pucp.edu.pe.siscomfi.view;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Login implements ActionListener{

	private JFrame frmSiscomfi;
	private JTextField txtUsuario;
	private JPasswordField passwordField;
	private JButton btnIngresar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
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
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSiscomfi = new JFrame();
		frmSiscomfi.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\samoel\\workspace\\Siscomfi\\Imagenes\\minilogo.png"));
		frmSiscomfi.setTitle("SISCOMFI");
		frmSiscomfi.setBounds(100, 100, 498, 300);
		frmSiscomfi.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSiscomfi.getContentPane().setLayout(null);
		
		JPanel pnLogo = new JPanel();
		pnLogo.setBounds(10, 11, 170, 239);
		BufferedImage biLogo = null;
		try {
			biLogo = ImageIO.read(new File("C:/Users/samoel/workspace/Siscomfi/Imagenes/logofinal.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Image biLogoAux = biLogo.getScaledInstance(pnLogo.getWidth(), pnLogo.getHeight(), Image.SCALE_SMOOTH);
		pnLogo.setLayout(null);
		JLabel lblLogo = new JLabel(new ImageIcon(biLogoAux));
		lblLogo.setBounds(0, 0, 170, 239);
		pnLogo.add(lblLogo);
		frmSiscomfi.getContentPane().add(pnLogo);
		
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(197, 51, 86, 14);
		frmSiscomfi.getContentPane().add(lblUsuario);
		
		JLabel lblContrasena = new JLabel("Contrase\u00F1a:");
		lblContrasena.setBounds(197, 111, 86, 14);
		frmSiscomfi.getContentPane().add(lblContrasena);
		
		txtUsuario = new JTextField();
		txtUsuario.setBounds(305, 48, 137, 20);
		frmSiscomfi.getContentPane().add(txtUsuario);
		txtUsuario.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(305, 108, 137, 20);
		frmSiscomfi.getContentPane().add(passwordField);
		
		btnIngresar = new JButton("INGRESAR");
		btnIngresar.setBounds(323, 158, 108, 23);
		frmSiscomfi.getContentPane().add(btnIngresar);
		
		JLabel lblRecuperar = new JLabel("\u00BFOlvid\u00F3 su contrase\u00F1a?");
		lblRecuperar.setBounds(294, 236, 178, 14);
		frmSiscomfi.getContentPane().add(lblRecuperar);
		
		//listener
		btnIngresar.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == btnIngresar){
			//frmSiscomfi.setVisible(false);
			frmSiscomfi.dispose();
			VistaPrincipal vstPrincipal = new VistaPrincipal();
			vstPrincipal.main(null);
		}
	}
	
	
}
