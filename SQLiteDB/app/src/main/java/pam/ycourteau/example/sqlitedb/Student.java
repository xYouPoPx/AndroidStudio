package pam.ycourteau.example.sqlitedb;

/**
 * Created by ycourteau on 15-09-21.
 */
public class Student {
    // Labels table name
    public static final String TABLE = "Student";

    // Labels Table Columns names
    public static final String KEY_ID = "id";
    public static final String KEY_name = "name";
    public static final String KEY_email = "email";
    public static final String KEY_age = "age";

    // property help us to keep data
    public int student_ID;
    public String name;
    public String email;
    public int age;
}

