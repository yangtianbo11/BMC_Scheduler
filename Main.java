/**
 * Description: Main class to read file, store information from the file as different instances
 *              Schedule classes into classrooms and time slots and enroll students in
 * 
 * Author: Tianbo Yang, Yitian Cao, Xinran Liu
 * Date Updated: Nov.9, 2021
 */
import java.io.*;
import java.util.*;

public class Main {
    /**
     * global instances to store all variables
     */
    public static Students [] stu;
    public static Courses [] classes;
    public static HashMap<String,Professors> professors;
    public static TimeSlots [] time;
    public static Rooms [] room;

    /**
     * For each Time slot, checking all other times slots and record
     * those time slots have schedule conflitcs with it. 
     */
    public static void setConflictTimes(){
        for(TimeSlots t: time){
            for(TimeSlots tt:time) {
                if(t.isOverlapping(tt) && !t.equals(tt)){
                    t.addConflictTime(tt);
                    tt.addConflictTime(t);
                }
            }
        }
        //sort the array by number of conflicts
        Arrays.sort(time, new Comparator<TimeSlots>() {
            @Override
                 //arguments to this method represent the arrays to be sorted   
                 public int compare(TimeSlots t1, TimeSlots t2){
                    int aa = t1.getConflictTime().size();
                    int bb = t2.getConflictTime().size();;
                    if(aa > bb)
                        return 1;
                    if(aa < bb)
                        return -1;
                    return 0;
                }
            });
    }

    /**
     * Calcualte the popularity of each courses by going through the 
     *  preference list of each student
     * Count the number for each course who have the same student that 
     *  want to enroll into the course
     */
    public static void getPopandCon(){
        //let the array of time slots be sorted by those has 
        // less conflicts with other time slot s to be assigned first
        setConflictTimes(); 
        ArrayList<Courses> temp = new ArrayList<Courses>(); 
        for(Students s: stu){
            temp = s.getPref(); //get the preference list for each student 
            for(int i=0; i<temp.size(); i++){
                //since the student want to enrool in the class, the popularity for
                //such course will increase
                temp.get(i).incrPop(); 
                // record the number of conflicts by checking if some student
                // want to take both courses at the same time, if so, we increase 
                // the number of conflicts
                for (int j = i+1; j<temp.size(); j++){
                    //if the two courses share the professor, we set the conflict number 
                    //to infinity to ensure they will not be assigned into the same time slots
                    if(temp.get(i).getPro().equals(temp.get(j).getPro())){
                        temp.get(i).setCon(temp.get(j), Integer.MAX_VALUE);
                        temp.get(j).setCon(temp.get(i), Integer.MAX_VALUE);
                    } else { 
                        temp.get(i).incrCon(temp.get(j));
                        temp.get(j).incrCon(temp.get(i));
                    }
                }
            }
        }
        //setting the conflict number of some course with some other course into infinity if
        //they share the same professor
        for(Professors p :professors.values()){
            ArrayList<Courses> teach = p.getCourse();
            for(int i=0;i<teach.size();i++) {
                for(int j=i+1; j<teach.size();j++){
                    teach.get(i).setCon(teach.get(j),Integer.MAX_VALUE);
                    teach.get(j).setCon(teach.get(i),Integer.MAX_VALUE);
                }
            }
        }
    }

    /**
     * Pairing each course
     * Store into a 2D array with index 0 and index 1 refer to 
     *  two courses that are paired
     * Sort the 2D array by decreasing number of conflicts,
     *  i.e., number of shared students
     * 
     * @return 2D array to store each pairing, sorted
     */

    public static Courses[][] pairing(){
        int total = classes.length * (classes.length-1) / 2; //total number of pairings
        //return array is 2D with index 1 storing some course and index 2 
        // storing it pairing courses
        Courses [][] returnArr = new Courses[total][2];
        int count = 0;
        for (int i = 0; i < classes.length-1; i++){
            for (int j = i+1; j< classes.length; j++){
                returnArr[count][0]=classes[i];
                returnArr[count][1]=classes[j];
                count++; //use count to record the first index of the 2d array
            }
        }
        //sort the 2D array by conflict numbers
        Arrays.sort(returnArr, new Comparator<Courses[]>() {
            @Override
                 //arguments to this method represent the arrays to be sorted   
                public int compare(Courses [] a, Courses [] b){
                    int aa = a[0].getCConflict(a[1]);
                    int bb = b[0].getCConflict(b[1]);
                    if(aa > bb)
                        return -1;
                    if(aa < bb)
                        return 1;
                    return 0;
                }
        });
        return returnArr;
    }

    /**
     * Sort the room by it capacity
     */
    public static void sortRoom(){
        Arrays.sort(room, new Comparator<Rooms>() {
            @Override
                //arguments to this method represent the arrays to be sorted   
                public int compare(Rooms a, Rooms b){
                    int aa = a.getCap();
                    int bb = b.getCap();
                    if(aa > bb)
                        return -1;
                    if(aa < bb)
                        return 1;
                    return 0;
                }
        });
    }

    /**
     * Scheduling the classes into classrooms and timeslots
     * 
     * Our definition of conflict numbers is 
     */
    public static void scheduling(){
        sortRoom();
        Courses [][] temp = pairing();
        int size = temp.length; 
        int finalCon, roomID, surplus, finalRoomID, finalT;
        finalCon = 0; 
        roomID = 0; 
        surplus = Integer.MIN_VALUE;
        finalRoomID = -1;
        for(int i = 0; i < size; i++){
            for (int m = 0; m <2; m++){
                if(temp[i][m].notScheduled() || temp[i][m].getPro().getName()==null){
                    // if(temp[i][m].getName().equals("002151") || temp[i][m].getName().equals("011826")){
                    //     System.out.println(i+" "+m);
                    // }

                    // storing the final conflict some courses in all time slots
                    int [] finalConflict = new int[time.length]; 
                    for(int j = 0; j < time.length; j++){
                        // find the largest available room
                        // we only need to first the first available room since the array is 
                        // alreay sorted by its capacity
                        for (int k = 0; k < room.length; k++){ 
                            if (!room[k].isAssigned(time[j])
                                && temp[i][m].getValidRooms().contains(room[k])){ 
                                roomID = k; //record the index for the largest availble room
                                k = room.length; //break
                            }
                        }
                        //surplus is positive only happens when some class have more student want to enroll
                        // than the room's capacity
                        surplus = temp[i][m].getPop() - room[roomID].getCap();
                        // record the larger from conflict number or the shortage of classroom capacity
                        finalCon = Math.max(surplus, sumOfConflict(time[j], temp[i][m]));
                        finalConflict[j] = finalCon;
                    }
                    //find the timeslot with minimum conflict numbers 
                    finalT = findMinCon(finalConflict);
                    //schedule the course into the timeslot
                    temp[i][m].setTime(time[finalT]); 
                    //record the course into timeslot instances
                    time[finalT].addClass(temp[i][m]);

                    //finding the room to be assigned
                    finalRoomID = roomID; //initialize it first into the largest available room
                    //finding the smallest room can fit all students inside
                    for(int h = room.length-1; h >= 0; h--){
                        if(room[h].getCap() >= temp[i][m].getPop() 
                            && !room[h].isAssigned(time[finalT])
                            && temp[i][m].getValidRooms().contains(room[h])){ 
                            finalRoomID = h;
                            break;
                        }
                        finalRoomID = h; //??do we need this?
                    }
                    //setting & scheduling
                    temp[i][m].setRoom(room[finalRoomID]);
                    room[finalRoomID].setTime(time[finalT],finalT);
                    room[finalRoomID].addCourse(temp[i][m], time[finalT]);
                }
            }
        }
    }

    /**
     * helper function for scheduling
     * @param arr array storing the conflict numbers of some course with all time slots
     * @return the index of the array with the least conflict number
     */
    public static int findMinCon(int [] arr){
        int id = 0;
        int min = arr[0];
        for (int i = 1; i < arr.length; i++){
            if(arr[i]<min){
                id = i;
                min = arr[i];
            }
        }
        if(min==Integer.MAX_VALUE) return -1;
        return id;
    }

    /**
     * calculate the number of conflicts between the course c and all other courses assigned
     * into the timeslot t and all other timeslots overlapping with t
     * @param t some timeslot
     * @param c some course
     * @return the number of conflicts 
     */
    public static int sumOfConflict(TimeSlots t, Courses c){
        int conflict = 0;
        //caculating the number of total conflicts 
        for (int i =0; i< t.getCourse().size();i++) {
            //when the professor teaching c is already teaching some other courses
            //thus will not be available
            if(t.getCourse().get(i).getPro().equals(c.getPro())){
                conflict = Integer.MAX_VALUE;
                break;
            }
            if(conflict != Integer.MAX_VALUE){
                conflict += t.getCourse().get(i).getCConflict(c);
            }
        }
        //checking all timeslots overlapping with t and redo the process with them
        for(TimeSlots tt : t.getConflictTime()){
            for (int i =0; i< tt.getCourse().size();i++) {
                if(tt.getCourse().get(i).getPro().equals(c.getPro())){
                    conflict = Integer.MAX_VALUE;
                    break;
                }
                if(conflict != Integer.MAX_VALUE){
                    conflict += tt.getCourse().get(i).getCConflict(c);
                }
                
            }
        }
        return conflict;
    }

    /**
     * enroll students into courses
     */
    public static void enrollment() {
        boolean available = true;
        // int sCou =0;
        for(Students s :stu) {
            ArrayList<Courses> temp = new ArrayList<Courses>(); 
            temp = s.getPref(); //get each student's preference list
            for(int i=0; i<temp.size(); i++) {
                //checking if the course students registered has no schedule
                //conlict with the next course the student want to enroll
                for(int j=0; j<s.getRegNum(); j++) {
                    // if(temp.get(i).getName().equals("002855") || s.getReg().get(j).getName().equals("002855)")){
                    //     System.out.println(sCou+" "+i+" "+j);
                    // }
                    TimeSlots a = temp.get(i).getTime();
                    TimeSlots b = s.getReg().get(j).getTime();
                    if(a.isOverlapping(b)){
                        available = false;
                    }
                }
                //at temp.get(i) class, it is available
                //students can enroll in temp.get(i) class when they are available
                //and the classroom has enough room to fit them in
                if((temp.get(i).getRoom().getCap() > temp.get(i).getReg().size()) && available){
                    temp.get(i).addStu(s);
                    s.addReg(temp.get(i));
                }
                available = true;
            }
            // sCou ++;
        }
    }

    /**
     * Read the input files and store all instances into several arrays
    */
    public static void readFile(String constraints, String prefs) throws FileNotFoundException, IOException{
        BufferedReader con; //constraints file
        BufferedReader pre; //students' preference list
        con = new BufferedReader(new FileReader(constraints)); //read the constaint file first
        String tmp;
        String [] info;
        boolean isRoom, isTeachers, isTime;
        isRoom = false; isTeachers = false; isTime = false;
        int tsize, rsize, csize, capacity;
        int roomCount =0;
        int classCount = 0;
        int timeCount  =0;
        //int psize = 0;
        //int pIndex = 0;
        tsize = 0; rsize = 0; csize = 0;  capacity = 0;
        while((tmp = con.readLine())!=null){
            info = tmp.split("\\s+"); //split each line with space
            if(info[0].equals("Class")){
                tsize = Integer.parseInt(info[2]);
                time = new TimeSlots[tsize];
                isTime = true;
            } else if (info[0].equals("Rooms")) {
                rsize = Integer.parseInt(info[1]);
                room = new Rooms[rsize];
                isRoom = true;
            } else if (info[0].equals("Classes")){
                csize = Integer.parseInt(info[1]);
                classes = new Courses[csize];
  
            } else if (info[0].equals("Teachers")){
                professors = new HashMap<>();
                isTeachers = true;
            } else {
                if(isTime && (!isRoom)&&(!isTeachers)){ //we need to store the timeslots
                    int start = Integer.parseInt(info[1].replaceAll(":",""));
                    int end = Integer.parseInt(info[3].replaceAll(":",""));
                    //we used military time for start and end time 
                    //e.g.use 2100 to represent 9:00PM
                    //12PM is just 1200
                    if(info[2].equals("PM") && start != 1200){
                        start +=1200;
                    }
                    if(info[4].equals("PM") && end != 1200){
                        end += 1200;
                    }
                    time[timeCount] = new TimeSlots(timeCount,start,end,info[5]);   
                    timeCount ++;  
                }
                if(isTime && isRoom && (!isTeachers)){
                    capacity = Integer.parseInt(info[1]);
                    TimeSlots[] emptyTimes = new TimeSlots[tsize];
                    room[roomCount] = new Rooms(capacity, emptyTimes,info[0]);
                    ++ roomCount;
                }
                if(isTime && isRoom && isTeachers){
                    
                    if(info.length>1){
                        classes[classCount] = new Courses(classCount, csize, tsize, info[0]);
                        Professors p = new Professors(info[1]);
                        if(!professors.containsKey(info[1])){
                            professors.put(info[1],p);
                        }
                        classes[classCount].setProf(p);
                        professors.get(info[1]).addCourse(classes[classCount]);
                        classes[classCount].setSubject(info[2]);
                        classes[classCount].toLab();
                        ArrayList<Rooms> validRooms=new ArrayList<Rooms>();
                        for(int k=3; k<info.length; k++){
                            for(int j=0; j<room.length; j++){
                                if(info[k].equals(room[j].getName())){
                                    validRooms.add(room[j]);
                                    break;
                                }
                            }
                        }
                        classes[classCount].setValidRooms(validRooms);
                        classCount++;
                    } else {
                        classes[classCount] = new Courses(classCount, csize, tsize, info[0]);
                        Professors p = new Professors(null);
                        classes[classCount].setProf(p);
                        classCount++;
                    }
                    
                }
            }
        }
        pre = new BufferedReader(new FileReader(prefs));
        tmp = pre.readLine();
        info = tmp.split("\\s+");
        int stuSize = Integer.parseInt(info[1]);
        stu = new Students[stuSize];
        for(int i = 0; i < stuSize; i++){
            stu[i] = new Students(i, "");
        }
        int stuID;
        stuID = 0;
        while((tmp = pre.readLine())!=null){
            info = tmp.split("\\s+");
            stu[stuID] = new Students(stuID, info[0]);
            boolean hasClass = false;
            for(int i = 1; i < info.length; i++){
                for(Courses c : stu[stuID].getPref()) {
                    if(c.getName().equals(info[i])){ //we have this class already
                        hasClass = true; // we might want to use this class again by 
                                        //naming it as a new class since it's a lab or something
                    }
                }
                if(hasClass == false) {
                    for(int j = 0; j < classes.length; j++){
                        if(classes[j].getName().equals(info[i])){
                            stu[stuID].addPref(classes[j]);
                            break;
                        }
                    }
                }
                
            }
            stuID++;
        }
        pre.close();
        con.close();
    }

    /**
     * Read the path for some file and will output the schedule in such file
     * and calculate the student prefernce value
     * @param file
     * @return the value of total students successfully enrolled, i.e., student
     *          preference value
     * @throws IOException
     */
    public static int outputSchedule(String file) throws IOException{
        int preferenceVal = 0;
        //calculate the preference value
        for (int i = 0; i < classes.length; i++){
            preferenceVal += (classes[i].getReg()).size();
        }
        //create the file if do not exist
        File f = new File(file);
        f.createNewFile();
        String stuID;
        BufferedWriter pw = new BufferedWriter(new FileWriter(file));
        String tmp = "";
        //formatting
        tmp = "Course\tRoom\tTeacher\tTime\tStudents\n";
        pw.write(tmp);
        tmp = "";
        for (int i = 0; i < classes.length; i++){
            ArrayList<Students> regList = classes[i].getReg();
            tmp += classes[i].getName() + "\t" + (classes[i].getRoom().getName()) + "\t"
                 + (classes[i].getPro().getName()) + "\t" 
                    + (classes[i].getTime().getID() + 1) + "\t";
            for (int j = 0; j < regList.size(); j++){
                stuID = regList.get(j).getName();
                tmp += stuID + " " ; 
            }
            pw.write(tmp + "\n");
            tmp = "";
        }
        pw.flush();
        pw.close();
        return preferenceVal;
    }

    public static void main(String [] args) throws FileNotFoundException, IOException{
        long start = System.currentTimeMillis();
        String con = args[0];
        String pref = args[1];
        String output = args[2];
        readFile(con,pref); //reading input
        getPopandCon(); //getting the popularity and conflict numbers
        scheduling(); //output a possible schedule
        enrollment(); //enroll students in
        int preferenceVal = outputSchedule(output); //output the schedule in a file and get the preference value
        long end = System.currentTimeMillis();
        
        //output result
        String [] info = con.split("/");
        String record = info[0] + "/record.txt";
        File f = new File(record);
        f.createNewFile();
        BufferedWriter pw = new BufferedWriter(new FileWriter(f, true));
        String [] info2 = info[1].split("_");
        String tmp = info2[1].replace(".txt", "");
        pw.write(tmp + "\n");
        pw.write("Student Preference Value: " + preferenceVal+ "\n");
        pw.write("Best Case Student Value: " + 4*stu.length+ "\n");
        pw.write("Fit percentage: " + 100* ((double)preferenceVal/(4*stu.length)) + "%"+ "\n");
        pw.write("Time used: " + (end-start)+ "\n");
        pw.flush();
        pw.close();

        //print in the terminal
        System.out.println("Student Preference Value: " + preferenceVal);
        System.out.println("Best Case Student Value: " + 4*stu.length);
        System.out.println("Fit percentage: " + 100* ((double)preferenceVal/(4*stu.length)) + "%");
        System.out.println("Time used: " + (end-start));
    }
}
