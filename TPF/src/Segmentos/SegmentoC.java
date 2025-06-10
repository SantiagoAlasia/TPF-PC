package Segmentos;

import app.Monitor;

public class SegmentoC extends Segmentos {
    private static ThreadLocal<Integer> Transicion = new ThreadLocal<Integer>(){
        protected Integer initialValue() {
            return 6; // Esta variable valdra: 6, 7. Estos valores corresponden a T5, T6
        }
    };

    public SegmentoC(Monitor monitor) {
        super(monitor);
    }

    @Override
    public void run() {
        int t;
        while(true){
            t = Transicion.get();
            monitor.fireTransition(t);
            System.out.println("ProcesamientoSimple: Transicion" + t);

            t =+ 1;
            if(t == 8){
                t = 6;
            }

            Transicion.set(t);
        }
    }
}