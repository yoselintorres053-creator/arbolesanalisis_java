package arboles_analisis;
import java.util.Scanner;

public class Arboles_analisis {

    // Esta funcion evalua el arbol
    public static double Evaluar(Nodo nodo) {
        // Si no tiene hijos, es un numero
        if (nodo.izquierda == null && nodo.derecha == null) {
            return Double.parseDouble(nodo.valor);
        }

        // Si si tiene hijos, evaluo a los dos
        double izquierda = Evaluar(nodo.izquierda);
        double derecha = Evaluar(nodo.derecha);

        // Hago la operacion segun el signo
        switch (nodo.valor) {
            case "+": return izquierda + derecha;
            case "-": return izquierda - derecha;
            case "*": return izquierda * derecha;
            case "/":
                if (derecha == 0)
                    throw new ArithmeticException("No se puede dividir entre cero");
                return izquierda / derecha;
            default:
                throw new IllegalArgumentException("Operador desconocido: " + nodo.valor);
        }
    }

    // Esta funcion arma el arbol desde el texto
    public static Nodo ConstruirArbol(String expr) {
        // Quito espacios
        expr = expr.replace(" ", "");

        // Si toda la expresion esta entre parentesis, los quito
        // Ejemplo: (3+5) -> 3+5
        while (expr.startsWith("(") && expr.endsWith(")") && ParentesisBien(expr)) {
            expr = expr.substring(1, expr.length() - 1);
        }

        // Busco el signo mas debil, primero + y -
        // Lo busco de derecha a izquierda para que respete el orden
        int pos = -1;
        int nivel = 0; // Para no contar los que estan dentro de ( )

        for (int i = expr.length() - 1; i >= 0; i--) {
            if (expr.charAt(i) == ')') nivel++;
            if (expr.charAt(i) == '(') nivel--;

            // Solo me fijo si no estoy dentro de un parentesis
            if (nivel == 0 && (expr.charAt(i) == '+' || expr.charAt(i) == '-')) {
                // El - al principio es negativo, no es operacion
                if (i == 0) continue;
                pos = i;
                break;
            }
        }

        // Si no encontre + o -, busco * o /
        if (pos == -1) {
            nivel = 0;
            for (int i = expr.length() - 1; i >= 0; i--) {
                if (expr.charAt(i) == ')') nivel++;
                if (expr.charAt(i) == '(') nivel--;

                if (nivel == 0 && (expr.charAt(i) == '*' || expr.charAt(i) == '/')) {
                    pos = i;
                    break;
                }
            }
        }

        // Si no encontre ningun signo, es solo un numero
        if (pos == -1) {
            return new Nodo(expr);
        }

        // Si si encontre, parto la expresion en 2
        String izq = expr.substring(0, pos);
        String signo = String.valueOf(expr.charAt(pos));
        String der = expr.substring(pos + 1);

        // Armo el nodo con ese signo
        Nodo raiz = new Nodo(signo);
        raiz.izquierda = ConstruirArbol(izq);
        raiz.derecha = ConstruirArbol(der);

        return raiz;
    }

    // Reviso que los parentesis de afuera si sean pareja
    static boolean ParentesisBien(String expr) {
        int nivel = 0;
        for (int i = 0; i < expr.length() - 1; i++) {
            if (expr.charAt(i) == '(') nivel++;
            if (expr.charAt(i) == ')') nivel--;
            if (nivel == 0) return false;
        }
        return true;
    }

    public static void main(String[] args) {

    java.awt.EventQueue.invokeLater(() -> {
        new igu.Pantalla().setVisible(true);
    });

}
    }
