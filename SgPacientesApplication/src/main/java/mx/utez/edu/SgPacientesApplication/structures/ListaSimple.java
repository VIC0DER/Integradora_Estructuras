package mx.utez.edu.SgPacientesApplication.structures;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;


  //Lista dinámica (arreglo redimensionable).

public class ListaSimple<T> {
    private Object[] data;
    private int size;

    public ListaSimple() {
        data = new Object[10];
        size = 0;
    }

    private void ensure() {
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
    }

    public int size() { return size; }

    public List<T> toList() {
        List<T> out = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            @SuppressWarnings("unchecked")
            T t = (T) data[i];
            out.add(t);
        }
        return out;
    }
}
