package net.eastern.FlyAway.api.obj;

/**
 * @author aspectious
 * The Message Object is quite simple.
 * JSON Format:
 * {"message":"<INSERT MESSAGE HERE>"}
 */
public class Message extends Object {
    public Message(String message) {
        super("{\"message\":\"" + message + "\"}");
    }
}
