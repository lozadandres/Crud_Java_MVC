package modelo;

public class Usuario {
    private int id_usuario;
    private String loguin;
    private String contrasena;
    private int id_agente_comerciales;
    private String tipo_usuario; // Ejemplo: "administrador", "agente"

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
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

    public int getId_agente_comerciales() {
        return id_agente_comerciales;
    }

    public void setId_agente_comerciales(int id_agente_comerciales) {
        this.id_agente_comerciales = id_agente_comerciales;
    }

    public String getTipo_usuario() {
        return tipo_usuario;
    }

    public void setTipo_usuario(String tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }
}