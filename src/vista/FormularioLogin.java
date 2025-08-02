package vista;

import modelo.Usuario;
import modelo.UsuarioServicio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class FormularioLogin extends JDialog {
    private final JTextField txtLoguin;
    private final JPasswordField txtContrasena;
    private Usuario usuarioAutenticado;

    public FormularioLogin(Frame parent, boolean modal) {
        super(parent, modal);
        setTitle("Inicio de Sesión");
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

        JButton btnIngresar = new JButton("Ingresar");
        JButton btnCancelar = new JButton("Cancelar");
        panel.add(btnIngresar);
        panel.add(btnCancelar);

        add(panel);

        btnIngresar.addActionListener((ActionEvent e) -> {
            String loguin = txtLoguin.getText();
            String contrasena = new String(txtContrasena.getPassword());
            UsuarioServicio servicio = new UsuarioServicio();
            usuarioAutenticado = servicio.autenticar(loguin, contrasena);
            if (usuarioAutenticado != null) {
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", "Error de autenticación", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancelar.addActionListener((ActionEvent e) -> {
            usuarioAutenticado = null;
            dispose();
        });
    }

    public Usuario getUsuarioAutenticado() {
        return usuarioAutenticado;
    }
}