import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import Ö£µÏ.HttpRequest;

public class RemoteSearch {
	public static void main(String args[]) {
		String[] Files = new String[args.length - 2];
		int n = Integer.parseInt(args[args.length-2]);
		int m = Integer.parseInt(args[args.length-1]);
//		System.out.println(n);
		for (int i = 0; i < Files.length; i++) {
			Files[i] = args[i].toString();
		}
		JSONObject o1 = new JSONObject();
		
		JSONArray Fields = new JSONArray();
		Fields.put("");
		JSONObject Query = new JSONObject();
		JSONObject Match = new JSONObject();
		JSONObject postag = new JSONObject();
		String blackstring = null;
		String whitestring = null;
		JSONObject getBlack = null;
		JSONObject getWhite = null;
		try {
			o1.put("fields", Fields);
			o1.put("size", 0);
			postag.put("operator", "and");
			postag.put("analyzer", "whitespace");
			postag.put("type", "phrase");
			postag.put("slop", 0);
			
		} catch (JSONException e1) {
			e1.printStackTrace();
		}

		
		FileWriter writer;

		for (int i = 0; i < Files.length; i++) {
			String FileName = Files[i];
			String WriteFileName = Files[i] + ".get" + n;
			File file = new File(FileName);
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new FileReader(file));
				writer = new FileWriter(WriteFileName, true);
				String tempString = null;
				int count = 0;
				while ((tempString = reader.readLine()) != null) {				
					count ++;
					if(count < n)
						continue;
					if(count % 100 == 0){
						System.out.println("ÒÑ´¦Àí" + count);
						writer.flush();
					}
					if(count > m){
						
						break;
					}
					postag.put("query", tempString);
					Match.put("postag", postag);
					Query.put("match", Match);
					o1.put("query", Query);
					System.out.println(o1);
					
					blackstring = HttpRequest.doPost(o1.toString(),
							"http://10.110.6.43:9200/essay/_search");
					System.out.println(blackstring);
					whitestring = HttpRequest.doPost(o1.toString(),
							"http://10.110.6.43:9200/pigai/_search");
					System.out.println(whitestring);
					getBlack = new JSONObject(blackstring);
					getWhite = new JSONObject(whitestring);
					
					String blackNum = getBlack.optJSONObject("hits").opt("total").toString();
					String whiteNum = getWhite.optJSONObject("hits").opt("total").toString();
					if(blackNum.equals("0") && whiteNum.equals("0")){
						continue;
					}
					// System.out.println(blackstring);
//					System.out.println(blackNum);
					// System.out.println(whitestring);
//					System.out.println(whiteNum);
					
//					System.out.println(tempString);
					writer.write(tempString + "\t" + blackNum + "\t" + whiteNum + "\n");
//					System.out.println(tempString + "\t" + blackNum + "\t" + whiteNum + "\n");
					
				}
				reader.close();
				writer.close();
				System.out.println("finished" + " " + m);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
}
