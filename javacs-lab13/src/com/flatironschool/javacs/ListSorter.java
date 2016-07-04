/**
 * 
 */
package com.flatironschool.javacs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Provides sorting algorithms.
 *
 */
public class ListSorter<T> {

        /**
         * Sorts a list using a Comparator object.
         * 
         * @param list
         * @param comparator
         * @return
         */
        public void insertionSort(List<T> list, Comparator<T> comparator) {
        
                for (int i=1; i < list.size(); i++) {
                        T elt_i = list.get(i);
                        int j = i;
                        while (j > 0) {
                                T elt_j = list.get(j-1);
                                if (comparator.compare(elt_i, elt_j) >= 0) {
                                        break;
                                }
                                list.set(j, elt_j);
                                j--;
                        }
                        list.set(j, elt_i);
                }
        }

        /**
         * Sorts a list using a Comparator object.
         * 
         * @param list
         * @param comparator
         * @return
         */
        public void mergeSortInPlace(List<T> list, Comparator<T> comparator) {
                List<T> sorted = mergeSort(list, comparator);
                list.clear();
                list.addAll(sorted);
        }

        /**
         * Sorts a list using a Comparator object.
         * 
         * Returns a list that might be new.
         * 
         * @param list
         * @param comparator
         * @return
         */
        public List<T> mergeSort(List<T> list, Comparator<T> comparator) {
                Integer size = list.size(); 

                if (size <= 1){
                        return list; 
                }
                Integer middle = size / 2; 
                List<T> left = list.subList(0, middle); 
                List<T> right = list.subList(middle, size); 
                left = mergeSort(left, comparator); 
                right = mergeSort(right, comparator); 
                return merge(left, right, left.size(), right.size(), comparator);
        
        }
        private List<T> merge(List<T> left, List<T> right, Integer sizeL, Integer sizeR, Comparator<T> comparator){
                List<T> merge_list = new ArrayList<T>(); 
                Integer leftCount = 0, rightCount = 0; 
                while (leftCount < sizeL && rightCount < sizeR){
                        T lval = left.get(leftCount); 
                        T rval = right.get(rightCount); 

                        if (comparator.compare(lval, rval) < 0){
                                merge_list.add(lval); 
                                leftCount++; 
                        }
                        else {
                                merge_list.add(rval); 
                                rightCount++;
                        }
                }
                if (leftCount >= sizeL){
                        merge_list.addAll(right.subList(rightCount, sizeR)); 
                }
                else{
                        merge_list.addAll(left.subList(leftCount, sizeL)); 
                }
                return merge_list; 
        }

        /**
         * Sorts a list using a Comparator object.
         * 
         * @param list
         * @param comparator
         * @return
         */
        public void heapSort(List<T> list, Comparator<T> comparator) {
        // FILL THIS IN!
                Integer init_size = 10; 
                PriorityQueue<T> queue = new PriorityQueue<T>(init_size, comparator);
                Integer count = 0; 
                Integer size = list.size(); 
                while(count < size){
                        queue.offer(list.get(count));
                        count++;  
                } 
                list.clear();
                while(true){
                        T val = (T)queue.poll(); 
                        if (val == null)
                                return; 
                        list.add(val);
                }
        }

        
        /**
         * Returns the largest `k` elements in `list` in ascending order.
         * 
         * @param k
         * @param list
         * @param comparator
         * @return 
         * @return
         */
        public List<T> topK(int k, List<T> list, Comparator<T> comparator) {
                PriorityQueue<T> queue = new PriorityQueue<T>(k, comparator);
                Integer count = 0; 
                Integer size = list.size(); 
                while (count < k){
                        queue.offer(list.get(count));
                        count++; 
                }
                while (count < size){
                        T val = list.get(count);
                        T smallest_val = queue.peek(); 
                        if (comparator.compare(val, smallest_val) < 0){
                                list.remove(val); 
                                size--; 
                        }
                        else{
                                queue.remove(smallest_val); 
                                queue.offer(val); 
                                count++; 
                        }
                }
                list.clear();
                while(true){
                        T val = (T)queue.poll(); 
                        if (val == null)
                                return list;
                        list.add(val);
                }
        }

        
        /**
         * @param args
         */
        public static void main(String[] args) {
                List<Integer> list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
                
                Comparator<Integer> comparator = new Comparator<Integer>() {
                        @Override
                        public int compare(Integer n, Integer m) {
                                return n.compareTo(m);
                        }
                };
                
                ListSorter<Integer> sorter = new ListSorter<Integer>();
                sorter.insertionSort(list, comparator);
                System.out.println(list);

                list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
                sorter.mergeSortInPlace(list, comparator);
                System.out.println(list);

                list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
                sorter.heapSort(list, comparator);
                System.out.println(list);
        
                list = new ArrayList<Integer>(Arrays.asList(6, 3, 5, 8, 1, 4, 2, 7));
                List<Integer> queue = sorter.topK(4, list, comparator);
                System.out.println(queue);
        }
}
