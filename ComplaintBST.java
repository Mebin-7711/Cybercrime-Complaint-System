import java.util.ArrayList;
import java.util.List;

/**
 * Simple BST storing Complaint keyed by complaintId.
 */
public class ComplaintBST {
    private BSTNode root;
    private int size = 0;

    public ComplaintBST() { root = null; }

    public boolean insert(Complaint c) {
        if (root == null) { root = new BSTNode(c); size++; return true; }
        BSTNode cur = root, parent = null;
        while (cur != null) {
            int cmp = c.getComplaintId().compareTo(cur.value.getComplaintId());
            if (cmp == 0) return false; // duplicate id
            parent = cur;
            if (cmp < 0) cur = cur.left; else cur = cur.right;
        }
        if (c.getComplaintId().compareTo(parent.value.getComplaintId()) < 0) parent.left = new BSTNode(c);
        else parent.right = new BSTNode(c);
        size++;
        return true;
    }

    public Complaint searchById(String id) {
        BSTNode cur = root;
        while (cur != null) {
            int cmp = id.compareTo(cur.value.getComplaintId());
            if (cmp == 0) return cur.value;
            if (cmp < 0) cur = cur.left; else cur = cur.right;
        }
        return null;
    }

    public List<Complaint> inOrder() {
        List<Complaint> list = new ArrayList<>();
        inOrderRec(root, list);
        return list;
    }
    private void inOrderRec(BSTNode node, List<Complaint> list) {
        if (node == null) return;
        inOrderRec(node.left, list);
        list.add(node.value);
        inOrderRec(node.right, list);
    }

    public List<Complaint> preOrder() {
        List<Complaint> list = new ArrayList<>();
        preOrderRec(root, list);
        return list;
    }
    private void preOrderRec(BSTNode node, List<Complaint> list) {
        if (node == null) return;
        list.add(node.value);
        preOrderRec(node.left, list);
        preOrderRec(node.right, list);
    }

    public boolean delete(String id) {
        // Deletion with standard BST logic
        BSTNode[] result = deleteRec(root, id);
        if (result == null) return false;
        root = result[0];
        boolean deleted = result[1] != null;
        if (deleted) size--;
        return deleted;
    }

    // helper returns pair [newRoot, deletedNode]
    private BSTNode[] deleteRec(BSTNode node, String id) {
        if (node == null) return new BSTNode[]{null, null};
        int cmp = id.compareTo(node.value.getComplaintId());
        if (cmp < 0) {
            BSTNode[] res = deleteRec(node.left, id);
            node.left = res[0];
            return new BSTNode[]{node, res[1]};
        } else if (cmp > 0) {
            BSTNode[] res = deleteRec(node.right, id);
            node.right = res[0];
            return new BSTNode[]{node, res[1]};
        } else {
            // delete this node
            if (node.left == null) return new BSTNode[]{node.right, node};
            if (node.right == null) return new BSTNode[]{node.left, node};
            // both children present: find inorder successor
            BSTNode successorParent = node;
            BSTNode successor = node.right;
            while (successor.left != null) { successorParent = successor; successor = successor.left; }
            node.value = successor.value;
            if (successorParent.left == successor) successorParent.left = successor.right;
            else successorParent.right = successor.right;
            return new BSTNode[]{node, successor};
        }
    }

    public int size() { return size; }
}