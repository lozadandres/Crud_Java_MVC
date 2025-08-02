package vista;

import modelo.Usuario;
import modelo.UsuarioServicio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class FormularioRegistroAdmin extends JDialog {
    private final JTextField txtLoguin;
    private final JPasswordField txtContrasena;
    private Usuario usuarioRegistrado;

    public FormularioRegistroAdmin(Frame parent, boolean modal) {
        super(parent, modal);
        setTitle("Registrar Administrador");
        setSize(350, 200);
        setLocationRelativeTo(parent);
        setResizable(false);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Login:"));
        txtLoguin = new JTextField();
        panel.add(txtLoguin);

        panel.add(new JLabel("Contraseña:"));
        txtContrasena = new JPasswordField();
        panel.add(txtContrasena);

        JButton btnRegistrar = new JButton("Registrar");
        JButton btnCancelar = new JButton("Cancelar");
        panel.add(btnRegistrar);
        panel.add(btnCancelar);

        add(panel);

        btnRegistrar.addActionListener((ActionEvent e) -> {
            String loguin = txtLoguin.getText();
            String contrasena = new String(txtContrasena.getPassword());
            if (loguin.isEmpty() || contrasena.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Usuario usuario = new Usuario();
            usuario.setLoguin(loguin);
            usuario.setContrasena(contrasena);
            usuario.setId_agente_comerciales(0); // No asociado a agente
            usuario.setTipo_usuario("administrador");
            UsuarioServicio servicio = new UsuarioServicio();
            servicio.crear(usuario);
            usuarioRegistrado = usuario;
            JOptionPane.showMessageDialog(this, "Administrador registrado con éxito", "Registro", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        });

        btnCancelar.addActionListener((ActionEvent e) -> {
            usuarioRegistrado = null;
            dispose();
        });
    }

    public Usuario getUsuarioRegistrado() {
        return usuarioRegistrado;
    }
}