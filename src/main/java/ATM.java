import java.util.LinkedHashMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ATM
{
    private static final Logger logger=Logger.getLogger(ATM.class.getName());
    private int currentBalance=0;
    //A dictionary where the key is the face-value of the note and the value is the number of bills
    private TreeMap<Integer,Integer> bills=new TreeMap<>();
    public ATM(String rawString) {
        updateBills(rawString);
    } //Constructor with String
    public ATM() {} //Empty Constructor

    private void updateBills( String rawString)
    {
        //Adds new notes to the system.
        //Input: "note1:NumberOfNotes1;note2:NumberOfNotes2....noteN:NumberOfNotesN"
        try
        {
        logger.log(Level.FINE,"Initial bills:{0} \nUpdate ATM Bills Started for string: {1}",new Object[]{ bills.toString(),rawString});
        String[] valueAndBills=rawString.strip().split(";");
            for (String valueAndBill : valueAndBills) {
                String[] valueNBills = valueAndBill.strip().split(":");
                int value = Integer.parseInt(valueNBills[0].strip());
                int numberOfBills = Integer.parseInt(valueNBills[1].strip());
                //if notes of the denomination already exist in the system, add them to existing notes
                if (bills.containsKey(value)) {
                    bills.put(value, bills.get(value) + numberOfBills);
                }
                //if notes of the denomination do not already exist in the system, add the denomination to the system and add the number of notes
                else {
                    bills.put(value, numberOfBills);
                }
                currentBalance += value * numberOfBills;
            }
        }

        catch(Exception ex)
            {
                logger.log(Level.FINE,"Exception: {0} \t Input string: {1}", new Object[]{ex.toString(), rawString});
                return;
            }

        logger.log(Level.FINE,"Update complete. Bills: {1}", bills.toString());
    }

    private void removeBills(LinkedHashMap<Integer,Integer> valuesNBills)
    {
        //Reduces the number of notes from the existing number of notes "bills" and updates "currentBalance"
        //Input: A LinkedHashMap in the format<face-value,NumberOfNotes>
        Set<Integer> billValues=valuesNBills.keySet();
        for(int value:billValues)
        {
            if(bills.containsKey(value)) {
                if(bills.get(value)>=valuesNBills.get(value))
                {
                    bills.put(value, bills.get(value)-valuesNBills.get(value));
                    currentBalance=currentBalance+valuesNBills.get(value)*value;
                }
                else
                    throw new CustomException("Bills could not be adjusted for the amount!", "Try a different amount!");
            }
            else
                throw new CustomException("Unknown Error!","Please try again!");
        }
    }

    public void updateATM(String rawString)
    {
        updateBills(rawString);
    }

    public String getBills()
    {
        //returns a string that contains the face-value of notes and the number of notes in the format "note1:NumberOfNotes1\note2:NumberOfNotes2"
        String finalString="";
        for(int value:bills.keySet())
        {
            finalString+=value+":"+bills.get(value)+"\n";
        }
        return finalString;
    }

    public String getBalance()
    {
        //returns the current Balance in the system
        return Integer.toString(currentBalance);
    }

    public String withdraw(int amount)
    {
        //Computes the number of notes of each denomination that sum up to "amount" such that minimum number of notes are used
        try {
            if (amount > currentBalance) {
                throw new CustomException("Not enough Balance in the ATM!", "Try a smaller amount!");
            }
            //valuesNBills is a LinkedHashMap of format<face-value,NumberOfBills>
            LinkedHashMap<Integer, Integer> valuesNBills = new LinkedHashMap<>();
            // foreach over decreasing denominations to  ensure minimum number of required notes
            for (int value : bills.descendingKeySet()) {
                //if notes of a particular value are empty, go to the next highest valued notes
                if (bills.get(value) <= 0)
                    continue;
                //noOfBills would be the number of notes required for a denomination
                int noOfBills = amount / value;
                if (noOfBills != 0) {
                    //if number of bills required is less than the bills available, add to valuesNBills and update amount
                    if (noOfBills <= bills.get(value)) {
                        valuesNBills.put(value, noOfBills);
                        amount = amount % value;
                    }
                    else {
                        //if number of bills required is more than the bills available, add all the notes available to valuesNBills and update amount
                        valuesNBills.put(value, bills.get(value));
                        amount -= bills.get(value);
                    }
                }
            }
            // If after looping through all denominations, remaining amount is not zero '0', Bills could not be adjusted
            if (amount != 0) {
                throw new CustomException("Bills could not be adjusted for the amount!", "Try a different amount!");
            }
            removeBills(valuesNBills);

            //finalString is the string to  be returned
        String finalString="";
        //Convert Hashmap to string format "Face-Value1:NumberOfNotes1\nFace-Value2:NumberOfNotes2\n..."
        for(int value:valuesNBills.keySet())
        {
            finalString+=value+":"+valuesNBills.get(value)+"\n";
        }
        return finalString;
        }
        catch (CustomException ce)
        {
            //if any problem occurs, return the problem along with possible solution
            return "Problem:"+ce.message+"\nPossible Solution:"+ce.solution+"\n";

        }
        catch (Exception ex)
        {
            logger.log(Level.FINE,"Exception: {0} ", ex.toString());
            return "Unknown problem";
        }
    }
}
