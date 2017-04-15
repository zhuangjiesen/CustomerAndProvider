# WebSocket 与 netty服务器 实战

### netty版本 4.1.9.Final 

#### 后续献上 线上的例子....

#### websocket原理是 
##### 客户端浏览器首先要向服务器发送一个http请求，这个请求与一般的http请求不同，包含了一些附加头消息，其中  Upgrade:WebSocket 表明这是一个申请协议升级的http请求 ，服务端解析这些附加头消息，并生成应答消息返回给客户端，客户端和服务端的WebSocket连接就建立起来了，双方可以通过这个连接通道自由地传递信息，并且这个连接会持续存在直到客户端和服务端某一方主动关闭连接

### WebSocket不兼容ie8 , ie9 
[项目源码地址](https://github.com/zhuangjiesen/DragsunNetty/tree/master/WebSocketNetty)

##### [前端页面代码](https://github.com/zhuangjiesen/DragsunNetty/tree/master/WebSocketNetty/FrontEnd)
##### [netty服务器代码](https://github.com/zhuangjiesen/DragsunNetty/tree/master/WebSocketNetty/NettyServer/JavaProServer)

### 前端页面

[H5中的WebSocket对象](http://www.runoob.com/html/html5-websocket.html)
#### 为了直观的看到聊天效果，把做成了左右两边的聊天框
#### 先注册用户名，注册成功后，可以输入接收人，发送消息；


##### 将消息类型封装成 messageType 对象
##### 将消息对象封装成类 WebSocketMessage 

##### 消息处理类 MessageTypeHandler 可以注册消息事件的回调函数，进行消息处理

##### 主要的 AppWebSocket 类
##### 用于对 websocket对象 的方法调用（消息发送，websocket连接 ，消息到达，连接关闭等..）的封装

#### nettyAlone.html 主要页面

```


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> webSocket 实例</title>
<script type="text/javascript"   src="../js/zepto.min.js" ></script>

<style type="text/css">
html,body{
	height: 98%;
	margin: 0;
}
ul,li,h1,h2,h3,h4,h5{
	padding: 0;
	margin: 0;
}
.mySendMsg {
	padding-left:300px;
	color : red;
}

.container{
	height: 100%;
	position: relative;
}


.left-container{
	position: absolute;
	left: 0;
	padding-left: 30px;
	width: 50%;
}


.right-container{
	width: 49%;
	position: absolute;
	left: 60%;
}

.chat-container{
	text-align: center;

}

.chat-container li{
	width: 45%;
	margin-top: 20px;
	display: inline-block;

}

.regist-box{
	border-top: 2px solid #000;
	padding-top: 20px;

	padding-bottom: 20px;
	border-bottom: 2px solid #000;
}
.online-list{
	margin-top: 30px;
	padding-bottom: 20px;
	border-bottom: 2px solid #000;
}
.chat-box{
	margin-top: 30px;
	padding-bottom: 20px;
	border-bottom: 2px solid #000;
}
button{
	margin-top: 20px;
}
.line-container{
	position: absolute;
	left: 53%;
	height: 100%;
	width: 2px;
	background: black;
}
.chat-content{
	display: none;
}
.left-chat-content{
	display: none;
}
</style>
</head>
<body>

<div  >聊天</div>

<div class="container">
	<div class="left-container" >
		<h3>左边聊天框</h3>
		<div class="regist-box">
			<div>注册</div>
			<label>用户名</label>
			<input type="text" name="leftRegistName" id="leftRegistName"   value="jason" >
			<br>
			<button id="leftRegistBtn" >注册用户</button>
			<div style="color:red;text-align :center; " id="leftRegistStatus"></div>

		</div>
		<div class="online-list left-chat-content">
			<div>在线列表</div>
			<ul id="leftOnlineList">
				<li></li>

			</ul>


		</div>
		<div class="chat-box left-chat-content"  >
			<div>聊天</div>

			<label>接受名</label>
			<input type="text" name="leftReceiveName" id="leftReceiveName" value="小黑"  >
			<br>
			<input type="text" name="leftContent" id="leftContent" value="小黑你好呀"  >
			<br>
			<button id="leftSendBtn" >发送消息</button>



		</div>
		<div class="chat-list left-chat-content">
			<div>聊天列表</div>

			<ul id="leftChatList">


			</ul>


		</div>



	</div>
	<div class="line-container" ></div>

	<div class="right-container" >
		<h3>右边聊天框</h3>
		<div class="regist-box">
			<div>注册</div>
			<label>用户名</label>
			<input type="text" name="registName" id="registName"  value="小黑">
			<br>
			<button id="registBtn" >注册用户</button>
			<div   style="color:red;text-align :center; " id="registStatus"></div>

		</div>
		<div class="online-list chat-content">
			<div>在线列表</div>
			<ul id="onlineList">
				<li></li>

			</ul>


		</div>
		<div class="chat-box chat-content">
			<div>聊天</div>

			<label>接受名</label>
			<input type="text" name="receiveName" id="receiveName"  value="jason">
			<br>
			<input type="text" name="content" id="content"  value="jason你好呀"   >
			<br>
			<button id="sendBtn" >发送消息</button>



		</div>
		<div class="chat-list chat-content">
			<div>聊天列表</div>

			<ul id="chatList">


			</ul>


		</div>



	</div>


	<div>


	</div>

</div>



<script type="text/javascript">


/*
 *
 http://www.runoob.com/html/html5-websocket.html
 */




 //消息类型
var messageType = {
	'regist' : 1 , //注册
	'sendMsg' : 2 , //发送消息
	'registSuccess' : 3 , //注册成功
	'registFail' : 4 , //注册失败
	'registDuplicate' : 5 , //重复用户
	'onlineUserEvent' : 6 , //在线用户推送
	'messageComeEvent' : 7 , //接收消息
	'sendFailNotFound' : 8 , //发送消息时找不到对象
	'sendError' : 9 , //发送消息错误
}

//消息类型调度
function MessageTypeHandler () {
	//注册
	this.regist = function(data){
	} ;

	 //发送消息
	this.sendMsg = function(data){
	} ;

	//注册成功
	this.registSuccess = function(data){
	} ;

	 //注册失败
	this.registFail = function(data){
	} ;

	//重复用户
	this.registDuplicate = function(data){
	} ;
	//在线用户推送
	this.onlineUserEvent = function(data){
	} ;
	 //接收消息
	this.messageComeEvent = function(data){
	} ;
	 //发送消息时找不到对象
	this.sendFailNotFound = function(data){
	} ;
	this.sendError = function(data){
	} ;

}



//消息对象
function WebSocketMessage (msgType , sendName , receiveName , content ) {
	 //消息类型
	 this.messageType = msgType;
	 //发送人
	 this.sendName = sendName;
	 // 接收人
	 this.receiveName = receiveName;
	 // 消息内容
	 this.content = content;

	 this.toJsonString = function(){
		 var obj = {};
		 obj.messageType = this.messageType;
		 obj.sendName = this.sendName;
		 obj.receiveName = this.receiveName;
		 obj.content = this.content;
		 return JSON.stringify(obj);
	 }
}


function AppWebSocket(url){
	this.name = null;
	this.isOpen = false;
	this.socket =  null;
	//关闭
	this.close = null;
	//发送方法
	this.send = null;
	//注册事件回调
	this.registCallback = null;
	//请求url
	this.url = url;
	this.MessageTypeHandler = null;

	this.setMessageTypeHandler = function (handler){
		this.MessageTypeHandler = handler;
	};


	//初始化并注册用户
	this.init = function(registName , isRegist , callback){
			 if ("WebSocket" in window) {
					var webSocket;
					try {
						//没有url
						if (!this.url) {
							return false;
						}

						webSocket = new WebSocket(this.url);
						webSocket.onerror = function (event) {
							console.log(' an error coming... ' , event);
							if (webSocket.readyState == 3) {
								//连接已经关闭或者未打开
								alert('连接失败');
							}
						}
					} catch (error) {
						console.log(' error : '+error);
					}
					this.socket = webSocket;


					//this 赋值，为了onopen中对send 等方法初始化
				 	var appWebSocketObj = this;


					// 打开Socket
					webSocket.onopen = function(event) {
						//WebSocket.OPEN 数值 1
						if (webSocket.readyState == WebSocket.OPEN) {
								this.isOpen = true;
							  	// 监听消息
							  	appWebSocketObj.onmessage = function(event) {
								  	webSocket.onMessage(event);
							  	};

							 	// 监听Socket的关闭
							  	appWebSocketObj.onclose = function(event) {
								  	webSocket.onClose(event);
							  	};

							  	//代理发送
							  	appWebSocketObj.send = function (msg){
								  	webSocket.send(msg);
							  	}

							  	appWebSocketObj.close = function (){
								 	webSocket.close();
							  	}

								
								if (isRegist) {
									if (callback) {
										appWebSocketObj.registCallback = callback;
									}
									//是否注册
									appWebSocketObj.regist(registName ,callback );
								}
						} else {
							return false;
						}
					}
			 } else {
				 alert("您的浏览器不支持 WebSocket!");
			 }

		};

	//注册用户名
	this.regist = function (registName ,callback ){
		//注册用户名
		if (registName && registName.length > 0) {
			//未注册回调函数
			if (!this.registCallback) {
				this.registCallback = callback;
			}

			//发送注册消息
			var webSocketMessage = new WebSocketMessage(messageType.regist , registName , null ,null );

			if (this.send) {
				this.send(webSocketMessage.toJsonString());
			} else {
				alert('消息发送函数未注册');

			}
		} else {
			return false;
		}
	};
	

	//消息到达
	this.onMessage = function (event){
	 	console.log('Client received a message',event);
	 	var data = event.data ;
	 	if (data && data.length > 0) {
	 		var dataObj = JSON.parse(data);
	 		var resultMessageType = dataObj.messageType;
	 		var messageTypeHandler = this.MessageTypeHandler;
			if (messageTypeHandler && typeof(messageTypeHandler == 'function') ){

		 	} else {
		 		console.error('请配置消息处理器');
		 	}

	 		switch(resultMessageType)
	 		{
		 		case messageType.regist:
		 			{
		 				//注册
		 				messageTypeHandler.regist(dataObj);
		 				break;
		 			}
		 		case messageType.messageComeEvent:
		 		  	{
		 		  		//消息达到
		 		  		messageTypeHandler.messageComeEvent(dataObj);
		 				break;
		 		  	}
		 		case messageType.registSuccess:
		 		  	{
		 		  		messageTypeHandler.registSuccess(dataObj);
		 		  		 //注册成功
						this.registCallback(true , event);
		 		  		break;

		 		  	}
		 		case messageType.registFail:
		 		  	{
		 		  		//注册失败
		 		  		messageTypeHandler.registFail(dataObj);
	  					this.registCallback(false , event);

						break;
		 		  	}
		 		case messageType.registDuplicate:
		 		  	{
		 		  		//重复消息
		 		  		messageTypeHandler.registDuplicate(dataObj);
	  					this.registCallback(false , event);
						break;
		 		  	}
		 		case messageType.onlineUserEvent:
		 		  	{
		 		  		//在线用户列表
		 		  		messageTypeHandler.onlineUserEvent(dataObj);
						break;
		 		  	}
				case messageType.sendFailNotFound:
		 		  	{
		 		  		//找不到接受对象
		 		  		messageTypeHandler.sendFailNotFound(dataObj);
						break;
		 		  	}
		 		case messageType.sendError:
		 		  	{
		 		  		//找不到接受对象
		 		  		messageTypeHandler.sendError(dataObj);
						break;
		 		  	}

		 		default:
	 		}



	 	} else {
	 		//消息为空
	 		alert('消息为空');
	 	}
	};

	//连接关闭
	this.onClose = function (event){
	 	console.log('Client notified socket has closed ..... ',event);
	 	alert('连接被关闭！！');
	};





}



function appendReceiveContent( sendName , content ,id){
	$('#'+id).append( '<li>'+ sendName +':'+content+'</li>');
}
function appendSendContent(msg , id){
	$('#'+id).append('<li class="mySendMsg" >我: '+msg+'</li>');
}
function appendOnlineList(item , id){
	$('#'+id).append('<li class="" >'+item+'</li>');
}








/*
$('#divDom1').on( 'click' ,function(){
});
*/
</script>
<script type="text/javascript"   src="../js/leftChatBox.js" ></script>

<script type="text/javascript"  src="../js/rightChatBox.js" ></script>
</body>
</html>






```



#### 左边聊天框 leftChatBox.js 


```

//设置函数，相当于闭包环境
function chatBoxLeft(){
	//自定义 WebSocket 对象
	//用于代理send 与 close 方法 使得外部调用
	var appWebSocket = new AppWebSocket('ws://127.0.0.1:18888');

	//消息处理
	var messageTypeHandler = new MessageTypeHandler();

	//注册成功
    messageTypeHandler.registSuccess = function(data){



		 	$('.left-chat-content').show();
		 	$('#leftRegistStatus').text('注册成功!');
	} ;

	 //注册失败
	messageTypeHandler.registFail = function(data){

 			//注册失败
 			alert('注册失败');
			$('#leftRegistStatus').text('注册失败!');
	} ;



	//重复用户
	messageTypeHandler.registDuplicate = function(data){


 			//注册失败
 			alert('重复用户名!');
			$('#leftRegistStatus').text('重复用户名!');
	} ;
	//在线用户推送
	messageTypeHandler.onlineUserEvent = function(data){

 			//在线用户推送
 			var onlineUserList = JSON.parse(data.content)['list'] ;
	 		if (onlineUserList && onlineUserList.length > 0) {
	 			$('#leftOnlineList').html('');
	 			for (var i = 0 ; i < onlineUserList.length ; i++) {
		 			appendOnlineList(onlineUserList[i] , 'leftOnlineList');
	 			}
	 		}
	} ;
	 //接收消息
	messageTypeHandler.messageComeEvent = function(data){

			//接收消息
			var content = data.content;
			var sendName = data.sendName;
 			appendReceiveContent(sendName , content , 'leftChatList');
	} ;

	 //接收消息失败
	messageTypeHandler.sendFailNotFound = function(data){

			alert('找不到接收人');
	} ;

	 //接收消息失败
	messageTypeHandler.sendError = function(data){

			alert(data.content);
	} ;





	appWebSocket.setMessageTypeHandler(messageTypeHandler) ;

	//注册用户
	$("#leftRegistBtn").on('click',function(e){
		var registName =  $('#leftRegistName').val();
		appWebSocket.init(registName , true , function (success ,event){
			if (success) {
				$('#leftRegistBtn').hide();
				console.log('注册成功');
			} else {

				
			}

		});


	});





	//$("#input1")是jQuery的实例，调用这个扩展方法
	$("#leftSendBtn").on('click',function(e){

		if (appWebSocket.send) {

			var receiveName = $('#leftReceiveName').val();
			var content = $('#leftContent').val();

			var webSocketMessage = new WebSocketMessage(messageType.sendMsg , null , receiveName ,content );
			appWebSocket.send(webSocketMessage.toJsonString() );
			appendSendContent(content , 'leftChatList');
		}

	//	if (appWebSocket.close) {
	//		appWebSocket.close();
	//	}



	});



}


//初始化左侧聊天框
chatBoxLeft();

```

#### 右侧聊天框 rightChatBox.js 


```


//设置函数，相当于闭包环境
function chatBoxRight(){
	//自定义 WebSocket 对象
	//用于代理send 与 close 方法 使得外部调用
	var appWebSocket = new AppWebSocket('ws://127.0.0.1:18888');

	//消息处理
	var messageTypeHandler = new MessageTypeHandler();

	//注册成功
	messageTypeHandler.registSuccess = function(data){
		 	$('.chat-content').show();
		 	$('#registStatus').text('注册成功!');

	} ;

	 //注册失败
	messageTypeHandler.registFail = function(data){

 			//注册失败
 			alert('注册失败');
			$('#registStatus').text('注册失败!');
	} ;

	//接收消息失败
	messageTypeHandler.sendFailNotFound = function(data){

			alert('找不到接收人');
	} ;


		 //接收消息失败
	messageTypeHandler.sendError = function(data){

			alert(data.content);
	} ;


	//重复用户
	messageTypeHandler.registDuplicate = function(data){

 			//注册失败
 	 		alert('重复用户名!');
			$('#registStatus').text('重复用户名!');
	} ;
	//在线用户推送
	messageTypeHandler.onlineUserEvent = function(data){

 			//在线用户推送
 			var onlineUserList = JSON.parse(data.content)['list'] ;
	 		if (onlineUserList && onlineUserList.length > 0) {
	 			$('#onlineList').html('');
	 			for (var i = 0 ; i < onlineUserList.length ; i++) {
		 			appendOnlineList(onlineUserList[i] , 'onlineList');
	 			}
	 		}

	} ;
	 //接收消息
	messageTypeHandler.messageComeEvent = function(data){

			//接收消息
			var content = data.content;
			var sendName = data.sendName;
 			appendReceiveContent(sendName , content , 'chatList');
	} ;

	appWebSocket.setMessageTypeHandler(messageTypeHandler) ;



	//注册用户
	$("#registBtn").on('click',function(e){
		var registName =  $('#registName').val();
		appWebSocket.init(registName , true , function (success ,event){
			if (success) {
				$('#registBtn').hide();
				console.log('注册成功');
			} else {

			}

		});


	});



	//$("#input1")是jQuery的实例，调用这个扩展方法
	$("#sendBtn").on('click',function(e){

		if (appWebSocket.send) {

			var receiveName = $('#receiveName').val();
			var content = $('#content').val();

			var webSocketMessage = new WebSocketMessage(messageType.sendMsg , null , receiveName ,content );
			appWebSocket.send(webSocketMessage.toJsonString() );
			appendSendContent(content , 'chatList');
		}

	//	if (appWebSocket.close) {
	//		appWebSocket.close();
	//	}



	});



}



//初始化右侧聊天框
chatBoxRight();


```


### netty 服务端
##### 例子是通过 《Netty权威指南》的websocket 开发上的例子 



##### 程序入口 App.java
##### 服务端是用netty写的程序

##### 入口  AppWebSocketNettyServer.java 的 startNettyServer() 方法


```


public class AppWebSocketNettyServer {
	private ExecutorService executorService = Executors.newSingleThreadExecutor();

	public AppWebSocketNettyServer() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public void startNettyServer() {
		executorService.execute(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub

				

		        EventLoopGroup boss = new NioEventLoopGroup();
		        EventLoopGroup worker = new NioEventLoopGroup();  
		  
		        try {  
		  
		            ServerBootstrap bootstrap = new ServerBootstrap();  
		          
		            
		            bootstrap.group(boss, worker);  
		            bootstrap.channel(NioServerSocketChannel.class);  
		            bootstrap.option(ChannelOption.SO_BACKLOG, 1024); //连接数  
		            bootstrap.option(ChannelOption.TCP_NODELAY, true);  //不延迟，消息立即发送  
//		            bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 2000);  //超时时间
		            bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true); //长连接  
		            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {  
		                @Override  
		                protected void initChannel(SocketChannel socketChannel)  
		                        throws Exception {  

		                	ChannelPipeline p = socketChannel.pipeline();

							WebSocketIpTableHandler ipTableHandler = new WebSocketIpTableHandler();
							p.addLast(ipTableHandler);

							//请求处理类
		                	WebSocketServerHandler webSocketServerHandler = new WebSocketServerHandler();
				
消息处理类			WebSocketMessageManager webSocketMessageManager = new WebSocketMessageManager();
							webSocketServerHandler.setWebSocketMessageManager(webSocketMessageManager);

		                	p.addLast("http-codec", new HttpServerCodec());
		                	p.addLast("aggregator", new HttpObjectAggregator(65536));
		                	p.addLast("http-chunked", new ChunkedWriteHandler());
		                	p.addLast("handler", webSocketServerHandler);
		                	
		                }  
		            });  
		            ChannelFuture f = bootstrap.bind(18888).sync();  
		          
		            
		            if (f.isSuccess()) {  
		               System.out.println("启动Netty服务成功 !! ");  
		            }
		  
		        } catch (Exception e) {  
		            e.printStackTrace();  
		        } finally {  
//		            boss.shutdownGracefully();  
//		            worker.shutdownGracefully();  
		        }  
		        System.out.println(" netty starting ... ");  

			}
		});
		
		
	}

	
}




```


### 主要消息处理类两个 WebSocketServerHandler.java WebSocketMessageManager.java 

#### 请求处理类 WebSocketServerHandler.java
##### WebSocketServerHandler.java 类继承 ChannelInboundHandlerAdapter.java 具体 netty 框架的 ChannelPipeline用法，自己去了解；


```

public class WebSocketServerHandler extends ChannelInboundHandlerAdapter    {

	/*websocket消息请求处理*/
	private WebSocketMessageManager webSocketMessageManager;


	public final static String WEBSOCKET= "websocket";
	public final static String Upgrade= "Upgrade";

	public final static String WEBSOCKET_ADDRESS_FORMAT="ws://%s/websocket";

	private WebSocketServerHandshaker handshaker;



	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(" WebSocketServerHandler _ channelActive.. ");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		if (msg instanceof FullHttpRequest) {
			//处理http请求
			handleHttpRequest(ctx, ((FullHttpRequest) msg));
		} else if (msg instanceof WebSocketFrame) {
			//处理websocket请求
			handlerWebSocketFrame(ctx, (WebSocketFrame) msg);
		} else if (msg instanceof CloseWebSocketFrame) {
			//关闭链路
			onWebSocketFrameColsed(ctx, (CloseWebSocketFrame)msg);
		} else {
			throw new RuntimeException("无法处理的请求");
		}
	}
	

	

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(" WebSocketServerHandler _ channelReadComplete.. ");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println(" WebSocketServerHandler _ exceptionCaught..  cause : "+cause.getMessage());
	}




	/*
	* 处理http请求
	* */
	public void handleHttpRequest(ChannelHandlerContext ctx,
								  FullHttpRequest req) {
		//不是websocket请求
		if (!req.decoderResult().isSuccess()
				|| (!this.WEBSOCKET.equals(req.headers().get(this.Upgrade).toString().toLowerCase()))) {

			DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(
					HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST);

			// 返回应答给客户端
			if (defaultFullHttpResponse.status().code() != 200) {
				ByteBuf buf = Unpooled.copiedBuffer(defaultFullHttpResponse.status().toString(),
						CharsetUtil.UTF_8);
				defaultFullHttpResponse.content().writeBytes(buf);
				buf.release();
			}
			// 如果是非Keep-Alive，关闭连接
			ChannelFuture f = ctx.channel().writeAndFlush(defaultFullHttpResponse);

			boolean isKeepAlive = false;

			if ((!isKeepAlive) || defaultFullHttpResponse.status().code() != 200) {
				f.addListener(ChannelFutureListener.CLOSE);
			}
			return ;
		}



		String host =req.headers().get("Host").toString();
		String webAddress = StringFormatter.format(WEBSOCKET_ADDRESS_FORMAT, host).getValueSafe();
		WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
				webAddress, null, false);
		WebSocketServerHandshaker handshaker = wsFactory.newHandshaker(req);
		if (handshaker == null) {
			WebSocketServerHandshakerFactory
					.sendUnsupportedVersionResponse(ctx.channel());
		} else {
			handshaker.handshake(ctx.channel(), req);
		}
	}

	/*
	* 处理websocket 请求
	* */
	public void handlerWebSocketFrame(ChannelHandlerContext ctx,
									  WebSocketFrame frame) {
		// 判断是否关闭链路的指令
		if ((frame instanceof CloseWebSocketFrame)) {
			System.out.println("关闭连接.... ");
			onWebSocketFrameColsed(ctx, frame);
			return ;
		}

		// 判断是否ping消息
		if (frame instanceof PingWebSocketFrame) {
			ctx.channel().write(
					new PongWebSocketFrame(frame.content().retain()));
			return;
		}
		if (frame instanceof PongWebSocketFrame) {
			ctx.channel().write(
					new PingWebSocketFrame(frame.content().retain()));
			return;
		}
		System.out.println("frame : "+frame.getClass().getName());


		// 本例程仅支持文本消息，不支持二进制消息
		if (!(frame instanceof TextWebSocketFrame)) {
			System.out.println("本例程仅支持文本消息，不支持二进制消息");
			throw new UnsupportedOperationException(String.format(
					"%s frame types not supported", frame.getClass().getName()));
		}

		// 是否注册用户
		WebSocketMessage registMessage = webSocketMessageManager.isRegistWebSocket(frame , ctx);
		if (registMessage != null) {
			String host = WebSocketUtil.getHost(ctx);
			if (webSocketMessageManager.isIpRegistLimited(host)) {
				//ip限制
				WebSocketMessage webSocketMessage = new WebSocketMessage();
				webSocketMessage.setMessageType(9);
				webSocketMessage.setContent("ip限制");
				TextWebSocketFrame tws = new TextWebSocketFrame(webSocketMessage.toJSONString());
				ctx.channel().writeAndFlush( tws);

				onWebSocketFrameColsed(ctx ,frame);
			} else {
				webSocketMessageManager.registIp(host);
				//注册
				webSocketMessageManager.registWebSocket(frame , ctx , registMessage);
			}

		} else {
			//发送消息
			WebSocketMessage webSocketMsg = webSocketMessageManager.getWebSocketMsg(frame, ctx);
			int result = webSocketMessageManager.sendMsg(webSocketMsg);
			if (result != 1) {
				//发送失败

				WebSocketMessage webSocketMessage = new WebSocketMessage();
				webSocketMessage.setMessageType(8);
				webSocketMessage.setContent("找不到接收人");
				TextWebSocketFrame tws = new TextWebSocketFrame(webSocketMessage.toJSONString());
				ctx.channel().writeAndFlush( tws);
			} else {
				//发送成功 可以将数据保存到数据库

			}
		}
	}



	/*
	* 处理websocket 关闭请求
	* */
	public void onWebSocketFrameColsed(ChannelHandlerContext ctx,
									   WebSocketFrame frame) {
		webSocketMessageManager.removeSender(ctx.channel().id().asLongText());
		if (handshaker != null){
			handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame
					.retain());
		}
	}

	public WebSocketMessageManager getWebSocketMessageManager() {
		return webSocketMessageManager;
	}

	public void setWebSocketMessageManager(WebSocketMessageManager webSocketMessageManager) {
		this.webSocketMessageManager = webSocketMessageManager;
	}
}

```

#### 消息处理类WebSocketMessageManager.java 

```

/**
 *
 * websocket消息管理类
 * Created by zhuangjiesen on 2017/4/14.
 */
public class WebSocketMessageManager {


    //用户集合
    public static ConcurrentHashMap<String, WebSocketHandlerContext> sendNameAndContextMap = new ConcurrentHashMap<String, WebSocketHandlerContext>();
    //channelid与用户名map
    private static ConcurrentHashMap<String, String> channelIdAndsendNameMap = new ConcurrentHashMap<String, String>();


    /*注册限制方式爆刷*/
    private final static int ipRegistLimit = 3;

    //ip列表 ，一个ip登陆次数
    private static ConcurrentHashMap<String, Integer> ipMap = new ConcurrentHashMap<String, Integer>();


    /** 在线人数统计 线程**/
    private static ScheduledExecutorService executorService = null;

    //2 秒推送
    private static int timeSeconds = 2;
    //用户新增
    private static AtomicBoolean hasOnlineUserAdd = new AtomicBoolean(false);
    //用户减少
    private static AtomicBoolean hasOnlineUserReduce = new AtomicBoolean(false);




    public WebSocketMessageManager() {
        // TODO Auto-generated constructor stub
        startSchedule();
    }

    
    /**
     * @Description: TODO
     * 开启定时线程 统计在线人数并推送
     * @author zhuangjiesen
     * @date 2017年4月12日 下午3:39:04
     */
    public synchronized void startSchedule(){
        if (executorService == null) {
            executorService = Executors.newScheduledThreadPool(1);
            executorService.scheduleAtFixedRate(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    //在线用户增加
                    if (hasOnlineUserAdd.get()) {
                        pushOnlineList();
                        hasOnlineUserAdd.compareAndSet(true, false);
                    } else {
                    }

                    //在线用户减少
                    if (hasOnlineUserReduce.get()) {
                        pushOnlineList();
                        hasOnlineUserReduce.compareAndSet(true, false);
                    } else {
                    }

                }
            }, timeSeconds, timeSeconds,  TimeUnit.SECONDS);
        }

    }


    /*
    * 给前端推送在线用户
    * */
    public void pushOnlineList(){

        Enumeration<String> keys = sendNameAndContextMap.keys();
        Collection values = null;
        while (keys.hasMoreElements()) {
            if (values == null) {
                values = channelIdAndsendNameMap.values();
            }

            String key = keys.nextElement();
            WebSocketHandlerContext webSocketHandlerContext = sendNameAndContextMap.get(key);
            Channel channel = webSocketHandlerContext.getChannelHandlerContext().channel();
            if (channel.isOpen() && channel.isOpen() && channel.isRegistered() && channel.isWritable()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("list", values);

                WebSocketMessage webSocketMessage = new WebSocketMessage();
                webSocketMessage.setMessageType(6);
                webSocketMessage.setContent(jsonObject.toJSONString());
                webSocketMessage.setSendName("管理员");
                TextWebSocketFrame tws = new TextWebSocketFrame(webSocketMessage.toJSONString());
                channel.writeAndFlush( tws);
            }
        }

    }

    /*
    * 注册聊天用户名
    * */
    public boolean registWebSocket(WebSocketFrame frame , ChannelHandlerContext ctx ,WebSocketMessage webSocketMessage ){
        //注册事件
        //注册用户
        String sendName = null;
        sendName = webSocketMessage.getSendName();
        System.out.println("regist : "+sendName);
        if (sendNameAndContextMap.containsKey(sendName)){
            System.out.println("已经存在注册用户");
            WebSocketMessage messageResult = new WebSocketMessage();
            messageResult.setMessageType(5);
            messageResult.setContent("已经存在注册用户");
            TextWebSocketFrame tws = new TextWebSocketFrame(messageResult.toJSONString());
            ctx.channel().writeAndFlush( tws);
            return false;
        }
        WebSocketHandlerContext webSocketHandlerContext = new WebSocketHandlerContext();
        webSocketHandlerContext.setId(ctx.channel().id().asLongText());
        webSocketHandlerContext.setName(sendName);
        webSocketHandlerContext.setChannelHandlerContext(ctx);

        sendNameAndContextMap.put(sendName, webSocketHandlerContext);
        channelIdAndsendNameMap.put(ctx.channel().id().asLongText(), sendName);
        WebSocketHandlerContext handlerContext = sendNameAndContextMap.get(sendName);
        if (handlerContext != null) {
            System.out.println("注册成功");

            if (!hasOnlineUserAdd.get()) {
                hasOnlineUserAdd.compareAndSet(false, true);
            }

            WebSocketMessage messageResult = new WebSocketMessage();
            messageResult.setMessageType(3);
            messageResult.setContent("注册成功");
            TextWebSocketFrame tws = new TextWebSocketFrame(messageResult.toJSONString());
            ctx.channel().writeAndFlush( tws);
            return true;
        } else {
            System.out.println("注册失败");

            WebSocketMessage messageResult = new WebSocketMessage();
            messageResult.setMessageType(4);
            messageResult.setContent("注册失败");
            TextWebSocketFrame tws = new TextWebSocketFrame(messageResult.toJSONString());
            ctx.channel().writeAndFlush( tws);
            return false;
        }
    }





    /**
     * @Description: TODO
     * 是否是注册消息
     * @author zhuangjiesen
     * @date 2017年4月11日 上午10:32:57
     * @param request
     * @return
     */
    public WebSocketMessage isRegistWebSocket(WebSocketFrame frame , ChannelHandlerContext ctx ){
        WebSocketMessage webSocketMessage = null;
        String requestContent = ((TextWebSocketFrame) frame).text();
        if (requestContent != null && (!requestContent.isEmpty())) {
            webSocketMessage = JSONObject.parseObject(requestContent, WebSocketMessage.class);
            if (webSocketMessage != null) {
                Integer messageType = webSocketMessage.getMessageType();
                if (messageType != null) {
                    switch (messageType.intValue()) {
                        case 1: {
                            return webSocketMessage;
                        }
                        default:
                            break;
                    }
                } else {
                    throw new RuntimeException("消息类型为空");
                }
            }

        }
        return null;
    }




    /**
     * @Description: TODO
     * 将请求封装成消息类型
     * @author zhuangjiesen
     * @date 2017年4月12日 下午3:38:08
     * @param frame
     * @param ctx
     * @return
     */
    public WebSocketMessage getWebSocketMsg(WebSocketFrame frame ,ChannelHandlerContext ctx ){
        WebSocketMessage webSocketMsg = null;
        String requestContent = ((TextWebSocketFrame) frame).text();
        if (requestContent != null && (!requestContent.isEmpty())) {
            WebSocketMessage webSocketMessage = JSONObject.parseObject(requestContent, WebSocketMessage.class);
            if (webSocketMessage != null) {
                Integer messageType = webSocketMessage.getMessageType();
                if (messageType != null) {
                    switch (messageType.intValue()) {
                        case 2:
                        {
                            //发送消息
                            String remoteAddress = ctx.channel().remoteAddress().toString();
                            String receiveName = webSocketMessage.getReceiveName();
                            String content = webSocketMessage.getContent();
                            String sendName = channelIdAndsendNameMap.get(ctx.channel().id().asLongText());
                            webSocketMsg = new WebSocketMessage();
                            webSocketMsg.setReceiveName(receiveName);
                            webSocketMsg.setSendName(sendName);
                            webSocketMsg.setContent(content);
                            webSocketMsg.setIpAddress(remoteAddress);

                        }
                        default:
                            break;
                    }
                }
            } else {
                throw new RuntimeException("消息内容为空");
            }


        }
        return webSocketMsg;
    }





    /**
     * @Description: TODO
     * 移除发送者
     * @author zhuangjiesen
     * @date 2017年4月10日 下午4:01:49
     * @param channelId
     */
    public void removeSender(String channelId){
        String sendName = channelIdAndsendNameMap.get(channelId);
        sendNameAndContextMap.remove(sendName);
        channelIdAndsendNameMap.remove(channelId);
        hasOnlineUserReduce.compareAndSet(false, true);
    }




    /**
     * @Description: TODO
     * 发送消息
     * @author zhuangjiesen
     * @date 2017年4月11日 上午11:20:14
     * @param webSocketMsg
     * @return
     *
     */
    public int sendMsg (WebSocketMessage webSocketMsg ){
        int result = 0;
        WebSocketHandlerContext handlerContext = sendNameAndContextMap.get(webSocketMsg.getReceiveName());
        if (handlerContext != null) {
            webSocketMsg.setMessageType(7);
            TextWebSocketFrame tws = new TextWebSocketFrame(webSocketMsg.toJSONString());
            handlerContext.getChannelHandlerContext().channel().writeAndFlush( tws);
            result = 1;
        } else {
            //找不到发送对象

            return 8;
        }
        return result;
    }



    /*
    * 是否被限制注册
    * */
    public boolean isIpRegistLimited (String host ){
        Integer times = ipMap.get(host);
        if (times != null && times.intValue() > (ipRegistLimit - 1)) {
            return true;
        }

        return false;
    }

    /*
    * 是否被限制注册
    * */
    public void registIp (String host ){
        Integer times = ipMap.get(host);
        if (times != null) {
            ipMap.put(host , times.intValue() + 1);
        } else {
            ipMap.put(host , 1);
        }

    }



}


```




