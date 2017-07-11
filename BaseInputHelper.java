package com.karuna.vm.inputHelper;

import java.util.HashMap;
import java.util.Scanner;

import com.karuna.vm.dto.Voter;
import com.karuna.vm.dto.VoterHelper;
import com.karuna.vm.dto.VotingStatus;
import com.karuna.vom.voteException.VoteException;

abstract public class BaseInputHelper {
	
	public abstract void setMotionAndTimePollIsOpened(VoterHelper voterHlp, Scanner in, String line) throws VoteException;
	public abstract String startVotingProess(Scanner in, HashMap<String, Voter> voterDetails, String line, VotingStatus votingStatus);

}
