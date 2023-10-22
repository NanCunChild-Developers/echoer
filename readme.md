<img src="https://github.com/NanCunChild/IMGS/blob/main/echoer/Promotion_1.png?raw=true"  style="width:128px; height:128px; border-radius:24px"/>

# ==Echoer 项目介绍==

>该项目是去中心化 Echoer 项目的App部分，需要和对应的硬件搭配使用。
---
>硬件模块使用的 *ESP32-WROOM-32* 主控，连接的无线模块为 *nRF24L01 2.4GHz* 天线为 *TXWF-JKS-20* 胶棒天线。
---
>软件上使用Java开发，API最低版本为33。目前版本采用的 **非Root** 形式，直接在应用层上搭建临时协议栈。 

# 目录
- [==Echoer 项目介绍==](#echoer-项目介绍)
- [目录](#目录)
  - [产品综述](#产品综述)
  - [工作原理](#工作原理)
  - [功能介绍](#功能介绍)
---

## 产品综述
Echoer项目旨在开发一种创新的**无中心通信系统**，使手机之间能够直接通信，不依赖互联网，通过多跳的方式，结合适当的算法，信息可以从一个节点经过多种跳转传递到最终节点，从而增加通信距离，适用于特殊场景，如**地震灾区**和**军事行动**。该项目的目标是提供一种可靠的通信解决方案，为成员提供快速联系的能力，无需依赖传统的网络基站。

---

## 工作原理

1. 加密和分布式技术：

 >确保通信内容的**安全和隐私保护**。用户的通信数据不会集中存储在单一的服务器上，减少了被攻击或数据泄露的风险。

2. 点对点通信：

>去中心化通讯设备实现**点对点**的直接通信。当用户发送消息或进行通话时，数据可以直接从发送方传输到接收方，**不需要经过中间服务器**进行转发。这种点对点通信的方式可以减少延迟和依赖性，并提高通信的效率和速度。

3. 分布式存储：

>为避免通讯数据集中存储在单一的中心化服务器上，去中心化通讯设备通常采用**分布式存储技术**。通讯数据可以分散存储在多个节点上，每个节点都保存一部分数据。这样可以提高数据的冗余性和可靠性，并**减少数据丢失的风险**。

4. 共识机制：

>为确保分布式网络中节点之间的**一致性和可信性**，去中心化通讯设备使用共识机制，（例如工作量证明机制）这些机制通过节点之间的竞争或验证来确保网络的安全性和稳定性。
---

## 功能介绍

1. 端到端加密：

>确保通信内容只能在发送方和接收方之间解密，防止任何第三方（包括服务提供商）窥探通信内容。

2. 分布式网络：
>通信通过一个分布式网络进行，而不是集中在一个中心化的服务器上。这样可以减少单点故障的风险，提高通信的**可靠性和安全性**。

3. 跨平台兼容性：
>由于去中心化通信工具的服务器端通常不会存储用户数据，因此用户可以在不同的设备和平台上无缝切换，而**不会丢失**任何信息。

4. 信息自毁：
>用户可以设定信息的存活时间，到时间后信息会**自动销毁**，无法被任何人恢复。

5. 匿名性：
>用户可以选择使用**假名或匿名**进行通信，保护自己的真实身份。

6. 数据存储去中心化：
>用户的个人信息和通信内容分散存储在多个节点上，**避免单点故障和数据泄露**的风险。