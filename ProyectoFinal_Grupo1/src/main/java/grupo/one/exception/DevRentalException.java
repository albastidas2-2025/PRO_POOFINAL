package grupo.one.exception;

public class DevRentalException extends Exception {
    private final String codigoError;
    private final String modulo;

    public DevRentalException(String mensaje) {
        super(mensaje);
        this.codigoError = "ERR-000";
        this.modulo = "GENERAL";
    }

    public DevRentalException(String mensaje, String codigoError, String modulo) {
        super(mensaje);
        this.codigoError = codigoError;
        this.modulo = modulo;
    }

    public DevRentalException(String mensaje, Throwable causa) {
        super(mensaje, causa);
        this.codigoError = "ERR-000";
        this.modulo = "GENERAL";
    }

    public String getCodigoError() {
        return codigoError;
    }

    public String getModulo() {
        return modulo;
    }

    @Override
    public String getMessage() {
        return String.format("[%s - %s] %s",
                codigoError, modulo, super.getMessage());
    }
}
