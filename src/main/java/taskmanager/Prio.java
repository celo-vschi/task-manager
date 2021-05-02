package taskmanager;

public enum Prio {

    HIGH(0),
    MEDIUM(1),
    LOW(2);

    private final int intValue;

    Prio(int intValue) {
        this.intValue = intValue;
    }

    public int getIntValue() {
        return this.intValue;
    }


}
