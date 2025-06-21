public class BPlusTree<T extends Comparable<T>> {
    private static final int T = 3; // Orden del B+ Tree

    private class Node {
        int n;
        boolean leaf = true;
        ArrayList<T> key = new ArrayList<>();
        ArrayList<Node> child = new ArrayList<>();

        int find(T k) {
            for (int i = 0; i < n; i++) {
                if (key.get(i).compareTo(k) == 0) {
                    return i;
                }
            }
            return -1;
        }
    }

    private Node root = new Node();

    public void update(T oldValue, T newValue) {
        delete(oldValue);
        insert(newValue);
    }

    public void insert(T key) {
        Node r = root;
        if (r.n == 2 * T - 1) {
            Node s = new Node();
            root = s;
            s.leaf = false;
            s.n = 0;
            s.child.add(r);
            split(s, 0, r);
            insertNonFull(s, key);
        } else {
            insertNonFull(r, key);
        }
    }

    private void split(Node x, int pos, Node y) {
        Node z = new Node();
        z.leaf = y.leaf;
        z.n = T - 1;
        for (int j = 0; j < T - 1; j++) {
            z.key.add(y.key.get(j + T));
        }
        if (!y.leaf) {
            for (int j = 0; j < T; j++) {
                z.child.add(y.child.get(j + T));
            }
        }
        y.n = T - 1;
        x.child.add(pos + 1, z);
        x.key.add(pos, y.key.get(T - 1));
        x.n++;
    }

    private void insertNonFull(Node x, T k) {
        int i = x.n - 1;
        if (x.leaf) {
            x.key.add(null);
            while (i >= 0 && k.compareTo(x.key.get(i)) < 0) {
                x.key.set(i + 1, x.key.get(i));
                i--;
            }
            x.key.set(i + 1, k);
            x.n++;
        } else {
            while (i >= 0 && k.compareTo(x.key.get(i)) < 0) {
                i--;
            }
            i++;
            if (x.child.get(i).n == 2 * T - 1) {
                split(x, i, x.child.get(i));
                if (k.compareTo(x.key.get(i)) > 0) {
                    i++;
                }
            }
            insertNonFull(x.child.get(i), k);
        }
    }

    public void delete(T key) {
        delete(root, key);
    }

    @SuppressWarnings("unchecked")
    private void delete(Node x, T key) {
        int pos = x.find(key);
        if (pos != -1) {
            if (x.leaf) {
                x.key.remove(pos);
                x.n--;
            } else {
                Node pred = x.child.get(pos);
                if (pred.n >= T) {
                    T predKey = getPred(x, pos);
                    delete(pred, predKey);
                    x.key.set(pos, predKey);
                } else {
                    Node next = x.child.get(pos + 1);
                    if (next.n >= T) {
                        T nextKey = getSucc(x, pos);
                        delete(next, nextKey);
                        x.key.set(pos, nextKey);
                    } else {
                        merge(x, pos);
                        delete(pred, key);
                    }
                }
            }
        } else {
            for (pos = 0; pos < x.n; pos++) {
                if (x.key.get(pos).compareTo(key) > 0) {
                    break;
                }
            }
            Node tmp = x.child.get(pos);
            if (tmp.n == T - 1) {
                Node nb = null;
                int devider = -1;

                if (pos != x.n && x.child.get(pos + 1).n >= T) {
                    devider = (int) x.key.get(pos);
                    nb = x.child.get(pos + 1);
                    x.key.set(pos, nb.key.get(0));
                    tmp.key.add((T) Integer.valueOf(devider));
                    tmp.child.add(tmp.n + 1, nb.child.get(0));
                    nb.key.remove(0);
                    nb.child.remove(0);
                    nb.n--;
                    delete(tmp, key);
                } else if (pos != 0 && x.child.get(pos - 1).n >= T) {
                    devider = (int) x.key.get(pos - 1);
                    nb = x.child.get(pos - 1);
                    x.key.set(pos - 1, nb.key.get(nb.n - 1));
                    Node child = nb.child.get(nb.n);
                    nb.n--;

                    tmp.key.add(0, (T) Integer.valueOf(devider));
                    tmp.child.add(0, child);
                    tmp.n++;
                    delete(tmp, key);
                } else {
                    if (pos != x.n) {
                        merge(x, pos);
                    } else {
                        merge(x, pos - 1);
                    }
                    delete(x.child.get(pos), key);
                }
            } else {
                delete(tmp, key);
            }
        }
    }

    private void merge(Node x, int pos) {
        Node left = x.child.get(pos);
        Node right = x.child.get(pos + 1);
        left.key.add((T) x.key.get(pos));
        left.key.addAll(right.key);
        left.child.addAll(right.child);
        x.key.remove(pos);
        x.child.remove(pos + 1);
        left.n += right.n + 1;
        x.n--;
    }

    private T getPred(Node x, int pos) {
        Node cur = x.child.get(pos);
        while (!cur.leaf) {
            cur = cur.child.get(cur.n);
        }
        return cur.key.get(cur.n - 1);
    }

    private T getSucc(Node x, int pos) {
        Node cur = x.child.get(pos + 1);
        while (!cur.leaf) {
            cur = cur.child.get(0);
        }
        return cur.key.get(0);
    }

    public boolean contains(T key) {
        return contains(root, key);
    }

    private boolean contains(Node x, T key) {
        int pos = 0;
        while (pos < x.n && key.compareTo(x.key.get(pos)) > 0) {
            pos++;
        }
        if (pos < x.n && key.compareTo(x.key.get(pos)) == 0) {
            return true;
        }
        if (x.leaf) {
            return false;
        } else {
            return contains(x.child.get(pos), key);
        }
    }

    public void display() {
        display(root, "");
    }

    private void display(Node x, String prefix) {
        if (x != null) {
            System.out.println(prefix + "└── " + x.key);
            for (int i = 0; i < x.child.size(); i++) {
                String childPrefix = prefix + (i == x.child.size() - 1 ? "    " : "│   ");
                display(x.child.get(i), childPrefix);
            }
        }
    }
}
