/**
 * Description: Timeslot class storing the position it is in the array,
 *              the course assigned to the timeslot, the start and end 
 *              time as well as the days the timeslots takes place, and
 *              recording the list of timeslot overlap with it
 * 
 * Date Updated: Nov.9, 2021
 * Author: Tianbo Yang, Yitian Cao, Xinran Liu
 */
import java.util.*;

public class TimeSlots {
    /**
     * ID: unique id for Time Slots
     * room: the rooms that are avaiable for this time slot
     * course: assigned courses at this time slot
     * start: start time of each timeslot
     * end: end time of each timeslot
     * weekdays: an array of size 5 recording the days timeslots occupied
     * conflictTimes: arraylist recording the timeslots have conflicts with this
     */
    private int ID; 
    //private Rooms [] room; 
    private ArrayList<Courses> course = new ArrayList<Courses>(); 
    private int start;
    private int end;
    private boolean[] weekdays;
    private ArrayList<TimeSlots> conflictTimes = new ArrayList<TimeSlots>();
    
    // /**
    //  * constructors of TimeSlots
    //  * @param i is the unique id for TimeSlot
    //  */
    // public TimeSlots(int i){
    //     ID = i;
    //     room = null;
    // }

    // /**
    //  * constructors of TimeSlots
    //  * @param i is the unique id for TimeSlot
    //  * @param r the rooms for this timeslot
    //  */
    // public TimeSlots(int i, Rooms [] r){
    //     ID = i;
    //     room = r;
    // }

    // /**
    //  * constructors of TimeSlots
    //  * @param i is the unique id for TimeSlot
    //  * @param c the assigned classes for this time slot
    //  */
    // public TimeSlots(int i, ArrayList<Courses> c){
    //     ID = i;
    //     room = null;
    //     course = c;
    // }

    // /**
    //  * constructors of TimeSlots
    //  * @param i is the unique id for TimeSlot
    //  * @param r the rooms for this timeslot
    //  * @param c the assigned classes for this time slot
    //  */
    // public TimeSlots(int i, Rooms [] r, ArrayList<Courses> c){
    //     ID = i;
    //     room = r;
    //     course = c;
    // }

    /**
     * constructor of timeslots based on the real analysis case
     * @param i ID for the timeslot (position in the array)
     * @param s
     * @param e
     * @param day
     */
    public TimeSlots(int i, int s, int e, String day){
        ID = i;
        start = s;
        end = e;
        weekdays = new boolean[5];
        //checking the days 
        for(int j =0; j< day.length();j++) {
            if(day.charAt(j) == 'M') {
                weekdays[0] = true;
            } 
            if(day.charAt(j) == 'T' && j<day.length()-1) {
                if(day.charAt(j+1)!= 'H'){
                    weekdays[1] = true;
                } else {
                    weekdays[3] = true;
                }
            } 
            if(day.charAt(j) == 'T' && j == day.length()-1) {
                weekdays[1] = true;
            }
            if(day.charAt(j) == 'W') {
                weekdays[2] = true;
            }
            if(day.charAt(j) == 'F') {
                weekdays[4] = true;
            }
        }
    }

    /** setters */
    public void setID(int i){
        ID = i;
    }
    // public void setRoom(Rooms [] r){
    //     room = r;
    // }

    public void setCourse(ArrayList<Courses> c){
        course = c;
    }

    public void setConflictTime(ArrayList<TimeSlots> ct){
        conflictTimes = ct;
    }

    /** getters */
    public int getID(){
        return ID;
    }

    // public Rooms [] getRooms(){
    //     return room;
    // }

    public ArrayList<Courses> getCourse(){
        return course;
    }

    public ArrayList<TimeSlots> getConflictTime(){
        return conflictTimes;
    }

    /**
     * find the class assigned to the given room 
     * @param r the given room
     * @return the class assigned in this timeslot in room r
     */
    public Courses getClass(Rooms r){
        return course.get(r.getID());
    }

    /**
     * assign class c into the timeslot
     * @param c the course assigned to the timeslot
     */
    public void addClass(Courses c){
        course.add(c);
    }

    /**
     * recording the timeslots that has overlapped with this
     * @param t timeslot overlap with this
     */
    public void addConflictTime(TimeSlots t){
        conflictTimes.add(t);
    }

    /**
     * checking if t1 timeslot is overlapping with this one
     * @param t1
     * @return
     */
    public boolean isOverlapping(TimeSlots t1) {
        //check if the timeslots will be at least one day on the same day
        boolean sameDay = false;
        for(int i=0; i< 5;i ++) {
            if(weekdays[i] && t1.weekdays[i]) {
                sameDay = true;
                break;
            }
        }
        //not on the same day -> will have no overlap
        if(sameDay == false) return false;
        //elsewise: check if the time interval is overlapping
        if((t1.start<=start && start <=t1.end)|| (t1.start<=end  && end <=t1.end)
         || (start<=t1.start && end >= t1.end) || (start>=t1.start && end<=t1.end)) {
            return true;
        }
        return false;
    }

    /**
     * check if a room at this time slot is assigned to some class
     * @param r the room to check
     * @return booleam if the room is assigned, true to be 
     *          assigned and false to be not assigned
     */
    public boolean isAssigned(Rooms r){
        return course.get(r.getID()) != null;
    }

}
