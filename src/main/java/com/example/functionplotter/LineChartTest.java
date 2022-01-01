package com.example.functionplotter;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.*;

public class LineChartTest extends Application
{
    final String[] Atr = new String[3];
    GridPane root = new GridPane();
    @Override
    public void start(Stage primaryStage) throws Exception {
        // TODO Auto-generated method stub

        Atr[0]="";
        Atr[1]="";
        Atr[2]="";
        Label Func=new Label("Function");
        Label MaxX = new Label("Max X");
        Label MinX = new Label("Min X");
        TextField tf1=new TextField();
        TextField tf2=new TextField();
        TextField tf3=new TextField();
        Button b = new Button("Solve");

        root.addRow(0, Func, tf1);
        root.addRow(1, MaxX, tf2);
        root.addRow(2, MinX, tf3);
        root.addRow(3,new Label(" "), b);
        Scene scene = new Scene(root,600,600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("LineChart");
        primaryStage.show();
        b.setOnAction(new EventHandler<ActionEvent>() {


            public void handle(ActionEvent arg0) {
                // TODO Auto-generated method stub
               Atr[0] =tf1.getText();
               Atr[1] =tf2.getText();
               Atr[2] =tf3.getText();

                drow( primaryStage);
            }
        } );


    }
    public static void main(String[] args) {
        launch(args);
    }

     void drow(Stage primaryStage){
        char[] fun = new char[0];
        if(!Atr.equals(null)){
            if(!Atr[0].equals(null)){
                fun =Atr[0].toCharArray();
            }
        }
        int count =1;
        for (char c : fun) {
            if (c == '+' || c == '-') {
                count++;
            }
        }
        long max=0;
        long min=0;
        char[]mxtx=Atr[1].toCharArray();
        for (char c : mxtx) {
            max *= 10;
            max += c - '0';
        }
        char[]mntx=Atr[2].toCharArray();
        for (char c : mntx) {
            min *= 10;
            min += c - '0';
        }




        List<mypair>p=new ArrayList<mypair>();



        boolean mark =false;
        for(int i=0;i<Atr[0].length();i++){
            mypair px=new mypair();
            px.neg=mark;
            mark=false;
            if (fun[i] != '+' && fun[i] != '-') {

                while(i<Atr[0].length()&&Character.isDigit(fun[i])){
                    px.a*=10;
                    px.a+=fun[i]-'0';
                    i++;
                }
                while(i<Atr[0].length()&&!Character.isDigit(fun[i])&&fun[i]!='-'&&fun[i]!='+'){
                    i++;
                }
                while(i<Atr[0].length()&&Character.isDigit(fun[i])){
                    px.p*=10;
                    px.p+=fun[i]-'0';
                    i++;
                }

            }
            else{


                if(fun[i]=='-')mark=true;

            }
            p.add(px);
        }
        int lilinth= (int) (max-min+1);

        List<mylist>lis=new ArrayList<mylist>();
        long mxy=-9223372036854775808L,mny=9223372036854775807L;
        for(int i=0;i<lilinth;i++){
            mylist ml =new mylist();
            for (LineChartTest.mypair mypair : p) {
                if (!mypair.neg) {
                    ml.y += mypair.calc(i + min);
                } else {
                    ml.y -= mypair.calc(i + min);
                }
            }
            mxy=Math.max(ml.y,mxy);
            mny=Math.min(ml.y,mny);
            ml.x=min+i;
            lis.add(ml);
        }

        //Defining Axis
        final NumberAxis xaxis = new NumberAxis(min,max,1);
        final NumberAxis yaxis = new NumberAxis(mny,mxy,1);

        //Defining Label for Axis
        xaxis.setLabel("X");
        yaxis.setLabel("Y");

        //Creating the instance of linechart with the specified axis
        LineChart linechart = new LineChart(xaxis,yaxis);

        //creating the series
        XYChart.Series series = new XYChart.Series();

        //setting name and the date to the series
        series.setName("Stock Analysis");
        for(mylist ml:lis) {

            series.getData().add(new XYChart.Data(ml.x, ml.y));
        }

        //adding series to the linechart
        linechart.getData().add(series);

        //setting Group and Scene
        //  Group root = new Group(linechart);



        root.addRow(4,new Label(" "), linechart);
        primaryStage.show();
    }


    static class mypair{
        public int a,p;
        public boolean neg=false;
        public mypair(){
            a=0;
            p=1;

        }
       public long calc(long x){
            long sol=1;
            for(int i=0;i<p;i++){
                sol*=x;
            }
            sol*=a;
            return sol;
       }
    }
    static class mylist{
        public long x=0,y=0;
        public mylist(){

        }
    }

}
