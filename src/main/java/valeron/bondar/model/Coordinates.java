package valeron.bondar.model;

import javax.validation.constraints.Min;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Coordinates {
    private int id;

    @Min(-271)
    private int xCoord;

    @Min(-595)
    private int yCoord;

    public Coordinates() { }

    public Coordinates(int xCoord, int yCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public int getId() {
        return id;
    }

    public int getxCoord() {
        return xCoord;
    }

    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
    }
}
