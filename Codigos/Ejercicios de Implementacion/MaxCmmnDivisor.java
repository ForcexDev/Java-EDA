public class MaxCmmnDivisor {
    //METODOS
    public static int gcd(int a, int b){
    
    int res = a%b;    
    while(res!= 0){
        a = b;
        b = res;
        res = a%b;
    }
    return b;
    }
 
    //MAIN
    public static void main(String[] args){
    
        System.out.println(gcd(2046, 360));
        System.out.println(gcd(3213,2));
        System.out.println(gcd(2046, 1024));
        System.out.println(gcd(97, 47));
    }
}
