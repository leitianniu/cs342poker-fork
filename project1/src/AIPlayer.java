import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

import org.omg.CORBA.PUBLIC_MEMBER;

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
			//System.out.println("(the value of card" + (i+1) + " is: " + current_card.getValue() + ")");
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
				// discard the 1 card if it's not an Ace
				
				break;
			default:
				break;
			}		
		}
		if (numOfPairs == 1 && hasThree){
		System.out.println("AI has a Full House");
		}
		
	}
	
	public static void ai_discard_draw(List<Card> ai_hand, CardPile deck, CardPile discardpile){
		int numToDiscard = 0;
		int[] cardsToDiscard = new int[4];

		Scanner in = new Scanner(System.in);
		Pattern delimiters = Pattern.compile(System.getProperty("line.separator")+"|\\s");
		in.useDelimiter(delimiters);	
		System.out.println("List the cards numbers you wish to discard. > ");
		System.out.println("(enter each card number followed by a space, or hit enter twice)");
		
		int debug_value = 0;
		int index_check = 1;
		while(in.hasNextInt()){
			numToDiscard++;
			cardsToDiscard[numToDiscard-1] = in.nextInt();
			debug_value = cardsToDiscard[numToDiscard-1] - index_check;
			ai_hand.get(debug_value).printCard();
			// remove from hand
			//ai_hand.remove(cardsToDiscard[numToDiscard] - 1);
			discardpile.insert_card(ai_hand.remove(debug_value));
			index_check++;
		}
		// draw cards to fill hand

		for(int i=0;i<numToDiscard;i++){
			ai_hand.add(deck.drawCard());
			top_of_deck++;
		}
		//reset players hand and sorts it again:
		for(int i = 0; i < 5; i++){
			ai_hand.get(i).reset_Matching();
		}
		handEvaluator.hand_pairing(ai_hand);
		print_aihand(ai_hand);
	}
	
	// end of class
}