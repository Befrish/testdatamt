package de.befrish.testdatamt.collection.id.map;

import de.befrish.testdatamt.collection.id.Identifiable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * @author Benno Müller
 */
public interface IdMap<E extends Identifiable<?>> extends Iterable<E> {

    /**
     * Gibt die Anzahl der enthaltenen Elemente zurück.
     *
     * @return Anzahl der enthaltenen Elemente
     */
    int size();

    /**
     * Prüft, ob die {@code IdMap} leer ist.
     *
     * @return true, wenn kein Element enthalten; false anderfalls
     */
    boolean isEmpty();

    /**
     * Prüft, ob ein Element mit der gegebenen ID enthalten ist.
     *
     * @param id ID
     *
     * @return true, wenn Element mit ID enthalten; false anderfalls
     */
    boolean containsId(Object id);

    /**
     * Prüft, ob ein Element enthalten ist.
     *
     * @param value Element
     *
     * @return true, wenn Element enthalten; false anderfalls
     */
    boolean contains(E value);

    /**
     * Gibt das Element mit einer ID zurück. Ist kein solches Element enthalten, wird {@code null} zurückgegeben.
     *
     * @param id ID
     *
     * @return Element mit ID oder {@code null}
     */
    E get(Object id);

    /**
     * Fügt den Wert mit ID als Schlüssel hinzu.
     *
     * @param value hinzuzufügender Wert
     *
     * @return true, wenn diese IdMap sich geändert hat; false anderfalls
     */
    boolean add(E value);

    /**
     * Fügt alle Werte mit ID als Schlüssel hinzu.
     *
     * @param c hinzuzufügende Werte
     *
     * @return true, wenn diese IdMap sich geändert hat; false anderfalls
     */
    void addAll(IdMap<? extends E> c);

    /**
     * Entfernt den Wert mit der gegebenen ID aus dem IdMap.
     *
     * @param id ID des zu entfernenden Wertes
     *
     * @return true, wenn diese IdMap sich geändert hat; false anderfalls
     */
    boolean remove(Object id);

    /**
     * Entfernt alle Werte anhand deren IDs aus dem IdMap.
     *
     * @param ids IDs
     *
     * @return true, wenn diese IdMap sich geändert hat; false anderfalls
     */
    void removeAll(Collection<?> ids);

    /**
     * Gibt die Menge aller IDs von den enthaltenen Elementen zurück.
     *
     * @return Menge aller IDs
     */
    Set<Object> ids();

    /**
     * Gibt alle enthaltenen Elemente zurück.
     *
     * @return alle enthaltenen Elemente
     */
    Collection<E> values();

    /**
     * Gibt alle enthaltenen Elemente mit ihren IDs als {@link java.util.Map} zurück.
     *
     * @return alle enthaltenen Elemente mit IDs
     */
    Map<Object, E> valuesWithIds();

    /**
     * Gibt einen sequentiellen Stream zurück, welche als Quelle die {@code IdMap} hat.
     *
     * @return sequientieller Stream
     */
    Stream<E> stream();

    /**
     * Verarbeitet die einzelnen Elemente der {@code IdMap}.
     *
     * @param action Verarbeitung eines Elementes
     */
    default void traverseValues(final Consumer<E> action) {
        new ArrayList<>(values()).forEach(action);
    }

}
