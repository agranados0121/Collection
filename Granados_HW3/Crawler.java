//package distrib_systems;
import org.jsoup.*;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Crawler {

        private HashSet<String> links;
        public static int MAX_DEPTH;
        public static Map<String, Integer> map = new HashMap<>();
        public Crawler() {
                links = new HashSet<String>();
        }

        public void getPageLinks(String url, int depth) {
                if((!links.contains(url) && depth <= MAX_DEPTH)) {
                        try {
                                links.add(url);
                                Document doc = Jsoup.connect(url).get();
                                Elements linksOnPage = doc.select("a[href]");
                                depth++;
                                
                                String[] wr = doc.text().split(" ");
				for(String w : wr){
				    System.out.println(w);
				}
                                for(Element page: linksOnPage) {
                                        getPageLinks(page.attr("abs:href"), depth);
                                }

                        } catch(IOException e) {
                                System.err.println("For " + url + ": " + e.getMessage());
                        }
                }
        }
        public static void main(String[] args) throws IOException {
                MAX_DEPTH = Integer.parseInt(args[1]);
                new Crawler().getPageLinks(args[0], 0);
                Scanner s = new Scanner(System.in);
                FileWriter f = new FileWriter("out.txt");
                while(s.hasNextLine()) {
                	String str = s.next();
                	Integer n = map.get(str);
                	n = (n == null) ? 1 : ++ n;
                	map.put(str, n);
                }
                s.close();
                Set<String> wrds = map.keySet();
                for(String w: wrds) {
                	f.write(w + " " + map.get(w));
                }
                f.close();
        }

}

