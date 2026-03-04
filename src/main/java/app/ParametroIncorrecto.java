package app;

/**
 * Excepción no comprobada (unchecked) que se lanza cuando se proporcionan
 * parámetros inválidos a un algoritmo criptográfico.
 *
 * <p>Se utiliza en las validaciones de constructores y métodos {@code setParametros()}
 * de las implementaciones de {@link app.algoritmos_criptograficos.Algoritmo}.</p>
 *
 * <p>Al extender {@link RuntimeException}, no requiere ser declarada en la firma
 * del método ni capturada obligatoriamente por el código cliente.</p>
 *
 * @author Sergio Escalante Presa
 * @version 1.0
 * @see app.algoritmos_criptograficos.Algoritmo
 */
public class ParametroIncorrecto extends RuntimeException {
    /**
     * Crea una nueva excepción con un mensaje descriptivo del error.
     *
     * @param message descripción del parámetro incorrecto y el rango válido
     */
    public ParametroIncorrecto(String message) {
        super(message);
    }
}
