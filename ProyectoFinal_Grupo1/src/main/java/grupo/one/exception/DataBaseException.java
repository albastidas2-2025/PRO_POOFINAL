package grupo.one.exception;

import java.awt.image.DataBufferDouble;

public class DataBaseException extends RuntimeException {

    private final String operacion;
    private final String coleccion;

    public DataBaseException(String mensaje, String operacion, String coleccion) {
        super(String.format("Error en BD | Operación: %s | Colección: %s | Detalle: %s",
                operacion, coleccion, mensaje));
        this.operacion = operacion;
        this.coleccion = coleccion;
    }

    public DataBaseException(String mensaje, String operacion, String coleccion, Throwable causa) {
        super(String.format("Error en BD | Operación: %s | Colección: %s | Detalle: %s",
                operacion, coleccion, mensaje), causa);
        this.operacion = operacion;
        this.coleccion = coleccion;
    }

    public String getOperacion() {
        return operacion;
    }
    public String getColeccion() {
        return coleccion;
    }

    public static DataBaseException conexionFallida(String mensaje, Throwable causa) {
        return new DataBaseException(mensaje, "CONEXION", "SISTEMA", causa);
    }

    public static DataBaseException insercionFallida(String coleccion, String mensaje) {
        return new DataBaseException(mensaje, "INSERT", coleccion);
    }

    public static DataBaseException consultaFallida(String coleccion, String mensaje) {
        return new DataBaseException(mensaje, "QUERY", coleccion);
    }
}
