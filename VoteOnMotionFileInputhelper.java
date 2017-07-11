package com.karuna.vm.inputHelper;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

import com.karuna.vm.dto.Voter;
import com.karuna.vm.dto.VoterConstants;
import com.karuna.vm.dto.VoterHelper;
import com.karuna.vm.dto.VotingStatus;
import com.karuna.vom.voteException.VoteException;

public class VoteOnMotionFileInputhelper extends BaseInputHelper{

	private static final String MOTION= "motion";
	private static final String TIME= "time";
	private static final String invalid = "invalid";
	private static final String motionNameError = "Enter valid motion desription only upon enting valid descrption votes can be accepted";
	private static final String DupicateId = "Voter Id entered has been voted already..!!";
	private static final int MIN_POLL_TIME = 2;
	
	
	/**
     * Populate the motion description and minimum 
     * amount of time voting is be opened
     *
     * @param scanner in
     * @param VoterHelper voterHlp
     * @return
	 * @throws VoteException 
     */
	@Override
	public void setMotionAndTimePollIsOpened(VoterHelper voterHlp, Scanner in, String line) throws VoteException{
		populateMotion(voterHlp, line);
		populateMinTimePollOpened(voterHlp, line);
	}
	/**
     * Populate the motion description
     *
     * @param scanner in
     * @param VoterHelper voterHlp
     * @return
	 * @throws VoteException 
     */
	private void populateMotion(VoterHelper voterHlp, String line) throws VoteException{

		if(voterHlp.getMotionDesc() == null && line.startsWith(MOTION) ){
			String motionDes = line.substring(7, line.length());
			motionDes.trim();
			if(!motionDes.equalsIgnoreCase("")){
				System.out.println("Entered Motion Description to start the voting::" + motionDes);
				voterHlp.setMotionDesc(motionDes);
			}else{
				throw new VoteException(motionNameError);
			}
		}
	}
	
	
	/**
     * Calculate the minimum poll time opened.
     * If minimum poll open time entered greater then 20 min will be defaulted to 15 min.
     *
     * @param scanner in
     * @param VoterHelper voterHlp
     * @return
     */
	private void populateMinTimePollOpened(VoterHelper voterHlp, String line){
		int time = MIN_POLL_TIME;
		if(voterHlp.getMinWaitTimeOpened() == 0 && line.startsWith(TIME)){
			String timeStr = line.substring(5, line.length());
			timeStr.trim();
			try{
				time = Math.abs(Integer.parseInt(timeStr));
				// Restrict the time to less then 20 min, usefull for test execution
				System.out.println("Entered duration of time(in minutes) :: "+time);
				if(time > 20){
					System.out.println("Minimum poll Wait Time set to 2 minites as entered duration time is more than 20 ");
					time = MIN_POLL_TIME;
				}
				voterHlp.setMinWaitTimeOpened(time*60000);
			}catch(NumberFormatException numExe){
				System.out.println("Minimum poll Wait Time set to 2 minites");
				voterHlp.setMinWaitTimeOpened(time*60000);
			}
		}
		
	}
	
	/**
     * Value  populated for voter object using keyBoardand is validated.
     * If the value entered is "DONE", return done for further process.
     *
     * @param scanner null
     * @param HashMap<String, Voter> voterDetails
     * @param String line
     * @param VotingStatus votingStatus
     * @return
     */
	@Override
	public String startVotingProess(Scanner in, HashMap<String, Voter> voterDetails, String line, VotingStatus votingStatus){
		Voter voter = new Voter();
		if(!line.startsWith(TIME) && !line.startsWith(MOTION)){
			System.out.println("STATUS:" +votingStatus.toString());
			String[] voterDet = line.split(" ");
			if(voterDet.length > 1) {
				voter.setId(voterDet[0].trim());
				populateEnteredVote(voter, voterDet[1].trim());
			} else{
				voter.setId(voterDet[0].trim());
				populateEnteredVote(voter, invalid);
			}

			if(validateVoterId(voterDetails, voter)){
				return DupicateId;
			} 
			voterDetails.put(voter.getId(), voter);
		}
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
     * Populates the vote field in voter object.
     * If the value entered is "DONE", return done, for further process.
     * If the value entered is "YES/Y/YEAS", saved as YEAS.
     * If the value entered is "NO/N/NAYS", saved as YEAS.
     * Any other values stored as NotValid
     *
     * @param scanner in
     * @param Voter voter
     * @return
     */
	public void populateEnteredVote(Voter voter, String vote){
		
		System.out.println("Mr/Mrs/Ms. "+ voter.getId() +" thanks for voting ");
		
		if(vote.equalsIgnoreCase("Yes") || vote.equalsIgnoreCase("Y") || vote.equalsIgnoreCase("Yeas")){
			voter.setVote(VoterConstants.YEAS.toString());
		} else if(vote.equalsIgnoreCase("No") || vote.equalsIgnoreCase("N") || vote.equalsIgnoreCase("Nays")) {
			voter.setVote(VoterConstants.NAYS.toString());
		} else{
			voter.setVote(VoterConstants.NotValid.toString());
		}
	}
	
	/**
     * Statements displayed that describes the steps to follow.
     *
     * @return
     */
	public static void printInitialDescrition(){
		System.out.println("Please follow the Below Steps for voting process.                                                                                       *");
		System.out.println("Step 1: 1st line of the file should strat with, {motion},then {:},followed by motion desciption                                         *");
		System.out.println("Step 2: 2nd line of the file should strat with, {time},then {:},followed by time in minutes(better not to execeed 20 min)               *");
		System.out.println("Step 3: 3rd line of the file might have id {any value}, then fallowed by a space { }, then Yeas/yes/y to agree or Nays/No/N to disagree *");
		System.out.println("Step 4: Duplicates are not allowed, entered will be discarded                                                                           *");
		System.out.println("*****************************************************************************************************************************************");
	}

}
