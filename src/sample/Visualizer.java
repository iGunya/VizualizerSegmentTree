package sample;

import Algorithm.Logic;
import Algorithm.SegmentTree;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Visualizer {

    private SegmentTree segmentTree;
    @FXML
    private Button ButtonSizeArr;

    @FXML
    private TextField SizeArr;

    @FXML
    private TextField MinSeg;

    @FXML
    private TextField MaxSeg;

    @FXML
    private Button MoveSeg;

    @FXML
    private Button ButtonPrev;

    @FXML
    private Button ButtonNext;

    @FXML
    private Button ButtonAuto;

    @FXML
    private Label NowStep;

    @FXML
    private Label Discription;

    @FXML
    private Label Ans;

    @FXML
    private ScrollPane ScrollBar;

    @FXML
    void initialize(){
        ButtonSizeArr.setOnAction(event -> {
            String sizeArr=SizeArr.getText().trim();

            if(!sizeArr.equals("")){
                createArr(sizeArr);
            }
        });
    }

    private void createArr(String sizeArr) {
        int size= Integer.parseInt(sizeArr);
        segmentTree=new SegmentTree(Logic.greate(size), size);
        viewArray(segmentTree.getBasicArr());
    }
    public void viewArray(int[] in){
        HBox container = new HBox(in.length);
        container.setSpacing(0);
        //FlowPane container = new FlowPane();
        //ScrollBar.setFitToHeight(true);
        ScrollBar.setContent(container);

        int x=0;
        for(int now:in){
            Label temp = new Label();
            temp.setText(String.valueOf(now));
            temp.setStyle("-fx-border-color: black;");
            temp.setFont(new Font("Arial",23));
            temp.setMinHeight(50);
            temp.setMinWidth(50);
            //temp.setTranslateX(x);
            temp.setAlignment(Pos.CENTER);
            container.getChildren().add(temp);
            //x+=60;

        }
        //ScrollBar.setContent(container);
    }
}
