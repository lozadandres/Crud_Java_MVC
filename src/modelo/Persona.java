package modelo;

/**
 * Clase abstracta que representa una persona en el sistema.
 * Contiene los atributos y métodos comunes para Cliente, AgenteComercial y Propietario.
 */
public abstract class Persona {
    /** ID único de la persona */
    protected int id;
    /** Número de cédula que identifica a la persona */
    protected int cedula;
    /** Nombre completo de la persona */
    protected String nombre_completo;
    /** Dirección de la persona */
    protected String direccion;
    /** Fecha de nacimiento de la persona */
    protected String fecha_nacimiento;
    /** Fecha de expedición del documento */
    protected String fecha_expedicion_docu;
    /** Correo electrónico de la persona */
    protected String correo_electronico;
    /** Primer número de teléfono */
    protected String telefono_1;
    /** Segundo número de teléfono */
    protected String telefono_2;

    /**
     * Constructor por defecto de la clase Persona.
     */
    public Persona() {
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getFecha_expedicion_docu() {
        return fecha_expedicion_docu;
    }

    public void setFecha_expedicion_docu(String fecha_expedicion_docu) {
        this.fecha_expedicion_docu = fecha_expedicion_docu;
    }

    public String getCorreo_electronico() {
        return correo_electronico;
    }

    public void setCorreo_electronico(String correo_electronico) {
        this.correo_electronico = correo_electronico;
    }

    public String getTelefono_1() {
        return telefono_1;
    }

    public void setTelefono_1(String telefono_1) {
        this.telefono_1 = telefono_1;
    }

    public String getTelefono_2() {
        return telefono_2;
    }

    public void setTelefono_2(String telefono_2) {
        this.telefono_2 = telefono_2;
    }

    /**
     * Retorna una representación en cadena de la persona.
     * @return cadena con los datos de la persona
     */
    @Override
    public String toString() {
        return "Persona{" +
                "id=" + id +
                ", cedula=" + cedula +
                ", nombre_completo='" + nombre_completo + '\'' +
                ", direccion='" + direccion + '\'' +
                ", fecha_nacimiento='" + fecha_nacimiento + '\'' +
                ", fecha_expedicion_docu='" + fecha_expedicion_docu + '\'' +
                ", correo_electronico='" + correo_electronico + '\'' +
                ", telefono_1='" + telefono_1 + '\'' +
                ", telefono_2='" + telefono_2 + '\'' +
                '}';
    }
}