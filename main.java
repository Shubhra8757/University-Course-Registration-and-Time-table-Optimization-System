import java.util.*;

class Student {
    String username, password, course;
    int[] hours;
    public Student(String u, String p, String c, int[] h){
        username=u; password=p; course=c; hours=h;
    }
}

public class main {
    static Scanner sc = new Scanner(System.in);
    static LinkedList<String> registrationOrder = new LinkedList<>();
    static HashMap<String, Student> students = new HashMap<>();
    static HashMap<String, Integer> courseFrequency = new HashMap<>();

    static HashMap<String,int[]> courseHours = new HashMap<>();
    static {
        courseHours.put("Mechanical", new int[]{9,10,11});
        courseHours.put("Chemical", new int[]{9,10,11});
        courseHours.put("Civil", new int[]{9,10,11});
        courseHours.put("Biotechnology", new int[]{9,10,11});
        courseHours.put("CSE", new int[]{9,10,11});
        courseHours.put("Physical Education", new int[]{9,10});
        courseHours.put("Law", new int[]{9,10});
        courseHours.put("MBA", new int[]{9,10});
        courseHours.put("BSc", new int[]{9,10});
        courseHours.put("MBBS", new int[]{9,10});
        courseHours.put("BPharma", new int[]{9,10});
        courseHours.put("MPharma", new int[]{9,10});
    }

    static HashMap<String,String[]> timetables = new HashMap<>();
    static {
        timetables.put("Mechanical", new String[]{"Math 9-10","Physics 10-11","Workshop 11-12"});
        timetables.put("Chemical", new String[]{"Chem 9-10","Physics 10-11","Lab 11-12"});
        timetables.put("Civil", new String[]{"Math 9-10","Construction 10-11","Drawing 11-12"});
        timetables.put("Biotechnology", new String[]{"Bio 9-10","Chem 10-11","Lab 11-12"});
        timetables.put("CSE", new String[]{"DSA 9-10","Java 10-11","Lab 11-12"});
        timetables.put("Physical Education", new String[]{"Yoga 9-10","Fitness 10-11"});
        timetables.put("Law", new String[]{"Law 9-10","Ethics 10-11"});
        timetables.put("MBA", new String[]{"Management 9-10","Finance 10-11"});
        timetables.put("BSc", new String[]{"Bio 9-10","Chem 10-11"});
        timetables.put("MBBS", new String[]{"Anatomy 9-10","Physiology 10-11"});
        timetables.put("BPharma", new String[]{"PharmChem 9-10","PharmLab 10-11"});
        timetables.put("MPharma", new String[]{"AdvancedPharm 9-10","Research 10-11"});
    }

    public static void main(String[] args){
        while(true){
            System.out.println("\n--- University Course Registration & Timetable ---");
            System.out.println("1. Register  2. Login  3. Course Popularity  4. Check Conflicts  5. Exit");
            System.out.print("Choice: ");
            int choice = sc.nextInt(); sc.nextLine();

            switch(choice){
                case 1: register(); break;
                case 2: login(); break;
                case 3: showPopularity(); break;
                case 4: checkConflicts(); break;
                case 5: System.out.println("Exiting..."); return;
                default: System.out.println("Invalid choice!");
            }
        }
    }

    static void register(){
        System.out.print("Username: "); String u=sc.nextLine();
        System.out.print("Password: "); String p=sc.nextLine();
        System.out.println("Courses: Mechanical, Chemical, Civil, Biotechnology, CSE, Physical Education, Law, MBA, BSc, MBBS, BPharma, MPharma");
        System.out.print("Select Course: "); String c=sc.nextLine();

        if(!timetables.containsKey(c)){ System.out.println("Invalid course!"); return; }
        if(students.containsKey(u)){ System.out.println("User already exists!"); return; }

        int[] hours = courseHours.get(c);
        students.put(u,new Student(u,p,c,hours));
        registrationOrder.add(u);
        courseFrequency.put(c, courseFrequency.getOrDefault(c,0)+1);
        System.out.println("Registered successfully!");
    }

    static void login(){
        System.out.print("Username: "); String u=sc.nextLine();
        System.out.print("Password: "); String p=sc.nextLine();

        if(students.containsKey(u) && students.get(u).password.equals(p)){
            System.out.println("Login successful! Welcome " + u);
            showTimetable(students.get(u));
        } else System.out.println("Invalid credentials!");
    }

    static void showTimetable(Student s){
        System.out.println("\n--- Timetable for "+s.username+" ("+s.course+") ---");
        for(String t: timetables.get(s.course)) System.out.println(t);

        if(slidingWindow(s.hours,2)) System.out.println("DSA Warning: Too many consecutive classes!");
    }

    static boolean slidingWindow(int[] arr,int maxConsec){
        int count=1;
        for(int i=1;i<arr.length;i++){
            if(arr[i]==arr[i-1]+1) count++;
            else count=1;
            if(count>maxConsec) return true;
        }
        return false;
    }

    static void showPopularity(){
        System.out.println("\n--- Course Popularity ---");
        for(String c: courseFrequency.keySet()){
            System.out.println(c + ": " + courseFrequency.get(c) + " registrations");
        }
    }

    static void checkConflicts(){
        System.out.println("\n--- Checking Timetable Conflicts Between Students ---");
        List<Student> list = new ArrayList<>(students.values());
        boolean conflict=false;
        for(int i=0;i<list.size();i++){
            for(int j=i+1;j<list.size();j++){
                if(twoPointerConflict(list.get(i).hours,list.get(j).hours)){
                    System.out.println("Conflict: "+list.get(i).username+" ("+list.get(i).course+") and "+list.get(j).username+" ("+list.get(j).course+")");
                    conflict=true;
                }
            }
        }
        if(!conflict) System.out.println("No conflicts detected!");
    }

    static boolean twoPointerConflict(int[] a,int[] b){
        Arrays.sort(a); Arrays.sort(b);
        int i=0,j=0;
        while(i<a.length && j<b.length){
            if(a[i]==b[j]) return true;
            if(a[i]<b[j]) i++; else j++;
        }
        return false;
    }
}
