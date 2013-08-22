package com.draquod.dungeonEscape;

import java.util.HashMap;
import java.util.Map;

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
	int numRooms;

	public DungeonGenerator() {
		SetupOps();

	}
	
	private int OddRandom(int Min,int Max){
		if (Max % 2 == 0) --Max;
		if (Min % 2 == 0) ++Min;
		int Random_No = Min + 2*(int)(Math.random()*((Max-Min)/2+1));
		return Random_No;
	}

	public void CreateDungeon() {
		cells = new int[n_cols][n_rows];
		for (int i = 0; i < n_cols; i++) {
			for (int j = 0; j < n_rows; j++) {
				cells[i][j] = NOTHING;
			}
		}
		PlaceRooms();
	}

	private void PlaceRooms() {
		int dungeonArea = n_rows * n_cols;
		int roomArea = room_max * room_max;
		numRooms = (int) ((float) dungeonArea / (float) roomArea*0.4);
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
					cells[i][j] = ROOM;
				}
			}
		}

	}

	private void SetupOps() {
		seed = 0;
		n_rows = 41;
		n_cols = 41;
		room_min = 3;
		room_max = 11;
		remove_deadends = 50;
		add_stairs = 2;
		cell_size = 8;
	}

}
