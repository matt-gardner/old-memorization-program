package memorizing;

import javax.swing.ImageIcon;

/* The basic data type of the program - a card has a word, a definition, 
 * the day and year memorized (for use in the "need to review" algorithm), 
 * and pointers to the previous and next card in the list.  
 * The methods should be self-explanatory. */
public class Card
{
	public String word;
	public String pictureFile;
	public ImageIcon picture;
	public String definition;
	public int dayMemorized;
	public int yearMemorized;
	
	public Card (String w, String def, boolean isAPicture, int day, int year)
	{
		if (isAPicture)
		{
			word = null;
			pictureFile = w;
			picture = new ImageIcon("memorizingpictures/"+w);
		}
		else
		{
			word = w;
			picture = null;
		}
		definition = def;
		dayMemorized = day;
		yearMemorized = year;
	}
	
	public Card (Card other)
	{
		word = other.word;
		picture = other.picture;
		definition = other.definition;
		dayMemorized = other.dayMemorized;
		yearMemorized = other.yearMemorized;
	}
	
	public Card copy()
	{
		Card newCard = new Card(this);
		return newCard;
	}
	
	public boolean needToReview(int dayToday, int yearToday)
	{
		int numberOfDays = daysBetween(dayToday,yearToday);
		if (numberOfDays < 7)
			return true;
		if (numberOfDays%7 == 0 && numberOfDays < 28)
			return true;
		if (numberOfDays%28 == 0)
			return true;
		return false;
	}
	
	public int daysBetween(int day1, int year1)
	{
		if (year1 == yearMemorized)
			return day1-dayMemorized;
		else
			return day1-dayMemorized+365;
	}
	
	/* Used by the "view words" button */
	public String toString() 
	{
		String wordsToPrint = "";
		if (word == null)
			wordsToPrint += pictureFile + "\n";
		else
			wordsToPrint += word + "\n";
		wordsToPrint += definition + "\n";
		if (word == null)
			wordsToPrint += "picture" + "\n";
		else
			wordsToPrint += "not a picture" + "\n";
		wordsToPrint += dayMemorized + "\n" + yearMemorized + "\n";
		return wordsToPrint;
	}
	
	public int compareTo(Card other)
	{
		if (word == null || other.word == null)
			return 1;
		if (dayMemorized == other.dayMemorized && yearMemorized == other.yearMemorized)
			return word.compareTo(other.word);
		else
		{
			if (yearMemorized == other.yearMemorized)
				return dayMemorized-other.dayMemorized;
			else
				return yearMemorized - other.yearMemorized;
			
			/* I did this at first, to get the exact number of days as the compare function, but I don't think
			 * you need it.  But I can't really check it just yet, 'cause it's April.  So I'm leaving it here
			 * in case I find that the above method doesn't work.
			if (yearMemorized > other.yearMemorized)
				return ((yearMemorized-other.yearMemorized)*365+dayMemorized) - other.dayMemorized;
			else
				return dayMemorized-((other.yearMemorized-yearMemorized)*365+other.dayMemorized);*/
		}
	}
	
	/* For reseting a word in the algorithm if you want to review it more */
	public void setDayMemorized(int day, int year)
	{
		dayMemorized = day;
		yearMemorized = year;
	}

	/* If the word is the same the cards are considered equal.
	 * You can't have more than one definition per word */
	public boolean equals(Card card)
	{
		if (word == null || card.word == null)
			return false;
		return word.equals(card.word);
	}
}
