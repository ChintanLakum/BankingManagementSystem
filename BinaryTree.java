import java.util.Random;
import java.util.Scanner;

public class BinaryTree {

    public class Node {
        int data;
        private Node left = null;
        private Node right = null;

        public Node(int data, Node left, Node right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }

        public Node getLeft() {
            return this.left;
        }

        public Node getRight() {
            return this.right;
        }

        public int getData() {
            return this.data;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        public void setData(int data) {
            this.data = data;
        }

    }

    public Node addNodeInBinaryTree(int data, Node root) {
        Node node = new Node(data, null, null);
        if (root == null) {
            root = node;
            System.out.println("Node is entered Successfully At Root Node.");

        } else if (root.getRight() == null || root.getLeft() == null) {
            if (root.getRight() == null) {
                root.setRight(node);
                System.out.println("Node is entered Successfully At right side of Root." + root.getData());
            } else {
                root.setLeft(node);
                System.out.println("Node is entered Successfully At Left side of Root." + root.getData());
            }
        } else {
            if (leftOrRightNode() == 0) {
                addNodeInBinaryTree(data, root.getLeft());
            } else {
                addNodeInBinaryTree(data, root.getRight());
            }
        }
        return root;
    }

    public void deleteNodeInBinaryTree(int data, Node root) {
        if (root != null) {
            // Node temp = root;
            
            if (root.getData() == data) {
                root =  setSubTree(root);
                System.out.println("It is The Main root of tree You can't delete it.");
            } else if (root.getLeft() != null && root.getLeft().getData() == data) {
                root.getLeft().setData(root.getLeft().getLeft().getData());
                System.out.println("Node is deleted successfully." + data);
                root.getLeft().setLeft(setSubTree(root.getLeft().getLeft()));
            } else if (root.getRight() != null && root.getRight().getData() == data) {
                root.getRight().setData(root.getRight().getRight().getData());
                System.out.println("Node is deleted successfully." + data);
                root.getRight().setRight(setSubTree(root.getRight().getRight()));
            } else {
                deleteNodeInBinaryTree(data, root.getLeft());
                deleteNodeInBinaryTree(data, root.getRight());
            }
        }
    }

    public void display(Node root) {
        System.out.println("preorder:");
        preOrder(root);
        System.out.println();

        System.out.println("inorder:");
        inOrder(root);
        System.out.println();

        System.out.println("postorder:");
        postOrder(root);
        System.out.println();
    }

    public Node subTree(int data, Node root) {
        Node newRoot;
        if (root.getData() == data) {
            newRoot = root;
            return newRoot;
        } else if (root.getLeft().getData() == data) {
            return root.getLeft();
        } else if (root.getRight().getData() == data) {
            return root.getRight();
        } else {
            if (leftOrRightNode() == 0)
                return subTree(data, root.getLeft());
            else
                return subTree(data, root.getRight());
        }
    }

    public void updateValueInNode(int oldData, int newData, Node root){
        if(root != null){
            if(root.getData() == oldData){
                root.setData(newData);
            }
            updateValueInNode(oldData, newData, root.getLeft());
            updateValueInNode(oldData, newData, root.getRight());
        }
    }

    public void preOrder(Node root) {
        if (root != null) {
            System.out.print(root.getData() + ", \t");
            preOrder(root.getLeft());
            preOrder(root.getRight());
        }
    }

    public void inOrder(Node root) {
        if (root != null) {
            inOrder(root.getLeft());
            System.out.print(root.getData() + ", \t");
            inOrder(root.getRight());
        }

    }

    public void postOrder(Node root) {
        if (root != null) {
            postOrder(root.getLeft());
            postOrder(root.getRight());
            System.out.print(root.getData() + ", \t");
        }

    }

    public boolean isLeaf(Node root) {
        if (root.getLeft() == null && root.getRight() == null)
            return true;
        return false;
    }

    public Node setSubTree(Node root) {
        if (!isLeaf(root)) {
            if (root.getLeft() == null || root.getRight() == null) {
                if (root.getLeft() != null) {
                    root.setData(root.getLeft().getData());
                    if (isLeaf(root.getLeft()))
                        root.setLeft(null);
                    else
                        setSubTree(root.getLeft());
                } else {
                    root.setData(root.getRight().getData());
                    if (isLeaf(root.getRight()))
                        root.setRight(null);
                    else
                        setSubTree(root.getRight());
                }
            } else {
                if (root.getLeft().getData() >= root.getRight().getData()) {
                    root.setData(root.getLeft().getData());
                    if (isLeaf(root.getLeft()))
                        root.setLeft(null);
                    else
                        setSubTree(root.getLeft());
                } else {
                    root.setData(root.getRight().getData());
                    if (isLeaf(root.getRight()))
                        root.setRight(null);
                    else
                        setSubTree(root.getRight());
                }
            }
            return root;
        } else {
            return null;
        }
    }

    

    public static int leftOrRightNode() {
        Random random = new Random();
        int leftOrRight = random.nextInt(2);
        System.out.println(leftOrRight);
        return leftOrRight;
    }

    public static void main(String[] args) {
        Scanner ip = new Scanner(System.in);
        Node root = null;
        BinaryTree br = new BinaryTree();
        while (true) {
            System.out.println("1. For add Node in Binary tree");
            System.out.println("2. For delete Node in Binary tree");
            System.out.println("3. For Display Binary tree");
            System.out.println("4. For Subtree of Binary tree");
            System.out.println("5. For Update value in Binar tree");
            System.out.println("6. For Exit");
            System.out.println("Enter Choice: ");
            int value = ip.nextInt();
            switch (value) {
                case 1: {
                    System.out.println("Enter Data: ");
                    int data = ip.nextInt();
                    root = br.addNodeInBinaryTree(data, root);
                    break;
                }

                case 2: {
                    System.out.println("Enter Data Which You want to delete: ");
                    int data = ip.nextInt();
                    br.deleteNodeInBinaryTree(data, root);
                    break;
                }

                case 3: {
                    br.display(root);
                    break;
                }

                case 4: {
                    System.out.println("Enter Data From Which You want To find subtree: ");
                    int data = ip.nextInt();
                    System.out.println("Subtree: ");
                    Node newRoot =br.subTree(data, root);
                    br.display(newRoot);
                    break;
                }

                case 5: {
                    System.out.println("Enter OldData From Which You want To find subtree: ");
                    int data = ip.nextInt();
                    System.out.println("Enter New Data From Which You want To find subtree: ");
                    int newData = ip.nextInt();
                    br.updateValueInNode(data, newData, root);
                    break;
                }

                case 6: {
                    System.exit(0);
                    break;
                } 
                default:
                    System.out.println("Entered Wrong Choice");
                    break;
            }

        }

    }
}