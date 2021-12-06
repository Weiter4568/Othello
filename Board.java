package initialization;

public class Board {
    private int[][] board = new int[8][8];//up down left right ul ud ru rd
    private int[]rowStop=new int [8];
    private int[]columnStop=new int[8];

    //创建一个步数类型的数据


    //构造器不知道要写啥
    //public Board(int[][] board) {
      //  for (int m = 0; m < 8; m++) {
        //    for (int n = 0; n < 8; n++) {
          //      this.board[m][n] = board[m][n];
            //}
        //}
    //}


    //get board
    public int[][] getBoard() {
        return this.board;
    }
    public int[]getColumnStop(){
        return this.columnStop;
    }
    public int[]getRowStop(){
        return this.rowStop;
    }


    //清空加初始化棋盘
    public void cleanBoard() {
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
        if (diffChess(figure, rowIndex - 1, columnIndex)) a++;//判断该子上方一个是否有对手子
        for (int x = 2; rowIndex - x >= 0; x++) {
            int i = rowIndex - x;
            if (a != 2) {
                return false;
            } else if (sameChess(figure, i,columnIndex)) {
               rowStop[0]=i;
                return true;
            } else if (isBoardVoid( i,columnIndex)) {
                return false;
            }
        }
        return false;
    }
    public boolean checkDoLocation(int figure, int rowIndex, int columnIndex) {//你想下的位置
        int a = 0;
        if (rowIndex <= 5) a++;//判断是否为边界点,必须至少在第三行
        if (diffChess(figure, rowIndex + 1, columnIndex)) a++;//判断该子下方一个是否有对手子
        for (int x = 2; rowIndex + x <= 7; x++) {
            int i = rowIndex + x;
            if (a != 2) {
                return false;
            } else if (sameChess(figure, i, columnIndex)) {
                rowStop[1]=i;
                return true;
            } else if (isBoardVoid( i,columnIndex)) {
                return false;
            }
        }
        return false;
    }
    public boolean checkLeLocation(int figure, int rowIndex, int columnIndex) {//你想下的位置
        int a = 0;
        if (columnIndex >= 2) a++;//判断是否为边界点,必须至少在第六行
        if (diffChess(figure, rowIndex, columnIndex - 1)) a++;//判断该子右方一个是否有对手子
        for (int x = 2; columnIndex + x >= 0; x++) {
            int i = columnIndex - x;
            if (a != 2) {
                return false;
            } else if (sameChess(figure,  rowIndex,i)) {
                columnStop[2]=i;
                return true;
            } else if (isBoardVoid(rowIndex,i )) {
                return false;
            }
        }
        return false;
    }
    public boolean checkRiLocation(int figure, int rowIndex, int columnIndex) {//你想下的位置
        int a = 0;
        if (columnIndex <= 5) a++;//判断是否为边界点,必须至少在第六行
        if (diffChess(figure, rowIndex, columnIndex + 1)) a++;//判断该子右方一个是否有对手子
        for (int x = 2; columnIndex + x <= 7; x++) {
            int i = columnIndex + x;
            if (a != 2) {
                return false;
            } else if (sameChess(figure, rowIndex,i )) {
                columnStop[3]=i;
                return true;
            } else if (isBoardVoid(rowIndex,i )) {
                return false;
            }
        }
        return false;
    }
    public boolean checkLULocation(int figure, int rowIndex, int columnIndex) {//左上
       int a = 0;
        if (rowIndex >= 2 && columnIndex >= 2) a++;//判断是否为边界点,必须至少在第三行
        if (diffChess(figure, rowIndex - 1, columnIndex - 1)) a++;//判断该子上方一个是否有对手子
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
                columnStop[4]=m;
                rowStop[4]=n;
                return true;
            } else if (isBoardVoid(m, n)) {
                return false;
            }
        }
        return false;
    }
    public boolean checkLDLocation(int figure, int rowIndex, int columnIndex) {//左下
        int a = 0;
        if (rowIndex <=5 && columnIndex >= 2) a++;//判断是否为边界点,必须至少在第三行
        if (diffChess(figure, rowIndex + 1, columnIndex - 1)) a++;//判断该子上方一个是否有对手子
        int haha;
        if (7-rowIndex <= columnIndex) {
            haha = 7-rowIndex;
        } else {
            haha = columnIndex;
        }
        for (int x = 2; haha - x >= 0; x++) {
            int n = columnIndex - x;
            int m = rowIndex + x;
            if (a != 2) {
                return false;
            } else if (sameChess(figure, m, n)) {
                columnStop[5]=m;
                rowStop[5]=n;
                return true;
            } else if (isBoardVoid(m, n)) {
                return false;
            }
        }
        return false;
    }
    public boolean checkRULocation(int figure, int rowIndex, int columnIndex) {//右上
        int a = 0;
        if (rowIndex >=2 && columnIndex <=5) a++;//判断是否为边界点,必须至少在第三行
        if (diffChess(figure, rowIndex - 1, columnIndex + 1)) a++;//判断该子上方一个是否有对手子
        int haha;
        if (rowIndex <= 7-columnIndex) {
            haha = rowIndex;
        } else {
            haha = 7-columnIndex;
        }
        for (int x = 2; haha - x >= 0; x++) {
            int n = columnIndex + x;
            int m = rowIndex - x;
            if (a != 2) {
                return false;
            } else if (sameChess(figure, m, n)) {
                columnStop[6]=m;
                rowStop[6]=n;
                return true;
            } else if (isBoardVoid(m, n)) {
                return false;
            }
        }
        return false;
    }
    public boolean checkRDLocation(int figure, int rowIndex, int columnIndex) {//右下
        int a = 0;
        if (rowIndex <=5 && columnIndex <= 5) a++;//判断是否为边界点,必须至少在第三行
        if (diffChess(figure, rowIndex + 1, columnIndex + 1)) a++;//判断该子上方一个是否有对手子
        int haha;
        if (7-rowIndex <= 7-columnIndex) {
            haha = 7-rowIndex;
        } else {
            haha =7- columnIndex;
        }
        for (int x = 2; haha - x >= 0; x++) {
            int n = columnIndex + x;
            int m = rowIndex + x;
            if (a != 2) {
                return false;
            } else if (sameChess(figure, m, n)) {
                columnStop[7]=m;
                rowStop[7]=n;
                return true;
            } else if (isBoardVoid(m, n)) {
                return false;
            }
        }
        return false;
    }
    //八个方向的汇总
    public boolean checkLocation(int figure,int rowIndex,int columnIndex){//结合八个方向能不能下,只要有一个能下就下
        if(checkUpLocation(figure,rowIndex,columnIndex)||checkDoLocation(figure,rowIndex,columnIndex)||checkLeLocation(figure,rowIndex,columnIndex)||checkRiLocation(figure,rowIndex,columnIndex)||checkLULocation(figure,rowIndex,columnIndex)||checkLDLocation(figure,rowIndex,columnIndex)||checkRULocation(figure,rowIndex,columnIndex)||checkRDLocation(figure,rowIndex,columnIndex)){
            return true;
        }else {
            return false;
        }
    }
   //某人走某位置会怎么改变棋盘
    public void changeBoard(int figure,int rowIndex,int columnIndex){
        if(checkUpLocation(figure,rowIndex,columnIndex)){
            for(int i=rowIndex;i>=rowStop[0];i--){
                board[i][columnIndex]=figure;
            }
        }
        if(checkDoLocation(figure,rowIndex,columnIndex)){
            for(int i=rowIndex;i<=rowStop[1];i++){
                board[i][columnIndex]=figure;
            }
        }
        if(checkLeLocation(figure,rowIndex,columnIndex)){
            for(int i=columnIndex;i>=columnStop[2];i--){
                board[rowIndex][i]=figure;
            }
        }
        if(checkRiLocation(figure,rowIndex,columnIndex)){
            for(int i=columnIndex;i<=columnStop[3];i++){
            board[rowIndex][i]=figure;
            }
        }
        if(checkLULocation(figure,rowIndex,columnIndex)){
            for(int n=columnIndex;n>=columnStop[4];n--){
               int m=rowIndex;
               board[m][n]=figure;
               m--;
            }
        }
        if(checkLDLocation(figure,rowIndex,columnIndex)){
            for(int n=columnIndex;n>=columnStop[5];n--){
                int m=rowIndex;
                board[m][n]=figure;
                m++;
            }
        }
        if(checkRULocation(figure,rowIndex,columnIndex)){
            for(int n=columnIndex;n<=columnStop[6];n++){
                int m=rowIndex;
                board[m][n]=figure;
                m--;
            }
        }
        if(checkRDLocation(figure,rowIndex,columnIndex)){
            for(int n=columnIndex;n<=columnStop[7];n++){
                int m=rowIndex;
                board[m][n]=figure;
                m++;
            }
        }
    }


}
