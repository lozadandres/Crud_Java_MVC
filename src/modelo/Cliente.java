package modelo;

/**
 * Clase que representa la entidad Cliente en el sistema.
 * Hereda de la clase abstracta Persona.
 */
public class Cliente extends Persona {
    /** ID único del cliente */
    private int id_cliente;

    /**
     * Constructor por defecto de la clase Cliente.
     */
    public Cliente() {
        super();
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    @Override
    public String toString() {
        return "Cliente{" + super.toString() +
                ", id_cliente=" + id_cliente +
                "}";
    }
}