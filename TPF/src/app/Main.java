package app;

import Segmentos.SegmentoA;
import Segmentos.SegmentoB;
import Segmentos.SegmentoC;
import Segmentos.SegmentoD;
import Segmentos.SegmentoE;

public class Main {
    public static void main (String[] args) {
        // 1. MATRICES 
        int plazas = 13; // Cantidad de plazas
        int transiciones = 12; // Cantidad de transiciones

        int[][] Ipos = new int[plazas][transiciones]; 
        int[][] Ineg = new int[plazas][transiciones]; 

        //entradas (Ineg)
        // ---------- Entrada (Ineg) ----------
        Ineg[0][0] = 1; // P0 → T0
        Ineg[2][0] = 1; // P2 → T0
        Ineg[1][1] = 1; // P1 → T1
        Ineg[3][2] = 1; // P3 → T2
        Ineg[4][3] = 1; // P4 → T3
        Ineg[5][4] = 1; // P5 → T4
        Ineg[3][5] = 1; // P3 → T5
        Ineg[6][2] = 1; // P6 → T2
        Ineg[6][5] = 1; // P6 → T5
        Ineg[3][6] = 1; // P3 → T6
        Ineg[6][6] = 1; // P6 → T6
        Ineg[7][7] = 1; // P7 → T7
        Ineg[6][7] = 1; // P6 → T7
        Ineg[3][8] = 1; // P3 → T8
        Ineg[8][9] = 1; // P8 → T9
        Ineg[9][10] = 1;// P9 → T10
        Ineg[10][10] = 1;// P10 → T10
        Ineg[11][11] = 1;// P11 → T11

        // ---------- Salida (Ipos) ----------
        Ipos[1][0] = 1; // T0 → P1
        Ipos[2][1] = 1; // T1 → P2
        Ipos[3][1] = 1; // T1 → P3
        Ipos[4][2] = 1; // T2 → P4
        Ipos[5][3] = 1; // T3 → P5
        Ipos[11][4] = 1; // T4 → P11
        Ipos[7][5] = 1; // T5 → P7
        Ipos[11][6] = 1; // T6 → P11
        Ipos[8][7] = 1; // T7 → P8
        Ipos[9][8] = 1; // T8 → P9
        Ipos[10][9] = 1;// T9 → P10
        Ipos[11][10] = 1;// T10 → P11
        Ipos[0][11] = 1; // T11 → P0
        Ipos[6][10] = 1; // T10 libera unidad de procesamiento
        Ipos[6][4] = 1;  // T4 libera unidad de procesamiento
        Ipos[6][6] = 1;  // T6 libera unidad de procesamiento
        Ipos[6][5] = 1;  // T5 libera unidad de procesamiento   

        // 2. MARCADO INICIAL
        int[] marcadoInicial = new int[plazas];
        marcadoInicial[0] = 3; // P0 tiene 3 tokens (datos para ingresar)
        marcadoInicial[2] = 1; // P2 tiene 1 token (bus libre)
        marcadoInicial[6] = 1; // P6 tiene 1 token (unidad de procesamiento libre)

        // 3. TRANSICIONES TEMPORALES
        boolean[] transicionesTemporales ={
            false, // T0
            true,  // T1
            false, // T2
            true,  // T3
            true,  // T4
            false, // T5
            true,  // T6
            false, // T7
            true,  // T8
            true,  // T9
            true,  // T10
            false  // T11
        };

        // 4. CREACION DEL MONITOR
        Monitor monitor = new Monitor(marcadoInicial, Ipos, Ineg, transicionesTemporales);

        // 5. INICIAR LOGGER
        LoggerTP.iniciar();

        // 6. CREAR E INICIAR HILOS DE SEGMENTOS
        Thread[] hilos = new Thread[5];
        hilos[0] = new Thread(new SegmentoA(monitor), "SegmentoA");
        hilos[1] = new Thread(new SegmentoB(monitor), "SegmentoB");
        hilos[2] = new Thread(new SegmentoC(monitor), "SegmentoC");
        hilos[3] = new Thread(new SegmentoD(monitor), "SegmentoD");
        hilos[4] = new Thread(new SegmentoE(monitor), "SegmentoE");

        for (Thread hilo : hilos) {
            hilo.start();
        }

        // 7. ESPERAR UN TIEMPO DE SIMULACION (ejemplo: 30 segundos)
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // 8. INTERRUMPIR LOS HILOS Y FINALIZAR LOGGER
        for (Thread hilo : hilos) {
            hilo.interrupt();
        }
        LoggerTP.finalizar();
    }
}