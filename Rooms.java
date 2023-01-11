/**
 * Description: Rooms storing its position in the array, info of room position,
 *              capacity, course held in this room and an array of all times,
 *              index storing null if room in the timeslot with timeslot id 
 *              as the index does not have a class assigned in it.
 * 
 * Date Updated: Nov.9, 2021
 * Author: Tianbo Yang, Yitian Cao, Xinran Liu
 */
public class Rooms {
    /**
     * ID: unique id for a room (the position it is in the array)
     * name: name of each class room, i.e., position + room number
     * capacity: the maximum number of students could take class in this room
     * course: courses assigned to this room
     * time: an array of all timeslots and each index is the timeslots id, 
     *         storing the class at that tine and in the room
     */
    private String name;
    private int capacity; 
    private Courses [] course; 
    private TimeSlots [] time; 

    /**
     * constructor
     * @param i unique id for a room (the position it is in the array)
     * @param c the maximum number of students could take class in this room
     * @param t an array of all timeslots and each index is the timeslots id, 
     *              storing the class at that tine and in the room
     * @param n name of each class room, i.e., position + room number
     */
    public Rooms (int c, TimeSlots [] t,String n){
        capacity = c;
        time= t;
        course = new Courses[t.length];
        name = n;
    }

    /** getters */
    public int getCap(){
        return capacity;
    }

    public Courses [] getCourse(){
        return course;
    }

    public TimeSlots [] getTime(){
        return time;
    }

    public String getName() {
        return name;
    }

    /** setters */
    public void setCap(int c){
        capacity = c;
    }

    public void setCourse(Courses [] c){
        course = c;
    }

    public void setTime(TimeSlots [] t){
        time = t;
    }

    public void setName(String n) {
        name = n;
    }

    public void setTime(TimeSlots t, int timeID){
        time[timeID] = t;
    }

    /**
     * get the course on timeslot t
     * @param t the timeslot we want to check course
     * @return
     */
    public Courses getTCourse(TimeSlots t){
        return course[t.getID()];
    }

    /**
     * record the course happened in this course
     * @param c
     * @param t
     */
    public void addCourse(Courses c, TimeSlots t){
        int tID = t.getID();
        course[tID] = c;
    }

    /**
     * checking this room could be assigned at some timeslot t
     * @param t timeslot to check
     * @return boolean if some could be assigned
     */
    public boolean isAssigned(TimeSlots t){
        boolean isoverlap=false;
        //checking if all timeslots that the room has class in overlap
        //with t
        for(int i=0; i<time.length;i++) {
            if(time[i]!= null){
                if(time[i].isOverlapping(t)) {
                    isoverlap = true;
                    break;
                }
            }
        }
        return isoverlap;
    }

}
