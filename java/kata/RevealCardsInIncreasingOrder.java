package kata;

import static kata.Printer.*;
import java.util.*;

/**
 * In a deck of cards, every card has a unique integer.  You can order the deck
 * in any order you want.
 *
 * Initially, all the cards start face down (unrevealed) in one deck.
 * Now, you do the following steps repeatedly, until all cards are revealed:
 *
 * 1. Take the top card of the deck, reveal it, and take it out of the deck.
 * 2. If there are still cards in the deck, put the next top card of the deck
 * at the bottom of the deck.
 * 3. If there are still unrevealed cards, go back to step 1.  Otherwise, stop.
 *
 * Return an ordering of the deck that would reveal the cards in increasing order.
 *
 * The first entry in the answer is considered to be the top of the deck.
 *
 * <p>https://leetcode.com/problems/reveal-cards-in-increasing-order/
 */
public class RevealCardsInIncreasingOrder {
  static int[] revealCardsInIncreasingOrder(int[] cards) {
    Arrays.sort(cards);
    int[] ans = new int[cards.length];

    Deque<Integer> indexes = new LinkedList<>();
    for (int i = 0; i < cards.length; i++) {
      indexes.addLast(i);
    }

    int i = 0;
    while (!indexes.isEmpty()) {
      ans[indexes.removeFirst()] = cards[i++];
      if (!indexes.isEmpty()) {
        indexes.addLast(indexes.removeFirst());
      }
    }
    return ans;
  }

  public static void main(String args[]) {
    runSample(new int[]{17,13,11,2,3,5,7}, new int[]{2,13,3,11,5,17,7});
  }

  static void runSample(int[] cards, int[] ans) {
    int[] ret = revealCardsInIncreasingOrder(cards);
    System.out.printf(
      "%s = %s(%s)\n",
      Arrays.toString(cards),
      Arrays.toString(ret),
      Arrays.toString(ans));
    System.out.println("Deal the card to verified:");
    Deque<Integer> orderedCards = new LinkedList<Integer>();
    for (int card : ret) {
      orderedCards.addLast(card);
    }
    while (!orderedCards.isEmpty()) {
      System.out.print(orderedCards.removeFirst() + ", ");
      if (!orderedCards.isEmpty()) {
        orderedCards.addLast(orderedCards.removeFirst());
      }
    }
    System.out.println();
  }
}
