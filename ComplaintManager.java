/**
 * ComplaintManager orchestrates storage & retrieval using both DS:
 * - BST for fast ID lookup
 * - LinkedList for chronological listing
 */
public class ComplaintManager {
    private ComplaintBST bst;
    private MyLinkedList<Complaint> orderedList;

    public ComplaintManager() {
        this.bst = new ComplaintBST();
        this.orderedList = new MyLinkedList<>();
    }

    public synchronized void submitComplaint(Complaint c) throws ValidationException {
        if (c == null) throw new ValidationException("Complaint is null");
        // basic validation
        if (c.getTitle() == null || c.getTitle().trim().isEmpty()) throw new ValidationException("Missing title");
        // Insert into BST (unique ID) and append to list
        boolean inserted = bst.insert(c);
        if (!inserted) throw new ValidationException("Duplicate complaint ID");
        orderedList.addLast(c);
    }

    public Complaint findById(String id) {
        return bst.searchById(id);
    }

    public boolean updateStatus(String id, Complaint.Status status) {
        Complaint c = bst.searchById(id);
        if (c == null) return false;
        c.setStatus(status);
        return true;
    }

    public boolean deleteById(String id) {
        return bst.delete(id);
    }

    public MyLinkedList<Complaint> listChronological() {
        return orderedList;
    }

    // Example: returns array of complaints sorted by priority using a simple sort
    public Complaint[] sortedByPriority() {
        // extract list into array
        int n = orderedList.size();
        Complaint[] arr = new Complaint[n];
        int i = 0;
        for (Complaint c: orderedList) arr[i++] = c;
        // simple stable insertion sort by priority (since n is expected small for coursework)
        for (int j=1;j<n;j++){
            Complaint key = arr[j];
            int k = j-1;
            while (k >=0 && arr[k].getPriority() > key.getPriority()){
                arr[k+1] = arr[k]; k--;
            }
            arr[k+1] = key;
        }
        return arr;
    }
}