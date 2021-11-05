ATM System

The ATM operates under the following rules:

● The bills used for withdrawal is removed from the ATM
● The ATM should always return the smallest amount of bills possible
  For example, a withdrawal of 500 should return 1 x 500 bill rather than 5 x 100 bills if possible
● The ATM can't give out more money than it contains
● The ATM can't give out amounts that the remaining bills don’t add up to

src\main\java\Driver.java contains the main function

Default bills in the ATM while initializing ATM object as 
new ATM(str);
where str is String in the format "NoteValue1:NumberOfNotes1;NoteValue2:NumberOfNotes2;...NotesValueN:NumberOfNotesN"

The notes in the ATM object can be updated by calling the updateATM(str) function where str is String in the format "NoteValue1:NumberOfNotes1;NoteValue2:NumberOfNotes2;...NotesValueN:NumberOfNotesN".
Example: updateATM(1000:2;500:3;100:5)
Note that updateATM only adds notes to the atm but does not set them as is.

withdraw(amount) function can be called to make withdrawal. This returns the number and kind of notes that sum up to "amount".
Example: withdraw(1500)

If the ATM does not have enough money or the sum or available notes does not add up to "amount", error is returned.

If all the criteria are met, the bills/notes are deducted from the ATM.

The test suite can be run using the command: mvn test
The test starts the ATM with a predetermined amount of each bill:
● 2 x 1000
● 3 x 500
● 5 x 100

Then, seven withdrawals are made:
1. 1500
2. 700
3. 400
4. 1100
5. 1000
6. 700
7. 300

