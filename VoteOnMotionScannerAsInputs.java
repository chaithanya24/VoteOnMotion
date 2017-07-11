package com.karuna.vm.input;

import java.util.HashMap;
import java.util.Scanner;

import com.karuna.vm.dto.Voter;
import com.karuna.vm.dto.VoterHelper;
import com.karuna.vm.dto.VotingStatus;
import com.karuna.vm.inputHelper.VoteOnMotionScannerInputHelper;
import com.karuna.vm.process.ProcessMotionVotes;
import com.karuna.vom.voteException.VoteException;

public class VoteOnMotionScannerAsInputs {

	public static void main(String[] args) {
		
		HashMap<String, Voter> voterDetails = new HashMap<String, Voter>();
		VoterHelper voterHlp = new VoterHelper();
		ProcessMotionVotes processVotes = new ProcessMotionVotes();
		VoteOnMotionScannerInputHelper inputHelper = new VoteOnMotionScannerInputHelper();
		Scanner in = new Scanner(System.in);
		try {
			// Populate the Steps to follow, motion description 
			// and minimum amount of time voting is be opened
			inputHelper.setMotionAndTimePollIsOpened(voterHlp, in, null);
			voterHlp.setTimeStarted(System.currentTimeMillis());
			for(int count=0; count <= 101; count++){
				String ret = inputHelper.startVotingProess(in, voterDetails, null, VotingStatus.VotingInProgress);
				if(ret.equalsIgnoreCase("Done")){
					break;
				}	
			}
			voterHlp.setTimeCompleted(System.currentTimeMillis());
			 //validation, counting and results processed.
			processVotes.processVotes(voterDetails, voterHlp, in);
		}  catch (InterruptedException e) {
			System.out.println("Thread interuupt exception while voting");
		}catch (VoteException vExe) {
			System.out.println(vExe.getMessage());
		}
		
		
	}
	
}
