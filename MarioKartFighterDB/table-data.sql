-- ALL CHARACTERS GET 20 POINTS TO DISTRUBUTE FOR attack AND defense
	-- points counts as 10 for health
-- all-around: even distrubution of attack and defense
-- power: health increased, attack and/or defense decreased
-- speed: decreased health, increased attack 
-- skill: attack < defense

--level 1 characters
INSERT INTO playablecharacter VALUES ('m001', 'mario', 'all-around', 100, 30.0, 15.0, 1);
INSERT INTO playablecharacter VALUES ('p001', 'peach', 'skill', 100, 18.0, 32.0, 1);
INSERT INTO playablecharacter VALUES ('w001', 'wario', 'power', 110, 19.0, 30.0, 1);
INSERT INTO playablecharacter VALUES ('y001', 'yoshi', 'speed', 80, 32.0, 15.0, 1);
INSERT INTO playablecharacter VALUES ('t001', 'toad', 'skill', 100, 30.0, 15.0, 1);
--level 2 characters
INSERT INTO playablecharacter VALUES ('dk001', 'donkey kong', 'power', 100, 35.0, 20.0, 2);
INSERT INTO playablecharacter VALUES ('l001', 'luigi', 'all-around', 100, 35.0, 17.0, 2);
INSERT INTO playablecharacter VALUES ('d001', 'daisy', 'skill', 100, 38.0, 22.0, 2);
INSERT INTO playablecharacter VALUES ('sg001', 'shy guy', 'all-around', 100, 34.0, 24.0, 2);
INSERT INTO playablecharacter VALUES ('t002', 'toadette', 'speed', 80, 32.0, 20.0, 2);
--level 3 characters
INSERT INTO playablecharacter VALUES ('b001', 'bowser', 'power', 120, 39.0, 29.0, 3);
INSERT INTO playablecharacter VALUES ('r001', 'rosalina', 'speed', 80, 34.0, 18.0, 3);
INSERT INTO playablecharacter VALUES ('dk002', 'diddy kong', 'speed', 75, 43.5, 25.0, 3);
--level 4 characters
INSERT INTO playablecharacter VALUES ('db001', 'dry bones', 'speed', 50, 45.0, 30.0, 4);
INSERT INTO playablecharacter VALUES ('w002', 'waluigi', 'skill', 100, 40.0, 25.0, 4);
INSERT INTO playablecharacter VALUES ('kp001', 'koopa troopa', 'all-around', 100, 45.0, 30.0, 4);
INSERT INTO playablecharacter VALUES ('kb001', 'king boo', 'power', 140, 43.0, 33.0, 5);


--ITEMS ADD TO STATS THAT ARE WEAK FOR A GIVEN TYPE
-- all-around: increase all stats evenly
-- power: increase attack and defense evenly
-- speed: increase health
-- skill: increase attack

--level 1 items
INSERT INTO item VALUES ('bs001', 'blue shell', 'power', 0, 2.0, 7.0, 1);
INSERT INTO item VALUES ('gs001', 'green shell', 'skill', 0, 12.0, 0.0, 1);
INSERT INTO item VALUES ('rs001', 'red shell', 'all-around', 10, 5.0, 5.0, 1);
INSERT INTO item VALUES ('rm001', 'red mushroom', 'speed', 10, 0.0, 0.0, 1);
INSERT INTO item VALUES ('b001', 'banana', 'all-around', 5, 5.5, 5.5, 1);
--level 2 items
INSERT INTO item VALUES ('gsx001', 'green shell x3', 'skill', 0, 10.0, 0.0, 2);
INSERT INTO item VALUES ('rsx001', 'red shell x3', 'all-around', 10, 7.0, 3.0, 2);
INSERT INTO item VALUES ('rmx001', 'red mushroom x3', 'speed', 25, 0.0, 0.0, 2);
--level 3 items
INSERT INTO item VALUES ('gm001', 'gold mushroom', 'speed', 30, 0.0, 2.5, 3);
INSERT INTO item VALUES ('bo001', 'bob-omb', 'skill', 0, 17.0, 0.0, 3);
INSERT INTO item VALUES ('si001', 'squid ink', 'all-around', 20, 4.0, 4.0, 3);
INSERT INTO item VALUES ('l001', 'lightning', 'power', 0, 4.0, 4.0, 3);
--level 4 items
INSERT INTO item VALUES ('sp001', 'star power', 'power', 10, 15.0, 15.0, 4);
INSERT INTO item VALUES ('bb001', 'bullet bill', 'speed', 40, 0.0, 0.0, 4);

--add admin players for testing
INSERT INTO player VALUES ('admin001', 'password1', 4, 400, 0, 0, 'dk001', 'sp001');
INSERT INTO player VALUES ('admin002', 'password2', 4, 400, 0, 0, 'p001', 'bb001');
INSERT INTO player VALUES ('admin003', 'password3', 2, 250, 3, 2, 'd001', 'b001');

--add matchrecords for testing
INSERT INTO matchRecord VALUES ('mr001', '2008-01-01 00:00:01', 'admin001', 'admin002', FALSE, TRUE);
INSERT INTO playerMatchRecord VALUES('mr001', 'admin001', 'dk002', 'sp001');
INSERT INTO playerMatchRecord VALUES('mr001', 'admin002', 'p001', 'bb001');

INSERT INTO matchRecord VALUES ('mr002', '2008-01-01 00:10:01', 'admin001', 'admin002', FALSE, FALSE);
INSERT INTO playerMatchRecord VALUES('mr002', 'admin001', 'dk001', 'sp001');
INSERT INTO playerMatchRecord VALUES('mr002', 'admin002', 'p001', 'bb001');
