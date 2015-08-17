import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.trees.EnglishGrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.PennTreebankLanguagePack;
import edu.stanford.nlp.trees.SemanticHeadFinder;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TypedDependency;

class TreeDependency {
	static SemanticHeadFinder headFinder = new SemanticHeadFinder(false); // keep
	static Predicate<String> puncFilter = new PennTreebankLanguagePack()
			.punctuationWordRejectFilter();

	static void walk_dep(Tree tree, FileWriter writer) {
		try {
			// FileWriter writer = new FileWriter(filename, true);
			GrammaticalStructure gs = new EnglishGrammaticalStructure(tree,
					puncFilter, headFinder);
			List<TypedDependency> tdl = gs.typedDependenciesCCprocessed();

			// ////////////////////////////////////////////////////////////

			List<Tree> trl = tree.subTreeList();
			List<Tree> T = new LinkedList<Tree>();
			for (Tree tr : trl) {
				if (tr.nodeString().contains("PP")) {
					// System.out.println(tr);
					T.add(tr);
					// System.out.println(T);
				}
			}
			// ////////////////////////////////////////////////////////////

			for (TypedDependency td : tdl) {
				String r = td.reln().toString();
				// System.out.println(td.gov().word());
				if (r.startsWith("prep_")) {
					String sub_r = r.substring(5);
					// int gov_idx = td.gov().index();
					// int dep_idx = td.dep().index();
					// System.out.println(td.gov());
					// System.out.println(tree.yieldWords().toString().split(",")[dep_idx-1].trim());
					String gov_word = td.gov().word();
					String dep_word = td.dep().word();
					String output = r + ":" + gov_word + " " + dep_word + "\n";
					writer.write(output);
					for (Tree t : T) {
						if (t.yield().toString().contains(sub_r)
								&& t.yield().toString().contains(dep_word)) {

							// System.out.println(t.yield().toString());
							String TreeString = "";
							String[] TreeWords = t.yieldWords().toString()
									.split(",");
							if (TreeWords.length > 10
									|| TreeWords[0].substring(1).trim()
											.equals(sub_r) == false) {
								continue;
							}
							for (int i = 0; i < TreeWords.length; i++) {
								System.out.println(TreeWords[i]);
								TreeString += TreeWords[i];
							}
							System.out.println(TreeString);
							if (TreeString.length() > 0) {
								int l = TreeString.length();
								TreeString = TreeString.substring(1, l - 1);
							}
							 System.out.println(TreeString);
							// System.out.println(sub_r);
							if (TreeString.startsWith(sub_r)) {
								writer.write("phrase:" + TreeString + "\n");
								break;
							}
						}

					}
				}
			}// for();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	static void getNP(Tree tree, FileWriter writer) {
		// GrammaticalStructure gs = new EnglishGrammaticalStructure(tree,
		// puncFilter, headFinder);

		List<Tree> trl = tree.subTreeList();
		List<Tree> T = new LinkedList<Tree>();
		tree.setSpans();
		tree.indexLeaves();
		// System.out.println(tree);
		List<String> treeStrings = new ArrayList<String>();
		treeStrings.add("^");
		for (TaggedWord t : tree.taggedYield()) {
			if (t.tag().equals("PRP") || t.tag().equals("NNP")
					|| t.tag().equals("NNPS"))
				treeStrings.add(t.value().toString());
			else
				treeStrings.add(t.value().toLowerCase());
			treeStrings.add(t.tag().toString());
		}
		treeStrings.add("");
		treeStrings.add("");
//		System.out.println(treeStrings);
//		try {
//			// writer.write(tree+"\n");
//			writer.write(treeStrings.toString() + "\n");
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
		for (Tree tr : trl) {
			if (tr.nodeString().equals("NP")) {

				// System.out.println(tr.getSpan());
				// System.out.println(tr.yieldWords());
				// System.out.println(tr.getLeaves().get(0).labels());
				// System.out.println();
				if (tr.yield().size() > 5 || tr.yield().size() == 1)
					continue;
				T.add(tr);
				// System.out.println(T);
			}
		}
		// ////////////////////////////////////////////////////////////

		// String NPString = "";
		// String TaggedNP = "";
		for (Tree t : T) {
			// System.out.println(TreeString);
			// System.out.println(t);
			int FirstIndex = t.getSpan().get(0);
			// System.out.println(FirstIndex);
			int LastIndex = t.getSpan().get(1);
			// System.out.println(LastIndex);
			int tSize = t.yield().size();
			int PrintStringSize = (int) Math.pow(2, tSize);
			String[] PrintStrings = new String[PrintStringSize];
			for (int i = 0; i < Math.pow(2, tSize); i++) {
				int j = 0;
				int k = i;
				int tmp = 0;
				PrintStrings[i] = "";
				while (j < tSize) {
					tmp = k % 2;
					k = k / 2;
					PrintStrings[i] += treeStrings.get(FirstIndex * 2 + 1 + 2 * j
							+ tmp)
							+ " ";
					j++;
				}
			}
			// for(int i = 0; i < PrintStringSize;i ++){
			// System.out.println(PrintStrings[i]);
			// }
			int end = 2*LastIndex + 4;
			int front = 2*FirstIndex;
			try {

				writer.write("word:" + treeStrings.get(front)
						+ " " + PrintStrings[0] + treeStrings.get(end) + "\n");
				writer.write("pos:" + treeStrings.get(front) + " "
						+ PrintStrings[PrintStringSize - 1]
						+ treeStrings.get(end) + "\n");
				for (int i = 1; i < PrintStringSize - 1; i++) {
					writer.write("word_pos:"
							+ treeStrings.get(front) + " "
							+ PrintStrings[i] + treeStrings.get(end) + "\n");
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}// for()

	}
	
	static void NGram(Tree tree, FileWriter writer, int n){
//		tree.setSpans();
//		tree.indexLeaves();
		// System.out.println(tree);
		List<String> treeStrings = new ArrayList<String>();
		try{
		ArrayList<TaggedWord> ListTags = tree.taggedYield();
		if(ListTags.equals(null) || ListTags.size() < n) return;
		for (TaggedWord t : tree.taggedYield()) {
			treeStrings.add("_" + t.tag().toLowerCase());
		}
		}catch(Exception e1){
			System.out.println(tree);
			return;
		}
		for(int i = 0; i <= treeStrings.size() - n; i ++){
			try {
				for(int j = 0; j < n-1; j ++){
				writer.write(treeStrings.get(i+j) + " " );
				}
				writer.write(treeStrings.get(i+n-1) + "\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}