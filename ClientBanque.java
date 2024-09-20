/*
 * ABDELHAKIM KHAOUITI
 * 20 SEP 2024
 */

import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.Scanner;

public class ClientBanque {
    public static void main(String[] args) {
        Properties props = System.getProperties();
        int status;
        ORB orb = null;

        try {
            orb = ORB.init(args, props);
            status = run(orb);
        } catch (Exception ex) {
            ex.printStackTrace();
            status = 1;
        }

        if (orb != null) {
            try {
                orb.destroy();
            } catch (Exception ex) {
                ex.printStackTrace();
                status = 1;
            }
        }

        System.exit(status);
    }

    static int run(ORB orb) throws IOException {
        Object obj;

        String refFile = "reference";
        try (FileInputStream file = new FileInputStream(refFile);
             BufferedReader in = new BufferedReader(new InputStreamReader(file))) {
            String ref = in.readLine();

            obj = orb.string_to_object(ref);
            Compte compte = CompteHelper.narrow(obj);
            int choice;
            double amount = 0.0;
            Scanner scanner = new Scanner(System.in);

            // Display menu
            System.out.println("+---------------------------+");
            System.out.println("|      Service bancaire     |");
            System.out.println("+---------------------------+");
            System.out.println("1 : Lecture du montant du compte");
            System.out.println("2 : Crédit du compte");
            System.out.println("3 : Débit du compte");
            System.out.println("0 : Quitter");

            do {
                choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        System.out.println("Lecture du montant du compte");
                        System.out.println(compte.solde());
                        break;
                    case 2:
                        System.out.println("Crédit du compte");
                        System.out.println("Taper le montant: ");
                        amount = scanner.nextDouble();
                        System.out.println(compte.credit(amount));
                        break;
                    case 3:
                        System.out.println("Débit du compte");
                        System.out.println("Taper le montant: ");
                        amount = scanner.nextDouble();
                        System.out.println(compte.debit(amount));
                        break;
                    case 0:
                        System.out.println("Quitter");
                        break;
                    default:
                        System.out.println("Choix invalide, veuillez réessayer.");
                }
            } while (choice != 0);
        }

        return 0;
    }
}
