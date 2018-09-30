package algorithm;


public class InsertSort {
    public static void main(String[] args) {
        int[] a = {2, 3, 1, 4, 6, 5, 7, 9, 8};

        //直接插入排序
//        int temp = -1;
//        for (int i = 1; i <= a.length - 1; i++) {
//            int n = 0;
//            if (a[i - 1] > a[i]) {
//                temp = a[i];
//                a[i] = a[i - 1];
//                for (int j = i - 2; j >= 0; j--) {
//                    if (a[j] > temp) {
//                        a[j + 1] = a[j];
//                    } else {
//                        n = j + 1;
//                        break;
//                    }
//                }
//                a[n] = temp;
//            }
//        }


        //希尔排序
        //增量gap，并逐步缩小增量
//        for(int gap=a.length/2;gap>0;gap/=2){
//            //从第gap个元素，逐个对其所在组进行直接插入排序操作
//            for(int i=gap;i<a.length;i++){
//                int j = i;
//                int temp = a[j];
//                if(a[j]<a[j-gap]){
//                    while(j-gap>=0 && temp<a[j-gap]){
//                        //移动法
//                        a[j] = a[j-gap];
//                        j-=gap;
//                    }
//                    a[j] = temp;
//                }
//            }
//        }


        //冒泡排序
//        int temp = -1;
//        for(int i = a.length - 1; i > 0; i--){
//            for(int j = 0; j < i; j++){
//                if(a[j] > a[j+1]){
//                    temp = a[j+1];
//                    a[j+1] = a[j];
//                    a[j] = temp;
//                }
//            }
//        }


        //快速排序
        QSort(a, 0, a.length - 1);


        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);

        }
    }

    public static void QSort(int[] a, int low, int high) {
        if (low < high) {
            int pivotloc = Partition(a, low, high);
            QSort(a, low, pivotloc - 1);
            QSort(a, pivotloc + 1, high);
        }
    }

    public static int Partition(int[] a, int low, int high) {
        int temp = a[low];
        while (low < high) {
            while (low < high && a[high] >= temp) {
                --high;
            }
            a[low] = a[high];
            while (low < high && a[low] <= temp) {
                ++low;
            }
            a[high] = a[low];
        }
        a[low] = temp;
        return low;
    }
}
