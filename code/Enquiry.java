import java.util.ArrayList;
import java.util.List;

public class Enquiry {
    private static int idCounter = 1;
    private final int id;
    private String text;
    private List<Reply> replies;

    public Enquiry(String text) {
        this.id = idCounter++;
        this.text = text;
        this.replies = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Reply> getReplies() {
        return replies;
    }

    public void addReply(String replyText, User repliedBy) {
        this.replies.add(new Reply(replyText, repliedBy));
    }

    // Inner class to represent a reply
    public static class Reply {
        private String text;
        private User repliedBy;

        public Reply(String text, User repliedBy) {
            this.text = text;
            this.repliedBy = repliedBy;
        }

        public String getText() {
            return text;
        }

        public User getRepliedBy() {
            return repliedBy;
        }
    }
}
