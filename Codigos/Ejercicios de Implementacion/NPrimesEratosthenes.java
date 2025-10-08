public class NPrimesEratosthenes {

    static boolean[] eratosthenes(int n){
    boolean[] primes = new boolean[n]; //inicializa un booleano en primes de n
    for (int i = 0; i < n; i++){
        primes[i] = true;
    }
    
    for(int p = 2; p * p <= n; p++){
        if (primes[p] == true){
            for (int i = p * p; i <= n;i += p){
                primes[i] = false;
            }
        }
    }

    return primes;
    }

    public static void main(String[] args){
    System.out.println(eratosthenes(32));
    }
}
