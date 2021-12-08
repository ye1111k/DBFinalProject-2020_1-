#가수 정보 저장할 table 생성/nop는 number of people 구성 인원 수/primary key는 가수의 name
create table singers(name varchar(100) not null, debutdate DATE not null, nop int, sex varchar(10), primary key (name));

#앨범 정보 저장할 table 생성/nos는 number of song 수록된 곡 수/primary key는 앨범의 이름, sname은 singers table 참조하는 foreign key
create table albums(name varchar(100) not null, nos int, releasedate DATE, sname varchar(100), primary key (name), foreign key (sname) references singers(name));

#곡 내용 저장할 table 생성/ptm은 playtime 중 minute, pts는 playtime 중 second
#primary key는 title/sname과 aname은 각각 singers table과 albums table을 참조하는 foreign key 
create table songs(title varchar(100) not null, sname varchar(100) not null, aname varchar(100) not null, ptm int, pts int, primary key(title), 
foreign key (sname) references singers(name), foreign key (aname) references albums(name));

#singers table의 name column index 생성
create index singer_sex on singers(sex);

#수록곡이 일정 수 이상을 넘는 앨범의 이름과 수록곡을 편하게 select하기 위해 관련된 값들만 보이도록 하는 view 생성
create view aview as
	select name, nos, title
	from albums, songs
	where albums.name=songs.aname;

#singers table부터 insert
insert into singers values ('IU', '2008-09-23', 1, 'female');
insert into singers values ('AKMU', '2014-04-07', 2, 'both');
insert into singers values ('RedVelvet', '2014-08-01', 5, 'female');
insert into singers values ('SHINEE', '2008-05-25', 5, 'male');
insert into singers values ('JungSeunghwan', '2016-11-29', 1, 'male');

#그 다음 albums table insert
insert into albums values ('Love poem', 6, '2019-11-18', 'IU');
insert into albums values ('Modern Times', 13, '2013-10-08', 'IU');
insert into albums values ('heart', 1, '2015-05-18', 'IU');
insert into albums values ('adolescence up', 6, '2016-05-04', 'AKMU');
insert into albums values ('voyage', 10, '2019-09-25', 'AKMU');
insert into albums values ('The red', 10, '2015-09-09', 'RedVelvet');
insert into albums values ('Russian Roulette', 7, '2016-09-07', 'RedVelvet');
insert into albums values ('The Reve Festival Finale', 16, '2019-12-23', 'RedVelvet');
insert into albums values ('Sherlock', 7, '2012-03-19', 'SHINEE');
insert into albums values ('The Misconception of Me', 9, '2013-04-26', 'SHINEE');
insert into albums values ('Odd', 11, '2015-05-18', 'SHINEE');
insert into albums values ('voice', 6, '2016-11-29', 'JungSeunghwan');

#마지막으로 songs table insert
insert into songs values ('Blueming', 'IU', 'Love poem', 3, 37);
insert into songs values ('Modern Times', 'IU', 'Modern Times', 3, 26);
insert into songs values ('Love poem', 'IU', 'Love poem', 4, 18);
insert into songs values ('heart', 'IU', 'heart', 2, 47);
insert into songs values ('red shoes', 'IU', 'Modern Times', 4, 14);
insert into songs values ('that people moving around', 'AKMU', 'adolescence up', 3, 23);
insert into songs values ('RE-BYE', 'AKMU', 'adolescence up', 3, 9);
insert into songs values ('how can i love a breakup, i love you', 'AKMU', 'voyage', 4, 50);
insert into songs values ('Dumb Dumb', 'RedVelvet', 'The Red', 3, 23);
insert into songs values ('Huff n Puff', 'RedVelvet', 'The Red', 3, 1);
insert into songs values ('Psycho', 'RedVelvet', 'The Reve Festival Finale', 3, 31);
insert into songs values ('In&Out', 'RedVelvet', 'The Reve Festival Finale', 3, 13);
insert into songs values ('Remember Forever', 'RedVelvet', 'The Reve Festival Finale', 3, 8);
insert into songs values ('Russian Roulette', 'RedVelvet', 'Russian Roulette', 3, 31);
insert into songs values ('View', 'SHINEE', 'Odd', 3, 11);
insert into songs values ('Sherlock', 'SHINEE', 'Sherlock', 3, 57);
insert into songs values ('Alarm Clock', 'SHINEE', 'Sherlock', 3, 59);
insert into songs values ('Why So Serious?', 'SHINEE', 'The Misconception of Me', 3, 40);
insert into songs values ('you are fool', 'JungSeunghwan', 'voice', 4, 0);
insert into songs values ('the winter', 'JungSeunghwan', 'voice', 3, 57);

