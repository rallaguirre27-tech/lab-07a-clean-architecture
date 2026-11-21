package pe.edu.unsch.banco.dominio;

import java.math.BigDecimal;
import java.util.Objects;

public class CuentaBancaria {
    private final String titular;
    private BigDecimal saldo;

    public CuentaBancaria(String titular, BigDecimal saldoInicial) {
        if (titular == null || titular.isBlank()) {
            throw new IllegalArgumentException("El titular no puede estar vacio.");
        }
        if (saldoInicial == null || saldoInicial.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El saldo inicial no puede ser negativo.");
        }
        this.titular = titular.trim();
        this.saldo = saldoInicial;
    }

    public boolean depositar(BigDecimal monto) {
        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        this.saldo = this.saldo.add(monto);
        return true;
    }

    public boolean retirar(BigDecimal monto) {
        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        if (monto.compareTo(this.saldo) > 0) {
            return false;
        }
        this.saldo = this.saldo.subtract(monto);
        return true;
    }

    public String getTitular() {
        return titular;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CuentaBancaria that)) return false;
        return Objects.equals(titular, that.titular);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titular);
    }
}

