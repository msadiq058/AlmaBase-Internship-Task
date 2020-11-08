# AlmaBase-Internship-Task
TASK <br>
Find the n most popular repositories of a given organization on Github (Eg:https://github.com/google) based on the number of forks. For each such repo find the top m committees and their commit counts. 
<br>
<br>
# Proposed Solution
<br>
Using the opensource GitHub API https://github-api.kohsuke.org/ to fetch the organization data , repos data and other information.
<br>
#Steps performed are in the given manner
1. Fetch the repos of the organization and its forks count.
2. Sort this fetched data with respect to forks count so that we can choose the most n forked repos.
3. For each repo in n repos we fetch the commits information. <br>And then for each commit we update the value of committer in the <br>map of < committerand number of commmit >.
4. Sort this data on basis of number of commits and then will store the<br> top m committies on basis of number of commits.
<br>
<br>

# Steps to execution :

1. git clone https://github.com/msadiq058/AlmaBase-Internship-Task.git
2. Build the project in IntelliJ Idea and build the maven dependencies for API.
3. Get your OAuth token from GitHub and enter it in https://github.com/msadiq058/AlmaBase-Internship-Task/blob/main/AlmaBase%20Task/src/main/java/PerformGithubTask.java this file.
4. Execute the code and insert the prompted input.
5. Output will be displayed on the provided screen in GUI.
<br>

###Note
For repos which have a large number of forks and commits please be patient<br> it will take some time to fetch the data.



