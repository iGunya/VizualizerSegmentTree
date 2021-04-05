package Algorithm;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import sample.Visualizer;

import java.util.Random;

public class Logic {

    public Pair findXY(int node){
        int lvl=0;
        int temp=node;
        while(temp!=0){
            temp>>=1;
            lvl++;
        }
        temp=1;
        for (int i=0;i<lvl-1;i++){
            temp<<=1;
        }
        return new Pair(node-temp,50*lvl);
    }

    public static int[] greate(int n){
        int[] arr = new int[n];
        Random random = new Random();
        for (int i=0;i<n;i++){
            arr[i]=random.nextInt(29)+1;
        }
        return arr;
    }

    public static Label ViweTree (int node, boolean f){
        if (f) return new Label();
        Label temp = new Label();
        temp.setText("?");
        temp.setFont(new Font("Arial",16));
        temp.setStyle("-fx-border-color: black; -fx-border-radius: 20");
        temp.setMinHeight(40);
        temp.setMinWidth(40);
        temp.setAlignment(Pos.CENTER);
        {
            int lvl = 0;
            int tmp = node;
            while (tmp != 0) {
                tmp >>= 1;
                lvl++;
            }
            tmp = 1;
            for (int i = 0; i < lvl - 1; i++) {
                tmp <<= 1;
            }
            int dx=tmp>>1;
            tmp--;
            double x =(1300.0 / (tmp+2)) * (node - tmp);
            if (lvl!=1) {
                if ((node & dx) != 0) {
                    x = x + (120 / lvl);
                } else {
                    x = x - (120 / lvl);
                }
            }
            int y= 75 * lvl;
            temp.setLayoutX(x);
            temp.setLayoutY(y);
        }
        //System.out.println(temp.getLayoutX()+ " " +temp.getLayoutY());
        return temp;
    }
    public static Line ViweLine(double x, double y, double xEnd, double yEnd){
        Line line = new Line();
        line.setStartX(x+20);
        line.setStartY(y);
        line.setEndX(xEnd+20);
        line.setEndY(yEnd+40);
        return line;
    }
    public static Label ViweBorder(double x, double y,Pair i,int indx){
        Label lable = new Label();
        int dx=0;
        if (indx%2==0)
            dx=20;
        else
            dx=-20;
        if (i.y==i.x){
            lable.setText("["+i.x+","+i.y+"]");
            lable.setLayoutX(x);
            lable.setLayoutY(y+40);
        } else {
            lable.setText("[" + i.x + "," + i.y + "]");
            lable.setLayoutX(x+dx);
            lable.setLayoutY(y - 20);
        }
        return lable;
    }
}

