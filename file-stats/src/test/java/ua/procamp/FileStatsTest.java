package ua.procamp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.OptionalDouble;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class FileStatsTest {


    @Test
    public void testCreateFileStatsFromExistingFile() {
        FileStats fileStats = FileStats.from("sotl.txt");
        //FileStats fileStats = FileStats.from(null);
    }

    @Test(expected = FileStatsException.class)
    public void testCreateFileStatsFromNonExistingFile() {
        FileStats fileStats = FileStats.from("blahblah.txt");
    }

    @Test
    public void testGetCharCount() {
        FileStats lambdaArticleFileStats = FileStats.from("sotl.txt");
        FileStats springCloudArticleFileStats = FileStats.from("scosb.txt");

        int aCharCountInLambdaArticle = lambdaArticleFileStats.getCharCount('a');
        int bCharCountInSpringArticle = springCloudArticleFileStats.getCharCount('b');

        assertEquals(2345, aCharCountInLambdaArticle);
        assertEquals(4, bCharCountInSpringArticle);
    }

    @Test
    public void testGetMostPopularCharacter() {
        FileStats lambdaArticleFileStats = FileStats.from("sotl.txt");
        FileStats springCloudArticleFileStats = FileStats.from("scosb.txt");

        char mostPopularCharacterInLambdaArticle = lambdaArticleFileStats.getMostPopularCharacter();
        char mostPopularCharacterInSpringArticle = springCloudArticleFileStats.getMostPopularCharacter();

        System.out.println(mostPopularCharacterInSpringArticle);

        assertEquals('e', mostPopularCharacterInLambdaArticle);
        assertEquals('e', mostPopularCharacterInSpringArticle);
    }

    @Test
    public void testContainsCharacter() {
        FileStats lambdaArticleFileStats = FileStats.from("sotl.txt");
        FileStats springCloudArticleFileStats = FileStats.from("scosb.txt");

        boolean lambdaArticleContainsExistingCharacter = lambdaArticleFileStats.containsCharacter('a');
        boolean lambdaArticleContainsWhitespace = lambdaArticleFileStats.containsCharacter(' ');
        boolean springArticleContainsExistingCharacter = springCloudArticleFileStats.containsCharacter('b');
        boolean springArticleContainsWhitespace = springCloudArticleFileStats.containsCharacter(' ');

        assertTrue(lambdaArticleContainsExistingCharacter);
        assertFalse(lambdaArticleContainsWhitespace);
        assertTrue(springArticleContainsExistingCharacter);
        assertFalse(springArticleContainsWhitespace);
    }

    @Test(expected = NoSuchElementException.class)
    public void testSomethingFalse(){
        HashMap<Character, Integer> myHashMap = new HashMap<Character, Integer>();
        for (int i=0; i<-1; i++){
            myHashMap.put((char)(65 + i), (int)(Math.random()*20));
        }
        System.out.println(myHashMap.toString());
        System.out.println(myHashMap.values().toString());
        double average = myHashMap.values().stream().mapToInt(Integer::intValue).average().orElseThrow(()->{
            NoSuchElementException e = new NoSuchElementException("No characters");
            System.out.println(e.toString());
            return e;
        });

    }
    @Test
    public void testSomethingTrue(){
        HashMap<Character, Integer> myHashMap = new HashMap<Character, Integer>();
        for (int i=0; i<28; i++){
            myHashMap.put((char)(65 + i), (int)(Math.random()*20));
        }
        System.out.println(myHashMap.toString());
        System.out.println(myHashMap.values().toString());

        int sumStream = myHashMap.values().stream().mapToInt(Integer::intValue).sum();
        double average = myHashMap.values().stream().mapToInt(Integer::intValue).average().orElseThrow(NoSuchElementException::new);

        System.out.println(average);
        int sumFor = 0;
        for (int count : myHashMap.values()){
            sumFor += count;
        }
        System.out.println(sumStream);
        System.out.println(sumFor);
        assertEquals(sumFor, sumStream);
    }
}
