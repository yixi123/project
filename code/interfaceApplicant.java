public interface interfaceApplicant {
    void viewProjects();
    void applyForProject(BTOProject project, String flatType);
    boolean isEligibleForProject(BTOProject project);
    void withdrawApplication();
    void viewApplicationStatus();
    void submitEnquiry(String enquiryText);
    void bookFlat(String flatType);
    void editEnquiry(int enquiryId, String newText);
    void deleteEnquiry(int enquiryId);
    String getRole();
}
