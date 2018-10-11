// class version 46.0 (46)
// access flags 0x1
public class com/java/core/proxy/cglib/RealSubject$$FastClassByCGLIB$$314f5111 extends net/sf/cglib/reflect/FastClass  {

  // compiled from: <generated>

  // access flags 0x1
  public <init>(Ljava/lang/Class;)V
    ALOAD 0
    ALOAD 1
    INVOKESPECIAL net/sf/cglib/reflect/FastClass.<init> (Ljava/lang/Class;)V
    RETURN
    MAXSTACK = 2
    MAXLOCALS = 2

  // access flags 0x1
  public getIndex(Lnet/sf/cglib/core/Signature;)I
    ALOAD 1
    INVOKEVIRTUAL java/lang/Object.toString ()Ljava/lang/String;
    DUP
    INVOKEVIRTUAL java/lang/Object.hashCode ()I
    LOOKUPSWITCH
      -1928737044: L0
      -1725733088: L1
      -1026001249: L2
      243996900: L3
      946854621: L4
      1116248544: L5
      1826985398: L6
      1902039948: L7
      1913648695: L8
      1984935277: L9
      2067235672: L10
      default: L11
   L0
    LDC "showSelf()V"
    INVOKEVIRTUAL java/lang/Object.equals (Ljava/lang/Object;)Z
    IFEQ L12
    ICONST_0
    IRETURN
   L1
    LDC "getClass()Ljava/lang/Class;"
    INVOKEVIRTUAL java/lang/Object.equals (Ljava/lang/Object;)Z
    IFEQ L12
    BIPUSH 8
    IRETURN
   L2
    LDC "wait(JI)V"
    INVOKEVIRTUAL java/lang/Object.equals (Ljava/lang/Object;)Z
    IFEQ L12
    ICONST_2
    IRETURN
   L3
    LDC "wait(J)V"
    INVOKEVIRTUAL java/lang/Object.equals (Ljava/lang/Object;)Z
    IFEQ L12
    ICONST_3
    IRETURN
   L4
    LDC "notifyAll()V"
    INVOKEVIRTUAL java/lang/Object.equals (Ljava/lang/Object;)Z
    IFEQ L12
    BIPUSH 10
    IRETURN
   L5
    LDC "wait()V"
    INVOKEVIRTUAL java/lang/Object.equals (Ljava/lang/Object;)Z
    IFEQ L12
    ICONST_4
    IRETURN
   L6
    LDC "equals(Ljava/lang/Object;)Z"
    INVOKEVIRTUAL java/lang/Object.equals (Ljava/lang/Object;)Z
    IFEQ L12
    ICONST_5
    IRETURN
   L7
    LDC "notify()V"
    INVOKEVIRTUAL java/lang/Object.equals (Ljava/lang/Object;)Z
    IFEQ L12
    BIPUSH 9
    IRETURN
   L8
    LDC "toString()Ljava/lang/String;"
    INVOKEVIRTUAL java/lang/Object.equals (Ljava/lang/Object;)Z
    IFEQ L12
    BIPUSH 6
    IRETURN
   L9
    LDC "hashCode()I"
    INVOKEVIRTUAL java/lang/Object.equals (Ljava/lang/Object;)Z
    IFEQ L12
    BIPUSH 7
    IRETURN
   L10
    LDC "show()V"
    INVOKEVIRTUAL java/lang/Object.equals (Ljava/lang/Object;)Z
    IFEQ L12
    ICONST_1
    IRETURN
   L11
    POP
   L12
    ICONST_M1
    IRETURN
    MAXSTACK = 2
    MAXLOCALS = 2

  // access flags 0x1
  public getIndex(Ljava/lang/String;[Ljava/lang/Class;)I
    ALOAD 1
    ALOAD 2
    SWAP
    DUP
    INVOKEVIRTUAL java/lang/Object.hashCode ()I
    LOOKUPSWITCH
      -1776922004: L0
      -1295482945: L1
      -1039689911: L2
      -338864151: L3
      3529469: L4
      3641717: L5
      147696667: L6
      1902066072: L7
      1950568386: L8
      default: L9
   L0
    LDC "toString"
    INVOKEVIRTUAL java/lang/Object.equals (Ljava/lang/Object;)Z
    IFEQ L10
    DUP
    ARRAYLENGTH
    TABLESWITCH
      0: L11
      default: L12
   L11
    POP
    BIPUSH 6
    IRETURN
   L12
    GOTO L13
   L1
    LDC "equals"
    INVOKEVIRTUAL java/lang/Object.equals (Ljava/lang/Object;)Z
    IFEQ L10
    DUP
    ARRAYLENGTH
    TABLESWITCH
      1: L14
      default: L15
   L14
    DUP
    ICONST_0
    AALOAD
    INVOKEVIRTUAL java/lang/Class.getName ()Ljava/lang/String;
    LDC "java.lang.Object"
    INVOKEVIRTUAL java/lang/Object.equals (Ljava/lang/Object;)Z
    IFEQ L13
    POP
    ICONST_5
    IRETURN
   L15
    GOTO L13
   L2
    LDC "notify"
    INVOKEVIRTUAL java/lang/Object.equals (Ljava/lang/Object;)Z
    IFEQ L10
    DUP
    ARRAYLENGTH
    TABLESWITCH
      0: L16
      default: L17
   L16
    POP
    BIPUSH 9
    IRETURN
   L17
    GOTO L13
   L3
    LDC "showSelf"
    INVOKEVIRTUAL java/lang/Object.equals (Ljava/lang/Object;)Z
    IFEQ L10
    DUP
    ARRAYLENGTH
    TABLESWITCH
      0: L18
      default: L19
   L18
    POP
    ICONST_0
    IRETURN
   L19
    GOTO L13
   L4
    LDC "show"
    INVOKEVIRTUAL java/lang/Object.equals (Ljava/lang/Object;)Z
    IFEQ L10
    DUP
    ARRAYLENGTH
    TABLESWITCH
      0: L20
      default: L21
   L20
    POP
    ICONST_1
    IRETURN
   L21
    GOTO L13
   L5
    LDC "wait"
    INVOKEVIRTUAL java/lang/Object.equals (Ljava/lang/Object;)Z
    IFEQ L10
    DUP
    ARRAYLENGTH
    TABLESWITCH
      0: L22
      1: L23
      2: L24
      default: L25
   L22
    POP
    ICONST_4
    IRETURN
   L23
    DUP
    ICONST_0
    AALOAD
    INVOKEVIRTUAL java/lang/Class.getName ()Ljava/lang/String;
    LDC "long"
    INVOKEVIRTUAL java/lang/Object.equals (Ljava/lang/Object;)Z
    IFEQ L13
    POP
    ICONST_3
    IRETURN
   L24
    DUP
    ICONST_0
    AALOAD
    INVOKEVIRTUAL java/lang/Class.getName ()Ljava/lang/String;
    LDC "long"
    INVOKEVIRTUAL java/lang/Object.equals (Ljava/lang/Object;)Z
    IFEQ L13
    DUP
    ICONST_1
    AALOAD
    INVOKEVIRTUAL java/lang/Class.getName ()Ljava/lang/String;
    LDC "int"
    INVOKEVIRTUAL java/lang/Object.equals (Ljava/lang/Object;)Z
    IFEQ L13
    POP
    ICONST_2
    IRETURN
   L25
    GOTO L13
   L6
    LDC "hashCode"
    INVOKEVIRTUAL java/lang/Object.equals (Ljava/lang/Object;)Z
    IFEQ L10
    DUP
    ARRAYLENGTH
    TABLESWITCH
      0: L26
      default: L27
   L26
    POP
    BIPUSH 7
    IRETURN
   L27
    GOTO L13
   L7
    LDC "notifyAll"
    INVOKEVIRTUAL java/lang/Object.equals (Ljava/lang/Object;)Z
    IFEQ L10
    DUP
    ARRAYLENGTH
    TABLESWITCH
      0: L28
      default: L29
   L28
    POP
    BIPUSH 10
    IRETURN
   L29
    GOTO L13
   L8
    LDC "getClass"
    INVOKEVIRTUAL java/lang/Object.equals (Ljava/lang/Object;)Z
    IFEQ L10
    DUP
    ARRAYLENGTH
    TABLESWITCH
      0: L30
      default: L31
   L30
    POP
    BIPUSH 8
    IRETURN
   L31
    GOTO L13
   L9
    POP
   L10
    GOTO L13
   L13
    POP
    ICONST_M1
    IRETURN
    MAXSTACK = 3
    MAXLOCALS = 3

  // access flags 0x1
  public getIndex([Ljava/lang/Class;)I
    ALOAD 1
    DUP
    ARRAYLENGTH
    TABLESWITCH
      0: L0
      default: L1
   L0
    POP
    ICONST_0
    IRETURN
   L1
    GOTO L2
   L2
    POP
    ICONST_M1
    IRETURN
    MAXSTACK = 2
    MAXLOCALS = 2

  // access flags 0x1
  public invoke(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object; throws java/lang/reflect/InvocationTargetException 
    TRYCATCHBLOCK L0 L1 L1 java/lang/Throwable
    ALOAD 2
    CHECKCAST com/java/core/proxy/cglib/RealSubject
    ILOAD 1
   L0
    TABLESWITCH
      0: L2
      1: L3
      2: L4
      3: L5
      4: L6
      5: L7
      6: L8
      7: L9
      8: L10
      9: L11
      10: L12
      default: L13
   L2
    INVOKEVIRTUAL com/java/core/proxy/cglib/RealSubject.showSelf ()V
    ACONST_NULL
    ARETURN
   L3
    INVOKEVIRTUAL com/java/core/proxy/cglib/RealSubject.show ()V
    ACONST_NULL
    ARETURN
   L4
    ALOAD 3
    ICONST_0
    AALOAD
    CHECKCAST java/lang/Number
    INVOKEVIRTUAL java/lang/Number.longValue ()J
    ALOAD 3
    ICONST_1
    AALOAD
    CHECKCAST java/lang/Number
    INVOKEVIRTUAL java/lang/Number.intValue ()I
    INVOKEVIRTUAL com/java/core/proxy/cglib/RealSubject.wait (JI)V
    ACONST_NULL
    ARETURN
   L5
    ALOAD 3
    ICONST_0
    AALOAD
    CHECKCAST java/lang/Number
    INVOKEVIRTUAL java/lang/Number.longValue ()J
    INVOKEVIRTUAL com/java/core/proxy/cglib/RealSubject.wait (J)V
    ACONST_NULL
    ARETURN
   L6
    INVOKEVIRTUAL com/java/core/proxy/cglib/RealSubject.wait ()V
    ACONST_NULL
    ARETURN
   L7
    ALOAD 3
    ICONST_0
    AALOAD
    INVOKEVIRTUAL com/java/core/proxy/cglib/RealSubject.equals (Ljava/lang/Object;)Z
    NEW java/lang/Boolean
    DUP_X1
    SWAP
    INVOKESPECIAL java/lang/Boolean.<init> (Z)V
    ARETURN
   L8
    INVOKEVIRTUAL com/java/core/proxy/cglib/RealSubject.toString ()Ljava/lang/String;
    ARETURN
   L9
    INVOKEVIRTUAL com/java/core/proxy/cglib/RealSubject.hashCode ()I
    NEW java/lang/Integer
    DUP_X1
    SWAP
    INVOKESPECIAL java/lang/Integer.<init> (I)V
    ARETURN
   L10
    INVOKEVIRTUAL com/java/core/proxy/cglib/RealSubject.getClass ()Ljava/lang/Class;
    ARETURN
   L11
    INVOKEVIRTUAL com/java/core/proxy/cglib/RealSubject.notify ()V
    ACONST_NULL
    ARETURN
   L12
    INVOKEVIRTUAL com/java/core/proxy/cglib/RealSubject.notifyAll ()V
    ACONST_NULL
    ARETURN
   L13
    GOTO L14
   L1
    NEW java/lang/reflect/InvocationTargetException
    DUP_X1
    SWAP
    INVOKESPECIAL java/lang/reflect/InvocationTargetException.<init> (Ljava/lang/Throwable;)V
    ATHROW
   L14
    NEW java/lang/IllegalArgumentException
    DUP
    LDC "Cannot find matching method/constructor"
    INVOKESPECIAL java/lang/IllegalArgumentException.<init> (Ljava/lang/String;)V
    ATHROW
    MAXSTACK = 5
    MAXLOCALS = 4

  // access flags 0x1
  public newInstance(I[Ljava/lang/Object;)Ljava/lang/Object; throws java/lang/reflect/InvocationTargetException 
    TRYCATCHBLOCK L0 L1 L1 java/lang/Throwable
    NEW com/java/core/proxy/cglib/RealSubject
    DUP
    ILOAD 1
   L0
    TABLESWITCH
      0: L2
      default: L3
   L2
    INVOKESPECIAL com/java/core/proxy/cglib/RealSubject.<init> ()V
    ARETURN
   L3
    GOTO L4
   L1
    NEW java/lang/reflect/InvocationTargetException
    DUP_X1
    SWAP
    INVOKESPECIAL java/lang/reflect/InvocationTargetException.<init> (Ljava/lang/Throwable;)V
    ATHROW
   L4
    NEW java/lang/IllegalArgumentException
    DUP
    LDC "Cannot find matching method/constructor"
    INVOKESPECIAL java/lang/IllegalArgumentException.<init> (Ljava/lang/String;)V
    ATHROW
    MAXSTACK = 5
    MAXLOCALS = 3

  // access flags 0x1
  public getMaxIndex()I
    BIPUSH 10
    IRETURN
    MAXSTACK = 1
    MAXLOCALS = 1
}
