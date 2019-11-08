package cn.itcast.jcalculator;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
 
public class Calculator extends JFrame implements ActionListener {
  
	
	private class WindowCloser extends WindowAdapter {
       public void windowClosing(WindowEvent we) {
           System.exit(0);
       }
    }
 
    int i;
    // Strings for Digit & Operator buttons.
    private final String[] str = { "7", "8", "9", "/", "4", "5", "6", "*", "1",
           "2", "3", "-", ".", "0", "=", "+" };
    // Build buttons.
    JButton[] buttons = new JButton[str.length];
    // For cancel or reset.
    JButton reset = new JButton("CE");
    // Build the text field to show the result.
    boolean isFirstDigit = true;  

    JTextField display = new JTextField("0");
    
    String operate = "=";
    
    double num = 0.0;
    
    /**
     * Constructor without parameters.
     */
    public Calculator() {
       super("My Calulator");
       // Add a panel.
       //JPanel panel1 = new JPanel(new GridLayout(4, 4));
       JPanel panel1 = new JPanel();
       panel1.setLayout(new GridLayout(4,4));
       for (i = 0; i < str.length; i++) {
           buttons[i] = new JButton(str[i]);
           panel1.add(buttons[i]);
       }
       //JPanel panel2 = new JPanel(new BorderLayout());
       JPanel panel2 = new JPanel();
       panel2.setLayout(new BorderLayout());
       panel2.add("Center", display);
       panel2.add("East", reset);
       // JPanel panel3 = new Panel();
       getContentPane().setLayout(new BorderLayout());
       getContentPane().add("North", panel2);
       getContentPane().add("Center", panel1);
       // Add action listener for each digit & operator button.
       for (i = 0; i < str.length; i++)
           buttons[i].addActionListener(this);
       // Add listener for "reset" button.
       reset.addActionListener(this);
       // Add listener for "display" button.
       display.addActionListener(this);
       // The "close" button "X".
       addWindowListener(new WindowCloser());
       // Initialize the window size.
       setSize(800, 800);
       // Show the window.
       // show(); Using show() while JDK version is below 1.5.
       setVisible(true);
       // Fit the certain size.
       pack();
    }  
   
    public void actionPerformed(ActionEvent e) {
       Object target = e.getSource();
       String label = e.getActionCommand();
       if (target == reset)
           handleReset();
       else if ("0123456789.".indexOf(label) >= 0)
           handleNumber(label);
       else
           handleOperator(label);
    }
    
    void handleReset() {
    	
    	display.setText("0");
    	isFirstDigit = true;
    	
    }
    
    void handleNumber(String label) {
    	
    	String before = display.getText();
    	
    	//若是第一个数，则直接显示出来
    	if(isFirstDigit) {
    		display.setText(label);
    	}
    	//当输入点  且 数中不包含.时
    	else if(label.equals(".") && before.indexOf(".") == -1) {
    		display.setText(before+label);
    	}
    	//不满足 以上条件 且 不是输入的点时
    	else if(!label.equals(".")){
    		display.setText(before+label);
    	}
    	isFirstDigit = false;
    	
    }
    void handleOperator(String label) {
    	
    	//当前文本框显示的数保存到nowNum
    	String nowNum = display.getText(); 
    	
    	if(label.equals("=")) { //操作数为 = 时执行操作
    		if(operate.equals("+")) {
        		num += Double.valueOf(nowNum);
        	}else if(operate.equals("-")) {
        		num -= Double.valueOf(nowNum);
        	}else if(operate.equals("*")) {
        		num *= Double.valueOf(nowNum);
        	}else if(operate.equals("/")) {
        		if(nowNum.equals("0")) { 
        			num = 0;
        		}else {
        			num /= Double.valueOf(nowNum);
        		}
        	}
    	}else {   //操作数为+-*/时
    		num = Double.valueOf(nowNum);
    		operate = label;
    	}
    	String result;
    	if(num != (int) num)
    		result = String.format("%.6f", num); //如果数是小数 则保留到小数点后六位
    	else {
    		result = String.format("%.0f", num);
    	}
    	display.setText(result);
    	
    	if(label.equals("=")&&operate.equals("/")&&num==0) { // 当除数为0 特判
    		display.setText("除数不能为0");
    	}
    	
    	isFirstDigit = true;
    }
    public static void main(String[] args) {
       new Calculator();
    }
}