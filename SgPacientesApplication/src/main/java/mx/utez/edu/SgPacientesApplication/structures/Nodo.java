package mx.utez.edu.SgPacientesApplication.structures;

public class Nodo<E> {
    protected E value;
    protected Nodo<E> next;

    public Nodo(E value){
        this.value = value;
    }
    public Nodo(E value, Nodo<E> next){
        this.value = value;
        this.next = next;
    }
}
