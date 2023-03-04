import com.alibaba.fastjson.JSONArray;
import org.junit.*;
import utils.HttpClientUtil;
import static org.junit.Assert.*;
import com.alibaba.fastjson.JSON;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;

public class shiyan {
    static int x=0;
    @BeforeClass
    public static void a0(){
        x=1;
        System.out.println("BeforeClass"+x);
    }
    @Before
    public void a1(){
        System.out.println("Before"+x);
    }

    @Test
    public  void apostnoheader(){
//        http://www.leiheya.com/jeecg-boot/yahelei/case/executedebug
        String url="http://www.leiheya.com/jeecg-boot/yahelei/case/executedebug";
        String body="http=http&host=www.leiheya.com&project_id=3&httpend=null&header=%7B%7D&url=%2Fjeecg-boot%2Fyahelei%2FInterface%2Flist&body2=departnameid%3D6d35e179cd814e3299bd588ea7daed3f%26url%3D%26page%3D1%26pagecount%3D10%26description%3D%26creator%3D%26project_id%3D3&body=%7B%7D&body_type=0&body_type2=1&expect=%7B%22status%22%3A%22%28int%295000%22%7D&postcondition=%7B%7D&departnameid=6d35e179cd814e3299bd588ea7daed3f&precondition=%5B%5D&method=GET&signtype=null&sign=%5B%5D&sqlid=null&expect_sql=%7B%7D";
        String res=HttpClientUtil.sendhttp(url,body,new HashMap(),"POST");
        System.out.println(res);
//        JSONArray s=JSON.parseArray(JSON.parseObject(res).getString("result"));
        assertEquals(1,2);
    }
    @Test
    public  void apostheader(){
//        http://www.leiheya.com/jeecg-boot/yahelei/case/executedebug
        Map y=new HashMap();
        y.put("Content-Type","application/x-www-form-urlencoded");
        String url="http://www.leiheya.com/jeecg-boot/yahelei/case/executedebug";
        String body="http=http&host=www.leiheya.com&project_id=3&httpend=null&header=%7B%7D&url=%2Fjeecg-boot%2Fyahelei%2FInterface%2Flist&body2=departnameid%3D6d35e179cd814e3299bd588ea7daed3f%26url%3D%26page%3D1%26pagecount%3D10%26description%3D%26creator%3D%26project_id%3D3&body=%7B%7D&body_type=0&body_type2=1&expect=%7B%22status%22%3A%22%28int%295000%22%7D&postcondition=%7B%7D&departnameid=6d35e179cd814e3299bd588ea7daed3f&precondition=%5B%5D&method=GET&signtype=null&sign=%5B%5D&sqlid=null&expect_sql=%7B%7D";
        String res=HttpClientUtil.sendhttp(url,body,y,"POST");
        System.out.println(res);
        assertEquals(1,1);
//        JSONArray s=JSON.parseArray(JSON.parseObject(res).getString("result"));
    }
    @Test
    public void a2() {
        String url="http://www.leiheya.com/jeecg-boot/yahelei/project/list";
        String body="host=&description=&creator=&departnameid=6d35e179cd814e3299bd588ea7daed3f";
        String res=HttpClientUtil.sendhttp(url,body,new HashMap(),"GET");
        System.out.println("testa2"+x);
        JSONArray s=JSON.parseArray(JSON.parseObject(res).getString("result"));
//        System.out.println(s.size()>0);
    }
    @Test
    public void a3() {
        String url="http://www.leiheya.com/jeecg-boot/yahelei/project/list";
        String body="host=&description=&creator=&departnameid=";
        String res=HttpClientUtil.sendhttp(url,body,new HashMap(),"GET");
//        System.out.println(res);
        System.out.println("testa3"+x);
        JSONArray s=JSON.parseArray(JSON.parseObject(res).getString("result"));
//        System.out.println(s.size()==0);
    }
    @After
    public void a4(){
        System.out.println("After"+x);
    }
    @AfterClass
    public static void a5(){
        x=2;
        System.out.println("AfterClass"+x);
    }
}