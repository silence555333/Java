package com.design.tree;

import java.util.*;

/**
 * @author yangfei
 * @create 2020-07-06 14:57
 */
public class TreeUtil {
    /**
     * 解题思路：查找中间的位置，然后进行平衡
     *
     * @param nums
     * @return
     */
    public TreeNode sortedArrayToBST(int[] nums) {
        //当数组的大小刚好为2n+1

        // 左右等分建立左右子树，中间节点作为子树根节点，递归该过程
        return nums == null ? null : buildTree(nums, 0, nums.length - 1);

    }

    private TreeNode buildTree(int[] nums, int l, int r) {
        if (l > r) {
            return null;
        }
        int m = l + (r - l) / 2;
        TreeNode root = new TreeNode(nums[m]);
        root.left = buildTree(nums, l, m - 1);
        root.right = buildTree(nums, m + 1, r);
        return root;
    }


    /**
     * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为“Start” ）。
     * <p>
     * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为“Finish”）。
     * <p>
     * 现在考虑网格中有障碍物。那么从左上角到右下角将会有多少条不同的路径？
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/unique-paths-ii
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * 网格中的障碍物和空位置分别用 1 和 0 来表示。
     * <p>
     * 说明：m 和 n 的值均不超过 100。
     * <p>
     * 示例 1:
     * <p>
     * 输入:
     * [
     *   [0,0,0],
     *   [0,1,0],
     *   [0,0,0]
     * ]
     * 输出: 2
     * 解释:
     * 3x3 网格的正中间有一个障碍物。
     * 从左上角到右下角一共有 2 条不同的路径：
     * 1. 向右 -> 向右 -> 向下 -> 向下
     * 2. 向下 -> 向下 -> 向右 -> 向右
     * <p>
     * <p>
     * 解题思路：
     * 1 使用二叉树 向下为左子树 标识为0 向右为右子树 标识为1
     * 2 遍历获取链路路径，使用深度优先
     *
     * 使用我的方法 ，超出内存
     *
     * @param obstacleGrid
     * @return
     */

    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int result = 0;
        Map<String, List> pathMap = new HashMap();//记录全部从根节点到叶子结点的路径
        //构建树
        TreeNode node = buildTree(obstacleGrid, 0, 0);
        //遍历获取所有路径
        if (node != null) {
            List<String> list = binaryTreePaths(node);
            if (list.size() > 0) {
                for (String l : list) {
                    System.out.println(l);
                    //添加判断就是开头结尾，需要根据传入的数组的长短
                    //需要特殊考虑 只有1个元素和 2个
                    // 只有最终对角为0时进行计算
                    if (l.contains("1")) {

                    } else {
                        if (obstacleGrid[obstacleGrid.length - 1][obstacleGrid[0].length - 1] == 0) {
                            if (l.contains("0") && l.split("->").length >= (obstacleGrid.length - 1 + obstacleGrid[0].length - 1) + 1) {
                                result = result + 1;
                            }
                        }
                    }


                }
            }
        }


        return result;


    }

    /**
     * 根据矩阵拼接二叉树
     * x表示横向的
     * y表示纵向的
     * 超出内存,加一个规则为1的过滤
     *
     * @param nums
     * @param x
     * @param y
     * @return
     */
    private TreeNode buildTree(int[][] nums, int x, int y) {
        //判断l,r 是否超出矩阵的长度
        if (x > nums.length - 1 || y > nums[x].length - 1 || nums[x][y] == 1) {
            return null;
        }
        TreeNode root = new TreeNode(nums[x][y]);
        root.left = buildTree(nums, x, y + 1);
        root.right = buildTree(nums, x + 1, y);
        return root;
    }

    /**
     * 递归调用，获取一棵二叉树的所有路径
     *
     * @param root
     * @return
     */
    private List<String> binaryTreePaths(TreeNode root) {
        if (null == root) {
            return new ArrayList<String>();
        }
        if (null == root.left && null == root.right) {
            List<String> nodeList = new ArrayList<String>();
            nodeList.add(String.valueOf(root.val));
            return nodeList;

        }
        List<String> results = new ArrayList<String>();
        List<String> leftPaths = binaryTreePaths(root.left);
        List<String> rightPaths = binaryTreePaths(root.right);
        if (null != leftPaths && leftPaths.size() > 0) {
            for (String lp : leftPaths) {
                results.add(root.val + "->" + lp);
            }
        }
        if (null != rightPaths && rightPaths.size() > 0) {
            for (String rp : rightPaths) {
                results.add(root.val + "->" + rp);
            }
        }

        return results;
    }
    public void inOrder(TreeNode Root) {
        if(Root==null) {
            System.out.println("空树");
            return;
        }
        TreeNode tmp=Root;
        Stack<TreeNode> s=new Stack<TreeNode>();
        while(tmp!=null || !s.empty()) {
            //1.将根节点入栈
            //2.将所有左孩子入栈
            while(tmp!=null) {
                s.push(tmp);
                tmp=tmp.left;
            }
            //3.访问栈顶元素
            tmp=s.pop();
            System.out.print(tmp.val+" ");
            //4.如果栈顶元素存在右孩子，则将右孩子赋值给tmp，也就是将右孩子入栈
            if(tmp.right!=null) {
                tmp=tmp.right;
            }
            //否则，将tmp置为null，表示下次要访问的是栈顶元素
            else {
                tmp=null;
            }
        }
        System.out.println();
    }


}
