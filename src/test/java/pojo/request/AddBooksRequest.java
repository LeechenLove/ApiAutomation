package pojo.request;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Lulu
 * @Description: TODO
 * @DateTime: 2022/8/13 22:02
 **/
public class AddBooksRequest {
    public String userId;
    public List<ISBN> collectionOfIsbns;

    //As of now this is for adding a single book, later we will add another constructor.
    //That will take a collection of ISBN to add multiple books
    public AddBooksRequest(String userId, ISBN isbn){
        this.userId = userId;
        collectionOfIsbns = new ArrayList<>();
        collectionOfIsbns.add(isbn);
    }
}
