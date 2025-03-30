import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HDBManager extends User {
    private List<BTOProject> createdProjects;

    public HDBManager(String nric, int age, String maritalStatus) {
        super(nric, age, maritalStatus);
        this.createdProjects = new ArrayList<>();
    }

    @Override
    public void viewProjects() {
        for (BTOProject project : BTOProject.getAllProjects()) {
            System.out.println(project);
        }
    }

    public void createProject(BTOProject project) {
        if (isProjectPeriodConflict(project)) {
            throw new IllegalArgumentException("Cannot manage multiple projects in the same period.");
        }
        project.setManagerInCharge(this);
        createdProjects.add(project);
    }

    public void editProject(int projectId, BTOProject updatedProject) {
        for (int i = 0; i < createdProjects.size(); i++) {
            if (createdProjects.get(i).getId() == projectId) {
                createdProjects.set(i, updatedProject);
                return;
            }
        }
        throw new IllegalArgumentException("Project not found.");
    }

    public void deleteProject(int projectId) {
        createdProjects.removeIf(project -> project.getId() == projectId);
    }

    public void toggleProjectVisibility(BTOProject project) {
        project.toggleVisibility();
    }

    public List<BTOProject> getCreatedProjects() {
        return createdProjects;
    }

    public List<BTOProject> filterProjectsByManager() {
        return BTOProject.getAllProjects().stream()
                .filter(project -> project.getManagerInCharge() == this)
                .collect(Collectors.toList());
    }

    public void approveOfficerRegistration(HDBOfficer officer, BTOProject project) {
        if (project.getAvailableOfficerSlots() <= 0) {
            throw new IllegalStateException("No available slots for HDB Officers.");
        }
        project.addOfficer(officer);
    }

    public void rejectOfficerRegistration(HDBOfficer officer, BTOProject project) {
        project.removeOfficer(officer);
    }

    public void approveApplication(BTOApplication application) {
        if (application.getProject().isFlatAvailable(application.getFlatType())) {
            application.updateStatus("Successful");
            System.out.println("Application approved.");
        } else {
            application.updateStatus("Unsuccessful");
            System.out.println("Application rejected due to lack of flats.");
        }
    }

    public void rejectApplication(BTOApplication application) {
        application.updateStatus("Rejected");
        System.out.println("Application rejected.");
    }

    public void approveWithdrawalRequest(BTOApplication application) {
        application.updateStatus("Withdrawn");
        System.out.println("Withdrawal request approved.");
    }

    public void generateReport(String filter) {
        System.out.println("Report:");
        for (BTOApplication application : BTOApplication.getAllApplications()) {
            if (filter.equals("Married") && "Married".equals(application.getApplicant().getMaritalStatus())) {
                System.out.println(application);
            }
            // Add more filters as needed
        }
    }

    public void viewAllEnquiries() {
        for (BTOProject project : BTOProject.getAllProjects()) {
            System.out.println("Project: " + project.getName());
            for (Enquiry enquiry : project.getEnquiries()) {
                System.out.println("Enquiry ID: " + enquiry.getId());
                System.out.println("Text: " + enquiry.getText());
                System.out.println("Replies: " + enquiry.getReplies());
            }
        }
    }

    public void replyToEnquiry(BTOProject project, int enquiryId, String replyText) {
        if (project.getManagerInCharge() != this) {
            throw new IllegalStateException("You are not in charge of this project.");
        }
        project.replyToEnquiry(enquiryId, replyText, this);
    }

    private boolean isProjectPeriodConflict(BTOProject newProject) {
        for (BTOProject project : createdProjects) {
            if (project.getOpeningDate().isBefore(newProject.getClosingDate()) &&
                project.getClosingDate().isAfter(newProject.getOpeningDate())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRole() {
        return "HDBManager";
    }
}
