package gesto.api.types;

public class Table {
    private String Num;
    private boolean Available;

    public Table(String Num, boolean Available) {
        this.Num = Num;
        this.Available = Available;
    }

    public void setNum(String Num) { this.Num = Num; }
    public void setAvailable(boolean Available) { this.Available = Available; }

    public String getNum() { return this.Num; }
    public boolean getAvailable() { return this.Available; }
}
