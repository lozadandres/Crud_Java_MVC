package controlador;

import modelo.Propietario;
import modelo.PropietarioServicio;
import vista.FormularioPropietario;
import vista.VentanaPropietarios;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Controlador que maneja las operaciones y eventos de la ventana de propietarios.
 */
public class PropietarioControlador implements ActionListener {
    /** Referencia a la ventana principal de propietarios */
    private final VentanaPropietarios vista;
    /** Servicio que maneja las operaciones con propietarios */
    private final PropietarioServicio servicio;
    /** Modelo de la tabla que muestra los propietarios */
    private final DefaultTableModel modeloTabla;

    /**
     * Constructor del controlador.
     * @param vista ventana principal de propietarios
     * @param modeloTabla modelo de datos de la tabla de propietarios
     */
    public PropietarioControlador(VentanaPropietarios vista, DefaultTableModel modeloTabla) {
        this.vista = vista;
        this.servicio = new PropietarioServicio();
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
            case "Agregar Propietario" -> manejarAgregarPropietario();
            case "Editar Propietario" -> manejarEditarPropietario();
            case "Eliminar Propietario" -> manejarEliminarPropietario();
            case "Buscar" -> manejarBuscarPropietario();
        }
    }

    /**
     * Maneja la acción de agregar un nuevo propietario.
     * Muestra el formulario de propietario y procesa los datos ingresados.
     */
    private void manejarAgregarPropietario() {
        FormularioPropietario formulario = new FormularioPropietario(vista, true);
        formulario.setVisible(true);
        if (formulario.getPropietario() != null) {
            servicio.crear(formulario.getPropietario());
            actualizarTabla();
        }
    }

    /**
     * Maneja la acción de editar un propietario existente.
     * Solicita el ID del propietario y muestra el formulario con sus datos.
     */
    private void manejarEditarPropietario() {
        String idStr = JOptionPane.showInputDialog(vista, "Ingrese el ID del propietario a editar:");
        if (idStr != null && !idStr.trim().isEmpty()) {
            try {
                int id = Integer.parseInt(idStr);
                ArrayList<Propietario> propietarios = servicio.leer();
                Propietario propietarioEncontrado = null;

                for (Propietario p : propietarios) {
                    if (p.getId_propietario() == id) {
                        propietarioEncontrado = p;
                        break;
                    }
                }

                if (propietarioEncontrado != null) {
                    FormularioPropietario formulario = new FormularioPropietario(vista, true, propietarioEncontrado);
                    formulario.setVisible(true);

                    if (formulario.getPropietario() != null) {
                        servicio.actualizar(formulario.getPropietario());
                        actualizarTabla();
                    }
                } else {
                    JOptionPane.showMessageDialog(vista, "No se encontró un propietario con el ID ingresado");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vista, "Por favor, ingrese un ID válido");
            }
        }
    }

    /**
     * Maneja la acción de eliminar un propietario.
     * Solicita el ID del propietario y confirma la eliminación.
     */
    private void manejarEliminarPropietario() {
        String idStr = JOptionPane.showInputDialog(vista, "Ingrese el ID del propietario a eliminar:");
        if (idStr != null && !idStr.trim().isEmpty()) {
            try {
                int id = Integer.parseInt(idStr);
                ArrayList<Propietario> propietarios = servicio.leer();
                boolean propietarioExiste = false;

                for (Propietario p : propietarios) {
                    if (p.getId_propietario() == id) {
                        propietarioExiste = true;
                        break;
                    }
                }

                if (propietarioExiste) {
                    int confirmacion = JOptionPane.showConfirmDialog(vista,
                            "¿Está seguro de eliminar este propietario?",
                            "Confirmar eliminación",
                            JOptionPane.YES_NO_OPTION);

                    if (confirmacion == JOptionPane.YES_OPTION) {
                        if (servicio.eliminar(id)) {
                            actualizarTabla();
                            JOptionPane.showMessageDialog(vista, "Propietario eliminado con éxito");
                        } else {
                            JOptionPane.showMessageDialog(vista, "No se pudo eliminar el propietario");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(vista, "No se encontró un propietario con el ID ingresado");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vista, "Por favor, ingrese un ID válido");
            }
        }
    }

    /**
     * Actualiza la tabla de propietarios con los datos más recientes.
     */
    public void actualizarTabla() {
        modeloTabla.setRowCount(0);
        ArrayList<Propietario> propietarios = servicio.leer();
        for (Propietario propietario : propietarios) {
            Object[] fila = {
                propietario.getId_propietario(),
                propietario.getCedula(),
                propietario.getNombre_completo(),
                propietario.getDireccion(),
                propietario.getFecha_nacimiento(),
                propietario.getFecha_expedicion_docu(),
                propietario.getCorreo_electronico(),
                propietario.getTelefono_1(),
                propietario.getTelefono_2()
            };
            modeloTabla.addRow(fila);
        }
    }

    /**
     * Maneja la acción de buscar propietarios por criterio.
     */
    private void manejarBuscarPropietario() {
        String criterio = vista.getTxtBuscar().getText();
        modeloTabla.setRowCount(0);
        ArrayList<Propietario> propietarios = servicio.buscarPorCriterio(criterio);
        if (propietarios.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "No se encontraron propietarios para el criterio ingresado.");
        }
        for (Propietario propietario : propietarios) {
            Object[] fila = {
                propietario.getId_propietario(),
                propietario.getCedula(),
                propietario.getNombre_completo(),
                propietario.getDireccion(),
                propietario.getFecha_nacimiento(),
                propietario.getFecha_expedicion_docu(),
                propietario.getCorreo_electronico(),
                propietario.getTelefono_1(),
                propietario.getTelefono_2()
            };
            modeloTabla.addRow(fila);
        }
    }
}