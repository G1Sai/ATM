import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestATM {

    private ATM atm;
    @Before
    public void setAtm()
    {
        atm=new ATM("1000:2;500:3;100:5");
    }

    @Test
    public void testWithdraw()
    {
        assertEquals("1000:1\n" +
                "500:1\n", atm.withdraw(1500));

        assertEquals("500:1\n" +
                "100:2\n", atm.withdraw(700));

        assertEquals("Problem:Bills could not be adjusted for the amount!\n" +
                "Possible Solution:Try a different amount!\n", atm.withdraw(400));

        assertEquals("1000:1\n" +
                "100:1\n", atm.withdraw(1100));

        assertEquals("Problem:Bills could not be adjusted for the amount!\n" +
                "Possible Solution:Try a different amount!\n", atm.withdraw(1000));

        assertEquals("500:1\n" +
                "100:2\n", atm.withdraw(700));

        assertEquals("Problem:Bills could not be adjusted for the amount!\n" +
                "Possible Solution:Try a different amount!\n", atm.withdraw(300));

    }

}
