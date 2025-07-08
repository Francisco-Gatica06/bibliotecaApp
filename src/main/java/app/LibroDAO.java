package app;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroDAO {

    private final Connection conn;

    public LibroDAO(Connection conn) {
        this.conn = conn;
    }

    // C
    public void agregarLibro(Libro libro) throws SQLException {
        String sql = "INSERT INTO Libro (isbn, titulo, anio_publicacion, disponible) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false); // Comienza la transacción

            stmt.setString(1, libro.getIsbn());
            stmt.setString(2, libro.getTitulo());
            stmt.setInt(3, libro.getAnioPublicacion());
            stmt.setBoolean(4, libro.isDisponible());

            stmt.executeUpdate();
            conn.commit(); // Confirmar la transacción
        } catch (SQLException e) {
            conn.rollback(); // Deshacer cambios si hay error
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    // R
    public List<Libro> obtenerTodos() throws SQLException {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT * FROM Libro";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                libros.add(new Libro(
                        rs.getString("isbn"),
                        rs.getString("titulo"),
                        rs.getInt("anio_publicacion"),
                        rs.getBoolean("disponible")
                ));
            }
        }
        return libros;
    }

    // U
    public void actualizarTitulo(String isbn, String nuevoTitulo) throws SQLException {
        String sql = "UPDATE Libro SET titulo = ? WHERE isbn = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);

            stmt.setString(1, nuevoTitulo);
            stmt.setString(2, isbn);
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
    public void eliminarLibro(String isbn) throws SQLException {
        String sql = "DELETE FROM Libro WHERE isbn = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);

            stmt.setString(1, isbn);
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
