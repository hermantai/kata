package kata;

import java.util.*;

/**
 * Given a bunch of meetings (person 1, person 2, timestamp), and we know a list of people who know
 * a story after the meetings, find out the people who may already know the
 * story before the meetings.
 *
 * Solution:
 * 1) Figure out all people who do not know after the meetings.
 * 2) Draw dependency * graphs by iterating all meetings in reverse timestamp.
 * 3) Find all people * who cannot know the stories at the beginning by doing DFS on all graphs
 * starting from people who do not know the story after the meetings.
 * 4) Return all people who may know the story before the meetings.
 */
public class GossipSpread {

  public static List<Integer> findOriginalGossiper(Meeting[] meetings, int[] peopleWhoKnow) {
    Set<Integer> peopleWhoKnowSet = new HashSet<>();
    List<Meeting> meetingsList = Arrays.asList(meetings);

    Collections.reverse(meetingsList);
    for (int p : peopleWhoKnow) {
      peopleWhoKnowSet.add(p);
    }

    Map<Integer, List<Node>> personsGraphs = new HashMap<>();

    for (Meeting meeting: meetingsList) {
      // newGraphs are used for updating personsGraphs. We can only
      // update personsGraphs after we have use it to retrieve graphs
      // for each person to create newGraphs, otherwise we will have
      // infinite recursion.
      List<Node> newGraphs = new ArrayList<>();

      updatePerson(meeting.person1, meeting.person2, personsGraphs, newGraphs);
      updatePerson(meeting.person2, meeting.person1, personsGraphs, newGraphs);

      for (Node graph: newGraphs) {
        personsGraphs.get(graph.person).add(graph);
      }
      // System.out.println(String.format("after processing meeting: %s -> %s", meeting.person1, meeting.person2));
      // printAllGraphs(personsGraphs);
    }

    Set<Integer> peopleWhoDoNotKnow = new HashSet<>();
    for (int person : personsGraphs.keySet()) {
      if (!peopleWhoKnowSet.contains(person)) {
        peopleWhoDoNotKnow.add(person);
      }
    }

    // System.out.println("people who do not know: " + peopleWhoDoNotKnow);
    // find all "clean" people
    Set<Integer> cleanPeople = new HashSet<>();
    for (int person : peopleWhoDoNotKnow) {
      for (Node node : personsGraphs.get(person)) {
        addCleanPeople(node, peopleWhoDoNotKnow, cleanPeople);
      }
    }
    cleanPeople.addAll(peopleWhoDoNotKnow);

    peopleWhoKnowSet.removeAll(cleanPeople);

    // System.out.println("print all graphs:");
    // printAllGraphs(personsGraphs);
    

    return new ArrayList<>(peopleWhoKnowSet);
  }

  public static void updatePerson(int fromPerson, int toPerson, Map<Integer, List<Node>> personsGraphs, List<Node> newGraphs) {
      List<Node> fromPersonGraphs = personsGraphs.get(fromPerson);
      if (fromPersonGraphs == null) {
        fromPersonGraphs = new ArrayList<>();
        Node newGraph = new Node(fromPerson);
        fromPersonGraphs.add(newGraph);

        personsGraphs.put(fromPerson, fromPersonGraphs);
      }

      for (Node graph : fromPersonGraphs) {
        if (!graph.children.containsKey(toPerson)) {
          Node newToPersonGraph = new Node(toPerson);
          graph.children.put(toPerson, newToPersonGraph);

          newGraphs.add(newToPersonGraph);
        }
      }
  }

  public static void addCleanPeople(Node node, Set<Integer> peopleWhoDoNotKnow, Set<Integer> cleanPeople) {
    for (Map.Entry<Integer, Node> entry: node.children.entrySet()) {
      if (!peopleWhoDoNotKnow.contains(entry.getKey())) {
        cleanPeople.add(entry.getKey());
        // System.out.println("updatecleanpeople: node: " + node.person + " child: " + entry.getKey());
        addCleanPeople(entry.getValue(), peopleWhoDoNotKnow, cleanPeople);
      }
    }
  }

  public static void printAllGraphs(Map<Integer, List<Node>> personsGraphs) {
    for (Map.Entry<Integer, List<Node>> entry: personsGraphs.entrySet()) {
      System.out.println("graphs for: " + entry.getKey());
      for (Node node : entry.getValue()) {
        printGraph(node);
      }
    }
  }

  public static void printGraph(Node node) {
    printGraphHelper(node, /* leadingStr= */ "");
  }

  public static void printGraphHelper(Node node, String leadingStr) {
    System.out.println(leadingStr + node.person);
    for (Node child: node.children.values()) {
      printGraphHelper(child, leadingStr + "  ");
    }
  }

  public static void main(String args[]) {
    Meeting meeting1 = new Meeting(1,2,100);
    Meeting meeting2 = new Meeting(3,4,200);
    Meeting meeting3 = new Meeting(1,3,300);
    Meeting meeting4 = new Meeting(2,5,400);

    // answer: 1, 2, 5
    System.out.println(findOriginalGossiper(new Meeting[]{meeting1, meeting2, meeting3, meeting4}, new int[]{1, 2, 3, 5})); // source is 1

    // answer: 3, 4
    System.out.println(findOriginalGossiper(new Meeting[]{meeting1}, new int[]{1, 3, 4})); // source is 3

    meeting1 = new Meeting(1,2,100);
    meeting2 = new Meeting(2,3,200);

    // answer: 3
    System.out.println(findOriginalGossiper(new Meeting[]{meeting1, meeting2}, new int[]{2, 3}));

    // answer: {}
    System.out.println(findOriginalGossiper(new Meeting[]{meeting1, meeting2}, new int[]{}));
  }

  static class Meeting {
    int person1;
    int person2;
    int time;

    Meeting(int person1, int person2, int time) {
      this.person1 = person1;
      this.person2 = person2;
      this.time = time;
    }
  }

  static class Node {
    int person;
    Map<Integer, Node> children = new HashMap<>();

    Node(int person) {
      this.person = person;
    }
  }
}
