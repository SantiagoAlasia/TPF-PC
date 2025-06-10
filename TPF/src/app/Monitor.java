package app;

import java.util.Arrays;
import java.util.concurrent.Semaphore;

public class Monitor implements MonitorInterface {
    private final RdP rdp;
    private final PoliticaInterface politica;
    private Semaphore mutex;
    private Colas colas;
    private boolean k; // Variable de Estado

    public Monitor (RdP rdp, PoliticaInterface politica, Colas colas) {
        this.rdp = rdp;
        this.politica = politica;
        this.colas = colas;
        mutex = new Semaphore(1);
    }

    @Override
    public boolean fireTransition(int transition) {
        try {
            mutex.acquire();
            k = true;

            while (k) {
                k = rdp.disparar(transition);
                if (k) {
                    int[] transicionesSensibilizadas = rdp.sensibilizadas();
                    int[] variablesDeCondicion = colas.quienesEstan();
                    int n = transicionesSensibilizadas.length;

                    int[] m = new int[n];
                    for (int i = 0; i < n; i++) {
                        m[i] = transicionesSensibilizadas[i] * variablesDeCondicion[i];
                    }

                    if(Arrays.stream(m).max().getAsInt() > 0){
                        int indice = politica.cualTransicionDisparar();
                        colas.release(indice);
                    }else{
                        k = false;
                    }
                }else{
                    mutex.release();
                    colas.acquire(transition);
                }
            }

            mutex.release();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally{
            return true;
        }
    }
}
