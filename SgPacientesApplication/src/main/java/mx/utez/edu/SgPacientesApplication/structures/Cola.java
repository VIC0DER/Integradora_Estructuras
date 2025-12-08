package mx.utez.edu.SgPacientesApplication.structures;

//Cola circular FIFO con redimensionamiento.


import lombok.Getter;

/**
 * Cola génerica basada en un arreglo redimensionable.
 * @param <T> Cualquier objeto.
 */
public class Cola<T> {
    @Getter
    private Object[] arr;
    @Getter
    private int head;
    @Getter
    private int tail;
    private int size;

    public Cola() {
        arr = new Object[16];
        head = 0; tail = 0; size = 0;
    }
    private void ensure() {
        if (size == arr.length) {
            Object[] n = new Object[arr.length * 2];
            for (int i = 0; i < size; i++) {
                n[i] = arr[(head + i) % arr.length];
            }
            arr = n; head = 0; tail = size;
        }
    }
    public void enqueue(T item) {
        ensure();
        arr[tail] = item;
        tail = (tail + 1) % arr.length;
        size++;
    }
    @SuppressWarnings("unchecked")
    public T dequeue() {
        if (size == 0) return null;
        T v = (T) arr[head];
        arr[head] = null;
        head = (head + 1) % arr.length;
        size--;
        return v;
    }
    @SuppressWarnings("unchecked")
    public T peek() {
        if (size == 0) return null;
        return (T) arr[head];
    }
    public boolean isEmpty() { return size == 0; }
    public int size() { return size; }
}
