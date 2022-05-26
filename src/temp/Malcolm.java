package temp;

public class Malcolm {

    private boolean light = false;

    public Malcolm() {
        // check if light is "ON" or "OFF"
        if (light) { // If true it run next line, if false it runs "else"
            System.out.println("Light is on");
            // Light is on
            // Turn off light
        } else { // runs if false
            System.out.println("Light is off");
            // Light is off
            // Turn on light
        }
    }
}
