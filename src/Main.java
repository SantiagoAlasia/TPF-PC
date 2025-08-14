import hilos.*;
import monitor.Monitor;
import monitor.MonitorInterface;
import politica.Politica;
import java.io.PrintWriter;
import java.io.IOException;
import analisis.AnalizadorLog;


public class Main {
    public static void main(String[] args) {
        // 1. Crear monitor concurrente
        MonitorInterface monitor = new Monitor();

        // 2. Elegir política: true = prioriza modo simple, false = aleatoria
        Politica politica = new Politica(false); // podés cambiar esto

        // 3. Crear todos los hilos del sistema
        Runnable hab = new HiloAccesoBuffer(monitor);            // T1 y T2
        Runnable hp = new HiloPolitica(monitor, politica);       // T3 o T6 o T8
        Runnable hs = new HiloSimple(monitor);                   // T7
        Runnable hm = new HiloMedio(monitor);                    // T4, T5
        Runnable hc = new HiloComplejo(monitor);                 // T9, T10, T11

        //Limpiar el log
        try (PrintWriter pw = new PrintWriter("transiciones.log")) {
            pw.print(""); // Borra el contenido
        } catch (IOException e) {
            e.printStackTrace();
        }

        Thread thHab = new Thread(hab);
        Thread thHp = new Thread(hp);
        Thread thHs = new Thread(hs);
        Thread thHm = new Thread(hm);
        Thread thHc = new Thread(hc);

        // Iniciar los hilos
        thHab.start();
        thHp.start();
        thHs.start();
        thHm.start();
        thHc.start();

        // 5. Ejecutar durante un tiempo definido (ej: 30 segundos)
        try {
            Thread.sleep(30000); // podés ajustar el tiempo total de ejecución
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 6. Detener todos los hilos de manera segura
        thHab.interrupt();
        thHp.interrupt();
        thHs.interrupt();
        thHm.interrupt();
        thHc.interrupt();

        System.out.println("Programa finalizado.");

        System.out.println("\nAnalizando el log de transiciones...");
        AnalizadorLog analizador = new AnalizadorLog();
        analizador.analizar();

    }
}
