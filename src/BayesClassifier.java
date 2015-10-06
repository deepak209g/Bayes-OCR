import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;


public class BayesClassifier implements Serializable{
	int TrainingExamples=0;
	int inpVector[];
	int classes[];
	int data[][];
	Pair queryVector[];
	
	public BayesClassifier(int inp[]){
		this.inpVector = inp;
	}
	public static class Pair implements Serializable{
		int first;
		double second;
		public String toString(){
			return this.first + "," + this.second  ;
			
		}
	}
	private class PairComp implements Comparator<Pair>{

		@Override
		public int compare(Pair o1, Pair o2) {
			// TODO Auto-generated method stub
			if(o2.second > o1.second){
				return 1;
			}else if(o2.second < o1.second){
				return -1;
			}else{
				return 0;
			}
		}
		
	}
	
	public BayesClassifier(int parameters, int classes){
		this.inpVector = new int[parameters];
		this.classes = new int[classes];
		this.data = new int[classes][parameters];
		this.queryVector = new Pair[classes];
		Arrays.fill(this.inpVector, 0);
		Arrays.fill(this.classes, 0);
		for(int i=0;i<data.length;i++){
			Arrays.fill(this.data[i], 0);
		}
	}
	
	public void Train(int vect[],int clas){
		this.TrainingExamples++;
		this.classes[clas]++;
		for(int i=0;i<vect.length;i++){
			if(vect[i] == 1){
				this.inpVector[i]++;
				this.data[clas][i]++;
			}
		}
	}
	
	public void Train(double vect[],int clas){
		this.TrainingExamples++;
		this.classes[clas]++;
		for(int i=0;i<vect.length;i++){
			if(vect[i] >= 1.0){
				this.inpVector[i]++;
				this.data[clas][i]++;
			}
		}
	}
	public int Query(double vect[]){
		int ans=0;
		double currmaxprob = 0;
		for(int i=0;i<classes.length;i++){
			double prob = ClassQuery(vect,i);
			this.queryVector[i] = new Pair();
			this.queryVector[i].first = i;
			this.queryVector[i].second = prob;
			if(prob>currmaxprob){
				currmaxprob = prob;
				ans = i;
			}
		}
		return ans;
	}
	
	public Pair[] QueryVector(int x){
		Pair ans[] = new Pair[x];
		Arrays.sort(this.queryVector,new PairComp());
		System.out.println();
		for(int i=0;i<x;i++){
			ans[i] = new Pair();
			ans[i].first = this.queryVector[i].first;
			ans[i].second = this.queryVector[i].second;
		}
		return ans;
	}
	
	
//	private double ClassQuery(int vect[],int clas){
//		double probClass = (double)this.inpVector[clas]/this.TrainingExamples;
//		double ans = 1.0;
//		for(int i=0;i<vect.length;i++){
//			ans = ans*(double)this.data[clas][i]/(double)this.classes[i];
//		}
//		ans *= probClass;
//		return ans;
//	}
	
	private double ClassQuery(double vect[],int clas){
		double probClass = (double)this.classes[clas]/this.TrainingExamples;
		double ans = 1.0;
		for(int i=0;i<vect.length;i++){
			if(vect[i]>=1.0){
				ans = ans*(double)this.data[clas][i]/(double)this.classes[clas];
			}else{
				ans = ans*(1 - (double)this.data[clas][i]/(double)this.classes[clas]);
			}
			
		}
		ans *= probClass;
		return ans;
	}
	public void ShowStat(){
		System.out.println("Training lenght : " + this.TrainingExamples);
		System.out.println();
		System.out.println(Arrays.toString(this.inpVector));
		System.out.println();
		System.out.println(Arrays.toString(this.classes));
		System.out.println();
	}
}
