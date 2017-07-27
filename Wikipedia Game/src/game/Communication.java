package game;

							//        Sends-Processes	
public class Communication {// X == Completed	/ == Not Applicable							
	// Pause Messages														//	   Server  Side | Client  Side 
	public static final String REQUEST_PAUSE = "pause please";				//	     X  -  X    |    X  -  X    
	public static final String PAUSE_REJECTED = "no pause";					//	     /  -  X    |    X  -  /   
	public static final String PAUSE_ACCEPT = "yes pause";					//	     /  -  X    |    X  -  /   
	public static final String TOGGLE_PAUSE = "toggle pause";				//	     X  -  /    |    /  -  X  
	public static final String UNPAUSE = "unpause";							//	     /  -  X    |    X  -  /  
	
	public static final String PAUSE_ACCEPTANCE_HAVE = "pause have";		//	     X  -  /    |    /  -  X  
	public static final String PAUSE_ACCEPTANCE_NEED = "pause need";		//	     X  -  /    |    /  -  X  
                                                                                                           
	// Round Flow                                                             	          
	public static final String SET_START = "set start";						//	     X  -  /    |    /  -  X   
	public static final String ADD_DESTINATION = "add destination";			//	     X  -  /    |    /  -  X   
	public static final String NEW_ROUND = "prep new round";				//	     X  -  /    |    /  -  X   
                                                                                                           
	// Round State                                                            	          
	public static final String ROUND_READY = "on your mark";				//	     X  -  /    |    /  -  X  
	public static final String COUNT_DOWN_ROUND = "get set";				//	     X  -  /    |    /  -  X  
	public static final String START_ROUND = "GO! GO! GO!";					//	     X  -  /    |    /  -  X   
                                                                                                          
	// Player Round Prep.                                                     	       
	public static final String PLAYER_READY = "ready";						//	     X  -  X    |    X  -  X   
	public static final String PLAYER_UNREADY = "gimmy a sec";				//	     X  -  X    |    X  -  X    
	
	public static final String PLAYERS_READY_RECALC = "perc ready recalc";	//	     X  -  /    |    /  -  X  
	public static final String PLAYERS_READY_NEEDED = "nr_players need";	//	     X  -  /    |    /  -  X
	
	public static final String AUTO_READY_START = "tick tock goes the ";	//	     X  -  /    |    /  -  X
	public static final String STOP_READY_START = "tock tick stop the ";	//	     X  -  /    |    /  -  X
                                                                                                           
	// Player State Change                                                    	      
	public static final String PLAYER_CONNECTED = "new player";				//	     X  -  /    |    /  -  X   
	public static final String PLAYER_DISCONNECTED = "l_player left";		//	     X  -  /    |    /  -  X   
                                                                                                           
	public static final String PLAYER_SPECTATING = "s_player spectating";	//	     X  -  X    |       -  X   
	public static final String PLAYER_PLAYING = "p_player playing";			//	     X  -  X    |       -  X   
                                                                                                           
	// Player Game Play                                                       	  
	public static final String PLAYER_DONE = "d_player done";				//	     X  -  X    |    X  -  X   
	public static final String PLAYER_QUIT = "i just cant";					//	     X  -  X    |    X  -  X  
	
	public static final String PLAYER_RANK = "new place in life";			//	     X  -  /    |    /  -  X 
	public static final String PLAYER_WON = "winner winner";				//	     X  -  /    |    /  -  X 
    
	// Server Property Change                                                     	  
	public static final String SERVER_PROPERTY_CHANGED = "mix it up";		//	     X  -  /    |    /  -  X   
}
