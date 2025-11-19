package mx.utez.edu.SgPacientesApplication.structures;

import java.util.AbstractList;
import java.util.Objects;


//Lista dinámica (arreglo redimensionable).

public class ListaSimple<E> extends AbstractList<E> {
    private Nodo<E> first;
    private int size;

    public ListaSimple() {
        first = null;
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

    @Override
    public int size() { return size; }

}
