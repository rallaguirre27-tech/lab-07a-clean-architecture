# Registro de Pruebas y Evidencias - Laboratorio 07-A

## 1. Tabla de resultados de casos de prueba (P01 - P07)

| Caso | Acción ejecutada | Resultado esperado | Resultado obtenido | Estado |
| :--- | :--- | :--- | :--- | :--- |
| **P01** | Registrar a Ana con S/ 1000 | Cuenta registrada, saldo 1000 | `Cuenta registrada` (Titular: Ana, Saldo: 1000) | Aprobado |
| **P02** | Depositar S/ 200 | Saldo 1200; movimiento registrado | `Deposito exitoso` (Saldo: 1200 y línea escrita en `movimientos.txt`) | Aprobado |
| **P03** | Retirar S/ 150 | Saldo 1050; movimiento registrado | `Retiro exitoso` (Saldo: 1050 y línea escrita en `movimientos.txt`) | Aprobado |
| **P04** | Retirar S/ 2000 | Operación rechazada; saldo 1050 | `Fondos insuficientes o monto invalido` (Saldo intacto: 1050) | Aprobado |
| **P05** | Depositar S/ -10 | Operación rechazada; saldo intacto | `Monto invalido` (Saldo intacto: 1050) | Aprobado |
| **P06** | Registrar nueva cuenta sin reiniciar | No reemplaza la cuenta activa | `Ya existe una cuenta activa (Ana). No se reemplazo la cuenta actual.` | Aprobado |
| **P07** | Salir y abrir nuevamente | Advertir que la cuenta no se restaura automáticamente | Al iniciar muestra la advertencia y al consultar saldo indica que no hay cuenta activa | Aprobado |

---

## 2. Verificación de `movimientos.txt` (Historial vs. Persistencia completa)

Tras ejecutar P01–P06, el archivo `movimientos.txt` registra únicamente las operaciones válidas realizadas en la sesión (creación de cuenta, depósito de S/ 200 y retiro de S/ 150) con fecha y hora.

> **Nota técnica importante:** `movimientos.txt` funciona exclusivamente como un **historial/bitácora de movimientos** y **no** constituye un almacenamiento completo de cuentas. Por ello, al cerrar y volver a abrir la aplicación (P07), el estado de la cuenta en memoria no se reconstruye ni se restaura automáticamente.

---

## 3. Evidencias registradas

- Captura de ejecución de casos P01–P06 en consola.
- Captura de la opción **4. Consultar saldo** confirmando titular `Ana` y saldo `1050`.
- Captura del contenido generado en `movimientos.txt`.
- Captura del reinicio de la aplicación (P07) mostrando la advertencia de no restauración automática.
