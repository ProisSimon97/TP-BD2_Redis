package ar.unrn.tp.modelo;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class VentaSimple {

    private LocalDateTime fecha;
    private String cliente;
    private double montoTotal;
    private String numeroUnico;

    public VentaSimple(LocalDateTime fecha, String cliente, double montoTotal, String numeroUnico) {
        this.fecha = fecha;
        this.cliente = cliente;
        this.montoTotal = montoTotal;
        this.numeroUnico = numeroUnico;
    }

    protected VentaSimple() {
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public String getCliente() {
        return cliente;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public String getNumeroUnico() {
        return numeroUnico;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public void setNumeroUnico(String numeroUnico) {
        this.numeroUnico = numeroUnico;
    }

    public static VentaSimple mapTo(Venta venta) {
        return VentaSimple.builder()
                .fecha(venta.getFecha())
                .cliente(venta.getCliente().getNombre() + " " + venta.getCliente().getApellido())
                .montoTotal(venta.getMontoTotal())
                .numeroUnico(venta.getNumeroUnico())
                .build();
    }
}
