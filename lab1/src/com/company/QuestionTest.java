package com.company;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {

    @Test
    void genQuestionNum() {
        Question question = new Question(1);
        int questionNum = question.genQuestionNum();
        Assert.assertFalse("True", questionNum>question.lists[1].length - 1 || questionNum < 0);
        /*if (questionNum>question.lists[1].length - 1 || questionNum < 0) System.out.println(false);
        else System.out.println(true);
         */
    }
}