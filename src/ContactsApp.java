import utils.Contacts;

import java.util.Map;

public class ContactsApp{


    private static void display(){
        System.out.println("""
                1. View contacts.
                2. Add a new contact.
                3. Search a contact by name.
                4. Delete an existing contact.
                5. Exit.
                Enter an option (1, 2, 3, 4 or 5):
                """);
    }

    public static void main(String[] args) {


//      Contacts.addContacts();
        Contacts.deleteContact();
    }




}