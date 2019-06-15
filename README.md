# CAConvert Analysis for Elasticsearch
提供分词时的中文数字和阿拉伯数字的互相转换

只提供了一个 token-filter `caconvert`

Custom example:
```json
PUT /caconvert/
{
  "settings": {
    "analysis": {
      "analyzer": {
        "ik_ana_smart": {
          "type": "custom",
          "tokenizer": "ik_smart",
          "filter": [
            "caconvert_filter"
          ]
        }
      },
      "filter": {
        "caconvert_filter": {
          "type": "caconvert"
        }
      }
    }
  }
}
```

Analyze tests
```json
GET caconvert/_analyze
{
  "analyzer": "ik_ana_smart",
  "text": "五千三百四十一"
}

Output：
{
  "tokens": [
    {
      "token": "5341",
      "start_offset": 0,
      "end_offset": 7,
      "type": "TYPE_CNUM",
      "position": 0
    }
  ]
}
```
