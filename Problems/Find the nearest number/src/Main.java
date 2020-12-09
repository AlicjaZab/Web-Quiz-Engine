import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        String list = scanner.nextLine();
        String[] elements = list.split(" ");
        int n = Integer.parseInt(scanner.nextLine());
        
        ArrayList<String> collection = new ArrayList<>();
        
        for(int i = 0; i < elements.length; i++) {
            int dist = Math.abs(Integer.parseInt(elements[i]) - n);
            if(!collection.isEmpty()){
                boolean add = true;
                for (int j = 0; j < collection.size(); j++) {
                    if (Math.abs(Integer.parseInt(collection.get(j)) - n) < dist){
                        //nie dodajemy elementu
                        add = false;
                    }else if(Math.abs(Integer.parseInt(collection.get(j)) - n) > dist){
                        collection.remove(j);
                        j--;
                    }
                }
                if(add){
                    collection.add(elements[i]);
                }
            }else {
                collection.add(elements[i]);
            }
        }

        Collections.sort(collection);

        for(String num : collection){
            System.out.print(num + " ");
        }
        
    }
}
