package AyudantiaPresolemne;

class TreeNode {
    int value; // Este es el valor del nodo
    TreeNode left, right; // Hijos izquierdo y derecho

    // Constructor del nodo
    TreeNode(int value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }
}

public class BSTSuccessor {
    TreeNode root;

    // Método para insertar un nodo en el BST
    public void insert(int value) {
        root = insertRec(root, value);
    }

    private TreeNode insertRec(TreeNode root, int value) {
        if (root == null) {
            return new TreeNode(value);
        }
        if (value < root.value) {
            root.left = insertRec(root.left, value);
        } else if (value > root.value) {
            root.right = insertRec(root.right, value);
        }
        return root;
    }

    // Método para encontrar el sucesor
    public int findSuccessor(int key) {
        TreeNode current = findNode(root, key);
        if (current == null) {
            return -1; // El nodo no existe en el árbol
        }

        // Caso 1: Si el nodo tiene subárbol derecho
        if (current.right != null) {
            return findMin(current.right).value;
        }

        // Caso 2: Si no tiene subárbol derecho, buscar entre los ancestros
        TreeNode successor = null;
        TreeNode ancestor = root;
        while (ancestor != current) {
            if (current.value < ancestor.value) {
                successor = ancestor; // Posible sucesor
                ancestor = ancestor.left;
            } else {
                ancestor = ancestor.right;
            }
        }
        return (successor != null) ? successor.value : -1;
    }

    // Encuentra el nodo con el valor dado
    private TreeNode findNode(TreeNode root, int key) {
        if (root == null || root.value == key) {
            return root;
        }
        if (key < root.value) {
            return findNode(root.left, key);
        }
        return findNode(root.right, key);
    }

    // Encuentra el nodo con el menor valor en el subárbol
    private TreeNode findMin(TreeNode root) {
        while (root.left != null) {
            root = root.left;
        }
        return root;
    }

    public static void main(String[] args) {
        BSTSuccessor bst = new BSTSuccessor();

        // Crear el árbol
        int[] values = {15, 10, 20, 5, 12, 18, 25, 3, 6, 13, 19};
        for (int value : values) {
            bst.insert(value);
        }

        // Ejemplos
        int[] testKeys = {1, 2, 3, 4, 5, 13, 16, 20, 100};
        for (int key : testKeys) {
            System.out.println("El sucesor de " + key + " es " + bst.findSuccessor(key));
        }
    }
}

