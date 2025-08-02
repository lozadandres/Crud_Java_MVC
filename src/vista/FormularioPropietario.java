package vista;

import modelo.Propietario;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class FormularioPropietario extends JDialog {
    private final JTextField txtId;
    private final JTextField txtCedula;
    private final JTextField txtNombreCompleto;
    private final JTextField txtDireccion;
    private final JTextField txtFechaNacimiento;
    private final JTextField txtFechaExpedicion;
    private final JTextField txtCorreoElectronico;
    private final JTextField txtTelefono1;
    private final JTextField txtTelefono2;
    private Propietario propietario;

    public FormularioPropietario(Frame parent, boolean modal) {
        this(parent, modal, null);
    }

    public FormularioPropietario(Frame parent, boolean modal, Propietario propietarioExistente) {
        super(parent, modal);
        setTitle(propietarioExistente == null ? "Agregar Propietario" : "Editar Propietario");
        setSize(400, 500);
        setLocationRelativeTo(parent);
        setResizable(false);

        JPanel panel = new JPanel(new GridLayout(10, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("ID:"));
        txtId = new JTextField();
        txtId.setEditable(false);
        if (propietarioExistente != null) {
            txtId.setText(String.valueOf(propietarioExistente.getId_propietario()));
        } else {
            modelo.PropietarioServicio servicio = new modelo.PropietarioServicio();
            txtId.setText(String.valueOf(servicio.obtenerSiguienteId()));
        }
        panel.add(txtId);

        panel.add(new JLabel("Cédula:"));
        txtCedula = new JTextField();
        panel.add(txtCedula);

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

        if (propietarioExistente != null) {
            txtCedula.setText(String.valueOf(propietarioExistente.getCedula()));
            txtNombreCompleto.setText(propietarioExistente.getNombre_completo());
            txtDireccion.setText(propietarioExistente.getDireccion());
            txtFechaNacimiento.setText(propietarioExistente.getFecha_nacimiento());
            txtFechaExpedicion.setText(propietarioExistente.getFecha_expedicion_docu());
            txtCorreoElectronico.setText(propietarioExistente.getCorreo_electronico());
            txtTelefono1.setText(propietarioExistente.getTelefono_1());
            txtTelefono2.setText(propietarioExistente.getTelefono_2());
        }

        btnGuardar.addActionListener((ActionEvent e) -> {
            if (validarCampos()) {
                propietario = new Propietario();
                propietario.setId_propietario(Integer.parseInt(txtId.getText()));
                propietario.setCedula(Integer.parseInt(txtCedula.getText()));
                propietario.setNombre_completo(txtNombreCompleto.getText());
                propietario.setDireccion(txtDireccion.getText());
                propietario.setFecha_nacimiento(txtFechaNacimiento.getText());
                propietario.setFecha_expedicion_docu(txtFechaExpedicion.getText());
                propietario.setCorreo_electronico(txtCorreoElectronico.getText());
                propietario.setTelefono_1(txtTelefono1.getText());
                propietario.setTelefono_2(txtTelefono2.getText());
                dispose();
            }
        });

        btnCancelar.addActionListener((ActionEvent e) -> {
            propietario = null;
            dispose();
        });
    }

    private boolean validarCampos() {
        if (txtId.getText().isEmpty() || txtCedula.getText().isEmpty() || txtNombreCompleto.getText().isEmpty() || txtDireccion.getText().isEmpty() || txtFechaNacimiento.getText().isEmpty() || txtFechaExpedicion.getText().isEmpty() || txtCorreoElectronico.getText().isEmpty() || txtTelefono1.getText().isEmpty() || txtTelefono2.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Validación", JOptionPane.WARNING_MESSAGE);
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

    public Propietario getPropietario() {
        return propietario;
    }
}