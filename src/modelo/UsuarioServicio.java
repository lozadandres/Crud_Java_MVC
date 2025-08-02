package modelo;

import java.sql.*;
import java.util.ArrayList;

import util.HashUtil;

public class UsuarioServicio {
    private final PostgresConexion conexionDB = new PostgresConexion();

    public void actualizar(Usuario usuario) {
        Connection conn = conexionDB.conectar();
        String sql = "UPDATE usuario SET loguin = ?, contraseña = ?, id_agente_comerciales = ?, tipo_usuario = ? WHERE id_usuario = ?";
        try {
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, usuario.getLoguin());
            // Hash de la contraseña antes de actualizar
            String hashContrasena = HashUtil.sha256(usuario.getContrasena());
            ps.setString(2, hashContrasena);
            if ("administrador".equals(usuario.getTipo_usuario())) {
                ps.setNull(3, java.sql.Types.INTEGER);
            } else {
                ps.setInt(3, usuario.getId_agente_comerciales());
                // Si es agente comercial, verificar que existe y actualizar sus credenciales
                if ("agente comercial".equals(usuario.getTipo_usuario())) {
                    // Primero verificar si existe el agente comercial
                    String sqlCheckAgente = "SELECT COUNT(*) FROM agentes_comerciales WHERE id_agente_comerciales = ?";
                    PreparedStatement psCheck = conn.prepareStatement(sqlCheckAgente);
                    psCheck.setInt(1, usuario.getId_agente_comerciales());
                    ResultSet rs = psCheck.executeQuery();
                    
                    if (rs.next() && rs.getInt(1) > 0) {
                        // El agente existe, actualizar sus credenciales
                        String sqlUpdateAgente = "UPDATE agentes_comerciales SET loguin = ?, contraseña = ? WHERE id_agente_comerciales = ?";
                        PreparedStatement psAgente = conn.prepareStatement(sqlUpdateAgente);
                        psAgente.setString(1, usuario.getLoguin());
                        psAgente.setString(2, hashContrasena);
                        psAgente.setInt(3, usuario.getId_agente_comerciales());
                        psAgente.executeUpdate();
                        psAgente.close();
                    } else {
                        throw new SQLException("El agente comercial con ID " + usuario.getId_agente_comerciales() + " no existe");
                    }
                    rs.close();
                    psCheck.close();
                }
            }
            ps.setString(4, usuario.getTipo_usuario());
            ps.setInt(5, usuario.getId_usuario());
            ps.executeUpdate();
            ps.close();
            conn.commit();
            System.out.println("Usuario actualizado con éxito");
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.out.println("Error al realizar rollback: " + ex.getMessage());
            }
            System.out.println("Error al actualizar el usuario: " + e.getMessage());
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Error al restaurar autoCommit: " + e.getMessage());
            }
            conexionDB.cerrar();
        }
    }

    public void eliminar(int idUsuario) {
        Connection conn = conexionDB.conectar();
        String sql = "DELETE FROM usuario WHERE id_usuario = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idUsuario);
            ps.executeUpdate();
            ps.close();
            System.out.println("Usuario eliminado con éxito");
        } catch (SQLException e) {
            System.out.println("Error al eliminar el usuario: " + e.getMessage());
        } finally {
            conexionDB.cerrar();
        }
    }

    public int obtenerSiguienteId() {
        int id = 1;
        Connection conn = conexionDB.conectar();
        String sql = "SELECT MAX(id_usuario) FROM usuario";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                id = rs.getInt(1) + 1;
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error al obtener el siguiente ID de usuario: " + e.getMessage());
        } finally {
            conexionDB.cerrar();
        }
        return id;
    }

    public boolean existeAdministrador() {
        boolean existe = false;
        Connection conn = conexionDB.conectar();
        String sql = "SELECT COUNT(*) FROM usuario WHERE tipo_usuario = 'administrador'";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                existe = rs.getInt(1) > 0;
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error al verificar administrador: " + e.getMessage());
        } finally {
            conexionDB.cerrar();
        }
        return existe;
    }

    public void crear(Usuario usuario) {
        Connection conn = conexionDB.conectar();
        String sql = "INSERT INTO usuario (id_usuario, loguin, contraseña, id_agente_comerciales, tipo_usuario) VALUES (?, ?, ?, ?, ?)";
        try {
            conn.setAutoCommit(false);
            usuario.setId_usuario(obtenerSiguienteId());
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, usuario.getId_usuario());
            ps.setString(2, usuario.getLoguin());
            // Hash de la contraseña antes de guardar
            String hashContrasena = HashUtil.sha256(usuario.getContrasena());
            ps.setString(3, hashContrasena);
            if ("administrador".equals(usuario.getTipo_usuario())) {
                ps.setNull(4, java.sql.Types.INTEGER);
            } else {
                ps.setInt(4, usuario.getId_agente_comerciales());
                // Si es agente comercial, verificar que existe y actualizar sus credenciales
                if ("agente comercial".equals(usuario.getTipo_usuario())) {
                    // Primero verificar si existe el agente comercial
                    String sqlCheckAgente = "SELECT COUNT(*) FROM agentes_comerciales WHERE id_agente_comerciales = ?";
                    PreparedStatement psCheck = conn.prepareStatement(sqlCheckAgente);
                    psCheck.setInt(1, usuario.getId_agente_comerciales());
                    ResultSet rs = psCheck.executeQuery();
                    
                    if (rs.next() && rs.getInt(1) > 0) {
                        // El agente existe, actualizar sus credenciales
                        String sqlUpdateAgente = "UPDATE agentes_comerciales SET loguin = ?, contraseña = ? WHERE id_agente_comerciales = ?";
                        PreparedStatement psAgente = conn.prepareStatement(sqlUpdateAgente);
                        psAgente.setString(1, usuario.getLoguin());
                        psAgente.setString(2, hashContrasena);
                        psAgente.setInt(3, usuario.getId_agente_comerciales());
                        psAgente.executeUpdate();
                        psAgente.close();
                    } else {
                        throw new SQLException("El agente comercial con ID " + usuario.getId_agente_comerciales() + " no existe");
                    }
                    rs.close();
                    psCheck.close();
                }
            }
            ps.setString(5, usuario.getTipo_usuario());
            ps.executeUpdate();
            ps.close();
            conn.commit();
            System.out.println("Usuario guardado con éxito");
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.out.println("Error al realizar rollback: " + ex.getMessage());
            }
            System.out.println("Error al guardar el usuario: " + e.getMessage());
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Error al restaurar autoCommit: " + e.getMessage());
            }
            conexionDB.cerrar();
        }
    }

    public Usuario autenticar(String loguin, String contrasena) {
        Usuario usuario = null;
        Connection conn = conexionDB.conectar();
        String sql = "SELECT * FROM usuario WHERE loguin = ? AND contraseña = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, loguin);
            // Hash de la contraseña ingresada antes de comparar
            String hashContrasena = HashUtil.sha256(contrasena);
            ps.setString(2, hashContrasena);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId_usuario(rs.getInt("id_usuario"));
                usuario.setLoguin(rs.getString("loguin"));
                usuario.setContrasena(rs.getString("contraseña"));
                usuario.setId_agente_comerciales(rs.getInt("id_agente_comerciales"));
                usuario.setTipo_usuario(rs.getString("tipo_usuario"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al autenticar usuario: " + e.getMessage());
        } finally {
            conexionDB.cerrar();
        }
        return usuario;
    }

    public ArrayList<Usuario> leer() {
        ArrayList<Usuario> listaUsuarios = new ArrayList<>();
        Connection conn = conexionDB.conectar();
        String sql = "SELECT * FROM usuario ORDER BY id_usuario";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId_usuario(rs.getInt("id_usuario"));
                usuario.setLoguin(rs.getString("loguin"));
                usuario.setContrasena(rs.getString("contraseña"));
                usuario.setId_agente_comerciales(rs.getInt("id_agente_comerciales"));
                usuario.setTipo_usuario(rs.getString("tipo_usuario"));
                listaUsuarios.add(usuario);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error al leer usuarios: " + e.getMessage());
        } finally {
            conexionDB.cerrar();
        }
        return listaUsuarios;
    }
}