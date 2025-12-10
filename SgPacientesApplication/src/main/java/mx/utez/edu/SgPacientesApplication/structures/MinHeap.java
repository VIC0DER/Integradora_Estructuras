package mx.utez.edu.SgPacientesApplication.structures;

import lombok.Getter;
import mx.utez.edu.SgPacientesApplication.model.Urgencia;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

public class MinHeap {
    @Getter
    private Urgencia[] heap;
    private int size;
    private int capacity;
    public MinHeap(int capacity) {
        this.capacity = capacity;
        this.heap = new Urgencia[capacity];
        this.size = 0;
    }
    private int getParent(int index ) { return (index - 1) / 2; }
    private int getLeftChild(int index ) { return 2 * index + 1; }
    private int getRightChild(int index ) { return 2 * index + 2; }

    /** Redimensiona el árbol cuando alcanza su capacidad máxima. */
    private void ensure() {
        if(size == capacity) {
            capacity *= 2;
            Urgencia[] newHeap = new Urgencia[capacity];
            System.arraycopy(heap, 0, newHeap, 0, size);
            heap = newHeap;
        }
    }
    /** Inserta un objeto Urgencia en el árbol y lo ordena en base a la prioridad. */
    public void insert(Urgencia o){
        ensure();
        heap[size++] = o;
        heapifyUp(size - 1);
    }
    /** Recibe como párametro una ListaSimple de objetos Urgencia.
     *  Almacena sus elementos en el árbol y los ordena en base a la prioridad.
     * @param urgencias
     * */
    public void insert(ListaSimple<Urgencia> urgencias){
        for(Urgencia urgencia : urgencias){
            ensure();
            heap[size++] = urgencia;
            heapifyUp(size - 1);
        }
    }

    /**
     * Compara la prioridad de {@link Urgencia} de {@code heap[index]} con su padre
     * para determinar si se debe volver a ordenar.
     * @param index posición desde donde se quiere empezar a ordenar.
     */
    private void heapifyUp(int index){
        if(index > 0 && heap[index].getPrioridad() <  heap[getParent(index)].getPrioridad()) {
            Urgencia temp = heap[index];
            heap[index] = heap[getParent(index)];
            heap[getParent(index)] = temp;
            heapifyUp(getParent(index));
        }
    }
    /**
     * Elimina del árbol el elemento con mayor prioridad y reordena.
     * */
    public Urgencia remove() {
        if (size == 0) throw new NoSuchElementException("Heap vacío");
        Urgencia root = heap[0];
        heap[0] = heap[--size];
        heap[size] = null;
        heapifyDown(0);
        return root;
    }

    /**
     * <p>Compara la prioridad de {@code heap[index]} con sus dos hijos actuales
     * para determinar si se debe volver a ordenar mediante intercambios de valores.</p>
     * <p>En caso de que la prioridad coincida, se considera el orden de inserción.</p>
     * @param index posición desde donde se quiere empezar a ordenar.
     */
    private void heapifyDown(int index){
        while(true) {
            int left = getLeftChild(index);
            int right = getRightChild(index);
            int smallest = index;

            smallest = getSmallest(left, smallest);
            smallest = getSmallest(right, smallest);

            if(smallest == index) break;
            Urgencia temp = heap[index];
            heap[index] = heap[smallest];
            heap[smallest] = temp;
        }
    }

    /**
     * Compara dos posiciones de {@code Urgencia[] heap} para determinar cuál debería ir primero.
     * @param index posición a ser comparada
     * @param reference posición de referencia para comparar
     * @return {@code index} o {@code reference}, en función de qué posición tiene mayor prioridad, u orden de inserción en caso de misma prioridad.
     */
    private int getSmallest(int index, int reference) {
        if(index < size) {
            if(heap[index].getPrioridad() < heap[reference].getPrioridad()) {
                return index;
            } else if(heap[index].getPrioridad() == heap[reference].getPrioridad()) {
                LocalDateTime leftDateTime = heap[index].getFechaRegistro();
                LocalDateTime referenceDateTime = heap[reference].getFechaRegistro();
                if(leftDateTime.isBefore(referenceDateTime)){
                    return index;
                }
            }
        }
        return reference;
    }
    public void printHeap() {
        for(int i = 0; i < size; i++) {
            System.out.print(heap[i].getPrioridad() + " ");
        }
        System.out.println();
    }
}
