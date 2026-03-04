# Criptografía — Biblioteca de Algoritmos Criptográficos

[![Java](https://img.shields.io/badge/Java-15-orange)](https://openjdk.java.net/)
[![Maven](https://img.shields.io/badge/Maven-3.x-blue)](https://maven.apache.org/)
[![Tests](https://img.shields.io/badge/Tests-108%20passed-brightgreen)]()
[![License](https://img.shields.io/badge/License-Academic-lightgrey)]()

> Biblioteca modular de algoritmos criptográficos desarrollada como práctica de la asignatura **Ciberseguridad en Servicios y Aplicaciones** — Universidad de Málaga (UMA), Ingeniería del Software, Curso 2025–2026.

---

## Índice

- [Descripción](#descripción)
- [Requisitos Previos](#requisitos-previos)
- [Instalación](#instalación)
- [Uso Rápido](#uso-rápido)
- [Arquitectura](#arquitectura)
- [Algoritmos Implementados](#algoritmos-implementados)
- [Testing](#testing)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Documentación Adicional](#documentación-adicional)
- [Contribución](#contribución)
- [Autores](#autores)
- [Licencia](#licencia)

---

## Descripción

Esta biblioteca proporciona una implementación limpia, extensible y bien testeada de algoritmos criptográficos clásicos en Java. Está diseñada siguiendo principios SOLID y patrones de diseño orientados a la fácil incorporación de nuevos algoritmos (simétricos, asimétricos y clásicos).

### Características principales

- **Modularidad**: Interfaz `Algoritmo` que permite añadir nuevos cifrados sin modificar código existente (Open/Closed Principle).
- **Abecedarios personalizables**: Soporte para cualquier conjunto de caracteres, no solo ASCII.
- **Fuerza bruta**: Descifrado interactivo por fuerza bruta integrado.
- **Validación robusta**: Control de parámetros con mensajes de error descriptivos.
- **108 tests unitarios**: Cobertura extensiva con JUnit 5.

---

## Requisitos Previos

| Requisito | Versión mínima |
|-----------|---------------|
| **Java JDK** | 15+ |
| **Apache Maven** | 3.6+ |
| **Sistema Operativo** | Windows / macOS / Linux |

Verificar instalación:

```bash
java --version
mvn --version
```

---

## Instalación

```bash
# 1. Clonar o descargar el proyecto
git clone <url-del-repositorio>
cd criptografia

# 2. Compilar el proyecto
mvn clean compile

# 3. Ejecutar los tests
mvn test

# 4. Empaquetar como JAR
mvn package
```

---

## Uso Rápido

### Cifrado César básico

```java
import app.algoritmos_criptograficos.clasicos.Caesar;
import java.util.List;

// Crear cifrador con clave de translación = 5
Caesar caesar = new Caesar(5);

// Cifrar un mensaje
String cifrado = caesar.cifra("Hola Mundo!");
System.out.println(cifrado); // Texto cifrado

// Descifrar con la clave conocida
String descifrado = caesar.descifra(cifrado, List.of(5));
System.out.println(descifrado); // "Hola Mundo!"

// Descifrar usando la clave interna del objeto
String descifrado2 = caesar.descifra(cifrado, false);
```

### Con abecedario personalizado

```java
import app.algoritmos_criptograficos.Abecedario;
import java.util.List;

// Solo letras minúsculas
List<Character> letras = new ArrayList<>();
for (char c = 'a'; c <= 'z'; c++) letras.add(c);

Abecedario abc = new Abecedario(letras);
Caesar caesar = new Caesar(3, abc);

caesar.cifra("hola");   // "krod"
```

### Descifrado por fuerza bruta

```java
Caesar caesar = new Caesar(8);
String cifrado = caesar.cifra("Secreto");

// Prueba todas las claves interactivamente
String resultado = caesar.descifra(cifrado, true);
```

---

## Arquitectura

```
┌─────────────────────────────────────────┐
│            <<interface>>                │
│              Algoritmo                  │
├─────────────────────────────────────────┤
│ + getParametros(): Map<String,Integer>  │
│ + getNombre(): String                   │
│ + setParametros(List<Integer>): void    │
│ + cifra(String): String                 │
│ + descifra(String, boolean): String     │
│ + descifra(String, List<Integer>): String│
│ + info(): String                        │
│ + getKeys(Map): Set<String>             │
│ + getElems(Map): Set<Integer>           │
│ + isDescifrado(Scanner,...): boolean    │
└──────────────────┬──────────────────────┘
                   │ implements
        ┌──────────┼──────────┐
        │          │          │
   ┌────▼───┐ ┌───▼────┐ ┌───▼──────┐
   │Clásicos│ │Simétri.│ │Asimétri. │
   │        │ │(futuro)│ │(futuro)  │
   │ Caesar │ │        │ │          │
   └────────┘ └────────┘ └──────────┘

┌────────────────────────┐
│      Abecedario        │
├────────────────────────┤
│ - caracteres: List     │
│ - size: int            │
│ + getCaracteres()      │
│ + contiene(char)       │
│ + getCaracter(int)     │
│ + indexOf(char)        │
│ + getSize()            │
└────────────────────────┘

┌────────────────────────┐
│   ParametroIncorrecto  │
│  <<RuntimeException>>  │
└────────────────────────┘
```

---

## Algoritmos Implementados

### Cifrado César

| Propiedad | Valor |
|-----------|-------|
| **Tipo** | Cifrado por sustitución (clásico) |
| **Clave** | Número entero ∈ (0, tamaño_abecedario) |
| **Complejidad cifrado** | O(n) |
| **Complejidad descifrado** | O(n) |
| **Complejidad fuerza bruta** | O(n × m), m = tamaño abecedario |
| **Seguridad** | Muy baja (fines educativos) |

**Fórmula:**
- Cifrado: `C(x) = (x + k) mod N`
- Descifrado: `D(x) = (x - k + N) mod N`

Donde `x` es la posición del carácter, `k` es la clave y `N` el tamaño del abecedario.

---

## Testing

### Ejecutar todos los tests

```bash
mvn test
```

### Resultados actuales

```
Tests run: 108, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

### Categorías de tests (13 grupos)

| Grupo | Tests | Descripción |
|-------|------:|-------------|
| Constructores | 19 | Claves válidas, inválidas, abecedarios personalizados |
| Getters e info | 5 | `getNombre`, `getParametros`, inmutabilidad, `info` |
| setParametros | 10 | Actualización válida/inválida, salida stderr |
| Cifrado | 16 | Cadena vacía, wrap-around, preserva longitud, parametrizados |
| Descifrado | 7 | Wrap-around, clave incorrecta, caracteres fuera del abc |
| Ida y vuelta | 19 | `cifra(descifra(msg)) == msg` con múltiples entradas |
| Abecedario personalizado | 6 | Dígitos, minúsculas, abc pequeño |
| Propiedades criptográficas | 6 | Determinismo, doble cifrado, complementariedad |
| Casos límite | 8 | Primer/último carácter, caracteres mixtos |
| descifra(msg, false) | 4 | Clave interna, tras setParametros |
| Consistencia descifrado | 3 | Equivalencia entre métodos de descifrado |
| Rendimiento | 2 | 100.000 caracteres en < 2 s |
| Interfaz Algoritmo | 3 | `instanceof`, `getKeys`, `getElems` |

> Ver documentación detallada en [`docs/TESTING.md`](docs/TESTING.md)

---

## Estructura del Proyecto

```
criptografia/
├── pom.xml                          # Configuración Maven
├── README.md                        # Este archivo
├── CHANGELOG.md                     # Historial de cambios
├── docs/
│   ├── ARCHITECTURE.md              # Arquitectura del sistema
│   ├── API.md                       # Referencia completa de la API
│   ├── TESTING.md                   # Documentación de tests
│   └── CONTRIBUTING.md              # Guía de contribución
├── src/
│   ├── main/java/app/
│   │   ├── ParametroIncorrecto.java # Excepción personalizada
│   │   └── algoritmos_criptograficos/
│   │       ├── Algoritmo.java       # Interfaz base
│   │       ├── Abecedario.java      # Gestión de abecedarios
│   │       ├── clasicos/
│   │       │   └── Caesar.java      # Cifrado César
│   │       ├── simetricos/          # (futuro)
│   │       └── asimetricos/         # (futuro)
│   └── test/java/app/
│       └── algoritmos_criptograficos/
│           └── clasicos/
│               └── CaesarTest.java  # 108 tests unitarios
└── target/                          # Artefactos compilados
```

---

## Documentación Adicional

| Documento | Descripción |
|-----------|-------------|
| [`docs/ARCHITECTURE.md`](docs/ARCHITECTURE.md) | Diseño, patrones y decisiones arquitectónicas |
| [`docs/API.md`](docs/API.md) | Referencia completa de clases y métodos |
| [`docs/TESTING.md`](docs/TESTING.md) | Estrategia de testing y cobertura |
| [`docs/CONTRIBUTING.md`](docs/CONTRIBUTING.md) | Guía para añadir nuevos algoritmos |
| [`CHANGELOG.md`](CHANGELOG.md) | Historial de versiones |

---

## Contribución

¿Quieres añadir un nuevo algoritmo? Consulta la [Guía de Contribución](docs/CONTRIBUTING.md).

Pasos rápidos:

1. Crea una clase en el paquete correspondiente (`clasicos/`, `simetricos/`, `asimetricos/`)
2. Implementa la interfaz `Algoritmo`
3. Añade tests unitarios con JUnit 5
4. Ejecuta `mvn test` y comprueba que pasan todos

---

## Autores

- **Sergio Escalante Presa** — Grado en Ingeniería del Software, Universidad de Málaga

---

## Licencia

Proyecto académico desarrollado para la asignatura **Ciberseguridad en Servicios y Aplicaciones**, 3.er curso, 2.º cuatrimestre. Universidad de Málaga, curso 2025–2026.

Uso exclusivamente educativo.

