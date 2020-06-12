package com.revature.mariokartfighter.dao;

import java.util.List;

import com.revature.mariokartfighter.models.MatchRecord;

public interface IMatchRecordRepo {
	public MatchRecord addMatchRecord(MatchRecord match);
	public List<MatchRecord> getAllMatches();
	public List<MatchRecord> getPlayerMatches(String playerID);
}	
