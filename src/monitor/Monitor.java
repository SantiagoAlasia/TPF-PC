package monitor;

import java.util.concurrent.locks.ReentrantLock;
import log.LoggerTP;

public class Monitor implements MonitorInterface {
    private final ReentrantLock lock = new ReentrantLock();

    private final int PLAZAS = 12;
    private final int TRANSICIONES = 12;

    private final int[] marcado = new int[PLAZAS];

    private final int[][] pre = new int[PLAZAS][TRANSICIONES];
    private final int[][] post = new int[PLAZAS][TRANSICIONES];
    private final int[][] incidencia = new int[PLAZAS][TRANSICIONES];
    private final int [] tiempos = new int [TRANSICIONES];

    public Monitor() {
        inicializarRedPetri();
        inicializarTiempos();
    }

    private void inicializarTiempos() {
        tiempos[1] = 100;  // T1: acceso al bus
        tiempos[3] = 150;  // T3: medio paso 1
        tiempos[4] = 150;  // T4: medio paso 2
        tiempos[6] = 200;  // T6: simple
        tiempos[8] = 120;  // T8: complejo paso 1
        tiempos[9] = 120;  // T9: complejo paso 2
        tiempos[10] = 120; // T10: complejo paso 3
    }

    private void inicializarRedPetri() {
        // Marcado inicial
        marcado[0] = 200; // P0: datos iniciales
        marcado[2] = 1;   // P2: bus libre
        marcado[6] = 1;   // P6: unidad de procesamiento libre

        // T1: P0 + P2 → P1
        pre[0][1] = 1;
        pre[2][1] = 1;
        post[1][1] = 1;

        // T2: P1 → P2 + P3
        pre[1][2] = 1;
        post[2][2] = 1;
        post[3][2] = 1;

        // T3: P3 → P4
        pre[3][3] = 1;
        post[4][3] = 1;

        // T4: P4 → P5
        pre[4][4] = 1;
        post[5][4] = 1;

        // T5: P5 → P6 + P11
        pre[5][5] = 1;
        post[6][5] = 1;
        post[11][5] = 1;

        // T6: P3 → P7
        pre[3][6] = 1;
        post[7][6] = 1;

        // T7: P7 → P6 + P11
        pre[7][7] = 1;
        post[6][7] = 1;
        post[11][7] = 1;

        // T8: P3 → P8
        pre[3][8] = 1;
        post[8][8] = 1;

        // T9: P8 → P9
        pre[8][9] = 1;
        post[9][9] = 1;

        // T10: P9 → P10
        pre[9][10] = 1;
        post[10][10] = 1;

        // T11: P10 → P6 + P11
        pre[10][11] = 1;
        post[6][11] = 1;
        post[11][11] = 1;

        // Matriz de incidencia
        for (int i = 0; i < PLAZAS; i++) {
            for (int j = 0; j < TRANSICIONES; j++) {
                incidencia[i][j] = post[i][j] - pre[i][j];
            }
        }
    }

    private boolean isEnabled(int t) {
        for (int p = 0; p < PLAZAS; p++) {
            if (pre[p][t] > 0 && marcado[p] < pre[p][t]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean fireTransition(int t) {
        lock.lock();
        try {
            if (!isEnabled(t)) return false;

            for (int p = 0; p < PLAZAS; p++) {
                marcado[p] += incidencia[p][t];
            }
            if (tiempos[t] > 0) {
                try {
                    Thread.sleep(tiempos[t]);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println(">> Esperando " + tiempos[t] + "ms por semantica temporal en T" + t);
            System.out.println("Transicion " + t + " disparada: " + nombreTransicion(t));
            for (int i = 0; i < PLAZAS; i++) {
                System.out.print("P" + i + ": " + marcado[i] + " ");
            }
            LoggerTP.log(t, nombreTransicion(t));
            System.out.println();

            return true;
        } finally {
            lock.unlock();
        }
    }

    private String nombreTransicion(int t) {
        return switch (t) {
            case 1 -> "T1 - Entrada (P0+P2 a P1)";
            case 2 -> "T2 - Buffer (P1 a P2+P3)";
            case 3 -> "T3 - Medio (P3 a P4)";
            case 4 -> "T4 - Medio (P4 a P5)";
            case 5 -> "T5 - Medio (P5 a P6+P11)";
            case 6 -> "T6 - Simple (P3 a P7)";
            case 7 -> "T7 - Simple (P7 a P6+P11)";
            case 8 -> "T8 - Complejo (P3 a P8)";
            case 9 -> "T9 - Complejo (P8 a P9)";
            case 10 -> "T10 - Complejo (P9 a P10)";
            case 11 -> "T11 - Complejo (P10 a P6+P11)";
            default -> "Desconocida";
        };
    }
}
