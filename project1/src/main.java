//import Card.java;
import java.io.*;
import java.util.*;


public class main{
	public static void main(String[] args){
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Hello welcome to 5 Card Poker.");
		System.out.println("How many computer opponents do you want to face (0-3 only)? ");
		/*int opponents = Integer.parseInt(scanner.nextLine());

		while(opponents < 0 || opponents >= 4){
			System.out.println("Value out of bounds please enter a new value: ");
			opponents = Integer.parseInt(scanner.nextLine());
		}*/

		System.out.println("The deck is being shuffled...");
		CardPile discardpile = new CardPile();
		CardPile deck = new CardPile(true);
		

		//deck.PilePrint();


		/*
		List<Card> player_hand = new ArrayList<>();

		int top_of_deck = 0;

		System.out.println("Player's hand: \n");
		for(int i = 0; i < 5; i++){
			player_hand.add(deck.drawCard(top_of_deck));
			player_hand.get(i).getCard();
			top_of_deck++;
		}*/

		//discardpile.insert_card(new Card('T', 'T'));
		//System.out.println("Discard pile:\n");
		//discardpile.PilePrint();

		System.out.println("Cards will now be dealt...");


		// Deal to Player
		List<Card> player_hand = new ArrayList<>();
		UserPlayer.initiate_hand(player_hand, deck);

        Collections.sort(player_hand);
        
		// Deal to AI (currently only 1 AI)
		// ToDo: Do a switch case with number of CPUs as the case numbers
		List<Card> ai_hand = new ArrayList<>();
		AIPlayer.initiate_hand(ai_hand, deck); 

		// print player cards
		System.out.print("The cards in your hand are:");
        UserPlayer.print_phand(player_hand);
        System.out.println("\n");
        // evaluate player cards
        rankOfHands.hand_calculator(player_hand);
        
        
        // For DEBUG purposes, let's peek at the AI's hand
		System.out.print("The cards in AI's hand are:\n");
        AIPlayer.print_aihand(ai_hand);
        System.out.println("\n");
        
        // sort AI hands
   		rankOfHands.hand_calculator(ai_hand);
   		AIPlayer.checkpairs(ai_hand);

     	
        //int ans = 'A' + 'K' + 'Q' + 'J' + 'T' = 379;
 
        //System.out.println("Value: " + ans);

        //String username = scanner.nextLine();


		System.exit(0);
	}
}