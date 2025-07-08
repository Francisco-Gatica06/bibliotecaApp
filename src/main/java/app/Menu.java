package app;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private final Connection conn;
    private final LibroDAO libroDAO;
    private final UsuarioDAO usuarioDAO;
    private final PrestamoDAO prestamoDAO;
    private final Scanner scanner;

    public Menu(Connection conn) {
        this.conn = conn;
        this.libroDAO = new LibroDAO(conn);
        this.usuarioDAO = new UsuarioDAO(conn);
        this.prestamoDAO = new PrestamoDAO(conn);
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        int opcion;
        do {
            mostrarMenu();
            opcion = Integer.parseInt(scanner.nextLine());

            try {
                switch (opcion) {
                    case 1 -> agregarLibro();
                    case 2 -> listarLibros();
                    case 3 -> actualizarTituloLibro();
                    case 4 -> eliminarLibro();
                    case 5 -> agregarUsuario();
                    case 6 -> listarUsuarios();
                    case 7 -> actualizarEmailUsuario();
                    case 8 -> eliminarUsuario();
                    case 9 -> registrarPrestamo();
                    case 10 -> listarPrestamos();
                    case 11 -> marcarPrestamoComoDevuelto();
                    case 12 -> eliminarPrestamo();
                    case 0 -> System.out.println("👋 Saliendo...");
                    default -> System.out.println("❌ Opción inválida.");
                }
            } catch (SQLException e) {
                System.out.println("❌ Error: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private void mostrarMenu() {
        System.out.println("""
                \n========= MENÚ BIBLIOTECA =====================================================================
                1. Agregar libro                5. Agregar usuario              9. Registrar préstamo
                2. Listar libros                6. Listar usuarios              10. Listar préstamos
                3. Actualizar título de libro   7. Actualizar email de usuario  11. Marcar préstamo como devuelto
                4. Eliminar libro               8. Eliminar usuario             12. Eliminar préstamo
                
                0. Salir
                ==================================================================================================
                Ingresa una opción:
                """);
    }

    private void agregarLibro() throws SQLException {
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        System.out.print("Año de publicación: ");
        int anio = Integer.parseInt(scanner.nextLine());

        Libro libro = new Libro(isbn, titulo, anio, true);
        libroDAO.agregarLibro(libro);
        System.out.println("✅ Libro agregado.");
    }

    private void listarLibros() throws SQLException {
        List<Libro> libros = libroDAO.obtenerTodos();
        System.out.println("📚 Libros registrados:");
        for (Libro l : libros) {
            System.out.printf("- %s | %s | %d | Disponible: %s%n", l.getIsbn(), l.getTitulo(), l.getAnioPublicacion(), l.isDisponible());
        }
    }

    private void actualizarTituloLibro() throws SQLException {
        System.out.print("ISBN del libro a actualizar: ");
        String isbn = scanner.nextLine();
        System.out.print("Nuevo título: ");
        String nuevoTitulo = scanner.nextLine();
        libroDAO.actualizarTitulo(isbn, nuevoTitulo);
        System.out.println("✏️ Título actualizado.");
    }

    private void eliminarLibro() throws SQLException {
        System.out.print("ISBN del libro a eliminar: ");
        String isbn = scanner.nextLine();
        libroDAO.eliminarLibro(isbn);
        System.out.println("🗑️ Libro eliminado.");
    }

    private void agregarUsuario() throws SQLException {
        System.out.print("RUT: ");
        String rut = scanner.nextLine();
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellido paterno: ");
        String ap1 = scanner.nextLine();
        System.out.print("Apellido materno: ");
        String ap2 = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Celular: ");
        String celular = scanner.nextLine();

        Usuario usuario = new Usuario(rut, nombre, ap1, ap2, email, celular);
        usuarioDAO.agregarUsuario(usuario);
        System.out.println("✅ Usuario agregado.");
    }

    private void listarUsuarios() throws SQLException {
        List<Usuario> usuarios = usuarioDAO.obtenerTodos();
        System.out.println("👥 Usuarios registrados:");
        for (Usuario u : usuarios) {
            System.out.printf("- %s | %s %s | %s%n", u.getRut(), u.getNombre(), u.getApellido1(), u.getEmail());
        }
    }

    private void actualizarEmailUsuario() throws SQLException {
        System.out.print("RUT del usuario a actualizar: ");
        String rut = scanner.nextLine();
        System.out.print("Nuevo email: ");
        String nuevoEmail = scanner.nextLine();
        usuarioDAO.actualizarEmail(rut, nuevoEmail);
        System.out.println("✏️ Email actualizado.");
    }

    private void eliminarUsuario() throws SQLException {
        System.out.print("RUT del usuario a eliminar: ");
        String rut = scanner.nextLine();
        usuarioDAO.eliminarUsuario(rut);
        System.out.println("🗑️ Usuario eliminado.");
    }

    private void registrarPrestamo() throws SQLException {
        System.out.print("ISBN del libro: ");
        String isbn = scanner.nextLine();
        System.out.print("RUT del usuario: ");
        String rut = scanner.nextLine();
        System.out.print("Días de duración del préstamo: ");
        int dias = Integer.parseInt(scanner.nextLine());

        LocalDate hoy = LocalDate.now();
        LocalDate devolucion = hoy.plusDays(dias);

        Prestamo p = new Prestamo(isbn, rut, hoy, devolucion, false);
        prestamoDAO.registrarPrestamo(p);
        System.out.println("✅ Préstamo registrado.");
    }

    private void listarPrestamos() throws SQLException {
        List<Prestamo> prestamos = prestamoDAO.obtenerTodos();
        System.out.println("📄 Préstamos:");
        for (Prestamo p : prestamos) {
            System.out.printf("- ID %d | Libro: %s | Usuario: %s | Devuelto: %s%n",
                    p.getIdPrestamo(), p.getIsbn(), p.getRut(), p.isDevuelto());
        }
    }

    private void marcarPrestamoComoDevuelto() throws SQLException {
        System.out.print("ID del préstamo a marcar como devuelto: ");
        int id = Integer.parseInt(scanner.nextLine());
        prestamoDAO.marcarComoDevuelto(id);
        System.out.println("✅ Préstamo marcado como devuelto.");
    }

    private void eliminarPrestamo() throws SQLException {
        System.out.print("ID del préstamo a eliminar: ");
        int id = Integer.parseInt(scanner.nextLine());
        prestamoDAO.eliminarPrestamo(id);
        System.out.println("🗑️ Préstamo eliminado.");
    }
}