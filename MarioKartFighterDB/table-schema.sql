DROP TABLE IF EXISTS player CASCADE;
DROP TABLE IF EXISTS item CASCADE;
DROP TABLE IF EXISTS playablecharacter CASCADE;
DROP TABLE IF EXISTS matchRecord CASCADE;
DROP TABLE IF EXISTS playerMatchRecord CASCADE;

CREATE TABLE item (
	itemID VARCHAR(64) PRIMARY KEY,
	name VARCHAR(64),
	typeThatCanUse VARCHAR(64),
	bonusToHealth INT,
	bonusToAttack FLOAT,
	bonusToDefense FLOAT,
	unlockAtLevel INT
);

CREATE TABLE playablecharacter (
	characterID VARCHAR(64) PRIMARY KEY,
	name VARCHAR(64),
	characterType VARCHAR(64),
	maxHealth INT,
	attackStat FLOAT,
	defenseStat FLOAT,
	unlockAtLevel INT
);

CREATE TABLE player (
	playerID VARCHAR(24) PRIMARY KEY,
	xpLevel INT,
	xpEarned INT,
	numberOfMatchesPlayed INT,
	numberOfWins INT,
	selectedCharacterID VARCHAR(64) REFERENCES playableCharacter,
	selectedItemID VARCHAR(64) REFERENCES item
);

CREATE TABLE matchRecord (
	matchID VARCHAR(36) PRIMARY KEY,
	timeOfMatch TIMESTAMP,
	player1ID VARCHAR(64) REFERENCES player,
	player2ID VARCHAR(64) REFERENCES player,
	player2IsBot BOOLEAN,
	winnerIsPlayer1 BOOLEAN
);

CREATE TABLE playerMatchRecord (
	matchID VARCHAR(36) REFERENCES matchRecord,
	playerID VARCHAR(64) REFERENCES player,
	characterID VARCHAR(64) REFERENCES playableCharacter,
	itemID VARCHAR(64) REFERENCES item
);
