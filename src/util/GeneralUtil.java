package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import util.exceptions.FormatException;

/**
 * 
 * A Class with utility default.
 * 
 * @author regrettabledanger10
 *
 */
public class GeneralUtil {
	
	/**
     * Binary search for exact match using Arrays.binarySearch.
     */
    public static <T extends Comparable<? super T>> int binarySearch(T[] arr, T value) {
        int idx = Arrays.binarySearch(arr, value);
        return idx >= 0 ? idx : -1;
    }

    /**
     * Finds the first index i such that arr[i] >= value (lower bound).
     */
    public static <T extends Comparable<? super T>> int lowerBound(T[] arr, T value) {
        int lo = 0, hi = arr.length;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (arr[mid].compareTo(value) < 0) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        return lo < arr.length ? lo : -1;
    }

    /**
     * Finds the first index i such that arr[i] > value (upper bound).
     */
    public static <T extends Comparable<? super T>> int upperBound(T[] arr, T value) {
        int lo = 0, hi = arr.length;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (arr[mid].compareTo(value) <= 0) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        return lo < arr.length ? lo : -1;
    }

    /** Wrapper: first index with element >= value */
    public static <T extends Comparable<? super T>> int binarySearchGreaterOrEqualThan(T[] arr, T value) {
        return lowerBound(arr, value);
    }

    /** Wrapper: first index with element > value */
    public static <T extends Comparable<? super T>> int binarySearchGreaterThan(T[] arr, T value) {
        return upperBound(arr, value);
    }

    /** Wrapper: last index with element <= value */
    public static <T extends Comparable<? super T>> int binarySearchLessOrEqualThan(T[] arr, T value) {
        int ub = upperBound(arr, value);
        return ub > 0 ? ub - 1 : -1;
    }

    /** Wrapper: last index with element < value */
    public static <T extends Comparable<? super T>> int binarySearchLessThan(T[] arr, T value) {
        int lb = lowerBound(arr, value);
        return lb > 0 ? lb - 1 : -1;
    }

	/**
	 * 
	 * Check if the something boolean is true.
	 * 
	 * @param s the array boolean to check.
	 * @return true if something boolean is true, false otherwise
	 */
	public static boolean casesToEqual(boolean[] booleans) {
		return casesToEqual(PrimitiveTypesArrayWrapper.wrap(booleans), (arr) -> Arrays.stream(arr).anyMatch(e -> e == true));
	}

	/**
	 * 
	 * Check if the String contains something case of the cases given.
	 * 
	 * @param s the String to check.
	 * @param cases the cases to be cmpared.
	 * @return true if equals with something case, false otherwise
	 */
	public static boolean casesToEqual(String s, String[] cases) {
		return casesToEqual(s, cases, (value, arr) -> Arrays.stream(arr).anyMatch(value::contains));
	}
	
	public static <T> boolean casesToEqual(T[] arr, Predicate<T[]> predicate) {
		return predicate.test(arr);
	}
	
	public static <T> boolean casesToEqual(T value, T[] arr, BiPredicate<T, T[]> biPredicate) {
		return biPredicate.test(value, arr);
	}

	/**
	 * Filters the characters of the string.
	 * 
	 * @param str the string to be filtered.
	 * @param filter the predicate, to be filter the string.
	 * @param inverse is the predicate is true, this inverts.
	 * @return the String filtered.
	 */
	public static final String charactersFilter(String str, Predicate<Character> filter, boolean inverse) {
		if(inverse) {
			filter = filter.negate();
		}
		StringBuilder filteredChars = new StringBuilder(str.length());
		for(char ch : str.toCharArray()) {
			if(filter.test(ch)) {
				filteredChars.append(ch);
			}
		}
		filteredChars.trimToSize();
		return filteredChars.toString();
	}
	
	public static int[] convertIntArgsStringToArrayInt(String arrStr) throws FormatException {
		if(!RegexEnum.ONLY_NUMBER_COMA_SPACE.isValid(arrStr)) {
			throw new FormatException("The format is incorrect, must be like: \"10, 9, 9, 2\"");
		}
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		String[] split = arrStr.split(" ");
		for(String indexSplit : split) {
			numbers.add(Integer.parseInt(onlyDigits(indexSplit)));
		}
		numbers.trimToSize();
		int[] convert = numbers.stream().mapToInt(Integer::intValue).toArray();
		return convert;
	}
	
	public <T> T[] concatArrays(T[] arr1, T[] arr2) {
		T[] result = Arrays.copyOf(arr1, arr1.length + arr2.length);
		System.arraycopy(arr2, 0, result, arr1.length, arr2.length);
		return result;
	}
	
	/**
	 * 
	 * Get the amount of the words of an Scanner.
	 * 
	 * @param sc the Scanner to be extract the amount of the words.
	 * @return the amount of the words.
	 * @author regrettabledanger10.
	 */
	public final static int countWords(final Scanner sc) {
		String str = sc.nextLine();
		if(str.length() == 0) {
			return 0;
		}
		String[] words = str.split(" ");
		return words.length;
	}
	
	/**
	 * 
	 * Get the amount of word of an String.
	 * 
	 * @param str the String to be extract the amount of the words.
	 * @return the amount of the words.
	 * @author regrettabledanger10.
	 */
	public final static int countWords(final String str) {
		if(str.length() == 0) {
			return 0;
		}
		String[] words = str.split(" ");
		return words.length;
	}
	
	/**
	 *
	 * Get only digits from String.
	 *
	 * @param str      String to be extract the numbers.
	 * @return the numbers..
	 * @author regrettabledanger10.
	 */
	public static String onlyDigits(String str) {
		return charactersFilter(str, Character::isDigit, false);
	}
	
	/**
	 *
	 * Get only letters from String.
	 *
	 * @param str      String to be extract the letters.
	 * @return the letters.
	 * @author regrettabledanger10.
	 */
	public static String onlyLetters(String str) {
		return charactersFilter(str, Character::isDigit, true);
	}
	
	/**
	 * 
	 * Search a letter in the String and return the position.
	 * The String is converted in a char array to get the position.
	 * 
	 * @param str the string to be searched
	 * @param key the letter to be searched for
	 * @return the index of the letter in the String, -1 if the letter cannot be found.
	 */
	public static int searchLetter(String str, String key) {
		char[] chs = str.toCharArray();
		for(int ch = 0; ch < chs.length; ch++) {
			if(String.valueOf(chs[ch]).equals(key)) {
				return ch;
			}
		}
		return -1;
	}
	
	/**
	 * Search a letter in the String and returns the positions where it coincides.
	 * The String is converted in a char array to get the positions.
	 * 
	 * @param str the string to be searched.
	 * @param key the letter to be searched for.
	 * @return an Array int with the positions.
	 */
	public static int[] searchLetterPositions(String str, String key) {
		char[] chars = str.toCharArray();
		ArrayList<Integer> positions = new ArrayList<Integer>();
		for(int charr = 0; charr < chars.length; charr++) {
			if(String.valueOf(chars[charr]).equals(key)) {
				positions.add(charr);
			}
		}
		positions.trimToSize();
		int[] allPositions = positions.stream().mapToInt(Integer::intValue).toArray();
		return allPositions;
	}
	
	/**
	 * 
	 * Orders the String separating by lines, reaching the given letters limit add a line separator.
	 * 
	 * @param s the String to order.
	 * @param limit the letters limit.
	 * @return the string ordered.
	 * @author regrettabledanger10.
	 */
	public static String separatorFromTo(String s, int limit) {
		String value4 = "";
		char[] chars = s.toCharArray();
		for(int i = 0; i < chars.length; i++) {
			if(i%limit == 0) {
				value4 = value4 + "\n" + chars[i];
				continue;
			}
			value4 = value4 + chars[i];
		}
		return value4;
	}
	
	/**
	 * 
	 * Orders the String separating by lines, each space add a line separator..
	 * 
	 * @param s the String to order.
	 * @return the string ordered.
	 * @author regrettabledanger10.
	 */
	public static String separatorSpace(String s) {
		String value4 = "";
		char[] chars = s.toCharArray();
		for(int i = 0; i < chars.length; i++) {
			if(chars[i] == ' ') {
				value4 = value4 + "\n" + chars[i];
				continue;
			}
			value4 = value4 + chars[i];
		}
		return value4;
	}
	
	/**
	 * Sort the array into natural or reverse order, by the Comparator c.
	 * 
	 * @param <T> The type of objects.
	 * @param arr the array to sort
	 * @param c the Comparator, if is Comparator.naturalOrder(), the order to set is natural, if is Comparator.reverseOrder(), the order is from less to higher.
	 */
	public final static <T> void sortNaturalOrReverse(T[] arr, Comparator <? super T> c) {
		for(short i = 0; i < arr.length-1; i++) {
			for(short j = 0; j < arr.length-i-1; j++) {
				if(c.compare(arr[j], arr[j+1]) > 0) {
					T temp = arr[j];
					arr[j] = arr[j+1];
					arr[j+1] = temp;
				}
			}
		}
	}
	
	/**
	 * in an array, must store the positions of the another array, like: for(i = 0; i < array.length; i++) {anotherArray[i] = i;}.
	 * 
	 * @param a a array to get the position.
	 * @return a position of the array.
	 * @author regrettabledanger10.
	 * @param <T>
	 */
	public static final <T> String stringContentArray(T[] a) {
		final String count = " is in the position ";
		short iMax = (short) (a.length - 1);
		StringBuilder deep = new StringBuilder();
		for (short content = 0; content < a.length; content++) {
			deep.append(a[content]);
			deep.append(count + "[" + content + "]");
			deep.append("\n");
			if (content == iMax) {
				return deep.toString();
			}
		}
		return "";
	}
	
	/**
	 * 
	 * Converts an array two dimensional into one dimensional array.
	 * @param <T>
	 * 
	 * @param src the two dimensional array to convert.
	 * @return the array onw dimensional with the content (omits the null content);
	 */
	public static <T> List<T> toListOneDimensional(final T[][] src) {
		List<T> oneDimensional = new ArrayList<T>();
		for(T[] arraycopy : src) {
			if(arraycopy != null) {
				Arrays.asList(arraycopy).forEach(oneDimensional::add);
			}
		}
		oneDimensional.removeAll(Collections.singleton(null));
		return oneDimensional;
	}
	
	private GeneralUtil() {}
	
}
