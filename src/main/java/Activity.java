public class Activity {
    private String name;
    private boolean isCircular = false;
    private boolean isFakeCircular = false;
    private int num;

    public Activity(String name) {
        this.name = name;
    }

    public Activity(String name, boolean isCircular, boolean isFakeCircular) {
        this.name = name;
        this.isCircular = isCircular;
        this.isFakeCircular = isFakeCircular;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCircular() {
        return isCircular;
    }

    public void setCircular(boolean circular) {
        isCircular = circular;
    }

    public boolean isFakeCircular() {
        return isFakeCircular;
    }

    public void setFakeCircular(boolean fakeCircular) {
        isFakeCircular = fakeCircular;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
