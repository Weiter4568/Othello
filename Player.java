package initialization;

public class Player {
    private int pid;
    private String name;
    private int figure;//代表黑手还是白手（白手为1，黑手为2）
    private static int playerCnt = 1;

    //构造器写什么，一个玩家还是两个玩家
    /* 构造器写一个玩家就好了吧 */
    //怎么输入黑手还是白手
    /* 我觉得可以用playCnt来判断，因为是黑棋先手，所以当playCnt为奇数的时候应该就是黑棋输入姓名 */
    //setName的意义是什么
    /* 就是在A4 里面可以改名这个操作，project里可以没有 */
    public Player(String name,int figure,int pid) {
        //第一个玩家信息
        this.pid = playerCnt;
        this.name = name;
        playerCnt++;
        this.figure=figure;

    }


    public int getPid() {
        return pid;
    }
    public String getName() {
        return name;
    }
    public int getFigure(){return figure;}

    public void setName(String name) {
        this.name = name;
    }
    public void setFigure(int figure){
        this.figure=figure;
    }


    public static int getPlayerCnt() {
        return playerCnt;
    }


    public String toString() {
        return String.format("Player: %s, pid: %d",name,pid);

    }
}
