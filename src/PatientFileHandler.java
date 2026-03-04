import java.io.*;
import java.util.Scanner;

public class PatientFileHandler {

    private String filename;

    public PatientFileHandler(String filename) {
        this.filename = filename;
        ensureFileExists();
    }

    private void ensureFileExists() {
        try {
            File f = new File(filename);
            if (!f.exists()) f.createNewFile();
        } catch (IOException e) {
            System.out.println("Could not create patient file: " + e.getMessage());
        }
    }

    // Load patients from file
    public void loadPatients(BSearchTreeType tree) {
        try (Scanner sc = new Scanner(new File(filename))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String history = parts[2];
                    String date = parts[3];
                    int room = Integer.parseInt(parts[4]);
                    String treatment = parts.length > 5 ? parts[5] : "";
                    String prescriptions = parts.length > 6 ? parts[6] : "";

                    tree.insertPatient(id, name, history, date, room, false);
                    PatientNodeType p = tree.searchPatient(id);
                    if (p != null) {
                        p.treatmentPlan = treatment;
                        p.prescriptions = prescriptions;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Patient file not found. Starting with empty system.");
        }
    }

    // Save all patients
    public void saveAllPatients(BSearchTreeType tree) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            saveInorder(tree.getRoot(), pw);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    private void saveInorder(NodeType node, PrintWriter pw) {
        if (node != null) {
            saveInorder(node.left, pw);
            PatientNodeType p = (PatientNodeType) node;
            pw.println(p.value + "," + p.name + "," + p.medicalHistory + "," +
                    p.admissionDate + "," + p.room + "," + p.treatmentPlan + "," + p.prescriptions);
            saveInorder(node.right, pw);
        }
    }
}
