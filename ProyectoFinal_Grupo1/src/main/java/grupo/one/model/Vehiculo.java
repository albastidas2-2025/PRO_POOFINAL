package grupo.one.model;

public abstract class Vehiculo implements Facturable {
    private String placa;
    private String marca;
    private String modelo;
    private double precioDia;

    private static int contadorVehiculos = 0;

    public Vehiculo() {
        this.placa = "";
        this.marca = "";
        this.modelo = "";
        this.precioDia = 1.0;
        contadorVehiculos++;
    }

    public Vehiculo(String placa, String marca, String modelo, double precioDia) {

        if (placa == null || placa.trim().isEmpty()) {
            this.placa = "";
        } else {
            this.placa = placa.trim();
        }

        this.marca = marca == null ? "" : marca.trim();
        this.modelo = modelo == null ? "" : modelo.trim();

        if (precioDia <= 0) {
            this.precioDia = 1.0;
        } else {
            this.precioDia = precioDia;
        }

        contadorVehiculos++;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        if (placa != null && !placa.trim().isEmpty()) {
            this.placa = placa.trim();
        }
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca == null ? "" : marca.trim();
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo == null ? "" : modelo.trim();
    }

    public double getPrecioDia() {
        return precioDia;
    }

    public void setPrecioDia(double precioDia) {
        if (precioDia > 0) {
            this.precioDia = precioDia;
        }
    }
    public static int getContadorVehiculos() {
        return contadorVehiculos;
    }
    @Override
    public double calcularCostoTotal(int dias) {
        return dias > 0 ? precioDia * dias : 0;
    }

    public abstract String getInfoDetallada();

    @Override
    public String toString() {
        return placa + " - " + marca + " " + modelo;
    }


}
