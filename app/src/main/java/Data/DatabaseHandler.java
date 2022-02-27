package Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import Model.Contact;
import Utils.Util;

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    /* Create Tables */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        /* SQL = Structured Query language , not case sensitive
         Databases have their own scripting  language that understands
         creating tables , deleting , updating so on. we can concatnate java and sql to actually
         create a database.
         Just backspace and comma can help remove error in SQL */
       /* String CREATE_CONTACT_TABLE = " CREATE TABLE " + Util.TABLE_NAME + "("
                + Util.KEY_ID + " INTEGER PRIMARY KEY, " + Util.KEY_NAME + "TEXT,"
                + Util.KEY_PHONE_NUMBER + " TEXT " + ")";*/

        String CREATE_CONTACT_TABLE = "CREATE TABLE " + Util.TABLE_NAME + " ("
                + Util.KEY_ID + " INTEGER PRIMARY KEY, "
                + Util.KEY_NAME + " TEXT, "
                + Util.KEY_PHONE_NUMBER + " TEXT"
                + ");";

        sqLiteDatabase.execSQL(CREATE_CONTACT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // dropping is deleting the table
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Util.TABLE_NAME);
        // create table again
        onCreate(sqLiteDatabase);
    }

    /*
     * Now we are going to create some CRUD operations
     * create , read , update, delete
     * First Add Contact  , instanciate a database
     **/

    // ADD CONTACT
    public void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        /* content values is data abstraction just like hash map  , list and so on
         add key value pair to objects and
         objects insert into into table
         create contect object and get the value of key " name "
         we are not inserting id
         id will b added incremented */
        ContentValues value = new ContentValues();
        value.put(Util.KEY_NAME, contact.getName());
        value.put(Util.KEY_PHONE_NUMBER, contact.getPhoneNumber());
        // insert to row
        db.insert(Util.TABLE_NAME , null, value);
        db.close();
    }

    /* we want to retrive a contact so its type of a contact , pass id because id is unique */

    // GET ONE CONTACT
    public Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        /* create a cursor which is object , which is a class that allow us to go/itrate through our
         database and get data
         Constructing a query that allow us to get all info pretending to the item with id that we are passing */
        Cursor cursor = db.query(Util.TABLE_NAME, new String[]{Util.KEY_ID
                        , Util.KEY_NAME, Util.KEY_PHONE_NUMBER}, Util.KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        /* first pass table name
          then we pass string array of all the items we want to retrive, once we pass the id
           */
        if (cursor != null)
            cursor.moveToFirst();
        // 0,1,2 are columns
        assert cursor != null;
        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));
        cursor.close();
        return contact;
    }

    // GET ALL CONTACT
    public List<Contact> getAllContact() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Contact> contactList = new ArrayList<>();
        // select al contact
        // astrisk means all
        String selectAll = "SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll, null);
        // loop through our contacts
        // if cursor has something
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));
                // add contact object to our contact list
                contactList.add(contact);
            } while (cursor.moveToNext());

        }
        cursor.close();
        return contactList;
    }

    // UPDATE CONTACT
/* int because after update database it returns Int or Id or index no of that row we updated,*/
    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util.KEY_NAME, contact.getName());
        values.put(Util.KEY_PHONE_NUMBER, contact.getPhoneNumber());
        // update row
       return  db.update(Util.TABLE_NAME, values, Util.KEY_ID + "=?",
                new String[]{String.valueOf(contact.getId())});

    }

    // DELETE SINGLE CONTACT
    public void deleteContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Util.TABLE_NAME , Util.KEY_ID + " = ? " ,
                new String[]{String.valueOf(contact.getId())});
        db.close();
    }

    // GET CONTACTS COUNT
    public int getContactsCount(){
        String countQuery = "SELECT * FROM " + Util.TABLE_NAME;
        SQLiteDatabase db  = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery , null);
       // cursor.close();
        return cursor.getCount();
    }
}
