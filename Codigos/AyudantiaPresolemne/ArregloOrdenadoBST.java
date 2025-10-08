package AyudantiaPresolemne;
import java.util.*;

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int val) {
        this.val = val;
        this.left = null;
        this.right = null;
    }
}

public class ArregloOrdenadoBST{
    // Función principal para convertir el array ordenado en un árbol binario balanceado
    public static TreeNode sortedArrayToBST(int[] nums) {
        if (nums == null || nums.length == 0) return null;
        return helper(nums, 0, nums.length - 1);
    }

    private static TreeNode helper(int[] nums, int left, int right) {
        if (left > right) return null;

        // Cambiamos la selección del índice medio para seguir el formato del ejemplo
        int mid = (left + right + 1) / 2; // Elige el medio superior en lugar del inferior
        TreeNode root = new TreeNode(nums[mid]);

        // Construir recursivamente los subárboles izquierdo y derecho
        root.left = helper(nums, left, mid - 1);
        root.right = helper(nums, mid + 1, right);

        return root;
    }

    // Función para imprimir el árbol en orden de nivel (formato BFS)
    public static List<Object> printLevelOrder(TreeNode root) {
        List<Object> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode current = queue.poll();
            if (current != null) {
                result.add(current.val);
                queue.add(current.left);
                queue.add(current.right);
            } else {
                result.add(null);
            }
        }

        // Eliminar los `null` sobrantes al final
        while (result.size() > 0 && result.get(result.size() - 1) == null) {
            result.remove(result.size() - 1);
        }

        return result;
    }

    public static void main(String[] args) {
        // Ejemplo
        int[] nums = {-10, -3, 0, 5, 9};

        // Convertir el array ordenado a BST
        TreeNode bst = sortedArrayToBST(nums);

        // Imprimir el árbol en formato de nivel
        List<Object> result = printLevelOrder(bst);
        System.out.println(result);
    }
}
