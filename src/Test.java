import java.rmi.UnexpectedException;
import java.util.*;
import java.util.function.Function;

public class Test {
    public static void main(String[] args) throws UnexpectedException {
        Function<Integer,String> add = (i)->String.valueOf(i++);
        List<String> strings = new ArrayList<>();
        strings.add("1st Str");
        strings.add("2nd Str");
        strings.add("3rd Str");
        strings.forEach(System.out::println);
    }
}

class Fruit{
    String name;

    public Fruit(String s){
        this.name = s;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String toString(){
        return this.name;
    }
}

class Apple extends Fruit{
    public Apple(String s) {
        super(s);
    }
};
class Pear extends Fruit{
    public Pear(String s) {
        super(s);
    }
};
class RedApple extends Apple{
    public RedApple(String s) {
        super(s);
    }
};
class GreenApple extends Apple{
    public GreenApple(String s) {
        super(s);
    }
};
class Plate<T> {
    Set<T> content;
    public Plate(){
        if(content==null){
            this.content = new HashSet<>();
        }
    }
    public Set<T> getContent() {
        return content;
    }
    public void setContent(Set<T> content) {
        this.content = content;
    }
    public void add(T e){
        this.content.add(e);
    }
}

