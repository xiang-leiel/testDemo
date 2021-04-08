package com.example.test.algorithm.leeCode.graph;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/is-graph-bipartite/
 * @Description 
 * @author leiel
 * @Date 2020/7/16 2:12 PM
 */
public class _判断二分图 {

    /**
     * 示例 1:
     * 输入: [[1,3], [0,2], [1,3], [0,2]]
     * 输出: true
     * 解释:
     * 无向图如下:
     * 0----1
     * |    |
     * |    |
     * 3----2
     * 我们可以将节点分成两组: {0, 2} 和 {1, 3}。
     * @param graph
     * @return
     */

    public boolean isBipartite(int[][] graph) {
        // 定义 visited 数组，初始值为 0 表示未被访问，赋值为 1 或者 -1 表示两种不同的颜色。
        int[] visited = new int[graph.length];
        // 因为图中可能含有多个连通域，所以我们需要判断是否存在顶点未被访问，若存在则从它开始再进行一轮 dfs 染色。
        for (int i = 0; i < graph.length; i++) {
            if (visited[i] == 0 && !dfs(graph, i, 1, visited)) {
                return false;
            }
        }
        return true;
    }

    private boolean dfs(int[][] graph, int v, int color, int[] visited) {
        // 如果要对某顶点染色时，发现它已经被染色了，则判断它的颜色是否与本次要染的颜色相同，如果矛盾，说明此无向图无法被正确染色，返回 false。
        if (visited[v] != 0) {
            return visited[v] == color;
        }

        // 对当前顶点进行染色，并将当前顶点的所有邻接点染成相反的颜色。
        visited[v] = color;
        for (int w: graph[v]) {
            if (!dfs(graph, w, -color, visited)) {
                return false;
            }
        }
        return true;
    }

}
