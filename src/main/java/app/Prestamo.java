package app;

import java.time.LocalDate;

public class Prestamo {
    private int idPrestamo;
    private String isbn;
    private String rut;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;
    private boolean devuelto;

    public Prestamo(int idPrestamo, String isbn, String rut, LocalDate fechaPrestamo, LocalDate fechaDevolucion, boolean devuelto) {
        this.idPrestamo = idPrestamo;
        this.isbn = isbn;
        this.rut = rut;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.devuelto = devuelto;
    }

    public Prestamo(String isbn, String rut, LocalDate fechaPrestamo, LocalDate fechaDevolucion, boolean devuelto) {
        this(0, isbn, rut, fechaPrestamo, fechaDevolucion, devuelto);
    }

    public int getIdPrestamo() { return idPrestamo; }
    public String getIsbn() { return isbn; }
    public String getRut() { return rut; }
    public LocalDate getFechaPrestamo() { return fechaPrestamo; }
    public LocalDate getFechaDevolucion() { return fechaDevolucion; }
    public boolean isDevuelto() { return devuelto; }

    public void setDevuelto(boolean devuelto) { this.devuelto = devuelto; }
}
