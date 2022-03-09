import java.util.Random;
import java.util.Scanner;
public class MineSweeper {
    int row;
    int column;
    MineSweeper(int row, int column){
        this.row = row;
        this.column = column;
    }
    boolean mineCheck(int[][] mines, int randR, int randC){
        return mines[randR][randC] != 1;
    }
    boolean pushCheck(int[][] mines, int rowG, int columnG){
        if (mines[rowG][columnG] == 2)
            return true;
        else if (rowG >= this.row || columnG >= this.column)
            return true;
        return false;
    }
    boolean finishCheck(int[][] mines){
        for (int i = 0; i < mines.length; i++)
            for (int j = 0; j < mines[i].length; j++)
                if (mines[i][j] == 0)
                    return true;
        return false;
    }

    void run() {
        // Ready to appearance of field
        char[][] field = new char[row][column];

        for (int i = 0; i < field.length; i++)
            for (int j = 0; j < field[i].length; j++)
                field[i][j] = '-';

        // Ready to mine pieces and places
        int mine = Math.round(this.row * this.column / 4);
        int[][] mines = new int[this.row][this.column];
        while (mine != 0) {
            int randR = new Random().nextInt(field[0].length - 1);
            int randC = new Random().nextInt(field.length - 1);
            if (mineCheck(mines, randR, randC))
                mines[randR][randC] = 1;
            else mine++;
            mine--;
        }

        // I showed appearance of field
        Scanner scan = new Scanner(System.in);
        for (char[] i : field) {
            for (char j : i)
                System.out.print(j + " ");
            System.out.println();
        }
        // The program will check that push to mine. If don't pushed to mine, the program will show mine pieces of environment.
        boolean isBoom = false;
        while(!isBoom && finishCheck(mines)) {
            System.out.print("Satırı giriniz: ");
            int rowG = scan.nextInt();
            System.out.print("Sütunu giriniz: ");
            int columnG = scan.nextInt();
            if (pushCheck(mines, rowG, columnG)) {
                System.out.println("Yanlış değer girdiniz, tekrar deneyiniz!");
                continue;
            }
            if (mines[rowG][columnG] == 1) {
                isBoom = true;
                break;
            } else mines[rowG][columnG] = 2;

            mineField(field, rowG, columnG);
            for (char[] i : field) {
                for (char j : i)
                    System.out.print(j);
                System.out.println();
            }
        }
        if (isBoom)
            System.out.println("Oyunu kaybettiniz. Yeniden bekleriz!");
        else System.out.println("Oyunu kazandınız. Tebrikler!");
    }
    char [][] mineField(char[][] field, int rowG, int columnG){
        int minePiece = 0;
        // Number of entered should be checked boundary of array
        if (rowG != 0) {
            if(columnG != (this.column- 1))
                if (field[rowG - 1][columnG + 1] == 1)
                    minePiece++;
            if (field[rowG-1][columnG] == 1)
                minePiece++;
            if (field[rowG-1][columnG-1] == 1)
                minePiece++;
        }
        if (columnG != 0) {
            if (rowG != (this.row- 1))
                if (field[rowG + 1][columnG - 1] == 1)
                    minePiece++;
            if (field[rowG][columnG-1] == 1) {
                minePiece++;
            }
        }
        if (rowG != (this.row- 1))
            if (field[rowG+1][columnG] == 1)
                minePiece++;
        if(columnG != (this.column - 1))
            if (field[rowG][columnG+1] == 1)
                minePiece++;
        if (rowG != (this.row- 1) && columnG != (this.column- 1))
            if (field[rowG+1][columnG+1] == 1)
                minePiece++;

        char ch = (char)(48+minePiece);
        field[rowG][columnG] = ch;
        return field;
    }
}
