package Laboratorio_5;

import java.lang.Comparable;
import java.util.ArrayList;
import java.util.Scanner;


class BST<Key extends Comparable<Key>, Value> {
     Node root;           
    //Clase nodo
    class Node {
        Key key;          
        Value val;         
        Node left, right;  
        int size;          
    //Constructor de nodo
        public Node(Key key, Value val, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
        }

    }
    //Constructor de BST
    public BST() {
    }
    //Metodos de BST.
    
    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }
    
    public int size() {return size(root);}

    private int size(Node x) {
        if (x == null) return 0;
        else return x.size;
    }

    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("calls delete() with a null key");
        root = delete(root, key);
    }
    
    private Node delete(Node x, Key key) {
        if (x == null) return null;
    
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = delete(x.left, key);
        } else if (cmp > 0) {
            x.right = delete(x.right, key);
        } else {
          
            if (x.right == null) return x.left; 
            if (x.left == null) return x.right; 
    
    
            Node successor = findSuccessor(x.right);
            successor.right = delete(x.right, successor.key); 
            successor.left = x.left; 
            x = successor;
        }
    
        x.size = size(x.left) + size(x.right) + 1; 
        return x;
    }
    
    private Node findSuccessor(Node x) {
        while (x.left != null) {
            x = x.left; 
        }
        return x;
    }

    //Metodos de busqueda.
    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("La key no puede ser nula");
        return get(root, key);
    }

    private Value get(Node x, Key key) {
        if (key == null) throw new IllegalArgumentException("La key no puede ser nula");
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else              return x.val;
    }

    public void inOrder(Node root){
        if (root == null){
            return;
        }

        inOrder(root.left);
        System.out.println("Victorias: " + root.key + " ; Nombre: " + root.val);
        inOrder(root.right);

    }
    //Metodo de inserción.
    
    public void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException("La key no puede ser nula");
        root = put(root, key, val);
    }

    private Node put(Node x, Key key, Value val) {
        if (x == null) return new Node(key, val, 1);
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) x.left  = put(x.left,  key, val);
        else if (cmp > 0) x.right = put(x.right, key, val);
        else              x.val   = val;
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    
}
//Map basado en Hash
class LinearProbingHashST<Key, Value> {

    private static final int INIT_CAPACITY = 4;
    private int n;          
    private int m;           
    private Key[] keys;      
    private Value[] vals;    

//Constructores
    public LinearProbingHashST() {
        this(INIT_CAPACITY);
    }

    public LinearProbingHashST(int capacity) {
        m = capacity;
        n = 0;
        keys = (Key[])   new Object[m];
        vals = (Value[]) new Object[m];
    }

    public int size() {return n;}
    public boolean isEmpty() {return size() == 0;}

    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    private int hash(Key key) {
        int h = key.hashCode();
        h ^= (h >>> 20) ^ (h >>> 12) ^ (h >>> 7) ^ (h >>> 4);
        return h & (m-1);
    }
    
    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");
        if (!contains(key)) return;
        int i = hash(key);
        while (!key.equals(keys[i])) {
            i = (i + 1) % m;
        }
        keys[i] = null;
        vals[i] = null;
        i = (i + 1) % m;
        while (keys[i] != null) {
            Key   keyToRehash = keys[i];
            Value valToRehash = vals[i];
            keys[i] = null;
            vals[i] = null;
            n--;
            put(keyToRehash, valToRehash);
            i = (i + 1) % m;
        }
        n--;
        if (n > 0 && n <= m/8) resize(m/2);
        assert check();
    }

    private void resize(int capacity) {
        LinearProbingHashST<Key, Value> temp = new LinearProbingHashST<Key, Value>(capacity);
        for (int i = 0; i < m; i++) {
            if (keys[i] != null) {
                temp.put(keys[i], vals[i]);
            }
        }
        keys = temp.keys;
        vals = temp.vals;
        m    = temp.m;
    }
    
    private boolean check() {
        if (m < 2*n) {
            System.err.println("Hash table size m = " + m + "; array size n = " + n);
            return false;
        }
        for (int i = 0; i < m; i++) {
            if (keys[i] == null) continue;
            else if (get(keys[i]) != vals[i]) {
                System.err.println("get[" + keys[i] + "] = " + get(keys[i]) + "; vals[i] = " + vals[i]);
                return false;
            }
        }
        return true;
    }

    //Metodo de busqueda.
    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        for (int i = hash(key); keys[i] != null; i = (i + 1) % m)
            if (keys[i].equals(key))
                return vals[i];
        return null;
    }

    //Metodo de inserción.
    public void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");
        if (val == null) {
            delete(key);
            return;
        }
        if (n >= m/2) resize(2*m);
        int i;
        for (i = hash(key); keys[i] != null; i = (i + 1) % m) {
            if (keys[i].equals(key)) {
                vals[i] = val;
                return;
            }
        }
        keys[i] = key;
        vals[i] = val;
        n++;
    }

}

class Player{
     String playerName; 
     int wins;
     int draws;
     int losses;
//Constructor de player
Player(String playerName){
     this.playerName = playerName;
     this.wins = 0;
     this.draws = 0;
     this.losses = 0;
}

//Metodos de player
void addWin(){this.wins++;}
void addDraw(){this.draws++;}
void addLoss(){this.losses++;}
double winRate(){
int totaldepartidas = (wins + draws + losses);
     if(totaldepartidas == 0) {
       return 0;
    }

double porcetajeVictorias = (double) wins / totaldepartidas;    
return porcetajeVictorias;
}
//Metodos adicionales
public int getDraws(){return draws;}
public int getLosses(){return losses;}
public int getWins(){return wins;}

}

class Scoreboard{
    BST<Integer,String> winTree; //key: numero de victorias, value: playername.
    LinearProbingHashST<String, Player> players; //registro de jugadores.
    int playedGames; //total de partidas en el sistema.
    //Contructor
    Scoreboard(){
        this.winTree = new BST<Integer, String>();
        this.players = new LinearProbingHashST<String, Player>();
        this.playedGames = 0;
    }

//Metodos de Scoreboard
void addGameResult(String winnerPlayerName, String looserPlayerName, boolean draw) {
    Player ganador = players.get(winnerPlayerName);
    Player perdedor = players.get(looserPlayerName);
    if (draw == true) {
        ganador.addDraw();
        perdedor.addDraw();
    } else {
        int oldWins = ganador.getWins(); 
        ganador.addWin();
        int newWins = ganador.getWins(); 
        
        winTree.delete(oldWins);
        winTree.put(newWins, winnerPlayerName);
        perdedor.addLoss();
    }
    playedGames++;
}

void registerPlayer(String playerName){
Player nuevoPlayer = new Player(playerName);
if (players.contains(playerName)){
    System.out.println("Jugador ya registrado");
    return;
}
players.put(playerName, nuevoPlayer);
}

boolean checkPlayer(String playerName){
if (players.contains(playerName)){
    System.out.println("Jugador registrado");
    return true;
}
System.out.println("Jugador no encontrado");
return false;
}

Player[] winRange(int lo, int hi) {
    ArrayList<Player> playersInRange = new ArrayList<>();
    ArrayList<BST<Integer, String>.Node> stack = new ArrayList<>();
    BST<Integer, String>.Node current = winTree.root;

    while (current != null || !stack.isEmpty()) {
        while (current != null) {
            stack.add(current);
            current = current.left;
        }
        current = stack.remove(stack.size() - 1);
        int key = current.key;
        if (key >= lo && key <= hi) {
            String playerName = current.val;
            Player player = players.get(playerName);
            if (player != null) {
                playersInRange.add(player);
            }
        }
        if (key > hi) break;
        current = current.right;
    }
    return playersInRange.toArray(new Player[0]);
}

Player[] winSuccessor(int wins) {
    BST<Integer, String>.Node current = winTree.root;
    BST<Integer, String>.Node successor = null;

    while (current != null) {
        if (wins < current.key) {
            successor = current;
            current = current.left;
        } else {
            current = current.right;
        }
    }
    if (successor != null) {
        ArrayList<Player> result = new ArrayList<>();
        String playerName = successor.val;
        Player player = players.get(playerName);
        if (player != null) {
            result.add(player);
        }
        return result.toArray(new Player[0]);
    }
    return new Player[0];
}

}

class Game{
String status; //status: IN_PROGRESS, VICTORY, DRAW.
String winnerPlayerName; //String vacio si es empate.
String playerNameA; 
String playerNameB;
TicTacToe ticTacToe;

//Metodos de Game / constructor.

Game(String playerNameA, String playerNameB) {
    this.playerNameA = playerNameA;
    this.playerNameB = playerNameB;
    this.ticTacToe = new TicTacToe();
    this.status = "IN_PROGRESS";
    this.winnerPlayerName = "";
}

void play() {
    Scanner scanner = new Scanner(System.in);
    Scoreboard scoreboard = new Scoreboard();

    System.out.println("Bienvenido a TicTacToe.");

    System.out.print("Ingrese el nombre del Jugador 1 (X): ");
    String player1 = scanner.nextLine();
    scoreboard.registerPlayer(player1);

    System.out.print("Ingrese el nombre del Jugador 2 (O): ");
    String player2 = scanner.nextLine();
    scoreboard.registerPlayer(player2);

    System.out.println("\nIniciando el juego entre " + player1 + " y " + player2 + ".");

    boolean jugarDeNuevo = true;
    while (jugarDeNuevo) {
        Game game = new Game(player1, player2);

        System.out.println("\nEl tablero está vacío y el jugador " + player1 + " comenzará (X).\n");

        while (game.status.equals("IN_PROGRESS")) {
            System.out.println("Estado actual del tablero:");
            game.ticTacToe.printBoard();
            String currentPlayer = game.ticTacToe.currentSymbol == 'X' ? player1 : player2;
            System.out.println("Turno de " + currentPlayer + " (" + game.ticTacToe.currentSymbol + ")");
            int x, y;
            while (true) {
                System.out.print("Ingrese la fila (1-3): ");
                x = scanner.nextInt();
                System.out.print("Ingrese la columna (1-3): ");
                y = scanner.nextInt();

                if (game.ticTacToe.makeMove(x - 1, y - 1)) {
                    break;
                } else {
                    System.out.println("Movimiento inválido. Inténtelo de nuevo.");
                }
            }
            if (game.ticTacToe.isGameOver()) {
                if (game.ticTacToe.checkWinner()) {
                    game.status = "VICTORY";
                    game.winnerPlayerName = currentPlayer;
                } else {
                    game.status = "DRAW";
                }
            }
        }
        System.out.println("\nEstado final del tablero:");
        game.ticTacToe.printBoard();

        if (game.status.equals("VICTORY")) {
            System.out.println("El ganador es: " + game.winnerPlayerName);
            scoreboard.addGameResult(game.winnerPlayerName, game.winnerPlayerName.equals(player1) ? player2 : player1, false);
            System.out.println("\nEstadísticas:");
            System.out.println("\nRango de victorias a buscar: ");
            System.out.print("Ingrese el rango mínimo de victorias: ");
            int minVictorias = scanner.nextInt();
            System.out.print("Ingrese el rango máximo de victorias: ");
            int maxVictorias = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer
            System.out.println("Jugadores con victorias entre " + minVictorias + " y " + maxVictorias + ":");
            Player[] playersInRange = scoreboard.winRange(minVictorias, maxVictorias);
            for (Player player : playersInRange) {
                System.out.println(player.playerName + " - Victorias: " + player.getWins());
            }
            System.out.println("\nBuscar sucesores: ");
            System.out.print("Ingrese la cantidad de victorias para buscar sucesores: ");
            int victorias = scanner.nextInt();
            Player[] successorPlayers = scoreboard.winSuccessor(victorias);
            for (Player player : successorPlayers) {
                System.out.println(player.playerName + " - Victorias: " + player.getWins());
            }
            scanner.nextLine(); // Limpiar buffer
            System.out.println("\nÁrbol de victorias (in-order):");
            scoreboard.winTree.inOrder(scoreboard.winTree.root);
        } else if (game.status.equals("DRAW")) {
            System.out.println("Es un empate.");
            scoreboard.addGameResult(player1, player2, true);
        }
        System.out.print("\n¿Quieres jugar de nuevo? (s/n): ");
        jugarDeNuevo = scanner.next().equalsIgnoreCase("s");
        scanner.nextLine(); // Limpiar buffer
    }
    System.out.println("\nGame over.");
    scanner.close();
}
}

public class TicTacToe {
char[][] grid;
char currentSymbol;

TicTacToe() {
    this.grid = new char[3][3];
    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
            grid[i][j] = ' '; 
        }
    }
    this.currentSymbol = 'X'; 
}

//Metodos de TicTacToe
boolean makeMove(int x, int y) {
    if (x < 0 || x >= 3 || y < 0 || y >= 3 || grid[x][y] != ' ') {
        return false; 
    }
    grid[x][y] = currentSymbol; 
    currentSymbol = (currentSymbol == 'X') ? 'O' : 'X'; 
    return true;
}

boolean isGameOver() {
    for (int i = 0; i < 3; i++) {
        if (grid[i][0] != ' ' && grid[i][0] == grid[i][1] && grid[i][1] == grid[i][2]) return true;
        if (grid[0][i] != ' ' && grid[0][i] == grid[1][i] && grid[1][i] == grid[2][i]) return true;
    }
    if (grid[0][0] != ' ' && grid[0][0] == grid[1][1] && grid[1][1] == grid[2][2]) return true;
    if (grid[0][2] != ' ' && grid[0][2] == grid[1][1] && grid[1][1] == grid[2][0]) return true;

    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
            if (grid[i][j] == ' ') return false;
        }
    }
    return true; 
}

boolean checkWinner() {
   
    for (int i = 0; i < 3; i++) {
        if (grid[i][0] != ' ' && grid[i][0] == grid[i][1] && grid[i][1] == grid[i][2]) return true;
        if (grid[0][i] != ' ' && grid[0][i] == grid[1][i] && grid[1][i] == grid[2][i]) return true;
    }
    if (grid[0][0] != ' ' && grid[0][0] == grid[1][1] && grid[1][1] == grid[2][2]) return true;
    if (grid[0][2] != ' ' && grid[0][2] == grid[1][1] && grid[1][1] == grid[2][0]) return true;


    return false;
}

void printBoard() {
    System.out.println();
    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
            System.out.print(grid[i][j] == ' ' ? "-" : grid[i][j]);
            if (j < 2) System.out.print(" | ");
        }
        System.out.println();
        if (i < 2) System.out.println("---------");
    }
    System.out.println();
}

public static void main(String[] args) {
Game jugar = new Game("Jugador1", "Jugador2");
jugar.play();
}

}


