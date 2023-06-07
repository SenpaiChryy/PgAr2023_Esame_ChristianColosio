import java.util.ArrayList;

public class Node {
    private int id;
    private ArrayList<Integer> links = new ArrayList<>();
    private String type;

    public Node() {}

    public Node(int id, ArrayList<Integer> links, String type) {
        this.id = id;
        this.links = links;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Integer> getLinks() {
        return links;
    }

    public void addLink(int link) {
        this.links.add(link);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {

        StringBuilder str = new StringBuilder();
        for (int i = 0; i < links.size(); i++) {
            str.append(links.get(i) + " ");
        }

        return "id: " + id + " type: " + type + " links: " + str;
    }
}
