# Changelog

Todos los cambios notables de este proyecto se documentan en este archivo.

El formato está basado en [Keep a Changelog](https://keepachangelog.com/es-ES/1.1.0/), y este proyecto adhiere a [Versionado Semántico](https://semver.org/lang/es/).

---

## [1.0.0] — 2026-03-04

### Añadido

- **Interfaz `Algoritmo`**: Contrato base para todos los algoritmos criptográficos.
  - Métodos: `cifra()`, `descifra()`, `getParametros()`, `setParametros()`, `getNombre()`, `info()`.
  - Métodos default: `getKeys()`, `getElems()`, `isDescifrado()`.
- **Clase `Abecedario`**: Gestión de conjuntos de caracteres.
  - Constructor por defecto (ASCII imprimible, 96 caracteres).
  - Constructor personalizado con lista de caracteres arbitraria.
  - Métodos: `getCaracteres()`, `contiene()`, `getCaracter()`, `indexOf()`, `getSize()`.
  - Lista de caracteres inmutable desde el exterior (`Collections.unmodifiableList`).
- **Clase `Caesar`**: Implementación del cifrado César.
  - Cifrado por sustitución monoalfabética con clave de translación.
  - Constructor `Caesar(int clave)` con abecedario ASCII por defecto.
  - Constructor `Caesar(int clave, Abecedario abc)` con abecedario personalizado.
  - Cifrado: `cifra(String mensaje)`.
  - Descifrado con clave explícita: `descifra(String msg, List<Integer> params)`.
  - Descifrado con clave interna: `descifra(String msg, false)`.
  - Descifrado por fuerza bruta interactivo: `descifra(String msg, true)`.
  - Validación de parámetros: clave ∈ (0, tamaño_abecedario).
  - `setParametros()` con validación segura (no lanza excepción, informa por `stderr`).
  - `getParametros()` devuelve mapa inmutable.
- **Clase `ParametroIncorrecto`**: Excepción personalizada (`RuntimeException`).
- **Suite de tests `CaesarTest`**: 108 tests unitarios con JUnit 5.
  - 13 grupos de tests organizados con `@Nested`.
  - Tests parametrizados con `@ParameterizedTest`.
  - Cobertura de constructores, cifrado, descifrado, ida y vuelta, propiedades criptográficas, casos límite, rendimiento y consistencia.
- **Documentación completa**:
  - `README.md` — Visión general del proyecto.
  - `CHANGELOG.md` — Historial de cambios (este archivo).
  - `docs/ARCHITECTURE.md` — Arquitectura y decisiones de diseño.
  - `docs/API.md` — Referencia completa de la API.
  - `docs/TESTING.md` — Estrategia y cobertura de testing.
  - `docs/CONTRIBUTING.md` — Guía para añadir nuevos algoritmos.
- **Configuración Maven** (`pom.xml`):
  - Java 15, JUnit Jupiter 5.10.2, Maven Surefire 3.2.5.

### Decisiones técnicas

- `Map<String, Integer>` en lugar de `Dictionary` (obsoleto desde Java 1.2).
- `HashMap` en lugar de `Hashtable` (mejor rendimiento sin sincronización innecesaria).
- `StringBuilder` con capacidad inicial para evitar redimensionamientos.
- Try-with-resources en `descifraBruta()` para evitar memory leaks.
- Patrón Strategy implícito mediante la interfaz `Algoritmo`.

---

## [Unreleased]

### Planificado

- Implementación de algoritmos simétricos (AES, DES).
- Implementación de algoritmos asimétricos (RSA).
- Cifrado de Vigenère (clásico).
- Cifrado Atbash (clásico).
- Análisis de frecuencias para descifrado automático.
- Interfaz gráfica o CLI interactivo.
- Generación automática de Javadoc.

