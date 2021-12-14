package Logic;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class Game {
    private int gid;
    private String name;
    private static int gameCnt = 1;
    private static Player whitePlayer;
    private static Player blackPlayer;
    private ArrayList<Step> stepList = new ArrayList<Step>(0);
    private static int[][] board = new int[8][8];
    //新加的
    private int whiteCnt;
    private int blackCnt;
    private static int blackFigure = -1;//代表黑手
    private static int whiteFigure = 1;//代表白手
    private int currentFigure = -1;//代表当前是黑手还是白手，黑手先下
    private int[] key=new int[8];
    int[] direction1 = new int[]{-1, 1, 0, 0, -1, -1, 1, 1};
    int[] direction2 = new int[]{0, 0, 1, -1, -1, 1, -1, 1};
    private boolean[][] canPut = new boolean[8][8];//每步完需要初始化
    private static ArrayList<boolean[][]> canPutList = new ArrayList<boolean[][]>();
    private static ArrayList<int[]> rowStopList = new ArrayList<int[]>();
    private static ArrayList<int[]> columnList = new ArrayList<int[]>();
    private static ArrayList<int[][]> boardList = new ArrayList<int[][]>();
    private int endMark = 0;
    private boolean gameOver = false;

    public Game(String name, Player whitePlayer, Player blackPlayer) {
        this.name = name;
        this.gid = gameCnt;
        gameCnt++;
        this.blackPlayer = blackPlayer;
        this.whitePlayer = whitePlayer;
        setBoard(board);
        this.key = new int[8];
        this.canPut = new boolean[8][8];
        this.canPutList = new ArrayList(0);
    }


    //get
    public int getGid() {
        return gid;
    }//该局游戏编号

    public String getName() {
        return name;
    }//该局游戏名称

    public void setName(String name) {
        this.name = name;
    }//set这句游戏名称

    public static int getGameCnt() {
        return gameCnt;
    }//游戏编号计数器

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public Player getBlackPlayer() {
        return blackPlayer;
    }

    public boolean getGameOver() {
        return this.gameOver;
    }

    public ArrayList<Step> getStepList() {
        return stepList;
    }//get步数数列

    public ArrayList getCanPutList() {
        return canPutList;
    }

    public int[] getKey() {
        return key;
    }

    public ArrayList getBoardList() {
        return boardList;
    }

    public int[][] getBoard() {
        int[][] board = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = this.board[i][j];
            }
        }
        return board;
    }//get棋盘

    public void setBoard(int[][] board) {
        for (int m = 0; m < 8; m++) {
            for (int n = 0; n < 8; n++) {
                this.board[m][n] = 0;
            }
        }
        this.board[3][3] = 1;
        this.board[4][4] = 1;
        this.board[3][4] = -1;
        this.board[4][3] = -1;
    }//初始化棋盘

    public int getBlackCnt() {
        blackCnt = 0;
        for (int m = 0; m < 8; m++) {
            for (int n = 0; n < 8; n++) {
                if (board[m][n] == -1) {
                    blackCnt++;
                }
            }
        }
        return blackCnt;
    }//直接扫一遍棋盘确定

    public int getWhiteCnt() {
        whiteCnt = 0;
        for (int m = 0; m < 8; m++) {
            for (int n = 0; n < 8; n++) {
                if (board[m][n] == 1) {
                    whiteCnt++;
                }
            }
        }
        return whiteCnt;
    }//直接扫一遍棋盘确定

    public int getBlackFigure() {
        return this.blackFigure;
    }

    public int getWhiteFigure() {
        return this.whiteFigure;
    }

    public int getCurrentFigure() {
        return this.currentFigure;
    }

    public static ArrayList<int[]> getColumnList() {
        return columnList;
    }

    public static ArrayList<int[]> getRowStopList() {
        return rowStopList;
    }

    public boolean[][] getCanPut() {
        return canPut;
    }

    public int getEndMark() {
        return endMark;
    }//判断啥时候结束用到的


    //方法
    public boolean isBoardVoid(int rowIndex, int columnIndex) {
        if (board[rowIndex][columnIndex] == 0) {
            return true;
        } else {
            return false;
        }
    }

    //判断某位置是黑子还是白子（该位置坐标）
    public boolean isBoardBlack(int rowIndex, int columnIndex) {
        if (board[rowIndex][columnIndex] == -1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isBoardWhite(int rowIndex, int columnIndex) {
        if (board[rowIndex][columnIndex] == 1) {
            return true;
        } else {
            return false;
        }
    }

    //判断是否和你相同(你的颜色，判断点的坐标）
    public boolean sameChess(int figure, int rowIndex, int columnIndex) {
        if ((figure == -1 && isBoardBlack(rowIndex, columnIndex)) || (figure == 1 && isBoardWhite(rowIndex, columnIndex))) {
            return true;
        } else {
            return false;
        }
    }

    //判断是否和你相反（你的颜色，判断点的坐标）
    public boolean diffChess(int figure, int rowIndex, int columnIndex) {
        if ((figure == 1 && isBoardBlack(rowIndex, columnIndex)) || (figure == -1 && isBoardWhite(rowIndex, columnIndex))) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkLocation(int figure, int rowIndex, int columnIndex) {
        int a = 0;
        for (int direction = 0; direction < 8; direction++) {
            //先判断第一个
            boolean b=false;
            int p = rowIndex + direction1[direction];
            int q = columnIndex + direction2[direction];
            if(p < 0 || p >= 8 || q < 0 || q >= 8 )
                continue;
            if (board[p][q] == -figure) {
                b = true;
                System.out.printf("%d, %d\n",p, q);
                System.out.printf("%d first is oppsite\n", direction);
            }
            c:if (b == true) {
               h: for (int k = 2; true; ++k) {
                    int i = rowIndex + direction1[direction] * k;
                    int j = columnIndex + direction2[direction] * k;
                    if (i < 0 || i >= 8 || j < 0 || j >= 8)
                        break c;
                    if (board[i][j] == 0)
                        break c;
                    if (board[i][j] == figure) {
                        a++;
                        key[direction] = k;
                        break;
                    }
                }
            }
//            System.out.printf("direction是%d ",direction);
        }
        if (a > 0) {
            return true;
        }
        return false;
    }

    public void changeBoard(int figure,int rowIndex,int columnIndex){
        for(int i=0;i<8;i++){
            System.out.print(key[i]);}
        for(int i=0;i<8;i++){
            if(key[i]>1){
                for(int k=0;k<key[i];++k){
                    board[rowIndex+direction1[i]*k][columnIndex+direction2[i]*k]=figure;
                }
            }
        }
        for(int i=0;i<8;i++){
            key[i]=0;
        }
    }


    //判断该人有无能走的位置//且将能走的位置用数组标记为true
    public boolean checkPut(int figure) {
        boolean mark = false;
        for (int m = 0; m < 8; m++) {
            for (int n = 0; n < 8; n++) {
                if (isBoardVoid(m, n)) {
                    if (checkLocation(figure, m, n)) {
                        canPut[m][n] = true;
                        mark = true;
                    }
                }
            }
        }
        if (mark) return true;
        return false;
    }

    //放置棋子
    public void putChess(int figure, int rowIndex, int columnIndex) {
        changeBoard(figure, rowIndex, columnIndex);//更新棋盘
        Step step = new Step(figure, rowIndex, columnIndex);
        stepList.add(step);//存进去下的这一步
        canPutList.add(canPut);
        boardList.add(board);
        //重新初始化canPut数组
        for (int m = 0; m < 8; m++) {
            for (int n = 0; n < 8; n++) {
                canPut[m][n] = false;
            }
        }
    }


    //关于有无可以落子及之后怎么办
    public void howDo(int figure, int rowIndex, int columnIndex) {
        if (checkPut(figure) && canPut[rowIndex][columnIndex]) {
            System.out.println(Arrays.deepToString(this.getCanPut()));
            endMark = 0;
            putChess(figure, rowIndex, columnIndex);
            System.out.println(Arrays.deepToString(this.getBoard()));
            System.out.println();
//            System.out.println(Arrays.deepToString(this.getCanPut()));
        } else if (!checkPut(figure) && !canPut[rowIndex][columnIndex]) {
            endMark++;
            if (endMark == 2) {
                checkWinner(blackCnt, whiteCnt);
                gameOver = true;
            }
        }
    }

    //判断胜负
    public Player checkWinner(int blackCnt, int whiteCnt) {
        if (blackCnt > whiteCnt) {
            return blackPlayer;
        } else {
            return whitePlayer;
        }
    }

    public int getWinnerPid() {
        if (blackPlayer == checkWinner(blackCnt, whiteCnt)) {
            return blackPlayer.getPid();
        } else {
            return whitePlayer.getPid();
        }
    }

    //悔棋
    public void repentanceChess() {
        boardList.remove(boardList.size() - 1);
        stepList.remove(stepList.size() - 1);
        canPutList.remove(canPutList.size() - 1);
    }

    //将棋盘数字化
    public StringBuilder putBoard() {
        StringBuilder PutBoard = new StringBuilder();
        int current;
        for (int m = 0; m < 8; m++) {
            for (int n = 0; n < 8; n++) {
                current = board[m][n];
                PutBoard.append(current + "\n");
            }
        }
        return PutBoard;
    }

    //存的格式
    public String toString() {
        return String.format(name + "\n" + gid + "\n" + blackPlayer.toString() + "\n" + blackCnt + "\n" +
                whitePlayer.toString() + "\n" + whiteCnt + "\n" + putBoard());

    }

    public void saveFile() throws Exception {
        String fileName = "D:\\黑白棋数据\\";
        String fileName2 = "newFile" + getGid() + ".txt";
        fileName = fileName + fileName2;
        Game game = new Game(name, whitePlayer, blackPlayer);
        Files.write(Paths.get(fileName), game.toString().getBytes(StandardCharsets.UTF_8));
    }

    public void upload(int gid) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("D:\\黑白棋数据\\" + "newFile" + gid + ".txt"));
        String allDate;
        while ((allDate = in.readLine()) != null) {
            String[] k = allDate.split(" ");
            this.name = k[0];
            this.gid = Integer.parseInt(k[1]);
            this.blackPlayer = new Player(k[2], -1);
            //Pid()=Integer.parseInt(k[3]);
            this.blackCnt = Integer.parseInt(k[4]);
            this.whitePlayer = new Player(k[5], 1);
            //pid=k[6];
            this.whiteCnt = Integer.parseInt(k[7]);
            int cnt = 8;
            for (int m = 0; m < 8; m++) {
                for (int n = 0; n < 8; n++) {
                    board[m][n] = Integer.parseInt(k[cnt]);
                    cnt++;
                }
            }
        }
    }


}






