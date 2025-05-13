package NavigationSystem;

class BSTNode {
    String key; // Nama lokasi (misalnya, "Gedung A")
    BSTNode left, right;

    public BSTNode(String key) {
        this.key = key;
        left = right = null;
    }
}

public class BST {
    private BSTNode root;

    public BST() {
        root = null;
    }

    // Insert node
    public void insert(String key) {
        root = insertRec(root, key);
    }

    private BSTNode insertRec(BSTNode root, String key) {
        if (root == null) {
            root = new BSTNode(key);
            return root;
        }
        if (key.compareTo(root.key) < 0)
            root.left = insertRec(root.left, key);
        else if (key.compareTo(root.key) > 0)
            root.right = insertRec(root.right, key);
        return root;
    }

    // Search node
    public boolean search(String key) {
        return searchRec(root, key) != null;
    }

    private BSTNode searchRec(BSTNode root, String key) {
        if (root == null || root.key.equals(key))
            return root;
        if (key.compareTo(root.key) < 0)
            return searchRec(root.left, key);
        return searchRec(root.right, key);
    }

    // Delete node
    public void delete(String key) {
        root = deleteRec(root, key);
    }

    private BSTNode deleteRec(BSTNode root, String key) {
        if (root == null)
            return root;
        if (key.compareTo(root.key) < 0)
            root.left = deleteRec(root.left, key);
        else if (key.compareTo(root.key) > 0)
            root.right = deleteRec(root.right, key);
        else {
            if (root.left == null)
                return root.right;
            else if (root.right == null)
                return root.left;
            root.key = minValue(root.right);
            root.right = deleteRec(root.right, root.key);
        }
        return root;
    }

    private String minValue(BSTNode root) {
        String minv = root.key;
        while (root.left != null) {
            minv = root.left.key;
            root = root.left;
        }
        return minv;
    }

    // Inorder traversal
    public void inorder() {
        inorderRec(root);
        System.out.println();
    }

    private void inorderRec(BSTNode root) {
        if (root != null) {
            inorderRec(root.left);
            System.out.print(root.key + " ");
            inorderRec(root.right);
        }
    }
}