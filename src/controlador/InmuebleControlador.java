package controlador;

import modelo.Inmueble;
import modelo.InmuebleServicio;
import vista.FormularioInmueble;
import vista.VentanaInmuebles;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class InmuebleControlador implements ActionListener {
    private final VentanaInmuebles vista;
    private final InmuebleServicio servicio;
    private final DefaultTableModel modeloTabla;

    public InmuebleControlador(VentanaInmuebles vista, DefaultTableModel modeloTabla) {
        this.vista = vista;
        this.servicio = new InmuebleServicio();
        this.modeloTabla = modeloTabla;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        switch (comando) {
            case "Agregar Inmueble" -> manejarAgregarInmueble();
            case "Editar Inmueble" -> manejarEditarInmueble();
            case "Eliminar Inmueble" -> manejarEliminarInmueble();
            case "Buscar" -> manejarBuscarInmueble();
        }
    }

    private void manejarAgregarInmueble() {
        FormularioInmueble formulario = new FormularioInmueble(vista, true);
        formulario.setVisible(true);
        if (formulario.getInmueble() != null) {
            servicio.crear(formulario.getInmueble());
            actualizarTabla();
        }
    }

    private void manejarEditarInmueble() {
        String idStr = JOptionPane.showInputDialog(vista, "Ingrese el ID del inmueble a editar:");
        if (idStr != null && !idStr.trim().isEmpty()) {
            try {
                int id = Integer.parseInt(idStr);
                ArrayList<Inmueble> inmuebles = servicio.leer();
                Inmueble inmuebleEncontrado = null;
                for (Inmueble i : inmuebles) {
                    if (i.getId_inmuebles() == id) {
                        inmuebleEncontrado = i;
                        break;
                    }
                }
                if (inmuebleEncontrado != null) {
                    FormularioInmueble formulario = new FormularioInmueble(vista, true, inmuebleEncontrado);
                    formulario.setVisible(true);
                    if (formulario.getInmueble() != null) {
                        servicio.actualizar(formulario.getInmueble());
                        actualizarTabla();
                    }
                } else {
                    JOptionPane.showMessageDialog(vista, "No se encontró un inmueble con el ID ingresado");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vista, "Por favor, ingrese un ID válido");
            }
        }
    }

    private void manejarEliminarInmueble() {
        String idStr = JOptionPane.showInputDialog(vista, "Ingrese el ID del inmueble a eliminar:");
        if (idStr != null && !idStr.trim().isEmpty()) {
            try {
                int id = Integer.parseInt(idStr);
                ArrayList<Inmueble> inmuebles = servicio.leer();
                boolean inmuebleExiste = false;
                for (Inmueble i : inmuebles) {
                    if (i.getId_inmuebles() == id) {
                        inmuebleExiste = true;
                        break;
                    }
                }
                if (inmuebleExiste) {
                    int confirmacion = JOptionPane.showConfirmDialog(vista,
                            "¿Está seguro de eliminar este inmueble?",
                            "Confirmar eliminación",
                            JOptionPane.YES_NO_OPTION);
                    if (confirmacion == JOptionPane.YES_OPTION) {
                        if (servicio.eliminar(id)) {
                            actualizarTabla();
                            JOptionPane.showMessageDialog(vista, "Inmueble eliminado con éxito");
                        } else {
                            JOptionPane.showMessageDialog(vista, "No se pudo eliminar el inmueble");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(vista, "No se encontró un inmueble con el ID ingresado");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vista, "Por favor, ingrese un ID válido");
            }
        }
    }

    public void actualizarTabla() {
        modeloTabla.setRowCount(0);
        ArrayList<Inmueble> inmuebles = servicio.leer();
        for (Inmueble inmueble : inmuebles) {
            Object[] fila = {
                inmueble.getId_inmuebles(),
                inmueble.getCodigo_inmuebles(),
                inmueble.getDescripcion_inmueble(),
                inmueble.getTipo_inmueble(),
                inmueble.getModalidad_comercializacion(),
                inmueble.getPrecio_final_venta(),
                inmueble.getEstado(),
                inmueble.getCantidad_banos(),
                inmueble.getTamano_metros_cuadrados(),
                inmueble.getDireccion(),
                inmueble.getDepartamento(),
                inmueble.getCiudad(),
                inmueble.getId_propietario(),
                inmueble.isEs_propiedad_inmobiliaria() ? "Sí" : "No",
                inmueble.getFecha_adquisicion(),
                inmueble.getCosto_adquisicion()
            };
            modeloTabla.addRow(fila);
        }
    }

    private void manejarBuscarInmueble() {
        String criterio = vista.getTxtBuscar().getText();
        modeloTabla.setRowCount(0);
        ArrayList<Inmueble> inmuebles = servicio.buscarPorCriterio(criterio);
        if (inmuebles.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "No se encontraron inmuebles para el criterio ingresado.");
        }
        for (Inmueble inmueble : inmuebles) {
            Object[] fila = {
                inmueble.getId_inmuebles(),
                inmueble.getCodigo_inmuebles(),
                inmueble.getDescripcion_inmueble(),
                inmueble.getTipo_inmueble(),
                inmueble.getModalidad_comercializacion(),
                inmueble.getPrecio_final_venta(),
                inmueble.getEstado(),
                inmueble.getCantidad_banos(),
                inmueble.getTamano_metros_cuadrados(),
                inmueble.getDireccion(),
                inmueble.getDepartamento(),
                inmueble.getCiudad(),
                inmueble.getId_propietario(),
                inmueble.isEs_propiedad_inmobiliaria() ? "Sí" : "No",
                inmueble.getFecha_adquisicion(),
                inmueble.getCosto_adquisicion()
            };
            modeloTabla.addRow(fila);
        }
    }
}