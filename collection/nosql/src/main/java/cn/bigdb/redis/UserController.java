package cn.bigdb.redis;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @RestController:spring mvc
 *
 */
@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private MyRedisTemplate myRedisTemplate;

    @RequestMapping("/testJedisCluster")
    public Map<String, String> testJedisCluster(@RequestParam("username") String username){
        String value =  myRedisTemplate.get(MyConstants.USER_FORWARD_CACHE_PREFIX, username);
        if(value == null || "".equals(value)){
            myRedisTemplate.set(MyConstants.USER_FORWARD_CACHE_PREFIX, username, "bbbb");
            return null;
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put(username, value);
        return map;
    }

}