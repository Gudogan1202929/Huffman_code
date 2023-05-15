package application;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class DecodeFile {

	static Node roott2 = new Node();
	static int numNode = 0;
	static String HederDec = "";
	static String bodeDec = "";
	static int nodeNumber = 0;
	static String dirr = "";

	public static void DecodeFileMethod(File file) {

		dirr = file.getAbsolutePath();

		BufferedInputStream Buff = null;

		try {
			Buff = new BufferedInputStream(new FileInputStream(file.getAbsoluteFile()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		String str = "";
		int n = 0;
		int boll = 0;
		byte o[] = new byte[1];

		try {

			while ((n = Buff.read(o)) != -1) {
				n = (byte) n;
				str += o[0] + " ";
			}

			str = str.trim();
			String[] strAree = str.split(" ");

			int c = Integer.parseInt(strAree[0]);
			nodeNumber = (byte) (c);
			nodeNumber = (nodeNumber & 0xff);

			int s = Integer.parseInt(strAree[1]);
			boll = (byte) (s);
			boll = (boll & 0xff);

			if (boll == 1) {
				nodeNumber = 256;
			}

			String[] arrHedar = new String[2 + (nodeNumber * 4)];

			for (int i = 0; i < arrHedar.length; i++) {
				int k = Integer.parseInt(strAree[i]);
				k = (byte) (k);
				// k = (k & 0xff); // here
				HederDec += Main.toZerosAndOne(k);
			}

			String[] arr8ByteHeder = new String[(HederDec.length() / 8)];

			for (int i = 0, j = 0; i < HederDec.length(); j++, i += 8) {

				if (i + 8 < HederDec.length()) {
					arr8ByteHeder[j] = HederDec.substring(i, i + 8);
				} else {
					arr8ByteHeder[j] = HederDec.substring(i, HederDec.length());
					break;
				}
			}

			bodeDec = "";
			for (int i = 2 + (nodeNumber * 4); i < strAree.length; i++) {
				int k = Integer.parseInt(strAree[i]);
				k = (byte) (k);
				bodeDec += Main.toZerosAndOne(k);
			}

			toCreateAnTree(arr8ByteHeder);

			printInorder(roott2);

		} catch (IOException e) {
			e.printStackTrace();
		}

		writeOnFileOrgen(roott2, bodeDec);

	}

	static void printInorder(Node node) {
		if (node == null)
			return;

		/* first recur on left child */
		printInorder(node.getLeftChild());

		/* now recur on right child */
		printInorder(node.getRightChild());

		/* then print the data of node */
		if (node.isLeaf()) {
			System.out.print(node.getOldAsci() + " ");
		}

	}

	private static void writeOnFileOrgen(Node roott2, String arr) {

		File orgen = new File(dirr.substring(0, dirr.length() - 5));

		Node temp = roott2;
		String string = "";

		try {
			FileOutputStream oout = new FileOutputStream(orgen);

			arr = arr.substring(0, arr.length() - Main.overFlow);
			System.out.println(arr);
			System.out.println(Main.bodyDec);

			for (int i = 0; i < arr.length(); i++) {

				if (arr.charAt(i) == '0' && temp.getLeftChild() != null) {
					if (temp.getLeftChild().getOldAsci() > -499) {
						try {
							string = Main.toZerosAndOne(temp.getLeftChild().getOldAsci());
							oout.write((byte) temp.getLeftChild().getOldAsci());
							temp = roott2;
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else {
						temp = temp.getLeftChild();
					}
				} else if (arr.charAt(i) == '1' && temp.getRightChild() != null) {
					if (temp.getRightChild().getOldAsci() > -499) {
						try {
							string = Main.toZerosAndOne(temp.getRightChild().getOldAsci());
							oout.write(Main.ParsByte(string));
							temp = roott2;
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else {
						temp = temp.getRightChild();
					}
				} else {
				}
			}
			try {
				oout.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	private static void toCreateAnTree(String[] bytarr) {

		int n = 0;
		Node temp = null;
		String st = "";
		int conv = 0;

		for (int i = 2; i < bytarr.length; i += 3) {

			n = Main.ParsByte(bytarr[i]);
			i++;

			st = bytarr[i] + bytarr[i + 1];

			temp = roott2;
			Node node = new Node();
			conv = Main.ParsByte(bytarr[i + 2]);

			node.setOldAsci(conv);

			for (int j = 0; j < n; j++) {

				if (st.charAt(j) == '0') {
					if (temp.getLeftChild() == null && j + 1 == n) {
						temp.setLeftChild(node);
					} else if (temp.getLeftChild() == null && j + 1 != n) {
						temp.setLeftChild(new Node());
						temp = temp.getLeftChild();
					} else {
						temp = temp.getLeftChild();
					}
				} else if (st.charAt(j) == '1') {
					if (temp.getRightChild() == null && j + 1 == n) {
						temp.setRightChild(node);
						System.out.println("Texzs2");
					} else if (temp.getRightChild() == null && j + 1 != n) {
						temp.setRightChild(new Node());
						temp = temp.getRightChild();
					} else {
						temp = temp.getRightChild();
					}
				}
			}
			if (i + 3 == bytarr.length - 1 || i + 3 == bytarr.length) {
				break;
			}

		}

	}

}
