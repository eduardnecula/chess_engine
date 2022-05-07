public class javaPiece {
    public String name;
    public int index;
    public String color;

    public javaPiece(String name, int index, String color) {
        this.name = name;
        this.index = index;
        this.color = color;
    }

    public javaPiece() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
