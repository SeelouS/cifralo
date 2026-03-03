package app.algoritmos_criptograficos;

import java.util.*;

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
    default Set<String> getKeys(Dictionary<String, Integer> dict) {
        return new HashSet<>(Collections.list(dict.keys()));
    }
    default Set<Integer> getElems(Dictionary<String, Integer> dict) {
        return new HashSet<>(Collections.list(dict.elements()));
    }
}