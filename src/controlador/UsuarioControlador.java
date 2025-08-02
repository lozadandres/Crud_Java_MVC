package controlador;

import modelo.Usuario;
import modelo.UsuarioServicio;
import vista.FormularioUsuario;
import vista.VentanaUsuarios;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class UsuarioControlador implements ActionListener {
    private final VentanaUsuarios vista;
    private final UsuarioServicio servicio;
    private final DefaultTableModel modeloTabla;

    public UsuarioControlador(VentanaUsuarios vista, DefaultTableModel modeloTabla) {
        this.vista = vista;
        this.servicio = new UsuarioServicio();
        this.modeloTabla = modeloTabla;
        actualizarTabla();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        switch (comando) {
            case "AGREGAR":
                manejarAgregarUsuario();
                break;
            case "EDITAR":
                manejarEditarUsuario();
                break;
            case "ELIMINAR":
                manejarEliminarUsuario();
                break;
        }
    }

    private void manejarAgregarUsuario() {
        FormularioUsuario formulario = new FormularioUsuario(vista, true);
        formulario.setVisible(true);
        if (formulario.getUsuario() != null) {
            servicio.crear(formulario.getUsuario());
            actualizarTabla();
        }
    }

    private void manejarEditarUsuario() {
        int filaSeleccionada = vista.getTablaUsuarios().getSelectedRow();
        if (filaSeleccionada != -1) {
            int idUsuario = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
            ArrayList<Usuario> usuarios = servicio.leer();
            Usuario usuarioSeleccionado = null;

            for (Usuario u : usuarios) {
                if (u.getId_usuario() == idUsuario) {
                    usuarioSeleccionado = u;
                    break;
                }
            }

            if (usuarioSeleccionado != null) {
                FormularioUsuario formulario = new FormularioUsuario(vista, true, usuarioSeleccionado);
                formulario.setVisible(true);
                if (formulario.getUsuario() != null) {
                    servicio.actualizar(formulario.getUsuario());
                    actualizarTabla();
                }
            }
        } else {
            JOptionPane.showMessageDialog(vista, "Por favor, seleccione un usuario para editar",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void manejarEliminarUsuario() {
        int filaSeleccionada = vista.getTablaUsuarios().getSelectedRow();
        if (filaSeleccionada != -1) {
            int idUsuario = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
            int confirmacion = JOptionPane.showConfirmDialog(vista,
                    "¿Está seguro de que desea eliminar este usuario?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                servicio.eliminar(idUsuario);
                actualizarTabla();
            }
        } else {
            JOptionPane.showMessageDialog(vista, "Por favor, seleccione un usuario para eliminar",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarTabla() {
        // Limpiar la tabla
        modeloTabla.setRowCount(0);

        // Obtener la lista actualizada de usuarios
        ArrayList<Usuario> usuarios = servicio.leer();

        // Llenar la tabla con los datos
        for (Usuario usuario : usuarios) {
            Object[] fila = {
                usuario.getId_usuario(),
                usuario.getLoguin(),
                usuario.getTipo_usuario(),
                usuario.getId_agente_comerciales()
            };
            modeloTabla.addRow(fila);
        }
    }
}