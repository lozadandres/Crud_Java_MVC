package vista;

import controlador.PropietarioControlador;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Ventana principal para la gestión de propietarios.
 * Muestra una tabla con la lista de propietarios y botones para las operaciones CRUD.
 */
public class VentanaPropietarios extends JFrame {
    /** Tabla que muestra la lista de propietarios */
    private final JTable tablaPropietarios;
    /** Modelo de datos para la tabla de propietarios */
    private final DefaultTableModel modeloTabla;
    /** Botón para agregar nuevos propietarios */
    private final JButton btnAgregar;
    /** Botón para editar propietarios existentes */
    private final JButton btnEditar;
    /** Botón para eliminar propietarios */
    private final JButton btnEliminar;
    /** Campo de texto para búsqueda */
    private final JTextField txtBuscar;
    /** Botón para realizar la búsqueda */
    private final JButton btnBuscar;
    /** Controlador que maneja los eventos de la ventana */
    private final PropietarioControlador controlador;

    /**
     * Constructor de la ventana principal de propietarios.
     * Inicializa y configura todos los componentes de la interfaz.
     */
    public VentanaPropietarios() {
        // Configuración de la ventana
        setTitle("Gestión de Propietarios");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        
        // Panel principal
        JPanel panel = new JPanel(new BorderLayout());
        
        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtBuscar = new JTextField(20);
        btnBuscar = new JButton("Buscar");
        panelBusqueda.add(new JLabel("Buscar:"));
        panelBusqueda.add(txtBuscar);
        panelBusqueda.add(btnBuscar);
        panel.add(panelBusqueda, BorderLayout.NORTH);
        
        // Configuración de la tabla
        String[] columnas = {"ID", "Cédula", "Nombre Completo", "Dirección", "Fecha Nacimiento", "Fecha Expedición", "Correo", "Teléfono 1", "Teléfono 2"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaPropietarios = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaPropietarios);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel panelBotones = new JPanel();
        btnAgregar = new JButton("Agregar Propietario");
        btnEditar = new JButton("Editar Propietario");
        btnEliminar = new JButton("Eliminar Propietario");
        
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panel.add(panelBotones, BorderLayout.SOUTH);
        
        // Agregar panel a la ventana
        add(panel);
        
        // Inicializar controlador
        controlador = new PropietarioControlador(this, modeloTabla);
        
        // Configurar eventos de botones
        configurarEventos();
        
        // Cargar datos iniciales
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