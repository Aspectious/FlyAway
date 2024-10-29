package net.eastern.FlyAway.api.obj;

/**
 * @author aspectious
 *
 * The API JSON Object System is a way to translate JSON to server-side objects for management.
 * All types of Objects can be translated in the JSONTranslator Class and are children of this class.
 * I implemented IObject as a brief blueprint the ability to at least get the raw JSON for export
 *
 */
public class Object implements IObject {
    private String raw;

    public Object() {
        this.raw = "{}";
    }
    public Object(String JSON) {
        this.raw = JSON;
    }
    @Override
    public String getRawJSON() {
        return raw;
    }
    public String setRawJSON(String JSON) {
        this.raw = JSON;
        return JSON;
    }
}
