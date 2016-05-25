package pe.pucp.edu.pe.siscomfi.view;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import pe.pucp.edu.pe.siscomfi.bm.BD.siscomfiManager;
import pe.pucp.edu.pe.siscomfi.model.Rol;
import pe.pucp.edu.pe.siscomfi.model.Usuario;

import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class VistaRegistrarUsuario extends JInternalFrame {
	private JTextField txtCodigo;
	private JTextField txtNombre;
	private JTextField txtApPaterno;
	private JTextField txtApMaterno;
	private JTextField txtCorreo;
	private JTextField txtDni;
	private JComboBox cbRol;
	

	public VistaRegistrarUsuario() {
		setClosable(true);
		setTitle("Registrar usuario");
		setBounds(100, 100, 456, 311);
		getContentPane().setLayout(null);
		
		JButton btnRegistrar = new JButton("Registrar");
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Usuario u = new Usuario ();
					u.setNombre(txtNombre.getText());
					u.setApellidoPaterno(txtApPaterno.getText());
					u.setApellidoMaterno(txtApMaterno.getText());
					u.setCorreoElectronico(txtCorreo.getText());
					u.setDni(txtDni.getText());
					
					u.setIdRol( Integer.parseInt( cbRol.getSelectedItem().toString().substring(0, 1)));
					
					siscomfiManager.addUsuario(u);
					
					limpiarVentana();
				} catch (Exception e){
					e.printStackTrace();
				}		
			}
		});
		btnRegistrar.setBounds(71, 247, 113, 23);
		getContentPane().add(btnRegistrar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(255, 247, 113, 23);
		getContentPane().add(btnCancelar);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "USUARIO", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 11, 421, 225);
		getContentPane().add(panel);
		
		JLabel label = new JLabel("Nombre (*)");
		label.setBounds(10, 52, 125, 14);
		panel.add(label);
		
		JLabel label_1 = new JLabel("Codigo");
		label_1.setBounds(10, 27, 125, 14);
		panel.add(label_1);
		
		txtCodigo = new JTextField();
		txtCodigo.setEditable(false);
		txtCodigo.setColumns(10);
		txtCodigo.setBounds(145, 24, 102, 20);
		panel.add(txtCodigo);
		
		txtNombre = new JTextField();
		txtNombre.setColumns(10);
		txtNombre.setBounds(145, 49, 266, 20);
		panel.add(txtNombre);
		
		JLabel lblRol = new JLabel("Rol (*)");
		lblRol.setBounds(10, 180, 125, 14);
		panel.add(lblRol);
		
		cbRol = new JComboBox();
		cbRol.setBounds(145, 177, 266, 20);
		panel.add(cbRol);
		fillCustomerCmb();
		
		JLabel lblApPaterno = new JLabel("Ap. Paterno (*)");
		lblApPaterno.setBounds(10, 80, 125, 14);
		panel.add(lblApPaterno);
		
		JLabel lblApMaterno = new JLabel("Ap. Materno (*)");
		lblApMaterno.setBounds(10, 105, 125, 14);
		panel.add(lblApMaterno);
		
		JLabel lblEmail = new JLabel("E-mail (*)");
		lblEmail.setBounds(10, 130, 125, 14);
		panel.add(lblEmail);
		
		JLabel lblDni = new JLabel("DNI (*)");
		lblDni.setBounds(10, 155, 125, 14);
		panel.add(lblDni);
		
		txtApPaterno = new JTextField();
		txtApPaterno.setBounds(145, 77, 266, 20);
		panel.add(txtApPaterno);
		txtApPaterno.setColumns(10);
		
		txtApMaterno = new JTextField();
		txtApMaterno.setBounds(145, 102, 266, 20);
		panel.add(txtApMaterno);
		txtApMaterno.setColumns(10);
		
		txtCorreo = new JTextField();
		txtCorreo.setBounds(145, 127, 266, 20);
		panel.add(txtCorreo);
		txtCorreo.setColumns(10);
		
		txtDni = new JTextField();
		txtDni.setBounds(145, 152, 266, 20);
		panel.add(txtDni);
		txtDni.setColumns(10);

	}
	
	
	public void fillCustomerCmb(){ //mostrare solo los clientes que estan activos
		cbRol.removeAllItems();
		ArrayList<Rol> rolList;
		try {
			rolList = siscomfiManager.queryAllRoles();
			for (int i=0; i<rolList.size();i++){				
				Rol r = (Rol)rolList.get(i);
				cbRol.addItem(r.getIdRol()+" - " + r.getNombre());
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void limpiarVentana(){
		
	}
}
