package Laboratorio_2;
//CLASE MESSAGE
class Message{
private int id; // todo mensaje tiene id único.
private String content; //contenido del mensaje
private boolean reported; //true = reportado, false = no reportado.
public String channelName; // Canal donde se envió el mensaje.
public Message next; //next para lista enlazada tipo Message.

public Message(String content, String channelName, int id){
    this.content = content;
    this.channelName = channelName;
    this.id = id;
    this.reported = false;
    this.next = null;
}

// Constructor de copiado.
public Message(Message m) {
    this.id = m.id;
    this.content = m.content;
    this.channelName = m.channelName;
    this.reported = m.reported;
    this.next = null;
}

//METODOS MESSAGE
public void report(){this.reported = true;}

public boolean IsReported(){return this.reported;}

public String getContent(){return this.content;}

public void setNext(Message m){this.next = m;}

public int getId(){return this.id;}

}
//CLASE CHANNEL
class Channel{
    public String name; //Nombre del canal.
    private Message headMessage; //head de la lista enlazada.
    public Channel next; // next de lista enlazada tipo channel.
    private Message tailMessage; // tail = ultimo mensaje.


    Channel(String name){
        this.name = name;
        this.headMessage = null;
        this.next = null;
        this.tailMessage = null;
    }
    //METODOS DE CHANNEL

    public void appendMessage(Message m){
        Message newMessage = new Message(m);
        if (headMessage == null){
            headMessage = newMessage;
            tailMessage = newMessage;
        } else {
            tailMessage.next = newMessage;
            tailMessage = newMessage;
        }
    }

    public Message search(int id){
        Message aux = this.headMessage;
        while (aux != null){
        if (aux.getId() == id){
            return aux;
        }
        aux = aux.next;
        }
        return null;
    }

    public boolean empty(){return this.headMessage == null;}

    public void setNext(Channel c){this.next = c;}

    public Message getHead(){return this.headMessage;}

    public Message getTail(){return this.tailMessage;}
}
//CLASE HISTORY
class History {
    private Message topMessage;
    History (){
        this.topMessage = null;
    }

    //METODOS DE HISTORY

    public void push(Message m){
    if (topMessage != null){
        m.next = topMessage;
    }
    topMessage = m;
    }
    
    public Message top(){return topMessage;}
    
    public boolean empty(){return this.topMessage == null;}

    public Message[] lastKMessages(int k) {
        if (topMessage == null) { // retorna el array vacio.
            return new Message[0];
        }
        Message aux = this.topMessage; // crea un auxiliar de topMessage.
        int counter = 0;

        while (aux != null) {
            counter++;
            aux = aux.next;
        }
        if (k > counter) { // Ajusta k segun el tamaño del stack.
            k = counter;
        }
        Message[] arrhist = new Message[k];
        aux = this.topMessage;
        for (int i = 0; i < k; i++) { // Agrega los ultimos mensajes al array.
            arrhist[i] = aux; 
            aux = aux.next; 
        }
        return arrhist;
    }

}

class ReportList{
private Message firstMessage;
private Message lastMessage;

    ReportList(){ 
    this.firstMessage = null;
    this.lastMessage = null;
}

//METODOS DE REPORT LIST
    public void enqueue(Message m){
        if (m.IsReported() == false){
            return;
        }
        Message newMessage = new Message(m);
        if (firstMessage == null){
            firstMessage = lastMessage = newMessage;
        } else {
            lastMessage.next = newMessage;
            lastMessage = newMessage;
        }
    }

    public Message[] firstKReportedMessages(int k){
        if (firstMessage == null) {
            return new Message[0]; 
        }
        Message aux = firstMessage;
        int counter = 0;
    
        while (aux != null && counter < k) {
            if (aux.IsReported()) {
                counter++;
            }
            aux = aux.next;
        }
        if (k > counter) {
            k = counter;
        }

        Message[] reportedMessages = new Message[k];
        aux = firstMessage;
        int index = 0;
    
        while (aux != null && index < k) {
            if (aux.IsReported()) {
                reportedMessages[index] = aux;
                index++;
            }
            aux = aux.next;
        }
        return reportedMessages; 
}

public Message first(){return this.firstMessage;}

public boolean empty(){return this.firstMessage == null;}
}

    //CLASE SLOK
    public class Slok {
        private Channel headChannel; 
        private History history;
        private ReportList reportList;
        private int idCounter;
    
        // Constructor de Slok
        public Slok() {
            this.headChannel = null;
            this.history = new History();
            this.reportList = new ReportList();
            this.idCounter = 1;
        }
    //METODOS DE SLOK
    public void reportMessage(int id, String channelName) {
        Channel channel = headChannel;
        while (channel != null) {
        if (channel.name == channelName) {
        Message message = channel.search(id);
            if (message != null) {
                message.report();
                reportList.enqueue(message);
                return;
            }
        }
                channel = channel.next;
        }
        System.out.println("Mensaje no encontrado.");
    }
    
    public void createMessage(String content, String channelName) {
        Channel channel = headChannel;
         while (channel != null) {
            if (channel.name.equals(channelName)) {
                Message newMessage = new Message(content, channelName, idCounter);
                channel.appendMessage(newMessage);
                history.push(newMessage);
                incrementIDCounter();
                return;
            }
            channel = channel.next;
        }
        System.out.println("Canal no encontrado.");
    }
    
        public void createChannel(String name) {
            Channel newChannel = new Channel(name);
            if (headChannel == null) {
                headChannel = newChannel;
            } else {
                Channel aux = headChannel;
                while (aux.next != null) {
                    aux = aux.next;
                }
                aux.next = newChannel;
            }
        }
    
        public void incrementIDCounter() { this.idCounter++; }
        public int getIDCounter() { return this.idCounter; }
        // CALCULAR USO DE MEMORIA
                public static void main(String[] args) {

            Slok slok = new Slok();
            slok.createChannel("General");
            slok.createChannel("General_2");
            slok.createMessage("Hola que tal, todo bien?", "General");
            slok.createMessage("Todo bien y tu crack?", "General");
            slok.createMessage("Muy bien", "General");
            slok.createMessage("HOLA", "General");
            slok.createMessage("alo", "General_2");
            slok.createMessage("si", "General_2");
            slok.createMessage("no", "General_2");
            slok.createMessage("HOLA", "General_2");
            slok.reportMessage(4, "General");
            slok.reportMessage(8, "General_2");
            slok.reportMessage(5, "General_2");
    
            Message[] firstReportedMessages = slok.reportList.firstKReportedMessages(5);
            System.out.println("Primeros mensajes reportados:");
            for (Message message : firstReportedMessages) {
                System.out.println("Mensaje: " + message.getContent() + " ID: " + message.getId());
            }
    
            int k = 6;
            Message[] lastMessages = slok.history.lastKMessages(k);
            System.out.println("Últimos " + k + " mensajes:");
            for (Message message : lastMessages) {
                System.out.println(message.getContent());
            }
           
        }
    }

