public class HDBOfficer extends Applicant {
    private BTOProject assignedProject;
    private boolean registrationPending;

    public HDBOfficer(String nric, int age, String maritalStatus) {
        super(nric, age, maritalStatus);
        this.registrationPending = false;
    }

    public void registerForProject(BTOProject project) {
        if (this.assignedProject != null) {
            throw new IllegalArgumentException("You are already assigned to a project.");
        }
        if (this.getApplication() != null && this.getApplication().getProject() == project) {
            throw new IllegalArgumentException("Cannot register for a project you have applied for.");
        }
        if (isProjectPeriodConflict(project)) {
            throw new IllegalArgumentException("Cannot register for multiple projects within the same application period.");
        }
        this.registrationPending = true;
        System.out.println("Registration for project " + project.getName() + " is pending approval.");
    }

    public boolean isRegistrationPending() {
        return registrationPending;
    }

    public void approveRegistration(BTOProject project) {
        this.assignedProject = project;
        this.registrationPending = false;
        System.out.println("Registration approved. You are now handling project: " + project.getName());
    }

    public void viewAssignedProjectDetails() {
        if (assignedProject == null) {
            throw new IllegalStateException("No project assigned.");
        }
        System.out.println("Project Details:");
        System.out.println("Name: " + assignedProject.getName());
        System.out.println("Neighborhood: " + assignedProject.getNeighborhood());
        System.out.println("Flat Types: " + assignedProject.getFlatTypes());
        System.out.println("Opening Date: " + assignedProject.getOpeningDate());
        System.out.println("Closing Date: " + assignedProject.getClosingDate());
    }

    public void replyToEnquiry(int enquiryId, String replyText) {
        if (assignedProject == null) {
            throw new IllegalStateException("No project assigned to reply to enquiries.");
        }
        assignedProject.replyToEnquiry(enquiryId, replyText, this);
    }

    public void handleFlatBooking(String applicantNric, String flatType) {
        if (assignedProject == null) {
            throw new IllegalStateException("No project assigned to handle bookings.");
        }
        Applicant applicant = (Applicant) User.getUserByNric(applicantNric);
        if (applicant == null || applicant.getApplication() == null || !"Successful".equals(applicant.getApplication().getStatus())) {
            throw new IllegalStateException("Applicant is not eligible to book a flat.");
        }
        assignedProject.bookFlat(flatType);
        applicant.getApplication().updateStatus("Booked");
        applicant.getApplication().setFlatType(flatType);
        System.out.println("Flat booked successfully for " + applicant.getNric());
    }

    public void generateReceipt(Applicant applicant) {
        if (applicant.getApplication() == null || !"Booked".equals(applicant.getApplication().getStatus())) {
            throw new IllegalStateException("No booking found for the applicant.");
        }
        System.out.println("Receipt:");
        System.out.println("Name: " + applicant.getNric());
        System.out.println("Age: " + applicant.getAge());
        System.out.println("Marital Status: " + applicant.getMaritalStatus());
        System.out.println("Flat Type: " + applicant.getApplication().getFlatType());
        System.out.println("Project: " + applicant.getApplication().getProject().getName());
    }

    private boolean isProjectPeriodConflict(BTOProject newProject) {
        if (assignedProject != null) {
            return assignedProject.getOpeningDate().isBefore(newProject.getClosingDate()) &&
                   assignedProject.getClosingDate().isAfter(newProject.getOpeningDate());
        }
        return false;
    }

    public BTOProject getAssignedProject() {
        return assignedProject;
    }
}
