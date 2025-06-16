package Segmentos; 

import app.LoggerTP;
import app.MonitorInterface;
import app.politica;
import java.util.ArrayList;
import java.util.List;

public class Segmentos implements Runnable {

    private final MonitorInterface monitor;
    private final int[] transicionesAsignadas;
    private final boolean usarPrioridadSimple;
    private final politica politica;

    public Segmentos(MonitorInterface monitor, int [] transicionesAsignadas, boolean usarPrioridadSimple) {
        this.monitor = monitor;
        this.transicionesAsignadas = transicionesAsignadas;
        this.usarPrioridadSimple = usarPrioridadSimple;
        this.politica = new politica();
        
    }

    @Override
    public void run() {
        String nombre = Thread.currentThread().getName();

        while (!Thread.currentThread().isInterrupted()) {
            try {
                // Obtener las transiciones habilitadas
                List<Integer> habilitadas = new ArrayList<>();

                for (int transicion : transicionesAsignadas) {
                    if (monitor.getHabilitadas().contains(transicion)) {
                        habilitadas.add(transicion);
                    }
                }
                // Elegir una transición según la política
                int transicionElegida;
                if (usarPrioridadSimple) {
                    transicionElegida = politica.eleegirTransicionAAleatoria(habilitadas);
                } else {
                    transicionElegida = politica.elegirTransicion(habilitadas);
                }

                //disparar si es posible
                if (transicionElegida == -1) {
                    // Solo mensaje por consola si se desea, o comentar esta línea
                    System.out.println(nombre + ": No hay transiciones habilitadas, esperando...");
                } else {
                    // Registrar la transición disparada en el log
                    LoggerTP.registrarTransicion(transicionElegida);
                }

                // Si no hay transiciones habilitadas, esperar un poco antes de volver a intentar
                if (transicionElegida == -1) {
                    synchronized (monitor) {
                        monitor.wait(100); // Espera 100 ms o hasta que otro hilo notifique
                    }
                    continue;
                }

                // Procesar la transición elegida
                monitor.fireTransition(transicionElegida);
                // Ya se registró la transición arriba, no es necesario log adicional aquí
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}