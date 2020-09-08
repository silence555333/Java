package com.design.dynamic;

import java.util.HashMap;

/**
 * @author yangfei
 * @create 2020-07-06 19:09
 * 使用动态规划解决问题
 */
public class Dynamic {
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
     * 当前节点的路径值等于可以到他的路径之和
     * 如 [i,j] = [i,j-1] + [i-1,j]
     *
     * @param obstacleGrid
     * @return
     */
    public int uniquePathsWithObstaclesByMyself(int[][] obstacleGrid) {
        HashMap<String, Integer> getValue = new HashMap<String, Integer>();
        //初始化数值
        getValue.put("0,0", 1);
        int result = 0;
        for (int i = 0; i <= obstacleGrid.length - 1; i++) {
            for (int j = 0; j <= obstacleGrid[0].length - 1; j++) {
                if (i == 0 && j == 0) {

                } else {
//                [i,j] = [i,j-1] + [i-1,j]
                    String last_right_key = i + "," + (j - 1);
                    int last_right = getValue.get(last_right_key) == null ? 0 : getValue.get(last_right_key);
                    String last_up_key = (i - 1) + "," + j;
                    int last_up = getValue.get(last_up_key) == null ? 0 : getValue.get(last_up_key);

                    int current_node = 0;
                    if (obstacleGrid[i][j] == 0) {
                        current_node = last_right + last_up;
                    }

                    getValue.put(i + "," + j, current_node);

                }
            }
        }
        result = getValue.get((obstacleGrid.length - 1) + "," + (obstacleGrid[0].length - 1)) == null ? 0 : getValue.get((obstacleGrid.length - 1) + "," + (obstacleGrid[0].length - 1));

        return result;
    }
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int n = obstacleGrid.length, m = obstacleGrid[0].length;
        int[] f = new int[m];

        f[0] = obstacleGrid[0][0] == 0 ? 1 : 0;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                if (obstacleGrid[i][j] == 1) {
                    f[j] = 0;
                    continue;
                }
                if (j - 1 >= 0 && obstacleGrid[i][j - 1] == 0) {
                    f[j] += f[j - 1];
                }
            }
        }

        return f[m - 1];
    }

}
