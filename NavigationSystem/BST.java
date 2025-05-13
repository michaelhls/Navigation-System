package NavigationSystem;

import java.util.LinkedList;
import java.util.Queue;

class BSTNode {
    public String key; // Nama lokasi (misalnya, "Gedung A")
    public BSTNode left, right;

    public BSTNode(String key) {
        this.key = key;
        this.left = this.right = null;
    }

    // Minimum or leftmost value
    public String minValue() {
        return this.left == null ? this.key : this.left.minValue();
    }

    // Inorder traversal
    public void inorder() {
        if (this.left != null) {
            this.left.inorder();
        }

        System.out.print(this.key + " ");
        
        if (this.right != null) {
            this.right.inorder();
        }
    }

    // Breadth-first traversal (BFS)
    public void breadth() {
        final Queue<BSTNode> queue = new LinkedList<>();

        queue.add(this);

        while (!queue.isEmpty()) {
            BSTNode node = queue.poll();

            System.out.print(node.key + " ");

            if (node.left != null) {
                queue.add(node.left);
            }

            if (node.right != null) {
                queue.add(node.right);
            }
        }
    }

    // Insert node
    public void insert(String inputKey) {
        final int comparisonResult = inputKey.compareTo(this.key);

        if (comparisonResult < 0) {
            this.left = new BSTNode(inputKey);
        } else if (comparisonResult > 0) {
            this.right = new BSTNode(inputKey);
        }
    }

    // Search node
    public BSTNode search(String inputKey) {
        final int comparisonResult = inputKey.compareTo(this.key);

        if (comparisonResult < 0) {
            if (this.left != null) {
                return this.left.search(inputKey);
            }
        } else if (comparisonResult > 0) {
            if (this.right != null) {
                return this.right.search(inputKey);
            }
        } else {
            return this;
        }

        return null;
    }

    // Delete node
    public BSTNode delete(String inputKey) {
        final int comparisonResult = inputKey.compareTo(this.key);

        if (comparisonResult < 0) {
            if (this.left != null) {
                this.left = this.left.delete(inputKey);
            }
        } else if (comparisonResult > 0) {
            if (this.right != null) {
                this.right = this.right.delete(inputKey);
            }
        } else {
            if (this.left == null)
                return this.right;
            else if (this.right == null)
                return this.left;
            
            this.key = this.right.minValue();
            this.right = this.right.delete(this.key);
        }
            
        return this;
    }
}

public class BST {
    private BSTNode root;

    public BST() {
        this.root = null;
    }

    // Insert node
    public void insert(String key) {
        if (this.root == null) {
            this.root = new BSTNode(key);
        } else {
            this.root.insert(key);
        }
    }

    // Search node
    public boolean search(String key) {
        return this.root.search(key) != null;
    }

    // Delete node
    public void delete(String key) {
        this.root = this.root.delete(key);
    }

    // Inorder traversal
    public void inorder() {
        this.root.inorder();
        System.out.println();
    }

    // Breadth-first traversal (BFS)
    public void breadth() {
        this.root.breadth();
        System.out.println();
    }
}