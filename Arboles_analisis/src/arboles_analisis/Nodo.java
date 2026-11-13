package arboles_analisis;

public class Nodo {
    String valor;
    Nodo izquierda;
    Nodo derecha;

    public Nodo(String valor) {
        this.valor = valor;
        this.izquierda = null;
        this.derecha = null;
    }
}