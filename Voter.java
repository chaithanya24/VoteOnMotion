package com.karuna.vm.dto;

/**
 * Voter details
 * @author karuna
 */
public class Voter {
	
	private String id;
	private String vote;

	/**
	 * Default constructor
	 */
	public Voter() {
		
	}
	/**
	 * @param id
	 * @param vote
	 */
	public Voter(String id, String vote) {
		super();
		this.id = id;
		this.vote = vote;
	}


	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}


	/**
	 * @return the vote
	 */
	public String getVote() {
		return vote;
	}


	/**
	 * @param vote the vote to set
	 */
	public void setVote(String vote) {
		this.vote = vote;
	}
	
	

}
