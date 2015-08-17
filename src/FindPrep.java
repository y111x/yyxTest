import java.io.*;

import edu.stanford.nlp.trees.*;

public class FindPrep {
	public static void main(String args[]) {
		String[] Files = new String[args.length]; 
//			{

//				 "/home/cikuu/limaolin/clue_snt_each650w_tree/clue_z.n5.snt.tree"
//				"C:\\Users\\zhdi\\test1.txt", "C:\\Users\\zhdi\\test2.txt"
//				};
		for(int i = 0; i < Files.length; i ++){
			Files[i] = args[i].toString();
		}
		for (int i = 0; i < Files.length; i++) {
			 String FileName = Files[i];
			 String WriteFileName = Files[i] + ".prep";
//			String FileName = "C:\\Users\\zhdi\\clue_a.n5.snt.tree";
//			String WriteFileName = "C:\\Users\\zhdi\\clue_a.n5.snt.tree.o";
			File file = new File(FileName);
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new FileReader(file));
				FileWriter writer = new FileWriter(WriteFileName);
				String tempString = null;
				while ((tempString = reader.readLine()) != null) {
					// System.out.println(tempString);
					// writer.write(tempString+"\n");
					Tree t = Tree.valueOf(tempString);
					// System.out.println(t.yieldWords().);
					TreeDependency.walk_dep(t, writer);

				}

				reader.close();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e1) {
					}
				}
			}// try-catch
		}// for(Files[])
	}// main
}


