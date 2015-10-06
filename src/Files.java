import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

import javax.imageio.ImageIO;


public class Files {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		File actual = new File("assets/");
		
		BayesClassifier classify = new BayesClassifier(360,62);
		File input;
		try {
			
        	input = new File("Bayes.ser");
        	if(input.createNewFile()){
        		System.out.println("Created fresh network!!");
        		
        	}else{
        		FileInputStream fileIn = new FileInputStream(input);
        		ObjectInputStream in = new ObjectInputStream(fileIn);
        		in.close();
        	}
        	
        	
    		double teacher[] = new double[26];
    		Arrays.fill(teacher, 0.0);
    		int train = 40000;
    		boolean ret = true;
    		File dirlist[] = actual.listFiles();
    		while(train>0){
    			int a = (int) (Math.random()*dirlist.length);
    			File currdir = dirlist[a];
        		File imglist[] = currdir.listFiles();
        		File currentImage = imglist[(int) (Math.random()*400)];
        		Image picturee = ImageIO.read(currentImage);
            	Image picture = picturee.getScaledInstance(50, 50, Image.SCALE_AREA_AVERAGING);
            	BufferedImage bi = new BufferedImage(picture.getWidth(null), picture.getHeight(null), BufferedImage.TYPE_BYTE_BINARY);
            	Graphics2D bGr =bi.createGraphics();
        	    bGr.drawImage(picture, 0, 0, null);
        	    bGr.dispose();
        	    
        	    double db[][] = new double[bi.getHeight(null)][bi.getHeight(null)];
        	    
        	    for(int i=0;i<bi.getHeight(null);i++){
        	    	for(int j=0;j<bi.getWidth(null);j++){
        	    		Color temp = new Color(bi.getRGB(j, i));
        	    		int col = temp.getBlue();
        	    		if(col>0){
        	    			col = 0;
        	    		}else{
        	    			col = 1;
        	    		}
        	    		db[i][j] = col;
        	    	}
        	    }
        	    
        	    ImgMat img = new ImgMat(db);
        	    img.GetRegionofInterest();
        	    img.ScaleDown(15, 24);
        	    
        	    
        	    double trn[] = img.TolinearArray();
        	    
        	    classify.Train(trn, a);
        	    if(train%100 == 0){
        	    	//classify.ShowStat();
        	    	
        	    	int out = classify.Query(trn);
        	    	if(out == a){
        	    		System.out.println("Phoda !!! :)");
        	    	}else{
        	    		System.out.println("Nahi phoda !!! :(");
        	    	}
        	    }
        	    train--;
            }
    		
    		
            
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try {
          fos = new FileOutputStream("Bayes.ser");
          out = new ObjectOutputStream(fos);
          //out.writeObject(nn);
          out.writeObject(classify);
          out.close();
        } catch (Exception ex) {
          ex.printStackTrace();
        }
	}

}
