package mx.utez.edu.SgPacientesApplication.structures;

import java.util.*;

/**
 * Esta clase es utilizada para mandar información en las respuestas HTTP.
 * @param <K> Tipo de objeto utilizado como clave para acceder al valor.
 * @param <V> Tipo de objeto para almacenar valores.
 */
public class MyHashMap<K, V> extends AbstractMap<K, V> {

    // Factor de carga (cuando redimensionar)
    private static final float LOAD_FACTOR = 0.75f;
    private static final int INITIAL_CAPACITY = 16;

    /**
     * Esta clase interna es equivalente a la clase {@code Node} en listas enlazadas.
     * @param <K> Clave del mismo tipo a la de la clase externa.
     * @param <V> Valor del mismo tipo a la de la clase externa.
     */
    private static class Entry<K, V> implements Map.Entry<K, V>{
        K key;
        V value;
        Entry<K, V> next;

        Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public K getKey() { return key; }

        @Override
        public V getValue() { return value; }

        @Override
        public V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }
    }

    private Entry<K, V>[] table;
    private int size = 0;

    @SuppressWarnings("unchecked")
    public MyHashMap() {
        table = new Entry[INITIAL_CAPACITY];
    }

    /**
     * Obtiene el índice dada una clave.
     * @param key objeto utilizado como clave para {@link Entry}.
     * @return índice calculado con el hashCode de la clave.
     */
    private int index(Object key) {
        return (key == null) ? 0 : Math.abs(key.hashCode()) % table.length;
    }

    /**
     * Inserta un valor especificado dada una clave.
     * @param key clave con la que el valor especificado es asociado.
     * @param value valor con el que la clave es asociada.
     * @return {@code null} si la clave es nueva o el viejo valor si ya existe.
     */
    @Override
    public V put(K key, V value) {
        int index = index(key);
        Entry<K, V> head = table[index]; // Incialmente head es null

        // Verificar si ya existe la clave
        for (Entry<K, V> curr = head; curr != null; curr = curr.next) {
            if (Objects.equals(curr.key, key)) {
                return curr.setValue(value); //Actualiza y retorna el viejo valor
            }
        }

        // Insertar nuevo nodo al inicio
        // Se desplaza head (parecido a las pilas)
        table[index] = new Entry<>(key, value, head);;
        size++;

        // Verificar si hay que redimensionar
        if ((1.0 * size) / table.length >= LOAD_FACTOR) {
            resize();
        }
        return null;
    }

    // Obtiene un valor por su clave
    @Override
    public V get(Object key) {
        // Va a encontrar un nodo, puede o no contener la key
        int index = index(key);
        Entry<K, V> head = table[index];
        // Recorre todas las entry apiladas en dicho nodo
        for (Entry<K, V> curr = head; curr != null; curr = curr.next) {
            if (Objects.equals(curr.key, key)) {
                return curr.value;
            }
        }
        return null; // Si llega aca es porque no encontró coincidencias de key
    }

    // Elimina una clave
    @Override
    public V remove(Object key) {
        int index = index(key);
        // Obtiene la primer entry correspondiente al índice
        Entry<K, V> head = table[index];
        // Como es la primer entry, no existe entry previa
        Entry<K, V> prev = null;
        // Recorre cada una de las entry en la posición del índice
        for (Entry<K, V> curr = head; curr != null; curr = curr.next) {
            if (Objects.equals(curr.key, key)) {
                if (prev != null) {
                    prev.next = curr.next;
                } else {
                    table[index] = curr.next;
                }
                size--;
                return curr.value;
            }
            prev = curr;
        }
        return null;
    }

    // Redimensiona el arreglo cuando se llena
    // Es útil para mantener la eficiencia de los métodos get y put, a medida que crece el map
    @SuppressWarnings("unchecked")
    private void resize() {
        // Guardamos el viejo array
        Entry<K, V>[] oldTable = table;
        // Le asignamos un array de 2 veces la longitud del anterior
        table = new Entry[oldTable.length * 2];
        size = 0; // Reiniciamos size
        // Obtiene todos los nodos del viejo array
        for (Entry<K, V> head : oldTable) {
            // Recorre cada una de las entry por nodo
            for (Entry<K, V> curr = head; curr != null; curr = curr.next) {
                put(curr.key, curr.value); //Va asignando nuevas posiciones a las claves y valores ya existentes
            }
        }
    }

    @Override
    public int size() {
        return size;
    }
    // Obtiene cada una de las entry del map
    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = new HashSet<>();
        for (Entry<K, V> head : table) {
            for (Entry<K, V> curr = head; curr != null; curr = curr.next) {
                set.add(curr);
            }
        }
        return set;
    }
    @Override
    public boolean containsKey(Object key) {
        return get(key) != null;
    }
}
