package Algorithm;

public enum Colors{
    GREEN("greenyellow"), RED("coral"), BLUE("deepskyblue"), COLD("gold");

    public String getT() {
        return t;
    }
    private String t;
    Colors (String t){
        this.t=t;
    }
}
