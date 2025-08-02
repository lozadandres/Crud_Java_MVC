package vista;

import modelo.Usuario;
import modelo.UsuarioServicio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class FormularioUsuario extends JDialog {
    private final JTextField txtLoguin;
    private final JPasswordField txtContrasena;
    private final JComboBox<String> comboTipoUsuario;
    private final JTextField txtIdAgenteComercial;
    private Usuario usuario;

    public FormularioUsuario(Frame parent, boolean modal) {
        this(parent, modal, null);
    }

    public FormularioUsuario(Frame parent, boolean modal, Usuario usuarioExistente) {
        super(parent, modal);
        setTitle(usuarioExistente == null ? "Agregar Usuario" : "Editar Usuario");
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setResizable(false);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Campos del formulario
        panel.add(new JLabel("Login:"));
        txtLoguin = new JTextField();
        panel.add(txtLoguin);

        panel.add(new JLabel("Contraseña:"));
        txtContrasena = new JPasswordField();
        panel.add(txtContrasena);

        panel.add(new JLabel("Tipo de Usuario:"));
        comboTipoUsuario = new JComboBox<>(new String[]{"administrador", "agente comercial"});
        panel.add(comboTipoUsuario);

        panel.add(new JLabel("ID Agente Comercial:"));
        txtIdAgenteComercial = new JTextField();
        panel.add(txtIdAgenteComercial);

        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");
        panel.add(btnGuardar);
        panel.add(btnCancelar);

        // Si estamos editando un usuario existente, llenar los campos
        if (usuarioExistente != null) {
            txtLoguin.setText(usuarioExistente.getLoguin());
            comboTipoUsuario.setSelectedItem(usuarioExistente.getTipo_usuario());
            if (usuarioExistente.getId_agente_comerciales() > 0) {
                txtIdAgenteComercial.setText(String.valueOf(usuarioExistente.getId_agente_comerciales()));
            }
        }

        // Manejar el cambio en el tipo de usuario
        comboTipoUsuario.addActionListener(e -> {
            String tipoSeleccionado = (String) comboTipoUsuario.getSelectedItem();
            txtIdAgenteComercial.setEnabled("agente comercial".equals(tipoSeleccionado));
            if ("administrador".equals(tipoSeleccionado)) {
                txtIdAgenteComercial.setText("");
            }
        });

        // Configurar el estado inicial del campo ID Agente Comercial
        txtIdAgenteComercial.setEnabled("agente comercial".equals(comboTipoUsuario.getSelectedItem()));

        // Acciones de los botones
        btnGuardar.addActionListener((ActionEvent e) -> {
            if (validarCampos()) {
                usuario = new Usuario();
                if (usuarioExistente != null) {
                    usuario.setId_usuario(usuarioExistente.getId_usuario());
                }
                usuario.setLoguin(txtLoguin.getText().trim());
                usuario.setContrasena(new String(txtContrasena.getPassword()));
                usuario.setTipo_usuario((String) comboTipoUsuario.getSelectedItem());
                
                if ("agente comercial".equals(usuario.getTipo_usuario()) && !txtIdAgenteComercial.getText().trim().isEmpty()) {
                    usuario.setId_agente_comerciales(Integer.parseInt(txtIdAgenteComercial.getText().trim()));
                }
                
                dispose();
            }
        });

        btnCancelar.addActionListener((ActionEvent e) -> dispose());

        add(panel);
    }

    private boolean validarCampos() {
        if (txtLoguin.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo Login es obligatorio", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (txtContrasena.getPassword().length == 0) {
            JOptionPane.showMessageDialog(this, "El campo Contraseña es obligatorio", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if ("agente comercial".equals(comboTipoUsuario.getSelectedItem()) && txtIdAgenteComercial.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El ID del Agente Comercial es obligatorio para usuarios tipo agente comercial", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!txtIdAgenteComercial.getText().trim().isEmpty()) {
            try {
                Integer.parseInt(txtIdAgenteComercial.getText().trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "El ID del Agente Comercial debe ser un número", "Error de validación", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        return true;
    }

    public Usuario getUsuario() {
        return usuario;
    }
}