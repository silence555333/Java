package com.design.tree;

/**
 * @author yangfei
 * @version 1.0
 * @Description TODO
 * @create 2021-01-11 11:33
 * 二叉搜索树
 */
public class BinarySearchTree {

//    public TreeNode convertBiNode(TreeNode root) {
//        //遍历二叉树
//
//
//
//
//    }

    /**
     * 这里应该为中序遍历
     * @param root
     * @return
     */
    public TreeNode inOrder(TreeNode root){
        TreeNode node=root;
        if(root==null){
            return null;
        }

        if(root.left==null){
            if(root.right!=null){
                return inOrder(root.right);
            }else {
                return node;
            }
        }else if(root.left!=null){
            return inOrder(root.left);
        }else{
            return null;
        }
    }
}
