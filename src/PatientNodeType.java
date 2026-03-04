public class PatientNodeType extends NodeType {

    public String name;
    public String medicalHistory;
    public String admissionDate;
    public int room;

    public String treatmentPlan = "";
    public String prescriptions = "";

    public PatientNodeType(int id, String name, String history, String date, int room) {
        super(id); // calls original NodeType constructor
        this.name = name;
        this.medicalHistory = history;
        this.admissionDate = date;
        this.room = room;
    }

    public void display() {
        System.out.println("\n=======================");
        System.out.println("Patient ID: " + value);
        System.out.println("Name: " + name);
        System.out.println("Medical History: " + medicalHistory);
        System.out.println("Admission Date: " + admissionDate);
        System.out.println("Room Number: " + room);
        if (!treatmentPlan.isEmpty())
            System.out.println("Treatment Plan: " + treatmentPlan);
        if (!prescriptions.isEmpty())
            System.out.println("Prescriptions: " + prescriptions);
        System.out.println("=======================");
    }

    public void updateTreatment(String plan, String pres) {
        this.treatmentPlan = plan;
        this.prescriptions = pres;
    }
}
