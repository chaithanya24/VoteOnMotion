package com.karuna.vm.inputHelper;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

import com.karuna.vm.dto.Voter;
import com.karuna.vm.dto.VoterConstants;
import com.karuna.vm.dto.VoterHelper;
import com.karuna.vm.dto.VotingStatus;
import com.karuna.vom.voteException.VoteException;

public class VoteOnMotionScannerInputHelper extends BaseInputHelper{

	private static final String DONE = "Done";
	private static final String DupicateId = "Voter Id entered has been voted already..!!";
	private static final String motionErr = "Enter valid motion desription only upon enting valid descrption votes can be accepted";
	private static final String voterIdErr = "Enter valid VoterID  to continue";
	private static final int MIN_POLL_TIME = 2;
	
	/**
     * Populate the Steps to follow, motion description and minimum 
     * amount of time voting is be opened
     *
     * @param scanner in
     * @param VoterHelper voterHlp
     * @return
     */
	@Override
	public void setMotionAndTimePollIsOpened(VoterHelper voterHlp, Scanner in, String line) throws VoteException{
		printInitialDescrition();
		populateMotion(voterHlp, in);
		populateMinTimePollOpened(voterHlp, in);
	}
	/**
     * Populate the motion description
     *
     * @param scanner in
     * @param VoterHelper voterHlp
     * @return
     */
	private void populateMotion(VoterHelper voterHlp, Scanner in){
		
		System.out.println("Enter the Motion Description Below to start the voting::");
		String motion = enterValueAndValidate(in, motionErr);
		voterHlp.setMotionDesc(motion);
	}
	
	
	/**
     * Calculate the minimum poll time opened.
     * If minimum poll open time entered greater then 20 min will be defaulted to 15 min.
     *
     * @param scanner in
     * @param VoterHelper voterHlp
     * @return
     */
	private void populateMinTimePollOpened(VoterHelper voterHlp, Scanner in){
		int time = MIN_POLL_TIME;
		System.out.println("Enter duration of time(minutes) for which the voting is open(By defaulf 15 Min and 20 Max) ::");
		String value = in.nextLine().trim();
		try{
			time = Math.abs(Integer.parseInt(value));
		}catch(NumberFormatException numExe){
			System.out.println("Minimum poll Wait Time set to 2 minites");
		}
		voterHlp.setMinWaitTimeOpened(time*60000);
	}
	
	/**
     * Value  populated for voter object using keyBoardand is validated.
     * If the value entered is "DONE", return done for further process.
     *
     * @param scanner in
     * @param HashMap<String, Voter> voterDetails
     * @param String null
     * @param VotingStatus votingStatus
     * @return
     */
	@Override
	public String startVotingProess(Scanner in, HashMap<String, Voter> voterDetails, String line, VotingStatus votingStatus){
		Voter voter = new Voter();
		System.out.println("STATUS::"+votingStatus.toString());
		populateEnteredVoterId(in, voter);
		if(DONE.equalsIgnoreCase(voter.getId())){
			return DONE;
		}
		if(validateVoterId(voterDetails, voter)){
			return DupicateId;
		}
		populateEnteredVote(in, voter);
		if(DONE.equalsIgnoreCase(voter.getVote())){
			return DONE;
		}
		voterDetails.put(voter.getId(), voter);
		return "";
	}
	
	/**
     * Validate if entered voter id voted already.
     *
     * @param HashMap<String, Voter> voterDetails
     * @param Voter voter
     * @return
     */
	private boolean validateVoterId(HashMap<String, Voter> voterDetails, Voter voter){
		String voterId = voter.getId();
		Set<String> voterIds = voterDetails.keySet();
		if(voterIds.contains(voterId)){
			System.out.println(DupicateId);
			return true;
		}
		return false;
	}
	
	/**
     * Populates a value entered using key board.
     * If the value entered is "DONE", return done for further process.
     * If a invalid value ""/ null then requested to enter again.
     *
     * @param scanner in
     * @param Voter voter
     * @return
     */
	private void populateEnteredVoterId(Scanner in, Voter voter){
		System.out.println("Enter Voter ID");
		String id = enterValueAndValidateDone(in, voterIdErr);
		voter.setId(id);
	}
	
	/**
     * Populates a value entered using key board.
     * If the value entered is "DONE", return done, for further process.
     * If the value entered is "YES/Y/YEAS", saved as YEAS.
     * If the value entered is "NO/N/NAYS", saved as YEAS.
     * Any other values stored as NotValid
     *
     * @param scanner in
     * @param Voter voter
     * @return
     */
	public void populateEnteredVote(Scanner in, Voter voter){
		
		System.out.println("Mr/Mrs/Ms. "+ voter.getId() +" Enter your Vote : ");
		String vote = in.nextLine().trim();
		
		if(vote.equalsIgnoreCase(DONE)){
			voter.setVote(DONE);
		}else if(vote.equalsIgnoreCase("Yes") || vote.equalsIgnoreCase("Y") || vote.equalsIgnoreCase("Yeas")){
			voter.setVote(VoterConstants.YEAS.toString());
		} else if(vote.equalsIgnoreCase("No") || vote.equalsIgnoreCase("N") || vote.equalsIgnoreCase("Nays")) {
			voter.setVote(VoterConstants.NAYS.toString());
		} else{
			voter.setVote(VoterConstants.NotValid.toString());
		}
		System.out.println("Thank you for taking time to vote");
		System.out.println("            *****                ");
	}
	
	/**
     * Statements displayed that describes the steps to follow.
     *
     * @return
     */
	private static void printInitialDescrition(){
		System.out.println("Please follow the Below Steps for voting process.                        	*");
		System.out.println("Step 1: You must enter a VALID MOTION description to continue on Voting		*");
		System.out.println("Step 2: Enter a valid voter Id (ID is mandatory to proceed)			*");
		System.out.println("Step 3: Enter Yeas/Yes/Y to agree with the motion proposed			*");
		System.out.println("Step 4: Enter Nays/No/N to disagree with the motion proposed			*");
		System.out.println("Step 5: If you are voting officer, Enter Done after no one to vote		*");
		System.out.println("Step 6: Once you entered DONE, you won't be able to re-initiate polling		*");
		System.out.println("*****************************************************************************");
	}
	
	/**
     * Value entered using key board is validated.
     * If a invalid value ""/ null then requested to enter again.
     *
     * @param scanner in
     * @param String errorMsg
     * @return
     */
	private String enterValueAndValidate(Scanner in, String errorMsg){
		String value = in.nextLine().trim();
		if(value != null && !"".equalsIgnoreCase(value)){
			return value;
		}else{
			System.err.println(errorMsg);
			enterValueAndValidate(in, errorMsg);
		}
		return value;
	}
	
	/**
     * Value entered using key board is validated.
     * If the value entered is "DONE", return done, for further process.
     * If a invalid value ""/ null then requested to enter again.
     *
     * @param scanner in
     * @param String errorMsg
     * @return
     */
	private String enterValueAndValidateDone(Scanner in, String errorMsg){

		String value = in.nextLine().trim();
		if(value.equalsIgnoreCase(DONE)){
			return value;
		}else if(value != null && !"".equalsIgnoreCase(value)){
			return value;
		}else{
			System.err.println(errorMsg);
			enterValueAndValidate(in, errorMsg);
		}
		return value;
	}
	
}
