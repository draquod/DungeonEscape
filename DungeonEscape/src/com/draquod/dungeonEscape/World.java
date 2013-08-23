package com.draquod.dungeonEscape;

public class World {
	private Room room;
	public Room[][] rooms;
	
	public World(){
		//room = new Room();
		//String map = "0 1100001000000 100100000 0";
		//Generate(map.replace(" ", ""),room,0);
		rooms = new Room[5][5];
		GenerateRooms();
	}
	
	public void GenerateRooms(){
		int x = (int) (Math.random()*4);
		int y = (int) (Math.random()*4);
		int x2 = (int) (Math.random()*4);
		int y2 = (int) (Math.random()*4);
		while(x == x2 && y ==y2){
			x2 = (int) (Math.random()*4);
			y2 = (int) (Math.random()*4);
		}
		
		rooms[x][y] = new Room();
		rooms[x][y].entrance = true;
		rooms[x2][y2] = new Room();
		rooms[x2][y2].exit = true;
		
		int cont = 0;
		
	}
	
	public int Generate(String s,Room room,int cont){
		
		if(s.charAt(cont) != '0'){
			room.top = new Room();
			cont = Generate(s,room.top,cont+1);
		}else{
			cont++;
		}
		if(s.charAt(cont) != '0'){
			room.right = new Room();
			cont = Generate(s,room.right,cont+1);
		}else{
			cont++;
		}
		if(s.charAt(cont) != '0'){
			room.bot = new Room();
			cont = Generate(s,room.bot,cont+1);
		}else{
			cont++;
		}
		if(s.charAt(cont) != '0'){
			room.left = new Room();
			cont = Generate(s,room.left,cont+1);
		}else{
			cont++;
		}
		return cont;
	}
	
	
	public char[][] RPrint(char[][] w,int x, int y,Room room){
		if(room.top != null){
			w[x][y-1] = 100;
			w = RPrint(w,x,y-1,room.top);
		}
		if(room.right != null){
			w[x+1][y] = 100;
			w = RPrint(w,x+1,y,room.right);
		}
		if(room.bot != null){
			w[x][y+1] = 100;
			w = RPrint(w,x,y+1,room.bot);
		}
		if(room.left != null){
			w[x-1][y] = 100;
			w = RPrint(w,x-1,y,room.left);
		}
		return w;
	}
			
	
	public void Debug_PrintWorld(){
		char[][] w = new char[11][11];
		int x = 5;
		int y = 5;
		w[x][y] = 100;
		if(room == null){
			System.out.println("ES NULL" );
		}else{
			w = RPrint(w, x, y, room);
			
			for(int i =0;i<11;i++){
				for(int j=0;j<11;j++){
					if(w[j][i] == 100)
						System.out.print("0");
					else
						System.out.print("-");
				}
				System.out.println("");
			}
		}
	}
}
