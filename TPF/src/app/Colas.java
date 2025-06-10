package app;

import java.util.concurrent.Semaphore;
import java.util.ArrayList;

public class Colas {
    private int m;
    private ArrayList<Semaphore> variablesDeCondicion;

    public Colas(int m) {
        this.m = m;
        this.variablesDeCondicion = new ArrayList<Semaphore>();
        for (int i = 0; i < m; i++){
            variablesDeCondicion.add(new Semaphore(1));
        }
    }

    public void acquire(int indice) {
        try {
            variablesDeCondicion.get(indice).acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void release(int indice) {
        variablesDeCondicion.get(indice).release();
    }

    public int[] quienesEstan(){
        int[] quienes = new int[m];
        for (int i = 0; i < m; i++){
            if(variablesDeCondicion.get(i).availablePermits() == 0){
                quienes[i] = 1;
            }
        }

        return quienes;
    }
}
