import java.io.*;
import java.util.*;

public class AIPlayer{
	
	static int top_of_deck = 0;
	static boolean extra_discard = false;
	public static void initiate_hand(List<Card> ai_hand, CardPile deck){
			for(int i = 0; i < 5; i++){
				ai_hand.add(deck.drawCard());
				//ai_hand.get(i).printCard();
				top_of_deck++;
		}
	}
	public static void print_aihand(List<Card> ai_hand){
		Card current_card;
		
		for (int i = 0; i < ai_hand.size(); i++) {
			System.out.print(" " +  (i+1) + ")");
			current_card = ai_hand.get(i);
			current_card.printCard();
			
			//int x = current_card.getValue();
			System.out.println("(the value of card" + (i+1) + " is: " + current_card.getValue() + ")");
		}
	}
	
	public static void checkpairs(List<Card> ai_hand){
		Card current_card;
		int[] appearance = new int[13]; // 13 ranks
		
		for (int i = 0; i < ai_hand.size(); i++) {
			current_card = ai_hand.get(i);
			appearance[(current_card.getValue() - 1)] += 1; // As defined in Card class, 2 = 1, Ace = 13
		}
		
		int numOfPairs = 0;
		boolean hasThree = false;
		boolean hasFour = false;
		
		for (int a = 0; a < appearance.length; a++ ){
			switch (appearance[a]){
			case 2:
				numOfPairs++;
				System.out.println("AI has a Pair");
				break;
			case 3:
				hasThree = true;
				System.out.println("AI has Three of A Kind");
				break;
			case 4:
				hasFour = true;
				System.out.println("AI has Four of A Kind");
				break;
			default:
				break;
			}		
		}
		if (numOfPairs == 1 && hasThree){
		System.out.println("AI has a Full House");
		}
		
	}
	
	
	
	
	
	// end of class
}