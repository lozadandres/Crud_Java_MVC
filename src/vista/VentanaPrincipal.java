package vista;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana principal de la aplicación que solo muestra opciones de navegación y login.
 */
import modelo.UsuarioServicio;
import vista.FormularioLogin;
import vista.FormularioRegistroAdmin;

public class VentanaPrincipal extends JFrame {
    public VentanaPrincipal() {
        UsuarioServicio usuarioServicio = new UsuarioServicio();
        if (!usuarioServicio.existeAdministrador()) {
            FormularioRegistroAdmin registroAdmin = new FormularioRegistroAdmin(this, true);
            registroAdmin.setVisible(true);
        }
        FormularioLogin login = new FormularioLogin(this, true);
        login.setVisible(true);
        if (login.getUsuarioAutenticado() == null) {
            System.exit(0);
        }
        setTitle("Sistema de Gestión Inmobiliaria");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        // Panel de botones superior para navegación
        JPanel panelNavegacion = new JPanel();
        JButton btnAgentes = new JButton("Gestionar Agentes Comerciales");
        JButton btnPropietarios = new JButton("Gestionar Propietarios");
        JButton btnClientes = new JButton("Gestionar Clientes");
        JButton btnInmuebles = new JButton("Gestionar Inmuebles");
        JButton btnUsuarios = new JButton("Gestionar Usuarios");
        
        // Mostrar botones según el tipo de usuario
        String tipoUsuario = login.getUsuarioAutenticado().getTipo_usuario();
        if ("agente comercial".equals(tipoUsuario)) {
            panelNavegacion.add(btnPropietarios);
            panelNavegacion.add(btnClientes);
            panelNavegacion.add(btnInmuebles);
        } else {
            panelNavegacion.add(btnAgentes);
            panelNavegacion.add(btnPropietarios);
            panelNavegacion.add(btnClientes);
            panelNavegacion.add(btnInmuebles);
            panelNavegacion.add(btnUsuarios);
        }
        panel.add(panelNavegacion, BorderLayout.NORTH);

        // Aquí podrías agregar componentes de login si es necesario
        JLabel lblBienvenida = new JLabel("Bienvenido al Sistema de Gestión Inmobiliaria", SwingConstants.CENTER);
        panel.add(lblBienvenida, BorderLayout.CENTER);

        // Configurar eventos para los botones de navegación
        btnAgentes.addActionListener(e -> {
            VentanaAgentesComerciales ventanaAgentes = new VentanaAgentesComerciales();
            ventanaAgentes.setVisible(true);
        });
        btnPropietarios.addActionListener(e -> {
            VentanaPropietarios ventanaPropietarios = new VentanaPropietarios();
            ventanaPropietarios.setVisible(true);
        });
        btnClientes.addActionListener(e -> {
            VentanaClientes ventanaClientes = new VentanaClientes();
            ventanaClientes.setVisible(true);
        });
        btnInmuebles.addActionListener(e -> {
            vista.VentanaInmuebles ventanaInmuebles = new vista.VentanaInmuebles();
            ventanaInmuebles.setVisible(true);
        });

        btnUsuarios.addActionListener(e -> {
            VentanaUsuarios ventanaUsuarios = new VentanaUsuarios();
            ventanaUsuarios.setVisible(true);
        });

        add(panel);
    }
}