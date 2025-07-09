package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/biblioteca";
        String user = "postgres";
        String password = "francisco";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            Menu menu = new Menu(conn);
            menu.iniciar();
        } catch (SQLException e) {
            System.out.println("Error al conectar: " + e.getMessage());
        }
    }
}