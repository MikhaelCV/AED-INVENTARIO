import java.util.Iterator;

/**
 * Interfaz genérica para un Tipo Abstracto de Datos (TAD).
 * Define las operaciones mínimas que deben implementar
 * las estructuras de datos concretas.
 *
 * @param <E> el tipo de elementos que almacena el TAD
 */
public interface TAD<E> extends Iterable<E> {

    /**
     * Inserta un elemento en la estructura.
     *
     * @param element elemento a insertar
     */
    void insert(E element);

    /**
     * Elimina un elemento de la estructura.
     *
     * @param element elemento a eliminar
     * @return true si el elemento existía y fue eliminado; false si no se encontró
     */
    boolean delete(E element);

    /**
     * Busca un elemento en la estructura.
     *
     * @param element elemento “clave” a buscar
     * @return el elemento encontrado, o null si no existe
     */
    E search(E element);

    /**
     * Comprueba si un elemento está presente.
     *
     * @param element elemento a consultar
     * @return true si existe en la estructura; false en caso contrario
     */
    boolean contains(E element);

    /**
     * Devuelve el número de elementos almacenados.
     *
     * @return tamaño actual de la estructura
     */
    int size();

    /**
     * Indica si la estructura está vacía.
     *
     * @return true si no contiene ningún elemento
     */
    boolean isEmpty();

    /**
     * Elimina todos los elementos, dejando la estructura vacía.
     */
    void clear();

    /**
     * Proporciona un iterador para recorrer los elementos
     * (p. e., para usar en un for-each).
     *
     * @return un Iterator sobre E
     */
    @Override
    Iterator<E> iterator();
}
