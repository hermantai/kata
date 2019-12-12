package kata;

import static kata.Printer.*;
import java.util.*;

/**
 * Given a char array representing tasks CPU need to do. It contains capital
 * letters A to Z where different letters represent different tasks. Tasks
 * could be done without original order. Each task could be done in one
 * interval. For each interval, CPU could finish one task or just be idle.

 * However, there is a non-negative cooling interval n that means between two
 * same tasks, there must be at least n intervals that CPU are doing different
 * tasks or just be idle.

 * You need to return the least number of intervals the CPU will take to finish
 * all the given tasks.
 *
 * <p>https://leetcode.com/problems/task-scheduler
 */
public class TaskScheduler {
  static int taskScheduler(char[] tasks, int cooldown) {
    Map<Character, Integer> numOfTasks = new HashMap<>();
    for (char task : tasks) {
      numOfTasks.put(task, numOfTasks.getOrDefault(task, 0) + 1);
    }

    PriorityQueue<Integer> remainingTasks = new PriorityQueue<>(Collections.reverseOrder());
    for (int times : numOfTasks.values()) {
      // It does not matter the task's name, because we just need to care
      // how many times a task has to be executed and how many different
      // tasks.
      remainingTasks.offer(times);
    }

    int clock = 0;
    while (!remainingTasks.isEmpty()) {
      List<Integer> tasksAfterCooldown = new ArrayList<>();
      int i = 0;
      while (i <= cooldown) {
        if (!remainingTasks.isEmpty()) {
          if (remainingTasks.peek() > 1) {
            // need to execute again if more than once
            tasksAfterCooldown.add(remainingTasks.poll() - 1);
          } else {
            remainingTasks.poll();
          }
        }
        clock++;
        if (remainingTasks.isEmpty() && tasksAfterCooldown.isEmpty()) {
          // Cannot exit unless tasksAfterCooldown is empty because
          // we have to wait for the cooldown to complete before
          // putting the tasks back to the queue for execution.
          break;
        }
        i++;
      }
      for (int t : tasksAfterCooldown) {
        remainingTasks.offer(t);
      }
    }
    return clock;
  }

  public static void main(String args[]) {
    runSample(new char[]{'A','A','A','B','B','B'}, 2, 8);
  }

  static void runSample(char[] tasks, int cooldown, int ans) {
    System.out.printf(
      "%s,%s = %s(%s)\n",
      Arrays.toString(tasks),
      cooldown,
      taskScheduler(tasks, cooldown),
      ans);
  }
}
