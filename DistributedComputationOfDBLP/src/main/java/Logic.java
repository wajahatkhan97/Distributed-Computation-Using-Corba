import com.opencsv.CSVWriter;

import com.typesafe.config.Config;
import dataprocessing.dblp_DataPOA;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

//TODO
/*
ALMOST FINISHED THE HOMEWORK REMEMBER TO CHANGE DBLP FILE TO THE ACTUAL ONE AND DON'T FORGET TO CHANGE THE TAGS
Also fix the test cases and also test your project from command line
BONUS CREDIT STILL LEFT DEPLOYEMENT CHECK ABHIJEETS LINKS
 */
public class Logic  extends dblp_DataPOA {
    public static Map<String,ArrayList<String>> author_venue; //stores author and venue as key value pair
    public static Map<String,Integer> keep_counts; //stores the author and their publication counts
    public static Map<String,ArrayList<String>> author_publications; //stores the all the authors with venue of single publication
    public static Map<String ,ArrayList<Integer>> author_with_no_interruption; //stores the all the authors with venue of single publication


    public static ArrayList<String> contains_venuenames;
    public static ArrayList<String> contains_author_names;
    public static ArrayList<String> contains_author_with_no_intereption;
    public static ArrayList<String> contains_venues;
    public static ArrayList<String> titles;
    public static ArrayList<Integer> years;
    private static final Logger LOGGER = LoggerFactory.getLogger(Logic.class);

    //File file = new File("dblp.xml");

    @Override
    public String sayhello() {
        LOGGER.info("TOP TEN AUTHORS: ");
        author_venue = new HashMap<>();
        keep_counts = new HashMap<>();
        author_with_no_interruption = new HashMap<>();
        author_publications = new HashMap<>();
        contains_venuenames = new ArrayList<>();
        //      contains_author_names = new ArrayList<>();
        titles = new ArrayList<>();

        contains_venues = new ArrayList<>();

        contains_venues = new ArrayList<>();
       String datacentersConfig = ApplicationConfig.DBLP.getString(Applicationconst.DBLP);
        File file = new File(datacentersConfig); //have the path via TYPESAFE CONFIG
   ////---------------from here to the end change according to new
        LOGGER.info("LOADING XML FILE: ------");

        try {
            if (file != null) {
                String author = "";
                String venue = "";
                int year = 0;
                String title = "";
                //gives document builder
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                //instance of builder to parse xml file
                Document doc = db.parse(file);
                doc.getDocumentElement().normalize();
              //  System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
                NodeList nodelist = doc.getElementsByTagName("article");


                for (int i = 0; i < nodelist.getLength(); i++) {
                    contains_author_names = new ArrayList<>();
                    contains_author_with_no_intereption = new ArrayList<>();
                    years = new ArrayList<>();
                    Node node = nodelist.item(i);
                  //  System.out.println("Node name: " + node.getNodeName());
                    if (node.getNodeType() == Node.ELEMENT_NODE) {

                        Element element = (Element) node;
                        //
                     //   System.out.println();
                        for (int j = 0; j < element.getElementsByTagName("author").getLength(); j++) {
                            if (element.getElementsByTagName("author").item(j) != null) {

                                author = element.getElementsByTagName("author").item(j).getTextContent();
                           //     System.out.println("Author " + author);
                                contains_author_names.add(author);
                                contains_author_with_no_intereption.add(author);
                            }
                        }
                        if (element.getElementsByTagName("title").item(0) != null) {
                            title = element.getElementsByTagName("title").item(0).getTextContent();
                            titles.add(element.getElementsByTagName("title").item(0).getTextContent());
                        //    System.out.println("title " + element.getElementsByTagName("title").item(0).getTextContent());
                        }
                        if (element.getElementsByTagName("year").item(0) != null) {
                            years.add(Integer.parseInt(element.getElementsByTagName("year").item(0).getTextContent()));
                       //     System.out.println("year " + element.getElementsByTagName("year").item(0).getTextContent());
                        }
                        if (element.getAttribute("key") != null) {
                            venue = element.getAttribute("key");
                        //    System.out.println("Key " + venue);
                        }
                        if (element.getElementsByTagName("journal").item(0) != null) {
                       //     System.out.println("journal " + element.getElementsByTagName("journal").item(0).getTextContent());
                        }

                        if (element.getElementsByTagName("booktitle").item(0) != null) {
                    //        System.out.println("journal " + element.getElementsByTagName("booktitle").item(0).getTextContent());
                        }

                        if (element.getElementsByTagName("ee").item(0) != null) {

                        //    System.out.println("crossref " + element.getElementsByTagName("ee").item(0).getTextContent());
                        }
                        //handles duplicates
                        if (author_venue.containsKey(venue)) {
                            for (int j = 0; j < author_venue.get(venue).size(); j++) {
                                contains_author_names.add(author_venue.get(venue).get(j));

                            }
                            author_venue.put(venue, contains_author_names);
                        }

                        for (int j = 0; j < contains_author_with_no_intereption.size(); j++) {
                            if (author_with_no_interruption.containsKey(contains_author_with_no_intereption.get(j))) {
                                ArrayList<Integer> contains_years = new ArrayList<>();

                                    /*
                                    so what you're saying is that put a list of years as a value and then add years to the respective authors
                                     */
                                contains_years = author_with_no_interruption.get(contains_author_with_no_intereption.get(j));
                                for (int k = 0; k < contains_years.size(); k++) {
                                    years.add(contains_years.get(k));
                                }
                                author_with_no_interruption.put(contains_author_with_no_intereption.get(j), years); //calculate authors
                            }
                            author_with_no_interruption.put(contains_author_with_no_intereption.get(j), years); //calculate authors
                        }
                        //else {
                        author_venue.put(venue, contains_author_names);
                        contains_venuenames.add(author); //actually contains_author names
                        contains_venues.add(venue);


                        //}
                    }
                }

                ///ADD THE INCOLLECTION TAG HERE////

                 nodelist = doc.getElementsByTagName("incollection");


                for (int i = 0; i < nodelist.getLength(); i++) {
                    contains_author_names = new ArrayList<>();
                    contains_author_with_no_intereption = new ArrayList<>();
                    years = new ArrayList<>();
                    Node node = nodelist.item(i);
                  //  System.out.println("Node name: " + node.getNodeName());
                    if (node.getNodeType() == Node.ELEMENT_NODE) {

                        Element element = (Element) node;
                        //
             //           System.out.println();
                        for (int j = 0; j < element.getElementsByTagName("author").getLength(); j++) {
                            if (element.getElementsByTagName("author").item(j) != null) {

                                author = element.getElementsByTagName("author").item(j).getTextContent();
                             //   System.out.println("Author " + author);
                                contains_author_names.add(author);
                                contains_author_with_no_intereption.add(author);
                            }
                        }
                        if (element.getElementsByTagName("title").item(0) != null) {
                            title = element.getElementsByTagName("title").item(0).getTextContent();
                            titles.add(element.getElementsByTagName("title").item(0).getTextContent());
                          //  System.out.println("title " + element.getElementsByTagName("title").item(0).getTextContent());
                        }
                        if (element.getElementsByTagName("year").item(0) != null) {
                            years.add(Integer.parseInt(element.getElementsByTagName("year").item(0).getTextContent()));
                          //  System.out.println("year " + element.getElementsByTagName("year").item(0).getTextContent());
                        }
                        if (element.getAttribute("key") != null) {
                            venue = element.getAttribute("key");
                        //    System.out.println("Key " + venue);
                        }
                        if (element.getElementsByTagName("journal").item(0) != null) {
                      //      System.out.println("journal " + element.getElementsByTagName("journal").item(0).getTextContent());
                        }

                        if (element.getElementsByTagName("booktitle").item(0) != null) {
                        //    System.out.println("journal " + element.getElementsByTagName("booktitle").item(0).getTextContent());
                        }

                        if (element.getElementsByTagName("ee").item(0) != null) {

                        //    System.out.println("crossref " + element.getElementsByTagName("ee").item(0).getTextContent());
                        }
                        //handles duplicates
                        if (author_venue.containsKey(venue)) {
                            for (int j = 0; j < author_venue.get(venue).size(); j++) {
                                contains_author_names.add(author_venue.get(venue).get(j));

                            }
                            author_venue.put(venue, contains_author_names);
                        }

                        for (int j = 0; j < contains_author_with_no_intereption.size(); j++) {
                            if (author_with_no_interruption.containsKey(contains_author_with_no_intereption.get(j))) {
                                ArrayList<Integer> contains_years = new ArrayList<>();

                                    /*
                                    so what you're saying is that put a list of years as a value and then add years to the respective authors
                                     */
                                contains_years = author_with_no_interruption.get(contains_author_with_no_intereption.get(j));
                                for (int k = 0; k < contains_years.size(); k++) {
                                    years.add(contains_years.get(k));
                                }
                                author_with_no_interruption.put(contains_author_with_no_intereption.get(j), years); //calculate authors
                            }
                            author_with_no_interruption.put(contains_author_with_no_intereption.get(j), years); //calculate authors
                        }
                        //else {
                        author_venue.put(venue, contains_author_names);
                        contains_venuenames.add(author); //actually contains_author names

                        contains_venues.add(venue);


                        //}
                    }
                }

                //Inproceeding/////////////

                ///ADD THE INCOLLECTION TAG HERE////

                nodelist = doc.getElementsByTagName("inproceedings");


                for (int i = 0; i < nodelist.getLength(); i++) {
                    contains_author_names = new ArrayList<>();
                    contains_author_with_no_intereption = new ArrayList<>();
                    years = new ArrayList<>();
                    Node node = nodelist.item(i);
                    //  System.out.println("Node name: " + node.getNodeName());
                    if (node.getNodeType() == Node.ELEMENT_NODE) {

                        Element element = (Element) node;
                        //
               //         System.out.println();
                        for (int j = 0; j < element.getElementsByTagName("author").getLength(); j++) {
                            if (element.getElementsByTagName("author").item(j) != null) {

                                author = element.getElementsByTagName("author").item(j).getTextContent();
                                //   System.out.println("Author " + author);
                                contains_author_names.add(author);
                                contains_author_with_no_intereption.add(author);
                            }
                        }
                        if (element.getElementsByTagName("title").item(0) != null) {
                            title = element.getElementsByTagName("title").item(0).getTextContent();
                            titles.add(element.getElementsByTagName("title").item(0).getTextContent());
                            //  System.out.println("title " + element.getElementsByTagName("title").item(0).getTextContent());
                        }
                        if (element.getElementsByTagName("year").item(0) != null) {
                            years.add(Integer.parseInt(element.getElementsByTagName("year").item(0).getTextContent()));
                            //  System.out.println("year " + element.getElementsByTagName("year").item(0).getTextContent());
                        }
                        if (element.getAttribute("key") != null) {
                            venue = element.getAttribute("key");
                            //    System.out.println("Key " + venue);
                        }
                        if (element.getElementsByTagName("journal").item(0) != null) {
                            //      System.out.println("journal " + element.getElementsByTagName("journal").item(0).getTextContent());
                        }

                        if (element.getElementsByTagName("booktitle").item(0) != null) {
                            //    System.out.println("journal " + element.getElementsByTagName("booktitle").item(0).getTextContent());
                        }

                        if (element.getElementsByTagName("ee").item(0) != null) {

                            //    System.out.println("crossref " + element.getElementsByTagName("ee").item(0).getTextContent());
                        }
                        //handles duplicates
                        if (author_venue.containsKey(venue)) {
                            for (int j = 0; j < author_venue.get(venue).size(); j++) {
                                contains_author_names.add(author_venue.get(venue).get(j));

                            }
                            author_venue.put(venue, contains_author_names);
                        }

                        for (int j = 0; j < contains_author_with_no_intereption.size(); j++) {
                            if (author_with_no_interruption.containsKey(contains_author_with_no_intereption.get(j))) {
                                ArrayList<Integer> contains_years = new ArrayList<>();

                                    /*
                                    so what you're saying is that put a list of years as a value and then add years to the respective authors
                                     */
                                contains_years = author_with_no_interruption.get(contains_author_with_no_intereption.get(j));
                                for (int k = 0; k < contains_years.size(); k++) {
                                    years.add(contains_years.get(k));
                                }
                                author_with_no_interruption.put(contains_author_with_no_intereption.get(j), years); //calculate authors
                            }
                            author_with_no_interruption.put(contains_author_with_no_intereption.get(j), years); //calculate authors
                        }
                        //else {
                        author_venue.put(venue, contains_author_names);
                        contains_venuenames.add(author); //actually contains_author names

                        contains_venues.add(venue);


                        //}
                    }
                }

            } else {
                throw new EmptyStackException();
            }
        } catch (Exception e) {
            e.getStackTrace();
        } //--------
        try {
            // create FileWriter object with file as parameter
            String datacentersConfig1 = ApplicationConfig.DBLP.getString(Applicationconst.TOP_TEN); //typesafe config
            LOGGER.info("LOADING CSV FILE: ------");

            FileWriter outputfile = new FileWriter(datacentersConfig1);
            Map<String, ArrayList<String>> contains_name = new HashMap<>();

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile);

            // adding header to csv
            String[] header = {"Venue", "Author", "counts"};
            writer.writeNext(header);

            // add data to csv
//TOP_TEN_AUTHOR
            contains_name = new HashMap<>();
            Map<String, Integer> multiple_publications = new HashMap<>();

            int count = 0;
            ArrayList<String> print_array = new ArrayList<>();
            ArrayList<String> duplicates = new ArrayList<>();
            ArrayList<String> duplicates_venue = new ArrayList<>();
            String venue = "";
            String name = "";
            ArrayList<String> author_name = null;
            //calculate the top 10 author of each venue
            LOGGER.info("GENERATING CSV FILE: ------");

            for (int i = 0; i < author_venue.size(); i++) {
                author_name = new ArrayList<>();
                venue = contains_venues.get(i);
                author_name = author_venue.get(contains_venues.get(i));
                // author_name.add(contains_venue.get(i)); //gets me the author name
                count = 0;
                if (contains_name.containsKey(venue)) { //so by doing this we are collecting all the authors of each venue in an array
                    author_name = contains_name.get(venue);
                    author_name.add(contains_venues.get(i));
                    contains_name.put(venue, author_name);
                } else {
                    contains_name.put(venue, author_name);
                }
            }
            for (int j = 0; j < contains_name.size(); j++) {
                venue = contains_venues.get(j);
                print_array = contains_name.get(venue);

                for (int i = 0; i < print_array.size(); i++) {
                    count = 0;
                    if (duplicates.contains(print_array.get(i))) {  //here you can count the duplicates and then you know how many duplicates
                            if(!multiple_publications.isEmpty()) {
                                if (multiple_publications.get(print_array.get(i)) > 5) {
                                    String[] data1 = {venue, print_array.get(i), Integer.toString(multiple_publications.get(print_array.get(i)))};
                                    writer.writeNext(data1);
                                }
                            }
                        if (duplicates_venue.contains(venue)) //meaning same venue publisher
                        {
                            count = multiple_publications.get(print_array.get(i));
                            count++;
                            multiple_publications.put(print_array.get(i), count);
                        //    String[] data1 = {venue, print_array.get(i), Integer.toString(multiple_publications.get(print_array.get(i)))};
                       //     writer.writeNext(data1);
                        } else {
                            count++;
                            multiple_publications.put(print_array.get(i), count);
                            //String[] data1 = {venue, print_array.get(i), Integer.toString(multiple_publications.get(print_array.get(i)))};
                         //   writer.writeNext(data1);
                        }
                    } else {
                        count++;
                        multiple_publications.put(print_array.get(i), count);
                        duplicates.add(print_array.get(i));
                        duplicates_venue.add(venue);
                     //   String[] data1 = {venue, print_array.get(i), Integer.toString(multiple_publications.get(print_array.get(i)))};
                      //  writer.writeNext(data1);
                    }
                }

            }


//
            // closing writer connection
            writer.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "Top_Ten_Authors";

    } //1

    @Override
    public String one_author() {

        author_venue = new HashMap<>();
        keep_counts = new HashMap<>();
        author_with_no_interruption = new HashMap<>();
        author_publications = new HashMap<>();
        contains_venuenames = new ArrayList<>();
        //      contains_author_names = new ArrayList<>();
        titles = new ArrayList<>();

        contains_venues = new ArrayList<>();

        contains_venues = new ArrayList<>();
        titles = new ArrayList<>();
        String datacentersConfig = ApplicationConfig.DBLP.getString(Applicationconst.DBLP); //typesafe config

        File file = new File(datacentersConfig);
        LOGGER.info("LOADING XML FILE: ------");

        try {
            if (file != null) {
                String author = "";
                String venue = "";
                int year = 0;
                String title = "";
                //gives document builder
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                //instance of builder to parse xml file
                Document doc = db.parse(file);
                doc.getDocumentElement().normalize();
               // System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
                NodeList nodelist = doc.getElementsByTagName("article");


                for (int i = 0; i < nodelist.getLength(); i++) {
                    contains_author_names = new ArrayList<>();
                    contains_author_with_no_intereption = new ArrayList<>();
                    years = new ArrayList<>();
                    Node node = nodelist.item(i);
                  //  System.out.println("Node name: " + node.getNodeName());
                    if (node.getNodeType() == Node.ELEMENT_NODE) {

                        Element element = (Element) node;
                        //
                     //   System.out.println();
                        for (int j = 0; j < element.getElementsByTagName("author").getLength(); j++) {
                            if (element.getElementsByTagName("author").item(j) != null) {

                                author = element.getElementsByTagName("author").item(j).getTextContent();
                            //    System.out.println("Author " + author);
                                contains_author_names.add(author);
                                contains_author_with_no_intereption.add(author);
                            }
                        }
                        if (element.getElementsByTagName("title").item(0) != null) {
                            title = element.getElementsByTagName("title").item(0).getTextContent();
                            titles.add(element.getElementsByTagName("title").item(0).getTextContent());
                         //   System.out.println("title " + element.getElementsByTagName("title").item(0).getTextContent());
                        }
                        if (element.getElementsByTagName("year").item(0) != null) {
                            years.add(Integer.parseInt(element.getElementsByTagName("year").item(0).getTextContent()));
                        //    System.out.println("year " + element.getElementsByTagName("year").item(0).getTextContent());
                        }
                        if (element.getAttribute("key") != null) {
                            venue = element.getAttribute("key");
                       //     System.out.println("Key " + venue);
                        }
                        if (element.getElementsByTagName("journal").item(0) != null) {
                        //    System.out.println("journal " + element.getElementsByTagName("journal").item(0).getTextContent());
                        }

                        if (element.getElementsByTagName("booktitle").item(0) != null) {
                       //     System.out.println("journal " + element.getElementsByTagName("booktitle").item(0).getTextContent());
                        }

                        if (element.getElementsByTagName("ee").item(0) != null) {

                      //      System.out.println("crossref " + element.getElementsByTagName("ee").item(0).getTextContent());
                        }
                        //handles duplicates

                        author_publications.put(title, contains_author_names);


                        //}
                    }
                }

                ///ADD THE INCOLLECTION TAG

                nodelist = doc.getElementsByTagName("incollection");


                for (int i = 0; i < nodelist.getLength(); i++) {
                    contains_author_names = new ArrayList<>();
                    contains_author_with_no_intereption = new ArrayList<>();
                    years = new ArrayList<>();
                    Node node = nodelist.item(i);
                   // System.out.println("Node name: " + node.getNodeName());
                    if (node.getNodeType() == Node.ELEMENT_NODE) {

                        Element element = (Element) node;
                        //
                     //   System.out.println();
                        for (int j = 0; j < element.getElementsByTagName("author").getLength(); j++) {
                            if (element.getElementsByTagName("author").item(j) != null) {

                                author = element.getElementsByTagName("author").item(j).getTextContent();
                       //         System.out.println("Author " + author);
                                contains_author_names.add(author);
                                contains_author_with_no_intereption.add(author);
                            }
                        }
                        if (element.getElementsByTagName("title").item(0) != null) {
                            title = element.getElementsByTagName("title").item(0).getTextContent();
                            titles.add(element.getElementsByTagName("title").item(0).getTextContent());
                        }
                        if (element.getElementsByTagName("year").item(0) != null) {
                            years.add(Integer.parseInt(element.getElementsByTagName("year").item(0).getTextContent()));

                        }
                        if (element.getAttribute("key") != null) {
                            venue = element.getAttribute("key");
                        //    System.out.println("Key " + venue);
                        }
                        if (element.getElementsByTagName("journal").item(0) != null) {
                       //     System.out.println("journal " + element.getElementsByTagName("journal").item(0).getTextContent());
                        }

                        if (element.getElementsByTagName("booktitle").item(0) != null) {
                          //  System.out.println("journal " + element.getElementsByTagName("booktitle").item(0).getTextContent());
                        }

                        if (element.getElementsByTagName("ee").item(0) != null) {

                         //   System.out.println("crossref " + element.getElementsByTagName("ee").item(0).getTextContent());
                        }
             author_publications.put(title, contains_author_names);


                        //}
                    }
                }

                ///ADD THE Inproceeding TAG

                nodelist = doc.getElementsByTagName("inproceedings");


                for (int i = 0; i < nodelist.getLength(); i++) {
                    contains_author_names = new ArrayList<>();
                    contains_author_with_no_intereption = new ArrayList<>();
                    years = new ArrayList<>();
                    Node node = nodelist.item(i);
                    // System.out.println("Node name: " + node.getNodeName());
                    if (node.getNodeType() == Node.ELEMENT_NODE) {

                        Element element = (Element) node;
                        //
                        //   System.out.println();
                        for (int j = 0; j < element.getElementsByTagName("author").getLength(); j++) {
                            if (element.getElementsByTagName("author").item(j) != null) {

                                author = element.getElementsByTagName("author").item(j).getTextContent();
                      //          System.out.println("Author " + author);
                                contains_author_names.add(author);
                                contains_author_with_no_intereption.add(author);
                            }
                        }
                        if (element.getElementsByTagName("title").item(0) != null) {
                            title = element.getElementsByTagName("title").item(0).getTextContent();
                            titles.add(element.getElementsByTagName("title").item(0).getTextContent());
                        }
                        if (element.getElementsByTagName("year").item(0) != null) {
                            years.add(Integer.parseInt(element.getElementsByTagName("year").item(0).getTextContent()));

                        }
                        if (element.getAttribute("key") != null) {
                            venue = element.getAttribute("key");
                            //    System.out.println("Key " + venue);
                        }
                        if (element.getElementsByTagName("journal").item(0) != null) {
                            //     System.out.println("journal " + element.getElementsByTagName("journal").item(0).getTextContent());
                        }

                        if (element.getElementsByTagName("booktitle").item(0) != null) {
                            //  System.out.println("journal " + element.getElementsByTagName("booktitle").item(0).getTextContent());
                        }

                        if (element.getElementsByTagName("ee").item(0) != null) {

                            //   System.out.println("crossref " + element.getElementsByTagName("ee").item(0).getTextContent());
                        }
                        author_publications.put(title, contains_author_names);


                        //}
                    }
                }


            } else {
                throw new EmptyStackException();
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        try {
            // create FileWriter object with file as parameter
            String datacentersConfig1 = ApplicationConfig.DBLP.getString(Applicationconst.ONE_AUTHOR); //typesafe config
            LOGGER.info("LOADING CSV FILE: ------");

            FileWriter outputfile1 = new FileWriter(datacentersConfig1);
            Map<String, ArrayList<String>> contains_name = new HashMap<>();

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile1);

            // adding header to csv
            String[] header = {"Publication", "Author Count"};
            writer.writeNext(header);


            // add data to csv
            LOGGER.info("GENERATING CSV FILE: ------");

            ArrayList<String> publications = new ArrayList<>();
            for(int i=0;i<author_publications.size();i++) {
                publications = author_publications.get(titles.get(i));
                if(publications==null){
                    LOGGER.info("Wrong Key");

                }
                else if (publications.size() > 1) {
                    LOGGER.info("This publication has more than 1 author");

                } else {
                    //this will contain all the author with 1 publication

                    LOGGER.info("Venue: " + titles.get(i) + " No: authors of publication " + publications.size());

                    String[] data1 = {titles.get(i), Integer.toString(publications.size())};
                    writer.writeNext(data1);
                }
            }
            //closing the writer
            writer.close();
            } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "One_author";

    } //2

    @Override
    public String author_no_intereption()  {

        author_venue = new HashMap<>();
        keep_counts = new HashMap<>();
        author_with_no_interruption = new HashMap<>();
        author_publications = new HashMap<>();
        contains_venuenames = new ArrayList<>();
        //      contains_author_names = new ArrayList<>();
        titles = new ArrayList<>();

        contains_venues = new ArrayList<>();
        String datacentersConfig = ApplicationConfig.DBLP.getString(Applicationconst.DBLP); //typesafe config
            LOGGER.info("LOADING XML FILE: ------");
        File file = new File(datacentersConfig);
        try {
            if (file != null) {
                String author = "";
                String venue = "";
                int year = 0;
                String title = "";
                //gives document builder
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                //instance of builder to parse xml file
                Document doc = db.parse(file);
                doc.getDocumentElement().normalize();
              //  System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
                NodeList nodelist = doc.getElementsByTagName("article");


                for (int i = 0; i < nodelist.getLength(); i++) {
                    contains_author_names = new ArrayList<>();
                    contains_author_with_no_intereption = new ArrayList<>();
                    years = new ArrayList<>();
                    Node node = nodelist.item(i);
               //     System.out.println("Node name: " + node.getNodeName());
                    if (node.getNodeType() == Node.ELEMENT_NODE) {

                        Element element = (Element) node;
                        //
                        //System.out.println();
                        for (int j = 0; j < element.getElementsByTagName("author").getLength(); j++) {
                            if (element.getElementsByTagName("author").item(j) != null) {

                                author = element.getElementsByTagName("author").item(j).getTextContent();
                     //           System.out.println("Author " + author);
                                contains_author_names.add(author);
                                contains_author_with_no_intereption.add(author);
                            }
                        }
                        if (element.getElementsByTagName("title").item(0) != null) {
                            title = element.getElementsByTagName("title").item(0).getTextContent();
                            titles.add(element.getElementsByTagName("title").item(0).getTextContent());
                           // System.out.println("title " + element.getElementsByTagName("title").item(0).getTextContent());
                        }
                        if (element.getElementsByTagName("year").item(0) != null) {
                            years.add(Integer.parseInt(element.getElementsByTagName("year").item(0).getTextContent()));
                           year = Integer.parseInt(element.getElementsByTagName("year").item(0).getTextContent());
                         //   System.out.println("year " + element.getElementsByTagName("year").item(0).getTextContent());
                        }
                        if (element.getAttribute("key") != null) {
                            venue = element.getAttribute("key");
                        //    System.out.println("Key " + venue);
                        }
                        if (element.getElementsByTagName("journal").item(0) != null) {
                         //   System.out.println("journal " + element.getElementsByTagName("journal").item(0).getTextContent());
                        }

                        if (element.getElementsByTagName("booktitle").item(0) != null) {
                      //      System.out.println("journal " + element.getElementsByTagName("booktitle").item(0).getTextContent());
                        }

                        if (element.getElementsByTagName("ee").item(0) != null) {

                       //     System.out.println("crossref " + element.getElementsByTagName("ee").item(0).getTextContent());
                        }

                        for (int j = 0; j < contains_author_with_no_intereption.size(); j++) {
                            if (author_with_no_interruption.containsKey(contains_author_with_no_intereption.get(j))) {
                                ArrayList<Integer> contains_years = new ArrayList<>();
                                    years= new ArrayList<>();
                                    years.add(year);
                                    /*
                                    so what you're saying is that put a list of years as a value and then add years to the respective authors
                                     */
                                contains_years = author_with_no_interruption.get(contains_author_with_no_intereption.get(j));
                                for (int k = 0; k < contains_years.size(); k++) {

                                    years.add(contains_years.get(k));
                                }
                                author_with_no_interruption.put(contains_author_with_no_intereption.get(j), years); //calculate authors
                            }
                            author_with_no_interruption.put(contains_author_with_no_intereption.get(j), years); //calculate authors
                        }
                        //else {
                        contains_venuenames.add(author); //actually contains_author names

                        //}
                    }
                }

                nodelist = doc.getElementsByTagName("incollection");


                for (int i = 0; i < nodelist.getLength(); i++) {
                    contains_author_names = new ArrayList<>();
                    contains_author_with_no_intereption = new ArrayList<>();
                    years = new ArrayList<>();
                    Node node = nodelist.item(i);
                  //  System.out.println("Node name: " + node.getNodeName());
                    if (node.getNodeType() == Node.ELEMENT_NODE) {

                        Element element = (Element) node;
                        //
                  //      System.out.println();
                        for (int j = 0; j < element.getElementsByTagName("author").getLength(); j++) {
                            if (element.getElementsByTagName("author").item(j) != null) {

                                author = element.getElementsByTagName("author").item(j).getTextContent();
                           //     System.out.println("Author " + author);
                                contains_author_names.add(author);
                                contains_author_with_no_intereption.add(author);
                            }
                        }
                        if (element.getElementsByTagName("title").item(0) != null) {
                            title = element.getElementsByTagName("title").item(0).getTextContent();
                            titles.add(element.getElementsByTagName("title").item(0).getTextContent());
                        //    System.out.println("title " + element.getElementsByTagName("title").item(0).getTextContent());
                        }
                        if (element.getElementsByTagName("year").item(0) != null) {
                            years.add(Integer.parseInt(element.getElementsByTagName("year").item(0).getTextContent()));
                         //   System.out.println("year " + element.getElementsByTagName("year").item(0).getTextContent());
                        }
                        if (element.getAttribute("key") != null) {
                            venue = element.getAttribute("key");
                        //    System.out.println("Key " + venue);
                        }
                        if (element.getElementsByTagName("journal").item(0) != null) {
                       //     System.out.println("journal " + element.getElementsByTagName("journal").item(0).getTextContent());
                        }

                        if (element.getElementsByTagName("booktitle").item(0) != null) {
                         //   System.out.println("journal " + element.getElementsByTagName("booktitle").item(0).getTextContent());
                        }

                        if (element.getElementsByTagName("ee").item(0) != null) {

                       //     System.out.println("crossref " + element.getElementsByTagName("ee").item(0).getTextContent());
                        }
                        //handles duplicates
                        if (author_venue.containsKey(venue)) {
                            for (int j = 0; j < author_venue.get(venue).size(); j++) {
                                contains_author_names.add(author_venue.get(venue).get(j));

                            }
                            author_venue.put(venue, contains_author_names);
                        }

                        for (int j = 0; j < contains_author_with_no_intereption.size(); j++) {
                            if (author_with_no_interruption.containsKey(contains_author_with_no_intereption.get(j))) {
                                ArrayList<Integer> contains_years = new ArrayList<>();

                                    /*
                                    so what you're saying is that put a list of years as a value and then add years to the respective authors
                                     */
                                contains_years = author_with_no_interruption.get(contains_author_with_no_intereption.get(j));
                                for (int k = 0; k < contains_years.size(); k++) {
                                    years.add(contains_years.get(k));
                                }
                                author_with_no_interruption.put(contains_author_with_no_intereption.get(j), years); //calculate authors
                            }
                            author_with_no_interruption.put(contains_author_with_no_intereption.get(j), years); //calculate authors
                        }
                        //else {
                        contains_venuenames.add(author); //actually contains_author names

                        //}
                    }
                }
        ////-------------------Inproceeding-----------------


                nodelist = doc.getElementsByTagName("inproceedings");


                for (int i = 0; i < nodelist.getLength(); i++) {
                    contains_author_names = new ArrayList<>();
                    contains_author_with_no_intereption = new ArrayList<>();
                    years = new ArrayList<>();
                    Node node = nodelist.item(i);
               //     System.out.println("Node name: " + node.getNodeName());
                    if (node.getNodeType() == Node.ELEMENT_NODE) {

                        Element element = (Element) node;
                        //
                   //     System.out.println();
                        for (int j = 0; j < element.getElementsByTagName("author").getLength(); j++) {
                            if (element.getElementsByTagName("author").item(j) != null) {

                                author = element.getElementsByTagName("author").item(j).getTextContent();
                                //     System.out.println("Author " + author);
                                contains_author_names.add(author);
                                contains_author_with_no_intereption.add(author);
                            }
                        }
                        if (element.getElementsByTagName("title").item(0) != null) {
                            title = element.getElementsByTagName("title").item(0).getTextContent();
                            titles.add(element.getElementsByTagName("title").item(0).getTextContent());
                            //    System.out.println("title " + element.getElementsByTagName("title").item(0).getTextContent());
                        }
                        if (element.getElementsByTagName("year").item(0) != null) {
                            years.add(Integer.parseInt(element.getElementsByTagName("year").item(0).getTextContent()));
                            //   System.out.println("year " + element.getElementsByTagName("year").item(0).getTextContent());
                        }
                        if (element.getAttribute("key") != null) {
                            venue = element.getAttribute("key");
                            //    System.out.println("Key " + venue);
                        }
                        if (element.getElementsByTagName("journal").item(0) != null) {
                            //     System.out.println("journal " + element.getElementsByTagName("journal").item(0).getTextContent());
                        }

                        if (element.getElementsByTagName("booktitle").item(0) != null) {
                            //   System.out.println("journal " + element.getElementsByTagName("booktitle").item(0).getTextContent());
                        }

                        if (element.getElementsByTagName("ee").item(0) != null) {

                            //     System.out.println("crossref " + element.getElementsByTagName("ee").item(0).getTextContent());
                        }
                        //handles duplicates
                        if (author_venue.containsKey(venue)) {
                            for (int j = 0; j < author_venue.get(venue).size(); j++) {
                                contains_author_names.add(author_venue.get(venue).get(j));

                            }
                            author_venue.put(venue, contains_author_names);
                        }

                        for (int j = 0; j < contains_author_with_no_intereption.size(); j++) {
                            if (author_with_no_interruption.containsKey(contains_author_with_no_intereption.get(j))) {
                                ArrayList<Integer> contains_years = new ArrayList<>();

                                    /*
                                    so what you're saying is that put a list of years as a value and then add years to the respective authors
                                     */
                                contains_years = author_with_no_interruption.get(contains_author_with_no_intereption.get(j));
                                for (int k = 0; k < contains_years.size(); k++) {
                                    years.add(contains_years.get(k));
                                }
                                author_with_no_interruption.put(contains_author_with_no_intereption.get(j), years); //calculate authors
                            }
                            author_with_no_interruption.put(contains_author_with_no_intereption.get(j), years); //calculate authors
                        }
                        //else {
                        contains_venuenames.add(author); //actually contains_author names

                        //}
                    }
                }

            } else {
                throw new EmptyStackException();
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
                try {
                    String datacentersConfig1 = ApplicationConfig.DBLP.getString(Applicationconst.CONSECUTIVE_PUBLICATION); //typesafe config
                    LOGGER.info("LOADING CSV FILE: ------");

                    FileWriter outputfile1 = new FileWriter(datacentersConfig1);
                    Map<String, ArrayList<String>> contains_name = new HashMap<>();

                    // create CSVWriter object filewriter object as parameter
                    CSVWriter writer = new CSVWriter(outputfile1);

                    // adding header to csv
                    String[] header = {"Author", "Years Published"};
                    writer.writeNext(header);

                    ArrayList<String> duplicates = new ArrayList<>();
                    ArrayList<Integer> years1 = new ArrayList<>();
                    LOGGER.info("GENERATING NEW CSV FILE: ------");

                    for (int i = 0; i < contains_venuenames.size(); i++) { //actually contains_Venuename have author names
                        if (!duplicates.contains(contains_venuenames.get(i))) {
                            duplicates.add(contains_venuenames.get(i));
                       //     System.out.println("Author Name: " + contains_venuenames.get(i) + "  Years: " + author_with_no_interruption.get(contains_venuenames.get(i)));
                            years1 = author_with_no_interruption.get(contains_venuenames.get(i));
                            if(years1.size()<10)
                            {
                                //LOGGER.info("Author did not publish for more than 10 years");
                                String[] data1 = {contains_venuenames.get(i),"No Consecutive Publishing"};
                                writer.writeNext(data1);
                            }
                             else {
                                if (isConsecutive(years1)) {
                                    for (int j = 0; j < years1.size(); j++) {

                                        String[] data1 = {contains_venuenames.get(i), Integer.toString(years1.get(j))};
                                        writer.writeNext(data1);
                                    }
                                }
                            }
                        } else {
                             //   LOGGER.info("DO NOT REPEAT DUPLICATES");
                        }
                    }


                    writer.close();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
        return "Authors with no intereption";
    }

    public boolean isConsecutive(ArrayList<Integer> list) { //check if author published consecutively
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i - 1) + 1 != list.get(i)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String top_100()     { //without CO-AUTHORS
        author_venue = new HashMap<>();
        keep_counts = new HashMap<>();
        author_with_no_interruption = new HashMap<>();
        author_publications = new HashMap<>();
        contains_venuenames = new ArrayList<>();
        //      contains_author_names = new ArrayList<>();
        titles = new ArrayList<>();

        contains_venues = new ArrayList<>();
        String datacentersConfig = ApplicationConfig.DBLP.getString(Applicationconst.DBLP); //typesafe config
        LOGGER.info("LOADING XML FILE: ------");

        File file = new File(datacentersConfig);
        try {
            if (file != null) {
                String author = "";
                String venue = "";
                int year = 0;
                String title = "";
                //gives document builder
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                //instance of builder to parse xml file
                Document doc = db.parse(file);
                doc.getDocumentElement().normalize();
           //     System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
                NodeList nodelist = doc.getElementsByTagName("article");


                for (int i = 0; i < nodelist.getLength(); i++) {
                    contains_author_names = new ArrayList<>();
                    contains_author_with_no_intereption = new ArrayList<>();
                    years = new ArrayList<>();
                    Node node = nodelist.item(i);
                   // System.out.println("Node name: " + node.getNodeName());
                    if (node.getNodeType() == Node.ELEMENT_NODE) {

                        Element element = (Element) node;
                        //
                        //System.out.println();
                        for (int j = 0; j < element.getElementsByTagName("author").getLength(); j++) {
                            if (element.getElementsByTagName("author").item(j) != null) {

                                author = element.getElementsByTagName("author").item(j).getTextContent();
                             //   System.out.println("Author " + author);
                                contains_author_names.add(author);
                                contains_author_with_no_intereption.add(author);
                            }
                        }
                        if (element.getElementsByTagName("title").item(0) != null) {
                            title = element.getElementsByTagName("title").item(0).getTextContent();
                            titles.add(element.getElementsByTagName("title").item(0).getTextContent());
                          //  System.out.println("title " + element.getElementsByTagName("title").item(0).getTextContent());
                        }
                        if (element.getElementsByTagName("year").item(0) != null) {
                            years.add(Integer.parseInt(element.getElementsByTagName("year").item(0).getTextContent()));
                          //  System.out.println("year " + element.getElementsByTagName("year").item(0).getTextContent());
                        }
                        if (element.getAttribute("key") != null) {
                            venue = element.getAttribute("key");
                         //   System.out.println("Key " + venue);
                        }
                        if (element.getElementsByTagName("journal").item(0) != null) {
                            //System.out.println("journal " + element.getElementsByTagName("journal").item(0).getTextContent());
                        }

                        if (element.getElementsByTagName("booktitle").item(0) != null) {
                           // System.out.println("journal " + element.getElementsByTagName("booktitle").item(0).getTextContent());
                        }

                        if (element.getElementsByTagName("ee").item(0) != null) {

                           // System.out.println("crossref " + element.getElementsByTagName("ee").item(0).getTextContent());
                        }

                        author_publications.put(title, contains_author_names);


                        //}
                    }
                }

                nodelist = doc.getElementsByTagName("incollection");


                for (int i = 0; i < nodelist.getLength(); i++) {
                    contains_author_names = new ArrayList<>();
                    contains_author_with_no_intereption = new ArrayList<>();
                    years = new ArrayList<>();
                    Node node = nodelist.item(i);
                 //   System.out.println("Node name: " + node.getNodeName());
                    if (node.getNodeType() == Node.ELEMENT_NODE) {

                        Element element = (Element) node;
                        //
                      //  System.out.println();
                        for (int j = 0; j < element.getElementsByTagName("author").getLength(); j++) {
                            if (element.getElementsByTagName("author").item(j) != null) {

                                author = element.getElementsByTagName("author").item(j).getTextContent();
                               // System.out.println("Author " + author);
                                contains_author_names.add(author);
                                contains_author_with_no_intereption.add(author);
                            }
                        }
                        if (element.getElementsByTagName("title").item(0) != null) {
                            title = element.getElementsByTagName("title").item(0).getTextContent();
                            titles.add(element.getElementsByTagName("title").item(0).getTextContent());
                          //  System.out.println("title " + element.getElementsByTagName("title").item(0).getTextContent());
                        }
                        if (element.getElementsByTagName("year").item(0) != null) {
                            years.add(Integer.parseInt(element.getElementsByTagName("year").item(0).getTextContent()));
                         //   System.out.println("year " + element.getElementsByTagName("year").item(0).getTextContent());
                        }
                        if (element.getAttribute("key") != null) {
                            venue = element.getAttribute("key");
                           // System.out.println("Key " + venue);
                        }
                        if (element.getElementsByTagName("journal").item(0) != null) {
                          //  System.out.println("journal " + element.getElementsByTagName("journal").item(0).getTextContent());
                        }

                        if (element.getElementsByTagName("booktitle").item(0) != null) {
                         //   System.out.println("journal " + element.getElementsByTagName("booktitle").item(0).getTextContent());
                        }

                        if (element.getElementsByTagName("ee").item(0) != null) {

                          //  System.out.println("crossref " + element.getElementsByTagName("ee").item(0).getTextContent());
                        }

                        author_publications.put(title, contains_author_names);


                        //}
                    }
                }

                //Inproceeding


                nodelist = doc.getElementsByTagName("inproceedings");


                for (int i = 0; i < nodelist.getLength(); i++) {
                    contains_author_names = new ArrayList<>();
                    contains_author_with_no_intereption = new ArrayList<>();
                    years = new ArrayList<>();
                    Node node = nodelist.item(i);
                    //   System.out.println("Node name: " + node.getNodeName());
                    if (node.getNodeType() == Node.ELEMENT_NODE) {

                        Element element = (Element) node;
                        //
                        //  System.out.println();
                        for (int j = 0; j < element.getElementsByTagName("author").getLength(); j++) {
                            if (element.getElementsByTagName("author").item(j) != null) {

                                author = element.getElementsByTagName("author").item(j).getTextContent();
                                // System.out.println("Author " + author);
                                contains_author_names.add(author);
                                contains_author_with_no_intereption.add(author);
                            }
                        }
                        if (element.getElementsByTagName("title").item(0) != null) {
                            title = element.getElementsByTagName("title").item(0).getTextContent();
                            titles.add(element.getElementsByTagName("title").item(0).getTextContent());
                            //  System.out.println("title " + element.getElementsByTagName("title").item(0).getTextContent());
                        }
                        if (element.getElementsByTagName("year").item(0) != null) {
                            years.add(Integer.parseInt(element.getElementsByTagName("year").item(0).getTextContent()));
                            //   System.out.println("year " + element.getElementsByTagName("year").item(0).getTextContent());
                        }
                        if (element.getAttribute("key") != null) {
                            venue = element.getAttribute("key");
                            // System.out.println("Key " + venue);
                        }
                        if (element.getElementsByTagName("journal").item(0) != null) {
                            //  System.out.println("journal " + element.getElementsByTagName("journal").item(0).getTextContent());
                        }

                        if (element.getElementsByTagName("booktitle").item(0) != null) {
                            //   System.out.println("journal " + element.getElementsByTagName("booktitle").item(0).getTextContent());
                        }

                        if (element.getElementsByTagName("ee").item(0) != null) {

                            //  System.out.println("crossref " + element.getElementsByTagName("ee").item(0).getTextContent());
                        }

                        author_publications.put(title, contains_author_names);


                        //}
                    }
                }

            } else {
                throw new EmptyStackException();
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        try {
            // create FileWriter object with file as parameter
            String datacentersConfig1 = ApplicationConfig.DBLP.getString(Applicationconst.TOP_100_AUTHOR); //typesafe config
            LOGGER.info("LOADING CSV FILE: ------");

            FileWriter outputfile1 = new FileWriter(datacentersConfig1);
            Map<String, ArrayList<String>> contains_name = new HashMap<>();

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile1);

            // adding header to csv
            String[] header = {"Author", "Publication"};
            writer.writeNext(header);


            // add data to csv
            LOGGER.info("GENERATING NEW CSV FILE: ------");

            ArrayList<String> publications = new ArrayList<>();
            for(int i=0;i<100;i++) {
                publications = author_publications.get(titles.get(i));
                if(publications==null){
                    LOGGER.info("Wrong Key");

                }
                else if (publications.size() > 1) {
                    LOGGER.info("This publication has co-authors");

                } else {
                    //this will contain all the author with 1 publication

                    //LOGGER.info("Venue: " + titles.get(i) + " No: authors of publication " + publications.size());
                    String[] data1 = {titles.get(i), Integer.toString(publications.size())};
                    writer.writeNext(data1);
                }
            }
            //closing the writer
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "top_100";
    } //4

    @Override
    public String top_100_co_authors() { //5
        System.setProperty("entityExpansionLimit", String.valueOf(Integer.MAX_VALUE));
        author_venue = new HashMap<>();
        keep_counts = new HashMap<>();
        author_with_no_interruption = new HashMap<>();
        author_publications = new HashMap<>();
        contains_venuenames = new ArrayList<>();
        //      contains_author_names = new ArrayList<>();
        titles = new ArrayList<>();

        contains_venues = new ArrayList<>();
        String datacentersConfig = ApplicationConfig.DBLP.getString(Applicationconst.DBLP); //typesafe config
        LOGGER.info("LOADING XML FILE: ------");

        File file = new File(datacentersConfig);
        try {
            if (file != null) {
                String author = "";
                String venue = "";
                int year = 0;
                String title = "";
                //gives document builder
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                //instance of builder to parse xml file
                Document doc = db.parse(file);
                doc.getDocumentElement().normalize();
              //  System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
                NodeList nodelist = doc.getElementsByTagName("article");


                for (int i = 0; i < nodelist.getLength(); i++) {
                    contains_author_names = new ArrayList<>();
                    contains_author_with_no_intereption = new ArrayList<>();
                    years = new ArrayList<>();
                    Node node = nodelist.item(i);
                 //   System.out.println("Node name: " + node.getNodeName());
                    if (node.getNodeType() == Node.ELEMENT_NODE) {

                        Element element = (Element) node;
                        //
                        System.out.println();
                        for (int j = 0; j < element.getElementsByTagName("author").getLength(); j++) {
                            if (element.getElementsByTagName("author").item(j) != null) {

                                author = element.getElementsByTagName("author").item(j).getTextContent();
                            //    System.out.println("Author " + author);
                                contains_author_names.add(author);
                                contains_author_with_no_intereption.add(author);
                            }
                        }
                        if (element.getElementsByTagName("title").item(0) != null) {
                            title = element.getElementsByTagName("title").item(0).getTextContent();
                            titles.add(element.getElementsByTagName("title").item(0).getTextContent());
                           // System.out.println("title " + element.getElementsByTagName("title").item(0).getTextContent());
                        }
                        if (element.getElementsByTagName("year").item(0) != null) {
                            years.add(Integer.parseInt(element.getElementsByTagName("year").item(0).getTextContent()));
                        //    System.out.println("year " + element.getElementsByTagName("year").item(0).getTextContent());
                        }
                        if (element.getAttribute("key") != null) {
                            venue = element.getAttribute("key");
                         //   System.out.println("Key " + venue);
                        }
                        if (element.getElementsByTagName("journal").item(0) != null) {
                          //  System.out.println("journal " + element.getElementsByTagName("journal").item(0).getTextContent());
                        }

                        if (element.getElementsByTagName("booktitle").item(0) != null) {
                         //   System.out.println("journal " + element.getElementsByTagName("booktitle").item(0).getTextContent());
                        }

                        if (element.getElementsByTagName("ee").item(0) != null) {

                        //    System.out.println("crossref " + element.getElementsByTagName("ee").item(0).getTextContent());
                        }

                        author_publications.put(title, contains_author_names);
                       // contains_venues.add(venue);


                        //}
                    }
                }

                nodelist = doc.getElementsByTagName("incollection");


                for (int i = 0; i < nodelist.getLength(); i++) {
                    contains_author_names = new ArrayList<>();
                    contains_author_with_no_intereption = new ArrayList<>();
                    years = new ArrayList<>();
                    Node node = nodelist.item(i);
                  //  System.out.println("Node name: " + node.getNodeName());
                    if (node.getNodeType() == Node.ELEMENT_NODE) {

                        Element element = (Element) node;
                        //
                        //System.out.println();
                        for (int j = 0; j < element.getElementsByTagName("author").getLength(); j++) {
                            if (element.getElementsByTagName("author").item(j) != null) {

                                author = element.getElementsByTagName("author").item(j).getTextContent();
                         //       System.out.println("Author " + author);
                                contains_author_names.add(author);
                                contains_author_with_no_intereption.add(author);
                            }
                        }
                        if (element.getElementsByTagName("title").item(0) != null) {
                            title = element.getElementsByTagName("title").item(0).getTextContent();
                            titles.add(element.getElementsByTagName("title").item(0).getTextContent());
                         //   System.out.println("title " + element.getElementsByTagName("title").item(0).getTextContent());
                        }
                        if (element.getElementsByTagName("year").item(0) != null) {
                            years.add(Integer.parseInt(element.getElementsByTagName("year").item(0).getTextContent()));
                          //  System.out.println("year " + element.getElementsByTagName("year").item(0).getTextContent());
                        }
                        if (element.getAttribute("key") != null) {
                            venue = element.getAttribute("key");
                           // System.out.println("Key " + venue);
                        }
                        if (element.getElementsByTagName("journal").item(0) != null) {
                        //    System.out.println("journal " + element.getElementsByTagName("journal").item(0).getTextContent());
                        }

                        if (element.getElementsByTagName("booktitle").item(0) != null) {
                          //  System.out.println("journal " + element.getElementsByTagName("booktitle").item(0).getTextContent());
                        }

                        if (element.getElementsByTagName("ee").item(0) != null) {

                          //  System.out.println("crossref " + element.getElementsByTagName("ee").item(0).getTextContent());
                        }
                        author_publications.put(title, contains_author_names);
                      //  contains_venues.add(venue);


                        //}
                    }
                }

                //inproceeding

                nodelist = doc.getElementsByTagName("inproceedings");


                for (int i = 0; i < nodelist.getLength(); i++) {
                    contains_author_names = new ArrayList<>();
                    contains_author_with_no_intereption = new ArrayList<>();
                    years = new ArrayList<>();
                    Node node = nodelist.item(i);
                    //  System.out.println("Node name: " + node.getNodeName());
                    if (node.getNodeType() == Node.ELEMENT_NODE) {

                        Element element = (Element) node;
                        //
//                        System.out.println();
                        for (int j = 0; j < element.getElementsByTagName("author").getLength(); j++) {
                            if (element.getElementsByTagName("author").item(j) != null) {

                                author = element.getElementsByTagName("author").item(j).getTextContent();
                                //       System.out.println("Author " + author);
                                contains_author_names.add(author);
                                contains_author_with_no_intereption.add(author);
                            }
                        }
                        if (element.getElementsByTagName("title").item(0) != null) {
                            title = element.getElementsByTagName("title").item(0).getTextContent();
                            titles.add(element.getElementsByTagName("title").item(0).getTextContent());
                            //   System.out.println("title " + element.getElementsByTagName("title").item(0).getTextContent());
                        }
                        if (element.getElementsByTagName("year").item(0) != null) {
                            years.add(Integer.parseInt(element.getElementsByTagName("year").item(0).getTextContent()));
                            //  System.out.println("year " + element.getElementsByTagName("year").item(0).getTextContent());
                        }
                        if (element.getAttribute("key") != null) {
                            venue = element.getAttribute("key");
                            // System.out.println("Key " + venue);
                        }
                        if (element.getElementsByTagName("journal").item(0) != null) {
                            //    System.out.println("journal " + element.getElementsByTagName("journal").item(0).getTextContent());
                        }

                        if (element.getElementsByTagName("booktitle").item(0) != null) {
                            //  System.out.println("journal " + element.getElementsByTagName("booktitle").item(0).getTextContent());
                        }

                        if (element.getElementsByTagName("ee").item(0) != null) {

                            //  System.out.println("crossref " + element.getElementsByTagName("ee").item(0).getTextContent());
                        }
                        author_publications.put(title, contains_author_names);
                        //  contains_venues.add(venue);


                        //}
                    }
                }

            } else {
                throw new EmptyStackException();
            }
        } catch (Exception e) {
            e.getStackTrace();
        }

        try {
            // create FileWriter object with file as parameter
            String datacentersConfig1 = ApplicationConfig.DBLP.getString(Applicationconst.TOP_100_AUTHOR_COAUTHOR); //typesafe config
            LOGGER.info("LOADING CSV FILE: ------");

            FileWriter outputfile1 = new FileWriter(datacentersConfig1);
            Map<String, ArrayList<String>> contains_name = new HashMap<>();

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile1);

            // adding header to csv
            String[] header = {"Author", "Publication"};
            writer.writeNext(header);


            // add data to csv
            LOGGER.info("GENERATING NEW  CSV FILE: ------");

            ArrayList<String> publications = new ArrayList<>();
            for(int i=0;i<48;i++) {
                publications = author_publications.get(titles.get(i));
                if(publications==null){
                    LOGGER.info("Wrong Key");

                }
                else if (publications.size() > 1) {
                    String[] data1 = {titles.get(i), String.valueOf(publications)};
                    writer.writeNext(data1);
                } else {
                    //this will contain all the author with 1 publication

                    LOGGER.info("This publication has one author");
                    //LOGGER.info("Venue: " + titles.get(i) + " No: authors of publication " + publications.size());
                }
            }
            //closing the writer
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "top_100_withcoauthors";
    }
}
