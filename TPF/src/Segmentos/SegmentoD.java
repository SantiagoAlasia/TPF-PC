package Segmentos;

import app.Monitor;

public class SegmentoD extends Segmentos {
    private static ThreadLocal<Integer> Transicion = new ThreadLocal<Integer>(){
        protected Integer initialValue() {
            return 8; // Esta variable valdra: 8, 9, 10, 11. Estos valores corresponden a T7, T8, T9, T10
        }
    };

    public SegmentoD(Monitor monitor) {
        super(monitor);
    }

    @Override
    public void run() {
        int t;
        while(true){
            t = Transicion.get();
            monitor.fireTransition(t);
            System.out.println("ProcesamientoComplejo: Transicion" + t);

            t =+ 1;
            if(t == 12){
                t = 8;
            }

            Transicion.set(t);
        }
    }
}