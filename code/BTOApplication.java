import java.util.ArrayList;
import java.util.List;

public class BTOApplication {
    private static List<BTOApplication> allApplications = new ArrayList<>();
    private String applicantNric;
    private BTOProject project;
    private String status; // Pending, Successful, Unsuccessful, Booked
    private String flatType; // Add this field if not already present

    public BTOApplication(String applicantNric, BTOProject project, String flatType) {
        this.applicantNric = applicantNric;
        this.project = project;
        this.flatType = flatType;
        this.status = "Pending";
        allApplications.add(this);
    }

    public void updateStatus(String newStatus) {
        this.status = newStatus;
    }

    public boolean canBookFlat() {
        return "Successful".equals(status);
    }

    public static List<BTOApplication> getAllApplications() {
        return allApplications;
    }

    public Applicant getApplicant() {
        return (Applicant) User.getUserByNric(applicantNric);
    }

    // Getters and setters...

    public BTOProject getProject() {
        return project;
    }

    public String getFlatType() {
        return flatType;
    }

    public String getStatus() {
        return status;
    }

    public void setFlatType(String flatType) {
        this.flatType = flatType;
    }
}
