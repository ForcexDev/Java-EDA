
import java.util.TreeSet;
import java.util.Set;

public class pqueue {

public static void main(String[] args) {
    Set<String> conjuntoTree = new TreeSet<String>();

    conjuntoTree.add("Sexo");
    conjuntoTree.add("Koko");
    conjuntoTree.add("Momo");
    conjuntoTree.add("Pablo");
    conjuntoTree.add("Juan");
    conjuntoTree.add("Alex");
    conjuntoTree.add("Ciorasis");


    for (String valor : conjuntoTree){
        System.out.println(valor);
    }
   





    // PriorityQueue<Integer> pqueue = new PriorityQueue<Integer>();
    // pqueue.add(5); // output: [5]
    // pqueue.add(4); //output: [4,5]
    // pqueue.add(6); //output: [4,5,6]
    // pqueue.add(2); //output: [2,4,6,5]
    // pqueue.add(1); 
    // pqueue.add(9);
    // pqueue.add(7);
    // pqueue.add(-1); //output: [-1,1,6,2,4,7,9,5]
    // System.out.println(pqueue);

    // pqueue.poll(); // elimina el nodo root.

    // System.out.println(pqueue);

    // pqueue.poll();

    // System.out.println(pqueue);

//    Queue<Integer> NombreQueue = new PriorityQueue<>();
//         NombreQueue.offer(3);
//         NombreQueue.offer(2);
//         NombreQueue.offer(4);
//         NombreQueue.offer(1);
//         NombreQueue.offer(5);
//         NombreQueue.offer(7);

//         System.out.println(NombreQueue);
}
}