import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



public class Dictionary implements Serializable{
	File actual = new File("CSV/");
	File alpha[] = actual.listFiles();
	MinDir words[] = new MinDir[alpha.length];
	int alphaSize[] = new int[alpha.length] ;
	
	public Dictionary() throws InterruptedException, FileNotFoundException, IOException, ParseException{
		Scanner sc;
		JSONParser parser = new JSONParser();
		for(int i=0;i< alpha.length;i++){
			int ct = 0;
			JSONArray a = (JSONArray) parser.parse(new FileReader(alpha[i]));
			words[i] = new MinDir((char)(i + 65));
			
			for(int j=0;j<a.size();j++){
				JSONObject obj = (JSONObject)a.get(j);
				words[i].AddWord(obj.get("data").toString());
				//System.out.println(obj.get("data").toString());
				ct++;
			}
			System.out.println(ct);
		}
	}
	
	public class MinDir implements Serializable{
		char id;
		ArrayList<String> words;
		private MinDir(char id){
			this.id = id;
			this.words = new ArrayList<String>();
		}
		
		private void AddWord(String word){
			this.words.add(word);
		}
		
		public double getProb(String prefix, String abet) throws InterruptedException{
			double ans = 0;
			int len = prefix.length();
			int left = 0;
			int Aleft,Aright;
			int right = this.words.size()-1;
			for(int i=1;i<len;i++){
				while(true){
					System.out.println("left : " + left + " right : " + right);
					//Thread.sleep(200);
					int middle = (left + right)/2;
					while(this.words.get(middle).length() < prefix.length()){
						middle++;
					}
					if(this.words.get(middle).charAt(i) > prefix.charAt(i)){
						right = middle;
					}else if(this.words.get(middle).charAt(i) < prefix.charAt(i)){
						left = middle;
					}else{
						break;
					}
				}
				while(this.words.get(left).charAt(i) == prefix.charAt(i) && this.words.get(left).length()>=prefix.length()){
					left--;
					System.out.println("left --");
				}
				while(this.words.get(right).charAt(i) == prefix.charAt(i) && this.words.get(right).length()>=prefix.length()){
					right++;
				}
			}
			System.out.println("out of if");
			double univforpref = right - left + 1;
			while(true){
				int middle = (left + right)/2;
				Thread.sleep(200);
				System.out.println("left:" + left +" right: "+right+ " middle: "+middle);
				while(this.words.get(middle).length() < prefix.length() + 1){
					middle++;
					System.out.println("aslfdj;lak");
				}
				if(this.words.get(middle).charAt(len) > abet.charAt(0)){
					right = middle;
				}else if(this.words.get(middle).charAt(len) < abet.charAt(0)){
					left = middle;
				}else{
					break;
				}
			}
			
			System.out.println("out of first while");
			while(this.words.get(left).charAt(len) == prefix.charAt(len) && this.words.get(left).length()>=prefix.length()){
				left--;
				System.out.println("left");
			}
			while(this.words.get(right).charAt(len) == prefix.charAt(len) && this.words.get(right).length()>=prefix.length()){
				right++;
			}
			double thischar = right - left + 1;
			ans = thischar/univforpref;
			System.out.println("returning");
			return ans;
		}
		
		public double other(String pref,String abet){
			double ans = 0.0;
			int left = 0 ,right = 0;
			boolean found = false;
			int sizeofuniv = right - left + 1;
			for(int i=0;i<this.words.size();i++){
				if(this.words.get(i).startsWith(pref + abet)){
					found = true;
					left = i;
					while(this.words.get(i).startsWith(pref+abet)){
						i++;
					}
					i--;
					right = i;
					break;
				}
			}
			double req = right - left + 1;
			if(found){
				ans = req;
			}else{
				ans = 0.0;
			}
			
			return ans;
		}
	}
}


