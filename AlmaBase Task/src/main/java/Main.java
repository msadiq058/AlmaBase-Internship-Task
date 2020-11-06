import org.kohsuke.github.*;

import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {

        GitHub github = new GitHubBuilder().withOAuthToken("").build();

        Scanner sc = new Scanner(System.in);
        String organization = "opensourcecode2020";
//        int n = sc.nextInt();
//        int m = sc.nextInt();
        int repos = 1, commities = 10;
        GHUser ghUser= github.getUser(organization);

        System.out.println(ghUser.getBio());

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
                        System.out.println(github_repo.getFullName());
                    }
                    catch (Exception e){
                        System.out.println("Full name error");
                    }
                    PagedIterable<GHCommit> commits = github_repo.listCommits();

//                    github_repo.l
                    HashMap<String, Integer> commits_count = new HashMap<>();
//                    try {
                    for (GHCommit commit : commits) {
                        try {
//                        System.out.println("Author "+commit.getAuthor().getName()+" Commiter "+commit.getCommitter().getName()+" Owner "+commit.getOwner().getName());
                            String user = "";
                            try {
                                user = commit.getAuthor().getName();
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
//                                System.out.println("Wrong commit");
                        }
//                            System.out.println(commits_count);
                    }
//                  }
//                  catch (Exception e){
//                      System.out.println("Wrong commit");
//                  }

                    System.out.println(commits_count+" "+"final");

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

                    for (int commity = 0; commity < commities; commity++) {
                        System.out.println(commit_store[commity][0] + " "+commit_store[commity][1]);
                    }
                    System.out.println('\n');
                } catch (Exception e) {
                    System.out.println("Exception found first"+e);
                }
            }
        }
        catch (Exception e){
            System.out.println("Exception found "+e);
        }
    }

}
