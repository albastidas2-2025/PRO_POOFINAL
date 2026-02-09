package grupo.one.exception;

public class ValidationException extends DevRentalException {

    private final String campo;
    private final String valor;

    public ValidationException(String mensaje, String campo, String valor) {
        super(mensaje, "ERR-400", "VALIDACION");
        this.campo = campo;
        this.valor = valor;
    }

    public ValidationException(String mensaje, String campo) {
        this(mensaje, campo, null);
    }

    public String getCampo() {
        return campo;
    }

    public String getValor() {
        return valor;
    }

    @Override
    public String getMessage() {
        if (valor != null) {
            return String.format("Validación fallida | Campo: '%s' | Valor: '%s' | Error: %s",
                    campo, valor, super.getMessage());
        }
        return String.format("Validación fallida | Campo: '%s' | Error: %s",
                campo, super.getMessage());
    }

    public static ValidationException campoRequerido(String campo) {
        return new ValidationException("El campo es requerido", campo);
    }

    public static ValidationException valorInvalido(String campo, String valor) {
        return new ValidationException("Valor inválido", campo, valor);
    }
    public static ValidationException rangoInvalido(String campo, Number valor, Number min, Number max) {
        return new ValidationException(
                String.format("Debe estar entre %s y %s", min, max),
                campo,
                valor != null ? valor.toString() : "null"
        );
    }
}