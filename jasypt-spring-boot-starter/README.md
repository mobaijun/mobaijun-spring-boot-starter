# Jasypt 快速操作启动器

该实用程序库简化了常用的 Jasypt 操作，如加密和解密。它提供了一个易于使用的界面，用于加密敏感信息、解密加密内容以及配置
Jasypt 设置。

## 特性

- **简化的加密和解密：** 使用简单的静态方法轻松加密和解密敏感信息。
- **可配置的设置：** 初始化 Jasypt 配置设置，如密码、算法、密钥获取迭代等。

## 使用

1. **加密:**

   ```java
   String encryptedValue = JasyptUtil.encrypt("mySensitiveValue");
   ```

2. **解密:**

   ```java
   String decryptedValue = JasyptUtil.decrypt(encryptedValue);
   ```

3. **配置:**

   在使用任何加密或解密方法之前，请确保使用提供的静态配置块配置 Jasypt 设置:

   ```java
   // Jasypt 配置块
   static {
       // Jasypt 设置
       SimpleStringPBEConfig config = new SimpleStringPBEConfig();
       config.setPassword("m#gkYlGo#deYnf_PMJ5");
       config.setAlgorithm("PBEWithMD5AndDES");
       // 添加其他配置设置...
   
       // 初始化 Jasypt 加密器
       ENCRYPTOR = new PooledPBEStringEncryptor();
       ENCRYPTOR.setConfig(config);
   }
   ```

## 配置设置

以下是可用的 Jasypt 配置设置：

- **密码：** 用于加密和解密的密码。
- **算法：** 加密算法（例如，“PBEWithMD5AndDES”）。
- **密钥获取迭代：** 密钥获取迭代次数。
- **池大小：** 加密密钥池的大小。
- **提供者名称：** JCE 提供者名称。
- **盐生成器类名称：** 盐生成器的类名称。
- **IV 生成器类名称：** 初始化向量（IV）生成器的类名称。
- **字符串输出类型：** 字符串输出类型（例如，“base64”）。

## 注意

确保根据应用程序的安全要求适当配置 Jasypt 设置。

随时使用此库简化与 Jasypt 相关的任务！