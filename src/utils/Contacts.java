package utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import static jdk.internal.org.jline.keymap.KeyMap.display;

public class Contacts {

    final static String filePath = "src/contacts.txt";

    public static Path pathToFile = Paths.get(filePath);

    static Input userInput = new Input();

    static File file = new File(filePath);

    public static Map<String, String> map
            = new HashMap<String, String>();




    ///// BASIC MENU //////
    public static void display(){
        Contacts.HashMapFromTextFile();
        System.out.print("""
                1. View contacts.
                2. Add a new contact.
                3. Search a contact by name.
                4. Delete an existing contact.
                5. Exit.
                Enter an option (1, 2, 3, 4 or 5):
                """);
    }



    ///// METHOD THAT READS THE TEXT FILE AND ADDS TO THE HASH MAP//////

    public static Map<String, String> HashMapFromTextFile() {

        BufferedReader br = null;

        try {

            // create BufferedReader object from the File
            br = new BufferedReader(new FileReader(file));

            String line = null;

            // read file line by line
            while ((line = br.readLine()) != null) {

                // split the line by :
                String[] parts = line.split(":");

                // first part is name, second is number
                String name = parts[0].trim();
                String number = parts[1].trim();

                // put name, number in HashMap if they are
                // not empty
                if (!name.equals("") && !number.equals(""))
                    map.put(name, number);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            // Always close the BufferedReader
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                }
                ;
            }
        }

        return map;
    }


    /// METHOD THAT PRINTS THE HASH MAP TO THE CONSOLE/////
    public static void print() {

        Map<String, String> mapFromFile
                = Contacts.HashMapFromTextFile();

        System.out.println("""
    
                Name | Phone Number
                -------------------""");
        for (Map.Entry<String, String> entry :
                mapFromFile.entrySet()) {
            System.out.println(entry.getKey() + " | "
                    + entry.getValue().replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1) $2-$3"));   //(123) 456-7890);
        }
        System.out.println();
    }


    //// METHOD TO ADD A CONTACT TO THE TEXT FILE AND HASH MAP ///////

    static String number;
    public static void addContacts() {

        System.out.println("Please enter a name for your new contact:");
        String name = userInput.getString();
        if(map.containsKey(name)) {
            System.out.println("This contact already exist would you like to overwrite it?(y/n)");
            String answer = userInput.getString();
            if (answer.equalsIgnoreCase("y")) {
                System.out.println("Please enter your contacts number");
                number = userInput.getString();
                map.replace(name, number);
                }
        } else {
            System.out.println("Please enter your contacts number");
            number = userInput.getString();
        }
        try {
            Files.writeString(pathToFile, String.format("%s:%s%n", name, number), StandardOpenOption.APPEND);
        } catch (IOException iox) {
            iox.printStackTrace();
        }
        print();
    }

    //// METHOD TO DELETE A CONTACT TO THE TEXT FILE AND HASH MAP ///////
    public static void deleteContact() {
        print();
        System.out.println("Please enter the contact you'd like to delete:");
        String name = userInput.getString();

        map.remove(name);

        BufferedWriter bf = null;
        try {
            // create new BufferedWriter for the output file
            bf = new BufferedWriter(new FileWriter(file));

            // iterate map entries
            for (Map.Entry<String, String> entry :
                    map.entrySet()) {

                // put key and value separated by a colon
                bf.write(entry.getKey() + ":"
                        + entry.getValue());

                // new line
                bf.newLine();
            }

            bf.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {

            try {

                // always close the writer
                bf.close();
            }
            catch (Exception e) {
            }
        }

        print();

    }

    //// METHOD TO SEARCH FOR A CONTACT IN THE HASH MAP AND PRINT OUT THAT CONTACT///////
    public static void searchContact()
    {
        System.out.println("Please enter the name of the contact you are searching for: ");
        String name = userInput.getString();
        map.get(name);

        System.out.printf("%s | %s%n%n", name, map.get(name));
    }


    ////// METHOD THAT PULLS ALL THE OTHER METHODS TOGETHER ///////
    public static void userOptions()
    {
        display();
        String numberIn = userInput.getString();
        switch(Integer.parseInt(numberIn))
        {
            case 1:
                print();
                userOptions();
                break;
            case 2:
                addContacts();
                userOptions();
                break;
            case 3:
                searchContact();
                userOptions();
                break;
            case 4:
                deleteContact();
                userOptions();
                break;
            case 5:
                System.out.println("");
                break;
            default:
                System.out.println("Please enter a valid option...");
                userOptions();
        }
    }

}


