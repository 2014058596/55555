package cn.com.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @ClassName: HuffmanTree
 * @Description: TODO
 * @author: 55555
 * @date: 2020年06月15日 10:47 下午
 */
public class HuffmanTree {

    List<Node<String>> list = new ArrayList<>();





    public static List<Node<String>> quickSort(List<Node<String>> list, int start, int end){
        if(start < end){
            int i = start;
            int j = end + 1;
            final Node<String> stringNode = list.get(i);

            while (true){
                while (i < end && stringNode.getWight() <= list.get(++i).getWight());

                while (j > start && stringNode.getWight() >= list.get(--j).getWight());

                if(i < j){
                    swap(list, i, j);
                }else {
                    break;
                }
            }
            swap(list, start, j);
            System.out.println("i :" + i + ", j:" + j);
            quickSort(list, start, j - 1);
            quickSort(list, j + 1 , end );

        }
        return list;
    }




    /**
     * 将指定集合中的i和j索引处的元素交换
     *
     * @param nodes
     * @param i
     * @param j
     */
    private static <E> boolean swap(List<Node<E>> nodes, int i, int j) {
        Node tmp;
        tmp = nodes.get(i);
        nodes.set(i, nodes.get(j));
        nodes.set(j, tmp);
        return true;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    static class Node<E>{
        E data;
        Node<E> left;
        Node<E> right;
        int wight;
    }


    public static void main(String[] args) {
        final List<Node<String>> collect = IntStream.rangeClosed(0, 10).mapToObj(x -> x + "").map(x -> {
            Node<String> node = new Node<>();
            node.setData(x);
            node.setWight(ThreadLocalRandom.current().nextInt(100));
            return node;
        }).collect(Collectors.toList());

        System.out.println(collect);
        System.out.println(quickSort(collect, 0, collect.size() - 1));
    }



}
