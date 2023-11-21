public class Door {
    String outside;
    int number;

    public Door(int number, String outside) {
        this.number = number;
        this.outside = outside;
    }

    public String getOutside() {
        return outside;
    }

    public void setOutside(String outside) {
        this.outside = outside;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "№" + getNumber() + "->" + getOutside();
    }
}
