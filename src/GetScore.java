import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;


public class GetScore {
	public static void main(String args[]){
		String[] files = new String[args.length];
		for(int i = 0; i < args.length; i ++){
			files[i] = args[i].toString();
		}
		final float t = (float)16618044/20000001;
		final float bt = (float)1000000/20000001;
		final float wt = (float)1000000/16618044;
//		System.out.println(t);
		FileWriter writer = null;
		File file = null;
		BufferedReader reader = null;
		for(int i = 0 ; i < files.length; i ++){
			float score = 0;
			file = new File(files[i]);
			try {
				reader = new BufferedReader(new FileReader(file));
				writer = new FileWriter(files[i] + ".score");
				String tempString = null;
				while((tempString = reader.readLine()) != null){
					String[] tmp = tempString.split("\t");
//					System.out.println(tmp.length);
//					System.out.println(tmp[0]);
//					System.out.println(tmp[1]);
//					System.out.println(tmp[2]);
					float b = Float.parseFloat(tmp[1]);
					float black = b*bt;
					float w = Float.parseFloat(tmp[2]);
					float white = w*wt;
					if(black < 10){
//						System.out.println("b < 10 && w < 10");
//						System.out.println(tempString);
						continue;
					}
					if(Float.compare(w, 0) == 0){
						
//						System.out.println("Float.compare(w, 0) == 0");
//						System.out.println(tempString);
						writer.write(tempString + "\t" + "-1" + "\n");
						continue;
					}
					score = b/w*t;
//					DecimalFormat decimaFormat = new DecimalFormat("0.00000");
//					BigDecimal bigDecimal = new BigDecimal(score);
//					System.out.println(score);
					
					//不使用科学计数法表示
					if(Float.compare(score, 0) != 0 && score < 1E-3 ){
						BigDecimal bigDecimal = new BigDecimal(score);

//						writer.write("###################################" + "\n");
//						writer.write(score + "\n");
//						writer.write(bigDecimal + "\n");
//						System.out.println("score < 1E-3");
//						System.out.println(tempString);
//						System.out.println(bigDecimal);
						writer.write(tmp[0] + "\t" + (int)(black+0.5) + "\t" + (int)(white+0.5) + "\t" + bigDecimal.toString().substring(0, 8) + "\n");
						continue;
					}
					writer.write(tmp[0] + "\t" + (int)(black+0.5) + "\t" + (int)(white+0.5) + "\t" + score + "\n");
				}
				reader.close();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
