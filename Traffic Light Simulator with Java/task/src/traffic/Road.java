package traffic;

public class Road {
    private final String name;
    private Status status = Status.CLOSED;
    private int currentTimer;

    public Road(String name, int timer) {
        this.name = name;
        currentTimer = timer;
    }

    public void spendTime() {
        currentTimer--;
    }

    public void decrementTimer(int decrement) {
        currentTimer = (decrement < currentTimer)? currentTimer - decrement : currentTimer;
    }

    public void openRoad() {
        status = Status.OPENED;
    }

    public void warnChangement() {
        status = Status.CHANGING;
    }

    public void closeRoad() {
        status = Status.CLOSED;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getTimer() {
        return currentTimer;
    }

    public Status getStatus() {
        return status;
    }

    //Setters
    public void setTimer(int timer) {
        currentTimer = timer;
    }
}
