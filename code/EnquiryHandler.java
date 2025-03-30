import java.util.ArrayList;
import java.util.List;

public class EnquiryHandler {
    private List<Enquiry> enquiries;

    public EnquiryHandler() {
        this.enquiries = new ArrayList<>();
    }

    public void submitEnquiry(String enquiryText) {
        enquiries.add(new Enquiry(enquiryText));
    }

    public void editEnquiry(int enquiryId, String newText) {
        for (Enquiry enquiry : enquiries) {
            if (enquiry.getId() == enquiryId) {
                enquiry.setText(newText);
                return;
            }
        }
        throw new IllegalArgumentException("Enquiry not found.");
    }

    public void deleteEnquiry(int enquiryId) {
        enquiries.removeIf(enquiry -> enquiry.getId() == enquiryId);
    }

    public void replyToEnquiry(int enquiryId, String replyText, User repliedBy) {
        for (Enquiry enquiry : enquiries) {
            if (enquiry.getId() == enquiryId) {
                enquiry.addReply(replyText, repliedBy);
                return;
            }
        }
        throw new IllegalArgumentException("Enquiry not found.");
    }

    public List<Enquiry> getEnquiries() {
        return enquiries;
    }
}
