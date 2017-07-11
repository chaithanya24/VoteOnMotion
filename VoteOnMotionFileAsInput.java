package com.karuna.vm.input;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Scanner;

import com.karuna.vm.dto.Voter;
import com.karuna.vm.dto.VoterHelper;
import com.karuna.vm.dto.VotingStatus;
import com.karuna.vm.inputHelper.VoteOnMotionFileInputhelper;
import com.karuna.vm.process.ProcessMotionVotes;
import com.karuna.vom.voteException.VoteException;

public class VoteOnMotionFileAsInput {
		
	public static void main(String[] args) {
		ProcessMotionVotes processVotes = new ProcessMotionVotes();
		HashMap<String, Voter> voterDetails = new HashMap<String, Voter>();
		VoterHelper voterHlp = new VoterHelper();
		VoteOnMotionFileAsInput inputUsingFile = new VoteOnMotionFileAsInput();
		InputStream is = null;
		String location = null;
		Scanner in = new Scanner(System.in);
		if(args.length > 0){
			location = args[0].trim();
		}
		try {
			/*
			 * Prints the initial steps and description
			 */
			VoteOnMotionFileInputhelper.printInitialDescrition();
			// Accept the file path arguement
			if(location != null){
				is = new FileInputStream(location);
			}else{
				is = new FileInputStream("C://personal//JCode//VoteOnMotion//src//test//resource//input.txt");
			}
			
			BufferedReader buf = new BufferedReader(new InputStreamReader(is)); 
			voterHlp.setTimeStarted(System.currentTimeMillis());
			String line = "";
			while((line = buf.readLine()) != null){
				// Process each line.
				inputUsingFile.processEachLine(voterDetails, voterHlp, line);
			}
			voterHlp.setTimeCompleted(System.currentTimeMillis());
			
		    //validation, counting and results processed. 
		    processVotes.processVotes(voterDetails, voterHlp, in);
		} catch (FileNotFoundException e) {
			System.out.println("Please enter a valid file name, enter file Not Found");
		}  catch (IOException e) {
			System.out.println("Io Exception While Votting");
		} catch (InterruptedException e) {
			System.out.println("Thread interuupt exception while voting");
		}catch (VoteException vExc){
			System.out.println(vExc.getMessage());
		}
	}
	
	/**
	 * Method helps to process each line from the given input file.
	 * 1st and 2nd lines (Motion and time) are used to set motion 
	 * description and time poll is opened. starting from 3rd line 
	 * voter details parsed processed.
	 * 
	 * @param HashMap<String, Voter> voterDetails
	 * @param VoterHelper voterHlp
	 * @param String line
	 * 
	 */
	public void processEachLine(HashMap<String, Voter> voterDetails, VoterHelper voterHlp, String line) throws VoteException{
		VoteOnMotionFileInputhelper helper =new VoteOnMotionFileInputhelper();
		line = ((line == null) ? "" : line.trim());
		if(!line.equalsIgnoreCase("")){
			helper.setMotionAndTimePollIsOpened(voterHlp, null, line);
			helper.startVotingProess(null, voterDetails, line, VotingStatus.VotingInProgress);
		}
	}

}
