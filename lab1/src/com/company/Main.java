package com.company;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
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
}

class Question{
    public final String[][] lists = {
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

    Question(int num){
        listNum = checkListNum(num);
        questionNum = genQuestionNum();
    }

    private int checkListNum(int num){
        if (num < 0) return 0;
        return Math.min(num, lists.length - 1);
    }

    public int genQuestionNum(){
        return (int) (Math.random()*lists[listNum].length);
    }

    public String getQuestion(){
        return lists[listNum][questionNum];
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

}
