package modelo;

/**
 * Clase que representa la entidad Propietario en el sistema.
 * Hereda de la clase abstracta Persona.
 */
public class Propietario extends Persona {
    /** ID único del propietario */
    private int id_propietario;

    /**
     * Constructor por defecto de la clase Propietario.
     */
    public Propietario() {
        super();
    }

    public int getId_propietario() {
        return id_propietario;
    }

    public void setId_propietario(int id_propietario) {
        this.id_propietario = id_propietario;
    }

    @Override
    public String toString() {
        return "Propietario{" + super.toString() +
                ", id_propietario=" + id_propietario +
                "}";
    }
}