import java.util.Scanner;

public  class Main {
    public static void main(String[] args) {


        System.out.println("----Banking Management System----");
        Scanner scan=new Scanner(System.in);

        boolean flag=true;
        while(flag)
        {

            System.out.println("1: Add New users----");
            System.out.println("2: view all users---");
            System.out.println("3: Debit Money----");
            System.out.println("4: credit Money---");
            System.out.println("5: Account Transfer---");
            System.out.println("6: Change user password---");
            System.out.println("7: check balance---");
            System.out.println("8: Transaction history---");
            System.out.println("9: Delete user---");
            System.out.println("10:Exit-----");

            int choice= scan.nextInt();
            switch (choice)
            {
                case 1:
                {
                    System.out.println("New user");
                    break;
                }
                case 2:
                {
                    System.out.println("view All users");
                    break;
                }
                case 3:
                {
                    System.out.println("Debit money");
                    break;
                }
                case 4:
                {
                    System.out.println("credit Money");
                    break;
                }
                case 5:
                {
                    System.out.println("Account transfer");
                    break;
                }
                case 6:
                {
                    System.out.println("Change user Password");
                    break;
                }
                case 7:
                {
                    System.out.println("Check balance");
                    break;
                }
                case 8:
                {
                    System.out.println("transaction history");
                    System.out.println("Mini Statement");
                    break;
                }
                case 9:
                {
                    System.out.println("remove a user from the bank");
                    break;
                }
                case 10:
                {
                    System.out.println("Exit");
                    break;
                }
                default:{
                    System.out.println("Enter the valid choice");
                    break;
                }

            }
        }
    }
}