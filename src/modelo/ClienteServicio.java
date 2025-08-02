package modelo;

import java.sql.*;
import java.util.ArrayList;

public class ClienteServicio implements ICrudServicio<Cliente> {
    private final PostgresConexion conexionDB = new PostgresConexion();

    @Override
    public int obtenerSiguienteId() {
        int id = 1;
        Connection conn = conexionDB.conectar();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT MAX(id_cliente) FROM cliente");
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
    public void crear(Cliente cliente) {
        Connection conn = conexionDB.conectar();
        String sql = "INSERT INTO cliente (id_cliente, cedula, nombre_completo, direccion, fecha_nacimiento, fecha_expedicion_documento, correo_electronico, telefono_1, telefono_2) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            cliente.setId_cliente(obtenerSiguienteId());
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, cliente.getId_cliente());
            ps.setInt(2, cliente.getCedula());
            ps.setString(3, cliente.getNombre_completo());
            ps.setString(4, cliente.getDireccion());
            // Validación de fecha de nacimiento
            if (cliente.getFecha_nacimiento() == null || cliente.getFecha_nacimiento().isEmpty()) {
                throw new IllegalArgumentException("La fecha de nacimiento no puede estar vacía");
            }
            if (!cliente.getFecha_nacimiento().matches("\\d{4}-\\d{2}-\\d{2}")) {
                throw new IllegalArgumentException("La fecha de nacimiento debe tener el formato yyyy-MM-dd");
            }
            ps.setDate(5, java.sql.Date.valueOf(cliente.getFecha_nacimiento()));
            // Validación de fecha de expedición
            if (cliente.getFecha_expedicion_docu() == null || cliente.getFecha_expedicion_docu().isEmpty()) {
                throw new IllegalArgumentException("La fecha de expedición no puede estar vacía");
            }
            if (!cliente.getFecha_expedicion_docu().matches("\\d{4}-\\d{2}-\\d{2}")) {
                throw new IllegalArgumentException("La fecha de expedición debe tener el formato yyyy-MM-dd");
            }
            ps.setDate(6, java.sql.Date.valueOf(cliente.getFecha_expedicion_docu()));
            ps.setString(7, cliente.getCorreo_electronico());
            ps.setString(8, cliente.getTelefono_1());
            ps.setString(9, cliente.getTelefono_2());
            ps.executeUpdate();
            ps.close();
            System.out.println("Cliente guardado con éxito en PostgreSQL");
        } catch (IllegalArgumentException e) {
            System.out.println("Error de validación: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error al guardar el cliente: " + e.getMessage());
        } finally {
            conexionDB.cerrar();
        }
    }

    @Override
    public ArrayList<Cliente> leer() {
        ArrayList<Cliente> listaClientes = new ArrayList<>();
        Connection conn = conexionDB.conectar();
        String sql = "SELECT * FROM cliente ORDER BY id_cliente";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId_cliente(rs.getInt("id_cliente"));
                cliente.setCedula(rs.getInt("cedula"));
                cliente.setNombre_completo(rs.getString("nombre_completo"));
                cliente.setDireccion(rs.getString("direccion"));
                cliente.setFecha_nacimiento(rs.getString("fecha_nacimiento"));
                cliente.setFecha_expedicion_docu(rs.getString("fecha_expedicion_documento"));
                cliente.setCorreo_electronico(rs.getString("correo_electronico"));
                cliente.setTelefono_1(rs.getString("telefono_1"));
                cliente.setTelefono_2(rs.getString("telefono_2"));
                listaClientes.add(cliente);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error al leer clientes: " + e.getMessage());
        } finally {
            conexionDB.cerrar();
        }
        return listaClientes;
    }

    @Override
    public boolean actualizar(Cliente cliente) {
        boolean actualizado = false;
        Connection conn = conexionDB.conectar();
        String sql = "UPDATE cliente SET cedula=?, nombre_completo=?, direccion=?, fecha_nacimiento=?, fecha_expedicion_documento=?, correo_electronico=?, telefono_1=?, telefono_2=? WHERE id_cliente=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, cliente.getCedula());
            ps.setString(2, cliente.getNombre_completo());
            ps.setString(3, cliente.getDireccion());
            ps.setDate(4, java.sql.Date.valueOf(cliente.getFecha_nacimiento()));
            ps.setDate(5, java.sql.Date.valueOf(cliente.getFecha_expedicion_docu()));
            ps.setString(6, cliente.getCorreo_electronico());
            ps.setString(7, cliente.getTelefono_1());
            ps.setString(8, cliente.getTelefono_2());
            ps.setInt(9, cliente.getId_cliente());
            actualizado = ps.executeUpdate() > 0;
            ps.close();
            if (actualizado) {
                System.out.println("Cliente actualizado con éxito en PostgreSQL");
            } else {
                System.out.println("Cliente no encontrado para actualizar");
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar el cliente: " + e.getMessage());
        } finally {
            conexionDB.cerrar();
        }
        return actualizado;
    }

    @Override
    public boolean eliminar(int id) {
        boolean eliminado = false;
        Connection conn = conexionDB.conectar();
        String sql = "DELETE FROM cliente WHERE id_cliente=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            eliminado = ps.executeUpdate() > 0;
            ps.close();
            if (eliminado) {
                System.out.println("Cliente eliminado con éxito en PostgreSQL");
            } else {
                System.out.println("Cliente no encontrado para eliminar");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar el cliente: " + e.getMessage());
        } finally {
            conexionDB.cerrar();
        }
        return eliminado;
    }

    public ArrayList<Cliente> buscarPorCriterio(String criterio) {
        ArrayList<Cliente> listaClientes = new ArrayList<>();
        Connection conn = conexionDB.conectar();
        String sql = "SELECT * FROM cliente WHERE CAST(id_cliente AS TEXT) LIKE ? OR CAST(cedula AS TEXT) LIKE ? OR LOWER(nombre_completo) LIKE ? ORDER BY id_cliente";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            String likeCriterio = "%" + criterio.toLowerCase() + "%";
            ps.setString(1, likeCriterio);
            ps.setString(2, likeCriterio);
            ps.setString(3, likeCriterio);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId_cliente(rs.getInt("id_cliente"));
                cliente.setCedula(rs.getInt("cedula"));
                cliente.setNombre_completo(rs.getString("nombre_completo"));
                cliente.setDireccion(rs.getString("direccion"));
                cliente.setFecha_nacimiento(rs.getString("fecha_nacimiento"));
                cliente.setFecha_expedicion_docu(rs.getString("fecha_expedicion_documento"));
                cliente.setCorreo_electronico(rs.getString("correo_electronico"));
                cliente.setTelefono_1(rs.getString("telefono_1"));
                cliente.setTelefono_2(rs.getString("telefono_2"));
                listaClientes.add(cliente);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al buscar clientes: " + e.getMessage());
        } finally {
            conexionDB.cerrar();
        }
        return listaClientes;
    }
}