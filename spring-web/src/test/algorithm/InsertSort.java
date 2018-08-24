package algorithm;

public class InsertSort {
    public static void main(String[] args) {
        int [] a = {2,3,1,4,6,5,7,9,8};

        //直接插入排序
//        int temp = -1;
//        int n = 0;
//        for (int i = 1; i <= a.length-1; i++){
//            if(a[i-1] > a[i]){
//                temp = a[i];
//                a[i] = a[i-1];
//                for(int j = i -2; j >= 0; j--){
//                    if(a[j] > temp) {
//                        a[j + 1] = a[j];
//                    }else{
//                        n = j+1;
//                        break;
//                    }
//                }
//                a[n] = temp;
//            }
//        }


        //希尔排序
        //增量gap，并逐步缩小增量
        for(int gap=a.length/2;gap>0;gap/=2){
            //从第gap个元素，逐个对其所在组进行直接插入排序操作
            for(int i=gap;i<a.length;i++){
                int j = i;
                int temp = a[j];
                if(a[j]<a[j-gap]){
                    while(j-gap>=0 && temp<a[j-gap]){
                        //移动法
                        a[j] = a[j-gap];
                        j-=gap;
                    }
                    a[j] = temp;
                }
            }
        }

        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);

        }
    }
}
