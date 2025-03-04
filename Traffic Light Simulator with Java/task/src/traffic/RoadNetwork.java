package traffic;

import java.util.Arrays;
import java.util.Objects;

public class RoadNetwork {
    private final int maxRoads;
    private final Road[] roads;
    private int roadsInSystem = 0;
    private int front = 0;
    private int rear = 0;
    private final int interval;
    private int timer;
    private int currentRoad = 0;

    public RoadNetwork(int max, int interval) {
        maxRoads = max;
        roads = new Road[max];
        this.interval = interval;
        timer = interval;
    }

    public String addRoad(String name) {
        if (roadsInSystem < maxRoads) {
            // If the queue is empty, reinitialise it
            if (roadsInSystem == 0) {
                front = 0;
                rear = -1;
                currentRoad = 0;
                timer = interval;
            }
            rear = (rear + 1) % maxRoads;
            roads[rear] = new Road(name, interval * (roadsInSystem - 1) + timer);
            roadsInSystem++;
            if (roadsInSystem == 1) {
                roads[rear].openRoad();
                roads[rear].setTimer(interval);
            }
            return String.format("%s added", name);
        } else {
            return "The queue is full";
        }
    }

    public String removeRoad() {
        if (roadsInSystem > 0) {
            if (front == currentRoad) changeRoad(true);
            Road road = roads[front];
            roads[front] = null;
            roadsInSystem--;
            front = (front + 1) % maxRoads;
            Arrays.stream(roads)
                    .filter(r -> r != null && r.getStatus() == Status.CLOSED)
                    .forEach(r -> r.decrementTimer(interval));
            return String.format("%s deleted", road.getName());
        } else {
            return "The queue is empty";
        }
    }

    public void spendTime() {
        if (roadsInSystem == 0) return;
        timer = timer > 0 ? timer - 1 : interval;
        Arrays.stream(roads)
                .filter(Objects::nonNull)
                .forEach(Road::spendTime);

        if (roads[currentRoad].getTimer() == 2) roads[currentRoad].warnChangement();
        if (roads[currentRoad].getTimer() == 0) changeRoad(false);
    }

    public void changeRoad(boolean onDeletion) {
        Road former = roads[currentRoad];
        former.closeRoad();
        former.setTimer(interval * (roadsInSystem - 1));
        if (currentRoad == rear) {
            currentRoad = front;
        } else if (currentRoad == roads.length - 1) {
            currentRoad = 0;
        } else {
            currentRoad++;
        }

        roads[currentRoad].openRoad();
        roads[currentRoad].setTimer(onDeletion? interval + 1 : interval);
    }

        // Getters
    public int getMax() {
        return maxRoads;
    }

    public Road[] getRoads() {
        if (roadsInSystem == 0) return new Road[0];

        Road[] roadNames = new Road[roadsInSystem];
        int index = 0;
        if (front <= rear) {
            for (int i = front; i <= rear; i++) {
                roadNames[index] = roads[i];
                index++;
            }
        } else {
           for (int i = front; i < roads.length; i++) {
               roadNames[index] = roads[i];
               index++;
           }
           for (int i = 0; i <= rear; i++) {
               roadNames[index] = roads[i];
               index++;
           }
        }
        return roadNames;
    }

    public int getInterval() {
        return interval;
    }
}
