package com.company;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;


@Retention(RetentionPolicy.RUNTIME)
@interface shouldRunIt{
    String info() default "";
}

class Stack{
    protected static int defMaxSize = 10;
    protected int maxSize;
    protected int[] stackArray;
    protected int top;

    public Stack(){
        this.maxSize = defMaxSize;
        stackArray = new int[maxSize];
        top = -1;
    }

    public Stack(int max) {
        this.maxSize = max;
        stackArray = new int[maxSize];
        top = -1;
    }

    public void push(int element) {
        if(!isFull()) stackArray[++top] = element;
        //System.out.println("Push " + element);
    }

    public int pop() {
        int ret = stackArray[top];
        if (top>0) top--;
        //System.out.println("Pop " + ret);
        return ret;
    }
    public int read() {
        return stackArray[top];
    }
    public boolean isEmpty() {
        return (top == -1);
    }
    public boolean isFull() {
        return (top == maxSize - 1);
    }
}

class Queue extends Stack{

    public Queue() {
        this.maxSize = defMaxSize;
        stackArray = new int[maxSize];
        top = -1;
    }

    public Queue(int max) {
        this.maxSize = max;
        stackArray = new int[maxSize];
        top = -1;
    }

    @shouldRunIt
    public void logAll(){
        System.out.println(toString());
    }

    @Override
    public int pop(){
        int first = stackArray[0];
        for (int i=0; i<top; i++){
            stackArray[i] = stackArray[i+1];
        }
        top--;
        return first;
    }

    public String toString(){
        StringBuilder result = new StringBuilder();
        for (int i=0; i<=top; i++){
            result.append(stackArray[i]);
        }
        return result.toString();
    }

}



public class Main {

    public static void main(String[] args) {
	// write your code here
        Stack s = new Stack(7);
        Queue q = new Queue(7);

        for(int i=0; i<7; i++){
            s.push(i+1);
            q.push(i+1);
        }
        System.out.println(" ");
        for(int i=0; i<7; i++){
            System.out.print(q.pop());
        }
        for(int i=0; i<7; i++){
            System.out.print(s.pop());
            q.push(i+1);
        }

        Class queueClass = Queue.class;
        System.out.println("\nName of queue class is " + queueClass.getName());
        System.out.println("\nName of queue's super class is " + queueClass.getSuperclass().getName());
        try {
            Method[] methods = queueClass.getMethods();

            System.out.println("Methods found: " + methods.length);
            for (Method method: methods){
                shouldRunIt ann = method.getAnnotation(shouldRunIt.class);
                System.out.println("                Name: " + method.getName()
                + "\nSpec: " + Modifier.toString(method.getModifiers()) + "\nParams: " + Arrays.toString(method.getParameterTypes()));

                if (ann != null) {
                    System.out.println("Invoking this method");
                    method.invoke(q);
                }
                else System.out.println("No shouldRunIt annotation");
            }
        }
        catch (SecurityException | IllegalAccessException | InvocationTargetException e) {};

    }



}
