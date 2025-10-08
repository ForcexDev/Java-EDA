package AyudantiaPresolemne;

class BubblesortRondas{
    BubblesortRondas(){
    }

    public static final void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    
 
    static void mergeSort ( int [] arr, int lo,int hi ) {
        if (lo >= hi ) return ;
        int mid = (lo + hi ) / 2;
        mergeSort (arr,lo , mid ) ;
        mergeSort (arr,mid + 1 , hi ) ;
        merge (arr,lo, hi ) ;
        for(int l = 0; l < arr.length; l++){
            System.out.println("Array de MergeSort" + arr[l]);
        }
        }
        static void merge (int [] arr, int lo, int hi) {
        int mid = ( lo + hi ) / 2;
        int n1 = mid - lo + 1;
        int n2 = hi - mid ;
        int [] L = new int [ n1 ];
        int [] R = new int [ n2 ];
        for ( int i = 0; i < n1 ; ++ i ) {
        L [ i ] = arr [ lo + i ];
        }
        for ( int j = 0; j < n2 ; ++ j ) {
        R [ j ] = arr [ mid + j + 1];
        }
        int i = 0 , j = 0 , k = 0;
        while ( i < n1 && j < n2 ) {
        if ( L [ i ] <= R [ j ]) {
        arr [ lo + k ] = L [ i ];
        ++ i ;
        ++ k ;
        } else {
        arr [ lo + k ] = R [ j ];
        ++ j ;
        ++ k ;
        }
        }
        while ( i < n1 ) {
        arr [ lo + k ] = L [ i ];
        ++ i ;
        ++ k ;
        }
        while ( j < n2 ) {
        arr [ lo + k ] = R [ j ];
        ++ j ;
        ++ k ;
        }
        
     }
 static void bubbleSort (int[] arr) {
    int contador = 0;
    for (int i = 0; i < arr.length - 1; i++) {
        boolean exchange = false;
        contador++;
    for (int j = 0; j < arr.length - i - 1; j++) {
    if (arr [j] > arr [j + 1]) {
        swap(arr,j,j + 1);
        exchange = true;
        
    }
    }
    if (!exchange) break ;
    }
    System.out.println("Rondas:" + contador);
    for (int i= 0; i < arr.length;i++){
        System.out.println("Array de BubbleSort: " + arr[i]);
    }
 }    
 public static void main(String[] args) {
    BubblesortRondas hola = new BubblesortRondas();
    int array[] = {6,3,1,5,8,2,5,9,8,6,3,24,54,5778,78,747};
    hola.bubbleSort(array);
    hola.mergeSort(array, 0, 1);

    
 }
}