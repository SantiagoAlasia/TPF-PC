package Segmentos;

import app.Monitor;


public class SegmentoA extends Segmentos {
    private static ThreadLocal<Integer> Transicion = new ThreadLocal<Integer>(){
        protected Integer initialValue() {
            return 0; // Esta variable valdra: 3, 4, 5. Estos valores corresponden a T2, T3, T4
        }
    };

    public SegmentoA(Monitor monitor) {
        super(monitor);
    }

    @Override
    public void run() {
        int t;
        while(true){
            t = Transicion.get();
            monitor.fireTransition(t);
            System.out.println("Entrada de datos: Transicion" + t);

            t =+ 1;
            if(t == 2){
                t = 0;
            }

            Transicion.set(t);
        }
    }
}
