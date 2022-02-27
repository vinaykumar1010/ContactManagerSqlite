package com.vinappstudio.contactmanagersqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import Data.DatabaseHandler;
import Model.Contact;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseHandler db = new DatabaseHandler(this);





        // Insert Contacts
        Log.d("Insert", "Inserting....");
        db.addContact(new Contact("Paul", "123456"));
        db.addContact(new Contact("John", "5857347564"));
        db.addContact(new Contact("Rose", "4345454343"));
        db.addContact(new Contact("Bella", "35553445"));
        // read them back
        Log.d("Reading", "Reading all contacts...");
        List<Contact> contactList = db.getAllContact();

        // get count
        Log.d("DB Count: ", String.valueOf(db.getContactsCount()));


        // Get all contact
//        for(Contact c : contactList){
//         String log = "ID: " + c.getId() + " , Name: " + c.getName() + ", Phone: " + c.getPhoneNumber();
//         Log.d("Name" , log);
//         }

        // Get one Contact
//        Contact oneContact = db.getContact(1);
//        Log.d("One Contact: "," Name: "+ oneContact.getName() +" Phone: " + oneContact.getPhoneNumber());

        // Update Contact
//        Contact oneContact = db.getContact(2);
//        oneContact.setName("Vinay");
//        oneContact.setPhoneNumber("000000");
//        int newContact = db.updateContact(oneContact);
//        Log.d("One Contact: ", "Updated Row:" + String.valueOf(newContact)
//                + " Name: " + oneContact.getName() + " Phone: " + oneContact.getPhoneNumber());

       // Delete an item
        Contact oneContact = db.getContact(1);
        db.deleteContact(oneContact);
        for(Contact c : contactList){
            String log = "ID: " + c.getId() + " , Name: " + c.getName() + ", Phone: " + c.getPhoneNumber();
            
            Log.d(" Name " , log);


        }

    }
}