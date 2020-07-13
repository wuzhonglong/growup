package com.wzl.chrome;

import com.wzl.chrome.task.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@SpringBootTest
class ChromeApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void test(){
        User a = new User("A", 18);
        User b = new User("B", 15);
        User c = new User("C", 28);
        User d = new User("D", 28);
        List<User> list = new ArrayList<>();
        list.add(a);
        list.add(b);
        list.add(c);
        list.add(d);
        System.out.println("前"+list);
        Collections.sort(list, new Comparator<User>() {
            @Override
            public int compare(User user1, User user2) {
                if(user1.getAge() > user2.getAge()){
                    return -1;
                }else if(user1.getAge() < user2.getAge()){
                    return 1;
                }else {
                    return 0;
                }
            }
        });
        System.out.println("后"+list);
        Collections.min(list, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                //....
                return 0;
            }
        });
    }

}
