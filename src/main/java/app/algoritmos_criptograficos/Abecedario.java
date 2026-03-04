package app.algoritmos_criptograficos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Gestiona un conjunto ordenado de caracteres (abecedario) sobre el que operan
 * los algoritmos criptográficos.
 *
 * <p>Soporta dos modos de creación:</p>
 * <ul>
 *   <li><b>Por defecto</b>: Caracteres ASCII imprimibles (códigos 0–127, excluyendo
 *       caracteres de control y DEL). Resultado: 96 caracteres.</li>
 *   <li><b>Personalizado</b>: Cualquier lista de caracteres proporcionada por el usuario.</li>
 * </ul>
 *
 * <p>La lista de caracteres es inmutable desde el exterior para garantizar la integridad
 * del abecedario durante las operaciones de cifrado/descifrado.</p>
 *
 * @author Sergio Escalante Presa
 * @version 1.0
 * @see app.algoritmos_criptograficos.Algoritmo
 */
public class Abecedario {

    private final List<Character> caracteres;
    private final int size;
    private static final List<Integer> excluidosAscii = List.of(1, 2, 3, 4, 5, 6, 7, 8,
            9, 10, 11, 12, 13, 14, 15, 16,
            17, 18, 19, 20, 21, 22, 23, 24,
            25, 26, 27, 28, 29, 30, 31, 127);

    /**
     * Constructor por defecto: crea el abecedario ASCII (0–254)
     * Se excluyen caracteres no visualizables
     */
    public Abecedario() {
        this.caracteres = new ArrayList<>(127);
        for (int i = 0; i < 128; i++) {
            if(!excluidosAscii.contains(i)) {
                this.caracteres.add((char) i);
            }
        }
        this.size = caracteres.size();
    }

    /**
     * Constructor personalizado: recibe una lista de caracteres
     */
    public Abecedario(List<Character> caracteres) {
        if (caracteres == null || caracteres.isEmpty()) {
            throw new IllegalArgumentException("El abecedario no puede ser nulo o vacío");
        }

        this.caracteres = new ArrayList<>(caracteres);
        this.size = caracteres.size();
    }

    public int getSize() {
        return size;
    }

    public List<Character> getCaracteres() {
        return Collections.unmodifiableList(caracteres);
    }

    public boolean contiene(char c) {
        return caracteres.contains(c);
    }

    public char getCaracter(int indice) {
        if (indice < 0 || indice >= size) {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }
        return caracteres.get(indice);
    }

    public int indexOf(char caracter) {
        return this.caracteres.indexOf(caracter);
    }

    @Override
    public String toString() {
        return caracteres.toString();
    }
}