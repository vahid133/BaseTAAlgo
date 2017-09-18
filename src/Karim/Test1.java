package Karim;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class Test1 {

	public static void main(String[] args){
		
		
		
	}
	
	
	
	
	public static void Initialization() throws FileNotFoundException{
		//initialize developers
		HashMap<String , Zone> columns=new HashMap<String, Zone>();
		ArrayList<Developer> developers=new ArrayList<Developer>();
		Developer developer = null;
		Scanner sc=new Scanner(System.in);
		sc=new Scanner(new File(sc.nextLine()));
		int i=0;
		int j=0;
		while(sc.hasNextLine()){
			
			if(i!=0){
				for(String s:sc.next().split(" ")){
				columns.put(Integer.toString(j),new Zone(j, s));
				j++;
			}
				
			}else{
				for(String s:sc.next().split(" ")){
					if(j!=0){
						developer.DZone_Coefficient.put(columns.get(s), Double.parseDouble(s));
					}
					else{
						developer=new Developer(0);
						developer.setID(Integer.parseInt(s));
					}
					j++;
				}
				i++;
				j=0;
			
				}
		}
		
		//initialize bugs: consider dependent bugs
			
		
		//set parameters: set number of variables 
		
		
		
	}
	
	//assigning to developer
	public static void Assigning(){
		
	}
}
