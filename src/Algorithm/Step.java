package Algorithm;

import javafx.scene.control.Label;

import java.util.ArrayList;

public class Step{
    public String discription;
    public ArrayList<Pair> litings=new ArrayList<Pair>(); //x-какой узел y-какой цвет
    public ArrayList<Pair> neverLitings=new ArrayList<Pair>(); //x-какой узел y-какой цвет
    public ArrayList <Pair> neverLitingsBase=new ArrayList<>(); // не зря книги читаю
    public ArrayList <Pair> litingsNowSeg=new ArrayList<>(); // не зря книги читаю
    public int ans=-1;
    public int date=-42;
    public int indexBase=-1;
}