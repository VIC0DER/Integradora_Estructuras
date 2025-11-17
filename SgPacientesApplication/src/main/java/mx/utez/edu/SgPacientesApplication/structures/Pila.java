package mx.utez.edu.SgPacientesApplication.structures;

//Pila LIFO (usada para historial temporal en memoria).

public class Pila<T> {
    private Object[] arr;
    private int top;

    public Pila() {
        arr = new Object[10];
        top = 0;
    }

    private void ensure() {
        if (top >= arr.length) {
            Object[] n = new Object[arr.length * 2];
            System.arraycopy(arr, 0, n, 0, arr.length);
            arr = n;
        }
    }

    public void push(T item) {
        ensure();
        arr[top++] = item;
    }

    @SuppressWarnings("unchecked")
    public T pop() {
        if (top == 0) return null;
        T v = (T) arr[--top];
        arr[top] = null;
        return v;
    }

    @SuppressWarnings("unchecked")
    public T peek() {
        if (top == 0) return null;
        return (T) arr[top - 1];
    }

    public boolean isEmpty() { return top == 0; }
}
