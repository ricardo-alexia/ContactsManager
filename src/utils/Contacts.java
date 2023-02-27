package utils;

import javax.xml.crypto.dsig.spec.XPathType;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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

      public static  Map<String, String> map
                = new HashMap<String, String>();
    public static Map<String, String> HashMapFromTextFile() {

        BufferedReader br = null;

        try {

            // create file object
            File file = new File(filePath);

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
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {

            // Always close the BufferedReader
            if (br != null) {
                try {
                    br.close();
                }
                catch (Exception e) {
                };
            }
        }

        return map;
    }

    public static void print() {

        Map<String, String> mapFromFile
                = Contacts.HashMapFromTextFile();

        System.out.println("""
                Name | Phone Number
                -------------------""");
        for (Map.Entry<String, String> entry :
                mapFromFile.entrySet()) {
            System.out.println(entry.getKey() + " | "
                    + entry.getValue());
        }
    }

    public static void addContacts() {
        System.out.println("Please enter a name for your new contact:");
        String name = userInput.getString();
        System.out.println("Please enter your contacts number");
        String number = userInput.getString();

        try {
            Files.writeString(pathToFile, String.format("%s:%s", name, number), StandardOpenOption.APPEND);
        } catch (IOException iox){
            iox.printStackTrace();
        }

        print();
    }

    public static void deleteContact() {
        print();
        System.out.println("Please enter the contact you'd like to delete:");
        String name = userInput.getString();

//        List<String> currentList = new ArrayList<>();
//        try {
//            currentList = Files.readAllLines(pathToFile);
//        } catch (IOException iox){
//            iox.printStackTrace();
//        }

//        map.remove();

//        print();

//        currentList.removeIf(item -> item.equals(name));
//        try {
//            Files.write(pathToFile, currentList);
//        } catch (IOException iox) {
//            iox.printStackTrace();
//        }
    }


}

