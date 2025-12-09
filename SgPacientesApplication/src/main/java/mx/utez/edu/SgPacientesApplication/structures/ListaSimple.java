package mx.utez.edu.SgPacientesApplication.structures;

import java.util.AbstractList;
import java.util.List;
import java.util.Objects;


/**
 * Lista génerica basada en un arreglo redimensionable.
 * @param <E> Cualquier objeto.
 */

public class ListaSimple<E> extends AbstractList<E> {
    private Object[] data;
    private int size = 0;

    public ListaSimple() {
        data = new Object[16];
    }
    /** Este metodo se encarga de redimensionar el arreglo que almacena los elementos. */
    private void ensure() {
        if (size >= data.length) {
            Object[] n = new Object[data.length * 2];
            System.arraycopy(data, 0, n, 0, data.length);
            data = n;
        }
    }

    /**
     * Este metodo es para agregar elementos a la lista.
     * @param e element whose presence in this collection is to be ensured
     * @return {@code true}
     */
    @Override
    public boolean add(E e) {
        ensure();
        data[size++] = e;
        return true;
    }
    public void addAll(List<E> elements){
        for (E e : elements) {
            ensure();
            data[size++] = e;
        }
    }

    /**
     * Obtiene el elemento dado su índice.
     * @param index índice del elemento a ser retornado.
     * @return elemento.
     * @throws IndexOutOfBoundsException si el índice es inválido.
     */
    @Override
    @SuppressWarnings("unchecked")
    public E get(int index) {
        if(index < 0 || index >= size){
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return (E) data[index];
    }

    /**
     * Obtiene el índice de un elemento dado.
     * @param o elemento a buscar.
     * @return índice del elemento, {@code -1} si no se encuentra.
     */
    @Override
    public int indexOf(Object o) {
        for(int i = 0; i < size; i++){
            if(Objects.equals(data[i], o)){
                return i;
            }
        }
        return -1;
    }

    /**
     * Reemplaza el valor con una posición y elemento dado.
     * @param index índice del elemento a ser reemplazado.
     * @param element elemento a ser almacenado.
     * @return el anterior valor.
     */
    @Override
    public E set(int index, E element) {
        E old = get(index);
        data[index] = element;
        return old;
    }

    /**
     * Elimina un elemento si esta presente, y desplaza los elementos necesarios para mantener continuidad.
     * @param o elemento a ser eliminado de la lista.
     * @return {@code true} si el elemento se encontró o {@code false} en caso contrario.
     */
    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if(Objects.equals(data[i], o)){
                int movements = size - 1 - i;
                if(movements > 0) System.arraycopy(data, i + 1, data, i, movements);
                data[--size] = null;
                return true;
            }
        }
        return false;
    }

    @Override
    public void clear() {
        System.arraycopy(new Object[data.length], 0, data, 0, data.length);
    }

    @Override
    public int size() { return size; }
    public boolean isEmpty() { return size == 0; }

}
