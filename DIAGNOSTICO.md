# Diagnóstico Arquitectónico - SistemaBancario (Código Inicial)

## 1. Tabla de responsabilidades y cambios que obligarían a modificar la clase

| Responsabilidad | Dónde aparece en `SistemaBancario.java` | Cambio de requisitos que obligaría a modificar `SistemaBancario` |
| :--- | :--- | :--- |
| **Capturar entrada** | Uso de `Scanner` (`nextLine()`, `nextDouble()`, `nextInt()`) en `registrarCuenta()`, `depositar()`, `retirar()` y `main` | Cambiar de consola (`Scanner`) a interfaz gráfica, API REST o pruebas automatizadas sin entrada interactiva |
| **Validar operaciones** | Condiciones `monto > 0` en `depositar()` y `monto > 0 && monto <= saldo` en `retirar()` | Cambiar reglas de negocio (por ejemplo, permitir sobregiro, comisiones o límites diarios de retiro) |
| **Actualizar saldos** | Modificación directa de `saldo += monto` y `saldo -= monto` sobre tipo `double` | Modificar cómo se calcula el saldo o cambiar el tipo numérico (por ejemplo, a `BigDecimal` por precisión monetaria) |
| **Escribir archivos** | Escritura en `movimientos.txt` mediante `FileWriter` dentro de `guardarMovimiento(...)` | Guardar movimientos en una base de datos relacional, servicio externo o cambiar el formato/ruta del archivo |
| **Imprimir resultados** | Llamadas a `System.out.print` y `System.out.println` en los métodos y en el menú | Cambiar el idioma/formato de salida, enviar logs a un sistema centralizado o desacoplar la presentación |
| **Coordinar el flujo** | Bucle `do-while` y `switch` en `main` despachando las cinco opciones | Agregar nuevos comandos, cambiar el orden/flujo del menú o reutilizar la lógica desde otro punto de entrada |

---

## 2. Respuestas a preguntas orientadoras

### ¿Qué sucede si cambiamos el archivo por una base de datos?
Como `guardarMovimiento(...)` instancia directamente `FileWriter("movimientos.txt", true)` dentro de `SistemaBancario`, migrar a una base de datos obliga a editar la clase principal del sistema. Al estar acoplada a una tecnología concreta de almacenamiento, el cambio de persistencia impacta directamente en el código del flujo bancario.

### ¿Por qué la entidad bancaria no debería utilizar `Scanner` o `FileWriter`?
Una entidad de dominio debe representar el estado y las reglas del negocio, no cómo se leen datos del usuario ni cómo se escriben archivos en disco. Si `CuentaBancaria` usara `Scanner` o `FileWriter`, sería difícil probar las reglas de depósito/retiro de forma aislada y cualquier cambio de interfaz o almacenamiento contaminaría el núcleo del negocio.

### ¿Qué pasaría al registrar una cuenta distinta?
En el código inicial, `registrarCuenta()` asigna directamente `titular = ...` y `saldo = ...` sin comprobar si ya existe una cuenta activa. Como resultado, al registrar una segunda cuenta se sobrescribe y reemplaza la cuenta anterior en memoria.

---

## 3. Defectos identificados y alcance de `movimientos.txt`

1. **Reemplazo silencioso de la cuenta activa:** Registrar una nueva cuenta sin reiniciar reemplaza los datos (`titular` y `saldo`) de la cuenta anterior en lugar de informar que ya existe una cuenta activa.
2. **Alcance limitado de `movimientos.txt`:** El archivo `movimientos.txt` funciona únicamente como un historial/bitácora de eventos con marca de tiempo (`LocalDateTime.now()`), pero **no** persiste ni restaura automáticamente el estado completo de la cuenta (ni garantiza consistencia total como almacén de cuentas) al reiniciar el programa.
3. **Alta concentración de responsabilidades (acoplamiento):** Entrada/salida, reglas de negocio, control de flujo y persistencia conviven en una sola clase (`SistemaBancario`), dificultando el mantenimiento y la evolución del software.

---

## 4. Justificación de la refactorización (Clean Architecture)

Separar el código en capas (`dominio`, `aplicacion` e `infraestructura` bajo `pe.edu.unsch.banco`) y aplicar inversión de dependencias permite:
- Mantener las reglas bancarias aisladas en `CuentaBancaria` (sin dependencias hacia `aplicacion` ni `infraestructura`).
- Coordinar los casos de uso en `CuentaService` dependiendo de la abstracción `MovimientoRepository` (sin importar de `infraestructura`).
- Aislar `ArchivoMovimientoRepository` y `Main` en los bordes externos para que cambiar la presentación o la persistencia no rompa el núcleo del negocio.
