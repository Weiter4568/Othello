package initialization;

public class Board {
    private int[][] board = new int[8][8];
    private int[]columnStop=new int[8];//up down left right ul ud ru rd
    private int[]rowStop=new int [8];

    //创建一个步数类型的数据


    //构造器不知道要写啥
    public Board(int[][] board) {
        for (int m = 0; m < 8; m++) {
            for (int n = 0; n < 8; n++) {
                this.board[m][n] = board[m][n];
            }
        }
    }


    //get board
    public int[][] getBoard() {
        return board;
    }
    public int[]getColumnStop(){
        return columnStop;
    }
    public int[]getRowStop(){
        return rowStop;
    }


    //清空加初始化棋盘
    public void setBoard() {
        for (int m = 0; m < 8; m++) {
            for (int n = 0; n < 8; n++) {
                board[m][n] = 0;
            }
        }
        board[3][3] = 1;
        board[4][4] = 1;
        board[4][3] = -1;
        board[3][4] = -1;
    }

    //判断某位置是否是空的
    public boolean boardVoid(int columnIndex, int rowIndex) {
        if (board[columnIndex][rowIndex] == 0) {
            return true;
        } else {
            return false;
        }
    }

    //判断某位置是黑子还是白子（该位置坐标）
    public boolean boardBlack(int columnIndex, int rowIndex) {
        if (board[columnIndex][rowIndex] == -1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean boardWhite(int columnIndex, int rowIndex) {
        if (board[columnIndex][rowIndex] == 1) {
            return true;
        } else {
            return false;
        }
    }

    //判断是否和你相同(你的颜色，判断点的坐标）
    public boolean sameChess(int figure, int columnIndex, int rowIndex) {
        if ((figure == -1 && boardBlack(columnIndex, rowIndex)) || (figure == 1 && boardWhite(columnIndex, rowIndex))) {
            return true;
        } else {
            return false;
        }
    }

    //判断是否和你相反（你的颜色，判断点的坐标）
    public boolean diffChess(int figure, int columnIndex, int rowIndex) {
        if ((figure == 1 && boardBlack(columnIndex, rowIndex)) || (figure == -1 && boardWhite(columnIndex, rowIndex))) {
            return true;
        } else {
            return false;
        }
    }

    //检索某位置向上有无同颜色的子还顺便把相同的子存起来，
    public boolean checkUpLocation(int figure, int columnIndex, int rowIndex) {//你想下的位置
        int a = 0;
        if (rowIndex >= 2) a++;//判断是否为边界点,必须至少在第三行
        if (diffChess(figure, columnIndex, rowIndex - 1)) a++;//判断该子上方一个是否有对手子
        for (int x = 2; rowIndex - x >= 0; x++) {
            int i = rowIndex - x;
            if (a != 2) {
                return false;
            } else if (sameChess(figure, columnIndex, i)) {
               rowStop[0]=i;
                return true;
            } else if (boardVoid(columnIndex, i)) {
                return false;
            }
        }
        return false;
    }
    public boolean checkDoLocation(int figure, int columnIndex, int rowIndex) {//你想下的位置
        int a = 0;
        if (rowIndex <= 5) a++;//判断是否为边界点,必须至少在第三行
        if (diffChess(figure, columnIndex, rowIndex + 1)) a++;//判断该子下方一个是否有对手子
        for (int x = 2; rowIndex + x <= 7; x++) {
            int i = rowIndex + x;
            if (a != 2) {
                return false;
            } else if (sameChess(figure, columnIndex, i)) {
                rowStop[1]=i;
                return true;
            } else if (boardVoid(columnIndex, i)) {
                return false;
            }
        }
        return false;
    }
    public boolean checkLeLocation(int figure, int columnIndex, int rowIndex) {//你想下的位置
        int a = 0;
        if (columnIndex >= 2) a++;//判断是否为边界点,必须至少在第六行
        if (diffChess(figure, columnIndex - 1, rowIndex)) a++;//判断该子右方一个是否有对手子
        for (int x = 2; columnIndex + x >= 0; x++) {
            int i = columnIndex - x;
            if (a != 2) {
                return false;
            } else if (sameChess(figure, i, rowIndex)) {
                columnStop[2]=i;
                return true;
            } else if (boardVoid(i, rowIndex)) {
                return false;
            }
        }
        return false;
    }
    public boolean checkRiLocation(int figure, int columnIndex, int rowIndex) {//你想下的位置
        int a = 0;
        if (columnIndex <= 5) a++;//判断是否为边界点,必须至少在第六行
        if (diffChess(figure, columnIndex + 1, rowIndex)) a++;//判断该子右方一个是否有对手子
        for (int x = 2; columnIndex + x <= 7; x++) {
            int i = columnIndex + x;
            if (a != 2) {
                return false;
            } else if (sameChess(figure, i, rowIndex)) {
                columnStop[3]=i;
                return true;
            } else if (boardVoid(i, rowIndex)) {
                return false;
            }
        }
        return false;
    }
    public boolean checkLULocation(int figure, int columnIndex, int rowIndex) {//左上
       int a = 0;
        if (rowIndex >= 2 && columnIndex >= 2) a++;//判断是否为边界点,必须至少在第三行
        if (diffChess(figure, columnIndex - 1, rowIndex - 1)) a++;//判断该子上方一个是否有对手子
        int haha;
        if (rowIndex <= columnIndex) {
             haha = rowIndex;
        } else {
             haha = columnIndex;
        }
        for (int x = 2; haha - x >= 0; x++) {
            int m = columnIndex - x;
            int n = rowIndex - x;
            if (a != 2) {
                return false;
            } else if (sameChess(figure, m, n)) {
                columnStop[4]=m;
                rowStop[4]=n;
                return true;
            } else if (boardVoid(m, n)) {
                return false;
            }
        }
        return false;
    }
    public boolean checkLDLocation(int figure, int columnIndex, int rowIndex) {//左下
        int a = 0;
        if (rowIndex <=5 && columnIndex >= 2) a++;//判断是否为边界点,必须至少在第三行
        if (diffChess(figure, columnIndex - 1, rowIndex + 1)) a++;//判断该子上方一个是否有对手子
        int haha;
        if (7-rowIndex <= columnIndex) {
            haha = 7-rowIndex;
        } else {
            haha = columnIndex;
        }
        for (int x = 2; haha - x >= 0; x++) {
            int m = columnIndex - x;
            int n = rowIndex + x;
            if (a != 2) {
                return false;
            } else if (sameChess(figure, m, n)) {
                columnStop[5]=m;
                rowStop[5]=n;
                return true;
            } else if (boardVoid(m, n)) {
                return false;
            }
        }
        return false;
    }
    public boolean checkRULocation(int figure, int columnIndex, int rowIndex) {//左下
        int a = 0;
        if (rowIndex >=2 && columnIndex <=5) a++;//判断是否为边界点,必须至少在第三行
        if (diffChess(figure, columnIndex + 1, rowIndex - 1)) a++;//判断该子上方一个是否有对手子
        int haha;
        if (rowIndex <= 7-columnIndex) {
            haha = rowIndex;
        } else {
            haha = 7-columnIndex;
        }
        for (int x = 2; haha - x >= 0; x++) {
            int m = columnIndex + x;
            int n = rowIndex - x;
            if (a != 2) {
                return false;
            } else if (sameChess(figure, m, n)) {
                columnStop[6]=m;
                rowStop[6]=n;
                return true;
            } else if (boardVoid(m, n)) {
                return false;
            }
        }
        return false;
    }
    public boolean checkRDLocation(int figure, int columnIndex, int rowIndex) {//左下
        int a = 0;
        if (rowIndex <=5 && columnIndex <= 5) a++;//判断是否为边界点,必须至少在第三行
        if (diffChess(figure, columnIndex + 1, rowIndex + 1)) a++;//判断该子上方一个是否有对手子
        int haha;
        if (7-rowIndex <= 7-columnIndex) {
            haha = 7-rowIndex;
        } else {
            haha =7- columnIndex;
        }
        for (int x = 2; haha - x >= 0; x++) {
            int m = columnIndex + x;
            int n = rowIndex + x;
            if (a != 2) {
                return false;
            } else if (sameChess(figure, m, n)) {
                columnStop[7]=m;
                rowStop[7]=n;
                return true;
            } else if (boardVoid(m, n)) {
                return false;
            }
        }
        return false;
    }
    public boolean checkLocation(int figure,int columnIndex,int rowIndex){//结合八个方向能不能下,只要有一个能下就下
        if(checkUpLocation(figure,columnIndex,rowIndex)||checkDoLocation(figure,columnIndex,rowIndex)||checkLeLocation(figure,columnIndex,rowIndex)||checkRiLocation(figure,columnIndex,rowIndex)||checkLULocation(figure,columnIndex,rowIndex)||checkLDLocation(figure,columnIndex,rowIndex)||checkRULocation(figure,columnIndex,rowIndex)||checkRDLocation(figure,columnIndex,rowIndex)){
            return true;
        }else {
            return false;
        }
    }
    public void changeBoard(int figure,int columnIndex,int rowIndex){
        if(checkUpLocation(figure,columnIndex,rowIndex)){
            for(int i=rowIndex;i>=rowStop[0];i--){
                board[columnIndex][i]=figure;
            }
        }
        if(checkDoLocation(figure,columnIndex,rowIndex)){
            for(int i=rowIndex;i<=rowStop[1];i++){
                board[columnIndex][i]=figure;
            }
        }
        if(checkLeLocation(figure,columnIndex,rowIndex)){
            for(int i=columnIndex;i>=columnStop[2];i--){
                board[i][rowIndex]=figure;
            }
        }
        if(checkRiLocation(figure,columnIndex,rowIndex)){
            for(int i=columnIndex;i<=columnStop[3];i++){
            board[i][rowIndex]=figure;
            }
        }
        if(checkLULocation(figure,columnIndex,rowIndex)){
            for(int m=columnIndex;m>=columnStop[4];m--){
               int n=rowIndex;
               board[m][n]=figure;
               n--;
            }
        }
        if(checkLDLocation(figure,columnIndex,rowIndex)){
            for(int m=columnIndex;m>=columnStop[5];m--){
                int n=rowIndex;
                board[m][n]=figure;
                n++;
            }
        }
        if(checkRULocation(figure,columnIndex,rowIndex)){
            for(int m=columnIndex;m<=columnStop[6];m++){
                int n=rowIndex;
                board[m][n]=figure;
                n--;
            }
        }
        if(checkRDLocation(figure,columnIndex,rowIndex)){
            for(int m=columnIndex;m<=columnStop[7];m++){
                int n=rowIndex;
                board[m][n]=figure;
                n++;
            }
        }
    }


}
