package 郑迪;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TestHttpRequest {

	public static void main(String[] args) throws Exception {
//		JSONObject postag=new JSONObject();
//		postag.put("operator", "and");
//		postag.put("analyzer", "whitespace");
//		postag.put("type", "phrase");
//		postag.put("slop", 0);
//		String query="dog";
//		postag.put("query", query);
//		JSONObject Match = new JSONObject();
//		Match.put("postag", postag);
//		JSONObject Query = new JSONObject();
//		Query.put("match", Match);
//		/**
//		 * 为什么一个Query要写这么复杂
//		 */
//		JSONObject HttpQuery=new JSONObject();
//		JSONArray Fields = new JSONArray();
//		Fields.put("");
//		HttpQuery.put("fields", Fields);
//		HttpQuery.put("size", 0);
//		HttpQuery.put("query", Query);
//		System.out.println(HttpQuery.toString());
//		/**
//		 * 查询黑名单
//		 */
//		String blackReturn=HttpRequest.doPost(HttpQuery.toString(),
//				"http://10.110.6.43:9200/essay/_search");
//		System.out.println(blackReturn);
//		/**
//		 * 查询白名单
//		 */
//		String whiteReturn=HttpRequest.doPost(HttpQuery.toString(),
//							"http://10.110.6.43:9200/pigai/_search");
//		System.out.println(whiteReturn);
//		JSONObject BlackJs = new JSONObject(blackReturn);
//		JSONObject WhiteJs = new JSONObject(whiteReturn);
//		String blackNum = BlackJs.optJSONObject("hits").opt("total").toString();
//		String whiteNum = WhiteJs.optJSONObject("hits").opt("total").toString();
//		System.out.println(blackNum+" "+whiteNum);
		getBlack(", a better");
		getWhite(", better");
	}
public static int getBlack(String query) throws Exception{
	JSONObject postag=new JSONObject();
	postag.put("operator", "and");
	postag.put("analyzer", "whitespace");
	postag.put("type", "phrase");
	postag.put("slop", 0);
	postag.put("query", query);
	JSONObject Match = new JSONObject();
	Match.put("postag", postag);
	JSONObject Query = new JSONObject();
	Query.put("match", Match);
	JSONObject HttpQuery=new JSONObject();
	JSONArray Fields = new JSONArray();
	Fields.put("");
	HttpQuery.put("fields", Fields);
	HttpQuery.put("size", 0);
	HttpQuery.put("query", Query);
	String blackReturn=HttpRequest.doPost(HttpQuery.toString(),
			"http://10.110.6.43:9200/essay/_search");
	JSONObject BlackJs = new JSONObject(blackReturn);
	String blackNum = BlackJs.optJSONObject("hits").opt("total").toString();
	int bNum=Integer.parseInt(blackNum);
	System.out.println(bNum);
	return bNum;
}
public static int getWhite(String query) throws Exception{
	JSONObject postag=new JSONObject();
	postag.put("operator", "and");
	postag.put("analyzer", "whitespace");
	postag.put("type", "phrase");
	postag.put("slop", 0);
	postag.put("query", query);
	JSONObject Match = new JSONObject();
	Match.put("postag", postag);
	JSONObject Query = new JSONObject();
	Query.put("match", Match);
	JSONObject HttpQuery=new JSONObject();
	JSONArray Fields = new JSONArray();
	Fields.put("");
	HttpQuery.put("fields", Fields);
	HttpQuery.put("size", 0);
	HttpQuery.put("query", Query);
	/**
	 * 以上全是封装json，感觉有点太麻烦了
	 */
	String whiteReturn=HttpRequest.doPost(HttpQuery.toString(),
			"http://10.110.6.43:9200/pigai/_search");
	JSONObject WhiteJs = new JSONObject(whiteReturn);
	String whiteNum = WhiteJs.optJSONObject("hits").opt("total").toString();
	int wNum=Integer.parseInt(whiteNum);
	System.out.println(wNum);
	return wNum;
}
}
