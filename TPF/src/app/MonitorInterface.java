package app;

public interface MonitorInterface {
    boolean fireTransition(int transition); // Dispara la transicion enviada si es que esta sensibilizada
    java.util.List<Integer> getHabilitadas(); // Agregado para exponer transiciones habilitadas
}