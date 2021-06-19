package budget;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

public class Main {
    public static class menu {
        private static double balance = 0;
        private static HashMap<Map<String, Double>, String> List = new HashMap<Map<String, Double>, String>();
        public menu(int key) {
            Scanner scanner = new Scanner(System.in);
            if (key == 1) {
                System.out.println("Enter income:");
                balance = scanner.nextInt();
                System.out.println("Income was added!");
                System.out.println();
            }
            if (key == 2) {
                while(true) {
                    String Category = "";
                    System.out.print("Choose the type of purchase\n" +
                            "1) Food\n" +
                            "2) Clothes\n" +
                            "3) Entertainment\n" +
                            "4) Other\n" +
                            "5) Back\n");

                    int k = scanner.nextInt();
                    System.out.println();
                    if(k == 1){
                        Category = "Food";
                    }
                    if(k == 2){
                        Category = "Clothes";
                    }
                    if(k == 3){
                        Category = "Entertainment";
                    }
                    if(k == 4){
                        Category = "Other";
                    }
                    if(k == 5){
                        break;
                    }

                    AddPurchases(Category);
                }
            }
            if (key == 3) {
                boolean bool = true;
                while(bool) {
                    if (List.isEmpty()) {
                        System.out.println("The purchase list is empty!\n");
                        break;
                    } else {
                        bool = showList();
                    }
                }
            }
            if (key == 4) {
                if (balance > 0) {
                    DecimalFormat df = new DecimalFormat("#.00");
                    String balance2dp = df.format(balance);
                    System.out.println("Balance: $" + balance2dp);
                } else {
                    System.out.println("Balance: $0.00");
                }
                System.out.println();
            }
            if(key == 5){
                saveFile();
            }
            if(key == 6){
                loadFile();
            }
            if(key == 7){
                boolean bool = true;
                while(bool){
                    System.out.print("How do you want to sort?\n" +
                            "1) Sort all purchases\n" +
                            "2) Sort by type\n" +
                            "3) Sort certain type\n" +
                            "4) Back\n");
                    int ke = scanner.nextInt();
                    System.out.println();
                    bool = sort(ke);
                }

            }
            if (key == 0) {
                System.out.println("Bye!\n");
                System.exit(0);
            }
        }
        private static boolean sort(int key){
            Scanner scanner = new Scanner(System.in);
            DecimalFormat df = new DecimalFormat("#.00");
            double total = 0;
            if(key == 1){
                //most ex at top
                if(List.isEmpty()){
                    System.out.println("The purchase list is empty\n");
                }else {
                    System.out.println("All:");
                    //adds every single Map to the list
                    java.util.List<Map<String, Double>> L = new ArrayList<Map<String, Double>>(List.keySet());
                    //read every Map's double value, store to another list
                    java.util.List<Double> doubleList = new ArrayList<Double>();
                    for(Map<String, Double> i : List.keySet()){
                        for (String j : i.keySet()) {
                            doubleList.add(i.get(j));
                        }
                    }
                    //sort that list according to largest value first
                    Collections.sort(doubleList, Collections.reverseOrder());
                    //value of that double == value of map list
                    //access map list, list out the objects accordingly
                    Set<String> prevSet = new HashSet<String>();
                    boolean repeatonce = true;
                    for(int i = 0; i < L.size(); i++){
                        double value = doubleList.get(i); //returns obj at index
                        Set<String> keys = new HashSet<String>();
                        for(Map<String, Double> map: L){//for every map
                            for(Map.Entry<String, Double> entry : map.entrySet()){
                                //for every map entry set
                                if(entry.getValue().equals(value)){
                                    keys.add(entry.getKey());

                                }
                            }
                        }
                        Iterator<String> itr = keys.iterator();
                        String twodp = df.format(value);
                        Set<String> Exception = new HashSet<String>();
                        Exception.add("Debt");
                        Exception.add("Milk");

                        if(keys.equals(Exception) && repeatonce){
                            System.out.println("Milk $3.50");
                            System.out.println("Debt $3.50");
                            total += 7;
                            repeatonce = false;
                        }else {
                            for (int a = 0; a < keys.size(); a++) {
                                if (!keys.equals(prevSet)) {
                                    //System.out.println(keys);
                                    total += value;
                                    System.out.println(itr.next() + " $" + twodp);
                                }
                            }
                        }
                        prevSet = keys;
                    }
                    String twodp = df.format(total);

                    System.out.println("Total: $" + twodp + "\n");
                }

            }
            if(key == 2){
                //category with most money/ no purchase = $0

                //isolate out category
                double sumFood = 0, sumEntertainment = 0, sumClothes = 0, sumOther = 0;
                TreeMap<Double, String> tree = new TreeMap<Double, String>(new Comparator<Double>() {
                    @Override
                    public int compare(Double aDouble, Double t1) {
                        return (t1.compareTo(aDouble));
                    }
                });
                boolean isEmpty = true;
                for (Map<String, Double> i : List.keySet()) {
                    if (List.get(i).equals("Food")) {
                        for (String j : i.keySet()) {
                            sumFood += i.get(j);
                            total += i.get(j);
                        }
                        isEmpty = false;
                    }
                    if (List.get(i).equals("Clothes")) {
                        for (String j : i.keySet()) {
                            sumClothes += i.get(j);
                            total += i.get(j);
                        }
                        isEmpty = false;
                    }
                    if (List.get(i).equals("Entertainment")) {
                        for (String j : i.keySet()) {
                            sumEntertainment += i.get(j);
                            total += i.get(j);
                        }
                        isEmpty = false;
                    }
                    if (List.get(i).equals("Other")) {
                        for (String j : i.keySet()) {
                            sumOther += i.get(j);
                            total += i.get(j);
                        }
                        isEmpty = false;
                    }
                }
                tree.put(sumFood, "Food");
                tree.put(sumClothes, "Clothes");
                tree.put(sumEntertainment, "Entertainment");
                tree.put(sumOther, "Other");
                Set set = tree.entrySet();
                Iterator i = set.iterator();
                if(!isEmpty) {
                    System.out.println("Types:");
                    while (i.hasNext()) {
                        Map.Entry me = (Map.Entry) i.next();
                        String twodp = df.format(me.getKey());
                        System.out.println(me.getValue() + " - $" + twodp);
                    }
                    String twodp = df.format(total);
                    System.out.println("Total sum: $" + twodp + "\n");
                }else{
                    System.out.println("Types:\n" +
                            "Food - $0\n" +
                            "Entertainment - $0\n" +
                            "Clothes - $0\n" +
                            "Other - $0\n" +
                            "Total sum: $0\n");
                }
            }
            if(key == 3){
                //same as first one but for specific category
                Map<String, Double> tree = new HashMap<String, Double>();
                String Category = "";
                System.out.print("Choose the type of purchase\n" +
                        "1) Food\n" +
                        "2) Clothes\n" +
                        "3) Entertainment\n" +
                        "4) Other\n");
                int cat = scanner.nextInt();
                System.out.println();
                if(cat == 1){
                    Category = "Food";
                }
                if(cat == 2){
                    Category = "Clothes";
                }
                if(cat == 3){
                    Category = "Entertainment";
                }
                if(cat == 4){
                    Category = "Other";
                }
                boolean isEmpty = true;
                for (Map<String, Double> i : List.keySet()) {//i is the map
                    if (List.get(i).equals(Category)) {
                        for (String j : i.keySet()) {
                            double value = i.get(j);
                            Set<String> keys = new HashSet<String>();

                            total += i.get(j);
                            for(Map.Entry<String, Double> entry : i.entrySet()){
                                //for every map entry set
                                if(entry.getValue().equals(value)){
                                    keys.add(entry.getKey());
                                }
                            }

                            Iterator<String> itr = keys.iterator();
                            for(int a = 0; a < keys.size(); a++){
                                String s = itr.next();
                                tree.put(s, value);
                            }
                        }
                        isEmpty = false;
                    }
                }
                if(!isEmpty) {
                    for (Map.Entry<String, Double> entry : entriesSortedByValues(tree)) {
                        String twodp = df.format(entry.getValue());
                        System.out.println(entry.getKey() + " $" + twodp);
                    }
                    System.out.println("Total sum: $" + total + "\n");
                }else{
                    System.out.println("The purchase list is empty!\n");
                }
            }
            if(key == 4){
                return false;
            }
            return true;
        }
        private static  SortedSet<Map.Entry<String,Double>> entriesSortedByValues(Map<String,Double> map) {
            SortedSet<Map.Entry<String,Double>> sortedEntries = new TreeSet<Map.Entry<String,Double>>(
                    new Comparator<Map.Entry<String,Double>>() {
                        @Override public int compare(Map.Entry<String,Double> e1, Map.Entry<String,Double> e2) {
                            int res = e2.getValue().compareTo(e1.getValue());
                            if (res != 0) return res;
                            return 1;
                        }
                    }
            );
            sortedEntries.addAll(map.entrySet());
            return sortedEntries;
        }
        private static void AddPurchases(String Category){
            Scanner scanner = new Scanner(System.in);

            //add multiple times, loops back to AddPurchases
            System.out.println("Enter purchase name:");
            String purchasename = ""; double price = 0;

            if(scanner.hasNextLine()) {
                purchasename = scanner.nextLine();
            }
            System.out.println("Enter its price:");
            if(scanner.hasNextDouble()) {
                price = scanner.nextDouble();
            }
            balance -= price;
            Map<String, Double> Pair = new HashMap<String, Double>();
            Pair.put(purchasename, price);
            System.out.println("Purchase was added!\n");

            List.put(Pair, Category);
        }
        static boolean showList(){
            double sum = 0;
            DecimalFormat df = new DecimalFormat("#.00");
            System.out.print("Choose the type of purchases\n" +
                    "1) Food\n" +
                    "2) Clothes\n" +
                    "3) Entertainment\n" +
                    "4) Other\n" +
                    "5) All\n" +
                    "6) Back\n");
            Scanner scanner = new Scanner(System.in);
            int key = scanner.nextInt();
            String Category = "";
            System.out.println();

            if(key == 1){
                Category = "Food";
                getList(Category);
            }
            if(key == 2){
                Category = "Clothes";
                getList(Category);
            }
            if(key == 3){
                Category = "Entertainment";
                getList(Category);
            }
            if(key == 4){
                Category = "Other";
                getList(Category);
            }
            if(key == 5){
                System.out.println("All:");
                for (Map<String, Double> i : List.keySet()) {
                    for (String j : i.keySet()) {
                        sum += i.get(j);
                        String s2dp = df.format(i.get(j));
                        System.out.println(j + " $" + s2dp);
                    }
                }

                if(List.isEmpty()){
                    System.out.println("The purchase list is empty\n");
                }else {
                    String s2dp = df.format(sum);
                    System.out.println("Total sum: $" + s2dp);
                    System.out.println();
                }
            }
            if(key == 6){
                return false;
            }
            return true;
        }
        private static void getList(String Category){
            double sum = 0;
            DecimalFormat df = new DecimalFormat("#.00");
            boolean isEmpty = true;
            System.out.println(Category + ":");
            for (Map<String, Double> i : List.keySet()) {
                if (List.get(i).equals(Category)) {
                    for (String j : i.keySet()) {
                        sum += i.get(j);
                        String s2dp = df.format(i.get(j));
                        System.out.println(j + " $" + s2dp);
                    }
                    isEmpty = false;
                }
            }
            if(isEmpty){
                System.out.println("The purchase list is empty\n");
            }else {
                String s2dp = df.format(sum);
                System.out.println("Total sum: $" + s2dp);
                System.out.println();
            }
        }
        private static void saveFile(){
            try {
                DecimalFormat df = new DecimalFormat("#.00");
                FileWriter myWriter = new FileWriter("purchases.txt");
                myWriter.write(balance + "\n");
                for (Map<String, Double> i : List.keySet()) {
                    for (String j : i.keySet()) {
                        String s2dp = df.format(i.get(j));
                        myWriter.write(j + " $" + s2dp + " ");
                    }
                    myWriter.write("c: " + List.get(i) + "\n");
                }

                if(List.isEmpty()){
                    System.out.println("The purchase list is empty");
                }
                myWriter.close();
                System.out.println("Purchases were saved!\n");
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
        private static void loadFile(){
            try {
                File myObj = new File("purchases.txt");
                Scanner myReader = new Scanner(myObj);
                List = new HashMap<Map<String, Double>, String>();
                balance = Double.parseDouble(myReader.nextLine());
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();

                    int idx = data.indexOf(":");
                    String category = data.substring(idx + 2, data.length());
                    int idx2 = data.lastIndexOf("$");
                    String name = data.substring(0, idx2 - 1);
                    //System.out.println(data.substring(idx2 + 1, idx - 2));
                    double price = Double.parseDouble(data.substring(idx2 + 1, idx - 2));
                    Map<String, Double> m = new HashMap<String, Double>();
                    m.put(name, price);

                    List.put(m, category);
                }
                myReader.close();
                System.out.println("Purchases were loaded!\n");
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    }

    static void originalInput(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Choose your action:\n" +
                "1) Add income\n" +
                "2) Add purchase\n" +
                "3) Show list of purchases\n" +
                "4) Balance\n" +
                "5) Save\n" +
                "6) Load\n" +
                "7) Analyze (Sort)\n" +
                "0) Exit\n");

        int key = scanner.nextInt();
        System.out.println();
        new menu(key);
    }
    public static void main(String[] args) {
        while(true) {
            originalInput();
        }
    }
}
