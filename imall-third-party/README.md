# imall-third-party

模块说明：主要提供项目对外的接口

主要封装：统一入参反参格式，自动拆包打包，使业务代码与拆包打包分离，我们手写业务代码时依旧按照spring提供的方式即可。

- 自动包装：入参自动校验请求格式和拆包（加密验签、校验token、时间戳等），反参自动对返回参数打包（加密加签）
- 开关配置：是否校验token、是否校验时间戳、校验时间戳时长、是否开启明文请求、是否校验sign

## 规则

对每个接口请求都需要传递 **token、ts、data、sign** 四个参数，权限校验机制基于这些参数进行。

- token：用于证明调用者身份、权限以及时效性的临时令牌，通过 token 接口根据对应的 tokentype 获取，所获取的 token 当天有效。
- ts：调用时刻的 unix 时间戳，单位为秒（10位），若阈值范围在（前后 5 分钟）内的调用被认为是合法的。主要防止重放攻击。
- data：接口请求传送的具体数据，根据接口需求来定义内容，值为 json 串，sign 签名计算时需要对 data 进行 BASE64（UTF-8）加密。

- sign：参数签名，需要根据 token、ts、data、私钥 计算出来。

  请求sign算法：按规则拼接 `ts=值&token=值&data=值&secret=值` 得到一个待签名值，将待签名值进行 MD5 加密得到 sign 数据值。

  响应sign算法：按规则拼接 `code=值&message=值&ts=值&data=值&secret=值` 得到一个待签名值，将待签名值进行 MD5 加密得到 sign 数据值。

## 示例

### 报文

url：http://localhost:8100/third-party/json/1/60001001/token

请求报文：

```json
{
    "data": "eyJ0b2tlbnR5cGUiOiJDRFIifQ==",
    "sign": "818e111ae47e6be96f2bfbf37894183f",
    "token": "",
    "ts": 1658546259
}
# data明文为："data": {"tokentype": "CDR"}
```

响应报文：

```json
{
    "code": 0,
    "data": "eyJ0b2tlbiI6ImExMWJjNTk0ZTMxYjQ4NzliNDBmN2Y1OTUwMWVlN2M0IiwidG9rZW50eXBlIjoiQ0RSIn0=",
    "message": "请求成功",
    "sign": "95fad862811abe43c3ad433dac16cec0",
    "ts": 1658546282
}
# data明文为："data": {"tokentype": "CDR", "token": "a11bc594e31b4879b40f7f59501ee7c4"}
```

当然，为方便开发调试，开发时可以在项目中暂时关闭各种校验，开启明文请求。

### 业务代码

按照传统spring方式即可，TokenVo为入参data对象，TokenDTO为反参data对象，在业务代码中直接接受处理返回即可，剩余的拆包打包会自动处理。

```java
@Slf4j
@RestController
@RequestMapping("/json/1/{companyid}")
@Api(tags = "token接口服务", description = "token管理")
public class TokenController {

    @ApiOperation("获取token")
    @ThirdPartyPublicParam(tokenType = TokenTypeEnum.Empty)
    @PostMapping("/token")
    public TokenDTO getToken(@PathVariable("companyid") String companyId,
                             @RequestBody @Validated TokenVo tokenVo) throws ApiException {
        log.info("companyId: {}, tokenVo: {}", companyId, tokenVo);
        String tokenType = tokenVo.getTokentype();
        String token = ThirdPartyPublicParamPlugin.createToken(tokenType, companyId);
        return new TokenDTO(tokenType, token);
    }
}
```

