package com.draquod.dungeonEscape;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.math.Vector2;

public class DungeonGenerator {

	public int NOTHING = 0x00000000;

	public int BLOCKED = 0x00000001;
	public int ROOM = 0x00000002;
	public int CORRIDOR = 0x00000004;

	public int PERIMETER = 0x00000010;
	public int ENTRANCE = 0x00000020;
	public int ROOM_ID = 0x0000FFC0;

	public int ARCH = 0x00010000;
	public int DOOR = 0x00020000;
	public int LOCKED = 0x00040000;
	public int TRAPPED = 0x00080000;
	public int SECRET = 0x00100000;
	public int PORTC = 0x00200000;
	public int STAIR_DN = 0x00400000;
	public int STAIR_UP = 0x00800000;

	public int LABEL = 0xFF000000;

	public int OPENSPACE = ROOM | CORRIDOR;
	public int DOORSPACE = ARCH | DOOR | LOCKED | TRAPPED | SECRET | PORTC;
	public int ESPACE = ENTRANCE | DOORSPACE | 0xFF000000;
	public int STAIRS = STAIR_DN | STAIR_UP;

	public int BLOCK_ROOM = BLOCKED | ROOM;
	public int BLOCK_CORR = BLOCKED | PERIMETER | CORRIDOR;
	public int BLOCK_DOOR = BLOCKED | DOORSPACE;

	int seed;
	public int n_rows;
	public int n_cols;
	int room_min;
	int room_max;
	int remove_deadends;
	int add_stairs;
	public int cell_size;

	int[][] cells;
	int[][] cells2;
	int numRooms;
	int room_id = 1;
	
	ArrayList<Integer> xs = new ArrayList<Integer>();
	ArrayList<Integer> ys = new ArrayList<Integer>();
	
	ArrayList<Integer> xe = new ArrayList<Integer>();
	ArrayList<Integer> ye = new ArrayList<Integer>();

	ArrayList<Integer> dirs = new ArrayList<Integer>();
	
	Node world;
	
	public Vector2 begin;
	public Vector2 end;
	public DungeonGenerator() {
		begin = new Vector2();
		end = new Vector2();
		SetupOps();
		dirs.add(0);
		dirs.add(1);
		dirs.add(2);
		dirs.add(3);
		

	}
	
	private int OddRandom(int Min,int Max){
		if (Max % 2 == 0) --Max;
		if (Min % 2 == 0) ++Min;
		int Random_No = Min + 2*(int)(Math.random()*((Max-Min)/2+1));
		return Random_No;
	}

	public void CreateDungeon() {
		xs.clear();
		ys.clear();
		xe.clear();
		ye.clear();
		room_id = 1;
		cells = new int[n_cols][n_rows];
		for (int i = 0; i < n_cols; i++) {
			for (int j = 0; j < n_rows; j++) {
				cells[i][j] = NOTHING;
			}
		}
		PlaceRooms();
		//CreateTunnels();
		
		System.out.println("ENTRACES PLACED");
		Tunneling();
		System.out.println("TUNNELS PLACED");
		//Tunneling();
		//Cleaning();
		RemoveEntrances();
		System.out.println("ENTRACES CLEANED");
		PlaceStairs();
		System.out.println("STAIRS PLACED");
		FindCruces();
		System.out.println("CRUCES FOUND");
		
	}
	
	private void FindCruces(){
		for(int i=0;i<(int)(n_cols/2);i++){
			for(int j =0;j<(int)(n_rows)/2;j++){
				int ci = (i*2)+1;
				int cj = (j*2)+1;
				int x = ci;
				int y = cj;
				int cont = 0;
				if(cells[x+1][y] == CORRIDOR || cells[x+1][y] == ENTRANCE){
					cont++;
				}
				if(cells[x-1][y] == CORRIDOR || cells[x-1][y] == ENTRANCE){
					cont++;
				}
				if(cells[x][y+1] == CORRIDOR || cells[x][y+1] == ENTRANCE){
					cont++; 
				}
				if(cells[x][y-1] == CORRIDOR || cells[x][y-1] == ENTRANCE){
					cont++;
				}
				
				if(cont > 2){
					cells[x][y] = 99;
				}
			}
		}
	}
	
	private void PlaceStairs(){
		
		xs.clear();
		ys.clear();
		
		for(int i=0;i<(int)(n_cols/2);i++){
			for(int j =0;j<(int)(n_rows)/2;j++){
				int ci = (i*2)+1;
				int cj = (j*2)+1;
				
				if(((cells[ci][cj] & (~ROOM_ID)) != ROOM) 
						&&  (cells[ci][cj] != PERIMETER) 
						&& (cells[ci][cj] != CORRIDOR)){
					if(ci > 2 && cj > 2 && ci < n_cols-2 && cj < n_rows -2){
						if(cells[ci+2][cj] == CORRIDOR ||
							cells[ci-2][cj] == CORRIDOR ||
							cells[ci][cj+2] == CORRIDOR ||
							cells[ci][cj-2] == CORRIDOR ){
							
							xs.add(ci);
							ys.add(cj);
							//cells[ci][cj] = 99;
							
						}
					}
				}
			}
		}
		
		int index;
		
		//Stair down
		index = (int) (Math.random()*(xs.size()-1));
		cells[xs.get(index)][ys.get(index)] = STAIR_DN;
		int ci = xs.get(index);
		int cj = ys.get(index);
		begin.x = ci;
		begin.y = cj;
		if(cells[ci+2][cj] == CORRIDOR){
			cells[ci+1][cj] = CORRIDOR;
		}
		else if(cells[ci-2][cj] == CORRIDOR){
			cells[ci-1][cj] = CORRIDOR;
		}
		else if(cells[ci][cj+2] == CORRIDOR){
			cells[ci][cj+1] = CORRIDOR;
		}
		else if(cells[ci][cj-2] == CORRIDOR ){
			cells[ci][cj-1] = CORRIDOR;
		}
		
		//Stair up
		index = (int) (Math.random()*(xs.size()-1));
		cells[xs.get(index)][ys.get(index)] = STAIR_UP;
		ci = xs.get(index);
		cj = ys.get(index);
		end.x = ci;
		end.y = cj;
		if(cells[ci+2][cj] == CORRIDOR){
			cells[ci+1][cj] = CORRIDOR;
		}
		else if(cells[ci-2][cj] == CORRIDOR){
			cells[ci-1][cj] = CORRIDOR;
		}
		else if(cells[ci][cj+2] == CORRIDOR){
			cells[ci][cj+1] = CORRIDOR;
		}
		else if(cells[ci][cj-2] == CORRIDOR ){
			cells[ci][cj-1] = CORRIDOR;
		}
	}
	
	private void RemoveEntrances(){
		for(int i =0;i<xe.size();i++){
			
			int dir = -1;
			int dx = 0;
			int dy = 0;
			if(cells[xe.get(i)+1][ye.get(i)] == 0){
				dir = 1;
			}
			if(cells[xe.get(i)-1][ye.get(i)] == 0 ){
				dir = 3;
			}
			if(cells[xe.get(i)][ye.get(i)+1] == 0){
				dir = 0;
			}
			if(cells[xe.get(i)][ye.get(i)-1] == 0 ){
				dir = 2;
			}
			if(dir == 0){
				dx = 0;
				dy = 1;
			}
			if(dir == 1){
				dx = 1;
				dy = 0;
			}
			if(dir == 2){
				dx = 0;
				dy = -1;
			}
			if(dir == 3){
				dx = -1;
				dy = 0;
			}
			if(dir > -1){
				
				cells[xe.get(i)][ye.get(i)] = PERIMETER;

			}
			
		}
	}
	
	private void Cleaning(){
		for(int i=0;i<(int)(n_cols/2);i++){
			for(int j =0;j<(int)(n_rows)/2;j++){
				int ci = (i*2)+1;
				int cj = (j*2)+1;
				if(cells[ci][cj] == CORRIDOR){
					int cont = 0;
					if(cells[ci+1][cj] == CORRIDOR || cells[ci+1][cj] == ENTRANCE){
						cont++;
					}
					if(cells[ci-1][cj] == CORRIDOR || cells[ci-1][cj] == ENTRANCE){
						cont++;
					}
					if(cells[ci][cj+1] == CORRIDOR || cells[ci][cj+1] == ENTRANCE){
						cont++; 
					}
					if(cells[ci][cj-1] == CORRIDOR || cells[ci][cj-1] == ENTRANCE){
						cont++;
					}
					
					if(cont == 0){
						cells[ci][cj] = 0;
					}
				}
			}
		}
	}
	
	private void Tunneling(){
		
		System.out.println("SIZE: " + xe.size());
		for(int i =0;i<xe.size();i++){
			
			int dir = 1;
			int dx = 0;
			int dy = 0;
			if(cells[xe.get(i)+1][ye.get(i)] == 0){
				dir = 1;
			}
			if(cells[xe.get(i)-1][ye.get(i)] == 0 ){
				dir = 3;
			}
			if(cells[xe.get(i)][ye.get(i)+1] == 0){
				dir = 0;
			}
			if(cells[xe.get(i)][ye.get(i)-1] == 0 ){
				dir = 2;
			}
			if(dir == 0){
				dx = 0;
				dy = 1;
			}
			if(dir == 1){
				dx = 1;
				dy = 0;
			}
			if(dir == 2){
				dx = 0;
				dy = -1;
			}
			if(dir == 3){
				dx = -1;
				dy = 0;
			}
			
			CreateTunnel(xe.get(i) + dx,ye.get(i) + dy,0);
			//cells[xe.get(i) + dx][ye.get(i) + dy] = 99;
		}
		
		/*
		
		for(int i=0;i<(int)(n_cols/2);i++){
			for(int j =0;j<(int)(n_rows)/2;j++){
				int ci = (i*2)+1;
				int cj = (j*2)+1;
				if(((cells[ci][cj] & (~ROOM_ID)) != ROOM) 
						&&  (cells[ci][cj] != PERIMETER) 
						&& (cells[ci][cj] != CORRIDOR)){
					
					CreateTunnel(ci,cj,0);
					xs.add(ci);
					ys.add(cj);
				}
			}
		}
		*/
		
		//cells2 = cells.clone();
		cells2 = new int[cells.length][cells[0].length];
		for(int i =0;i<cells.length;i++){
			for(int j =0;j<cells[0].length;j++){
				cells2[i][j] = cells[i][j];
			}
		}
		
		for(int i=0;i<(int)(n_cols/2);i++){
			for(int j =0;j<(int)(n_rows)/2;j++){
				int ci = (i*2)+1;
				int cj = (j*2)+1;
				
				if(cells[ci][cj] == CORRIDOR){
					int cont = 0;
					int dir = 0;
					if(cells2[ci+1][cj] == CORRIDOR || cells2[ci+1][cj] == ENTRANCE){
						cont++;
						dir = 1;
					}
					if(cells2[ci-1][cj] == CORRIDOR || cells2[ci-1][cj] == ENTRANCE){
						cont++;
						dir = 3;
					}
					if(cells2[ci][cj+1] == CORRIDOR || cells2[ci][cj+1] == ENTRANCE){
						cont++; 
						dir = 0;
					}
					if(cells2[ci][cj-1] == CORRIDOR || cells2[ci][cj-1] == ENTRANCE){
						cont++;
						dir = 2;
					}
					
					if(cont == 1){
						//cells[ci][cj] = 99;
						//if(Math.random() > 0.3){
							RemoveDeadEnd(ci,cj,dir);
						//}
					}
				}
			}
		}
		
	}
	
	void RemoveDeadEnd(int x, int y,int dir){
		
		int dx = 0;
		int dy = 0;
		
		int cont = 0;
		if(cells[x+1][y] == CORRIDOR || cells[x+1][y] == ENTRANCE){
			cont++;
			dir = 1;
		}
		if(cells[x-1][y] == CORRIDOR || cells[x-1][y] == ENTRANCE){
			cont++;
			dir = 3;
		}
		if(cells[x][y+1] == CORRIDOR || cells[x][y+1] == ENTRANCE){
			cont++; 
			dir = 0;
		}
		if(cells[x][y-1] == CORRIDOR || cells[x][y-1] == ENTRANCE){
			cont++;
			dir = 2;
		}
		if(dir == 0){
			dx = 0;
			dy = 1;
		}
		if(dir == 1){
			dx = 1;
			dy = 0;
		}
		if(dir == 2){
			dx = 0;
			dy = -1;
		}
		if(dir == 3){
			dx = -1;
			dy = 0;
		}
		if(cont == 1){
			cells[x][y] = 0;
			RemoveDeadEnd(x+dx,y+dy,dir);
		}
	}

	private void PlaceRooms() {
		int dungeonArea = n_rows * n_cols;
		int roomArea = room_max * room_max;
		numRooms = (int) ((float) dungeonArea / (float) roomArea*0.6);
		System.out.println("NUM ROOMS: " + numRooms);
		for (int i = 0; i < numRooms; i++) {
			PlaceRoom();
		}
		
	}

	private void PlaceRoom() {
		int x;
		int y;
		int width;
		int height;
		int x1;
		int x2;
		int y1;
		int y2;

		boolean correct = true;
		do {
			x = OddRandom(0,n_cols-1);
			y = OddRandom(0,n_rows-1);
			width = OddRandom(room_min,room_max);
			height = OddRandom(room_min,room_max);

			x1 = x - 1;
			x2 = x + width + 1;
			y1 = y - 1;
			y2 = y + height + 1;

			correct = true;
			
			if(x2 > n_cols-1 || y2 > n_rows -1 || x1 < 0 || y1 < 0){
				correct = false;
			}else{
				for (int i = x1; i < x2; i++) {
					for (int j = y1; j < y2; j++) {
						if ((cells[i][j] ^ ROOM) == 0
								|| (cells[i][j] ^ PERIMETER) == 0) {
							correct = false;
						}
					}
				}
			}
		} while (correct == false);

		for (int i = x1; i < x2; i++) {
			for (int j = y1; j < y2; j++) {
				if (i == x1 || i == x2 - 1 || j == y1 || j == y2 - 1) {
					cells[i][j] = PERIMETER;
				} else {
					cells[i][j] = ROOM | room_id << 6;
				}
			}
		}
		room_id++;
		
		int roomH = (y2 - y1)/4;
		int roomW = (x2 - x1)/4;
		int opens = (int)(Math.sqrt(roomH*roomW));
		opens = opens +  (int)(Math.random()*opens);
		System.out.println("OPENS: " + opens);
		for(int i =x1+1;i<x2-1;i+=2){
			if(y1 > 4){
				xs.add(i);
				ys.add(y1);
			}
			if(y2 < n_cols-5){
				xs.add(i);
				ys.add(y2-1);
			}
		}
		
		for(int i =y1+1;i<y2-1;i+=2){
			if(x1 > 4){
				xs.add(x1);
				ys.add(i);
			}
			if(x2 < n_rows - 5){
				xs.add(x2-1);
				ys.add(i);
			}
		}
		
		for(int i=0;i<opens;i++){
			int index = (int) (Math.random()*(xs.size()-1));
			int xi = xs.get(index);
			int yi = ys.get(index);
			xs.remove(index);
			ys.remove(index);
			cells[xi][yi] = ENTRANCE;
			System.out.println("Entrance placed x: " + xi + " y: " + yi);
			xe.add(xi);
			ye.add(yi);
			
		}
		
		xs.clear();
		ys.clear();
		
		
		
		
	}
	
	
	private <T> void swap(List<T> list, int idx1, int idx2) {
        T o1 = list.get(idx1);
        list.set(idx1, list.get(idx2));
        list.set(idx2, o1);
}
	
	public <T> void shuffle(List<T> objects) {
		Random r = new Random();
        for(int i = objects.size(); i > 1; i--) {
                swap(objects, i - 1, r.nextInt(i));
        }
	
	}
	
	
	void CreateTunnel(int x, int y,int lastDir){
		if(((cells[x][y] & (~ROOM_ID)) != ROOM) 
				&&  (cells[x][y] != PERIMETER) 
				&& (cells[x][y] != CORRIDOR)){
			cells[x][y] = CORRIDOR;
			//Collections.shuffle(dirs);
			shuffle(dirs);
			for(int i =0;i<4;i++){
				int dx = 0;
				int dy = 0;
				int dir = 0;
				if(Math.random() > 0.5){
					dir = dirs.get(i);
				}else{
					dir = lastDir;
				}
				if(dir == 0){
					dx = 0;
					dy = 2;
				}
				if(dir == 1){
					dx = 2;
					dy = 0;
				}
				if(dir == 2){
					dx = 0;
					dy = -2;
				}
				if(dir == 3){
					dx = -2;
					dy = 0;
				}
				if(true){
					int nextX = x + dx;
					int nextY = y + dy;
					if(nextX > 0 && nextY > 0 && nextX < n_cols && nextY < n_rows){
						if(((cells[nextX][nextY] & (~ROOM_ID)) != ROOM) 
								&&  (cells[nextX][nextY] != PERIMETER) 
								&& (cells[nextX][nextY] != CORRIDOR)){
							cells[x + dx/2][y + dy/2] = CORRIDOR;
							CreateTunnel(nextX, nextY, dir);
						
						}
					}
				}
			}
		}
	}
	
	
	
	

	private void SetupOps() {
		seed = 0;
		n_rows = 21;
		n_cols = 21;
		room_min = 3;
		room_max = 8;
		remove_deadends = 50;
		add_stairs = 2;
		cell_size = 6;
	}

}
