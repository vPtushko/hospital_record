import java.util.Scanner;
import java.util.Map;

public class HospitalSystem {

    static Scanner sc = new Scanner(System.in);
    static BSearchTreeType tree = new BSearchTreeType();
    static final String FILENAME = "patients.txt";
    static PatientFileHandler fileHandler = new PatientFileHandler(FILENAME);

    // Create LoginManager instance
    static LoginManager loginManager = new LoginManager("admin", "1234", 3);

    public static void main(String[] args) {

        // Login check
        if (!loginManager.login()) {
            System.out.println("Too many failed attempts. Exiting program.");
            return;
        }
            fileHandler.loadPatients(tree);

        int choice = 0;
        do {
            System.out.println("\n==== PATIENT RECORD SYSTEM ====");
            System.out.println("1. Insert New Patient");
            System.out.println("2. Search by ID");
            System.out.println("3. Display All Patients");
            System.out.println("4. Update Treatment/Prescriptions");
            System.out.println("5. Medical Analytics Report");
            System.out.println("6. Exit");
            System.out.print("Choice: ");
//            choice = sc.nextInt();
//            sc.nextLine();

            String input = sc.nextLine(); // read everything as string
            try {
                choice = Integer.parseInt(input); // try to convert
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number between 1 and 6.");
                continue; // go back to menu
            }
            switch(choice) {
                case 1: insertPatient(); break;
                case 2: search(); break;
                case 3: tree.displayPatients(); break;
                case 4: updateTreatment(); break;
                case 5: analyticsReport(); break;
                case 6: System.out.println("Exiting system."); break;
                default: System.out.println("Invalid choice!");
            }

        } while (choice != 6);
    }

    public static void insertPatient() {
        System.out.print("ID: "); int id = sc.nextInt(); sc.nextLine();
        if (tree.searchPatient(id) != null) { System.out.println("Duplicate ID!"); return; }
        System.out.print("Name: "); String name = sc.nextLine();
        System.out.print("Medical History: "); String history = sc.nextLine();
        System.out.print("Admission Date: "); String date = sc.nextLine();
        System.out.print("Room Number: "); int room = sc.nextInt(); sc.nextLine();

        tree.insertPatient(id, name, history, date, room, true);
        fileHandler.saveAllPatients(tree);
        System.out.println("Patient Added!");
    }

    public static void search() {
        System.out.print("Enter patient ID: "); int id = sc.nextInt();
        PatientNodeType p = tree.searchPatient(id);
        if (p == null) System.out.println("Record not found.");
        else p.display();
    }

    public static void updateTreatment() {
        System.out.print("Enter patient ID to update: "); int id = sc.nextInt(); sc.nextLine();
        PatientNodeType p = tree.searchPatient(id);
        if (p == null) { System.out.println("Patient not found."); return; }

        System.out.print("Enter Treatment Plan: "); String plan = sc.nextLine();
        System.out.print("Enter Prescriptions: "); String pres = sc.nextLine();
        p.updateTreatment(plan, pres);
        fileHandler.saveAllPatients(tree);
        System.out.println("Treatment updated!");
    }

    public static void analyticsReport() {
        System.out.print("Enter condition to count patients: "); String condition = sc.nextLine();
        int count = tree.countPatientsWithCondition(condition);
        System.out.println("Patients with '" + condition + "': " + count);

        System.out.println("=== Admissions by Date ===");
        Map<String, Integer> trends = tree.admissionTrends();
        for (String date : trends.keySet()) {
            System.out.println(date + ": " + trends.get(date) + " patients");
        }
    }
}
