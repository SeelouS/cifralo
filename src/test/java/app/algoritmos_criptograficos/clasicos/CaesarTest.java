package app.algoritmos_criptograficos.clasicos;

import app.ParametroIncorrecto;
import app.algoritmos_criptograficos.Abecedario;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests extensivos para la clase Caesar.
 * Cubre: constructores, cifrado, descifrado, setParametros,
 * info, abecedarios personalizados, casos límite y propiedades criptográficas.
 */
class CaesarTest {

    // =========================================================================
    // CONSTRUCTORES
    // =========================================================================

    @Nested
    @DisplayName("Constructores")
    class ConstructorTests {

        @Test
        @DisplayName("Constructor por defecto: clave = 2")
        void constructorPorDefecto() {
            Caesar caesar = new Caesar();
            assertEquals(2, caesar.getParametros().get("translacion"));
        }

        @Test
        @DisplayName("Constructor con clave válida")
        void constructorConClaveValida() {
            Caesar caesar = new Caesar(5);
            assertEquals(5, caesar.getParametros().get("translacion"));
        }

        @Test
        @DisplayName("Constructor con clave = 0")
        void constructorConClaveCero() {
            Caesar caesar = new Caesar(0);
            assertEquals(0, caesar.getParametros().get("translacion"));
        }

        @Test
        @DisplayName("Constructor con clave = 1")
        void constructorConClaveUno() {
            Caesar caesar = new Caesar(1);
            assertEquals(1, caesar.getParametros().get("translacion"));
        }

        @Test
        @DisplayName("Constructor con clave demasiado grande lanza excepción")
        void constructorConClaveDemasiadoGrande() {
            Abecedario abc = new Abecedario();
            int claveInvalida = abc.getSize();
            assertThrows(ParametroIncorrecto.class, () -> new Caesar(claveInvalida));
        }

        @Test
        @DisplayName("Constructor con clave = tamaño abecedario lanza excepción")
        void constructorConClaveIgualTamano() {
            Abecedario abc = new Abecedario();
            assertThrows(ParametroIncorrecto.class, () -> new Caesar(abc.getSize()));
        }

        @Test
        @DisplayName("Constructor con clave muy grande lanza excepción")
        void constructorConClaveMuyGrande() {
            assertThrows(ParametroIncorrecto.class, () -> new Caesar(9999));
        }

        @Test
        @DisplayName("Constructor con abecedario personalizado")
        void constructorConAbecedarioPersonalizado() {
            List<Character> letras = List.of('a', 'b', 'c', 'd', 'e');
            Abecedario abc = new Abecedario(letras);
            Caesar caesar = new Caesar(2, abc);
            assertEquals(2, caesar.getParametros().get("translacion"));
        }

        @Test
        @DisplayName("Constructor con abecedario personalizado y clave inválida")
        void constructorConAbecedarioPersonalizadoClaveInvalida() {
            List<Character> letras = List.of('a', 'b', 'c');
            Abecedario abc = new Abecedario(letras);
            assertThrows(ParametroIncorrecto.class, () -> new Caesar(3, abc));
        }

        @Test
        @DisplayName("Constructor con abecedario personalizado y clave límite válida")
        void constructorConAbecedarioPersonalizadoClaveLimite() {
            List<Character> letras = List.of('a', 'b', 'c', 'd');
            Abecedario abc = new Abecedario(letras);
            Caesar caesar = new Caesar(3, abc);
            assertEquals(3, caesar.getParametros().get("translacion"));
        }
    }

    // =========================================================================
    // GETTERS E INFO
    // =========================================================================

    @Nested
    @DisplayName("Getters e info")
    class GettersInfoTests {

        @Test
        @DisplayName("getNombre devuelve 'Caesar'")
        void getNombre() {
            Caesar caesar = new Caesar();
            assertEquals("Caesar", caesar.getNombre());
        }

        @Test
        @DisplayName("getParametros devuelve mapa con 'translacion'")
        void getParametrosContieneClave() {
            Caesar caesar = new Caesar(7);
            Map<String, Integer> params = caesar.getParametros();
            assertTrue(params.containsKey("translacion"));
            assertEquals(7, params.get("translacion"));
        }

        @Test
        @DisplayName("getParametros devuelve mapa inmutable")
        void getParametrosEsInmutable() {
            Caesar caesar = new Caesar(3);
            Map<String, Integer> params = caesar.getParametros();
            assertThrows(UnsupportedOperationException.class,
                    () -> params.put("translacion", 99));
        }

        @Test
        @DisplayName("getParametros tiene tamaño 1")
        void getParametrosTamano() {
            Caesar caesar = new Caesar();
            assertEquals(1, caesar.getParametros().size());
        }

        @Test
        @DisplayName("info contiene nombre y parámetros")
        void infoContieneNombreYParametros() {
            Caesar caesar = new Caesar(5);
            String info = caesar.info();
            assertTrue(info.contains("Caesar"));
            assertTrue(info.contains("translacion"));
            assertTrue(info.contains("5"));
        }
    }

    // =========================================================================
    // SET PARÁMETROS
    // =========================================================================

    @Nested
    @DisplayName("setParametros")
    class SetParametrosTests {

        @Test
        @DisplayName("setParametros actualiza la clave correctamente")
        void setParametrosActualizaClave() {
            Caesar caesar = new Caesar(3);
            caesar.setParametros(List.of(10));
            assertEquals(10, caesar.getParametros().get("translacion"));
        }

        @Test
        @DisplayName("setParametros con lista vacía lanza excepción")
        void setParametrosListaVacia() {
            Caesar caesar = new Caesar();
            assertThrows(ParametroIncorrecto.class,
                    () -> caesar.setParametros(List.of()));
        }

        @Test
        @DisplayName("setParametros con demasiados parámetros lanza excepción")
        void setParametrosDemasiadosParametros() {
            Caesar caesar = new Caesar();
            assertThrows(ParametroIncorrecto.class,
                    () -> caesar.setParametros(List.of(1, 2)));
        }

        @Test
        @DisplayName("setParametros con 3 parámetros lanza excepción")
        void setParametrosTresParametros() {
            Caesar caesar = new Caesar();
            assertThrows(ParametroIncorrecto.class,
                    () -> caesar.setParametros(List.of(1, 2, 3)));
        }

        @Test
        @DisplayName("Cifrar tras setParametros usa nueva clave")
        void cifrarTrasSetParametros() {
            Caesar caesar = new Caesar(1);
            String cifrado1 = caesar.cifra("abc");

            caesar.setParametros(List.of(2));
            String cifrado2 = caesar.cifra("abc");

            assertNotEquals(cifrado1, cifrado2);
        }
    }

    // =========================================================================
    // CIFRADO
    // =========================================================================

    @Nested
    @DisplayName("Cifrado")
    class CifradoTests {

        @Test
        @DisplayName("Cifrar cadena vacía devuelve cadena vacía")
        void cifrarCadenaVacia() {
            Caesar caesar = new Caesar(3);
            assertEquals("", caesar.cifra(""));
        }

        @Test
        @DisplayName("Cifrar un solo carácter")
        void cifrarUnCaracter() {
            // Abecedario personalizado simple para verificar manualmente
            List<Character> letras = List.of('a', 'b', 'c', 'd', 'e');
            Abecedario abc = new Abecedario(letras);
            Caesar caesar = new Caesar(1, abc);
            assertEquals("b", caesar.cifra("a"));
        }

        @Test
        @DisplayName("Cifrar con clave 0 devuelve el mismo mensaje")
        void cifrarConClaveCero() {
            Caesar caesar = new Caesar(0);
            String mensaje = "Hola Mundo!";
            assertEquals(mensaje, caesar.cifra(mensaje));
        }

        @Test
        @DisplayName("Cifrar produce texto diferente al original (clave > 0)")
        void cifrarProduceTextoDiferente() {
            Caesar caesar = new Caesar(5);
            String mensaje = "Hola Mundo";
            String cifrado = caesar.cifra(mensaje);
            assertNotEquals(mensaje, cifrado);
        }

        @Test
        @DisplayName("Cifrar con abecedario personalizado: wrap-around")
        void cifrarConWrapAround() {
            // 'e' con clave 2 en {a,b,c,d,e} -> wrap -> 'b'
            List<Character> letras = List.of('a', 'b', 'c', 'd', 'e');
            Abecedario abc = new Abecedario(letras);
            Caesar caesar = new Caesar(2, abc);
            assertEquals("b", caesar.cifra("e"));
        }

        @Test
        @DisplayName("Cifrar palabra completa con abecedario personalizado")
        void cifrarPalabraAbcPersonalizado() {
            List<Character> letras = List.of('a', 'b', 'c', 'd', 'e');
            Abecedario abc = new Abecedario(letras);
            Caesar caesar = new Caesar(1, abc);
            // a->b, b->c, c->d, d->e, e->a
            assertEquals("bcdea", caesar.cifra("abcde"));
        }

        @Test
        @DisplayName("Cifrar mantiene caracteres que no están en el abecedario")
        void cifrarMantieneCaracteresNoEnAbecedario() {
            List<Character> letras = List.of('a', 'b', 'c', 'd');
            Abecedario abc = new Abecedario(letras);
            Caesar caesar = new Caesar(1, abc);
            // 'z' no está en el abecedario, debe mantenerse
            String resultado = caesar.cifra("az");
            assertTrue(resultado.endsWith("z"));
        }

        @ParameterizedTest
        @ValueSource(ints = {1, 2, 3, 5, 10, 20, 50})
        @DisplayName("Cifrar con distintas claves produce resultados distintos")
        void cifrarConDistintasClaves(int clave) {
            Caesar caesar = new Caesar(clave);
            String mensaje = "TestMessage";
            String cifrado = caesar.cifra(mensaje);
            if (clave > 0) {
                assertNotEquals(mensaje, cifrado);
            }
        }

        @Test
        @DisplayName("Cifrar mensaje largo")
        void cifrarMensajeLargo() {
            Caesar caesar = new Caesar(3);
            String mensaje = "a".repeat(10000);
            String cifrado = caesar.cifra(mensaje);
            assertEquals(10000, cifrado.length());
        }

        @Test
        @DisplayName("Cifrar preserva longitud del mensaje")
        void cifrarPreservaLongitud() {
            Caesar caesar = new Caesar(7);
            String mensaje = "Hola Mundo 123!@#";
            assertEquals(mensaje.length(), caesar.cifra(mensaje).length());
        }
    }

    // =========================================================================
    // DESCIFRADO
    // =========================================================================

    @Nested
    @DisplayName("Descifrado")
    class DescifradoTests {

        @Test
        @DisplayName("Descifrar cadena vacía devuelve cadena vacía")
        void descifrarCadenaVacia() {
            Caesar caesar = new Caesar(3);
            assertEquals("", caesar.descifra("", List.of(3)));
        }

        @Test
        @DisplayName("Descifrar con clave 0 devuelve el mismo mensaje")
        void descifrarConClaveCero() {
            Caesar caesar = new Caesar(0);
            String mensaje = "Test123!";
            assertEquals(mensaje, caesar.descifra(mensaje, List.of(0)));
        }

        @Test
        @DisplayName("Descifrar un carácter con abecedario personalizado")
        void descifrarUnCaracter() {
            List<Character> letras = List.of('a', 'b', 'c', 'd', 'e');
            Abecedario abc = new Abecedario(letras);
            Caesar caesar = new Caesar(1, abc);
            assertEquals("a", caesar.descifra("b", List.of(1)));
        }

        @Test
        @DisplayName("Descifrar con wrap-around hacia atrás")
        void descifrarConWrapAroundHaciaAtras() {
            List<Character> letras = List.of('a', 'b', 'c', 'd', 'e');
            Abecedario abc = new Abecedario(letras);
            Caesar caesar = new Caesar(2, abc);
            // 'a' con clave -2 => wrap -> 'd'
            assertEquals("d", caesar.descifra("a", List.of(2)));
        }

        @Test
        @DisplayName("Descifrar preserva longitud del mensaje")
        void descifrarPreservaLongitud() {
            Caesar caesar = new Caesar(5);
            String mensajeCifrado = caesar.cifra("Hola Mundo!");
            String descifrado = caesar.descifra(mensajeCifrado, List.of(5));
            assertEquals(mensajeCifrado.length(), descifrado.length());
        }

        @Test
        @DisplayName("Descifrar con clave incorrecta NO devuelve el original")
        void descifrarConClaveIncorrecta() {
            Caesar caesar = new Caesar(5);
            String mensaje = "Mensaje secreto";
            String cifrado = caesar.cifra(mensaje);
            String descifrado = caesar.descifra(cifrado, List.of(3));
            assertNotEquals(mensaje, descifrado);
        }

        @Test
        @DisplayName("Descifrar mantiene caracteres fuera del abecedario")
        void descifrarMantieneCaracteresFuera() {
            List<Character> letras = List.of('a', 'b', 'c', 'd');
            Abecedario abc = new Abecedario(letras);
            Caesar caesar = new Caesar(1, abc);
            String cifrado = caesar.cifra("az");
            String descifrado = caesar.descifra(cifrado, List.of(1));
            assertEquals("az", descifrado);
        }
    }

    // =========================================================================
    // PROPIEDAD: CIFRAR + DESCIFRAR = IDENTIDAD
    // =========================================================================

    @Nested
    @DisplayName("Propiedad de ida y vuelta (cifrar + descifrar = original)")
    class IdaYVueltaTests {

        @ParameterizedTest
        @ValueSource(ints = {0, 1, 2, 3, 5, 10, 20, 50})
        @DisplayName("Cifrar y descifrar devuelve el mensaje original (distintas claves)")
        void cifrarYDescifrarEsIdentidad(int clave) {
            Caesar caesar = new Caesar(clave);
            String mensaje = "Hola Mundo 123!@#";
            String cifrado = caesar.cifra(mensaje);
            String descifrado = caesar.descifra(cifrado, List.of(clave));
            assertEquals(mensaje, descifrado, "Fallo con clave " + clave);
        }

        @Test
        @DisplayName("Ida y vuelta con mensaje vacío")
        void idaYVueltaMensajeVacio() {
            Caesar caesar = new Caesar(5);
            String cifrado = caesar.cifra("");
            String descifrado = caesar.descifra(cifrado, List.of(5));
            assertEquals("", descifrado);
        }

        @Test
        @DisplayName("Ida y vuelta con un solo espacio")
        void idaYVueltaEspacio() {
            Caesar caesar = new Caesar(3);
            String cifrado = caesar.cifra(" ");
            String descifrado = caesar.descifra(cifrado, List.of(3));
            assertEquals(" ", descifrado);
        }

        @ParameterizedTest
        @CsvSource({
                "1, abcde",
                "2, Hello World",
                "3, Password123",
                "5, Ciberseguridad",
                "10, Test con espacios y numeros 42",
                "7, !@#$%"
        })
        @DisplayName("Ida y vuelta con distintos mensajes y claves")
        void idaYVueltaParametrizado(int clave, String mensaje) {
            Caesar caesar = new Caesar(clave);
            String cifrado = caesar.cifra(mensaje);
            String descifrado = caesar.descifra(cifrado, List.of(clave));
            assertEquals(mensaje, descifrado);
        }

        @Test
        @DisplayName("Ida y vuelta usando descifra(msg, false)")
        void idaYVueltaConDescifraSinFuerzaBruta() {
            Caesar caesar = new Caesar(4);
            String mensaje = "Prueba de descifra sin fuerza bruta";
            String cifrado = caesar.cifra(mensaje);
            String descifrado = caesar.descifra(cifrado, false);
            assertEquals(mensaje, descifrado);
        }

        @Test
        @DisplayName("Ida y vuelta con mensaje largo")
        void idaYVueltaMensajeLargo() {
            Caesar caesar = new Caesar(13);
            StringBuilder sb = new StringBuilder();
            for (int i = 32; i < 127; i++) {
                sb.append((char) i);
            }
            String mensaje = sb.toString();
            String cifrado = caesar.cifra(mensaje);
            String descifrado = caesar.descifra(cifrado, List.of(13));
            assertEquals(mensaje, descifrado);
        }

        @Test
        @DisplayName("Ida y vuelta con abecedario personalizado")
        void idaYVueltaAbecedarioPersonalizado() {
            List<Character> letras = new ArrayList<>();
            for (char c = 'a'; c <= 'z'; c++) {
                letras.add(c);
            }
            Abecedario abc = new Abecedario(letras);
            Caesar caesar = new Caesar(13, abc);
            String mensaje = "holamundo";
            String cifrado = caesar.cifra(mensaje);
            String descifrado = caesar.descifra(cifrado, List.of(13));
            assertEquals(mensaje, descifrado);
        }
    }

    // =========================================================================
    // ABECEDARIO PERSONALIZADO
    // =========================================================================

    @Nested
    @DisplayName("Abecedario personalizado")
    class AbecedarioPersonalizadoTests {

        @Test
        @DisplayName("Solo letras minúsculas: a->d, b->e, c->f con clave 3")
        void soloMinusculas() {
            List<Character> letras = new ArrayList<>();
            for (char c = 'a'; c <= 'z'; c++) letras.add(c);
            Abecedario abc = new Abecedario(letras);
            Caesar caesar = new Caesar(3, abc);
            assertEquals("krod", caesar.cifra("hola"));
        }

        @Test
        @DisplayName("Solo dígitos")
        void soloDigitos() {
            List<Character> digitos = List.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
            Abecedario abc = new Abecedario(digitos);
            Caesar caesar = new Caesar(3, abc);
            // 1->4, 2->5, 3->6
            assertEquals("456", caesar.cifra("123"));
        }

        @Test
        @DisplayName("Solo dígitos con wrap-around")
        void soloDigitosWrapAround() {
            List<Character> digitos = List.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
            Abecedario abc = new Abecedario(digitos);
            Caesar caesar = new Caesar(3, abc);
            // 8->1, 9->2, 0->3
            assertEquals("123", caesar.cifra("890"));
        }

        @Test
        @DisplayName("Abecedario de 2 caracteres")
        void abecedarioDeDos() {
            List<Character> ab = List.of('a', 'b');
            Abecedario abc = new Abecedario(ab);
            Caesar caesar = new Caesar(1, abc);
            // a->b, b->a
            assertEquals("ba", caesar.cifra("ab"));
        }

        @Test
        @DisplayName("Abecedario de 1 carácter: clave 0 no cambia nada")
        void abecedarioDeUno() {
            List<Character> uno = List.of('x');
            Abecedario abc = new Abecedario(uno);
            Caesar caesar = new Caesar(0, abc);
            assertEquals("x", caesar.cifra("x"));
        }
    }

    // =========================================================================
    // PROPIEDADES CRIPTOGRÁFICAS
    // =========================================================================

    @Nested
    @DisplayName("Propiedades criptográficas")
    class PropiedadesCriptograficasTests {

        @Test
        @DisplayName("Dos claves diferentes producen cifrados diferentes")
        void dosClavesDiferentes() {
            Caesar c1 = new Caesar(3);
            Caesar c2 = new Caesar(7);
            String mensaje = "Prueba";
            assertNotEquals(c1.cifra(mensaje), c2.cifra(mensaje));
        }

        @Test
        @DisplayName("Misma clave produce el mismo cifrado (determinismo)")
        void determinismo() {
            Caesar caesar = new Caesar(5);
            String mensaje = "Determinismo";
            assertEquals(caesar.cifra(mensaje), caesar.cifra(mensaje));
        }

        @Test
        @DisplayName("Cifrar dos veces NO es lo mismo que cifrar una vez (con misma clave)")
        void dobleCifrado() {
            Caesar caesar = new Caesar(3);
            String mensaje = "Doble";
            String cifrado1 = caesar.cifra(mensaje);
            String cifrado2 = caesar.cifra(cifrado1);
            assertNotEquals(cifrado1, cifrado2);
        }

        @Test
        @DisplayName("Doble cifrado se deshace con doble descifrado")
        void dobleCifradoDobleDescifrado() {
            Caesar caesar = new Caesar(3);
            String mensaje = "Doble cifrado";
            String cifrado1 = caesar.cifra(mensaje);
            String cifrado2 = caesar.cifra(cifrado1);
            String descifrado1 = caesar.descifra(cifrado2, List.of(3));
            String descifrado2 = caesar.descifra(descifrado1, List.of(3));
            assertEquals(mensaje, descifrado2);
        }

        @Test
        @DisplayName("Cifrar con clave k y luego con clave (N-k) devuelve el original")
        void cifradoComplementario() {
            Abecedario abc = new Abecedario();
            int tamano = abc.getSize();
            int k = 5;
            Caesar caesarK = new Caesar(k);
            Caesar caesarComplemento = new Caesar(tamano - k);

            String mensaje = "Complementario";
            String cifrado = caesarK.cifra(mensaje);
            String dobleCifrado = caesarComplemento.cifra(cifrado);
            assertEquals(mensaje, dobleCifrado);
        }
    }

    // =========================================================================
    // CASOS LÍMITE
    // =========================================================================

    @Nested
    @DisplayName("Casos límite")
    class CasosLimiteTests {

        @Test
        @DisplayName("Cifrar cadena de un solo carácter repetido")
        void cifrarCaracterRepetido() {
            Caesar caesar = new Caesar(5);
            String mensaje = "aaaa";
            String cifrado = caesar.cifra(mensaje);
            // Todos los caracteres cifrados deben ser iguales
            assertEquals(1, cifrado.chars().distinct().count());
        }

        @Test
        @DisplayName("Cifrar preserva espacios")
        void cifrarPreservaEspacios() {
            Caesar caesar = new Caesar(3);
            String mensaje = "a b c";
            String cifrado = caesar.cifra(mensaje);
            assertEquals(5, cifrado.length());
        }

        @Test
        @DisplayName("Cifrar con clave máxima válida (tamaño - 1)")
        void cifrarConClaveMaxima() {
            Abecedario abc = new Abecedario();
            int claveMaxima = abc.getSize() - 1;
            Caesar caesar = new Caesar(claveMaxima);
            String mensaje = "Test";
            String cifrado = caesar.cifra(mensaje);
            String descifrado = caesar.descifra(cifrado, List.of(claveMaxima));
            assertEquals(mensaje, descifrado);
        }

        @Test
        @DisplayName("Cifrar carácter que es el último del abecedario (wrap-around)")
        void cifrarUltimoCaracterAbecedario() {
            List<Character> letras = List.of('x', 'y', 'z');
            Abecedario abc = new Abecedario(letras);
            Caesar caesar = new Caesar(1, abc);
            // z -> wrap -> x
            assertEquals("x", caesar.cifra("z"));
        }

        @Test
        @DisplayName("Cifrar carácter que es el primero del abecedario")
        void cifrarPrimerCaracterAbecedario() {
            List<Character> letras = List.of('x', 'y', 'z');
            Abecedario abc = new Abecedario(letras);
            Caesar caesar = new Caesar(1, abc);
            // x -> y
            assertEquals("y", caesar.cifra("x"));
        }

        @Test
        @DisplayName("Descifrar el primer carácter con wrap-around hacia atrás")
        void descifrarPrimerCaracterWrapAround() {
            List<Character> letras = List.of('x', 'y', 'z');
            Abecedario abc = new Abecedario(letras);
            Caesar caesar = new Caesar(1, abc);
            // descifrar 'x' con clave 1 -> wrap -> 'z'
            assertEquals("z", caesar.descifra("x", List.of(1)));
        }

        @Test
        @DisplayName("Mensaje con solo caracteres fuera del abecedario")
        void mensajeSoloCaracteresFuera() {
            List<Character> letras = List.of('a', 'b', 'c');
            Abecedario abc = new Abecedario(letras);
            Caesar caesar = new Caesar(1, abc);
            // 'xyz' no están en el abecedario
            assertEquals("xyz", caesar.cifra("xyz"));
        }

        @Test
        @DisplayName("Mensaje mixto: algunos en abc, otros no")
        void mensajeMixto() {
            List<Character> letras = List.of('a', 'b', 'c');
            Abecedario abc = new Abecedario(letras);
            Caesar caesar = new Caesar(1, abc);
            String cifrado = caesar.cifra("axb");
            String descifrado = caesar.descifra(cifrado, List.of(1));
            assertEquals("axb", descifrado);
        }
    }

    // =========================================================================
    // DESCIFRA SIN FUERZA BRUTA (descifra(msg, false))
    // =========================================================================

    @Nested
    @DisplayName("descifra(msg, false) usa la clave interna")
    class DescifraSinFuerzaBrutaTests {

        @Test
        @DisplayName("descifra(msg, false) usa la clave del constructor")
        void descifraSinFuerzaBrutaUsaClave() {
            Caesar caesar = new Caesar(4);
            String mensaje = "Mensaje original";
            String cifrado = caesar.cifra(mensaje);
            assertEquals(mensaje, caesar.descifra(cifrado, false));
        }

        @Test
        @DisplayName("descifra(msg, false) usa la clave actualizada por setParametros")
        void descifraSinFuerzaBrutaTrasSetParametros() {
            Caesar caesar = new Caesar(2);
            caesar.setParametros(List.of(5));
            String mensaje = "Prueba";
            String cifrado = caesar.cifra(mensaje);
            assertEquals(mensaje, caesar.descifra(cifrado, false));
        }

        @Test
        @DisplayName("descifra(msg, false) con cadena vacía")
        void descifraSinFuerzaBrutaCadenaVacia() {
            Caesar caesar = new Caesar(3);
            assertEquals("", caesar.descifra("", false));
        }
    }

    // =========================================================================
    // CONSISTENCIA ENTRE MÉTODOS DE DESCIFRADO
    // =========================================================================

    @Nested
    @DisplayName("Consistencia entre métodos de descifrado")
    class ConsistenciaDescifradoTests {

        @Test
        @DisplayName("descifra(msg, List.of(k)) == descifra(msg, false) con misma clave")
        void consistenciaEntreMetodos() {
            Caesar caesar = new Caesar(6);
            String mensaje = "Consistencia";
            String cifrado = caesar.cifra(mensaje);

            String descifrado1 = caesar.descifra(cifrado, List.of(6));
            String descifrado2 = caesar.descifra(cifrado, false);

            assertEquals(descifrado1, descifrado2);
        }

        @Test
        @DisplayName("Ambos métodos devuelven el mensaje original")
        void ambosDevuelvenOriginal() {
            Caesar caesar = new Caesar(8);
            String mensaje = "Test consistencia!";
            String cifrado = caesar.cifra(mensaje);

            assertEquals(mensaje, caesar.descifra(cifrado, List.of(8)));
            assertEquals(mensaje, caesar.descifra(cifrado, false));
        }
    }

    // =========================================================================
    // RENDIMIENTO
    // =========================================================================

    @Nested
    @DisplayName("Rendimiento")
    class RendimientoTests {

        @Test
        @DisplayName("Cifrar 100.000 caracteres en tiempo razonable")
        void rendimientoCifrado() {
            Caesar caesar = new Caesar(7);
            String mensaje = "abcdefghij".repeat(10000);

            long inicio = System.nanoTime();
            String cifrado = caesar.cifra(mensaje);
            long tiempo = System.nanoTime() - inicio;

            assertEquals(100000, cifrado.length());
            // Debe completarse en menos de 2 segundos
            assertTrue(tiempo < 2_000_000_000L, "Cifrado demasiado lento: " + tiempo / 1_000_000 + "ms");
        }

        @Test
        @DisplayName("Descifrar 100.000 caracteres en tiempo razonable")
        void rendimientoDescifrado() {
            Caesar caesar = new Caesar(7);
            String mensaje = "abcdefghij".repeat(10000);
            String cifrado = caesar.cifra(mensaje);

            long inicio = System.nanoTime();
            String descifrado = caesar.descifra(cifrado, List.of(7));
            long tiempo = System.nanoTime() - inicio;

            assertEquals(mensaje, descifrado);
            assertTrue(tiempo < 2_000_000_000L, "Descifrado demasiado lento: " + tiempo / 1_000_000 + "ms");
        }
    }

    // =========================================================================
    // INTERFAZ ALGORITMO
    // =========================================================================

    @Nested
    @DisplayName("Implementación de la interfaz Algoritmo")
    class InterfazAlgoritmoTests {

        @Test
        @DisplayName("Caesar implementa Algoritmo")
        void caesarImplementaAlgoritmo() {
            Caesar caesar = new Caesar();
            assertInstanceOf(app.algoritmos_criptograficos.Algoritmo.class, caesar);
        }

        @Test
        @DisplayName("getKeys devuelve las claves del mapa de parámetros")
        void getKeysDevuelveClaves() {
            Caesar caesar = new Caesar();
            Set<String> keys = caesar.getKeys(caesar.getParametros());
            assertTrue(keys.contains("translacion"));
            assertEquals(1, keys.size());
        }

        @Test
        @DisplayName("getElems devuelve los valores del mapa de parámetros")
        void getElemsDevuelveValores() {
            Caesar caesar = new Caesar(7);
            Set<Integer> elems = caesar.getElems(caesar.getParametros());
            assertTrue(elems.contains(7));
            assertEquals(1, elems.size());
        }
    }
}

