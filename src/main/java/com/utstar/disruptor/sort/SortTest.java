package com.utstar.disruptor.sort;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;

public class SortTest {

    public int[] arr = {51,84,55,12,79,64};

    /**
     * 冒泡排序
     */
    @Test
    public void test1(){
        for(int i=0;i<arr.length-1;i++){
            for(int j=0;j<arr.length-i-1;j++){
                if(arr[j]>arr[j+1]){
                    int temp = arr[j+1];
                    arr[j+1]=arr[j];
                    arr[j]=temp;
                }
            }
        }
        System.out.println(Arrays.toString(arr));
    }


    /**
     * 快速排序
     */
    @Test
    public void test2(){
        quickSort(arr,0,arr.length-1);
        System.out.println(Arrays.toString(arr));
    }

    public void quickSort(int[] a,int low,int high){
        if(low < high){
            int middle = getMiddle(a,low,high);
            //低字段区递归排序
            quickSort(a,low,middle-1);
            //高字段区递归排序
            quickSort(a,middle+1,high);
        }
    }

    /**
     * 获取中间索引
     * @param a
     * @param low
     * @param high
     * @return
     */
    private int getMiddle(int[] a, int low, int high) {
        //中轴{51,84,55,12,79,64};
        int temp = a[low];
        while (low<high){
            while (low<high && a[high]>=temp){
                high--;
            }
            //小的放到中轴左边
            a[low] = a[high];
            while (low<high && a[low]< temp){
                low++;
            }
            //大的放右边
            a[high] = a[low];
        }
        a[low] = temp;
        return low;
    }


    /**
     * 选择排序
     */
    @Test
    public void test3(){
        int size = arr.length;
        int temp = 0;
        for(int i=0;i<size;i++){
            //记录每一轮最小元素位置
            int k = i;
            for(int j=size-1;j>i;j--){
                if(arr[j]<arr[k]){
                    //确定最小元素位置
                    k = j;
                }
            }
            //将最小元素交换到左边
            temp = arr[i];
            arr[i] = arr[k];
            arr[k] = temp;
        }
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 插入排序
     */
    @Test
    public void test4(){

        int size = arr.length;
        int temp = 0;
        int j=0;
        for(int i=0;i<size;i++){
            temp = arr[i];
            //如果比较元素比已排序列表从右向左第一个元素小,将前面的值后移一位
            for(j = i;j > 0 && temp < arr[j-1];j--){
                arr[j] = arr[j-1];
            }
            arr[j] = temp;
        }
        System.out.println(Arrays.toString(arr));
    }


}
