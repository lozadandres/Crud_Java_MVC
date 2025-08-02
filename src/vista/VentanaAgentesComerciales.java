package vista;

import controlador.AgenteComercialControlador;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Ventana principal para la gestión de agentes comerciales.
 * Muestra una tabla con la lista de agentes y botones para las operaciones CRUD.
 */
public class VentanaAgentesComerciales extends JFrame {
    /** Tabla que muestra la lista de agentes comerciales */
    private final JTable tablaAgentes;
    /** Modelo de datos para la tabla de agentes */
    private final DefaultTableModel modeloTabla;
    /** Botón para agregar nuevos agentes */
    private final JButton btnAgregar;
    /** Botón para editar agentes existentes */
    private final JButton btnEditar;
    /** Botón para eliminar agentes */
    private final JButton btnEliminar;
    /** Campo de texto para búsqueda */
    private final JTextField txtBuscar;
    /** Botón para realizar la búsqueda */
    private final JButton btnBuscar;
    /** Controlador que maneja los eventos de la ventana */
    private final AgenteComercialControlador controlador;

    /**
     * Constructor de la ventana principal de agentes comerciales.
     * Inicializa y configura todos los componentes de la interfaz.
     */
    public VentanaAgentesComerciales() {
        // Configuración de la ventana
        setTitle("Gestión de Agentes Comerciales");
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
        String[] columnas = {"ID", "Cédula", "Login", "Nombre Completo", "Correo", "Celular"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaAgentes = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaAgentes);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel panelBotones = new JPanel();
        btnAgregar = new JButton("Agregar Agente");
        btnEditar = new JButton("Editar Agente");
        btnEliminar = new JButton("Eliminar Agente");
        
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panel.add(panelBotones, BorderLayout.SOUTH);
        
        // Agregar panel a la ventana
        add(panel);
        
        // Inicializar controlador
        controlador = new AgenteComercialControlador(this, modeloTabla);
        
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