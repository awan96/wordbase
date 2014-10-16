import java.util.*;
import java.io.*;
import java.lang.*;

public class WordBase1_1 
{
	public static char[][] board = new char[15][12];
	public static int[][] point = new int[15][12]; 
	public static String[] dict = new String[172821];
	public static String team;
	public static int height;
	//public static String[] answer = new String[10000];
	//public static int[] score = new int[10000];
	//public static int[] target = new int[10000];
	//public static int count = 0;
	
	//public static PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("WordBase.out")));

	
	public static void main(String[] args) throws IOException
	{
		//create an out file to write answers in
		//public static PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("WordBase.out")));
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Orange or Blue team: ");
		team = sc.nextLine();
		System.out.print("Vertical Height (how far up/down it extends from your starting point): ");
		height = sc.nextInt();
		
		//input the dictionary
		BufferedReader d = new BufferedReader(new FileReader("fulllist.txt"));
		for (int i = 0; i < dict.length; i++)
		{
			dict[i] = d.readLine();
		}
		
		//input the gameboard into an array using WordBase.in
		BufferedReader f = new BufferedReader(new FileReader("WordBase.in"));
		for (int i = 0; i < 12; i++)
		{
			board[0][i] = '~';
			board[14][i] = '~';
		}
		for (int i = 0; i < 15; i++)
		{
			board[i][0] = '~';
			board[i][11] = '~';
		}
		for (int i = 1; i < 14; i++)
		{
			StringTokenizer st = new StringTokenizer(f.readLine());
			for (int j = 1; j < 11; j++)
			{
				board[i][j] = st.nextToken().charAt(0);
			}
		}
		
		//input the gameboard point status into an array using WordBase.in
		f.readLine();
		for (int i = 0; i < 12; i++)
		{
			point[0][i] = -1;
			point[14][i] = -1;
		}
		for (int i = 0; i < 15; i++)
		{
			point[i][0] = -1;
			point[i][11] = -1;
		}
		for (int i = 1; i < 14; i++)
		{
			StringTokenizer st = new StringTokenizer(f.readLine());
			for (int j = 1; j < 11; j++)
			{
				point[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		//start generating words and matching them with the dictionary
		for (int i = 1; i < 14; i++) //y cord
		{
			for (int j = 1; j < 11; j++) //x cord
			{
				if (point[i][j] == 0) //must be a tile you own
				{ 
					String word = "" + board[i][j]; //creates the word to be tested
					System.out.println("Coordinates: " + i + " " + j + ", " + word);
					int[][] point1 = new int[15][]; //clones a new array to change -1
					for (int k = 0; k < 15; k++)
					{
						point1[k] = point[k].clone();
					}
					point1[i][j] = -1;
					ArrayList<String> list = new ArrayList<String>(); //creates list of possible words
					for (int k = 0; k < 172820; k++)
					{
						if (dict[k].startsWith(word)) 
						{
							list.add(dict[k]);
						}
					}
					int y1 = i;
					test(i, j, word, point1, list, y1);
					System.out.println();
				}
			}
		}
		
		//out.close();
		//System.exit(0);
	}
	
	public static void test(int y, int x, String word, int[][] point1, ArrayList<String> list, int y1) throws IOException
	{
		
		for (int i = y - 1; i <= y + 1; i++) //surrounding 3, y
		{
			for (int j = x - 1; j <= x + 1; j++) //surrounding 3, x
			{
				if (point1[i][j] != -1) //cannot be a tile used before or out of bounds
				{
					String word1 = word + board[i][j]; //create the new word
					ArrayList<String> list1 = new ArrayList<String>(); //new blank list
					for (int k = 0; k < list.size(); k++) //now, for every word in the original list...
					{
						if (list.get(k).startsWith(word1)) //if it starts with the combo thus far..
						{
							if(team.equalsIgnoreCase("ORANGE") || team.equalsIgnoreCase("O")){     
 							if (list.get(k).equals(word1) && (i - y1) >= height) //and is also the whole word..
							{
								System.out.println(list.get(k) + " " + (i-y1) + " Pos: (" + i + ") ");
							}
							else
							{
								list1.add(list.get(k)); //if not, add it to the new list
							}
							}else{
							if (list.get(k).equals(word1) && (y1-i) >= height) //and is also the whole word..
							{
								System.out.println(list.get(k) + " " + (i-y1) + " Pos: (" + i + ") ");
							}
							else
							{
								list1.add(list.get(k)); //if not, add it to the new list
							}
							}
						}
					}
					if (list1.size() > 0) //if there are words in the new list
					{
						int[][] point2 = new int[15][]; //clones a new array to change -1
						for (int k = 0; k < 15; k++)
						{
							point2[k] = point1[k].clone();
						}
						point2[i][j] = -1;
						test(i, j, word1, point2, list1, y1);
					}
				}
			}
		}
	}
}
