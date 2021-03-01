package Algorithm;

class SegmentTre{
    public static void main(String[] args) {
        int[] da = new int[]{10, 11, 9, 5, 15, 6};
        //SegmentTre test = new SegmentTre(da,6);
       // test.print();
    }
}

public class SegmentTree {
    private int[] source, base;
    private int size;

    public SegmentTree(int[] in, int size){
        this.base=in;
        source = new int[4*size];
        this.size=size;
        build(in,1, 0 , size-1);
    }

    private void build(int[] in, int v, int tl,int tr){
        if (tl==tr)
            source[v]=in[tl];
        else{
            int tm=(tl+tr)/2;
            build(in,v*2, tl,tm);
            build(in,v*2+1, tm+1,tr);
            source[v]=source[v*2]+source[v*2+1];
        }
    }
    public int sum(int v, int tl, int tr, int l, int r){
        if(l>r)
            return 0;
        if (l==tl && r==tr)  // корень полностью входит в отезок
            return source[v];
        int tm = (tl+tr)/2;
        return sum(v*2,tl,tm,l,Math.min(r,tm))
                +sum(v*2+1,tm+1,tr,Math.max(l,tm+1),r);
    }
    public void print(){
        for (int now:source) {
            System.out.print(now+" ");
        }
    }

    public int[] getBasicArr() {
        return base;
    }
}
