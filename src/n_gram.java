import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import edu.stanford.nlp.trees.Tree;

public class n_gram {
	public static void main(String args[]) {
		// args[0] = "C:\\Users\\zhdi\\clue_a.n5.snt.tree";
		// args[1] = "3";
		String[] Files = new String[args.length - 1];
		// {
		// "/home/cikuu/limaolin/clue_snt_each650w_tree/clue_a.n5.snt.tree",
		// "/home/cikuu/limaolin/clue_snt_each650w_tree/clue_b.n5.snt.tree",
		// "/home/cikuu/limaolin/clue_snt_each650w_tree/clue_c.n5.snt.tree",
		// "/home/cikuu/limaolin/clue_snt_each650w_tree/clue_d.n5.snt.tree",
		// "/home/cikuu/limaolin/clue_snt_each650w_tree/clue_e.n5.snt.tree",
		// "/home/cikuu/limaolin/clue_snt_each650w_tree/clue_f.n5.snt.tree",
		// "/home/cikuu/limaolin/clue_snt_each650w_tree/clue_g.n5.snt.tree",
		// "/home/cikuu/limaolin/clue_snt_each650w_tree/clue_h.n5.snt.tree",
		// "/home/cikuu/limaolin/clue_snt_each650w_tree/clue_i.n5.snt.tree",
		// "/home/cikuu/limaolin/clue_snt_each650w_tree/clue_j.n5.snt.tree",
		// "/home/cikuu/limaolin/clue_snt_each650w_tree/clue_k.n5.snt.tree",
		// "/home/cikuu/limaolin/clue_snt_each650w_tree/clue_l.n5.snt.tree",
		// "/home/cikuu/limaolin/clue_snt_each650w_tree/clue_m.n5.snt.tree",
		// "/home/cikuu/limaolin/clue_snt_each650w_tree/clue_n.n5.snt.tree",
		// "/home/cikuu/limaolin/clue_snt_each650w_tree/clue_o.n5.snt.tree",
		// "/home/cikuu/limaolin/clue_snt_each650w_tree/clue_p.n5.snt.tree",
		// "/home/cikuu/limaolin/clue_snt_each650w_tree/clue_q.n5.snt.tree",
		// "/home/cikuu/limaolin/clue_snt_each650w_tree/clue_r.n5.snt.tree",
		// "/home/cikuu/limaolin/clue_snt_each650w_tree/clue_s.n5.snt.tree",
		// "/home/cikuu/limaolin/clue_snt_each650w_tree/clue_t.n5.snt.tree",
		// "/home/cikuu/limaolin/clue_snt_each650w_tree/clue_u.n5.snt.tree",
		// "/home/cikuu/limaolin/clue_snt_each650w_tree/clue_v.n5.snt.tree",
		// "/home/cikuu/limaolin/clue_snt_each650w_tree/clue_w.n5.snt.tree",
		// "/home/cikuu/limaolin/clue_snt_each650w_tree/clue_x.n5.snt.tree",
		// "/home/cikuu/limaolin/clue_snt_each650w_tree/clue_y.n5.snt.tree",
		// "/home/cikuu/limaolin/clue_snt_each650w_tree/clue_z.n5.snt.tree"
		// "C:\\Users\\zhdi\\test1.txt",
		// "C:\\Users\\zhdi\\clue_a.n5.snt.tree"
		// };
		for (int i = 0; i < Files.length; i++) {
			Files[i] = args[i].toString();
		}
		int n = (int) args[args.length - 1].charAt(0) - 48;
		System.out.println(n);
		for (int i = 0; i < Files.length; i++) {
			String FileName = Files[i];
			String WriteFileName = Files[i] + "." + n + "gram";
			File file = new File(FileName);
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new FileReader(file));
				FileWriter writer = new FileWriter(WriteFileName);
				String tempString = null;
				while ((tempString = reader.readLine()) != null) {
					try {
						Tree t = Tree.valueOf(tempString);
						if (t == null) {
							System.out.println(tempString);
							continue;
						}
						TreeDependency.NGram(t, writer, n);
					} catch (Exception e1) {
						continue;
					}
				}// while()

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
