package controlador;

import modelo.AgenteComercial;
import modelo.AgenteComercialServicio;
import vista.FormularioAgenteComercial;
import vista.VentanaAgentesComerciales;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Controlador que maneja las operaciones y eventos de la ventana de agentes comerciales.
 */
public class AgenteComercialControlador implements ActionListener {
    /** Referencia a la ventana principal de agentes comerciales */
    private final VentanaAgentesComerciales vista;
    /** Servicio que maneja las operaciones con agentes comerciales */
    private final AgenteComercialServicio servicio;
    /** Modelo de la tabla que muestra los agentes */
    private final DefaultTableModel modeloTabla;

    /**
     * Constructor del controlador.
     * @param vista ventana principal de agentes comerciales
     * @param modeloTabla modelo de datos de la tabla de agentes
     */
    public AgenteComercialControlador(VentanaAgentesComerciales vista, DefaultTableModel modeloTabla) {
        this.vista = vista;
        this.servicio = new AgenteComercialServicio();
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
            case "Agregar Agente" -> manejarAgregarAgente();
            case "Editar Agente" -> manejarEditarAgente();
            case "Eliminar Agente" -> manejarEliminarAgente();
            case "Buscar" -> manejarBuscarAgente();
        }
    }

    /**
     * Maneja la acción de agregar un nuevo agente comercial.
     * Muestra el formulario de agente y procesa los datos ingresados.
     */
    private void manejarAgregarAgente() {
        FormularioAgenteComercial formulario = new FormularioAgenteComercial(vista, true);
        formulario.setVisible(true);
        if (formulario.getAgente() != null) {
            servicio.crear(formulario.getAgente());
            actualizarTabla();
        }
    }

    /**
     * Maneja la acción de editar un agente existente.
     * Solicita el ID del agente y muestra el formulario con sus datos.
     */
    private void manejarEditarAgente() {
        String idStr = JOptionPane.showInputDialog(vista, "Ingrese el ID del agente a editar:");
        if (idStr != null && !idStr.trim().isEmpty()) {
            try {
                int id = Integer.parseInt(idStr);
                ArrayList<AgenteComercial> agentes = servicio.leer();
                AgenteComercial agenteEncontrado = null;

                for (AgenteComercial a : agentes) {
                    if (a.getId_agente_comerciales() == id) {
                        agenteEncontrado = a;
                        break;
                    }
                }

                if (agenteEncontrado != null) {
                    FormularioAgenteComercial formulario = new FormularioAgenteComercial(vista, true, agenteEncontrado);
                    formulario.setVisible(true);

                    if (formulario.getAgente() != null) {
                        servicio.actualizar(formulario.getAgente());
                        actualizarTabla();
                    }
                } else {
                    JOptionPane.showMessageDialog(vista, "No se encontró un agente con el ID ingresado");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vista, "Por favor, ingrese un ID válido");
            }
        }
    }

    /**
     * Maneja la acción de eliminar un agente.
     * Solicita el ID del agente y confirma la eliminación.
     */
    private void manejarEliminarAgente() {
        String idStr = JOptionPane.showInputDialog(vista, "Ingrese el ID del agente a eliminar:");
        if (idStr != null && !idStr.trim().isEmpty()) {
            try {
                int id = Integer.parseInt(idStr);
                ArrayList<AgenteComercial> agentes = servicio.leer();
                boolean agenteExiste = false;

                for (AgenteComercial a : agentes) {
                    if (a.getId_agente_comerciales() == id) {
                        agenteExiste = true;
                        break;
                    }
                }

                if (agenteExiste) {
                    int confirmacion = JOptionPane.showConfirmDialog(vista,
                            "¿Está seguro de eliminar este agente?",
                            "Confirmar eliminación",
                            JOptionPane.YES_NO_OPTION);

                    if (confirmacion == JOptionPane.YES_OPTION) {
                        if (servicio.eliminar(id)) {
                            actualizarTabla();
                            JOptionPane.showMessageDialog(vista, "Agente eliminado con éxito");
                        } else {
                            JOptionPane.showMessageDialog(vista, "No se pudo eliminar el agente");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(vista, "No se encontró un agente con el ID ingresado");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vista, "Por favor, ingrese un ID válido");
            }
        }
    }

    /**
     * Actualiza la tabla de agentes con los datos más recientes.
     */
    public void actualizarTabla() {
        modeloTabla.setRowCount(0);
        ArrayList<AgenteComercial> agentes = servicio.leer();
        for (AgenteComercial agente : agentes) {
            Object[] fila = {
                agente.getId_agente_comerciales(),
                agente.getCedula(),
                agente.getLoguin(),
                agente.getNombre_completo(),
                agente.getCorreo_electronico(),
                agente.getCelular()
            };
            modeloTabla.addRow(fila);
        }
    }

    /**
     * Maneja la acción de buscar agentes comerciales por criterio.
     */
    private void manejarBuscarAgente() {
        String criterio = vista.getTxtBuscar().getText();
        modeloTabla.setRowCount(0);
        ArrayList<AgenteComercial> agentes = servicio.buscarPorCriterio(criterio);
        if (agentes.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "No se encontraron agentes para el criterio ingresado.");
        }
        for (AgenteComercial agente : agentes) {
            Object[] fila = {
                agente.getId_agente_comerciales(),
                agente.getCedula(),
                agente.getLoguin(),
                agente.getNombre_completo(),
                agente.getCorreo_electronico(),
                agente.getCelular()
            };
            modeloTabla.addRow(fila);
        }
    }
}