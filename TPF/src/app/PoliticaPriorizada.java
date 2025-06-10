package app;

public class PoliticaPriorizada implements PoliticaInterface {
    @Override
    public int cualTransicionDisparar() {
        return 6; // Prioriza la transicion simple, es decir, la T5
    }
}
