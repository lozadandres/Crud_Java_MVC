package controlador;

import modelo.Cliente;
import modelo.ClienteServicio;
import vista.FormularioCliente;
import vista.VentanaClientes;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Controlador que maneja las operaciones y eventos de la ventana de clientes.
 */
public class ClienteControlador implements ActionListener {
    /** Referencia a la ventana principal de clientes */
    private final VentanaClientes vista;
    /** Servicio que maneja las operaciones con clientes */
    private final ClienteServicio servicio;
    /** Modelo de la tabla que muestra los clientes */
    private final DefaultTableModel modeloTabla;

    /**
     * Constructor del controlador.
     * @param vista ventana principal de clientes
     * @param modeloTabla modelo de datos de la tabla de clientes
     */
    public ClienteControlador(VentanaClientes vista, DefaultTableModel modeloTabla) {
        this.vista = vista;
        this.servicio = new ClienteServicio();
        this.modeloTabla = modeloTabla;
    }

    /**
     * Maneja los eventos de los botones de la interfaz.
     * @param e evento de acción recibido
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        switch (comando) {
            case "Agregar Cliente" -> manejarAgregarCliente();
            case "Editar Cliente" -> manejarEditarCliente();
            case "Eliminar Cliente" -> manejarEliminarCliente();
            case "Buscar" -> manejarBuscarCliente();
        }
    }

    /**
     * Maneja la acción de agregar un nuevo cliente.
     */
    private void manejarAgregarCliente() {
        FormularioCliente formulario = new FormularioCliente(vista, true);
        formulario.setVisible(true);
        if (formulario.getCliente() != null) {
            servicio.crear(formulario.getCliente());
            actualizarTabla();
        }
    }

    /**
     * Maneja la acción de editar un cliente existente.
     */
    private void manejarEditarCliente() {
        String idStr = JOptionPane.showInputDialog(vista, "Ingrese el ID del cliente a editar:");
        if (idStr != null && !idStr.trim().isEmpty()) {
            try {
                int id = Integer.parseInt(idStr);
                ArrayList<Cliente> clientes = servicio.leer();
                Cliente clienteEncontrado = null;
                for (Cliente c : clientes) {
                    if (c.getId_cliente() == id) {
                        clienteEncontrado = c;
                        break;
                    }
                }
                if (clienteEncontrado != null) {
                    FormularioCliente formulario = new FormularioCliente(vista, true, clienteEncontrado);
                    formulario.setVisible(true);
                    if (formulario.getCliente() != null) {
                        servicio.actualizar(formulario.getCliente());
                        actualizarTabla();
                    }
                } else {
                    JOptionPane.showMessageDialog(vista, "No se encontró un cliente con el ID ingresado");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vista, "Por favor, ingrese un número de ID válido");
            }
        }
    }

    /**
     * Maneja la acción de eliminar un cliente.
     */
    private void manejarEliminarCliente() {
        String idStr = JOptionPane.showInputDialog(vista, "Ingrese el ID del cliente a eliminar:");
        if (idStr != null && !idStr.trim().isEmpty()) {
            try {
                int id = Integer.parseInt(idStr);
                ArrayList<Cliente> clientes = servicio.leer();
                boolean clienteExiste = false;
                for (Cliente c : clientes) {
                    if (c.getId_cliente() == id) {
                        clienteExiste = true;
                        break;
                    }
                }
                if (clienteExiste) {
                    int confirmacion = JOptionPane.showConfirmDialog(vista,
                            "¿Está seguro de eliminar este cliente?",
                            "Confirmar eliminación",
                            JOptionPane.YES_NO_OPTION);
                    if (confirmacion == JOptionPane.YES_OPTION) {
                        if (servicio.eliminar(id)) {
                            actualizarTabla();
                            JOptionPane.showMessageDialog(vista, "Cliente eliminado con éxito");
                        } else {
                            JOptionPane.showMessageDialog(vista, "No se pudo eliminar el cliente");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(vista, "No se encontró un cliente con el ID ingresado");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vista, "Por favor, ingrese un número de ID válido");
            }
        }
    }

    /**
     * Actualiza la tabla de clientes con los datos más recientes.
     */
    public void actualizarTabla() {
        modeloTabla.setRowCount(0);
        ArrayList<Cliente> clientes = servicio.leer();
        for (Cliente cliente : clientes) {
            Object[] fila = {
                cliente.getId_cliente(),
                cliente.getCedula(),
                cliente.getNombre_completo(),
                cliente.getDireccion(),
                cliente.getFecha_nacimiento(),
                cliente.getFecha_expedicion_docu(),
                cliente.getCorreo_electronico(),
                cliente.getTelefono_1(),
                cliente.getTelefono_2()
            };
            modeloTabla.addRow(fila);
        }
    }

    /**
     * Maneja la acción de buscar clientes por criterio.
     */
    private void manejarBuscarCliente() {
        String criterio = vista.getTxtBuscar().getText();
        modeloTabla.setRowCount(0);
        ArrayList<Cliente> clientes = servicio.buscarPorCriterio(criterio);
        if (clientes.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "No se encontraron clientes para el criterio ingresado.");
        }
        for (Cliente cliente : clientes) {
            Object[] fila = {
                cliente.getId_cliente(),
                cliente.getCedula(),
                cliente.getNombre_completo(),
                cliente.getDireccion(),
                cliente.getFecha_nacimiento(),
                cliente.getFecha_expedicion_docu(),
                cliente.getCorreo_electronico(),
                cliente.getTelefono_1(),
                cliente.getTelefono_2()
            };
            modeloTabla.addRow(fila);
        }
    }
}