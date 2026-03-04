# Arquitectura del Sistema

## Índice

- [Visión General](#visión-general)
- [Principios de Diseño](#principios-de-diseño)
- [Diagrama de Clases](#diagrama-de-clases)
- [Componentes](#componentes)
- [Patrones de Diseño](#patrones-de-diseño)
- [Flujo de Datos](#flujo-de-datos)
- [Decisiones Técnicas](#decisiones-técnicas)
- [Extensibilidad](#extensibilidad)

---

## Visión General

El sistema está diseñado como una **biblioteca modular de algoritmos criptográficos** que permite cifrar y descifrar mensajes de texto utilizando diferentes algoritmos. La arquitectura se basa en una interfaz común (`Algoritmo`) que define el contrato para todas las implementaciones, siguiendo el principio de **programar contra interfaces, no contra implementaciones**.

### Capas del sistema

```
┌─────────────────────────────────────────────────────┐
│                   Capa de Cliente                   │
│              (main.java / aplicación)               │
├─────────────────────────────────────────────────────┤
│                Capa de Algoritmos                   │
│         Algoritmo (interfaz) + Caesar, ...          │
├─────────────────────────────────────────────────────┤
│              Capa de Soporte / Modelo               │
│         Abecedario + ParametroIncorrecto            │
└─────────────────────────────────────────────────────┘
```

---

## Principios de Diseño

### SOLID

| Principio | Aplicación |
|-----------|------------|
| **S** — Responsabilidad Única | `Caesar` solo cifra/descifra. `Abecedario` solo gestiona caracteres. `ParametroIncorrecto` solo modela errores. |
| **O** — Abierto/Cerrado | Nuevos algoritmos se añaden creando nuevas clases que implementen `Algoritmo`, sin modificar código existente. |
| **L** — Sustitución de Liskov | Cualquier `Algoritmo` puede ser intercambiado por otro sin romper el código cliente. |
| **I** — Segregación de Interfaces | `Algoritmo` define solo los métodos esenciales para cualquier algoritmo criptográfico. |
| **D** — Inversión de Dependencias | El código cliente depende de la abstracción `Algoritmo`, no de las implementaciones concretas. |

### Otros principios aplicados

- **Fail-fast**: Validación de parámetros en constructores con excepciones descriptivas.
- **Inmutabilidad defensiva**: `getParametros()` y `getCaracteres()` devuelven copias inmutables.
- **Encapsulación**: Campos privados, acceso controlado mediante getters.
- **Composición sobre herencia**: `Caesar` tiene un `Abecedario` (composición), no hereda de él.

---

## Diagrama de Clases

```
                    ┌──────────────────────────────────────┐
                    │        <<interface>> Algoritmo        │
                    ├──────────────────────────────────────┤
                    │ + getParametros(): Map<String,Integer>│
                    │ + getNombre(): String                 │
                    │ + setParametros(List<Integer>): void  │
                    │ + cifra(String): String               │
                    │ + descifra(String, boolean): String   │
                    │ + descifra(String, List<Integer>): String│
                    │ + info(): String              «default»│
                    │ + getKeys(Map): Set<String>   «default»│
                    │ + getElems(Map): Set<Integer>  «default»│
                    │ + isDescifrado(Scanner,...): boolean «d»│
                    └──────────┬───────────────────────────┘
                               │
                               │ implements
              ┌────────────────┼─────────────────┐
              │                │                 │
    ┌─────────▼──────┐  ┌─────▼──────┐  ┌──────▼───────┐
    │    clasicos/    │  │ simetricos/│  │ asimetricos/ │
    │                 │  │  (vacío)   │  │   (vacío)    │
    │  ┌───────────┐  │  └────────────┘  └──────────────┘
    │  │  Caesar   │  │
    │  ├───────────┤  │
    │  │-nombre    │  │         ┌──────────────────────┐
    │  │-parametros│──┼────────►│ ParametroIncorrecto  │
    │  │-abecedario│──┼──┐     │ «RuntimeException»   │
    │  ├───────────┤  │  │     └──────────────────────┘
    │  │+Caesar(int)│ │  │
    │  │+Caesar(int,│ │  │     ┌──────────────────────┐
    │  │ Abecedario)│ │  └────►│     Abecedario       │
    │  │+cifra()   │  │        ├──────────────────────┤
    │  │+descifra()│  │        │-caracteres: List<Char>│
    │  │-translada()│ │        │-size: int             │
    │  │-descifraBruta()│      │-excluidosAscii: List  │
    │  │-parametrosCorrectos() │├──────────────────────┤
    │  └───────────┘  │        │+Abecedario()          │
    └─────────────────┘        │+Abecedario(List<Char>)│
                               │+getCaracteres()       │
                               │+contiene(char)        │
                               │+getCaracter(int)      │
                               │+indexOf(char)         │
                               │+getSize()             │
                               └──────────────────────┘
```

### Relaciones

| Origen | Destino | Tipo | Descripción |
|--------|---------|------|-------------|
| `Caesar` | `Algoritmo` | Implementación | Caesar implementa la interfaz Algoritmo |
| `Caesar` | `Abecedario` | Composición | Caesar contiene un Abecedario |
| `Caesar` | `ParametroIncorrecto` | Dependencia | Caesar lanza esta excepción |
| `Abecedario` | — | Independiente | No depende de ninguna otra clase del dominio |

---

## Componentes

### 1. Interfaz `Algoritmo`

**Paquete**: `app.algoritmos_criptograficos`

Contrato que define el comportamiento de cualquier algoritmo criptográfico. Proporciona:

- **Métodos abstractos**: Operaciones que cada algoritmo debe implementar (cifrar, descifrar, gestión de parámetros).
- **Métodos default**: Funcionalidades comunes como `info()`, `getKeys()`, `getElems()`, `isDescifrado()`.

### 2. Clase `Caesar`

**Paquete**: `app.algoritmos_criptograficos.clasicos`

Implementación del cifrado César. Realiza sustitución monoalfabética desplazando cada carácter una cantidad fija de posiciones en el abecedario.

**Responsabilidades**:
- Cifrar mensajes aplicando translación positiva.
- Descifrar mensajes aplicando translación negativa.
- Validar que los parámetros estén dentro del rango válido.
- Ofrecer descifrado por fuerza bruta interactivo.

### 3. Clase `Abecedario`

**Paquete**: `app.algoritmos_criptograficos`

Gestiona el conjunto de caracteres sobre el que operan los algoritmos.

**Responsabilidades**:
- Almacenar el conjunto de caracteres válidos.
- Proporcionar búsqueda de posiciones (`indexOf`).
- Soportar abecedarios arbitrarios (no solo ASCII).

### 4. Clase `ParametroIncorrecto`

**Paquete**: `app`

Excepción de tipo `RuntimeException` que se lanza cuando se proporcionan parámetros inválidos a un algoritmo.

---

## Patrones de Diseño

### Strategy (implícito)

La interfaz `Algoritmo` actúa como la estrategia base. Cada implementación concreta (`Caesar`, futuros algoritmos) es una estrategia intercambiable. El código cliente puede trabajar con cualquier `Algoritmo` sin conocer la implementación concreta:

```java
Algoritmo cifrador = new Caesar(5);
String cifrado = cifrador.cifra("mensaje");
```

### Template Method (parcial)

Los métodos `default` en `Algoritmo` (`info()`, `getKeys()`, `getElems()`) definen un comportamiento base que las implementaciones heredan automáticamente, pudiendo sobreescribirlos si es necesario.

### Composition

`Caesar` utiliza composición con `Abecedario` en lugar de herencia, lo que permite:
- Reutilizar `Abecedario` en otros algoritmos.
- Cambiar el abecedario en tiempo de construcción.
- Testear ambas clases de forma independiente.

---

## Flujo de Datos

### Cifrado

```
Entrada: "Hola" + clave=3
          │
          ▼
    ┌─────────────┐
    │  cifra()    │
    │             │
    │  Para cada  │
    │  carácter:  │──► translada(char, key, false)
    │             │         │
    │             │         ▼
    │             │    indexOf(char) en Abecedario
    │             │         │
    │             │         ▼
    │             │    (posición + key) % tamaño
    │             │         │
    │             │         ▼
    │             │    abc.get(nuevaPosición)
    │             │
    └─────────────┘
          │
          ▼
Salida: "Krod" (cifrado)
```

### Descifrado

```
Entrada: "Krod" + clave=3
          │
          ▼
    ┌─────────────┐
    │ descifra()  │
    │             │
    │  Para cada  │
    │  carácter:  │──► translada(char, key, true)
    │             │         │
    │             │         ▼
    │             │    indexOf(char) en Abecedario
    │             │         │
    │             │         ▼
    │             │    (posición - key + tamaño) % tamaño
    │             │         │
    │             │         ▼
    │             │    abc.get(nuevaPosición)
    │             │
    └─────────────┘
          │
          ▼
Salida: "Hola" (original)
```

---

## Decisiones Técnicas

### ¿Por qué `Map` en lugar de `Dictionary`?

| Aspecto | Dictionary (Hashtable) | Map (HashMap) |
|---------|----------------------|---------------|
| Estado | Obsoleto desde Java 1.2 | Estándar actual |
| Sincronización | Sí (innecesaria aquí) | No (más rápido) |
| API | `Enumeration` (antigua) | `keySet()`, `values()` |
| Rendimiento | ~20-30% más lento | Más rápido |

### ¿Por qué `StringBuilder` con capacidad inicial?

```java
// Evita redimensionamientos internos del buffer
StringBuilder resultado = new StringBuilder(mensaje.length());
```

Al conocer de antemano el tamaño del resultado (igual al del mensaje), reservamos la memoria necesaria desde el principio, evitando copias innecesarias.

### ¿Por qué `setParametros` no lanza excepción?

Se optó por un enfoque defensivo: en lugar de interrumpir la ejecución, `setParametros` informa del error por `stderr` y **no modifica** los parámetros actuales. Esto permite un uso más flexible en contextos interactivos donde el usuario puede equivocarse.

### ¿Por qué `getParametros()` devuelve un mapa inmutable?

```java
return Collections.unmodifiableMap(parametros);
```

Para preservar la encapsulación y evitar que código externo modifique el estado interno del objeto sin pasar por la validación de `setParametros()`.

---

## Extensibilidad

### Añadir un nuevo algoritmo clásico

1. Crear la clase en `app.algoritmos_criptograficos.clasicos`.
2. Implementar la interfaz `Algoritmo`.
3. Definir la lógica de cifrado y descifrado.
4. Crear tests en `src/test/java/app/algoritmos_criptograficos/clasicos/`.

### Añadir un algoritmo simétrico/asimétrico

1. Crear la clase en el paquete correspondiente (`simetricos/` o `asimetricos/`).
2. Implementar `Algoritmo`.
3. Si se necesitan parámetros adicionales (e.g., IV, modo de operación), extender el mapa de parámetros.

### Ejemplo de extensión

```java
public class Vigenere implements Algoritmo {
    private Map<String, Integer> parametros;
    private Abecedario abecedario;
    private String claveTexto;
    
    // Implementar todos los métodos de Algoritmo...
}
```

