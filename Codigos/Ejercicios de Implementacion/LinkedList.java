class LinkedList {
    Node head; // metodo head tipo nodo.
    public LinkedList(){
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
   //getters y setters
   public int getData(){return data;}
   public Node getNext(){return next;}
   public void setData(int data){this.data = data;}
   public void setNext(Node next){this.next = next;}
   }

    void insert_head(int x){
    if (this.head == null){
        this.head = new Node(x, null);
        return;
    }
    Node aux = new Node (x,this.head);
    this.head = aux;
    }

    void insert_last(int x){
       Node aux = this.head;
       while (aux.next != null){
        aux = aux.next;
       }
       aux.next = new Node(x);
    }

    public void insert_at(int i, int x){
        Node aux = new Node(x);
        if (i == 0) {
            aux.next = head;
            head = aux;
          }else{
            Node current = head;
            for (int j = 0; j < i - 1; j++){
                current = current.next;
            }
            aux.next = current.next;
            current.next = aux;
        }
    }
    
    public int get_at(int i){
        Node current = this.head;
        for (int j = 0; j < i; j++){
            current = current.next;
        }
        return current.data;
    }

    public int delete_at(int i){
        if (i == 0){
            int data = head.data;
            head = head.next;
            return data;
        } else {
            Node current = head;
            for (int j = 0; j < i -1; j++){
                current = current.next;
            }
            int data = current.next.data;
            current.next = current.next.next;
            return data;
        }
    }

    void printList(){
        if (this.head == null){System.out.println("Null");}
        Node aux= this.head;
        while(aux != null){
            System.out.println(aux.data);
            aux = aux.next;
        }
    }

        public static void main(String[] args){
            LinkedList lista = new LinkedList();
            lista.insert_head(20);
            lista.insert_at(1, 30);
            lista.insert_at(2, 292);
            System.out.println(lista.get_at(2));
            lista.insert_head(1);
            lista.printList();
            lista.insert_last(9999);
            System.out.println("Nueva lista: ");
            lista.printList();
            lista.delete_at(0);
            lista.delete_at(0);
            System.out.println("Nueva lista: ");
            lista.printList();
            
        }
    }


    

