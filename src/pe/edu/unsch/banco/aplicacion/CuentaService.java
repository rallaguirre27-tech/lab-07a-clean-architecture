package pe.edu.unsch.banco.aplicacion;

import pe.edu.unsch.banco.aplicacion.puertos.MovimientoRepository;
import pe.edu.unsch.banco.dominio.CuentaBancaria;

import java.math.BigDecimal;

public class CuentaService {
    private final MovimientoRepository movimientoRepository;
    private CuentaBancaria cuentaActiva;

    public CuentaService(MovimientoRepository movimientoRepository) {
        if (movimientoRepository == null) {
            throw new IllegalArgumentException("El repositorio de movimientos es obligatorio.");
        }
        this.movimientoRepository = movimientoRepository;
    }

    public String registrarCuenta(String titular, BigDecimal saldoInicial) {
        if (cuentaActiva != null) {
            return "Ya existe una cuenta activa (" + cuentaActiva.getTitular() + "). No se reemplazo la cuenta actual.";
        }
        try {
            this.cuentaActiva = new CuentaBancaria(titular, saldoInicial);
            movimientoRepository.guardar(
                    "Cuenta creada | Titular: " + cuentaActiva.getTitular() +
                            " | Saldo inicial: S/ " + cuentaActiva.getSaldo()
            );
            return "Cuenta registrada";
        } catch (IllegalArgumentException e) {
            return "Operacion rechazada: " + e.getMessage();
        }
    }

    public String depositar(BigDecimal monto) {
        if (cuentaActiva == null) {
            return "Debe registrar una cuenta primero.";
        }
        if (cuentaActiva.depositar(monto)) {
            movimientoRepository.guardar(
                    "Titular: " + cuentaActiva.getTitular() +
                            " | Deposito: S/" + monto +
                            " | Saldo actual: S/" + cuentaActiva.getSaldo()
            );
            return "Deposito exitoso";
        }
        return "Monto invalido";
    }

    public String retirar(BigDecimal monto) {
        if (cuentaActiva == null) {
            return "Debe registrar una cuenta primero.";
        }
        if (cuentaActiva.retirar(monto)) {
            movimientoRepository.guardar(
                    "Titular: " + cuentaActiva.getTitular() +
                            " | Retiro: S/" + monto +
                            " | Saldo actual: S/" + cuentaActiva.getSaldo()
            );
            return "Retiro exitoso";
        }
        return "Fondos insuficientes o monto invalido";
    }

    public String consultarSaldo() {
        if (cuentaActiva == null) {
            return "No hay una cuenta activa registrada.";
        }
        return "Titular: " + cuentaActiva.getTitular() + "\nSaldo: " + cuentaActiva.getSaldo();
    }

    public boolean tieneCuentaActiva() {
        return cuentaActiva != null;
    }
}

