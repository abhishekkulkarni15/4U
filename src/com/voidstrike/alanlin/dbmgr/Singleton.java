package com.voidstrike.alanlin.dbmgr;

public class Singleton {
	private static DBMgr innerMgr;
	
	public Singleton(){
		innerMgr = null;
	}
	
	public static DBMgr getInstance() {
		if (innerMgr == null) 
			innerMgr = new DBMgr();
		return innerMgr;
	}
}
