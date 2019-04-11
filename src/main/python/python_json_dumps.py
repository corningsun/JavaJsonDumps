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



