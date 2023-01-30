import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class WorkoutFormatter {

    public void format() {
        String file = "workouts.txt";
        Scanner sc = null;
        try {
            sc = new Scanner(new File("C:\\Users\\arnav\\IdeaProjects\\" +
                    "WorkoutFormatter\\src\\" + file));
        } catch (
                FileNotFoundException e) {
            String currentDir = System.getProperty("user.dir");
            System.out.println("Be sure " + "workouts.txt" + " is in this directory: ");
            System.out.println(currentDir);
        }

        if (sc != null) {
            System.out.println(sc.nextLine().trim() + "\n");
            Stack<WorkoutBlock> rev = new Stack<>();
            WorkoutBlock wb = new WorkoutBlock();
            while (sc.hasNext()) {
                String line = sc.nextLine().trim();
                if (isHeader(line)) {
                    wb.setHeader(line);
                } else {
                    wb.addLift(line);
                }
                if (line.equals("")) {
                    rev.push(wb);
                    wb = new WorkoutBlock();
                }
            }
            rev.push(wb);
            while (!rev.isEmpty()) {
                System.out.println(rev.pop());
            }
        }
    }

    public boolean isHeader(String s) {
        return s.equals("Push") || s.equals("Pull") || s.equals("Legs") || //if it's PPL
                //exclude facepulls, pullovers, pushdowns, etc.
                (s.contains("Pull") && !(s.contains("Face") || s.contains("overs") ||
                        s.contains("down") || s.contains("up"))) ||
                (s.contains("Push") && !(s.contains("down") || s.contains("up")))
                || (s.contains("Legs"));
    }

    private static class WorkoutBlock {
        private String header;
        private final ArrayList<String> lifts;

        public WorkoutBlock() {
            lifts = new ArrayList<>();
        }

        public void setHeader(String s) {
            header = s;
        }

        public void addLift(String s) {
            if (!s.equals("")) {
                lifts.add(s);
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(header);
            sb.append("\n");
            for (String l : lifts) {
                sb.append(l).append("\n");
            }
            return sb.toString();
        }
    }
}
