public class EnteroABinario{

   public static String IntToBin(int x){
      String bin = "";
      while (x > 0){
      bin = (x%2) + bin; // Si es X: impar = 1 ; par = 0. + array de bin
      x /= 2; //Se divide entre dos
      }
      return bin;
   }
   public static void main(String[] args){
    System.out.println(IntToBin(6));

   }
}