package com.karuna.vm.process;

import java.util.HashMap;

import com.karuna.vm.dto.Voter;
import com.karuna.vm.dto.VoterHelper;
import com.karuna.vom.voteException.VoteException;

public interface VoteOnMotionService{

	
	/**
     * Validates motion description and total number of votes.
     *
     * @param HashMap<String, Voter> voterDetails
     * @param VoterHelper voterHelper
     * @throws VoteException
     */
	public void validateVotes(HashMap<String, Voter> voterDetails, VoterHelper voterHelper) throws VoteException;
	
	/**
     * Counts the total number of votes polled.
     * 
     * @param HashMap<String, Voter> voterDetails
     * @param VoterHelper voterHelper
     */
	public void countPolledVotes(HashMap<String, Voter> voterDetails, VoterHelper voterHlp);
	
	/**
     * Process Vice president vote.
     *
     * @param Voter voter
     */
	public void processVpVote(Voter voter, VoterHelper voterHelper);
	
	/**
     * Displays results.
     * 
     * @param VoterHelper voterHelper
     */
	public void results(VoterHelper voterHelper);
	
}
