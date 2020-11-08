

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.*;

public class Main {


    private String org_name;
    private int repo_num,commit_num;

    /*
    This constructor initializes organization name ,repo ,commit to be found
    when the data is entered in the GUI field as provided.
     */
    Main(String name,int repo,int commit){
        org_name = name;
        repo_num = repo;
        commit_num = commit;
    }

    Main(){

    }

    public static void main(String[] args) throws IOException {

        String org = "";
        int repo= 0 , commit = 0;

        /*
            Here calling the DesignGUI class in which we can perform GUI task.
            And then running the java frame continuously so that we can perform
            as many as query as required.
            The frame will be closed when we click on exit button.
         */

        Runnable runnable = new Runnable(){
            public void run(){
                JFrame frame = new DesignGUI();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        };
        EventQueue.invokeLater(runnable);

    }



    public String initPerformTask() throws IOException{

        /*
        This function calls the PerformGithubTask class in which all task
        for fetching the data and analyzing it is performed.

         @return String
            will return the fetched answer to be displayed on GUI.
         */
        PerformGithubTask performGithubTask = new PerformGithubTask(org_name,repo_num,commit_num);
        performGithubTask.PerformTask();
        String result = performGithubTask.getResult();
        return result;

    }

}
