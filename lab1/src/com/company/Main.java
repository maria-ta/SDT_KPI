package com.company;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        System.out.println("Запуск тестового режиму");
        System.out.println(testMode());
        System.out.println("Запуск робочого режиму");
        normalMode();
    }
    private static int getInt(String signStr){
        Scanner in = new Scanner(System.in);
        int quantity = 1;
        while(true){
            System.out.println(signStr);
            if(in.hasNextInt()){
                quantity = in.nextInt();
                in.close();
                break;
            }
            else{
                System.out.println("ПОМИЛКА: Невірний формат вводу. Спробуйте ще раз");
                in.next();
            }
        }
        return quantity;
    }

    private static void normalMode(){
        int quantity = getInt("Введіть кількість (ціле число) білетів для генерації:");

        for (int i=0; i<quantity; i++){
            Ticket ticket = new Ticket(i+1);
            ticket.printTicket();
        }
    }

    private static boolean testMode(){
        int quantity = (int) (Math.random() * Math.pow(10, (int) (Math.random()*5)));
        System.out.println("Введіть кількість (ціле число) білетів для генерації:");
        System.out.println(quantity);
        for (int i=0; i<quantity; i++){
            Ticket ticket = new Ticket(i+1);
            if (!ticket.Test()) return false;
        }
        return true;
    }
}

class Question{
    public String[][] lists = {
            {
                "1.1 Вопрос",
                    "1.2 Вопрос",
                    "1.3 Вопрос",
                    "1.4 Вопрос",
            },
            {
                    "2.1 Вопрос",
                    "2.2 Вопрос"
            },
            {
                    "3.1 Вопрос",
                    "3.2 Вопрос",
                    "3.3 Вопрос",
                    "3.4 Вопрос",
                    "3.5 Вопрос"
            }
    };
    private int listNum;
    private int questionNum;
    //private String questionText;

    Question(int num){
        listNum = checkListNum(num);
        questionNum = genQuestionNum();
    }

    private int checkListNum(int num){
        if (num < 0) return 0;
        return Math.min(num, lists.length - 1);
    }

    public int genQuestionNum(){
        //questionNum -- from 0 to (int) 0.999999...*lists[num].length === lists[num].length - 1;
        return (int) (Math.random()*lists[listNum].length);
        //return 100;
    }

    public String getQuestion(){
        return lists[listNum][questionNum];
    }

    public boolean Test(){
        if (questionNum>lists[listNum].length - 1 || questionNum < 0) return false;
        else return true;
    }
}

class Ticket{
    private int ticketNum;
    private Question[] questions = new Question[3];

    Ticket(int num){
        ticketNum = num;
        for (int i=0; i<3; i++){
            questions[i] = new Question(i);
        }
    }
    public String getTicket(){
        StringBuilder result = new StringBuilder();
        for (int i=0; i<3; i++){
            result.append(questions[i].getQuestion());
            result.append("\n");
        }
        return result.toString();
    }

    public void printTicket(){
        System.out.println("Білет №" + (ticketNum));
        System.out.println(getTicket());
    }

    public boolean Test(){
        for (int i=0; i<3; i++){
            if (!questions[i].Test()) return false;
        }
        return true;
    }

}
