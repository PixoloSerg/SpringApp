/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package springapp;

/**
 *
 * @author Sveta
 */
public class SpringApp {
    
    private static class ShareableResource {
	
	private boolean empty = true;
	private String message;
	
	public synchronized String getMessage() {
	    System.out.println("getMessage start. Time: " + System.currentTimeMillis());
	    while(empty) {
		try {
		    System.out.println("getMessage wait...");
		    wait();
		} catch (InterruptedException ex) {
		    System.err.println("Thread was interrupted.");
		}
	    }
	    empty = true;
	    notifyAll();
	    System.out.println("getMessage end. Time: " + System.currentTimeMillis());
	    return message;	    
	}
	
	public synchronized void putMessage(String message) {
	    System.err.println("putMessage start. Time: " + System.currentTimeMillis());
	    while(!empty) {
		try {
		    System.err.println("putMessage wait...");
		    wait();		    
		} catch (InterruptedException ex) {
		    System.err.println("Thread was interrupted.");
		}
	    }
	    empty = false;
	    this.message = message;
	    System.err.println("putMessage end. Time: " + System.currentTimeMillis());
	    notifyAll();	    	    
	}	
    }
    
    private static final class Block {
	
	private static int i = 0;
	
	public synchronized void add() {
	    System.out.println("Add " + System.currentTimeMillis());
	    i++;
//	    try {		
//		Thread.sleep(1000);
//	    } catch (InterruptedException ex) {
//		
//	    }
	}
	
	public synchronized int get() {
	    System.err.print("Get through " + System.currentTimeMillis() + ". ");
//	    try {		
//		Thread.sleep(1000);
//	    } catch (InterruptedException ex) {
//		
//	    }
	    return i;
	}
	
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	
//	ShareableResource resource = new ShareableResource();
//	
//	final String[] MESSAGES = {"First message", "SecondMessage", "ThirdMessage", "EXIT"};
//	
//	(new Thread( 
//		() -> {
//		    for(String msg : MESSAGES) {
//			resource.putMessage(msg);
//		    }
//		}
//	)).start();
//	(new Thread(
//		() -> {
//		    String msg;
//		    for(msg = resource.getMessage(); !"EXIT".equals(msg); msg = resource.getMessage()) {
//			System.out.println("Get message: " + msg);
//		    }
//		    System.out.println(msg);
//		}
//	)).start();


	Block block = new Block();
	
	(new Thread( 
		() -> { 
		    for(int i=0; i<3; i++) {
			block.add();
		    }
		} 
	)).start();
	(new Thread( 
		() -> { 
		    for(int i=0; i<3; i++) {
			int v = block.get();
			System.err.println("Value: " + v);
		    }
		}
	)).start();
	
    }
    
}
