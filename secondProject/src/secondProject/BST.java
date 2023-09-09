package secondProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BST {

	class Node {
		int key;
		int value;
		Node left, right;

		public Node(int item) {
			key = item;
			left = right = null;
		}
	}

	Node root;

	BST() {
		root = null;
	}
	
	Node maximumNode(Node r)
    {
        while (r.right != null) {
            r = r.right;
        }
        return r;
    }

	void insert(int key) {
		root = insertNode(root, key);
	}

	Node insertNode(Node root, int key) {

		if (root == null) {
			root = new Node(key);
			return root;
		}

		if (key<=root.key)
			root.left = insertNode(root.left, key);
		else if (key > root.key)
			root.right = insertNode(root.right, key);

		return root;
	}
	
	void postorder() {
		postorderRec(root);
	}
	
	void postorderRec(Node root) {
		if (root != null) {
			postorderRec(root.left);
			postorderRec(root.right);
			root.value = numberOfChild(root) - 1;
			System.out.print("[" + root.key + ", " + root.value + "]    ");
		}
	}
	
	Node deleteNode(Node root, int key)
    {
        if (root == null) {
            return null;
        }
        if (key < root.key) {
            root.left = deleteNode(root.left, key);
        }
        else if (key > root.key) {
            root.right = deleteNode(root.right, key);
        }
        else
        {
            if (root.left == null && root.right == null) {
                return null;
            }
            else if (root.left != null && root.right != null)
            {
                Node findMax = maximumNode(root.left);
 
                root.key = findMax.key;
                root.left = deleteNode(root.left, findMax.key);
            }
            else
            {
                Node c = null;
                if(root.left != null)
                	c = root.left;
                else
                	c = root.right;
                root = c;
            }
        }
        return root;
    }
	
	Node deleteRoot(Node r) {
		return deleteNode(r, r.key);
	}
	
	Node deleteMax(Node r) {
		return deleteNode(r, maximumNode(r).key);
	}

	int numberOfChild(Node r) {
		if (r == null)
			return 0;
		int l = 1;
		l += numberOfChild(r.left);
		l += numberOfChild(r.right);
		return l;
	}

	int soySayisi(Node root) {
		if (root == null)
			return 0;
		int res = root.value;
		res += soySayisi(root.right);
		res += soySayisi(root.left);
		return res;
	}

	public static void main(String[] args) {
		String[] parts = null;
		String[] parts2 = null;
		try {
			File myObj = new File("oyuncu1.txt");
			Scanner myReader = new Scanner(myObj);
			String key = myReader.nextLine();
			parts = key.split("#");
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		
		try {
			File myObj = new File("oyuncu2.txt");
			Scanner myReader = new Scanner(myObj);
			String key = myReader.nextLine();
			parts2 = key.split("#");
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		
		BST tree = new BST();
		for(int i = 0 ; i < parts.length ; i++) {
			tree.insert(Integer.parseInt(parts[i]));
		}
		
		BST tree2 = new BST();
		
		for(int i = 0 ; i < parts.length ; i++) {
			tree2.insert(Integer.parseInt(parts2[i]));
		}
		int score1 = 0, score2 = 0, tur = 0, last1 = 0, last2 = 0;
		
		while(true) {
			System.out.println("oyuncu1 agaci postorder gezinti sonucu :");
			tree.postorder();
			int soy1 = tree.soySayisi(tree.root);
			System.out.print("\noyuncu1 agaci toplam soy sayisi : " + soy1 + "\n");
			
			System.out.println("*********************************************************");
			System.out.println("oyuncu2 agaci postorder gezinti sonucu :");
			tree2.postorder();
			int soy2 = tree2.soySayisi(tree2.root);
			System.out.print("\noyuncu2 agaci toplam soy sayisi : " + soy2 + "\n");
			tur++;
			if(soy1 < soy2) {
				score1++;
				System.out.println("======Oyuncu 1 bu turu kazandi======");
				System.out.println("Tur " + tur + " Toplam Skor");
				System.out.println("Oyuncu1: " + score1 );
				System.out.println("Oyuncu2: " + score2 );	
				int ins =  tree2.maximumNode(tree2.root).key;
				tree2.deleteMax(tree2.root);
				tree.insert(ins);
				ins = tree.root.key;
				tree.deleteRoot(tree.root);
				tree2.insert(ins);
				last1++;
				last2 = 0;
				
			}
			else if(soy1 > soy2) {
				score2++;
				System.out.println("======Oyuncu 2 bu turu kazandi======");
				System.out.println("Tur " + tur + " Toplam Skor");
				System.out.println("Oyuncu1: " + score1 );
				System.out.println("Oyuncu2: " + score2 );		
				int ins =  tree.maximumNode(tree.root).key;
				tree.deleteMax(tree.root);
				tree2.insert(ins);
				ins = tree2.root.key;
				tree2.deleteRoot(tree2.root);
				tree.insert(ins);
				last2++;
				last1 = 0;
			}
			else {
				last2 = 0;
				last1 = 0;
				System.out.println("======Tur berabere sonuçlandi======");
				int ins =  tree.root.key;
				int ins2 =  tree2.root.key;
				tree.deleteRoot(tree.root);
				tree2.deleteRoot(tree2.root);
				tree.insert(ins2);
				tree2.insert(ins);
				
			}
			System.out.println("Devam (D)");
			Scanner k = new Scanner(System.in);
			String c = k.nextLine();
			if(last1 == 5 || last2 == 5 || tur == 20 || !(c.equals("D") || c.equals("d"))) {
				break;
			}
			
		}
		if(score1 > score2) {
			System.out.println("Oyun bitti! Oyuncu 1 kazandi!");
		}
		else if (score2 > score1) {
			System.out.println("Oyun bitti! Oyuncu 2 kazandi!");
		}
		else {
			System.out.println("Oyun bitti! Berabere sonuclandi!");
		}
	}
}
