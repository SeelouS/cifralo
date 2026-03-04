package app.algoritmos_criptograficos;

import java.util.*;

/**
 * Interfaz base para todos los algoritmos criptográficos del sistema.
 *
 * <p>Define el contrato que cualquier algoritmo de cifrado/descifrado debe cumplir,
 * independientemente de su tipo (clásico, simétrico o asimétrico).</p>
 *
 * <p>Proporciona métodos abstractos para las operaciones principales y métodos
 * {@code default} para funcionalidades comunes como obtener información del algoritmo
 * o interactuar con el usuario durante un ataque de fuerza bruta.</p>
 *
 * @author Sergio Escalante Presa
 * @version 1.0
 * @see app.algoritmos_criptograficos.clasicos.Caesar
 * @see Abecedario
 */
public interface Algoritmo {

    /**
     * Devuelve los parámetros del algoritmo como un mapa clave-valor.
     *
     * @return mapa inmutable con los parámetros del algoritmo
     */
    Map<String, Integer> getParametros();

    /**
     * Devuelve el nombre identificativo del algoritmo.
     *
     * @return nombre del algoritmo (e.g., {@code "Caesar"})
     */
    String getNombre();

    /**
     * Actualiza los parámetros del algoritmo.
     *
     * <p>El comportamiento ante parámetros inválidos depende de la implementación:
     * puede lanzar una excepción o informar por {@code stderr} sin modificar el estado.</p>
     *
     * @param nuevosParametros lista de valores enteros para los parámetros
     */
    void setParametros(List<Integer> nuevosParametros);

    /**
     * Cifra un mensaje de texto plano.
     *
     * @param mensaje texto plano a cifrar. Puede ser una cadena vacía
     * @return texto cifrado, generalmente de la misma longitud que el original
     */
    String cifra(String mensaje);

    /**
     * Descifra un mensaje cifrado.
     *
     * @param mensajeCifrado texto cifrado a descifrar
     * @param fuerzaBruta    {@code true} para descifrado interactivo por fuerza bruta,
     *                       {@code false} para usar la clave interna del objeto
     * @return texto descifrado
     */
    String descifra(String mensajeCifrado, boolean fuerzaBruta);

    /**
     * Descifra un mensaje cifrado con una clave explícita proporcionada como parámetro.
     *
     * @param mensajeCifrado          texto cifrado a descifrar
     * @param parametrosParaDescifrar lista con la(s) clave(s) de descifrado
     * @return texto descifrado
     */
    String descifra(String mensajeCifrado, List<Integer> parametrosParaDescifrar);

    /**
     * Devuelve información legible del algoritmo: nombre y parámetros actuales.
     *
     * @return cadena con formato {@code "Algoritmo: <nombre>\nParametros: <params>"}
     */
    default String info() {
        return "Algoritmo: " + getNombre() + "\n" + "Parametros: " + getParametros();
    }

    /**
     * Extrae las claves (nombres) de un mapa de parámetros.
     *
     * @param map mapa del que extraer las claves
     * @return conjunto de nombres de parámetros
     */
    default Set<String> getKeys(Map<String, Integer> map) {
        return map.keySet();
    }

    /**
     * Extrae los valores de un mapa de parámetros.
     *
     * @param map mapa del que extraer los valores
     * @return conjunto de valores de parámetros
     */
    default Set<Integer> getElems(Map<String, Integer> map) {
        return new HashSet<>(map.values());
    }

    /**
     * Pregunta al usuario de forma interactiva si el descifrado es correcto.
     *
     * <p>Utilizado durante el descifrado por fuerza bruta. Si el usuario responde "N",
     * se incrementa la clave para probar la siguiente.</p>
     *
     * @param scanner    scanner para leer la entrada del usuario
     * @param input      variable para almacenar la entrada
     * @param descifrado estado actual de descifrado
     * @param key        lista mutable con la clave actual (se incrementa si responde "N")
     * @return {@code true} si el usuario confirma que el descifrado es correcto
     */
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