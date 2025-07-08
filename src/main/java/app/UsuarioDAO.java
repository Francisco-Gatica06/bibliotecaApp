package app;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private final Connection conn;

    public UsuarioDAO(Connection conn) {
        this.conn = conn;
    }

    // C
    public void agregarUsuario(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO Usuario (rut, nombre, apellido1, apellido2, email, celular) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);

            stmt.setString(1, usuario.getRut());
            stmt.setString(2, usuario.getNombre());
            stmt.setString(3, usuario.getApellido1());
            stmt.setString(4, usuario.getApellido2());
            stmt.setString(5, usuario.getEmail());
            stmt.setString(6, usuario.getCelular());

            stmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    // R
    public List<Usuario> obtenerTodos() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM Usuario";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                usuarios.add(new Usuario(
                        rs.getString("rut"),
                        rs.getString("nombre"),
                        rs.getString("apellido1"),
                        rs.getString("apellido2"),
                        rs.getString("email"),
                        rs.getString("celular")
                ));
            }
        }
        return usuarios;
    }

    // U
    public void actualizarEmail(String rut, String nuevoEmail) throws SQLException {
        String sql = "UPDATE Usuario SET email = ? WHERE rut = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);

            stmt.setString(1, nuevoEmail);
            stmt.setString(2, rut);
            stmt.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    // D
    public void eliminarUsuario(String rut) throws SQLException {
        String sql = "DELETE FROM Usuario WHERE rut = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);

            stmt.setString(1, rut);
            stmt.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }
}
