package log;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LoggerTP {
    private static final String NOMBRE_ARCHIVO = "Transiciones.log";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern ("HH:mm:ss.SSS");


    //Agregar una linea al archivo
    public static synchronized void log(int transicion, String nombre){
        try (PrintWriter out = new PrintWriter (new FileWriter (NOMBRE_ARCHIVO, true))) {
            String hora = LocalTime.now().format(formatter);
            out.println(hora + "| T" + transicion + " | " + nombre);
        }catch (IOException e){
            System.err.println("Error al escribir en el log: " + e.getMessage());
        }
    }

    
}
