package modelo;

import java.sql.*;
import java.util.ArrayList;

public class InmuebleServicio extends BaseServicio<Inmueble> implements ICrudServicio<Inmueble> {
    private final PostgresConexion conexionDB = new PostgresConexion();

    @Override
    public int obtenerSiguienteId() {
        int id = 1;
        Connection conn = conexionDB.conectar();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT MAX(id_inmuebles) FROM inmuebles");
            if (rs.next()) {
                id = rs.getInt(1) + 1;
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error al obtener siguiente ID: " + e.getMessage());
        } finally {
            conexionDB.cerrar();
        }
        return id;
    }

    @Override
    public void crear(Inmueble inmueble) {
        Connection conn = conexionDB.conectar();
        String sql = "INSERT INTO inmuebles (id_inmuebles, codigo_inmuebles, descripcion_inmueble, tipo_inmueble, modalidad_comercializacion, precio_final_venta, estado, foto_de_inmueble, cantidad_baños, tamaño_metros_cuadrados, direccion, departamento, ciudad, id_propietario, es_propiedad_inmobiliaria, fecha_adquisicion, costo_adquisicion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            inmueble.setId_inmuebles(obtenerSiguienteId());
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, inmueble.getId_inmuebles());
            ps.setString(2, inmueble.getCodigo_inmuebles());
            ps.setString(3, inmueble.getDescripcion_inmueble());
            ps.setString(4, inmueble.getTipo_inmueble());
            ps.setString(5, inmueble.getModalidad_comercializacion());
            if (inmueble.getPrecio_final_venta() != null) {
                ps.setDouble(6, inmueble.getPrecio_final_venta());
            } else {
                ps.setNull(6, Types.DOUBLE);
            }
            ps.setString(7, inmueble.getEstado());
            ps.setString(8, inmueble.getFoto_de_inmueble());
            ps.setInt(9, inmueble.getCantidad_banos());
            if (inmueble.getTamano_metros_cuadrados() != null) {
                ps.setDouble(10, inmueble.getTamano_metros_cuadrados());
            } else {
                ps.setNull(10, Types.DOUBLE);
            }
            ps.setString(11, inmueble.getDireccion());
            ps.setString(12, inmueble.getDepartamento());
            ps.setString(13, inmueble.getCiudad());
            if (inmueble.getId_propietario() != null) {
                ps.setInt(14, inmueble.getId_propietario());
            } else {
                ps.setNull(14, Types.INTEGER);
            }
            ps.setBoolean(15, inmueble.isEs_propiedad_inmobiliaria());
            // Validación y conversión de fecha_adquisicion
            if (inmueble.getFecha_adquisicion() == null || inmueble.getFecha_adquisicion().isEmpty()) {
                throw new IllegalArgumentException("La fecha de adquisición no puede estar vacía");
            }
            if (!inmueble.getFecha_adquisicion().matches("\\d{4}-\\d{2}-\\d{2}")) {
                throw new IllegalArgumentException("La fecha de adquisición debe tener el formato yyyy-MM-dd");
            }
            ps.setDate(16, java.sql.Date.valueOf(inmueble.getFecha_adquisicion()));
            if (inmueble.getCosto_adquisicion() != null) {
                ps.setDouble(17, inmueble.getCosto_adquisicion());
            } else {
                ps.setNull(17, Types.DOUBLE);
            }
            ps.executeUpdate();
            ps.close();
            System.out.println("Inmueble guardado con éxito en PostgreSQL");
        } catch (IllegalArgumentException e) {
            System.out.println("Error de validación: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error al guardar el inmueble: " + e.getMessage());
        } finally {
            conexionDB.cerrar();
        }
    }

    @Override
    public ArrayList<Inmueble> leer() {
        ArrayList<Inmueble> listaInmuebles = new ArrayList<>();
        Connection conn = conexionDB.conectar();
        String sql = "SELECT * FROM inmuebles ORDER BY id_inmuebles";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Inmueble inmueble = new Inmueble();
                inmueble.setId_inmuebles(rs.getInt("id_inmuebles"));
                inmueble.setCodigo_inmuebles(rs.getString("codigo_inmuebles"));
                inmueble.setDescripcion_inmueble(rs.getString("descripcion_inmueble"));
                inmueble.setTipo_inmueble(rs.getString("tipo_inmueble"));
                inmueble.setModalidad_comercializacion(rs.getString("modalidad_comercializacion"));
                inmueble.setPrecio_final_venta(rs.getDouble("precio_final_venta"));
                inmueble.setEstado(rs.getString("estado"));
                inmueble.setFoto_de_inmueble(rs.getString("foto_de_inmueble"));
                inmueble.setCantidad_banos(rs.getInt("cantidad_baños"));
                inmueble.setTamano_metros_cuadrados(rs.getDouble("tamaño_metros_cuadrados"));
                inmueble.setDireccion(rs.getString("direccion"));
                inmueble.setDepartamento(rs.getString("departamento"));
                inmueble.setCiudad(rs.getString("ciudad"));
                int idProp = rs.getInt("id_propietario");
                if (rs.wasNull()) inmueble.setId_propietario(null); else inmueble.setId_propietario(idProp);
                inmueble.setEs_propiedad_inmobiliaria(rs.getBoolean("es_propiedad_inmobiliaria"));
                inmueble.setFecha_adquisicion(rs.getString("fecha_adquisicion"));
                inmueble.setCosto_adquisicion(rs.getDouble("costo_adquisicion"));
                listaInmuebles.add(inmueble);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error al leer inmuebles: " + e.getMessage());
        } finally {
            conexionDB.cerrar();
        }
        return listaInmuebles;
    }

    @Override
    public boolean actualizar(Inmueble inmueble) {
        boolean actualizado = false;
        Connection conn = conexionDB.conectar();
        String sql = "UPDATE inmuebles SET codigo_inmuebles=?, descripcion_inmueble=?, tipo_inmueble=?, modalidad_comercializacion=?, precio_final_venta=?, estado=?, foto_de_inmueble=?, cantidad_baños=?, tamaño_metros_cuadrados=?, direccion=?, departamento=?, ciudad=?, id_propietario=?, es_propiedad_inmobiliaria=?, fecha_adquisicion=?, costo_adquisicion=? WHERE id_inmuebles=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, inmueble.getCodigo_inmuebles());
            ps.setString(2, inmueble.getDescripcion_inmueble());
            ps.setString(3, inmueble.getTipo_inmueble());
            ps.setString(4, inmueble.getModalidad_comercializacion());
            if (inmueble.getPrecio_final_venta() != null) {
                ps.setDouble(5, inmueble.getPrecio_final_venta());
            } else {
                ps.setNull(5, Types.DOUBLE);
            }
            ps.setString(6, inmueble.getEstado());
            ps.setString(7, inmueble.getFoto_de_inmueble());
            ps.setInt(8, inmueble.getCantidad_banos());
            if (inmueble.getTamano_metros_cuadrados() != null) {
                ps.setDouble(9, inmueble.getTamano_metros_cuadrados());
            } else {
                ps.setNull(9, Types.DOUBLE);
            }
            ps.setString(10, inmueble.getDireccion());
            ps.setString(11, inmueble.getDepartamento());
            ps.setString(12, inmueble.getCiudad());
            if (inmueble.getId_propietario() != null) {
                ps.setInt(13, inmueble.getId_propietario());
            } else {
                ps.setNull(13, Types.INTEGER);
            }
            ps.setBoolean(14, inmueble.isEs_propiedad_inmobiliaria());
            // Validación y conversión de fecha_adquisicion
            if (inmueble.getFecha_adquisicion() == null || inmueble.getFecha_adquisicion().isEmpty()) {
                throw new IllegalArgumentException("La fecha de adquisición no puede estar vacía");
            }
            if (!inmueble.getFecha_adquisicion().matches("\\d{4}-\\d{2}-\\d{2}")) {
                throw new IllegalArgumentException("La fecha de adquisición debe tener el formato yyyy-MM-dd");
            }
            ps.setDate(15, java.sql.Date.valueOf(inmueble.getFecha_adquisicion()));
            if (inmueble.getCosto_adquisicion() != null) {
                ps.setDouble(16, inmueble.getCosto_adquisicion());
            } else {
                ps.setNull(16, Types.DOUBLE);
            }
            ps.setInt(17, inmueble.getId_inmuebles());
            actualizado = ps.executeUpdate() > 0;
            ps.close();
            if (actualizado) {
                System.out.println("Inmueble actualizado con éxito en PostgreSQL");
            } else {
                System.out.println("Inmueble no encontrado para actualizar");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error de validación: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error al actualizar el inmueble: " + e.getMessage());
        } finally {
            conexionDB.cerrar();
        }
        return actualizado;
    }

    @Override
    public boolean eliminar(int id) {
        boolean eliminado = false;
        Connection conn = conexionDB.conectar();
        String sql = "DELETE FROM inmuebles WHERE id_inmuebles=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            eliminado = ps.executeUpdate() > 0;
            ps.close();
            if (eliminado) {
                System.out.println("Inmueble eliminado con éxito en PostgreSQL");
            } else {
                System.out.println("Inmueble no encontrado para eliminar");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar el inmueble: " + e.getMessage());
        } finally {
            conexionDB.cerrar();
        }
        return eliminado;
    }

    public ArrayList<Inmueble> buscarPorCriterio(String criterio) {
        ArrayList<Inmueble> listaInmuebles = new ArrayList<>();
        Connection conn = conexionDB.conectar();
        String sql = "SELECT * FROM inmuebles WHERE CAST(id_inmuebles AS TEXT) LIKE ? OR LOWER(descripcion_inmueble) LIKE ? OR LOWER(tipo_inmueble) LIKE ? ORDER BY id_inmuebles";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            String likeCriterio = "%" + criterio.toLowerCase() + "%";
            ps.setString(1, likeCriterio);
            ps.setString(2, likeCriterio);
            ps.setString(3, likeCriterio);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Inmueble inmueble = new Inmueble();
                inmueble.setId_inmuebles(rs.getInt("id_inmuebles"));
                inmueble.setCodigo_inmuebles(rs.getString("codigo_inmuebles"));
                inmueble.setDescripcion_inmueble(rs.getString("descripcion_inmueble"));
                inmueble.setTipo_inmueble(rs.getString("tipo_inmueble"));
                inmueble.setModalidad_comercializacion(rs.getString("modalidad_comercializacion"));
                inmueble.setPrecio_final_venta(rs.getDouble("precio_final_venta"));
                inmueble.setEstado(rs.getString("estado"));
                inmueble.setFoto_de_inmueble(rs.getString("foto_de_inmueble"));
                inmueble.setCantidad_banos(rs.getInt("cantidad_baños"));
                inmueble.setTamano_metros_cuadrados(rs.getDouble("tamaño_metros_cuadrados"));
                inmueble.setDireccion(rs.getString("direccion"));
                inmueble.setDepartamento(rs.getString("departamento"));
                inmueble.setCiudad(rs.getString("ciudad"));
                int idProp = rs.getInt("id_propietario");
                if (rs.wasNull()) inmueble.setId_propietario(null); else inmueble.setId_propietario(idProp);
                inmueble.setEs_propiedad_inmobiliaria(rs.getBoolean("es_propiedad_inmobiliaria"));
                inmueble.setFecha_adquisicion(rs.getString("fecha_adquisicion"));
                inmueble.setCosto_adquisicion(rs.getDouble("costo_adquisicion"));
                listaInmuebles.add(inmueble);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al buscar inmuebles: " + e.getMessage());
        } finally {
            conexionDB.cerrar();
        }
        return listaInmuebles;
    }
}