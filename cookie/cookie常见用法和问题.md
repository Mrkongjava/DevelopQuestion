## 将cookie写到浏览器

```
//controller代码
@GetMapping("/login")
public stringlogin(HttpServletResponse response) {

    // 设置cookie（参数分别是：cookie的key、cookie的value）
    Cookie cookie = new Cookie(name, value);
    cookie.setPath("/"); //存储路径
    cookie.setMaxAge(maxAge);//过期时间，单位秒
    response.addCookie(cookie);

    return null;
}
```



## 从request获取cookie

```
/**
 * 获取返回的map中获取到需要的cookie
 * @param request
 * @param name
 * @return
 */
public static Cookie get(HttpServletRequest request,
                       String name) {
    Map<String, Cookie> cookieMap = readCookieMap(request);
    if (cookieMap.containsKey(name)) {
        return cookieMap.get(name);
    }else {
        return null;
    }
}

/**
 * 将cookie封装成Map
 * @param request
 * @return
 */
private static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
    Map<String, Cookie> cookieMap = new HashMap<>();
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie cookie: cookies) {
            cookieMap.put(cookie.getName(), cookie);
        }
    }
    return cookieMap;
}
```



## cookie 删除

其实删除cookie好简单，将对应token的cookie的value设为null，然后过期时间设为0；那么就会自动过期，自动删除