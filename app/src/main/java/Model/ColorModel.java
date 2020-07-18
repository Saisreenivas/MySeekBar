package Model;

/**
 * Created by Sai sreenivas on 2/15/2017.
 */

public class ColorModel {

    private int _id;
    private String color;

    public ColorModel() {
    }

    public ColorModel(int _id, String color) {
        this._id = _id;
        this.color = color;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
