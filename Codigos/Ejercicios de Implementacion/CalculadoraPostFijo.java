import java.util.Stack;

public class CalculadoraPostFijo {
    public static void main(String[] args) {
        String array1[] = {"2", "3", "+", "3", "3", "+", "*"};
        // Usar una pila para evaluar la expresión
        Stack<Integer> stack = new Stack<>();

        // Recorrer cada elemento del array
        for (String s : array1) {
            if (isNumeric(s)) {
                // Si es un número, lo apilamos
                stack.push(Integer.parseInt(s));
            } else {
                // Si es un operador, sacamos los dos últimos números de la pila
                int b = stack.pop();
                int a = stack.pop();
                
                // Realizamos la operación según el operador
                int resultado = 0;
                switch (s) {
                    case "+":
                        resultado = a + b;
                        break;
                    case "-":
                        resultado = a - b;
                        break;
                    case "*":
                        resultado = a * b;
                        break;
                    case "/":
                        resultado = a / b;
                        break;
                }
                // Apilamos el resultado
                stack.push(resultado);
            }
        }

        // El resultado final estará en la cima de la pila
        System.out.println(stack.pop()); // Debería imprimir 30
    }

    // Método para verificar si una cadena es numérica
    private static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}