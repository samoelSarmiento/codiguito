package pe.pucp.edu.pe.siscomfi.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import pe.pucp.edu.pe.siscomfi.bm.BD.RolDB;
import pe.pucp.edu.pe.siscomfi.controller.RolController;
import pe.pucp.edu.pe.siscomfi.model.Rol;

import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class VistaMantenimientoUsuario extends JInternalFrame implements ActionListener {
	private JTextField txtNombre;
	private JTextField txtApPaterno;
	private JTextField txtApMaterno;
	private JTextField txtCorreoElectronico;
	private JTextField txtDNI;
	private JTable tblUsuarios;
	private JComboBox<String> cmbRol;
	private JButton btnEliminar;
	private JButton btnCrear;
	private JButton btnModificar;
	private TableModel tblModel;

	public VistaMantenimientoUsuario() {
		setBounds(100, 100, 643, 436);
		setTitle("Mantenimiento de Usuarios");
		setClosable(true);
		getContentPane().setLayout(null);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(10, 42, 112, 14);
		getContentPane().add(lblNombre);

		JLabel lblApellidoMaterno = new JLabel("Apellido Materno:");
		lblApellidoMaterno.setBounds(10, 81, 112, 14);
		getContentPane().add(lblApellidoMaterno);

		JLabel lblApellidoPaterno = new JLabel("Apellido Paterno:");
		lblApellidoPaterno.setBounds(342, 42, 112, 14);
		getContentPane().add(lblApellidoPaterno);

		JLabel lblCorreoElectronico = new JLabel("Correo Electr\u00F3nico:");
		lblCorreoElectronico.setBounds(342, 81, 112, 14);
		getContentPane().add(lblCorreoElectronico);

		JLabel lblPerfilDeUsuario = new JLabel("Perfil de Usuario:");
		lblPerfilDeUsuario.setBounds(342, 120, 112, 14);
		getContentPane().add(lblPerfilDeUsuario);

		JLabel lblDni = new JLabel("DNI:");
		lblDni.setBounds(10, 120, 112, 14);
		getContentPane().add(lblDni);

		txtNombre = new JTextField();
		txtNombre.setBounds(132, 39, 140, 20);
		getContentPane().add(txtNombre);
		txtNombre.setColumns(10);

		txtApPaterno = new JTextField();
		txtApPaterno.setBounds(463, 39, 140, 20);
		getContentPane().add(txtApPaterno);
		txtApPaterno.setColumns(10);

		txtApMaterno = new JTextField();
		txtApMaterno.setBounds(132, 78, 140, 20);
		getContentPane().add(txtApMaterno);
		txtApMaterno.setColumns(10);

		txtCorreoElectronico = new JTextField();
		txtCorreoElectronico.setBounds(463, 78, 140, 20);
		getContentPane().add(txtCorreoElectronico);
		txtCorreoElectronico.setColumns(10);

		txtDNI = new JTextField();
		txtDNI.setBounds(132, 117, 140, 20);
		getContentPane().add(txtDNI);
		txtDNI.setColumns(10);

		cmbRol = new JComboBox<String>();
		cmbRol.setBounds(463, 117, 140, 20);
		getContentPane().add(cmbRol);
		ArrayList<Rol> listaRol = RolController.queryAllRoles();
		for (int i = 0; i < listaRol.size(); i++) {
			Rol rolL = listaRol.get(i);
			cmbRol.addItem(rolL.getIdRol() + " - " + rolL.getNombre());
		}

		btnCrear = new JButton("Crear");
		btnCrear.setBounds(57, 177, 112, 23);
		getContentPane().add(btnCrear);

		btnModificar = new JButton("Modificiar");
		btnModificar.setBounds(248, 177, 112, 23);
		getContentPane().add(btnModificar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(440, 177, 112, 23);
		getContentPane().add(btnEliminar);

		JPanel pnTablaUsuarios = new JPanel();
		pnTablaUsuarios.setBounds(32, 226, 548, 158);
		getContentPane().add(pnTablaUsuarios);
		pnTablaUsuarios.setLayout(null);

		JScrollPane scpTablaUsuarios = new JScrollPane();
		scpTablaUsuarios.setBounds(0, 0, 548, 158);
		pnTablaUsuarios.add(scpTablaUsuarios);

		tblUsuarios = new JTable();
		tblModel = new TableModel();
		tblUsuarios.setModel(tblModel);
		tblUsuarios.getTableHeader().setReorderingAllowed(false);
		scpTablaUsuarios.setViewportView(tblUsuarios);

		// Listener
		btnModificar.addActionListener(this);
		btnCrear.addActionListener(this);
		btnEliminar.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnEliminar) {
			int option = JOptionPane.showConfirmDialog(null, "¿Desea eliminar el usuario?", "",
					JOptionPane.YES_NO_OPTION);
			
		}
	}

	class TableModel extends DefaultTableModel {
		String titles[] = { "ID", "NOMBRE", "APELLIDO", "CORREO", "DNI", "TIPO PERFIL" };

		public int getColumnCount() {
			return titles.length;
		}

		public String getColumnName(int col) {
			return titles[col];
		}

		public Object getValueAt(int arg0, int arg1) {
			return null;
		}
	}
}
