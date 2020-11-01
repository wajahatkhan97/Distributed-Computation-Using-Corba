import junit.framework.TestCase;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;

public class Testcasesforlogicclass extends TestCase {
//These test aren't extensive basically what they're simply checking if the files are being created successfully or not
    //One of the reason of doing this is because my functions are only out function and they are not dependent on any outside data
    //all the data is coming from .conf file.
private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);

    Logic test = new Logic();
    @BeforeEach
    public void initialize()
    {
        LOGGER.info("Executing all test cases: ");

        test = new Logic();
    }

    @Test
    public void testtop_ten() {
        //testing the return values if they're completeing the process

        test.sayhello(); //generate the file first and then check if generated correctly
        String[] name = {"Gerrit Bleumer", "Alex Biryukov", "Gerrit Bleumer", "Gerrit Bleumer"};
        String CSV_File = "src\\main\\resources\\top_ten.csv";
        BufferedReader br = null;
        String line ="";
        try{
            br=new BufferedReader(new FileReader(CSV_File));
            // while((line=br.readLine())!=null){
            line= br.readLine();
            for(int i=0;i<name.length;i++){
                line= br.readLine();
                String[]authors = line.split(",");
                //and we only want to read authors and their publisher counts
                authors[1] = authors[1].replace("\"", "");

                assertEquals(name[i],authors[1]);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    @Test
    public void testOne_author() {
        test.one_author();
        String[] name = {"A note on the paper by Murat Cenk and Ferruh Ozbudak Multiplication of polynomials modulo xn", "On the distribution of integer points on curves of genus zero."};
        String CSV_File = "src\\main\\resources\\one_author.csv";
        BufferedReader br = null;
        String line ="";
        try{
            br=new BufferedReader(new FileReader(CSV_File));
            // while((line=br.readLine())!=null){
            line= br.readLine();
            for(int i=0;i<name.length;i++){
                line= br.readLine();
                String[]authors = line.split(",");
                //and we only want to read authors and their publisher counts
                authors[0] = authors[0].replace("\"", "");

                assertEquals(name[i],authors[0]);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    @Test
    public void testAuthor_no_intereption()  {
       test.author_no_intereption();
        String[] name = {"Dongxiao Yu", "Subhash Suri"};
        String CSV_File = "src\\main\\resources\\without_intereption.csv";
        BufferedReader br = null;
        String line ="";
        try{
            br=new BufferedReader(new FileReader(CSV_File));
            // while((line=br.readLine())!=null){
            line= br.readLine();
            for(int i=0;i<name.length;i++){
                line= br.readLine();
                String[]authors = line.split(",");
                //and we only want to read authors and their publisher counts
                authors[0] = authors[0].replace("\"", "");

                assertEquals(name[i],authors[0]);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    @Test
    public void testTop_100() {
       test.top_100();

        String[] name = {"A note on the paper by Murat Cenk and Ferruh Ozbudak Multiplication of polynomials modulo xn", "On the distribution of integer points on curves of genus zero."};
        String CSV_File = "src\\main\\resources\\top_100.csv";
        BufferedReader br = null;
        String line ="";
        try{
            br=new BufferedReader(new FileReader(CSV_File));
            // while((line=br.readLine())!=null){
            line= br.readLine();
            for(int i=0;i<name.length;i++){
                line= br.readLine();
                String[]authors = line.split(",");
                //and we only want to read authors and their publisher counts
                authors[0] = authors[0].replace("\"", "");

                assertEquals(name[i],authors[0]);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
        //add 5th case for authors with maximum co-authors
    @Test
    public void testTop_100_withcoauthors()
    {
       test.top_100_co_authors();


        String[] name = {"Offline and online algorithms for single-minded selling problem.", "k-Capture in multiagent pursuit evasion"};
        String CSV_File = "src\\main\\resources\\top_100_withmostco-authors.csv";
        BufferedReader br = null;
        String line ="";
        try{
            br=new BufferedReader(new FileReader(CSV_File));
            // while((line=br.readLine())!=null){
            line= br.readLine();
            for(int i=0;i<name.length;i++){
                line= br.readLine();
                String[]authors = line.split(",");
                //and we only want to read authors and their publisher counts
                authors[0] = authors[0].replace("\"", "");

                assertEquals(name[i],authors[0]);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}