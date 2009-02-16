package memorizing;

import javax.swing.*;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/* CardReaderPanel.java
 * Created by Matthew Gardner
 * Created June 2007
 * Last Modified 2/15/2009
 *
 * See modification notes in revisions.txt.
 * 
 * The main part of a program for memorizing words.  This program requires a driver with a main method, but 
 * only to instantiate it and call the start() method.  I considered making the different frames separate 
 * classes to make the file smaller and easier to read, but that would be too much hassle if I could get it to 
 * work at all, because the frames depend on each other - you need to access the class variables from almost all 
 * of the frames.  I suppose I could do it with inheritance, but that's more trouble than it's worth.
 * The program keeps track of cards entered by reading and writing text files that are stored in a folder 
 * called memorizingfiles.  The folders memorizingfiles and memorizingpictures need to be in the same 
 * folder as the memorizing folder. */

@SuppressWarnings("serial")
public class CardReaderPanel extends JPanel
{

	/* Not all of these variables are used in methods other than the constructor, 
	 * but enough of them are to justify making all of them class variables 
	 * to keep them together for easier reference.  All of the variables 
	 * that each panel uses are declared right here.  The organization of the 
	 * panels is taken care of in the constructor. */
	
	/* Opening panel */
	private JFrame frame;
	private JPanel openingPanel, welcomePanel, labelPanel, subPanel;
	private JLabel welcomeLabel, wordSetLabel, numCardsLabel;
	private JButton newWordButton, saveWordsButton, saveWordsAsButton, loadWordsButton, reviewWordsButton, 
					viewWordsButton, quitButton, newListButton, testButton;
	private JFileChooser chooser;
	private OpeningPanelListener openingListener;
	
	/* Create Card panel */
	private JPanel createCardPanel, subPanel2;
	private JLabel wordLabel, defLabel;
	private JTextField wordField, defField;
	private JButton addWordButton, doneButton;
	private JCheckBox isPicture;
	private CreatePanelListener createListener;
	
	/* Review Card Panel */
	private JPanel reviewCardPanel, subPanel3, subPanel5;
	private JLabel word, definition, wordsReviewing;
	private JButton resetWordButton, showDefButton, nextWordButton, doneReviewingButton, showHintButton, prevWordButton;
	private JCheckBox allWords, byDefinition, randomize;
	private ReviewPanelListener reviewListener;
	
	/* Save Cards Panel */
	private JPanel saveCardsPanel, subPanel4;
	private JLabel fileNameLabel;
	private JTextField fileNameField;
	private JButton saveButton, backButton;
	private SavePanelListener saveListener;
	
	/* View Words Panel */
	private JPanel viewWordsPanel, doneViewingPanel;
	private JTextArea viewWordsArea;
	private JScrollPane viewWordsScrollPane;
	private JButton acceptChangesButton, cancelButton;
	private ViewPanelListener viewingListener;
	
	/* Test Words Panel */
	private JPanel testWordsPanel, testButtonPanel;
	private JTextArea testArea;
	private JLabel testWordLabel, testDefLabel;
	private JCheckBox testAllWords;
	private JButton checkButton, nextTestButton, doneTestingButton;
	private TestPanelListener testListener;
	
	/* Global variables that are needed */
	private int xSize = 750, ySize = 200;
	private Dimension normalSize = new Dimension(xSize,ySize);
	private Dimension bigSize = new Dimension(700,440);
	//private Card list, current, randomList, savedList;
	private int numCards, numCardsReviewing;
	private String numReviewingString = "Reviewing Word: ";
	private int maxStringLength = 85;
	private String cardListName; //cardList is the name of the current file being read
	private String errorMessage = "";
	private Calendar calendar;
	private int dayToday, yearToday;
	private CardList<Card> cardList, randomList, fullList, reviewList;
	private int currentCardIndex;
	private Card current;
	
	public CardReaderPanel ()
	{
		initializeOpeningWindow();
		initializeCreateCardWindow();
		initializeReviewCardsWindow();
		initializeSaveCardsWindow();
		initializeViewCardsWindow();
		initializeTestWindow();
		fullList = new CardList<Card>();
		
		setDay();
	}
	
	private void setDay() {
		calendar = Calendar.getInstance();
		dayToday = calendar.get(Calendar.DAY_OF_YEAR);
		yearToday = calendar.get(Calendar.YEAR);
	}
	
	/* These methods used to comprise the constructor, but I decided to separate them
	 * so it would be easier to navigate through the code. Now the constructor calls these methods. */
	private void initializeOpeningWindow()
	{
		frame = new JFrame("Memorization Program");
		openingPanel = new JPanel();
		openingPanel.setLayout(new BoxLayout(openingPanel, BoxLayout.Y_AXIS));
		openingPanel.setPreferredSize(normalSize);
		welcomePanel = new JPanel();
		welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.X_AXIS));
		welcomeLabel = new JLabel ("Welcome to the Program!");
		wordSetLabel = new JLabel ("Current Set of Words: " + cardListName);
		numCardsLabel = new JLabel ("Number of Words: " + numCards);
		labelPanel = new JPanel();
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.X_AXIS));
		labelPanel.add(Box.createHorizontalGlue());
		labelPanel.add(wordSetLabel);
		labelPanel.add(Box.createHorizontalGlue());
		labelPanel.add(numCardsLabel);
		labelPanel.add(Box.createHorizontalGlue());
		subPanel = new JPanel();
		subPanel.setLayout(new GridLayout(2,5));
		subPanel.setPreferredSize(new Dimension(xSize, 15));
		openingListener = new OpeningPanelListener();
		newWordButton = new JButton ("Add Words");
		newWordButton.addActionListener(openingListener);
		newWordButton.setMnemonic('A');
		saveWordsButton = new JButton ("Save Words");
		saveWordsButton.addActionListener(openingListener);
		saveWordsButton.setMnemonic('S');
		saveWordsAsButton = new JButton ("Save Words As");
		saveWordsAsButton.addActionListener(openingListener);
		saveWordsAsButton.setMnemonic('W');
		loadWordsButton = new JButton ("Load Words");
		loadWordsButton.addActionListener(openingListener);
		loadWordsButton.setMnemonic('L');
		reviewWordsButton = new JButton ("Review Words");
		reviewWordsButton.addActionListener(openingListener);
		reviewWordsButton.setMnemonic('R');
		testButton = new JButton("Test Words");
		testButton.addActionListener(openingListener);
		testButton.setMnemonic('T');
		viewWordsButton = new JButton ("Edit Words");
		viewWordsButton.addActionListener(openingListener);
		viewWordsButton.setMnemonic('E');
		quitButton = new JButton("Quit");
		quitButton.addActionListener(openingListener);
		quitButton.setMnemonic('Q');
		newListButton = new JButton("New List");
		newListButton.addActionListener(openingListener);
		newListButton.setMnemonic('N');
		welcomePanel.add(Box.createHorizontalGlue());
		welcomePanel.add(welcomeLabel);
		welcomePanel.add(Box.createHorizontalGlue());
		subPanel.add(newWordButton);
		subPanel.add(saveWordsButton);
		subPanel.add(reviewWordsButton);
		subPanel.add(testButton);
		subPanel.add(quitButton);
		subPanel.add(newListButton);
		subPanel.add(saveWordsAsButton);
		subPanel.add(viewWordsButton);
		subPanel.add(loadWordsButton);
		openingPanel.add(Box.createVerticalGlue());
		openingPanel.add(welcomePanel);
		openingPanel.add(Box.createVerticalGlue());
		openingPanel.add(labelPanel);
		openingPanel.add(subPanel);		
	}
	private void initializeCreateCardWindow()
	{
		createCardPanel = new JPanel ();
		createCardPanel.setLayout(new BoxLayout(createCardPanel, BoxLayout.Y_AXIS));
		createCardPanel.setPreferredSize(normalSize);
		createListener = new CreatePanelListener();
		wordLabel = new JLabel ("Word, reference, or file name for a picture:");
		defLabel = new JLabel ("Definition, text, or description:");
		wordField = new JTextField ("Enter Word or Reference");
		wordField.addActionListener(createListener);
		defField = new JTextField ("Enter Definition or Text");
		defField.addActionListener(createListener);
		doneButton = new JButton ("Done");
		doneButton.addActionListener(createListener);
		doneButton.setMnemonic('D');
		addWordButton = new JButton ("Add Word");
		addWordButton.addActionListener(createListener);
		addWordButton.setMnemonic('A');
		isPicture = new JCheckBox("This is a picture");
		isPicture.addActionListener(createListener);
		subPanel2 = new JPanel ();
		subPanel2.setLayout(new BoxLayout(subPanel2, BoxLayout.X_AXIS));
		subPanel2.add(Box.createHorizontalGlue());
		subPanel2.add(isPicture);
		subPanel2.add(Box.createHorizontalGlue());
		subPanel2.add(addWordButton);
		subPanel2.add(Box.createHorizontalGlue());
		subPanel2.add(doneButton);
		subPanel2.add(Box.createHorizontalGlue());
		createCardPanel.add(wordLabel);
		createCardPanel.add(wordField);
		createCardPanel.add(Box.createVerticalGlue());
		createCardPanel.add(defLabel);
		createCardPanel.add(defField);
		createCardPanel.add(Box.createVerticalGlue());
		createCardPanel.add(subPanel2);
	}
	private void initializeReviewCardsWindow()
	{
		reviewCardPanel = new JPanel();
		reviewCardPanel.setLayout(new BoxLayout(reviewCardPanel, BoxLayout.Y_AXIS));
		reviewCardPanel.setPreferredSize(bigSize);
		word = new JLabel();
		//Font font = new Font("Times",Font.PLAIN,24);
		//word.setFont(font); - I toyed with the idea of being able to change the font.  I might do it later.
		JPanel wordPanel = new JPanel();
		wordPanel.add(word);
		wordPanel.add(Box.createHorizontalGlue());
		definition = new JLabel();
		JPanel defPanel = new JPanel();
		defPanel.add(definition);
		defPanel.add(Box.createHorizontalGlue());
		reviewListener = new ReviewPanelListener();
		resetWordButton = new JButton("Reset word");
		resetWordButton.addActionListener(reviewListener);
		resetWordButton.setMnemonic('W');
		showDefButton = new JButton("Show Definition");
		showDefButton.addActionListener(reviewListener);
		showDefButton.setMnemonic('S');
		showHintButton = new JButton("Show Hint");
		showHintButton.addActionListener(reviewListener);
		showHintButton.setMnemonic('H');
		prevWordButton = new JButton("Previous Word");
		prevWordButton.addActionListener(reviewListener);
		prevWordButton.setMnemonic('P');
		nextWordButton = new JButton("Next Word");
		nextWordButton.addActionListener(reviewListener);
		nextWordButton.setMnemonic('N');
		doneReviewingButton = new JButton("Done Reviewing");
		doneReviewingButton.addActionListener(reviewListener);
		doneReviewingButton.setMnemonic('D');
		allWords = new JCheckBox("Review All Words");
		allWords.addActionListener(reviewListener);
		allWords.setMnemonic('A');
		byDefinition = new JCheckBox("By Definition");
		byDefinition.addActionListener(reviewListener);
		byDefinition.setMnemonic('B');
		randomize = new JCheckBox("Randomize");
		randomize.addActionListener(reviewListener);
		randomize.setMnemonic('R');
		wordsReviewing = new JLabel(numReviewingString);
		subPanel5 = new JPanel();
		subPanel5.setLayout(new BoxLayout(subPanel5, BoxLayout.X_AXIS));
		subPanel5.add(Box.createHorizontalGlue());
		subPanel5.add(randomize);
		subPanel5.add(Box.createHorizontalGlue());
		subPanel5.add(byDefinition);
		subPanel5.add(Box.createHorizontalGlue());
		subPanel5.add(allWords);
		subPanel5.add(Box.createHorizontalGlue());
		subPanel5.add(wordsReviewing);
		subPanel5.add(Box.createHorizontalGlue());
		subPanel3 = new JPanel();
		//subPanel3.setLayout(new GridLayout(2, 3));
		subPanel3.setLayout(new BoxLayout(subPanel3, BoxLayout.X_AXIS));
		subPanel3.add(resetWordButton);
		subPanel3.add(showHintButton);
		subPanel3.add(showDefButton);
		subPanel3.add(prevWordButton);
		subPanel3.add(nextWordButton);
		subPanel3.add(doneReviewingButton);
		reviewCardPanel.add(Box.createVerticalGlue());
		reviewCardPanel.add(wordPanel);
		reviewCardPanel.add(Box.createVerticalGlue());
		reviewCardPanel.add(defPanel);
		reviewCardPanel.add(Box.createVerticalGlue());
		reviewCardPanel.add(subPanel5);
		reviewCardPanel.add(subPanel3);
	}
	private void initializeSaveCardsWindow()
	{
		saveCardsPanel = new JPanel();
		saveCardsPanel.setLayout(new BoxLayout(saveCardsPanel, BoxLayout.Y_AXIS));
		saveCardsPanel.setPreferredSize(new Dimension(300,100));
		fileNameLabel = new JLabel("Enter File Name (include extension):");
		fileNameField = new JTextField();
		saveListener = new SavePanelListener();
		saveButton = new JButton("Save");
		saveButton.addActionListener(saveListener);
		saveButton.setMnemonic('S');
		backButton = new JButton("Back");
		backButton.addActionListener(saveListener);
		backButton.setMnemonic('B');
		subPanel4 = new JPanel();
		subPanel4.setLayout(new BoxLayout(subPanel4, BoxLayout.X_AXIS));
		subPanel4.add(Box.createHorizontalGlue());
		subPanel4.add(saveButton);
		subPanel4.add(Box.createHorizontalGlue());
		subPanel4.add(backButton);
		subPanel4.add(Box.createHorizontalGlue());
		saveCardsPanel.add(fileNameLabel);
		saveCardsPanel.add(fileNameField);
		saveCardsPanel.add(subPanel4);
	}
	private void initializeViewCardsWindow()
	{
		viewWordsPanel = new JPanel();
		viewWordsPanel.setLayout(new BoxLayout(viewWordsPanel, BoxLayout.Y_AXIS));
		viewWordsPanel.setPreferredSize(bigSize);
		viewWordsArea = new JTextArea(10, 80);
		viewingListener = new ViewPanelListener();
		viewWordsArea.setLineWrap(true);
		viewWordsArea.setWrapStyleWord(true);
		viewWordsScrollPane = new JScrollPane(viewWordsArea);
		acceptChangesButton = new JButton("Accept Changes");
		acceptChangesButton.addActionListener(viewingListener);
		acceptChangesButton.setMnemonic('A');
		cancelButton = new JButton("Cancel and Return");
		cancelButton.addActionListener(viewingListener);
		cancelButton.setMnemonic('C');
		viewWordsPanel.add(viewWordsScrollPane);
		doneViewingPanel = new JPanel();
		doneViewingPanel.setLayout(new BoxLayout(doneViewingPanel, BoxLayout.X_AXIS));
		doneViewingPanel.add(Box.createHorizontalGlue());
		doneViewingPanel.add(acceptChangesButton);
		doneViewingPanel.add(Box.createHorizontalGlue());
		doneViewingPanel.add(cancelButton);
		doneViewingPanel.add(Box.createHorizontalGlue());
		viewWordsPanel.add(doneViewingPanel);
	}
	private void initializeTestWindow()
	{
		testListener = new TestPanelListener();
		testWordsPanel = new JPanel();
		testButtonPanel = new JPanel();
		testWordLabel = new JLabel("This is the word");
		testDefLabel = new JLabel("This is the definition");
		JPanel wordPanel = new JPanel();
		wordPanel.add(testWordLabel);
		wordPanel.add(Box.createHorizontalGlue());
		JPanel defPanel = new JPanel();
		defPanel.add(testDefLabel);
		defPanel.add(Box.createHorizontalGlue());
		testArea = new JTextArea();
		testArea.setSize(xSize,ySize/3);
		testArea.setLineWrap(true);
		testArea.setWrapStyleWord(true);
		//testArea.setBorder(new Border());
		testAllWords = new JCheckBox("Test All Words");
		testAllWords.addActionListener(testListener);
		testAllWords.setMnemonic('A');
		checkButton = new JButton("Check Answer");
		checkButton.addActionListener(testListener);
		checkButton.setMnemonic('C');
		nextTestButton = new JButton("Next Word");
		nextTestButton.addActionListener(testListener);
		nextTestButton.setMnemonic('N');
		doneTestingButton = new JButton("Done");
		doneTestingButton.addActionListener(testListener);
		doneTestingButton.setMnemonic('D');
		testButtonPanel.setLayout(new BoxLayout(testButtonPanel, BoxLayout.X_AXIS));
		testButtonPanel.add(Box.createHorizontalGlue());
		testButtonPanel.add(testAllWords);
		testButtonPanel.add(Box.createHorizontalGlue());
		testButtonPanel.add(checkButton);
		testButtonPanel.add(Box.createHorizontalGlue());
		testButtonPanel.add(nextTestButton);
		testButtonPanel.add(Box.createHorizontalGlue());
		testButtonPanel.add(doneTestingButton);
		testButtonPanel.add(Box.createHorizontalGlue());
		testWordsPanel.setLayout(new BoxLayout(testWordsPanel, BoxLayout.Y_AXIS));
		testWordsPanel.add(Box.createVerticalGlue());
		testWordsPanel.add(wordPanel);
		testWordsPanel.add(Box.createVerticalGlue());
		testWordsPanel.add(defPanel);
		testWordsPanel.add(Box.createVerticalGlue());
		testWordsPanel.add(testArea);
		testWordsPanel.add(testButtonPanel);
	}
	
	/* Method called by driver to begin the program */
	public void start() 
	{
		try
		{
			File lastFile = new File("memorizingfiles/lastfile");
			BufferedReader in = new BufferedReader(new FileReader(lastFile));
			String fileName = in.readLine();
			loadWords(fileName);
		}
		catch (Exception e)
		{
			loadWords("blank.mem");
		}
		goToOpeningFrame();
		frame.setLocation(350,250);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(this);
		frame.setVisible(true);
		frame.pack();
		grabFocus();
	}
	
	
	/* The purpose of these methods should be obvious by their names.  
	 * They are called by the action listeners when the appropriate button is pressed. */
	private void goToOpeningFrame()
	{
		setDay();
		currentCardIndex = 0;
		removeAll();
		numCards = countCards();
		welcomeLabel.setText("Welcome to the Program!");
		numCardsLabel.setText("Number of Words: " + numCards);
		add(openingPanel);
		repaint();
		frame.pack();
	}
	
	private void goToCreateCardFrame()
	{
		setDay();
		removeAll();
		wordField.setText("Enter Word or Reference");
		defField.setText("Enter Definition or Text");
		isPicture.setSelected(false);
		add(createCardPanel);
		repaint();
		frame.pack();
	}
	
	/* This is longer than the others because it needs to check if the words need to be reviewed */
	private void goToReviewCardFrame()
	{
		setDay();
		makeReviewList(fullList);
		removeAll();
		frame.getRootPane().setDefaultButton(nextWordButton);
		if (thereArePictures())
			reviewCardPanel.setPreferredSize(bigSize);
		else
			reviewCardPanel.setPreferredSize(normalSize);
		allWords.setSelected(false);
		byDefinition.setSelected(false);
		randomize.setSelected(false);
		word.setIcon(null);
		definition.setIcon(null);
		currentCardIndex = 0;
		if (fullList.size() == 0)
		{
			word.setText("There are no words!");
			showDefButton.setText("Show Definition");
			definition.setText("");
			disableButtons();
		}
		else
		{
			numCardsReviewing = countCardsToReview();
			currentCardIndex = 0;
			wordsReviewing.setText(numReviewingString+(currentCardIndex+1)+"/"+numCardsReviewing);
			cardList = reviewList;
			if (numCardsReviewing == 0)
			{
				word.setText("There are no words that need to be reviewed.");
				definition.setText("");
				disableButtons();
				allWords.setEnabled(true);
				add(reviewCardPanel);
				repaint();
				frame.pack();
				return;
			}
			else
			{
				current = cardList.get(0);
				enableButtons();
				current = cardList.get(currentCardIndex);
				if (current.word != null)
				{
					word.setText(current.word);
					showDefButton.setText("Show Definition");
				}
				else
				{
					word.setText("");
					word.setIcon(current.picture);
					showDefButton.setText("Show Description");
				}
				definition.setText("");
			}
		}
		add(reviewCardPanel);
		repaint();
		frame.pack();
	}

	private void goToTestCardFrame()
	{
		setDay();
		makeReviewList(fullList);
		removeAll();
		if (fullList.size() == 0)
		{
			testWordLabel.setText("No words to be tested!");
			testDefLabel.setText("");
			checkButton.setEnabled(false);
			nextTestButton.setEnabled(false);
			
		}
		else
		{
			numCardsReviewing = countCardsToReview();
			wordsReviewing.setText(numReviewingString+(currentCardIndex+1)+"/"+numCardsReviewing);
			cardList = reviewList;
			currentCardIndex = 0;
			if (numCardsReviewing == 0)
			{
				testWordLabel.setText("No words to be tested!");
				checkButton.setEnabled(false);
				nextTestButton.setEnabled(false);
			}
			else 
			{
				current = cardList.get(0);
				if (current.word != null)
					testWordLabel.setText(current.word);
				else
					testWordLabel.setText("This is a picture...");
				testDefLabel.setText("");
				testArea.setText("");
				checkButton.setEnabled(true);
			}
		}
		testAllWords.setSelected(false);
		testWordsPanel.setPreferredSize(normalSize);
		add(testWordsPanel);
		repaint();
		frame.pack();
	}
	
	private void goToSaveWordsFrame()
	{
		setDay();
		removeAll();
		add(saveCardsPanel);
		frame.getRootPane().setDefaultButton(saveButton);
		repaint();
		frame.pack();
	}
	
	private void goToViewWordsFrame()
	{
		setDay();
		removeAll();
		if (fullList.size() != 0)
			viewWordsArea.setText(fullList.displayWords());
		else
			viewWordsArea.setText("There are no words.");
		add(viewWordsPanel);
		repaint();
		frame.pack();
	}
	
	private void disableButtons()
	{
		showDefButton.setEnabled(false);
		showHintButton.setEnabled(false);
		nextWordButton.setEnabled(false);
		resetWordButton.setEnabled(false);
		byDefinition.setEnabled(false);
		allWords.setEnabled(false);
		randomize.setEnabled(false);
	}
	
	private void enableButtons()
	{
		showDefButton.setEnabled(true);
		showHintButton.setEnabled(true);
		nextWordButton.setEnabled(true);
		resetWordButton.setEnabled(true);
		byDefinition.setEnabled(true);
		allWords.setEnabled(true);
		randomize.setEnabled(true);
	}
	
	
	/* This version of save words is called from the "Save words as" panel.
	 * For ease of reading in the file, the word, definition, year and 
	 * day memorized are all put on separate lines. */
	private boolean saveWords()
	{
		try
		{
			String fileName = fileNameField.getText();
			if (!fileName.endsWith(".mem"))
				throw new InvalidFileNameException();
			return saveWords(fileName);
		}
		catch (InvalidFileNameException e)
		{
			errorMessage = "File name must end with .mem.  Please try again.";
			return false;
		}
	}
	/* This version of save words is called from the "Save words" button on the main panel, 
	 * when you just write to the file you're already using */
	private boolean saveWords(String fileName)
	{
		try
		{
			OutputStreamWriter o = new OutputStreamWriter(new FileOutputStream("memorizingfiles/"+fileName),"UTF-8");
			BufferedWriter buff = new BufferedWriter(o);
			PrintWriter out = new PrintWriter(buff);
			if (fullList.size() == 0)
				out.print("No Words");
			else
				out.print(fullList.print());
			out.close();
			cardListName = fileName;
			wordSetLabel.setText("Current Set of Words: " + cardListName);
			File lastFile = new File("memorizingfiles/lastfile");
			out = new PrintWriter(new BufferedWriter(new FileWriter(lastFile)));
			out.println(cardListName);
			out.close();
			return true;
		}
		catch (IOException e)
		{
			errorMessage = "Error saving words.  Words not saved.";
			return false;
		}
	}
	/* Called from the Load words button - opens a file chooser.  
	 * To start a new list of words, open a blank file - blank.mem.
	 * Don't overwrite blank.mem or you can't start a new file unless you delete all of your current words. */
	private void loadWords()
	{
		try
		{
			chooser = new JFileChooser(System.getProperty("user.dir")+"/memorizingfiles");
			if (chooser.showOpenDialog(openingPanel) != JFileChooser.APPROVE_OPTION)
				return;
			File file = chooser.getSelectedFile();
			if (file == null)
				return;
			cardListName = file.getName();
			if (!cardListName.endsWith(".mem"))
				throw new InvalidFileNameException();
			InputStreamReader temp = new InputStreamReader(new FileInputStream(file), "UTF-8");
			BufferedReader in = new BufferedReader(temp);
			if (!parseWords(in))
			{
				welcomeLabel.setText("Blank file loaded");
				wordSetLabel.setText("Current set of words: Empty");
				numCardsLabel.setText("Number of Cards: 0");
				newWordButton.setEnabled(true);
				saveWordsButton.setEnabled(true);
				saveWordsAsButton.setEnabled(true);
				reviewWordsButton.setEnabled(true); 
				viewWordsButton.setEnabled(true);
				return;
			}
			else
			{
				welcomeLabel.setText("Words successfully loaded");
			}
			wordSetLabel.setText("Current Set of Words: " + cardListName);
			numCards = countCards();
			numCardsLabel.setText("Number of Cards: " + numCards);
			newWordButton.setEnabled(true);
			saveWordsButton.setEnabled(true);
			saveWordsAsButton.setEnabled(true);
			reviewWordsButton.setEnabled(true); 
			viewWordsButton.setEnabled(true);
		}
		catch (InvalidFileNameException e)
		{
			goToOpeningFrame();
			welcomeLabel.setText("Invalid file.  Please load a .mem file.");
			newWordButton.setEnabled(false);
			saveWordsButton.setEnabled(false);
			saveWordsAsButton.setEnabled(false);
			reviewWordsButton.setEnabled(false); 
			viewWordsButton.setEnabled(false);
		}
		catch (IOException e)
		{
			System.out.println("You did something wrong");
		}
	}	
	/* Called at start of program to load the default word set */
	private void loadWords(String fileName)
	{
		try
		{
			cardListName = fileName;
			File file = new File("memorizingfiles/"+fileName);
			InputStreamReader temp = new InputStreamReader(new FileInputStream(file), "UTF-8");
			BufferedReader in = new BufferedReader(temp);
			if (!parseWords(in))
			{
				wordSetLabel.setText("Current Set of Words: New");
				saveWordsButton.setEnabled(false);
				return;
			}
			else
			{
				wordSetLabel.setText("Current Set of Words: " + cardListName);
			}
		}
		catch (IOException e)
		{
			welcomeLabel.setText("Error occurred loading words");
		}
	}
	/* Called when leaving the view/edit panel, to update the list of words with the changes made */
	private boolean updateWords(String words)
	{
		CardList<Card> backupList = fullList.copy();
		try
		{
			StringReader string = new StringReader(words);
			BufferedReader in = new BufferedReader(string);
			parseWords(in);
			return true;
		}
		catch (Exception e)
		{
			fullList = backupList;
			return false;
		}
	}

	private boolean parseWords(BufferedReader in) throws IOException
	{
		fullList = new CardList<Card>();
		String word = in.readLine();
		if (word.equals("No Words"))
		{
			return false;
		}
		String definition = in.readLine();
		String picture = in.readLine();
		boolean isAPicture;
		if (picture.equals("picture"))
			isAPicture = true;
		else
			isAPicture = false;
		int day = Integer.parseInt(in.readLine());
		int year = Integer.parseInt(in.readLine());
		newCard(word,definition,isAPicture,day,year);
		while ((word = in.readLine()) != null)
		{
			definition = in.readLine();
			picture = in.readLine();
			if (picture.equals("picture"))
				isAPicture = true;
			else
				isAPicture = false;
			day = Integer.parseInt(in.readLine());
			year = Integer.parseInt(in.readLine());
			newCard(word,definition,isAPicture,day,year);
		}
		return true;
	}
	
		
	/* I wrote this in the easiest way I could think of so I wouldn't have to 
	 * modify my listener code.  This method creates a temporary array of cards,
	 * populates it randomly from the words in the current list (making a copy of
	 * the list, of course, so you don't mess with the pointers in the original list),
	 * then makes a new list ordered in the same order as the array.  It saves a pointer
	 * to the original list, makes the "list" variable point to the random list, and 
	 * then you're done.  When you leave the review panel you need to be sure to reset the
	 * list pointer to the saved list. */
	private void randomize()
	{
		Card[] tempList = new Card[numCards];
		Random rand = new Random();
		for (int i=0; i<fullList.size(); i++)
		{
			int j = rand.nextInt(numCards);
			while (tempList[j] != null)
				j = rand.nextInt(numCards);
			tempList[j] = fullList.get(i);
		}
		randomList = new CardList<Card>();
		for (int i=0; i<tempList.length; i++)
		{
			randomList.add(tempList[i]);
		}
	}
	
	private boolean thereArePictures()
	{
		for (int i=0; i<fullList.size(); i++) {
			if (fullList.get(i).word == null)
				return true;
		}
		return false;
	}
	
	private String breakUpString(String string)
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
	
	/* The basic data type of the program - a card has a word, a definition, 
	 * the day and year memorized (for use in the "need to review" algorithm), 
	 * and pointers to the previous and next card in the list.  
	 * The methods should be self-explanatory. */
	private class Card
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
		
		public boolean needToReview()
		{
			int numberOfDays = daysBetween(dayToday,yearToday,dayMemorized,yearMemorized);
			if (numberOfDays < 7)
				return true;
			if (numberOfDays%7 == 0 && numberOfDays < 28)
				return true;
			if (numberOfDays%28 == 0)
				return true;
			return false;
		}
		
		private int daysBetween(int day1, int year1, int day2, int year2)
		{
			if (year1 == year2)
				return day1-day2;
			else
				return day1-day2+365;
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
	
	/* A simple wrapper for an ArrayList that adds a few methods for working 
	 * with all of the cards, like displaying them. */
	private class CardList<T> extends ArrayList<T> {
		
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
	
	/* Add a new card to the list.  This method does not allow duplicate words, 
	 * and it adds the card to the list in alphabetical order. */
	private boolean newCard(String word, String def, boolean isAPicture, int day, int year)
	{
		Card newCard = new Card(word, def, isAPicture, day, year);
		if (fullList.contains(newCard))
			return false;
		int i;
		for (i=0; i<fullList.size(); i++) {
			if (newCard.compareTo(fullList.get(i)) < 0)
				break;
		}
		fullList.add(i,newCard);
		return true;
	}
	private int countCards()
	{
		return fullList.size();
	}
	private int countCardsToReview()
	{
		return reviewList.size();
	}	
	
	private void makeReviewList(CardList<Card> list)
	{
		reviewList = new CardList<Card>();
		for (Card c : list) {
			if (c.needToReview()) {
				reviewList.add(c);
			}
		}
	}
	
	/* Listeners for the various panels.  They should be fairly self-explanatory. */
	private class OpeningPanelListener implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
		{
			if (event.getSource() == newWordButton)  
				goToCreateCardFrame();
			if (event.getSource() == saveWordsButton) 
				if(saveWords(cardListName))
					welcomeLabel.setText("Words Successfully Saved");
				else
					welcomeLabel.setText("An error occurred...  Sorry...");
			if (event.getSource() == saveWordsAsButton)
				goToSaveWordsFrame();
			if (event.getSource() == reviewWordsButton)  
				goToReviewCardFrame();
			if (event.getSource() == testButton)
				goToTestCardFrame();
			if (event.getSource() == loadWordsButton)  
			{
				if (wordSetLabel.getText().endsWith("Modified"))
				{
					int response = JOptionPane.showConfirmDialog(frame, 
							"Words not saved.  Do you want to discard changes?",
							"Loading Word Set",
							JOptionPane.YES_NO_OPTION);
					switch (response) {
					case JOptionPane.YES_OPTION:
						loadWords();
					case JOptionPane.NO_OPTION:
						return;
					}
				}
				else
					loadWords();
			}
			if (event.getSource() == newListButton)
			{
				if (wordSetLabel.getText().endsWith("Modified"))
				{
					int response = JOptionPane.showConfirmDialog(frame, 
							"Words not saved.  Do you want to discard changes?",
							"Creating new list",
							JOptionPane.YES_NO_OPTION);
					switch (response) {
					case JOptionPane.YES_OPTION:
						loadWords("blank.mem");
						wordSetLabel.setText("Current Set of Words: New");
						saveWordsButton.setEnabled(false);
						return;
					case JOptionPane.NO_OPTION:
						return;
					}
				}
				loadWords("blank.mem");
				wordSetLabel.setText("Current Set of Words: New");
				saveWordsButton.setEnabled(false);
			}
			if (event.getSource() == viewWordsButton)
				goToViewWordsFrame();
			if (event.getSource() == quitButton) 
				if (wordSetLabel.getText().endsWith("Modified"))
				{
					int response = JOptionPane.showConfirmDialog(frame, 
							"Words not saved.  Do you want to discard changes?",
							"Quitting the program",
							JOptionPane.YES_NO_OPTION);
					switch (response) {
					case JOptionPane.YES_OPTION:
						System.exit(0);
					case JOptionPane.NO_OPTION:
						return;
					}
				}
				else
					System.exit(0);
		}
	}
	private class CreatePanelListener implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
		{
			if (event.getSource() == addWordButton || event.getSource() == wordField || event.getSource() == defField) 
			{	//the newCard method checks for repeats and returns false if you already have the word
				if (newCard(wordField.getText(),defField.getText(),isPicture.isSelected(),
						calendar.get(Calendar.DAY_OF_YEAR),calendar.get(Calendar.YEAR)))
				{
					wordField.setText("Word added.  Add another word?");
					defField.setText("Word added.  Add another definition?");
					wordSetLabel.setText("Current Set of Words: Modified");
				}
				else
				{
					String word = wordField.getText();
					wordField.setText(word + " is already in the program.  Add failed.");
					defField.setText("Please enter another word.");
				}
			}
			if (event.getSource() == doneButton)
				goToOpeningFrame();
		}
	}
	private class ReviewPanelListener implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
		{
			if (event.getSource() == randomize)
			{
				if (randomize.isSelected())
				{
					randomize();
				}
				showFirstWord();
			}
			if (event.getSource() == byDefinition)
			{
				showWord();
				if (byDefinition.isSelected())
					showHintButton.setEnabled(false);
				else
					showHintButton.setEnabled(true);
			}
			if (event.getSource() == allWords)
			{	
				showFirstWord();

				if (allWords.isSelected())
					numCardsReviewing = countCards();
				else
					numCardsReviewing = countCardsToReview();
				wordsReviewing.setText(numReviewingString+(currentCardIndex+1)+"/"+numCardsReviewing);
			}
			if (event.getSource() == resetWordButton)
			{
				current.setDayMemorized(calendar.get(Calendar.DAY_OF_YEAR),calendar.get(Calendar.YEAR));
				wordSetLabel.setText("Current Set of Words: Modified");
			}
			if (event.getSource() == doneReviewingButton)  
			{
				cardList = fullList;
				goToOpeningFrame();
			}
			if (event.getSource() == showDefButton) 
			{
				showDefinition();
			}
			if (event.getSource() == showHintButton)
			{
				showHint();
			}
			if (event.getSource() == nextWordButton)  
			{		//You don't have to worry about the case that there is no word to review, 
					//because we took care of that in the goToReviewCardFrame method, and this 
					//button would be disabled
				if (!(currentCardIndex == cardList.size() - 1))
					currentCardIndex++;
				else
					currentCardIndex = 0;
				current = cardList.get(currentCardIndex);
				showWord();
				wordsReviewing.setText(numReviewingString+(currentCardIndex+1)+"/"+numCardsReviewing);
			}
			if (event.getSource() == prevWordButton)
			{
				if (!(currentCardIndex == 0))
					currentCardIndex--;
				else
					currentCardIndex = cardList.size() - 1;
				current = cardList.get(currentCardIndex);
				showWord();
				wordsReviewing.setText(numReviewingString+(currentCardIndex+1)+"/"+numCardsReviewing);
			}
		}
		
		private void showFirstWord()
		{
			if (allWords.isSelected()) {
				if (randomize.isSelected())
					cardList = randomList;
				else
					cardList = fullList;
			}
			else {
				if (randomize.isSelected())
					makeReviewList(randomList);
				else
					makeReviewList(fullList);
				cardList = reviewList;
			}
			if (cardList.size() == 0)
			{
				word.setText("There are no words that need to be reviewed.");
				definition.setText("");
				disableButtons();
				allWords.setEnabled(true);
				return;
			}
			else
			{
				currentCardIndex = 0;
				current = cardList.get(0);
				showWord();
				enableButtons();
			}
		}
		
		private void showWord()
		{
			word.setIcon(null);
			definition.setIcon(null);
			showHintButton.setText("Show Hint");
			if (byDefinition.isSelected())
			{
				word.setText(breakUpString(current.definition));
				if (current.word == null)
					showDefButton.setText("Show Picture");
				else
					showDefButton.setText("Show Word");
				definition.setText("");
				showDefButton.setEnabled(true);
			}
			else
			{
				if (current.word != null)
				{
					word.setText(breakUpString(current.word));
					showDefButton.setText("Show Definition");
				}
				else
				{
					word.setText("");
					word.setIcon(current.picture);
					showDefButton.setText("Show Description");
				}
				definition.setText("");
				showDefButton.setEnabled(true);
			}
		}
		
		private void showDefinition()
		{
			if (showDefButton.getText().startsWith("Hide"))
			{
				showDefButton.setText(showDefButton.getText().replaceFirst("Hide", "Show"));
				showHintButton.setText("Show Hint");
				definition.setText("");
				definition.setIcon(null);
			}
			else
			{
				if (!byDefinition.isSelected())
				{
					definition.setText(breakUpString(current.definition));
					if (current.word == null)
						showDefButton.setText("Hide Description");
					else {
						showDefButton.setText("Hide Definition");
						showHintButton.setText("Show Hint");
					}
				}
				else
				{
					if (current.word == null)
					{
						definition.setText("");
						definition.setIcon(current.picture);
						showDefButton.setText("Hide Picture");
					}
					else
					{
						definition.setText(breakUpString(current.word));
						showDefButton.setText("Hide Word");
					}
				}
			}
		}
		
		private void showHint() 
		{
			if (showHintButton.getText().startsWith("Hide"))
			{
				showHintButton.setText(showHintButton.getText().replaceFirst("Hide", "Show"));
				showDefButton.setText("Show Definition");
				definition.setText("");
				definition.setIcon(null);
			}
			else
			{
				String defstring = "";
				String temp = current.definition;
				while (temp.length() > 0)
				{
					String toAdd = "";
					//Get everything before the start of a tag
					if (temp.indexOf('<') >= 0)
					{
						toAdd = temp.substring(0,temp.indexOf('<'));
						temp = temp.substring(temp.indexOf('<'));
						if (toAdd.length() > 0)
							defstring += firstLetters(toAdd);
						if (temp.indexOf('>') >= 0)
						{
							defstring += temp.substring(0,temp.indexOf('>')+1);
							temp = temp.substring(temp.indexOf('>')+1);
						}
					}
					//There was no tag
					else
					{
						toAdd = temp;
						temp = "";
						defstring += firstLetters(toAdd);
					}
				}
				definition.setText("<html>" + defstring);
				showHintButton.setText("Hide Hint");
				showDefButton.setText("Show Definition");
			}
		}
		
		private String firstLetters(String string)
		{
			String newString = "";
			String[] words = string.split("\\s+");
			for (int i=0; i<words.length; i++)
				if (words[i].length() > 0)
					newString += words[i].charAt(0) + " ";	
			return newString;
		}
	}
	private class SavePanelListener implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
		{
			if (event.getSource() == saveButton)
			{
				if(saveWords())
				{
					goToOpeningFrame();
					welcomeLabel.setText("Words Successfully Saved");
					saveWordsButton.setEnabled(true);
				}
				else
				{
					goToOpeningFrame();
					welcomeLabel.setText(errorMessage);
				}
			}
			if (event.getSource() == backButton)
			{
				goToOpeningFrame();
				welcomeLabel.setText("Words Not Saved");
			}
		}
	}
	private class ViewPanelListener implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
		{
			if (event.getSource() == acceptChangesButton)
			{	
				if (updateWords(viewWordsArea.getText()))
				{
					goToOpeningFrame();
					wordSetLabel.setText("Current Set of Words: Modified");
					welcomeLabel.setText("Words successfully modified.");
				}
				else
				{
					goToOpeningFrame();
					welcomeLabel.setText("Could not modify words. Words not changed.");
				}
			}
			if (event.getSource() == cancelButton)
				goToOpeningFrame();
		}
	}
	private class TestPanelListener implements ActionListener
	{
		//TODO: Fix this method
		public void actionPerformed (ActionEvent event)
		{
			if (event.getSource() == checkButton)
			{
				if (checkButton.getText().equals("Hide Answer"))
				{
					testDefLabel.setText("");
					checkButton.setText("Check Answer");
				}
				else
				{
					String entered = testArea.getText();
					if (entered.equals(current.definition))
						testDefLabel.setText("Correct!");
					else
					{
						int i=0;
						for (i=0; i<entered.length(); i++)
						{
							if (!current.definition.startsWith(entered.substring(0,i)))
								break;
						}
						String result = current.definition;
						if (i != 0)
							result = result.substring(0,i-1) + "<i><b>" + result.substring(i-1);
						else
							result = "<i><b>" + result;
						testDefLabel.setText("<html>Incorrect...<br>" + breakUpString(result));
					}
					checkButton.setText("Hide Answer");
				}
			}
			if (event.getSource() == nextTestButton)
			{
				if (currentCardIndex < cardList.size()-1)
					current = cardList.get(++currentCardIndex);
				else {
					currentCardIndex = 0;
					current = cardList.get(0);
				}
				if (!testAllWords.isSelected())
					while (!current.needToReview())
					{
						if (currentCardIndex < cardList.size()-1)
							current = cardList.get(++currentCardIndex);
						else {
							currentCardIndex = 0;
							current = cardList.get(0);
						}
					}
				if (current.word != null)
					testWordLabel.setText(current.word);
				else
					testWordLabel.setText("This is a picture...");
				testDefLabel.setText("");
				testArea.setText("");
				checkButton.setText("Check Answer");
				checkButton.setEnabled(true);
			}
			if (event.getSource() == testAllWords)
			{
				nextTestButton.setEnabled(true);
				currentCardIndex = 0;
				current = cardList.get(0);
				if (current.word != null)
					testWordLabel.setText(current.word);
				else
					testWordLabel.setText("This is a picture...");
				testDefLabel.setText("");
				testArea.setText("");
				if (!testAllWords.isSelected())
					while (!current.needToReview())
					{
						if (currentCardIndex < cardList.size()-1)
						{
							current = cardList.get(++currentCardIndex);
							if (current.word != null)
								testWordLabel.setText(current.word);
							else
								testWordLabel.setText("This is a picture...");
							testDefLabel.setText("");
							testArea.setText("");
							checkButton.setEnabled(true);
						}
						else
						{
							testWordLabel.setText("No words to be tested!");
							checkButton.setEnabled(false);
							nextTestButton.setEnabled(false);
							break;
						}
					}
				
			}
			if (event.getSource() == doneTestingButton)
			{
				goToOpeningFrame();
			}
		}
	}
	private class InvalidFileNameException extends Exception
	{
	
	}
}
