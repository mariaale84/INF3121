/**mariaale Oblig5 spring2013 */
/**main method is in class Oblig5 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.util.Scanner;
import java.io.IOException;
import java.io.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
/**
 * Tegner ut et Sudoku-brett.
 * @author Christian Tryti
 * @author Stein Gjessing
 */




abstract  class Rute {
    SudokuBeholder sudokubeholder;
    int value;
    Rute order;
    Rad rad;
    Kolonne kol;
    Box boks;
    Brett brett;
    int numSol;


    public   Rute(int value){
	this.value = value;
    }


    public boolean containsKol(int num){
	boolean fast = false ;
	for(int i = 0; i < kol.value.length; i++){
	    if(kol.value[i].value == num){
		fast = true;
		break;
	    }
	}
	return fast;
    }
    public boolean containsBox(int num){
	boolean fast = false ;
	for(int i = 0 ; i < boks.value.length; i++){
	    if(boks.value[i].value == num){
		fast = true;
		break;
	    }
	}
	return fast;
    }
    public void updateRaw(int u){
	for(int i = 0; i < rad.value.length; i++){


	}
    }

 public void pullNext(Rute rute) {
	this.order = rute;
    }

    abstract public void fillInnRemainingOfBoard( );
    public boolean containsRad(int num){
	boolean fast = false ;
	for(int i = 0; i < rad.value.length; i++){
	    if(rad.value[i].value == num){
		fast = true ;
		break;
	    }
	}
	return fast;
    }

    public void printBox(){
	System.out.println(" possible values are printed  : ");
	for(int i = 0; i < boks.value.length; i++){
	    System.out.print(boks.value[i].value + " / ");
	}
	System.out.println();
    }
    public void setValue(int val){
	value = val;
    }

    public String[][]  getStrArr(int rad,int kol,Rute[][] arr ){
	String[][] newArr = new String[rad][kol];
	for(int i = 0; i < rad; i++){
	    for(int j = 0; j < kol; j++){
		if(arr[i][j].value == 10){
		    newArr[i][j] = "A";
		}else if (arr[i][j].value == 11)
		    {
			newArr[i][j] = "B";
		    }
		else if (arr[i][j].value == 12)
		    {
			newArr[i][j] = "C";
		    }
		else if (arr[i][j].value == 13)
		    {
			newArr[i][j] = "D";
		    }
		else if (arr[i][j].value == 14)
		    {
			newArr[i][j] = "E";
		    }
		else if (arr[i][j].value == 15)
		    {
			newArr[i][j] = "F";
		    }
		else if (arr[i][j].value == 16)
		    {
			newArr[i][j] = "G";
		    }
		else {
		    newArr[i][j] = String.valueOf(arr[i][j].value);
		}
	    }
	}
	return newArr;
    }

}


class Brett{
    SudokuBeholder beh;
    SudokuGUI gui;
    String fileName;
    Rute[][] ruter;
    int number;
    int nBoks;
    int nRads;
    int nKols;


    public Brett(String filename){

	beh = new SudokuBeholder();


	try{
	    Scanner scan = new Scanner(new File(filename));
	    System.out.println("\n  ***Start*** \n ");
	    String antBoks = null ;
	    String antRad = null ;
	    String antKolonne = null ;
	    String rad = null ;
	    this.fileName = filename;

	    /** reading from file (number of boxes in sudoku */
	    antBoks = scan.nextLine();

	    /** converting to int */

	    int konvert = Integer.parseInt(antBoks);
	    nBoks = konvert;
	    /** number of ruters in sudoku */

	    int square = konvert*konvert;
	    Rute[][] sudoku = new Rute [konvert][konvert];


	    antRad = scan.nextLine();

	    int numberRads = Integer.parseInt(antRad);
	    nRads = numberRads;



	    antKolonne = scan.nextLine();

	    int numberKols = Integer.parseInt(antKolonne);
	    nKols = numberKols;




	    /**  is reading rute from file and printing them */

	    char[][] rd = new char[konvert][konvert];
	    for (int i = 0; i < konvert; i ++){
		String str = scan.nextLine();
		for (int j = 0; j < konvert; j++){
		    char c = str.charAt(j);
		    rd[i][j] = c;

		}
	    }


	    int[][] array = new int [konvert][konvert];



	    konvCharTilInt( konvert, array, rd);

	    /** todimensjonal array */
	    Rute[][]table = new Rute[konvert][konvert];

	    for (int i = 0; i < konvert; i++){
		for (int j = 0; j < konvert; j++){
		    int temp = array[i][j];

		    if (temp == -1 ){
			Plus pl = new Plus(0);
			pl.brett = this;
			pl.numRad = i;
			pl.numKol = j;

			table[i][j] =(Rute) pl;

		    } else {

			Adv d = new Adv(temp);
			d.brett = this;
			table[i][j] =(Rute) d;

		    }
		}
	    }

	    /** endimensional array av todimensjonal array */

	    Rute[] ruterOne = new Rute [square];
	    ruterOneArr(square,konvert,ruterOne,table);


	    Rute[] pek = new Rute[square];
	    ruterOneArrNext(square,pek,ruterOne);


	    setNextPek(square,ruterOne,pek);


	    /** gathering 2D sudoku */

	    gatherRuter(square,konvert,sudoku,ruterOne);

	    Rad[] ruteRad = new Rad[konvert];
	    radArr(konvert,ruteRad,sudoku);

	    Kolonne[] ruteKol = new Kolonne[konvert];
	    kolArr(konvert,ruteKol,sudoku);
	    int horisontalAnt = konvert/numberKols;
	    int verticalAnt = konvert/numberRads;

	    /** array with elements in boxes */

	    Box[] ruteBoks = new Box[konvert];
	    boksArr(konvert,verticalAnt, horisontalAnt,numberRads,numberKols,ruteBoks,sudoku);



	    fillUpSudoku(konvert,verticalAnt,horisontalAnt,numberRads, numberKols,ruteRad,ruteKol,ruteBoks,sudoku);


	    ruter = sudoku;

	    ruter[0][0].fillInnRemainingOfBoard();

	    gui = new SudokuGUI(konvert,numberRads, numberKols,fileName);
	    gui.losningsBrett = this;


	    scan.close();
	}catch(FileNotFoundException e){System.out.println(" ***FAILURE*** ");

	    e.printStackTrace();
}


    }

    /** Methods */

    void printSudoku(){
	for (int i = 0; i < ruter.length ; i ++){
	    for (int j = 0; j < ruter[0].length; j++){
		System.out.print(ruter[i][j].value+ " ");
	    }
	    System.out.println();
	}
    }

    /** Rute[][]*/
    public Rute[][]  makeDubl(Rute[][] sud){
	Rute[][] dublikat = new Rute[sud.length][sud[0].length];
	for(int i = 0; i < sud.length ; i++){
	    for(int j = 0; j < sud[0].length ; j++){
		dublikat[i][j] = sud[i][j];
	    }
	}
	System.out.println();
	return dublikat;
    }

    /** print value of sudoku element and value to its next peker  */
    public void printSudokuN(int konvert,Rute[][] sudoku){
	System.out.println ("  print table   ");
	for (int i = 0; i < konvert; i ++){

	    for (int j = 0; j < konvert; j++){
		if(sudoku[i][j].order != null){
		    System.out.print(sudoku[i][j].value+"("+sudoku[i][j].order.value+") ");}
		else {
		    System.out.print(sudoku[i][j].value+"*");
		}
	    }
	    System.out.println();
	}
	System.out.println("");
    }
    /** converts from char into int  */
    public void konvCharTilInt(int konvert,int[][] array,char[][] rd){
	for (int i = 0; i < konvert; i ++){
	    for (int j = 0; j < konvert; j++){

		array [i][j] = Character.digit(rd[i][j],16);

	    }
	}
    }

    /**  onedimensjonal array */
    public void   ruterOneArr(int square,int konvert, Rute[] ruterOne, Rute[][] table){

	int q = 0;
	while (q < square){
	    for (int i = 0; i < konvert; i ++){
		for (int j = 0; j < konvert; j++){
		    ruterOne [q] = table[i][j];
		    q++;
		}
	    }
	}
    }

   /** raw array*/
    public void  radArr(int konvert, Rad[] ruteRad,Rute[][] sudoku){

	for (int i = 0; i < konvert; i ++){
	    Rute[] temp = new Rute[konvert];
	    for (int j = 0; j < konvert; j++){

		temp[j] = sudoku[i][j];
	    }
	    ruteRad [i] = new Rad(temp,konvert);

	}


    }


    /** column array*/
    public void kolArr(int konvert,Kolonne[]ruteKol,Rute[][] sudoku){


	for (int j = 0; j < konvert; j ++){
	    Rute[] temp = new Rute[konvert];
	    for (int i = 0; i < konvert; i++){

		temp[i] = sudoku[i][j] ;
	    }
	    ruteKol[j] = new Kolonne(temp,konvert);

	}
    }


    /** Box array*/
    public void  boksArr(int konvert, int vertikalAnt, int horisontalAnt, int numberRads ,int numberKols, Box [] ruteBoks, Rute[][] sudoku){


	int p = 0;
	while(p < konvert){
	    int m = 0;
	    while(m < vertikalAnt){
		int k = 0;
		while (k < horisontalAnt)
		    {
			Rute[] temp = new Rute[numberKols*numberRads];
			int l = 0;
			while( l < numberKols*numberRads){
			    for (int i = 0+numberRads*m; i < numberRads+numberRads*m; i++){

				for (int j = 0+numberKols*k; j < numberKols+numberKols*k; j++){

				    temp[l] = sudoku[i][j];
				    l++ ;
				}

			    }

			}

			Box b = new Box(temp,numberKols*numberRads);

			ruteBoks[p] = b ;


			p++;
			k++;
		    }
		m++;
	    }

	}
    }
    void print(int konvert,Rute[][] sud){
	for (int i = 0;i < konvert; i++){
	    for (int j = 0 ;j < konvert; j++){
		System.out.print(sud[i][j].value +"   ");
	    }
	    System.out.println();
	}
    }

    void printInt(int konvert,int[][] sud){
	for (int i = 0; i < konvert; i ++){
	    for (int j = 0; j < konvert; j++){
		System.out.print(sud[i][j]+"   ");
	    }
	    System.out.println();
	}
    }

    void printChar(int konvert,char[][] sud){
	for (int i = 0;i < konvert; i ++){
	    for (int j = 0 ;j < konvert; j++){
		System.out.print(sud[i][j]+"   ");
	    }
	    System.out.println();
	}
    }





    public void   ruterOneArrNext(int square,Rute[] pek,Rute[] ruterOne){

	for (int i = 0; i < square-1; i++){
	    pek[i] = ruterOne[i+1];
	}
    }


    public void setNextPek(int square, Rute[] ruterOne, Rute[] pek){


	for (int i = 0; i < square; i++){
	    ruterOne[i].pullNext(pek[i]) ;
	}
    }


     /** set int value into Rute*/
    public void settValue(int numRad,int numKol,int tallet){
	ruter[numRad][numKol].value = tallet;
    }


    /** array (RUTE) is gathered*/

    public void gatherRuter(int square,int konvert,Rute[][] sudoku,Rute[] ruterOne){

	int t = 0;
	while (t < square){
	    for (int i = 0; i < konvert; i ++){
		for (int j = 0 ;j < konvert; j++){


		    sudoku[i][j] = ruterOne[t];
		    t++ ;
		}
	    }
	}
    }

  public void  printBoxElem(int radd,int koll, Rute[][] sudoku){
	System.out.println("\n  values are printed : "+radd+"/"+koll+"\n");

	for(int i = 0 ; i < sudoku[0].length ; i++){
	    System.out.print(sudoku[radd][koll].boks.value[i].value+"  / ");
	}
	System.out.println();
    }



    public void  printKolElem(int radd,int koll, Rute[][] sudoku){
	System.out.println("\n  column : "+radd+"/"+koll+"\n");

	for(int i = 0 ; i < sudoku[0].length ; i++){
	    System.out.print(sudoku[radd][koll].kol.value[i].value+"  / ");
	}
	System.out.println();
    }



    public void  printRawElem(int radd,int koll, Rute[][] sudoku){
	System.out.println("\n  raw : "+radd+"/"+koll+"\n");

	for(int i = 0 ; i < sudoku[0].length ; i++){
	    System.out.print(sudoku[radd][koll].rad.value[i].value+"  / ");
	}
	System.out.println();
    }


    /** raw, column, box are implemented in sudoku*/
    public void fillUpSudoku(int konvert, int vertikalAnt,int horisontalAnt,int numberRads, int numberKols, Rad[] ruteRad, Kolonne[] ruteKol, Box[] ruteBoks,Rute[][] sudoku){


	for (int i = 0; i < konvert; i ++){
	    for (int j = 0 ;j < konvert; j++){
		sudoku[i][j].rad = ruteRad[i];
		sudoku[i][j].kol = ruteKol[j];
	    }
	}


	int r = 0;
	while(r < ruteBoks.length){

	    for (int m = 0; m < vertikalAnt;m++){

		for (int k = 0; k < horisontalAnt; k++){

		    for (int i = numberRads*m; i < numberRads+numberRads*m; i ++){

			for (int j = numberKols*k; j < numberKols+numberKols*k; j++){

			    sudoku[i][j].boks = ruteBoks[r];
			}
		    }
		    r++;
		}
	    }

	}
    }
}


class Box extends RawOver{
    Box(Rute[] value, int size){
	super(value,size);
    }

}

class Rad extends RawOver{

    Rad(Rute[] value,int length){
	super(value,length);
    }


}
abstract class RawOver{
    Rute[] value;
    int size;

    RawOver(Rute value[],int size){
	this.value = value;
	this.size = size;
    }

}

class Kolonne extends RawOver{
    Kolonne(Rute[] value,int length){
	super(value,length);
    }

}

class Adv extends Rute {

    public  Adv(int  value){
	super( value) ;
    }
    public  void fillInnRemainingOfBoard(){

	if(order != null ){

	    order.fillInnRemainingOfBoard( );
	} else {
	    numSol++;

	    if(numSol <= 100){
		brett.number = numSol;
		brett.beh.insert( getStrArr(brett.nBoks,brett.nBoks,brett.ruter));
	    } else{
		return;
	    }
	}

    }
}

class Oblig5{
    public static void main(String[]args){


	/** object Brett*/

	//	Brett brett = new Brett("brett.9.3.3.txt");
  	Brett brett = new Brett("brett.6.2.txt");
	brett.beh.writeToFile("solution.txt");




    }
}

class Plus extends Rute {


    int numRad;
    int numKol;
    public  Plus (int value){
	super (value);
    }
    public  void fillInnRemainingOfBoard(){

	int sizeRad = rad.value.length;

     	for(int i = 1 ; i < sizeRad+1 ; i++){
	    if(containsRad(i)== false && containsBox(i) == false  && containsKol(i) == false   ){

		value = i;

		if(order != null   ){

		    order.fillInnRemainingOfBoard();

		} else {
		    numSol++;
		    if(numSol <= 100){
			brett.number = numSol;


			brett.beh.insert( getStrArr(brett.nBoks,brett.nBoks,brett.ruter));
		    } else {
			return;
		    }


		}



		setValue(0);


	    }
	    else {

	    }


	}



    }
    public void printRuteNumber(){
	System.out.print("   Rute number :   "+numRad+"/"+numKol+"     ");
    }
}



class SudokuBeholder {

    Rute ruteBeh;
    ArrayList<String[][]> sudoku = new ArrayList<String[][]>();

    SudokuBeholder(){



    }
    void insert(String[][] arrLosn){
	sudoku.add(arrLosn);

    }



    public String[][] get(int i){

	return sudoku.get(i);
    }



    public int getSolutionCount(){
	return sudoku.size();
    }



    public void writeToFile (String file){
	try {
	    PrintWriter printSkrivern = new PrintWriter(new FileWriter(file));
	    for( int i = 0; i < getSolutionCount(); i++){
		String[][] res = sudoku.get(i);
		printSkrivern.print(i+1+":");

		for(int j = 0; j < res.length; j++){
		    for(int k = 0; k < res[0].length; k++){
			printSkrivern.print(res[j][k]);
		    }
		    printSkrivern.print("//");
		}
		printSkrivern.println();
	    }
	    printSkrivern.close();
	}
	catch (IOException e ){
	    System.out.println(" FAILURE ");
	}
    }

}
