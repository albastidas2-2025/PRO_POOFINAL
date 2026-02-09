package grupo.one.model;

import java.time.LocalDate;

public class Alquiler {

    private String idAlquiler;
    private Vehiculo vehiculo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int dias;
    private double costoTotal;
    private String estado;

    public Alquiler(String idAlquiler, Vehiculo vehiculo, int dias) {
        this.idAlquiler = idAlquiler;
        this.vehiculo = vehiculo;
        this.dias = dias;
        this.fechaInicio = LocalDate.now();
        this.fechaFin = fechaInicio.plusDays(dias);

        this.costoTotal = vehiculo.calcularCostoTotal(dias);

        this.estado = "ACTIVO";
    }

    public String getIdAlquiler() {
        return idAlquiler;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public int getDias() {
        return dias;
    }

    public double getCostoTotal() {
        return costoTotal;
    }

    public String getEstado() {
        return estado;
    }

    public void finalizarAlquiler() {
        this.estado = "FINALIZADO";
    }

    @Override
    public String toString() {
        return vehiculo + " | días: " + dias + " | total: $" + costoTotal;
    }

}
