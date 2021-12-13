package Logic;

public class Step {
    private int sid;
    private int rowIndex;
    private int columnIndex;
    private int figure;
    private static int stepCnt=1;


    public Step( int figure,int rowIndex, int columnIndex){
        this.sid=stepCnt;
        stepCnt++;
        this.rowIndex=rowIndex;
        this.columnIndex=columnIndex;
        this.figure =figure;
    }

    public int getSid(){
        return sid;
    }
    public int getFigure(){
        return figure;
    }
    public void setFigure(int figure){
        this.figure = figure;
    }
    public int getRowIndex(){
        return rowIndex;
    }
    public void setRowIndex(int rowIndex){
        this.rowIndex=rowIndex;
    }
    public int getColumnIndex(){
        return columnIndex;
    }
    public void setColumnIndex(int columnIndex){
        this.columnIndex=columnIndex;
    }
    public static int getStepCnt(){
        return stepCnt;
    }

}
