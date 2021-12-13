package Logic;

import java.util.ArrayList;

public class GameSystem {
    //成员变量
    private static ArrayList<Player> playerList = new ArrayList<>();
    private static ArrayList<Game> gameList = new ArrayList<>();
    private float calculatePlayerWinRate;

    //构造器
    public GameSystem() {
        ArrayList<Player> playerList = new ArrayList<>();
        ArrayList<Game> gameList = new ArrayList<>();
    }

    //方法
    public ArrayList<Game> getGameList() {
        return gameList;
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    public float getCalculatePlayerWinRate() {
        return calculatePlayerWinRate;
    }

    public boolean checkPlayer(int pid) {
        for (int i = 0; i < playerList.size(); i++) {
            if (pid == playerList.get(i).getPid()) {
                return true;
            }
        }
        return false;
    }

    public boolean checkGame(int gid) {
        for (int i = 0; i < gameList.size(); i++) {
            if (gid == gameList.get(i).getGid()) {
                return true;
            }
        }
        return false;
    }

    public boolean addPlayer(Player player) {
        if (checkPlayer(player.getPid())) {
            return false;
        } else {
            playerList.add(player);
            return true;
        }
    }

    public boolean addGame(Game game) {
        int d = 0;
        for (int i = 0; i < gameList.size(); i++) {
            if (gameList.get(i).getGid() == game.getGid())
                return false;
        }
        int a = 0, b = 0;
        for (int i = 0; i < playerList.size(); i++) {
            if (game.getWhitePlayer().getPid() == playerList.get(i).getPid()) {
                a = 1;
            }
            if (game.getBlackPlayer().getPid() == playerList.get(i).getPid()) {
                b = 1;
            }
        }
        if (a + b == 2) {
            gameList.add(game);
            return true;
        } else {
            return false;
        }

    }

    public ArrayList<Game> listPlayerGame(int pid) {
        ArrayList<Game> gameArrayList = new ArrayList<Game>(0);
        for (int i = 0; i < gameList.size(); i++) {
            if (pid == gameList.get(i).getBlackPlayer().getPid() || pid == gameList.get(i).getWhitePlayer().getPid()) {


                gameArrayList.add(this.gameList.get(i));
            }
        }
        return gameArrayList;
    }
    public float calculatePlayerWinRate(int pid){
        int point=0;
        for(Game game:listPlayerGame(pid)){
            if(game.getWinnerPid()==pid){
                point++;
            }
        }
        if(point!=0){
            calculatePlayerWinRate=(float) point/listPlayerGame(pid).size();
        }else {
            calculatePlayerWinRate=0;
        }
        return calculatePlayerWinRate;
    }



}
