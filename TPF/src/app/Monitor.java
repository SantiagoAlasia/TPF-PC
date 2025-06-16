package app;

import java.util.ArrayList;
import java.util.List;

public class Monitor implements MonitorInterface {

    private final int[] marcado;
    private final int[][] Ipos; //entrada
    private final int[][] Ineg;//salida
    private final boolean[] transicionesTemporales;

    public Monitor(int[] marcadoInicial, int[][] Ipos, int[][] Ineg, boolean[] temporales) {
        this.marcado = marcadoInicial.clone(); // Copia defensiva para evitar modificaciones externas
        this.Ipos = Ipos;
        this.Ineg = Ineg;
        this.transicionesTemporales = temporales;
    }

    // --- INVARIANTES DE PLAZA ---
    // Cada fila es un invariante: coeficientes para cada plaza
    private final int[][] invariantesPlaza = {
        {0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // Invariante 1
    };
    // Valores esperados para cada invariante
    private final int[] valoresEsperados = {
        1, // Valor esperado para invariante 1
    };

    // Método para verificar invariantes de plaza
    private void verificarInvariantes() {
        for (int i = 0; i < invariantesPlaza.length; i++) {
            int suma = 0;
            for (int p = 0; p < marcado.length; p++) {
                suma += invariantesPlaza[i][p] * marcado[p];
            }
            if (suma != valoresEsperados[i]) {
                System.out.println("[INVARIANTE PLAZA] NO SE CUMPLE el invariante " + (i+1) + ": valor actual = " + suma + ", esperado = " + valoresEsperados[i]);
            }
        }
    }

    @Override
    public synchronized boolean fireTransition(int t) {
        if (!estaHabilitada(t)) {
            return false; // La transición no está habilitada
        }

        //Quitar los tokens de las plazas de entrada
        for (int p=0; p<marcado.length; p++) {
            marcado[p] -= Ineg[p][t];
        }

        //Simular la demora si la transicion es temporal
        if (transicionesTemporales[t]) {
            long start = System.currentTimeMillis();
            long waitTime = 100; // 100 ms
            while (true) {
                long elapsed = System.currentTimeMillis() - start;
                long remaining = waitTime - elapsed;
                if (remaining <= 0) break;
                try {
                    wait(remaining);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return false;
                }
            }
        }

        //Agregar los tokens a las plazas de salida
        for (int p=0; p<marcado.length; p++) {
            marcado[p] += Ipos[p][t];
        }
        // Verificar invariantes de plaza después de cada disparo
        verificarInvariantes();
        // Notificar a otros hilos que el estado ha cambiado
        notifyAll();
        return true; // La transición se disparó exitosamente
    }
    private boolean estaHabilitada(int t) {
        for (int p = 0; p < marcado.length; p++) {
            if (marcado[p] < Ineg[p][t]) {
                return false; // Si alguna plaza no tiene suficientes tokens, la transición no está habilitada
            }
        }
        return true; // Todas las plazas tienen suficientes tokens
    }

    // Agrego método público para obtener transiciones habilitadas
    @Override
    public List<Integer> getHabilitadas() {
        List<Integer> habilitadas = new ArrayList<>();
        for (int t = 0; t < Ineg[0].length; t++) {
            if (estaHabilitada(t)) {
                habilitadas.add(t);
            }
        }
        return habilitadas;
    }
}