package hello;

import org.springframework.data.annotation.Id;

/**
 * Created by kaustubh on 20/04/15.
 */
public class Person {

    @Id
    private String _id;
    private String name;

    public Person(String _id, String name) {
        this._id = _id;
        this.name = name;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
