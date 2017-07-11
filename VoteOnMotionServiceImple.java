package com.karuna.vm.process;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import java.util.TimeZone;

import com.karuna.vm.dto.Voter;
import com.karuna.vm.dto.VoterConstants;
import com.karuna.vm.dto.VoterHelper;
import com.karuna.vm.dto.VotingStatus;
import com.karuna.vom.voteException.VoteException;

public class VoteOnMotionServiceImple implements VoteOnMotionService{
	private static final int maxVotesAllowed = 101;
	private static final long minTimePollingAvailable = 15*60000;
	private static final String motionNameError = "Enter a valid motion desription only upon entering valid description votes can be accepted";
	private static final String numberOfVotesError = "The maximum votes that can be received on a motion is 101, please restart from the begining";
	private static final String pollingTimeError = "A motion cannot be closed for voting less than 15 minutes after it was opened";
	
	/**
     * Validates motion description and total number of votes.
     *
     * @param HashMap<String, Voter> voterDetails
     * @param VoterHelper voterHelper
     * @throws VoteException
     */
	@Override
	public void validateVotes(HashMap<String, Voter> voterDetails, VoterHelper voterHelper) throws VoteException {
		/*
		 * Note: Most of the validation done  while entering the input.
		 * usefull in case of validating from file(.txt) as input for voting, motion name etc.
		 */ 
		validateMotionName(voterHelper.getMotionDesc());
		validateTotalVotes(voterDetails, voterHelper);
		// commented for testing
		//validateVotingTimeOpened(voterHelper);
	}
	

	/**
     * Counts the total number of votes polled.
     * If vote is YEAS then increment the support total.
     * If vote is NAYS the increment the appose total.
     * If vote is NotValid then increment the invalid total.
     *
     * @param HashMap<String, Voter> voterDetails
     * @param VoterHelper voterHelper
     * 
     */
	@Override
	public void countPolledVotes(HashMap<String, Voter> voterDetails, VoterHelper voterHlp){

		System.out.println("STATUS::"+VotingStatus.CountingStarted.toString());
		Set<String> ids = voterDetails.keySet();
		
		for (String id : ids) {
			Voter voter = voterDetails.get(id);
			if(voter.getVote().equalsIgnoreCase(VoterConstants.YEAS.toString())){
				voterHlp.incrementSupport();
			}else if(voter.getVote().equalsIgnoreCase(VoterConstants.NAYS.toString())){
				voterHlp.incrementOppose();
			}else{
				voterHlp.incrementInvalid();
				System.out.println("Voter Id/Name :" +id+ " polled an invalid vote, sorry your vote ignored in the counting");
			}
		}
	}

	/**
     * Process Vice president vote.
     * If vote is YEAS then motion passed with VP vote.
     * If vote is NAYS then motion failed with VP vote.
     * If vote is NotValid then motion failed with as VP unavailable.
     * else motion failed with as VP invalid vote.
     *
     * @param Voter voter
     * @param VoterHelper voterHelper
     */
	@Override
	public void processVpVote(Voter voter, VoterHelper voterHelper) {
		
		if(voter.getVote().equalsIgnoreCase(VoterConstants.YEAS.toString())){
			System.out.println("*********MOTION PASSED SUCCESFULLY WITH VP VOTE**********");
		}else if(voter.getVote().equalsIgnoreCase(VoterConstants.NAYS.toString())){
			System.out.println("*********MOTION FAILED SUCCESFULLY WITH VP VOTE**********");
		} else {
			System.out.println("*********MOTION FAILED SUCCESFULLY AS VP NOT AVAILABLE [OR] VP ENTERED INVALID VOTE**********");
		} 
		printResults(voterHelper);
	}
	

	/**
     * This method helps to validate name of the motion.
     * Throws voteException if null/blank. 
     *
     * @param String motionName
     * @throws  VoteException
     */
	private void validateMotionName(String motionName) throws VoteException{
		
		if(motionName == null || motionName.trim().equalsIgnoreCase("")){
			System.out.println(motionNameError);
			throw new VoteException(motionNameError);
		} 
	}
	
	/**
     * This method helps to validate total number of votes polled. 
     * throws voteException incase total number of votes exceeds 
     * the default max votes allowed. 
     *
     * @param HashMap<String, Voter> voterDetails
     * @param VoterHelper voterHlp
     * @throws  VoteException
     */
	private void validateTotalVotes(HashMap<String, Voter> voterDetails, VoterHelper voterHelper) throws VoteException{
		
		if(voterHelper.getTotalVotesPolled() > maxVotesAllowed) {
			System.out.println(numberOfVotesError);
			throw new VoteException(numberOfVotesError);
		}
	}
	/**
     * This method helps to validate total time voting 
     * was opened. throws voteException incase total time 
     * is less then the default min time 
     *
     * @param VoterHelper voterHlp
     * @throws  VoteException
     */
	public void validateVotingTimeOpened(VoterHelper voterHlp) throws VoteException{
		long totalTimeforVoting = voterHlp.getTimeStarted() - voterHlp.getTimeCompleted();
		if(totalTimeforVoting <= minTimePollingAvailable){
			System.out.println(pollingTimeError);
			throw new VoteException(pollingTimeError);
		}
	}
	
	/**
     * Displays motion passed or failed, and other required details.
     *
     * @param VoterHelper voterHelper
     */
	@Override
	public void results(VoterHelper voterHelper) {
		if(voterHelper.getTotalSupport() > voterHelper.getTotalOppose()){
			System.out.println("********************MOTION PASSED SUCCESSFULLY********************");
			System.out.println();
		}else{
			System.out.println("********************MOTION FAILED SUCCESSFULLY********************");
			System.out.println();
		}
		printResults(voterHelper);
	}
	/**
     * Displays total votes polled, total support votes, 
     * total appose votes, voting start time, voting end time.
     *
     * @param VoterHelper voterHelper
     */
	private void printResults(VoterHelper voterHelper){
		DateFormat df = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss");
		df.setTimeZone(TimeZone.getTimeZone("GMT+0"));
		System.out.println("***********************VOTING RESULTS***************************");
		System.out.println("Total number for votes polled      ::" + voterHelper.getTotalVotesPolled());
		System.out.println("Total number for votes to support  ::" + voterHelper.getTotalSupport());
		System.out.println("Total number for votes to oppose   ::" + voterHelper.getTotalOppose());
		System.out.println("Total number for invalid votes     ::" + voterHelper.getTotalInvalid());
		System.out.println("Time voting started                ::" + df.format(new Date(voterHelper.getTimeStarted())));
		System.out.println("Time voting Completed              ::" + df.format(new Date(voterHelper.getTimeCompleted())));	
		System.out.println("STATUS                             ::"+VotingStatus.CountingCompleted.toString());
	}
}
