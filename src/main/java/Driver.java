public class Driver {
    public static void main(String[] args)
    {
        String initString="1000:2;500:3;100:5";

        int[] withdrawals={1500,700,400,1100,1000,700,300};
        ATM atm = new ATM(initString);
        for(int withdrawal:withdrawals)
        {
            System.out.println(atm.withdraw(withdrawal));
        }
}}
