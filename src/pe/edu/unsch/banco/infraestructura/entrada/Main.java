package pe.edu.unsch.banco.infraestructura.entrada;

import pe.edu.unsch.banco.aplicacion.CuentaService;
import pe.edu.unsch.banco.aplicacion.puertos.MovimientoRepository;
import pe.edu.unsch.banco.infraestructura.salida.ArchivoMovimientoRepository;

import java.math.BigDecimal;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        MovimientoRepository repositorio = new ArchivoMovimientoRepository();
        CuentaService servicio = new CuentaService(repositorio);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Nota: movimientos.txt registra el historial de movimientos; al reiniciar no se restaura automaticamente la cuenta.");

        int opcion;
        do {
            System.out.println("\n=== SISTEMA BANCARIO ===");
            System.out.println("1. Registrar cuenta");
            System.out.println("2. Depositar dinero");
            System.out.println("3. Retirar dinero");
            System.out.println("4. Consultar saldo");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opcion: ");

            String lineaOpcion = scanner.nextLine().trim();
            try {
                opcion = Integer.parseInt(lineaOpcion);
            } catch (NumberFormatException e) {
                System.out.println("Opcion invalida");
                opcion = -1;
                continue;
            }

            switch (opcion) {
                case 1 -> {
                    System.out.print("Ingrese titular: ");
                    String titular = scanner.nextLine();
                    BigDecimal saldoInicial = leerMonto(scanner, "Ingrese saldo inicial: ");
                    if (saldoInicial != null) {
                        System.out.println(servicio.registrarCuenta(titular, saldoInicial));
                    }
                }
                case 2 -> {
                    BigDecimal monto = leerMonto(scanner, "Monto a depositar: ");
                    if (monto != null) {
                        System.out.println(servicio.depositar(monto));
                    }
                }
                case 3 -> {
                    BigDecimal monto = leerMonto(scanner, "Monto a retirar: ");
                    if (monto != null) {
                        System.out.println(servicio.retirar(monto));
                    }
                }
                case 4 -> System.out.println(servicio.consultarSaldo());
                case 5 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opcion invalida");
            }
        } while (opcion != 5);

        scanner.close();
    }

    private static BigDecimal leerMonto(Scanner scanner, String mensaje) {
        System.out.print(mensaje);
        String entrada = scanner.nextLine().trim().replace(",", ".");
        try {
            return new BigDecimal(entrada);
        } catch (NumberFormatException e) {
            System.out.println("Monto invalido");
            return null;
        }
    }
}

