package list;

public class LinkedList<T> {
    private class Node {
        T data;
        Node next;

        Node(T data) {
            this.data = data;
        }
    }

    private Node head;
    private int size;

    /**
     * Construye una lista vacía.
     */
    public LinkedList() {
        head = null;
        size = 0;
    }

    /**
     * Añade un elemento al final de la lista.
     * @param data elemento a añadir
     */
    public void add(T data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    /**
     * Obtiene el elemento en la posición indicada.
     * @param index posición del elemento
     * @return elemento en la posición
     * @throws IndexOutOfBoundsException si el índice es inválido
     */
    public T get(int index) {
        rangeCheck(index);
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    /**
     * Elimina la primera ocurrencia del elemento dado.
     * @param data elemento a eliminar
     * @return true si se eliminó, false si no se encontró
     */
    public boolean remove(T data) {
        if (head == null) {
            return false;
        }
        if (head.data.equals(data)) {
            head = head.next;
            size--;
            return true;
        }
        Node current = head;
        while (current.next != null && !current.next.data.equals(data)) {
            current = current.next;
        }
        if (current.next != null) {
            current.next = current.next.next;
            size--;
            return true;
        }
        return false;
    }

    /**
     * Devuelve el número de elementos en la lista.
     * @return tamaño de la lista
     */
    public int size() {
        return size;
    }

    /**
     * Comprueba si la lista contiene el elemento dado.
     * @param data elemento a buscar
     * @return true si existe al menos una ocurrencia
     */
    public boolean contains(T data) {
        Node current = head;
        while (current != null) {
            if (current.data.equals(data)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    /**
     * Limpia la lista, eliminando todos los elementos.
     */
    public void clear() {
        head = null;
        size = 0;
    }

    /**
     * Verifica rango para métodos indexados.
     * @param index posición a verificar
     */
    private void rangeCheck(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }
}
