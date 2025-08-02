package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import controlador.UsuarioControlador;

public class VentanaUsuarios extends JFrame {
    private final JTable tablaUsuarios;
    private final DefaultTableModel modeloTabla;
    private final JButton btnAgregar;
    private final JButton btnEditar;
    private final JButton btnEliminar;

    public VentanaUsuarios() {
        setTitle("Gestión de Usuarios");
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Crear el modelo de tabla
        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Definir las columnas
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Login");
        modeloTabla.addColumn("Tipo Usuario");
        modeloTabla.addColumn("ID Agente Comercial");

        // Crear la tabla
        tablaUsuarios = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaUsuarios);

        // Panel de botones
        JPanel panelBotones = new JPanel();
        btnAgregar = new JButton("Agregar Usuario");
        btnEditar = new JButton("Editar Usuario");
        btnEliminar = new JButton("Eliminar Usuario");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);

        // Organizar el layout
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        // Obtener el controlador
        UsuarioControlador controlador = new UsuarioControlador(this, modeloTabla);

        // Asignar el controlador a los botones
        btnAgregar.addActionListener(controlador);
        btnEditar.addActionListener(controlador);
        btnEliminar.addActionListener(controlador);

        // Asignar nombres de acción a los botones
        btnAgregar.setActionCommand("AGREGAR");
        btnEditar.setActionCommand("EDITAR");
        btnEliminar.setActionCommand("ELIMINAR");
    }

    public JTable getTablaUsuarios() {
        return tablaUsuarios;
    }

    public JButton getBtnAgregar() {
        return btnAgregar;
    }

    public JButton getBtnEditar() {
        return btnEditar;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }
}