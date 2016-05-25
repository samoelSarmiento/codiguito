package pe.pucp.edu.pe.siscomfi.view;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;

public class VistaIniciarProceso extends JInternalFrame {
	private JTextField textField;
	private JTextField txtFase;

	public VistaIniciarProceso() {
		setTitle("Iniciar Proceso");
		setBounds(100, 100, 326, 255);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Partido Pol\u00EDtico:");
		lblNewLabel.setBounds(12, 63, 126, 16);
		getContentPane().add(lblNewLabel);
		
		JComboBox cbPartido = new JComboBox();
		cbPartido.setBounds(124, 60, 170, 22);
		getContentPane().add(cbPartido);
		
		JLabel lblRuta = new JLabel("Ruta:");
		lblRuta.setBounds(12, 99, 85, 16);
		getContentPane().add(lblRuta);
		
		textField = new JTextField();
		textField.setBounds(124, 96, 116, 22);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton button = new JButton("...");
		button.setBounds(252, 95, 45, 25);
		getContentPane().add(button);
		
		JButton btnProcesar = new JButton("Procesar");
		btnProcesar.setBounds(107, 163, 97, 25);
		getContentPane().add(btnProcesar);
		
		txtFase = new JTextField();
		txtFase.setEditable(false);
		txtFase.setText("Fase 1");
		txtFase.setBounds(124, 25, 170, 22);
		getContentPane().add(txtFase);
		txtFase.setColumns(10);
		
		JLabel lblFaseDelProceso = new JLabel("Fase del proceso:");
		lblFaseDelProceso.setBounds(12, 28, 108, 16);
		getContentPane().add(lblFaseDelProceso);

	}
}
