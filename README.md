# Laboratorio 07-A: Arquitectura Limpia (Clean Architecture)

- **Curso:** Construcción y Evolución de Software (IS-388)
- **Modalidad:** Trabajo individual (asumiendo los roles de dominio, aplicación, infraestructura de archivo, menú/pruebas y documentación)
- **Repositorio:** `https://github.com/rallaguirre27-tech/lab-07a-clean-architecture.git`

---

## 1. Instrucciones de ejecución

### Requisitos previos
- **JDK 17 o superior** (probado con JDK 27 en IntelliJ IDEA).
- **IntelliJ IDEA** y **Git**.

### Ejecución desde IntelliJ IDEA
1. Abre la carpeta `BancoInicial` en IntelliJ IDEA.
2. Verifica que `src` esté marcada como **Sources Root**.
3. Ejecuta la clase `pe.edu.unsch.banco.Main`.

### Ejecución desde consola (Git Bash)
```bash
mkdir -p out/refactor
javac -d out/refactor $(find src/pe/edu/unsch/banco -name "*.java")
java -cp out/refactor pe.edu.unsch.banco.Main
Operaciones disponibles
Registrar cuenta

Depositar dinero

Retirar dinero

Consultar saldo

Salir
2. Estructura de paquetes y archivos
BancoInicial/
├── DIAGNOSTICO.md
├── PRUEBAS.md
├── README.md
├── movimientos.txt
└── src/
    ├── SistemaBancario.java
    └── pe/
        └── edu/
            └── unsch/
                └── banco/
                    ├── Main.java
                    ├── aplicacion/
                    │   ├── CuentaService.java
                    │   └── MovimientoRepository.java
                    ├── dominio/
                    │   └── CuentaBancaria.java
                    └── infraestructura/
                        └── ArchivoMovimientoRepository.java
3. Regla de dependencias y decisión técnica (BigDecimal)
Regla de dependencias:

dominio (CuentaBancaria) no importa clases de aplicacion ni de infraestructura.

aplicacion (CuentaService, MovimientoRepository) no importa clases de infraestructura.

infraestructura (ArchivoMovimientoRepository) implementa la interfaz MovimientoRepository definida en aplicacion (inversión de dependencias).

Main actúa como punto de entrada/presentación, lee entradas del usuario, maneja errores de formato e inyecta ArchivoMovimientoRepository en CuentaService.

Uso de BigDecimal: Se emplea BigDecimal en lugar de double para representar saldos y montos monetarios, evitando errores de precisión decimal binaria en operaciones financieras.

Registro múltiple y persistencia: Si ya existe una cuenta activa, intentar registrar otra informa que ya existe sin reemplazarla. Asimismo, movimientos.txt guarda el historial de movimientos, pero no restaura automáticamente el estado de la cuenta al reiniciar la aplicación.
4. Preguntas para sustentación
¿Qué problema concreto resolvieron al separar CuentaBancaria de Main?
Se evitó que la entidad de negocio dependiera de la consola (Scanner y System.out) o del flujo del menú. Así, CuentaBancaria concentra únicamente el estado de la cuenta y las reglas de validación de depósitos, retiros y saldo inicial, pudiendo evolucionar la interfaz sin alterar las reglas bancarias.

¿Por qué CuentaService recibe MovimientoRepository en su constructor?
Para aplicar inversión de dependencias e inyección por constructor. De este modo, CuentaService depende únicamente de la abstracción MovimientoRepository y no conoce si los movimientos se guardan en un archivo de texto, en memoria o en una base de datos.

¿Qué archivos deberían cambiar si reemplazamos FileWriter por JDBC/H2?
Principalmente se crearía/modificaría el adaptador de infraestructura (por ejemplo, JdbcMovimientoRepository en pe.edu.unsch.banco.infraestructura) y se inyectaría esa implementación en Main. CuentaBancaria, MovimientoRepository y CuentaService no requieren cambiar sus reglas de negocio ni su contrato.

¿En qué capa se valida que el retiro no exceda el saldo?
En la capa de dominio, dentro de CuentaBancaria.retirar(BigDecimal monto), donde se verifica que el monto sea positivo y no supere el saldo disponible.

¿Qué diferencia existe entre registrar movimientos y persistir cuentas?
Registrar movimientos guarda un historial cronológico de las operaciones realizadas (alta, depósitos, retiros) en movimientos.txt, mientras que persistir cuentas implica almacenar y recuperar el estado completo y vigente de las entidades/cuentas (titular, saldo actual, estado) entre ejecuciones del sistema.
5. Dificultades y Conclusiones
Dificultades
Corregir el defecto de diseño del código inicial donde registrar una segunda cuenta sobrescribía la cuenta activa sin advertencia.

Mantener la separación estricta de capas (dominio y aplicacion libres de importaciones de infraestructura) y manejar con BigDecimal las entradas numéricas desde consola.

Conclusiones
Dividir el sistema en dominio, aplicacion e infraestructura bajo pe.edu.unsch.banco reduce el acoplamiento y hace el código más legible y mantenible.

Usar la interfaz MovimientoRepository e inyectarla en CuentaService permite cambiar la tecnología de almacenamiento sin modificar el núcleo del negocio.
