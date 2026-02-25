package algoritmos_criptograficos;

import java.util.Dictionary;
import java.util.List;

public interface Algoritmo {
    Dictionary<String, Integer> parametros = null;

    default List<Integer> getParametros() {return List(parametros.elements());}
    void setParametros(List<Integer> nuevosParametros);
    String cifra(String mensaje);
    String descifra(String mensajeCifrado);
    String descifra(String mensajeCifrado, List<Integer> parametrosParaDescifrar);
}
