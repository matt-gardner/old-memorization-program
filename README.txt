Memorization Program
README

Created and copyrighted by Matthew Gardner

Feel free to use this program however you wish, but I ask that you do not
distribute it unless granted permission by me.

Email: draifen@gmail.com
Last updated March 29, 2008

How to use the program:

In order to run the program, put the Memorization Program folder in your
"Program Files" folder (on a PC - in the "Applications" folder on a Mac), and
double click the Memorization Program.cmd file.  It should work fine from
there.  (On a Mac it could be more complicated.  You might need to change the
execution privilege for the .command file.  Instructions for doing that are at
the end of this file.) At start up, the program will load the last file saved.
If no file has been saved, it will load a blank list.  The program should be
pretty self explanatory, but here are a few tips:

This program works best when it is used daily, as it has an algorithm that
decides what words should be reviewed.  It reviews words once a day for a week,
once a week for a month, and once a month after that, until such time as the
user deletes the word.  There are mechanisms to override the algorithm, of
course, and the reset word button in the review panel resets the day the word
was entered to the present day.

Keyboard navigation is possible by holding alt (option on a Mac).  The keys to
press the various buttons are then underlined (on a PC they should already be
underlined).  

If a button is highlighted in blue (on a Mac - I think it is outlined on a PC),
pressing Enter will press the button.  Pressing enter while in text fields will
also do what you would expect (in the save words as panel it will hit save, in
the add words panel it will add the word, in the view/edit words panel it will
put in a new line).

To add a picture, put the picture in the memorizingpictures folder, then in the
add words panel put the name of the file in the word text area, with its
description in the definition text area, and click the "This is a picture" box
before adding the word.  If you enter a file name that doesn't exist or isn't
in the folder, the program won't warn you - when you get to the word, it will
just show up blank.  Make sure the pictures aren't too big.  If there are no
pictures in your list, the review panel will be its normal size.  If there are
pictures the panel gets bigger, but still, pictures much bigger than 150 pixels
on each side will be too big for the program.  If that happens, you can still
navigate out by pressing alt+d, then you can delete the picture from the
View/Edit words panel.  

View/Edit panel:

For ease of programming, the format of the text in this panel is perhaps a
little awkward.  The first line is the word, the second is the definition, the
third is a line saying whether or not the "word" is a picture, the fourth is
the day of the year the word was entered into the program, and the fifth is the
year (those two numbers are used for the reviewing algorithm to decide whether
or not to show the word).  Any changes you make to the words or definitions
while in this panel are made in the list of words itself.  When you hit "accept
changes," the format needs to still be the same, with no extra or missing
lines, or the program will give you an error message and won't make the
changes.  The changes made are not written to file until you save them with the
save button.  Here is where you can delete words or modify the definition of a
word if you wish.  To delete a word, just delete all five of its lines, making
sure there is no extra space in between the words before and after it.  This is
the same format that is used in the .mem files, so if you wish you can also
make the changes to that file.  But be careful, because if you mess it up the
file won't load anymore.  At least in the view/edit panel if you make bad
changes it keeps what you had before.

If you have any questions, find any bugs, or think of some cool addition to the
program, send me an email.

Changing execution privileges on a Mac:

If you try double clicking the Memorization Program.command file and it doesn't
work, the file probably is not set to be executable.  To fix that, open a
terminal and navigate to the folder where the file is, by typing something like
this:

cd /Applications/Memorization\ Program/

When you are in the right folder, type this:

chmod 755 Memorization\ Program.command

You should then be able to double click the icon and start the program.
