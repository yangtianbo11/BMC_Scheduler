/**
 * Description: Class of Professors. Professor with have a name, stored in String 
 *              and a list of courses it teach, stored in ArrayList
 * 
 * Author: Tianbo Yang, Yitian Cao, Xinran Liu
 * Date Updated: Nov.9, 2021
 */
import java.util.*;

public class Professors {
    /**
     * ID: the position it is in the array
     * name: the professor id
     * teach: the list of course it will teach
     */
    private int ID;
    private String name;
    private ArrayList<Courses> teach = new ArrayList<Courses>(); 

    /**
     * constructor of the professor based on real life cases
     * @param n the "ID" in the text given -> we consider this as 
     *          the name of the professor
     */
    public Professors(String n) {
        name = n;
    }

    /**
     * constructor of the professor based on unrealistic cases
     * @param i ID, professor's position in the array
     */
    public Professors(int i){
        ID = i;
    }

    /** getters */
    public int getID(){
        return ID;
    }

    public String getName(){
        return name;
    }

    public ArrayList<Courses> getCourse(){
        return teach;
    }

    /** setters */
    public void setID(int i){
        ID = i;
    }

    public void setName(String n){
        name = n;
    }

    public void setCourse(ArrayList<Courses> c){
        teach = c;
    }

    /**
     * adding a course that the prof will teach
     * @param c
     */
    public void addCourse(Courses c){
        teach.add(c);
    }

    //check if this professor and another professor is the same professor
    public boolean equals(Professors a){
        if(this.name == null || a.getName()==null) {
            return false;
        }
        return (a.getName().equals(name));
    }
}
