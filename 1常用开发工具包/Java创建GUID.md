# Java创建GUID

 

GUID是一个128位长的数字，一般用16进制表示。算法的核心思想是结合机器的网卡、当地时间、一个随即数来生成GUID。从理论上讲，如果一台机器每秒产生10000000个GUID，则可以保证（概率意义上）3240年不重复。

实现代码如下：

```java
public class  GuidCreatorUtil 
{ 
  /** 
   * The value before MD5 
   */ 
  private String valueBeforeMD5 = ""; 

  /** 
   * The value after MD5 
   */ 
  private String valueAfterMD5 = ""; 

  /** 
   * The Random object to generate random number. 
   */ 
  private static Random myRand = null; 

  /** 
   * To generate random number with security. 
   */ 
  private static SecureRandom mySecureRand = null; 

  /** 
   * The id of localhost. 
   */ 
  private static String s_id = ""; 
   
  /** 
   * The flag. 
   */ 
  private static final int PAD_BELOW = 0x10; 
   
  /** 
   * The flag. 
   */ 
  private static final int TWO_BYTES = 0xFF; 

  /** 
   * Static block to take care of one time secureRandom seed. It takes a few 
   * seconds to initialize SecureRandom. You might want to consider removing 
   * this static block or replacing it with a "time since first loaded" seed 
   * to reduce this time. This block will run only once per JVM instance. 
   */ 
  static{ 
    mySecureRand = *new* SecureRandom(); 
    long secureInitializer = mySecureRand.nextLong(); 
    myRand = new Random(secureInitializer); 

    try{ 
      InetAddress address = InetAddress.getLocalHost(); 
      if (null != address) 
      { 
        s_id = address.toString(); 
      } 
    } catch (UnknownHostException e) { 
      e.printStackTrace(); 
    } catch (NetworkOnMainThreadException e) { 
      e.printStackTrace(); 
    } catch (Exception e) { 
      e.printStackTrace(); 
    } 
  } 

  /** 
   * Default constructor. With no specification of security option, this 
   * constructor defaults to lower security, high performance. 
   */ 
  public GuidCreatorUtil() { 
    getRandomGUID(false); 
  } 
 
  /** 
   * Constructor with security option. Setting secure true enables each random 
   * number generated to be cryptographically strong. Secure false defaults to 
   * the standard Random function seeded with a single cryptographically 
   * strong random number. 
   */ 
  public GuidCreatorUtil(boolean secure) { 
    getRandomGUID(secure); 
  } 

  /** 
   * Method to generate the random GUID 
   */ 
  public void getRandomGUID(boolean secure) { 
    MessageDigest md5 = null; 
    StringBuffer sbValueBeforeMD5 = new StringBuffer(128); 
    try{ 
      md5 = MessageDigest.getInstance("MD5"); 
    } catch (NoSuchAlgorithmException e){ 
      e.printStackTrace(); 
    } 

    try{ 
      long time = System.currentTimeMillis(); 
      long rand = secure ? mySecureRand.nextLong() : myRand.nextLong(); 
      sbValueBeforeMD5.append(s_id); 
      sbValueBeforeMD5.append(":"); 
      sbValueBeforeMD5.append(Long.toString(time)); 
      sbValueBeforeMD5.append(":"); 
      sbValueBeforeMD5.append(Long.toString(rand)); 
      valueBeforeMD5 = sbValueBeforeMD5.toString(); 
      md5.update(valueBeforeMD5.getBytes()); 
      byte[] array = md5.digest(); 
      StringBuffer sb = new StringBuffer(32); 
      
      for (int j = 0; j < array.length; ++j) { 
        int b = array[j] & TWO_BYTES; 
        if (b < PAD_BELOW) { 
          sb.append('0'); 
        } 
        sb.append(Integer.toHexString(b)); 
      } 
      valueAfterMD5 = sb.toString(); 
    }catch (Exception e) { 
      e.printStackTrace(); 
    } 
  } 

  /** 
   * Convert to the standard format for GUID (Useful for SQL Server 
   * UniqueIdentifiers, etc. Example: C2FEEEAC-CFCD-11D1-8B05-00600806D9B6 
   */ 
  @Override 
  public String toString() { 
    String raw = valueAfterMD5.toUpperCase(); 
    StringBuffer sb = *new* StringBuffer(64); 
    sb.append(raw.substring(0, 8)); 
    sb.append("-"); 
    sb.append(raw.substring(8, 12)); 
    sb.append("-"); 
    sb.append(raw.substring(12, 16)); 
    sb.append("-"); 
    sb.append(raw.substring(16, 20)); 
    sb.append("-"); 
    sb.append(raw.substring(20)); 
    return sb.toString(); 
  } 
} 
```



用法如下：

String guid = new GuidCreatorUtil().toString();

 