package Segmentos;

import app.Monitor;

public class SegmentoE extends Segmentos {
    private static ThreadLocal<Integer> Transicion = new ThreadLocal<Integer>(){
        protected Integer initialValue() {
            return 12; // Esta variable valdra: 12. Este valor corresponden a T11
        }
    };

    public SegmentoE(Monitor monitor) {
        super(monitor);
    }

    @Override
    public void run() {
        while(true){
            int t = Transicion.get();
            monitor.fireTransition(t);
            System.out.println("Entrada de datos: Transicion" + t);
        }
    }
}
