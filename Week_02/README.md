# 1、GCLogAnalysis 测试

测试代码：
```
public class GCLogAnalysis {
	private static Random random = new Random();
	public static void main(String[] args) {
		// 当前毫秒时间戳
		long startMillis = System.currentTimeMillis();
		// 持续运行毫秒数; 可根据需要进行修改
		long timeoutMillis = TimeUnit.SECONDS.toMillis(1);
		// 结束时间戳
		long endMillis = startMillis + timeoutMillis;
		LongAdder counter = new LongAdder();
		System.out.println("正在执行...");
		// 缓存一部分对象; 进入老年代
		int cacheSize = 2000;
		Object[] cachedGarbage = new Object[cacheSize];
		// 在此时间范围内,持续循环
		while (System.currentTimeMillis() < endMillis) {
			// 生成垃圾对象
			Object garbage = generateGarbage(100*1024);
			counter.increment();
			int randomIndex = random.nextInt(2 * cacheSize);
			if (randomIndex < cacheSize) {
				cachedGarbage[randomIndex] = garbage;
			}
		}
		System.out.println("执行结束!共生成对象次数:" + counter.longValue());
	}

	// 生成对象
	private static Object generateGarbage(int max) {
		int randomSize = random.nextInt(max);
		int type = randomSize % 4;
		Object result = null;
		switch (type) {
			case 0:
				result = new int[randomSize];
				break;
			case 1:
				result = new byte[randomSize];
				break;
			case 2:
				result = new double[randomSize];
				break;
			default:
				StringBuilder builder = new StringBuilder();
				String randomString = "randomString-Anything";
				while (builder.length() < randomSize) {
					builder.append(randomString);
					builder.append(max);
					builder.append(randomSize);
				}
				result = builder.toString();
				break;
		}
		return result;
	}
}

```


## 1.串行SerialGC测试

-XX:+UseSerialGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xms512m -Xmx512m

测试结果：
```
2020-10-27T22:28:30.704+0800: [GC (Allocation Failure) [DefNew: 139776K->17472K(157248K), 0.0242659 secs] 139776K->46488K(506816K), 0.0242976 secs] [Times: user=0.00 sys=0.02, real=0.02 secs] 
2020-10-27T22:28:30.751+0800: [GC (Allocation Failure) [DefNew: 157248K->17471K(157248K), 0.0336927 secs] 186264K->95813K(506816K), 0.0337119 secs] [Times: user=0.02 sys=0.02, real=0.03 secs] 
2020-10-27T22:28:30.806+0800: [GC (Allocation Failure) [DefNew: 157215K->17471K(157248K), 0.0250491 secs] 235557K->139754K(506816K), 0.0250685 secs] [Times: user=0.03 sys=0.00, real=0.03 secs] 
2020-10-27T22:28:30.855+0800: [GC (Allocation Failure) [DefNew: 157247K->17471K(157248K), 0.0226877 secs] 279530K->178200K(506816K), 0.0227077 secs] [Times: user=0.02 sys=0.02, real=0.02 secs] 
2020-10-27T22:28:30.898+0800: [GC (Allocation Failure) [DefNew: 157247K->17468K(157248K), 0.0263207 secs] 317976K->222109K(506816K), 0.0263414 secs] [Times: user=0.02 sys=0.02, real=0.03 secs] 
2020-10-27T22:28:30.947+0800: [GC (Allocation Failure) [DefNew: 157244K->17471K(157248K), 0.0233074 secs] 361885K->264946K(506816K), 0.0233269 secs] [Times: user=0.00 sys=0.03, real=0.02 secs] 
2020-10-27T22:28:30.989+0800: [GC (Allocation Failure) [DefNew: 157247K->17470K(157248K), 0.0270936 secs] 404722K->313094K(506816K), 0.0271140 secs] [Times: user=0.02 sys=0.01, real=0.03 secs] 
2020-10-27T22:28:31.037+0800: [GC (Allocation Failure) [DefNew: 157246K->17469K(157248K), 0.0261476 secs] 452870K->359077K(506816K), 0.0261689 secs] [Times: user=0.02 sys=0.02, real=0.03 secs] 
2020-10-27T22:28:31.085+0800: [GC (Allocation Failure) [DefNew: 157245K->157245K(157248K), 0.0000129 secs][Tenured: 341608K->281193K(349568K), 0.0412001 secs] 498853K->281193K(506816K), [Metaspace: 3396K->3396K(1056768K)], 0.0412471 secs] [Times: user=0.05 sys=0.00, real=0.04 secs] 
2020-10-27T22:28:31.149+0800: [GC (Allocation Failure) [DefNew: 139776K->17471K(157248K), 0.0075214 secs] 420969K->325408K(506816K), 0.0075430 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
2020-10-27T22:28:31.176+0800: [GC (Allocation Failure) [DefNew: 157247K->157247K(157248K), 0.0000166 secs][Tenured: 307936K->305527K(349568K), 0.0456799 secs] 465184K->305527K(506816K), [Metaspace: 3396K->3396K(1056768K)], 0.0457315 secs] [Times: user=0.05 sys=0.00, real=0.04 secs] 
2020-10-27T22:28:31.243+0800: [GC (Allocation Failure) [DefNew: 139776K->139776K(157248K), 0.0000136 secs][Tenured: 305527K->321436K(349568K), 0.0464890 secs] 445303K->321436K(506816K), [Metaspace: 3396K->3396K(1056768K)], 0.0465370 secs] [Times: user=0.05 sys=0.00, real=0.05 secs] 
2020-10-27T22:28:31.309+0800: [GC (Allocation Failure) [DefNew: 139776K->139776K(157248K), 0.0000147 secs][Tenured: 321436K->318578K(349568K), 0.0490969 secs] 461212K->318578K(506816K), [Metaspace: 3396K->3396K(1056768K)], 0.0491546 secs] [Times: user=0.05 sys=0.00, real=0.05 secs] 
2020-10-27T22:28:31.377+0800: [GC (Allocation Failure) [DefNew: 139776K->139776K(157248K), 0.0000158 secs][Tenured: 318578K->340572K(349568K), 0.0312722 secs] 458354K->340572K(506816K), [Metaspace: 3396K->3396K(1056768K)], 0.0313272 secs] [Times: user=0.03 sys=0.00, real=0.03 secs] 
2020-10-27T22:28:31.429+0800: [GC (Allocation Failure) [DefNew: 139776K->139776K(157248K), 0.0000156 secs][Tenured: 340572K->344238K(349568K), 0.0504060 secs] 480348K->344238K(506816K), [Metaspace: 3396K->3396K(1056768K)], 0.0504575 secs] [Times: user=0.05 sys=0.00, real=0.05 secs] 
2020-10-27T22:28:31.502+0800: [GC (Allocation Failure) [DefNew: 139776K->139776K(157248K), 0.0000149 secs][Tenured: 344238K->349492K(349568K), 0.0564180 secs] 484014K->350818K(506816K), [Metaspace: 3396K->3396K(1056768K)], 0.0564673 secs] [Times: user=0.05 sys=0.00, real=0.06 secs] 
2020-10-27T22:28:31.589+0800: [Full GC (Allocation Failure) [Tenured: 349492K->331447K(349568K), 0.0653875 secs] 506314K->331447K(506816K), [Metaspace: 3396K->3396K(1056768K)], 0.0654179 secs] [Times: user=0.05 sys=0.00, real=0.07 secs] 
执行结束!共生成对象次数:9093
Heap
 def new generation   total 157248K, used 6167K [0x00000000e0000000, 0x00000000eaaa0000, 0x00000000eaaa0000)
  eden space 139776K,   4% used [0x00000000e0000000, 0x00000000e0605e70, 0x00000000e8880000)
  from space 17472K,   0% used [0x00000000e9990000, 0x00000000e9990000, 0x00000000eaaa0000)
  to   space 17472K,   0% used [0x00000000e8880000, 0x00000000e8880000, 0x00000000e9990000)
 tenured generation   total 349568K, used 331447K [0x00000000eaaa0000, 0x0000000100000000, 0x0000000100000000)
   the space 349568K,  94% used [0x00000000eaaa0000, 0x00000000fee4dc38, 0x00000000fee4de00, 0x0000000100000000)
 Metaspace       used 3403K, capacity 4500K, committed 4864K, reserved 1056768K
  class space    used 367K, capacity 388K, committed 512K, reserved 1048576K
```
一共进行16次youngGC，1次fullGC；
前面8次GC耗时大概在26ms左右，最后几次youngGC其实属于无用功，老年代快满了，耗时也较多，接近50ms。


## 2.并行ParallelGC测试

-XX:+UseParallelGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xms512m -Xmx512m

测试结果：
```
2020-10-27T22:43:12.863+0800: [GC (Allocation Failure) [PSYoungGen: 131584K->21500K(153088K)] 131584K->46587K(502784K), 0.0087615 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
2020-10-27T22:43:12.892+0800: [GC (Allocation Failure) [PSYoungGen: 153084K->21502K(153088K)] 178171K->91629K(502784K), 0.0125153 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
2020-10-27T22:43:12.923+0800: [GC (Allocation Failure) [PSYoungGen: 153086K->21492K(153088K)] 223213K->138695K(502784K), 0.0132514 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
2020-10-27T22:43:12.954+0800: [GC (Allocation Failure) [PSYoungGen: 153076K->21502K(153088K)] 270279K->185566K(502784K), 0.0125581 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
2020-10-27T22:43:12.988+0800: [GC (Allocation Failure) [PSYoungGen: 152720K->21494K(153088K)] 316784K->222904K(502784K), 0.0119709 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
2020-10-27T22:43:13.017+0800: [GC (Allocation Failure) [PSYoungGen: 152621K->21501K(80384K)] 354032K->268130K(430080K), 0.0158513 secs] [Times: user=0.00 sys=0.02, real=0.02 secs] 
2020-10-27T22:43:13.041+0800: [GC (Allocation Failure) [PSYoungGen: 80060K->34569K(116736K)] 326690K->287228K(466432K), 0.0069433 secs] [Times: user=0.05 sys=0.00, real=0.01 secs] 
2020-10-27T22:43:13.057+0800: [GC (Allocation Failure) [PSYoungGen: 93449K->44939K(116736K)] 346108K->304925K(466432K), 0.0083592 secs] [Times: user=0.03 sys=0.00, real=0.01 secs] 
2020-10-27T22:43:13.076+0800: [GC (Allocation Failure) [PSYoungGen: 103819K->53713K(116736K)] 363805K->319821K(466432K), 0.0079496 secs] [Times: user=0.05 sys=0.00, real=0.01 secs] 
2020-10-27T22:43:13.093+0800: [GC (Allocation Failure) [PSYoungGen: 112455K->38310K(116736K)] 378563K->338793K(466432K), 0.0109540 secs] [Times: user=0.05 sys=0.00, real=0.01 secs] 
2020-10-27T22:43:13.104+0800: [Full GC (Ergonomics) [PSYoungGen: 38310K->0K(116736K)] [ParOldGen: 300483K->232555K(349696K)] 338793K->232555K(466432K), [Metaspace: 3397K->3397K(1056768K)], 0.0350839 secs] [Times: user=0.05 sys=0.02, real=0.03 secs] 
2020-10-27T22:43:13.150+0800: [GC (Allocation Failure) [PSYoungGen: 58880K->21808K(116736K)] 291435K->254363K(466432K), 0.0041048 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T22:43:13.163+0800: [GC (Allocation Failure) [PSYoungGen: 80604K->17935K(116736K)] 313159K->271712K(466432K), 0.0054117 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
2020-10-27T22:43:13.176+0800: [GC (Allocation Failure) [PSYoungGen: 76743K->21102K(116736K)] 330519K->291545K(466432K), 0.0058919 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
2020-10-27T22:43:13.192+0800: [GC (Allocation Failure) [PSYoungGen: 79694K->16746K(116736K)] 350137K->306978K(466432K), 0.0066960 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
2020-10-27T22:43:13.207+0800: [GC (Allocation Failure) [PSYoungGen: 75294K->19897K(116736K)] 365526K->326328K(466432K), 0.0075545 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
2020-10-27T22:43:13.215+0800: [Full GC (Ergonomics) [PSYoungGen: 19897K->0K(116736K)] [ParOldGen: 306430K->261209K(349696K)] 326328K->261209K(466432K), [Metaspace: 3397K->3397K(1056768K)], 0.0397328 secs] [Times: user=0.09 sys=0.00, real=0.04 secs] 
2020-10-27T22:43:13.264+0800: [GC (Allocation Failure) [PSYoungGen: 58880K->21045K(116736K)] 320089K->282254K(466432K), 0.0038455 secs] [Times: user=0.05 sys=0.00, real=0.00 secs] 
2020-10-27T22:43:13.276+0800: [GC (Allocation Failure) [PSYoungGen: 79734K->20811K(116736K)] 340943K->301255K(466432K), 0.0063630 secs] [Times: user=0.03 sys=0.00, real=0.01 secs] 
2020-10-27T22:43:13.292+0800: [GC (Allocation Failure) [PSYoungGen: 79629K->18001K(116736K)] 360073K->317664K(466432K), 0.0062539 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
2020-10-27T22:43:13.307+0800: [GC (Allocation Failure) [PSYoungGen: 76881K->17342K(116736K)] 376544K->334362K(466432K), 0.0056363 secs] [Times: user=0.02 sys=0.05, real=0.01 secs] 
2020-10-27T22:43:13.313+0800: [Full GC (Ergonomics) [PSYoungGen: 17342K->0K(116736K)] [ParOldGen: 317019K->284944K(349696K)] 334362K->284944K(466432K), [Metaspace: 3397K->3397K(1056768K)], 0.0684813 secs] [Times: user=0.09 sys=0.00, real=0.07 secs] 
2020-10-27T22:43:13.393+0800: [GC (Allocation Failure) [PSYoungGen: 58715K->23342K(116736K)] 343659K->308286K(466432K), 0.0241386 secs] [Times: user=0.00 sys=0.00, real=0.03 secs] 
2020-10-27T22:43:13.427+0800: [GC (Allocation Failure) [PSYoungGen: 82222K->18094K(116736K)] 367166K->325532K(466432K), 0.0079612 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
2020-10-27T22:43:13.445+0800: [GC (Allocation Failure) [PSYoungGen: 76974K->19179K(116736K)] 384412K->343977K(466432K), 0.0071323 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
2020-10-27T22:43:13.452+0800: [Full GC (Ergonomics) [PSYoungGen: 19179K->0K(116736K)] [ParOldGen: 324798K->299331K(349696K)] 343977K->299331K(466432K), [Metaspace: 3397K->3397K(1056768K)], 0.0420584 secs] [Times: user=0.13 sys=0.00, real=0.04 secs] 
2020-10-27T22:43:13.505+0800: [GC (Allocation Failure) [PSYoungGen: 58880K->20693K(116736K)] 358211K->320025K(466432K), 0.0034247 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T22:43:13.519+0800: [GC (Allocation Failure) [PSYoungGen: 79573K->17091K(116736K)] 378905K->336679K(466432K), 0.0057373 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T22:43:13.525+0800: [Full GC (Ergonomics) [PSYoungGen: 17091K->0K(116736K)] [ParOldGen: 319588K->304439K(349696K)] 336679K->304439K(466432K), [Metaspace: 3397K->3397K(1056768K)], 0.0456005 secs] [Times: user=0.09 sys=0.00, real=0.04 secs] 
2020-10-27T22:43:13.579+0800: [GC (Allocation Failure) [PSYoungGen: 58880K->22004K(116736K)] 363319K->326443K(466432K), 0.0032057 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T22:43:13.591+0800: [GC (Allocation Failure) [PSYoungGen: 80884K->20045K(116736K)] 385323K->345495K(466432K), 0.0054426 secs] [Times: user=0.03 sys=0.00, real=0.00 secs] 
2020-10-27T22:43:13.596+0800: [Full GC (Ergonomics) [PSYoungGen: 20045K->0K(116736K)] [ParOldGen: 325450K->305588K(349696K)] 345495K->305588K(466432K), [Metaspace: 3397K->3397K(1056768K)], 0.0418712 secs] [Times: user=0.06 sys=0.00, real=0.04 secs] 
2020-10-27T22:43:13.648+0800: [GC (Allocation Failure) [PSYoungGen: 58130K->19893K(116736K)] 363719K->325481K(466432K), 0.0031201 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T22:43:13.659+0800: [GC (Allocation Failure) [PSYoungGen: 78691K->22674K(117760K)] 384279K->346482K(467456K), 0.0082819 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
2020-10-27T22:43:13.668+0800: [Full GC (Ergonomics) [PSYoungGen: 22674K->0K(117760K)] [ParOldGen: 323808K->312282K(349696K)] 346482K->312282K(467456K), [Metaspace: 3397K->3397K(1056768K)], 0.0485724 secs] [Times: user=0.11 sys=0.00, real=0.05 secs] 
2020-10-27T22:43:13.725+0800: [GC (Allocation Failure) [PSYoungGen: 59904K->21907K(116736K)] 372186K->334189K(466432K), 0.0033235 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T22:43:13.738+0800: [GC (Allocation Failure) [PSYoungGen: 81811K->40705K(114176K)] 394093K->352988K(463872K), 0.0056403 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
2020-10-27T22:43:13.753+0800: [GC (Allocation Failure) [PSYoungGen: 100194K->57340K(117248K)] 412477K->374963K(466944K), 0.0090986 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
执行结束!共生成对象次数:8519
Heap
 PSYoungGen      total 117248K, used 60320K [0x00000000f5580000, 0x0000000100000000, 0x0000000100000000)
  eden space 59904K, 4% used [0x00000000f5580000,0x00000000f5868fe0,0x00000000f9000000)
  from space 57344K, 99% used [0x00000000f9000000,0x00000000fc7ff190,0x00000000fc800000)
  to   space 57344K, 0% used [0x00000000fc800000,0x00000000fc800000,0x0000000100000000)
 ParOldGen       total 349696K, used 317623K [0x00000000e0000000, 0x00000000f5580000, 0x00000000f5580000)
  object space 349696K, 90% used [0x00000000e0000000,0x00000000f362dd58,0x00000000f5580000)
 Metaspace       used 3404K, capacity 4500K, committed 4864K, reserved 1056768K
  class space    used 367K, capacity 388K, committed 512K, reserved 1048576K
```

并行回收器进行了大量GC，其中youngGC有31次，fullGC有7次。
最开始10次youngGC（耗时约10ms）后触发了一次fullGC（约40ms），后面几乎每2-3次youngGC就会触发一次fullGC，老年代越来越大。
值得注意的是虽然并行GC次数比较频繁，但每次GC耗时低，且多线程并行实际业务线程停顿时间非常短（real=0-0.1）
并行GC也是JDK8默认回收器（Parallel Scavenge + Parallel Scavenge Old）


## 3.CMS GC测试

-XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xms512m -Xmx512m

测试结果：
```
2020-10-27T23:03:39.067+0800: [GC (Allocation Failure) [ParNew: 139776K->17471K(157248K), 0.0087319 secs] 139776K->41662K(506816K), 0.0087676 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
2020-10-27T23:03:39.098+0800: [GC (Allocation Failure) [ParNew: 157247K->17472K(157248K), 0.0146792 secs] 181438K->88944K(506816K), 0.0147025 secs] [Times: user=0.03 sys=0.02, real=0.01 secs] 
2020-10-27T23:03:39.137+0800: [GC (Allocation Failure) [ParNew: 157248K->17472K(157248K), 0.0228946 secs] 228720K->135136K(506816K), 0.0229185 secs] [Times: user=0.11 sys=0.00, real=0.02 secs] 
2020-10-27T23:03:39.179+0800: [GC (Allocation Failure) [ParNew: 157107K->17472K(157248K), 0.0252202 secs] 274772K->182766K(506816K), 0.0252456 secs] [Times: user=0.06 sys=0.03, real=0.03 secs] 
2020-10-27T23:03:39.230+0800: [GC (Allocation Failure) [ParNew: 157248K->17472K(157248K), 0.0225531 secs] 322542K->224866K(506816K), 0.0225786 secs] [Times: user=0.05 sys=0.02, real=0.02 secs] 
2020-10-27T23:03:39.253+0800: [GC (CMS Initial Mark) [1 CMS-initial-mark: 207394K(349568K)] 225248K(506816K), 0.0001407 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T23:03:39.253+0800: [CMS-concurrent-mark-start]
2020-10-27T23:03:39.257+0800: [CMS-concurrent-mark: 0.004/0.004 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T23:03:39.257+0800: [CMS-concurrent-preclean-start]
2020-10-27T23:03:39.258+0800: [CMS-concurrent-preclean: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T23:03:39.258+0800: [CMS-concurrent-abortable-preclean-start]
2020-10-27T23:03:39.274+0800: [GC (Allocation Failure) [ParNew: 157248K->17472K(157248K), 0.0247263 secs] 364642K->273721K(506816K), 0.0247499 secs] [Times: user=0.03 sys=0.03, real=0.02 secs] 
2020-10-27T23:03:39.319+0800: [GC (Allocation Failure) [ParNew: 157248K->17472K(157248K), 0.0251341 secs] 413497K->321615K(506816K), 0.0251605 secs] [Times: user=0.06 sys=0.03, real=0.03 secs] 
2020-10-27T23:03:39.361+0800: [GC (Allocation Failure) [ParNew: 157248K->157248K(157248K), 0.0000159 secs][CMS2020-10-27T23:03:39.361+0800: [CMS-concurrent-abortable-preclean: 0.003/0.104 secs] [Times: user=0.14 sys=0.06, real=0.10 secs] 
 (concurrent mode failure): 304143K->253910K(349568K), 0.0421915 secs] 461391K->253910K(506816K), [Metaspace: 3396K->3396K(1056768K)], 0.0422435 secs] [Times: user=0.03 sys=0.00, real=0.04 secs] 
2020-10-27T23:03:39.425+0800: [GC (Allocation Failure) [ParNew: 139722K->17472K(157248K), 0.0065319 secs] 393633K->301696K(506816K), 0.0065559 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
2020-10-27T23:03:39.432+0800: [GC (CMS Initial Mark) [1 CMS-initial-mark: 284224K(349568K)] 302103K(506816K), 0.0001055 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T23:03:39.432+0800: [CMS-concurrent-mark-start]
2020-10-27T23:03:39.435+0800: [CMS-concurrent-mark: 0.002/0.002 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T23:03:39.435+0800: [CMS-concurrent-preclean-start]
2020-10-27T23:03:39.435+0800: [CMS-concurrent-preclean: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T23:03:39.435+0800: [CMS-concurrent-abortable-preclean-start]
2020-10-27T23:03:39.462+0800: [GC (Allocation Failure) [ParNew: 157248K->17472K(157248K), 0.0167490 secs] 441472K->348302K(506816K), 0.0167724 secs] [Times: user=0.03 sys=0.02, real=0.02 secs] 
2020-10-27T23:03:39.480+0800: [CMS-concurrent-abortable-preclean: 0.001/0.044 secs] [Times: user=0.06 sys=0.02, real=0.05 secs] 
2020-10-27T23:03:39.480+0800: [GC (CMS Final Remark) [YG occupancy: 23916 K (157248 K)][Rescan (parallel) , 0.0002788 secs][weak refs processing, 0.0000058 secs][class unloading, 0.0002803 secs][scrub symbol table, 0.0005143 secs][scrub string table, 0.0001500 secs][1 CMS-remark: 330830K(349568K)] 354747K(506816K), 0.0013062 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T23:03:39.481+0800: [CMS-concurrent-sweep-start]
2020-10-27T23:03:39.482+0800: [CMS-concurrent-sweep: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T23:03:39.482+0800: [CMS-concurrent-reset-start]
2020-10-27T23:03:39.482+0800: [CMS-concurrent-reset: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T23:03:39.505+0800: [GC (Allocation Failure) [ParNew: 156780K->156780K(157248K), 0.0000171 secs][CMS: 295844K->284293K(349568K), 0.0510089 secs] 452624K->284293K(506816K), [Metaspace: 3397K->3397K(1056768K)], 0.0510639 secs] [Times: user=0.05 sys=0.00, real=0.05 secs] 
2020-10-27T23:03:39.556+0800: [GC (CMS Initial Mark) [1 CMS-initial-mark: 284293K(349568K)] 285041K(506816K), 0.0001041 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T23:03:39.556+0800: [CMS-concurrent-mark-start]
2020-10-27T23:03:39.559+0800: [CMS-concurrent-mark: 0.002/0.002 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T23:03:39.559+0800: [CMS-concurrent-preclean-start]
2020-10-27T23:03:39.559+0800: [CMS-concurrent-preclean: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T23:03:39.559+0800: [CMS-concurrent-abortable-preclean-start]
2020-10-27T23:03:39.579+0800: [GC (Allocation Failure) [ParNew: 139776K->17472K(157248K), 0.0077808 secs] 424069K->337495K(506816K), 0.0078058 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
2020-10-27T23:03:39.610+0800: [GC (Allocation Failure) [ParNew: 157248K->157248K(157248K), 0.0000156 secs][CMS2020-10-27T23:03:39.610+0800: [CMS-concurrent-abortable-preclean: 0.001/0.051 secs] [Times: user=0.05 sys=0.00, real=0.05 secs] 
 (concurrent mode failure): 320023K->299880K(349568K), 0.0567141 secs] 477271K->299880K(506816K), [Metaspace: 3397K->3397K(1056768K)], 0.0567665 secs] [Times: user=0.05 sys=0.00, real=0.06 secs] 
2020-10-27T23:03:39.686+0800: [GC (Allocation Failure) [ParNew: 139776K->17472K(157248K), 0.0089415 secs] 439656K->350985K(506816K), 0.0089640 secs] [Times: user=0.05 sys=0.00, real=0.01 secs] 
2020-10-27T23:03:39.695+0800: [GC (CMS Initial Mark) [1 CMS-initial-mark: 333513K(349568K)] 351775K(506816K), 0.0000993 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T23:03:39.695+0800: [CMS-concurrent-mark-start]
2020-10-27T23:03:39.697+0800: [CMS-concurrent-mark: 0.002/0.002 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T23:03:39.697+0800: [CMS-concurrent-preclean-start]
2020-10-27T23:03:39.698+0800: [CMS-concurrent-preclean: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T23:03:39.698+0800: [CMS-concurrent-abortable-preclean-start]
2020-10-27T23:03:39.698+0800: [CMS-concurrent-abortable-preclean: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T23:03:39.698+0800: [GC (CMS Final Remark) [YG occupancy: 38119 K (157248 K)][Rescan (parallel) , 0.0002368 secs][weak refs processing, 0.0000054 secs][class unloading, 0.0002270 secs][scrub symbol table, 0.0003820 secs][scrub string table, 0.0001162 secs][1 CMS-remark: 333513K(349568K)] 371632K(506816K), 0.0010265 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T23:03:39.699+0800: [CMS-concurrent-sweep-start]
2020-10-27T23:03:39.700+0800: [CMS-concurrent-sweep: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T23:03:39.700+0800: [CMS-concurrent-reset-start]
2020-10-27T23:03:39.700+0800: [CMS-concurrent-reset: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T23:03:39.718+0800: [GC (Allocation Failure) [ParNew: 157248K->17472K(157248K), 0.0092659 secs] 450234K->350406K(506816K), 0.0092898 secs] [Times: user=0.05 sys=0.02, real=0.01 secs] 
2020-10-27T23:03:39.727+0800: [GC (CMS Initial Mark) [1 CMS-initial-mark: 332934K(349568K)] 351133K(506816K), 0.0001050 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T23:03:39.727+0800: [CMS-concurrent-mark-start]
2020-10-27T23:03:39.730+0800: [CMS-concurrent-mark: 0.002/0.002 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T23:03:39.730+0800: [CMS-concurrent-preclean-start]
2020-10-27T23:03:39.730+0800: [CMS-concurrent-preclean: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T23:03:39.730+0800: [CMS-concurrent-abortable-preclean-start]
2020-10-27T23:03:39.730+0800: [CMS-concurrent-abortable-preclean: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T23:03:39.730+0800: [GC (CMS Final Remark) [YG occupancy: 44683 K (157248 K)][Rescan (parallel) , 0.0002524 secs][weak refs processing, 0.0000058 secs][class unloading, 0.0002707 secs][scrub symbol table, 0.0004050 secs][scrub string table, 0.0001233 secs][1 CMS-remark: 332934K(349568K)] 377617K(506816K), 0.0011311 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T23:03:39.732+0800: [CMS-concurrent-sweep-start]
2020-10-27T23:03:39.732+0800: [CMS-concurrent-sweep: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T23:03:39.732+0800: [CMS-concurrent-reset-start]
2020-10-27T23:03:39.733+0800: [CMS-concurrent-reset: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T23:03:39.748+0800: [GC (Allocation Failure) [ParNew: 157248K->157248K(157248K), 0.0000170 secs][CMS: 299048K->322232K(349568K), 0.0491716 secs] 456296K->322232K(506816K), [Metaspace: 3397K->3397K(1056768K)], 0.0492271 secs] [Times: user=0.06 sys=0.00, real=0.05 secs] 
2020-10-27T23:03:39.797+0800: [GC (CMS Initial Mark) [1 CMS-initial-mark: 322232K(349568K)] 322749K(506816K), 0.0001025 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T23:03:39.797+0800: [CMS-concurrent-mark-start]
2020-10-27T23:03:39.800+0800: [CMS-concurrent-mark: 0.002/0.002 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T23:03:39.800+0800: [CMS-concurrent-preclean-start]
2020-10-27T23:03:39.800+0800: [CMS-concurrent-preclean: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T23:03:39.800+0800: [CMS-concurrent-abortable-preclean-start]
2020-10-27T23:03:39.818+0800: [GC (Allocation Failure) [ParNew: 139776K->139776K(157248K), 0.0000156 secs][CMS2020-10-27T23:03:39.818+0800: [CMS-concurrent-abortable-preclean: 0.000/0.018 secs] [Times: user=0.02 sys=0.00, real=0.02 secs] 
 (concurrent mode failure): 322232K->331071K(349568K), 0.0620749 secs] 462008K->331071K(506816K), [Metaspace: 3397K->3397K(1056768K)], 0.0621276 secs] [Times: user=0.06 sys=0.00, real=0.06 secs] 
2020-10-27T23:03:39.904+0800: [GC (Allocation Failure) [ParNew: 139776K->139776K(157248K), 0.0000184 secs][CMS: 331071K->344550K(349568K), 0.0576620 secs] 470847K->344550K(506816K), [Metaspace: 3397K->3397K(1056768K)], 0.0577179 secs] [Times: user=0.06 sys=0.00, real=0.06 secs] 
2020-10-27T23:03:39.962+0800: [GC (CMS Initial Mark) [1 CMS-initial-mark: 344550K(349568K)] 344841K(506816K), 0.0001050 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T23:03:39.962+0800: [CMS-concurrent-mark-start]
2020-10-27T23:03:39.964+0800: [CMS-concurrent-mark: 0.002/0.002 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T23:03:39.964+0800: [CMS-concurrent-preclean-start]
2020-10-27T23:03:39.965+0800: [CMS-concurrent-preclean: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T23:03:39.965+0800: [CMS-concurrent-abortable-preclean-start]
2020-10-27T23:03:39.965+0800: [CMS-concurrent-abortable-preclean: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T23:03:39.965+0800: [GC (CMS Final Remark) [YG occupancy: 22721 K (157248 K)][Rescan (parallel) , 0.0001938 secs][weak refs processing, 0.0000054 secs][class unloading, 0.0002405 secs][scrub symbol table, 0.0003635 secs][scrub string table, 0.0000999 secs][1 CMS-remark: 344550K(349568K)] 367272K(506816K), 0.0009627 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T23:03:39.966+0800: [CMS-concurrent-sweep-start]
2020-10-27T23:03:39.966+0800: [CMS-concurrent-sweep: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T23:03:39.966+0800: [CMS-concurrent-reset-start]
2020-10-27T23:03:39.967+0800: [CMS-concurrent-reset: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
执行结束!共生成对象次数:9859
Heap
 par new generation   total 157248K, used 103281K [0x00000000e0000000, 0x00000000eaaa0000, 0x00000000eaaa0000)
  eden space 139776K,  73% used [0x00000000e0000000, 0x00000000e64dc738, 0x00000000e8880000)
  from space 17472K,   0% used [0x00000000e8880000, 0x00000000e8880000, 0x00000000e9990000)
  to   space 17472K,   0% used [0x00000000e9990000, 0x00000000e9990000, 0x00000000eaaa0000)
 concurrent mark-sweep generation total 349568K, used 343999K [0x00000000eaaa0000, 0x0000000100000000, 0x0000000100000000)
 Metaspace       used 3403K, capacity 4500K, committed 4864K, reserved 1056768K
  class space    used 367K, capacity 388K, committed 512K, reserved 1048576K
```
CMS是老年代的垃圾回收器，旨在进行fullGC时实现并发收集、低停顿，为了达成这个目标CMS进行了相当复杂的GC过程，有六大阶段：

阶段 1：Initial Mark（初始标记）
阶段 2：Concurrent Mark（并发标记）
阶段 3：Concurrent Preclean（并发预清理）
阶段 4： Final Remark（最终标记）
阶段 5： Concurrent Sweep（并发清除）
阶段 6： Concurrent Reset（并发重置）

其中初始标记、重新标记这两个步骤仍然需要“Stop The World”。初始标记仅仅只是标记一下GCRoots能直接关联到的对象，速度很快；重新标记阶段则是为了修正并发标记期间，因用户程序继续运作而导致标记产生变动的那一部分对象的标记记录，这个阶段的停顿时间通常会比初始标记阶段稍长一些，但也远比并发标记阶段的时间短。
由于在整个过程中耗时最长的并发标记和并发清除阶段中，垃圾收集器线程都可以与用户线程一起工作，所以从总体上来说，CMS收集器的内存回收过程是与用户线程一起并发执行的。

所以CMS总共的耗时其实是比较低的。


## 4.G1 GC测试

由于G1GC日志详情太过复杂，这里只截取一部分：
-XX:+UseG1GC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xms512m -Xmx512m
```
2020-10-27T23:16:30.748+0800: [GC pause (G1 Evacuation Pause) (young), 0.0021080 secs]
   [Parallel Time: 1.8 ms, GC Workers: 4]
      [GC Worker Start (ms): Min: 157.0, Avg: 157.0, Max: 157.0, Diff: 0.0]
      [Ext Root Scanning (ms): Min: 0.2, Avg: 0.2, Max: 0.2, Diff: 0.0, Sum: 0.9]
      [Update RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
         [Processed Buffers: Min: 0, Avg: 0.0, Max: 0, Diff: 0, Sum: 0]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 1.3, Avg: 1.4, Max: 1.5, Diff: 0.3, Sum: 5.6]
      [Termination (ms): Min: 0.0, Avg: 0.1, Max: 0.2, Diff: 0.2, Sum: 0.6]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [GC Worker Total (ms): Min: 1.8, Avg: 1.8, Max: 1.8, Diff: 0.0, Sum: 7.2]
      [GC Worker End (ms): Min: 158.8, Avg: 158.8, Max: 158.8, Diff: 0.0]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.0 ms]
   [Other: 0.2 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.1 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.0 ms]
      [Humongous Reclaim: 0.0 ms]
      [Free CSet: 0.0 ms]
   [Eden: 25.0M(25.0M)->0.0B(21.0M) Survivors: 0.0B->4096.0K Heap: 29.0M(512.0M)->9192.0K(512.0M)]
 [Times: user=0.00 sys=0.00, real=0.00 secs] 
 2020-10-27T23:16:30.950+0800: [GC concurrent-root-region-scan-start]
2020-10-27T23:16:30.950+0800: [GC concurrent-root-region-scan-end, 0.0001582 secs]
2020-10-27T23:16:30.950+0800: [GC concurrent-mark-start]
2020-10-27T23:16:30.954+0800: [GC concurrent-mark-end, 0.0034574 secs]
2020-10-27T23:16:30.954+0800: [GC remark [Finalize Marking, 0.0000976 secs] [GC ref-proc, 0.0001178 secs] [Unloading, 0.0006287 secs], 0.0014545 secs]
 [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T23:16:30.956+0800: [GC cleanup 236M->222M(512M), 0.0003064 secs]
 [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-27T23:16:30.956+0800: [GC concurrent-cleanup-start]
2020-10-27T23:16:30.956+0800: [GC concurrent-cleanup-end, 0.0000276 secs]
```
打印精简版GC日志：
-XX:+UseG1GC -XX:+PrintGC -XX:+PrintGCDateStamps -Xms512m -Xmx512m
```
2020-10-27T23:19:41.039+0800: [GC pause (G1 Evacuation Pause) (young) 30M->10096K(512M), 0.0025484 secs]
2020-10-27T23:19:41.051+0800: [GC pause (G1 Evacuation Pause) (young) 36M->19M(512M), 0.0024521 secs]
2020-10-27T23:19:41.062+0800: [GC pause (G1 Evacuation Pause) (young) 54M->33M(512M), 0.0031763 secs]
2020-10-27T23:19:41.083+0800: [GC pause (G1 Evacuation Pause) (young) 92M->54M(512M), 0.0035056 secs]
2020-10-27T23:19:41.099+0800: [GC pause (G1 Evacuation Pause) (young) 115M->82M(512M), 0.0042687 secs]
2020-10-27T23:19:41.125+0800: [GC pause (G1 Evacuation Pause) (young) 165M->113M(512M), 0.0047815 secs]
2020-10-27T23:19:41.149+0800: [GC pause (G1 Evacuation Pause) (young) 204M->142M(512M), 0.0050530 secs]
2020-10-27T23:19:41.208+0800: [GC pause (G1 Evacuation Pause) (young) 298M->196M(512M), 0.0120068 secs]
2020-10-27T23:19:41.235+0800: [GC pause (G1 Humongous Allocation) (young) (initial-mark) 254M->220M(512M), 0.0047223 secs]
2020-10-27T23:19:41.240+0800: [GC concurrent-root-region-scan-start]
2020-10-27T23:19:41.240+0800: [GC concurrent-root-region-scan-end, 0.0001600 secs]
2020-10-27T23:19:41.240+0800: [GC concurrent-mark-start]
2020-10-27T23:19:41.243+0800: [GC concurrent-mark-end, 0.0033134 secs]
2020-10-27T23:19:41.244+0800: [GC remark, 0.0012306 secs]
2020-10-27T23:19:41.245+0800: [GC cleanup 240M->227M(512M), 0.0004113 secs]
2020-10-27T23:19:41.245+0800: [GC concurrent-cleanup-start]
2020-10-27T23:19:41.245+0800: [GC concurrent-cleanup-end, 0.0000427 secs]
```

可以看到G1的GC过程是在CMS基础上进一步复杂化，通过多region动态分区、卡表等技术，实现相当低的GC停顿。
当然，此处512m测试并不能发挥G1真正实力，在大内存应用上更适合G1，能发挥其优势，这个优劣势的Java堆容量平衡点通常在6GB至8GB之间。

注意：
JDK 9发布之日，G1宣告取代Parallel Scavenge加Parallel Old组合，成为服务端模式下的默认垃圾收集器，而CMS则沦落至被声明为不推荐使用（Deprecate）的收集器。。

---

# 2、压测演练（gateway-server）

压测指令：
.\sb.exe -u http://localhost:8088/api/hello -c 30 -N 30

## 1.串行SerialGC测试
java -jar -XX:+UseSerialGC -Xms2g -Xmx2g gateway-server-0.0.1-SNAPSHOT.jar

测试结果：
```
Starting at 2020/10/27 23:56:09
[Press C to stop the test]
121968  (RPS: 3526.1)
---------------Finished!----------------
Finished at 2020/10/27 23:56:44 (took 00:00:34.7610451)
Status 200:    121981

RPS: 3914.8 (requests/second)
Max: 96ms
Min: 0ms
Avg: 0.4ms

  50%   below 0ms
  60%   below 0ms
  70%   below 0ms
  80%   below 0ms
  90%   below 1ms
  95%   below 3ms
  98%   below 5ms
  99%   below 7ms
99.9%   below 13ms
```


## 2.并行ParallelGC测试
java -jar -XX:+UseParallelGC -Xms2g -Xmx2g gateway-server-0.0.1-SNAPSHOT.jar

测试结果：
```
Starting at 2020/10/27 23:59:06
[Press C to stop the test]
118152  (RPS: 3417.7)
---------------Finished!----------------
Finished at 2020/10/27 23:59:41 (took 00:00:34.7689975)
Status 200:    118164

RPS: 3790.5 (requests/second)
Max: 82ms
Min: 0ms
Avg: 0.5ms

  50%   below 0ms
  60%   below 0ms
  70%   below 0ms
  80%   below 0ms
  90%   below 1ms
  95%   below 3ms
  98%   below 5ms
  99%   below 7ms
99.9%   below 14ms
```
和串行GC的延迟、吞吐量差不多

## 3.CMS GC测试
java -jar -XX:+UseConcMarkSweepGC -Xms2g -Xmx2g gateway-server-0.0.1-SNAPSHOT.jar

测试结果：
```
Starting at 2020/10/28 0:01:24
[Press C to stop the test]
118886  (RPS: 3430.3)
---------------Finished!----------------
Finished at 2020/10/28 0:01:59 (took 00:00:34.8248172)
Status 200:    118886

RPS: 3818 (requests/second)
Max: 96ms
Min: 0ms
Avg: 0.5ms

  50%   below 0ms
  60%   below 0ms
  70%   below 0ms
  80%   below 0ms
  90%   below 1ms
  95%   below 3ms
  98%   below 5ms
  99%   below 7ms
99.9%   below 15ms
```

## 4.G1 GC测试
java -jar -XX:+UseG1GC -Xms2g -Xmx2g gateway-server-0.0.1-SNAPSHOT.jar

测试结果：
```
Starting at 2020/10/28 0:06:39
[Press C to stop the test]
114649  (RPS: 3320.8)
---------------Finished!----------------
Finished at 2020/10/28 0:07:13 (took 00:00:34.6902038)
Status 200:    114656

RPS: 3682 (requests/second)
Max: 74ms
Min: 0ms
Avg: 0.5ms

  50%   below 0ms
  60%   below 0ms
  70%   below 0ms
  80%   below 0ms
  90%   below 1ms
  95%   below 3ms
  98%   below 5ms
  99%   below 7ms
99.9%   below 14ms
```
。。。。。我并不能看出啥区别，可能测试环境不太适合


# 3、 HttpClient示例
```
	public static void main(String[] args) {

		try {
			//获得Http客户端
			CloseableHttpClient httpclient = HttpClients.createDefault();

			//创建get请求
			HttpGet httpGet = new HttpGet("http://localhost:8808/test");
			//执行请求
			CloseableHttpResponse execute = httpclient.execute(httpGet);
			//解析返回值
			StatusLine statusLine = execute.getStatusLine();
			//获取到返回状态码
			System.out.println("状态码为："+statusLine.getStatusCode());
			String content = EntityUtils.toString(execute.getEntity());

			System.out.println(content);

			httpclient.close();
			execute.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
```
