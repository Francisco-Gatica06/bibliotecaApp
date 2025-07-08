package app;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAO {

    private final Connection conn;

    public PrestamoDAO(Connection conn) {
        this.conn = conn;
    }

    // C
    public void registrarPrestamo(Prestamo p) throws SQLException {
        String sql = "INSERT INTO Prestamo (isbn, rut, fecha_prestamo, fecha_devolucion, devuelto) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);

            stmt.setString(1, p.getIsbn());
            stmt.setString(2, p.getRut());
            stmt.setDate(3, Date.valueOf(p.getFechaPrestamo()));
            stmt.setDate(4, Date.valueOf(p.getFechaDevolucion()));
            stmt.setBoolean(5, p.isDevuelto());

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
    public List<Prestamo> obtenerTodos() throws SQLException {
        List<Prestamo> lista = new ArrayList<>();
        String sql = "SELECT * FROM Prestamo";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Prestamo p = new Prestamo(
                        rs.getInt("id_prestamo"),
                        rs.getString("isbn"),
                        rs.getString("rut"),
                        rs.getDate("fecha_prestamo").toLocalDate(),
                        rs.getDate("fecha_devolucion") != null ? rs.getDate("fecha_devolucion").toLocalDate() : null,
                        rs.getBoolean("devuelto")
                );
                lista.add(p);
            }
        }
        return lista;
    }

    // U
    public void marcarComoDevuelto(int idPrestamo) throws SQLException {
        String sql = "UPDATE Prestamo SET devuelto = TRUE WHERE id_prestamo = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);
            stmt.setInt(1, idPrestamo);
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
    public void eliminarPrestamo(int idPrestamo) throws SQLException {
        String sql = "DELETE FROM Prestamo WHERE id_prestamo = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);
            stmt.setInt(1, idPrestamo);
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
