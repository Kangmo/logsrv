package controllers;

import java.io.*;

public class LogStorageWorker extends Thread {
	String path;
	PipedInputStream pis;

	public String contentType = "";

	public LogStorageWorker(String path, PipedInputStream pis) {
	    super();
	    this.path = path;
	    this.pis = pis;
	}

	public void run() {
	    try {
	    	
	    	System.out.println("LogStorageWorker path:" + path +",");
//	        myApi.store(pis, path, contentType);
	        pis.close();
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        try {pis.close();} catch (Exception ex2) {}
	    }
	}
}