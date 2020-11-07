
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class DesignGUI extends JFrame  {

    private String org_name ,ans;
    private int repo_numbers , committers_numbers;


    public DesignGUI(){
//        System.out.println("Hello");
        setSize(900,400);

        setTitle("GitHub Task");
//        System.out.println("Hello");
        JSplitPane splitPane = new JSplitPane();
        splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(300);

        final JTextArea textArea = new JTextArea(100, 45);
        JScrollPane scrollText = new JScrollPane(textArea);
        textArea.setEditable(false);

        splitPane.setRightComponent(scrollText);
        add(splitPane);

        JPanel panel = new JPanel(new GridLayout(0,1));

        splitPane.setLeftComponent(panel);

        JPanel namePanel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JPanel namePanel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JPanel namePanel3 = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JPanel submitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        addNameControlsString(namePanel1);
        addNameControlsRepo(namePanel2);
        addNameControlsCommitters(namePanel3);


        JButton submitButton = new JButton("Get Data");
        submitButton.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent evt) {
                if(SwingUtilities.isLeftMouseButton(evt)){

//                    Main.check = true;
//                    String fullStr = org_name+" "+String.valueOf(repo_numbers)+" "+String.valueOf(committers_numbers);
//                    String ans = org_name+repo_numbers+committers_numbers;
//                    textArea.append(ans);
//                    System.out.println("Hii");
                    PerformGithubTask perform = new PerformGithubTask(org_name,repo_numbers,committers_numbers);
                    try {
                        perform.PerformTask();
                    }
                    catch (Exception e){

                    }
                    String result = perform.getResult();
                    textArea.append(result);

                }
            }
        });

        submitPanel.add(submitButton);

        panel.add(namePanel1);
        panel.add(namePanel2);
        panel.add(namePanel3);
        panel.add(submitPanel);

    }

    private void addNameControlsString(JPanel namePanel){
        JLabel fName = new JLabel("Organization Name");
        namePanel.add(fName);

        final JTextField Name = new JTextField(15);
        Name.setBackground(Color.WHITE);

        Name.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char letter = e.getKeyChar();
                if (Name.getText().length() >= 15 )
                    e.consume();
//                else if(!Character.isLetter(letter)){
//                    e.consume();
//                }
            }
        });

        Name.addFocusListener(new FocusListener(){
            public void focusGained(FocusEvent fe){

            }

            public void focusLost(FocusEvent fe){
                org_name = Name.getText();
            }
        });

        namePanel.add(Name);
    }

    private void addNameControlsRepo(JPanel namePanel){
        String name = adjustText("Repo count");
        JLabel count = new JLabel(name);
        namePanel.add(count);

        final JTextField repo_count = new JTextField(9);
        repo_count.addFocusListener(new FocusListener(){

            public void focusLost(FocusEvent fe){
                repo_numbers = Integer.parseInt(repo_count.getText());
            }

            public void focusGained(FocusEvent fe){

            }
        });

        repo_count.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char letter = e.getKeyChar();
                if (repo_count.getText().length() >= 10 ){
                    e.consume();
                }
                else if(!Character.isDigit(letter)){
                    e.consume();
                }
            }
        });

        namePanel.add(repo_count);
    }

    private void addNameControlsCommitters(JPanel namePanel){
        String name = adjustText("Committers count");
        JLabel count = new JLabel(name);
        namePanel.add(count);

        final JTextField repo_count = new JTextField(9);
        repo_count.addFocusListener(new FocusListener(){

            public void focusLost(FocusEvent fe){
                committers_numbers = Integer.parseInt(repo_count.getText());
            }

            public void focusGained(FocusEvent fe){

            }
        });

        repo_count.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char letter = e.getKeyChar();
                if (repo_count.getText().length() >= 10 ){
                    e.consume();
                }
                else if(!Character.isDigit(letter)){
                    e.consume();
                }
            }
        });

        namePanel.add(repo_count);
    }

    private String adjustText(String name){
        String org = "Organization Name";
        int limit = org.length() - name.length();
        for(int i=0;i<=limit;i++){
            name = name +" ";
        }
        return name;

    }

    private void setValue(){

    }

    public void display(String result){
        ans = result;
    }


}