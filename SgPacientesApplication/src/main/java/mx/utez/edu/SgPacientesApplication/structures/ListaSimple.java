package mx.utez.edu.SgPacientesApplication.structures;

import java.util.AbstractList;
import java.util.Objects;


//Lista dinámica (arreglo redimensionable).

public class ListaSimple<E> extends AbstractList<E> {
    private Nodo<E> first;
    private Object[] data;
    private int size;

    public ListaSimple() {
        first = null;
        data = new Object[10];
        size = 0;
    }
    @Override
    public boolean add(E e) {
        Nodo<E> newNode = new Nodo<>(e);
        if (first == null) {
            first = newNode;
        }else{
            Nodo<E> aux = first;
            while (aux.next != null){
                aux = aux.next;
            }
            aux.next = newNode;
        }
        size++;
        return true;
    }
    @Override
    public E get(int index) {
        Nodo<E> aux = first;
        int i = 0;
        if(index < 0 || index >= size){
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        while (aux != null) {
            if(index == i){
                return aux.value;
            }
            aux = aux.next;
            i++;
        }
        return null;
    }
    @Override
    public boolean remove(Object o) {
        Nodo<E> left = null;
        Nodo<E> aux = first;

        while (aux != null) {
            if(Objects.equals(aux.value, o)){
                if(left != null){
                    left.next = aux.next;
                } else {
                    first = aux.next;
                }
                size--;
                return true;
            }
            left = aux;
            aux = aux.next;
        }
        return false;
    }

    /*private void ensure() {
            if (size >= data.length) {
                Object[] n = new Object[data.length * 2];
                System.arraycopy(data, 0, n, 0, data.length);
                data = n;
            }
        }

        public void add(T item) {
            ensure();
            data[size++] = item;
        }

        public boolean remove(Predicate<T> pred) {
            for (int i = 0; i < size; i++) {
                @SuppressWarnings("unchecked")
                T t = (T) data[i];
                if (pred.test(t)) {
                    int move = size - i - 1;
                    if (move > 0) System.arraycopy(data, i + 1, data, i, move);
                    data[--size] = null;
                    return true;
                }
            }
            return false;
        }

        public Optional<T> find(Predicate<T> pred) {
            for (int i = 0; i < size; i++) {
                @SuppressWarnings("unchecked")
                T t = (T) data[i];
                if (pred.test(t)) return Optional.of(t);
            }
            return Optional.empty();
        }*/
    @Override
    public int size() { return size; }

    /*public List<T> toList() {
        List<T> out = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            @SuppressWarnings("unchecked")
            T t = (T) data[i];
            out.add(t);
        }
        return out;
    }*/
}
