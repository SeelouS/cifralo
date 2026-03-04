package app;

import app.algoritmos_criptograficos.Abecedario;
import app.algoritmos_criptograficos.clasicos.Caesar;

import java.util.ArrayList;
import java.util.List;

public class main{
    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("PRUEBAS DEL CIFRADO CÉSAR");
        System.out.println("=".repeat(60));

        // Test 1: Constructor por defecto
        System.out.println("\n🔹 Test 1: Caesar con clave por defecto (2)");
        Caesar caesar1 = new Caesar();
        System.out.println(caesar1.info());

        String mensaje1 = "Hola Mundo!";
        System.out.println("Mensaje original: " + mensaje1);
        String cifrado1 = caesar1.cifra(mensaje1);
        System.out.println("Mensaje cifrado: " + cifrado1);
        String descifrado1 = caesar1.descifra(cifrado1, false);
        System.out.println("Mensaje descifrado: " + descifrado1);
        System.out.println("✅ ¿Coincide? " + mensaje1.equals(descifrado1));

        // Test 2: Constructor con clave personalizada
        System.out.println("\n" + "=".repeat(60));
        System.out.println("🔹 Test 2: Caesar con clave personalizada (5)");
        Caesar caesar2 = new Caesar(5);
        System.out.println(caesar2.info());

        String mensaje2 = "Ciberseguridad 2024!";
        System.out.println("Mensaje original: " + mensaje2);
        String cifrado2 = caesar2.cifra(mensaje2);
        System.out.println("Mensaje cifrado: " + cifrado2);
        String descifrado2 = caesar2.descifra(cifrado2, false);
        System.out.println("Mensaje descifrado: " + descifrado2);
        System.out.println("✅ ¿Coincide? " + mensaje2.equals(descifrado2));

        // Test 3: Descifrado con clave explícita
        System.out.println("\n" + "=".repeat(60));
        System.out.println("🔹 Test 3: Descifrar con parámetros explícitos");
        Caesar caesar3 = new Caesar(10);
        String mensaje3 = "Java es genial!!!";
        System.out.println("Mensaje original: " + mensaje3);
        String cifrado3 = caesar3.cifra(mensaje3);
        System.out.println("Mensaje cifrado: " + cifrado3);

        // Descifrar con la clave correcta
        String descifrado3 = caesar3.descifra(cifrado3, List.of(10));
        System.out.println("Descifrado con clave 10: " + descifrado3);
        System.out.println("✅ ¿Coincide? " + mensaje3.equals(descifrado3));

        // Descifrar con clave incorrecta
        String descifradoIncorrecto = caesar3.descifra(cifrado3, List.of(5));
        System.out.println("Descifrado con clave 5 (incorrecta): " + descifradoIncorrecto);
        System.out.println("❌ ¿Coincide? " + mensaje3.equals(descifradoIncorrecto));

        // Test 4: Mensaje con caracteres especiales
        System.out.println("\n" + "=".repeat(60));
        System.out.println("🔹 Test 4: Mensaje con números y símbolos");
        Caesar caesar4 = new Caesar(3);
        String mensaje4 = "Password123!@#";
        System.out.println("Mensaje original: " + mensaje4);
        String cifrado4 = caesar4.cifra(mensaje4);
        System.out.println("Mensaje cifrado: " + cifrado4);
        String descifrado4 = caesar4.descifra(cifrado4, false);
        System.out.println("Mensaje descifrado: " + descifrado4);
        System.out.println("✅ ¿Coincide? " + mensaje4.equals(descifrado4));

        // Test 5: Mensaje largo (prueba de rendimiento)
        System.out.println("\n" + "=".repeat(60));
        System.out.println("🔹 Test 5: Mensaje largo (prueba de rendimiento)");
        Caesar caesar5 = new Caesar(7);
        String mensaje5 = "Este es un mensaje mucho más largo para probar el rendimiento " +
                         "del algoritmo de cifrado César con StringBuilder optimizado. " +
                         "¿Funcionará correctamente? ¡Vamos a verlo!";
        System.out.println("Mensaje original: " + mensaje5);

        long inicio = System.nanoTime();
        String cifrado5 = caesar5.cifra(mensaje5);
        long tiempoCifrado = System.nanoTime() - inicio;
        System.out.println("Mensaje cifrado: " + cifrado5);
        System.out.println("⏱️ Tiempo de cifrado: " + tiempoCifrado/1000.0 + " microsegundos");

        inicio = System.nanoTime();
        String descifrado5 = caesar5.descifra(cifrado5, false);
        long tiempoDescifrado = System.nanoTime() - inicio;
        System.out.println("Mensaje descifrado: " + descifrado5);
        System.out.println("⏱️ Tiempo de descifrado: " + tiempoDescifrado/1000.0 + " microsegundos");
        System.out.println("✅ ¿Coincide? " + mensaje5.equals(descifrado5));

        // Test 6: Abecedario personalizado
        System.out.println("\n" + "=".repeat(60));
        System.out.println("🔹 Test 6: Caesar con abecedario personalizado (solo letras)");
        List<Character> letrasMinusculas = new ArrayList<>();
        for (char c = 'a'; c <= 'z'; c++) {
            letrasMinusculas.add(c);
        }
        Abecedario abcPersonalizado = new Abecedario(letrasMinusculas);
        Caesar caesar6 = new Caesar(3, abcPersonalizado);
        System.out.println(caesar6.info());
        System.out.println("Abecedario: " + abcPersonalizado);

        String mensaje6 = "hola";
        System.out.println("Mensaje original: " + mensaje6);
        String cifrado6 = caesar6.cifra(mensaje6);
        System.out.println("Mensaje cifrado: " + cifrado6);
        String descifrado6 = caesar6.descifra(cifrado6, false);
        System.out.println("Mensaje descifrado: " + descifrado6);
        System.out.println("✅ ¿Coincide? " + mensaje6.equals(descifrado6));

        // Resumen final
        System.out.println("\n" + "=".repeat(60));
        System.out.println("✅ TODAS LAS PRUEBAS COMPLETADAS");
        System.out.println("=".repeat(60));
        System.out.println("\n💡 Consejo: Para probar fuerza bruta, usa:");
        System.out.println("   caesar.descifra(mensajeCifrado, true)");
    }
}
