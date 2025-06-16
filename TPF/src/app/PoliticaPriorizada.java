package app;

import java.util.List;

public class PoliticaPriorizada implements PoliticaInterface {

    @Override
    public int cualTransicionDisparar(List<Integer> habilitadas) {
        // Prioriza T6 (modo simple), si está habilitada
        for (int t : habilitadas) {
            if (t == 6) return 6; // Prioridad a T6 (inicio modo simple)
        }

        // Si no está T6, elige la primera disponible
        return (habilitadas.isEmpty() || habilitadas.get(0) == null) ? -1 : habilitadas.get(0);
    }
}

