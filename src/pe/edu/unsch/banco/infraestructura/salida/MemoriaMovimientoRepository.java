package pe.edu.unsch.banco.infraestructura.salida;

import pe.edu.unsch.banco.aplicacion.puertos.MovimientoRepository;

import java.util.ArrayList;
import java.util.List;

public class MemoriaMovimientoRepository implements MovimientoRepository {
    private final List<String> movimientos = new ArrayList<>();

    @Override
    public void guardar(String movimiento) {
        movimientos.add(movimiento);
    }

    public List<String> listarMovimientos() {
        return List.copyOf(movimientos);
    }
}




