# Java JSON 序列化如何匹配 Python json.dumps() 结果

为啥写这个? 

因为总有一些 23 乱定义接口啊～

https://www.v2ex.com/t/500208

## 坑点总结

* 忽略空字段
* 日期格式化
* 按字段排序
* json 逗号，冒号等加空格
* ascii 码转换，而且不能有大写字母

## Python 代码示例

[python_json_dumps.py](src/main/python/python_json_dumps.py)

```python
#!/usr/bin/env python3
# coding: utf-8

from json import dumps

if __name__ == '__main__':
    demo_bean = {
        "id": 1,
        "name": "demoName",
        "values": [1, 2, 3, 4]
    }
    demo_json = dumps(demo_bean, sort_keys=True).encode('utf-8')
    print(demo_json)
    # b'{"id": 1, "name": "demoName", "values": [1, 2, 3, 4]}'

    demo_bean['chinese'] = "中文"
    demo_json = dumps(demo_bean, sort_keys=True).encode('utf-8')
    print(demo_json)
    # b'{"chinese": "\\u4e2d\\u6587", "id": 1, "name": "demoName", "values": [1, 2, 3, 4]}'
```

## Java 示例

[JavaJsonDumpsTest.java](src/test/java/com/corning/jsondumps/JavaJsonDumpsTest.java)

```java
@Slf4j
public class JavaJsonDumpsTest {

    private Map<String, Object> objectMap;

    @Before
    public void setUp() throws Exception {
        objectMap = new HashMap<>();
        objectMap.put("id", 1);
        objectMap.put("name", "demoName");
        objectMap.put("values", Lists.newArrayList(1, 2, 3, 4));
    }

    @Test
    public void jsonDumps() throws JsonProcessingException {
        String result = JavaJsonDumps.dumps(objectMap);
        assertEquals("{\"id\": 1, \"name\": \"demoName\", \"values\": [1, 2, 3, 4]}", result);
    }

    @Test
    public void dumpsChinese() throws JsonProcessingException {
        objectMap.put("chinese", "中文");
        String result = JavaJsonDumps.dumps(objectMap);
        assertEquals("{\"chinese\": \"\\u4e2d\\u6587\", \"id\": 1, \"name\": \"demoName\", \"values\": [1, 2, 3, 4]}", result);
    }
}
```

* 运行结果

```log
2019-04-11 14:10:35 DEBUG JavaJsonDumps:56 - jsonString={"id": 1, "name": "demoName", "values": [1, 2, 3, 4]}
2019-04-11 14:10:35 DEBUG JavaJsonDumps:59 - jsonTranslate={"id": 1, "name": "demoName", "values": [1, 2, 3, 4]}

2019-04-11 14:10:35 DEBUG JavaJsonDumps:56 - jsonString={"chinese": "中文", "id": 1, "name": "demoName", "values": [1, 2, 3, 4]}
2019-04-11 14:10:35 DEBUG JavaJsonDumps:59 - jsonTranslate={"chinese": "\u4e2d\u6587", "id": 1, "name": "demoName", "values": [1, 2, 3, 4]}
```

## API 接口签名验证的正确实现方式

Python 服务端直接粗暴地 `json.dumps()` 序列化后做 MD5 校验简直太坑人了，如果不是拿到对方的源码分析，我根本就别想连调了。

那怎么才算正确的实现方式呢？我觉得可以参考这些：
 
* [cnblogs 开放api接口签名验证](https://www.cnblogs.com/codelir/p/5327462.html)
* [gitee HTTP Sign](https://gitee.com/xixifeng.com/httpsign)

