# Guía de Contribución

## Índice

- [Cómo Añadir un Nuevo Algoritmo](#cómo-añadir-un-nuevo-algoritmo)
- [Paso a Paso](#paso-a-paso)
- [Plantilla de Algoritmo](#plantilla-de-algoritmo)
- [Plantilla de Tests](#plantilla-de-tests)
- [Checklist](#checklist)
- [Convenciones de Código](#convenciones-de-código)
- [Estructura de Paquetes](#estructura-de-paquetes)

---

## Cómo Añadir un Nuevo Algoritmo

El sistema está diseñado para que añadir un nuevo algoritmo sea sencillo. Solo necesitas:

1. Crear una clase que implemente la interfaz `Algoritmo`.
2. Colocarla en el paquete adecuado.
3. Crear tests unitarios con JUnit 5.
4. Verificar que todos los tests pasan.

---

## Paso a Paso

### 1. Elegir el paquete

| Tipo de algoritmo | Paquete |
|-------------------|---------|
| Clásico (César, Vigenère, Atbash…) | `app.algoritmos_criptograficos.clasicos` |
| Simétrico (AES, DES, 3DES…) | `app.algoritmos_criptograficos.simetricos` |
| Asimétrico (RSA, ElGamal…) | `app.algoritmos_criptograficos.asimetricos` |

### 2. Crear la clase

Crea un archivo `.java` en el paquete correspondiente. Ejemplo para un cifrado de Vigenère:

```
src/main/java/app/algoritmos_criptograficos/clasicos/Vigenere.java
```

### 3. Implementar la interfaz `Algoritmo`

Tu clase debe implementar todos los métodos abstractos:

| Método | Obligatorio | Descripción |
|--------|:-----------:|-------------|
| `getParametros()` | ✅ | Devolver mapa inmutable con los parámetros |
| `getNombre()` | ✅ | Devolver el nombre del algoritmo |
| `setParametros(List<Integer>)` | ✅ | Actualizar parámetros con validación |
| `cifra(String)` | ✅ | Cifrar un mensaje |
| `descifra(String, boolean)` | ✅ | Descifrar con clave interna o fuerza bruta |
| `descifra(String, List<Integer>)` | ✅ | Descifrar con clave explícita |

### 4. Crear tests unitarios

Crea el archivo de tests espejo:

```
src/test/java/app/algoritmos_criptograficos/clasicos/VigenereTest.java
```

### 5. Compilar y ejecutar tests

```bash
mvn clean test
```

---

## Plantilla de Algoritmo

```java
package app.algoritmos_criptograficos.clasicos;

import app.algoritmos_criptograficos.Abecedario;
import app.algoritmos_criptograficos.Algoritmo;
import app.ParametroIncorrecto;

import java.util.*;

/**
 * Implementación del cifrado [NOMBRE].
 * 
 * <p>[Breve descripción del algoritmo y su funcionamiento].</p>
 * 
 * <p><b>Fórmula de cifrado:</b> [fórmula]</p>
 * <p><b>Fórmula de descifrado:</b> [fórmula]</p>
 * 
 * @author [Tu nombre]
 * @version 1.0
 * @see Algoritmo
 * @see Abecedario
 */
public class NuevoAlgoritmo implements Algoritmo {

    private static final String nombre = "NuevoAlgoritmo";
    private Map<String, Integer> parametros;
    private Abecedario abecedario;

    /**
     * Crea una instancia con el abecedario ASCII por defecto.
     *
     * @param clave parámetro principal del algoritmo. Debe ser > 0 y < tamaño del abecedario
     * @throws ParametroIncorrecto si la clave está fuera de rango
     */
    public NuevoAlgoritmo(int clave) {
        this.abecedario = new Abecedario();
        if (!parametrosCorrectos(List.of(clave))) {
            throw new ParametroIncorrecto("Clave fuera de rango válido");
        }
        this.parametros = new HashMap<>();
        parametros.put("clave", clave);
    }

    /**
     * Crea una instancia con un abecedario personalizado.
     *
     * @param clave parámetro principal del algoritmo
     * @param abc   abecedario personalizado
     * @throws ParametroIncorrecto si la clave está fuera de rango
     */
    public NuevoAlgoritmo(int clave, Abecedario abc) {
        this.abecedario = abc;
        if (!parametrosCorrectos(List.of(clave))) {
            throw new ParametroIncorrecto("Clave fuera de rango válido");
        }
        this.parametros = new HashMap<>();
        parametros.put("clave", clave);
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
        // Validar número de parámetros
        if (nuevosParametros.size() != parametros.size()) {
            System.err.println("Número incorrecto de parámetros.");
            return;
        }
        // Validar valores
        if (!parametrosCorrectos(nuevosParametros)) {
            System.err.println("Parámetros fuera de rango.");
            return;
        }
        // Actualizar
        this.parametros.put("clave", nuevosParametros.get(0));
    }

    @Override
    public String cifra(String mensaje) {
        // TODO: Implementar lógica de cifrado
        StringBuilder resultado = new StringBuilder(mensaje.length());
        // ...
        return resultado.toString();
    }

    @Override
    public String descifra(String mensajeCifrado, List<Integer> parametrosParaDescifrar) {
        // TODO: Implementar lógica de descifrado
        StringBuilder resultado = new StringBuilder(mensajeCifrado.length());
        // ...
        return resultado.toString();
    }

    @Override
    public String descifra(String mensajeCifrado, boolean fuerzaBruta) {
        if (fuerzaBruta) {
            // TODO: Implementar fuerza bruta
            return "";
        } else {
            return descifra(mensajeCifrado, new ArrayList<>(getElems(this.parametros)));
        }
    }

    private boolean parametrosCorrectos(List<Integer> params) {
        return params.get(0) > 0 && params.get(0) < abecedario.getSize();
    }
}
```

---

## Plantilla de Tests

```java
package app.algoritmos_criptograficos.clasicos;

import app.ParametroIncorrecto;
import app.algoritmos_criptograficos.Abecedario;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class NuevoAlgoritmoTest {

    // =========================================================================
    // CONSTRUCTORES
    // =========================================================================

    @Nested
    @DisplayName("Constructores")
    class ConstructorTests {

        @Test
        @DisplayName("Constructor con clave válida")
        void constructorConClaveValida() {
            NuevoAlgoritmo algo = new NuevoAlgoritmo(5);
            assertEquals(5, algo.getParametros().get("clave"));
        }

        @Test
        @DisplayName("Constructor con clave inválida lanza excepción")
        void constructorConClaveInvalida() {
            assertThrows(ParametroIncorrecto.class, () -> new NuevoAlgoritmo(0));
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
            NuevoAlgoritmo algo = new NuevoAlgoritmo(3);
            assertEquals("", algo.cifra(""));
        }

        @Test
        @DisplayName("Cifrar produce texto diferente al original")
        void cifrarProduceTextoDiferente() {
            NuevoAlgoritmo algo = new NuevoAlgoritmo(5);
            assertNotEquals("Hola", algo.cifra("Hola"));
        }
    }

    // =========================================================================
    // IDA Y VUELTA
    // =========================================================================

    @Nested
    @DisplayName("Ida y vuelta")
    class IdaYVueltaTests {

        @ParameterizedTest
        @ValueSource(ints = {1, 2, 3, 5, 10, 20})
        @DisplayName("Cifrar y descifrar devuelve el original")
        void idaYVuelta(int clave) {
            NuevoAlgoritmo algo = new NuevoAlgoritmo(clave);
            String mensaje = "Mensaje de prueba 123!";
            String cifrado = algo.cifra(mensaje);
            String descifrado = algo.descifra(cifrado, List.of(clave));
            assertEquals(mensaje, descifrado);
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
        void rendimiento() {
            NuevoAlgoritmo algo = new NuevoAlgoritmo(7);
            String mensaje = "a".repeat(100000);
            long inicio = System.nanoTime();
            algo.cifra(mensaje);
            long tiempo = System.nanoTime() - inicio;
            assertTrue(tiempo < 2_000_000_000L);
        }
    }
}
```

---

## Checklist

Antes de dar por finalizada tu contribución, verifica:

### Código
- [ ] La clase implementa todos los métodos de `Algoritmo`
- [ ] La clase está en el paquete correcto
- [ ] Los constructores validan los parámetros
- [ ] `getParametros()` devuelve un mapa inmutable
- [ ] `setParametros()` valida sin lanzar excepción (informa por `stderr`)
- [ ] `cifra()` y `descifra()` manejan cadenas vacías
- [ ] Los caracteres fuera del abecedario se mantienen sin cambios
- [ ] Se usa `StringBuilder` con capacidad inicial
- [ ] Javadoc en la clase y métodos públicos

### Tests
- [ ] Tests de constructores (válidos e inválidos)
- [ ] Tests de cifrado (vacío, un carácter, wrap-around, caracteres fuera del abc)
- [ ] Tests de descifrado (con clave correcta e incorrecta)
- [ ] Tests de ida y vuelta: `descifra(cifra(m)) == m`
- [ ] Tests parametrizados con múltiples claves
- [ ] Tests de rendimiento (100K caracteres < 2s)
- [ ] Tests con abecedario personalizado
- [ ] Tests de `setParametros` (válido, inválido, stderr)
- [ ] Todos los tests pasan: `mvn test`

### Documentación
- [ ] Actualizar `CHANGELOG.md` con la nueva versión
- [ ] Actualizar `README.md` si se añade un nuevo tipo de algoritmo
- [ ] Documentar el algoritmo en `docs/API.md`

---

## Convenciones de Código

### General

- **Idioma del código**: Español (nombres de variables, comentarios, Javadoc)
- **Idioma de los identificadores Java**: Se permite inglés para constantes (`nombre`, `parametros`)
- **Encoding**: UTF-8
- **Java**: Versión 15+

### Formato

- Indentación: 4 espacios (no tabuladores)
- Llaves: Estilo K&R (llave de apertura en la misma línea)
- Línea máxima: 120 caracteres

### Nomenclatura

| Elemento | Convención | Ejemplo |
|----------|-----------|---------|
| Clases | PascalCase | `Caesar`, `Abecedario` |
| Métodos | camelCase | `cifra()`, `descifra()` |
| Variables | camelCase | `abecedario`, `nuevaPosicion` |
| Constantes | UPPER_SNAKE_CASE | `NOMBRE` (recomendado) |
| Paquetes | minúsculas | `algoritmos_criptograficos` |
| Tests | camelCase descriptivo | `constructorConClaveValida` |

### Javadoc

Todas las clases y métodos públicos deben tener Javadoc:

```java
/**
 * Cifra el mensaje aplicando la translación a cada carácter.
 *
 * <p>Los caracteres que no pertenecen al abecedario se mantienen sin cambios.</p>
 *
 * @param mensaje texto plano a cifrar. Puede ser vacío
 * @return texto cifrado, con la misma longitud que el original
 * @see #descifra(String, List)
 */
public String cifra(String mensaje) { ... }
```

---

## Estructura de Paquetes

```
src/main/java/app/
├── ParametroIncorrecto.java
└── algoritmos_criptograficos/
    ├── Algoritmo.java              ← Interfaz base
    ├── Abecedario.java             ← Gestión de abecedarios
    ├── clasicos/                   ← Algoritmos clásicos
    │   ├── Caesar.java             ✅ Implementado
    │   ├── Vigenere.java           📝 Por implementar
    │   └── Atbash.java             📝 Por implementar
    ├── simetricos/                 ← Algoritmos simétricos
    │   ├── AES.java                📝 Por implementar
    │   └── DES.java                📝 Por implementar
    └── asimetricos/                ← Algoritmos asimétricos
        └── RSA.java                📝 Por implementar

src/test/java/app/
└── algoritmos_criptograficos/
    └── clasicos/
        ├── CaesarTest.java         ✅ 108 tests
        ├── VigenereTest.java        📝 Por crear
        └── AtbashTest.java          📝 Por crear
```

