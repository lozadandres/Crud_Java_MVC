package modelo;

import java.util.ArrayList;

public interface ICrudServicio<T> {
    int obtenerSiguienteId();
    void crear(T entidad);
    ArrayList<T> leer();
    boolean actualizar(T entidad);
    boolean eliminar(int id);
}