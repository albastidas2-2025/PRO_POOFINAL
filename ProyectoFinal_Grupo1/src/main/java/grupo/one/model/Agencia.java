package grupo.one.model;

import java.util.ArrayList;
import java.util.List;

public class Agencia {
    private List<Vehiculo> inventario;
    private List<Alquiler> alquileres;

    public Agencia() {
        inventario = new ArrayList<>();
        alquileres = new ArrayList<>();
    }

    public void agregarVehiculo(Vehiculo vehiculo) {
        if (vehiculo != null) {
            inventario.add(vehiculo);
        }
    }

    public List<Vehiculo> getInventario() {
        return inventario;
    }

    public Alquiler crearAlquiler(String id, Vehiculo vehiculo, int dias) {
        Alquiler alquiler = new Alquiler(id, vehiculo, dias);
        alquileres.add(alquiler);
        return alquiler;
    }
}
