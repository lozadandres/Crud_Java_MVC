package modelo;

import java.util.ArrayList;

/**
 * Clase base genérica para operaciones CRUD sobre base de datos.
 * @param <T> Tipo de entidad
 */
public abstract class BaseServicio<T> {
    public BaseServicio() {}

    public abstract int obtenerSiguienteId();

    public abstract void crear(T entidad);

    public abstract ArrayList<T> leer();

    public abstract boolean actualizar(T entidad);

    public abstract boolean eliminar(int id);
}