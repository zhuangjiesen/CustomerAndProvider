// class version 46.0 (46)
// access flags 0x1
public class com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5 extends com/java/core/proxy/cglib/RealSubject  implements net/sf/cglib/proxy/Factory  {

  // compiled from: <generated>

  // access flags 0x2
  private Z CGLIB$BOUND

  // access flags 0x1A
  private final static Ljava/lang/ThreadLocal; CGLIB$THREAD_CALLBACKS

  // access flags 0x1A
  private final static [Lnet/sf/cglib/proxy/Callback; CGLIB$STATIC_CALLBACKS

  // access flags 0x2
  private Lnet/sf/cglib/proxy/MethodInterceptor; CGLIB$CALLBACK_0

  // access flags 0x1A
  private final static Ljava/lang/reflect/Method; CGLIB$showSelf$0$Method

  // access flags 0x1A
  private final static Lnet/sf/cglib/proxy/MethodProxy; CGLIB$showSelf$0$Proxy

  // access flags 0x1A
  private final static [Ljava/lang/Object; CGLIB$emptyArgs

  // access flags 0x1A
  private final static Ljava/lang/reflect/Method; CGLIB$show$1$Method

  // access flags 0x1A
  private final static Lnet/sf/cglib/proxy/MethodProxy; CGLIB$show$1$Proxy

  // access flags 0x1A
  private final static Ljava/lang/reflect/Method; CGLIB$finalize$2$Method

  // access flags 0x1A
  private final static Lnet/sf/cglib/proxy/MethodProxy; CGLIB$finalize$2$Proxy

  // access flags 0x1A
  private final static Ljava/lang/reflect/Method; CGLIB$equals$3$Method

  // access flags 0x1A
  private final static Lnet/sf/cglib/proxy/MethodProxy; CGLIB$equals$3$Proxy

  // access flags 0x1A
  private final static Ljava/lang/reflect/Method; CGLIB$toString$4$Method

  // access flags 0x1A
  private final static Lnet/sf/cglib/proxy/MethodProxy; CGLIB$toString$4$Proxy

  // access flags 0x1A
  private final static Ljava/lang/reflect/Method; CGLIB$hashCode$5$Method

  // access flags 0x1A
  private final static Lnet/sf/cglib/proxy/MethodProxy; CGLIB$hashCode$5$Proxy

  // access flags 0x1A
  private final static Ljava/lang/reflect/Method; CGLIB$clone$6$Method

  // access flags 0x1A
  private final static Lnet/sf/cglib/proxy/MethodProxy; CGLIB$clone$6$Proxy

  // access flags 0x8
  static CGLIB$STATICHOOK1()V
    NEW java/lang/ThreadLocal
    DUP
    INVOKESPECIAL java/lang/ThreadLocal.<init> ()V
    PUTSTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$THREAD_CALLBACKS : Ljava/lang/ThreadLocal;
    ICONST_0
    ANEWARRAY java/lang/Object
    PUTSTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$emptyArgs : [Ljava/lang/Object;
    LDC "com.java.core.proxy.cglib.RealSubject$$EnhancerByCGLIB$$be39b9b5"
    INVOKESTATIC java/lang/Class.forName (Ljava/lang/String;)Ljava/lang/Class;
    ASTORE 0
    BIPUSH 10
    ANEWARRAY java/lang/String
    DUP
    ICONST_0
    LDC "finalize"
    AASTORE
    DUP
    ICONST_1
    LDC "()V"
    AASTORE
    DUP
    ICONST_2
    LDC "equals"
    AASTORE
    DUP
    ICONST_3
    LDC "(Ljava/lang/Object;)Z"
    AASTORE
    DUP
    ICONST_4
    LDC "toString"
    AASTORE
    DUP
    ICONST_5
    LDC "()Ljava/lang/String;"
    AASTORE
    DUP
    BIPUSH 6
    LDC "hashCode"
    AASTORE
    DUP
    BIPUSH 7
    LDC "()I"
    AASTORE
    DUP
    BIPUSH 8
    LDC "clone"
    AASTORE
    DUP
    BIPUSH 9
    LDC "()Ljava/lang/Object;"
    AASTORE
    LDC "java.lang.Object"
    INVOKESTATIC java/lang/Class.forName (Ljava/lang/String;)Ljava/lang/Class;
    DUP
    ASTORE 1
    INVOKEVIRTUAL java/lang/Class.getDeclaredMethods ()[Ljava/lang/reflect/Method;
    INVOKESTATIC net/sf/cglib/core/ReflectUtils.findMethods ([Ljava/lang/String;[Ljava/lang/reflect/Method;)[Ljava/lang/reflect/Method;
    DUP
    ICONST_0
    AALOAD
    PUTSTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$finalize$2$Method : Ljava/lang/reflect/Method;
    ALOAD 1
    ALOAD 0
    LDC "()V"
    LDC "finalize"
    LDC "CGLIB$finalize$2"
    INVOKESTATIC net/sf/cglib/proxy/MethodProxy.create (Ljava/lang/Class;Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lnet/sf/cglib/proxy/MethodProxy;
    PUTSTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$finalize$2$Proxy : Lnet/sf/cglib/proxy/MethodProxy;
    DUP
    ICONST_1
    AALOAD
    PUTSTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$equals$3$Method : Ljava/lang/reflect/Method;
    ALOAD 1
    ALOAD 0
    LDC "(Ljava/lang/Object;)Z"
    LDC "equals"
    LDC "CGLIB$equals$3"
    INVOKESTATIC net/sf/cglib/proxy/MethodProxy.create (Ljava/lang/Class;Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lnet/sf/cglib/proxy/MethodProxy;
    PUTSTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$equals$3$Proxy : Lnet/sf/cglib/proxy/MethodProxy;
    DUP
    ICONST_2
    AALOAD
    PUTSTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$toString$4$Method : Ljava/lang/reflect/Method;
    ALOAD 1
    ALOAD 0
    LDC "()Ljava/lang/String;"
    LDC "toString"
    LDC "CGLIB$toString$4"
    INVOKESTATIC net/sf/cglib/proxy/MethodProxy.create (Ljava/lang/Class;Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lnet/sf/cglib/proxy/MethodProxy;
    PUTSTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$toString$4$Proxy : Lnet/sf/cglib/proxy/MethodProxy;
    DUP
    ICONST_3
    AALOAD
    PUTSTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$hashCode$5$Method : Ljava/lang/reflect/Method;
    ALOAD 1
    ALOAD 0
    LDC "()I"
    LDC "hashCode"
    LDC "CGLIB$hashCode$5"
    INVOKESTATIC net/sf/cglib/proxy/MethodProxy.create (Ljava/lang/Class;Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lnet/sf/cglib/proxy/MethodProxy;
    PUTSTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$hashCode$5$Proxy : Lnet/sf/cglib/proxy/MethodProxy;
    DUP
    ICONST_4
    AALOAD
    PUTSTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$clone$6$Method : Ljava/lang/reflect/Method;
    ALOAD 1
    ALOAD 0
    LDC "()Ljava/lang/Object;"
    LDC "clone"
    LDC "CGLIB$clone$6"
    INVOKESTATIC net/sf/cglib/proxy/MethodProxy.create (Ljava/lang/Class;Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lnet/sf/cglib/proxy/MethodProxy;
    PUTSTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$clone$6$Proxy : Lnet/sf/cglib/proxy/MethodProxy;
    POP
    ICONST_4
    ANEWARRAY java/lang/String
    DUP
    ICONST_0
    LDC "showSelf"
    AASTORE
    DUP
    ICONST_1
    LDC "()V"
    AASTORE
    DUP
    ICONST_2
    LDC "show"
    AASTORE
    DUP
    ICONST_3
    LDC "()V"
    AASTORE
    LDC "com.java.core.proxy.cglib.RealSubject"
    INVOKESTATIC java/lang/Class.forName (Ljava/lang/String;)Ljava/lang/Class;
    DUP
    ASTORE 1
    INVOKEVIRTUAL java/lang/Class.getDeclaredMethods ()[Ljava/lang/reflect/Method;
    INVOKESTATIC net/sf/cglib/core/ReflectUtils.findMethods ([Ljava/lang/String;[Ljava/lang/reflect/Method;)[Ljava/lang/reflect/Method;
    DUP
    ICONST_0
    AALOAD
    PUTSTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$showSelf$0$Method : Ljava/lang/reflect/Method;
    ALOAD 1
    ALOAD 0
    LDC "()V"
    LDC "showSelf"
    LDC "CGLIB$showSelf$0"
    INVOKESTATIC net/sf/cglib/proxy/MethodProxy.create (Ljava/lang/Class;Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lnet/sf/cglib/proxy/MethodProxy;
    PUTSTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$showSelf$0$Proxy : Lnet/sf/cglib/proxy/MethodProxy;
    DUP
    ICONST_1
    AALOAD
    PUTSTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$show$1$Method : Ljava/lang/reflect/Method;
    ALOAD 1
    ALOAD 0
    LDC "()V"
    LDC "show"
    LDC "CGLIB$show$1"
    INVOKESTATIC net/sf/cglib/proxy/MethodProxy.create (Ljava/lang/Class;Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lnet/sf/cglib/proxy/MethodProxy;
    PUTSTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$show$1$Proxy : Lnet/sf/cglib/proxy/MethodProxy;
    POP
    RETURN
    RETURN
    MAXSTACK = 6
    MAXLOCALS = 2

  // access flags 0x10
  final CGLIB$showSelf$0()V
    ALOAD 0
    INVOKESPECIAL com/java/core/proxy/cglib/RealSubject.showSelf ()V
    RETURN
    MAXSTACK = 1
    MAXLOCALS = 1

  // access flags 0x11
  public final showSelf()V
    ALOAD 0
    GETFIELD com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$CALLBACK_0 : Lnet/sf/cglib/proxy/MethodInterceptor;
    DUP
    IFNONNULL L0
    POP
    ALOAD 0
    INVOKESTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$BIND_CALLBACKS (Ljava/lang/Object;)V
    ALOAD 0
    GETFIELD com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$CALLBACK_0 : Lnet/sf/cglib/proxy/MethodInterceptor;
   L0
    DUP
    IFNULL L1
    ALOAD 0
    GETSTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$showSelf$0$Method : Ljava/lang/reflect/Method;
    GETSTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$emptyArgs : [Ljava/lang/Object;
    GETSTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$showSelf$0$Proxy : Lnet/sf/cglib/proxy/MethodProxy;
    INVOKEINTERFACE net/sf/cglib/proxy/MethodInterceptor.intercept (Ljava/lang/Object;Ljava/lang/reflect/Method;[Ljava/lang/Object;Lnet/sf/cglib/proxy/MethodProxy;)Ljava/lang/Object;
    RETURN
   L1
    ALOAD 0
    INVOKESPECIAL com/java/core/proxy/cglib/RealSubject.showSelf ()V
    RETURN
    MAXSTACK = 5
    MAXLOCALS = 1

  // access flags 0x10
  final CGLIB$show$1()V
    ALOAD 0
    INVOKESPECIAL com/java/core/proxy/cglib/RealSubject.show ()V
    RETURN
    MAXSTACK = 1
    MAXLOCALS = 1

  // access flags 0x11
  public final show()V
    ALOAD 0
    GETFIELD com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$CALLBACK_0 : Lnet/sf/cglib/proxy/MethodInterceptor;
    DUP
    IFNONNULL L0
    POP
    ALOAD 0
    INVOKESTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$BIND_CALLBACKS (Ljava/lang/Object;)V
    ALOAD 0
    GETFIELD com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$CALLBACK_0 : Lnet/sf/cglib/proxy/MethodInterceptor;
   L0
    DUP
    IFNULL L1
    ALOAD 0
    GETSTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$show$1$Method : Ljava/lang/reflect/Method;
    GETSTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$emptyArgs : [Ljava/lang/Object;
    GETSTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$show$1$Proxy : Lnet/sf/cglib/proxy/MethodProxy;
    INVOKEINTERFACE net/sf/cglib/proxy/MethodInterceptor.intercept (Ljava/lang/Object;Ljava/lang/reflect/Method;[Ljava/lang/Object;Lnet/sf/cglib/proxy/MethodProxy;)Ljava/lang/Object;
    RETURN
   L1
    ALOAD 0
    INVOKESPECIAL com/java/core/proxy/cglib/RealSubject.show ()V
    RETURN
    MAXSTACK = 5
    MAXLOCALS = 1

  // access flags 0x10
  final CGLIB$finalize$2()V throws java/lang/Throwable 
    ALOAD 0
    INVOKESPECIAL com/java/core/proxy/cglib/RealSubject.finalize ()V
    RETURN
    MAXSTACK = 1
    MAXLOCALS = 1

  // access flags 0x14
  protected final finalize()V throws java/lang/Throwable 
    ALOAD 0
    GETFIELD com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$CALLBACK_0 : Lnet/sf/cglib/proxy/MethodInterceptor;
    DUP
    IFNONNULL L0
    POP
    ALOAD 0
    INVOKESTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$BIND_CALLBACKS (Ljava/lang/Object;)V
    ALOAD 0
    GETFIELD com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$CALLBACK_0 : Lnet/sf/cglib/proxy/MethodInterceptor;
   L0
    DUP
    IFNULL L1
    ALOAD 0
    GETSTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$finalize$2$Method : Ljava/lang/reflect/Method;
    GETSTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$emptyArgs : [Ljava/lang/Object;
    GETSTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$finalize$2$Proxy : Lnet/sf/cglib/proxy/MethodProxy;
    INVOKEINTERFACE net/sf/cglib/proxy/MethodInterceptor.intercept (Ljava/lang/Object;Ljava/lang/reflect/Method;[Ljava/lang/Object;Lnet/sf/cglib/proxy/MethodProxy;)Ljava/lang/Object;
    RETURN
   L1
    ALOAD 0
    INVOKESPECIAL com/java/core/proxy/cglib/RealSubject.finalize ()V
    RETURN
    MAXSTACK = 5
    MAXLOCALS = 1

  // access flags 0x10
  final CGLIB$equals$3(Ljava/lang/Object;)Z
    ALOAD 0
    ALOAD 1
    INVOKESPECIAL com/java/core/proxy/cglib/RealSubject.equals (Ljava/lang/Object;)Z
    IRETURN
    MAXSTACK = 2
    MAXLOCALS = 2

  // access flags 0x11
  public final equals(Ljava/lang/Object;)Z
    ALOAD 0
    GETFIELD com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$CALLBACK_0 : Lnet/sf/cglib/proxy/MethodInterceptor;
    DUP
    IFNONNULL L0
    POP
    ALOAD 0
    INVOKESTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$BIND_CALLBACKS (Ljava/lang/Object;)V
    ALOAD 0
    GETFIELD com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$CALLBACK_0 : Lnet/sf/cglib/proxy/MethodInterceptor;
   L0
    DUP
    IFNULL L1
    ALOAD 0
    GETSTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$equals$3$Method : Ljava/lang/reflect/Method;
    ICONST_1
    ANEWARRAY java/lang/Object
    DUP
    ICONST_0
    ALOAD 1
    AASTORE
    GETSTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$equals$3$Proxy : Lnet/sf/cglib/proxy/MethodProxy;
    INVOKEINTERFACE net/sf/cglib/proxy/MethodInterceptor.intercept (Ljava/lang/Object;Ljava/lang/reflect/Method;[Ljava/lang/Object;Lnet/sf/cglib/proxy/MethodProxy;)Ljava/lang/Object;
    DUP
    IFNONNULL L2
    POP
    ICONST_0
    GOTO L3
   L2
    CHECKCAST java/lang/Boolean
    INVOKEVIRTUAL java/lang/Boolean.booleanValue ()Z
   L3
    IRETURN
   L1
    ALOAD 0
    ALOAD 1
    INVOKESPECIAL com/java/core/proxy/cglib/RealSubject.equals (Ljava/lang/Object;)Z
    IRETURN
    MAXSTACK = 7
    MAXLOCALS = 2

  // access flags 0x10
  final CGLIB$toString$4()Ljava/lang/String;
    ALOAD 0
    INVOKESPECIAL com/java/core/proxy/cglib/RealSubject.toString ()Ljava/lang/String;
    ARETURN
    MAXSTACK = 1
    MAXLOCALS = 1

  // access flags 0x11
  public final toString()Ljava/lang/String;
    ALOAD 0
    GETFIELD com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$CALLBACK_0 : Lnet/sf/cglib/proxy/MethodInterceptor;
    DUP
    IFNONNULL L0
    POP
    ALOAD 0
    INVOKESTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$BIND_CALLBACKS (Ljava/lang/Object;)V
    ALOAD 0
    GETFIELD com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$CALLBACK_0 : Lnet/sf/cglib/proxy/MethodInterceptor;
   L0
    DUP
    IFNULL L1
    ALOAD 0
    GETSTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$toString$4$Method : Ljava/lang/reflect/Method;
    GETSTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$emptyArgs : [Ljava/lang/Object;
    GETSTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$toString$4$Proxy : Lnet/sf/cglib/proxy/MethodProxy;
    INVOKEINTERFACE net/sf/cglib/proxy/MethodInterceptor.intercept (Ljava/lang/Object;Ljava/lang/reflect/Method;[Ljava/lang/Object;Lnet/sf/cglib/proxy/MethodProxy;)Ljava/lang/Object;
    CHECKCAST java/lang/String
    ARETURN
   L1
    ALOAD 0
    INVOKESPECIAL com/java/core/proxy/cglib/RealSubject.toString ()Ljava/lang/String;
    ARETURN
    MAXSTACK = 5
    MAXLOCALS = 1

  // access flags 0x10
  final CGLIB$hashCode$5()I
    ALOAD 0
    INVOKESPECIAL com/java/core/proxy/cglib/RealSubject.hashCode ()I
    IRETURN
    MAXSTACK = 1
    MAXLOCALS = 1

  // access flags 0x11
  public final hashCode()I
    ALOAD 0
    GETFIELD com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$CALLBACK_0 : Lnet/sf/cglib/proxy/MethodInterceptor;
    DUP
    IFNONNULL L0
    POP
    ALOAD 0
    INVOKESTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$BIND_CALLBACKS (Ljava/lang/Object;)V
    ALOAD 0
    GETFIELD com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$CALLBACK_0 : Lnet/sf/cglib/proxy/MethodInterceptor;
   L0
    DUP
    IFNULL L1
    ALOAD 0
    GETSTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$hashCode$5$Method : Ljava/lang/reflect/Method;
    GETSTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$emptyArgs : [Ljava/lang/Object;
    GETSTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$hashCode$5$Proxy : Lnet/sf/cglib/proxy/MethodProxy;
    INVOKEINTERFACE net/sf/cglib/proxy/MethodInterceptor.intercept (Ljava/lang/Object;Ljava/lang/reflect/Method;[Ljava/lang/Object;Lnet/sf/cglib/proxy/MethodProxy;)Ljava/lang/Object;
    DUP
    IFNONNULL L2
    POP
    ICONST_0
    GOTO L3
   L2
    CHECKCAST java/lang/Number
    INVOKEVIRTUAL java/lang/Number.intValue ()I
   L3
    IRETURN
   L1
    ALOAD 0
    INVOKESPECIAL com/java/core/proxy/cglib/RealSubject.hashCode ()I
    IRETURN
    MAXSTACK = 5
    MAXLOCALS = 1

  // access flags 0x10
  final CGLIB$clone$6()Ljava/lang/Object; throws java/lang/CloneNotSupportedException 
    ALOAD 0
    INVOKESPECIAL com/java/core/proxy/cglib/RealSubject.clone ()Ljava/lang/Object;
    ARETURN
    MAXSTACK = 1
    MAXLOCALS = 1

  // access flags 0x14
  protected final clone()Ljava/lang/Object; throws java/lang/CloneNotSupportedException 
    ALOAD 0
    GETFIELD com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$CALLBACK_0 : Lnet/sf/cglib/proxy/MethodInterceptor;
    DUP
    IFNONNULL L0
    POP
    ALOAD 0
    INVOKESTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$BIND_CALLBACKS (Ljava/lang/Object;)V
    ALOAD 0
    GETFIELD com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$CALLBACK_0 : Lnet/sf/cglib/proxy/MethodInterceptor;
   L0
    DUP
    IFNULL L1
    ALOAD 0
    GETSTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$clone$6$Method : Ljava/lang/reflect/Method;
    GETSTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$emptyArgs : [Ljava/lang/Object;
    GETSTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$clone$6$Proxy : Lnet/sf/cglib/proxy/MethodProxy;
    INVOKEINTERFACE net/sf/cglib/proxy/MethodInterceptor.intercept (Ljava/lang/Object;Ljava/lang/reflect/Method;[Ljava/lang/Object;Lnet/sf/cglib/proxy/MethodProxy;)Ljava/lang/Object;
    ARETURN
   L1
    ALOAD 0
    INVOKESPECIAL com/java/core/proxy/cglib/RealSubject.clone ()Ljava/lang/Object;
    ARETURN
    MAXSTACK = 5
    MAXLOCALS = 1

  // access flags 0x9
  public static CGLIB$findMethodProxy(Lnet/sf/cglib/core/Signature;)Lnet/sf/cglib/proxy/MethodProxy;
    ALOAD 0
    INVOKEVIRTUAL java/lang/Object.toString ()Ljava/lang/String;
    DUP
    INVOKEVIRTUAL java/lang/Object.hashCode ()I
    LOOKUPSWITCH
      -1928737044: L0
      -1574182249: L1
      -508378822: L2
      1826985398: L3
      1913648695: L4
      1984935277: L5
      2067235672: L6
      default: L7
   L0
    LDC "showSelf()V"
    INVOKEVIRTUAL java/lang/Object.equals (Ljava/lang/Object;)Z
    IFEQ L8
    GETSTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$showSelf$0$Proxy : Lnet/sf/cglib/proxy/MethodProxy;
    ARETURN
   L1
    LDC "finalize()V"
    INVOKEVIRTUAL java/lang/Object.equals (Ljava/lang/Object;)Z
    IFEQ L8
    GETSTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$finalize$2$Proxy : Lnet/sf/cglib/proxy/MethodProxy;
    ARETURN
   L2
    LDC "clone()Ljava/lang/Object;"
    INVOKEVIRTUAL java/lang/Object.equals (Ljava/lang/Object;)Z
    IFEQ L8
    GETSTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$clone$6$Proxy : Lnet/sf/cglib/proxy/MethodProxy;
    ARETURN
   L3
    LDC "equals(Ljava/lang/Object;)Z"
    INVOKEVIRTUAL java/lang/Object.equals (Ljava/lang/Object;)Z
    IFEQ L8
    GETSTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$equals$3$Proxy : Lnet/sf/cglib/proxy/MethodProxy;
    ARETURN
   L4
    LDC "toString()Ljava/lang/String;"
    INVOKEVIRTUAL java/lang/Object.equals (Ljava/lang/Object;)Z
    IFEQ L8
    GETSTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$toString$4$Proxy : Lnet/sf/cglib/proxy/MethodProxy;
    ARETURN
   L5
    LDC "hashCode()I"
    INVOKEVIRTUAL java/lang/Object.equals (Ljava/lang/Object;)Z
    IFEQ L8
    GETSTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$hashCode$5$Proxy : Lnet/sf/cglib/proxy/MethodProxy;
    ARETURN
   L6
    LDC "show()V"
    INVOKEVIRTUAL java/lang/Object.equals (Ljava/lang/Object;)Z
    IFEQ L8
    GETSTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$show$1$Proxy : Lnet/sf/cglib/proxy/MethodProxy;
    ARETURN
   L7
    POP
   L8
    ACONST_NULL
    ARETURN
    MAXSTACK = 2
    MAXLOCALS = 1

  // access flags 0x1
  public <init>()V
    ALOAD 0
    DUP
    INVOKESPECIAL com/java/core/proxy/cglib/RealSubject.<init> ()V
    INVOKESTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$BIND_CALLBACKS (Ljava/lang/Object;)V
    RETURN
    MAXSTACK = 2
    MAXLOCALS = 1

  // access flags 0x9
  public static CGLIB$SET_THREAD_CALLBACKS([Lnet/sf/cglib/proxy/Callback;)V
    GETSTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$THREAD_CALLBACKS : Ljava/lang/ThreadLocal;
    ALOAD 0
    INVOKEVIRTUAL java/lang/ThreadLocal.set (Ljava/lang/Object;)V
    RETURN
    MAXSTACK = 2
    MAXLOCALS = 1

  // access flags 0x9
  public static CGLIB$SET_STATIC_CALLBACKS([Lnet/sf/cglib/proxy/Callback;)V
    ALOAD 0
    PUTSTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$STATIC_CALLBACKS : [Lnet/sf/cglib/proxy/Callback;
    RETURN
    MAXSTACK = 1
    MAXLOCALS = 1

  // access flags 0x1A
  private final static CGLIB$BIND_CALLBACKS(Ljava/lang/Object;)V
    ALOAD 0
    CHECKCAST com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5
    ASTORE 1
    ALOAD 1
    GETFIELD com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$BOUND : Z
    IFNE L0
    ALOAD 1
    ICONST_1
    PUTFIELD com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$BOUND : Z
    GETSTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$THREAD_CALLBACKS : Ljava/lang/ThreadLocal;
    INVOKEVIRTUAL java/lang/ThreadLocal.get ()Ljava/lang/Object;
    DUP
    IFNONNULL L1
    POP
    GETSTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$STATIC_CALLBACKS : [Lnet/sf/cglib/proxy/Callback;
    DUP
    IFNONNULL L1
    POP
    GOTO L0
   L1
    CHECKCAST [Lnet/sf/cglib/proxy/Callback;
    ALOAD 1
    SWAP
    ICONST_0
    AALOAD
    CHECKCAST net/sf/cglib/proxy/MethodInterceptor
    PUTFIELD com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$CALLBACK_0 : Lnet/sf/cglib/proxy/MethodInterceptor;
   L0
    RETURN
    MAXSTACK = 3
    MAXLOCALS = 2

  // access flags 0x1
  public newInstance([Lnet/sf/cglib/proxy/Callback;)Ljava/lang/Object;
    ALOAD 1
    INVOKESTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$SET_THREAD_CALLBACKS ([Lnet/sf/cglib/proxy/Callback;)V
    NEW com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5
    DUP
    INVOKESPECIAL com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.<init> ()V
    ACONST_NULL
    INVOKESTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$SET_THREAD_CALLBACKS ([Lnet/sf/cglib/proxy/Callback;)V
    ARETURN
    MAXSTACK = 2
    MAXLOCALS = 2

  // access flags 0x1
  public newInstance(Lnet/sf/cglib/proxy/Callback;)Ljava/lang/Object;
    ICONST_1
    ANEWARRAY net/sf/cglib/proxy/Callback
    DUP
    ICONST_0
    ALOAD 1
    AASTORE
    INVOKESTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$SET_THREAD_CALLBACKS ([Lnet/sf/cglib/proxy/Callback;)V
    NEW com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5
    DUP
    INVOKESPECIAL com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.<init> ()V
    ACONST_NULL
    INVOKESTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$SET_THREAD_CALLBACKS ([Lnet/sf/cglib/proxy/Callback;)V
    ARETURN
    MAXSTACK = 4
    MAXLOCALS = 2

  // access flags 0x1
  public newInstance([Ljava/lang/Class;[Ljava/lang/Object;[Lnet/sf/cglib/proxy/Callback;)Ljava/lang/Object;
    ALOAD 3
    INVOKESTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$SET_THREAD_CALLBACKS ([Lnet/sf/cglib/proxy/Callback;)V
    NEW com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5
    DUP
    ALOAD 1
    DUP
    ARRAYLENGTH
    TABLESWITCH
      0: L0
      default: L1
   L0
    POP
    INVOKESPECIAL com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.<init> ()V
    GOTO L2
   L1
    GOTO L3
   L3
    POP
    NEW java/lang/IllegalArgumentException
    DUP
    LDC "Constructor not found"
    INVOKESPECIAL java/lang/IllegalArgumentException.<init> (Ljava/lang/String;)V
    ATHROW
   L2
    ACONST_NULL
    INVOKESTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$SET_THREAD_CALLBACKS ([Lnet/sf/cglib/proxy/Callback;)V
    ARETURN
    MAXSTACK = 5
    MAXLOCALS = 4

  // access flags 0x1
  public getCallback(I)Lnet/sf/cglib/proxy/Callback;
    ALOAD 0
    INVOKESTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$BIND_CALLBACKS (Ljava/lang/Object;)V
    ALOAD 0
    ILOAD 1
    TABLESWITCH
      0: L0
      default: L1
   L0
    GETFIELD com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$CALLBACK_0 : Lnet/sf/cglib/proxy/MethodInterceptor;
    GOTO L2
   L1
    POP
    ACONST_NULL
   L2
    ARETURN
    MAXSTACK = 2
    MAXLOCALS = 2

  // access flags 0x1
  public setCallback(ILnet/sf/cglib/proxy/Callback;)V
    ILOAD 1
    TABLESWITCH
      0: L0
      default: L1
   L0
    ALOAD 0
    ALOAD 2
    CHECKCAST net/sf/cglib/proxy/MethodInterceptor
    PUTFIELD com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$CALLBACK_0 : Lnet/sf/cglib/proxy/MethodInterceptor;
    GOTO L1
   L1
    RETURN
    MAXSTACK = 2
    MAXLOCALS = 3

  // access flags 0x1
  public getCallbacks()[Lnet/sf/cglib/proxy/Callback;
    ALOAD 0
    INVOKESTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$BIND_CALLBACKS (Ljava/lang/Object;)V
    ALOAD 0
    ICONST_1
    ANEWARRAY net/sf/cglib/proxy/Callback
    DUP
    ICONST_0
    ALOAD 0
    GETFIELD com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$CALLBACK_0 : Lnet/sf/cglib/proxy/MethodInterceptor;
    AASTORE
    ARETURN
    MAXSTACK = 5
    MAXLOCALS = 1

  // access flags 0x1
  public setCallbacks([Lnet/sf/cglib/proxy/Callback;)V
    ALOAD 0
    ALOAD 1
    DUP2
    ICONST_0
    AALOAD
    CHECKCAST net/sf/cglib/proxy/MethodInterceptor
    PUTFIELD com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$CALLBACK_0 : Lnet/sf/cglib/proxy/MethodInterceptor;
    RETURN
    MAXSTACK = 5
    MAXLOCALS = 2

  // access flags 0x8
  static <clinit>()V
    INVOKESTATIC com/java/core/proxy/cglib/RealSubject$$EnhancerByCGLIB$$be39b9b5.CGLIB$STATICHOOK1 ()V
    RETURN
    MAXSTACK = 0
    MAXLOCALS = 0
}
