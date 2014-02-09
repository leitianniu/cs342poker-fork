import java.io.*;
import java.util.*;

public class rankOfHands{
	
	public static void hand_pairing(List<Card> player_hand){
		for(int i = 0; i < 5; i++){
			for(int j = i+1; j < 5; j++){
				if(player_hand.get(i).getValue() == player_hand.get(j).getValue()){
					player_hand.get(i).incr_matching();
					player_hand.get(j).incr_matching();
				}
			}
		}

	}
}