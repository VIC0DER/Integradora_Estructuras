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

    private void ensure() {
        if (size >= arr.length) {
            Object[] n = new Object[arr.length * 2];
            System.arraycopy(arr, 0, n, 0, arr.length);
            arr = n;
        }
    }

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

    @SuppressWarnings("unchecked")
    public T pop() {
        if (isEmpty()) return null;
        T v = (T) arr[0];
        System.arraycopy(arr, 1, arr, 0, size - 1);
        arr[--size] = null;
        return v;
    }

    @SuppressWarnings("unchecked")
    public T peek() {
        return (T) arr[0];
    }

    public boolean isEmpty() { return size == 0; }
}
