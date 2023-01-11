/**
 * Description: Student class storing the position it is in the array,
 *              its preferred class list, the class list it successfully
 *              enrolled and its student ID shown in the file
 * 
 * Date Updated: Nov.9, 2021
 * Author: Tianbo Yang, Yitian Cao, Xinran Liu
 */
import java.util.*;

public class Students {
    /**
     * ID: unique id for each student, the position it is in the array
     * pref: a list of classes the student would like to take
     * reg: a list of final classes the student would take
     * name: the student ID it has read from the file
     */
    private int ID; 
    private ArrayList<Courses> pref = new ArrayList<Courses>();        
    private ArrayList<Courses> reg = new ArrayList<Courses>(); 
    private String name; 

    /* constructor
     * @param i, the unique id of the student
     */
    public Students(int i, String n){
        ID = i;
        name = n;
        //reg is already initialized
    }
    
    /** getters */
    public int getID(){
        return ID;
    }

    public ArrayList<Courses> getReg(){
        return reg;
    }

    public ArrayList<Courses> getPref(){
        return pref;
    }

    public String getName() {
        return name;
    }

    /** setters */
    public void setID(int i){
        ID = i;
    }

    public void setReg(ArrayList<Courses> r){
        reg = r;
    }

    public void setPref(ArrayList<Courses> p){
        pref = p;
    }

    public void setName(String n) {
        name = n;
    }

    /**
     * add the course read from the file to student's preference list
     * @param c
     */
    public void addPref(Courses c){
        pref.add(c);
    }

    /* addReg will add the course into the student's registration list
     * @param c, the course that the student will take
     */
    public void addReg(Courses c){
        reg.add(c);
    }

    /**
     * get the number of courses student registered
     * @return
     */
    public int getRegNum(){
        return reg.size();
    }
}
