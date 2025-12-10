package mx.utez.edu.SgPacientesApplication.structures;

//Pila LIFO (usada para historial temporal en memoria).

import lombok.Getter;

/**
 * Pila génerica basada en un arreglo redimensionable.
 * @param <T> Cualquier objeto.
 */
public class Pila<T> {
    @Getter
    private Object[] arr;
    private int size;

    public Pila() {
        arr = new Object[10];
        size = 0;
    }
    /**
     * Redimensiona la pila cuando alcanza su capacidad máxima.
     */
    private void ensure() {
        if (size >= arr.length) {
            Object[] n = new Object[arr.length * 2];
            System.arraycopy(arr, 0, n, 0, arr.length);
            arr = n;
        }
    }
    /**
     * Este metodo utiliza {@code ensure()} para garantizar que los elementos quepan en la pila.
     * Desplaza los elementos existentes una posición y le asigna el nuevo elemento a {@code arr[0]} y aumenta {@code size}.
     * @param item elemento a ser agregado.
     */
    public void push(T item) {
        ensure();
        if(isEmpty()){
            arr[size++] = item;
            return;
        }
        System.arraycopy(arr, 0, arr, 1, size);
        arr[0] = item;
        size++;
    }
    /**
     * Este metodo itera una {@code ListaSimple<T>}, utiliza {@code ensure()} para garantizar que los elementos quepan en la pila.
     * Desplaza los elementos existentes una posición y le asigna el nuevo elemento a {@code arr[0]} y aumenta {@code size}.
     * @param items {@code ListaSimple<T>} elementos a ser agregados.
     */
    public void push(ListaSimple<T> items){
        for (T item : items) {
            ensure();
            if(isEmpty()){
                arr[size++] = item;
                continue;
            }
            System.arraycopy(arr, 0, arr, 1, size);
            arr[0] = item;
            size++;
        }
    }
    /**
     * Obtiene y elimina la cima de la pila.
     * Acerca el resto de valores a {@code arr[0]},
     * elimina el último valor para evitar duplicados y decrementa {@code size}.
     * @return el elemento al inicio de la cola.
     */
    @SuppressWarnings("unchecked")
    public T pop() {
        if (isEmpty()) return null;
        T v = (T) arr[0];
        System.arraycopy(arr, 1, arr, 0, size - 1);
        arr[--size] = null;
        return v;
    }
    /**
     * Obtiene la cima de la pila, sin eliminar.
     * @return el elemento en la cima de la pila.
     */
    @SuppressWarnings("unchecked")
    public T peek() {
        return (T) arr[0];
    }

    public boolean isEmpty() { return size == 0; }
}
