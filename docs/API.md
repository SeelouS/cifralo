# Referencia de la API

## Índice

- [Interfaz Algoritmo](#interfaz-algoritmo)
- [Clase Caesar](#clase-caesar)
- [Clase Abecedario](#clase-abecedario)
- [Clase ParametroIncorrecto](#clase-parametroincorrecto)
- [Ejemplos de Uso](#ejemplos-de-uso)

---

## Interfaz `Algoritmo`

**Paquete**: `app.algoritmos_criptograficos`  
**Tipo**: Interfaz  
**Descripción**: Contrato base que define el comportamiento de cualquier algoritmo criptográfico del sistema.

### Métodos abstractos

---

#### `getParametros()`

```java
Map<String, Integer> getParametros()
```

Devuelve los parámetros del algoritmo como un mapa clave-valor.

| | Descripción |
|---|---|
| **Retorna** | `Map<String, Integer>` — Mapa con los parámetros del algoritmo |
| **Notas** | Las implementaciones deben devolver un mapa inmutable |

---

#### `getNombre()`

```java
String getNombre()
```

Devuelve el nombre identificativo del algoritmo.

| | Descripción |
|---|---|
| **Retorna** | `String` — Nombre del algoritmo (e.g., `"Caesar"`) |

---

#### `setParametros(List<Integer> nuevosParametros)`

```java
void setParametros(List<Integer> nuevosParametros)
```

Actualiza los parámetros del algoritmo.

| | Descripción |
|---|---|
| **Parámetros** | `nuevosParametros` — Lista de valores enteros para los parámetros |
| **Comportamiento** | Depende de la implementación. Puede lanzar excepción o informar por `stderr` |

---

#### `cifra(String mensaje)`

```java
String cifra(String mensaje)
```

Cifra un mensaje de texto plano.

| | Descripción |
|---|---|
| **Parámetros** | `mensaje` — Texto plano a cifrar |
| **Retorna** | `String` — Texto cifrado |

---

#### `descifra(String mensajeCifrado, boolean fuerzaBruta)`

```java
String descifra(String mensajeCifrado, boolean fuerzaBruta)
```

Descifra un mensaje. Si `fuerzaBruta` es `true`, prueba todas las claves posibles de forma interactiva. Si es `false`, utiliza la clave interna del objeto.

| | Descripción |
|---|---|
| **Parámetros** | `mensajeCifrado` — Texto cifrado a descifrar |
| | `fuerzaBruta` — `true` para fuerza bruta interactiva, `false` para usar clave interna |
| **Retorna** | `String` — Texto descifrado |

---

#### `descifra(String mensajeCifrado, List<Integer> parametrosParaDescifrar)`

```java
String descifra(String mensajeCifrado, List<Integer> parametrosParaDescifrar)
```

Descifra un mensaje con una clave explícita proporcionada como parámetro.

| | Descripción |
|---|---|
| **Parámetros** | `mensajeCifrado` — Texto cifrado |
| | `parametrosParaDescifrar` — Lista con la(s) clave(s) de descifrado |
| **Retorna** | `String` — Texto descifrado |

---

### Métodos default

---

#### `info()`

```java
default String info()
```

Devuelve información legible del algoritmo: nombre y parámetros actuales.

| | Descripción |
|---|---|
| **Retorna** | `String` — Cadena con formato `"Algoritmo: <nombre>\nParametros: <params>"` |

**Ejemplo de salida**:
```
Algoritmo: Caesar
Parametros: {translacion=5}
```

---

#### `getKeys(Map<String, Integer> map)`

```java
default Set<String> getKeys(Map<String, Integer> map)
```

Extrae las claves de un mapa de parámetros.

| | Descripción |
|---|---|
| **Parámetros** | `map` — Mapa del que extraer las claves |
| **Retorna** | `Set<String>` — Conjunto de claves |

---

#### `getElems(Map<String, Integer> map)`

```java
default Set<Integer> getElems(Map<String, Integer> map)
```

Extrae los valores de un mapa de parámetros.

| | Descripción |
|---|---|
| **Parámetros** | `map` — Mapa del que extraer los valores |
| **Retorna** | `Set<Integer>` — Conjunto de valores |

---

#### `isDescifrado(Scanner scanner, String input, boolean descifrado, List<Integer> key)`

```java
default boolean isDescifrado(Scanner scanner, String input, boolean descifrado, List<Integer> key)
```

Pregunta al usuario de forma interactiva si el descifrado es correcto. Utilizado en el modo de fuerza bruta.

| | Descripción |
|---|---|
| **Parámetros** | `scanner` — Scanner para leer entrada del usuario |
| | `input` — Variable para almacenar la entrada |
| | `descifrado` — Estado actual de descifrado |
| | `key` — Lista mutable con la clave actual (se incrementa si el usuario responde "N") |
| **Retorna** | `boolean` — `true` si el usuario confirma descifrado correcto |

---

---

## Clase `Caesar`

**Paquete**: `app.algoritmos_criptograficos.clasicos`  
**Implementa**: `Algoritmo`  
**Descripción**: Cifrado César — algoritmo de sustitución monoalfabética por desplazamiento.

### Constructores

---

#### `Caesar(int clave)`

```java
public Caesar(int clave)
```

Crea un cifrador César con el abecedario ASCII por defecto (96 caracteres imprimibles).

| | Descripción |
|---|---|
| **Parámetros** | `clave` — Valor de translación. Debe cumplir: `0 < clave < abecedario.getSize()` |
| **Lanza** | `ParametroIncorrecto` — Si la clave es ≤ 0 o ≥ tamaño del abecedario |

**Ejemplo**:
```java
Caesar caesar = new Caesar(5);       // OK
Caesar caesar = new Caesar(0);       // ParametroIncorrecto
Caesar caesar = new Caesar(-1);      // ParametroIncorrecto
Caesar caesar = new Caesar(9999);    // ParametroIncorrecto
```

---

#### `Caesar(int clave, Abecedario abc)`

```java
public Caesar(int clave, Abecedario abc)
```

Crea un cifrador César con un abecedario personalizado.

| | Descripción |
|---|---|
| **Parámetros** | `clave` — Valor de translación. Debe cumplir: `0 < clave < abc.getSize()` |
| | `abc` — Abecedario personalizado a utilizar |
| **Lanza** | `ParametroIncorrecto` — Si la clave está fuera de rango |

**Ejemplo**:
```java
List<Character> letras = List.of('a', 'b', 'c', 'd', 'e');
Abecedario abc = new Abecedario(letras);
Caesar caesar = new Caesar(2, abc);  // OK, clave ∈ (0, 5)
Caesar caesar = new Caesar(5, abc);  // ParametroIncorrecto
```

---

### Métodos públicos

---

#### `getParametros()`

```java
public Map<String, Integer> getParametros()
```

| | Descripción |
|---|---|
| **Retorna** | `Map<String, Integer>` — Mapa inmutable con la entrada `"translacion" → <valor>` |
| **Notas** | La modificación del mapa retornado lanza `UnsupportedOperationException` |

---

#### `getNombre()`

```java
public String getNombre()
```

| | Descripción |
|---|---|
| **Retorna** | `"Caesar"` |

---

#### `setParametros(List<Integer> nuevosParametros)`

```java
public void setParametros(List<Integer> nuevosParametros)
```

Actualiza la clave de translación. **No lanza excepción** en caso de error: informa por `stderr` y **no modifica** los parámetros.

| | Descripción |
|---|---|
| **Parámetros** | `nuevosParametros` — Lista con exactamente 1 elemento: la nueva clave |
| **Comportamiento en error** | |
| Lista vacía o con más de 1 elemento | Imprime error a `stderr`, no cambia parámetros |
| Clave ≤ 0 o ≥ tamaño abecedario | Imprime error a `stderr`, no cambia parámetros |
| Clave válida | Actualiza la translación |

**Ejemplo**:
```java
Caesar caesar = new Caesar(3);
caesar.setParametros(List.of(10));  // OK, translación = 10
caesar.setParametros(List.of(0));   // Error en stderr, translación sigue siendo 10
caesar.setParametros(List.of());    // Error en stderr, translación sigue siendo 10
caesar.setParametros(List.of(1,2)); // Error en stderr, translación sigue siendo 10
```

---

#### `cifra(String mensaje)`

```java
public String cifra(String mensaje)
```

Cifra el mensaje aplicando la translación a cada carácter.

| | Descripción |
|---|---|
| **Parámetros** | `mensaje` — Texto plano. Puede ser vacío |
| **Retorna** | `String` — Texto cifrado, misma longitud que el original |
| **Complejidad** | O(n), donde n es la longitud del mensaje |
| **Notas** | Los caracteres que no están en el abecedario se mantienen sin cambios |

**Fórmula**: `C(x) = abc[(indexOf(x) + clave) % N]`

---

#### `descifra(String mensajeCifrado, List<Integer> parametrosParaDescifrar)`

```java
public String descifra(String mensajeCifrado, List<Integer> parametrosParaDescifrar)
```

Descifra el mensaje con una clave explícita.

| | Descripción |
|---|---|
| **Parámetros** | `mensajeCifrado` — Texto cifrado |
| | `parametrosParaDescifrar` — Lista con la clave de descifrado en la posición 0 |
| **Retorna** | `String` — Texto descifrado |
| **Complejidad** | O(n) |

**Fórmula**: `D(x) = abc[(indexOf(x) - clave + N) % N]`

---

#### `descifra(String mensajeCifrado, boolean fuerzaBruta)`

```java
public String descifra(String mensajeCifrado, boolean fuerzaBruta)
```

| | Descripción |
|---|---|
| **Si `fuerzaBruta = false`** | Descifra usando la clave interna del objeto |
| **Si `fuerzaBruta = true`** | Prueba claves de 0 a N-1 interactivamente por consola |
| **Retorna** | `String` — Texto descifrado |

---

### Métodos privados

| Método | Descripción |
|--------|-------------|
| `translada(char, int, boolean)` | Aplica la translación a un carácter. `retroceso=false` para cifrar, `true` para descifrar |
| `descifraBruta(String)` | Bucle interactivo de fuerza bruta por consola |
| `parametrosCorrectos(List<Integer>)` | Valida que la clave esté en el rango `(0, tamaño_abecedario)` |

---

---

## Clase `Abecedario`

**Paquete**: `app.algoritmos_criptograficos`  
**Descripción**: Gestiona un conjunto ordenado de caracteres sobre el que operan los algoritmos criptográficos.

### Constructores

---

#### `Abecedario()`

```java
public Abecedario()
```

Crea un abecedario con los caracteres ASCII imprimibles (códigos 0–127, excluyendo caracteres de control y DEL). Resultado: **96 caracteres**.

Caracteres excluidos: códigos 1–31 y 127 (caracteres de control y DEL).

---

#### `Abecedario(List<Character> caracteres)`

```java
public Abecedario(List<Character> caracteres)
```

Crea un abecedario personalizado.

| | Descripción |
|---|---|
| **Parámetros** | `caracteres` — Lista de caracteres. No puede ser `null` ni vacía |
| **Lanza** | `IllegalArgumentException` — Si la lista es nula o vacía |

---

### Métodos públicos

| Método | Retorna | Descripción |
|--------|---------|-------------|
| `getSize()` | `int` | Número de caracteres en el abecedario |
| `getCaracteres()` | `List<Character>` | Lista inmutable de caracteres |
| `contiene(char c)` | `boolean` | `true` si el carácter pertenece al abecedario |
| `getCaracter(int indice)` | `char` | Carácter en la posición dada. Lanza `IndexOutOfBoundsException` si el índice es inválido |
| `indexOf(char caracter)` | `int` | Posición del carácter en el abecedario, o `-1` si no existe |
| `toString()` | `String` | Representación textual del abecedario |

---

---

## Clase `ParametroIncorrecto`

**Paquete**: `app`  
**Extiende**: `RuntimeException`  
**Descripción**: Excepción no comprobada (unchecked) que se lanza cuando se proporcionan parámetros inválidos a un algoritmo.

### Constructor

```java
public ParametroIncorrecto(String message)
```

| | Descripción |
|---|---|
| **Parámetros** | `message` — Mensaje descriptivo del error |

**Ejemplo de mensaje**:
```
El parámetro que has pasado no es correcto. Comprueba que esté entre 0 y 96 (excluidos)
```

---

---

## Ejemplos de Uso

### Ejemplo 1: Cifrado y descifrado básico

```java
Caesar caesar = new Caesar(5);

String cifrado = caesar.cifra("Hola Mundo!");
// cifrado = "Mtkf%Rzsi~&"

String descifrado = caesar.descifra(cifrado, List.of(5));
// descifrado = "Hola Mundo!"

assert descifrado.equals("Hola Mundo!");
```

### Ejemplo 2: Abecedario personalizado (solo minúsculas)

```java
List<Character> letras = new ArrayList<>();
for (char c = 'a'; c <= 'z'; c++) letras.add(c);

Abecedario abc = new Abecedario(letras);
Caesar caesar = new Caesar(13, abc); // ROT13

String cifrado = caesar.cifra("hola");     // "ubyn"
String descifrado = caesar.descifra(cifrado, List.of(13)); // "hola"
```

### Ejemplo 3: Cambiar clave en tiempo de ejecución

```java
Caesar caesar = new Caesar(3);
caesar.cifra("abc"); // Cifra con clave 3

caesar.setParametros(List.of(10)); // Cambia la clave a 10
caesar.cifra("abc"); // Cifra con clave 10
```

### Ejemplo 4: Uso polimórfico con la interfaz

```java
Algoritmo cifrador = new Caesar(7);
System.out.println(cifrador.info());
String resultado = cifrador.cifra("Polimorfismo");
```

### Ejemplo 5: Manejo de errores

```java
try {
    Caesar caesar = new Caesar(0);  // Lanza ParametroIncorrecto
} catch (ParametroIncorrecto e) {
    System.err.println("Error: " + e.getMessage());
}
```

