package app.algoritmos_criptograficos.clasicos;

import app.algoritmos_criptograficos.Abecedario;
import app.algoritmos_criptograficos.Algoritmo;
import app.ParametroIncorrecto;

import java.util.*;

/**
 * Implementación del cifrado César — algoritmo de sustitución monoalfabética por desplazamiento.
 *
 * <p>El cifrado César es uno de los cifrados más antiguos y simples conocidos. Cada carácter
 * del texto plano se sustituye por otro carácter que se encuentra un número fijo de posiciones
 * más adelante en el abecedario. Este número fijo se denomina <b>clave de translación</b>.</p>
 *
 * <p><b>Fórmula de cifrado:</b> {@code C(x) = abc[(indexOf(x) + k) mod N]}</p>
 * <p><b>Fórmula de descifrado:</b> {@code D(x) = abc[(indexOf(x) - k + N) mod N]}</p>
 *
 * <p>Donde {@code x} es el carácter, {@code k} es la clave de translación, {@code N} es el
 * tamaño del abecedario y {@code abc} es la lista de caracteres del abecedario.</p>
 *
 * <h3>Ejemplo de uso:</h3>
 * <pre>{@code
 * Caesar caesar = new Caesar(5);
 * String cifrado = caesar.cifra("Hola Mundo!");
 * String descifrado = caesar.descifra(cifrado, List.of(5));
 * // descifrado == "Hola Mundo!"
 * }</pre>
 *
 * <h3>Restricciones:</h3>
 * <ul>
 *   <li>La clave debe ser estrictamente mayor que 0 y menor que el tamaño del abecedario.</li>
 *   <li>Los caracteres que no pertenecen al abecedario se mantienen sin cambios.</li>
 * </ul>
 *
 * @author Sergio Escalante Presa
 * @version 1.0
 * @see Algoritmo
 * @see Abecedario
 * @see app.ParametroIncorrecto
 */
public class Caesar implements Algoritmo {
    private static final String nombre = "Caesar";
    private Map<String, Integer> parametros;
    private Abecedario abecedario;

    public Caesar(int clave) {
        this.abecedario = new Abecedario();
        boolean parametroCorrecto = parametrosCorrectos(List.of(clave));

        if(!parametroCorrecto) {
            throw new ParametroIncorrecto("El parámetro que has pasado no es correcto. Comprueba que esté entre 0 y " +
            this.abecedario.getSize() + "(excluidos)\n");
        }
        this.parametros = new HashMap<>(1);
        parametros.put("translacion", clave);
    }

    public Caesar (int clave, Abecedario abc) {
        this.abecedario = abc;
        boolean parametroCorrecto = parametrosCorrectos(List.of(clave));

        if(!parametroCorrecto) {
            throw new ParametroIncorrecto("El parámetro que has pasado no es correcto. Comprueba que esté entre 0 y " +
                    this.abecedario.getSize() + "(excluidos)\n");
        }
        this.parametros = new HashMap<>(1);
        parametros.put("translacion", clave);
    }

    @Override
    public Map<String, Integer> getParametros() {
        return Collections.unmodifiableMap(parametros);
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public void setParametros(List<Integer> nuevosParametros) {
        if (nuevosParametros.size() != 1) {
            System.err.println("Has definido un número incorrecto de parámetros.|" +
                    "\n" + "Definido: " + nuevosParametros.size() + "\n" + "Necesarios: 1");
            System.err.println("No se cambiarán los parámetros.");
        } else if (!parametrosCorrectos(nuevosParametros)){
            System.err.println("El parámetro que has pasado no es correcto. Comprueba que esté entre 0 y " +
                    this.abecedario.getSize() + "(excluidos)\n" +
                    "No se cambiarán los parámetros");

        } else {
            this.parametros.put("translacion", nuevosParametros.get(0));
        }

    }

    @Override
    public String cifra(String mensaje) {
        int key = parametros.get("translacion");
        List<Character> abc = abecedario.getCaracteres();
        int abcSize = abc.size();
        StringBuilder resultado = new StringBuilder(mensaje.length());

        for (char caracter : mensaje.toCharArray()) {
            resultado.append(translada(caracter, key, false));
        }

        return resultado.toString();
    }



    @Override
    public String descifra(String mensajeCifrado, List<Integer> parametrosParaDescifrar) {
        int key = parametrosParaDescifrar.get(0);
        StringBuilder resultado = new StringBuilder(mensajeCifrado.length());

        for (char caracter : mensajeCifrado.toCharArray()) {
            resultado.append(translada(caracter, key, true));
        }

        return resultado.toString();
    }

    @Override
    public String descifra(String mensajeCifrado, boolean fuerzaBruta) {
        String resultado;
        if(fuerzaBruta) {
            resultado = descifraBruta(mensajeCifrado);
        } else {
            resultado = descifra(mensajeCifrado, new ArrayList<>(getElems(this.parametros)));
        }
        return resultado;
    }

    private char translada(char caracter, int key, boolean retroceso) {
        List<Character> abc = abecedario.getCaracteres();
        int abcSize = abc.size();
        int posicion = abecedario.indexOf(caracter);

        if (posicion == -1) {
            // Carácter no está en el abecedario: devolver sin cambios
            return caracter;
        }

        int nuevaPosicion;
        if (retroceso) {
            nuevaPosicion = (posicion - key + abcSize) % abcSize;
        } else {
            nuevaPosicion = (posicion + key) % abcSize;
        }

        return abc.get(nuevaPosicion);
    }

    private String descifraBruta(String mensajeCifrado) {
        String resultado = "";
        try (Scanner scanner = new Scanner(System.in)) {
            List<Integer> key = new ArrayList<>(1);
            key.add(0);
            boolean descifrado = false;

            while (!descifrado && key.get(0) < abecedario.getSize()) {
                System.out.println("Probando con key: " + key.get(0));
                resultado = descifra(mensajeCifrado, key);
                System.out.println("Mensaje obtenido con key " + key.get(0) + ":\n" + resultado);
                descifrado = isDescifrado(scanner, "", false, key);
            }
        }

        return resultado;
    }

    private boolean parametrosCorrectos(List<Integer> nuevosParametros) {
        return nuevosParametros.get(0) < this.abecedario.getSize() && nuevosParametros.get(0) > 0;
    }
}
