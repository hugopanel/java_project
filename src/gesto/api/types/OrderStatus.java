package gesto.api.types;

public enum OrderStatus {
    PREPARING(0),
    SERVED(1),
    PAYED(2);

    private final int value;
    OrderStatus(int value) { this.value = value; }

    public int getValue() { return this.value; }
}
