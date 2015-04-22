package hello;

/**
 * Created by kaustubh on 21/04/15.
 */
public class OurReference {
    private final String id;
    private final String name;

    public OurReference(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
