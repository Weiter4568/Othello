package com.example.othello;


import Logic.Game;
import Logic.GameSystem;
import Logic.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;

public class OthelloController {
    private Stage stage;
    private Scene scene;
    private int clickRowIndex;
    private int clickColumnIndex;
    private static Game game;
    @FXML
    GridPane gridPane = new GridPane();

    @FXML
    Button personAgainstMode;

    @FXML
    public void onClickPVP (ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Othello.class.getResource("loginScene.fxml"));
        scene = new Scene(fxmlLoader.load());
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
//        initGrid();
    }

    /*public void initGrid() {
        *//*for (int i = 0 ; i < 8 ; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setHgrow(Priority.SOMETIMES);
            gridPane.getColumnConstraints().add(colConstraints);
        }
        for (int i = 0 ; i < 8 ; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setVgrow(Priority.SOMETIMES);
            gridPane.getRowConstraints().add(rowConstraints);
        }*//*
        System.out.println("yes");
        for(int i = 0; i < 8; i++ ) {
            for(int j = 0; j < 8; j++) {
                addPane(j, i);
            }
        }
    }*/
    /*// 给GridPane上每个单元格添加pane
    public void addPane(int rowIndex, int columnIndex) {
        Pane pane = new Pane();
        pane.setOnMouseClicked(event -> {
            System.out.println("yes clicked");
            clickRowIndex = gridPane.getRowIndex(pane);
            clickColumnIndex = gridPane.getColumnIndex(pane);
            System.out.printf("Mouse clicked [%d, %d]",clickRowIndex, clickColumnIndex);
        });
        gridPane.setRowIndex(pane, rowIndex);
        gridPane.setColumnIndex(pane, rowIndex);
        gridPane.add(pane, columnIndex, rowIndex);
    }
*/
    public OthelloController() {
        game = new Game(null,null,null);
    }

    public void setGame(String name, Player whitePlayer, Player blackPlayer) {
        game = new Game(name, whitePlayer, blackPlayer);
    }

    @FXML
    public void mouseClicked(MouseEvent event) {
        int columnIndex = (int)(event.getX()/56.25);
        int rowIndex = (int)(event.getY()/56.25);
        System.out.printf("Mouse clicked [%d, %d]\n", rowIndex, columnIndex);
        game.howDo(game.getCurrentFigure(), rowIndex, columnIndex);
        repaintBoard();
    }

    // add a whitePiece
    public void addWhitePiece(int rowIndex, int columnIndex){
        ImageView imageView;
        Image image = new Image("file:/Users/weiter/IdeaProjects/Othello/src/main/resources/com/example/othello/whitepiece.png");
        imageView = new ImageView(image);
        imageView.setFitWidth(56.25);
        imageView.setFitHeight(56.25);
        imageView.setVisible(true);
        gridPane.add(imageView, columnIndex, rowIndex);
    }
    // add a blackPiece
    public void addBlackPiece(int rowIndex, int columnIndex){
        ImageView imageView;
        Image image = new Image("file:/Users/weiter/IdeaProjects/Othello/src/main/resources/com/example/othello/blackpiece.png");
        imageView = new ImageView(image);
        imageView.setFitWidth(56.25);
        imageView.setFitHeight(56.25);
        imageView.setVisible(true);
        gridPane.add(imageView, columnIndex, rowIndex);
    }

    @FXML
    Button sureButton;
    @FXML
    TextField whitePlayerName;
    @FXML
    TextField blackPlayerName;
    @FXML
    TextField gameName;

    // 登陆界面的sureButton
    @FXML
    public void onClickSureButton (ActionEvent event) throws IOException {
        setGame(gameName.getText(), new Player(whitePlayerName.getText(), 1), new Player(blackPlayerName.getText(), -1));
        System.out.println(game.getBlackPlayer().getName());
        System.out.println(whitePlayerName.getText());
        System.out.println(blackPlayerName.getText());

        FXMLLoader fxmlLoader = new FXMLLoader(Othello.class.getResource("gameScene.fxml"));
        scene = new Scene(fxmlLoader.load());
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        System.out.println(Arrays.deepToString(game.getBoard()));
        repaintBoard();
    }

    // 重新绘制棋盘，根据目前的board
    @FXML
    public void repaintBoard() {
        System.out.println("yes" );
//        System.out.println("yes");
        //gridPane.getChildren().clear();
        gridPane.setGridLinesVisible(true);
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
//                addPane(j, i);
                if(game.getBoard()[i][j] == 1){
                    ImageView imageView;
                    Image image = new Image("file:/Users/weiter/IdeaProjects/Othello/src/main/resources/com/example/othello/whitepiece.png");
                    imageView = new ImageView(image);
                    imageView.setFitWidth(56.25);
                    imageView.setFitHeight(56.25);
                    gridPane.add(imageView, j, i);
                    System.out.printf("%d+%d\n",i,j);
                }
                else if(game.getBoard()[i][j] == -1) {
                    ImageView imageView;
                    Image image = new Image("file:/Users/weiter/IdeaProjects/Othello/src/main/resources/com/example/othello/blackpiece.png");
                    imageView = new ImageView(image);
                    imageView.setFitHeight(56.25);
                    imageView.setFitWidth(56.25);
                    gridPane.add(imageView, j, i);
                    System.out.printf("%d+%d\n",i,j);
                }
            }
        }
    }

    // game界面的testButton
    @FXML
    Button testButton;
    @FXML
    public void onClickTestButton( ActionEvent event) throws IOException {
        for(int i = 0; i < 8;i ++) {
            for(int j = 0; j < 8; j++) {
                ImageView imageView;
                Image image = new Image("file:/Users/weiter/IdeaProjects/Othello/src/main/resources/com/example/othello/blackpiece.png");
                imageView = new ImageView(image);
                imageView.setFitHeight(56.25);
                imageView.setFitWidth(56.25);
                gridPane.add(imageView, j, i);
            }
        }
    }
}
