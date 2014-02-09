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
	
	public static void ai_discard_draw(List<Card> ai_hand, CardPile deck, CardPile discardpile, String ai_discard){
		int numToDiscard = 0;
		int[] cardsToDiscard = new int[4];

		Scanner in = new Scanner(ai_discard);
		Pattern delimiters = Pattern.compile(System.getProperty("line.separator")+"|\\s");
		in.useDelimiter(delimiters);	
//		System.out.println("List the cards numbers you wish to discard. > ");
//		System.out.println("(enter each card number followed by a space, or hit enter twice)");
		
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
		System.out.print("\nAfter discarding and drawing cards, AI's hands: ");
		for(int i = 0; i < 5; i++){
			ai_hand.get(i).reset_Matching();
		}
		handEvaluator.hand_pairing(ai_hand);
		print_aihand(ai_hand);
	}
	
	public static void hand_pairing(List<Card> hand){
		for(int i = 0; i < 5; i++){
			for(int j = i+1; j < 5; j++){
				if(hand.get(i).getValue() == hand.get(j).getValue()){
					hand.get(i).incr_matching();
					hand.get(j).incr_matching();
				}
			}
		}

	}

	public static int ai_hand_handler(List<Card> hand, CardPile deck, CardPile discardpile){
		int match_total = 0;

		//adds the the hands match pairings.
		//then the total will help evaluate the hand
		//special cases are needed for flush, straight, straight flush
		//and high card.

		for(int i = 0; i < 5; i++){
			match_total += hand.get(i).getMatching();
		}

		//System.out.println("total: " + match_total);
		switch(match_total){
			case 0:
				return special_hands(hand, deck, discardpile);
			case 2:
				System.out.println("AI has one pair");
				System.out.print("\nAI discarded: ");
				ai_discard_draw(hand, deck, discardpile, "3 4 5");
				System.out.print("\n");
				return match_total;
			case 4:
				System.out.println("AI has two pair");
				System.out.print("\nAI discarded: ");
				ai_discard_draw(hand, deck, discardpile, "5");
				System.out.print("\n");
				return match_total;
			case 6:
				System.out.println("AI has three of a kind");
				System.out.print("\nAI discarded: ");
				ai_discard_draw(hand, deck, discardpile, "4 5");
				System.out.print("\n");
				return match_total;
			case 8:
				System.out.println("AI has a full house");
				// No discarding occurs when AI has full house
				//returning 9 because three of a king < straight < flush < full house
				//							6				7		8			9
				return match_total+1;
			case 12:
				System.out.println("AI has four of a kind");
				// just a little modification to the algorithm, if AI has Four of A Kind AND and Ace, no discard occurs
				if(!hand.get(5).equals('A')){
					System.out.print("\nAI discarded: ");
					ai_discard_draw(hand, deck, discardpile, "5");
					System.out.print("\n");
				}
				return match_total;
			default:
				return match_total;
		}

	}
	// No discarding occurs when AI has one of the special hands
	private static int special_hands(List<Card> hand, CardPile deck, CardPile discardpile){
		//check for straight:
		boolean straight = true, flush = true;

		//straight check:
		for(int i = 0; i < 4; i++){
			if(hand.get(i).getValue() != (hand.get(i+1).getValue() + 1))
				straight = false;
		}

		//flush check:
		for(int i = 0; i < 4; i++){
			if(hand.get(i).getSuit() != (hand.get(i+1).getSuit()))
				flush = false;
		}

		if(straight && flush){
			System.out.println("AI has a straight flush");
			return 13;
		}
		else if(flush){
			System.out.println("AI has a flush");
			return 8;
		}
		else if(straight){
			System.out.println("AI has a straight");
			return 7;
		}
		else{
			System.out.print("AI's highest card is: ");
			hand.get(0).printCard();
			System.out.print("\nAI discarded the lowest three cards:");
			ai_discard_draw(hand, deck, discardpile, "3 4 5");
			System.out.print("\n");
			System.out.println("");
			return 0;
		}	
	}
	
	// end of class
}