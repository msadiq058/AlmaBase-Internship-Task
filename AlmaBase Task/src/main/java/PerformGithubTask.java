import org.kohsuke.github.*;

import java.io.IOException;
import java.util.*;

public class PerformGithubTask {

    private String organization;
    private int repos,commities;
    private StringBuffer ans = new StringBuffer();

    PerformGithubTask(String s,int n,int m){
        organization = s;
        repos = n;
        commities = m;
    }

    public  void PerformTask() throws IOException {

        GitHub github = new GitHubBuilder().withOAuthToken("").build();

        GHUser ghUser= null;
        try {
            ghUser = github.getUser(organization);
        } catch (IOException e) {

        }

        System.out.println(ghUser.getBio()+"\n");

        PagedIterable<GHRepository> github_repo_extract = ghUser.listRepositories();

        List<GHRepository> github_repo_list = new LinkedList<GHRepository>(github_repo_extract.toList());

        Collections.sort(github_repo_list,new Comparator<GHRepository>(){
            @Override
            public int compare(GHRepository ghRepository, GHRepository t1) {
                return Integer.compare(ghRepository.getForksCount(),t1.getForksCount())*-1;
            }
        });



        try {
            for (int repo = 0; repo < repos; repo++) {
                try {
                    GHRepository github_repo = github_repo_list.get(repo);
                    try {
                        ans.append(fixString("\nRepo Name")+fixString("Forks Count")+"\n");
                        ans.append(fixString(github_repo.getFullName())+fixString(github_repo.getForksCount()+"")+"\n");
                        ans.append("\n");
                    }
                    catch (Exception e){
                    }
                    PagedIterable<GHCommit> commits = github_repo.listCommits();

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
                        catch (Exception e){
                        }
                    }

                    String[][] commit_store = new String[commits_count.size()][2];
                    int ind = 0;
                    for (Map.Entry<String, Integer> entry : commits_count.entrySet()) {
                        commit_store[ind][0] = entry.getKey();
                        commit_store[ind][1] = String.valueOf(entry.getValue());
                        ind ++;
                    }

                    Arrays.sort(commit_store, new Comparator<String[]>() {
                        @Override
                        public int compare(String[] t1, String[] t2) {
                            int val1 = Integer.parseInt(t1[1]);
                            int val2 = Integer.parseInt(t2[1]);
                            return Integer.compare(val1, val2)*-1;
                        }
                    });

                    ans.append(fixString("Top Committers")+fixString("No of Commits")+"\n");
                    for (int commity = 0; commity < commities; commity++) {

                        ans.append(fixString(commit_store[commity][0]) + fixString(commit_store[commity][1])+"\n");
                    }
                    ans.append("\n\n");

                } catch (Exception e) {
                }
            }
        }

        catch (Exception e){
        }
//        System.out.println(ans);
    }
    String getResult(){
        return ans.toString();
    }

    public static String fixString(String s){
        int len = 35;
        for(int i=s.length();i<=len;i++)
            s += " ";
        return s;
    }
}
