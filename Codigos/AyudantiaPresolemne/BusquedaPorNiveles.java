package AyudantiaPresolemne;
import java.util.Queue;
import java.util.LinkedList;

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    
    TreeNode(int x) {
        val = x;
    }
}

class BinaryTree {
    // Función para realizar un recorrido por niveles (Level Order Traversal)
    public void levelOrder(TreeNode root) {
        if (root == null) {
            return;
        }

        // Usamos una cola para almacenar los nodos a medida que los visitamos
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root); // Añadimos la raíz a la cola
        
        while (!queue.isEmpty()) {
            TreeNode current = queue.poll();  // Extraemos el nodo actual de la cola
            System.out.print(current.val + " ");  // Imprimimos el valor del nodo

            // Añadimos los hijos a la cola si existen
            if (current.left != null) {
                queue.offer(current.left);
            }
            if (current.right != null) {
                queue.offer(current.right);
            }
        }
    }
}

public class BusquedaPorNiveles {
    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();
        
        // Creación de un árbol de ejemplo
        TreeNode root = new TreeNode(15);
        root.left = new TreeNode(10);
        root.right = new TreeNode(20);
        root.left.left = new TreeNode(5);
        root.left.right = new TreeNode(12);
        root.right.left = new TreeNode(18);
        root.left.left.left = new TreeNode(3);
        root.left.left.right = new TreeNode(6);
        root.left.right.right = new TreeNode(13);
        root.right.left.right = new TreeNode(19);

        // Imprimir el árbol en recorrido por niveles
        tree.levelOrder(root);
    }
}
