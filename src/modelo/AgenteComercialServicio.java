package modelo;

import java.sql.*;
import java.util.ArrayList;

public class AgenteComercialServicio extends BaseServicio<AgenteComercial> implements ICrudServicio<AgenteComercial> {
    private final PostgresConexion conexionDB = new PostgresConexion();

    @Override
    public int obtenerSiguienteId() {
        int id = 1;
        Connection conn = conexionDB.conectar();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT MAX(id_agente_comerciales) FROM agentes_comerciales");
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
    public void crear(AgenteComercial agente) {
        Connection conn = conexionDB.conectar();
        String sql = "INSERT INTO agentes_comerciales (id_agente_comerciales, cedula, loguin, contraseña, nombre_completo, direccion, fecha_nacimiento, fecha_expedicion_documento, correo_electronico, celular) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            agente.setId_agente_comerciales(obtenerSiguienteId());
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, agente.getId_agente_comerciales());
            ps.setInt(2, agente.getCedula());
            ps.setString(3, agente.getLoguin());
            ps.setString(4, agente.getContrasena());
            ps.setString(5, agente.getNombre_completo());
            ps.setString(6, agente.getDireccion());
            // Validación de fecha de nacimiento
            if (agente.getFecha_nacimiento() == null || agente.getFecha_nacimiento().isEmpty()) {
                throw new IllegalArgumentException("La fecha de nacimiento no puede estar vacía");
            }
            if (!agente.getFecha_nacimiento().matches("\\d{4}-\\d{2}-\\d{2}")) {
                throw new IllegalArgumentException("La fecha de nacimiento debe tener el formato yyyy-MM-dd");
            }
            ps.setDate(7, java.sql.Date.valueOf(agente.getFecha_nacimiento()));
            // Validación de fecha de expedición
            if (agente.getFecha_expedicion_docu() == null || agente.getFecha_expedicion_docu().isEmpty()) {
                throw new IllegalArgumentException("La fecha de expedición no puede estar vacía");
            }
            if (!agente.getFecha_expedicion_docu().matches("\\d{4}-\\d{2}-\\d{2}")) {
                throw new IllegalArgumentException("La fecha de expedición debe tener el formato yyyy-MM-dd");
            }
            ps.setDate(8, java.sql.Date.valueOf(agente.getFecha_expedicion_docu()));
            ps.setString(9, agente.getCorreo_electronico());
            ps.setString(10, agente.getCelular());
            ps.executeUpdate();
            ps.close();
            System.out.println("Agente comercial guardado con éxito en PostgreSQL");
        } catch (IllegalArgumentException e) {
            System.out.println("Error de validación: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error al guardar el agente comercial: " + e.getMessage());
        } finally {
            conexionDB.cerrar();
        }
    }

    @Override
    public ArrayList<AgenteComercial> leer() {
        ArrayList<AgenteComercial> listaAgentes = new ArrayList<>();
        Connection conn = conexionDB.conectar();
        String sql = "SELECT * FROM agentes_comerciales ORDER BY id_agente_comerciales";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                AgenteComercial agente = new AgenteComercial();
                agente.setId_agente_comerciales(rs.getInt("id_agente_comerciales"));
                agente.setCedula(rs.getInt("cedula"));
                agente.setLoguin(rs.getString("loguin"));
                agente.setContrasena(rs.getString("contraseña"));
                agente.setNombre_completo(rs.getString("nombre_completo"));
                agente.setDireccion(rs.getString("direccion"));
                agente.setFecha_nacimiento(rs.getString("fecha_nacimiento"));
                agente.setFecha_expedicion_docu(rs.getString("fecha_expedicion_documento"));
                agente.setCorreo_electronico(rs.getString("correo_electronico"));
                agente.setCelular(rs.getString("celular"));
                listaAgentes.add(agente);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error al leer agentes comerciales: " + e.getMessage());
        } finally {
            conexionDB.cerrar();
        }
        return listaAgentes;
    }

    @Override
    public boolean actualizar(AgenteComercial agente) {
        boolean actualizado = false;
        Connection conn = conexionDB.conectar();
        String sql = "UPDATE agentes_comerciales SET cedula=?, loguin=?, contraseña=?, nombre_completo=?, direccion=?, fecha_nacimiento=?, fecha_expedicion_documento=?, correo_electronico=?, celular=? WHERE id_agente_comerciales=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, agente.getCedula());
            ps.setString(2, agente.getLoguin());
            ps.setString(3, agente.getContrasena());
            ps.setString(4, agente.getNombre_completo());
            ps.setString(5, agente.getDireccion());
            ps.setDate(6, java.sql.Date.valueOf(agente.getFecha_nacimiento()));
            ps.setDate(7, java.sql.Date.valueOf(agente.getFecha_expedicion_docu()));
            ps.setString(8, agente.getCorreo_electronico());
            ps.setString(9, agente.getCelular());
            ps.setInt(10, agente.getId_agente_comerciales());
            actualizado = ps.executeUpdate() > 0;
            ps.close();
            if (actualizado) {
                System.out.println("Agente comercial actualizado con éxito en PostgreSQL");
            } else {
                System.out.println("Agente comercial no encontrado para actualizar");
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar el agente comercial: " + e.getMessage());
        } finally {
            conexionDB.cerrar();
        }
        return actualizado;
    }

    @Override
    public boolean eliminar(int id) {
        boolean eliminado = false;
        Connection conn = conexionDB.conectar();
        String sql = "DELETE FROM agentes_comerciales WHERE id_agente_comerciales=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            eliminado = ps.executeUpdate() > 0;
            ps.close();
            if (eliminado) {
                System.out.println("Agente comercial eliminado con éxito en PostgreSQL");
            } else {
                System.out.println("Agente comercial no encontrado para eliminar");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar el agente comercial: " + e.getMessage());
        } finally {
            conexionDB.cerrar();
        }
        return eliminado;
    }

    public ArrayList<AgenteComercial> buscarPorCriterio(String criterio) {
        ArrayList<AgenteComercial> listaAgentes = new ArrayList<>();
        Connection conn = conexionDB.conectar();
        String sql = "SELECT * FROM agentes_comerciales WHERE CAST(id_agente_comerciales AS TEXT) LIKE ? OR CAST(cedula AS TEXT) LIKE ? OR LOWER(nombre_completo) LIKE ? ORDER BY id_agente_comerciales";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            String likeCriterio = "%" + criterio.toLowerCase() + "%";
            ps.setString(1, likeCriterio);
            ps.setString(2, likeCriterio);
            ps.setString(3, likeCriterio);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AgenteComercial agente = new AgenteComercial();
                agente.setId_agente_comerciales(rs.getInt("id_agente_comerciales"));
                agente.setCedula(rs.getInt("cedula"));
                agente.setLoguin(rs.getString("loguin"));
                agente.setContrasena(rs.getString("contraseña"));
                agente.setNombre_completo(rs.getString("nombre_completo"));
                agente.setDireccion(rs.getString("direccion"));
                agente.setFecha_nacimiento(rs.getString("fecha_nacimiento"));
                agente.setFecha_expedicion_docu(rs.getString("fecha_expedicion_documento"));
                agente.setCorreo_electronico(rs.getString("correo_electronico"));
                agente.setCelular(rs.getString("celular"));
                listaAgentes.add(agente);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al buscar agentes comerciales: " + e.getMessage());
        } finally {
            conexionDB.cerrar();
        }
        return listaAgentes;
    }
}