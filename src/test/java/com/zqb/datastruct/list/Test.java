package com.zqb.datastruct.list; 

public class Test {

	public static void main(String[] args) {
		
		String[] contents = "682,617,FFFFFF-683,626,000000-671,618,FFFFFF-691,617,FFFFFF-682,624,353535".split("-");
		
		String[] point = contents[0].split(",");
		
		int x = Integer.parseInt(point[0]);
		int y = Integer.parseInt(point[1]);
		String color = point[2];
		
		StringBuilder arg = new StringBuilder();
		
		arg.append(x).append(",").append(y).append(",").append("\""+color+"\"").append(",");
		
		StringBuilder arg3 = new StringBuilder();
		for(int i=1; i<contents.length; i++) {
			String[] con = contents[i].split(",");
			
			arg3.append(Integer.parseInt(con[0])-x).append("|").
			append(Integer.parseInt(con[1])-y).append("|").append(con[2]).append(",");
			
		}
		arg3.deleteCharAt(arg3.length()-1);
		arg.append("\""+arg3+"\"").append(", 0.9");
		
		System.out.println(arg);
	}
}
