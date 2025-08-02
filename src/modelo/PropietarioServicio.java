package modelo;

import java.sql.*;
import java.util.ArrayList;

public class PropietarioServicio extends BaseServicio<Propietario> implements ICrudServicio<Propietario> {
    private final PostgresConexion conexionDB = new PostgresConexion();

    @Override
    public int obtenerSiguienteId() {
        int id = 1;
        Connection conn = conexionDB.conectar();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT MAX(id_propietario) FROM propietario");
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
    public void crear(Propietario propietario) {
        Connection conn = conexionDB.conectar();
        String sql = "INSERT INTO propietario (id_propietario, cedula, nombre_completo, direccion, fecha_nacimiento, fecha_expedicion_documento, correo_electronico, telefono_1, telefono_2) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            propietario.setId_propietario(obtenerSiguienteId());
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, propietario.getId_propietario());
            ps.setInt(2, propietario.getCedula());
            ps.setString(3, propietario.getNombre_completo());
            ps.setString(4, propietario.getDireccion());
            // Validación de fecha de nacimiento
            if (propietario.getFecha_nacimiento() == null || propietario.getFecha_nacimiento().isEmpty()) {
                throw new IllegalArgumentException("La fecha de nacimiento no puede estar vacía");
            }
            if (!propietario.getFecha_nacimiento().matches("\\d{4}-\\d{2}-\\d{2}")) {
                throw new IllegalArgumentException("La fecha de nacimiento debe tener el formato yyyy-MM-dd");
            }
            ps.setDate(5, java.sql.Date.valueOf(propietario.getFecha_nacimiento()));
            // Validación de fecha de expedición
            if (propietario.getFecha_expedicion_docu() == null || propietario.getFecha_expedicion_docu().isEmpty()) {
                throw new IllegalArgumentException("La fecha de expedición no puede estar vacía");
            }
            if (!propietario.getFecha_expedicion_docu().matches("\\d{4}-\\d{2}-\\d{2}")) {
                throw new IllegalArgumentException("La fecha de expedición debe tener el formato yyyy-MM-dd");
            }
            ps.setDate(6, java.sql.Date.valueOf(propietario.getFecha_expedicion_docu()));
            ps.setString(7, propietario.getCorreo_electronico());
            ps.setString(8, propietario.getTelefono_1());
            ps.setString(9, propietario.getTelefono_2());
            ps.executeUpdate();
            ps.close();
            System.out.println("Propietario guardado con éxito en PostgreSQL");
        } catch (IllegalArgumentException e) {
            System.out.println("Error de validación: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error al guardar el propietario: " + e.getMessage());
        } finally {
            conexionDB.cerrar();
        }
    }

    @Override
    public ArrayList<Propietario> leer() {
        ArrayList<Propietario> listaPropietarios = new ArrayList<>();
        Connection conn = conexionDB.conectar();
        String sql = "SELECT * FROM propietario ORDER BY id_propietario";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Propietario propietario = new Propietario();
                propietario.setId_propietario(rs.getInt("id_propietario"));
                propietario.setCedula(rs.getInt("cedula"));
                propietario.setNombre_completo(rs.getString("nombre_completo"));
                propietario.setDireccion(rs.getString("direccion"));
                propietario.setFecha_nacimiento(rs.getString("fecha_nacimiento"));
                propietario.setFecha_expedicion_docu(rs.getString("fecha_expedicion_documento"));
                propietario.setCorreo_electronico(rs.getString("correo_electronico"));
                propietario.setTelefono_1(rs.getString("telefono_1"));
                propietario.setTelefono_2(rs.getString("telefono_2"));
                listaPropietarios.add(propietario);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error al leer propietarios: " + e.getMessage());
        } finally {
            conexionDB.cerrar();
        }
        return listaPropietarios;
    }

    @Override
    public boolean actualizar(Propietario propietario) {
        boolean actualizado = false;
        Connection conn = conexionDB.conectar();
        String sql = "UPDATE propietario SET cedula=?, nombre_completo=?, direccion=?, fecha_nacimiento=?, fecha_expedicion_documento=?, correo_electronico=?, telefono_1=?, telefono_2=? WHERE id_propietario=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, propietario.getCedula());
            ps.setString(2, propietario.getNombre_completo());
            ps.setString(3, propietario.getDireccion());
            ps.setDate(4, java.sql.Date.valueOf(propietario.getFecha_nacimiento()));
            ps.setDate(5, java.sql.Date.valueOf(propietario.getFecha_expedicion_docu()));
            ps.setString(6, propietario.getCorreo_electronico());
            ps.setString(7, propietario.getTelefono_1());
            ps.setString(8, propietario.getTelefono_2());
            ps.setInt(9, propietario.getId_propietario());
            actualizado = ps.executeUpdate() > 0;
            ps.close();
            if (actualizado) {
                System.out.println("Propietario actualizado con éxito en PostgreSQL");
            } else {
                System.out.println("Propietario no encontrado para actualizar");
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar el propietario: " + e.getMessage());
        } finally {
            conexionDB.cerrar();
        }
        return actualizado;
    }

    @Override
    public boolean eliminar(int id) {
        boolean eliminado = false;
        Connection conn = conexionDB.conectar();
        String sql = "DELETE FROM propietario WHERE id_propietario=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            eliminado = ps.executeUpdate() > 0;
            ps.close();
            if (eliminado) {
                System.out.println("Propietario eliminado con éxito en PostgreSQL");
            } else {
                System.out.println("Propietario no encontrado para eliminar");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar el propietario: " + e.getMessage());
        } finally {
            conexionDB.cerrar();
        }
        return eliminado;
    }

    public ArrayList<Propietario> buscarPorCriterio(String criterio) {
        ArrayList<Propietario> listaPropietarios = new ArrayList<>();
        Connection conn = conexionDB.conectar();
        String sql = "SELECT * FROM propietario WHERE CAST(id_propietario AS TEXT) LIKE ? OR CAST(cedula AS TEXT) LIKE ? OR LOWER(nombre_completo) LIKE ? ORDER BY id_propietario";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            String likeCriterio = "%" + criterio.toLowerCase() + "%";
            ps.setString(1, likeCriterio);
            ps.setString(2, likeCriterio);
            ps.setString(3, likeCriterio);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Propietario propietario = new Propietario();
                propietario.setId_propietario(rs.getInt("id_propietario"));
                propietario.setCedula(rs.getInt("cedula"));
                propietario.setNombre_completo(rs.getString("nombre_completo"));
                propietario.setDireccion(rs.getString("direccion"));
                propietario.setFecha_nacimiento(rs.getString("fecha_nacimiento"));
                propietario.setFecha_expedicion_docu(rs.getString("fecha_expedicion_documento"));
                propietario.setCorreo_electronico(rs.getString("correo_electronico"));
                propietario.setTelefono_1(rs.getString("telefono_1"));
                propietario.setTelefono_2(rs.getString("telefono_2"));
                listaPropietarios.add(propietario);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al buscar propietarios: " + e.getMessage());
        } finally {
            conexionDB.cerrar();
        }
        return listaPropietarios;
    }
}