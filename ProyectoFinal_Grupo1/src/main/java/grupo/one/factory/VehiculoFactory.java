package grupo.one.factory;


import grupo.one.model.Automovil;
import grupo.one.model.Motocicleta;
import grupo.one.model.Vehiculo;

public class VehiculoFactory {
    public static final String TIPO_AUTO = "AUTO";
    public static final String TIPO_MOTO = "MOTO";

    public static Vehiculo crearVehiculo(String tipo, String placa, String marca,
                                         String modelo, double precioPorDia, int parametroExtra) {

        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de vehículo no puede ser nulo o vacío");
        }

        if (placa == null || placa.trim().isEmpty()) {
            throw new IllegalArgumentException("La placa no puede ser nula o vacía");
        }

        if (marca == null || marca.trim().isEmpty()) {
            throw new IllegalArgumentException("La marca no puede ser nula o vacía");
        }

        if (modelo == null || modelo.trim().isEmpty()) {
            throw new IllegalArgumentException("El modelo no puede ser nulo o vacío");
        }

        if (precioPorDia <= 0) {
            throw new IllegalArgumentException("El precio por día debe ser mayor a 0");
        }

        if (parametroExtra <= 0) {
            throw new IllegalArgumentException("El parámetro extra debe ser mayor a 0");
        }
        String tipoUpper = tipo.toUpperCase().trim();

        if (TIPO_AUTO.equals(tipoUpper)) {
            return new Automovil(placa, marca, modelo, precioPorDia, parametroExtra);
        }
        else if (TIPO_MOTO.equals(tipoUpper)) {
            return new Motocicleta(placa, marca, modelo, precioPorDia, parametroExtra);
        }
        else {
            throw new IllegalArgumentException("Tipo de vehículo no válido: " + tipo +
                    ". Tipos válidos: AUTO, MOTO");
        }
    }

    public static Automovil crearAuto(String placa, String marca, String modelo,
                                      double precioPorDia, int numPuertas) {
        return new Automovil(placa, marca, modelo, precioPorDia, numPuertas);
    }

    public static Motocicleta crearMoto(String placa, String marca, String modelo,
                                        double precioPorDia, int cilindrada) {
        return new Motocicleta(placa, marca, modelo, precioPorDia, cilindrada);
    }

    public static Vehiculo crearVehiculoConValoresPorDefecto(String tipo, String placa,
                                                             String marca, String modelo) {

        String tipoUpper = tipo.toUpperCase().trim();

        if (TIPO_AUTO.equals(tipoUpper)) {
            return new Automovil(placa, marca, modelo, 50.0, 4);
        }
        else if (TIPO_MOTO.equals(tipoUpper)) {
            return new Motocicleta(placa, marca, modelo, 30.0, 150);
        }
        else {
            throw new IllegalArgumentException("Tipo de vehículo no válido: " + tipo);
        }
    }

    public static boolean esTipoValido(String tipo) {
        if (tipo == null) return false;

        String tipoUpper = tipo.toUpperCase().trim();
        return TIPO_AUTO.equals(tipoUpper) || TIPO_MOTO.equals(tipoUpper);
    }

    public static String[] getTiposDisponibles() {
        return new String[]{TIPO_AUTO, TIPO_MOTO};
    }

    public static String getDescripcionTipo(String tipo) {
        String tipoUpper = tipo.toUpperCase().trim();

        if (TIPO_AUTO.equals(tipoUpper)) {
            return "Automóvil";
        }
        else if (TIPO_MOTO.equals(tipoUpper)) {
            return "Motocicleta";
        }
        else {
            return "Desconocido";
        }
    }

    public static String getEtiquetaParametroExtra(String tipo) {
        String tipoUpper = tipo.toUpperCase().trim();

        if (TIPO_AUTO.equals(tipoUpper)) {
            return "Número de puertas";
        }
        else if (TIPO_MOTO.equals(tipoUpper)) {
            return "Cilindrada (cc)";
        }
        else {
            return "Parámetro extra";
        }
    }

}
