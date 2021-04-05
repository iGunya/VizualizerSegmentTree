package sample;

import Algorithm.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class Visualizer {

    public int currentStep=-1;
    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), this::handle));
    boolean autoNext=false;

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
    private AnchorPane DrowPane;

    @FXML
    private Button to;

    @FXML
    private TextField numTo;

    @FXML
    void initialize(){
        ButtonSizeArr.setOnAction(event -> {
            String sizeArr=SizeArr.getText().trim();
            try {
                Integer.parseInt(sizeArr);
            }catch (NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Ошибка данных");
                alert.setHeaderText("Введенно не число");
                alert.setContentText("Введите целое число");
                alert.showAndWait();
                return;
            }
            if (Integer.parseInt(sizeArr)>16) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Превышение количества элементов");
                alert.setHeaderText(sizeArr+" элементов не влезает на форму");
                alert.setContentText("Задайте не более 16 элементов");
                alert.showAndWait();
                return;
            }
            if (Integer.parseInt(sizeArr)<0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Отрицательное значение");
                alert.setHeaderText("Введенно отрицательное значение");
                alert.setContentText("Введите положительное число");
                alert.showAndWait();
                return;
            }
            DrowPane.getChildren().clear();
            currentStep=-1;
            if(!sizeArr.equals("")){
                createArr(sizeArr);
            }
            buttonControlNext();
            NowStep.setText(0+"\\"+(segmentTree.steps.size()-1));
        });

        ButtonNext.setOnAction(event -> {
            buttonControlNext();
        });

        ButtonPrev.setOnAction(event -> {
           buttonControlPrev();
        });

        MoveSeg.setOnAction(event -> {
            try {
                int a = Integer.parseInt(MinSeg.getText());
                int b = Integer.parseInt(MaxSeg.getText());
                if (b<a) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Неверные границы");
                    alert.setHeaderText("Правая граница меньше левой");
                    alert.setContentText("Введите корректные границы");
                    alert.showAndWait();
                    return;
                }
            }catch (NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Ошибка данных");
                alert.setHeaderText("Введенно не число");
                alert.setContentText("Введите целое число");
                alert.showAndWait();
                return;
            }
            if(currentStep < segmentTree.steps.size()-1){
                System.out.print("Построение дерева еще не законченно");
            }else {
                Step s =new Step();
                s.discription = "Начинаем поиск из вершины дерева";
                for (int i=Integer.parseInt(MinSeg.getText());i<Integer.parseInt(MaxSeg.getText())+1;i++)
                    s.neverLitingsBase.add(new Pair(i,2));
                s.litings.add(new Pair(1,2));
                segmentTree.steps.add(s);
                Step s1 =new Step();
                s1.discription = "Итоговая сумма диапазона: "+segmentTree.sum(1, 0, segmentTree.getSize(), Integer.parseInt(MinSeg.getText()), Integer.parseInt(MaxSeg.getText()));
                segmentTree.steps.get(segmentTree.steps.size()-1).discription="Подсчёт закончен";
                segmentTree.steps.get(segmentTree.steps.size()-1).litings.remove(1);
                s1.neverLitings= segmentTree.steps.get(segmentTree.steps.size()-1).neverLitings;
                s1.neverLitingsBase = segmentTree.steps.get(segmentTree.steps.size()-1).neverLitingsBase;
                for (int i=0;i<segmentTree.getBasicArr().length;i++)
                    s.litingsNowSeg.add(new Pair(i,1));
                for (int i=Integer.parseInt(MinSeg.getText());i<=Integer.parseInt(MaxSeg.getText());i++)
                    s.litingsNowSeg.add(new Pair(i,3));
                segmentTree.steps.add(s1);
            }
            NowStep.setText(currentStep+"\\"+(segmentTree.steps.size()-1));
        });

        to.setOnAction(event -> {
            currentStep=0;
            for (;currentStep<=Integer.parseInt(numTo.getText());currentStep++) {
                if (currentStep < segmentTree.steps.size()) {
                    clearView();
                    if (segmentTree.getStep(currentStep).ans != -1) {
                        Ans.setText(String.valueOf(Integer.parseInt(Ans.getText()) + segmentTree.getStep(currentStep).ans));
                    }
                    Discription.setText(segmentTree.getStep(currentStep).discription);
                    if (segmentTree.getStep(currentStep).indexBase != -1) {
                        ((HBox) ScrollBar.getContent()).getChildren().get(segmentTree.getStep(currentStep).indexBase).setStyle("-fx-background-color: greenyellow ;");
                    }
                    if (segmentTree.getStep(currentStep).date != -42) {
                        if (segmentTree.getStep(currentStep).litings.get(0).x == 1) {
                            ((Label) DrowPane.getChildren().get(0)).setText(String.valueOf(segmentTree.getStep(currentStep).date));
                        } else
                            ((Label) DrowPane.getChildren().get((segmentTree.getStep(currentStep).litings.get(0).x - 1) * 3 - 1)).setText(String.valueOf(segmentTree.getStep(currentStep).date));
                    }
                    liting();
                }
            }
            currentStep--;
        });

        ButtonAuto.setOnAction(event -> {
            if (autoNext){
                autoNext = false;
                timeline.stop();
                ButtonAuto.setText("Автовыполнение");
            } else  {
                ButtonAuto.setText("ОСТАНОВИТЬ");
                timeline.setCycleCount(segmentTree.steps.size()-1-currentStep);
                timeline.play();
                autoNext=true;
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
        }
        viewTree();

        //ScrollBar.setContent(container);
    }
    public void viewTree(){
        segmentTree.createNodeArr();
        for(int i=0;i<segmentTree.getLastIndex();++i){
            Label da= segmentTree.getNode(i);
            DrowPane.getChildren().add(da);
            if (da.getText().isEmpty()) {
                DrowPane.getChildren().add(new Line());
                DrowPane.getChildren().add(new Line());// костыль
                continue;
            }
            if (i!=0) DrowPane.getChildren().add(Logic.ViweLine(da.getLayoutX(), da.getLayoutY(),
                    segmentTree.getNode((i-1)/2).getLayoutX(),segmentTree.getNode((i-1)/2).getLayoutY()));
            DrowPane.getChildren().add(Logic.ViweBorder(da.getLayoutX(), da.getLayoutY(), segmentTree.getBorder(i),i));
        }
    }

    private void buttonControlPrev(){
        if(currentStep < segmentTree.steps.size()){
            currentStep--;
            clearView();
            Discription.setText(segmentTree.getStep(currentStep).discription);
            if (segmentTree.getStep(currentStep).ans != -1) {
                Ans.setText(String.valueOf(Integer.parseInt(Ans.getText()) - segmentTree.getStep(currentStep).ans));
            }
            if (segmentTree.getStep(currentStep).indexBase!=-1){
                ((HBox)ScrollBar.getContent()).getChildren().get(segmentTree.getStep(currentStep).indexBase).setStyle("-fx-background-color: greenyellow ;");
            }
            if (segmentTree.getStep(currentStep+1).date != -42){
                if (segmentTree.getStep(currentStep+1).litings.get(0).x == 1){
                    ((Label)DrowPane.getChildren().get(0)).setText("?");
                }else
                    ((Label)DrowPane.getChildren().get((segmentTree.getStep(currentStep+1).litings.get(0).x-1)*3-1)).setText("?");
            }
            liting();
        }
    }

    private void clearView(){
        NowStep.setText(currentStep+"\\"+(segmentTree.steps.size()-1));
        for (Node now:DrowPane.getChildren()) {
            if (now instanceof Label){
                if (((Label)now).getText().indexOf('[')==-1){
                    now.setStyle("-fx-background-color: null;-fx-border-color: black; -fx-border-radius: 20");
                }
            }
        }
        for (Node now:((HBox)ScrollBar.getContent()).getChildren()){
            now.setStyle("-fx-background-color: null; -fx-border-color: black");
        }
    }

    private void liting(){
        if (!segmentTree.getStep(currentStep).neverLitingsBase.equals(null)){
            for (int i=0;i<segmentTree.getStep(currentStep).neverLitingsBase.size();i++) {
                Colors[] temp = Colors.values();
                String color = temp[segmentTree.getStep(currentStep).neverLitingsBase.get(i).y].getT();
                ((HBox) ScrollBar.getContent()).getChildren().get(segmentTree.getStep(currentStep).neverLitingsBase.get(i).x)
                        .setStyle("-fx-background-color: "+color+" ;");
            }
        }
        if (!segmentTree.getStep(currentStep).litingsNowSeg.equals(null)){
            for (int i=0;i<segmentTree.getStep(currentStep).litingsNowSeg.size();i++) {
                Colors[] temp = Colors.values();
                //(Label)((HBox) ScrollBar.getContent()).getChildren().get(segmentTree.getStep(currentStep).litingsNowSeg.get(i).x).getStyle().
                String color = temp[segmentTree.getStep(currentStep).litingsNowSeg.get(i).y].getT();
                ((HBox) ScrollBar.getContent()).getChildren().get(segmentTree.getStep(currentStep).litingsNowSeg.get(i).x)
                        .setStyle("-fx-background-color: "+color+" ;");
            }
        }
        if(segmentTree.getStep(currentStep).neverLitings.size() != 0) {
            for (int i=0;i<segmentTree.getStep(currentStep).neverLitings.size();i++) {
                Colors[] temp = Colors.values();
                String color = temp[segmentTree.getStep(currentStep).neverLitings.get(i).y].getT();
                if (segmentTree.getStep(currentStep).neverLitings.get(i).x == 1){
                    DrowPane.getChildren().get(0).
                            setStyle("-fx-background-radius: 40;-fx-background-color: "+color+";"); // в стили можно добваить -fx-border-color: black; -fx-border-radius: 20;
                } else{
                    DrowPane.getChildren().get((segmentTree.getStep(currentStep).neverLitings.get(i).x-1)*3-1).
                            setStyle("-fx-background-radius: 40;-fx-background-color: "+color+";"); // в стили можно добваить -fx-border-color: black; -fx-border-radius: 20;
                }
            }
        }
        if(segmentTree.getStep(currentStep).litings.size() != 0) {
            for (int i=0;i<segmentTree.getStep(currentStep).litings.size();i++) {
                Colors[] temp = Colors.values();
                String color = temp[segmentTree.getStep(currentStep).litings.get(i).y].getT();
                if (segmentTree.getStep(currentStep).litings.get(i).x == 1){
                    DrowPane.getChildren().get(0).
                            setStyle("-fx-background-radius: 40;-fx-background-color: "+color+";"); // в стили можно добваить -fx-border-color: black; -fx-border-radius: 20;
                } else{
                    DrowPane.getChildren().get((segmentTree.getStep(currentStep).litings.get(i).x-1)*3-1).
                            setStyle("-fx-background-radius: 40;-fx-background-color: "+color+";"); // в стили можно добваить -fx-border-color: black; -fx-border-radius: 20;
                }
            }
        }
    }

    private void buttonControlNext(){
        if(currentStep < segmentTree.steps.size()-1){
            currentStep++;
            clearView();
            if (segmentTree.getStep(currentStep).ans!=-1){
                Ans.setText(String.valueOf(Integer.parseInt(Ans.getText())+segmentTree.getStep(currentStep).ans));
            }
            Discription.setText(segmentTree.getStep(currentStep).discription);

            if (segmentTree.getStep(currentStep).indexBase!=-1){
                ((HBox)ScrollBar.getContent()).getChildren().get(segmentTree.getStep(currentStep).indexBase).setStyle("-fx-background-color: greenyellow ;");
            }
            if (segmentTree.getStep(currentStep).date != -42){
                if (segmentTree.getStep(currentStep).litings.get(0).x == 1){
                    ((Label)DrowPane.getChildren().get(0)).setText(String.valueOf(segmentTree.getStep(currentStep).date));
                }else
                    ((Label)DrowPane.getChildren().get((segmentTree.getStep(currentStep).litings.get(0).x-1)*3-1)).setText(String.valueOf(segmentTree.getStep(currentStep).date));
            }
            liting();
        }
        if (currentStep==segmentTree.steps.size()-1){
            ButtonAuto.setText("Автовыполнение");
        }
    }

    private void handle(ActionEvent ev) {
        buttonControlNext();
    }
}
