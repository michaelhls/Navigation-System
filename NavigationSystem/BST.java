package NavigationSystem;

class BSTNode {
    String key; // Nama lokasi (misalnya, "Gedung A")
    BSTNode left, right;

    public BSTNode(String key) {
        this.key = key;
        this.left = this.right = null;
    }

    public String minValue() {
        return this.left == null ? this.key : this.left.minValue();
    }

    // Inorder traversal
    public void inorderRec() {
        if (this.left != null) {
            this.left.inorderRec();
        }

        System.out.print(this.key + " ");
        
        if (this.right != null) {
            this.right.inorderRec();
        }
    }

    public void insertRec(String inputKey) {
        final int comparisonResult = inputKey.compareTo(this.key);

        if (comparisonResult < 0) {
            this.left = new BSTNode(inputKey);
        } else if (comparisonResult > 0) {
            this.right = new BSTNode(inputKey);
        }
    }

    public BSTNode searchRec(String inputKey) {
        final int comparisonResult = inputKey.compareTo(this.key);

        if (comparisonResult < 0) {
            if (this.left != null) {
                return this.left.searchRec(inputKey);
            }
        } else if (comparisonResult > 0) {
            if (this.right != null) {
                return this.right.searchRec(inputKey);
            }
        } else {
            return this;
        }

        return null;
    }

    public BSTNode deleteRec(String inputKey) {
        final int comparisonResult = inputKey.compareTo(this.key);

        if (comparisonResult < 0) {
            if (this.left != null) {
                this.left = this.left.deleteRec(inputKey);
            }
        } else if (comparisonResult > 0) {
            if (this.right != null) {
                this.right = this.right.deleteRec(inputKey);
            }
        } else {
            if (this.left == null)
                return this.right;
            else if (this.right == null)
                return this.left;
            
            this.key = this.right.minValue();
            this.right = this.right.deleteRec(this.key);
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
            this.root.insertRec(key);
        }
    }

    // Search node
    public boolean search(String key) {
        return this.root.searchRec(key) != null;
    }

    // Delete node
    public void delete(String key) {
        this.root = this.root.deleteRec(key);
    }

    // Inorder traversal
    public void inorder() {
        this.root.inorderRec();
        System.out.println();
    }
}