package modelo;

/**
 * Clase que representa la entidad Agente Comercial en el sistema.
 * Hereda de la clase abstracta Persona.
 */
public class AgenteComercial extends Persona {
    /** ID único del agente comercial */
    private int id_agente_comerciales;

    /** Nombre de usuario para el sistema */
    private String loguin;
    /** Contraseña del agente */
    private String contrasena;
    /** Número de celular del agente */
    private String celular;

    /**
     * Constructor por defecto de la clase AgenteComercial.
     */
    public AgenteComercial() {
        super();
    }

    public String getLoguin() {
        return loguin;
    }

    public void setLoguin(String loguin) {
        this.loguin = loguin;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    /**
     * Retorna una representación en cadena del agente comercial.
     * @return cadena con los datos del agente comercial
     */
    public int getId_agente_comerciales() {
        return id_agente_comerciales;
    }

    public void setId_agente_comerciales(int id_agente_comerciales) {
        this.id_agente_comerciales = id_agente_comerciales;
    }

    @Override
    public String toString() {
        return "AgenteComercial{" + super.toString() +
                ", id_agente_comerciales=" + id_agente_comerciales +
                ", loguin='" + loguin + '\'' +
                ", contrasena='" + contrasena + '\'' +
                ", celular='" + celular + '\'' +
                '}';
    }
}