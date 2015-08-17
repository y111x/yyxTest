package ֣��;

public class HashTest {
	private static int MEM = 23962000;
	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
    System.out.println(RSHash("yang"));
    System.out.println(JSHash("yang"));
	}
	public static long RSHash(String str) {
		int b = 378551;
		int a = 63689;
		long hash = 0;

		for (int i = 0; i < str.length(); i++) {
			hash = hash * a + str.charAt(i);
			a = a * b;
		}
		long h=Math.abs(hash);
		if (h >= MEM)
			return h % MEM;
		return h;
	}

	/* End Of RS Hash Function */

	public static long JSHash(String str) {
		long hash = 1315423911;

		for (int i = 0; i < str.length(); i++) {
			hash ^= ((hash << 5) + str.charAt(i) + (hash >> 2));
		}

		long h=Math.abs(hash);
		if (h >= MEM)
			return h % MEM;
		return h;
	}

	/* End Of JS Hash Function */

	public long PJWHash(String str) {
		long BitsInUnsignedInt = (long) (4 * 8);
		long ThreeQuarters = (long) ((BitsInUnsignedInt * 3) / 4);
		long OneEighth = (long) (BitsInUnsignedInt / 8);
		long HighBits = (long) (0xFFFFFFFF) << (BitsInUnsignedInt - OneEighth);
		long hash = 0;
		long test = 0;

		for (int i = 0; i < str.length(); i++) {
			hash = (hash << OneEighth) + str.charAt(i);

			if ((test = hash & HighBits) != 0) {
				hash = ((hash ^ (test >> ThreeQuarters)) & (~HighBits));
			}
		}

		long h=Math.abs(hash);
		if (h >= MEM)
			return h % MEM;
		return h;
	}

	/* End Of P. J. Weinberger Hash Function */

	public long ELFHash(String str) {
		long hash = 0;
		long x = 0;

		for (int i = 0; i < str.length(); i++) {
			hash = (hash << 4) + str.charAt(i);

			if ((x = hash & 0xF0000000L) != 0) {
				hash ^= (x >> 24);
			}
			hash &= ~x;
		}

		long h=Math.abs(hash);
		if (h >= MEM)
			return h % MEM;
		return h;
	}

	/* End Of ELF Hash Function */

	public long BKDRHash(String str) {
		long seed = 131; // 31 131 1313 13131 131313 etc..
		long hash = 0;

		for (int i = 0; i < str.length(); i++) {
			hash = (hash * seed) + str.charAt(i);
		}

		long h=Math.abs(hash);
		if (h >= MEM)
			return h % MEM;
		return h;
	}

	/* End Of BKDR Hash Function */

	public long SDBMHash(String str) {
		long hash = 0;

		for (int i = 0; i < str.length(); i++) {
			hash = str.charAt(i) + (hash << 6) + (hash << 16) - hash;
		}
		long h=Math.abs(hash);
		if (h >= MEM)
			return h % MEM;
		return h;
	}

	/* End Of SDBM Hash Function */

	public long DJBHash(String str) {
		long hash = 5381;

		for (int i = 0; i < str.length(); i++) {
			hash = ((hash << 5) + hash) + str.charAt(i);
		}
		long h=Math.abs(hash);
		if (h >= MEM)
			return h % MEM;
		return h;
	}

	/* End Of DJB Hash Function */

	public long DEKHash(String str) {
		long hash = str.length();

		for (int i = 0; i < str.length(); i++) {
			hash = ((hash << 5) ^ (hash >> 27)) ^ str.charAt(i);
		}
		long h=Math.abs(hash);
		if (h >= MEM)
			return h % MEM;
		return h;
	}

	/* End Of DEK Hash Function */

	public long BPHash(String str) {
		long hash = 0;

		for (int i = 0; i < str.length(); i++) {
			hash = hash << 7 ^ str.charAt(i);
		}
		long h=Math.abs(hash);
		if (h >= MEM)
			return h % MEM;
		return h;
	}

	/* End Of BP Hash Function */

	public long FNVHash(String str) {
		long fnv_prime = 0x811C9DC5;
		long hash = 0;

		for (int i = 0; i < str.length(); i++) {
			hash *= fnv_prime;
			hash ^= str.charAt(i);
		}long h=Math.abs(hash);
		if (h >= MEM)
			return h % MEM;
		return h;
	}

	/* End Of FNV Hash Function */

	public long APHash(String str) {
		long hash = 0xAAAAAAAA;

		for (int i = 0; i < str.length(); i++) {
			if ((i & 1) == 0) {
				hash ^= ((hash << 7) ^ str.charAt(i) * (hash >> 3));
			} else {
				hash ^= (~((hash << 11) + str.charAt(i) ^ (hash >> 5)));
			}
		}
		long h=Math.abs(hash);
		if (h >= MEM)
			return h % MEM;
		return h;
	}
	/* End Of AP Hash Function */
}
