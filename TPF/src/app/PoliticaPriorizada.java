package app;

import java.util.List;

public class PoliticaPriorizada implements PoliticaInterface {

    @Override
    public int cualTransicionDisparar(List<Integer> habilitadas) {
        // Prioriza T5 (modo simple), si está habilitada
        for (int t : habilitadas) {
            if (t == 5) return 5; // Prioridad a T5 (inicio modo simple)
        }

        // Si no está T5, elige la primera disponible
        return (habilitadas.isEmpty() || habilitadas.get(0) == null) ? -1 : habilitadas.get(0);
    }
}

