package com.draquod.dungeonEscape;

public class Room {
	private Cell[][] cells;
	public Room top;
	public Room right;
	public Room bot;
	public Room left;
	boolean entrance = false;
	boolean exit = false;
	
	public Room(){
		/*
		cells = new Cell[10][10];
		for(int i =0;i<10;i++){
			for(int j=0;j<10;j++){
				cells[i][j] = new Cell();
			}
		}
		*/
	}
}
