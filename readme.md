# CORBA in Java 02: A Step-by-Step Guide

## Overview

This guide walks you through creating a simple CORBA application in Java, where a client communicates with a server to receive a message. We'll use Java 1.8 to implement CORBA (Common Object Request Broker Architecture) and define an IDL (Interface Definition Language) interface to facilitate communication between the client and server.

### Prerequisites

- **Java 1.8** must be installed on your machine.
- **CORBA IDL Compiler** (`idlj`) must be available.

### Steps

---

### 1. Install Java 1.8

Ensure you have Java 1.8 installed on your system. You can download it from the official Oracle archive:

- [Download Java SE 8](https://www.oracle.com/java/technologies/javase/javase8-archive-downloads.html)

#### Installation Instructions:
1. Install Java by following the instructions on the website.
2. After installation, add the **bin** folder to your system's `PATH`. This allows Java and CORBA tools like `idlj` to be executed from the command line.

Example `PATH`:
```
C:\Program Files\Java\jdk1.8.0_202\bin
```

### 2. Verify Installation

Check if `idlj` (the IDL compiler) is available by running the following command in your terminal or command prompt:

```bash
idlj -version
```

If the command runs successfully and shows the version, you're ready to proceed.

### 3. Write an IDL File

Create a new file named `banque.idl`. This file defines the CORBA interface, which will be used for communication between the client and server.

```idl
interface Compte {
    double solde();
    boolean credit(in double amount);
    boolean debit(in double amount);
};
```

### 4. Compile the IDL File

Use the CORBA IDL compiler to generate the necessary classes for both client and server communication.

Run the following command:

```bash
idlj -fall hello.idl
```

#### After Compilation:
- A new directory named `hello` will be created.
- Several Java classes will be generated. These include stubs and skeletons necessary for CORBA communication.

Make sure to open and review the generated files to understand the communication structure.

### 5. Implement the server and Client

Now that the IDL has been compiled, implement the server and client in Java. Use the generated classes to handle CORBA object communication.

#### impl_compte_heritage.java

```java
public class impl_compte_heritage extends ComptePOA{

   double solde;

   impl_compte_heritage(double solde){
      this.solde = solde;
   }

   @Override
   public double solde() {
      return solde;
   }

   @Override
   public boolean credit(double amount) {
      if (amount >= 0) {
         this.solde = this.solde + amount;
         return true;
      }
      return false;
   }

   @Override
   public boolean debit(double amount) {
      if (amount <= solde) {
         this.solde = this.solde - amount;
         return true;
      }
      return false;
   }
}

```

#### server.java

```java
public class server {
   public static void main(String[] args) {
      Properties props = System.getProperties();
      int status = 0;
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

   static int run(ORB orb) throws UserException, IOException {
      POA rootPOA;
      POAManager manager;

      rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
      manager = rootPOA.the_POAManager();

      impl_compte_heritage compte_impl = new impl_compte_heritage(5000);
      Compte compte = compte_impl._this(orb);

      String ref = orb.object_to_string(compte);
      String refFile = "reference";
      try (FileOutputStream file = new FileOutputStream(refFile);
           PrintWriter out = new PrintWriter(file)) {
         out.println(ref);
         System.out.println("server en attente des clients\nRef: "+ref);
      } catch (Exception ignored) {}

      try {
         if (manager != null) {
            manager.activate();
         }
         orb.run();
      } catch (Exception e) {
         e.printStackTrace();
         return 1;
      }

      return 0;
   }
}
```

#### ClientBanque.java

```java
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
```

### 6. Run the server and Client

After implementing both the client and server:

1. **Run the server**:
    - Open a terminal and execute the following command:
   ```bash
   java server
   ```

2. **Run the Client**:
    - Open another terminal and execute the following command:
   ```bash
   java Client
   ```

If everything is set up correctly, the client will print `Hello from the server!` to the console.

---

### Summary

This guide showed how to implement a simple CORBA-based client-server system in Java. By following these steps, you've defined a CORBA interface, compiled the necessary Java classes, implemented the server and client, and executed the communication between them.