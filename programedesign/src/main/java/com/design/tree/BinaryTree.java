package com.design.tree;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * @author yangfei
 * @version 1.0
 * @Description TODO
 * @create 2021-01-07 19:27
 * 二叉树的操作
 * 思考问题应该从一个一个点出发
 * 如树的插入应该考虑插入一个节点如何实现
 */
public class BinaryTree {
    private Node tree;
    private static List<TreeNode> nodeList = null;
    public   void insert(int val) {
        if (tree == null) {
            tree = new Node(val);
            return;
        }
        Node p = tree;

        while (p != null) {
            if (val > p.data) {
                if (p.right == null) {
                    Node nde = new Node(val);
                    p.right = nde;
                }
                p = p.right;

            } else {
                if (p.left == null) {
                    Node nde = new Node(val);
                    p.left = nde;
                }
                p = p.left;
            }
        }
    }

    public Node select(int val) {
        Node p = tree;


        while (p != null) {
            if (p.data < val) {
                p = p.right;
            } else if (p.data > val) {
                p = p.left;
            } else {
                return p;
            }

        }
        return null;
    }


    public static class Node {
        private int data;
        private Node left;
        private Node right;

        public Node(int data) {
            this.data = data;
        }
    }

    public static void main(String[] args){

        //定义数组
        int[] arr=new int[]{2,3,5,7,9};
        BinaryTree binaryTree=new BinaryTree();
        binaryTree.buildTree(arr);

        TreeNode root=nodeList.get(0);

       List<Integer> res= binaryTree.preorderTraversal(root);

       for (int i:res){
           System.out.println(i);
       }


    }


    /**
     * 递归先序遍历
     * @param root
     * @return
     */
    public List<Integer> preorderTraversal(TreeNode root) {

        List<Integer> list=new ArrayList<Integer>();
        preorder(root,list);
        return list;
    }


    private void preorder(TreeNode root, List<Integer> list) {
        if(root==null){
            return;
        }
        list.add(root.val);
        preorder(root.left,list);
        preorder(root.right,list);

    }

    /**
     * 中序遍历
     * 递归实现
     * @param root
     * @return
     */
    public List<Integer> inorderTraversal(TreeNode root){

        List<Integer> list=new ArrayList<Integer>();
        inorder(root,list);
        return list;

    }
    private  void inorder(TreeNode root,List<Integer> res){
        if(root==null){
            return;
        }
        inorder(root.left,res);
        res.add(root.val);
        inorder(root.right,res);
    }

    /**
     * 递归后序遍历
     * @param root
     * @return
     */

    public List<Integer> postorderTraversal(TreeNode root){
        List<Integer> list=new ArrayList<Integer>();
        postorder(root,list);
        return list;

    }

    private void postorder(TreeNode root, List<Integer> res) {
        if(root==null){
            return;
        }
        postorder(root.left,res);
        postorder(root.right,res);
        res.add(root.val);

    }

    /**
     * 迭代后序遍历
     * @param root
     * @return
     */
    public List<Integer> postorderTraversalStack(TreeNode root){
        List<Integer> res = new ArrayList<Integer>();
        if (root == null) {
            return res;
        }

        Deque<TreeNode> stack = new LinkedList<TreeNode>();
        TreeNode prev = null;
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
                System.out.println("stack.left" +root.val);
            }
            root = stack.pop();
            if (root.right == null || root.right == prev) {
                res.add(root.val);
                prev = root;
                root = null;
            } else {
                stack.push(root);
                root = root.right;
            }
        }
        return res;
    }



    /**
     * 迭代实现中序遍历
     * @param root
     * @return
     */
    public List<Integer> inorderTraversalStack(TreeNode root){

        List<Integer> res= new ArrayList<Integer>();
        Deque<TreeNode> stack= new LinkedList<TreeNode>();
        TreeNode node=root;
        while (!stack.isEmpty()||node!=null){
            while (node!=null){
                    stack.push(node);
                    node=node.left;
            }
            node=stack.pop();
            res.add(node.val);

            node=node.right;

        }

        return res;
    }


    /**
     * 迭代实现先序遍历
     * @param root
     * @return
     */
    public List<Integer> preorderTraversalStack(TreeNode root){
        List<Integer> res= new ArrayList<Integer>();
        if(root==null){
            return res;
        }

        Deque<TreeNode> stack= new LinkedList<TreeNode>();
        TreeNode node=root;
        while (!stack.isEmpty()||node!=null){
            while (node!=null){
                res.add(node.val);
                stack.push(node);
                node=node.left;
            }
            node=stack.pop();
            node=node.right;
        }
   return res;
    }

    public  List<List<Integer>> levelOrder(TreeNode root){

        List<List<Integer>> res =new LinkedList<List<Integer>>();
        if(root==null){
            return  res;
        }
        //初始化添加顶点
        List<TreeNode> list=new ArrayList<TreeNode>();
        list.add(root);
        levelorderstack(list,res);
        return  res;
    }


    public void levelorderstack(List<TreeNode> list,List<List<Integer>>  res){
        List<Integer> tmp=new ArrayList<Integer>();
        List<TreeNode> node_tmp=new ArrayList<TreeNode>();
        if(!list.isEmpty()){
            for(TreeNode node:list){
                if(node!=null) {
                    tmp.add(node.val);
                    if(node.left!=null){
                        node_tmp.add(node.left);
                    }
                    if (node.right!=null){
                        node_tmp.add(node.right);
                    }
                    list.remove(node);
                }

            }
        }
        res.add(tmp);
        levelorderstack(node_tmp,res);
    }

    /**
     * 按照数组构建树
     * @param val
     * @param
     * @return
     */

    public void buildTree(int[] val){
        nodeList = new LinkedList<TreeNode>();
        TreeNode node=new TreeNode(val[0]);
        // 将一个数组的值依次转换为Node节点
        for (int nodeIndex = 0; nodeIndex < val.length; nodeIndex++) {
            nodeList.add(new TreeNode(val[nodeIndex]));
        }

        for (int  parentIndex=0;parentIndex<val.length/2 -1 ;parentIndex++){
            //左孩子
            nodeList.get(parentIndex).left=nodeList.get(parentIndex*2+1);
            //右孩子
            nodeList.get(parentIndex).right=nodeList.get(parentIndex*2+2);
        }
        //最后一个父节点，因为最后一个父节点可能没有右孩子，所以单独拿出来处理
        int lastParentIndex= val.length/2-1;
        //左孩子
        nodeList.get(lastParentIndex).left=nodeList.get(lastParentIndex*2+1);

        //右孩子，如果数组的长度为奇数才建立右孩子
        if(val.length%2==1){
            nodeList.get(lastParentIndex).right=nodeList.get(lastParentIndex*2+2);
        }
    }
}
