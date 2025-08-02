package vista;

import controlador.ClienteControlador;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Ventana principal para la gestión de clientes.
 * Muestra una tabla con la lista de clientes y botones para las operaciones CRUD.
 */
public class VentanaClientes extends JFrame {
    /** Tabla que muestra la lista de clientes */
    private final JTable tablaClientes;
    /** Modelo de datos para la tabla de clientes */
    private final DefaultTableModel modeloTabla;
    /** Botón para agregar nuevos clientes */
    private final JButton btnAgregar;
    /** Botón para editar clientes existentes */
    private final JButton btnEditar;
    /** Botón para eliminar clientes */
    private final JButton btnEliminar;
    /** Campo de texto para búsqueda */
    private final JTextField txtBuscar;
    /** Botón para realizar la búsqueda */
    private final JButton btnBuscar;
    /** Controlador que maneja los eventos de la ventana */
    private final ClienteControlador controlador;

    /**
     * Constructor de la ventana principal de clientes.
     * Inicializa y configura todos los componentes de la interfaz.
     */
    public VentanaClientes() {
        setTitle("Gestión de Clientes");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtBuscar = new JTextField(20);
        btnBuscar = new JButton("Buscar");
        panelBusqueda.add(new JLabel("Buscar:"));
        panelBusqueda.add(txtBuscar);
        panelBusqueda.add(btnBuscar);
        panel.add(panelBusqueda, BorderLayout.NORTH);

        String[] columnas = {"ID", "Cédula", "Nombre", "Dirección", "Fecha Nacimiento", "Fecha Expedición", "Correo", "Teléfono 1", "Teléfono 2"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaClientes = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaClientes);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        btnAgregar = new JButton("Agregar Cliente");
        btnEditar = new JButton("Editar Cliente");
        btnEliminar = new JButton("Eliminar Cliente");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panel.add(panelBotones, BorderLayout.SOUTH);

        add(panel);

        controlador = new ClienteControlador(this, modeloTabla);
        configurarEventos();
        controlador.actualizarTabla();
    }

    /**
     * Configura los eventos de los botones asociándolos al controlador.
     */
    private void configurarEventos() {
        btnAgregar.addActionListener(controlador);
        btnEditar.addActionListener(controlador);
        btnEliminar.addActionListener(controlador);
        btnBuscar.addActionListener(controlador);
    }

    /**
     * Permite el acceso al campo de texto de búsqueda desde el controlador.
     */
    public JTextField getTxtBuscar() {
        return txtBuscar;
    }
}