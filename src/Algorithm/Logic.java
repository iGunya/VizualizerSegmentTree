package Algorithm;

import javafx.scene.control.Label;
import javafx.scene.text.Font;

import java.util.Random;

public class Logic {

    public static int[] greate(int n){
        int[] arr = new int[n];
        Random random = new Random();
        for (int i=0;i<n;i++){
            arr[i]=random.nextInt(29);
        }
        return arr;
    }

    public static void ViweTree (SegmentTree tree){
        Label temp = new Label();
        temp.setText("?");
        temp.setFont(new Font("Arial",16));
        temp.setStyle("-fx-border-color: black; -fx-border-radius: 20");
        temp.setMinHeight(40);
        temp.setMinWidth(40);
    }
}
