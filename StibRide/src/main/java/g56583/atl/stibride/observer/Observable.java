package g56583.atl.stibride.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * /**
 * This class represents an observable object, or "data" in the model-view
 * paradigm. It can be subclassed to represent an object that the application
 * wants to have observed.
 * <p>
 * An observable object can have one or more observers. An observer may be any
 * object that implements interface {@code Observer}. After an observable
 * instance changes, an application calling the {@code Observable}'s
 * {@code notifyObservers} method causes all of its observers to be notified of
 * the change by a call to their {@code update} method.
 * <p>
 * The order in which notifications will be delivered is unspecified. The
 * default implementation provided in the Observable class will notify Observers
 * in the order in which they registered interest, but subclasses may change
 * this order, use no guaranteed order, deliver notifications on separate
 * threads, or may guarantee that their subclass follows this order, as they
 * choose.
 * <p>
 * Note that this notification mechanism has nothing to do with threads and is
 * completely separate from the {@code wait} and {@code notify} mechanism of
 * class {@code Object}.
 * <p>
 * When an observable object is newly created, its set of observers is empty.
 * Two observers are considered the same if and only if the {@code equals}
 * method returns true for them.
 *
 * @author jlc
 */
public class Observable {

    private final List<Observer> observers;

    /**
     * Construct an Observable with zero Observers.
     */
    public Observable() {
        observers = new ArrayList<>();
    }

    /**
     * Adds an observer to the set of observers for this object, provided that
     * it is not the same as some observer already in the set. The order in
     * which notifications will be delivered to multiple observers is not
     * specified. See the class comment.
     *
     * @param observer an observer to be added.
     * @throws NullPointerException if the parameter o is null.
     */
    public void addObserver(Observer observer) {
        if (observer == null) {
            throw new NullPointerException();
        }
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    /**
     * Deletes an observer from the set of observers of this object. Passing
     * {@code null} to this method will have no effect.
     *
     * @param observer the observer to be deleted.
     */
    public synchronized void deleteObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     * If this object has changed, as indicated by the {@code hasChanged}
     * method, then notify all of its observers and then call the
     * {@code clearChanged} method to indicate that this object has no longer
     * changed.
     * <p>
     * Each observer has its {@code update} method called with two arguments:
     * this observable object and {@code null}. In other words, this method is
     * equivalent to:
     * <blockquote>{@code
     * notifyObservers(null)}</blockquote>
     *
     * @see java.util.Observable#clearChanged()
     * @see java.util.Observable#hasChanged()
     * @see java.util.Observer#update(java.util.Observable, Object)
     */
    protected void notifyObservers() {
        notifyObservers(null);
    }

    /**
     * If this object has changed, as indicated by the {@code hasChanged}
     * method, then notify all of its observers and then call the
     * {@code clearChanged} method to indicate that this object has no longer
     * changed.
     * <p>
     * Each observer has its {@code update} method called with two arguments:
     * this observable object and the {@code arg} argument.
     *
     * @param arg any object.
     * @see java.util.Observable#clearChanged()
     * @see java.util.Observable#hasChanged()
     * @see java.util.Observer#update(java.util.Observable, Object)
     */
    protected void notifyObservers(Object arg) {
        for (Observer observer : observers) {
            observer.update(this, arg);
        }
    }

    /**
     * Returns the number of observers of this {@code Observable} object.
     *
     * @return the number of observers of this object.
     */
    public synchronized int countObservers() {
        return observers.size();
    }
}
