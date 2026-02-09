package grupo.one.controller;

import grupo.one.model.Alquiler;
import grupo.one.model.Vehiculo;
import grupo.one.service.AlquilerService;
import org.bson.Document;

import javax.swing.*;
import java.util.List;

public class AlquilerController {
    private final AlquilerService alquilerService;

    public AlquilerController(AlquilerService alquilerService) {
        this.alquilerService = alquilerService;
    }

    public boolean registrarAlquiler(Alquiler alquiler) {
        try {
            return alquilerService.registrarAlquiler(alquiler);
        } catch (Exception e) {
            mostrarError("Error al registrar alquiler: " + e.getMessage());
            return false;
        }
    }

    public Alquiler crearYRegistrarAlquiler(Vehiculo vehiculo, int dias, String idAlquiler) {
        try {
            if (vehiculo == null) {
                throw new IllegalArgumentException("Debe seleccionar un vehículo");
            }
            if (dias <= 0) {
                throw new IllegalArgumentException("Los días deben ser mayores a 0");
            }
            if (idAlquiler == null || idAlquiler.trim().isEmpty()) {
                throw new IllegalArgumentException("El ID del alquiler es requerido");
            }
            Alquiler alquiler = new Alquiler(idAlquiler, vehiculo, dias);
            alquilerService.registrarAlquiler(alquiler);
            return alquiler;
        } catch (Exception e) {
            mostrarError("Error al crear alquiler: " + e.getMessage());
            throw e;
        }
    }
    public Document buscarAlquilerPorId(String id) {
        try {
            if (id == null || id.trim().isEmpty()) {
                mostrarAdvertencia("Debe ingresar un ID válido");
                return null;
            }

            return alquilerService.buscarAlquilerPorId(id.trim());
        } catch (Exception e) {
            mostrarError("Error al buscar alquiler: " + e.getMessage());
            return null;
        }
    }
    public List<Document> listarTodosAlquileres() {
        try {
            return alquilerService.listarTodosAlquileres();
        } catch (Exception e) {
            mostrarError("Error al listar alquileres: " + e.getMessage());
            return null;
        }
    }

    public List<Document> buscarAlquileresPorVehiculo(String placa) {
        try {
            if (placa == null || placa.trim().isEmpty()) {
                mostrarAdvertencia("Debe ingresar una placa válida");
                return null;
            }

            return alquilerService.buscarAlquileresPorVehiculo(placa.trim());

        } catch (Exception e) {
            mostrarError("Error al buscar alquileres por vehículo: " + e.getMessage());
            return null;
        }
    }
    public boolean actualizarAlquiler(Alquiler alquiler) {
        try {
            if (alquiler == null) {
                mostrarAdvertencia("El alquiler no puede ser nulo");
                return false;
            }

            return alquilerService.actualizarAlquiler(alquiler);

        } catch (Exception e) {
            mostrarError("Error al actualizar alquiler: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarAlquiler(String id) {
        try {
            if (id == null || id.trim().isEmpty()) {
                mostrarAdvertencia("Debe ingresar un ID válido");
                return false;
            }

            int confirmacion = JOptionPane.showConfirmDialog(
                    null,
                    "¿Está seguro de eliminar el alquiler con ID: " + id + "?",
                    "Confirmar Eliminación",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirmacion == JOptionPane.YES_OPTION) {
                boolean eliminado = alquilerService.eliminarAlquiler(id);
                if (eliminado) {
                    mostrarExito("Alquiler eliminado correctamente");
                }
                return eliminado;
            }
            return false;

        } catch (Exception e) {
            mostrarError("Error al eliminar alquiler: " + e.getMessage());
            return false;
        }
    }
    public boolean existeAlquiler(String id) {
        try {
            if (id == null || id.trim().isEmpty()) {
                return false;
            }

            return alquilerService.existeAlquiler(id.trim());

        } catch (Exception e) {
            System.err.println("Error al verificar alquiler: " + e.getMessage());
            return false;
        }
    }
    public String obtenerEstadisticasAlquileres() {
        try {
            return alquilerService.obtenerEstadisticas();
        } catch (Exception e) {
            return "Error al obtener estadísticas: " + e.getMessage();
        }
    }

    public long contarAlquileres() {
        try {
            return alquilerService.contarAlquileres();
        } catch (Exception e) {
            mostrarError("Error al contar alquileres: " + e.getMessage());
            return 0;
        }
    }

    public double calcularIngresosTotales() {
        try {
            return alquilerService.calcularIngresosTotales();
        } catch (Exception e) {
            mostrarError("Error al calcular ingresos: " + e.getMessage());
            return 0.0;
        }
    }
    public boolean validarDiasAlquiler(int dias) {
        return dias > 0 && dias <= 365;
    }
    public String formatearAlquilerParaMostrar(Document alquilerDoc) {
        if (alquilerDoc == null) {
            return "Alquiler no encontrado";
        }

        try {
            Document vehiculoDoc = (Document) alquilerDoc.get("vehiculo");

            return String.format(
                    "ID Alquiler: %s\n" +
                            "Vehículo: %s %s (%s)\n" +
                            "Días: %d\n" +
                            "Costo Total: $%.2f\n" +
                            "Estado: %s",
                    alquilerDoc.getString("idAlquiler"),
                    vehiculoDoc.getString("marca"),
                    vehiculoDoc.getString("modelo"),
                    vehiculoDoc.getString("placa"),
                    alquilerDoc.getInteger("dias"),
                    alquilerDoc.getDouble("costoTotal"),
                    alquilerDoc.getString("estado")
            );
        } catch (Exception e) {
            return "Error al formatear alquiler: " + e.getMessage();
        }
    }
    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarAdvertencia(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
    }
}
