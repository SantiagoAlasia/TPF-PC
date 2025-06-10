package Segmentos;

import app.Monitor;

public class SegmentoB extends Segmentos {
    private static ThreadLocal<Integer> Transicion = new ThreadLocal<Integer>(){
        protected Integer initialValue() {
            return 3; // Esta variable valdra: 3, 4, 5. Estos valores corresponden a T2, T3, T4
        }
    };

    public SegmentoB(Monitor monitor) {
        super(monitor);
    }

    @Override
    public void run() {
        int t;
        while(true){
            t = Transicion.get();
            monitor.fireTransition(t);
            System.out.println("Procesamiento Medio: Transicion" + t);

            t =+ 1;
            if(t == 6){
                t = 3;
            }

            Transicion.set(t);
        }
    }
}
