package kata;

import java.util.*;

/**
 * Longest path in graph is n-p hard, but longest path in a DAG has a linear
 * solution. https://www.geeksforgeeks.org/find-longest-path-directed-acyclic-graph/
 *
 * <ul>
 * <li>Topological sort the vertices.
 * <li>initialize dist[] = {-inf, -inf...} for all vertices.
 * <li>For source, initialize dist with 0.
 * <li>Process each vertex one by one and update dist[].
 * <li>Find the highest number in dist.
 * </ul>
 */
public class LongestPathInDag {

  static class Edge {
    int dst;
    int weight;
		public String toString() {
      return String.format("Edge(dst=%s, weight=%s)", dst, weight);
    }
  }

  static Result longestPathInDag(int n, int[][] edges) {
    // Each edge is {i, j, w} meaning it takes w to go from i to j
    Result result = new Result();
    if (n == 0) {
      result.path = new int[0];
      return result;
    }

    int[] dist = new int[n];
    int[] backtrack = new int[n];

    Map<Integer, List<Edge>> neighbors = new HashMap<>();
    Set<Integer> notSources = new HashSet<>();
    for (int i = 0; i < edges.length; i++) {
      int[] edge = edges[i];
      int u = edge[0];
      Edge e = new Edge();
      e.dst = edge[1];
      e.weight = edge[2];
      notSources.add(e.dst);

      List<Edge> uedges = neighbors.get(u);
      if (uedges == null) {
        uedges = new ArrayList<>();
        neighbors.put(u, uedges);
      }
      uedges.add(e);
    }

    for (int i = 0; i < n; i++) {
      if (notSources.contains(i)) {
        dist[i] = Integer.MIN_VALUE;
      } else {
        dist[i] = 0;
      }
      backtrack[i] = -1;
    }
    List<Integer> sorted = getSortedVertexes(n, neighbors);
    System.out.println("Sorted: " + sorted);

    // update dist[v] if dist[u] + e.weight > dist[v]
    for (Integer u : sorted) {
      List<Edge> uedges = neighbors.get(u);
      if (uedges != null) {
        for (Edge e : uedges) {
          int fromU = dist[u] + e.weight;
          if (fromU > dist[e.dst]) {
            dist[e.dst] = fromU;
            backtrack[e.dst] = u;
          }
        }
      }
    }

    // skip the check of cyclic graph. We can do that by doing
    // DFS, then check if we visit a node from the stack.
    
    int end = -1;
    for (int i = 0; i < n; i++) {
      if (dist[i] > result.longest) {
        result.longest = dist[i];
        end = i;
      }
    }

    List<Integer> reversedPath = new ArrayList<>();
    if (end != -1) {
      while (backtrack[end] != -1) {
        reversedPath.add(end);
        end = backtrack[end];
      }
      // the source of the path has to be added. It has -1 in backtrack.
      reversedPath.add(end);
    }

    Collections.reverse(reversedPath);
    result.path = reversedPath.stream().mapToInt(i -> i).toArray();

    return result;
  }

  // Topologically sorting the vertexes
  static List<Integer> getSortedVertexes(int n, Map<Integer, List<Edge>> edges) {
    int []indegrees = new int[n];

    for (List<Edge> uedges : edges.values()) {
      for (Edge e : uedges) {
        indegrees[e.dst]++;
      }
    }

    List<Integer> sources = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      if (indegrees[i] == 0) {
        sources.add(i);
      }
    }

    List<Integer> sorted = new ArrayList<>();
    while (!sources.isEmpty()) {
      int u = sources.remove(0);
      sorted.add(u);
      
      List<Edge> uedges = edges.get(u);
      if (uedges != null) {
        for (Edge e : uedges) {
          indegrees[e.dst]--;
          if (indegrees[e.dst] == 0) {
            sources.add(e.dst);
          }
        }
      }
    }

    return sorted;
  }

  public static void main(String args[]) {
    runSample(6, new int[][]{
      {0, 1, 5},
      {0, 2, 3},
      {1, 3, 6},
      {1, 2, 2},
      {2, 4, 4},
      {2, 5, 2},
      {2, 3, 7},
      {3, 5, 1},
      {3, 4, -1},
      {4, 5, -2},
    });
    //               1
    //        /      |     \
    //       2 -->   6  -->   3
    //       |
    //       4
    //       |
    //       5
    runSample(6, new int[][]{
      {0, 1, 1},
      {0, 5, 1},
      {0, 2, 1},
      {1, 3, 1},
      {3, 4, 1},
      {1, 5, 1},
      {5, 2, 1},
    });
  }

  static void runSample(int n, int[][] edges) {
    System.out.printf("%s,%s = %s\n", n, multiArrayToString(edges), longestPathInDag(n, edges));
  }

  public static String multiArrayToString(int[][] m) {
    StringBuilder sb = new StringBuilder();
    boolean isFirst = true;
    for (int[] row : m) {
      if (isFirst) {
        isFirst = false;
      } else {
        sb.append(", ");
      }
      sb.append(Arrays.toString(row));
    }
    return sb.toString();
  }

  static class Result {
    int longest = 0;
    int[] path;

    public String toString() {
      return String.format("Result(longest=%s, path=%s)", longest, Arrays.toString(path));
    }
  }
}
