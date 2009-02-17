package memorizing;

public class StringUtils
{
	public static String firstLetters(String string)
	{
		String newString = "";
		String[] words = string.split("\\s+");
		for (int i=0; i<words.length; i++)
			if (words[i].length() > 0)
				newString += words[i].charAt(0) + " ";	
		return newString;
	}
	
	public static String breakUpString(String string, int maxStringLength)
	{
		if (string.length() < maxStringLength)
			return string;
		String newString = "<html>", part = "";
		int partIndex = 0, index = 0;
		while (index < string.length())
		{
			if (index+maxStringLength > string.length())
				part = string.substring(index);
			else
				part = string.substring(index,index+maxStringLength);
			if (part.length() < maxStringLength)
			{
				newString += part;
				break;
			}
			partIndex = part.lastIndexOf(' ');
			index += partIndex;
			newString += part.substring(0,partIndex) + "<br>";
		}
		return newString;
	}
}
