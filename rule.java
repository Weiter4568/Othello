package initialization;

public class rule {
    //成员变量
    private static int blackFigure = -1;//代表黑手
    private static int whiteFigure = 1;//代表白手
    private static int currentFigure = -1;//代表当前是黑手还是白手，黑手先下

    private static int blackCnt;//黑子（计数器）
    private static int whiteCnt;//白子（计数器)
    private int gid;//代表第几场游戏
    private static int stepCnt = 0;
    private static Board board=new Board();
    private static boolean[][]canPut=new boolean[8][8];
    private static int endMark=0;

    //成员变量结束


    //方法开始


    //get方法
    public int getBlackFigure() {
        return blackFigure;
    }
    public Board getBoard(){
        return board;
    }

    public static int getEndMark() {
        return endMark;
    }

    public int getWhiteFigure() {
        return whiteFigure;
    }

    public int getCurrentFigure() {
        return currentFigure;
    }


    public int getBlackCnt() {
        return blackCnt;
    }

    public int getWhiteCnt() {
        return whiteCnt;
    }

    public int getGid() {
        return gid;
    }

    public static int getStepCnt() {
        return stepCnt;
    }


    //检查你该不该走(黑子先走)没用////////
    public boolean checkFigure(int figure) {
        if (stepCnt == 0 && currentFigure == figure) {
            return true;
        } else if (currentFigure == figure) {
            return true;
        } else {
            return false;
        }
    }
    //判断该人有无能走的位置//且将能走的位置用数组标记为true
    public boolean checkPut(int figure){
        for(int m=0;m<8;m++) {
            for (int n = 0; n < 8; n++) {
                if (board.isBoardVoid(m, n)) {
                    if (board.checkLocation(figure, m, n)) {
                        canPut[m][n] = true;
                        return true;
                    }
                }
            }
        }
        return false;
    }
    //放置棋子
    public void putChess(int figure, int rowIndex, int columnIndex) {
        board.changeBoard(figure, rowIndex, columnIndex);//更新棋盘
        currentFigure = -figure;//交换黑白手
        whiteCnt = 0;
        blackCnt = 0;
        for (int m = 0; m < 8; m++) {
            for (int n = 0; n < 8; n++) {
                if (board.getBoard()[m][n] == 1) {
                    whiteCnt++;
                } else if (board.getBoard()[m][n] == -1) {
                    blackCnt++;
                }
            }
        }//更新计数器
        //重新初始化canPut数组
        for (int m = 0; m < 8; m++) {
            for (int n = 0; n < 8; n++) {
                canPut[m][n] = false;
            }
        }
    }
    //判断胜负
    public int checkWinner(int blackCnt,int whiteCnt){
        if(blackCnt>whiteCnt){
            return -1;
        }else if(blackCnt<whiteCnt){
            return 1;
        }else return 0;
    }
    //关于有无可以落子及之后怎么办
    public void howDo(int figure,int rowIndex,int columnIndex){
        if(checkFigure(figure)&&checkPut(figure)&&canPut[rowIndex][columnIndex]){
            endMark=0;
            putChess(figure,rowIndex,columnIndex);
        }else if(checkFigure(figure)&&!checkPut(figure)){
            endMark++;
            if(endMark==2){
                checkWinner(blackCnt,whiteCnt);
            }
        }
        currentFigure=-figure;
    }



}