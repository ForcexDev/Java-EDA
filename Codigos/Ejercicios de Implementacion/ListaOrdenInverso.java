public class ListaOrdenInverso {
    Node head; // metodo head tipo nodo.
    public ListaOrdenInverso(){
        this.head = null;
    }
   public class Node {
   int data; 
   Node next; //metodo next tipo nodo

   //Constructor del nodo
   public Node(int data){
    this.data= data;
   }
   public Node(int data, Node next){
    this.data = data;
    this.next= next;
   }

   int getData(){
    return this.data;
}
}

void insert_head(int x){
    if (this.head == null){
        this.head = new Node (x, null);
        return;
    }
    Node aux = new Node (x, this.head);
    this.head = aux;
}

void insert_last (int x){
    Node aux = this.head;
    while (aux.next != null){
        aux = aux.next;
    }
    aux.next = new Node (x);
}

void invertirLista() {
    Node current = this.head;
    Node previous = null;
    Node nextNode;

    while (current != null) {
        nextNode = current.next; // Guarda el siguiente nodo
        current.next = previous; // Invierte el enlace
        previous = current; // Mueve previous hacia adelante
        current = nextNode; // Avanza al siguiente nodo
    }
    this.head = previous; // Actualiza la cabeza a la nueva cabeza
}


public static void main(String[] args){
    ListaOrdenInverso lista1 = new ListaOrdenInverso();
    lista1.insert_head(7); 
    lista1.insert_last(5);
    lista1.insert_last(3);
    lista1.insert_last(1);
    System.out.println(lista1.head.getData());
    System.out.println(lista1.head.next.getData());
    System.out.println(lista1.head.next.next.getData());
    System.out.println(lista1.head.next.next.next.getData() + "si");
    lista1.invertirLista();
    System.out.println(lista1.head.getData());
    System.out.println(lista1.head.next.getData());
    System.out.println(lista1.head.next.next.getData());
    System.out.println(lista1.head.next.next.next.getData() + "si");
}
}