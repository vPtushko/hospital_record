public class BinaryTreeType {
    protected NodeType root;

    public BinaryTreeType() {
        root = null;
    }

    public boolean search(int searchItem) {
        return false; // will be implemented in subclass
    }

    public void insert(int insertItem) {
        // will be implemented in subclass
    }

    public void deleteNode(int deleteItem) {
        // will be implemented in subclass
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void inorderTraversal() {
        inorder(root);
        System.out.println();
    }

    public void preorderTraversal() {
        preorder(root);
        System.out.println();
    }

    public void postorderTraversal() {
        postorder(root);
        System.out.println();
    }

    public int treeHeight() {
        return height(root);
    }

    public int treeNodeCount() {
        return nodeCount(root);
    }

    public int treeLeavesCount() {
        return leavesCount(root);
    }

    // ---- Private helper methods ----
    private void inorder(NodeType p) {
        if (p != null) {
            inorder(p.left);
            System.out.print(p.value + " ");
            inorder(p.right);
        }
    }

    private void preorder(NodeType p) {
        if (p != null) {
            System.out.print(p.value + " ");
            preorder(p.left);
            preorder(p.right);
        }
    }

    private void postorder(NodeType p) {
        if (p != null) {
            postorder(p.left);
            postorder(p.right);
            System.out.print(p.value + " ");
        }
    }

    private int height(NodeType p) {
        if (p == null) return 0;
        return 1 + Math.max(height(p.left), height(p.right));
    }

    private int nodeCount(NodeType p) {
        if (p == null) return 0;
        return 1 + nodeCount(p.left) + nodeCount(p.right);
    }

    private int leavesCount(NodeType p) {
        if (p == null) return 0;
        if (p.left == null && p.right == null) return 1;
        return leavesCount(p.left) + leavesCount(p.right);
    }

}
