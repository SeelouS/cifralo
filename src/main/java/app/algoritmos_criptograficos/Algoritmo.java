package app.algoritmos_criptograficos;

import java.util.*;

public interface Algoritmo {

    Map<String, Integer> getParametros();
    String getNombre();
    void setParametros(List<Integer> nuevosParametros);
    String cifra(String mensaje);
    String descifra(String mensajeCifrado, boolean fuerzaBruta);
    String descifra(String mensajeCifrado, List<Integer> parametrosParaDescifrar);
    default String info() {
        return "Algoritmo: " + getNombre() + "\n" + "Parametros: " + getParametros();
    }
    default Set<String> getKeys(Map<String, Integer> map) {
        return map.keySet();
    }
    default Set<Integer> getElems(Map<String, Integer> map) {
        return new HashSet<>(map.values());
    }

    default boolean isDescifrado(Scanner scanner, String input, boolean descifrado, List<Integer> key) {
        do {
            System.out.println("¿Está correctamente descifrado? (S/N)\n");
            if(scanner.hasNextLine()) {
                input = scanner.nextLine();
            }
        } while (!(input.equals("S") || input.equals("N")));

        if (input.equals("S")) {
            descifrado = true;
        } else {
            key.set(0, key.get(0) + 1);
        }
        return descifrado;
    }
}