public class AVLTree<T extends Comparable<T>> {
    private class Node {
        T key;
        int height;
        Node left, right;

        Node(T d) {
            key = d;
            height = 1;
        }
    }

    private Node root;

    public void insert(T key) {
        root = insert(root, key);
    }

    private Node insert(Node node, T key) {
        if (node == null) {
            return new Node(key);
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = insert(node.left, key);
        } else if (cmp > 0) {
            node.right = insert(node.right, key);
        } else {
            return node; 
        }
        node.height = 1 + Math.max(height(node.left), height(node.right));
        return balance(node);
    }

    public void delete(T key) {
        root = delete(root, key);
    }

    private Node delete(Node root, T key) {
        if (root == null) {
            return root;
        }
        int cmp = key.compareTo(root.key);
        if (cmp < 0) {
            root.left = delete(root.left, key);
        } else if (cmp > 0) {
            root.right = delete(root.right, key);
        } else {
            if ((root.left == null) || (root.right == null)) {
                Node temp = null;
                if (temp == root.left) {
                    temp = root.right;
                } else {
                    temp = root.left;
                }
                if (temp == null) {
                    root = null;
                } else {
                    root = temp;
                }
            } else {
                Node temp = minValueNode(root.right);
                root.key = temp.key;
                root.right = delete(root.right, temp.key);
            }
        }
        if (root == null) {
            return root;
        }
        root.height = Math.max(height(root.left), height(root.right)) + 1;
        return balance(root);
    }

    private Node minValueNode(Node node) {
        Node current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    public boolean contains(T key) {
        return contains(root, key);
    }

    private boolean contains(Node node, T key) {
        if (node == null) {
            return false;
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            return contains(node.left, key);
        } else if (cmp > 0) {
            return contains(node.right, key);
        } else {
            return true;
        }
    }

    private int height(Node N) {
        if (N == null) {
            return 0;
        }
        return N.height;
    }

    private Node balance(Node y) {
        int balance = getBalance(y);
        if (balance > 1 && getBalance(y.left) >= 0) {
            return rightRotate(y);
        }
        if (balance > 1 && getBalance(y.left) < 0) {
            y.left = leftRotate(y.left);
            return rightRotate(y);
        }
        if (balance < -1 && getBalance(y.right) <= 0) {
            return leftRotate(y);
        }
        if (balance < -1 && getBalance(y.right) > 0) {
            y.right = rightRotate(y.right);
            return leftRotate(y);
        }
        return y;
    }

    private int getBalance(Node N) {
        if (N == null) {
            return 0;
        }
        return height(N.left) - height(N.right);
    }

    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;
        x.right = y;
        y.left = T2;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        return x;
    }

    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;
        y.left = x;
        x.right = T2;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        return y;
    }

    public void update(T oldValue, T newValue) {
        root = delete(root, oldValue);
        insert(newValue);
    }

     // Recorridos en el árbol

    // Preorden: raíz, izquierda, derecha
    public void preorder() {
        preorder(root);
    }

    private void preorder(Node node) {
        if (node != null) {
            System.out.print(node.key + " ");
            preorder(node.left);
            preorder(node.right);
        }
    }

    // Inorden: izquierda, raíz, derecha
    public void inorder() {
        inorder(root);
    }

    private void inorder(Node node) {
        if (node != null) {
            inorder(node.left);
            System.out.print(node.key + " ");
            inorder(node.right);
        }
    }

    // Postorden: izquierda, derecha, raíz
    public void postorder() {
        postorder(root);
    }

    private void postorder(Node node) {
        if (node != null) {
            postorder(node.left);
            postorder(node.right);
            System.out.print(node.key + " ");
        }
    }

}
