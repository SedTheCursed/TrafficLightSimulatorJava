package traffic;

import java.util.Arrays;

import static traffic.Utils.cleanConsole;
import static traffic.Utils.println;

public class TrafficSystem extends Thread {
    private Boolean keepRunning = true;
    private Boolean system = false;
    private int clock = 0;
    private final RoadNetwork roads;

    TrafficSystem(RoadNetwork roads) {
        this.roads = roads;
    }

    @Override
    public void run() {
        runClock();
    }

    /**
     * Every second, the clock in incremented by 1 and if the system information
     * are currently displayed, update them on the screen.
     */
    private void runClock() {
        while (keepRunning) {
            try {
                Thread.sleep(1000);
                clock++;
                if (clock > 1) roads.spendTime();

                if (system) printInformation();
            } catch (InterruptedException e) {
                clock = 0;
            }
        }
    }

    /**
     * Stop the system
     */
    public void stopSystem() {
        keepRunning = false;
    }

    /**
     * Set the system information to be displayed
     */
    public void displaySystemInformationOn() {
        system = true;
    }

    /**
     * Set the system information to not be displayed
     */
    public void displaySystemInformationOff() {
        system = false;
    }

    public String addRoad(String name) {
        return roads.addRoad(name);
    }

    public String removeRoad() {
        return roads.removeRoad();
    }

    /**
     * Show the time of the system has been on, the number of roads and the interval between light
     * on the console.
     */
     private void printInformation() {
         Road[] roadsNames = roads.getRoads();

         cleanConsole();
         println(String.format("! %ds. have passed since system startup !", clock));
         println(String.format("! Number of roads: %d !", roads.getMax()));
         println(String.format("! Interval: %d !", roads.getInterval()));

         if (roadsNames.length > 0) {
             println("");
             Arrays.stream(roadsNames).forEach(r -> {
                 String name = r.getName();
                 int timer = r.getTimer();
                 String status;
                 String color = switch (r.getStatus()) {
                     case CLOSED -> {
                         status = "closed";
                         yield "\u001B[31m";
                     }
                     case CHANGING -> {
                         status = "open";
                         yield "\u001B[33m";
                     }
                     case OPENED -> {
                         status = "open";
                         yield "\u001B[32m";
                     }
                 };

                 println(String.format("Road \"%s\" will be %s%s for %ds.\u001B[0m", name, color, status, timer));
             });
             println("");
         }

         println("! Press \"Enter\" to open menu !");
    }
}
