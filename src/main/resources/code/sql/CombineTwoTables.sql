-- https://leetcode.com/problems/combine-two-tables/
SELECT p.FirstName, p.LastName, a.City, a.State from Person p LEFT JOIN Address a On p.PersonId=a.PersonId;