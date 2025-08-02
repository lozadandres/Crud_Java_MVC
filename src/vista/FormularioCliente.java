package vista;

import modelo.Cliente;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Formulario modal para agregar o editar datos de un cliente.
 * Se utiliza tanto para la creación como para la modificación de clientes.
 */
public class FormularioCliente extends JDialog {
    private final JTextField txtId;
    private final JTextField txtCedula;
    private final JTextField txtNombre;
    private final JTextField txtDireccion;
    private final JTextField txtFechaNacimiento;
    private final JTextField txtFechaExpedicion;
    private final JTextField txtCorreoElectronico;
    private final JTextField txtTelefono1;
    private final JTextField txtTelefono2;
    private Cliente cliente;
    
    /**
     * Constructor para crear un nuevo cliente.
     * @param parent ventana padre
     * @param modal indica si el diálogo es modal
     */
    public FormularioCliente(Frame parent, boolean modal) {
        this(parent, modal, null);
    }
    
    /**
     * Constructor para editar un cliente existente.
     * @param parent ventana padre
     * @param modal indica si el diálogo es modal
     * @param clienteExistente cliente a editar, null si es nuevo
     */
    public FormularioCliente(Frame parent, boolean modal, Cliente clienteExistente) {
        super(parent, modal);
        setTitle(clienteExistente == null ? "Agregar Cliente" : "Editar Cliente");
        setSize(400, 500);
        setLocationRelativeTo(parent);
        setResizable(false);
        
        JPanel panel = new JPanel(new GridLayout(10, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        panel.add(new JLabel("ID:"));
        txtId = new JTextField();
        txtId.setEditable(false);
        if (clienteExistente != null) {
            txtId.setText(String.valueOf(clienteExistente.getId_cliente()));
        } else {
            modelo.ClienteServicio servicio = new modelo.ClienteServicio();
            txtId.setText(String.valueOf(servicio.obtenerSiguienteId()));
        }
        panel.add(txtId);
        
        panel.add(new JLabel("Cédula:"));
        txtCedula = new JTextField();
        panel.add(txtCedula);
        
        panel.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panel.add(txtNombre);
        
        panel.add(new JLabel("Dirección:"));
        txtDireccion = new JTextField();
        panel.add(txtDireccion);
        
        panel.add(new JLabel("Fecha Nacimiento (DD/MM/AAAA):"));
        txtFechaNacimiento = new JTextField();
        panel.add(txtFechaNacimiento);
        
        panel.add(new JLabel("Fecha Expedición Doc. (DD/MM/AAAA):"));
        txtFechaExpedicion = new JTextField();
        panel.add(txtFechaExpedicion);
        
        panel.add(new JLabel("Correo Electrónico:"));
        txtCorreoElectronico = new JTextField();
        panel.add(txtCorreoElectronico);
        
        panel.add(new JLabel("Teléfono 1:"));
        txtTelefono1 = new JTextField();
        panel.add(txtTelefono1);
        
        panel.add(new JLabel("Teléfono 2:"));
        txtTelefono2 = new JTextField();
        panel.add(txtTelefono2);
        
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");
        
        panel.add(btnGuardar);
        panel.add(btnCancelar);
        
        add(panel);
        
        if (clienteExistente != null) {
            txtCedula.setText(String.valueOf(clienteExistente.getCedula()));
            txtNombre.setText(clienteExistente.getNombre_completo());
            txtDireccion.setText(clienteExistente.getDireccion());
            txtFechaNacimiento.setText(clienteExistente.getFecha_nacimiento());
            txtFechaExpedicion.setText(clienteExistente.getFecha_expedicion_docu());
            txtCorreoElectronico.setText(clienteExistente.getCorreo_electronico());
            txtTelefono1.setText(clienteExistente.getTelefono_1());
            txtTelefono2.setText(clienteExistente.getTelefono_2());
        }
        
        btnGuardar.addActionListener((ActionEvent e) -> {
            if (validarCampos()) {
                cliente = new Cliente();
                cliente.setId_cliente(Integer.parseInt(txtId.getText()));
                cliente.setCedula(Integer.parseInt(txtCedula.getText()));
                cliente.setNombre_completo(txtNombre.getText());
                cliente.setDireccion(txtDireccion.getText());
                cliente.setFecha_nacimiento(txtFechaNacimiento.getText());
                cliente.setFecha_expedicion_docu(txtFechaExpedicion.getText());
                cliente.setCorreo_electronico(txtCorreoElectronico.getText());
                cliente.setTelefono_1(txtTelefono1.getText());
                cliente.setTelefono_2(txtTelefono2.getText());
                dispose();
            }
        });
        
        btnCancelar.addActionListener((ActionEvent e) -> {
            cliente = null;
            dispose();
        });
    }
    
    /**
     * Valida que todos los campos del formulario estén correctamente llenos.
     * @return true si los campos son válidos, false en caso contrario
     */
    private boolean validarCampos() {
        // Validación básica existente
        if (txtId.getText().isEmpty() || txtCedula.getText().isEmpty() || txtNombre.getText().isEmpty() || txtDireccion.getText().isEmpty() || txtFechaNacimiento.getText().isEmpty() || txtFechaExpedicion.getText().isEmpty() || txtCorreoElectronico.getText().isEmpty() || txtTelefono1.getText().isEmpty() || txtTelefono2.getText().isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Validación", javax.swing.JOptionPane.WARNING_MESSAGE);
            return false;
        }
        // Validación de formato de fechas
        String fechaNac = txtFechaNacimiento.getText();
        String fechaExp = txtFechaExpedicion.getText();
        if (!fechaNac.matches("\\d{4}-\\d{2}-\\d{2}")) {
            javax.swing.JOptionPane.showMessageDialog(this, "La fecha de nacimiento debe tener el formato yyyy-MM-dd", "Validación", javax.swing.JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (!fechaExp.matches("\\d{4}-\\d{2}-\\d{2}")) {
            javax.swing.JOptionPane.showMessageDialog(this, "La fecha de expedición debe tener el formato yyyy-MM-dd", "Validación", javax.swing.JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
    
    /**
     * Obtiene el cliente creado o editado en el formulario.
     * @return objeto Cliente con los datos ingresados, null si se canceló
     */
    public Cliente getCliente() {
        return cliente;
    }
}