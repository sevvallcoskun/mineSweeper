package project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent; 
import java.awt.event.MouseListener;
import java.awt.event.ItemEvent;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;



import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.util.Random;
 
public class mineSweeper2 extends JFrame implements MouseListener,ActionListener
{	
	JFrame frame;
	private int c=9;
	private int r=9;	
	private int m=10;
	btn[][] board=new btn[9][9]; 
	JPanel panel = new JPanel();
	
	private JButton restartBtn;
	private JLabel statusBar;
	private JMenuBar mb;
	private JMenu difMenu;
	private JRadioButton easy;
	private JRadioButton medium;
	private JRadioButton hard;
	int row,col;
	int openB;
    Container grid = new Container();
  
	public void Menu() {
		mb=new JMenuBar();
		difMenu = new JMenu("Difficulty");
		statusBar = new JLabel("Easy");
        statusBar.setBorder(BorderFactory.createEtchedBorder());
        add(statusBar, BorderLayout.SOUTH);
       
		JRadioButtonMenuItem easy=new JRadioButtonMenuItem("Easy");
		easy.setSelected(true);
		difMenu.add(easy);
		easy.addItemListener((e) -> {
			if (e.getStateChange() == ItemEvent.SELECTED)
		    {
		    	r=9;
		    	c=9;
		    	m=10;
		    }
		});
		        
		JRadioButtonMenuItem medium=new JRadioButtonMenuItem("Medium");
		medium.setSelected(true);
		difMenu.add(medium);
		medium.addItemListener((e) -> {
			if (e.getStateChange() == ItemEvent.SELECTED)
		    {
				r=16;
		    	c=16;
		    	m=40;
		    }
		});
		
		JRadioButtonMenuItem hard=new JRadioButtonMenuItem("Hard");
		hard.setSelected(true);
		difMenu.add(hard);
		hard.addItemListener((e) -> {
			if (e.getStateChange() == ItemEvent.SELECTED)
		    {
				r=30;
		    	c=16;
		    	m=99;
		    }
		});
		r=9;
    	c=9;
    	m=10;
		ButtonGroup bg=new ButtonGroup();
		bg.add(easy);
		bg.add(medium);
		bg.add(hard);		
		panel.add(easy);
		panel.add(medium);
		panel.add(hard);
        mb.add(difMenu);
        easy.addActionListener(this);
        medium.addActionListener(this);
        hard.addActionListener(this);
        setJMenuBar(mb);	
 
	}

	public mineSweeper2() {
		openB=0;
		restartBtn=new JButton("restart");
		frame=new JFrame("Mine Sweeper");
		frame.setSize(500,500);	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout()); 	 
		panel.add(restartBtn);
		frame.add(panel,BorderLayout.PAGE_START);
		restartBtn.addActionListener(this);
		grid.setLayout(new GridLayout(9,9)); 
		for(int row=0;row<board.length;row++) {
			for(int col=0;col<board[0].length;col++) {				
				 board[row][col] =new btn(row,col); 
	             board[row][col].addActionListener(this); 	             
	             board[row][col].addMouseListener(this); 
	             grid.add(board[row][col]);	        
			}
		}
		frame.add(grid);
		createMine();
		mineCheck();
		frame.setVisible(true);
	}
			
	public void createMine() {
		int i=0;
		while(i<m) {
			int rRow=(int)(Math.random()*board.length);
			int rCol=(int)(Math.random()*board[0].length);		
		    while(board[rRow][rCol].isMine()) {
			    rRow=(int)(Math.random()*board.length);
			    rCol=(int)(Math.random()*board[0].length);
		} 
		    board[rRow][rCol].setMine(true);	
		    i++;
	}
}
			
	public void printAllMine() {
		for(int row=0;row<board.length;row++) {
			for(int col=0;col<board[0].length;col++) {
				if(board[row][col].isMine()) {
			//		board[row][col].setIcon(ImageIconReturn("redmine.png"));
					board[row][col].setIcon(ImageIconReturn("mine.png"));
				}
				else {
					board[row][col].setText(board[row][col].getCount() + "");
					board[row][col].setEnabled(false);
				}				
			}
		}		
	}
	
	public void printRedMine() {
		if(board[row][col].isMine()) {
			board[row][col].setIcon(ImageIconReturn("red.png"));
		}
	}
	
	private ImageIcon ImageIconReturn(String str)
    {
    	return new ImageIcon(mineSweeper2.class.getResource(str));
    }

public void mineCheck() {
	for (int r=0;r<board.length;r++) {
		for (int c=0;c<board[0].length;c++) {			
			int cs=0;
			//left
			if (r>0 && board[r-1][c].isMine())
				cs++;		
			//right
			if (r<board.length-1 && board[r+1][c].isMine())
				cs++;
			//up
			if (c>0 && board[r][c-1].isMine())
				cs++;
			//down
			if (c<board.length-1 && board[r][c+1].isMine())
				cs++;
			//top left
			if (r>0 && c>0 && board[r-1][c-1].isMine())
				cs++;
			//top right
			if (r<board.length -1 && c>0 && board[r+1][c-1].isMine())
				cs++;
			//bottom left
			if (r>0 && c<board.length-1 && board[r-1][c+1].isMine())
				cs++;
			//bottom right
			if (r<board.length -1 && c<board.length-1 && board[r+1][c+1].isMine())
				cs++;
			board[r][c].setCount(cs);
			
       }			
   }
}
 
public void openBtn(int r,int c) {	
	if(r<0 || c<0 || r>=board.length || c>=board[0].length|| board[r][c].getText().length() > 0
			|| board[r][c].isEnabled() == false)
		return;
	else if(board[r][c].getCount()!=0) 
	{
		board[r][c].setText(board[r][c].getCount() + "");	
		board[r][c].setEnabled(false);
		openB++;
	}
	else {	
		openB++;
		board[r][c].setEnabled(false);
		openBtn(r-1,c);
		openBtn(r+1,c);
		openBtn(r,c-1);
		openBtn(r,c+1);
	}	
	
} 

@Override
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	if(e.getSource()==restartBtn)
	{	
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[0].length; col++) 
			{
			  board[row][col].setMine(false);
			  board[row][col].setEnabled(true);
              board[row][col].setText("");
              board[row][col].setIcon(ImageIconReturn(""));
            }
        }        
		createMine();
		mineCheck();
	}
}
	
	
	class btn extends JButton{
		private int row,col,count;
		private boolean mine,flag;	
		public btn(int row, int col) {
			this.row = row;
			this.col = col;
			this.count = 0;
			this.mine = false;
			this.flag = false;
		}
	
		public void setMine(String string) {
			// TODO Auto-generated method stub
			
		}
		public int getRow() {
			return row;
		}
		public void setRow(int row) {
			this.row = row;
		}
		public int getCol() {
			return col;
		}
		public void setCol(int col) {
			this.col = col;
		}
		public int getCount() {
			return count;
		}
		public void setCount(int count) {
			this.count = count;
		}
		public boolean isMine() {
			return mine;
		}
		public void setMine(boolean mine) {
			this.mine = mine;
		}
		public boolean isFlag() {
			return flag;
		}
		public void setFlag(boolean flag) {
			this.flag = flag;
		}
		
		
	}
	 
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
		btn b=(btn) e.getComponent();
		if(e.getButton()==1) 
		{
			if(b.isMine()) {
				printAllMine();
			}
			else {
				openBtn(b.getRow(), b.getCol());
				if (openB == (board.length * board[0].length) -10) {
					JOptionPane.showInputDialog(frame, "congrats!",ImageIconReturn("happy.png"));
					printAllMine();
				}
			}
		}
		else if(e.getButton()==3)
		{
			if(!b.isFlag()) {
				b.setIcon(ImageIconReturn("flag1.png"));
				b.setFlag(true);
			}
			else {
				b.setIcon(null);
				b.setFlag(false);
			}
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub	
			 
		}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub		
	} 

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new mineSweeper2();
		
	}		
}