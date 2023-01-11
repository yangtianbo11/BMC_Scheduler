import java.util.ArrayList;
/* The Courses will contain information about one course
 */
public class Courses{
    //instances field
    private int ID; //unique id for a course
    private ArrayList<Students> registrationList = new ArrayList<Students>();
                    //a list of registered students
    private Professors prof; // the professor who will teach the class
    private int popularity; // the number of students who want to take this class
    private Rooms room; // the assigned room for this class
    private ArrayList<Rooms> validRooms = new ArrayList<Rooms>(); 
                    //list of valid rooms this class can be taught in
    private TimeSlots time; //the assigned time for this class
    private int [] classConflict; //the index of the array is the each class and the value represents
                                  //the conflict number of this class with the class at certain index
    private int [] finalConflict; //the index of the array is the time slot and the value represents
                                  //a value that represents the conflict score at the given time slot
    private String name;
    private String subject; //the subject for this class determines the lab room assignment
    private boolean lab; //does this class have a lab
    /* Constructor
     * @param i, the id of the course
     * @param cap, the number of classes to be scheduled
     * @param t, number of timeslots
     */
    public Courses (int i, int cap, int t, String n){
        ID = i;
        //registration list already initialized
        classConflict = new int[cap];
        finalConflict = new int[t];
        prof = null;
        popularity = 0;
        room = null;
        time = null;
        name = n;
        subject = null;
        lab = false;
    }

    //getters and setters
    public int getID(){ 
        return  ID;
    }

    public ArrayList<Students> getReg(){
        return registrationList;
    }

    public int getPop(){
        return popularity;
    }

    public Rooms getRoom(){
        return room;
    }

    public ArrayList<Rooms> getValidRooms(){
        return validRooms;
    }

    public Professors getPro(){
        return prof;
    }

    public TimeSlots getTime(){
        return time;
    }

    public String getSubject(){
        return subject;
    }

    public boolean hasLab(){
        return lab;
    }

    public void incrConflict(Courses c){
        classConflict[c.ID]++;
    }

    public void incrConflict(int c){
        classConflict[c]++;
    }

    public int getCConflict(Courses c){
        return classConflict[c.ID];
    }

    public int getCConflict(int c){
        return classConflict[c];
    }

    public int getFConflict(Courses c){
        return finalConflict[c.ID];
    }

    public int getFConlcit(int c){
        return finalConflict[c];
    }

    public String getName(){
        return name;
    }

    public void setFConflict(Courses c, int i){
        finalConflict[c.ID] = i;
    }

    public void setFConflict(int c, int i){
        finalConflict[c] = i;
    }

    public void setProf(Professors p){
        prof = p;
    }

    public void setRoom(Rooms r){
        room = r;
    }

    public void setValidRooms(ArrayList<Rooms> vR){
        validRooms = vR;
    }

    public void setTime(TimeSlots t){
        time = t;
    }

    public void setSubject(String s){
        subject = s;
    }

    public void setLab(boolean l){
        lab = l;
    }

    //increment popularity of the class
    public void incrPop(){
        popularity++;
    }

    //increment the conflict number of this class and another class c
    public void incrCon(Courses c){
        classConflict[c.ID]++;
    }

    public void incrCon(int c){
        classConflict[c]++;
    }

    public void setCon(Courses c, int i){
        classConflict[c.ID] = i;
    }

    public void setCon(int c, int i){
        classConflict[c] = i;
    }

    //add a student s to the registration list 
    public void addStu(Students s){
        registrationList.add(s);
    }

    //to see if the class is scheduled or not
    public boolean notScheduled(){
        return (time==null)||(room==null);
    }

    //evaluates whether the class has a lab section based on subject
    public void toLab(){
        if(subject.equals("MATH") 
        || subject.equals("CHEM") 
        || subject.equals("PSYC") 
        || subject.equals("BIOL") 
        || subject.equals("ECON")
        || subject.equals("PHYS")
        || subject.equals("CMSC")){
            lab = true;
        }
    }
}