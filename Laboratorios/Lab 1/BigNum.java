package Laboratorio_1;

import java.util.Random;

class BigNum {
    public int[] num; // numero representado en sistema decimal, cada casilla digito de 0 a 9.
    public int size; // largo del numero.
    public boolean sign; // positivo = true, negativo = false.

    public BigNum(int num[], int size, boolean sign){
        this.num = num;
        this.size = size;
        this.sign = sign;

    }

public BigNum(int x){

if (x >= 0){
    this.sign = true;
} else {
   this.sign = false;
} //asignar signo.

x = Math.abs(x);    // Valor absoluto de x
String NumToString = Integer.toString(x); // Convertir int a string
this.size = NumToString.length(); // largo del nro
this.num = new int[this.size]; // crea array del largo del nro

for (int i = 0; i < this.size; i++){
    this.num[i] = Character.getNumericValue((NumToString.charAt(i)));
}
}

public BigNum(String x, boolean sign){
    
    this.sign = sign;  // asignar signo.
    this.size = x.length();  // Tamaño del numero
    this.num = new int[this.size];  // Arreglo para los digitos

    // Convertir cada digito de la cadena a un digito y almacenarlo en el arreglo num.
    for (int i = 0; i < this.size; i++) {
        this.num[i] = Character.getNumericValue(x.charAt(i));
    }
}

public static BigNum add(BigNum x, BigNum y) {
    // Caso 1
    if (x.sign && y.sign) {
        return addConAcarreo(x, y);  // Suma directa.
    }
    // Caso 2
    if (x.sign && !y.sign) {
        int comparacion = compararAbsoluto(x, y);
        if (comparacion  >= 0) {
            // abs(x) >= abs(y) -> x - y.
            return Resta(x, y);
        } else {
            // abs(x) < abs(y) -> -(y - x).
            BigNum result = Resta(y, x);
            result.sign = false;  // Aplicar signo negativo.
            return result;
        }
    }
    // Caso 3
    if (!x.sign && y.sign) {
        int comparacion  = compararAbsoluto(x, y);
        if (comparacion >= 0) {
            // abs(x) >= abs(y) -> -(x - y).
            BigNum result = Resta(x, y);
            result.sign = false;  // Aplicar signo negativo.
            return result;
        } else {
            // abs(x) < abs(y) -> y - x.
            return Resta(y, x);
        }
    }
    // Caso 4
    if (!x.sign && !y.sign) {
        BigNum result = addConAcarreo(x, y);  // Suma directa.
        result.sign = false;  // Aplicar signo negativo.
        return result;
    }
    return new BigNum(0);  // Caso por defecto (no debería alcanzarse).
}

// Método para sumar con acarreo.
private static BigNum addConAcarreo(BigNum x, BigNum y) {
    int TamanoMax = Math.max(x.size, y.size) + 1; // +1 por posible acarreo.
    int[] NumResult = new int[TamanoMax ];
    int carreo = 0;
    int i = x.size - 1, j = y.size - 1, k = TamanoMax  - 1;

    while (i >= 0 || j >= 0 || carreo != 0) {
        int digitoX = (i >= 0) ? x.num[i] : 0;
        int digitoY = (j >= 0) ? y.num[j] : 0;

        int sum = digitoX + digitoY + carreo;
        NumResult[k] = sum % 10;
        carreo = sum / 10;

        i--; j--; k--;
    }

    int TamanoResult = (NumResult[0] == 0) ? TamanoMax  - 1 : TamanoMax ;
    int[] acortadoResult = new int[TamanoResult];
    System.arraycopy(NumResult, TamanoMax - TamanoResult, acortadoResult, 0, TamanoResult);

    return new BigNum(acortadoResult, TamanoResult, true);
}

// Método para restar con préstamo.
private static BigNum Resta(BigNum x, BigNum y) {
    int TamanoMax  = x.size;
    int[] NumResult = new int[TamanoMax];

    int aux = 0;
    int i = x.size - 1, j = y.size - 1, k = TamanoMax - 1;

    while (i >= 0 || j >= 0) {
        int digitoX = (i >= 0) ? x.num[i] : 0;
        int digitoY = (j >= 0) ? y.num[j] : 0;

        int diff = digitoX - digitoY - aux;
        if (diff < 0) {
            diff += 10;
            aux = 1;
        } else {
            aux = 0;
        }

        NumResult[k] = diff;
        i--; j--; k--;
    }

    int resultSize = TamanoMax ;
    while (resultSize > 1 && NumResult[TamanoMax - resultSize] == 0) {
        resultSize--;  // Reducir el tamaño si hay ceros a la izquierda.
    }

    int[] acortadoResult = new int[resultSize];
    System.arraycopy(NumResult, TamanoMax - resultSize, acortadoResult, 0, resultSize);

    return new BigNum(acortadoResult, resultSize, true);  // Mantener signo positivo.
}

// Método para comparar absolutos (sin considerar signo).
private static int compararAbsoluto(BigNum x, BigNum y) {
    if (x.size != y.size) {
        return (x.size > y.size) ? 1 : -1;
    }

    for (int i = 0; i < x.size; i++) {
        if (x.num[i] != y.num[i]) {
            return (x.num[i] > y.num[i]) ? 1 : -1;
        }
    }
   return 0;
}


public static BigNum multiply(BigNum x, BigNum y) {
    // Determinar el tamaño máximo del resultado.
    int TamanoResult = x.size + y.size; // Producto de dos números de tamaño n y m tendrá a lo más n + m dígitos.
    int[] NumResult = new int[TamanoResult];

    // Multiplicación con acarreo.
    for (int i = x.size - 1; i >= 0; i--) {
        int carreo = 0;
        for (int j = y.size - 1; j >= 0; j--) {
            int producto = x.num[i] * y.num[j] + NumResult[i + j + 1] + carreo;
            NumResult[i + j + 1] = producto % 10;  // Guardar el dígito en la posición correcta.
            carreo = producto / 10;  // Calcular acarreo.
        }
        NumResult[i] += carreo;  // Añadir cualquier acarreo restante.
    }

    // Encontrar el tamaño real del resultado, eliminando ceros a la izquierda.
    int aux = 0;
    while (aux < TamanoResult - 1 && NumResult[aux] == 0) {
        aux++;
    }

    int[] acortadoResult = new int[TamanoResult - aux];
    System.arraycopy(NumResult, aux, acortadoResult, 0, acortadoResult.length);

    // Determinar el signo del resultado.
    boolean resultSign = (x.sign == y.sign);  // true si ambos son positivos o negativos, false si son diferentes.

    return new BigNum(acortadoResult, acortadoResult.length, resultSign);
}

public String toString() {
        StringBuilder sb = new StringBuilder();
        if (!this.sign) {
            sb.append('-'); // Añadir el signo negativo si corresponde
        }
        for (int digit : this.num) {
            sb.append(digit);
        }
        return sb.toString();
    }
    public static void main(String[] args) {
        int digitos = (int) Math.pow(10, 6); // 10^9
        Random random = new Random();
    
        StringBuilder num1Builder = new StringBuilder(digitos);
        StringBuilder num2Builder = new StringBuilder(digitos);
    
        // Generar dos números de 10^9 dígitos.
        for (int i = 0; i < digitos; i++) {
            num1Builder.append(random.nextInt(10)); // Dígitos aleatorios entre 0 y 9.
            num2Builder.append(random.nextInt(10));
        }
    
        BigNum num1 = new BigNum(num1Builder.toString(), true);
        BigNum num2 = new BigNum(num2Builder.toString(), true);
    
        // Medir el tiempo de ejecución en nanosegundos.
        long Tinicial = System.nanoTime();
        BigNum adicion = add(num1, num2);
        BigNum product = multiply(num1, num2);
        long Tfinal = System.nanoTime();
        long duracion = Tfinal - Tinicial; // Tiempo en nanosegundos.
    
        System.out.println("Tiempo de ejecución: " + duracion + " nanosegundos.");
    }
    }
