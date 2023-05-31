package Banks.Interfaces;

public interface Observable {
    void Notification();

    void addObserver(Observer observer);
}
