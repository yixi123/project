import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BTOProject {
    private int id;
    private static int idCounter = 1;
    private String name;
    private String neighborhood;
    private HDBManager managerInCharge;
    private Map<String, Integer> flatTypes; // e.g., {"2-Room": 50, "3-Room": 100}
    private LocalDate openingDate;
    private LocalDate closingDate;
    private boolean visibility;
    private List<HDBOfficer> assignedOfficers;
    private EnquiryHandler enquiryHandler; // Use EnquiryHandler for managing enquiries
    private static final int MAX_OFFICER_SLOTS = 10;
    private static List<BTOProject> allProjects = new ArrayList<>();

    public BTOProject(String name, String neighborhood, Map<String, Integer> flatTypes, LocalDate openingDate, LocalDate closingDate) {
        this.name = name;
        this.id = idCounter++;
        this.neighborhood = neighborhood;
        this.flatTypes = flatTypes;
        this.openingDate = openingDate;
        this.closingDate = closingDate;
        this.visibility = true;
        this.assignedOfficers = new ArrayList<>();
        this.enquiryHandler = new EnquiryHandler(); // Initialize EnquiryHandler
        allProjects.add(this);
    }

    public void toggleVisibility() {
        this.visibility = !this.visibility;
    }

    public Map<String, Integer> getFlatTypes() {
        return flatTypes;
    }

    public boolean isFlatAvailable(String flatType) {
        return flatTypes.getOrDefault(flatType, 0) > 0;
    }

    public void bookFlat(String flatType) {
        if (!isFlatAvailable(flatType)) {
            throw new IllegalArgumentException("No flats available for the selected type.");
        }
        flatTypes.put(flatType, flatTypes.get(flatType) - 1);
    }

    public boolean isVisible() {
        return visibility;
    }

    public static List<BTOProject> getAllProjects() {
        return allProjects;
    }

    public LocalDate getOpeningDate() {
        return openingDate;
    }

    public LocalDate getClosingDate() {
        return closingDate;
    }

    public String getName() {
        return name;
    }

    public HDBManager getManagerInCharge() {
        return managerInCharge;
    }

    public void setManagerInCharge(HDBManager manager) {
        this.managerInCharge = manager;
    }

    public int getId() {
        return id;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public List<Enquiry> getEnquiries() {
        return enquiryHandler.getEnquiries(); // Delegate to EnquiryHandler
    }

    public void addEnquiry(Enquiry enquiry) {
        enquiryHandler.getEnquiries().add(enquiry); // Delegate to EnquiryHandler
    }

    public void editEnquiry(int enquiryId, String newText) {
        enquiryHandler.editEnquiry(enquiryId, newText); // Delegate to EnquiryHandler
    }

    public void deleteEnquiry(int enquiryId) {
        enquiryHandler.deleteEnquiry(enquiryId); // Delegate to EnquiryHandler
    }

    public void replyToEnquiry(int enquiryId, String replyText, User repliedBy) {
        enquiryHandler.replyToEnquiry(enquiryId, replyText, repliedBy); // Delegate to EnquiryHandler
    }

    public int getAvailableOfficerSlots() {
        return MAX_OFFICER_SLOTS - assignedOfficers.size();
    }

    public void addOfficer(HDBOfficer officer) {
        if (assignedOfficers.size() >= MAX_OFFICER_SLOTS) {
            throw new IllegalStateException("No available slots for HDB Officers.");
        }
        assignedOfficers.add(officer);
    }

    public void removeOfficer(HDBOfficer officer) {
        assignedOfficers.remove(officer);
    }
}
