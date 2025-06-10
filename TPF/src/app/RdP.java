package app;

public class RdP {
    private final int m;
    private final int [][] matrizDeIncidencia;
    private int [] marcadoActual;
    private int [] transicionesSensibilizadas;
    private int [] vectorDeDisparo;

    public RdP() {
        m = 12;
        matrizDeIncidencia = new int [m][m];
        marcadoActual = new int []{3, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0};
        transicionesSensibilizadas = new int []{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        vectorDeDisparo = new int []{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    }

    public boolean disparar(int indice){
        if (transicionesSensibilizadas[indice] == 1){
            // Calculo del nuevo marcado
            vectorDeDisparo[indice] = 1;
            int[] vectorAux = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < m; j++) {
                    vectorAux[i] = vectorAux[i] + (matrizDeIncidencia[i][j] * vectorDeDisparo[j]);
                }
            }

            for (int i = 0; i < m; i++) {
                marcadoActual[i] = marcadoActual[i] + vectorAux[i];
            }

            // Calculo de las nuevas transicion sensibilizadas
            for (int i = 0; i < m; i++) {
                if (marcadoActual[i] >= 1){
                    transicionesSensibilizadas[i] = 1;
                }
                else{
                    transicionesSensibilizadas[i] = 0;
                }
            }

            return true;
        }
        return false;
    }

    public int[] sensibilizadas(){
        return transicionesSensibilizadas;
    }

    public int cantidadTransiciones(){
        return m;
    }
}