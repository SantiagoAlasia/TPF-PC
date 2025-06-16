package app;

public class RdP {

    private static final int P = 13; // Cantidad de plazas
    private static final int T = 12; // Cantidad de transiciones

    private final int[][] Ipos; // matriz de salidas
    private final int[][] Ineg; // matriz de entradas
    private final int[] marcado;

    public RdP(int[][] Ipos, int[][] Ineg, int[] marcadoInicial) {
        this.Ipos = Ipos;
        this.Ineg = Ineg;
        this.marcado = new int[P];
        System.arraycopy(marcadoInicial, 0, this.marcado, 0, P);
    }

    /**
     * Verifica si la transición t está habilitada
     */
    public boolean estaHabilitada(int t) {
        for (int p = 0; p < P; p++) {
            if (marcado[p] < Ineg[p][t]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Dispara la transición t si está habilitada.
     * Actualiza el marcado.
     */
    public boolean disparar(int t) {
        if (!estaHabilitada(t)) return false;

        // Quitar tokens (Ineg)
        for (int p = 0; p < P; p++) {
            marcado[p] -= Ineg[p][t];
        }

        // Agregar tokens (Ipos)
        for (int p = 0; p < P; p++) {
            marcado[p] += Ipos[p][t];
        }

        return true;
    }

    /**
     * Devuelve el marcado actual
     */
    public int[] getMarcado() {
        return marcado;
    }

    /**
     * Devuelve un vector binario con las transiciones habilitadas (1 = habilitada)
     */
    public int[] sensibilizadas() {
        int[] s = new int[T];
        for (int t = 0; t < T; t++) {
            s[t] = estaHabilitada(t) ? 1 : 0;
        }
        return s;
    }

    public int cantidadTransiciones() {
        return T;
    }

    public int cantidadPlazas() {
        return P;
    }
}
