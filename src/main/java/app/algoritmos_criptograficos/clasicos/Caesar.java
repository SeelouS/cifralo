package app.algoritmos_criptograficos.clasicos;

import app.algoritmos_criptograficos.Abecedario;
import app.algoritmos_criptograficos.Algoritmo;
import app.ParametroIncorrecto;

import java.util.*;

public class Caesar implements Algoritmo {
    private static final String nombre = "Caesar";
    private Map<String, Integer> parametros;
    private Abecedario abecedario;

    public Caesar(){
        this.abecedario = new Abecedario();
        this.parametros = new HashMap<>(1);
        parametros.put("translacion", 2);

    }

    public Caesar(int clave) {
        this.abecedario = new Abecedario();
        if(clave >= abecedario.getSize()) {
            throw new ParametroIncorrecto("Translacion demasiado grande. Pon un numero más pequeño");
        }
        this.parametros = new HashMap<>(1);
        parametros.put("translacion", clave);
    }

    public Caesar (int clave, Abecedario abc) {
        this.abecedario = abc;
        if(clave >= abecedario.getSize()) {
            throw new ParametroIncorrecto("Translacion demasiado grande. Pon un numero más pequeño");
        }
        this.parametros = new HashMap<>(1);
        parametros.put("translacion", clave);
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
        if (nuevosParametros.size() != this.parametros.size()) {
            throw new ParametroIncorrecto("Has definido un número incorrecto de parámetros.|" +
                    "\n" + "Definido: " + nuevosParametros.size() + "\n" + "Necesarios: " + this.parametros.size());
        }
        int posLista = 0;
        for (String key: getKeys(this.parametros)) {
            this.parametros.put(key, nuevosParametros.get(posLista));
            posLista++;
        }

    }

    @Override
    public String cifra(String mensaje) {
        int key = parametros.get("translacion");
        List<Character> abc = abecedario.getCaracteres();
        int abcSize = abc.size();
        StringBuilder resultado = new StringBuilder(mensaje.length());

        for (char caracter : mensaje.toCharArray()) {
            int posicion = abecedario.indexOf(caracter);
            if (posicion != -1) {
                // Carácter encontrado en el abecedario: aplicar translación
                int nuevaPosicion = (posicion + key) % abcSize;
                resultado.append(abc.get(nuevaPosicion));
            } else {
                // Carácter no está en el abecedario: mantenerlo sin cambios
                resultado.append(caracter);
            }
        }

        return resultado.toString();
    }

    private char translada(char caracter, int key, boolean retroceso) {
        List<Character> abc = abecedario.getCaracteres();
        int abcSize = abc.size();
        int posicion = abecedario.indexOf(caracter);

        if (posicion == -1) {
            // Carácter no está en el abecedario: devolver sin cambios
            return caracter;
        }

        int nuevaPosicion;
        if (retroceso) {
            nuevaPosicion = (posicion - key + abcSize) % abcSize;
        } else {
            nuevaPosicion = (posicion + key) % abcSize;
        }

        return abc.get(nuevaPosicion);
    }

    private String descifraBruta(String mensajeCifrado) {
        String resultado = "";
        try (Scanner scanner = new Scanner(System.in)) {
            List<Integer> key = new ArrayList<>(1);
            key.add(0);
            boolean descifrado = false;

            while (!descifrado && key.get(0) < abecedario.getSize()) {
                System.out.println("Probando con key: " + key.get(0));
                resultado = descifra(mensajeCifrado, key);
                System.out.println("Mensaje obtenido con key " + key.get(0) + ":\n" + resultado);
                descifrado = isDescifrado(scanner, "", false, key);
            }
        }

        return resultado;
    }

    @Override
    public String descifra(String mensajeCifrado, List<Integer> parametrosParaDescifrar) {
        int key = parametrosParaDescifrar.get(0);
        StringBuilder resultado = new StringBuilder(mensajeCifrado.length());

        for (char caracter : mensajeCifrado.toCharArray()) {
            resultado.append(translada(caracter, key, true));
        }

        return resultado.toString();
    }

    @Override
    public String descifra(String mensajeCifrado, boolean fuerzaBruta) {
        String resultado;
        if(fuerzaBruta) {
            resultado = descifraBruta(mensajeCifrado);
        } else {
            resultado = descifra(mensajeCifrado, new ArrayList<>(getElems(this.parametros)));
        }
        return resultado;
    }
}
