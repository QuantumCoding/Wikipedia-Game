package game;

							//        Sends-Processes	
public class Communication {// X == Completed	/ == Not Applicable							
	// Pause Messages														//	   Server  Side | Client  Side 
	public static final String REQUEST_PAUSE = "pause please";				//	     X  -  X    |    X  -  X    
	public static final String PAUSE_REJECTED = "no pause";					//	     /  -  X    |    X  -  /   
	public static final String PAUSE_ACCEPT = "yes pause";					//	     /  -  X    |    X  -  /   
	public static final String TOOGLE_PAUSE = "toggle pause";				//	     X  -  /    |    /  -  X  
                                                                                                           
	// Round Flow                                                             	          
	public static final String SET_START = "set start";						//	     X  -  /    |    /  -  X   
	public static final String ADD_DESTINATION = "add destination";			//	     X  -  /    |    /  -  X   
	public static final String NEW_ROUND = "prep new round";				//	     X  -  /    |    /  -  X   
                                                                                                           
	// Round State                                                            	          
	public static final String READY_ROUND = "on your mark";				//	     X  -  /    |    /  -  X  
	public static final String COUNT_DOWN_ROUND = "get set";				//	     X  -  /    |    /  -  X  
	public static final String START_ROUND = "GO! GO! GO!";					//	     X  -  /    |    /  -  X   
                                                                                                          
	// Player Round Prep.                                                     	       
	public static final String PLAYER_READY = "ready";						//	     X  -  X    |       -  X   
	public static final String PLAYER_UNREADY = "gimmy a sec";				//	     X  -  X    |       -  X   
                                                                                                           
	// Player State Change                                                    	      
	public static final String PLAYER_CONNECTED = "new player";				//	     X  -  /    |    /  -  X   
	public static final String PLAYER_DISCONNECTED = "player left";			//	     X  -  /    |    /  -  X   
                                                                                                           
	public static final String PLAYER_SPECTATING = "player spectating";		//	     X  -  X    |       -  X   
	public static final String PLAYER_PLAYING = "player playing";			//	     X  -  X    |       -  X   
                                                                                                           
	// Player Game Play                                                       	  
	public static final String PLAYER_DONE = "player done";					//	     X  -  X    |    X  -  X   
	public static final String PLAYER_QUIT = "i just cant";					//	     X  -  X    |    X  -  X  
}
