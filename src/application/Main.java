package application;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.PriorityQueue;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {

	public int[] byteAree = new int[256];
	public static int muberNode = 0;
	public String fileName = "";
	public static Node root;
	public int fileSizrBefore = 0;
	public static String forHeder = "";
	BufferedInputStream Buff = null;
	int How_Much_Char = 0;
	public static String what = "";
	public static String Directory = "";
	public static int overFlow = 0;
	public static int kiloo = 0;
	public static String s3 = "";
	public static boolean is256 = false;
	public static String bodyDec = "";

	public static void main(String[] args) {

		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		scene1(primaryStage);
	}

	public void scene1(Stage stage) {

		BorderPane bp = new BorderPane();

		VBox vb = new VBox(20);

		Label lCoinExp = new Label();
		Label l2 = new Label();
		Label lDpTable = new Label();
		Label l4 = new Label();
		Label LabExpeacted = new Label();

		Button Enter = new Button("Extract Here To Huffman");
		Enter.setStyle("-fx-background-color: rgb(211, 211, 211);-fx-background-radius: 20;-fx-text-fill: red");
		Enter.setMinHeight(40);
		Enter.setMinWidth(160);

		Button pEXP = new Button("Decode Hufmaan File");
		pEXP.setStyle("-fx-background-color: rgb(211, 211, 211);-fx-background-radius: 20;-fx-text-fill: red");
		pEXP.setMinHeight(40);
		pEXP.setMinWidth(200);

		Button pDpTable = new Button("Information");
		pDpTable.setStyle("-fx-background-color: rgb(211, 211, 211);-fx-background-radius: 20;-fx-text-fill: red");
		pDpTable.setMinHeight(40);
		pDpTable.setMinWidth(240);

		vb.getChildren().addAll(l4, Enter, l2, pEXP, LabExpeacted, pDpTable, lDpTable, lCoinExp);

		bp.setCenter(vb);
		vb.setAlignment(Pos.CENTER);

		bp.setBackground(new Background(new BackgroundImage(new Image("im.png"), null, null, null, null)));

		Scene scene1 = new Scene(bp, 850, 420);
		stage.setScene(scene1);
		stage.setTitle("Optimal Strategy for a Game using Dynamic Programming");
		stage.show();

		Enter.setOnAction(e -> {

			FileChooser passFileChooser = new FileChooser();
			passFileChooser.setTitle("select Passengers File");
			passFileChooser.setInitialDirectory(new File("."));
			passFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files", "*"));

			try {
				read(passFileChooser.showOpenDialog(stage));
			} catch (Exception e1) {
				System.out.println(e1.getMessage());
			}
		});

		pEXP.setOnAction(e -> {

			FileChooser passFileChooser = new FileChooser();
			passFileChooser.setTitle("select Passengers File");
			passFileChooser.setInitialDirectory(new File("."));
			passFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files", "*"));

			try {
				DecodeFile.DecodeFileMethod(passFileChooser.showOpenDialog(stage));
			} catch (Exception e1) {
				System.out.println(e1.getMessage());
			}
		});

		pDpTable.setOnAction(e -> {
			cene4(stage);
		});

	}

	private void read(File file) throws Exception {

		String allFile = "";
		try {

			Directory = file.getAbsolutePath() + ".huff";

			Buff = new BufferedInputStream(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int b = 0;

		try {
			while ((b = Buff.read()) != -1) {

				b = (byte) b;

				byteAree[b & 0xff]++;
				allFile += b + " ";
				if (byteAree[b & 0xff] == 1) {
					How_Much_Char++;
					muberNode++;
				}
			}
			System.out.println(allFile);

		} catch (IOException e) {
			Alert a = new Alert(Alert.AlertType.ERROR);
			a.setContentText("File is empty");
			a.setHeaderText("Error");
			a.show();
		}

		Node[] nodeLife = new Node[256];

		if (muberNode == 256) {
			is256 = true;
		}

		for (int i = 0; i < byteAree.length; i++) {
			if (byteAree[i] > 0) {
				nodeLife[i] = new Node(i, byteAree[i]);
			}
		}

		PriorityQueue<Node> heap = new PriorityQueue<>();

		int size = 0;

		for (int i = 0; i < nodeLife.length; i++) {
			if (nodeLife[i] != null) {
				heap.add(nodeLife[i]);
				size++;
			}
		}

		root = buledTree(heap, size);
		fileSizrBefore = root.getFreq();

		Node[] newASCI = new Node[size];
		for (int i = 0; i < newASCI.length; i++) {
			newASCI[i] = new Node();
		}

		toKnowEveryCharAscii(root, newASCI);

		for (int i = 0; i < newASCI.length; i++) {
			System.out.println(newASCI[i].getOldAsci() + ":" + newASCI[i].getFinalDir());
		}

		printCode(root, newASCI);

		System.out.println(forHeder);
		System.out.println(forHeder.length());

		for (int i = 0; i < newASCI.length; i++) {
			what += (char) newASCI[i].getOldAsci() + "         :     Huffman Code:  " + newASCI[i].getFinalDir()
					+ "         :     Old Path:  " + toZerosAndOne(i) + "\n";
		}

//		DecodeFile.printInorder(root);
		writeOnFile(root, allFile, newASCI);

	}

	int i = 0;
	String rod = "";

	private void toKnowEveryCharAscii(Node root2, Node[] newASCI) {

		if (root2 != null) {
			if (root2.getDir() != null) {
				rod += root2.getDir();
			}
			if (root2.isLeaf() && root2.getOldAsci() != -1) {
				newASCI[i].setOldAsci(root2.getOldAsci());
				newASCI[i].setFinalDir(rod);
				newASCI[i].setRodLong(rod.length());
				i++;
				if (rod.length() > 0)
					rod = rod.substring(0, rod.length() - 1);
				return;
			}

			toKnowEveryCharAscii(root2.getLeftChild(), newASCI);
			toKnowEveryCharAscii(root2.getRightChild(), newASCI);
			if (rod.length() > 0)
				rod = rod.substring(0, rod.length() - 1);
		}
		return;
	}

	private void writeOnFile(Node root2, String inFile, Node[] newAsci) {

		File newFile = new File(Directory);
		FileOutputStream out = null;

		try {
			out = new FileOutputStream(newFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String str = "";
		int k = 0;

		try {
			out.write(ParsByte(toZerosAndOne(newAsci.length)));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		if (is256 == true) {
			try {
				out.write(ParsByte("00000001"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				out.write(ParsByte("00000000"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		while (true) {
			if (k + 8 >= forHeder.length()) {
				str = forHeder.substring(k, forHeder.length());
				kiloo++;
				s += str + " ";
				try {
					out.write(ParsByte(str));
					break;
				} catch (IOException e) {
					e.printStackTrace();
				}

				break;
			} else if (k + 8 < forHeder.length()) {
				str = forHeder.substring(k, k + 8);
				kiloo++;
				s += str + " ";
				try {
					out.write(ParsByte(str));
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				break;
			}
			k += 8;
		}

		str = "";
		k = 0;

		String whatInToWrite = toZerosAndOne(newAsci, inFile);
		s3 = whatInToWrite;

		while (true) {
			if (k + 8 >= whatInToWrite.length()) {
				str = whatInToWrite.substring(k, whatInToWrite.length());
				bodyDec = bodyDec + str;
				kiloo++;
				s += str + " ";
				try {
					out.write(ParsByte(str));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			} else if (k + 8 < whatInToWrite.length()) {
				str = whatInToWrite.substring(k, k + 8);
				bodyDec = bodyDec + str;
				kiloo++;
				s += str + " ";
				try {
					out.write(ParsByte(str));
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				break;
			}
			k += 8;
		}

		try {
			out.close();
//			String q = "1111111100000001"+forHeder+bodyDec;
//			System.out.println(q);
//			System.out.println(q.length());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Node buledTree(PriorityQueue<Node> heap, int size) {

		Node n = null;
		for (int i = 0; i < size - 1; i++) {
			Node temp = heap.remove();
			temp.setDir("0");
			Node temp2 = heap.remove();
			temp2.setDir("1");
			n = new Node(temp.getFreq() + temp2.getFreq());
			n.setLeftChild(temp);
			n.setRightChild(temp2);

			heap.add(n);
		}
		return n;
	}

	String s = "";

	public static void printCode(Node root, Node[] newAci) {

		String str = "";

		for (int i = 0; i < newAci.length; i++) {

			if (newAci[i].getFinalDir().length() == 16) {
				forHeder += toZerosAndOne(newAci[i].getRodLong()) + newAci[i].getFinalDir()
						+ toZerosAndOne(newAci[i].getOldAsci());
			} else if (newAci[i].getFinalDir().length() < 16) {
				str = newAci[i].getFinalDir();
				for (int j = 0; j < 16 - newAci[i].getFinalDir().length(); j++) {
					str += "0";
				}
				forHeder += toZerosAndOne(newAci[i].getRodLong()) + str + toZerosAndOne(newAci[i].getOldAsci());
			}
		}
		return;
	}

	public static String toZerosAndOne(int asci) {

		String res = "";

		if (asci < 0) {
			asci = asci & 0xff;
		}

		if (asci >= 0) {
			if (asci - 128 >= 0) {
				res += "1";
				asci = asci - 128;
			} else {
				res += "0";
			}

			if (asci - 64 >= 0) {
				res += "1";
				asci = asci - 64;
			} else {
				res += "0";
			}
			if (asci - 32 >= 0) {
				res += "1";
				asci = asci - 32;
			} else {
				res += "0";
			}
			if (asci - 16 >= 0) {
				res += "1";
				asci = asci - 16;
			} else {
				res += "0";
			}
			if (asci - 8 >= 0) {
				res += "1";
				asci = asci - 8;
			} else {
				res += "0";
			}
			if (asci - 4 >= 0) {
				res += "1";
				asci = asci - 4;
			} else {
				res += "0";
			}
			if (asci - 2 >= 0) {
				res += "1";
				asci = asci - 2;
			} else {
				res += "0";
			}
			if (asci - 1 >= 0) {
				res += "1";
				asci = asci - 1;
			} else {
				res += "0";
			}
		}
		return res;

	}

	public static String toZerosAndOne(Node[] as, String infile) {

		String toWrite = "";

		infile = infile.trim();
		String[] asciString = infile.split(" ");

		int[] covert = new int[asciString.length];

		for (int i = 0; i < covert.length; i++) {
			covert[i] = Integer.parseInt(asciString[i]);
		}

		for (int i = 0; i < covert.length; i++) {
			for (int j = 0; j < as.length; j++) {

				if ((covert[i] & 0xff) == as[j].getOldAsci()) {
					toWrite += as[j].getFinalDir();
				}
			}
		}

		return toWrite;
	}

	public static byte ParsByte(String st) {
		byte b = 0;
		int i = 0;

		if (st.length() < 8) {
			overFlow = 8 - st.length();
			for (int j = 0; j < 8; j++) {
				if (st.length() != 8) {
					st += "0";
				}
			}
		}

		if (st.charAt(i) == '1') {
			b += 128;
		}
		i++;
		if (st.charAt(i) == '1') {
			b += 64;
		}
		i++;
		if (st.charAt(i) == '1') {
			b += 32;
		}
		i++;
		if (st.charAt(i) == '1') {
			b += 16;
		}
		i++;
		if (st.charAt(i) == '1') {
			b += 8;
		}
		i++;
		if (st.charAt(i) == '1') {
			b += 4;
		}
		i++;
		if (st.charAt(i) == '1') {
			b += 2;
		}
		i++;
		if (st.charAt(i) == '1') {
			b += 1;
		}

		return b;
	}

	// to show expected value
	public void cene4(Stage stage) {

		BorderPane bp7 = new BorderPane();

		Label l71 = new Label("The expected result");
		l71.setStyle("-fx-font: 24 arial;");

		bp7.setTop(l71);
		BorderPane.setAlignment(l71, Pos.TOP_CENTER);

		HBox hb7 = new HBox(20);
		Button b61 = new Button("done");
		b61.setStyle("-fx-background-color: rgb(211, 211, 211);-fx-background-radius: 20;-fx-text-fill: red");
		b61.setMinHeight(40);
		b61.setMinWidth(80);

		Button b62 = new Button("back");
		b62.setStyle("-fx-background-color: rgb(211, 211, 211);-fx-background-radius: 20;-fx-text-fill: red");
		b62.setMinHeight(40);
		b62.setMinWidth(80);

		hb7.getChildren().addAll(b61, b62);
		bp7.setBottom(hb7);
		BorderPane.setAlignment(hb7, Pos.CENTER);

		TextArea l72 = new TextArea("  ");
		l72.setStyle("-fx-font: 24 arial;");

		bp7.setCenter(l72);
		BorderPane.setAlignment(l72, Pos.TOP_CENTER);

		Scene scene7 = new Scene(bp7, 1200, 700);
		stage.setScene(scene7);
		stage.setTitle("The expected result");
		stage.show();

		b61.setOnAction(e -> {
			l72.setText(what);
		});

		b62.setOnAction(e -> {
			scene1(stage);
		});

	}

}
