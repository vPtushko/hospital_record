public class BSearchTreeType extends BinaryTreeType {

    @Override
    public boolean search(int searchItem) {
        NodeType current = root;
        while (current != null) {
            if (searchItem == current.value)
                return true;
            else if (searchItem < current.value)
                current = current.left;
            else
                current = current.right;
        }
        return false;
    }

    public NodeType getRoot() {
        return root;
    }


    // Recursive search helper for BST
    private NodeType searchRec(NodeType node, int key) {
        if (node == null) {
            return null; // Key not found
        }

        if (node.value == key) {
            return node; // Found the node
        } else if (key < node.value) {
            return searchRec(node.left, key); // Search in left subtree
        } else {
            return searchRec(node.right, key); // Search in right subtree
        }
    }


    @Override
    public void insert(int insertItem) {
        NodeType newNode = new NodeType(insertItem);
        if (root == null) {
            root = newNode;
            return;
        }

        NodeType current = root;
        NodeType trailCurrent = null;

        while (current != null) {
            trailCurrent = current;
            if (insertItem == current.value) {
                System.out.println("The item to be inserted is already in the tree -- duplicates are not allowed.");
                return;
            } else if (insertItem < current.value) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        if (insertItem < trailCurrent.value)
            trailCurrent.left = newNode;
        else
            trailCurrent.right = newNode;
    }

    @Override
    public void deleteNode(int deleteItem) {
        root = deleteFromTree(root, deleteItem);
    }

    private NodeType deleteFromTree(NodeType p, int deleteItem) {
        if (p == null) {
            System.out.println("The item to be deleted is not in the tree.");
            return null;
        }

        if (deleteItem < p.value) {
            p.left = deleteFromTree(p.left, deleteItem);
        } else if (deleteItem > p.value) {
            p.right = deleteFromTree(p.right, deleteItem);
        } else { // found node
            if (p.left == null)
                return p.right;
            else if (p.right == null)
                return p.left;

            // node with two children: replace with inorder predecessor (max in left subtree)
            NodeType temp = p.left;
            while (temp.right != null)
                temp = temp.right;
            p.value = temp.value;
            p.left = deleteFromTree(p.left, temp.value);
        }

        return p;
    }

    public void insertPatient(int id, String name, String history, String date, int room) {
        root = insertPatientRec(root, id, name, history, date, room);
    }

    private NodeType insertPatientRec(NodeType root, int id, String name, String history, String date, int room) {

        if (root == null) {
            root = new PatientNodeType(id, name, history, date, room);
            return root;
        }

        if (id < root.value) {
            root.left = insertPatientRec(root.left, id, name, history, date, room);
        } else if (id > root.value) {
            root.right = insertPatientRec(root.right, id, name, history, date, room);
        }

        return root;
    }


    // Insert patient with duplicate check flag
    public void insertPatient(int id, String name, String history, String date, int room, boolean checkDuplicates) {
        if (checkDuplicates && searchPatient(id) != null) {
            System.out.println("Duplicate ID! Patient not added.");
            return;
        }
        root = insertPatientRec(root, id, name, history, date, room);
    }

    // Analytics: count patients by medical condition
    public int countPatientsWithCondition(String condition) {
        return countConditionRec(root, condition.toLowerCase());
    }

    private int countConditionRec(NodeType node, String condition) {
        if (node == null) return 0;
        PatientNodeType p = (PatientNodeType) node;
        int count = 0;
        if (p.medicalHistory.toLowerCase().contains(condition)) count++;
        count += countConditionRec(node.left, condition);
        count += countConditionRec(node.right, condition);
        return count;
    }

    // Analytics: admissions by date
    public java.util.Map<String, Integer> admissionTrends() {
        java.util.Map<String, Integer> trends = new java.util.HashMap<>();
        fillTrends(root, trends);
        return trends;
    }

    private void fillTrends(NodeType node, java.util.Map<String, Integer> trends) {
        if (node != null) {
            fillTrends(node.left, trends);
            PatientNodeType p = (PatientNodeType) node;
            trends.put(p.admissionDate, trends.getOrDefault(p.admissionDate, 0) + 1);
            fillTrends(node.right, trends);
        }
    }


    public PatientNodeType searchPatient(int id) {
        NodeType result = searchRec(root, id);
        if (result == null)
            return null;
        return (PatientNodeType) result;
    }

    public void displayPatients() {
        inorderPatients(root);
    }

    private void inorderPatients(NodeType node) {
        if (node != null) {
            inorderPatients(node.left);
            ((PatientNodeType)node).display();
            inorderPatients(node.right);
        }
    }


}
