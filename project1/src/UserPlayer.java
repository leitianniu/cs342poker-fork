import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class UserPlayer{
	
	static int top_of_deck = 0;
	static boolean extra_discard = false;

	public static void initiate_hand(List<Card> player_hand, CardPile deck){
		for(int i = 0; i < 5; i++){
			player_hand.add(deck.drawCard());
			//player_hand.get(i).printCard();
			top_of_deck++;
		}
	}
	public static void print_phand(List<Card> player_hand){
		for (int i = 0; i < player_hand.size(); i++) {
			System.out.print(" " +  (i+1) + ")");
			if(player_hand.get(i).getRank() == 'A')
				extra_discard = true;
			player_hand.get(i).printCard();
		}

		if(extra_discard == true){
			System.out.println("\nSince you have an Ace you can keep the Ace and " +
			                    "discard the other four cards");
		}
	}
	public static void discard_draw(List<Card> player_hand, CardPile deck, CardPile discardpile){
		int numToDiscard = -1;
		int[] cardsToDiscard = new int[4];
		
		Scanner in = new Scanner(System.in);
		Pattern delimiters = Pattern.compile(System.getProperty("line.separator")+"|\\s");
		in.useDelimiter(delimiters); 
		System.out.println("List the cards numbers you wish to discard. > ");
		System.out.println("(enter each card number followed by a space, or hit enter twice)");
		while(in.hasNextInt()){
			numToDiscard++;
			cardsToDiscard[numToDiscard] = in.nextInt();
			player_hand.get(cardsToDiscard[numToDiscard] - 1).printCard();
			// remove from hand
			//player_hand.remove(cardsToDiscard[numToDiscard] - 1);
			discardpile.insert_card(player_hand.remove(cardsToDiscard[numToDiscard] - 1));
		}
		System.out.print("are discarded\n");
		// draw cards to fill hand
		for(int i=0;i<numToDiscard;i++){
			player_hand.add(deck.drawCard());
			top_of_deck++;
		}
		// print out player hand again
		print_phand(player_hand);
	}
}