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

    /**
     * Redimensiona la cola cuando alcanza su capacidad máxima.
     */
    private void ensure() {
        if (size == arr.length) {
            Object[] n = new Object[arr.length * 2];
            for (int i = 0; i < size; i++) {
                n[i] = arr[(head + i) % arr.length];
            }
            arr = n; head = 0; tail = size; //Importante esta asignación para que tail recupere continuidad
        }
    }

    /**
     * Este metodo utiliza {@code ensure()} para garantizar
     * que los elementos quepan en la cola. Mueve {@code tail} a una posición siguiente.
     *
     * @param item elemento a ser agregado.
     */
    public void enqueue(T item) {
        ensure();
        arr[tail] = item;
        tail = (tail + 1) % arr.length;
        size++;
    }

    /**
     * Obtiene y elimina el inicio de la cola. Mueve {@code head} una posición y decrementa {@code size}.
     * @return el elemento al inicio de la cola.
     */
    @SuppressWarnings("unchecked")
    public T dequeue() {
        if (size == 0) return null;
        T v = (T) arr[head];
        arr[head] = null;
        head = (head + 1) % arr.length;
        size--;
        return v;
    }

    /**
     * Obtiene el inicio de la cola, sin eliminar.
     * @return el elemento al inicio de la cola.
     */
    @SuppressWarnings("unchecked")
    public T peek() {
        if (size == 0) return null;
        return (T) arr[head];
    }
    public boolean isEmpty() { return size == 0; }
    public int size() { return size; }
}
