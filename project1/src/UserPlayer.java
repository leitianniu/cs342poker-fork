import java.io.*;
import java.util.*;

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
}