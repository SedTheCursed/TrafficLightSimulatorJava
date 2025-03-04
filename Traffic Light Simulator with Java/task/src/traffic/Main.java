package traffic;

public class Main {
    private static final Panel panel = new Panel();
    private static TrafficSystem system;

    public static void main(String[] args){
        initialiseSystem();
        operate();
    }

    /**
     * Initialise the system by displaying a welcome message and asking for the initial
     * number of roads and the interval of opening/closing roads
     */
    private static void initialiseSystem() {
        panel.greetings();
        system = new TrafficSystem(
            new RoadNetwork(
                panel.setField("the number of roads"),
                panel.setField("the interval")
            )
        );
        system.setName("QueueThread");
        system.start();
    }

    /**
     * Ask for an action and execute it.
      */
    private static void operate() {
        while (true) {
            switch (panel.askAction()) {
                case 1:
                    addRoad();
                    break;
                case 2:
                    removeRoad();

                    break;
                case 3:
                    panel.displaySystemInformation(system);
                    break;
                case 0:
                    system.stopSystem();
                    panel.turnOff();
                    return;
                default:
                    panel.displayMessage("Incorrect option");
            }
            panel.clear(system);
        }
    }

    private static void addRoad() {
        String newRoad = panel.askRoadName();
        String response = system.addRoad(newRoad);
        panel.displayMessage(response);
    }

    private static void removeRoad() {
        String response = system.removeRoad();
        panel.displayMessage(response);
    }
}
