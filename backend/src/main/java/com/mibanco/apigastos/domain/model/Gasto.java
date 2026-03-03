package com.mibanco.apigastos.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Gasto {
    private String id;
    private String titulo;
    private String motivo;
    private LocalDate fecha;
    private BigDecimal monto;

    public Gasto(String id, String titulo, String motivo, LocalDate fecha, BigDecimal monto) {
        this.id = id;
        this.titulo = titulo;
        this.motivo = motivo;
        this.fecha = fecha;
        this.monto = monto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
}
