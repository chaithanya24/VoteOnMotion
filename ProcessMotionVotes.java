package com.karuna.vm.process;

import java.util.HashMap;
import java.util.Scanner;

import com.karuna.vm.dto.Voter;
import com.karuna.vm.dto.VoterHelper;
import com.karuna.vm.dto.VotingStatus;
import com.karuna.vm.inputHelper.VoteOnMotionScannerInputHelper;
import com.karuna.vom.voteException.VoteException;

public class ProcessMotionVotes {

	private static final String VP= "vp";
	VoteOnMotionServiceImple impl = new VoteOnMotionServiceImple(); 
	VoteOnMotionScannerInputHelper inputHelper = new VoteOnMotionScannerInputHelper();

	/**
     * Process polled votes, this includes validate the polled votes, count
     * the total number votes and check if the polling ties and required VP 
     * voting process.
     *
     * @param HashMap<String, Voter> voterDetails
     * @param VoterHelper voterHelper
     * 
     * @return String str
     * @throws VoteException
     */
	public String processPolledVotes(HashMap<String, Voter> voterDetails, VoterHelper voterHelper) throws VoteException{
		impl.validateVotes(voterDetails, voterHelper);
		impl.countPolledVotes(voterDetails, voterHelper);	
		if(isVpVotingRequired(voterHelper)){
			return VP;
		} else{
			impl.results(voterHelper);
		}
		return "";
	}
	
	/**
     * verifies if polled votes ready for counting, If ready for 
     * counting then starts the counting else calculates the thread 
     * wait time and then triggers the counting process. 
     *
     * @param HashMap<String, Voter> voterDetails
     * @param VoterHelper voterHelper
     * @param Scanner in
     * 
     * @throws VoteException
     * @throws InterruptedException
     */
	public void processVotes(HashMap<String, Voter> voterDetails, VoterHelper voterHelper, Scanner in) throws VoteException, InterruptedException{
		System.out.println("STATUS::"+VotingStatus.VotingCompleted.toString());
		voterHelper.setTotalVotesPolled(voterDetails.keySet().size());
		if (isReadyForCounting(voterHelper)){
			String ret = processPolledVotes(voterDetails, voterHelper);
			if(ret.equalsIgnoreCase(VP)){
				enterVpVote(in, voterHelper);
			}
		}else {
			long waitTime = timeTotriggerProcessPolledVotes(voterHelper);
			Thread.sleep(waitTime);
			String ret = processPolledVotes(voterDetails, voterHelper);
			if(ret.equalsIgnoreCase(VP)){
				enterVpVote(in, voterHelper);
			}
		}
	}
	

	/**
     * This method helps populate the VP vote and validate his vote. 
     *
     * @param Scanner in
     * @param VoterHelper voterHelper
     */
	private void enterVpVote(Scanner in, VoterHelper voterHelper){
		Voter voter = new Voter();
		voter.setId(VP);
		inputHelper.populateEnteredVote(in, voter);
		impl.processVpVote(voter, voterHelper);
	}
	
	/**
     * This method helps calculate the wait time. 
     *
     * @param VoterHelper voterHlp
     * @return long waitTime
     */
	private static long timeTotriggerProcessPolledVotes(VoterHelper voterHlp){
		long waitTime = 0;
		long timeVoted =  voterHlp.getTimeCompleted() - voterHlp.getTimeStarted();
		waitTime = voterHlp.getMinWaitTimeOpened() - timeVoted;
		waitTime = (waitTime < 60000) ? 60000 : waitTime;
		System.out.println("Please Wait, " +waitTime/60000 + " minutes Before counting starts" );
		return waitTime;		
	}
	
	/**
     * This method helps to find ready for counting. 
     *
     * @param VoterHelper voterHlp
     * @return boolean 
     */
	private boolean isReadyForCounting(VoterHelper voterHlp){
		long timeVoted = voterHlp.getTimeCompleted() - voterHlp.getTimeStarted();
		
		if(voterHlp.getMinWaitTimeOpened() <= timeVoted ){
			return true;
		}
		return false;
	}
	

	/**
     * This method helps to find if Vice president voting required or not. 
     *
     * @param VoterHelper voterHlp
     * @return boolean 
     */
	private boolean isVpVotingRequired(VoterHelper voterHelper){
		if(voterHelper.getTotalSupport() == voterHelper.getTotalOppose()){
			System.out.println("******************MOTION TIED ***************************");
			System.out.println("*  Please allow Vice-president to vote on the motion	*");
			System.out.println("**************************** ****************************");
			return true;
		}
		return false;
	}

}
