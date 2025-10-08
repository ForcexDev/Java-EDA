package AyudantiaPresolemne;

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    
    TreeNode(int x) {
        val = x;
    }
}

class BinaryTree {
    
    // Función para calcular la altura del árbol
    public int height(TreeNode root) {
        if (root == null) {
            return 0; // La altura de un árbol vacío es 0
        }
        int leftHeight = height(root.left);
        int rightHeight = height(root.right);
        return Math.max(leftHeight, rightHeight) + 1; // La altura es el máximo de los subárboles + 1
    }
}

public class CalcularAltura {
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

        // Calcular y mostrar la altura del árbol
        System.out.println("La altura del árbol es: " + tree.height(root));
    }
}


