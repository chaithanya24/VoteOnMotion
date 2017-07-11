package com.karuna.vm.dto;

/**
 * Voter helper
 * @author karuna
 */
public class VoterHelper {
	
	private String stringToParse;
	private String votingStatus;
	private String motionDesc;
	private long timeStarted ;
	private long timeCompleted;
	private long minWaitTimeOpened;
	private int totalSupport = 0;
	private int totalOppose = 0;
	private int totalInvalid = 0;
	private int totalVotesPolled = 0; 

	
	/**
	 * @return the stringToParse
	 */
	public String getStringToParse() {
		return stringToParse;
	}


	/**
	 * @param stringToParse the stringToParse to set
	 */
	public void setStringToParse(String stringToParse) {
		this.stringToParse = stringToParse;
	}


	/**
	 * @return the votingStatus
	 */
	public String getVotingStatus() {
		return votingStatus;
	}


	/**
	 * @param votingStatus the votingStatus to set
	 */
	public void setVotingStatus(String votingStatus) {
		this.votingStatus = votingStatus;
	}


	/**
	 * @return the motionDesc
	 */
	public String getMotionDesc() {
		return motionDesc;
	}


	/**
	 * @param motionDesc the motionDesc to set
	 */
	public void setMotionDesc(String motionDesc) {
		this.motionDesc = motionDesc;
	}


	/**
	 * @return the timeStarted
	 */
	public long getTimeStarted() {
		return timeStarted;
	}


	/**
	 * @param timeStarted the timeStarted to set
	 */
	public void setTimeStarted(long timeStarted) {
		this.timeStarted = timeStarted;
	}


	/**
	 * @return the timeCompleted
	 */
	public long getTimeCompleted() {
		return timeCompleted;
	}


	/**
	 * @param timeCompleted the timeCompleted to set
	 */
	public void setTimeCompleted(long timeCompleted) {
		this.timeCompleted = timeCompleted;
	}


	/**
	 * @return the minWaitTimeOpened
	 */
	public long getMinWaitTimeOpened() {
		return minWaitTimeOpened;
	}


	/**
	 * @param minWaitTimeOpened the minWaitTimeOpened to set
	 */
	public void setMinWaitTimeOpened(long minWaitTimeOpened) {
		if(minWaitTimeOpened > 20*60000){
			setMinWaitTimeOpened(2*60000);
		}
		this.minWaitTimeOpened = minWaitTimeOpened;
	}


	/**
	 * @return the totalSupport
	 */
	public int getTotalSupport() {
		return totalSupport;
	}


	/**
	 * @param totalSupport the totalSupport to set
	 */
	public void setTotalSupport(int totalSupport) {
		this.totalSupport = totalSupport;
	}


	/**
	 * @return the totalOppose
	 */
	public int getTotalOppose() {
		return totalOppose;
	}


	/**
	 * @param totalOppose the totalOppose to set
	 */
	public void setTotalOppose(int totalOppose) {
		this.totalOppose = totalOppose;
	}


	/**
	 * @return the totalInvalid
	 */
	public int getTotalInvalid() {
		return totalInvalid;
	}


	/**
	 * @param totalInvalid the totalInvalid to set
	 */
	public void setTotalInvalid(int totalInvalid) {
		this.totalInvalid = totalInvalid;
	}


	/**
	 * @return the totalVotesPolled
	 */
	public int getTotalVotesPolled() {
		return totalVotesPolled;
	}


	/**
	 * @param totalVotesPolled the totalVotesPolled to set
	 */
	public void setTotalVotesPolled(int totalVotesPolled) {
		this.totalVotesPolled = totalVotesPolled;
	}

	/**
	 * Increments the support vote total by one.
	 */
	public void incrementSupport(){
		
		setTotalSupport(this.getTotalSupport()+1);
	}
	
	/**
	 * Increments the oppose vote total by one.
	 */
	public void incrementOppose(){
		
		setTotalOppose(this.getTotalOppose()+1);
	}
	
	/**
	 * Increments the invalid vote total by one.
	 */
	public void incrementInvalid(){
		
		setTotalInvalid(this.getTotalInvalid()+1);
	}	

}
