
package pe.edu.unsch.banco.aplicacion.puertos;

import java.math.BigDecimal;

public interface CuentaUseCase {
    String registrarCuenta(String titular, BigDecimal saldoInicial);
    String depositar(BigDecimal monto);
    String retirar(BigDecimal monto);
    String consultarSaldo();
    boolean tieneCuentaActiva();
}




