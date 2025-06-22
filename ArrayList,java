package list;


/**
 * ArrayList personalizado genérico para el Sistema de Gestión y Optimización de Inventarios en Almacenes.
 * Implementa almacenamiento dinámico y métodos auxiliares de búsqueda y manipulación sin usar Iterator.
 * Permite recorrer elementos con bucles indexados convencional.
 * @param <T> tipo de elementos almacenados
 */
public class ArrayList<T> {
    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * Construye una lista con capacidad inicial por defecto.
     */
    public ArrayList() {
        elements = new Object[DEFAULT_CAPACITY];
    }

    /**
     * Añade un elemento al final de la lista.
     * @param e elemento a añadir
     */
    public void add(T e) {
        ensureCapacity();
        elements[size++] = e;
    }

    /**
     * Inserta un elemento en la posición indicada.
     * @param index posición donde insertar
     * @param e elemento a insertar
     * @throws IndexOutOfBoundsException si el índice es inválido
     */
    public void add(int index, T e) {
        rangeCheckForAdd(index);
        ensureCapacity();
        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = e;
        size++;
    }

    /**
     * Limpia la lista, eliminando todos los elementos.
     */
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    /**
     * Devuelve el índice de la primera ocurrencia del elemento, o -1 si no existe.
     * @param e elemento a buscar
     * @return índice de la primera ocurrencia, o -1
     */
    public int indexOf(T e) {
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(e)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Devuelve el índice de la última ocurrencia del elemento, o -1 si no existe.
     * @param e elemento a buscar
     * @return índice de la última ocurrencia, o -1
     */
    public int lastIndexOf(T e) {
        for (int i = size - 1; i >= 0; i--) {
            if (elements[i].equals(e)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Obtiene el elemento en la posición indicada.
     * @param index posición del elemento
     * @return elemento en la posición
     * @throws IndexOutOfBoundsException si el índice es inválido
     */
    @SuppressWarnings("unchecked")
    public T get(int index) {
        rangeCheck(index);
        return (T) elements[index];
    }

    /**
     * Reemplaza el elemento en la posición indicada.
     * @param index posición del elemento a reemplazar
     * @param element nuevo elemento
     * @return elemento anterior
     * @throws IndexOutOfBoundsException si el índice es inválido
     */
    @SuppressWarnings("unchecked")
    public T set(int index, T element) {
        rangeCheck(index);
        T oldValue = (T) elements[index];
        elements[index] = element;
        return oldValue;
    }

    /**
     * Elimina el elemento en la posición indicada.
     * @param index posición del elemento a eliminar
     * @return elemento eliminado
     * @throws IndexOutOfBoundsException si el índice es inválido
     */
    @SuppressWarnings("unchecked")
    public T remove(int index) {
        rangeCheck(index);
        T oldValue = (T) elements[index];
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(elements, index + 1, elements, index, numMoved);
        }
        elements[--size] = null; // evita fugas de memoria
        return oldValue;
    }

    /**
     * Elimina la primera ocurrencia del elemento dado.
     * @param e elemento a eliminar
     * @return true si se eliminó, false si no se encontró
     */
    public boolean remove(T e) {
        int idx = indexOf(e);
        if (idx >= 0) {
            remove(idx);
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
     * Verifica si la lista está vacía.
     * @return true si está vacía
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Comprueba si la lista contiene el elemento dado.
     * @param e elemento a buscar
     * @return true si existe al menos una ocurrencia
     */
    public boolean contains(T e) {
        return indexOf(e) >= 0;
    }

    /**
     * Representación en cadena de los elementos.
     * @return cadena con formato [e1, e2, ...]
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(elements[i]);
            if (i < size - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Asegura la capacidad mínima para un nuevo elemento.
     */
    private void ensureCapacity() {
        if (size == elements.length) {
            int newCapacity = elements.length * 2;
            Object[] newElements = new Object[newCapacity];
            System.arraycopy(elements, 0, newElements, 0, size);
            elements = newElements;
        }
    }

    /**
     * Verifica rango para operaciones get/set/remove.
     * @param index posición a verificar
     */
    private void rangeCheck(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    /**
     * Verifica rango para operaciones add en posición.
     * @param index posición a verificar
     */
    private void rangeCheckForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }
}
