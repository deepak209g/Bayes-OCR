import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.json.simple.parser.ParseException;


public class DictDriver {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException, ParseException {
		// TODO Auto-generated method stub
		File input = new File("Dict.ser");
		Dictionary mydict;
    	if(input.createNewFile()){
    		System.out.println("Created fresh network!!");
    		mydict = new Dictionary();
    		
    		
    		FileOutputStream fos = null;
	        ObjectOutputStream out = null;
	        try {
	          fos = new FileOutputStream("Dict.ser");
	          out = new ObjectOutputStream(fos);
	          //out.writeObject(nn);
	          out.writeObject(mydict);
	          out.close();
	        } catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        System.out.println(mydict.words[0].other("a", "p"));
	    }else{
    		FileInputStream fileIn;
			try {
				fileIn = new FileInputStream(input);
				ObjectInputStream in = new ObjectInputStream(fileIn);
				mydict = (Dictionary) in.readObject();
				System.out.println(mydict.words[0].other("a", "p"));
	    		in.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    	}

	}

}
