package game_engine.controllers;

import game_engine.AI.AISimulator;
import game_engine.handlers.AIHandler;
import game_engine.interfaces.AIWorkspace;
import game_engine.AI.BFSSearcher;

/**
 * This class is a controller for Artificial Intelligence and allows for access to various AI utilities.
 * 
 * @author adamtache
 *
 */

public class AIController{

	private AIHandler myAIHandler;
	private AISimulator myAISimulator;
	private BFSSearcher myAISearcher;

	public AIController(AIWorkspace engineWorkspace) {
		this.myAISearcher = new BFSSearcher(engineWorkspace);
		this.myAIHandler = new AIHandler(engineWorkspace);
		this.myAISimulator = new AISimulator(engineWorkspace);
		this.myAIHandler.updateAIBranches();
	}
	
	public void updateAI() {
		this.myAIHandler.updateAIBranches();
	}
	
	public  AIHandler getAIHandler(){
		return myAIHandler;
	}
	
	public AISimulator getAISimulator(){
		return myAISimulator;
	}
	
	public BFSSearcher getAISearcher(){
		return myAISearcher;
	}

}