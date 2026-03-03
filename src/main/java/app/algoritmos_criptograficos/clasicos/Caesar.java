package app.algoritmos_criptograficos.clasicos;

import app.algoritmos_criptograficos.Abecedario;
import app.algoritmos_criptograficos.Algoritmo;
import app.ParametroIncorrecto;

import java.util.*;

public class Caesar implements Algoritmo {
    private static final String nombre = "Caesar";
    private Dictionary<String, Integer> parametros;
    private Abecedario abecedario;

    public Caesar(){
        this.abecedario = new Abecedario();
        this.parametros = new Hashtable<>(1);
        parametros.put("translacion", 2);

    }

    public Caesar(int clave) {
        this.abecedario = new Abecedario();
        if(clave >= abecedario.getSize()) {
            throw new ParametroIncorrecto("Translacion demasiado grande. Pon un numero más pequeño");
        }
        this.parametros = new Hashtable<>(1);
        parametros.put("translacion", clave);
    }

    public Caesar (int clave, Abecedario abc) {
        this.abecedario = abc;
        if(clave >= abecedario.getSize()) {
            throw new ParametroIncorrecto("Translacion demasiado grande. Pon un numero más pequeño");
        }
        this.parametros = new Hashtable<>(1);
        parametros.put("translacion", clave);
    }

    @Override
    public Dictionary<String, Integer> getParametros() {
        return parametros;
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
        return "";
    }

    private String descifraBruta(String mensajeCifrado) {
        String resultado;
        Scanner scanner = new Scanner(System.in);
        List<Integer> key = new ArrayList<>(1);
        key.set(0, 0);
        Boolean descifrado = false;
        do {
            System.out.println("Probando con key: " + key.get(0));
            resultado = descifra(mensajeCifrado, key);
            System.out.println("Mensahe obtenido con key " + key.get(0) +":\n" + resultado);
            String input="";
            do {
                System.out.println("¿Está correctamente descifrado? (S/N)\n");
                if(scanner.hasNextLine()) {
                    input = scanner.nextLine();
                }
            } while (!input.equals("S") && !input.equals("N"));

            switch (input){
                case "S" -> descifrado = true;
                case "N" -> key.set(0, key.get(0) + 1);
            }

        } while (!descifrado);

        return resultado;
    }

    @Override
    public String descifra(String mensajeCifrado, List<Integer> parametrosParaDescifrar) {
        return "";
    }

    @Override
    public String descifra(String mensajeCifrado, Boolean fuerzaBruta) {
        String resultado;
        if(fuerzaBruta) {
            resultado = descifraBruta(mensajeCifrado);
        } else {
            resultado = descifra(mensajeCifrado, new ArrayList<>(getElems(this.parametros)));
        }
        return resultado;
    }
}
