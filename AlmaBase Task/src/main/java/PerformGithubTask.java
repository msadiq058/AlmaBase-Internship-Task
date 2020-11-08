/*

    Here I am using an API which performs the tasks like fetching the repos
    fetching fork count , fetching commits and returns it.
    It is an open-source API.
    Link for the github repo of API is https://github.com/hub4j/github-api

 */


import org.kohsuke.github.*;

import java.io.IOException;
import java.util.*;

public class PerformGithubTask {

    // Variables for storing the input data.

    private String organization;
    private int repos, committees;

    // Result to be stored in this StringBuffer
    private StringBuffer result = new StringBuffer();

    /*
    This contructor initializes the local values for the
      input variables .
     */

    PerformGithubTask(String s,int n,int m){
        organization = s;
        repos = n;
        committees = m;
    }


    public  void PerformTask() throws IOException {

        /*
    It is a function which perform the task in the given manner :
    1. Fetch the repos of the organization and its forks count.
    2. Sort this fetched data with respect to forks count so that we can choose
        the most n forked repos.
    3. For each repo in n repos we fetch the commits.
        And then for each commit we update the value of
        committer in map of <committer , number of commmit>.
    4. Sort this data on basis of number of commits
        and then will store the top m committies on basis of number of commits.
     */



        //   Initializing the github contructor for fetching the data with
        //   Here use your personal authentication token.

        GitHub github = new GitHubBuilder().withOAuthToken("").build();

        /*
            Finding for the organization if present then perform next operation
            otherwise don't do anything.
         */
        GHUser ghUser = null;
        try {
            ghUser = github.getUser(organization.trim());
        } catch (IOException e) {
            result.append("\n\n    ");
            result.append("No organization found of name "+organization);
            return;
        }

        // Printing the bio for checking whether processed something or not
        System.out.println(ghUser.getBio()+"\n");

        // This class stores the Github repos for that Github User

        PagedIterable<GHRepository> github_repo_extract = ghUser.listRepositories();

        List<GHRepository> github_repo_list = new LinkedList<GHRepository>(github_repo_extract.toList());

        // Sorting the repos with respect to fork count

        Collections.sort(github_repo_list,new Comparator<GHRepository>(){
            @Override
            public int compare(GHRepository ghRepository, GHRepository t1) {
                return Integer.compare(ghRepository.getForksCount(),t1.getForksCount())*-1;
            }
        });

        try {
            // iterating for each top n repos
            for (int repo = 0; repo < repos; repo++) {
                try {
                    GHRepository github_repo = github_repo_list.get(repo);
                    System.out.println("Repo count "+repo);
                    try {

                        // Adding the name and fork count of repo in result

                        result.append("\n\n    ");
                        result.append(fixString("Repo Name")).append("Forks Count").append("\n");
                        result.append("    "+fixString(github_repo.getFullName())).append(github_repo.getForksCount()).append("\n");
                        result.append("\n");
                    }
                    catch (Exception ignored){
                    }

                    // Storing commits for that repo
                    PagedIterable<GHCommit> commits = github_repo.listCommits();

                    // Iterating in commits for each commit so that we can store the user and commit numbers

                    HashMap<String, Integer> commits_count = new HashMap<>();
                    for (GHCommit commit : commits) {
                        try {
                            String user = "";
                            try {
                                user = commit.getCommitter().getName();
                            } catch (Exception e) {
                                user = "null";
                            }
                            if (!user.equals("null")) {
                                if (commits_count.containsKey(user))
                                    commits_count.put(user, commits_count.get(user) + 1);
                                else commits_count.put(user, 1);
                            }
                        }
                        catch (Exception ignored){
                        }
                    }

                    // Transferring the data from map to string so that we can perform sorting task

                    String[][] commit_store = new String[commits_count.size()][2];
                    int ind = 0;
                    for (Map.Entry<String, Integer> entry : commits_count.entrySet()) {
                        commit_store[ind][0] = entry.getKey();
                        commit_store[ind][1] = String.valueOf(entry.getValue());
                        ind ++;
                    }

                    // Sorting the commits stored

                    Arrays.sort(commit_store, new Comparator<String[]>() {
                        @Override
                        public int compare(String[] t1, String[] t2) {
                            int val1 = Integer.parseInt(t1[1]);
                            int val2 = Integer.parseInt(t2[1]);
                            return Integer.compare(val1, val2)*-1;
                        }
                    });

                    //Adding the top m committies to result

                    boolean check = false;
                    try {
                        for (int commity = 0; commity < committees; commity++) {
                            if (commity == 0) {
                                String lcheck = commit_store[commity][0];
                                result.append("    "+fixString("Top Committers")).append("No. of Commits").append("\n");
                                result.append("    "+fixString(commit_store[commity][0])).append(commit_store[commity][1]).append("\n");
                                check = true;
                            } else {
                                result.append("    "+fixString(commit_store[commity][0])).append(commit_store[commity][1]).append("\n");
                            }
                        }
                    }
                    catch (Exception notRequired){

                    }
                    if(check)
                        result.append("\n\n");
                    else{
                        // If no committer found display this warning

                        result.append("    "+"No Committer found!!\n\n");
                    }
                } catch (Exception e) {
                    result.append("\n\n     No more repos found for this organization.");
                    return;
                }
            }

        }

        catch (Exception e){

        }

    }

    // Returnig the result obtained

    String getResult(){
        return result.toString();
    }

    /*
    This function takes an String input and fixes it to length of 70
        so that all the data will be aligned.
     */
    public static String fixString(String s){
        int len = 70;
        StringBuilder sBuilder = new StringBuilder(s);
        for(int i = sBuilder.length(); i<=len; i++)
            sBuilder.append(" ");
        s = sBuilder.toString();
        return s;
    }
}
