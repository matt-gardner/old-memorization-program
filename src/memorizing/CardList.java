package memorizing;

import java.util.ArrayList;

/* A simple wrapper for an ArrayList that adds a few methods for working 
 * with all of the cards, like displaying them. */
@SuppressWarnings("serial")
public class CardList<T> extends ArrayList<T> {
	
	public CardList<T> copy() {
		CardList<T> newList = new CardList<T>();
		for (int i=0; i<size(); i++) {
			newList.add(get(i));
		}
		return newList;
	}
	
	public String displayWords() {
		String wordsToDisplay = "";
		for (int i=0; i<size(); i++) {
			wordsToDisplay += get(i).toString();
		}
		return wordsToDisplay;
	}
	
	public String print() {
		return displayWords();
	}
}