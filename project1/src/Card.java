import java.io.*;
import java.util.*;
public class Card implements Comparable {
	
	private char rank;
	private char suit;
	private int matching;
	private int value;

	static private boolean init_vflag = false;
	static List<Character> hand_values = new ArrayList<Character>();

	public Card(char initRank, char initSuit){
		rank = initRank;
		suit = initSuit;
		matching = 0;

		if(init_vflag == false)
			init_vlist();
		value = hand_values.indexOf(new Character(rank));
	}

	//this overrides the compare function in Comparable library
	//helps sort the hand when callling Collections.sort();
	@Override
	public int compareTo(Object o){
		Card c = (Card) o;
		return c.value - value;
	}

	public void printCard(){

		//Debug print:
		//System.out.println(rank + "" + suit + "" + value);
		System.out.print(rank + "" + suit + " ");
	}
	public void incr_matching(){
		matching++;
	}
	public char getRank(){
		return rank;
	}

	public char getSuit(){
		return suit;
	}

	public int getValue(){
		return value;
	}

	public int getMatching(){
		return matching;
	}

	public void reset_Matching(){
		matching = 0;
	}

	private static void init_vlist(){
		hand_values.add(new Character('0'));
		hand_values.add(new Character('2'));
		hand_values.add(new Character('3'));
		hand_values.add(new Character('4'));
		hand_values.add(new Character('5'));
		hand_values.add(new Character('6'));
		hand_values.add(new Character('7'));
		hand_values.add(new Character('8'));
		hand_values.add(new Character('9'));
		hand_values.add(new Character('T'));
		hand_values.add(new Character('J'));
		hand_values.add(new Character('Q'));
		hand_values.add(new Character('K'));
		hand_values.add(new Character('A'));

		init_vflag = true;
	}

}