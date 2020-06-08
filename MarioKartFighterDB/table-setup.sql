DROP TABLE IF EXISTS player CASCADE;
DROP TABLE IF EXISTS item CASCADE;
DROP TABLE IF EXISTS playableCharacter CASCADE;
DROP TABLE IF EXISTS matchRecord CASCADE;

CREATE TABLE item (
	itemID VARCHAR(64) PRIMARY KEY,
	name VARCHAR(64),
	typeThatCanUse VARCHAR(64),
	bonusToHealth INT,
	bonusToAttack FLOAT,
	bonusToDefense FLOAT,
	unlockAtLevel INT
);

CREATE TABLE playableCharacter (
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
	matchID VARCHAR(128) PRIMARY KEY,
	timeOfMatch TIMESTAMP,
	player1ID VARCHAR(64) REFERENCES player,
	player1CharacterID VARCHAR(64) REFERENCES playableCharacter,
	player1ItemID VARCHAR(64) REFERENCES item,
	player2ID VARCHAR(64) REFERENCES player,
	player2CharacterID VARCHAR(64) REFERENCES playableCharacter,
	player2ItemID VARCHAR(64) REFERENCES item,
	player2IsBot BOOLEAN,
	winnerID VARCHAR(64)
);