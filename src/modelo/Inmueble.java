package modelo;

/**
 * Clase que representa la entidad Inmueble en el sistema.
 */
public class Inmueble {
    private int id_inmuebles;
    private String codigo_inmuebles;
    private String descripcion_inmueble;
    private String tipo_inmueble;
    private String modalidad_comercializacion;
    private Double precio_final_venta;
    private String estado;
    private String foto_de_inmueble;
    private int cantidad_banos;
    private Double tamano_metros_cuadrados;
    private String direccion;
    private String departamento;
    private String ciudad;
    private Integer id_propietario; // Puede ser null si es de la inmobiliaria
    private boolean es_propiedad_inmobiliaria;
    private String fecha_adquisicion;
    private Double costo_adquisicion;

    public Inmueble() {}

    // Getters y setters
    public int getId_inmuebles() { return id_inmuebles; }
    public void setId_inmuebles(int id_inmuebles) { this.id_inmuebles = id_inmuebles; }
    public String getCodigo_inmuebles() { return codigo_inmuebles; }
    public void setCodigo_inmuebles(String codigo_inmuebles) { this.codigo_inmuebles = codigo_inmuebles; }
    public String getDescripcion_inmueble() { return descripcion_inmueble; }
    public void setDescripcion_inmueble(String descripcion_inmueble) { this.descripcion_inmueble = descripcion_inmueble; }
    public String getTipo_inmueble() { return tipo_inmueble; }
    public void setTipo_inmueble(String tipo_inmueble) { this.tipo_inmueble = tipo_inmueble; }
    public String getModalidad_comercializacion() { return modalidad_comercializacion; }
    public void setModalidad_comercializacion(String modalidad_comercializacion) { this.modalidad_comercializacion = modalidad_comercializacion; }
    public Double getPrecio_final_venta() { return precio_final_venta; }
    public void setPrecio_final_venta(Double precio_final_venta) { this.precio_final_venta = precio_final_venta; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getFoto_de_inmueble() { return foto_de_inmueble; }
    public void setFoto_de_inmueble(String foto_de_inmueble) { this.foto_de_inmueble = foto_de_inmueble; }
    public int getCantidad_banos() { return cantidad_banos; }
    public void setCantidad_banos(int cantidad_banos) { this.cantidad_banos = cantidad_banos; }
    public Double getTamano_metros_cuadrados() { return tamano_metros_cuadrados; }
    public void setTamano_metros_cuadrados(Double tamano_metros_cuadrados) { this.tamano_metros_cuadrados = tamano_metros_cuadrados; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }
    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
    public Integer getId_propietario() { return id_propietario; }
    public void setId_propietario(Integer id_propietario) { this.id_propietario = id_propietario; }
    public boolean isEs_propiedad_inmobiliaria() { return es_propiedad_inmobiliaria; }
    public void setEs_propiedad_inmobiliaria(boolean es_propiedad_inmobiliaria) { this.es_propiedad_inmobiliaria = es_propiedad_inmobiliaria; }
    public String getFecha_adquisicion() { return fecha_adquisicion; }
    public void setFecha_adquisicion(String fecha_adquisicion) { this.fecha_adquisicion = fecha_adquisicion; }
    public Double getCosto_adquisicion() { return costo_adquisicion; }
    public void setCosto_adquisicion(Double costo_adquisicion) { this.costo_adquisicion = costo_adquisicion; }

    @Override
    public String toString() {
        return "Inmueble{" +
                "id_inmuebles=" + id_inmuebles +
                ", codigo_inmuebles='" + codigo_inmuebles + '\'' +
                ", descripcion_inmueble='" + descripcion_inmueble + '\'' +
                ", tipo_inmueble='" + tipo_inmueble + '\'' +
                ", modalidad_comercializacion='" + modalidad_comercializacion + '\'' +
                ", precio_final_venta=" + precio_final_venta +
                ", estado='" + estado + '\'' +
                ", foto_de_inmueble='" + foto_de_inmueble + '\'' +
                ", cantidad_banos=" + cantidad_banos +
                ", tamano_metros_cuadrados=" + tamano_metros_cuadrados +
                ", direccion='" + direccion + '\'' +
                ", departamento='" + departamento + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", id_propietario=" + id_propietario +
                ", es_propiedad_inmobiliaria=" + es_propiedad_inmobiliaria +
                ", fecha_adquisicion='" + fecha_adquisicion + '\'' +
                ", costo_adquisicion=" + costo_adquisicion +
                '}';
    }
}