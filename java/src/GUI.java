import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.text.DecimalFormat;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class GUI
        extends JFrame
        implements ActionListener
{
    DecimalFormat form = new DecimalFormat("00.##");
    private final ButtonGroup choices = new ButtonGroup();
    public int num;
    public int qnumb = 0;
    public int right = 0;
    public int wrong = 0;
    public int questions = 0;
    public String[][] ques;
    public String end = ".png";
    private JPanel contentPane;
    JRadioButton optiona;
    JRadioButton optionb;
    JRadioButton optionc;
    JRadioButton optiond;
    JRadioButton optione;
    JButton Done;
    JButton Next;
    JButton Jump;
    JTextPane Question;
    JTextPane Outcome;
    JTextPane score;
    JTextPane eof;
    JTextPane str;
    JMenu file;
    JMenu chopen;
    JMenuBar menuBar;
    JMenuItem ch1;
    JMenuItem ch2;
    JMenuItem ch3;
    JMenuItem ch4;
    JMenuItem ch5;
    JMenuItem ch6;
    JMenuItem ch7;
    JMenuItem ch8;
    JMenuItem ch9;
    JMenuItem ch10;
    JMenuItem ch11;
    double avg;
    double r;
    double q;
    double perfect = 100.0D;
    String chois;
    String outcome;
    Reader R = new Reader();
    JTextPane quesn;
    JTextField jumt;
    JLabel of;
    JMenuItem About;
    URL readf;

    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    GUI frame = new GUI();
                    //frame.setVisible(true);
                    frame.hidestr();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    public void actionPerformed(ActionEvent e)
    {
        if ((e.getActionCommand().equals("A")) ||
                (e.getActionCommand().equals("B")) ||
                (e.getActionCommand().equals("C")) ||
                (e.getActionCommand().equals("D")) ||
                (e.getActionCommand().equals("E")))
        {
            this.Done.setVisible(true);
            this.chois = e.getActionCommand();
        }
        else if (e.getActionCommand().equals("Done"))
        {
            this.optiona.setEnabled(false);
            this.optionb.setEnabled(false);
            this.optionc.setEnabled(false);
            this.optiond.setEnabled(false);
            this.optione.setEnabled(false);
            this.Done.setVisible(false);
            this.Next.setVisible(true);
            if (!this.chois.equals(this.ques[this.qnumb][0]))
            {
                this.outcome =
                        ("That is wrong, the correct answer was: " + this.ques[this.qnumb][0].toUpperCase());
                scorer(0, 1, 1);
                wronger();
                righter();
            }
            else if (this.chois.equals(this.ques[this.qnumb][0]))
            {
                this.outcome = "Correct!";
                scorer(1, 0, 1);
                righter();
            }
            this.Outcome.setText(this.outcome);
            this.score.setText("You got " + this.right + " right" + " out of " +
                    this.questions + "\n");
            this.qnumb += 1;
        }
        else if (e.getActionCommand().equals("Next"))
        {
            if (this.qnumb >= Reader.getnum(this.readf))
            {
                hide();
                this.eof.setText("There are no more question left for this chapter, pick another one or quit while you're ahead.\nYou got " + this.right + " questions right" + " out of " + this.questions + "\n");
                this.eof.setVisible(true);
            }
            else if (this.qnumb < Reader.getnum(this.readf))
            {
                nexter();
                this.Next.setVisible(false);
            }
            nuller();
        }
        else if ((e.getActionCommand().equals("U1-1e.txt")) ||
                (e.getActionCommand().equals("U1-2e.txt")) ||
                (e.getActionCommand().equals("U2-1e.txt")) ||
                (e.getActionCommand().equals("U2-2e.txt")) ||
                (e.getActionCommand().equals("U3-1e.txt")) ||
                (e.getActionCommand().equals("U3-2e.txt")) ||
                (e.getActionCommand().equals("U4-1e.txt")) ||
                (e.getActionCommand().equals("U4-2e.txt")) ||
                (e.getActionCommand().equals("U4-3e.txt")) ||
                (e.getActionCommand().equals("U5-1e.txt")) ||
                (e.getActionCommand().equals("U5-2e.txt")))
        {
            this.readf = GUI.class.getResource(e.getActionCommand());
            initer();
            unhide();
            Reader.read(this.readf, this.ques);
            this.qnumb = 0;
            this.optiona.setText(this.ques[this.qnumb][1]);
            this.optionb.setText(this.ques[this.qnumb][2]);
            this.optionc.setText(this.ques[this.qnumb][3]);
            this.optiond.setText(this.ques[this.qnumb][4]);
            this.optione.setText(this.ques[this.qnumb][5]);
            this.Question.setText(this.qnumb + 1 + ") " + this.ques[this.qnumb][6]);
            if (this.ques[this.qnumb][5] != null)
            {
                this.optione.setVisible(true);
                this.optione.setEnabled(true);
            }
            else
            {
                this.optione.setVisible(false);
            }
            this.optiona.setEnabled(true);
            this.optionb.setEnabled(true);
            this.optionc.setEnabled(true);
            this.optiond.setEnabled(true);
            this.right = 0;
            this.wrong = 0;
            this.questions = 0;
            this.score.setText("");
            this.jumt.setText(Integer.toString(this.qnumb + 1));
            nuller();
            this.choices.clearSelection();
            this.Done.setVisible(false);
        }
        else if (e.getActionCommand().equals("Jump"))
        {
            try
            {
                if (Integer.parseInt(this.jumt.getText()) - 1 > Reader.getnum(this.readf))
                {
                    JOptionPane.showMessageDialog(null, "That number is too big! Pick a number smaller than " + Reader.getnum(this.readf) + ".");
                }
                else if (Integer.parseInt(this.jumt.getText()) - 1 <= Reader.getnum(this.readf))
                {
                    this.qnumb = (Integer.parseInt(this.jumt.getText()) - 1);
                    this.right = 0;
                    this.wrong = 0;
                    this.questions = 0;
                    this.score.setText("");
                    nexter();
                    nuller();
                    this.Next.setVisible(false);
                }
            }
            catch (Exception f)
            {
                JOptionPane.showMessageDialog(null, "Do NOT input anything other than numbers!");
            }
        }
        else if (e.getActionCommand().equals("About"))
        {
            JOptionPane.showMessageDialog(null, "Version 1.0.0 of the multiple choice program\nIf you have any problems with the program please email:\ncentprogram@gmail.com");
        }
    }

    public void wronger()
    {
        if (this.chois.equals("A")) {
            this.optiona.setBackground(Color.RED);
        } else if (this.chois.equals("B")) {
            this.optionb.setBackground(Color.RED);
        } else if (this.chois.equals("C")) {
            this.optionc.setBackground(Color.RED);
        } else if (this.chois.equals("D")) {
            this.optiond.setBackground(Color.RED);
        } else if (this.chois.equals("E")) {
            this.optione.setBackground(Color.RED);
        }
    }

    public void righter()
    {
        if (this.ques[this.qnumb][0].equals("A")) {
            this.optiona.setBackground(Color.GREEN);
        } else if (this.ques[this.qnumb][0].equals("B")) {
            this.optionb.setBackground(Color.GREEN);
        } else if (this.ques[this.qnumb][0].equals("C")) {
            this.optionc.setBackground(Color.GREEN);
        } else if (this.ques[this.qnumb][0].equals("D")) {
            this.optiond.setBackground(Color.GREEN);
        } else if (this.ques[this.qnumb][0].equals("E")) {
            this.optione.setBackground(Color.GREEN);
        }
    }

    public void nuller()
    {
        this.optiona.setBackground(null);
        this.optionb.setBackground(null);
        this.optionc.setBackground(null);
        this.optiond.setBackground(null);
        this.optione.setBackground(null);
    }

    public void scorer(int i, int j, int k)
    {
        this.right += i;
        this.wrong += j;
        this.questions += k;
    }

    public void nexter()
    {
        this.choices.clearSelection();
        this.optiona.setEnabled(true);
        this.optionb.setEnabled(true);
        this.optionc.setEnabled(true);
        this.optiona.setText(this.ques[this.qnumb][1]);
        this.optionb.setText(this.ques[this.qnumb][2]);
        this.optionc.setText(this.ques[this.qnumb][3]);
        if (this.ques[this.qnumb][6].endsWith(this.end))
        {
            this.Question.setText("");
            this.Question.insertIcon(new ImageIcon(GUI.class.getResource(this.ques[this.qnumb][6])));
        }
        else
        {
            this.Question.setText(this.qnumb + 1 + ") " + this.ques[this.qnumb][6]);
        }
        this.Outcome.setText("");
        if (this.ques[this.qnumb][5] != null)
        {
            this.optione.setVisible(true);
            this.optione.setEnabled(true);
            this.optione.setText(this.ques[this.qnumb][5]);
        }
        else if (this.ques[this.qnumb][5] == null)
        {
            this.optione.setVisible(false);
        }
        if (this.ques[this.qnumb][4] != null)
        {
            this.optiond.setVisible(true);
            this.optiond.setEnabled(true);
            this.optiond.setText(this.ques[this.qnumb][4]);
        }
        else if (this.ques[this.qnumb][4] == null)
        {
            this.optiond.setVisible(false);
        }
        this.str.setText("");
        this.jumt.setText(Integer.toString(this.qnumb + 1));
    }

    public String avgr(int i, int j)
    {
        this.r = i;
        this.q = j;
        this.avg = (this.r * 10.0D / this.q * 10.0D);
        return this.form.format(this.avg);
    }

    public void initer()
    {
        this.num = Reader.getnum(this.readf);
        this.ques = new String[this.num][7];
    }

    public void unhide()
    {
        this.optiona.setVisible(true);
        this.optionb.setVisible(true);
        this.optionc.setVisible(true);
        this.optiond.setVisible(true);
        this.Question.setVisible(true);
        this.Outcome.setVisible(true);
        this.Done.setVisible(true);
        this.score.setVisible(true);
        this.str.setVisible(false);
        this.Jump.setVisible(true);
        this.of.setVisible(true);
        this.jumt.setVisible(true);
        this.jumt.setText(Integer.toString(this.qnumb + 1));
        this.quesn.setVisible(true);
        this.quesn.setText(Integer.toString(Reader.getnum(this.readf)));
    }

    public void hide()
    {
        this.optiona.setVisible(false);
        this.optionb.setVisible(false);
        this.optionc.setVisible(false);
        this.optiond.setVisible(false);
        this.optione.setVisible(false);
        this.Question.setVisible(false);
        this.Outcome.setVisible(false);
        this.score.setVisible(false);
        this.Jump.setVisible(false);
        this.of.setVisible(false);
        this.jumt.setVisible(false);
        this.quesn.setVisible(false);
    }

    public void hidestr()
    {
        hide();
        this.str.setVisible(true);
        this.eof.setVisible(false);
    }

    public GUI()
    {
        setIconImage(Toolkit.getDefaultToolkit().getImage(GUI.class.getResource("icon.png")));
        setTitle("Grade 11 Chemistry Mutiple Choice Program");
        setDefaultCloseOperation(3);
        setBounds(100, 100, 800, 650);
        this.contentPane = new JPanel();
        this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(this.contentPane);
        this.contentPane.setLayout(null);

        this.menuBar = new JMenuBar();
        this.menuBar.setBounds(0, 0, 968, 25);
        this.file = new JMenu("File");
        this.menuBar.add(this.file);
        this.file.setFont(new Font("Tahoma", 0, 15));

        this.chopen = new JMenu("Open");
        this.file.add(this.chopen);
        this.chopen.setFont(new Font("Tahoma", 0, 15));

        this.ch1 = new JMenuItem("Unit 1 - Structure");
        this.ch1.setFont(new Font("Tahoma", 0, 15));
        this.ch1.setActionCommand("U1-1e.txt");
        this.ch1.addActionListener(this);
        this.chopen.add(this.ch1);

        this.ch2 = new JMenuItem("Unit 1 - Bonding");
        this.ch2.setFont(new Font("Tahoma", 0, 15));
        this.ch2.setActionCommand("U1-2e.txt");
        this.ch2.addActionListener(this);
        this.chopen.add(this.ch2);

        this.ch3 = new JMenuItem("Unit 2 - Naming Rxns");
        this.ch3.setFont(new Font("Tahoma", 0, 15));
        this.ch3.setActionCommand("U2-1e.txt");
        this.ch3.addActionListener(this);
        this.chopen.add(this.ch3);

        this.ch4 = new JMenuItem("Unit 2 - Organic Molecues");
        this.ch4.setFont(new Font("Tahoma", 0, 15));
        this.ch4.setActionCommand("U2-2e.txt");
        this.ch4.addActionListener(this);
        this.chopen.add(this.ch4);

        this.ch5 = new JMenuItem("Unit 3 - Moles");
        this.ch5.setFont(new Font("Tahoma", 0, 15));
        this.ch5.setActionCommand("U3-1e.txt");
        this.ch5.addActionListener(this);
        this.chopen.add(this.ch5);

        this.ch6 = new JMenuItem("Unit 3 - Mass Stoichiometry");
        this.ch6.setFont(new Font("Tahoma", 0, 15));
        this.ch6.setActionCommand("U3-2e.txt");
        this.ch6.addActionListener(this);
        this.chopen.add(this.ch6);

        this.ch7 = new JMenuItem("Unit 4 - Solutions");
        this.ch7.setFont(new Font("Tahoma", 0, 15));
        this.ch7.setActionCommand("U4-1e.txt");
        this.ch7.addActionListener(this);
        this.chopen.add(this.ch7);

        this.ch8 = new JMenuItem("Unit 4 - Solubility");
        this.ch8.setFont(new Font("Tahoma", 0, 15));
        this.ch8.setActionCommand("U4-2e.txt");
        this.ch8.addActionListener(this);
        this.chopen.add(this.ch8);

        this.ch9 = new JMenuItem("Unit 4 - Acids");
        this.ch9.setFont(new Font("Tahoma", 0, 15));
        this.ch9.setActionCommand("U4-3e.txt");
        this.ch9.addActionListener(this);
        this.chopen.add(this.ch9);

        this.ch10 = new JMenuItem("Unit 5 - Gas Laws");
        this.ch10.setFont(new Font("Tahoma", 0, 15));
        this.ch10.setActionCommand("U5-1e.txt");
        this.ch10.addActionListener(this);
        this.chopen.add(this.ch10);

        this.ch11 = new JMenuItem("Unit 5 - Gas Stoichiometry");
        this.ch11.setFont(new Font("Tahoma", 0, 15));
        this.ch11.setActionCommand("U5-2e.txt");
        this.ch11.addActionListener(this);
        this.chopen.add(this.ch11);

        this.About = new JMenuItem("About");
        this.file.add(this.About);
        this.About.setFont(new Font("Tahoma", 0, 15));
        this.About.addActionListener(this);
        this.About.setActionCommand("About");
        this.contentPane.add(this.menuBar);

        this.score = new JTextPane();
        this.score.setEditable(false);
        this.score.setBounds(14, 512, 764, 51);
        this.contentPane.add(this.score);

        this.quesn = new JTextPane();
        this.quesn.setEditable(false);
        this.quesn.setBounds(322, 579, 27, 22);
        this.contentPane.add(this.quesn);

        this.Question = new JTextPane();
        this.Question.setFont(new Font("Tahoma", 0, 15));
        this.Question.setBounds(10, 29, 760, 280);
        this.Question.setEditable(false);
        this.contentPane.add(this.Question);

        this.eof = new JTextPane();
        this.eof.setVisible(false);
        this.eof.setFont(new Font("Tahoma", 0, 15));
        this.eof.setEditable(false);
        this.eof.setBounds(10, 33, 760, 51);
        this.contentPane.add(this.eof);

        this.str = new JTextPane();
        this.str.setFont(new Font("Tahoma", 0, 15));
        this.str.setEditable(false);
        this.str.setText("Pick a chapter; any chapter will do since you need to know them all.");
        this.str.setBounds(10, 38, 764, 63);
        this.contentPane.add(this.str);

        this.of = new JLabel("of");
        this.of.setFont(new Font("Tahoma", 0, 13));
        this.of.setBounds(308, 580, 11, 16);
        this.contentPane.add(this.of);

        this.Next = new JButton("Next");
        this.Next.setBounds(10, 574, 89, 25);
        this.contentPane.add(this.Next);
        this.Next.setVisible(false);
        this.Next.setActionCommand("Next");
        this.Next.addActionListener(this);

        this.Done = new JButton("Done");
        this.Done.setBounds(10, 574, 89, 25);
        this.contentPane.add(this.Done);
        this.Done.setVisible(false);
        this.Done.setActionCommand("Done");
        this.Done.addActionListener(this);

        this.jumt = new JTextField();
        this.jumt.setFont(new Font("Tahoma", 0, 13));
        this.jumt.setHorizontalAlignment(0);
        this.jumt.setBounds(277, 579, 27, 22);
        this.contentPane.add(this.jumt);
        this.jumt.setColumns(10);

        this.optiona = new JRadioButton();
        this.optiona.setFont(new Font("Tahoma", 0, 13));
        this.optiona.setBounds(14, 326, 756, 23);
        this.optiona.setEnabled(false);
        this.choices.add(this.optiona);
        this.contentPane.add(this.optiona);
        this.optiona.setActionCommand("A");
        this.optiona.addActionListener(this);

        this.optionb = new JRadioButton();
        this.optionb.setFont(new Font("Tahoma", 0, 13));
        this.optionb.setBounds(14, 354, 756, 23);
        this.optionb.setEnabled(false);
        this.choices.add(this.optionb);
        this.contentPane.add(this.optionb);
        this.optionb.setActionCommand("B");
        this.optionb.addActionListener(this);

        this.optionc = new JRadioButton();
        this.optionc.setFont(new Font("Tahoma", 0, 13));
        this.optionc.setBounds(14, 382, 756, 23);
        this.optionc.setEnabled(false);
        this.choices.add(this.optionc);
        this.contentPane.add(this.optionc);
        this.optionc.setActionCommand("C");
        this.optionc.addActionListener(this);

        this.optiond = new JRadioButton();
        this.optiond.setFont(new Font("Tahoma", 0, 13));
        this.optiond.setBounds(14, 410, 756, 23);
        this.optiond.setEnabled(false);
        this.choices.add(this.optiond);
        this.contentPane.add(this.optiond);
        this.optiond.setActionCommand("D");
        this.optiond.addActionListener(this);

        this.optione = new JRadioButton();
        this.optione.setFont(new Font("Tahoma", 0, 13));
        this.optione.setBounds(14, 438, 756, 23);
        this.optione.setEnabled(false);
        this.choices.add(this.optione);
        this.contentPane.add(this.optione);
        this.optione.setActionCommand("E");
        this.optione.addActionListener(this);

        this.Outcome = new JTextPane();
        this.Outcome.setBounds(14, 495, 760, 23);
        this.Outcome.setEditable(false);
        this.contentPane.add(this.Outcome);

        this.Jump = new JButton("Jump");
        this.Jump.setBounds(361, 576, 97, 25);
        this.Jump.setActionCommand("Jump");
        this.Jump.addActionListener(this);
        this.contentPane.add(this.Jump);
        if (System.getProperty("os.name").equals("Windows XP"))
        {
            this.eof.setBackground(new Color(236, 233, 216));
            this.Outcome.setBackground(new Color(236, 233, 216));
            this.Question.setBackground(new Color(236, 233, 216));
            this.score.setBackground(new Color(236, 233, 216));
            this.str.setBackground(new Color(236, 233, 216));
        }
        else if (System.getProperty("os.name").equals("Windows 7"))
        {
            this.eof.setBackground(SystemColor.menu);
            this.Outcome.setBackground(SystemColor.menu);
            this.Question.setBackground(SystemColor.menu);
            this.score.setBackground(SystemColor.menu);
            this.str.setBackground(SystemColor.menu);
        }
        else if (System.getProperty("os.name").equals("Mac OS X"))
        {
            this.eof.setBackground(new Color(237, 237, 237));
            this.Outcome.setBackground(new Color(237, 237, 237));
            this.Question.setBackground(new Color(237, 237, 237));
            this.score.setBackground(new Color(237, 237, 237));
            this.str.setBackground(new Color(237, 237, 237));
        }

        this.readf = GUI.class.getResource("U2-2e.txt");
        initer();
        unhide();
        Reader.read(this.readf, this.ques);
        this.qnumb = 0;
        this.optiona.setText(this.ques[this.qnumb][1]);
        this.optionb.setText(this.ques[this.qnumb][2]);
        this.optionc.setText(this.ques[this.qnumb][3]);
        this.optiond.setText(this.ques[this.qnumb][4]);
        this.optione.setText(this.ques[this.qnumb][5]);
        this.Question.setText(this.qnumb + 1 + ") " + this.ques[this.qnumb][6]);
        String[] textFiles = {
                "U1-1e.txt", "U1-2e.txt", "U2-1e.txt", "U2-2e.txt", "U3-1e.txt", "U3-2e.txt",
                "U4-1e.txt", "U4-2e.txt", "U4-3e.txt", "U5-1e.txt", "U5-2e.txt"
        };
/*
        for(int i1 = 0; i1 < textFiles.length; i1++) {
            for(int i = 0; i < this.ques.length; i++) {
                //if(this.ques[i][6] == null || this.ques[i][this.ques[i][0].toCharArray()[0]-64].substring(4) == null){
                //break;
                //}
                try {
                    System.out.println("The correct answer to: \"" + this.ques[i][6] + "\" is: " + this.ques[i][this.ques[i][0].toCharArray()[0] - 64].substring(4));
                } catch (NullPointerException e) {
                    //e.printStackTrace();
                }
            }
            System.out.println("Finished with file: " + textFiles[i1] + " moving onto next file...");
        }
        */

        int count = 1;
        try{ for (String file : textFiles) {
            this.readf = GUI.class.getResource(file);
            System.out.println("File: " + this.readf + " and loc: " + file);
            initer();
            unhide();
            Reader.read(this.readf, this.ques);
            this.qnumb = 0;

            File logFile = new File("c" + count + ".txt");
            BufferedWriter writer = null;
            writer = new BufferedWriter(new FileWriter(logFile));
            System.out.println(logFile.getCanonicalPath());

            for (String question1[] : this.ques) {
                for (String question : question1) {
                    System.out.println(question);
                    if (question != null) {
                        writer.write(question + "\n");
                    } else {
                        writer.write("null" + "\n");
                    }
                }
                writer.write("---\n");
            }
            count++;

            writer.close();
        }} catch(Exception e){ e.printStackTrace(); }

        /*
        if (this.ques[this.qnumb][5] != null)
        {
            this.optione.setVisible(true);
            this.optione.setEnabled(true);
        }
        else
        {
            this.optione.setVisible(false);
        }
        this.optiona.setEnabled(true);
        this.optionb.setEnabled(true);
        this.optionc.setEnabled(true);
        this.optiond.setEnabled(true);
        this.right = 0;
        this.wrong = 0;
        this.questions = 0;
        this.score.setText("");
        this.jumt.setText(Integer.toString(this.qnumb + 1));
        nuller();
        this.choices.clearSelection();
        this.Done.setVisible(false);
        */


    }
}
