# Estrategia de Testing

## Índice

- [Resumen](#resumen)
- [Herramientas](#herramientas)
- [Ejecución](#ejecución)
- [Estructura de los Tests](#estructura-de-los-tests)
- [Catálogo de Tests](#catálogo-de-tests)
- [Técnicas de Testing Aplicadas](#técnicas-de-testing-aplicadas)
- [Matriz de Cobertura](#matriz-de-cobertura)
- [Convenciones](#convenciones)

---

## Resumen

| Métrica | Valor |
|---------|-------|
| **Tests totales** | 108 |
| **Tests pasados** | 108 |
| **Tests fallidos** | 0 |
| **Tests ignorados** | 0 |
| **Grupos (clases @Nested)** | 13 |
| **Tiempo total** | ~0.2 s |
| **Framework** | JUnit Jupiter 5.10.2 |
| **Última ejecución** | 2026-03-04 |

---

## Herramientas

| Herramienta | Versión | Propósito |
|-------------|---------|-----------|
| JUnit Jupiter | 5.10.2 | Framework de tests |
| Maven Surefire | 3.2.5 | Ejecución de tests |
| `@ParameterizedTest` | JUnit 5 | Tests parametrizados |
| `@Nested` | JUnit 5 | Agrupación jerárquica |
| `ByteArrayOutputStream` | Java stdlib | Captura de `stderr` |

---

## Ejecución

### Ejecutar todos los tests

```bash
mvn test
```

### Ejecutar un grupo específico

```bash
mvn test -Dtest="CaesarTest\$ConstructorTests"
mvn test -Dtest="CaesarTest\$CifradoTests"
```

### Ejecutar un test individual

```bash
mvn test -Dtest="CaesarTest\$ConstructorTests#constructorConClaveValida"
```

### Salida esperada

```
Tests run: 108, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

---

## Estructura de los Tests

Los tests están organizados jerárquicamente usando `@Nested` para agrupar por funcionalidad:

```
CaesarTest
├── ConstructorTests           (19 tests)
├── GettersInfoTests           ( 5 tests)
├── SetParametrosTests         (10 tests)
├── CifradoTests               (16 tests)
├── DescifradoTests            ( 7 tests)
├── IdaYVueltaTests            (19 tests)
├── AbecedarioPersonalizadoTests( 6 tests)
├── PropiedadesCriptograficasTests( 6 tests)
├── CasosLimiteTests           ( 8 tests)
├── DescifraSinFuerzaBrutaTests( 4 tests)
├── ConsistenciaDescifradoTests( 3 tests)
├── RendimientoTests           ( 2 tests)
└── InterfazAlgoritmoTests     ( 3 tests)
                         TOTAL: 108 tests
```

---

## Catálogo de Tests

### 1. Constructores (19 tests)

| # | Test | Tipo | Descripción |
|---|------|------|-------------|
| 1 | `constructorConClaveValida` | Positivo | Clave = 5, verifica parámetro almacenado |
| 2 | `constructorConClaveUno` | Positivo | Clave = 1 (mínima válida) |
| 3 | `constructorConVariasClavesValidas` | Parametrizado | Claves: 1, 2, 5, 10, 20, 50 |
| 4 | `constructorConClaveCero` | Negativo | Clave = 0 → `ParametroIncorrecto` |
| 5 | `constructorConClaveNegativa` | Negativo | Clave = -1 → `ParametroIncorrecto` |
| 6 | `constructorConClaveMuyNegativa` | Negativo | Clave = -100 → `ParametroIncorrecto` |
| 7 | `constructorConClaveIgualTamano` | Límite | Clave = N → `ParametroIncorrecto` |
| 8 | `constructorConClaveDemasiadoGrande` | Negativo | Clave = 9999 → `ParametroIncorrecto` |
| 9 | `constructorConClaveMaximaValida` | Límite | Clave = N-1, verifica que funciona |
| 10 | `constructorConAbecedarioPersonalizado` | Positivo | Abc custom + clave válida |
| 11 | `constructorConAbecedarioPersonalizadoClaveInvalida` | Negativo | Abc de 3 chars + clave = 3 → excepción |
| 12 | `constructorConAbecedarioPersonalizadoClaveCero` | Negativo | Abc custom + clave = 0 → excepción |
| 13 | `constructorConAbecedarioPersonalizadoClaveLimite` | Límite | Abc de 4 chars + clave = 3 |
| 14 | `mensajeExcepcionContienRango` | Negativo | Verifica que el mensaje de error es informativo |

### 2. Getters e info (5 tests)

| # | Test | Tipo | Descripción |
|---|------|------|-------------|
| 1 | `getNombre` | Positivo | Retorna `"Caesar"` |
| 2 | `getParametrosContieneClave` | Positivo | Contiene `"translacion"` con valor correcto |
| 3 | `getParametrosEsInmutable` | Seguridad | Modificar el mapa lanza `UnsupportedOperationException` |
| 4 | `getParametrosTamano` | Positivo | Mapa tiene exactamente 1 entrada |
| 5 | `infoContieneNombreYParametros` | Positivo | String contiene "Caesar", "translacion" y el valor |

### 3. setParametros (10 tests)

| # | Test | Tipo | Descripción |
|---|------|------|-------------|
| 1 | `setParametrosActualizaClave` | Positivo | Valor válido actualiza correctamente |
| 2 | `setParametrosListaVaciaNoLanzaExcepcion` | Negativo | Lista vacía → no cambia, escribe a stderr |
| 3 | `setParametrosDemasiadosParametrosNoLanzaExcepcion` | Negativo | 2 params → no cambia, stderr |
| 4 | `setParametrosTresParametrosNoLanzaExcepcion` | Negativo | 3 params → no cambia |
| 5 | `setParametrosConClaveCeroNoActualiza` | Negativo | Clave 0 → no cambia, stderr |
| 6 | `setParametrosConClaveNegativaNoActualiza` | Negativo | Clave -5 → no cambia |
| 7 | `setParametrosConClaveDemasiadoGrandeNoActualiza` | Negativo | Clave ≥ N → no cambia |
| 8 | `cifrarTrasSetParametrosValido` | Integración | Cifrado usa nueva clave |
| 9 | `cifrarTrasSetParametrosInvalidoUsaClaveAnterior` | Integración | Cifrado sigue con clave anterior |
| 10 | `multiplesSetParametros` | Secuencial | Solo aplica la última clave válida |

### 4. Cifrado (16 tests)

| # | Test | Tipo | Descripción |
|---|------|------|-------------|
| 1 | `cifrarCadenaVacia` | Límite | `""` → `""` |
| 2 | `cifrarUnCaracter` | Positivo | `"a"` → `"b"` con clave 1 |
| 3 | `cifrarProduceTextoDiferente` | Positivo | Cifrado ≠ original |
| 4 | `cifrarConWrapAround` | Límite | Último carácter → primero |
| 5 | `cifrarPalabraAbcPersonalizado` | Positivo | `"abcde"` → `"bcdea"` |
| 6 | `cifrarMantieneCaracteresNoEnAbecedario` | Especial | Caracteres fuera del abc se conservan |
| 7–13 | `cifrarConDistintasClaves` | Parametrizado | 7 claves distintas, todas producen cifrado ≠ original |
| 14 | `cifrarMensajeLargo` | Rendimiento | 10.000 chars, preserva longitud |
| 15 | `cifrarPreservaLongitud` | Propiedad | `len(cifrado) == len(original)` |
| 16 | `cifrarCaracterRepetido` | Propiedad | `"aaaa"` → todos los chars iguales |

### 5. Descifrado (7 tests)

| # | Test | Tipo | Descripción |
|---|------|------|-------------|
| 1 | `descifrarCadenaVacia` | Límite | `""` → `""` |
| 2 | `descifrarUnCaracter` | Positivo | `"b"` → `"a"` con clave 1 |
| 3 | `descifrarConWrapAroundHaciaAtras` | Límite | Primer carácter → último |
| 4 | `descifrarPreservaLongitud` | Propiedad | Longitud se mantiene |
| 5 | `descifrarConClaveIncorrecta` | Negativo | Clave incorrecta → resultado ≠ original |
| 6 | `descifrarMantieneCaracteresFuera` | Especial | Chars fuera del abc se conservan |
| 7 | `descifrarConClaveCero` | Especial | Clave 0 → texto sin alterar |

### 6. Ida y vuelta (19 tests)

| # | Test | Tipo | Descripción |
|---|------|------|-------------|
| 1–7 | `cifrarYDescifrarEsIdentidad` | Parametrizado | 7 claves: `descifra(cifra(msg)) == msg` |
| 8 | `idaYVueltaMensajeVacio` | Límite | Cadena vacía |
| 9 | `idaYVueltaEspacio` | Límite | Solo un espacio |
| 10–15 | `idaYVueltaParametrizado` | Parametrizado | 6 combinaciones mensaje+clave |
| 16 | `idaYVueltaConDescifraSinFuerzaBruta` | Integración | Usando `descifra(msg, false)` |
| 17 | `idaYVueltaTodosLosCaracteres` | Exhaustivo | ASCII 32–126 |
| 18 | `idaYVueltaAbecedarioPersonalizado` | Variante | abc de a–z |
| 19 | `idaYVueltaClaveMaxima` | Límite | Clave = N-1 |

### 7. Abecedario personalizado (6 tests)

| # | Test | Descripción |
|---|------|-------------|
| 1 | `soloMinusculas` | `"hola"` → `"krod"` con clave 3 |
| 2 | `soloDigitos` | `"123"` → `"456"` |
| 3 | `soloDigitosWrapAround` | `"890"` → `"123"` |
| 4 | `abecedarioDeDos` | `"ab"` → `"ba"` con clave 1 |
| 5 | `abecedarioDeTres` | `"xyz"` → `"zxy"` con clave 2 |
| 6 | `caracteresNoPresentesSeMantienenConAbcPersonalizado` | `"a1c"` → `"b1a"` |

### 8. Propiedades criptográficas (6 tests)

| # | Test | Propiedad |
|---|------|-----------|
| 1 | `dosClavesDiferentes` | k₁ ≠ k₂ ⟹ C(m, k₁) ≠ C(m, k₂) |
| 2 | `determinismo` | C(m, k) = C(m, k) siempre |
| 3 | `dobleCifrado` | C(C(m)) ≠ C(m) |
| 4 | `dobleCifradoDobleDescifrado` | D(D(C(C(m)))) = m |
| 5 | `cifradoComplementario` | C(C(m, k), N-k) = m |
| 6 | `cifradoSumaClaves` | C(C(m, k₁), k₂) = m si k₁+k₂ = N |

### 9. Casos límite (8 tests)

| # | Test | Descripción |
|---|------|-------------|
| 1 | `cifrarPreservaEspacios` | Espacios se cifran, longitud se mantiene |
| 2 | `cifrarConClaveMaxima` | Clave N-1, ida y vuelta funciona |
| 3 | `cifrarUltimoCaracterAbecedario` | Wrap-around del último al primero |
| 4 | `cifrarPrimerCaracterAbecedario` | Primer carácter se desplaza correctamente |
| 5 | `descifrarPrimerCaracterWrapAround` | Wrap-around inverso |
| 6 | `mensajeSoloCaracteresFuera` | Mensaje intacto si ningún char está en abc |
| 7 | `mensajeMixto` | Mezcla de chars dentro y fuera del abc |
| 8 | `unSoloCaracterIdaYVuelta` | Un solo carácter, ida y vuelta |

### 10. descifra(msg, false) (4 tests)

| # | Test | Descripción |
|---|------|-------------|
| 1 | `descifraSinFuerzaBrutaUsaClave` | Usa la clave del constructor |
| 2 | `descifraSinFuerzaBrutaTrasSetParametros` | Usa clave actualizada |
| 3 | `descifraSinFuerzaBrutaCadenaVacia` | Cadena vacía |
| 4 | `descifraTrasSetParametrosInvalido` | Tras set inválido, usa clave original |

### 11. Consistencia descifrado (3 tests)

| # | Test | Descripción |
|---|------|-------------|
| 1 | `consistenciaEntreMetodos` | `descifra(msg, List.of(k))` = `descifra(msg, false)` |
| 2 | `ambosDevuelvenOriginal` | Ambos devuelven el mensaje original |
| 3 | `consistenciaTrasSetParametros` | Consistencia se mantiene tras cambiar clave |

### 12. Rendimiento (2 tests)

| # | Test | Criterio |
|---|------|----------|
| 1 | `rendimientoCifrado` | 100.000 chars cifrados en < 2 s |
| 2 | `rendimientoDescifrado` | 100.000 chars descifrados en < 2 s |

### 13. Interfaz Algoritmo (3 tests)

| # | Test | Descripción |
|---|------|-------------|
| 1 | `caesarImplementaAlgoritmo` | `Caesar instanceof Algoritmo` |
| 2 | `getKeysDevuelveClaves` | `getKeys()` contiene `"translacion"` |
| 3 | `getElemsDevuelveValores` | `getElems()` contiene el valor de la clave |

---

## Técnicas de Testing Aplicadas

### 1. Partición en clases de equivalencia

- **Clave válida**: 1 ≤ k < N
- **Clave inválida baja**: k ≤ 0
- **Clave inválida alta**: k ≥ N

### 2. Análisis de valores límite

- Clave = 0 (justo fuera del rango inferior)
- Clave = 1 (mínimo válido)
- Clave = N-1 (máximo válido)
- Clave = N (justo fuera del rango superior)

### 3. Testing de propiedades (Property-Based)

- **Identidad**: `descifra(cifra(m)) = m`
- **Determinismo**: `cifra(m) = cifra(m)` (misma entrada → misma salida)
- **Complementariedad**: `cifra(cifra(m, k), N-k) = m`
- **Preservación de longitud**: `len(cifra(m)) = len(m)`

### 4. Tests parametrizados

Se usan `@ParameterizedTest` con `@ValueSource` y `@CsvSource` para probar múltiples entradas con el mismo test:

```java
@ParameterizedTest
@ValueSource(ints = {1, 2, 3, 5, 10, 20, 50})
void cifrarYDescifrarEsIdentidad(int clave) { ... }
```

### 5. Captura de stderr

Para verificar que `setParametros` informa correctamente por `stderr`:

```java
@BeforeEach
void capturarStdErr() {
    errContent = new ByteArrayOutputStream();
    System.setErr(new PrintStream(errContent));
}

@AfterEach
void restaurarStdErr() {
    System.setErr(originalErr);
}
```

---

## Matriz de Cobertura

### Cobertura por método de Caesar

| Método | Tests directos | Tests indirectos | Cobertura |
|--------|:-:|:-:|:-:|
| `Caesar(int)` | 9 | 60+ | ✅ Completa |
| `Caesar(int, Abecedario)` | 5 | 15+ | ✅ Completa |
| `getParametros()` | 4 | 90+ | ✅ Completa |
| `getNombre()` | 1 | 1 | ✅ Completa |
| `setParametros()` | 10 | 4 | ✅ Completa |
| `cifra()` | 16 | 30+ | ✅ Completa |
| `descifra(String, List)` | 7 | 25+ | ✅ Completa |
| `descifra(String, boolean)` | 4 | 3 | ✅ Completa |
| `translada()` (privado) | — | 50+ | ✅ Indirecta |
| `parametrosCorrectos()` (privado) | — | 15+ | ✅ Indirecta |
| `descifraBruta()` (privado) | — | — | ⚠️ No testeado (interactivo) |

### Cobertura por tipo de entrada

| Tipo de entrada | Cubierto |
|----------------|:--------:|
| Cadena vacía | ✅ |
| Un solo carácter | ✅ |
| Múltiples caracteres | ✅ |
| Caracteres especiales | ✅ |
| Números | ✅ |
| Espacios | ✅ |
| Caracteres fuera del abecedario | ✅ |
| Mensaje largo (100K chars) | ✅ |
| Abecedario por defecto (ASCII) | ✅ |
| Abecedario personalizado | ✅ |

---

## Convenciones

### Nomenclatura de tests

```
[acción][Contexto][ResultadoEsperado]
```

Ejemplos:
- `constructorConClaveValida` — Acción: constructor, Contexto: clave válida
- `cifrarConWrapAround` — Acción: cifrar, Contexto: wrap-around
- `setParametrosConClaveNegativaNoActualiza` — Acción: setParametros, Contexto: clave negativa, Resultado: no actualiza

### Organización

- Un archivo de test por clase: `CaesarTest.java` para `Caesar.java`.
- Agrupación por funcionalidad con `@Nested`.
- Cada test con `@DisplayName` descriptivo en español.

### Principio AAA (Arrange-Act-Assert)

```java
@Test
void ejemplo() {
    // Arrange
    Caesar caesar = new Caesar(5);
    String mensaje = "Hola";
    
    // Act
    String cifrado = caesar.cifra(mensaje);
    String descifrado = caesar.descifra(cifrado, List.of(5));
    
    // Assert
    assertEquals(mensaje, descifrado);
}
```

