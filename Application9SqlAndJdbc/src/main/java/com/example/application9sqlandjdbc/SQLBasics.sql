CREATE TABLE trainees(
    rollno INT PRIMARY KEY,
    sname VARCHAR(40),
    per FLOAT,
    branch VARCHAR(10),
    mobile VARCHAR(12)
);
# Q- can we use rollNo as rollNo. i.e. use special characters?
# A- Only letters, numbers, and underscores (_) are allowed along with other specific symbols depending on the database system

# Q- Why have ; at the end of each command?
# A- It indicates the end of a SQL statement, thus preventing multiple non-related statements from being executed together.

INSERT INTO trainees
VALUES(101, 'Aman', 65, 'CSE', '9876543210');

INSERT INTO trainees
VALUES (102, 'Raman', 80, 'ECE', '9876543111');

# Partial insertion (i.e. insertion in specific columns)
insert into trainees (rollno, sname, mobile)
values (103, 'Harry', '1111122222');

update trainees
set branch = 'ECE', per = 90
where rollno = 103;

# Retrieval
SELECT * from trainees;

# giving grace percentage to all students having less than or equal to 70%
update trainees
set per = per + 5
where per <= 70;

select *
from trainees
where sname = 'Aman';

insert into trainees
values (105, 'Chaman', 91, 'MECH', '9090901111');

select * from trainees
where sname = 'Aman' and branch = 'ECE';

# using OR operator
select *
from trainees
where branch = 'MECH' or branch = 'CSE';

# using IN operator
select *
from trainees
where branch in ('MECH', 'CSE');

# using NOT EQUAL TO operator (<>, !=)
select *
from trainees
where branch <> 'CSE';

# displays only these columns
select rollno, sname, per
from trainees;

# sorting on the basis of sname and retrieving specific columns for all records.
select rollno, sname, per
from trainees
order by sname;

select rollno, sname, per
from trainees
order by per;

# sorting in descending order of percentage
select rollno, sname, per
from trainees
order by per desc;

# displays top 2 students
select *
from trainees
limit 2;

# displays top 2 highest percentage students
select rollno, sname, per
from trainees
order by per desc
limit 2;

# selects all names starting with 'A'
SELECT * FROM TRAINEES
WHERE sname like 'A%';

# selects all names ending with 'an'
SELECT * FROM TRAINEES
WHERE sname like '%an';

# selects all names having 'ma' in between
SELECT * FROM TRAINEES
WHERE sname like '%ma%';

select
	max(per) as 'Maximum Percentage',
	sum(per) as 'Total Percentage',
	avg(per) as 'Average Percentage',
	min(per) as 'Minimum Percentage'
from trainees;

# Subquery (nested query)
# Find the details of the student having the highest percentage
select * from trainees
where per = (select max(per) from trainees);

SELECT MAX(PER)
FROM TRAINEES
WHERE BRANCH = 'ECE';

SELECT
	sname,
	branch,
	MAX(PER)
FROM trainees
group by branch
having branch like '%e';

-- Above statement is incorrect for SQLServer or postgres
-- Reason:
-- You are selecting sname, but sname is not in the GROUP BY clause and is not inside an aggregate function.
-- SQL does not know which sname to pick for each group, since there could be many names in the same branch.

-- What Actually Happens
-- Standard SQL: This query will fail with an error like:
-- column "sname" must appear in the GROUP BY clause or be used in an aggregate function
-- Some SQL dialects (like MySQL with ONLY_FULL_GROUP_BY off): It may pick a random sname from the group, but this is not reliable or standard.

-- Correct way to find topper from each branch
SELECT t.sname, t.branch, t.PER
FROM trainees t
INNER JOIN (
    SELECT branch, MAX(PER) AS max_per
    FROM trainees
    GROUP BY branch
) maxes
ON t.branch = maxes.branch AND t.PER = maxes.max_per
WHERE t.branch LIKE '%e';

# returns distinct branches
SELECT DISTINCT branch
FROM trainees;

# deletes all records from table
delete from trainees;

delete from trainees
where rollno = 105;

# deletes the table itself
drop table trainees;

-- MySQL in XAMPP is actually MariaDB (a fork of MySQL)
-- Current version of MariaDB in XAMPP - 10.4.13-MariaDB
-- It is advised to not have different server for MySQL and MariaDB due to changes in connection strings and configs.

-- MariaDB is largely compatible with MySQL's APIs and commands, meaning you can generally use MySQL connectors to connect to it.