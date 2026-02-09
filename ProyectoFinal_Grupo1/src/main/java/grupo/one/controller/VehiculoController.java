package grupo.one.controller;


import grupo.one.model.Vehiculo;
import grupo.one.service.VehiculoService;
import org.bson.Document;

import javax.swing.*;
import java.util.List;

public class VehiculoController {
    private final VehiculoService vehiculoService;

    public VehiculoController(VehiculoService vehiculoService) {
        this.vehiculoService = vehiculoService;
    }

    public boolean registrarVehiculo(Vehiculo vehiculo) {
        try {
            return vehiculoService.registrarVehiculo(vehiculo);
        } catch (Exception e) {
            mostrarError("Error al registrar vehículo: " + e.getMessage());
            return false;
        }
    }

    public Document buscarVehiculo(String placa) {
        try {
            if (placa == null || placa.trim().isEmpty()) {
                mostrarAdvertencia("Debe ingresar una placa válida");
                return null;
            }

            return vehiculoService.buscarVehiculoPorPlaca(placa.trim());

        } catch (Exception e) {
            mostrarError("Error al buscar vehículo: " + e.getMessage());
            return null;
        }
    }

    public List<Document> listarTodosVehiculos() {
        try {
            return vehiculoService.listarTodosVehiculos();
        } catch (Exception e) {
            mostrarError("Error al listar vehículos: " + e.getMessage());
            return null;
        }
    }

    public boolean actualizarVehiculo(Vehiculo vehiculo) {
        try {
            if (vehiculo == null) {
                mostrarAdvertencia("El vehículo no puede ser nulo");
                return false;
            }
            return vehiculoService.actualizarVehiculo(vehiculo);
        } catch (Exception e) {
            mostrarError("Error al actualizar vehículo: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarVehiculo(String placa) {
        try {
            if (placa == null || placa.trim().isEmpty()) {
                mostrarAdvertencia("Debe ingresar una placa válida");
                return false;
            }

            int confirmacion = JOptionPane.showConfirmDialog(
                    null,
                    "¿Está seguro de eliminar el vehículo con placa: " + placa + "?",
                    "Confirmar Eliminación",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirmacion == JOptionPane.YES_OPTION) {
                boolean eliminado = vehiculoService.eliminarVehiculo(placa);
                if (eliminado) {
                    mostrarExito("Vehículo eliminado correctamente");
                }
                return eliminado;
            }
            return false;

        } catch (Exception e) {
            mostrarError("Error al eliminar vehículo: " + e.getMessage());
            return false;
        }
    }

    public boolean existeVehiculo(String placa) {
        try {
            if (placa == null || placa.trim().isEmpty()) {
                return false;
            }
            return vehiculoService.existeVehiculo(placa.trim());

        } catch (Exception e) {
            System.err.println("Error al verificar vehículo: " + e.getMessage());
            return false;
        }
    }

    public List<Document> obtenerVehiculosDisponibles() {
        try {
            return vehiculoService.obtenerVehiculosDisponibles();
        } catch (Exception e) {
            mostrarError("Error al obtener vehículos disponibles: " + e.getMessage());
            return null;
        }
    }

    public String obtenerEstadisticas() {
        try {
            return vehiculoService.obtenerEstadisticasVehiculos();
        } catch (Exception e) {
            return "Error al obtener estadísticas: " + e.getMessage();
        }
    }

    public long contarVehiculos() {
        try {
            return vehiculoService.contarVehiculos();
        } catch (Exception e) {
            mostrarError("Error al contar vehículos: " + e.getMessage());
            return 0;
        }
    }

    public String formatearVehiculoParaMostrar(Document vehiculoDoc) {
        if (vehiculoDoc == null) {
            return "Vehículo no encontrado";
        }
        try {
            return String.format(
                    "Placa: %s\n" +
                            "Marca: %s\n" +
                            "Modelo: %s\n" +
                            "Precio por día: $%.2f\n" +
                            "Tipo: %s",
                    vehiculoDoc.getString("placa"),
                    vehiculoDoc.getString("marca"),
                    vehiculoDoc.getString("modelo"),
                    vehiculoDoc.getDouble("precioPorDia"),
                    vehiculoDoc.getString("tipo")
            );
        } catch (Exception e) {
            return "Error al formatear vehículo: " + e.getMessage();
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(
                null,
                mensaje,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    private void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(
                null,
                mensaje,
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void mostrarAdvertencia(String mensaje) {
        JOptionPane.showMessageDialog(
                null,
                mensaje,
                "Advertencia",
                JOptionPane.WARNING_MESSAGE
        );
    }

}