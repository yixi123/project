import java.util.List;

public class Applicant extends User implements interfaceApplicant {
    private BTOApplication application;

    public Applicant(String nric, int age, String maritalStatus) {
        super(nric, age, maritalStatus);
    }

    @Override
    public void viewProjects() {
        for (BTOProject project : BTOProject.getAllProjects()) {
            if (project.isVisible() && isEligibleForProject(project)) {
                System.out.println(project);
            }
        }
    }

    public void applyForProject(BTOProject project, String flatType) {
        if (application != null && !"Unsuccessful".equals(application.getStatus())) {
            throw new IllegalStateException("You already have an active application.");
        }
        if (!project.isVisible() || !isEligibleForProject(project)) {
            throw new IllegalArgumentException("You are not eligible to apply for this project.");
        }
        if (!project.isFlatAvailable(flatType)) {
            throw new IllegalArgumentException("Selected flat type is not available.");
        }
        if ("Single".equals(getMaritalStatus()) && !"2-Room".equals(flatType)) {
            throw new IllegalArgumentException("Singles can only apply for 2-Room flats.");
        }
        this.application = new BTOApplication(this.getNric(), project, flatType);
        System.out.println("Application submitted successfully. Status: Pending");
    }

    @Override
    public boolean isEligibleForProject(BTOProject project) {
        if ("Single".equals(getMaritalStatus()) && getAge() >= 35) {
            return project.getFlatTypes().containsKey("2-Room");
        } else if ("Married".equals(getMaritalStatus()) && getAge() >= 21) {
            return true;
        }
        return false;
    }

    public void viewApplicationStatus() {
        if (application == null) {
            System.out.println("No active application.");
            return;
        }
        System.out.println("Project: " + application.getProject().getName());
        System.out.println("Status: " + application.getStatus());
    }

    public void withdrawApplication() {
        if (application == null) {
            System.out.println("No active application to withdraw.");
            return;
        }
        if ("Booked".equals(application.getStatus())) {
            System.out.println("Cannot withdraw an application after booking a flat.");
            return;
        }
        application.updateStatus("Withdrawn");
        application = null;
        System.out.println("Application withdrawn successfully.");
    }

    @Override
    public void submitEnquiry(String enquiryText) {
        if (application != null && application.getProject() != null) {
            Enquiry enquiry = new Enquiry(enquiryText);
            getEnquiryHandler().getEnquiries().add(enquiry); // Add to User's EnquiryHandler
            application.getProject().addEnquiry(enquiry); // Add to the associated BTOProject
        } else {
            throw new IllegalStateException("No project associated with the enquiry.");
        }
    }

    public void viewEnquiries() {
        for (Enquiry enquiry : getEnquiries()) {
            System.out.println("Enquiry ID: " + enquiry.getId());
            System.out.println("Text: " + enquiry.getText());
            System.out.println("Replies: " + enquiry.getReplies());
        }
    }

    @Override
    public void editEnquiry(int enquiryId, String newText) {
        getEnquiryHandler().editEnquiry(enquiryId, newText);
    }

    @Override
    public void deleteEnquiry(int enquiryId) {
        getEnquiryHandler().deleteEnquiry(enquiryId);
    }

    public void bookFlat(String flatType) {
        if (application == null || !"Successful".equals(application.getStatus())) {
            throw new IllegalStateException("You are not eligible to book a flat.");
        }
        application.getProject().bookFlat(flatType);
        application.updateStatus("Booked");
        System.out.println("Flat booked successfully.");
    }

    public List<Enquiry> getEnquiries() {
        return getEnquiryHandler().getEnquiries();
    }

    @Override
    public String getRole() {
        return "Applicant";
    }

    public BTOApplication getApplication() {
        return application;
    }

}
