package app;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;

public class LoggerTP {

    private static final String LOG_FILE = "log.txt";
    private static BufferedWriter writer;

    //Este metodo se encarga de escribir en el archivo de log al comienzo de la ejecucion
    //y cada vez que se llama al metodo log

    public static synchronized void iniciar () {
        try {
            if (writer != null) {
                writer.close(); // Cierra el writer anterior si existe
            }
            writer = new BufferedWriter(new FileWriter(LOG_FILE, false)); //sobreescribe el archivo
            writer.write("===========LOG DE TRANSICIONES===========");
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            System.err.println("Error al iniciar el logger: " + e.getMessage());
        }
    }

    //Llamo este metodo cuando se dispara una transicion
    public static synchronized void registrarTransicion(int numeroTransicion) {
        try {
            if (writer == null) {
                System.err.println("Logger no inicializado. Llamando a iniciar().");
                iniciar();
            }
            String linea = String.format("[%s] Hilo: %s - Disparó transición T%d", 
            LocalTime.now(), 
            Thread.currentThread().getName(),
            numeroTransicion
        );
            writer.write(linea);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            System.err.println("Error al registrar la transicion: " + e.getMessage());
        }
    }

    //Llamo este metodo cuando se termina la ejecucion
    public static synchronized void finalizar() {
        try {
            if (writer != null) {
                writer.flush();
                writer.write("===========FIN DEL LOG===========");
                writer.newLine();
                writer.close();
                writer = null;
            }
        } catch (IOException e) {
            System.err.println("Error al finalizar el logger: " + e.getMessage());
        }
    }
}