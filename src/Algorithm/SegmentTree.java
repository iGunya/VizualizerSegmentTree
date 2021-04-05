package Algorithm;

import javafx.scene.control.Label;

import java.util.ArrayList;

class SegmentTre{
    public static void main(String[] args) {
        int[] da = new int[]{10, 11, 9, 5, 15, 6};
        //SegmentTre test = new SegmentTre(da,6);
       // test.print();
    }
}

public class SegmentTree {
    private int[] source, base;
    private int lastLaiting;

    public Pair getBorder(int i) {
        return border[i+1];
    }

    private Pair[] border;
    private int size,sizeSoutce=0;


    private int lastIndex;
    private ArrayList<Label> node=new ArrayList<Label>();
    public ArrayList<Step> steps = new ArrayList<Step>();

    public SegmentTree(int[] in, int size){

        Step s=new Step();
        s.discription="Создан случайный массив из " + size +" элементов";
        steps.add(s);

        Step s2=new Step();
        s2.discription="Из вершины начинам заполнять дерево";
        s2.litings.add(new Pair(1,1));//пусть пока 1-красный
        steps.add(s2);

        this.base=in;
        source = new int[4*size];
        border = new Pair[4*size];
        this.size=size;
        build(in,1, 0 , size-1);
        correctStep();

        Step s3=new Step();
        s3.discription="Построение дерева законченно";
        steps.add(s3);
    }

    private void build(int[] in, int v, int tl,int tr){
        if (border[v] == null){
            border[v]=new Pair(tl,tr);
        }
        if (tl==tr) {
            sizeSoutce++;
           /* if (!steps.isEmpty()) {
                if(!steps.get(steps.size() - 1).discription.contains("Выходим из листа"))
                    steps.remove(steps.size()-1);
            }*/
            source[v] = in[tl];
            lastIndex=Math.max(v,lastIndex);
            Step s= new Step();
            s.date=source[v];
            s.indexBase = tl;
            s.discription="Заполняем лист (элемент исходного" +
                    " массива с индексом " +tl+") "+source[v]+"";
            s.litings.add(new Pair(v,0));
            steps.add(s);

            Step s1= new Step();
            s1.discription="Выходим из листа "+source[v];
            s1.litings.add(new Pair(v,2));
            s1.litings.add(new Pair(v/2,1));
            steps.add(s1);
        }
        else{
            int tm=(tl+tr)/2;

            Step s= new Step();
            s.discription="Ищем листы в левом поддереве ";
            s.litings.add(new Pair(v,2)); //родитель синий
            s.litings.add(new Pair(v*2,1)); //сын красный
            steps.add(s);

            build(in,v*2, tl,tm);

            Step s1= new Step();
            s1.discription="Ищем листы в правом поддереве ";
            s1.litings.add(new Pair(v,2)); //родитель синий
            s1.litings.add(new Pair(v*2+1,1)); //сын красный
            steps.add(s1);

            build(in,v*2+1, tm+1,tr);

            source[v]=source[v*2]+source[v*2+1];
            sizeSoutce++;

            Step s2= new Step();
            s2.date=source[v];
            s2.discription="Сумма узла "+v+" складывается из его \"детей\" "+source[v*2]+" "+source[v*2+1];
            s2.litings.add(new Pair(v,0)); //родитель красный
            s2.litings.add(new Pair(v*2,1)); //сын зелёный
            s2.litings.add(new Pair(v*2+1,1)); //сын зелёный
            steps.add(s2);

            Step s3= new Step();
            s3.discription="Выходим из узла "+source[v];
            s3.litings.add(new Pair(v,2)); //лист синий
            s3.litings.add(new Pair(v/2,1)); //сын красный
            steps.add(s3);
        }
    }

    public void correctStep(){
        steps.remove(steps.size()-1);
    }

    public int sum(int v, int tl, int tr, int l, int r){
        if(l>r) {
            Step s = new Step();
            for (int i=tl;i<=tr;i++)
                s.litingsNowSeg.add(new Pair(i,1));
            s.neverLitings= new ArrayList<>(steps.get(steps.size()-1).neverLitings);
            s.neverLitingsBase= new ArrayList<>(steps.get(steps.size()-1).neverLitingsBase);
            s.discription="Узел вне границы поиска";
            s.litings.add(new Pair(v,1));
            steps.add(s);

            Step s3 = new Step();
            s3.neverLitings= new ArrayList<>(steps.get(steps.size()-1).neverLitings);
            s3.neverLitingsBase= new ArrayList<>(steps.get(steps.size()-1).neverLitingsBase);
            s3.discription="Выходим из узла "+source[v];
            s3.litings.add(new Pair(v,2)); //лист синий
            s3.litings.add(new Pair(v/2,1)); //сын красный
            steps.add(s3);
            return 0;
        }
        if (l==tl && r==tr) { // корень полностью входит в отезок
            Step s = new Step();
            s.discription="Узел полностью входит в отрезок";
            s.neverLitingsBase= new ArrayList<>(steps.get(steps.size()-1).neverLitingsBase);
            s.neverLitings= new ArrayList<>(steps.get(steps.size()-1).neverLitings);
            for (int i=0;i<s.neverLitingsBase.size();i++)
                if(s.neverLitingsBase.get(i).x==l){
                    for (int k=i;k<i+r-l+1;k++)
                        s.neverLitingsBase.set(k,new Pair(s.neverLitingsBase.get(k).x, 0));
                    break;
                }
            s.ans=source[v];
            s.litings.add(new Pair(v,0));
            steps.add(s);


            Step s3 = new Step();
            s3.neverLitings= new ArrayList(steps.get(steps.size()-1).neverLitings);
            s3.neverLitingsBase= new ArrayList<>(steps.get(steps.size()-1).neverLitingsBase);
            s3.neverLitings.add(new Pair(v,0));
            s3.discription="Выходим из узла "+source[v]+"\nДобавляя значение узла к итоговоу ответу";
            s3.litings.add(new Pair(v,2)); //лист синий
            s3.litings.add(new Pair(v/2,1)); //сын красный
            steps.add(s3);

            return source[v];
        }
        int tm = (tl+tr)/2;

        Step s = new Step();
        s.discription="Заходим в левый узел";
        s.neverLitings= new ArrayList<>(steps.get(steps.size()-1).neverLitings);
        s.neverLitingsBase= new ArrayList<>(steps.get(steps.size()-1).neverLitingsBase);
        s.litings.add(new Pair(v*2, 2));
        /*
        if (r<tm) {
            for (int i = tl; i <= Math.min(tm,r); i++)
                s.litingsNowSeg.add(new Pair(i, 3));
            for (int i = r+1; i <= tm; i++)
                s.litingsNowSeg.add(new Pair(i, 1));
        }else{
            for (int i = tl; i < l; i++)
                s.litingsNowSeg.add(new Pair(i, 1));
            for (int i = l; i <= Math.min(tm,r); i++)
                s.litingsNowSeg.add(new Pair(i, 3));
        }*/
        for (int i = tl; i <= tm; i++)
            s.litingsNowSeg.add(new Pair(i, 1));
        for (int i = l; i <= Math.min(tm,r); i++)
            s.litingsNowSeg.add(new Pair(i, 3));
        steps.add(s);

        int sum1 = sum(v*2,tl,tm,l,Math.min(r,tm));

        Step s2 = new Step();
        s2.discription="Заходим в правый узел";
        s2.neverLitings= new ArrayList<>(steps.get(steps.size()-1).neverLitings);
        s2.neverLitingsBase= new ArrayList<>(steps.get(steps.size()-1).neverLitingsBase);
        s2.litings.add(new Pair(v*2+1, 2));
        for (int i = tm+1; i <= tr; i++)
            s2.litingsNowSeg.add(new Pair(i, 1));
        for (int i = Math.max(tm+1,l); i <= r; i++)
            s2.litingsNowSeg.add(new Pair(i, 3));
        steps.add(s2);

        int sum2 = sum(v*2+1,tm+1,tr,Math.max(l,tm+1),r);
        Step s4 = new Step();
        s4.discription="Выходим из узла "+source[v];
        s4.litings.add(new Pair(v,2)); //лист синий
        s4.neverLitings= new ArrayList<>(steps.get(steps.size()-1).neverLitings);
        s4.neverLitingsBase= new ArrayList<>(steps.get(steps.size()-1).neverLitingsBase);
        s4.litings.add(new Pair(v/2,1)); //сын красный
        steps.add(s4);

        return sum1+sum2;
    }
    public void print(){
        for (int now:source) {
            System.out.print(now+" ");
        }
    }

    public int getSizeSourse() {
        return sizeSoutce;
    }

    public void createNodeArr(){
        for(int i=1;i<lastIndex+1;i++){
            if (source[i]!=0) {
                this.node.add(Logic.ViweTree(i,false));
            } else{
                this.node.add(Logic.ViweTree(i,true));
            }
        }
    }

    public Step getStep (int i){
        return steps.get(i);
    }

    public int getSize() {
        return size-1;
    }

    public void setDataNode(int date, int i){
        this.node.get(i).setText(String.valueOf(date));
    }
    public Label getNode(int i){
        return this.node.get(i);
    }
    public int[] getBasicArr() {
        return base;
    }
    public int getLastIndex() {
        return lastIndex;
    }
}


