
package pe.edu.unsch.banco.infraestructura;

import pe.edu.unsch.banco.aplicacion.MovimientoRepository;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class ArchivoMovimientoRepository implements MovimientoRepository {
    private static final String ARCHIVO_MOVIMIENTOS = "movimientos.txt";

    @Override
    public void guardar(String movimiento) {
        try (FileWriter archivo = new FileWriter(ARCHIVO_MOVIMIENTOS, true)) {
            archivo.write(LocalDateTime.now() + " | " + movimiento + "\n");
        } catch (IOException e) {
            System.out.println("Error al guardar");
        }
    }
}
