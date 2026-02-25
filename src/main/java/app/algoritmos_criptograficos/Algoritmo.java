package app.algoritmos_criptograficos;

import java.util.Dictionary;
import java.util.List;

public interface Algoritmo {

    Dictionary<String, Integer> getParametros();
    String getNombre();
    void setParametros(List<Integer> nuevosParametros);
    String cifra(String mensaje);
    String descifra(String mensajeCifrado, Boolean fuerzaBruta);
    String descifra(String mensajeCifrado, List<Integer> parametrosParaDescifrar);
    default String info() {
        return "Algoritmo: " + getNombre() + "\n" + "Parametros: " + getParametros();
    }
}
