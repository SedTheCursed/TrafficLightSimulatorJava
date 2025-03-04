package traffic;

import java.util.Scanner;

import static traffic.Utils.*;

/**
 * Traffic light control panel
 */
public class Panel {
    private final Scanner sc = new Scanner(System.in);
    private final String[] commands = {"Quit","Add road","Delete road","Open system"};

    /**
     * Displays a welcome message
     */
    public void greetings() {
        println("Welcome to the traffic management system!");
    }

    /**
     * Ask for an integer to set a field
     * @param field Field description
     * @return Value to be set in a field
     */
    public int setField(String field) {
        String error = "Incorrect input. Please try again: ";

        print(String.format("Input %s: ", field));
        do {
            try {
                int input = Integer.parseInt(sc.nextLine());
                if (input <= 0) {
                    print(error);
                    continue;
                }
                return input;
            } catch (NumberFormatException e) {
                print(error);
            }
        }while (true);
    }

    /**
     * Ask for an action to be done by displaying a menu.
     * @return Action id
     */
    public int askAction() {
        displayMenu();
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public String askRoadName() {
        print("Input road name: ");
        return sc.nextLine();
    }

    /**
     * Display a given message in the console
     * @param msg Message to be displayed
     */
    public void displayMessage(String msg) {
        println(msg);
    }

    /**
     * Display information about the state of the system
     * @param system System which information are displayed
     */
    public void displaySystemInformation(TrafficSystem system) {
        system.displaySystemInformationOn();
    }

    /**
     * Clear the panel after an input
     * @param system System which information could be currently displayed
     */
    public void clear(TrafficSystem system) {
        sc.nextLine();
        system.displaySystemInformationOff();
        cleanConsole();
    }

    /**
     * Turn off the panel.
     */
    public void turnOff() {
        sc.close();
        println("Bye!");
    }

    /**
     * Display the control panel menu
     */
    private void displayMenu() {
        println("Menu:");
        for(int i = 1; i<commands.length; i++){
            println(String.format("%d. %s", i, commands[i]));
        }
        println(String.format("%d. %s", 0, commands[0]));
    }

}
