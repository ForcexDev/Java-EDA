
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

    public static void main(String[]args){
    BigNum num1= new BigNum(-3);

    System.out.println("Signo: " + (num1.sign));
        
}
}