/* RESPONSABILIDADES DEL MONITOR: 
 * 1. Modelar la red de petri de forma genèrica
 * 2. Saber que transiciones están habilitadas
 * 3. Disparar transiciones solo si estan sensibilizadas
 * 4. Actualizar el marcado (estado actual de los tokens)
 * 5. Ser thread-safe, es decir, que no se pueda acceder a su estado interno de forma concurrente
 */


package monitor;

import java.util.concurrent.locks.ReentrantLock;

public class Monitor implements MonitorInterface {
    private final ReentrantLock lock = new ReentrantLock();

    private final int PLAZAS = 12; //P0 a P11
    private final int TRANSICIONES = 12; //T1 a T12

    private final int [] marcado = new int[PLAZAS]; //Estado actual de los tokens 

    // Matriz de incidencia para las transiciones
    private final int [][] pre = new int [PLAZAS][TRANSICIONES];
    private final int [][] post = new int [PLAZAS][TRANSICIONES];
    private final int [][] incidencia = new int [PLAZAS][TRANSICIONES];
    
    public Monitor() {
        inicializarRedPetri();
    }

    private void inicializarRedPetri() {
        //Marcado inicial 
        marcado[0] = 200; //Po: 200 datos esperando 
        marcado [2] = 1; //P2: bus libre
        marcado [6] = 1; //P6: unidad libre 

        //T1: P0, P2 -> P1
        pre[0][1]=1; // P0
        pre[2][1]=1; // P2
        post[1][1]=1; // P1

        //T2: P1 -> P2, P3
        pre[1][2]=1; // P1
        post[2][2]=1; // P2
        post[3][2]=1; // P3

        //T3: P3, P6 -> P4
        pre[3][3]=1; // P3
        pre[6][3]=1; // P6
        post[4][3]=1; // P4

        //T4: P4, P6 -> P5
        pre[4][4]=1; // P4
        pre[6][4]=1; // P6
        post[5][4]=1; // P5

        //T5: P5 -> P6, P11
        pre[5][5]=1; // P5
        post[6][5]=1; // P6
        post[11][5]=1; // P11

        //T6: P3, P6 -> P7
        pre[3][6]=1; // P3
        pre[6][6]=1; // P6
        post[7][6]=1; // P7

        //T7: P7 -> P6, P11
        pre[7][7]=1; // P7
        post[6][7]=1; // P6
        post[11][7]=1; // P11

        //T8: P3, P6 -> P8
        pre[3][8]=1;
        pre[6][8]=1; // P6
        post[8][8]=1; // P8

        //T9: P8, P6 -> P9
        pre[8][9]=1; // P8
        pre[6][9]=1; // P6
        post[9][9]=1; // P9

        //T10: P9, P6 -> P10
        pre[9][10]=1; // P9
        pre[6][10]=1; // P6
        post[10][10]=1; // P10

        //T11: P10 -> P6, P11
        pre[10][11]=1; // P10
        post[6][11]=1; // P6
        post[11][11]=1; // P11

        //Matriz de incidencia = post - pre
        for (int i = 0; i < PLAZAS; i++) {
            for (int j = 0; j < TRANSICIONES; j++) {
                incidencia[i][j] = post[i][j] - pre[i][j];
            }
        }
    }
    
    private boolean isEnabled (int t) {
        for (int p = 0; p < PLAZAS; p++) {
            if (pre[p][t] > 0 && marcado[p] < pre[p][t]) {
                return false; // Si alguna plaza no tiene suficientes tokens, la transición no está habilitada
            }
        }
        return true; // Todas las plazas tienen suficientes tokens

    }

    @Override
    public boolean fireTransition(int transition) {
        lock.lock();
        try {
            if (!isEnabled(transition))return false; // Si la transición no está habilitada, no se dispara)

            // Actualizar el marcado
            for (int p = 0; p < PLAZAS; p++) {
                marcado[p] += incidencia[p][transition]; // Actualiza el marcado restando pre y sumando post
            }

            //Verificamos que la transicion este habilitada y se dispara 
            System.out.println("Transition " + transition + " fired. Marcado actual:");
            for (int i = 0; i < PLAZAS; i++) {
                System.out.print("P" + i + ": " + marcado[i] + " ");
            }
            System.out.println();

            return true; // La transición se disparó correctamente
     
        }
        finally {
            lock.unlock();
        }
    }
}

