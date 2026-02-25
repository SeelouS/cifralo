package app.algoritmos_criptograficos.clasicos;

import app.algoritmos_criptograficos.Abecedario;
import app.algoritmos_criptograficos.Algoritmo;
import app.ParametroIncorrecto;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class Caesar implements Algoritmo {
    private static final String nombre = "Caesar";
    private Dictionary<String, Integer> parametros;
    private Abecedario abecedario;

    public Caesar(){
        this.abecedario = new Abecedario();
        this.parametros = new Hashtable<>(1);
        parametros.put("Translacion", 2);

    }

    public Caesar(int clave) {
        this.abecedario = new Abecedario();
        if(clave >= abecedario.getSize()) {
            throw new ParametroIncorrecto("Translacion demasiado grande. Pon un numero más pequeño");
        }
        this.parametros = new Hashtable<>(1);
        parametros.put("Translacion", clave);
    }

    public Caesar (int clave, Abecedario abc) {
        this.abecedario = abc;
        if(clave >= abecedario.getSize()) {
            throw new ParametroIncorrecto("Translacion demasiado grande. Pon un numero más pequeño");
        }
        this.parametros = new Hashtable<>(1);
        parametros.put("Translacion", clave);
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

    }

    @Override
    public String cifra(String mensaje) {
        return "";
    }

    @Override
    public String descifra(String mensajeCifrado, Boolean fuerzaBruta) {
        return "";
    }

    @Override
    public String descifra(String mensajeCifrado, List<Integer> parametrosParaDescifrar) {
        return "";
    }
}
