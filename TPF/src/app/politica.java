package app;

import java.util.List;
import java.util.Random;

public class politica {
    private final Random random;

    public politica() {
        this.random = new Random();
    }

    /**
     * Política 1: elige una transición aleatoria entre las habilitadas.
     * Retorna -1 si no hay ninguna habilitada.
     */
public int elegirTransicion(List<Integer> habilitadas) {
    if (habilitadas == null || habilitadas.isEmpty()) {
        return -1;
    }
    return habilitadas.get(random.nextInt(habilitadas.size()));
    }

    /**
     * Política 2: prioriza las transiciones asociadas al modo de procesamiento simple (T6 y T7).
     * Si no están habilitadas, elige otra aleatoriamente.
     */
    public int eleegirTransicionAAleatoria(List<Integer> habilitadas) {
        if (habilitadas == null || habilitadas.isEmpty()) {
            return -1;
        }
        // Verifica si T6 o T7 están habilitadas
        if (habilitadas.contains(6) || habilitadas.contains(7)) {
            if (habilitadas.contains(6)) {
                return 6;
            } else {
                return 7;
            }
        }
        // Si no, elige aleatoriamente
        return habilitadas.get(random.nextInt(habilitadas.size()));
    }
}