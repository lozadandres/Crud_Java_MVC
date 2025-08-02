package vista;

import modelo.Inmueble;
import modelo.Propietario;
import modelo.PropietarioServicio;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class FormularioInmueble extends JDialog {
    private final JTextField txtId;
    private final JTextField txtCodigo;
    private final JTextField txtDescripcion;
    private final JComboBox<String> comboTipo;
    private final JTextField txtModalidad;
    private final JTextField txtPrecioVenta;
    private final JTextField txtEstado;
    private final JTextField txtFoto;
    private final JTextField txtBanos;
    private final JTextField txtTamano;
    private final JTextField txtDireccion;
    private final JTextField txtDepartamento;
    private final JTextField txtCiudad;
    private final JComboBox<String> comboPropietario;
    private final JCheckBox chkInmobiliaria;
    private final JTextField txtFechaAdq;
    private final JTextField txtCostoAdq;
    private Inmueble inmueble;
    private ArrayList<Propietario> propietarios;

    public FormularioInmueble(Frame parent, boolean modal) {
        this(parent, modal, null);
    }

    public FormularioInmueble(Frame parent, boolean modal, Inmueble inmuebleExistente) {
        super(parent, modal);
        JOptionPane.showMessageDialog(this, "Antes de diligenciar el formulario, por favor tenga en cuenta:\n\n- Debe seleccionar un propietario o marcar como propiedad de la inmobiliaria.\n- No debe marcar ambas opciones al mismo tiempo.\n- Si la propiedad es de la inmobiliaria, no seleccione propietario.\n- Si la propiedad tiene propietario, no marque la casilla de inmobiliaria.", "Instrucciones para diligenciar", JOptionPane.INFORMATION_MESSAGE);
        setTitle(inmuebleExistente == null ? "Agregar Inmueble" : "Editar Inmueble");
        setSize(800, 700);
        setLocationRelativeTo(parent);
        setResizable(false);

        JPanel panel = new JPanel(new GridLayout(0, 4, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Fila 1
        panel.add(new JLabel("ID:"));
        txtId = new JTextField();
        txtId.setEditable(false);
        modelo.InmuebleServicio servicio = new modelo.InmuebleServicio();
        txtId.setText(String.valueOf(servicio.obtenerSiguienteId()));
        panel.add(txtId);
        panel.add(new JLabel("Código:"));
        txtCodigo = new JTextField();
        panel.add(txtCodigo);
        // Fila 2
        panel.add(new JLabel("Descripción:"));
        txtDescripcion = new JTextField();
        panel.add(txtDescripcion);
        panel.add(new JLabel("Tipo:"));
        comboTipo = new JComboBox<>(new String[]{"Casa", "Apartamento", "Finca", "Local Comercial"});
        panel.add(comboTipo);
        // Fila 3
        panel.add(new JLabel("Modalidad Comercialización:"));
        txtModalidad = new JTextField();
        panel.add(txtModalidad);
        panel.add(new JLabel("Precio Venta:"));
        txtPrecioVenta = new JTextField();
        panel.add(txtPrecioVenta);
        // Fila 4
        panel.add(new JLabel("Estado:"));
        txtEstado = new JTextField();
        panel.add(txtEstado);
        panel.add(new JLabel("Foto (URL):"));
        txtFoto = new JTextField();
        panel.add(txtFoto);
        // Fila 5
        panel.add(new JLabel("Cantidad Baños:"));
        txtBanos = new JTextField();
        panel.add(txtBanos);
        panel.add(new JLabel("Tamaño m2:"));
        txtTamano = new JTextField();
        panel.add(txtTamano);
        // Fila 6
        panel.add(new JLabel("Dirección:"));
        txtDireccion = new JTextField();
        panel.add(txtDireccion);
        panel.add(new JLabel("Departamento:"));
        txtDepartamento = new JTextField();
        panel.add(txtDepartamento);
        // Fila 7
        panel.add(new JLabel("Ciudad:"));
        txtCiudad = new JTextField();
        panel.add(txtCiudad);
        panel.add(new JLabel("Propietario (ID - Nombre):"));
        comboPropietario = new JComboBox<>();
        cargarPropietarios();
        panel.add(comboPropietario);
        // Fila 8
        panel.add(new JLabel("¿Propiedad de la inmobiliaria?"));
        chkInmobiliaria = new JCheckBox();
        panel.add(chkInmobiliaria);
        panel.add(new JLabel("Fecha Adquisición (yyyy-MM-dd):"));
        txtFechaAdq = new JTextField();
        panel.add(txtFechaAdq);
        // Fila 9
        panel.add(new JLabel("Costo Adquisición:"));
        txtCostoAdq = new JTextField();
        panel.add(txtCostoAdq);
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        // Fila 10 (botones)
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");
        panel.add(btnGuardar);
        panel.add(btnCancelar);

        add(panel);

        if (inmuebleExistente != null) {
            txtId.setText(String.valueOf(inmuebleExistente.getId_inmuebles()));
            txtCodigo.setText(inmuebleExistente.getCodigo_inmuebles());
            txtDescripcion.setText(inmuebleExistente.getDescripcion_inmueble());
            comboTipo.setSelectedItem(inmuebleExistente.getTipo_inmueble());
            txtModalidad.setText(inmuebleExistente.getModalidad_comercializacion());
            txtPrecioVenta.setText(inmuebleExistente.getPrecio_final_venta() != null ? inmuebleExistente.getPrecio_final_venta().toString() : "");
            txtEstado.setText(inmuebleExistente.getEstado());
            txtFoto.setText(inmuebleExistente.getFoto_de_inmueble());
            txtBanos.setText(String.valueOf(inmuebleExistente.getCantidad_banos()));
            txtTamano.setText(inmuebleExistente.getTamano_metros_cuadrados() != null ? inmuebleExistente.getTamano_metros_cuadrados().toString() : "");
            txtDireccion.setText(inmuebleExistente.getDireccion());
            txtDepartamento.setText(inmuebleExistente.getDepartamento());
            txtCiudad.setText(inmuebleExistente.getCiudad());
            if (inmuebleExistente.getId_propietario() != null) {
                for (int i = 0; i < comboPropietario.getItemCount(); i++) {
                    if (comboPropietario.getItemAt(i).startsWith(inmuebleExistente.getId_propietario() + " - ")) {
                        comboPropietario.setSelectedIndex(i);
                        break;
                    }
                }
            }
            chkInmobiliaria.setSelected(inmuebleExistente.isEs_propiedad_inmobiliaria());
            txtFechaAdq.setText(inmuebleExistente.getFecha_adquisicion());
            txtCostoAdq.setText(inmuebleExistente.getCosto_adquisicion() != null ? inmuebleExistente.getCosto_adquisicion().toString() : "");
        }

        btnGuardar.addActionListener((ActionEvent e) -> {
            if (validarCampos()) {
                inmueble = new Inmueble();
                inmueble.setId_inmuebles(Integer.parseInt(txtId.getText()));
                inmueble.setCodigo_inmuebles(txtCodigo.getText());
                inmueble.setDescripcion_inmueble(txtDescripcion.getText());
                inmueble.setTipo_inmueble((String) comboTipo.getSelectedItem());
                inmueble.setModalidad_comercializacion(txtModalidad.getText());
                inmueble.setPrecio_final_venta(!txtPrecioVenta.getText().isEmpty() ? Double.parseDouble(txtPrecioVenta.getText()) : null);
                inmueble.setEstado(txtEstado.getText());
                inmueble.setFoto_de_inmueble(txtFoto.getText());
                inmueble.setCantidad_banos(Integer.parseInt(txtBanos.getText()));
                inmueble.setTamano_metros_cuadrados(!txtTamano.getText().isEmpty() ? Double.parseDouble(txtTamano.getText()) : null);
                inmueble.setDireccion(txtDireccion.getText());
                inmueble.setDepartamento(txtDepartamento.getText());
                inmueble.setCiudad(txtCiudad.getText());
                if (chkInmobiliaria.isSelected()) {
                    inmueble.setId_propietario(null);
                    inmueble.setEs_propiedad_inmobiliaria(true);
                } else {
                    String seleccionado = (String) comboPropietario.getSelectedItem();
                    if (seleccionado != null && seleccionado.contains(" - ")) {
                        int idProp = Integer.parseInt(seleccionado.split(" - ")[0]);
                        inmueble.setId_propietario(idProp);
                    }
                    inmueble.setEs_propiedad_inmobiliaria(false);
                }
                inmueble.setFecha_adquisicion(txtFechaAdq.getText());
                inmueble.setCosto_adquisicion(!txtCostoAdq.getText().isEmpty() ? Double.parseDouble(txtCostoAdq.getText()) : null);
                dispose();
            }
        });

        btnCancelar.addActionListener((ActionEvent e) -> {
            inmueble = null;
            dispose();
        });
    }

    private void cargarPropietarios() {
        propietarios = new PropietarioServicio().leer();
        comboPropietario.addItem("Seleccione...");
        for (Propietario p : propietarios) {
            comboPropietario.addItem(p.getId_propietario() + " - " + p.getNombre_completo());
        }
    }

    private boolean validarCampos() {
        if (txtCodigo.getText().isEmpty() || txtDescripcion.getText().isEmpty() || comboTipo.getSelectedIndex() == -1 || txtModalidad.getText().isEmpty() || txtEstado.getText().isEmpty() || txtBanos.getText().isEmpty() || txtDireccion.getText().isEmpty() || txtDepartamento.getText().isEmpty() || txtCiudad.getText().isEmpty() || txtFechaAdq.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos obligatorios deben estar completos", "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (!txtFechaAdq.getText().matches("\\d{4}-\\d{2}-\\d{2}")) {
            JOptionPane.showMessageDialog(this, "La fecha de adquisición debe tener el formato yyyy-MM-dd", "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        try {
            if (!txtPrecioVenta.getText().isEmpty()) Double.parseDouble(txtPrecioVenta.getText());
            if (!txtTamano.getText().isEmpty()) Double.parseDouble(txtTamano.getText());
            if (!txtCostoAdq.getText().isEmpty()) Double.parseDouble(txtCostoAdq.getText());
            Integer.parseInt(txtBanos.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Verifique los campos numéricos (precio, tamaño, baños, costo)", "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (!chkInmobiliaria.isSelected() && (comboPropietario.getSelectedIndex() <= 0)) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un propietario o marcar como propiedad de la inmobiliaria", "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    public Inmueble getInmueble() {
        return inmueble;
    }
}