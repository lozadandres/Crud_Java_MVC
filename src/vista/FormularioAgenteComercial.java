package vista;

import modelo.AgenteComercial;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class FormularioAgenteComercial extends JDialog {
    private final JTextField txtId;
    private final JTextField txtCedula;
    private final JTextField txtLoguin;
    private final JPasswordField txtContrasena;
    private final JTextField txtNombreCompleto;
    private final JTextField txtDireccion;
    private final JTextField txtFechaNacimiento;
    private final JTextField txtFechaExpedicion;
    private final JTextField txtCorreoElectronico;
    private final JTextField txtCelular;
    private AgenteComercial agente;

    public FormularioAgenteComercial(Frame parent, boolean modal) {
        this(parent, modal, null);
    }

    public FormularioAgenteComercial(Frame parent, boolean modal, AgenteComercial agenteExistente) {
        super(parent, modal);
        setTitle(agenteExistente == null ? "Agregar Agente Comercial" : "Editar Agente Comercial");
        setSize(400, 500);
        setLocationRelativeTo(parent);
        setResizable(false);

        JPanel panel = new JPanel(new GridLayout(11, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("ID:"));
        txtId = new JTextField();
        txtId.setEditable(false);
        if (agenteExistente != null) {
            txtId.setText(String.valueOf(agenteExistente.getId_agente_comerciales()));
        } else {
            modelo.AgenteComercialServicio servicio = new modelo.AgenteComercialServicio();
            txtId.setText(String.valueOf(servicio.obtenerSiguienteId()));
        }
        panel.add(txtId);

        panel.add(new JLabel("Cédula:"));
        txtCedula = new JTextField();
        panel.add(txtCedula);

        panel.add(new JLabel("Login:"));
        txtLoguin = new JTextField();
        txtLoguin.setEditable(false);
        txtLoguin.setEnabled(false);
        panel.add(txtLoguin);

        panel.add(new JLabel("Contraseña:"));
        txtContrasena = new JPasswordField();
        txtContrasena.setEditable(false);
        txtContrasena.setEnabled(false);
        panel.add(txtContrasena);

        panel.add(new JLabel("Nombre Completo:"));
        txtNombreCompleto = new JTextField();
        panel.add(txtNombreCompleto);

        panel.add(new JLabel("Dirección:"));
        txtDireccion = new JTextField();
        panel.add(txtDireccion);

        panel.add(new JLabel("Fecha Nacimiento (yyyy-MM-dd):"));
        txtFechaNacimiento = new JTextField();
        panel.add(txtFechaNacimiento);

        panel.add(new JLabel("Fecha Expedición Doc. (yyyy-MM-dd):"));
        txtFechaExpedicion = new JTextField();
        panel.add(txtFechaExpedicion);

        panel.add(new JLabel("Correo Electrónico:"));
        txtCorreoElectronico = new JTextField();
        panel.add(txtCorreoElectronico);

        panel.add(new JLabel("Celular:"));
        txtCelular = new JTextField();
        panel.add(txtCelular);

        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        panel.add(btnGuardar);
        panel.add(btnCancelar);

        add(panel);

        if (agenteExistente != null) {
            txtCedula.setText(String.valueOf(agenteExistente.getCedula()));
            txtLoguin.setText(agenteExistente.getLoguin());
            txtLoguin.setEditable(false);
            txtLoguin.setEnabled(false);
            txtContrasena.setText(agenteExistente.getContrasena());
            txtContrasena.setEditable(false);
            txtContrasena.setEnabled(false);
            txtNombreCompleto.setText(agenteExistente.getNombre_completo());
            txtDireccion.setText(agenteExistente.getDireccion());
            txtFechaNacimiento.setText(agenteExistente.getFecha_nacimiento());
            txtFechaExpedicion.setText(agenteExistente.getFecha_expedicion_docu());
            txtCorreoElectronico.setText(agenteExistente.getCorreo_electronico());
            txtCelular.setText(agenteExistente.getCelular());
        }

        btnGuardar.addActionListener((ActionEvent e) -> {
            if (validarCampos()) {
                agente = new AgenteComercial();
                agente.setId_agente_comerciales(Integer.parseInt(txtId.getText()));
                agente.setCedula(Integer.parseInt(txtCedula.getText()));
                agente.setLoguin(txtLoguin.getText());
                agente.setContrasena(new String(txtContrasena.getPassword()));
                agente.setNombre_completo(txtNombreCompleto.getText());
                agente.setDireccion(txtDireccion.getText());
                agente.setFecha_nacimiento(txtFechaNacimiento.getText());
                agente.setFecha_expedicion_docu(txtFechaExpedicion.getText());
                agente.setCorreo_electronico(txtCorreoElectronico.getText());
                agente.setCelular(txtCelular.getText());
                dispose();
            }
        });

        btnCancelar.addActionListener((ActionEvent e) -> {
            agente = null;
            dispose();
        });
    }

    private boolean validarCampos() {
        if (txtId.getText().isEmpty() || txtCedula.getText().isEmpty() || txtNombreCompleto.getText().isEmpty() || txtDireccion.getText().isEmpty() || txtFechaNacimiento.getText().isEmpty() || txtFechaExpedicion.getText().isEmpty() || txtCorreoElectronico.getText().isEmpty() || txtCelular.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios excepto login y contraseña", "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        // Validación de formato de fechas
        String fechaNac = txtFechaNacimiento.getText();
        String fechaExp = txtFechaExpedicion.getText();
        if (!fechaNac.matches("\\d{4}-\\d{2}-\\d{2}")) {
            JOptionPane.showMessageDialog(this, "La fecha de nacimiento debe tener el formato yyyy-MM-dd", "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (!fechaExp.matches("\\d{4}-\\d{2}-\\d{2}")) {
            JOptionPane.showMessageDialog(this, "La fecha de expedición debe tener el formato yyyy-MM-dd", "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        // Validación básica de correo
        if (!txtCorreoElectronico.getText().contains("@")) {
            JOptionPane.showMessageDialog(this, "El correo electrónico no es válido", "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    public AgenteComercial getAgente() {
        return agente;
    }
}