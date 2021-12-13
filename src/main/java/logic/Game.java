package Logic;
import java.io.File;
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
    private static int[][] board =new int[8][8];
    //新加的
    private int whiteCnt;
    private int blackCnt;
    private static int blackFigure = -1;//代表黑手
    private static int whiteFigure = 1;//代表白手
    private int currentFigure = -1;//代表当前是黑手还是白手，黑手先下
    private int[] rowStop = new int[8];//每步完需要初始化
    private int[] columnStop = new int[8];//每步完需要初始化
    private boolean[][] canPut = new boolean[8][8];//每步完需要初始化
    private static ArrayList<boolean[][]> canPutList=new ArrayList<boolean[][]>();
    private static ArrayList <int[]>rowStopList=new ArrayList<int[]>();
    private static ArrayList <int[]>columnList=new ArrayList<int[]>();
    private static ArrayList <int[][]>boardList=new ArrayList<int[][]>();
    private int endMark = 0;
    private boolean gameOver = false;

    public Game(String name, Player whitePlayer, Player blackPlayer) {
        this.name = name;
        this.gid = gameCnt;
        gameCnt++;
        this.blackPlayer = blackPlayer;
        this.whitePlayer = whitePlayer;
        setBoard(board);
        this.rowStop = new int[8];
        this.columnStop = new int[8];
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

    public boolean getGameOver () {
        return this.gameOver;
    }

    public ArrayList<Step> getStepList() {
        return stepList;
    }//get步数数列

    public ArrayList getCanPutList() {
        return canPutList;
    }

    public ArrayList getColumnList() {
        return columnList;
    }

    public ArrayList getRowStopList() {
        return rowStopList;
    }

    public ArrayList getBoardList() {
        return boardList;
    }

    public int[][] getBoard() {
        int [][]board = new int[8][8];
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                board[i][j] = this.board[i][j];
            }
        }
        return board;
    }//get棋盘

    public void setBoard(int[][] board) {
        for (int m = 0; m < 8; m++) {
            for (int n = 0; n < 8; n++) {
                this.board[m][n] = board[m][n];
            }
        }
        this.board[3][3] = -1;
        this.board[4][4] = -1;
        this.board[3][4] = 1;
        this.board[4][3] = 1;
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

    public int[] getColumnStop() {
        return this.columnStop;
    }

    public int[] getRowStop() {
        return this.rowStop;
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

    //检索某位置向上有无同颜色的子还顺便把相同的子存起来，
    public boolean checkUpLocation(int figure, int rowIndex, int columnIndex) {//你想下的位置
        int a = 0;
        if (rowIndex >= 2) a++;//判断是否为边界点,必须至少在第三行
        else return false;
        if (diffChess(figure, rowIndex - 1, columnIndex)) a++;//判断该子上方一个是否有对手子
        for (int x = 2; rowIndex - x >= 0; x++) {
            int i = rowIndex - x;
            if (a != 2) {
                return false;
            } else if (sameChess(figure, i, columnIndex)) {
                rowStop[0] = i;
                return true;
            } else if (isBoardVoid(i, columnIndex)) {
                return false;
            }
        }
        return false;
    }

    public boolean checkDoLocation(int figure, int rowIndex, int columnIndex) {//你想下的位置
        int a = 0;
        if (rowIndex <= 5) a++;//判断是否为边界点,必须至少在第三行
        else return false;
        if (diffChess(figure, rowIndex + 1, columnIndex)) a++;//判断该子下方一个是否有对手子
        for (int x = 2; rowIndex + x <= 7; x++) {
            int i = rowIndex + x;
            if (a != 2) {
                return false;
            } else if (sameChess(figure, i, columnIndex)) {
                rowStop[1] = i;
                return true;
            } else if (isBoardVoid(i, columnIndex)) {
                return false;
            }
        }
        return false;
    }

    public boolean checkLeLocation(int figure, int rowIndex, int columnIndex) {//你想下的位置
        int a = 0;
        if (columnIndex >= 2) a++;//判断是否为边界点,必须至少在第六行
        else return false;
        if (diffChess(figure, rowIndex, columnIndex - 1)) a++;//判断该子左方一个是否有对手子
        else return false;
        for (int x = 2; columnIndex + x >= 0; x++) {
            int i = columnIndex - x;
            if (a != 2) {
                return false;
            } else if (sameChess(figure, rowIndex, i)) {
                columnStop[2] = i;
                return true;
            } else if (isBoardVoid(rowIndex, i)) {
                return false;
            }
        }
        return false;
    }

    public boolean checkRiLocation(int figure, int rowIndex, int columnIndex) {//你想下的位置
        int a = 0;
        if (columnIndex <= 5) a++;//判断是否为边界点,必须至少在第六行
        else return false;
        if (diffChess(figure, rowIndex, columnIndex + 1)) a++;//判断该子右方一个是否有对手子
        else return false;
        for (int x = 2; columnIndex + x <= 7; x++) {
            int i = columnIndex + x;
            if (a != 2) {
                return false;
            } else if (sameChess(figure, rowIndex, i)) {
                columnStop[3] = i;
                return true;
            } else if (isBoardVoid(rowIndex, i)) {
                return false;
            }
        }
        return false;
    }

    public boolean checkLULocation(int figure, int rowIndex, int columnIndex) {//左上
        int a = 0;
        if (rowIndex >= 2 && columnIndex >= 2) a++;//判断是否为边界点,必须至少在第三行
        else return false;
        if (diffChess(figure, rowIndex - 1, columnIndex - 1)) a++;//判断该子上方一个是否有对手子
        else return false;
        int haha;
        if (rowIndex <= columnIndex) {
            haha = rowIndex;
        } else {
            haha = columnIndex;
        }
        for (int x = 2; haha - x >= 0; x++) {
            int m = rowIndex - x;
            int n = columnIndex - x;
            if (a != 2) {
                return false;
            } else if (sameChess(figure, m, n)) {
                columnStop[4] = m;
                rowStop[4] = n;
                return true;
            } else if (isBoardVoid(m, n)) {
                return false;
            }
        }
        return false;
    }

    public boolean checkLDLocation(int figure, int rowIndex, int columnIndex) {//左下
        int a = 0;
        if (rowIndex <= 5 && columnIndex >= 2) a++;//判断是否为边界点,必须至少在第三行
        else return false;
        if (diffChess(figure, rowIndex + 1, columnIndex - 1)) a++;//判断该子上方一个是否有对手子
        else return false;
        int haha;
        if (7 - rowIndex <= columnIndex) {
            haha = 7 - rowIndex;
        } else {
            haha = columnIndex;
        }
        for (int x = 2; haha - x >= 0; x++) {
            int n = columnIndex - x;
            int m = rowIndex + x;
            if (a != 2) {
                return false;
            } else if (sameChess(figure, m, n)) {
                columnStop[5] = m;
                rowStop[5] = n;
                return true;
            } else if (isBoardVoid(m, n)) {
                return false;
            }
        }
        return false;
    }

    public boolean checkRULocation(int figure, int rowIndex, int columnIndex) {//左下
        int a = 0;
        if (rowIndex >= 2 && columnIndex <= 5) a++;//判断是否为边界点,必须至少在第三行
        else return false;
        if (diffChess(figure, rowIndex - 1, columnIndex + 1)) a++;//判断该子上方一个是否有对手子
        else return false;
        int haha;
        if (rowIndex <= 7 - columnIndex) {
            haha = rowIndex;
        } else {
            haha = 7 - columnIndex;
        }
        for (int x = 2; haha - x >= 0; x++) {
            int n = columnIndex + x;
            int m = rowIndex - x;
            if (a != 2) {
                return false;
            } else if (sameChess(figure, m, n)) {
                columnStop[6] = m;
                rowStop[6] = n;
                return true;
            } else if (isBoardVoid(m, n)) {
                return false;
            }
        }
        return false;
    }

    public boolean checkRDLocation(int figure, int rowIndex, int columnIndex) {//左下
        int a = 0;
        if (rowIndex <= 5 && columnIndex <= 5) a++;//判断是否为边界点,必须至少在第三行
        else return false;
        if (diffChess(figure, rowIndex + 1, columnIndex + 1)) a++;//判断该子上方一个是否有对手子
        else return false;
        int haha;
        if (7 - rowIndex <= 7 - columnIndex) {
            haha = 7 - rowIndex;
        } else {
            haha = 7 - columnIndex;
        }
        for (int x = 2; haha - x >= 0; x++) {
            int n = columnIndex + x;
            int m = rowIndex + x;
            if (a != 2) {
                return false;
            } else if (sameChess(figure, m, n)) {
                columnStop[7] = m;
                rowStop[7] = n;
                return true;
            } else if (isBoardVoid(m, n)) {
                return false;
            }
        }
        return false;
    }

    //八个方向的汇总
    public boolean checkLocation(int figure, int rowIndex, int columnIndex) {//结合八个方向能不能下,只要有一个能下就下
        if (checkUpLocation(figure, rowIndex, columnIndex) || checkDoLocation(figure, rowIndex, columnIndex) || checkLeLocation(figure, rowIndex, columnIndex) || checkRiLocation(figure, rowIndex, columnIndex) || checkLULocation(figure, rowIndex, columnIndex) || checkLDLocation(figure, rowIndex, columnIndex) || checkRULocation(figure, rowIndex, columnIndex) || checkRDLocation(figure, rowIndex, columnIndex)) {
            return true;
        } else {
            return false;
        }
    }

    //某人走某位置会怎么改变棋盘
    public void changeBoard(int figure, int rowIndex, int columnIndex) {
        if (checkUpLocation(figure, rowIndex, columnIndex)) {
            for (int i = rowIndex; i >= rowStop[0]; i--) {
                board[i][columnIndex] = figure;
            }
        }
        if (checkDoLocation(figure, rowIndex, columnIndex)) {
            for (int i = rowIndex; i <= rowStop[1]; i++) {
                board[i][columnIndex] = figure;
            }
        }
        if (checkLeLocation(figure, rowIndex, columnIndex)) {
            for (int i = columnIndex; i >= columnStop[2]; i--) {
                board[rowIndex][i] = figure;
            }
        }
        if (checkRiLocation(figure, rowIndex, columnIndex)) {
            for (int i = columnIndex; i <= columnStop[3]; i++) {
                board[rowIndex][i] = figure;
            }
        }
        if (checkLULocation(figure, rowIndex, columnIndex)) {
            for (int n = columnIndex; n >= columnStop[4]; n--) {
                int m = rowIndex;
                board[m][n] = figure;
                m--;
            }
        }
        if (checkLDLocation(figure, rowIndex, columnIndex)) {
            for (int n = columnIndex; n >= columnStop[5]; n--) {
                int m = rowIndex;
                board[m][n] = figure;
                m++;
            }
        }
        if (checkRULocation(figure, rowIndex, columnIndex)) {
            for (int n = columnIndex; n <= columnStop[6]; n++) {
                int m = rowIndex;
                board[m][n] = figure;
                m--;
            }
        }
        if (checkRDLocation(figure, rowIndex, columnIndex)) {
            for (int n = columnIndex; n <= columnStop[7]; n++) {
                int m = rowIndex;
                board[m][n] = figure;
                m++;
            }
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
//                        return true;
                    }
                }
            }
        }
        if(mark) return true;
        return false;
    }

    //放置棋子
    public void putChess(int figure, int rowIndex, int columnIndex) {
        changeBoard(figure, rowIndex, columnIndex);//更新棋盘
        Step step=new Step(figure,rowIndex,columnIndex);
        stepList.add(step);//存进去下的这一步
        canPutList.add(canPut);
        rowStopList.add(rowStop);
        columnList.add(columnStop);
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
        } else if (!checkPut(figure)) {
            endMark++;
            if (endMark == 2) {
                checkWinner(blackCnt, whiteCnt);
                gameOver = true;
            }
        }
        currentFigure = -figure;
    }

    //判断胜负
    public Player checkWinner(int blackCnt, int whiteCnt) {
        if (blackCnt > whiteCnt) {
            return blackPlayer;
        } else {
            return whitePlayer;
        }
    }
    public int getWinnerPid(){
        if( blackPlayer==checkWinner(blackCnt,whiteCnt)){
            return blackPlayer.getPid();
        }else{
            return whitePlayer.getPid();
        }
    }
    //悔棋
    public void repentanceChess(){
        boardList.remove(boardList.size()-1);
        stepList.remove(stepList.size()-1);
        canPutList.remove(canPutList.size()-1);
    }
    //将棋盘数字化
    public StringBuilder putBoard(){
        StringBuilder PutBoard=new StringBuilder();
        int current;
        for(int m=0;m<8;m++){
            for(int n=0;n<8;n++){
               current=board[m][n];
               PutBoard.append(current+"\n");
            }
        }
        return PutBoard;
    }
    //存的格式
    public String toString() {
        return String.format(name+"\n"+gid+"\n" +blackPlayer.toString()+"\n"+blackCnt+"\n"+
                whitePlayer.toString()+"\n"+whiteCnt+"\n"+ putBoard());

    }
    public void saveFile()throws Exception{
        String fileName="D:\\黑白棋数据\\";
        String fileName2 = "newFile"+getGid()+".txt";
        fileName = fileName + fileName2;
        Game game=new Game(name,whitePlayer,blackPlayer);
        Files.write(Paths.get(fileName),game.toString().getBytes(StandardCharsets.UTF_8)) ;
    }
    public void upload (int gid) {
        String fileName="D:\\黑白棋数据\\"+"newFile"+gid+".txt";

    }

}
