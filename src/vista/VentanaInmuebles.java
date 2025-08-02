package vista;

import controlador.InmuebleControlador;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Ventana principal para la gestión de inmuebles.
 * Muestra una tabla con la lista de inmuebles y botones para las operaciones CRUD.
 */
public class VentanaInmuebles extends JFrame {
    private final JTable tablaInmuebles;
    private final DefaultTableModel modeloTabla;
    private final JButton btnAgregar;
    private final JButton btnEditar;
    private final JButton btnEliminar;
    private final JTextField txtBuscar;
    private final JButton btnBuscar;
    private final InmuebleControlador controlador;

    public VentanaInmuebles() {
        setTitle("Gestión de Inmuebles");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 500);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtBuscar = new JTextField(20);
        btnBuscar = new JButton("Buscar");
        panelBusqueda.add(new JLabel("Buscar:"));
        panelBusqueda.add(txtBuscar);
        panelBusqueda.add(btnBuscar);
        panel.add(panelBusqueda, BorderLayout.NORTH);

        String[] columnas = {"ID", "Código", "Descripción", "Tipo", "Modalidad", "Precio Venta", "Estado", "Baños", "Tamaño (m2)", "Dirección", "Departamento", "Ciudad", "ID Propietario", "¿Inmobiliaria?", "Fecha Adq.", "Costo Adq."};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaInmuebles = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaInmuebles);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        btnAgregar = new JButton("Agregar Inmueble");
        btnEditar = new JButton("Editar Inmueble");
        btnEliminar = new JButton("Eliminar Inmueble");
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panel.add(panelBotones, BorderLayout.SOUTH);

        add(panel);

        controlador = new controlador.InmuebleControlador(this, modeloTabla);
        configurarEventos();
        controlador.actualizarTabla();
    }

    private void configurarEventos() {
        btnAgregar.addActionListener(controlador);
        btnEditar.addActionListener(controlador);
        btnEliminar.addActionListener(controlador);
        btnBuscar.addActionListener(controlador);
    }

    public JTextField getTxtBuscar() {
        return txtBuscar;
    }
}